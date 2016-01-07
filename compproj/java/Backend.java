

import java.io.*;
import java.util.Hashtable;
import java.util.LinkedList;

public class Backend {
	String pathFolder;
	String nameFolder;
	Hashtable<String,String> variables = new Hashtable<String,String>();
	
	public Backend (String path) { 

		String[] tabPath = path.split("/");
		nameFolder = tabPath[tabPath.length -1];
		int tailleName = nameFolder.length();
		nameFolder = nameFolder.split("\\.")[0];
		this.pathFolder = path.substring(0, path.length()-tailleName);
		
		convert(path);
	}
	
	public void convert(String path) {
		 
		
		String retour;
		retour = String.format("\t%s\n", ".text");
		
		LinkedList<String[]> strMinCaml = new LinkedList<String[]>();
	 
		try{
			InputStream fl=new FileInputStream(path); 
			InputStreamReader read=new InputStreamReader(fl);
			BufferedReader buffer=new BufferedReader(read);
			String ligne;
			while ((ligne=buffer.readLine())!=null){
				ligne = ligne.trim();
				String[] tmp = ligne.split(" ", '\t');
				strMinCaml.add(tmp);
				
				// debug
				// System.out.println("#"+ligne+"#");
			}
			buffer.close(); 
			
			int numLigne = 0;

			int nbRegVar=4;
			for (String[] l : strMinCaml){
				int longueur = l[1].length();
				// vérification 1ère ligne
				if (numLigne == 0) {
					assert(l[0] == "let");
					assert(l[1] == "_");
					assert(l[2] == "=");
					retour = String.format("%s\t%s\n%s\n", retour, ".global _start", "_start:");
				} else {
					String toWrite="";
					switch (l[0]) {
						case "call":
							toWrite = print_ligne(1,l[1].substring(9, longueur),l);
							retour = String.format("%s%s\n", retour, toWrite );
							toWrite = "";
							
							break;
							
						 case "let":
							 if (l[3].equals("call")){
								toWrite = print_ligne(4,l[4].substring(9, 19),l);
								retour = String.format("%s%s\n", retour, toWrite );
								toWrite = "";
							 } 
							 else if(l[3].equals("add") || l[3].equals("sub") || l[3].equals("mul")){
								 
							 }
							 else {
								affectRegistre(l[1],l[3],nbRegVar);
								toWrite = "mov\tr"+nbRegVar+",#"+l[3];
								retour = String.format("%s\t%s\n", retour, toWrite );
								toWrite = "";
								
								nbRegVar++;
							 }
								
							break;
							
						default:
					}
				}
				numLigne ++;
				
			}
			retour = String.format("%s\t%s\n", retour, "bl\tmin_caml_exit");

			PrintWriter w = new PrintWriter( new BufferedWriter( new FileWriter(this.pathFolder+this.nameFolder+".s")));
			w.print(retour);
			w.close();
			System.out.println(retour);
		}		
		catch (Exception e){
			System.out.println(e.toString());
		}
	}
	
	
	private void Affiche(String[] l){
		System.out.print(l.length);
		for (int i =0; i<l.length; i++) {
			System.out.print(l[i]);
			
		}
		System.out.print("\n");
	}


	
	private void affectRegistre(String Var, String num,int nb){
		String registre = String.format("r%d", nb);
		this.variables.put(Var, registre);
	}
	
	private String opArithm(String operateur, String r1, String r2){
		String toWrite="";
		toWrite = String.format("\t%s\tr0,%s,%s\n",operateur, r1, r2);
		return toWrite;
	}
	
	
	private String print_ligne(int col, String methode, String[] l){
		String toWrite="";
		if (l[col].length() >=9 ) {
			if (l[col].substring(0, 9).equals("_min_caml")) {
				int lenght = l[col].length();
				if(l[col].substring(9, l[col].length()).equals(methode)){
					String var = l[col+1];
					
					String registre = variables.get(var);
					toWrite = "\tmov\t"+"r0"+","+registre+"\n";
				}

				toWrite += "\tbl\t" +l[col].substring(1);
				
				lenght = l[col].length();
				if (l[col].substring(lenght-2, lenght).equals("()")) {
					toWrite = toWrite.substring(0, toWrite.length()-2);
				}
			} 		
		}
		return toWrite;
	}
	
}

