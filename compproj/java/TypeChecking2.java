import java.util.LinkedList;

public class TypeChecking2 implements ObjVisitor<LinkedList<Equations>> {

	@Override
	public LinkedList<Equations> visit(Bool e) {
		Equations eq = new Equations(new TBool(), e.typeAttendu);
		LinkedList<Equations> retour = new LinkedList<Equations>();
		retour.add(eq);
		return retour;
	}

	@Override
	public LinkedList<Equations> visit(Int e) {
		Equations eq = new Equations(new TInt(), e.typeAttendu);
		LinkedList<Equations> retour = new LinkedList<Equations>();
		retour.add(eq);
		return retour;
	}

	@Override
	public LinkedList<Equations> visit(Float e) {
		Equations eq = new Equations(new TFloat(), e.typeAttendu);
		LinkedList<Equations> retour = new LinkedList<Equations>();
		retour.add(eq);
		return retour;
	}

	@Override
	public LinkedList<Equations> visit(Not e) {
		Equations eq = new Equations(new TBool(), e.typeAttendu);
		LinkedList<Equations> retour = new LinkedList<Equations>();
		retour.add(eq);
		e.e.typeAttendu = new TBool();
		e.e.env = e.env;
		retour.addAll(e.e.accept(this));
		return retour;
	}

	@Override
	public LinkedList<Equations> visit(Neg e) {
		Equations eq = new Equations(new TInt(), e.typeAttendu);
		LinkedList<Equations> retour = new LinkedList<Equations>();
		retour.add(eq);
		e.e.typeAttendu = new TInt();
		e.e.env = e.env;
		retour.addAll(e.e.accept(this));
		return retour;
	}

	@Override
	public LinkedList<Equations> visit(Add e) {
		Equations eq = new Equations(new TInt(), e.typeAttendu);
		LinkedList<Equations> retour = new LinkedList<Equations>();
		retour.add(eq);
		e.e1.typeAttendu = new TInt();
		e.e1.env = e.env;
		e.e2.typeAttendu = new TInt();
		e.e2.env = e.env;
		retour.addAll(e.e1.accept(this));
		retour.addAll(e.e2.accept(this));
		return retour;
	}

	@Override
	public LinkedList<Equations> visit(Sub e) {
		Equations eq = new Equations(new TInt(), e.typeAttendu);
		LinkedList<Equations> retour = new LinkedList<Equations>();
		retour.add(eq);
		e.e1.typeAttendu = new TInt();
		e.e1.env = e.env;
		e.e2.typeAttendu = new TInt();
		e.e2.env = e.env;
		retour.addAll(e.e1.accept(this));
		retour.addAll(e.e2.accept(this));
		return retour;
	}

	@Override
	public LinkedList<Equations> visit(FNeg e) {
		Equations eq = new Equations(new TFloat(), e.typeAttendu);
		LinkedList<Equations> retour = new LinkedList<Equations>();
		retour.add(eq);
		e.e.typeAttendu = new TFloat();
		e.e.env = e.env;
		retour.addAll(e.e.accept(this));
		return retour;
	}

	@Override
	public LinkedList<Equations> visit(FAdd e) {
		Equations eq = new Equations(new TFloat(), e.typeAttendu);
		LinkedList<Equations> retour = new LinkedList<Equations>();
		retour.add(eq);
		e.e1.typeAttendu = new TFloat();
		e.e1.env = e.env;
		e.e2.typeAttendu = new TFloat();
		e.e2.env = e.env;
		retour.addAll(e.e1.accept(this));
		retour.addAll(e.e2.accept(this));
		return retour;
	}

	@Override
	public LinkedList<Equations> visit(FSub e) {
		Equations eq = new Equations(new TFloat(), e.typeAttendu);
		LinkedList<Equations> retour = new LinkedList<Equations>();
		retour.add(eq);
		e.e1.typeAttendu = new TFloat();
		e.e1.env = e.env;
		e.e2.typeAttendu = new TFloat();
		e.e2.env = e.env;
		retour.addAll(e.e1.accept(this));
		retour.addAll(e.e2.accept(this));
		return retour;
	}

	@Override
	public LinkedList<Equations> visit(FMul e) {
		Equations eq = new Equations(new TFloat(), e.typeAttendu);
		LinkedList<Equations> retour = new LinkedList<Equations>();
		retour.add(eq);
		e.e1.typeAttendu = new TFloat();
		e.e1.env = e.env;
		e.e2.typeAttendu = new TFloat();
		e.e2.env = e.env;
		retour.addAll(e.e1.accept(this));
		retour.addAll(e.e2.accept(this));
		return retour;
	}

