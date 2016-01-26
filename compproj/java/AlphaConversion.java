import java.util.LinkedList;
import java.util.List;

public class AlphaConversion implements ObjVisitor<Exp> {
	static LinkedList<Id> varSeen = new LinkedList<Id>();

	static LinkedList <BindVar> envVariables = new LinkedList<BindVar>();
	static LinkedList <BindVar> envProcedures = new LinkedList<BindVar>();


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
		e.e = e.e.accept(this);
		return e;
	}

	@Override
	public Exp visit(Neg e) {
		e.e = e.e.accept(this);
		return e;
	}

	@Override
	public Exp visit(Add e) {
		e.e1 = e.e1.accept(this);		
		e.e2 = e.e2.accept(this);
		return e;
	}

	@Override
	public Exp visit(Sub e) {
		e.e1 = e.e1.accept(this);		
		e.e2 = e.e2.accept(this);
		return e;
	}

	@Override
	public Exp visit(FNeg e) {
		e.e = e.e.accept(this);
		return e;
	}

	@Override
	public Exp visit(FAdd e) {
		e.e1 = e.e1.accept(this);		
		e.e2 = e.e2.accept(this);
		return e;
	}

	@Override
	public Exp visit(FSub e) {
		e.e1 = e.e1.accept(this);		
		e.e2 = e.e2.accept(this);
		return e;
	}

	@Override
	public Exp visit(FMul e) {
		Exp newExp = e;
		e.e1 = e.e1.accept(this);
		e.e2 = e.e2.accept(this);
		if (e.e1 instanceof Float) {
			Id newId = Id.gen();
			Let newLet = new Let(newId, new TFloat(), (Exp)e.e1.clone(), e);
			e.e1 = new Var(newId);
			newExp = newLet;
		}
		if (e.e2 instanceof Float) {
			Id newId = Id.gen();
			Let newLet = new Let(newId, new TFloat(), (Exp)e.e2.clone(), newExp);
			e.e2 = new Var(newId);
			newExp = newLet;
		}

		return newExp;
	}

	@Override
	public Exp visit(Mul e) {
		Exp newExp = e;
		e.e1 = e.e1.accept(this);
		e.e2 = e.e2.accept(this);
		if (e.e1 instanceof Int) {
			Id newId = Id.gen();
			Let newLet = new Let(newId, new TInt(), (Exp)e.e1.clone(), e);
			e.e1 = new Var(newId);
			newExp = newLet;
		}
		if (e.e2 instanceof Int) {
			Id newId = Id.gen();
			Let newLet = new Let(newId, new TInt(), (Exp)e.e2.clone(), newExp);
			e.e2 = new Var(newId);
			newExp = newLet;
		}

		return newExp;
	}

	@Override
	public Exp visit(FDiv e) {
		e.e1 = e.e1.accept(this);		
		e.e2 = e.e2.accept(this);
		return e;
	}

	@Override
	public Exp visit(Eq e) {
		e.e1 = e.e1.accept(this);		
		e.e2 = e.e2.accept(this);
		return e;
	}

	@Override
	public Exp visit(LE e) {
		e.e1 = e.e1.accept(this);		
		e.e2 = e.e2.accept(this);
		return e;
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
		Id newVar = e.id;

		if (isInVar(e.id)) {
			newVar = Id.gen();
			while (/*isInProc(newVar) ||*/ isInVar(newVar)) {
				newVar = Id.gen();
			}
		}
		envVariables.addLast(new BindVar(e.id, newVar));
		varSeen.add(newVar);
		e.id = newVar;
		e.e2 = e.e2.accept(this);
		envVariables.removeLast();
		return e;
	}

	@Override
	public Exp visit(Var e) {
		e.id=getNewId(e.id);
		return e;
	}

	@Override
	public Exp visit(LetRec e) {
		// renommage des noms de proc
		Id newProc = e.fd.id;
		if (isInVar(e.fd.id)) {
			newProc = Id.gen();
			while (/*isInProc(newProc) ||*/ isInVar(newProc)) {
				newProc = Id.gen();
			}
			newProc.id = "_proc" + newProc;
		}
		varSeen.add(newProc);
		envProcedures.addLast(new BindVar(e.fd.id, newProc));
		e.fd.id = newProc;

		// renommage des params
		List<Id> newArgs = new LinkedList<Id>() ;

		for(Id id : e.fd.args){
			Id newVar = id;
			if (isInVar(id)) {
				newVar = Id.gen();
				while (/*isInProc(newVar) ||*/ isInVar(newVar)) {
					newVar = Id.gen();
				}
			}
			varSeen.add(newVar);
			envVariables.addLast(new BindVar(id, newVar));
			newArgs.add(newVar);
		}
		e.fd.args = newArgs;


		e.fd.e = e.fd.e.accept(this);
		for(Id id : e.fd.args){
			envVariables.removeLast();
		}
		e.e = e.e.accept(this);
		envProcedures.removeLast();
		
		return e;

	}

	@Override
	public Exp visit(App e) {
		e.e = e.e.accept(this);
		List<Exp> newParams = new LinkedList<Exp>();
		for (Exp es : e.es) {
			newParams.add(es.accept(this));
		}
		e.es = newParams;
		return e;
	}

	@Override
	public Exp visit(Tuple e) {
		List<Exp> newExp = new LinkedList<Exp>();
		for (Exp exp : e.es) {
			newExp.add(exp.accept(this));
			
		}
		e.es=newExp;
		return e;
	}

	@Override
	public Exp visit(LetTuple e) {
		e.e1 = e.e1.accept(this);
		List<Id> newIds= new LinkedList<Id>();
		for (Id id : e.ids) {
			Id newVar = id;

			if (isInVar(id)) { 
				newVar = Id.gen();
				while (/*isInProc(newVar) ||*/ isInVar(newVar)) {
					newVar = Id.gen();
				}
			}
			envVariables.addLast(new BindVar(id, newVar));
			varSeen.add(newVar);
			newIds.add(newVar);
		}
		e.ids = newIds;
		e.e2 = e.e2.accept(this);
		for (Id id : e.ids) {
			envVariables.removeLast();
		}
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

	private class BindVar{
		Id IdPrec;
		Id IdNew;

		private BindVar(Id idold,Id idnew){
			this.IdNew=idnew;
			this.IdPrec=idold;
		}
	}


	private boolean isInVar(Id id) {
		for (Id i : varSeen) {
			if (i.equals(id)) {
				return true;
			}
		}
		return false;
	}
	
	/*private boolean isInProc(Id id) {
		for (BindVar bv : procedures) {
			if (bv.IdPrec.equals(id) || bv.IdNew.equals(id)) {
				return true;
			}
		}
		return false;
	}*/


	private Id getNewId(Id id) {
		Id newId = id;
		for (BindVar bv : envVariables) {
			if (bv.IdPrec.equals(id)) {
				newId =  bv.IdNew;
			}
		}
		for (BindVar bv : envProcedures) {
			if (bv.IdPrec.equals(id)) {
				newId =  bv.IdNew;
			}
		}
		return newId;
	}
}
