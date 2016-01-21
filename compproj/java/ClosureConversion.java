import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;


public class ClosureConversion implements ObjVisitor<Exp> {

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
		e.e.env = e.env;
		e.e = e.e.accept(this);
		return e;
	}

	@Override
	public Exp visit(Neg e) {
		e.e.env = e.env;
		e.e = e.e.accept(this);
		return e;
	}

	@Override
	public Exp visit(Add e) {

		e.e1.env = e.env;
		e.e2.env = e.env;
		e.e1 = e.e1.accept(this);
		e.e2 = e.e2.accept(this);

		return e;
	}

	@Override
	public Exp visit(Sub e) {

		e.e1.env = e.env;
		e.e2.env = e.env;
		e.e1 = e.e1.accept(this);
		e.e2 = e.e2.accept(this);

		return e;
	}

	@Override
	public Exp visit(FNeg e) {

		e.e.env = e.env;
		e.e = e.e.accept(this);

		return e;
	}

	@Override
	public Exp visit(FAdd e) {

		e.e1.env = e.env;
		e.e2.env = e.env;
		e.e1 = e.e1.accept(this);
		e.e2 = e.e2.accept(this);

		return e;
	}

	@Override
	public Exp visit(FSub e) {

		e.e1.env = e.env;
		e.e2.env = e.env;
		e.e1 = e.e1.accept(this);
		e.e2 = e.e2.accept(this);

		return e;
	}

	@Override
	public Exp visit(FMul e) {

		e.e1.env = e.env;
		e.e2.env = e.env;
		e.e1 = e.e1.accept(this);
		e.e2 = e.e2.accept(this);

		return e;
	}
	
	@Override
	public Exp visit(Mul e) {

		e.e1.env = e.env;
		e.e2.env = e.env;
		e.e1 = e.e1.accept(this);
		e.e2 = e.e2.accept(this);

		return e;
	}

	@Override
	public Exp visit(FDiv e) {

		e.e1.env = e.env;
		e.e2.env = e.env;
		e.e1 = e.e1.accept(this);
		e.e2 = e.e2.accept(this);

		return e;
	}

	@Override
	public Exp visit(Eq e) {

		e.e1.env = e.env;
		e.e2.env = e.env;
		e.e1 = e.e1.accept(this);
		e.e2 = e.e2.accept(this);

		return e;
	}

	@Override
	public Exp visit(LE e) {

		e.e1.env = e.env;
		e.e2.env = e.env;
		e.e1 = e.e1.accept(this);
		e.e2 = e.e2.accept(this);

		return e;
	}

	@Override
	public Exp visit(If e) {

		e.e1.env = e.env;
		e.e2.env = e.env;
		e.e3.env = e.env;
		e.e1 = e.e1.accept(this);
		e.e2 = e.e2.accept(this);
		e.e3 = e.e3.accept(this);

		return e;
	}

	@Override
	public Exp visit(Let e) {

		e.e1.env = e.env;
		e.e1 = e.e1.accept(this);
		e.e2.env = e.env;
		e.e2.addEnv(e.id, new TVar(e.id.id));
		e.e2.accept(this);
		e.e2.env.removeFirst();

		return e;
	}

	@Override
	public Exp visit(Var e) {
		return e;
	}

	@Override
	public Exp visit(LetRec e) {

		if (e.fd.e instanceof LetRec ) {
			LetRec newLetRec = (LetRec)e.fd.e.clone();
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
				} 
			}
			// liaison avec le nouveau fils
			Exp E2Prec = (Exp)newLetRec.e.clone();
			newLetRec.e = (LetRec)e.clone();
			((LetRec)newLetRec.e).fd.e = e.fd.e;
			e.fd.e = E2Prec;
			newLetRec.env = e.env;
			newLetRec.fd.e.env = e.env;
			newLetRec.fd.e = newLetRec.fd.e.accept(this);
			newLetRec.e = newLetRec.e.accept(this);
	
			return newLetRec;
		}
		e.addEnv(e.fd.id, new TVar(e.fd.id.id));
		e.fd.e.env = e.env;
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

		return e;
	}

	@Override
	public Exp visit(App e) {
		return e;
	}

	@Override
	public Exp visit(Tuple e) {

		// TODO Auto-generated method stub

		return e;
	}

	@Override
	public Exp visit(LetTuple e) {

		// TODO Auto-generated method stub

		return e;
	}

	@Override
	public Exp visit(Array e) {

		// TODO Auto-generated method stub

		return e;
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
