
public class TypeChecking implements ObjVisitor<Type> {

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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Type visit(Var e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Type visit(LetRec e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Type visit(App e) {
		// TODO Auto-generated method stub
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

	@Override
	public Type visit(FunDef e) {
		// TODO Auto-generated method stub
		return null;
	}

}

