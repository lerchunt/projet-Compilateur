import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;


public class TypeChecking implements ObjVisitor<Type> {
	
	private Hashtable<String, Type> tabSym = new Hashtable<String, Type>();
	private static boolean def = false ; 
	private static int cpt = 0 ;

	@Override
	public Type visit(Bool e) {
		return new TBool();
	}

	@Override
	public Type visit(Int e) {
		return new TInt();
	}

	@Override
	public Type visit(Float e) {
		return new TFloat();
	}

	@Override
	public Type visit(Not e) {
		if(!def){
			if(e.e.accept(this) instanceof TBool)
			{
				return e.e.accept(this);
			}
			System.err.println(e.e + " : expected a type boolean");
			System.exit(1);
		} else {
			if(e.e.accept(this) instanceof TUnit){
				tabSym.remove(e.e.toString());
				tabSym.put(e.e.toString(),new TBool());
				return tabSym.get(e.e.toString());
			} else if (e.e.accept(this) instanceof TBool) {
				return e.e.accept(this);
			}
		}
		return null;
	}

	@Override
	public Type visit(Neg e) {
		if(!def){
			if(e.e.accept(this) instanceof TInt || e.e.accept(this) instanceof TFloat)
			{
				return e.e.accept(this);
			}
			System.err.println(e.e + " : expected a type int or float");
			System.exit(1);
		} else {
				return e.e.accept(this);
		}
		return null;
	}

	@Override
	public Type visit(Add e) {
		if(!def){
			if(e.e1.accept(this) instanceof TInt && e.e2.accept(this) instanceof TInt)
			{
				return e.e1.accept(this);
			} 
			System.err.println(e.e1.toString() + " + " + e.e2.toString() + " : expected a type int ");
			System.exit(1);
		} else {
			if(e.e1.accept(this) instanceof TUnit && e.e2.accept(this) instanceof TInt){
				tabSym.remove(e.e1.toString());
				tabSym.put(e.e1.toString(), new TInt());
				return e.e2.accept(this);
			} else if (e.e2.accept(this) instanceof TUnit && e.e1.accept(this) instanceof TInt){
				tabSym.remove(e.e2.toString());
				tabSym.put(e.e2.toString(), new TInt());
				return e.e1.accept(this);
			} else if(e.e1.accept(this) instanceof TInt && e.e2.accept(this) instanceof TInt)
			{
				return e.e1.accept(this);
			}
			System.err.println(e.e1.toString() + " + " + e.e2.toString() + " : expected a type int ");
			System.exit(1);
		}
		return null ;
	}

	@Override
	public Type visit(Sub e) {
		if(!def){
			if(e.e1.accept(this) instanceof TInt && e.e2.accept(this) instanceof TInt)
			{
				return e.e1.accept(this);
			}
			System.err.println(e.e1.toString() + " - " + e.e2.toString() + " : expected a type int ");
			System.exit(1);
		} else {
			if(e.e1.accept(this) instanceof TUnit && e.e2.accept(this) instanceof TInt){
				tabSym.remove(e.e1.toString());
				tabSym.put(e.e1.toString(), new TInt());
				return e.e2.accept(this);
			} else if (e.e2.accept(this) instanceof TUnit && e.e1.accept(this) instanceof TInt){
				tabSym.remove(e.e2.toString());
				tabSym.put(e.e2.toString(), new TInt());
				return e.e1.accept(this);
			} else if(e.e1.accept(this) instanceof TInt && e.e2.accept(this) instanceof TInt)
			{
				return e.e1.accept(this);
			}
			System.err.println(e.e1.toString() + " - " + e.e2.toString() + " : expected a type int ");
			System.exit(1);
		}
	
	return null ;
}

	@Override
	public Type visit(FNeg e) {
		if(!def){
			if(e.e.accept(this) instanceof TFloat)
			{
				return e.e.accept(this);
			}
			System.err.println(e.e + " : expected a type float");
			System.exit(1);
		} else {
			if(e.e.accept(this) instanceof TUnit){
				tabSym.remove(e.e.toString());
				tabSym.put(e.e.toString(),new TFloat());
				return e.e.accept(this);
			} else if (e.e.accept(this) instanceof TFloat){
				return e.e.accept(this);
			}
			System.err.println(e.e + " : expected a type float ");
			System.exit(1);
		}
		
		return null;
	}

