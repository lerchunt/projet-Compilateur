import java.util.Collections;
import java.util.LinkedList;


public class RegistreAllocation implements Visitor {
	
	public RegistreAllocation() {
		for (int i=3; i<12; i++) {
			String nom = String.format("r%d", i);
			tabReg.add(new Registre(nom));
		}
	}
	
	static LinkedList<Noeud> tabVar = new LinkedList<>();
	static LinkedList<Registre> tabReg= new LinkedList<>();
	
	static String getRegistre(Id v) {
		Noeud noeud = null;
		for (Noeud n : tabVar) {
			if (n.var.id.equals(v)) {
				if (n.regCour != null && !n.regCour.isEmpty() && n.regCour.contains("[sp,")) {
					n.var.dec();
					return n.regCour;
				}
				noeud = n;
			}
		}
		LinkedList<Registre> liste = (LinkedList<Registre>)tabReg.clone();
		for (Registre r : liste) {
			LinkedList<Var> l = (LinkedList<Var>)r.var.clone();
			for (Var varcour : l) {
				if (varcour.nbOccurence <= 0) {
					r.var.remove(varcour);
				}
			}
			if (!r.var.isEmpty()) {
				if (r.var.getLast().id.equals(v)) {
					if (noeud != null) {
						noeud.var.dec();
						noeud.regCour = r.nom;
						if (noeud.var != null && noeud.var.nbOccurence <= 0) {
							tabReg.get(liste.indexOf(r)).var.removeLast();
						}
					}
					return r.nom;
				}
			}
		}
		return allocRegistre(v);
	}
	
	static private String allocRegistre(Id v) {
		for (Registre r : tabReg) {
			if (r.var.isEmpty()) {
				for (Noeud n : tabVar) {
					if (n.var.id.equals(v)) {
						if (n != null) {
							n.var.dec();
							n.regCour = r.nom;
						}

						r.var.addLast(n.var);
						return r.nom;
					} 
				}
				Var var = new Var(v);
				r.var.addLast(var);
				return r.nom;
			}
		}
		return null;
	}
	
	static public String spillInit(Id id) {
		// on regarde si on peut spiller une variable
		LinkedList<Integer> cmp = new LinkedList<Integer>();
		for (Noeud n : tabVar) {
			if (id.equals(n.var.id)) {
				for (Registre reg : tabReg) {
					Var last = reg.var.getLast();
					if ( reg.var.isEmpty() || !n.arc.contains(last.id)) {
						reg.var.addLast(n.var);
						SpillTabVar();
						n.regCour = reg.nom;
						for (Noeud no : tabVar) {
							if (last.id.equals(no.var.id)) {
								no.regCour = "[sp, #0]";
							}
						}
						return reg.nom;
					}
					int cmpt = 0;
					for (Id i : n.arc) {
						if (reg.var.getLast().id.equals(i)) {
							cmpt ++;
						}
					}
					cmp.add(cmpt);
				}
				SpillTabVar();
				Registre r = tabReg.get(cmp.indexOf(Collections.min(cmp)));
				if (!r.var.isEmpty()) {
					for (Noeud no : tabVar) {
						if (no.var.id.equals(r.var.getLast().id)) {
							no.regCour = "[sp, #0]";
						}
					}
				}
				r.var.addLast(n.var);
				String retour = r.nom;
				n.regCour = retour ;
				return retour;
			}
		}
		return null;
	}

	private static void SpillTabVar() {
		for (Noeud n : tabVar) {
			n.spillReg();
		}
	}
	
	private static void UnSpillTabVar() {
		for (Noeud n : tabVar) {
			n.unSpillReg();
		}
	}

	static public String spillStart(String id_registre) {
		String retour = "\tSUB\tsp, sp, #4\n";
		retour = String.format("%s\tSTR\t%s, [sp,#0]\n",retour, id_registre);
		return retour;
	}
	
	static public String spillEnd(String id_registre) {
		String retour = "\tADD\tsp, sp, #4\n";
		retour += String.format("\tLDR\t%s, [sp,#0]\n", id_registre);
		for (Registre reg : tabReg) {
			if (reg.nom.equals(id_registre)) {
				reg.var.removeLast();
				for (Noeud n : tabVar) {
					if (!reg.var.isEmpty() && n.var.id.equals(reg.var.getLast().id)) {
						n.regCour = id_registre;
					}
				}
				UnSpillTabVar();
				return retour;
			}
		}
		UnSpillTabVar();
		return retour;
	}

	/*static void add(Id v, String reg) {
		Var var = new Var(v);
		tabVar.add(new Noeud(var));
		Registre r = new Registre(reg);
		r.var.addLast(var);
		tabReg.add(r);
	}*/
	
	/*static void sup(Id v) {
		for (Registre r : tabReg) {
			if (!r.var.isEmpty()) {
				if (r.var.getLast().id.equals(v)) {
					r.var.removeLast();
				}
			}
		}
		for (Noeud var : (LinkedList<Noeud>)tabVar.clone()) {
			if (var.var.id.equals(v)) {
				tabVar.remove(var);
			}
		}
	}*/
	
