import java.util.Hashtable;


public class GenerationS implements ObjVisitor<String> {
	public static String defFunc = "";
	public static String defVar = "";
	public int nbReg;
	
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

		/*affectRegistre(e.e1.accept(this),nbReg);
		String registre1 = variables.get(e.e1.accept(this));
    	String retour = String.format("\tmov\t%s,%s\n", registre1,e.e1.accept(this));
    	nbReg++;
    	affectRegistre(e.e2.accept(this),nbReg);
		String registre2 = variables.get(e.e2.accept(this));
    	retour += String.format("\tmov\t%s,%s\n", registre2,e.e2.accept(this));
		return retour += String.format("\tadd\tr0,%s,%s\n",registre1,registre2);*/
		return "";
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

		String registre = RegistreAllocation.getRegistre(e.id);
		String retour = String.format("\tmov\t%s,%s\n", registre,e.e1.accept(this));
		retour += e.e2.accept(this);
		return retour;
	}

	@Override
	public String visit(Var e) {
		return e.id.id;
	}

	@Override
	public String visit(LetRec e) {
		String registre ="r";
		String reg="";
		String retour = "";
		retour += e.e.accept(this);
		defFunc +=String.format("\nmin_caml_%s:\n",e.fd.id);
		int nbreg = 0;
		
		for (int i=0;i<e.fd.args.size();i++){
			if (nbreg <4){
				RegistreAllocation.add(e.fd.args.get(i),String.format("%s%d", registre,nbreg));
				reg = RegistreAllocation.getRegistre(e.fd.args.get(i));
				defFunc += String.format("\tmov\t%s,r%d\n",reg,nbreg);
				nbreg++;
			}
			else{
				System.err.println(e.fd.id + " : invalid argument number (>3)");
				System.exit(1);
			}
		}
		
		e.fd.e.accept(this);
		for (Id id : e.fd.args){
			RegistreAllocation.sup(id);
		}
				
		return retour;
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
	
	
	
}
