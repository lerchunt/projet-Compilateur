import java.util.*;

public class GenerationASML implements Visitor {
	
	public GenerationASML()
	{
		System.out.println("let _ = ");
	}
    public void visit(Unit e) {
        
    }

    public void visit(Bool e) {
       
    }

    public void visit(Int e) {
        
    }

    public void visit(Float e) {
        
    }

    public void visit(Not e) {
        
    }

    public void visit(Neg e) {
        
    }

    public void visit(Add e) {
        
    }

    public void visit(Sub e) {
       
    }

    public void visit(FNeg e){
        
    }

    public void visit(FAdd e){
        
    }

    public void visit(FSub e){
        
    }

    public void visit(FMul e) {
        
    }
    
    public void visit(Mul e) {
        
    }

    public void visit(FDiv e){
        
    }

    public void visit(Eq e){
        
    }

    public void visit(LE e){
        
    }

    public void visit(If e){
   
    }

    public void visit(Let e) {
        
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
        System.out.print("\t call _min_caml_");
        e.e.accept(this);
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