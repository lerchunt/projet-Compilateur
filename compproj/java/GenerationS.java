import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;


public class GenerationS implements ObjVisitor<String> {

	public static String defFunc = "";
	public static String defVar = "";
	private boolean data = false; 
	private int nbFloat=0;

	private static int cmpIf = 0;
	private static int cmpTab =0;

	private static int cptf=0;

	@Override
	public String visit(Bool e) {
		if (e.b){
			return "#1";
		} else return "#0";
	}

	@Override
	public String visit(Int e) {
		if (e.i <= 121){
			return String.format("#%d",e.i);
		}
		return String.format("\tldr\t %s,=%d\n",e.registreDeRetour,e.i);
	}

	@Override
	public String visit(Float e) {
		if(!data){
			defVar += ".data\n";
			data = true;
		}
		nbFloat++;
		String strFloat="fl"+nbFloat;
		String registre = e.registreDeRetour;
		String retour = String.format("\tldr\t%s,addr_Float%d\n", registre,nbFloat);
		retour += String.format("\tldr\t%s,[%s]\n", registre,registre);		
		defFunc += String.format("addr_Float%d:\t.word %s\n", nbFloat,strFloat);
		defVar += strFloat+":\t.float "+e.f+"\n";
		return retour;
	}

	@Override
	public String visit(Not e) {
		if (e.e instanceof Not){
			return ((Not)e.e).e.accept(this);
		} else if (e.e instanceof Eq){
			return e.e.accept(this).replaceAll("bne", "beq");
		} else if (e.e instanceof LE){
			return e.e.accept(this).replaceAll("bgt", "ble");
		} else if (e.e instanceof Bool){
			if (!((Bool)e.e).b) {
				return String.format("\tb\tifFalse%d\n", cmpIf);
			} 
		}else if (e.e instanceof Var){
			String reg = e.registreDeRetour;
			String retour = "";
			if (reg.contains("[sp,")) {
				retour += String.format("\tldr\tr10,%s\n",reg);
				reg = "r10";
			} 
			return String.format("%s\trsc\t%s,%s\n\tmul\t%s,%s\n",retour,reg,e.e.accept(this),reg,reg);

		} else {
			System.err.println("internal error -- GenerationS not");
			System.exit(1);
		}
		return null;
	}

	@Override
	public String visit(Neg e) {
		if(e.e instanceof Int){
			if(((Int)(e.e)).i < 121){
				return String.format("\tmov\t%s,#-%d\n",e.registreDeRetour,((Int)(e.e)).i);
			} 
			e.e.registreDeRetour = e.registreDeRetour ; 
			return String.format("\tldr\t%s,=-%d\n",e.registreDeRetour,((Int)(e.e)).i);
		} else {
			System.err.println("internal error -- GenerationS -- Neg");
			System.exit(1);
			return null;
		}
	}

	@Override
	public String visit(Add e) {
		String r1 = "";
		String r2 = "";
		String retour = "";
		if(e.e1 instanceof Int){
			if(((Int)e.e1).i <= 121){
				r1 = e.e1.accept(this);
			} else {
				r1 = "r9" ;
				retour = String.format("\tldr\t%s,=%d\n",r1,((Int)(e.e1)).i);	
			}
		}
		else if (e.e1 instanceof Var) {
			r1 = e.e1.accept(this);
			if (r1.contains("[sp,")) {
				retour += String.format("\tldr\t%s,%s\n","r9",r1);
				r1 = "r9";
			} 
		} else {
			System.err.println("internal error -- GenerationS -- add");
			System.exit(1);
			return null;
		}
		if(e.e2 instanceof Int){
			if(((Int)e.e2).i <= 121){
				r2 = e.e2.accept(this);
			} else {
				r2 = "r10" ;
				retour+= String.format("\tldr\t%s,=%d\n",r2,((Int)(e.e2)).i);	
			}	
		}
		else if (e.e2 instanceof Var) {
			r2 = e.e2.accept(this);
			if (r2.contains("[sp,")) {
				retour += String.format("\tldr\t%s,%s\n","r10",r2);
				r2 = "r10";
			} 
		} else {
			System.err.println("internal error -- GenerationS -- add");
			System.exit(1);
			return null;
		}
		if(e.e1 instanceof Int) {
			return String.format("%s\tadd\t%s,%s,%s\n",retour,e.registreDeRetour, r2, r1);
		}
		else
			return String.format("%s\tadd\t%s,%s,%s\n",retour,e.registreDeRetour, r1, r2);
	}

