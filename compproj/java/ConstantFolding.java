import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;


public class ConstantFolding implements ObjVisitor<Exp> {

	private static Hashtable<String, Exp> tabSym = new Hashtable<String, Exp>();
	public static Hashtable<String, Bool> tabVar = new Hashtable<String, Bool>();


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
		return e.accept(this);
	}

	@Override
	public Exp visit(Neg e) {
		return e.accept(this);
	}

	@Override
	public Exp visit(Add e) {
		if(tabSym.containsKey(((Var)e.e1).id.toString()))
		{
			e.e1 = tabSym.get(((Var)e.e1).id.toString()) ;
		} 
		if(tabSym.containsKey(((Var)e.e2).id.toString()))
		{
			e.e2 = tabSym.get(((Var)e.e2).id.toString()) ;
		} 
		e.e1 = e.e1.accept(this);
		e.e2 = e.e2.accept(this);
		return e;
	}

	@Override
	public Exp visit(Sub e) {
		if(tabSym.containsKey(((Var)e.e1).id.toString()))
		{
			e.e1 = tabSym.get(((Var)e.e1).id.toString()) ;
		} 
		if(tabSym.containsKey(((Var)e.e2).id.toString()))
		{
			e.e2 = tabSym.get(((Var)e.e2).id.toString()) ;
		} 
		e.e1 = e.e1.accept(this);
		e.e2 = e.e2.accept(this);
		return e;
	}

	@Override
	public Exp visit(FNeg e) {
		return e.accept(this);
	}

	@Override
	public Exp visit(FAdd e) {
		if(tabSym.containsKey(((Var)e.e1).id.toString()))
		{
			e.e1 = tabSym.get(((Var)e.e1).id.toString()) ;
		} 
		if(tabSym.containsKey(((Var)e.e2).id.toString()))
		{
			e.e2 = tabSym.get(((Var)e.e2).id.toString()) ;
		} 
		e.e1 = e.e1.accept(this);
		e.e2 = e.e2.accept(this);
		return e;
	}

	@Override
	public Exp visit(FSub e) {
		if(tabSym.containsKey(((Var)e.e1).id.toString()))
		{
			e.e1 = tabSym.get(((Var)e.e1).id.toString()) ;
		} 
		if(tabSym.containsKey(((Var)e.e2).id.toString()))
		{
			e.e2 = tabSym.get(((Var)e.e2).id.toString()) ;
		} 
		e.e1 = e.e1.accept(this);
		e.e2 = e.e2.accept(this);
		return e;
	}

	@Override
	public Exp visit(FMul e) {
		if(tabSym.containsKey(((Var)e.e1).id.toString()))
		{
			e.e1 = tabSym.get(((Var)e.e1).id.toString()) ;
		} 
		if(tabSym.containsKey(((Var)e.e2).id.toString()))
		{
			e.e2 = tabSym.get(((Var)e.e2).id.toString()) ;
		}
		e.e1 = e.e1.accept(this);
		e.e2 = e.e2.accept(this);;
		return e;
	}

	@Override
	public Exp visit(Mul e) {
		if(tabSym.containsKey(((Var)e.e1).id.toString()))
		{
			e.e1 = tabSym.get(((Var)e.e1).id.toString()) ;
		} 
		if(tabSym.containsKey(((Var)e.e2).id.toString()))
		{
			e.e2 = tabSym.get(((Var)e.e2).id.toString()) ;
		} 
		e.e1 = e.e1.accept(this);
		e.e2 = e.e2.accept(this);
		return e;
	}

	@Override
	public Exp visit(FDiv e) {
		if(tabSym.containsKey(((Var)e.e1).id.toString()))
		{
			e.e1 = tabSym.get(((Var)e.e1).id.toString()) ;
		} 
		if(tabSym.containsKey(((Var)e.e2).id.toString()))
		{
			e.e2 = tabSym.get(((Var)e.e2).id.toString()) ;
		} 
		e.e1 = e.e1.accept(this);
		e.e2 = e.e2.accept(this);
		return e;
	}

	@Override
	public Exp visit(Eq e) {
		if(tabSym.containsKey(((Var)e.e1).id.toString()))
		{
			e.e1 = tabSym.get(((Var)e.e1).id.toString()) ;
		} 
		if(tabSym.containsKey(((Var)e.e2).id.toString()))
		{
			e.e2 = tabSym.get(((Var)e.e2).id.toString()) ;
		} 
		e.e1 = e.e1.accept(this);
		e.e2 = e.e2.accept(this);
		return e;
	}

	@Override
	public Exp visit(LE e) {
		if(tabSym.containsKey(((Var)e.e1).id.toString()))
		{
			e.e1 = tabSym.get(((Var)e.e1).id.toString()) ;
		} 
		if(tabSym.containsKey(((Var)e.e2).id.toString()))
		{
			e.e2 = tabSym.get(((Var)e.e2).id.toString()) ;
		} 
		e.e1 = e.e1.accept(this);
		e.e2 = e.e2.accept(this);
		return e;
	}

	@Override
	public Exp visit(If e) {
		if(tabSym.containsKey(((Var)e.e1).id.toString()))
		{
			e.e1 = tabSym.get(((Var)e.e1).id.toString()) ;
		} 
		if(tabSym.containsKey(((Var)e.e2).id.toString()))
		{
			e.e2 = tabSym.get(((Var)e.e2).id.toString()) ;
		} 
		if(tabSym.containsKey(((Var)e.e3).id.toString()))
		{
			e.e3 = tabSym.get(((Var)e.e3).id.toString()) ;
		}
		e.e1 = e.e1.accept(this);
		e.e2 = e.e2.accept(this);
		e.e3 = e.e3.accept(this);
		return e;
	}

	@Override
	public Exp visit(Let e) {
		Exp exp ;
		if(!e.e1.isVIFB()){
			if(!tabSym.containsKey(e.id.toString())) {
				exp = e.e1.accept(this) ; 
				tabSym.put(e.id.toString(), exp);
			} else {
				exp = e.e1.accept(this) ; 
				tabSym.remove(e.id.toString());
				tabSym.put(e.id.toString(), exp);
			}
		} else {
			e.e1 = e.e1.accept(this);
		}
		e.e2 = e.e2.accept(this);
		return e;
	}

	@Override
	public Exp visit(Var e) {
		return e;
	}

	@Override
	public Exp visit(LetRec e) {
		e.e = e.e.accept(this);
		e.fd.e = e.fd.e.accept(this);
		return e;
	}
	
	public List<Exp> printIntFix(List<Exp> args) {
		Iterator<Exp> it = args.iterator();
		if (!args.isEmpty()) {
		    if(tabSym.containsKey(it.toString())) {
		    	it = (Iterator<Exp>) tabSym.get(it.toString());
		    }
	    }
		while (it.hasNext()) {
	    	if(tabSym.containsKey(it.next().toString())) {
		    	it = (Iterator<Exp>) tabSym.get(it.next().toString());
		    }
	    }
		args = (List<Exp>) it ;
		return args;
}

	@Override
	public Exp visit(App e) {
			//e.es = printIntFix(e.es);
		return e;
	}

	@Override
	public Exp visit(Tuple e) {
		for (Exp exp : e.es) {
			exp = exp.accept(this);
		}
		return e;
	}

	@Override
	public Exp visit(LetTuple e) {
		e.e1 = e.e1.accept(this);
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

}
