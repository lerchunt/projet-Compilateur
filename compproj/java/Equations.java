public class Equations {
	Type t1;
	Type t2;
	String v ;
	public Equations(Type t1, Type t2, String v) {
		this.t1 = t1;
		this.t2 = t2;
		this.v = v ;
	}
	
	@Override
	public String toString() {
		return v.toString() + " : " + t1.toString() + "=" + t2.toString();
	}
}
