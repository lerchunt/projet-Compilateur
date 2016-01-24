import java.util.*;

public class GenerationASML implements ObjVisitor<String> {
	static String asml;
	static String haut ="";
	static String inter ="\nlet _ = \n\t";
	static int cp ; 
	private static int var = 0 ;
	private static boolean def = false ; 
	private static boolean iff = false ;
	public GenerationASML()
	{
		cp = 0 ;
	}
	
	public String variable(int c){
		return String.format("gg%s", c);
	}
    public String visit(Unit e) {
		return e.toString();
    }

    public String visit(Bool e) {
    	if(e.b == false) {
        	return String.format("%s",0);
    	}
    	return String.format("%s",1);
    }

    public String visit(Int e) {
    	return String.format("%s",e.i);
    }

    public String visit(Float e) {
    	String s = String.format("%.2f", e.f);
    	s=s.replace(',','.');
		return s;

    }

    public String visit(Not e) {
    	return String.format("not %s",e.e.accept(this));
    }

    public String visit(Neg e) {
    	return String.format("neg%s",e.e.accept(this));
    }

    public String visit(Add e) {
    	String retour ="";
    	if(!def)
    	{
    		if(e.e1 instanceof Var){
    			if(e.e2 instanceof Var){
    				retour += String.format("add %s %s",e.e1.accept(this), e.e2.accept(this));
    			} else {
    				GenerationASML.inter += String.format("\n\tlet %s = %s in ",variable(var),e.e2.accept(this));
    				retour += String.format("add %s %s", variable(var),e.e1.accept(this));
    				var++;
    			}
    		} else {
    			if(e.e2 instanceof Var){
    				GenerationASML.inter += String.format("\n\tlet %s = %s in ",variable(var),e.e1.accept(this));
    				retour += String.format("add %s %s", variable(var),e.e2.accept(this));
    				var++;
    			} else {
    				String v1=variable(var);
        			var++;
    				GenerationASML.inter += String.format("\n\tlet %s = %s in \n\tlet %s = %s in ",v1,e.e2.accept(this), variable(var),e.e1.accept(this));
    				retour += String.format("add %s %s",v1,variable(var));
    				var++;
    			}
    		}
    		return retour ;
    	} else {
    		if(e.e1 instanceof Int) {
    			if(e.e2 instanceof Var){
    	    		return String.format("add %s %s",e.e2.accept(this),e.e1.accept(this));
    			} 
    		}
    		return String.format("add %s %s",e.e1.accept(this),e.e2.accept(this));
    	}
    }
    
    public String visit(Sub e) {
    	String retour ="";
    	if(!def)
    	{
    		if(e.e1 instanceof Var){
    			if(e.e2 instanceof Var){
    				retour += String.format("sub %s %s",e.e1.accept(this), e.e2.accept(this));
    			} else {
    				GenerationASML.inter += String.format("\n\tlet %s = %s in ",variable(var),e.e2.accept(this));
    				retour += String.format("sub %s %s", variable(var),e.e1.accept(this));
    				var++;
    			}
    		} else {
    			if(e.e2 instanceof Var){
    				GenerationASML.inter += String.format("\n\tlet %s = %s in ",variable(var),e.e1.accept(this));
    				retour += String.format("sub %s %s", variable(var),e.e2.accept(this));
    				var++;
    			} else {
    				String v1=variable(var);
        			var++;
    				GenerationASML.inter += String.format("\n\tlet %s = %s in \n\tlet %s = %s in ",v1,e.e2.accept(this), variable(var),e.e1.accept(this));
    				retour += String.format("sub %s %s",v1,variable(var));
    				var++;
    			}
    		}
    		return retour ;
    	} else {
    		if(e.e1 instanceof Int) {
    			if(e.e2 instanceof Var){
    	    		return String.format("sub %s %s",e.e2.accept(this),e.e1.accept(this));
    			} 
    		}
    		return String.format("sub %s %s",e.e1.accept(this),e.e2.accept(this));
    	}
    }

    public String visit(FNeg e){
    	return String.format("neg%s",e.e.accept(this));

    }