	@Override
	public Type visit(FAdd e) {
		if(!def){
			if(e.e1.accept(this) instanceof TFloat && e.e2.accept(this) instanceof TFloat)
			{
				return e.e1.accept(this);
			}
			System.err.println(e.e1 + " +. " + e.e2 + " : expected a type float ");
			System.exit(1);
		} else {
			if(e.e1.accept(this) instanceof TUnit && e.e2.accept(this) instanceof TFloat){
				tabSym.remove(e.e1.toString());
				tabSym.put(e.e1.toString(), new TFloat());
				return e.e2.accept(this);
			} else if (e.e2.accept(this) instanceof TUnit && e.e1.accept(this) instanceof TFloat){
				tabSym.remove(e.e2.toString());
				tabSym.put(e.e2.toString(), new TFloat());
				return e.e1.accept(this);
			} else if (e.e2.accept(this) instanceof TFloat && e.e1.accept(this) instanceof TFloat){
				return e.e1.accept(this);
			}
			System.err.println(e.e1.toString() + " +. " + e.e2.toString() + " : expected a type float ");
			System.exit(1);
		}
		return null ;
	}

	@Override
	public Type visit(FSub e) {
		if(!def){
			if(e.e1.accept(this) instanceof TFloat && e.e2.accept(this) instanceof TFloat)
			{
				return e.e1.accept(this);
			}
			System.err.println(e.e1 + " -. " + e.e2 + " : expected a type float ");
			System.exit(1);
		} else {
			if(e.e1.accept(this) instanceof TUnit && e.e2.accept(this) instanceof TFloat){
				tabSym.remove(e.e1.toString());
				tabSym.put(e.e1.toString(), e.e2.accept(this));
				return e.e2.accept(this);
			} else if (e.e2.accept(this) instanceof TUnit && e.e1.accept(this) instanceof TFloat){
				tabSym.remove(e.e2.toString());
				tabSym.put(e.e2.toString(), e.e1.accept(this));
				return e.e1.accept(this);
			} else if(e.e1.accept(this) instanceof TFloat && e.e2.accept(this) instanceof TFloat)
			{
				return e.e1.accept(this);
			}
			System.err.println(e.e1.toString() + " -. " + e.e2.toString() + " : expected a type float ");
			System.exit(1);
		}
		return null ;
	}

	@Override
	public Type visit(FMul e) {
		if(!def){
			if(e.e1.accept(this) instanceof TFloat && e.e2.accept(this) instanceof TFloat)
			{
				return e.e1.accept(this);
			}
			System.err.println(e.e1 + " *. " + e.e2 + " : expected a type float ");
			System.exit(1);
		} else {
			if(e.e1.accept(this) instanceof TUnit && e.e2.accept(this) instanceof TFloat){
				tabSym.remove(e.e1.toString());
				tabSym.put(e.e1.toString(), new TFloat());
				return e.e2.accept(this);
			} else if (e.e2.accept(this) instanceof TUnit && e.e1.accept(this) instanceof TFloat){
				tabSym.remove(e.e2.toString());
				tabSym.put(e.e2.toString(), new TFloat());
				return e.e1.accept(this);
			} else if (e.e2.accept(this) instanceof TFloat && e.e1.accept(this) instanceof TFloat){
				return e.e1.accept(this);
			}
			System.err.println(e.e1.toString() + " *. " + e.e2.toString() + " : expected a type float ");
			System.exit(1);
		}
		return null ;
	}

	@Override
	public Type visit(Mul e) {
		if(!def){
			if(e.e1.accept(this) instanceof TInt && e.e2.accept(this) instanceof TInt)
			{
				return e.e1.accept(this);
			}
			System.err.println(e.e1 + " * " + e.e2 + " : expected a type int ");
			System.exit(1);
		} else {
			if(e.e1.accept(this) instanceof TUnit && e.e2.accept(this) instanceof TInt){
				tabSym.remove(e.e1.toString());
				tabSym.put(e.e1.toString(), new TInt());
				return e.e2.accept(this);
			} else if (e.e2.accept(this) instanceof TUnit && e.e1.accept(this) instanceof TInt){
				tabSym.remove(e.e2.toString());
				tabSym.put(e.e2.toString(), new TInt());
				return e.e1.accept(this);
			} else if(e.e1.accept(this) instanceof TInt && e.e2.accept(this) instanceof TInt){
				return e.e1.accept(this);
			}
			System.err.println(e.e1.toString() + " * " + e.e2.toString() + " : expected a type int ");
			System.exit(1);
		}
		
		return null ;
	}

