import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class KNormalization implements ObjVisitor<Exp> {
	FrontEnd fe;
	Var affectation;
	public KNormalization(){

	}
	public KNormalization(Var v){
		this.affectation=v;

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

		return KNormOpBin(e, new TInt());

	}

	@Override
	public Exp visit(Sub e) {	

		e.e1 = e.e1.accept(this);
		e.e2 = e.e2.accept(this);

		return KNormOpBin(e, new TInt());
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
		return KNormOpBin(e, new TFloat());
	}

	@Override
	public Exp visit(FSub e) {
		
		e.e1 = e.e1.accept(this);
		e.e2 = e.e2.accept(this);
		return KNormOpBin(e, new TFloat());
	}

	@Override
	public Exp visit(FMul e) {

		e.e1 = e.e1.accept(this);
		e.e2 = e.e2.accept(this);
		return KNormOpBin(e, new TFloat());
	}
	@Override
	public Exp visit(Mul e) {

		e.e1 = e.e1.accept(this);
		e.e2 = e.e2.accept(this);
		return KNormOpBin(e, new TInt());
	}

	@Override
	public Exp visit(FDiv e) {

		e.e1 = e.e1.accept(this);
		e.e2 = e.e2.accept(this);
		return KNormOpBin(e, new TFloat());
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
		e.e2 = e.e2.accept(this);
		return KNormLet(e);
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
		e.e = e.e.accept(this);
		Exp newExp = e;
		List<Exp> newEs = new LinkedList<Exp>();
		Collections.reverse(e.es);
		for (Exp exp : e.es) {
			Exp argOpt = exp.accept(this);
			if (exp.isOpBin()) {
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
				} else if (argOpt instanceof OpBin){
					Id newId = Id.gen();
					Type t = new TInt();
					if (((OpBin)argOpt).e1 instanceof Float) {
						t = new TFloat();
					}
					Var newVar = new Var(newId);
					newExp = new Let(newId,t,argOpt,newExp);
					argOpt = newVar;
				}
			}
			newEs.add(argOpt);
		}
		e.es = newEs;
		Collections.reverse(e.es);
		return newExp;
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

	private boolean isNormalizable(OpBin e) {
		if (!e.e1.isVar() || !e.e2.isVar()) {
			return true;
		}
		return false;
	}

	private Exp KNormOpBin(OpBin e, Type type) {
		Exp retour = e;
		boolean letE1 = false;
		boolean letE2 = false;
		
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
			letE2 = true;
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
			letE1 = true;
		}
		if ( isNormalizable(e)) {
			OpBin newOp = (OpBin)(e.clone());
			int NbVarTemp = 3;
			if ( e.e1.isVar() || e.e2.isVar()) {
				NbVarTemp = 2;
			}

			newOp = (OpBin)e.clone();
			Exp Var1value = e.e1;
			Exp Var2value = e.e2;

			Exp Var1 = Var1value;
			Id Var1Id = new Id("default");
			Exp Var2 = Var2value;
			Id Var2Id = new Id("default");

			if (Var1.isVar()) {
				Var1Id = ((Var)Var1).id;
			}
			if (Var2.isVar()) {
				Var2Id = ((Var)Var2).id;
			}

			if (!Var1value.isVar()) {
				Var1Id = Id.gen();
				Var1 = new Var(Var1Id);
				if (!Var2value.isVar()) {
					Var2Id = Id.gen();
					Var2 = new Var(Var2Id);
				}
			} else {
				if (!Var2value.isVar()) {
					Var2Id = Id.gen();
					Var2 = new Var(Var2Id);
				}
			}
			Id VarOpId = Id.gen();
			Exp VarOp = new Var(VarOpId);

			newOp.e1 = Var1;
			newOp.e2 = Var2;

			Exp newExp = new Let(VarOpId, type, newOp, VarOp);
			if (NbVarTemp == 3) {
				newExp = new Let(Var2Id, type, Var2value, newExp);
				newExp = new Let(Var1Id, type, Var1value, newExp);
			} else {
				if (Var1value.isVar()) {
					newExp = new Let(Var2Id, type, Var2value, newExp);
				} else  {
					newExp = new Let(Var1Id, type, Var1value, newExp);
				}
			}
			Exp cour = retour;
			if (letE1 || letE2) {
				while (((Let)cour).e2 instanceof Let) {
					cour = ((Let)cour).e2;
				}
				((Let)cour).e2 = newExp;
			} else {
				return newExp;
			}
		}
		return retour;
	}

	private Exp KNormLet(Let e) {
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
	
}
