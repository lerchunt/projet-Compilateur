import java.util.Collections;
import java.util.LinkedList;


public class RegistreAllocation implements Visitor {
	
	public RegistreAllocation() {
		for (int i=4; i<11; i++) {
			String nom = String.format("r%d", i);
			tabReg.add(new Registre(nom));
		}
	}
	
	static LinkedList<Noeud> tabVar = new LinkedList<>();
	static LinkedList<Registre> tabReg= new LinkedList<>();
	
	static String getRegistre(Id v) {
		Var var = null;
		for (Noeud n : tabVar) {
			if (n.var.id.equals(v)) {
				var = n.var;
			}
		}
		for (Registre r : tabReg) {
			if (!r.var.isEmpty()) {
				if (r.var.getLast().id.equals(v)) {
					if (var != null) {
						var.dec();
					}
					if (var != null && var.nbOccurence <= 0) {
						r.var.removeLast();
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
		for (Noeud n : tabVar) {
			if (id.equals(n.var.id)) {
				for (Registre reg : tabReg) {
					if ( !reg.var.isEmpty() && !n.arc.contains(reg.var.getLast().id)) {
						reg.var.add(n.var);
						return reg.nom;
					}
				}
			}
		}
		return null;
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
				return retour;
			}
		}
		return retour;
	}

	static void add(Id v, String reg) {
		Var var = new Var(v);
		tabVar.add(new Noeud(var));
		Registre r = new Registre(reg);
		r.var.addLast(var);
		tabReg.add(r);
	}
	
	static void sup(Id v) {
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
	}
	
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
		public LinkedList<Id> arc = new LinkedList<Id>();
		
		public Noeud(Var var) {
			this.var = var;
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