	@Override
	public Type visit(FDiv e) {
		if(!def){
			if(e.e1.accept(this) instanceof TFloat && e.e2.accept(this) instanceof TFloat)
			{
				return e.e1.accept(this);
			}
			System.err.println(e.e1 + " /. " + e.e2 + " : expected a type float ");
			System.exit(1);
		} else {
			if(e.e1.accept(this) instanceof TUnit && e.e2.accept(this) instanceof TFloat){
				tabSym.remove(e.e1.toString());
				tabSym.put(e.e1.toString(), new TFloat());
				return e.e2.accept(this);
			} else if (e.e2.accept(this) instanceof TUnit && e.e1.accept(this) instanceof TFloat){
				tabSym.remove(e.e2.toString());
				tabSym.put(e.e2.toString(), new TFloat());
				return e.e1.accept(this);
			} else if (e.e2.accept(this) instanceof TFloat && e.e1.accept(this) instanceof TFloat){
				return e.e1.accept(this);
			}
			System.err.println(e.e1.toString() + " /. " + e.e2.toString() + " : expected a type float ");
			System.exit(1);
		}
		return null ;
	}

	@Override
	public Type visit(Eq e) {
		if(!def) {
			if(e.e1.accept(this) instanceof TFloat && e.e2.accept(this) instanceof TFloat)
			{
				return new TBool();
			} else if (e.e1.accept(this) instanceof TInt && e.e2.accept(this) instanceof TInt)
			{
				return new TBool();
			} else if (e.e1.accept(this) instanceof TBool && e.e2.accept(this) instanceof TBool){
				return new TBool();
			} 
		} else {
			if(e.e1.accept(this) instanceof TUnit && e.e2.accept(this) instanceof TUnit){
				tabSym.remove(e.e1.toString());
				tabSym.put(e.e1.toString(), new TBool());
				tabSym.remove(e.e2.toString());
				tabSym.put(e.e2.toString(), new TBool());
				return tabSym.get(e.e2.toString());
			} else if(e.e1.accept(this) instanceof TFloat && e.e2.accept(this) instanceof TFloat)
			{
				return new TBool();
			} else if (e.e1.accept(this) instanceof TInt && e.e2.accept(this) instanceof TInt)
			{
				return new TBool();
			} else if (e.e1.accept(this) instanceof TBool && e.e2.accept(this) instanceof TBool){
				return new TBool();
			} 
		}
		System.err.println(e.e1 + " = " + e.e2 + " : expected a similar type");
		System.exit(1);
		return null ;
	}

	@Override
	public Type visit(LE e) {
		if(!def){
			if(e.e1.accept(this) instanceof TFloat && e.e2.accept(this) instanceof TFloat)
			{
				return new TBool();
			} else if (e.e1.accept(this) instanceof TInt && e.e2.accept(this) instanceof TInt)
			{
				return new TBool();
			} else if (e.e1.accept(this) instanceof TBool && e.e2.accept(this) instanceof TBool)
			{
				return new TBool();
			}
		} else if(e.e1.accept(this) instanceof TUnit && e.e2.accept(this) instanceof TUnit) {
			tabSym.remove(e.e1.toString());
			tabSym.put(e.e1.toString(), new TBool());
			tabSym.remove(e.e2.toString());
			tabSym.put(e.e2.toString(), new TBool());
			return tabSym.get(e.e2.toString());
		}
		System.err.println(e.e1 + " <= " + e.e2 + " : expected a similar type ");
		System.exit(1);
		return null ;
	}

	@Override
	public Type visit(If e) {
		if(!def){
			if(e.e1.accept(this) instanceof TBool)
			{
				if(e.e2.accept(this) instanceof TInt && e.e3.accept(this) instanceof TInt)
				{
					return e.e2.accept(this);
				} else if(e.e2.accept(this) instanceof TFloat && e.e3.accept(this) instanceof TFloat)
				{
					return e.e2.accept(this);
				} else if(e.e2.accept(this) instanceof TBool && e.e3.accept(this) instanceof TBool)
				{
					return e.e2.accept(this);
				}
			}
		} else {
			if(e.e1.accept(this) instanceof TBool)
			{
				if(e.e2.accept(this) instanceof TUnit && e.e3.accept(this) instanceof TUnit)
				{
					return e.e2.accept(this);
				} else if (e.e2.accept(this) instanceof TUnit && e.e3.accept(this) instanceof TFloat)
				{
					tabSym.remove(e.e2.toString());
					tabSym.put(e.e2.toString(), e.e3.accept(this));
					return tabSym.get(e.e2.toString());
				} else if (e.e2.accept(this) instanceof TUnit && e.e3.accept(this) instanceof TInt)
				{
					tabSym.remove(e.e2.toString());
					tabSym.put(e.e2.toString(), e.e3.accept(this));
					return tabSym.get(e.e2.toString());
				} else if (e.e2.accept(this) instanceof TUnit && e.e3.accept(this) instanceof TBool)
				{
					tabSym.remove(e.e2.toString());
					tabSym.put(e.e2.toString(), e.e3.accept(this));
					return tabSym.get(e.e2.toString());
				} else if (e.e3.accept(this) instanceof TUnit && e.e2.accept(this) instanceof TFloat)
				{
					tabSym.remove(e.e3.toString());
					tabSym.put(e.e3.toString(), e.e2.accept(this));
					return tabSym.get(e.e3.toString());
				} else if (e.e3.accept(this) instanceof TUnit && e.e2.accept(this) instanceof TInt)
				{
					tabSym.remove(e.e3.toString());
					tabSym.put(e.e3.toString(), e.e2.accept(this));
					return tabSym.get(e.e3.toString());
				} else if (e.e3.accept(this) instanceof TUnit && e.e2.accept(this) instanceof TBool)
				{
					tabSym.remove(e.e3.toString());
					tabSym.put(e.e3.toString(), e.e2.accept(this));
					return tabSym.get(e.e3.toString());
				}
			}
		}
		System.err.println("if " + e.e1.toString() + " then " + e.e2.toString() + " else " + e.e3.toString() + " : expected a type bool then similar ");
		System.exit(1);
		return null ;
	}

