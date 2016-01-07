

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

			int nbRegVar=3;
			for (String[] l : strMinCaml){
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
							if (l[1].length() >=9 ) {
								if (l[1].substring(0, 9).equals("_min_caml")) {
									int lenght = l[1].length();
									if(l[1].substring(9, 19).equals("_print_int")){
										String var = l[2];
										String registre = variables.get(var);
										toWrite = "\tmov\t"+"r0"+","+registre+"\n";
									}

									toWrite += "\tbl\t" +l[1].substring(1);
									
									lenght = l[1].length();
									if (l[1].substring(lenght-2, lenght).equals("()")) {
										toWrite = toWrite.substring(0, toWrite.length()-2);
									}
									
									
								} 
							}
							retour = String.format("%s%s\n", retour, toWrite );
							toWrite = "";
							break;
						 case "let":
							String Var = l[1];
							nbRegVar++;
							String num = l[3];
							String registre = String.format("r%d", nbRegVar);
							this.variables.put(Var, registre);
							
							toWrite = "mov\tr"+nbRegVar+",#"+num;
						
							retour = String.format("%s\t%s\n", retour, toWrite );
							toWrite = "";
							break;
						default:
					}
					
						
				}
				numLigne ++;
				
			}
			retour = String.format("%s\t%s\n", retour, "bl\tmin_caml_exit");
			
			/*PrintWriter w = new PrintWriter( new BufferedWriter( new FileWriter(this.pathFolder+this.nameFolder+".s")));
			w.print(retour);
			w.close();*/
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
}
