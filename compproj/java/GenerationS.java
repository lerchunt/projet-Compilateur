import java.util.Hashtable;


public class GenerationS implements ObjVisitor<String> {
	public static String defFunc = "";
	public static String defVar = "";
	public int nbReg;

	@Override
	public String visit(Bool e) {
		if (e.b){
			return "#1";
		} else return "#0";
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
		return e.e.accept(this);
	}

	@Override
	public String visit(Neg e) {
		if(e.e instanceof Int){
			return String.format("#	-%d",((Int)(e.e)).i);
		} else {
			System.err.println("internal error -- GenerationS -- Not");
			System.exit(1);
			return null;
		}
	}

	@Override
	public String visit(Add e) {
		String r1 = "";
		String r2 = "";
		if(e.e1 instanceof Int){
			r1 = e.e1.accept(this);
		}
		else if (e.e1 instanceof Var) {
			r1 = e.e1.accept(this);
		} 
		else {
			System.err.println("internal error -- GenerationS -- add");
			System.exit(1);
			return null;
		}
		if(e.e2 instanceof Int){
			r2 = e.e2.accept(this);
		}
		else if (e.e2 instanceof Var) {
			r2 = e.e2.accept(this);
		} 
		else {
			System.err.println("internal error -- GenerationS -- add");
			System.exit(1);
			return null;
		}

		if(e.e1 instanceof Int)
			return String.format("\tadd\t%s,%s,%s\n",e.registreDeRetour, r2, r1);
		else
			return String.format("\tadd\t%s,%s,%s\n",e.registreDeRetour, r1, r2);

	}

	@Override
	public String visit(Sub e) {
		String r1 = "";
		String r2 = "";
		if(e.e1 instanceof Int){
			r1 = e.e1.accept(this);
		}
		else if (e.e1 instanceof Var) {
			r1 = e.e1.accept(this);
		} 
		else {
			System.err.println("internal error -- GenerationS -- add");
			System.exit(1);
			return null;
		}
		if(e.e2 instanceof Int){
			r2 = e.e2.accept(this);
		}
		else if (e.e2 instanceof Var) {
			r2 = e.e2.accept(this);
		} 
		else {
			System.err.println("internal error -- GenerationS -- add");
			System.exit(1);
			return null;
		}

		if(e.e1 instanceof Int)
			return String.format("\tsub\t%s,%s,%s\n",e.registreDeRetour, r2, r1);
		else
			return String.format("\tsub\t%s,%s,%s\n",e.registreDeRetour, r1, r2);
	}

	@Override
	public String visit(FNeg e) {
		if(e.e instanceof Int){
			return String.format("#	-%d",((Int)(e.e)).i);
		} else {
			System.err.println("internal error -- GenerationS -- Not");
			System.exit(1);
			return null;
		}
	}

	@Override
	public String visit(FAdd e) {
		String r1 = "";
		String r2 = "";
		if (e.e1 instanceof Var) {
			r1 = e.e1.accept(this);
		} else {
			System.err.println("internal error -- GenerationS -- add");
			System.exit(1);
			return null;
		}
		if (e.e2 instanceof Var) {
			r2 = e.e2.accept(this);
		} else {
			System.err.println("internal error -- GenerationS -- add");
			System.exit(1);
			return null;
		}
		return String.format("\tadd\t%s, %s, %s\n",e.registreDeRetour, r1, r2);
	}

	@Override
	public String visit(FSub e) {
		String r1 = "";
		String r2 = "";
		if (e.e1 instanceof Var) {
			r1 = e.e1.accept(this);
		} else {
			System.err.println("internal error -- GenerationS -- sub");
			System.exit(1);
			return null;
		}
		if (e.e2 instanceof Var) {
			r2 = e.e2.accept(this);
		} else {
			System.err.println("internal error -- GenerationS -- sub");
			System.exit(1);
			return null;
		}
		return String.format("\tsub\t%s, %s, %s\n",e.registreDeRetour, r1, r2);
	}

	@Override
	public String visit(FMul e) {
		String r1 = "";
		String r2 = "";
		if (e.e1 instanceof Var) {
			r1 = e.e1.accept(this);
		} else {
			System.err.println("internal error -- GenerationS -- mul");
			System.exit(1);
			return null;
		}
		if (e.e2 instanceof Var) {
			r2 = e.e2.accept(this);
		} else {
			System.err.println("internal error -- GenerationS -- mul");
			System.exit(1);
			return null;
		}
		return String.format("\tmul\t%s, %s, %s\n",e.registreDeRetour, r1, r2);
	}

	@Override
	public String visit(Mul e) {
		String r1 = "";
		String r2 = "";
		if(e.e1 instanceof Int){
			r1 = e.e1.accept(this);
		}
		else if (e.e1 instanceof Var) {
			r1 = e.e1.accept(this);
		} 
		else {
			System.err.println("internal error -- GenerationS -- add");
			System.exit(1);
			return null;
		}
		if(e.e2 instanceof Int){
			r2 = e.e2.accept(this);
		}
		else if (e.e2 instanceof Var) {
			r2 = e.e2.accept(this);
		} 
		else {
			System.err.println("internal error -- GenerationS -- add");
			System.exit(1);
			return null;
		}

		if(e.e1 instanceof Int)
			return String.format("\tmul\t%s,%s,%s\n",e.registreDeRetour, r2, r1);
		else
			return String.format("\tmul\t%s,%s,%s\n",e.registreDeRetour, r1, r2);

	}

	@Override
	public String visit(FDiv e) {
		e.e1.accept(this);
		e.e2.accept(this);
		return null;
	}

	@Override
	public String visit(Eq e) {
		String retour=String.format("\tcmp\t%s,%s\n", e.e1.accept(this),e.e2.accept(this));
		retour += "\tbeq\tifTrue\n";
		return retour;
	}

