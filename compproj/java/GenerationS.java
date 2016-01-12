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
		String retour = "";
		int bool = 0; //False
		if (e.e instanceof Bool){
			if(!((Bool)(e.e)).b){
				bool = 1;				
			}
			retour += (String.format("#%d",bool));
		} else if (e.e instanceof Var){
			String r = RegistreAllocation.getRegistre(((Var)(e.e)).id);
			
			
		} else {
			System.err.println("internal error -- GenerationS -- Not");
			System.exit(1);
			return null;
		}
		return retour;
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
		if (e.e1 instanceof Var) {
			r1 = RegistreAllocation.getRegistre(((Var)e.e1).id);
		} else {
			System.err.println("internal error -- GenerationS -- add");
			System.exit(1);
			return null;
		}
		if (e.e2 instanceof Var) {
			r2 = RegistreAllocation.getRegistre(((Var)e.e2).id);
		} else {
			System.err.println("internal error -- GenerationS -- add");
			System.exit(1);
			return null;
		}
		return String.format("\tadd\t%s,%s,%s\n",e.registreDeRetour, r1, r2);
	}

	@Override
	public String visit(Sub e) {
		String r1 = "";
		String r2 = "";
		if (e.e1 instanceof Var) {
			r1 = RegistreAllocation.getRegistre(((Var)e.e1).id);
		} else {
			System.err.println("internal error -- GenerationS -- sub");
			System.exit(1);
			return null;
		}
		if (e.e2 instanceof Var) {
			r2 = RegistreAllocation.getRegistre(((Var)e.e2).id);
		} else {
			System.err.println("internal error -- GenerationS -- sub");
			System.exit(1);
			return null;
		}
		return String.format("\tsub\t%s, %s, %s\n",e.registreDeRetour, r1, r2);
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
			r1 = RegistreAllocation.getRegistre(((Var)e.e1).id);
		} else {
			System.err.println("internal error -- GenerationS -- add");
			System.exit(1);
			return null;
		}
		if (e.e2 instanceof Var) {
			r2 = RegistreAllocation.getRegistre(((Var)e.e2).id);
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
			r1 = RegistreAllocation.getRegistre(((Var)e.e1).id);
		} else {
			System.err.println("internal error -- GenerationS -- sub");
			System.exit(1);
			return null;
		}
		if (e.e2 instanceof Var) {
			r2 = RegistreAllocation.getRegistre(((Var)e.e2).id);
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
			r1 = RegistreAllocation.getRegistre(((Var)e.e1).id);
		} else {
			System.err.println("internal error -- GenerationS -- mul");
			System.exit(1);
			return null;
		}
		if (e.e2 instanceof Var) {
			r2 = RegistreAllocation.getRegistre(((Var)e.e2).id);
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
		if (e.e1 instanceof Var) {
			r1 = RegistreAllocation.getRegistre(((Var)e.e1).id);
		} else {
			System.err.println("internal error -- GenerationS -- mul");
			System.exit(1);
			return null;
		}
		if (e.e2 instanceof Var) {
			r2 = RegistreAllocation.getRegistre(((Var)e.e2).id);
		} else {
			System.err.println("internal error -- GenerationS -- mul");
			System.exit(1);
			return null;
		}
		return String.format("\tmul\t%s, %s, %s\n",e.registreDeRetour, r1, r2);
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
		String retour ="";
		if (e.e1 instanceof Not){
			String registre = RegistreAllocation.getRegistre(e.id);
			retour = String.format("\tmov\t%s,%s\n", registre,e.e1.accept(this));
			
			retour += e.e2.accept(this);
			return retour;
		}else{			
			String registre = RegistreAllocation.getRegistre(e.id);
			retour = String.format("\tmov\t%s,%s\n", registre,e.e1.accept(this));
			retour += e.e2.accept(this);
			return retour;
		}
	}

	@Override
	public String visit(Var e) {
		return e.id.id;
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
			defFunc += retour;
		}

		//e.fd.e.accept(this);

		return e.e.accept(this);	
	}

	@Override
	public String visit(App e) {
		String retour="";
		String registre="";
		for(Exp param : e.es){
			if(param.accept(this)==null){
				retour += String.format("\tbl\tmin_caml_%s\n",e.e.accept(this));
			}else if (param instanceof Var){
				registre = RegistreAllocation.getRegistre(((Var)param).id);
				retour +=String.format("\tmov\tr0,%s\n", registre);
				retour = String.format("%s\tbl\tmin_caml_%s\n",retour,e.e.accept(this));
			}else {
				retour +=String.format("\tmov\tr0,%s\n",param.accept(this));
				retour = String.format("%s\tbl\tmin_caml_%s\n",retour,e.e.accept(this));
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
