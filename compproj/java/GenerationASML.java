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
    	String s = String.format("%.2f", e.f);
    	s=s.replace(',','.');
    	GenerationASML.asml += s ;
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
    	GenerationASML.asml += " fadd ";
		e.e1.accept(this);
		GenerationASML.asml += " ";
		e.e2.accept(this);      
    }

    public void visit(FSub e){
    	GenerationASML.asml += " fsub ";
		e.e1.accept(this);
		GenerationASML.asml += " ";
		e.e2.accept(this); 
    }

    public void visit(FMul e) {
    	GenerationASML.asml += " fmul ";
		e.e1.accept(this);
		GenerationASML.asml += " ";
		e.e2.accept(this);
    }
    
    public void visit(Mul e) {
    	GenerationASML.asml += " mul ";
		e.e1.accept(this);
		GenerationASML.asml += " ";
		e.e2.accept(this);
    }

    public void visit(FDiv e){
    	GenerationASML.asml += " fdiv ";
		e.e1.accept(this);
		GenerationASML.asml += " ";
		e.e2.accept(this);
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
    	String haut ="";
        if((e.e1).isFloat())
        {
        	haut = "let _" +e.id;
        	String s = String.format("%.2f",((Float)e.e1).f);
        	s=s.replace(',','.');
            haut += " = " + s + "\n";
            GenerationASML.asml = haut + GenerationASML.asml ;
            GenerationASML.asml += "\tlet ";
        	GenerationASML.asml += e.id;
            GenerationASML.asml += " = _" + e.id;
            GenerationASML.asml += " in ";
            e.e2.accept(this);
            GenerationASML.asml += "\n";
        } else {
        	GenerationASML.asml += "\tlet ";
        	GenerationASML.asml += e.id;
            GenerationASML.asml += " = ";
            e.e1.accept(this);
            GenerationASML.asml += " in ";
            e.e2.accept(this);
            GenerationASML.asml += "\n";
        } 
    }
        

    public void visit(Var e){
        GenerationASML.asml += e.id;
    }


    // print sequence of identifiers 
    static <E> String printInfix(List<E> l, String op) {
        if (l.isEmpty()) {
            return "";
        }
        String t = "" ;
        Iterator<E> it = l.iterator();
        t += it.next();
        while (it.hasNext()) {
            t += op + it.next();
        }
        return t ;
        
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
      /*String haut = "let _" + e.fd.id + " = \n";
      haut += printInfix(e.fd.args, " ");
	  e.fd.e.accept(this);
	  haut += " in ";
	  e.e.accept(this);
      GenerationASML.asml = haut + GenerationASML.asml;
      System.out.print(haut);*/
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