import java.util.LinkedList;
import java.util.List;

abstract class Exp implements Cloneable {
	String registreDeRetour  = "r0";
    abstract void accept(Visitor v);
    
    // ********* Pour typing Apo *********************************
    LinkedList<Environnement> env = new LinkedList<Environnement>();
    Type typeAttendu;
    protected class Environnement {
    	Id nom;
    	Type t;
    	public Environnement(Id nom, Type t) {
    		this.nom = nom;
    		this.t = t;
    	}
    }
    public void initiateEnv() {
    	TFun tP1 = new TFun();
    	tP1.typeRetour = new TUnit();
    	tP1.typeArgs = new LinkedList<Type>();
    	tP1.typeArgs.add(new TInt());
    	/* TO DO Print_string
    	TFun tP2 = new TFun();
    	tP1.typeRetour = new TUnit();
    	tP1.typeArgs.add(new TInt());
    	*/
    	this.env.add(new Environnement(new Id("print_int"), tP1));
    }
    // ***********************************************************

    abstract <E> E accept(ObjVisitor<E> v);
     
    @Override
    public Object clone() {
    	Object o = null;
    	try {
      		// On récupère l'instance à renvoyer par l'appel de la 
      		// méthode super.clone()
      		o = super.clone();
    	} catch(CloneNotSupportedException cnse) {
      		// Ne devrait jamais arriver car nous implémentons 
      		// l'interface Cloneable
      		cnse.printStackTrace(System.err);
	    }
	    // on renvoie le clone
	    return o;
  	}
    
    public boolean isVIFB()
    {
    	return false ;
    }
    
    public boolean isVar()
    {
    	return false ;
    }
    
    public boolean isInt()
    {
    	return false ;
    }
    
    public boolean isFloat()
    {
    	return false ;
    } 
    
    public boolean isBool()
    {
    	return false ;
    }

    public boolean isOpBin() {
    	return false;
    }

    public boolean isOpUn() {
    	return false;
    }

	public void addEnv(Id id, Type t) {
		this.env.addFirst(new Environnement(id,t));
	}
}

/**
 * opération binaire
 * @author apollinaire
 *
 */
abstract class OpBin extends Exp {
	Exp e1;
    Exp e2;
    
    OpBin(Exp e1, Exp e2) {
        this.e1 = e1;
        this.e2 = e2;
    }
    
    @Override
    public boolean isOpBin() {
    	return true;
    }
}

/**
 * operation unaire
 */
abstract class OpUn extends Exp {
	Exp e;
    
    OpUn(Exp e) {
        this.e = e;
    }

    @Override
    public boolean isOpUn() {
    	return true;
    }
}

class Unit extends Exp {
    <E> E accept(ObjVisitor<E> v) {
        return v.visit(this);
    }

    void accept(Visitor v) {
        v.visit(this);
    }
}

class Bool extends Exp {
    boolean b;

    Bool(boolean b) {
        this.b = b;
    }
    
    @Override
    public boolean isBool()
    {
    	return true ;
    }
    
    @Override
    public String toString()
    {
    	return Boolean.toString(b);
    }
    
    <E> E accept(ObjVisitor<E> v) {
        return v.visit(this);
    }

    void accept(Visitor v) {
        v.visit(this);
    }

    }

class Int extends Exp {
    int i;

    Int(int i) {
        this.i = i;
    }
    
    @Override
    public boolean isInt()
    {
    	return true ;
    }
    
    @Override
    public String toString()
    {
    	return Integer.toString(i);
    }

    <E> E accept(ObjVisitor<E> v) {
        return v.visit(this);
    }
    void accept(Visitor v) {
        v.visit(this);
    }
}

class Float extends Exp {
    float f;

    Float(float f) {
        this.f = f;
    }
    
    @Override
    public boolean isFloat()
    {
    	return true ;
    }
    
    @Override
    public String toString()
    {
    	return String.valueOf(f);
    }
    
    
    <E> E accept(ObjVisitor<E> v) {
        return v.visit(this);
    }
    void accept(Visitor v) {
        v.visit(this);
    }
}

