import java.util.LinkedList;


public class RegistreAllocation implements Visitor {
	
	public RegistreAllocation() {
		for (int i=4; i<12; i++) {
			String nom = String.format("r%d", i);
			tabReg.add(new Registre(nom));
		}
	}
	
	private class Registre {
		public String nom;
		public Var var = null;
		
		public Registre(String nom) {
			this.nom = nom;
		}
	}
	
	static LinkedList<Var> tabVar = new LinkedList<>();
	static LinkedList<Registre> tabReg= new LinkedList<>();
	
	static String getRegistre(Id v) {
		Var var = null;
		for (Var vs : tabVar) {
			if (vs.id.equals(v)) {
				var = vs;
			}
		}
		if (var == null) {
			System.err.println("internal error -- registre allocation");
			System.exit(1);
			return null;
		}
		for (Registre r : tabReg) {
			if (r.var.equals(v)) {
				var.dec();
				return r.nom;
			}
		}
		return allocRegistre(v);
	}
	
	static private String allocRegistre(Id v) {
		for (Registre r : tabReg) {
			if (r.var == null) {
				for (Var var : tabVar) {
					if (var.id.equals(v)) {
						r.var = var;
						return r.nom;
					}
				}
			}
		}
		return null;
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
	}

	@Override
	public void visit(Neg e) {
	}

	@Override
	public void visit(Add e) {
	}

	@Override
	public void visit(Sub e) {
	}

	@Override
	public void visit(FNeg e) {
	}

	@Override
	public void visit(FAdd e) {
	}

	@Override
	public void visit(FSub e) {
	}

	@Override
	public void visit(FMul e) {
	}

	@Override
	public void visit(Mul e) {
	}

	@Override
	public void visit(FDiv e) {
	}

	@Override
	public void visit(Eq e) {
	}

	@Override
	public void visit(LE e) {
	}

	@Override
	public void visit(If e) {
	}

	@Override
	public void visit(Let e) {
		tabVar.add(new Var(e.id));
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
	}

	@Override
	public void visit(App e) {
	}

	@Override
	public void visit(Tuple e) {
	}

	@Override
	public void visit(LetTuple e) {
	}

	@Override
	public void visit(Array e) {
	}

	@Override
	public void visit(Get e) {
	}

	@Override
	public void visit(Put e) {
	}

	@Override
	public void visit(FunDef e) {
	}

}