    public String visit(FAdd e){
    	String retour ="";
    	if(!def)
    	{
    		if(e.e1 instanceof Var){
    			if(e.e2 instanceof Var){
    				retour += String.format("fadd %s %s",e.e1.accept(this), e.e2.accept(this));
    			} else {
    				GenerationASML.haut += String.format("\nlet _%s = %s",variable(var),e.e2.accept(this));
    				retour += String.format("fadd _%s %s", variable(var),e.e1.accept(this));
    				var++;
    			}
    		} else {
    			if(e.e2 instanceof Var){
    				GenerationASML.haut += String.format("\nlet _%s = %s",variable(var),e.e1.accept(this));
    				retour += String.format("fadd _%s %s", variable(var),e.e2.accept(this));
    				var++;
    			} else {
    				String v1=variable(var);
        			var++;
    				GenerationASML.haut += String.format("\nlet _%s = %s \n\tlet _%s = %s",v1,e.e2.accept(this), variable(var),e.e1.accept(this));
    				retour += String.format("fadd _%s _%s",v1,variable(var));
    				var++;
    			}
    		}
    		return retour ;
    	} else {
    		return String.format("fadd %s %s",e.e1.accept(this),e.e2.accept(this));
    	}
    }

    public String visit(FSub e){
    	String retour ="";
    	if(!def)
    	{
    		if(e.e1 instanceof Var){
    			if(e.e2 instanceof Var){
    				retour += String.format("fsub %s %s",e.e1.accept(this), e.e2.accept(this));
    			} else {
    				GenerationASML.haut += String.format("\nlet _%s = %s",variable(var),e.e2.accept(this));
    				retour += String.format("fsub _%s %s", variable(var),e.e1.accept(this));
    				var++;
    			}
    		} else {
    			if(e.e2 instanceof Var){
    				GenerationASML.haut += String.format("\nlet _%s = %s",variable(var),e.e1.accept(this));
    				retour += String.format("fsub _%s %s", variable(var),e.e2.accept(this));
    				var++;
    			} else {
    				String v1=variable(var);
        			var++;
    				GenerationASML.haut += String.format("\nlet _%s = %s \n\tlet _%s = %s",v1,e.e2.accept(this), variable(var),e.e1.accept(this));
    				retour += String.format("fsub _%s _%s",v1,variable(var));
    				var++;
    			}
    		}
    		return retour ;
    	} else {
    		return String.format("fsub %s %s",e.e1.accept(this),e.e2.accept(this));
    	}
    }

    public String visit(FMul e) {
    	String retour ="";
    	if(!def)
    	{
    		if(e.e1 instanceof Var){
    			if(e.e2 instanceof Var){
    				retour += String.format("fmul %s %s",e.e1.accept(this), e.e2.accept(this));
    			} else {
    				GenerationASML.haut += String.format("\nlet _%s = %s",variable(var),e.e2.accept(this));
    				retour += String.format("fmul _%s %s", variable(var),e.e1.accept(this));
    				var++;
    			}
    		} else {
    			if(e.e2 instanceof Var){
    				GenerationASML.haut += String.format("\nlet _%s = %s",variable(var),e.e1.accept(this));
    				retour += String.format("fmul _%s %s", variable(var),e.e2.accept(this));
    				var++;
    			} else {
    				String v1=variable(var);
        			var++;
    				GenerationASML.haut += String.format("\nlet _%s = %s \n\tlet _%s = %s",v1,e.e2.accept(this), variable(var),e.e1.accept(this));
    				retour += String.format("fmul _%s _%s",v1,variable(var));
    				var++;
    			}
    		}
    		return retour ;
    	} else {
    		return String.format("fmul %s %s",e.e1.accept(this),e.e2.accept(this));
    	}
    }
    
    public String visit(Mul e) {
    	String retour ="";
    	if(!def)
    	{
    		if(e.e1 instanceof Var){
    			if(e.e2 instanceof Var){
    				retour += String.format("mul %s %s",e.e1.accept(this), e.e2.accept(this));
    			} else {
    				GenerationASML.inter += String.format("\n\tlet %s = %s in ",variable(var),e.e2.accept(this));
    				retour += String.format("mul %s %s", variable(var),e.e1.accept(this));
    				var++;
    			}
    		} else {
    			if(e.e2 instanceof Var){
    				GenerationASML.inter += String.format("\n\tlet %s = %s in ",variable(var),e.e1.accept(this));
    				retour += String.format("mul %s %s", variable(var),e.e2.accept(this));
    				var++;
    			} else {
    				String v1=variable(var);
        			var++;
    				GenerationASML.inter += String.format("\n\tlet %s = %s in \n\tlet %s = %s in ",v1,e.e2.accept(this), variable(var),e.e1.accept(this));
    				retour += String.format("mul %s %s",v1,variable(var));
    				var++;
    			}
    		}
    		return retour ;
    	} else {
    		return String.format("mul %s %s",e.e1.accept(this),e.e2.accept(this));
    	}
    }

