
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Exp visit(Bool e) {
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Exp visit(Neg e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Exp visit(Add e) {
		FrontEnd.nb_var++;
		Id id1=new Id("var"+FrontEnd.nb_var);
		FrontEnd.nb_var++;
		Id id2=new Id("var"+FrontEnd.nb_var);
		FrontEnd.nb_var++;
		Id id3=new Id("var"+FrontEnd.nb_var);

		Let exp=new Let(id1,null,e.e1,new Let(id2,null,e.e2,new Let(id3,null,new Add(new Var(id1),new Var(id2)), null)));
		return exp;

	}

	@Override
	public Exp visit(Sub e) {		
		FrontEnd.nb_var++;
		Id id1=new Id("var"+FrontEnd.nb_var);
		FrontEnd.nb_var++;
		Id id2=new Id("var"+FrontEnd.nb_var);
		FrontEnd.nb_var++;
		Id id3=new Id("var"+FrontEnd.nb_var);

		Let exp=new Let(id1,null,e.e1,new Let(id2,null,e.e2,new Let(id3,null,new Sub(new Var(id1),new Var(id2)), null)));
		return exp;
	}

	@Override
	public Exp visit(FNeg e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Exp visit(FAdd e) {
		FrontEnd.nb_var++;
		Id id1=new Id("var"+FrontEnd.nb_var);
		FrontEnd.nb_var++;
		Id id2=new Id("var"+FrontEnd.nb_var);
		FrontEnd.nb_var++;
		Id id3=new Id("var"+FrontEnd.nb_var);

		Let exp=new Let(id1,null,e.e1,new Let(id2,null,e.e2,new Let(id3,null,new FAdd(new Var(id1),new Var(id2)), null)));
		return exp;
	}

	@Override
	public Exp visit(FSub e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Exp visit(FMul e) {
		FrontEnd.nb_var++;
		Id id1=new Id("var"+FrontEnd.nb_var);
		FrontEnd.nb_var++;
		Id id2=new Id("var"+FrontEnd.nb_var);
		FrontEnd.nb_var++;
		Id id3=new Id("var"+FrontEnd.nb_var);

		Let exp=new Let(id1,null,e.e1,new Let(id2,null,e.e2,new Let(id3,null,new FMul(new Var(id1),new Var(id2)), null)));
		return exp;
	}
	@Override
	public Exp visit(Mul e) {
		FrontEnd.nb_var++;
		Id id1=new Id("var"+FrontEnd.nb_var);
		FrontEnd.nb_var++;
		Id id2=new Id("var"+FrontEnd.nb_var);
		FrontEnd.nb_var++;
		Id id3=new Id("var"+FrontEnd.nb_var);

		Let exp=new Let(id1,null,e.e1,new Let(id2,null,e.e2,new Let(id3,null,new Mul(new Var(id1),new Var(id2)),null)));
		return exp;
	}

	@Override
	public Exp visit(FDiv e) {
		FrontEnd.nb_var++;
		Id id1=new Id("var"+FrontEnd.nb_var);
		FrontEnd.nb_var++;
		Id id2=new Id("var"+FrontEnd.nb_var);
		FrontEnd.nb_var++;
		Id id3=new Id("var"+FrontEnd.nb_var);

		Let exp=new Let(id1,null,e.e1,new Let(id2,null,e.e2,new Let(id3,null,new FDiv(new Var(id1),new Var(id2)), null)));
		return exp;
	}

	@Override
	public Exp visit(Eq e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Exp visit(LE e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Exp visit(If e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Exp visit(Let e) {

		e.e1.accept(new KNormalization());
		e.e2.accept(new KNormalization());
		/*// Vérifier si Add/sub/mul/Fmul/Fdiv
		if(e.e1 instanceof Add || e.e1 instanceof Sub || e.e1 instanceof Mul || e.e1 instanceof FAdd || e.e1 instanceof FDiv || e.e1 instanceof FMul){
			
		}*/
		return null;
	}

	@Override
	public Exp visit(Var e) {

		return null;
	}

	@Override
	public Exp visit(LetRec e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Exp visit(App e) {
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Exp visit(Put e) {
		// TODO Auto-generated method stub
		return null;
	}

}
