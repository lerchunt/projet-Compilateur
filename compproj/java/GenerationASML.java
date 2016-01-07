import java.util.*;

public class GenerationASML implements Visitor {
	static String asml;
	
	public GenerationASML()
	{
		GenerationASML.asml = "let _ = \n";
	}
    public void visit(Unit e) {
        
    }

    public void visit(Bool e) {
    	GenerationASML.asml += e.b ;
    }

    public void visit(Int e) {
    	GenerationASML.asml += e.i ;
    }

    public void visit(Float e) {
    	/* definition des float au debut du programme*/
    }

    public void visit(Not e) {
    	GenerationASML.asml += "not ";
        e.e.accept(this);
    }

    public void visit(Neg e) {
    	GenerationASML.asml += "-";
        e.e.accept(this);
    }

    public void visit(Add e) {
    	GenerationASML.asml += " add ";
		e.e1.accept(this);
		GenerationASML.asml += " ";
		e.e2.accept(this);
    }

    public void visit(Sub e) {
    	GenerationASML.asml += " sub ";
		e.e1.accept(this);
		GenerationASML.asml += " ";
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
    
    public void visit(Mul e) {
        
    }

    public void visit(FDiv e){
        
    }

    public void visit(Eq e){
        e.e1.accept(this);
        GenerationASML.asml += " = ";
        e.e2.accept(this);
    }

    public void visit(LE e){
    	e.e1.accept(this);
    	GenerationASML.asml += " <= ";
        e.e2.accept(this);
    }

    public void visit(If e){
    	GenerationASML.asml += "if ";
        e.e1.accept(this);
        GenerationASML.asml += " then ( ";
        e.e2.accept(this);
        GenerationASML.asml += " ) else ( ";
        e.e3.accept(this);
        GenerationASML.asml += " ) ";
    }

    public void visit(Let e) {
    	GenerationASML.asml += "\tlet ";
        GenerationASML.asml += e.id;
        GenerationASML.asml += " = ";
        e.e1.accept(this);
        GenerationASML.asml += " in ";
        e.e2.accept(this);
        GenerationASML.asml += "\n";
    }

    public void visit(Var e){
        GenerationASML.asml += e.id;
    }


    // print sequence of identifiers 
    static <E> void printInfix(List<E> l, String op) {
        if (l.isEmpty()) {
            return;
        }
        Iterator<E> it = l.iterator();
        GenerationASML.asml += it.next();
        while (it.hasNext()) {
            GenerationASML.asml += op + it.next();
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
            GenerationASML.asml += op;
            it.next().accept(this);
        }
    }

    public void visit(LetRec e){
       
    }

    public void visit(App e){
        GenerationASML.asml += "\tcall _min_caml_";
        e.e.accept(this);
        GenerationASML.asml += " ";
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