class Not extends OpUn {
    Not(Exp e) {
        super(e);
    }

    <E> E accept(ObjVisitor<E> v) {
        return v.visit(this);
    }
    void accept(Visitor v) {
        v.visit(this);
    }
    
    @Override
    public String toString()
    {
    	return "not " + e;
    }
}

class Neg extends OpUn {
    Neg(Exp e) {
        super(e);
    }

    <E> E accept(ObjVisitor<E> v) {
        return v.visit(this);
    }
    void accept(Visitor v) {
        v.visit(this);
    }
    
    @Override
    public String toString()
    {
    	return "-" + e;
    }
}

class Add extends OpBin {
	
    Add(Exp e1, Exp e2) {
        super(e1,e2);
    }

    <E> E accept(ObjVisitor<E> v) {
        return v.visit(this);
    }
    void accept(Visitor v) {
        v.visit(this);
    }
}

class Sub extends OpBin {

    Sub(Exp e1, Exp e2) {
        super(e1,e2);
    }

    <E> E accept(ObjVisitor<E> v) {
        return v.visit(this);
    }
    void accept(Visitor v) {
        v.visit(this);
    }
}

class FNeg extends OpUn {

    FNeg(Exp e) {
        super(e);
    }

    <E> E accept(ObjVisitor<E> v) {
        return v.visit(this);
    }
    void accept(Visitor v) {
        v.visit(this);
    }
}

class FAdd extends OpBin {

    FAdd(Exp e1, Exp e2) {
        super(e1,e2);
    }

    <E> E accept(ObjVisitor<E> v) {
        return v.visit(this);
    }
    void accept(Visitor v) {
        v.visit(this);
    }
}

class FSub extends OpBin {

    FSub(Exp e1, Exp e2) {
        super(e1,e2);
    }

    <E> E accept(ObjVisitor<E> v) {
        return v.visit(this);
    }
    void accept(Visitor v) {
        v.visit(this);
    }
}

class FMul extends OpBin {

    FMul(Exp e1, Exp e2) {
        super(e1,e2);
    }

    <E> E accept(ObjVisitor<E> v) {
        return v.visit(this);
    }
    void accept(Visitor v) {
        v.visit(this);
    }
}

class Mul extends OpBin {

    Mul(Exp e1, Exp e2) {
    	super(e1,e2);
    }

    <E> E accept(ObjVisitor<E> v) {
        return v.visit(this);
    }
    void accept(Visitor v) {
        v.visit(this);
    }
}

class FDiv extends OpBin {

    FDiv(Exp e1, Exp e2) {
    	super(e1,e2);
    }

    <E> E accept(ObjVisitor<E> v) {
        return v.visit(this);
    }
    void accept(Visitor v) {
        v.visit(this);
    }
}

class Eq extends OpBin { 

    Eq(Exp e1, Exp e2) {
    	super(e1,e2);
    }

    <E> E accept(ObjVisitor<E> v) {
        return v.visit(this);
    }
    void accept(Visitor v) {
        v.visit(this);
    }
    
    @Override
    public String toString()
    {
    	return e1.toString() + " = " + e2.toString();
    }
}

class LE extends OpBin { 

    LE(Exp e1, Exp e2) {
    	super(e1,e2);
    }

    <E> E accept(ObjVisitor<E> v) {
        return v.visit(this);
    }
    void accept(Visitor v) {
        v.visit(this);
    }  
    
    @Override
    public String toString()
    {
    	return e1.toString() + " <= " + e2.toString();
    }
}

class If extends Exp {
    Exp e1;
    Exp e2;
    Exp e3;

    If(Exp e1, Exp e2, Exp e3) {
        this.e1 = e1;
        this.e2 = e2;
        this.e3 = e3;
    }

    <E> E accept(ObjVisitor<E> v) {
        return v.visit(this);
    }
    void accept(Visitor v) {
        v.visit(this);
    }
}

class Let extends Exp {
    Id id;
    Type t;
    Exp e1;
    Exp e2;	

