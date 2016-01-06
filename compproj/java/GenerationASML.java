import java.util.*;

public class GenerationASML implements Visitor {
	
	public GenerationASML()
	{
		System.out.println("let _ = ");
	}
    public void visit(Unit e) {
        
    }

    public void visit(Bool e) {
        System.out.print(e.b);
    }

    public void visit(Int e) {
        System.out.print(e.i);
    }

    public void visit(Float e) {
    	/* definition des float au debut du programme*/
    }

    public void visit(Not e) {
    	System.out.print("not ");
        e.e.accept(this);
    }

    public void visit(Neg e) {
    	System.out.print("-");
        e.e.accept(this);
    }

    public void visit(Add e) {
		System.out.print(" add ");
		e.e1.accept(this);
		System.out.print(" ");
		e.e2.accept(this);
    }

    public void visit(Sub e) {
    	System.out.print(" sub ");
		e.e1.accept(this);
		System.out.print(" ");
		e.e2.accept(this);
    }

    public void visit(FNeg e){
        
    }

    public void visit(FAdd e){
        
    }

    public void visit(FSub e){
        
    }

    public void visit(FMul e) {
        
    }

    public void visit(FDiv e){
        
    }

    public void visit(Eq e){
        e.e1.accept(this);
        System.out.print(" = ");
        e.e2.accept(this);
    }

    public void visit(LE e){
    	e.e1.accept(this);
        System.out.print(" <= ");
        e.e2.accept(this);
    }

    public void visit(If e){
    	System.out.print("if ");
        e.e1.accept(this);
        System.out.print(" then ( ");
        e.e2.accept(this);
        System.out.print(" ) else ( ");
        e.e3.accept(this);
        System.out.print(" ) ");
    }

    public void visit(Let e) {
    	System.out.print("let ");
        System.out.print(e.id);
        System.out.print(" = ");
        e.e1.accept(this);
        System.out.print(" in ");
        e.e2.accept(this);
        System.out.println("");
    }

    public void visit(Var e){
        System.out.print(e.id);
    }


    // print sequence of identifiers 
    static <E> void printInfix(List<E> l, String op) {
        if (l.isEmpty()) {
            return;
        }
        Iterator<E> it = l.iterator();
        System.out.print(it.next());
        while (it.hasNext()) {
            System.out.print(op + it.next());
        }
    }

    // print sequence of Exp
    void printInfix2(List<Exp> l, String op) {
        if (l.isEmpty()) {
            return;
        }
        Iterator<Exp> it = l.iterator();
        it.next().accept(this);
        while (it.hasNext()) {
            System.out.print(op);
            it.next().accept(this);
        }
    }

    public void visit(LetRec e){
       
    }

    public void visit(App e){
        System.out.print("call _min_caml_");
        e.e.accept(this);
        System.out.print(" ");
        printInfix2(e.es, " ");
    }

    public void visit(Tuple e){
      
    }

    public void visit(LetTuple e){
        
    }

    public void visit(Array e){
        
    }

    public void visit(Get e){
        
    }

    public void visit(Put e){
        
    }
}