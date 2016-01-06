
public class FrontEnd {
	public FrontEnd(Exp e) {
		// appelle des différentes passes
		e.accept(new KNormalization());
	}
}
