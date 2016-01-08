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
		// TODO Auto-generated method stub
		return e;
	}

	@Override
	public Exp visit(Bool e) {
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
		e.e = e.e.accept(this);
		return e;
	}

	@Override
	public Exp visit(Neg e) {
		// TODO Auto-generated method stub
		e.e = e.e.accept(this);
		return e;
	}

	@Override
	public Exp visit(Add e) {
		e.e1 = e.e1.accept(this);
        e.e2 = e.e2.accept(this);
		/*FrontEnd.nb_var++;
		Id id1=new Id("var"+FrontEnd.nb_var);
		FrontEnd.nb_var++;
		Id id2=new Id("var"+FrontEnd.nb_var);
		FrontEnd.nb_var++;
		Id id3=new Id("var"+FrontEnd.nb_var);

		Let exp=new Let(id1,null,e.e1,new Let(id2,null,e.e2,new Let(id3,null,new Add(new Var(id1),new Var(id2)), null)));
		return exp;*/
		
		return e;

	}

	@Override
	public Exp visit(Sub e) {	

		e.e1 = e.e1.accept(this);
        e.e2 = e.e2.accept(this);
        
		/*FrontEnd.nb_var++;
		Id id1=new Id("var"+FrontEnd.nb_var);
		FrontEnd.nb_var++;
		Id id2=new Id("var"+FrontEnd.nb_var);
		FrontEnd.nb_var++;
		Id id3=new Id("var"+FrontEnd.nb_var);

		Let exp=new Let(id1,null,e.e1,new Let(id2,null,e.e2,new Let(id3,null,new Sub(new Var(id1),new Var(id2)), null)));*/
		return e;
	}

	@Override
	public Exp visit(FNeg e) {
		// TODO Auto-generated method stub
		e.e = e.e.accept(this);
		return e;
	}

	@Override
	public Exp visit(FAdd e) {
		/*FrontEnd.nb_var++;
		Id id1=new Id("var"+FrontEnd.nb_var);
		FrontEnd.nb_var++;
		Id id2=new Id("var"+FrontEnd.nb_var);
		FrontEnd.nb_var++;
		Id id3=new Id("var"+FrontEnd.nb_var);

		Let exp=new Let(id1,null,e.e1,new Let(id2,null,e.e2,new Let(id3,null,new FAdd(new Var(id1),new Var(id2)), null)));*/

		e.e1 = e.e1.accept(this);
        e.e2 = e.e2.accept(this);
		return e;
	}

	@Override
	public Exp visit(FSub e) {
		// TODO Auto-generated method stub
		e.e1 = e.e1.accept(this);
        e.e2 = e.e2.accept(this);
		return e;
	}

	@Override
	public Exp visit(FMul e) {
		/*FrontEnd.nb_var++;
		Id id1=new Id("var"+FrontEnd.nb_var);
		FrontEnd.nb_var++;
		Id id2=new Id("var"+FrontEnd.nb_var);
		FrontEnd.nb_var++;
		Id id3=new Id("var"+FrontEnd.nb_var);

		Let exp=new Let(id1,null,e.e1,new Let(id2,null,e.e2,new Let(id3,null,new FMul(new Var(id1),new Var(id2)), null)));*/

		e.e1 = e.e1.accept(this);
        e.e2 = e.e2.accept(this);
		return e;
	}
	@Override
	public Exp visit(Mul e) {
		/*FrontEnd.nb_var++;
		Id id1=new Id("var"+FrontEnd.nb_var);
		FrontEnd.nb_var++;
		Id id2=new Id("var"+FrontEnd.nb_var);
		FrontEnd.nb_var++;
		Id id3=new Id("var"+FrontEnd.nb_var);

		Let exp=new Let(id1,null,e.e1,new Let(id2,null,e.e2,new Let(id3,null,new Mul(new Var(id1),new Var(id2)),null)));*/

		e.e1 = e.e1.accept(this);
        e.e2 = e.e2.accept(this);
		return e;
	}

	@Override
	public Exp visit(FDiv e) {
		/*FrontEnd.nb_var++;
		Id id1=new Id("var"+FrontEnd.nb_var);
		FrontEnd.nb_var++;
		Id id2=new Id("var"+FrontEnd.nb_var);
		FrontEnd.nb_var++;
		Id id3=new Id("var"+FrontEnd.nb_var);

		Let exp=new Let(id1,null,e.e1,new Let(id2,null,e.e2,new Let(id3,null,new FDiv(new Var(id1),new Var(id2)), null)));*/

		e.e1 = e.e1.accept(this);
        e.e2 = e.e2.accept(this);
		return e;
	}

	@Override
	public Exp visit(Eq e) {
		// TODO Auto-generated method stub
		e.e1 = e.e1.accept(this);
        e.e2 = e.e2.accept(this);
		return e;
	}

	@Override
	public Exp visit(LE e) {
		// TODO Auto-generated method stub
		e.e1 = e.e1.accept(this);
        e.e2 = e.e2.accept(this);
		return e;
	}

	@Override
	public Exp visit(If e) {
		// TODO Auto-generated method stub
		e.e1 = e.e1.accept(this);
		e.e2 = e.e2.accept(this);
        e.e3 = e.e3.accept(this);
		return e;
	}

	@Override
	public Exp visit(Let e) {

		/*e.e1.accept(new KNormalization());
		e.e2.accept(new KNormalization());
		// Vérifier si Add/sub/mul/Fmul/Fdiv
		if(e.e1 instanceof Add || e.e1 instanceof Sub || e.e1 instanceof Mul || e.e1 instanceof FAdd || e.e1 instanceof FDiv || e.e1 instanceof FMul){
			
		}*/
		e.e1 = e.e1.accept(this);
        e.e2 = e.e2.accept(this);
        
	      // on crée un nombre de variable = height + 2 
	      if (e.e1.isOpBin()) {
	    	  LinkedList<Id> listId = new LinkedList<Id>();
	    	  for (int i = 1; i<= 3; i++) {
	    		  listId.add(new Id(String.format("v%d", i)));
	    	  }
	    	  Exp VarOp = new Var(listId.getLast());
	    	  Exp Var1 = new Var(listId.getFirst());
	    	  Exp Var2 = new Var(listId.get(1));
	    	  OpBin newOp = ((OpBin)e.e1);
	    	  Exp Var1value = ((OpBin)e.e1).e1;
	    	  Exp Var2value = ((OpBin)e.e1).e2;
	    	  
	    	  newOp.e1 = Var1;
	    	  newOp.e2 = Var2;
	    	  Exp newExp = new Let(listId.getLast(), e.t, newOp, VarOp);
	    	  newExp = new Let(listId.get(1), e.t, Var2value, newExp);
	    	  newExp = new Let(listId.getFirst(), e.t, Var1value, newExp);
	    	  return newExp;
	      }
		return e;
	}

	@Override
	public Exp visit(Var e) {

		return e;
	}

	@Override
	public Exp visit(LetRec e) {
		// TODO Auto-generated method stub
		e.e = e.e.accept(this);
		return e;
	}

	@Override
	public Exp visit(App e) {
		// TODO Auto-generated method stub
		e.e = e.e.accept(this);
        for (Exp exp : e.es) {
            exp = exp.accept(this);
        }
		return e;
	}

	@Override
	public Exp visit(Tuple e) {
		// TODO Auto-generated method stub
		for (Exp exp : e.es) {
            exp = exp.accept(this);
        }
		return e;
	}

	@Override
	public Exp visit(LetTuple e) {
		// TODO Auto-generated method stub
		e.e1 = e.e1.accept(this);
        e.e2 = e.e2.accept(this);
		return e;
	}

	@Override
	public Exp visit(Array e) {
		// TODO Auto-generated method stub
		e.e1 = e.e1.accept(this);
        e.e2 = e.e2.accept(this);
		return e;
	}

	@Override
	public Exp visit(Get e) {
		// TODO Auto-generated method stub
		e.e1 = e.e1.accept(this);
        e.e2 = e.e2.accept(this);
		return e;
	}

	@Override
	public Exp visit(Put e) {
		// TODO Auto-generated method stub
		e.e1 = e.e1.accept(this);
        e.e2 = e.e2.accept(this);
		e.e3 = e.e3.accept(this);
		return e;
	}

}
