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
        
	      // on crée un nombre de variable = nb de valeurs hard codées dans l'op +1
	      if (e.e1.isOpBin() && isNormalizable((OpBin) e.e1)) {

	    	  int nbVar = 1;
	    	  OpBin newOp = (OpBin)((OpBin)e.e1).clone();
	    	  while (e.e1.isOpBin()) {
	    		  int NbVarTemp = 3;
		    	  if (((OpBin) e.e1).e1.isVar() || ((OpBin) e.e1).e2.isVar()) {
		    		  NbVarTemp = 2;
		    	  }
		    	  
		    	  Exp parent = e;
		    	  OpBin cour = (OpBin) e.e1;
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
    	    	  
    	    	  Exp newExp = new Let(VarOpId, e.t, newOp, VarOp);
    	    	  if (NbVarTemp == 3) {
    		    	  newExp = new Let(Var2Id, e.t, Var2value, newExp);
    		    	  newExp = new Let(Var1Id, e.t, Var1value, newExp);
    	    	  } else {
    	    		  if (Var1value.isVar()) {
    			    	  newExp = new Let(Var2Id, e.t, Var2value, newExp);
    	    		  } else  {
    	    			  newExp = new Let(Var1Id, e.t, Var1value, newExp);
    	    		  }
    	    	  }
    	    	  if (parent.isOpBin()) {
    	    		  ((OpBin)parent).e1 = newExp;
    	    	  } else {
    	    		  ((Let)parent).e1 = newExp;
    	    	  }
	    	  }
	      }
			e.e1 = e.e1.accept(this);
	        e.e2 = e.e2.accept(this);
		return e;
	}

	private boolean isNormalizable(OpBin e) {
		if (!e.e1.isVar() || !e.e1.isVar()) {
			return true;
		}
		return false;
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
