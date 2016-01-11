import java.util.Hashtable;


public class GenerationS implements ObjVisitor<String> {
	
	static Hashtable<String,String> variables = new Hashtable<String,String>();
	private int nbReg = 4;
	
	@Override
	public String visit(Bool e) {
		return null;
	}

	@Override
	public String visit(Int e) {
		return String.format("#%d",e.i);
	}

	@Override
	public String visit(Float e) {
		return null;
	}

	@Override
	public String visit(Not e) {
		e.e.accept(this);
		return null;
	}

	@Override
	public String visit(Neg e) {
		e.e.accept(this);
		return null;
	}

	@Override
	public String visit(Add e) {
		affectRegistre(e.e1.accept(this),nbReg);
		String registre1 = variables.get(e.e1.accept(this));
    	String retour = String.format("\tmov\t%s,%s\n", registre1,e.e1.accept(this));
    	nbReg++;
    	affectRegistre(e.e2.accept(this),nbReg);
		String registre2 = variables.get(e.e2.accept(this));
    	retour += String.format("\tmov\t%s,%s\n", registre2,e.e2.accept(this));
		return retour += String.format("\tadd\tr0,%s,%s\n",registre1,registre2);
	}

	@Override
	public String visit(Sub e) {
		return String.format("\tsub\tr0,%s,%s",e.e1.accept(this),e.e2.accept(this));
	}

	@Override
	public String visit(FNeg e) {
		e.e.accept(this);
		return null;
	}

	@Override
	public String visit(FAdd e) {
		e.e1.accept(this);
		e.e2.accept(this);
		return null;
	}

	@Override
	public String visit(FSub e) {
		e.e1.accept(this);
		e.e2.accept(this);
		return null;
	}

	@Override
	public String visit(FMul e) {
		e.e1.accept(this);
		e.e2.accept(this);
		return null;
	}

	@Override
	public String visit(Mul e) {
		return String.format("\tmul\tr0,%s,%s",e.e1.accept(this),e.e2.accept(this));
	}

	@Override
	public String visit(FDiv e) {
		e.e1.accept(this);
		e.e2.accept(this);
		return null;
	}

	@Override
	public String visit(Eq e) {
		e.e1.accept(this);
		e.e2.accept(this);
		return null;
	}

	@Override
	public String visit(LE e) {
		e.e1.accept(this);
		e.e2.accept(this);
		return null;
	}

	@Override
	public String visit(If e) {
		e.e1.accept(this);
		e.e2.accept(this);
		e.e3.accept(this);
		return null;
	}

	@Override
	public String visit(Let e) {
		affectRegistre(e.e2.accept(this),nbReg);
		String registre = variables.get(e.e2.accept(this));
		String retour = String.format("\tmov\t%s,%s\n", registre,e.e1.accept(this));
		retour += String.format("\tmov\tr0,%s\n", registre);
		//nbReg++;
		//System.out.println(retour);
		return retour;
	}

	@Override
	public String visit(Var e) {
		return e.id.id;
	}

	@Override
	public String visit(LetRec e) {
		return(this.visit(e));
	}

	@Override
	public String visit(App e) {
    	String retour="";
    	
    	for(Exp param : e.es){
    		if(param.accept(this)==null){
    			retour += String.format("\tbl\tmin_caml_%s\n",e.e.accept(this));

    		} else if(param.isInt()){
	    		retour+=String.format("\tmov\tr%d,%s\n", nbReg,param.accept(this));
	    		retour+=String.format("\tmov\tr0,r%d\n", nbReg);
	    		retour = String.format("%s\tbl\tmin_caml_%s\n",retour,e.e.accept(this));
	    		nbReg++;
    		}
    	}
		return retour;
		
	}

	@Override
	public String visit(Tuple e) {
		
		return null;
	}

	@Override
	public String visit(LetTuple e) {
		e.e1.accept(this);
		e.e2.accept(this);
		return null;
	}

	@Override
	public String visit(Array e) {
		e.e1.accept(this);
		e.e2.accept(this);
		return null;
	}

	@Override
	public String visit(Get e) {
		e.e1.accept(this);
		e.e2.accept(this);
		return null;
	}

	@Override
	public String visit(Put e) {
		e.e1.accept(this);
		e.e2.accept(this);
		e.e3.accept(this);
		return null;
	}

	@Override
	public String visit(Unit unit) {
		
		return null;
	}	
	
	private void affectRegistre(String Var, int nb){
		String registre = String.format("r%d", nb);
		this.variables.put(Var, registre);
	}
	
	
}