	@Override
	public String visit(Sub e) {
		String r1 = "";
		String r2 = "";
		String retour = "";
		if(e.e1 instanceof Int){
			if(((Int)e.e1).i <= 121){
				r1 = e.e1.accept(this);
			} else {
			retour += String.format("\tldr\t%s,=%d\n","r9",((Int)(e.e1)).i);	
			r1 = "r9" ;
			}
		}
		else if (e.e1 instanceof Var) {
			r1 = e.e1.accept(this);
			if (r1.contains("[sp,")) {
				retour += String.format("\tldr\t%s,%s\n","r9",r1);
				r1 = "r9";
			} 
		} else {
			System.err.println("internal error -- GenerationS -- add");
			System.exit(1);
			return null;
		}
		if(e.e2 instanceof Int){
			if(((Int)e.e2).i <= 121){
				r2 = e.e2.accept(this);
			} else {
			retour+= String.format("\tldr\t%s,=%d\n","r10",((Int)(e.e2)).i);	
			r2 = "r10" ;
			}
		}
		else if (e.e2 instanceof Var) {
			r2 = e.e2.accept(this);
			if (r2.contains("[sp,")) {
				retour += String.format("\tldr\t%s,%s\n","r10",r2);
				r2 = "r10";
			} 
		} else {
			System.err.println("internal error -- GenerationS -- sub");
			System.exit(1);
			return null;
		}
		if(e.e1 instanceof Int) {
			return String.format("%s\tsub\t%s,%s,%s\n",retour,e.registreDeRetour, r2, r1);
		}
		else
			return String.format("%s\tsub\t%s,%s,%s\n",retour,e.registreDeRetour, r1, r2);
	}

	@Override
	public String visit(FNeg e) {
		/*Jamais utilisé*/
		if(e.e instanceof Int){
			if(((Int)(e.e)).i < 121){
				return String.format("\tmov\t%s,#-%d\n",e.registreDeRetour,((Int)(e.e)).i);
			} 
			e.e.registreDeRetour = e.registreDeRetour ; 
			return e.e.accept(this);
		} else {
			System.err.println("internal error -- GenerationS -- FNeg");
			System.exit(1);
			return null;
		}
	}

	@Override
	public String visit(FAdd e) {
		String r1 = "";
		String r2 = "";
		String retour = "";
		if(e.e1 instanceof Float){
			r1 = e.e1.registreDeRetour;
			retour += String.format("%s\tmov\tr1,%s\n",e.e1.accept(this),r1);
		} else if (e.e1 instanceof Var){
			r1 = e.e1.accept(this);
			if (r1.contains("[sp,")) {
				retour += String.format("\tldr\t%s,%s\n","r9",r1);
				r1 = "r9";
			} 
			retour += String.format("\tmov\tr1,%s\n",r1);
		} else {
			System.err.println("internal error -- GenerationS -- fadd");
			System.exit(1);
			return null;
		}
		if(e.e2 instanceof Float){
			r1 = e.e2.registreDeRetour;
			retour += String.format("%s\tmov\tr0,%s\n",e.e2.accept(this),r1);
		}else if (e.e2 instanceof Var){
			r2 = e.e2.accept(this);
			if (r2.contains("[sp,")) {
				retour += String.format("\tldr\t%s,%s\n","r10",r2);
				r2 = "r10";
			} 
			retour += String.format("\tmov\tr0,%s\n",r2);
		} else {
			System.err.println("internal error -- GenerationS -- add");
			System.exit(1);
			return null;
		}

		retour += "\tbl\tmin_caml_fadd\n";
		return String.format("%s\tmov\t%s, r0\n",retour,e.registreDeRetour);
	}

	@Override
	public String visit(FSub e) {
		String r1 = "";
		String r2 = "";
		String retour = "";
		if(e.e1 instanceof Float){
			r1 = e.e1.registreDeRetour;
			retour += String.format("%s\tmov\tr1,%s\n",e.e1.accept(this),r1);
		} else if (e.e1 instanceof Var){
			r1 = e.e1.accept(this);
			if (r1.contains("[sp,")) {
				retour += String.format("\tldr\t%s,%s\n","r9",r1);
				r1 = "r9";
			} 
			retour += String.format("\tmov\tr1,%s\n",r1);
		} else {
			System.err.println("internal error -- GenerationS -- fadd");
			System.exit(1);
			return null;
		}
		if(e.e2 instanceof Float){
			r1 = e.e2.registreDeRetour;
			retour += String.format("%s\tmov\tr0,%s\n",e.e2.accept(this),r1);
		}else if (e.e2 instanceof Var){
			r2 = e.e2.accept(this);
			if (r2.contains("[sp,")) {
				retour += String.format("\tldr\t%s,%s\n","r10",r2);
				r2 = "r10";
			} 
			retour += String.format("\tmov\tr0,%s\n",r2);
		} else {
			System.err.println("internal error -- GenerationS -- add");
			System.exit(1);
			return null;
		}

		retour += "\tbl\tmin_caml_fsub\\n";
		return String.format("%s\tmov\t%s, r0\n",retour,e.registreDeRetour);
	}

