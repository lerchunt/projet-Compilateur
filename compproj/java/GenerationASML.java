import java.util.*;

public class GenerationASML implements ObjVisitor<String> {
	static String asml;
	static int cp ; 
	static boolean def = false ; 
	public GenerationASML()
	{
		GenerationASML.asml = "\nlet _ = \n\t";
		cp = 0 ;
	}
    public String visit(Unit e) {
		return GenerationASML.asml;
    }

    public String visit(Bool e) {
    	if(!def){
    		GenerationASML.asml += e.b ;
    		return GenerationASML.asml ;
    	} else {
    		String txt = "" ;
        	txt += e.b ;
    		return txt;
    	}
    }

    public String visit(Int e) {
    	if(!def){
    		GenerationASML.asml += e.i ;
    		return GenerationASML.asml ;
    	} else {
    		String txt = "" ;
        	txt += e.i ;
    		return txt;
    	}	
    }

    public String visit(Float e) {
    	String s = String.format("%.2f", e.f);
    	s=s.replace(',','.');
		return s;

    }

    public String visit(Not e) {
    	if(!def){
    		GenerationASML.asml += "not ";
            e.e.accept(this);
    		return GenerationASML.asml;
    	} else {
    		String txt = "not " ; 
    		txt += e.e.accept(this);
    		return txt ;
    	}
    }

    public String visit(Neg e) {
    	if(!def){
    		GenerationASML.asml += "-";
            e.e.accept(this);
    		return GenerationASML.asml;
    	} else {
    		String txt = "-";
            txt += e.e.accept(this);
    		return txt;
    	}
    }

    public String visit(Add e) {
    	if(!def)
    	{
    		GenerationASML.asml += " add ";
    		e.e1.accept(this);
    		GenerationASML.asml += " ";
    		e.e2.accept(this);
    		return GenerationASML.asml;
    	} else {
    		String txt = " add ";
    		txt += e.e1.accept(this) + " ";
    		txt += e.e2.accept(this) + "\n";
    		return txt;
    	} 	
    }

    public String visit(Sub e) {
    	if(!def)
    	{
    		GenerationASML.asml += " sub ";
    		e.e1.accept(this);
    		GenerationASML.asml += " ";
    		e.e2.accept(this);
    		return GenerationASML.asml;
    	} else {
    		String txt = " sub ";
    		txt += e.e1.accept(this) + " ";
    		txt += e.e2.accept(this);
    		return txt;
    	}

    }

    public String visit(FNeg e){
		return GenerationASML.asml;

    }

    public String visit(FAdd e){
    	if(!def)
    	{
    		GenerationASML.asml += " fadd ";
    		e.e1.accept(this);
    		GenerationASML.asml += " ";
    		e.e2.accept(this);
    		return GenerationASML.asml;
    	} else {
    		String txt = " fadd ";
    		txt += e.e1.accept(this) + " ";
    		txt += e.e2.accept(this);
    		return txt;
    	}
    }

    public String visit(FSub e){
    	if(!def)
    	{
    		GenerationASML.asml += " fsub ";
    		e.e1.accept(this);
    		GenerationASML.asml += " ";
    		e.e2.accept(this);
    		return GenerationASML.asml;
    	} else {
    		String txt = " fsub ";
    		txt += e.e1.accept(this) + " ";
    		txt += e.e2.accept(this);
    		return txt;
    	}

    }

    public String visit(FMul e) {
    	if(!def)
    	{
    		GenerationASML.asml += " fmul ";
    		e.e1.accept(this);
    		GenerationASML.asml += " ";
    		e.e2.accept(this);
    		return GenerationASML.asml;
    	} else {
    		String txt = " fmul ";
    		txt += e.e1.accept(this) + " ";
    		txt += e.e2.accept(this);
    		return txt;
    	}

    }
    
    public String visit(Mul e) {
    	if(!def)
    	{
    		GenerationASML.asml += " mul ";
    		e.e1.accept(this);
    		GenerationASML.asml += " ";
    		e.e2.accept(this);
    		return GenerationASML.asml;
    	} else {
    		String txt = " mul ";
    		txt += e.e1.accept(this) + " ";
    		txt += e.e2.accept(this);
    		return txt;
    	}
    }

