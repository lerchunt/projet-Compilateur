import static org.junit.Assert.*;
import java.io.FileNotFoundException;
import java.io.FileReader;

import org.junit.Test;

public class TestGenerateS {

	@Test
	public void testPrintNewLine() {
		Parser p;
		try {
			p = new Parser(new Lexer(new FileReader("/home/julie/Documents/ProjetCompil/high5/compproj/minml/print_newline.ml")));
			Exp expression = (Exp) p.parse().value; 
			String retour = this.head();
		    retour += expression.accept(new GenerationS());
		    retour += this.end();
		    
		    System.out.println(retour);    
	    } catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void testPrint() {
		Parser p;
		try {
			p = new Parser(new Lexer(new FileReader("/home/julie/Documents/ProjetCompil/high5/compproj/minml/print.ml")));
			Exp expression = (Exp) p.parse().value;
		    String retour = this.head();
			retour += expression.accept(new GenerationS());
			retour += this.end();
		    System.out.println(retour);    
	    } catch (Exception e) {
			e.printStackTrace();
		}
	}
	public String head(){
		return "\t.text\n\t.global _start\n_start:\n";
	}
	public String end(){
		return "\tbl\tmin_caml_exit\n";
	}
}
