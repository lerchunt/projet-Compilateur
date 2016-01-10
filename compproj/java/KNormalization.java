import java.util.LinkedList;


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
		return e;
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
		for (Exp exp : e.es) {
			exp = exp.accept(this);
		}
		return e;
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
	public Exp visit(FunDef e) {
		e.e.accept(this);
		return e;
	}


	private boolean isNormalizable(OpBin e) {
		if (!e.e1.isVar() || !e.e2.isVar()) {
			return true;
		}
		return false;
	}

	private Exp KNormOpBin(OpBin e, Type type) {
		if ( isNormalizable(e)) {
			int nbVar = 1;
			OpBin newOp = (OpBin)(e.clone());
			while (e.isOpBin()) {
				Exp parent = e;
				int NbVarTemp = 3;
				if ( e.e1.isVar() || e.e2.isVar()) {
					NbVarTemp = 2;
				}

				OpBin cour = e;
				while (cour.e1.isOpBin()) {
					parent = cour;
					cour = (OpBin) cour.e1;
				}
				newOp = (OpBin)cour.clone();
				Exp Var1value = cour.e1;
				Exp Var2value = cour.e2;

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
					Var1Id = new Id(String.format("v%d", nbVar));
					Var1 = new Var(Var1Id);
					nbVar++;
					if (!Var2value.isVar()) {
						Var2Id = new Id(String.format("v%d", nbVar));
						Var2 = new Var(Var2Id);
						nbVar++;
					}
				} else {
					Var2Id = new Id(String.format("v%d", nbVar));
					Var2 = new Var(Var2Id);
					nbVar++;
				}
				Id VarOpId = new Id(String.format("v%d", nbVar));
				Exp VarOp = new Var(VarOpId);
				nbVar++;

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
				return newExp;
			}
		}
		return e;
	}

}
