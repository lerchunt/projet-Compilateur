import java.util.Collections;
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
	private static int cmpTuple =0;
	private static int cptf=0;

	private LinkedList<Closure> envFunc = new LinkedList<Closure>();
	private class Closure {
		public Closure(Id i, List<Id> args) {
			this.id = i;
			for (Id exp : args) {
				this.args.add(exp);
			}
		}
		Id id;
		List<Id> args = new LinkedList<Id>();
	}

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
		}else{
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
				r1 = "r11" ;
				retour += String.format("\tldr\t%s,=%d\n",r1,((Int)(e.e1)).i);	
			}
		}
		else if (e.e1 instanceof Var) {
			r1 = e.e1.accept(this);
			if (r1.contains("[sp,")) {
				retour += String.format("\tldr\t%s,%s\n","r11",r1);
				r1 = "r11";
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
			retour+= String.format("%s\tadd\t%s,%s,%s\n",retour,e.registreDeRetour, r2, r1);
		}else{
			retour+= String.format("%s\tadd\t%s,%s,%s\n",retour,e.registreDeRetour, r1, r2);
		}
		return retour;
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
				retour += String.format("\tldr\t%s,=%d\n","r11",((Int)(e.e1)).i);	
				r1 = "r11" ;
			}
		}
		else if (e.e1 instanceof Var) {
			r1 = e.e1.accept(this);
			if (r1.contains("[sp,")) {
				retour += String.format("\tldr\t%s,%s\n","r11",r1);
				r1 = "r11";
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
			retour += String.format("\tmov\tr11, %s\n", r1);
			return String.format("%s\tsub\t%s,%s,%s\n",retour,e.registreDeRetour, "r11", r2);
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
		} else if (e.e instanceof Float){ 
			e.e.registreDeRetour = "r1";
			return String.format("\tmov\tr0,#0\n%s\tbl\tmin_caml_fsub\n\tmov\t%s,r0\n",e.e.accept(this),e.registreDeRetour);
			
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
			e.e1.registreDeRetour = "r10";
			retour += String.format("%s\tmov\tr1,%s\n",e.e1.accept(this),"r10");
		} else if (e.e1 instanceof Var){
			r1 = e.e1.accept(this);
			if (r1.contains("[sp,")) {
				retour += String.format("\tldr\t%s,%s\n","r10",r1);
				r1 = "r10";
			} 
			retour += String.format("\tmov\tr1,%s\n",r1);
		} else {
			System.err.println("internal error -- GenerationS -- fadd");
			System.exit(1);
			return null;
		}
		if(e.e2 instanceof Float){
			e.e2.registreDeRetour= "r11";
			if(!r2.equals("r0"))
				retour += String.format("%s\tmov\tr0,%s\n",e.e2.accept(this),"r11");
			else
				retour += e.e2.accept(this);
		}else if (e.e2 instanceof Var){
			r2 = e.e2.accept(this);
			if (r2.contains("[sp,")) {
				retour += String.format("\tldr\t%s,%s\n","r11",r2);
				r2 = "r11";
			} 
			retour += String.format("\tmov\tr0,%s\n",r2);
		} else {
			System.err.println("internal error -- GenerationS -- fadd");
			System.exit(1);
			return null;
		}

		retour += "\tbl\tmin_caml_fadd\n";
		if (!e.registreDeRetour.equals("r0"))
			return String.format("%s\tmov\t%s, r0\n",retour,e.registreDeRetour);
		else
			return retour;
	}

	@Override
	public String visit(FSub e) {
		String r1 = "";
		String r2 = "";
		String retour = "";
		if(e.e1 instanceof Float){
			e.e1.registreDeRetour = "r10";
			retour += String.format("%s\tmov\tr0,%s\n",e.e1.accept(this),"r10");
		} else if (e.e1 instanceof Var){
			r1 = e.e1.accept(this);
			if (r1.contains("[sp,")) {
				retour += String.format("\tldr\t%s,%s\n","r10",r1);
				r1 = "r10";
			} 
			retour += String.format("\tmov\tr0,%s\n",r1);
		} else {
			System.err.println("internal error -- GenerationS -- fsub");
			System.exit(1);
			return null;
		}
		if(e.e2 instanceof Float){
			e.e2.registreDeRetour= "r11";
			if(!r2.equals("r0"))
				retour += String.format("%s\tmov\tr1,%s\n",e.e2.accept(this),"r11");
			else
				retour += e.e2.accept(this);
		}else if (e.e2 instanceof Var){
			r2 = e.e2.accept(this);
			if (r2.contains("[sp,")) {
				retour += String.format("\tldr\t%s,%s\n","r11",r2);
				r2 = "r11";
			} 
			retour += String.format("\tmov\tr1,%s\n",r2);
		} else {
			System.err.println("internal error -- GenerationS -- fsub");
			System.exit(1);
			return null;
		}

		retour += "\tbl\tmin_caml_fsub\n";
		if (!e.registreDeRetour.equals("r0"))
			return String.format("%s\tmov\t%s, r0\n",retour,e.registreDeRetour);
		else
			return retour;
	}

	@Override
	public String visit(FMul e) {
		String r1 = "";
		String r2 = "";
		String retour = "";
		if(e.e1 instanceof Float){
			e.e1.registreDeRetour = "r10";
			retour += String.format("%s\tmov\tr1,%s\n",e.e1.accept(this),"r10");
		} else if (e.e1 instanceof Var){
			r1 = e.e1.accept(this);
			if (r1.contains("[sp,")) {
				retour += String.format("\tldr\t%s,%s\n","r10",r1);
				r1 = "r10";
			} 
			retour += String.format("\tmov\tr1,%s\n",r1);
		} else {
			System.err.println("internal error -- GenerationS -- fmul");
			System.exit(1);
			return null;
		}
		if(e.e2 instanceof Float){
			e.e2.registreDeRetour= "r11";
			if(!r2.equals("r0"))
				retour += String.format("%s\tmov\tr0,%s\n",e.e2.accept(this),"r11");
			else
				retour += e.e2.accept(this);
		}else if (e.e2 instanceof Var){
			r2 = e.e2.accept(this);
			if (r2.contains("[sp,")) {
				retour += String.format("\tldr\t%s,%s\n","r11",r2);
				r2 = "r11";
			} 
			retour += String.format("\tmov\tr0,%s\n",r2);
		} else {
			System.err.println("internal error -- GenerationS -- fmul");
			System.exit(1);
			return null;
		}

		retour += "\tbl\tmin_caml_fmul\n";
		if (!e.registreDeRetour.equals("r0"))
			return String.format("%s\tmov\t%s, r0\n",retour,e.registreDeRetour);
		else
			return retour;
	}

	@Override
	public String visit(Mul e) {
		String r1 = "";
		String r2 = "";
		String retour = "";
		if(e.e1 instanceof Int){
			r1 = e.e1.accept(this);
			retour += String.format("\tmov\tr11,%s\n",r1);
			r1 = "r11";
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
			r2 = e.e2.accept(this);
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
			e.e1.registreDeRetour = "r10";
			retour += String.format("%s\tmov\tr1,%s\n",e.e1.accept(this),"r10");
		} else if (e.e1 instanceof Var){
			r1 = e.e1.accept(this);
			if (r1.contains("[sp,")) {
				retour += String.format("\tldr\t%s,%s\n","r10",r1);
				r1 = "r10";
			} 
			retour += String.format("\tmov\tr1,%s\n",r1);
		} else {
			System.err.println("internal error -- GenerationS -- fdiv");
			System.exit(1);
			return null;
		}
		if(e.e2 instanceof Float){
			e.e2.registreDeRetour= "r11";
			if(!r2.equals("r0"))
				retour += String.format("%s\tmov\tr0,%s\n",e.e2.accept(this),"r11");
			else
				retour += e.e2.accept(this);
		}else if (e.e2 instanceof Var){
			r2 = e.e2.accept(this);
			if (r2.contains("[sp,")) {
				retour += String.format("\tldr\t%s,%s\n","r11",r2);
				r2 = "r11";
			} 
			retour += String.format("\tmov\tr0,%s\n",r2);
		} else {
			System.err.println("internal error -- GenerationS -- fdiv");
			System.exit(1);
			return null;
		}

		retour += "\tbl\tmin_caml_fdiv\n";
		if (!e.registreDeRetour.equals("r0"))
			return String.format("%s\tmov\t%s, r0\n",retour,e.registreDeRetour);
		else
			return retour;
	}

	@Override
	public String visit(Eq e) {
		String retour = "";
		String s1 = "";
		String s2 = "";
		if (e.e1 instanceof App) {
			e.e1.registreDeRetour = "r11";
			s1 = e.e1.accept(this);
			retour += s1;
			s1 = "r11";
		} else if (e.e1 instanceof Int && ((Int)(e.e1)).i > 121 ) {
			s1 = "r11" ;
			retour = String.format("\tldr\t%s,=%d\n",s1,((Int)(e.e1)).i);
		} else if (e.e1 instanceof Var) {
			s1 = e.e1.accept(this);
			if (s1.contains("[sp,")) {
				retour += String.format("\tldr\t%s,%s\n","r11",s1);
				s1 = "r11";
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
			e.e1.registreDeRetour = "r11";
			s1 = e.e1.accept(this);
			retour += s1;
			s1 = "r11";
		} else if (e.e1 instanceof Int && ((Int)(e.e1)).i > 121 ) {
			s1 = "r11";
			retour += String.format("\tldr\t%s,=%d\n","r11",((Int)(e.e1)).i);
		} else if (e.e1 instanceof Var) {
			s1 = e.e1.accept(this);
			if (s1.contains("[sp,")) {
				retour += String.format("\tldr\t%s,%s\n","r11",s1);
				s1 = "r11";
			} 
		} else if (e.e1 instanceof Int){
			retour+= String.format("\tmov\tr10,%s\n", e.e1.accept(this)); 
			s1 = "r10";
		}else{
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
		retour +=String.format("\tcmp\t%s,%s\n", s1,s2);
		retour += "\tbgt\tifFalse"+cmpIf+"\n";
		return retour;
	}

	@Override
	public String visit(If e) {
		cmpIf ++;
		int IdIf = cmpIf;
		String retour ="";		
		String ifTrue="ifTrue"+IdIf+":\n";
		String ifFalse="ifFalse"+IdIf+":\n";

		if(e.e1 instanceof OpBin || e.e1 instanceof OpUn){
			e.e1.registreDeRetour = e.registreDeRetour;
			retour += e.e1.accept(this);
		} else if (e.e1 instanceof Bool) {
			if (!((Bool)e.e1).b) {
				retour += "\tb\tifFalse"+IdIf+"\n";
			}
		} else if (e.e1 instanceof Var) {
			e.e1.registreDeRetour = e.registreDeRetour;
			String r1 = e.e1.accept(this);
			if (r1.contains("[sp,")) {
				retour += String.format("\tldr\t%s,%s\n","r11",r1);
				r1 = "r11";
			} 
			retour += String.format("\tcmp\t%s,#1\n", r1);
			retour += "\tbne\tifFalse"+IdIf+"\n";
		}else if (e.e1 instanceof Not){
			e.e1.accept(this);
		} else {
			System.err.println("internal error - If e1 (GenerationS)");
			System.exit(1);		
		}


		if (e.e2 instanceof Var){			
			String s1 = e.e2.accept(this);
			if (s1.contains("[sp,")) {
				ifTrue += String.format("\tldr\t%s,%s\n","r11",s1);
				s1 = "r11";
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
		}else if (e.e2 instanceof Float){
			e.e2.registreDeRetour = e.registreDeRetour;
			ifTrue += e.e2.accept(this);
		} else if (e.e2 instanceof FNeg){
			e.e2.registreDeRetour = e.registreDeRetour;
			ifTrue += e.e2.accept(this);
		}else{// entier  +bool
			ifTrue+=String.format("\tmov\t%s,%s\n",e.registreDeRetour,e.e2.accept(this));	
		}

		if (e.e3 instanceof Var){
			String s1 = e.e3.accept(this);
			if (s1.contains("[sp,")) {
				ifFalse += String.format("\tldr\t%s,%s\n","r11",s1);
				s1 = "r11";
			} 
			ifFalse+=String.format("\tmov\t%s,%s\n",e.registreDeRetour,s1);			
		} else if (e.e3 instanceof App){
			e.e3.registreDeRetour = e.registreDeRetour;
			ifFalse+=e.e3.accept(this);			
		} else if (e.e3 instanceof Let){
			e.e3.registreDeRetour = e.registreDeRetour;		
			ifFalse+=e.e3.accept(this);		
		} else if (e.e3 instanceof LetRec){
			e.e3.registreDeRetour = e.registreDeRetour;
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
		} else if (e.e3 instanceof FNeg){
			e.e3.registreDeRetour = e.registreDeRetour;
			ifFalse += e.e3.accept(this);
		} else {  //entier +float +bool
			ifFalse+=String.format("\tmov\t%s,%s\n",e.registreDeRetour,e.e3.accept(this));
		}		

		ifFalse+="\tb\tendIf"+IdIf+"\n";
		ifTrue+="\tb\tendIf"+IdIf+"\n";
		retour+=ifTrue;			
		retour+=ifFalse;
		retour+="endIf"+IdIf+":\n";

		return retour;
	}

	@Override
	public String visit(Let e) {
		boolean isSpill1 = false;
		String retour ="";
		String registre = RegistreAllocation.getRegistre(e.id);
		String regVar = "";
		int cpt = 0;
		
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
			if (regE1.contains("bl")) {		
				defFunc +=String.format("\nmin_caml_%s:\n",e.id);
				RegistreAllocation.startDefFunc();
				defFunc += regE1+"\n";
				defFunc += "\n\tbx\tlr\n";
				RegistreAllocation.endDefFunc();
			} else {
				if (regE1.contains("[sp,")) {
					retour += String.format("\tldr\t%s,%s\n","r11",regE1);
					regE1 = "r11";
				} 
				retour += String.format("\tmov\t%s,%s\n",registre,regE1);
			}
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
			e.e1.registreDeRetour = registre;
			retour += String.format("%s", e.e1.accept(this));
		}else if (e.e1 instanceof Get){
			if(!data){
				defVar += ".data\n";
				data = true;
			}
			e.e1.registreDeRetour = registre;
			retour+=String.format("%s", e.e1.accept(this));		
		}else if (e.e1 instanceof Tuple){
			e.e1.registreDeRetour = registre;
			retour += String.format("%s", e.e1.accept(this));
		} else if (e.e1 instanceof Int && ((Int)(e.e1)).i > 121){
			e.e1.registreDeRetour = e.registreDeRetour ; 
			retour += String.format("\tldr\t%s,=%d\n",e.registreDeRetour,((Int)(e.e1)).i);
		} else if (e.e1 instanceof Put){
			e.e1.registreDeRetour = registre;
			retour +=e.e1.accept(this);
		}else if (e.e1 instanceof FNeg){
			e.e1.registreDeRetour = registre;
			retour+=e.e1.accept(this);
		}else{
			retour += String.format("\tmov\t%s,%s\n", registre,e.e1.accept(this));
		}

		if (e.e2 instanceof OpBin){
			e.e2.registreDeRetour = e.registreDeRetour;
			retour += e.e2.accept(this);
			
		} else if (e.e2 instanceof Var) {
			String regE1 = e.e2.accept(this);
			if (regE1.contains("[sp,")) {
				retour += String.format("\tldr\t%s,%s\n","r11",regE1);
				regE1 = "r11";
			} 
			retour += String.format("\tmov\t%s,%s\n",e.registreDeRetour,regE1);
		}  else if (e.e2 instanceof Int && ((Int)(e.e2)).i > 121){
			e.e2.registreDeRetour = e.registreDeRetour ; 
			retour += String.format("\tldr\t%s,=%d\n",e.registreDeRetour,((Int)(e.e2)).i);	
		}else if(e.e2 instanceof LetTuple){
			/*for (Id id : ((LetTuple)(e.e2)).ids){
				regVar = RegistreAllocation.getRegistre(id);
				retour += regVar + " "+id+ "\n";
				retour += "\tmov\tr10,#"+cpt+"\n";
				retour += "\tldr\t"+regVar+",[r4,r10,LSL #2]\n";
				cpt++;
			}*/
			retour += e.e2.accept(this);
		}else {
			e.e2.registreDeRetour = e.registreDeRetour ; 
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
			return String.format("\tbl\tmin_caml_%s\n",e.id.id);
		}
		return null;
	}

	@Override
	public String visit(LetRec e) {
		String reg="";
		String retour = "";
		int nbreg = e.fd.args.size()-1;
		envFunc.add(new Closure(e.fd.id,e.fd.args));
		
		RegistreAllocation.startDefFunc();
		defFunc +=String.format("\nmin_caml_%s:\n",e.fd.id);
		defFunc += String.format("\t@prologue\n%s\n",prologue(0));
		int pile =28;
		LinkedList<String> isSpilled = new LinkedList<String>();
		Collections.reverse(e.fd.args);
		for (Id id : e.fd.args){
			if (nbreg < 4){
				reg = RegistreAllocation.getRegistre(id);
				if (reg == null) {
					reg = RegistreAllocation.spillInit(id);
					isSpilled.add(reg);
					defFunc += RegistreAllocation.spillStart(reg);
				}
				defFunc += String.format("\tmov\t%s,r%d\n",reg,nbreg);
			}
			else{
				reg = RegistreAllocation.getRegistre(id);
				if (reg == null) {
					reg = RegistreAllocation.spillInit(id);
					isSpilled.add(reg);
					defFunc += RegistreAllocation.spillStart(reg);
				}
				defFunc += String.format("\tldr\t%s, [r13, #%d]\n",reg,pile);
				pile+=4;
			}
			nbreg--;
		}
		Collections.reverse(e.fd.args);

		if (e.fd.e instanceof OpBin){
			((OpBin)e.fd.e).registreDeRetour = "r0";
			retour += e.fd.e.accept(this);
		} else if (e.fd.e instanceof Var) {
			String s1 = e.fd.e.accept(this);
			if (s1.contains("[sp,")) {
				retour += String.format("\tldr\t%s,%s\n","r11",s1);
				s1 = "r11";
			} 
			if (RegistreAllocation.isInTabVar(((Var)e.fd.e).id)) {
				retour += String.format("\tmov\tr0,%s\n",s1);
			} else {
				for (Closure c : envFunc) {
					if (c.id.equals(((Var)e.fd.e).id)) {
						int cmp = 0;
						for (int i = c.args.size()-1; i>= 0; i--) {
							RegistreAllocation.addVar(String.format("closure%sr%d",((Var)e.fd.e).id.id,cmp));
							retour += String.format("\tldr\tr%d, [r13, #%d]\n",i,pile+cmp*4);
							cmp++;
						}
					}
				}
				retour += s1;
			}
		} else if (e.fd.e instanceof App) {
			for (Closure c : envFunc) {
				if (((App)e.fd.e).e instanceof Var && c.id.equals(((Var)((App)e.fd.e).e).id)) {
					int cmp = 0;
					for (Id id : c.args) {
						if (e.fd.args.contains(id)) {
							retour += String.format("\tmov\tr%d,%s\n",cmp, RegistreAllocation.getRegistre(id));
						} else {
							RegistreAllocation.addVar(String.format("closure%sr%d",(((Var)((App)e.fd.e).e).id).id,cmp));
							retour += String.format("\tldr\tr%d, [r13, #%d]\n",cmp,28+cmp*4);
						}
						cmp++;
					}
					retour = String.format("%s\tbl\tmin_caml_%s\n",retour,(((Var)((App)e.fd.e).e).id.id));
				}
			}
			//retour += e.fd.e.accept(this);
		} else {
			retour += e.fd.e.accept(this);
		}
		for (String s : isSpilled) {
			retour += RegistreAllocation.spillEnd(s);
		}
		defFunc += retour+"\n";
		defFunc +=String.format("\n\t@epilogue:\n%s\n",epilogue());
		defFunc += "\n\tbx\tlr\n";
		RegistreAllocation.endDefFunc();

		e.e.registreDeRetour = e.registreDeRetour;
		return e.e.accept(this);	
	}

	@Override
	public String visit(App e) {
		String retour="";
		String registre="";
		int nbParam = 0;
		for(Exp param : e.es){
			if (nbParam < 4) {
				param.registreDeRetour = String.format("r%d", nbParam);
			} else {
				param.registreDeRetour = "r10";
			}
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
							retour += String.format("\tldr\tr10,%s\n",registre);
							retour +=String.format("\tmov\t%s,%s\n",param.registreDeRetour,  "r10");
						}else {
							retour +=String.format("\tmov\t%s,%s\n",param.registreDeRetour,  registre);
						}
					} else if (e.e instanceof App){
						((App)e.e).closure.add(param.registreDeRetour);
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
						((App)e.e).closure.add(param.registreDeRetour);
					} else{
						System.err.println("internal error - definition function (GenerationS)");
						System.exit(1);
					}
					nbParam++;
				} else if (param instanceof Int && ((Int)(param)).i > 121){
					retour += String.format("\tldr\t%s,=%d\n",param.registreDeRetour,((Int)(param)).i);
					if (e.e instanceof App){
						((App)e.e).closure.add(param.registreDeRetour);
					}
					nbParam++;
				} else {
					if (e.e instanceof Var){
						if (strP.contains("[sp,")) {
							retour += String.format("\tldr\tr10,%s\n",strP);
							retour +=String.format("\tmov\t%s,%s\n",param.registreDeRetour,  "r10");
						} else {
							if(param instanceof Float){
								retour +=String.format("%s",strP);
							}else if (param instanceof Int && ((Int)param).i > 121) {
								retour += strP;
							}else if(param instanceof Array){
								retour+=strP;
							}else if(param instanceof Tuple){
								retour+=strP;
							}else if(param instanceof Get){
								retour+=strP;
							}else if(param instanceof Put){
								retour+=strP;
							}else if (param instanceof FNeg) {
								retour+=strP;
							}else{
								retour +=String.format("\tmov\t%s,%s\n", param.registreDeRetour, strP);
							}
						}
						nbParam++;
					} else if (e.e instanceof App){
						retour +=String.format("\tmov\tr%d,%s\n", nbParam, strP);
						((App)e.e).closure.add(param.registreDeRetour);
						nbParam++;
					}else{
						System.err.println("internal error - definition function (GenerationS)");
						System.exit(1);
					}
				}
			}
			if (nbParam > 4) {
				retour += String.format("\tSTMFD\tsp!, {%s}\n",param.registreDeRetour);
			}
		}
		if (e.e instanceof Var) {
			for (String s : e.closure) {
				RegistreAllocation.SpillTabVar();
				retour = String.format("\tSTMFD\tsp!, {%s}\n",s) + retour;
			}
			retour = String.format("%s\tbl\tmin_caml_%s\n",retour,((Var)e.e).id.id);
			for (String s : e.closure) {
				retour += String.format("\tLDMFD\tsp!, {%s}\n","r1");
				RegistreAllocation.UnSpillTabVar();
			}
		} else if (e.e instanceof App){
			retour+=e.e.accept(this);
		}else{
			System.err.println("internal error - definition function (GenerationS)");
			System.exit(1);
		}


		while (nbParam>4) {
			retour += String.format("\tLDMFD\tsp!, {%s}\n","r1");
			nbParam--;
		}
		if (e.registreDeRetour!= null && !e.registreDeRetour.equals("r0")) {
			retour += String.format("\tmov\t%s,r0\n",e.registreDeRetour);
		}
		return retour;

	}

	@Override
	public String visit(Tuple e) {
		cmpTuple++;	
		String retour = "";
		boolean isSpill1 = false;
		String reg = e.registreDeRetour;
		//Create array composed to the element of Tuple : 
		int nbTuple = e.es.size();
		if(!data){
			defVar += ".data\n";
			data = true;
		}
		defVar+=String.format("arrayTuple%d:\t.skip %d\n",cmpTuple,nbTuple *100);
		defFunc+=String.format("addr_tabTuple%d:\t.word arrayTuple%d\n",cmpTuple,cmpTuple);

		retour+=String.format("\tldr\t%s,addr_tabTuple%d\n\tmov\tr0,%s\n",reg,cmpTuple,reg);
		retour+=String.format("\tmov\tr1,#%d\t\n", nbTuple);
		retour+=String.format("\tmov\tr2,#0\n");
		retour+="\tbl\tmin_caml_create_array\n";

		for(int i=0;i<nbTuple;i++){
			String regTuple = e.es.get(i).registreDeRetour;
			if (e.es.get(i) instanceof Float){
				retour+=String.format("%s",e.es.get(i).accept(this));
				retour+=String.format("\tmov\tr10,#%d\n",i);
				retour+=String.format("\tstr\t%s,[%s,r10,LSL #2]\n",regTuple,reg);
			} else if (e.es.get(i) instanceof Array){
				retour+=String.format("%s\n",e.es.get(i).accept(this));
				retour+=String.format("\tmov\t%s,%s\n", regTuple,((Array)(e.es.get(i))).e1.registreDeRetour);
				retour+=String.format("\tmov\tr10,#%d\n",i);
				retour+=String.format("\tstr\t%s,[%s,r10,LSL #2]\n",regTuple,reg);					
			}else if (e.es.get(i) instanceof Var){
				String regVar = RegistreAllocation.getRegistre(((Var)(e.es.get(i))).id); 
				if (regVar == null) {
					isSpill1 = true;
					regVar = RegistreAllocation.spillInit(((Var)(e.es.get(i))).id);
					retour += RegistreAllocation.spillStart(regVar);
				}
				e.es.get(i).registreDeRetour = regVar;				
				retour+=String.format("\tmov\t%s,%s\n",regTuple,e.es.get(i).accept(this));
				retour+=String.format("\tmov\tr10,#%d\n",i);
				retour+=String.format("\tstr\t%s,[%s,r10,LSL #2]\n",regTuple,reg);
				if (isSpill1){
					RegistreAllocation.spillEnd(regVar);
					isSpill1 = false;
				}	
			}else if (e.es.get(i) instanceof Int){
				retour+=String.format("\tmov\t%s,%s\n",regTuple,e.es.get(i).accept(this));
				retour+=String.format("\tmov\tr10,#%d\n",i);
				retour+=String.format("\tstr\t%s,[%s,r10,LSL #2]\n",regTuple,reg);
			}
		}
		return retour;
	}

	@Override
	public String visit(LetTuple e) { 
		boolean isSpill = false; 
		String retour ="";
		int cpt=0;
		if (e.e1 instanceof Tuple) {
			retour += "retour";
			for (int i=0;i<((Tuple)(e.e1)).es.size();i++){
				if (((Tuple)(e.e1)).es.get(i) instanceof Int){
					String regVarTuple = RegistreAllocation.getRegistre(e.ids.get(i)); 
					if (regVarTuple == null) {
						isSpill = true;
						regVarTuple = RegistreAllocation.spillInit(e.ids.get(i));
						retour += RegistreAllocation.spillStart(regVarTuple);
					}
					((Tuple)(e.e1)).es.get(i).registreDeRetour = regVarTuple;
					
					if (isSpill){
						RegistreAllocation.spillEnd(regVarTuple);
						isSpill=false;
					}
				}
			}
			retour += e.e1.accept(this);
		}else if (e.e1 instanceof Var){
			String regVar = RegistreAllocation.getRegistre(((Var)(e.e1)).id); 
			if (regVar == null) {
				isSpill = true;
				regVar = RegistreAllocation.spillInit(((Var)(e.e1)).id);
				retour += RegistreAllocation.spillStart(regVar);
			}
			retour+=String.format("\tmov\t%s,%s\n",regVar,e.e1.registreDeRetour);
			if (isSpill){
				RegistreAllocation.spillEnd(regVar);
			}	
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
		}else if (e.e2 instanceof Int && ((Int)(e.e2)).i > 121){
			e.e2.registreDeRetour = e.registreDeRetour ; 
			retour += String.format("\tldr\t%s,=%d\n",e.registreDeRetour,((Int)(e.e2)).i);
		} else if (e.e2 instanceof App){
			retour += e.e2.accept(this);
		}else if (e.e2 instanceof Let){
			retour += e.e2.accept(this);
		} else {
			retour += e.e2.accept(this);
		}
		return retour;
	}

	@Override
	public String visit(Array e) {
		String retour="";
		cmpTab++;
		String defTab ="";
		boolean isSpill = false;

		if (e.e1 instanceof Int){
			int tailleT = ((Int)(e.e1)).i;

			defTab+=String.format("\tldr\t%s,addr_tab%d\n\tmov\tr0,%s\n",e.registreDeRetour,cmpTab,e.registreDeRetour);
			defTab+=String.format("\tmov\tr1,#%d\t@lenght of the array\n", tailleT);

			defVar+=String.format("array%d:\t.skip %d\n",cmpTab,tailleT *100);
			defFunc+=String.format("addr_tab%d:\t.word array%d\n",cmpTab,cmpTab);

			if (e.e2 instanceof Float){
				e.e2.registreDeRetour = "r2";
				retour+=defTab+e.e2.accept(this);
				retour+="\tbl\tmin_caml_create_float_array\n";

			}else if(e.e2 instanceof Tuple){	
				retour+=e.e2.accept(this);	
				retour+=String.format("\tmov\t%s,%s\n", e.registreDeRetour,e.e2.registreDeRetour);
			}else if(e.e2 instanceof Array){
				retour+=e.e2.accept(this);
				retour+=String.format("\tmov\tr2,%s\n%s\n",((Array)(e.e2)).e2.registreDeRetour,defTab);
				retour+="\tbl\tmin_caml_create_array\n";
			}else if (e.e2 instanceof Var){
				String regVar = RegistreAllocation.getRegistre(((Var)(e.e2)).id); 
				if (regVar == null) {
					isSpill = true;
					regVar = RegistreAllocation.spillInit(((Var)(e.e2)).id);
					retour += RegistreAllocation.spillStart(regVar);
				}
				retour+=defTab;
				retour+=String.format("\tmov\tr2,%s\n",regVar);							
				retour+="\tbl\tmin_caml_create_array\n";

				if (isSpill){
					RegistreAllocation.spillEnd(regVar);
				}


			}else{
				retour+=defTab;	
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
		String retour = "";
		boolean isSpill = false;

		if (e.e2 instanceof Int){
			retour+=String.format("\tmov\tr10,%s\n",e.e2.accept(this));
		}else if (e.e2 instanceof Var){
			String regVar2 = RegistreAllocation.getRegistre(((Var)(e.e2)).id); 
			if (regVar2 == null) {
				isSpill = true;
				regVar2 = RegistreAllocation.spillInit(((Var)(e.e2)).id);
				retour += RegistreAllocation.spillStart(regVar2);
			}
			retour+=String.format("\tmov\tr10,%s\n",regVar2);
		}else{
			System.err.println("internal error - Get (GenerationS)");
			System.exit(1);
		}
		if (e.e1 instanceof Var){
			String regVar = RegistreAllocation.getRegistre(((Var)(e.e1)).id); 
			if (regVar == null) {
				isSpill = true;
				regVar = RegistreAllocation.spillInit(((Var)(e.e1)).id);
				retour += RegistreAllocation.spillStart(regVar);
			}
			retour+=String.format("\tldr\t%s,[%s,r10,LSL #2]\n",e.registreDeRetour,regVar);
			
			if (isSpill){
				RegistreAllocation.spillEnd(regVar);
			}
		} else if (e.e1 instanceof Get){
			retour += e.e1.accept(this);
			retour+=String.format("\tldr\t%s,[%s,r10,LSL #2]\n",e.registreDeRetour,e.e1.registreDeRetour);
		}	
		
		return retour;
	}

	@Override
	public String visit(Put e) {
		String retour = "";
		boolean isSpill = false;
		boolean isSpill2 = false;



		if (e.e2 instanceof Int){
			retour+=String.format("\tmov\tr10,%s\n",e.e2.accept(this));
		}else if (e.e2 instanceof Var){
			String regVar2 = RegistreAllocation.getRegistre(((Var)(e.e1)).id); 
			if (regVar2 == null) {
				isSpill = true;
				regVar2 = RegistreAllocation.spillInit(((Var)(e.e1)).id);
				retour += RegistreAllocation.spillStart(regVar2);
			}
			retour+=String.format("\tmov\tr10,%s\n",regVar2);
		}else{
			System.err.println("internal error - Put (GenerationS)");
			System.exit(1);
		}	

		if (e.e1 instanceof Var){
			String regVar = RegistreAllocation.getRegistre(((Var)(e.e1)).id); 
			if (regVar == null) {
				isSpill = true;
				regVar = RegistreAllocation.spillInit(((Var)(e.e1)).id);
				retour += RegistreAllocation.spillStart(regVar);
			}
			if (e.e3 instanceof Var){
				String regVar3 = RegistreAllocation.getRegistre(((Var)(e.e3)).id);
				if (regVar3 == null) {
					isSpill2 = true;
					regVar3 = RegistreAllocation.spillInit(((Var)(e.e1)).id);
					retour += RegistreAllocation.spillStart(regVar3);
				}
				retour+=String.format("\tstr\t%s,[%s,r10,LSL #2]\n",regVar3,regVar);					
				if (isSpill2){
					RegistreAllocation.spillEnd(regVar3);
				}
			} else if (e.e3 instanceof Array){
				e.e3.registreDeRetour = "r11";
				retour+=e.e3.accept(this);
				retour+=String.format("\tstr\tr11,[%s,r10,LSL #2]\n",regVar);
			}else{
				retour+=String.format("\tmov\tr11,%s\n",e.e3.accept(this));
				retour+=String.format("\tstr\tr11,[%s,r10,LSL #2]\n",e.e1.registreDeRetour);
			}
			if (isSpill){
				RegistreAllocation.spillEnd(regVar);
			}
		} else if (e.e1 instanceof Put){
			retour += e.e1.accept(this);
			retour+=String.format("\tstr\t%s,[%s,r10,LSL #2]\n",e.registreDeRetour,e.e1.registreDeRetour);
		}	
		return retour;
	}

	@Override
	public String visit(Unit unit) {
		return null;
	}	

	private String epilogue() {
		//return "\tadd\tr13,r10,#0\n\tldr\tr10,[r13]\n\tadd\tr13,r13,#4";
		return "\tnop\n\tldmfd\tsp!, {r4-r9, pc}\n";
	}

	private String prologue(int nbVL) {
		//return String.format("\tadd\tr13,r13,#-4\n\tstr\tr10,[r13]\n\tadd\tr10,r13,#0\n\tadd\tr13,r13,#-%d @taille des variables locales\n", 4*nbVL);
		return "\tstmfd\tsp!, {r4-r9, lr}\n";
	}
}
