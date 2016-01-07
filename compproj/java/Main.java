import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

public class Main {
  static public void main(String argv[]) {   
		String[] tabPath = argv[0].split("/");
		String nameFolder = tabPath[tabPath.length -1];
		int tailleName = nameFolder.length();
		nameFolder = nameFolder.split("\\.")[0];
		String pathFolder = argv[0].substring(0, argv[0].length()-tailleName);
		
    try {
      Parser p = new Parser(new Lexer(new FileReader(argv[0])));
      Exp expression = (Exp) p.parse().value;      
      assert (expression != null);

      System.out.println("------ AST ------");
      expression.accept(new PrintVisitor());
      System.out.println();
      
      System.out.println("------ ASML ------");
      expression.accept(new GenerationASML());
      System.out.println();
      
      String pathAsml = pathFolder+nameFolder+".asml";
      
      PrintWriter w = new PrintWriter( new BufferedWriter( new FileWriter(pathAsml)));
		w.print(GenerationASML.asml);
		w.close();

		new Backend(pathAsml).startBackEnd();
		
      System.out.println("------ Height of the AST ----");
      int height = Height.computeHeight(expression);
      System.out.println("using Height.computeHeight: " + height);


      ObjVisitor<Integer> v = new HeightVisitor();
      height = expression.accept(v);
      System.out.println("using HeightVisitor: " + height);

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}

