

import java.io.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.LinkedList;

public class Backend {
	String pathFolder;
	String nameFolder;
	Hashtable<String,String> variables = new Hashtable<String,String>();

	private int nbRegVar=4;
	
	public Backend (String path) { 

		String[] tabPath = path.split("/");
		nameFolder = tabPath[tabPath.length -1];
		int tailleName = nameFolder.length();
		nameFolder = nameFolder.split("\\.")[0];
		this.pathFolder = path.substring(0, path.length()-tailleName);
		
	}
	
	public void startBackEnd() {
		try{
			LinkedList<String[]> strMinCaml = new LinkedList<String[]>();
			InputStream fl=new FileInputStream(this.pathFolder+this.nameFolder+".asml"); 
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
			
			String retour = convert(strMinCaml);

			PrintWriter w = new PrintWriter( new BufferedWriter( new FileWriter(this.pathFolder+this.nameFolder+".s")));
			w.print(retour);
			w.close();
			//System.out.println(retour);
		}		
		catch (Exception e){
			System.out.println(e.toString());
		}
	}
	
	public String convert(LinkedList<String[]> strMinCaml) {
		String retour = String.format("\t%s\n", ".text");
			for (String[] l : strMinCaml){
				retour += genereAsLigne(l) +"\n";
			}
			return String.format("%s\t%s\n", retour, "bl\tmin_caml_exit");
	}
	
	private String genereAsLigne(String[] l) {
		String toWrite="";
		switch (l[0]) {
			case "call":
				return print_ligne(1,l);
				
			 case "let":
				 if (l[1].equals("_") && l[2].equals("=")) {
					 return String.format("\t%s\n%s", ".global _start", "_start:");
				 } else {
					 if (l[3].equals("call")){
						return print_ligne(4,l);
						/*String[] suite = Arrays.copyOfRange(l, 3, l.length);
						toWrite = genereAsLigne(suite, nbRegVar); */
						
					 } else if (l[3].equals("add")||l[3].equals("sub")||l[3].equals("mul")){
						 String r1 = this.variables.get(l[4]);
						 String r2 = this.variables.get(l[5]);
						 affectRegistre(l[1],nbRegVar);
						 String r3 = this.variables.get(l[1]);
						 nbRegVar++;
						 return operation(l[3],r1,r2,r3);
					 }else{
						 
						affectRegistre(l[1],nbRegVar);
						toWrite = "\tmov\tr"+nbRegVar+",#"+l[3];
						nbRegVar++;
						return toWrite;
					 }
				 }
				
			default:
		}
		return "";
	}
	
	private void Affiche(String[] l){
		System.out.print(l.length);
		for (int i =0; i<l.length; i++) {
			System.out.print(l[i]);
			
		}
		System.out.print("\n");
	}

	private String operation(String operateur,String r1,String r2, String r3){
		String toWrite="";
		toWrite = String.format("\t%s\t%s,%s,%s",operateur,r3,r1,r2);
		
		return toWrite;
	}
	
	
	private void affectRegistre(String Var, int nb){
		String registre = String.format("r%d", nb);
		this.variables.put(Var, registre);
	}
	
	private String print_ligne(int col,  String[] l){
		String toWrite="";
		if (l[col].length() >=9 ) {
			if (l[col].substring(0, 9).equals("_min_caml")) {
				int lenght = l[col].length();
				if(!l[col].substring(9, l[col].length()).equals("_print_newline()")){
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