	@Override
	public LinkedList<Equations> visit(Mul e) {
		Equations eq = new Equations(new TInt(), e.typeAttendu);
		LinkedList<Equations> retour = new LinkedList<Equations>();
		retour.add(eq);
		e.e1.typeAttendu = new TInt();
		e.e1.env = e.env;
		e.e2.typeAttendu = new TInt();
		e.e2.env = e.env;
		retour.addAll(e.e1.accept(this));
		retour.addAll(e.e2.accept(this));
		return retour;
	}

	@Override
	public LinkedList<Equations> visit(FDiv e) {
		Equations eq = new Equations(new TFloat(), e.typeAttendu);
		LinkedList<Equations> retour = new LinkedList<Equations>();
		retour.add(eq);
		e.e1.typeAttendu = new TFloat();
		e.e1.env = e.env;
		e.e2.typeAttendu = new TFloat();
		e.e2.env = e.env;
		retour.addAll(e.e1.accept(this));
		retour.addAll(e.e2.accept(this));
		return retour;
	}

	@Override
	public LinkedList<Equations> visit(Eq e) {
		Equations eq = new Equations(new TBool(), e.typeAttendu);
		LinkedList<Equations> retour = new LinkedList<Equations>();
		retour.add(eq);
		Type same = Type.gen();
		e.e1.typeAttendu = same;
		e.e1.env = e.env;
		e.e2.typeAttendu = same;
		e.e2.env = e.env;
		retour.addAll(e.e1.accept(this));
		retour.addAll(e.e2.accept(this));
		return retour;
	}

	@Override
	public LinkedList<Equations> visit(LE e) {
		Equations eq = new Equations(new TBool(), e.typeAttendu);
		LinkedList<Equations> retour = new LinkedList<Equations>();
		retour.add(eq);
		Type same = Type.gen();
		e.e1.typeAttendu = same;
		e.e1.env = e.env;
		e.e2.typeAttendu = same;
		e.e2.env = e.env;
		retour.addAll(e.e1.accept(this));
		retour.addAll(e.e2.accept(this));
		return retour;
	}

	@Override
	public LinkedList<Equations> visit(If e) {
		LinkedList<Equations> retour = new LinkedList<Equations>();
		e.e1.typeAttendu = new TBool();
		e.e1.env = e.env;
		e.e2.typeAttendu = e.typeAttendu;
		e.e2.env = e.env;
		e.e3.typeAttendu = e.typeAttendu;
		e.e3.env = e.env;
		retour.addAll(e.e1.accept(this));
		retour.addAll(e.e2.accept(this));
		retour.addAll(e.e3.accept(this));
		return retour;
	}


	@Override
	public LinkedList<Equations> visit(Let e) {
		e.t = Type.gen();
		e.e1.typeAttendu = e.t;
		e.e1.env = e.env;
		e.e2.typeAttendu = e.typeAttendu;
		e.e2.env.addAll(e.env);
		e.e2.addEnv(e.id, e.t);
		LinkedList<Equations> retour = new LinkedList<Equations>();
		retour.addAll(e.e1.accept(this));
		retour.addAll(e.e2.accept(this));
		e.e2.env.removeFirst();
		return retour;
	}

	@Override
	public LinkedList<Equations> visit(Var e) {
		Type Tvar = e.rechercheEnv();
		if (Tvar == null) {
			System.err.println("error "+e.id.id+" if not defined");
			System.exit(1);
		} else {
			LinkedList<Equations> retour = new LinkedList<Equations>();
			retour.add(new Equations(e.typeAttendu, Tvar));
			return retour;
		}
		return null;
	}

	@Override
	public LinkedList<Equations> visit(LetRec e) {
		LinkedList<Equations> retour = new LinkedList<Equations>();
		e.fd.type = new TFun();
		Type newT = Type.gen();
		((TFun)e.fd.type).typeRetour = newT;
		e.fd.e.typeAttendu = newT;
		e.fd.e.env.addAll(e.env);
		for (Id id : e.fd.args) {
			Type newP = Type.gen();
			e.fd.e.addEnv(id, newP);
			((TFun)e.fd.type).typeArgs.add(newP);
		}
		retour.addAll(e.fd.e.accept(this));
		for (Id id : e.fd.args) {
			e.fd.e.env.removeFirst();
		}

		e.e.typeAttendu = e.typeAttendu;
		e.e.env.addAll(e.env);
		e.e.addEnv(e.fd.id, e.fd.type);
		retour.addAll(e.e.accept(this));
		e.e.env.removeFirst();

		return retour;
	}