    Let(Id id, Type t, Exp e1, Exp e2) {
        this.id = id;
        this.t = t;
        this.e1 = e1;
        this.e2 = e2;
    }
    
    @Override
    public boolean isVIFB()
    {
    	return true ;
    }
    
    <E> E accept(ObjVisitor<E> v) {
        return v.visit(this);
    }
    void accept(Visitor v) {
        v.visit(this);
    }
}

class Var extends Exp {
    Id id;
    
    int nbOccurence = 1;

    Var(Id id) {
        this.id = id;
    }
    
    @Override
    public String toString()
    {
    	return id.toString();
    }
    
    public void inc() {
    	this.nbOccurence ++;
    }
    
    public void dec() {
    	this.nbOccurence --;
    }
    

    <E> E accept(ObjVisitor<E> v) {
        return v.visit(this);
    }
    void accept(Visitor v) {
        v.visit(this);
    }
    
    @Override
    public boolean isVar()
    {
    	return true ;
    }

	public Type rechercheEnv() {
		for (Environnement e : env) {
			if (e.nom.equals(id)) {
				return e.t;
			}
		}
		return null;
	}
}

class LetRec extends Exp {
    FunDef fd;
    Exp e;

    LetRec(FunDef fd, Exp e) {
        this.fd = fd;
        this.e = e;
    }

    <E> E accept(ObjVisitor<E> v) {
        return v.visit(this);
    }
    void accept(Visitor v) {
        v.visit(this);
    }
	public Type isDefineAs() {
		for (Environnement e : env) {
			if (e.nom.equals(fd.id)) {
				return e.t;
			}
		}
		return null;
	}
}

class App extends Exp {
    Exp e;
    List<Exp> es;

    App(Exp e, List<Exp> es) {
        this.e = e;
        this.es = es;
    }

    <E> E accept(ObjVisitor<E> v) {
        return v.visit(this);
    }
    void accept(Visitor v) {
        v.visit(this);
    }
}

class Tuple extends Exp {
    List<Exp> es;

    Tuple(List<Exp> es) {
        this.es = es;
    }

    <E> E accept(ObjVisitor<E> v) {
        return v.visit(this);
    }

    void accept(Visitor v) {
        v.visit(this);
    }
}

class LetTuple extends Exp {
    List<Id> ids;
    List<Type> ts;
    Exp e1;
    Exp e2;

    LetTuple(List<Id> ids, List<Type> ts, Exp e1, Exp e2) {
        this.ids = ids;
        this.ts = ts;
        this.e1 = e1;
        this.e2 = e2;
    }

    <E> E accept(ObjVisitor<E> v) {
        return v.visit(this);
    }
    void accept(Visitor v) {
        v.visit(this);
    }
}

class Array extends Exp {
    Exp e1;
    Exp e2;

    Array(Exp e1, Exp e2) {
        this.e1 = e1;
        this.e2 = e2;
    }

    <E> E accept(ObjVisitor<E> v) {
        return v.visit(this);
    }
    void accept(Visitor v) {
        v.visit(this);
    }
}

class Get extends Exp {
    Exp e1;
    Exp e2;

    Get(Exp e1, Exp e2) {
        this.e1 = e1;
        this.e2 = e2;
    }
 
    <E> E accept(ObjVisitor<E> v) {
        return v.visit(this);
    }

    void accept(Visitor v) {
        v.visit(this);
    }
}

class Put extends Exp {
    Exp e1;
    Exp e2;
    Exp e3;

    Put(Exp e1, Exp e2, Exp e3) {
        this.e1 = e1;
        this.e2 = e2;
        this.e3 = e3;
    }

    <E> E accept(ObjVisitor<E> v) {
        return v.visit(this);
    }

    void accept(Visitor v) {
        v.visit(this);
    }
}

class FunDef{
    Id id;
    Type type;
    List<Id> args;
    Exp e;

    FunDef(Id id, Type t, List<Id> args, Exp e) {
        this.id = id;
        this.type = t; // is it the right type? 
        //this.type = t;
        this.args = args;
        this.e = e;
    }
 
}
 
