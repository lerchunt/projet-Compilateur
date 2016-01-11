import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class Main {
	static String Path = "";
	static String InName = "";
	static String OutName = "";
	static String Version = "1";
	static public void main(String argv[]) {   
		// gestion des options

		boolean run = true;
		boolean stopAfterTypeChecking = false;
		boolean printAsml = false;
		for(int i =0; i< argv.length; i++) {
			switch (argv[i]) {
			case "-o":
				OutName = argv[i+1];
				break;
			case "-h":
				try {
					ReadFile("../../Doc/help.txt");
				} catch (IOException e) {
					e.printStackTrace();
				}
				run = false;
				break;
			case "-v":
				System.out.println("Version : "+Version);
				run = false;
				break;
			case "-t":
				stopAfterTypeChecking = true;
				break;
			case "-asml":
				printAsml = true;
				break;
			default:
			}
		}

		if (run) {

			String[] tabPath = argv[argv.length-1].split("/");
			String nameFolder = tabPath[tabPath.length -1];
			int tailleName = nameFolder.length();

			InName = nameFolder.split("\\.")[0];
			if (OutName == ""){
				OutName=InName;
			}
			Path = argv[argv.length-1].substring(0, argv[argv.length-1].length()-tailleName);

			try {
				Parser p = new Parser(new Lexer(new FileReader(argv[argv.length-1])));
				Exp expression = (Exp) p.parse().value;      
				assert (expression != null);
				
				System.out.println("------ AST ------");
				expression.accept(new PrintVisitor());
				System.out.println();
				System.out.println("------ TYPE CHECKING ------");
				expression.accept(new TypeChecking());
				System.out.println("OK");

				// Type Checking
				if (!stopAfterTypeChecking) {
					expression  = expression.accept(new KNormalization());
					System.out.println("------ AST Knorm ------");
					expression.accept(new PrintVisitor());
					System.out.println();
					expression = expression.accept(new AlphaConversion());
					System.out.println("------ AST AlphaConv ------");
					expression.accept(new PrintVisitor());
					System.out.println();
					System.out.println("------ ASML ------");
					expression.accept(new GenerationASML());
					System.out.println();

					String pathAsml = Path+InName+".asml";

					PrintWriter w = new PrintWriter( new BufferedWriter( new FileWriter(pathAsml)));
					w.print(GenerationASML.asml);
					w.close();

					// option -asml
					if (printAsml) {
						ReadFile(pathAsml);
					}

					new Backend(pathAsml);
					// supprimer le fichier asml créé.
					new File(pathAsml).delete();
				}
				System.out.println("------ Height of the AST ----");
				int height = Height.computeHeight(expression);
				System.out.println("using Height.computeHeight: " + height);


				ObjVisitor<Integer> v = new HeightVisitor();
				height = expression.accept(v);
				System.out.println("using HeightVisitor: " + height);

			} catch (Exception e) {
				System.err.println(e.getMessage());
				System.exit(1);
			}
		}
	}
	private static void ReadFile(String path) throws IOException {
		InputStream fl=new FileInputStream(path); 
		InputStreamReader read=new InputStreamReader(fl);
		BufferedReader buffer=new BufferedReader(read);
		String ligne;
		while ((ligne=buffer.readLine())!=null){
			System.out.println(ligne);
		}
		buffer.close(); 
	}
}