	@Override
	public LinkedList<Equations> visit(App e) {
		LinkedList<Equations> retour = new LinkedList<Equations>();
		Type tFun = new TFun();
		if (e.e instanceof Var) {
			e.e.env = e.env;
			tFun = ((Var)e.e).rechercheEnv();
			if (tFun instanceof TFun) {
				Equations eq = new Equations(((TFun)tFun).typeRetour , e.typeAttendu);
				retour.add(eq);
				LinkedList<Type> tParams = new LinkedList<Type>();
				if (e.es.size() == ((TFun)tFun).typeArgs.size()) {
					int cmp = 0;
					for (Exp param : e.es) {
						Type ts = Type.gen();
						eq = new Equations(ts, ((TFun)tFun).typeArgs.get(cmp));
						retour.add(eq);
						param.env = e.env;
						param.typeAttendu = ts;
						retour.addAll(param.accept(this));
					}
				} else {
					System.err.println("error "+((Var)e.e).id.id+" expected "+((TFun)tFun).typeArgs.size()+" arguments");
					System.exit(1);
				}
			} else {
				System.err.println("error "+((Var)e.e).id.id+" expected as function and found as "+ tFun.toString());
				System.exit(1);
			}
		} else {
			((TFun)tFun).typeRetour = e.typeAttendu;
			LinkedList<Type> tParams = new LinkedList<Type>();
			for (Exp param : e.es) {
				Type ts = Type.gen();
				param.env = e.env;
				param.typeAttendu = ts;
				retour.addAll(param.accept(this));
				tParams.add(ts);
			}
			((TFun)tFun).typeArgs = tParams;
			e.e.env = e.env;
			e.e.typeAttendu = tFun;
			retour.addAll(e.e.accept(this));
		}
		return retour;
	}

	@Override
	public LinkedList<Equations> visit(Tuple e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LinkedList<Equations> visit(LetTuple e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LinkedList<Equations> visit(Array e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LinkedList<Equations> visit(Get e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LinkedList<Equations> visit(Put e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LinkedList<Equations> visit(Unit unit) {
		Equations eq = new Equations(new TUnit(), unit.typeAttendu);
		LinkedList<Equations> retour = new LinkedList<Equations>();
		retour.add(eq);
		return retour;
	}

	public static LinkedList<Equations> resolution(LinkedList<Equations> lEq) {
		int assertNotInfiniteBoucle = 0;
		if (lEq == null || lEq.isEmpty()) {
			return lEq;
		}
		for (int i = 0; i < lEq.size(); i ++) {
			if (assertNotInfiniteBoucle <= lEq.size()*lEq.size()) {
				Equations eq = lEq.get(i);
				if (eq.t1 instanceof TVar) {
					if (eq.t2 instanceof TVar) {
						Equations toInsert = lEq.remove(i);
						lEq.addLast(toInsert);
					} else {
						// on parcourt la liste en remplacant var pas son type
						for (int j = 1; j< lEq.size(); j++) {
							Equations eq2 = lEq.get(j);
							if (eq2.t1.equals(eq.t1)) {
								eq2.t1 = eq.t2;
							} else if (eq2.t2.equals(eq.t1)) {
								eq2.t2 = eq.t2;
							}
						}
					}
				} else {
					if (eq.t2 instanceof TVar) {
						// on parcourt la liste en remplacant var pas son type
						for (int j = 1; j< lEq.size(); j++) {
							Equations eq2 = lEq.get(j);
							if (eq2.t1.equals(eq.t2)) {
								eq2.t1 = eq.t1;
							} else if (eq2.t2.equals(eq.t2)) {
								eq2.t2 = eq.t1;
							}
						}
					} else {
						if (!eq.t1.equalsType(eq.t2)) {
							System.err.println("type error : " + eq.t1.toString() + " != " + eq.t2.toString());
							System.exit(1);
						} 
					}
				}
				assertNotInfiniteBoucle ++;
			}

		}
		return lEq;
	}
}
