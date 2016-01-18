import java.util.LinkedList;
import java.util.List;


public class ClosureConversion implements ObjVisitor<Exp> {
	LinkedList<Closure> varLibre = new LinkedList<Closure>();
	int index = 0;
	
	private class Closure {
		Id proc;
		LinkedList<Var> varL = new LinkedList<Var>();
		public Closure(Id proc) {
			this.proc = proc;
		}
	}

	@Override
	public Exp visit(Unit e) {
		return e;
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
		int prec = index;
		e.e.env = e.env;
		e.e = e.e.accept(this);
		index = prec;
		return e;
	}

	@Override
	public Exp visit(Neg e) {
		int prec = index;
		e.e.env = e.env;
		e.e = e.e.accept(this);
		index = prec;
		return e;
	}

	@Override
	public Exp visit(Add e) {
		int prec = index;
		e.e1.env = e.env;
		e.e2.env = e.env;
		e.e1 = e.e1.accept(this);
		e.e2 = e.e2.accept(this);
		index = prec;
		return e;
	}

	@Override
	public Exp visit(Sub e) {
		int prec = index;
		e.e1.env = e.env;
		e.e2.env = e.env;
		e.e1 = e.e1.accept(this);
		e.e2 = e.e2.accept(this);
		index = prec;
		return e;
	}

	@Override
	public Exp visit(FNeg e) {
		int prec = index;
		e.e.env = e.env;
		e.e = e.e.accept(this);
		index = prec;
		return e;
	}

	@Override
	public Exp visit(FAdd e) {
		int prec = index;
		e.e1.env = e.env;
		e.e2.env = e.env;
		e.e1 = e.e1.accept(this);
		e.e2 = e.e2.accept(this);
		index = prec;
		return e;
	}

	@Override
	public Exp visit(FSub e) {
		int prec = index;
		e.e1.env = e.env;
		e.e2.env = e.env;
		e.e1 = e.e1.accept(this);
		e.e2 = e.e2.accept(this);
		index = prec;
		return e;
	}

	@Override
	public Exp visit(FMul e) {
		int prec = index;
		e.e1.env = e.env;
		e.e2.env = e.env;
		e.e1 = e.e1.accept(this);
		e.e2 = e.e2.accept(this);
		index = prec;
		return e;
	}
	
	@Override
	public Exp visit(Mul e) {
		int prec = index;
		e.e1.env = e.env;
		e.e2.env = e.env;
		e.e1 = e.e1.accept(this);
		e.e2 = e.e2.accept(this);
		index = prec;
		return e;
	}

	@Override
	public Exp visit(FDiv e) {
		int prec = index;
		e.e1.env = e.env;
		e.e2.env = e.env;
		e.e1 = e.e1.accept(this);
		e.e2 = e.e2.accept(this);
		index = prec;
		return e;
	}

	@Override
	public Exp visit(Eq e) {
		int prec = index;
		e.e1.env = e.env;
		e.e2.env = e.env;
		e.e1 = e.e1.accept(this);
		e.e2 = e.e2.accept(this);
		index = prec;
		return e;
	}

	@Override
	public Exp visit(LE e) {
		int prec = index;
		e.e1.env = e.env;
		e.e2.env = e.env;
		e.e1 = e.e1.accept(this);
		e.e2 = e.e2.accept(this);
		index = prec;
		return e;
	}

	@Override
	public Exp visit(If e) {
		int prec = index;
		e.e1.env = e.env;
		e.e2.env = e.env;
		e.e3.env = e.env;
		e.e1 = e.e1.accept(this);
		e.e2 = e.e2.accept(this);
		e.e3 = e.e3.accept(this);
		index = prec;
		return e;
	}

	@Override
	public Exp visit(Let e) {
		int prec = index;
		e.e1.env = e.env;
		e.e1 = e.e1.accept(this);
		e.e2.env = e.env;
		e.e2.addEnv(e.id, new TVar(e.id.id));
		e.e2.accept(this);
		e.e2.env.removeFirst();
		index = prec;
		return e;
	}

	@Override
	public Exp visit(Var e) {
		Type t = e.rechercheEnv();
		if (t == null) {
			varLibre.get(index).varL.add(e);
		}
		return e;
	}

	@Override
	public Exp visit(LetRec e) {
		int prec = index;
		if (e.fd.e instanceof LetRec && ((LetRec)e.fd.e).fd.type instanceof TFun) {
			LetRec newLetRec = (LetRec)e.fd.e.clone();
			List<Exp> newArgs = new LinkedList<Exp>();
			// ajout des parametres
			for (Id idE : e.fd.args) {
				boolean isInE2 = false;
				for (Id idE2 : ((LetRec)e.fd.e).fd.args) {
					if (idE.equals(idE2)) {
						isInE2 = true;
					}
				}
				if (!isInE2) {
					newLetRec.fd.args.add(idE);
				} else {
					newArgs.add(new Var(idE));
				}
			}
			// liaison avec le nouveau fils
			Exp E2Prec = (Var)newLetRec.e.clone();
			newLetRec.e = (LetRec)e.clone();
			((LinkedList<Exp>)newArgs).addFirst(E2Prec);
			((LetRec)newLetRec.e).fd.e = e.fd.e;
			newLetRec.env = e.env;
			newLetRec = (LetRec) newLetRec.accept(this);
			index = prec;
			return newLetRec;
		}
		
		varLibre.addLast(new Closure(e.fd.id));
		index = varLibre.size()-1;
		e.addEnv(e.fd.id, new TVar(e.fd.id.id));
		e.fd.e.env.addAll(e.env);
		for (Id id : e.fd.args) {
			e.fd.e.addEnv(id, new TVar(id.id));
		}
		e.fd.e = e.fd.e.accept(this);
		for (Id id : e.fd.args) {
			e.fd.e.env.removeFirst();
		}
		e.e.env.addAll(e.env);
		e.e = e.e.accept(this);
		e.env.removeFirst();
		index = prec;
		return e;
	}

	@Override
	public Exp visit(App e) {
		int prec = index;
		if (e.e instanceof Var) {
			for (Closure c : varLibre) {
				if (c.proc.equals(((Var)e.e).id)) {
					e.closure = c.varL;
				}
			}
		}
		index = prec;
		return e;
	}

	@Override
	public Exp visit(Tuple e) {
		int prec = index;
		// TODO Auto-generated method stub
		index = prec;
		return null;
	}

	@Override
	public Exp visit(LetTuple e) {
		int prec = index;
		// TODO Auto-generated method stub
		index = prec;
		return e;
	}

	@Override
	public Exp visit(Array e) {
		int prec = index;
		// TODO Auto-generated method stub
		index = prec;
		return e;
	}

	@Override
	public Exp visit(Get e) {
		int prec = index;
		index = prec;
		return e;
	}

	@Override
	public Exp visit(Put e) {
		int prec = index;
		index = prec;
		return e;
	}

}
