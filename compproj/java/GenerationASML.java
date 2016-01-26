import java.util.*;

public class GenerationASML implements ObjVisitor<String> {
	static String asml;
	static String haut ="";
	static String inter ="\nlet _ = \n\t";
	static int cp ; 
	private static int var = 0 ;
	private static boolean def = false ; 
	private static boolean not = false ; 
	private static boolean iff = false ;
	private static boolean app = false ;
	private static boolean opbin = false ;
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
    	return String.format("%s",e.e.accept(this));
    }

    public String visit(Neg e) {
    	String retour ="";
    	if(e.e instanceof Var){
        	retour += String.format("neg %s",e.e.accept(this));
    	} else {
    		GenerationASML.inter += String.format("\n\tlet %s = %s in ",variable(var),e.e.accept(this));
        	retour += String.format("neg %s",variable(var));
			var++;
    	}
    	return retour ;
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
    				retour += String.format("add %s %s ", variable(var),e.e1.accept(this));
    				var++;
    			}
    		} else {
    			if(e.e2 instanceof Var){
    				GenerationASML.inter += String.format("\n\tlet %s = %s in ",variable(var),e.e1.accept(this));
    				retour += String.format("add %s %s ", variable(var),e.e2.accept(this));
    				var++;
    			} else {
    				String v1=variable(var);
        			var++;
    				GenerationASML.inter += String.format("\n\tlet %s = %s in \n\tlet %s = %s in ",v1,e.e2.accept(this), variable(var),e.e1.accept(this));
    				retour += String.format("add %s %s ",v1,variable(var));
    				var++;
    			}
    		}
    		return retour ;
    	} else {
    		if(e.e1 instanceof Int) {
    			if(e.e2 instanceof Var){
    	    		return String.format("add %s %s ",e.e2.accept(this),e.e1.accept(this));
    			} else {
    				String v1=variable(var);
        			var++;
    				GenerationASML.haut += String.format("\n\tlet %s = %s in \n\tlet %s = %s in ",v1,e.e2.accept(this), variable(var),e.e1.accept(this));
    				retour += String.format("add %s %s ",v1,variable(var));
    				var++;
    			}
    		}
    		return String.format("add %s %s ",e.e1.accept(this),e.e2.accept(this));
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
    			} else {
    				String v1=variable(var);
        			var++;
    				GenerationASML.haut += String.format("\n\tlet %s = %s in \n\tlet %s = %s in ",v1,e.e2.accept(this), variable(var),e.e1.accept(this));
    				retour += String.format("sub %s %s",v1,variable(var));
    				var++;
    			}
    		}
    		return String.format("sub %s %s",e.e1.accept(this),e.e2.accept(this));
    	}
    }

    public String visit(FNeg e){
    	String retour ="";
    	if(e.e instanceof Var){
        	retour += String.format("fneg %s",e.e.accept(this));
    	} else {
    		String v1 = variable(var++);
    		GenerationASML.haut += String.format("\nlet _%s = %s ",v1,e.e.accept(this));
        	GenerationASML.inter += String.format("\n\tlet %s = _%s in \n\tlet %s = mem(%s +0) in",v1,v1,variable(var),v1);
    		retour += String.format("fneg %s",variable(var));
			var++;
    	}
    	return retour ;
    }


    public String visit(FAdd e){
    	String retour ="";
    	if(!def)
    	{
    		if(e.e1 instanceof Var){
    			if(e.e2 instanceof Var){
    				retour += String.format("fadd %s %s",e.e1.accept(this), e.e2.accept(this));
    			} else {
    				String v = variable(var++);
    				GenerationASML.haut += String.format("\nlet _%s = %s",v,e.e2.accept(this));
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
		if(e.e1 instanceof Var){
			if(e.e2 instanceof Var){
				retour += String.format("fmul %s %s",e.e1.accept(this), e.e2.accept(this));
			} else {
				String v1 = variable(var++); 
				GenerationASML.haut += String.format("\nlet _%s = %s",v1,e.e2.accept(this));
				if(!def){
    				GenerationASML.inter += String.format("\n\tlet %s = _%s in \n\tlet %s = mem(%s + 0) in", v1,v1,variable(var),v1);
				} else {
    				GenerationASML.haut += String.format("\n\tlet %s = _%s in \n\tlet %s = mem(%s + 0) in", v1,v1,variable(var),v1);
				}
				retour += String.format("fmul %s %s",variable(var++),e.e1.accept(this));
				var++;
			}
		} else {
			if(e.e2 instanceof Var){
				String v1 = variable(var++);
				GenerationASML.haut += String.format("\nlet _%s = %s",v1,e.e1.accept(this));	
				if(!def){
    				GenerationASML.inter += String.format("\n\tlet %s = _%s in \n\tlet %s = mem(%s + 0) in", v1,v1,variable(var),v1);
				} else {
    				GenerationASML.haut += String.format("\n\tlet %s = _%s in \n\tlet %s = mem(%s + 0) in", v1,v1,variable(var),v1);
				}
				retour += String.format("fmul %s %s",variable(var++),e.e2.accept(this));
				var++;
			} else {
				String v1=variable(var);
    			var++;
				GenerationASML.haut += String.format("\nlet _%s = %s \n\tlet _%s = %s",variable(var),e.e2.accept(this), v1,e.e1.accept(this));
				retour += String.format("fmul _%s _%s",v1,variable(var));
				var++;
			}
		}
		return retour ;
    
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
    		if(e.e1 instanceof Int) {
    			if(e.e2 instanceof Var){
    	    		return String.format("mul %s %s",e.e2.accept(this),e.e1.accept(this));
    			} else {
    				String v1=variable(var);
        			var++;
    				GenerationASML.haut += String.format("\n\tlet %s = %s in \n\tlet %s = %s in ",v1,e.e2.accept(this), variable(var),e.e1.accept(this));
    				retour += String.format("mul %s %s",v1,variable(var));
    				var++;
    			}
    		}
    		return String.format("mul %s %s",e.e1.accept(this),e.e2.accept(this));
    	}
    }

    public String visit(FDiv e){
    	String retour ="";
		if(e.e1 instanceof Var){
			if(e.e2 instanceof Var){
				retour += String.format("fdiv %s %s",e.e1.accept(this), e.e2.accept(this));
			} else {
				String v1 = variable(var++);
				GenerationASML.haut += String.format("\nlet _%s = %s",variable(var),e.e2.accept(this));
				if(!def){
    				GenerationASML.inter += String.format("\n\tlet %s = _%s in \n\tlet %s = mem(%s + 0) in", v1,v1,variable(var),v1);
				} else {
    				GenerationASML.haut += String.format("\n\tlet %s = _%s in \n\tlet %s = mem(%s + 0) in", v1,v1,variable(var),v1);
				}
				retour += String.format("fdiv _%s %s", variable(var),e.e1.accept(this));
				var++;
			}
		} else {
			if(e.e2 instanceof Var){
				String v1 = variable(var++);
				GenerationASML.haut += String.format("\nlet _%s = %s",variable(var),e.e1.accept(this));
				if(!def){
    				GenerationASML.inter += String.format("\n\tlet %s = _%s in \n\tlet %s = mem(%s + 0) in", v1,v1,variable(var),v1);
				} else {
    				GenerationASML.haut += String.format("\n\tlet %s = _%s in \n\tlet %s = mem(%s + 0) in", v1,v1,variable(var),v1);
				}
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
    }

    public String visit(Eq e){ 
    	String retour ="";
    	String v1 ="";
    	String v2="";
		if(!def)
    	{
			if(e.e1.isFloat()){
    			GenerationASML.haut = String.format("\nlet _z%s = %s \nlet _z%s = %s",cp++,e.e1.accept(this),cp++,e.e2.accept(this));
    			retour += String.format("_z%s = _z%s", cp--,cp++);
    		} else if(e.e1 instanceof Bool || e.e1 instanceof Int) {
    			if(e.e2 instanceof Int || e.e2 instanceof Bool) {
    				String v = variable(var) ;
        			var++;
        			 v1 = variable(var++) ;
    				GenerationASML.inter += String.format("\n\tlet %s = %s in \n\t let %s = %s in ",v,e.e1.accept(this),v1,e.e2.accept(this));
            		if(not){
                		retour += String.format("%s != %s", v,v1);
                		not = false ;
            		} else {
                		retour += String.format("%s = %s", v,v1);
            		}
            		var++;
    			} else {
    				String v = variable(var) ;
        			var++;
    				GenerationASML.inter += String.format("\n\tlet %s = %s in ",v,e.e1.accept(this));
            		if(not){
                		retour += String.format("%s != %s", v,e.e2.accept(this));
                		not = false ;
            		} else {
                		retour += String.format("%s = %s", v,e.e2.accept(this));
            		}
    			}	
    		} else if(e.e1 instanceof App || e.e2 instanceof App) {
        		if(e.e1 instanceof App) {
        			String txt = e.e1.accept(this);
        			v1 = variable(var++);
            		GenerationASML.inter += String.format("\n\tlet %s = %s in ",v1,txt);
        		}else {
            		v1 = e.e1.accept(this);
            	} if(e.e2 instanceof App){
        			String txt = e.e2.accept(this);
        			v2 = variable(var++);
            		GenerationASML.inter += String.format("\n\tlet %s = %s in ",v2,txt);
        		}else {
        			v2 = e.e2.accept(this);
        		}
            	if(not){
            		retour += String.format("%s >= %s", v1,v2);
            		not = false ;
    			} else {
            		retour += String.format("%s <= %s", v1,v2);
    			}
            } else {
    			if(not){
        			retour += String.format("%s != %s", e.e1.accept(this),e.e2.accept(this));
            		not = false ;
        		} else {
        			retour += String.format("%s = %s", e.e1.accept(this),e.e2.accept(this));
        		}
    		}
    	} else {
    		if(e.e1.isFloat()){
    			GenerationASML.haut = String.format("\nlet _z%s = %s \nlet _z%s = %s",cp++,e.e1.accept(this),cp++,e.e2.accept(this));
    			retour += String.format("_z%s = _z%s", cp--,cp++);
    		} else if(e.e1 instanceof Bool || e.e1 instanceof Int) {
    			if(e.e2 instanceof Bool || e.e2 instanceof Int){
    				String v = variable(var) ;
        			var++;
        			 v1 = variable(var++) ;
    				GenerationASML.haut += String.format("\n\tlet %s = %s in \n\t let %s = %s in ",v,e.e1.accept(this),v1,e.e2.accept(this));
            		if(not){
                		retour += String.format("%s != %s", v,v1);
                		not = false ;
            		} else {
                		retour += String.format("%s = %s", v,v1);
            		}
            		var++;
    			} else if(e.e1 instanceof App || e.e2 instanceof App) {
            		if(e.e1 instanceof App) {
            			String txt = e.e1.accept(this);
            			v1 = variable(var++);
                		GenerationASML.haut += String.format("\n\tlet %s = %s in ",v1,txt);
            		}else {
                		v1 = e.e1.accept(this);
                	} if(e.e2 instanceof App){
            			String txt = e.e2.accept(this);
            			v2 = variable(var++);
                		GenerationASML.haut += String.format("\n\tlet %s = %s in ",v2,txt);
            		}else {
            			v2 = e.e2.accept(this);
            		}
                	if(not){
                		retour += String.format("%s >= %s", v1,v2);
                		not = false ;
        			} else {
                		retour += String.format("%s <= %s", v1,v2);
        			}
    			} else {
    				String v = variable(var) ;
        			var++;
    				GenerationASML.haut += String.format("\n\tlet %s = %s in ",v,e.e1.accept(this));
            		if(not){
                		retour += String.format("%s != %s", v,e.e2.accept(this));
                		not = false ;
            		} else {
                		retour += String.format("%s = %s", v,e.e2.accept(this));
            		}
            		var++;
    			}
    			
    		} else {
    			if(not){
        			retour += String.format("%s != %s", e.e1.accept(this),e.e2.accept(this));
            		not = false ;
        		} else {
        			retour += String.format("%s = %s", e.e1.accept(this),e.e2.accept(this));
        		}
    		}
    	}
			
    	return retour ;
	}

    public String visit(LE e){
    	String retour ="";
    	String v1="";
    	String v2="";
		if(!def)
    	{
			if(e.e1.isFloat()){
    			GenerationASML.haut = String.format("\nlet _z%s = %s \nlet _z%s = %s",cp++,e.e1.accept(this),cp++,e.e2.accept(this));
    			retour += String.format("_z%s <= _z%s", cp--,cp++);
    		} else if(e.e1 instanceof Bool || e.e1 instanceof Int) {
    			if(e.e2 instanceof Int || e.e2 instanceof Bool) {
    				String v = variable(var) ;
        			var++;
        			 v1 = variable(var++) ;
    				GenerationASML.inter += String.format("\n\tlet %s = %s in \n\tlet %s = %s in ",v,e.e1.accept(this),v1,e.e2.accept(this));
    				if(not){
                		retour += String.format("%s >= %s", v,v1);
                		not = false ;
        			} else {
                		retour += String.format("%s <= %s", v,v1);
        			}
            		var++;
    			} else {
    				String v = variable(var) ;
        			var++;
    				GenerationASML.inter += String.format("\n\tlet %s = %s in",v,e.e1.accept(this));
    				if(not){
                		retour += String.format("%s >= %s", v,e.e2.accept(this));
                		not = false ;
        			} else {
                		retour += String.format("%s <= %s", v,e.e2.accept(this));
        			}
    			}
    			
    		} else if(e.e1 instanceof App || e.e2 instanceof App) {
        		if(e.e1 instanceof App) {
        			String txt = e.e1.accept(this);
        			v1 = variable(var++);
            		GenerationASML.inter += String.format("\n\tlet %s = %s in ",v1,txt);
        		}else {
            		v1 = e.e1.accept(this);
            	} if(e.e2 instanceof App){
        			String txt = e.e2.accept(this);
        			v2 = variable(var++);
            		GenerationASML.inter += String.format("\n\tlet %s = %s in ",v2,txt);
        		}else {
        			v2 = e.e2.accept(this);
        		}
            	if(not){
            		retour += String.format("%s >= %s", v1,v2);
            		not = false ;
    			} else {
            		retour += String.format("%s <= %s", v1,v2);
    			}
          	} else if(not){
    			retour += String.format("%s >= %s", e.e1.accept(this),e.e2.accept(this));
        		not = false ;
			} else {
    			retour += String.format("%s <= %s", e.e1.accept(this),e.e2.accept(this));

			}
    		return retour;
    	} else {
    		if(e.e1.isFloat()){
    			GenerationASML.haut = String.format("\nlet _z%s = %s \nlet _z%s = %s",cp++,e.e1.accept(this),cp++,e.e2.accept(this));
    			retour += String.format("_z%s <= _z%s", cp--,cp++);
    		} else if(e.e1 instanceof Bool || e.e1 instanceof Int) {
    			if(e.e2 instanceof Int || e.e2 instanceof Bool) {
    				String v = variable(var) ;
        			var++;
        			 v1 = variable(var++) ;
    				GenerationASML.haut += String.format("\n\tlet %s = %s in \n\t let %s = %s in ",v,e.e1.accept(this),v1,e.e2.accept(this));
            		if(not){
                		retour += String.format("%s >= %s", v,v1);
                		not = false ;
        			} else {
                		retour += String.format("%s <= %s", v,v1);
        			}
            		var++;
    			} else {
    				String v = variable(var) ;
        			var++;
    				GenerationASML.inter += String.format("\n\tlet %s = %s in",v,e.e1.accept(this));
    				if(not){
                		retour += String.format("%s >= %s", v,e.e2.accept(this));
                		not = false ;
        			} else {
                		retour += String.format("%s <= %s", v,e.e2.accept(this));
        			}
    			}
    		} else if(e.e1 instanceof App || e.e2 instanceof App) {
        		if(e.e1 instanceof App) {
        			String txt = e.e1.accept(this);
        			v1 = variable(var++);
            		GenerationASML.haut += String.format("\n\tlet %s = %s in ",v1,txt);
        		}else {
            		v1 = e.e1.accept(this);
            	} if(e.e2 instanceof App){
        			String txt = e.e2.accept(this);
        			v2 = variable(var++);
            		GenerationASML.haut += String.format("\n\tlet %s = %s in ",v2,txt);
        		}else {
        			v2 = e.e2.accept(this);
        		}
            	if(not){
            		retour += String.format("%s >= %s", v1,v2);
            		not = false ;
    			} else {
            		retour += String.format("%s <= %s", v1,v2);
    			}
          	} else {
    			if(not){
        			retour += String.format("%s >= %s", e.e1.accept(this),e.e2.accept(this));
            		not = false ;
    			} else {
        			retour += String.format("%s <= %s", e.e1.accept(this),e.e2.accept(this));

    			}
    		}
    		return retour;}

    }

    public String visit(If e){
    	String retour ="";
    	String haut ="";
    	if(e.e1 instanceof Not){
			not = true;
    	}
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
    				if(((Bool)e.e1).b == true) {
    					GenerationASML.inter += String.format("\n\tlet %s = %s in ",variable(var),e.e1.accept(this));
    	                retour += String.format("\n\tif %s = %s ",variable(var),variable(var++));
    				} else {
    					String v = variable(var++);
    					GenerationASML.inter += String.format("\n\tlet %s = %s in \n\tlet %s = 1",v,e.e1.accept(this),variable(var));
    	                retour += String.format("\n\tif %s = %s ",v,variable(var++));
        			
    				}
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
    			String v1 ="";
    			String v2="";
    			v1 = String.format("_z%s",cp++);
    			GenerationASML.haut = String.format("let %s = %s \n%s",v1,e.e2.accept(this),GenerationASML.haut);
    			if(e.e3 instanceof Float) {
        			v2 = String.format("_z%s",cp);
    				GenerationASML.haut  = String.format("let %s = %s \n%s",v2,e.e3.accept(this),GenerationASML.haut);
    			} else {
    				v2 = e.e3.accept(this);
    			}
                if(e.e1 instanceof Bool){
    				if(((Bool)e.e1).b == true) {
    					retour += String.format("\n\tlet %s = %s in ",variable(var),e.e1.accept(this));
    	                retour += String.format("\n\tif %s = %s ",variable(var),variable(var++));
    				} else {
    					String v = variable(var++);
    					retour += String.format("\n\tlet %s = %s in \n\tlet %s = 1",v,e.e1.accept(this),variable(var));
    	                retour += String.format("\n\tif %s = %s ",v,variable(var++));
        			
    				}
                } else {
                    retour += String.format("\n\tif %s ",e.e1.accept(this));
                }
                if(iff){
                	retour += String.format("then ( \n\t\tlet %s = z%s in %s\n\t)else(\n\t\tlet %s = z%s in %s\n\t)",asml,v1,asml,asml,v2,asml);

                } else {
                	retour += String.format("then ( \n\t\t%s \n\t)else(\n\t\t %s \n\t)",v1,v2);
                }
        		return retour;
    		}else {
    			
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
        		GenerationASML.haut += String.format("\nlet _%s = %s",e.id,e.e1.accept(this));
                retour += String.format("let %s = _%s in \n\tlet %s = mem(%s +0) in ", e.id,e.id,variable(var),e.id);
				
                if(e.e1.isVIFB() || e.e1 instanceof If){
                	retour += "\n\t";
                }
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
            	retour += String.format("\n\tlet %s = ",e.id);
                if(e.e1.isVIFB() || e.e1 instanceof If){
                	retour += "\n\t";
                }
                retour += String.format("%s in %s ",e.e1.accept(this),e.e2.accept(this));
            } 
    		return retour;
        } else {
        	if((e.e1).isFloat())
            {
            	GenerationASML.haut += String.format("let _%s = %s \n", e.id,e.e1.accept(this)) ;
                retour += String.format("let %s = _%s in \n\tlet %s = mem(%s +0) in ", e.id,e.id,variable(var),e.id);
                if(e.e2.isVar()) {
                    retour += String.format("%s \n", e.e2.accept(this));
                } else {
                    retour += String.format("\n %s", e.e2.accept(this));
                }
            } else {
            	retour += String.format("\n\tlet %s = %s in %s", e.id.id,e.e1.accept(this),e.e2.accept(this));
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
    public String printInfix2(List<Exp> l, String op,String id) {
    	String t ="";
		String txt="";
    	for(Exp e : l){
    		if(def) {
    			if(e instanceof Var){
            		t += e + " ";  
    			} else {
    				if (e instanceof App) {
        				if(app) {
                			app = false ;
                    		txt = e.accept(this);
                    		app = true ;
                		} else {
                    		txt = e.accept(this);
                		}
        				if(app){
            				txt += String.format("in call _%s %s",id,t) ;
                            t = String.format("\n\tlet %s = %s",variable(var),txt);
            			} else {
                            t += String.format("\n\tlet %s = %s",variable(var),txt);
            			}
        			} else {
                		GenerationASML.haut += String.format("\n\tlet %s = %s in ",variable(var),e.accept(this));
                		
        			}
    				t += variable(var) + " ";
            		var++;
    			}
    		} else {
    			if(e instanceof Var){
            		t += e + " ";  
    			} else if (e instanceof Float) {
    				String v = variable(var++) ;
    				GenerationASML.haut += String.format("\nlet _%s = %s",v,((Float)e).f);
    				GenerationASML.inter += String.format("\n\tlet %s = _%s in \n\tlet %s = mem(%s +0) in",v,v,variable(var),v);
    				t += variable(var++) ;
    			} else if (e instanceof App || e instanceof OpBin || e instanceof OpUn || e instanceof If) {
    				if(app) {
            			app = false ;
                		txt = e.accept(this);
                		app = true ;
            		} else {
                		txt = e.accept(this);
            		}
    				if(app){
        				txt += String.format("in call _min_caml_%s %s ",id,t) ;
                        t = String.format("\n\tlet %s = %s ",variable(var),txt);
        			} else if(e instanceof OpBin ){
        				opbin = true;
        				txt += String.format(" in call _min_caml_%s %s ",id,t) ;
        				t = String.format("\n\tlet %s = %s ",variable(var),txt);
        			} else if(e instanceof OpUn) {
        				opbin = true;
        				String v = variable(var++);
        				GenerationASML.inter += String.format("\n\tlet %s = %s in \n\tlet %s = call _min_caml_%s %s in ",v,txt,variable(var),id,v) ;
        			} else if (e instanceof If){
        				t += String.format(" in call _min_caml_%s %s ",id,t) ;
        				t = String.format("\n\tlet %s = %s ",variable(var),txt);
        			} else { 
                        GenerationASML.inter += String.format("\n\tlet %s = %s in ",variable(var),txt);
        			}
    				t += variable(var) + " ";
            		var++;
    			} else {
            		GenerationASML.inter += String.format("\n\tlet %s = %s in ",variable(var),e.accept(this));
            		t += variable(var) + " ";
            		var++;
    			}
    		}
    	}
        return t ;
    }

    public String visit(LetRec e){
        GenerationASML.haut += String.format("\nlet _%s %s = \n\t",e.fd.id,printInfix(e.fd.args, " "),printInfix(e.fd.args, " "));
        def = true ;
        String a = e.fd.e.accept(this);
        GenerationASML.haut += String.format ("%s",a);
	    def = false ;
	    String retour = e.e.accept(this);
        return retour;
    }

    public String visit(App e){
    	String retour ="";
    		if(e.e instanceof Var && (((Var)e.e).id.id.equals("print_int") || ((Var)e.e).id.id.equals("print_float") || ((Var)e.e).id.id.equals("truncate") || ((Var)e.e).id.id.equals("print_newline") || ((Var)e.e).id.id.equals("int_of_float") || ((Var)e.e).id.id.equals("float_of_int"))){
    			for(Exp r : e.es){
    	    		if (r.isVIFB()){
    	    			app = true ;
    	    			retour += printInfix2(e.es, " ",e.e.accept(this));
    	    		}
    	    	} 
    			if(((Var)e.e).id.id.equals("print_float") ){
    				String v = variable(var++) ;
    	    		retour += String.format("\n\tlet %s = call _min_caml_int_of_float %s in ",v,printInfix2(e.es, " ",e.e.accept(this)),variable(var),v);
    	    		retour += String.format("call _min_caml_print_int %s ",v);
    			} else if(!app){
        	    	String txt = printInfix2(e.es, " ",e.e.accept(this));
        	    	if(opbin){
        	    		retour += txt ;
        	    	} else
    	    		retour += String.format("call _min_caml_%s %s ",e.e.accept(this),txt);
    			}
    		} else {
    			for(Exp r : e.es){
    	    		if ((!(r instanceof Var) && !(r instanceof Int) )){
    	    			app = true ;
    	    			retour += printInfix2(e.es, " ",e.e.accept(this));
    	    			app = true ;
    	    		}
    			}
    			if(!app) {
    				String txt = e.e.accept(this);
    				String str2 = "call";
    				if(txt.toLowerCase().contains(str2.toLowerCase())){
    					GenerationASML.inter += String.format("let %s = %s in",variable(var),txt);
            			retour += String.format("call _%s %s ",variable(var++),printInfix2(e.es, " ",e.e.accept(this)));
    				} else
        			retour += String.format("call _%s %s ",txt,printInfix2(e.es, " ",e.e.accept(this)));

    	    		}
    			app = false;
    		}
    		return retour ;
    }

    public String visit(Tuple e){
    	return String.format("%s",printInfix(e.es, " "));
    }

    public String visit(LetTuple e){
    	return String.format("let %s = 1 in \n\tlet %s = call _min_caml_create_array %s %s in %s",variable(var),printInfix(e.ids,""),variable(var++), e.e1.accept(this),e.e2.accept(this));
    }

    public String visit(Array e){
    	String retour ="";
    	String v1 = "";
    	String v2 = "";
    	String v =variable(var++);
    	if(e.e1 instanceof Int){
			v2 = variable(var++);
			GenerationASML.inter += String.format("\n\tlet %s = %s in",v2,e.e1.accept(this));
    	} else if (e.e1 instanceof Var){
			v2 = e.e1.accept(this);
    	} 
    	if(e.e2 instanceof Int){
			v1 = variable(var++);
			GenerationASML.inter += String.format("\n\tlet %s = %s in",v1,e.e2.accept(this));
        	retour += String.format(" call _min_caml_create_float_array %s %s",v,v2,v1);
    	} else if (e.e2 instanceof Var){
			v1 = e.e2.accept(this);
        	retour += String.format(" call _min_caml_create_float_array %s %s",v,v2,v1);
    	} else if (e.e2 instanceof Float){
			v1 = variable(var++);
			String v3 = variable(var++);
			GenerationASML.inter += String.format("\n\tlet %s = _%s in\n\tlet %s = mem(%s + 0) in",v1,v1,v3,v1);
			GenerationASML.haut += String.format("\nlet _%s = %s",v1,e.e2.accept(this));
        	retour += String.format(" call _min_caml_create_float_array %s %s",v,v2,v1);
    	} 
    	return retour;
    }

    public String visit(Get e){
    	String v = variable(var);
    	GenerationASML.inter += String.format("\n\tlet %s = mem(%s + %s) in",v, e.e1.accept(this), e.e2.accept(this));
    	return v;
    	}

    public String visit(Put e){
    	return String.format("mem(%s + %s) <- %s", e.e1.accept(this), e.e2.accept(this),e.e3.accept(this));
    }
}
