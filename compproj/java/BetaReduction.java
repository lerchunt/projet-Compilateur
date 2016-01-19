import java.util.LinkedList;


public class BetaReduction implements ObjVisitor<Exp> {
	
	private LinkedList<AssXY> env = new LinkedList<AssXY>();
	
	private class AssXY {
		Id x;
		Var y;
		public AssXY(Id id, Var e1) {
			this.x = id;
			this.y = e1;
		}
	}
	
	private Exp find(Exp x) {
		if (x instanceof Var) {
			for (AssXY a : env) {
				if (a.x.equals(((Var)x).id)) {
					return a.y;
				}
			}
		}
		return x;
	}

	@Override
	public Exp visit(Bool e) {
		return e;
	}

	@Override
	public Exp visit(Int e) {
		return e;
	}

	@Override
	public Exp visit(Float e) {
		return e;
	}

	@Override
	public Exp visit(Not e) {
		return BetaOpUn(e);
	}

	@Override
	public Exp visit(Neg e) {
		return BetaOpUn(e);
	}

	@Override
	public Exp visit(Add e) {
		return BetaOpBin(e);
	}

	@Override
	public Exp visit(Sub e) {
		return BetaOpBin(e);
	}

	@Override
	public Exp visit(FNeg e) {
		return BetaOpUn(e);
	}

	@Override
	public Exp visit(FAdd e) {
		return BetaOpBin(e);
	}

	@Override
	public Exp visit(FSub e) {
		return BetaOpBin(e);
	}

	@Override
	public Exp visit(FMul e) {
		return BetaOpBin(e);
	}

	@Override
	public Exp visit(Mul e) {
		return BetaOpBin(e);
	}

	@Override
	public Exp visit(FDiv e) {
		return BetaOpBin(e);
	}

	@Override
	public Exp visit(Eq e) {
		return BetaOpBin(e);
	}

	@Override
	public Exp visit(LE e) {
		return BetaOpBin(e);
	}

	@Override
	public Exp visit(If e) {
		If newIf = (If)e.clone();
		newIf.e1 = e.e1.accept(this);
		newIf.e2 = e.e2.accept(this);
		newIf.e3 = e.e3.accept(this);
		return newIf;
	}

	@Override
	public Exp visit(Let e) {
		Exp newE1 = e.e1.accept(this);
		if( newE1 instanceof Var) {
			env.addFirst(new AssXY(e.id, (Var)e.e1));
			Exp newE2 = e.e2.accept(this);
			Let newLet = (Let)e.clone();
			newLet.e1 = newE1;
			newLet.e2 = newE2;
			return newLet;
		}
		Exp newE2 = e.e2.accept(this);
		return new Let(e.id, e.t, newE1, newE2);
	}

	@Override
	public Exp visit(Var e) {
		return find(e);
	}

	@Override
	public Exp visit(LetRec e) {
		LetRec newLetRec = (LetRec) e.clone();
		newLetRec.fd.id = e.fd.id;
		newLetRec.fd.type = e.fd.type;
		newLetRec.fd.args = e.fd.args;
		newLetRec.fd.e = e.fd.e.accept(this);
		newLetRec.e = e.e.accept(this);
		return newLetRec;
	}

	@Override
	public Exp visit(App e) {
		App newApp = (App) e.clone();
		newApp.e = find(e.e);
		LinkedList<Exp> args = new LinkedList<Exp>();
		for (Exp es : e.es) {
			args.add(find(es));
		}
		newApp.es = args;
		return newApp;
	}

	@Override
	public Exp visit(Tuple e) {
		for (Exp exp : e.es) {
			exp = exp.accept(this);
		}
		return e;
	}

	@Override
	public Exp visit(LetTuple e) {
		e.e1 = e.e1.accept(this);
		e.e2 = e.e2.accept(this);
		return e;
	}

	@Override
	public Exp visit(Array e) {
		e.e1 = e.e1.accept(this);
		e.e2 = e.e2.accept(this);
		return e;
	}

	@Override
	public Exp visit(Get e) {
		e.e1 = e.e1.accept(this);
		e.e2 = e.e2.accept(this);
		return e;
	}

	@Override
	public Exp visit(Put e) {
		e.e1 = e.e1.accept(this);
		e.e2 = e.e2.accept(this);
		e.e3 = e.e3.accept(this);
		return e;
	}

	@Override
	public Exp visit(Unit unit) {
		return unit;
	}
	
	private OpBin BetaOpBin(OpBin e) {
		OpBin newOp = (OpBin)e.clone();
		newOp.e1 = find(e.e1);
		newOp.e2 = find(e.e2);
		return newOp;
	}
	
	private OpUn BetaOpUn(OpUn e) {
		OpUn newOp = (OpUn)e.clone();
		newOp.e = find(e.e);
		return newOp;
	}

}