	static void finaliseArc() {
		for (Noeud n : tabVar) {
			boolean endBoucle = false;
			LinkedList<Id> liste = (LinkedList<Id>)n.arc.clone();
			Collections.reverse(liste);
			for (Id id : liste) {
					if (id.equals(n.var.id)) {
						endBoucle = true;
					}
					if (!endBoucle) {
						n.arc.removeLast();
					}
			}
		}
	}
	
	@Override
	public void visit(Unit e) {
	}

	@Override
	public void visit(Bool e) {
	}

	@Override
	public void visit(Int e) {
	}

	@Override
	public void visit(Float e) {
	}

	@Override
	public void visit(Not e) {
		e.e.accept(this);
	}

	@Override
	public void visit(Neg e) {
		e.e.accept(this);
	}

	@Override
	public void visit(Add e) {
		e.e1.accept(this);
		e.e2.accept(this);
	}

	@Override
	public void visit(Sub e) {
		e.e1.accept(this);
		e.e2.accept(this);
	}

	@Override
	public void visit(FNeg e) {
		e.e.accept(this);
	}

	@Override
	public void visit(FAdd e) {
		e.e1.accept(this);
		e.e2.accept(this);
	}

	@Override
	public void visit(FSub e) {
		e.e1.accept(this);
		e.e2.accept(this);
	}

	@Override
	public void visit(FMul e) {
		e.e1.accept(this);
		e.e2.accept(this);
	}

	@Override
	public void visit(Mul e) {
		e.e1.accept(this);
		e.e2.accept(this);
	}

	@Override
	public void visit(FDiv e) {
		e.e1.accept(this);
		e.e2.accept(this);
	}

	@Override
	public void visit(Eq e) {
		e.e1.accept(this);
		e.e2.accept(this);
	}

	@Override
	public void visit(LE e) {
	}

	@Override
	public void visit(If e) {
		e.e1.accept(this);
		e.e2.accept(this);
		e.e3.accept(this);
	}

	@Override
	public void visit(Let e) {
		Var newVar = new Var(e.id);
		tabVar.add(new Noeud(newVar));
		e.e1.accept(this);
		e.e2.accept(this);
	}

	@Override
	public void visit(Var e) {
		for (Noeud n : tabVar) {
			if (n.var.id.equals(e.id)) {
				n.var.inc();
			}
			n.arc.add(e.id);
		}
	}

	@Override
	public void visit(LetRec e) {
		for (Id id : e.fd.args) {
			Var newVar = new Var(id);
			tabVar.add(new Noeud(newVar));
		}
		e.e.accept(this);
		e.fd.e.accept(this);
	}

	@Override
	public void visit(App e) {
		e.e.accept(this);
		for (Exp es : e.es) {
			es.accept(this);
		}
	}

	@Override
	public void visit(Tuple e) {
		for (Exp es : e.es) {
			es.accept(this);
		}
	}

	@Override
	public void visit(LetTuple e) {
		for (Id id : e.ids) {
			Var newVar = new Var(id);
			tabVar.add(new Noeud(newVar));
		}
		e.e1.accept(this);
		e.e2.accept(this);
	}

	@Override
	public void visit(Array e) {
		e.e1.accept(this);
		e.e2.accept(this);
	}

	@Override
	public void visit(Get e) {
		e.e1.accept(this);
		e.e2.accept(this);
	}

	@Override
	public void visit(Put e) {
		e.e1.accept(this);
		e.e2.accept(this);
		e.e3.accept(this);
	}

	
	private static class Registre {
		public String nom;
		public LinkedList<Var> var = new LinkedList<Var>();
		
		public Registre(String nom) {
			this.nom = nom;
		}
	}
	
	private static class Noeud {
		public Var var;
		String regCour;
		int cmpPile = 0;
		public LinkedList<Id> arc = new LinkedList<Id>();
		
		public Noeud(Var var) {
			this.var = var;
		}
		
		public void unSpillReg() {
			if (regCour != null && !regCour.isEmpty() && regCour.contains("[sp,")){
				cmpPile -= 4;
				regCour = String.format("[sp, #%d]",cmpPile);
			}
		}

		public void spillReg() {
			if (regCour != null && !regCour.isEmpty() && regCour.contains("[sp,")){
				cmpPile += 4;
				regCour = String.format("[sp, #%d]",cmpPile);
			}
		}
	}


	public static void printTabVar() {
		String retour = "TabVar : \n";
		for (Noeud v : tabVar) {
			retour = String.format("%s%s - %d | ", retour, v.var.id.id, v.var.nbOccurence);
		}
		System.out.println(retour);
	}

	public static boolean isInTabVar(Id id) {
		for (Noeud n : RegistreAllocation.tabVar) {
			if (n.var.id.equals(id)) {
				return true;
			}
		}
		return false;
	}

	public static void updateTabVarArc(Var e) {
		boolean afterE = false;
		for (Noeud n : tabVar) {
			if (n.var.id.equals(e.id)) {
				afterE = true;
			}
			if (afterE) {
				if (!n.arc.isEmpty()){
					n.arc.removeFirst();
				}
			}
		}
	}

}