	@Override
	public String visit(LE e) {
		String retour=String.format("\tcmp\t%s,%s\n", e.e1.accept(this),e.e2.accept(this));
		
		e.e1.accept(this); 
		e.e2.accept(this);
		return retour;
	}

	@Override
	public String visit(If e) {
		String retour ="";
		String regE1 = "";
		String regE2="";
		String regE3="";
		String ifTrue="ifTrue:\n";
		String ifFalse="ifFalse:\n";
		
		if(e.e1 instanceof OpBin || e.e1 instanceof OpUn){
			Id idretour = Id.gen();
			regE1 = RegistreAllocation.getRegistre(idretour);
			((OpBin)e.e1).registreDeRetour = regE1;
			retour += e.e1.accept(this);
			
		} else {
			System.err.println("internal error - If e1 (GenerationS)");
			System.exit(1);
		}
		
		
		if (e.e2 instanceof Var){
			
		} else if (e.e2 instanceof App){
			
		} else if (e.e2 instanceof Let){
			
		} else if (e.e2 instanceof LetRec){
			
		} else if (e.e2 instanceof OpBin){ 
						
		} else if (e.e2 instanceof OpUn){ //neg et not
			
		} else { //entier +float +bool
			ifTrue+=String.format("\tmov\t%s,%s\n",e.registreDeRetour,e.e2.accept(this));
		}
		
		
		if (e.e3 instanceof Var){
			
		} else if (e.e3 instanceof App){
			
		} else if (e.e3 instanceof Let){
			
		} else if (e.e3 instanceof LetRec){
		
		} else if (e.e3 instanceof OpBin){ 
						
		} else if (e.e3 instanceof OpUn){ //neg et not
			
		} else { //entier +float +bool
			ifFalse+=String.format("\tmov\t%s,%s\n",e.registreDeRetour,e.e3.accept(this));
		}
		
		ifFalse+="\tb\tendIf\n";
		retour+= ifFalse;
		
		retour+=ifTrue;
		retour+="endIf:\n";
		
		return retour;
	}

	@Override
	public String visit(Let e) {
		String retour ="";
		String registre = RegistreAllocation.getRegistre(e.id);
		if (e.e1 instanceof OpBin){
			((OpBin)e.e1).registreDeRetour = registre;
			retour += e.e1.accept(this);
		} else if (e.e1 instanceof Var) {
			String regE1 = e.e1.accept(this);
			retour += String.format("\tmov\t%s,%s\n",registre,regE1);
		} else if (e.e1 instanceof If){
			((If)e.e1).registreDeRetour = registre;
			retour+=e.e1.accept(this);
		} else {
			retour += String.format("\tmov\t%s,%s\n", registre,e.e1.accept(this));
		}
		
		if (e.e2 instanceof OpBin){
			Id idretour = Id.gen();
			String regRetour = RegistreAllocation.getRegistre(idretour);
			((OpBin)e.e2).registreDeRetour = regRetour;
			retour += e.e2.accept(this);
			retour += String.format("\tmov\tr0%s\n",regRetour);
		} else if (e.e2 instanceof Var) {
			String regE1 = e.e2.accept(this);
			retour += String.format("\tmov\tr0,%s\n",regE1);
		} else {
			retour += e.e2.accept(this);
		}
		
		return retour;
	}

	@Override
	public String visit(Var e) {
		return RegistreAllocation.getRegistre(e.id);
	}

	@Override
	public String visit(LetRec e) {
		String reg="";
		String retour = "";
		int nbreg = 0;
		defFunc +=String.format("\nmin_caml_%s:\n",e.fd.id);

		for (Id id : e.fd.args){
			if (nbreg <4){
				reg = RegistreAllocation.getRegistre(id);
				defFunc += String.format("\tmov\t%s,r%d\n",reg,nbreg);
				nbreg++;
			}
			else{
				System.err.println(e.fd.id + " : invalid argument number (>3)");
				System.exit(1);
			}
		}
		if (e.fd.e instanceof OpBin){
			Id idretour = Id.gen();
			String regRetour = RegistreAllocation.getRegistre(idretour);
			((OpBin)e.fd.e).registreDeRetour = regRetour;
			retour += e.fd.e.accept(this);
			retour += String.format("\tmov\tr0,%s",regRetour);
			for (Id id : e.fd.args){
				RegistreAllocation.sup(id);
			}
		} else {
			retour += e.fd.e.accept(this);
		}
		defFunc += retour;
		defFunc += "\tbx\tlr\n";

		return e.e.accept(this);	
	}

	@Override
	public String visit(App e) {
		String retour="";
		String registre="";
		int nbParam = 0;
		for(Exp param : e.es){
			if (nbParam >3) {
				System.err.println("invalid argument number (>3) in function call");
				System.exit(1);
			}
			String strP = param.accept(this);
			if(strP!=null){
				if (param instanceof Var){
					nbParam ++;
					registre = strP;
					retour +=String.format("\tmov\tr%d,%s\n",nbParam-1,  registre);
				} else if (param instanceof App) {
					nbParam ++;
					retour += strP;
				} else {
					nbParam++;
					retour +=String.format("\tmov\tr%d,%s\n", nbParam-1, strP);
				}
			}
		}
		if (e.e instanceof Var) {
			retour = String.format("%s\tbl\tmin_caml_%s\n",retour,((Var)e.e).id.id);
		} else {
			System.err.println("internal error - definition function (GenerationS)");
			System.exit(1);
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

	private String epilogue() {
		return "\tadd\tr13,r11,#0\n\tldr\tr11,[r13]\n\tadd\tr13,r13,#4\n\tbx\n\tlr";
	}

}
