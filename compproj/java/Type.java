abstract class Type {
    private static int x = 0;
    static Type gen() {
        return new TVar("?" + x++);
    }
	public abstract boolean equalsType(Type g) ;
    
}

class TUnit extends Type {

	@Override
	public boolean equalsType(Type g) {
		return g instanceof TUnit;
	} 
}

class TBool extends Type {

	@Override
	public boolean equalsType(Type g) {
		return g instanceof TBool;
	} 
	
	@Override
    public String toString()
    {
    	return "boolean";
    }
}

class TInt extends Type {

	@Override
	public boolean equalsType(Type g) {
		return (g instanceof TInt) ;
	} 
	
	 @Override
	    public String toString()
	    {
	    	return "int";
	    }
	
}

class TFloat extends Type {

	@Override
	public boolean equalsType(Type g) {
		return g instanceof TFloat ;
	}
	
	@Override
    public String toString()
    {
    	return "float";
    }
}

class TFun extends Type { 
	
	@Override
	public boolean equalsType(Type g) {
		return g instanceof TFun ;
	}
}

class TTuple extends Type { 
	
	@Override
	public boolean equalsType(Type g) {
		return g instanceof TTuple ;
	}
}

class TArray extends Type { 
	
	@Override
	public boolean equalsType(Type g) {
		return g instanceof TArray ;
	}
}

class TVar extends Type {
    String v;
    TVar(String v) {
        this.v = v;
    }
    @Override
    public String toString() {
        return v; 
    }
    
    @Override
	public boolean equalsType(Type g) {
		return g instanceof TVar ;
	}
}

