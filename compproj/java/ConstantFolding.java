import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;


public class ConstantFolding implements ObjVisitor<Exp> {

	private static Hashtable<String, Exp> tabSym = new Hashtable<String, Exp>();
	static Hashtable<String, Boolean> tabVar = new Hashtable<String, Boolean>();
	
	@Override
	public Exp visit(Bool e) {
		return e;
	}

	@Override
	public Exp visit(Int e) {
		return e;
	}

	@Override
	public Exp visit(Float e) {
		return e;
	}

	@Override
	public Exp visit(Not e) {
		e.e = e.e.accept(this) ; 
		if (e.e instanceof Bool) {
			return new Bool(!((Bool)e.e).b);
		} else if (e.e instanceof Not) {
			return ((Not)e.e).e.accept(this);
		}
		return e ;
	}

	@Override
	public Exp visit(Neg e) {
		e.e = e.e.accept(this) ; 
		return e ;
	}

	@Override
	public Exp visit(Add e) {
		OpBin newExp = (OpBin)CFolding(e);
		if (newExp.e1 instanceof Int && newExp.e2 instanceof Int) {
			return new Int(((Int)newExp.e1).i + ((Int)newExp.e2).i);
		}
		return newExp;
	}

	@Override
	public Exp visit(Sub e) {
		OpBin newExp = (OpBin)CFolding(e);
		if (newExp.e1 instanceof Int && newExp.e2 instanceof Int) {
			int test = ((Int)newExp.e1).i - ((Int)newExp.e2).i;
			if(test < 0){
				return new Neg(new Int((-test)));
			}
			return new Int(((Int)newExp.e1).i - ((Int)newExp.e2).i);
		}
		return newExp;
	}

	@Override
	public Exp visit(FNeg e) {
		e.e = e.e.accept(this) ; 
		return e ;
	}

	@Override
	public Exp visit(FAdd e) {
		OpBin newExp = (OpBin)CFolding(e);
		if (newExp.e1 instanceof Float && newExp.e2 instanceof Float) {
			return new Float(((Float)newExp.e1).f + ((Float)newExp.e2).f);
		}
		return newExp;
	}

	@Override
	public Exp visit(FSub e) {
		OpBin newExp = (OpBin)CFolding(e);
		if (newExp.e1 instanceof Float && newExp.e2 instanceof Float) {
			float test = ((Float)newExp.e1).f - ((Float)newExp.e2).f ;
			if(test < 0) {
				return new FNeg(new Float(-test));
			}
			return new Float(((Float)newExp.e1).f - ((Float)newExp.e2).f);
		}
		return newExp;
	}

	@Override
	public Exp visit(FMul e) {
		OpBin newExp = (OpBin)CFolding(e);
		if (newExp.e1 instanceof Float && newExp.e2 instanceof Float) {
			return new Float(((Float)newExp.e1).f * ((Float)newExp.e2).f);
		}
		return newExp;
	}

	@Override
	public Exp visit(Mul e) {
		OpBin newExp = (OpBin)CFolding(e);
		if (newExp.e1 instanceof Int && newExp.e2 instanceof Int) {
			return new Int(((Int)newExp.e1).i * ((Int)newExp.e2).i);
		}
		return newExp;
	}

	@Override
	public Exp visit(FDiv e) {
		OpBin newExp = (OpBin)CFolding(e);
		if (newExp.e1 instanceof Float && newExp.e2 instanceof Float) {					
			return new Float(((Float)newExp.e1).f / ((Float)newExp.e2).f);
		}
		return newExp;
	}

	@Override
	public Exp visit(Eq e) {
		OpBin newExp = (OpBin)CFolding(e);
		if (newExp.e1 instanceof Float && newExp.e2 instanceof Float) {
			return new Bool(((Float)newExp.e1).f == ((Float)newExp.e2).f);
		} else if (newExp.e1 instanceof Int && newExp.e2 instanceof Int) {
			return new Bool(((Int)newExp.e1).i == ((Int)newExp.e2).i);
		} else if (newExp.e1 instanceof Bool && newExp.e2 instanceof Bool) {
			return new Bool(((Bool)newExp.e1).b == ((Bool)newExp.e2).b);
		}
		return newExp;
	}

