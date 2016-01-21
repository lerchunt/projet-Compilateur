import java.util.LinkedList;


public class RegistreAllocation implements Visitor {
	
	public RegistreAllocation() {
		for (int i=4; i<12; i++) {
			String nom = String.format("r%d", i);
			tabReg.add(new Registre(nom));
		}
	}
	
	static LinkedList<Var> tabVar = new LinkedList<>();
	static LinkedList<Registre> tabReg= new LinkedList<>();
	static int spillCmp = 16;
	
	static String getRegistre(Id v) {
		Var var = null;
		for (Var vs : tabVar) {
			if (vs.id.equals(v)) {
				var = vs;
			}
		}
		for (Registre r : tabReg) {
			if (r.var != null) {
				if (r.var.id.equals(v)) {
					if (var != null) {
						var.dec();
					}
					if (var != null && var.nbOccurence <= 0) {
						r.var = null;
					}
					return r.nom;
				}
			}
		}
		return allocRegistre(v);
	}
	
	static private String allocRegistre(Id v) {
		for (Registre r : tabReg) {
			if (r.var == null) {
				for (Var var : tabVar) {
					if (var.id.equals(v)) {
						if (var != null) {
							var.dec();
						}
						r.var = var;
						return r.nom;
					} 
				}
				Var var = new Var(v);
				r.var = var;
				return r.nom;
			}
		}
		return null;
	}
	
	static public String spillInit(Id v) {
		String id_registre = String.format("r%d", spillCmp); 
		Registre newR= new Registre(id_registre);
		newR.var = new Var(v);
		tabReg.addLast(newR);
		spillCmp++;
		return id_registre;
	}

	static public String spillStart(String id_registre) {
		String retour = "\tSUB\tsp, sp, #4\n";
		retour = String.format("%s\tSTR\t%s, [sp,#0]\n",retour, id_registre);
		return retour;
	}
	
	static public String spillEnd(String id_registre) {
		String retour = "\tADD\tsp, sp, #4\n";
		retour += String.format("\tLDR\t%s, [sp,#0]\n", id_registre);
		tabReg.removeLast();
		spillCmp--;
		return retour;
	}

	static void add(Id v, String reg) {
		Var var = new Var(v);
		tabVar.add(var);
		Registre r = new Registre(reg);
		r.var = var;
		tabReg.add(r);
	}
	
	static void sup(Id v) {
		for (Registre r : tabReg) {
			if (r.var != null) {
				if (r.var.id.equals(v)) {
					r.var = null;
				}
			}
		}
		for (Var var : (LinkedList<Var>)tabVar.clone()) {
			if (var.id.equals(v)) {
				tabVar.remove(var);
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
		tabVar.add(new Var(e.id));
		e.e1.accept(this);
		e.e2.accept(this);
	}

	@Override
	public void visit(Var e) {
		for (Var v : tabVar) {
			if (v.id.equals(e.id)) {
				v.inc();
			}
		}
	}

	@Override
	public void visit(LetRec e) {
		for (Id id : e.fd.args) {
			tabVar.add(new Var(id));
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
			tabVar.add(new Var(id));
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
		public Var var = null;
		
		public Registre(String nom) {
			this.nom = nom;
		}
	}


	public static void printTabVar() {
		String retour = "TabVar : \n";
		for (Var v : tabVar) {
			retour = String.format("%s%s - %d | ", retour, v.id.id, v.nbOccurence);
		}
		System.out.println(retour);
	}

}