	@Override
	public String visit(FMul e) {
		String r1 = "";
		String r2 = "";
		String retour = "";
		if(e.e1 instanceof Float){
			r1 = e.e1.registreDeRetour;
			retour += String.format("%s\tmov\tr1,%s\n",e.e1.accept(this),r1);
		} else if (e.e1 instanceof Var){
			r1 = e.e1.accept(this);
			if (r1.contains("[sp,")) {
				retour += String.format("\tldr\t%s,%s\n","r1",r1);
			} 
			retour += String.format("\tmov\tr1,%s\n",r1);
		} else {
			System.err.println("internal error -- GenerationS -- fmul");
			System.exit(1);
			return null;
		}
		if(e.e2 instanceof Float){
			r1 = e.e2.registreDeRetour;
			retour += String.format("%s\tmov\tr0,%s\n",e.e2.accept(this),r1);
		}else if (e.e1 instanceof Var){
			r2 = e.e2.accept(this);
			if (r2.contains("[sp,")) {
				retour += String.format("\tldr\t%s,%s\n","r0",r2);
				retour += String.format("\tmov\tr0,%s\n",r2);
			} 
		} else {
			System.err.println("internal error -- GenerationS -- fmul");
			System.exit(1);
			return null;
		}

		retour += "\tbl\tmin_caml_fmul\n";
		return String.format("%s\tmov\t%s, r0\n",retour,e.registreDeRetour);
	}

	@Override
	public String visit(Mul e) {
		String r1 = "";
		String r2 = "";
		String retour = "";
		if(e.e1 instanceof Int){
			retour += String.format("\tmov\tr9,%s\n",r1);
			r1 = "r9";
		}
		else if (e.e1 instanceof Var) {
			r1 = e.e1.accept(this);
		} 
		else {
			System.err.println("internal error -- GenerationS -- mul");
			System.exit(1);
			return null;
		}
		if(e.e2 instanceof Int){
			retour += String.format("\tmov\tr10,%s\n",r2);
			r2 = "r10";
		}
		else if (e.e2 instanceof Var) {
			r2 = e.e2.accept(this);
		} 
		else {
			System.err.println("internal error -- GenerationS -- mul");
			System.exit(1);
			return null;
		}
		return String.format("%s\tmul\t%s,%s,%s\n",retour ,e.registreDeRetour, r1, r2);

	}

	@Override
	public String visit(FDiv e) {
		String r1 = "";
		String r2 = "";
		String retour = "";
		if(e.e1 instanceof Float){
			r1 = e.e1.registreDeRetour;
			retour += String.format("%s\tmov\tr1,%s\n",e.e1.accept(this),r1);
		} else if (e.e1 instanceof Var){
			r1 = e.e1.accept(this);
			retour += String.format("\tmov\tr1,%s\n",r1);
		} else {
			System.err.println("internal error -- GenerationS -- fdiv");
			System.exit(1);
			return null;
		}
		if(e.e2 instanceof Float){
			r1 = e.e2.registreDeRetour;
			retour += String.format("%s\tmov\tr0,%s\n",e.e2.accept(this),r1);
		}else if (e.e1 instanceof Var){
			r2 = e.e2.accept(this);
			retour += String.format("\tmov\tr0,%s\n",r2);
		} 
		else {
			System.err.println("internal error -- GenerationS -- fdiv");
			System.exit(1);
			return null;
		}
		String reg = e.registreDeRetour;
		return retour + "\tbl\tmin_caml_fdiv\n" + "\tmov\t"+reg+",r0\n";
	}

