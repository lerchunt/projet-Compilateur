import java.util.LinkedList;

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

    @Override
    public String toString() {
        return "unit"; 
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
	LinkedList<Type> typeArgs = new LinkedList<Type>();
	Type typeRetour;
	
	@Override
	public boolean equalsType(Type g) {
		return g instanceof TFun ;
	}
    @Override
    public String toString() {
        return "function"; 
    }
}

class TTuple extends Type { 	
	LinkedList<Type> typeArgs = new LinkedList<Type>();

	String v;
    TTuple(String v) {
        this.v = v;
    }
    
    TTuple(LinkedList<Type> A) {
        this.typeArgs = A;
    }
    
	@Override
	public boolean equalsType(Type g) {
		return g instanceof TTuple ;
	}
    @Override
    public String toString() {
        return "tuple"; 
    }
}

class TArray extends Type { 
	Type typeParamArray;
	
	public TArray(){
	}
	
	public TArray(Type t){
		this.typeParamArray = t ;
	}
	
	@Override
	public boolean equalsType(Type g) {
		return g instanceof TArray ;
	}
    @Override
    public String toString() {
        return "array"; 
    }
}

class TVar extends Type {
	// only for an equation with TArray
	Type typeParamArray;
	LinkedList<Type> typeArgs = new LinkedList<Type>();
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
    
    @Override
    public boolean equals(Object obj) {
        if (obj==this) {
            return true;
        }
 
        if (obj instanceof TVar) {
            TVar other = (TVar) obj;
 
            if (!this.v.equals(other.v)) {
                return false; 
            }

            return true;
        }
        return false;
    }
}