    public String visit(FDiv e){
    	String retour ="";
    	if(!def)
    	{
    		if(e.e1 instanceof Var){
    			if(e.e2 instanceof Var){
    				retour += String.format("fdiv %s %s",e.e1.accept(this), e.e2.accept(this));
    			} else {
    				GenerationASML.haut += String.format("\nlet _%s = %s",variable(var),e.e2.accept(this));
    				retour += String.format("fdiv _%s %s", variable(var),e.e1.accept(this));
    				var++;
    			}
    		} else {
    			if(e.e2 instanceof Var){
    				GenerationASML.haut += String.format("\nlet _%s = %s",variable(var),e.e1.accept(this));
    				retour += String.format("fdiv _%s %s", variable(var),e.e2.accept(this));
    				var++;
    			} else {
    				String v1=variable(var);
        			var++;
    				GenerationASML.haut += String.format("\nlet _%s = %s \n\tlet _%s = %s",v1,e.e2.accept(this), variable(var),e.e1.accept(this));
    				retour += String.format("fdiv _%s _%s",v1,variable(var));
    				var++;
    			}
    		}
    		return retour ;
    	} else {
    		return String.format("fdiv %s %s",e.e1.accept(this),e.e2.accept(this));
    	}
    }

    public String visit(Eq e){ 
    	String retour ="";
		if(!def)
    	{
			if(e.e1.isFloat()){
    			GenerationASML.haut = String.format("\nlet _z%s = %s \nlet _z%s = %s",cp++,e.e1.accept(this),cp++,e.e2.accept(this));
    			retour += String.format("_z%s = _z%s", cp--,cp++);
    		} else if(e.e1 instanceof Bool || e.e1 instanceof Int) {
    			String v = variable(var) ;
    			var++;
				GenerationASML.inter += String.format("\n\tlet %s = %s in \n\t let %s = %s in ",v,e.e1.accept(this),variable(var),e.e2.accept(this));
        		retour += String.format("%s = %s", v,variable(var));
        		var++;
    		} else {
    			retour += String.format("%s = %s", e.e1.accept(this),e.e2.accept(this));
    		}
    		return retour;
    	} else {
    		return String.format("%s = %s",e.e1.accept(this),e.e2.accept(this));
    	}

    }

    public String visit(LE e){
    	String retour ="";
		if(!def)
    	{
			if(e.e1.isFloat()){
    			GenerationASML.haut = String.format("\nlet _z%s = %s \nlet _z%s = %s",cp++,e.e1.accept(this),cp++,e.e2.accept(this));
    			retour += String.format("_z%s <= _z%s", cp--,cp++);
    		} else if(e.e1 instanceof Bool || e.e1 instanceof Int) {
    			String v = variable(var) ;
    			var++;
				GenerationASML.inter += String.format("\n\tlet %s = %s in \n\t let %s = %s in ",v,e.e1.accept(this),variable(var),e.e2.accept(this));
        		retour += String.format("%s <= %s", v,variable(var));
        		var++;
    		} else {
    			retour += String.format("%s <= %s", e.e1.accept(this),e.e2.accept(this));
    		}
    		return retour;
    	} else {
    		return String.format("%s <= %s",e.e1.accept(this),e.e2.accept(this));
    	}

    }