    public String visit(FDiv e){
    	if(!def)
    	{
    		GenerationASML.asml += " fdiv ";
    		e.e1.accept(this);
    		GenerationASML.asml += " ";
    		e.e2.accept(this);
    		return GenerationASML.asml;
    	} else {
    		String txt = " fdiv ";
    		txt += e.e1.accept(this) + " ";
    		txt += e.e2.accept(this);
    		return txt;
    	}
    }

    public String visit(Eq e){ 
		if(!def)
    	{
			e.e1.accept(this);
	        GenerationASML.asml += " = ";
	        e.e2.accept(this);
    		return GenerationASML.asml;
    	} else {
    		String txt = " ";
    		txt += e.e1.accept(this) + " = ";
    		txt += e.e2.accept(this);
    		return txt;
    	}

    }

    public String visit(LE e){
    	if(!def)
    	{
			e.e1.accept(this);
	        GenerationASML.asml += " <= ";
	        e.e2.accept(this);
    		return GenerationASML.asml;
    	} else {
    		String txt = " ";
    		txt += e.e1.accept(this) + " <= ";
    		txt += e.e2.accept(this);
    		return txt;
    	}
    }

    public String visit(If e){
    	if(!def)
    	{
    		if (e.e2.isFloat()){
    			String haut = "let_z" + cp++  ;
    			String s = e.e2.accept(this);
                haut += " = " + s + "\n";
                haut += "let_z" + cp  ;
    			s = e.e3.accept(this);
                haut += " = " + s + "\n";
                GenerationASML.asml = haut + GenerationASML.asml ;
    			GenerationASML.asml += "if " ;
                e.e1.accept(this);
                cp--;
                GenerationASML.asml += " then ( \n\t_z" + cp++ + "\n\t";
                GenerationASML.asml += ") else (\n\t _z" + cp++ +" \n\t) ";
        		return GenerationASML.asml;
    		}else
    		{
    			GenerationASML.asml += "if ";
                e.e1.accept(this);
                GenerationASML.asml += " then (\n\t";
                e.e2.accept(this);
                if(!e.e2.isVIFB()){
                    GenerationASML.asml += " \n\t";
                }
                GenerationASML.asml += ") else (\n\t";
                e.e3.accept(this);
                if(!e.e3.isVIFB()){
                    GenerationASML.asml += " \n\t)";
                } else {
                	GenerationASML.asml += ") ";
                }
        		return GenerationASML.asml;
    		}
    	} else {
    		if (e.e2.isFloat()){
    			String haut = "let_z" + cp++  ;
    			String s = e.e2.accept(this);
                haut += " = " + s + "\n";
                haut += "let_z" + cp  ;
    			s = e.e3.accept(this);
                haut += " = " + s + "\n";
                String txt = haut + GenerationASML.asml ;
    			txt += "if " ;
                e.e1.accept(this);
                cp-- ;
                txt += " then ( \n\t_z" + cp++ + "\n\t";
                txt += ") else (\n\t _z" + cp++ +" \n\t) ";
        		return txt ;
    		}else {
	    		String txt = "if ";
	    		txt += e.e1.accept(this) + " then ( \n\t";
	    		txt += e.e2.accept(this) ;
	    		if(!e.e2.isVIFB()){
	                txt += " \n\t";
	            }
	    		txt += " ) else (\n\t ";
	    		txt += e.e3.accept(this) ;
	    		if(!e.e3.isVIFB()){
	                txt += " \n\t)";
	            } else {
	            	txt += ") ";
	            }
	    		return txt;
    		}
    	}
    	
    }