	@Override
	public String visit(Eq e) {
		String retour = "";
		String s1 = "";
		String s2 = "";
		if (e.e1 instanceof App) {
			e.e1.registreDeRetour = "r9";
			s1 = e.e1.accept(this);
			retour += s1;
			s1 = "r9";
		} else if (e.e1 instanceof Int && ((Int)(e.e1)).i > 121 ) {
			s1 = "r9" ;
			retour = String.format("\tldr\t%s,=%d\n",s1,((Int)(e.e1)).i);
		} else if (e.e1 instanceof Var) {
			s1 = e.e1.accept(this);
			if (s1.contains("[sp,")) {
				retour += String.format("\tldr\t%s,%s\n","r9",s1);
				s1 = "r9";
			} 
		} else  {
			s1 = e.e1.accept(this);
		}
		if (e.e2 instanceof App) {
			e.e2.registreDeRetour = "r10";
			s2 = e.e2.accept(this);
			retour += s2;
			s2 = "r10";
		} else if (e.e1 instanceof Int && ((Int)(e.e1)).i > 121 ) {
			s2 = "r10";
			retour += String.format("\tldr\t%s,=%d\n",s2,((Int)(e.e2)).i);
		} else if (e.e2 instanceof Var) {
			s2 = e.e2.accept(this);
			if (s2.contains("[sp,")) {
				retour += String.format("\tldr\t%s,%s\n","r10",s2);
				s2 = "r10";
			} 
		} else {
			s2 = e.e2.accept(this);
		}
		retour += String.format("\tcmp\t%s,%s\n", s1,s2);
		retour += "\tbne\tifFalse"+cmpIf+"\n";
		return retour;
	}

	@Override
	public String visit(LE e) {
		String retour = "";
		String s1 = "";
		String s2 = "";
		if (e.e1 instanceof App) {
			e.e1.registreDeRetour = "r9";
			s1 = e.e1.accept(this);
			retour += s1;
			s1 = "r9";
		} else if (e.e1 instanceof Int && ((Int)(e.e1)).i > 121 ) {
			s1 = "r9";
			retour += String.format("\tldr\t%s,=%d\n","r9",((Int)(e.e1)).i);
		} else if (e.e1 instanceof Var) {
			s1 = e.e1.accept(this);
			if (s1.contains("[sp,")) {
				retour += String.format("\tldr\t%s,%s\n","r9",s1);
				s1 = "r9";
			} 
		} else  {
			s1 = e.e1.accept(this);
		}
		if (e.e2 instanceof App) {
			e.e2.registreDeRetour = "r10";
			s2 = e.e2.accept(this);
			retour += s2;
			s2 = "r10";
		} else if (e.e1 instanceof Int && ((Int)(e.e1)).i > 121 ) {
			s2 = "r10";
			retour += String.format("\tldr\t%s,=%d\n","r10",((Int)(e.e2)).i);
		} else if (e.e2 instanceof Var) {
			s2 = e.e2.accept(this);
			if (s2.contains("[sp,")) {
				retour += String.format("\tldr\t%s,%s\n","r10",s2);
				s2 = "r10";
			} 
		} else {
			s2 = e.e2.accept(this);
		}
		retour =String.format("\tcmp\t%s,%s\n", s1,s2);
		retour += "\tbgt\tifFalse"+cmpIf+"\n";
		return retour;
	}

