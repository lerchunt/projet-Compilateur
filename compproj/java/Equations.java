public class Equations {
	Type t1;
	Type t2;
	public Equations(Type t1, Type t2) {
		this.t1 = t1;
		this.t2 = t2;
	}
	
	@Override
	public String toString() {
		return t1.toString() + "=" + t2.toString();
	}
}
