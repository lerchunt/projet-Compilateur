import java.util.*;

public class GenerationASML implements ObjVisitor<String> {
	static String asml;
	static int cp ; 
	private static boolean def = false ; 
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
    		return String.format("%s",e.b);
    	}
    }

    public String visit(Int e) {
    	if(!def){
    		GenerationASML.asml += e.i ;
    		return GenerationASML.asml ;
    	} else {
    		return String.format("%s",e.i);
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
    		return String.format("not %s",e.e.accept(this));
    	}
    }

    public String visit(Neg e) {
    	if(!def){
    		GenerationASML.asml += "-";
            e.e.accept(this);
    		return GenerationASML.asml;
    	} else {
    		return String.format("- %s",e.e.accept(this));
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
    		return String.format("add %s %s",e.e1.accept(this),e.e2.accept(this));
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
    		return String.format("sub %s %s",e.e1.accept(this),e.e2.accept(this));
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
    		return String.format("fadd %s %s",e.e1.accept(this),e.e2.accept(this));
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
    		return String.format("fsub %s %s",e.e1.accept(this),e.e2.accept(this));
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
    		return String.format("fmul %s %s",e.e1.accept(this),e.e2.accept(this));
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
    		return String.format("mul %s %s",e.e1.accept(this),e.e2.accept(this));
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
    		return String.format("fdiv %s %s",e.e1.accept(this),e.e2.accept(this));
    	}
    }

    public String visit(Eq e){ 
		if(!def)
    	{
			if(e.e1.isFloat()){
    			String haut = "let _z" + cp++ + " = " + e.e1.accept(this)+ "\n";
    			haut += "let _z" + cp++ + " = " + e.e2.accept(this)+ "\n";
    			GenerationASML.asml = haut + GenerationASML.asml ; 
    			GenerationASML.asml += "_z" + cp-- +  " = " + "_z" +cp++ ;
    		}
			e.e1.accept(this);
	        GenerationASML.asml += " = ";
	        e.e2.accept(this);
    		return GenerationASML.asml;
    	} else {
    		return String.format("%s = %s",e.e1.accept(this),e.e2.accept(this));
    	}

    }

    public String visit(LE e){
    	if(!def)
    	{
    		if(e.e1.isFloat()){
    			String haut = "let _z" + cp++ + " = " + e.e1.accept(this)+ "\n";
    			haut += "let _z" + cp++ + " = " + e.e2.accept(this)+ "\n";
    			GenerationASML.asml = haut + GenerationASML.asml ; 
    			GenerationASML.asml += "_z" + cp-- +  " <= " + "_z" +cp++ ;
    		} else {
    			e.e1.accept(this);
    	        GenerationASML.asml += " <= ";
    	        e.e2.accept(this);
    		}
    		return GenerationASML.asml;
    	} else {
    		return String.format("%s <= %s",e.e1.accept(this),e.e2.accept(this));

    	}
    }

    public String visit(If e){
    	if(!def)
    	{
    		if (e.e2.isFloat()){
    			String haut = "let _z" + cp++  ;
    			String s = e.e2.accept(this);
                haut += " = " + s + "\n";
                haut += "let _z" + cp  ;
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
                GenerationASML.asml += " then (\n\t\t";
                e.e2.accept(this);
                if(!e.e2.isVIFB()){
                    GenerationASML.asml += " \n\t";
                }
                GenerationASML.asml += ") else (\n\t\t";
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
                txt += " then ( \n\t\t_z" + cp++ + "\n\t";
                txt += ") else (\n\t\t _z" + cp++ +" \n\t) ";
        		return txt ;
    		}else {
    			
        		String retour = String.format("\n\tif %s then ( \n\t%s ",e.e1.accept(this),e.e2.accept(this));
;	    		if(!e.e2.isVIFB()){
	                retour += " \n\t";
	            }
				retour += String.format(") else (\n\t%s ",e.e3.accept(this));
	    		if(!e.e3.isVIFB()){
	                retour += " \n\t)";
	            } else {
	            	retour += ") ";
	            }
	    		return retour;
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
                if(e.e1.isVIFB() || e.e1 instanceof If){
                	GenerationASML.asml += "\n\t";
                }
                e.e1.accept(this);
                GenerationASML.asml += " in ";
                if(!e.e2.isVIFB() && !(e.e2 instanceof App))
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
                    txt += " \n";
                } else {
                    txt += " \n";
                	e.e2.accept(this);
                }
            } else {
            	String retour = String.format("let %s = %s in", e.id.id,e.e1.accept(this));
                if(!e.e2.isVIFB())
                {
                	retour += e.e2.accept(this)+ " \n";
                } else {
                    retour += " \n" + e.e2.accept(this);
                }
                return retour;
            } 
    		return txt;
        }

    }
        

    public String visit(Var e){
    	if(!def){
    		GenerationASML.asml += e.id;
    		return GenerationASML.asml;
    	} else {
    		return e.id.id;
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
	  /*if(e.e.isVIFB()) 
	  {
		  haut += e.e.accept(this);
	  } */
	  def = false ;
      GenerationASML.asml = haut + GenerationASML.asml;
      e.e.accept(this);
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
        	String retour = String.format("call _min_caml_ %s", e.e.accept(this));
            retour += printInfix(e.es, " ");
    		return retour;
    	}
    }

    public String visit(Tuple e){
    	if(!def){
	    	GenerationASML.asml += " (" +printInfix(e.es, ", ") + ")";
	    	return  GenerationASML.asml ;
    	} else {
    		return " (" +printInfix(e.es, ", ") + ")";
    	}
    }

    public String visit(LetTuple e){
    	String retour = e.e1.accept(this);
    	retour += e.e2.accept(this);
		return retour;

    }

    public String visit(Array e){
    	if(!def){
    		GenerationASML.asml += "call _min_caml_create_array ";
    		e.e1.accept(this);
    		e.e2.accept(this) ;
    		return GenerationASML.asml;
    	} else {
    		return String.format("call _min_caml_create_array %s %s", e.e1.accept(this), e.e2.accept(this));
    	}
    }

    public String visit(Get e){
    	if(!def){
	    	GenerationASML.asml += "mem(";
			e.e1.accept(this);
			GenerationASML.asml += " + " ;
			e.e2.accept(this) ;
			return GenerationASML.asml;
		} else {
    		return String.format("mem(%s + %s)", e.e1.accept(this), e.e2.accept(this));
		}
    }

    public String visit(Put e){
    	if(!def){
	    	GenerationASML.asml += "mem(";
			e.e1.accept(this);
			GenerationASML.asml += " + " ;
			e.e2.accept(this) ;
			GenerationASML.asml += ") <- " ;
			e.e3.accept(this);
			return GenerationASML.asml;
		} else {
    		return String.format("mem(%s + %s) <- %s", e.e1.accept(this), e.e2.accept(this),e.e3.accept(this));

		}
    }
}
