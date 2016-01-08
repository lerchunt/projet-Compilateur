
public class FrontEnd {
	public static int nb_var=0;
	
	public FrontEnd(Exp e) {
		// appelle des différentes passes
		e.accept(new KNormalization());
	}
}
