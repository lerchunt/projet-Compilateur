import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


public class ReductionOfNestedLet implements ObjVisitor<Exp> {

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
		e.e = e.e.accept(this);
		return ReducOpUn(e);
	}

	@Override
	public Exp visit(Neg e) {
		e.e = e.e.accept(this);
		return ReducOpUn(e);
	}

	@Override
	public Exp visit(Add e) {
		e.e1 = e.e1.accept(this);
		e.e2 = e.e2.accept(this);
		return ReducOpBin(e);
	}

	@Override
	public Exp visit(Sub e) {
		e.e1 = e.e1.accept(this);
		e.e2 = e.e2.accept(this);
		return ReducOpBin(e);
	}

	@Override
	public Exp visit(FNeg e) {
		e.e = e.e.accept(this);
		return ReducOpUn(e);
	}

	@Override
	public Exp visit(FAdd e) {
		e.e1 = e.e1.accept(this);
		e.e2 = e.e2.accept(this);
		return ReducOpBin(e);
	}

	@Override
	public Exp visit(FSub e) {
		e.e1 = e.e1.accept(this);
		e.e2 = e.e2.accept(this);
		return ReducOpBin(e);
	}

	@Override
	public Exp visit(FMul e) {
		e.e1 = e.e1.accept(this);
		e.e2 = e.e2.accept(this);
		return ReducOpBin(e);
	}

	@Override
	public Exp visit(Mul e) {
		e.e1 = e.e1.accept(this);
		e.e2 = e.e2.accept(this);
		return ReducOpBin(e);
	}

	@Override
	public Exp visit(FDiv e) {
		e.e1 = e.e1.accept(this);
		e.e2 = e.e2.accept(this);
		return ReducOpBin(e);
	}

	@Override
	public Exp visit(Eq e) {
		e.e1 = e.e1.accept(this);
		e.e2 = e.e2.accept(this);
		return ReducOpBin(e);
	}

	@Override
	public Exp visit(LE e) {
		e.e1 = e.e1.accept(this);
		e.e2 = e.e2.accept(this);
		return ReducOpBin(e);
	}

	@Override
	public Exp visit(If e) {
		e.e1 = e.e1.accept(this);
		e.e2 = e.e2.accept(this);
		e.e3 = e.e3.accept(this);
		return e;
	}

	@Override
	public Exp visit(Let e) {
		e.e1 = e.e1.accept(this);
		e.e2 = e.e2.accept(this);
		return ReducLet(e);
	}

	@Override
	public Exp visit(Var e) {
		return e;
	}

	@Override
	public Exp visit(LetRec e) {
		e.fd.e = e.fd.e.accept(this);
		e.e = e.e.accept(this);
		return e;
	}

	@Override
	public Exp visit(App e) {
		Exp newExp = e;
		e.e = e.e.accept(this);
		
		List<Exp> newEs = new LinkedList<Exp>();
		for (Exp exp : e.es) {
			Exp argOpt = exp.accept(this);
			if (argOpt instanceof Let) {
				Let cour = (Let) argOpt;
				while (cour.e2 instanceof Let) {
					cour = (Let)cour.e2;
				}
				Let newExp2 = (Let)argOpt.clone();
				argOpt = cour.e2;
				cour = newExp2;
				while (cour.e2 instanceof Let) {
					cour = (Let)cour.e2;
				}
				cour.e2 = newExp;
				newExp = newExp2;
			}
			newEs.add(argOpt);
		}
		if (!newEs.isEmpty()) {
			e.es = newEs;
		}
		return newExp;
	}

	@Override
	public Exp visit(Tuple e) {
		for (Exp exp : e.es) {
			exp = exp.accept(this);
		}
		return ReducTuple(e);
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


	private Exp ReducLet(Let e) {
		if (!e.e1.isVar()) {
			if (e.e1 instanceof Let) {
				Let cour = (Let)e.e1;
				while (cour.e2 instanceof Let) {
					cour = (Let)cour.e2;
				}
				Let newExp = (Let)e.e1.clone();
				e.e1 = cour.e2;
				cour = newExp;
				while (cour.e2 instanceof Let) {
					cour = (Let)cour.e2;
				}
				cour.e2 = e;
				return newExp;
			} 
		}
		return e;
	}
	
	private Exp ReducOpBin(OpBin e) {
		Exp retour = e;
		
		if (e.e2 instanceof Let) {
			Let newExp = (Let)e.e2.clone();
			Exp cour = e.e2;
			while (cour instanceof Let) {
				cour = ((Let)cour).e2;
			}
			Exp tmpE2 = cour;
			e.e2 = tmpE2;
			cour = newExp;
			while (((Let)cour).e2 instanceof Let) {
				cour = ((Let)cour).e2;
			}
			((Let)cour).e2 = e;
			retour=newExp;
		}
		if (e.e1 instanceof Let) {
			Let newExp = (Let)e.e1.clone();
			Exp cour = e.e1;
			while (cour instanceof Let) {
				cour = ((Let)cour).e2;
			}
			Exp tmpE2 = cour;
			e.e1 = tmpE2;
			cour = newExp;
			while (((Let)cour).e2 instanceof Let) {
				cour = ((Let)cour).e2;
			}
			((Let)cour).e2 = retour;
			retour=newExp;
		}
		return retour;
	}
	private Exp ReducOpUn(OpUn e) {
		Exp retour = e;
		
		if (e.e instanceof Let) {
			Let newExp = (Let)e.e.clone();
			Exp cour = e.e;
			while (cour instanceof Let) {
				cour = ((Let)cour).e2;
			}
			Exp tmpE2 = cour;
			e.e = tmpE2;
			cour = newExp;
			while (((Let)cour).e2 instanceof Let) {
				cour = ((Let)cour).e2;
			}
			((Let)cour).e2 = e;
			retour=newExp;
		}
		return retour;
	}

	private Exp ReducTuple(Tuple e) {
		List<Exp> liste = new LinkedList<Exp>();
		Exp newExp = e;
		Collections.reverse(e.es);
		for (Exp arg : e.es) {
			if (arg instanceof Let) {
				Let cour = (Let) arg;
				while (cour.e2 instanceof Let) {
					cour = (Let)cour.e2;
				}
				Let newExp2 = (Let)arg.clone();
				arg = cour.e2;
				cour = newExp2;
				while (cour.e2 instanceof Let) {
					cour = (Let)cour.e2;
				}
				cour.e2 = newExp;
				newExp = newExp2;
			}
			liste.add(arg);
		}
		if (!liste.isEmpty()) {
			e.es = liste;
		}
		Collections.reverse(e.es);
		return newExp;
	}
}