	@Override
	public Exp visit(LE e) {
		OpBin newExp = (OpBin)CFolding(e);
		if (newExp.e1 instanceof Float && newExp.e2 instanceof Float) {
			return new Bool(((Float)newExp.e1).f <= ((Float)newExp.e2).f);
		} else if (newExp.e1 instanceof Int && newExp.e2 instanceof Int) {
			return new Bool(((Int)newExp.e1).i <= ((Int)newExp.e2).i);
		} else if (newExp.e1 instanceof Bool && newExp.e2 instanceof Bool) {
			return new Bool(((Bool)newExp.e1).b == ((Bool)newExp.e2).b);
		}
		return newExp;
	}

	@Override
	public Exp visit(If e) {
		if (e.e1 instanceof Var) {
			if(tabSym.containsKey(((Var)e.e1).id.toString()))
			{
				tabVar.put(((Var)e.e1).id.toString(), true) ;
				e.e1 = tabSym.get(((Var)e.e1).id.toString()) ;
			} 
		}
		if (e.e2 instanceof Var) {
			if(tabSym.containsKey(((Var)e.e2).id.toString()))
			{
				tabVar.put(((Var)e.e2).id.toString(), true) ;
				e.e2 = tabSym.get(((Var)e.e2).id.toString()) ;
			} 
		}
		if (e.e3 instanceof Var) {
			if(tabSym.containsKey(((Var)e.e3).id.toString()))
			{
				tabVar.put(((Var)e.e3).id.toString(), true) ;
				e.e3 = tabSym.get(((Var)e.e3).id.toString()) ;
			}
		}
		e.e1 = e.e1.accept(this);
		e.e2 = e.e2.accept(this);
		e.e3 = e.e3.accept(this);
		return e;
	}

	@Override
	public Exp visit(Let e) {
		if(!tabSym.containsKey(e.id.toString())) {
			if(e.e1 instanceof Var){
				if(tabSym.containsKey(((Var)e.e1).id.toString())){
					tabVar.put(((Var)e.e1).id.toString(), true) ;
					Exp retour = tabSym.get(((Var)e.e1).id.toString());
					if (retour instanceof Var) {
						e.e1 = retour;
					} 
				}
			} 
			e.e1 = e.e1.accept(this) ; 
			if (e.e1 instanceof Int || e.e1 instanceof Float || e.e1 instanceof Bool || e.e1 instanceof Var || e.e1 instanceof Unit) {
				tabSym.put(e.id.toString(), e.e1);
				tabVar.put(e.id.toString(), true);
			}
		} else {
			e.e1 = e.e1.accept(this) ; 
			tabSym.remove(e.id.toString());
			tabSym.put(e.id.toString(), e.e1);
		}
		if(e.e2 instanceof Var){
			if(tabSym.containsKey(((Var)e.e2).id.toString())){
				tabVar.put(((Var)e.e2).id.toString(), true) ;
				e.e2 = tabSym.get(((Var)e.e2).id.toString());
			}
		} else {
			e.e2 = e.e2.accept(this);
		}
		if (e.e2 instanceof Var && e.id.equals(((Var)(e.e2)).id)) {
			return e.e1;
		}
		return e;      
	}

	@Override
	public Exp visit(Var e) {
		if(tabSym.containsKey(e.id.toString())){
			tabVar.put(e.id.toString(), false) ;
		}
		return e;
	}

	@Override
	public Exp visit(LetRec e) {
		if(!tabSym.containsKey(e.fd.id.toString())){
			tabSym.put(e.fd.id.toString(), e);
			tabVar.put(e.fd.id.toString(), true) ;
		} 
		e.fd.e = e.fd.e.accept(this);
		e.e = e.e.accept(this);
		return e;
	}