	@Override
	public Type visit(Let e) {
		if(!tabSym.containsKey(e.id.toString())) {
			Type exp = e.e1.accept(this) ; 
			tabSym.put(e.id.toString(), exp);
		} else {
			Type exp = e.e1.accept(this) ; 
			tabSym.remove(e.id.toString());
			tabSym.put(e.id.toString(), exp);
		}		
		return e.e2.accept(this);
	}

	@Override
	public Type visit(Var e) {
		if(!def){
			if(!tabSym.containsKey(e.id.toString())) {
				System.err.println(e.id.toString() + " : doesn't exist");
				System.exit(1);
			} else {
				return tabSym.get(e.id.toString());
			}
		} else {
			if(!tabSym.containsKey(e.id.toString())) {
				System.err.println(e.id.toString() + " : doesn't exist");
				System.exit(1);
			} else {
				return tabSym.get(e.id.toString());
			}
		}	
			
		return null;
	}

	@Override
	public Type visit(LetRec e) {
		if(tabSym.containsKey(e.fd.id.toString())) {
			System.err.println(e.fd.id.toString() + " : is already exist");
			System.exit(1);
		}
		def = true ; 
		printInfix(e.fd.args) ;
		Type f = e.fd.e.accept(this);
		tabSym.put(e.fd.id.toString(), f);
		if(e.e.isVar()){
			e.e.accept(this);
			def = false ;
		} else {
			def = false ;
			e.e.accept(this);
		}
		return 	e.e.accept(this);
	}
	
	private void printInfix(List<Id> args) {
		Iterator<Id> it = args.iterator();
		if (!args.isEmpty()) {
		    tabSym.put(it.toString(), new TUnit());
        }
		while (it.hasNext()) {
        	tabSym.put(it.next().toString(), new TUnit());
        }
	}

	public Type printInfix2(List<Exp> l) {
        if (l.isEmpty()) {
            return null;
        }
        Iterator<Exp> it = l.iterator();
        Type t = it.next().accept(this);
        while (it.hasNext()) {
            if(it.next().accept(this) != t){
            	System.err.println(" : expected similar types");
    			System.exit(1);
            }
        }
        return t ;
    }
	
	@Override
	public Type visit(App e) {
		String ap = ((Var)e.e).id.toString();
		if(!tabSym.containsKey(((Var)e.e).id.toString())) {
			if(ap.equals("print_int")) {
				if(!(printInfix2(e.es) instanceof TInt)){
					System.err.println(" expected a type int in " + ap);
	    			System.exit(1);
				} else {
					return new TUnit();
				}
			} else if(ap.equals("print_float")) {
				if(!(printInfix2(e.es) instanceof TFloat)){
					System.err.println(" expected a type float in " + ap);
	    			System.exit(1);
				} else {
					return new TUnit();
				}
			}
		}else {
			Type att = tabSym.get(((Var)e.e).id.toString()) ;
			if(!(printInfix2(e.es).equalsType(att))){
				System.err.println("expected a type " + att.toString());
    			System.exit(1);
			}
			return tabSym.get(((Var)e.e).id.toString());
		
		}
		
		return null;
	}

	@Override
	public Type visit(Tuple e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Type visit(LetTuple e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Type visit(Array e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Type visit(Get e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Type visit(Put e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Type visit(Unit unit) {
		// TODO Auto-generated method stub
		return null;
	}
}