    public String visit(If e){
    	String retour ="";
    	String haut ="";
    	if(!def)
    	{
    		if (e.e2.isFloat()){
    			GenerationASML.haut += String.format("let _z%s = %s \nlet _z%s = %s",cp++,e.e2.accept(this),cp,e.e3.accept(this));
                if(e.e1 instanceof Bool){
    				GenerationASML.inter += String.format("\n\tlet %s = %s in",variable(var),e.e1.accept(this));
                    retour += String.format("\n\tif %s = %s ",variable(var),variable(var++));
                } else {
                    retour += String.format("\n\tif %s ",e.e1.accept(this));
                }
                cp--;
                if(iff){
                	retour += String.format("then ( \n\t\tlet %s = _z%s in %s\n\t)else(\n\t\tlet %s = _z%s in %s\n\t)",asml,cp++,asml,asml,cp++,asml);

                } else {
                	retour += String.format("then ( \n\t\t_z%s \n\t)else(\n\t\t _z%s \n\t)",cp++,cp++);
                }
        		return retour;
    		} else {
    			if(e.e1 instanceof Bool){
	            	GenerationASML.inter += String.format("\n\tlet %s = %s in ",variable(var),e.e1.accept(this));
	                retour += String.format("\n\tif %s = %s ",variable(var),variable(var++));
    			} else {
                    retour += String.format("\n\tif %s ",e.e1.accept(this));
                }if(iff){
                	retour += String.format("then ( \n\t\tlet %s = %s in %s\n\t)else(\n\t\tlet %s = %s in %s\n\t)",asml,e.e2.accept(this),asml,asml,e.e3.accept(this),asml);
                } else {
                	retour += String.format("then ( \n\t\t%s \n\t)else(\n\t\t %s \n\t)",e.e2.accept(this),e.e3.accept(this));
                }
    		}
    		return retour ;
    	} else {
    		if (e.e2.isFloat()){
    			haut = "let_z" + cp++  ;
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
    			
        		retour = String.format("\n\tif %s then ( \n\t%s ",e.e1.accept(this),e.e2.accept(this));
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
    	String retour ="";
        if(!def){
        	if((e.e1).isFloat())
            {
        		GenerationASML.haut += String.format("let _%s = %s \n",e.id,e.e1.accept(this));
        		retour = String.format("\n\tlet %s = _%s in",e.id,e.id);
                if(e.e1.isVIFB() || e.e1 instanceof If){
                	retour += "\n\t";
                }
                retour += String.format("%s in ",e.e1.accept(this));
                if(!e.e2.isVIFB() && !(e.e2 instanceof App)){
                	retour += String.format("%s \n\t ",e.e2.accept(this));
                } else {
                	retour += String.format("\n\t %s",e.e2.accept(this));
                }
            } else if (e.e1 instanceof If) {
            	iff = true ; 
            	asml = e.id.id ; 
            	retour += String.format("%s in ",e.e1.accept(this));
                if(!e.e2.isVIFB() && !(e.e2 instanceof App)){
                	retour += String.format("%s \n\t ",e.e2.accept(this));
                } else {
                	retour += String.format("\n\t %s",e.e2.accept(this));
                }
            	iff = false ;
            }
        	else {
            	retour += String.format("let %s = ",e.id);
                if(e.e1.isVIFB() || e.e1 instanceof If){
                	retour += "\n\t";
                }
                retour += String.format("%s in ",e.e1.accept(this));
                if(!e.e2.isVIFB() && !(e.e2 instanceof App)){
                	retour += String.format("%s \n\t ",e.e2.accept(this));
                } else {
                	retour += String.format("\n\t %s",e.e2.accept(this));
                }
            } 
    		return retour;
        } else {
        	if((e.e1).isFloat())
            {
            	GenerationASML.haut += String.format("let _%s = %s \n", e.id,e.e1.accept(this)) ;
                retour += String.format("let %s = _%s in \n", e.id,e.id);
                if(e.e2.isVar()) {
                    retour += String.format("%s \n", e.e2.accept(this));
                } else {
                    retour += String.format("\n %s", e.e2.accept(this));
                }
            } else {
            	retour += String.format("let %s = %s in ", e.id.id,e.e1.accept(this));
                if(!e.e2.isVIFB())
                {
                    retour += String.format("%s \n", e.e2.accept(this));
                } else {
                    retour += String.format("\n %s", e.e2.accept(this));
                }
            } 
        }
        return retour;
    }
        

    public String visit(Var e){
    	e.id.id=e.id.id.replace('?','_');
    	return e.id.id;
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
    public String printInfix2(List<Exp> l, String op) {
    	String t ="";
    	for(Exp e : l){
        	if(e instanceof Var){
        		t += e ;        	
        	} else if(e instanceof App) {
        		String txt = e.accept(this);
        		GenerationASML.inter += String.format("\n\tlet %s = %s in ",variable(var),txt);
        		System.out.println();
        		t += variable(var);
        		var++;
        	} else {
        		GenerationASML.inter += String.format("\n\tlet %s = %s in ",variable(var),e.accept(this));
        		t += variable(var);
        		var++;
        	}
        }
        return t ;
    }

    public String visit(LetRec e){
    	GenerationASML.haut += String.format("let _%s %s = \n\t",e.fd.id,printInfix(e.fd.args, " "),printInfix(e.fd.args, " "));
        def = true ; 
        GenerationASML.haut += String.format ("%s",e.fd.e.accept(this));
	    def = false ;
	    String retour = e.e.accept(this);
        return retour;
    }

    public String visit(App e){
    	String retour ="";
    		if(e.e instanceof Var && (((Var)e.e).id.id.equals("print_int") || ((Var)e.e).id.id.equals("print_float") || ((Var)e.e).id.id.equals("truncate") || ((Var)e.e).id.id.equals("print_newline") || ((Var)e.e).id.id.equals("int_of_float") || ((Var)e.e).id.id.equals("float_of_int"))){
    			retour += String.format("call _min_caml_%s %s ",e.e.accept(this),printInfix2(e.es, " "));
    		} else {
    			retour += String.format("call _%s %s ",e.e.accept(this),printInfix2(e.es, " "));
    		}
    		return retour ;
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