	public List<Exp> printIntFix(List<Exp> args){
		List<Exp> retour = new LinkedList<Exp>();
		for (Exp arg : args) {
			Exp newArg = arg;
			if( arg instanceof Var){
				if(tabSym.containsKey(((Var)arg).id.toString())) {
					tabVar.put(((Var)arg).id.toString(), true) ;
					newArg = tabSym.get(((Var)arg).id.toString());
				}
			} else {
				newArg = arg.accept(this);
			}
			retour.add(newArg);
		}
		return retour;
	}
	
	// spe Tuple
    public <E> List<E>  printInfix(List<E> ids, Exp e1) {
        
    	int cpt = 0 ;
    	
		while(cpt < ids.size()){
			E arg = ids.get(cpt);
			if(!tabSym.containsKey(arg.toString())) {
				if(e1 instanceof Var){
					if(tabSym.containsKey(e1.toString())){
						tabVar.remove(e1.toString());
					}
				} else if (e1 instanceof Tuple) {
					Exp exp = ((Tuple)e1).es.get(cpt) ;
					tabSym.put(arg.toString(), exp);
					tabVar.put(arg.toString(), true) ;
				}
			}
			cpt++;
		}
		return ids;
    }

	@Override
	public Exp visit(App e) {
		if (e.e instanceof Var) {
			if(tabSym.containsKey(((Var)e.e).id.toString())){
				tabVar.remove(((Var)e.e).id.toString()) ;
				Exp newExp = tabSym.get(((Var)e.e).id.toString());
				if (newExp instanceof Var) {
					e.e = newExp;
				}
			}
			String ap = ((Var)e.e).id.toString();
			if(!ap.equals("print_int") && !ap.equals("print_float")) {
				e.e = e.e.accept(this);
				e.es = printIntFix(e.es);
				return e ;
			}
		}
		e.e = e.e.accept(this);
		e.es = printIntFix(e.es);
		return e;
	}

	@Override
	public Exp visit(Tuple e) {
		e.es = printIntFix(e.es);
		return e;
	}

	@Override
	public Exp visit(LetTuple e) {
		e.e1 = e.e1.accept(this);
		e.ids = printInfix(e.ids,e.e1);
		e.e2 = e.e2.accept(this);
		return e;
	}

	@Override
	public Exp visit(Array e) {
		e.e1 = e.e1.accept(this);
		e.e2 = e.e2.accept(this);
		return e;
	}

	@Override
	public Exp visit(Get e) {
		if(e.e1 instanceof Var){
			if(tabSym.containsKey(((Var)e.e1).id.toString())){
				tabVar.remove(((Var)e.e1).id.toString()) ;
			}
		}
		if(e.e2 instanceof Var){
			if(tabSym.containsKey(((Var)e.e2).id.toString())){
				tabVar.remove(((Var)e.e2).id.toString()) ;
			}
		}
		e.e1 = e.e1.accept(this);
		e.e2 = e.e2.accept(this);
		return e;
	}

	@Override
	public Exp visit(Put e) {
		e.e1 = e.e1.accept(this);
		e.e2 = e.e2.accept(this);
		e.e3 = e.e3.accept(this);
		return e;
	}

	@Override
	public Exp visit(Unit unit) {
		return unit;
	}

	private Exp CFolding(OpBin e) {
		OpBin newExp = (OpBin)e.clone();

		if (e.e1 instanceof Var) {
			if(tabSym.containsKey(((Var)e.e1).id.toString()))
			{
				tabVar.put(((Var)e.e1).id.toString(), true) ;
				newExp.e1 = tabSym.get(((Var)e.e1).id.toString()) ;
			} 
		}
		if (e.e2 instanceof Var){
			if(tabSym.containsKey(((Var)e.e2).id.toString()))
			{
				tabVar.put(((Var)e.e2).id.toString(), true) ;
				newExp.e2 = tabSym.get(((Var)e.e2).id.toString()) ;
			} 
		}
		newExp.e1 = newExp.e1.accept(this);
		newExp.e2 = newExp.e2.accept(this);
		return newExp;
	}

}
