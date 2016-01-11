import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;


public class TypeChecking implements ObjVisitor<Type> {
	
	private Hashtable<String, Type> tabSym = new Hashtable<String, Type>();

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
		if(e.e.accept(this) instanceof TBool)
		{
			return e.e.accept(this);
		}
		System.err.println(e.e + " : expected a type boolean");
		System.exit(1);
		return null;
	}

	@Override
	public Type visit(Neg e) {
		if(e.e.accept(this) instanceof TInt || e.e.accept(this) instanceof TFloat)
		{
			return e.e.accept(this);
		}
		System.err.println(e.e + " : expected a type float or int ");
		System.exit(1);
		return null;
	}

	@Override
	public Type visit(Add e) {
		if(e.e1.accept(this) instanceof TInt && e.e2.accept(this) instanceof TInt)
		{
			return e.e1.accept(this);
		}
		System.err.println(e.e1.toString() + " + " + e.e2.toString() + " : expected a type int ");
		System.exit(1);
		return null ;
	}

	@Override
	public Type visit(Sub e) {
		if(e.e1.accept(this) instanceof TInt && e.e2.accept(this) instanceof TInt)
		{
			return e.e1.accept(this);
		}
		System.err.println(e.e1 + " - " + e.e2 + " : expected a type int ");
		System.exit(1);
		return null ;
	}

	@Override
	public Type visit(FNeg e) {
		if(e.e.accept(this) instanceof TFloat )
		{
			return e.e.accept(this);
		}
		System.err.println(e.e + " : expected a type float ");
		System.exit(1);
		return null ;
	}

	@Override
	public Type visit(FAdd e) {
		if(e.e1.accept(this) instanceof TFloat && e.e2.accept(this) instanceof TFloat)
		{
			return e.e1.accept(this);
		}
		System.err.println(e.e1 + " +. " + e.e2 + " : expected a type float ");
		System.exit(1);
		return null ;
	}

	@Override
	public Type visit(FSub e) {
		if(e.e1.accept(this) instanceof TFloat && e.e2.accept(this) instanceof TFloat)
		{
			return e.e1.accept(this);
		}
		System.err.println(e.e1 + " -. " + e.e2 + " : expected a type float ");
		System.exit(1);
		return null ;
	}

	@Override
	public Type visit(FMul e) {
		if(e.e1.accept(this) instanceof TFloat && e.e2.accept(this) instanceof TFloat)
		{
			return e.e1.accept(this);
		}
		System.err.println(e.e1 + " *. " + e.e2 + " : expected a type float ");
		System.exit(1);
		return null ;
	}

	@Override
	public Type visit(Mul e) {
		if(e.e1.accept(this) instanceof TInt && e.e2.accept(this) instanceof TInt)
		{
			return e.e1.accept(this);
		}
		System.err.println(e.e1 + " * " + e.e2 + " : expected a type int ");
		System.exit(1);
		return null ;
	}

	@Override
	public Type visit(FDiv e) {
		if(e.e1.accept(this) instanceof TFloat && e.e2.accept(this) instanceof TFloat)
		{
			return e.e1.accept(this);
		}
		System.err.println(e.e1 + " /. " + e.e2 + " : expected a type float ");
		System.exit(1);
		return null ;
	}

	@Override
	public Type visit(Eq e) {
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
		System.err.println(e.e1 + " = " + e.e2 + " : expected a similar type");
		System.exit(1);
		return null ;
	}

	@Override
	public Type visit(LE e) {
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
		System.err.println(e.e1 + " <= " + e.e2 + " : expected a similar type ");
		System.exit(1);
		return null ;
	}

	@Override
	public Type visit(If e) {
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
		System.err.println("if " + e.e1.toString() + " then " + e.e2.toString() + " else " + e.e3.toString() + " : expected a type bool then similar ");
		System.exit(1);
		return null ;
	}

	@Override
	public Type visit(Let e) {
		if(!e.e1.isVar()) {
			if(!tabSym.containsKey(e.id.toString())) {
				Type exp = e.e1.accept(this) ; 
				tabSym.put(e.id.toString(), exp);
			} else {
				tabSym.remove(e.id.toString());
				Type exp = e.e1.accept(this) ; 
				tabSym.put(e.id.toString(), exp);
			}
		}
		e.e2.accept(this);
	//Enumeration r = tabSym.elements();
	//while (r.hasMoreElements()) System.out.println(r.nextElement().toString());
		
		return null;
	}

	@Override
	public Type visit(Var e) {
		if(!tabSym.containsKey(e.id.toString())) {
			System.err.println(e.id.toString() + " : doesn't exist");
			System.exit(1);
		} else {
			return tabSym.get(e.id.toString());
		}
		return null;
	}

	@Override
	public Type visit(LetRec e) {
		// TODO Auto-generated method stub
		return null;
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
		if(ap.equals("print_int")) {
			if(!(printInfix2(e.es) instanceof TInt)){
				System.err.println(" expected a type int");
    			System.exit(1);
			}
		} else if(ap.equals("print_float")) {
			if(!(printInfix2(e.es) instanceof TFloat)){
				System.err.println(" expected a type float");
    			System.exit(1);
			}
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