	@Override
	public String visit(If e) {
		cmpIf ++;
		String retour ="";		
		String ifTrue="ifTrue"+cmpIf+":\n";
		String ifFalse="ifFalse"+cmpIf+":\n";

		if(e.e1 instanceof OpBin || e.e1 instanceof OpUn){
			e.e1.registreDeRetour = e.registreDeRetour;
			retour += e.e1.accept(this);
		} else if (e.e1 instanceof Bool) {
			if (!((Bool)e.e1).b) {
				retour += "\tb\tifFalse"+cmpIf+"\n";
			}
		} else if (e.e1 instanceof Var) {
			e.e1.registreDeRetour = e.registreDeRetour;
			String r1 = e.e1.accept(this);
			if (r1.contains("[sp,")) {
				retour += String.format("\tldr\t%s,%s\n","r9",r1);
				r1 = "r9";
			} 
			retour += String.format("\tcmp\t%s,#1\n", r1);
			retour += "\tbne\tifFalse"+cmpIf+"\n";
		} else {
			System.err.println("internal error - If e1 (GenerationS)");
			System.exit(1);
		}
		if (e.e1 instanceof Not){
			e.e1.accept(this);
		}


		if (e.e2 instanceof Var){			
			String s1 = e.e2.accept(this);
			if (s1.contains("[sp,")) {
				ifTrue += String.format("\tldr\t%s,%s\n","r9",s1);
				s1 = "r9";
			} 
			ifTrue+=String.format("\tmov\t%s,%s\n",e.registreDeRetour,s1);			
		} else if (e.e2 instanceof App){
			e.e2.registreDeRetour = e.registreDeRetour;
			ifTrue+=e.e2.accept(this);				
		} else if (e.e2 instanceof Let){
			e.e2.registreDeRetour = e.registreDeRetour;
			ifTrue+=e.e2.accept(this);			
		} else if (e.e2 instanceof LetRec){
			e.e2.registreDeRetour = e.registreDeRetour;
			ifTrue+=e.e2.accept(this);			
		} else if (e.e2 instanceof OpBin || e.e2 instanceof OpUn){ 
			e.e2.registreDeRetour = e.registreDeRetour;
			ifTrue += e.e2.accept(this);		
		} else if (e.e2 instanceof If) {
			e.e2.registreDeRetour = e.registreDeRetour;
			ifTrue += e.e2.accept(this);
		} else if(e.e2 instanceof Int && ((Int)(e.e2)).i > 121){
			e.e2.registreDeRetour = e.registreDeRetour ; 
			ifTrue +=e.e2.accept(this);	
		} else { // entier +float +bool
			ifTrue+=String.format("\tmov\t%s,%s\n",e.registreDeRetour,e.e2.accept(this));	
		}

		if (e.e3 instanceof Var){
			String s1 = e.e3.accept(this);
			if (s1.contains("[sp,")) {
				ifFalse += String.format("\tldr\t%s,%s\n","r9",s1);
				s1 = "r9";
			} 
			ifFalse+=String.format("\tmov\t%s,%s\n",e.registreDeRetour,s1);			
		} else if (e.e3 instanceof App){
			e.e3.registreDeRetour = e.registreDeRetour;
			ifFalse+=e.e3.accept(this);			
		} else if (e.e3 instanceof Let){
			e.e2.registreDeRetour = e.registreDeRetour;		
			ifFalse+=e.e3.accept(this);		
		} else if (e.e3 instanceof LetRec){
			e.e2.registreDeRetour = e.registreDeRetour;
			ifFalse+=e.e3.accept(this);		
		} else if (e.e3 instanceof OpBin || e.e3 instanceof OpUn){ 
			e.e3.registreDeRetour = e.registreDeRetour;
			ifFalse += e.e3.accept(this);		
		} else if (e.e3 instanceof If) {
			e.e3.registreDeRetour = e.registreDeRetour;
			ifFalse += e.e3.accept(this);
		} else if(e.e3 instanceof Int && ((Int)(e.e3)).i > 121){
			e.e3.registreDeRetour = e.registreDeRetour ; 
			ifFalse += e.e3.accept(this);	
		} else {  //entier +float +bool
			ifFalse+=String.format("\tmov\t%s,%s\n",e.registreDeRetour,e.e3.accept(this));
		}		

		ifFalse+="\tb\tendIf"+cmpIf+"\n";
		ifTrue+="\tb\tendIf"+cmpIf+"\n";
		retour+=ifTrue;			
		retour+=ifFalse;
		retour+="endIf"+cmpIf+":\n";

		cmpIf --;
		return retour;
	}

	@Override
	public String visit(Let e) {
		boolean isSpill1 = false;
		String retour ="";
		String registre = RegistreAllocation.getRegistre(e.id);
		if (registre == null) {
			isSpill1 = true;
			registre = RegistreAllocation.spillInit(e.id);
			retour += RegistreAllocation.spillStart(registre);
		}
		if (e.e1 instanceof OpBin){
			e.e1.registreDeRetour = registre;
			retour += e.e1.accept(this);
		} else if (e.e1 instanceof Var) {
			String regE1 = e.e1.accept(this);
			if (regE1.contains("[sp,")) {
				retour += String.format("\tldr\t%s,%s\n","r9",regE1);
				regE1 = "r9";
			} 
			retour += String.format("\tmov\t%s,%s\n",registre,regE1);
		} else if (e.e1 instanceof If){
			e.e1.registreDeRetour = registre;
			retour+=e.e1.accept(this);
		} else if (e.e1 instanceof Float){
			e.e1.registreDeRetour = registre;
			retour+=e.e1.accept(this);			
		} else if (e.e1 instanceof Not){
			e.e1.registreDeRetour = registre;
			retour += e.e1.accept(this);
		} else if (e.e1 instanceof App) {
			e.e1.registreDeRetour = registre;
			retour += e.e1.accept(this);
			retour += String.format("\tmov\t%s,r0\n",registre);
		}else if (e.e1 instanceof Neg){
			e.e1.registreDeRetour = registre;
			retour += String.format("%s", e.e1.accept(this));
		}else if (e.e1 instanceof Array) {
			if(!data){
				defVar += ".data\n";
				data = true;
			}
			retour+=String.format("%s", e.e1.accept(this));
		}else if (e.e1 instanceof Get){
			if(!data){
				defVar += ".data\n";
				data = true;
			}
			e.e1.registreDeRetour = registre;
			retour+=String.format("%s", e.e1.accept(this));			
		} else if (e.e1 instanceof Int && ((Int)(e.e1)).i > 121){
			e.e1.registreDeRetour = e.registreDeRetour ; 
			retour += String.format("\tldr\t%s,=%d\n",e.registreDeRetour,((Int)(e.e1)).i);
		} else{
			retour += String.format("\tmov\t%s,%s\n", registre,e.e1.accept(this));
		}

		if (e.e2 instanceof OpBin){
			e.e2.registreDeRetour = e.registreDeRetour;
			retour += e.e2.accept(this);
		} else if (e.e2 instanceof Var) {
			String regE1 = e.e2.accept(this);
			if (regE1.contains("[sp,")) {
				retour += String.format("\tldr\t%s,%s\n","r9",regE1);
				regE1 = "r9";
			} 
			retour += String.format("\tmov\t%s,%s\n",e.registreDeRetour,regE1);
		}  else if (e.e2 instanceof Int && ((Int)(e.e2)).i > 121){
			e.e2.registreDeRetour = e.registreDeRetour ; 
			retour += String.format("\tldr\t%s,=%d\n",e.registreDeRetour,((Int)(e.e2)).i);
		} else {
			retour += e.e2.accept(this);
		}
		if (isSpill1) {
			retour += RegistreAllocation.spillEnd(registre);
		}
		return retour;
	}

