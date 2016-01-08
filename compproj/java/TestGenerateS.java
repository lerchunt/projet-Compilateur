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
		    String retour = expression.accept(new GenerationS());
		    System.out.println(retour);    
	    } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