    public String visit(Let e) {
    	String haut ="";
        if(!def){
        	if((e.e1).isFloat())
            {
            	haut = "let _" +e.id;
            	String s = e.e1.accept(this);
                haut += " = " + s + "\n";
                GenerationASML.asml = haut + GenerationASML.asml ;
                GenerationASML.asml += "let ";
            	GenerationASML.asml += e.id;
                GenerationASML.asml += " = _" + e.id;
                GenerationASML.asml += " in ";
                if(!e.e2.isVIFB())
                {
                	e.e2.accept(this);
                    GenerationASML.asml += "\n\t";
                } else {
                    GenerationASML.asml += "\n\t";
                	e.e2.accept(this);
                }
            } else {
            	GenerationASML.asml += "let ";
            	GenerationASML.asml += e.id;
                GenerationASML.asml += " = ";
                if(!e.e1.isVIFB()){
                	GenerationASML.asml += "\n\t";
                }
                e.e1.accept(this);
                GenerationASML.asml += " in ";
                if(!e.e2.isVIFB())
                {
                	e.e2.accept(this);
                    GenerationASML.asml += " \n\t";
                } else {
                    GenerationASML.asml += " \n\t";
                	e.e2.accept(this);
                }
            } 
    		return GenerationASML.asml;
        } else {
        	String txt = "";
        	if((e.e1).isFloat())
            {
            	haut = "let _" +e.id;
            	String s = e.e1.accept(this);
                haut += " = " + s + "\n";
                txt = haut ;
                txt += "let "+ e.id;
                txt += " = _" + e.id;
                txt += " in " ;
                if(e.e2.isVar())
                {
                	e.e2.accept(this);
                    txt += " \n\t";
                } else {
                    txt += " \n\t";
                	e.e2.accept(this);
                }
            } else {
            	txt += "let " + e.id +" = ";
                txt += e.e1.accept(this);
                txt += " in " ;
                if(!e.e2.isVIFB())
                {
                	txt += e.e2.accept(this);
                    txt += " \n\t";
                } else {
                    txt += " \n\t";
                	txt += e.e2.accept(this);
                }
            } 
    		return txt;
        }

    }
        

    public String visit(Var e){
    	if(!def){
    		GenerationASML.asml += e.id;
    		return GenerationASML.asml;
    	} else {
    		String txt = "" ;
    		txt += e.id;
    		return txt;
    	}
    }


    // print sequence of identifiers 
    static <E> String printInfix(List<E> l, String op) {
        if (l.isEmpty()) {
            return "";
        }
        String t = "" ;
        Iterator<E> it = l.iterator();
        t += it.next();
        while (it.hasNext()) {
            t += op + it.next();
        }
        return t ;
        
    }

    // print sequence of Exp
    void printInfix2(List<Exp> l, String op) {
        if (l.isEmpty()) {
            return;
        }
        Iterator<Exp> it = l.iterator();
        it.next().accept(this);
        while (it.hasNext()) {
            GenerationASML.asml += op;
            it.next().accept(this);
        }
    }

    public String visit(LetRec e){
      String haut = "let _" + e.fd.id ;
	  haut += " " +printInfix(e.fd.args, " ");
      haut += " = \n";
      haut += "\tlet " +  printInfix(e.fd.args, " ") + " = ";
      if(e.fd.e.isVIFB())
      {
    	  haut += "\n\t" ;
      }
      def = true ; 
	  haut += e.fd.e.accept(this);
	  if(!e.e.isVIFB()) 
	  {
		  haut += e.e.accept(this);
	  }
	  def = false ;
      GenerationASML.asml = haut + GenerationASML.asml;
      return GenerationASML.asml;
    }

    public String visit(App e){
    	if(!def){
    		GenerationASML.asml += "call _min_caml_";
            e.e.accept(this);
            GenerationASML.asml += " ";
            printInfix2(e.es, " ");
    		return GenerationASML.asml;
    	} else {
    		String txt = "call _min_caml_";
            txt += e.e.accept(this) + " ";
            txt += printInfix(e.es, " ");
    		return txt;
    	}
    }

    public String visit(Tuple e){
		return GenerationASML.asml;

    }

    public String visit(LetTuple e){
		return GenerationASML.asml;

    }

    public String visit(Array e){
		return GenerationASML.asml;

    }

    public String visit(Get e){
		return GenerationASML.asml;

    }

    public String visit(Put e){
		return GenerationASML.asml;

    }
	@Override
	public String visit(FunDef e) {
		return GenerationASML.asml;
	}
}
