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
	
	static LinkedList<Registre> tabReg= new LinkedList<>();
	
	static String getRegistre(Var v) {
		for (Registre r : tabReg) {
			if (r.var.equals(v)) {
				return r.nom;
			}
		}
		return allocRegistre(v);
	}
	
	static private String allocRegistre(Var v) {
		for (Registre r : tabReg) {
			if (r.var == null) {
				v.dec();
				r.var = v;
			}
			return r.nom;
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
	}

	@Override
	public void visit(Var e) {
		e.inc();
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