	@Override
	public String visit(Var e) {
		boolean isVar = RegistreAllocation.isInTabVar(e.id);
		RegistreAllocation.updateTabVarArc(e);
		if(isVar){
			String reg = RegistreAllocation.getRegistre(e.id);
			if (reg != null) {
				return reg; 
			} else {
				System.err.println("register null for "+e.id.id);
				//System.exit(1);
			}
		}else{
			return String.format("\tbl\tmin_caml_%s\n",e.id);
		}
		return null;
	}

	@Override
	public String visit(LetRec e) {
		String reg="";
		String retour = "";
		int nbreg = 0;
		RegistreAllocation.startDefFunc();
		defFunc +=String.format("\nmin_caml_%s:\n",e.fd.id);
		defFunc += String.format("\t@prologue\n%s\n",prologue(0));
		for (Id id : e.fd.args){
			if (nbreg <4){
				reg = RegistreAllocation.getRegistre(id);
				if (reg == null) {
					reg = RegistreAllocation.spillInit(id);
					defFunc += RegistreAllocation.spillStart(reg);
				}
				defFunc += String.format("\tmov\t%s,r%d\n",reg,nbreg);
				nbreg++;
			}
			else{
				System.err.println(e.fd.id + " : invalid argument number (>3)");
				System.exit(1);
			}
		}

		if (e.fd.e instanceof OpBin){
			((OpBin)e.fd.e).registreDeRetour = "r0";
			retour += e.fd.e.accept(this);
		} else if (e.fd.e instanceof Var) {
			String s1 = e.fd.e.accept(this);
			if (s1.contains("[sp,")) {
				retour += String.format("\tldr\t%s,%s\n","r9",s1);
				s1 = "r9";
			} 
			if (RegistreAllocation.isInTabVar(((Var)e.fd.e).id)) {
				retour += String.format("\tmov\tr0,%s",s1);
			} else {
				retour += s1;
			}
		} else {
			retour += e.fd.e.accept(this);
		}
		defFunc += retour+"\n";
		defFunc +=String.format("\n\t@epilogue:\n%s\n",epilogue());
		defFunc += "\n\tbx\tlr\n";
		RegistreAllocation.endDefFunc();

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
			param.registreDeRetour = String.format("r%d", nbParam);
			if(param instanceof Float){
				if(!data){
					defVar += ".data\n";
					data = true;
				}
			}
			String strP = param.accept(this);
			if(strP!=null){
				if (param instanceof Var){
					registre = strP;
					if (e.e instanceof Var) {
						if (registre.contains("[sp,")) {
							retour += String.format("\tldr\tr10,%s",registre);
							retour +=String.format("\tmov\tr%d,%s\n",nbParam,  "r10");
						} else {
							retour +=String.format("\tmov\tr%d,%s\n",nbParam,  registre);
						}

					} else if (e.e instanceof App){
						((App)e.e).closure.add(registre);
						retour += e.e.accept(this);
					} else{
						System.err.println("internal error - definition function (GenerationS)");
						System.exit(1);
					}
					nbParam ++;
				} else if (param instanceof App || param instanceof OpBin) {
					if (e.e instanceof Var){
						if (nbParam>1){
							retour = String.format("%s\tstmdb\tsp!, {r0-r%d}\n",retour, nbParam-1);
						} else if (nbParam==1) {
							retour = String.format("%s\tstmdb\tsp!, {r0}\n",retour);
						}
						retour += strP;
						if (nbParam>1){
							retour = String.format("%s\tldmia\tsp!, {r0-r%d}\n",retour, nbParam-1);
						} else if (nbParam==1) {
							retour = String.format("%s\tldmia\tsp!, {r0}\n",retour);
						}
					} else if (e.e instanceof App){
						if (nbParam>1){
							retour = String.format("%s\tstmbd\tsp!, {r0-r%d}\n",retour, nbParam-1);
						} else if (nbParam==1) {
							retour = String.format("%s\tstmbd\tsp!, {r0}\n",retour);
						}
						retour += strP;
						if (nbParam>1){
							retour = String.format("%s\tldmia\tsp!, {r0-r%d}\n",retour, nbParam-1);
						} else if (nbParam==1) {
							retour = String.format("%s\tldmia\tsp!, {r0}\n",retour);
						}
						((App)e.e).closure.add(param.registreDeRetour);
					} else{
						System.err.println("internal error - definition function (GenerationS)");
						System.exit(1);
					}
					nbParam++;
				} else if (param instanceof Int && ((Int)(param)).i > 121){
					retour += String.format("\tldr\t%s,=%d\n",param.registreDeRetour,((Int)(param)).i);
					nbParam++;
				} else {
					if (e.e instanceof Var){
						if (strP.contains("[sp,")) {
							retour += String.format("\tldr\tr10,%s",strP);
							retour +=String.format("\tmov\tr%d,%s\n",nbParam,  "r10");
						} else {
							if(param instanceof Float){
								retour +=String.format("%s\n",strP);
							}else if (param instanceof Int && ((Int)param).i > 121) {
								retour += strP;
							} else {
								retour +=String.format("\tmov\tr%d,%s\n", nbParam, strP);
							}
						}
						nbParam++;
					} else if (e.e instanceof App){
						((App)e.e).closure.add(strP);
					}else{
						System.err.println("internal error - definition function (GenerationS)");
						System.exit(1);
					}
				}
			}
		}
		if (e.e instanceof Var) {
			for (String s : e.closure) {
				nbParam++;
				retour +=String.format("\tmov\tr%d,%s\n",nbParam-1,  s);
			}
			retour = String.format("%s\tbl\tmin_caml_%s\n",retour,((Var)e.e).id.id);
		} else if (e.e instanceof App){
			retour+=e.e.accept(this);
		}else{
			System.err.println("internal error - definition function (GenerationS)");
			System.exit(1);
		}


