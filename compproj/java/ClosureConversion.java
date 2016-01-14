import java.util.LinkedList;
import java.util.List;


public class ClosureConversion implements ObjVisitor<Exp> {
	
	private class Closure {
		List<Exp> params;
		LetRec funcDApel;
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
		return e;
	}

	@Override
	public Exp visit(Neg e) {
		return e;
	}

	@Override
	public Exp visit(Add e) {
		return e;
	}

	@Override
	public Exp visit(Sub e) {
		return e;
	}

	@Override
	public Exp visit(FNeg e) {
		return e;
	}

	@Override
	public Exp visit(FAdd e) {
		return e;
	}

	@Override
	public Exp visit(FSub e) {
		return e;
	}

	@Override
	public Exp visit(FMul e) {
		return e;
	}
	
	@Override
	public Exp visit(Mul e) {
		return e;
	}

	@Override
	public Exp visit(FDiv e) {
		return e;
	}

	@Override
	public Exp visit(Eq e) {
		return e;
	}

	@Override
	public Exp visit(LE e) {
		return e;
	}

	@Override
	public Exp visit(If e) {
		e.e2 = e.e2.accept(this);
		e.e3 = e.e3.accept(this);
		return e;
	}

	@Override
	public Exp visit(Let e) {
		return e;
	}

	@Override
	public Exp visit(Var e) {
		return e;
	}

	@Override
	public Exp visit(LetRec e) {
		e.e = e.e.accept(this);
		e.fd.e = e.fd.e.accept(this);
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
			((LetRec)newLetRec.e).fd.e = new Tuple(newArgs);
			
			//création du LetTuple
			E2Prec = ((LetRec)newLetRec.e).e;
			List<Id> listId = new LinkedList<Id>();
			Id nId = Id.gen();
			nId.id = "_func_"+nId.id;
			listId.add(nId);
			//recherche du neouds d'appel de e
			// ****************************** ici
			Exp newAppel = e.e;
			//Id nId = Id.gen();
			nId.id = "_func_"+nId.id;
			listId.add(nId);
			//((LetRec)newLetRec.e).e = new LetTuple();
			return newLetRec;
		}
		return e;
	}

	@Override
	public Exp visit(App e) {
		return e;
	}

	@Override
	public Exp visit(Tuple e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Exp visit(LetTuple e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Exp visit(Array e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Exp visit(Get e) {
		return e;
	}

	@Override
	public Exp visit(Put e) {
		return e;
	}

}