		if (e.registreDeRetour!= null && !e.registreDeRetour.equals("r0")) {
			retour += String.format("\tmov\t%s,r0\n",e.registreDeRetour);
		}
		return retour;

	}

	@Override
	public String visit(Tuple e) {
		String tuple="";
		for(Exp exp : e.es){
			tuple += String.format("%s",exp.accept(this));
		}
		return tuple;
	}

	@Override
	public String visit(LetTuple e) {
		String retour ="";
		String registre="";
		int cpt=0;
		LinkedList<Boolean> isSpill = new LinkedList<Boolean>();
		for (Id id : e.ids) {
			isSpill.add(true);
			registre = RegistreAllocation.getRegistre(id);
			if (registre == null) {
				isSpill.set(cpt, true);	
				registre = RegistreAllocation.spillInit(id);
				retour += RegistreAllocation.spillStart(registre);
			}
			if ( e.e1 instanceof Tuple) {
				retour += String.format("\tmov\t%s,%s\n", registre, ((Tuple)e.e1).es.get(cpt).accept(this));	
			} else {
				retour += String.format("\tmov\t%s,%s\n", registre, e.e1.accept(this));	
			}
			cpt++;
		}
		if (e.e2 instanceof OpBin){
			boolean isSpill1 = false;
			Id idretour = Id.gen();
			String regRetour = RegistreAllocation.getRegistre(idretour);
			if (regRetour == null) {
				isSpill1 = true;
				regRetour = RegistreAllocation.spillInit(idretour);
				retour += RegistreAllocation.spillStart(regRetour);
			}
			e.e2.registreDeRetour = regRetour;
			retour += e.e2.accept(this);
			retour += String.format("\tmov\t%s,%s\n",e.registreDeRetour,regRetour);
			if (isSpill1) {
				retour += RegistreAllocation.spillEnd(regRetour);
			}
		} else if (e.e2 instanceof Var) {
			String regE1 = e.e2.accept(this);
			retour += String.format("\tmov\t%s,%s\n",e.registreDeRetour,regE1);
		} else {
			retour += e.e2.accept(this);
		}
		return retour;
	}

	@Override
	public String visit(Array e) {
		LinkedList<Id> listeid = new LinkedList<Id>();
		String retour="";
		cmpTab++;

		if (e.e1 instanceof Int){
			int tailleT = ((Int)(e.e1)).i;

			Id idretour = Id.gen();
			e.registreDeRetour= RegistreAllocation.getRegistre(idretour);
			defVar+=String.format("array%d:\t.skip %d\n",cmpTab,tailleT *100);
			defFunc+=String.format("addr_tab%d:\t.word array%d\n",cmpTab,cmpTab);

			retour+=String.format("\tldr\t%s,addr_tab%d\n\tmov\tr0,%s\n",e.registreDeRetour,cmpTab,e.registreDeRetour);
			retour+=String.format("\tmov\tr1,#%d\t@lenght of the array\n", tailleT);
			if (e.e2 instanceof Float){
				idretour = Id.gen();
				listeid.add(idretour);
				e.e2.registreDeRetour= RegistreAllocation.getRegistre(idretour);
				retour+=e.e2.accept(this);
				retour+=String.format("\tmov\tr2,%s\n",e.e2.registreDeRetour);
				retour+="\tbl\tmin_caml_create_float_array\n";
			}else if(e.e2 instanceof Tuple){
				//save r0,r1 :
				//retour+=String.format("\tmov\t%s,r1",); 



				idretour = Id.gen();
				listeid.add(idretour);
				String reg= RegistreAllocation.getRegistre(idretour);
				idretour = Id.gen();
				listeid.add(idretour);
				String reg2= RegistreAllocation.getRegistre(idretour);
				String regTuple="";

				//Create array composed to the element of Tuple : 
				int nbTuple = ((Tuple)(e.e2)).es.size();
				retour+="\tmov\tr0,#0\n";
				retour+=String.format("\tmov\tr1,#%d\n",nbTuple);
				retour+=String.format("\tmov\tr2,%s",((Tuple)e.e2).es.get(0));
				retour+="\tbl\tmin_caml_create_array\n";
				for(int i=1;i<nbTuple;i++){
					regTuple=((Tuple)e.e2).es.get(i).registreDeRetour;
					retour+=String.format("\tmov\t%s,#%d\n",reg2,i);
					retour+=String.format("\tstr\t%s,[%s,%s,LSL #2]\n",regTuple,reg,reg2);
				}





				retour+="\tbl\tmin_caml_create_array\n";
			}else{
				retour+=String.format("\tmov\tr2,%s\n",e.e2.accept(this));
				retour+="\tbl\tmin_caml_create_array\n";
			}			

		}else{
			System.err.println("internal error - Array (GenerationS)");
			System.exit(1);
		}		
		return retour;
	}

	@Override
	public String visit(Get e) {
		LinkedList<Id> listeid = new LinkedList<Id>();
		String retour = "";
		if (e.e1 instanceof Array){
			retour += e.e1.accept(this);
			String reg0 = e.e1.registreDeRetour;
			if (e.e2 instanceof Int){
				Id idretour = Id.gen();
				listeid.add(idretour);
				String reg1 = RegistreAllocation.getRegistre(idretour);				
				retour+=String.format("\tmov\t%s,%s\n",reg1,e.e2.accept(this));
				retour+=String.format("\tldr\t%s,[%s,%s,LSL #2]\n",e.registreDeRetour,reg0,reg1);				
			}else{
				System.err.println("internal error - Get (GenerationS)");
				System.exit(1);
			}
		}	
		return retour;
	}

	@Override
	public String visit(Put e) {
		LinkedList<Id> listeid = new LinkedList<Id>();
		String retour = "";
		if (e.e1 instanceof Array){
			retour += e.e1.accept(this);
			String reg0 = e.e1.registreDeRetour;
			if (e.e2 instanceof Int){
				Id idretour = Id.gen();
				listeid.add(idretour);
				String reg1 = RegistreAllocation.getRegistre(idretour);	
				retour+=String.format("\tmov\t%s,%s\n",reg1,e.e2.accept(this));
				retour+=String.format("\tstr\t%s,[%s,%s,LSL #2]\n",e.registreDeRetour,reg0,reg1);				
			}else{
				System.err.println("internal error - Put (GenerationS)");
				System.exit(1);
			}
		}	
		return retour;
	}

	@Override
	public String visit(Unit unit) {
		return null;
	}	

	private String epilogue() {
		//return "\tadd\tr13,r10,#0\n\tldr\tr10,[r13]\n\tadd\tr13,r13,#4";
		return "\tnop\n\tldmfd\tsp!, {r4-r8, lr}\n";
	}

	private String prologue(int nbVL) {
		//return String.format("\tadd\tr13,r13,#-4\n\tstr\tr10,[r13]\n\tadd\tr10,r13,#0\n\tadd\tr13,r13,#-%d @taille des variables locales\n", 4*nbVL);
		return "\tstmfd\tsp!, {r4-r8, lr}\n";
	}
}
