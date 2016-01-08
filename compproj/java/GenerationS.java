
public class GenerationS implements ObjVisitor<String> {
	static String asml;
	
	public GenerationS()
	{
		GenerationS.asml = "\t.text\n\t.global _start\n_start:";
	}
	
	@Override
	public String visit(Bool e) {
		return null;
	}

	@Override
	public String visit(Int e) {
		return null;
	}

	@Override
	public String visit(Float e) {
		return null;
	}

	@Override
	public String visit(Not e) {
		e.e.accept(this);
		return null;
	}

	@Override
	public String visit(Neg e) {
		e.e.accept(this);
		return null;
	}

	@Override
	public String visit(Add e) {
		e.e1.accept(this);
		e.e2.accept(this);
		return null;
	}

	@Override
	public String visit(Sub e) {
		e.e1.accept(this);
		e.e2.accept(this);
		return null;
	}

	@Override
	public String visit(FNeg e) {
		e.e.accept(this);
		return null;
	}

	@Override
	public String visit(FAdd e) {
		e.e1.accept(this);
		e.e2.accept(this);
		return null;
	}

	@Override
	public String visit(FSub e) {
		e.e1.accept(this);
		e.e2.accept(this);
		return null;
	}

	@Override
	public String visit(FMul e) {
		e.e1.accept(this);
		e.e2.accept(this);
		return null;
	}

	@Override
	public String visit(Mul e) {
		e.e1.accept(this);
		e.e2.accept(this);
		return null;
	}

	@Override
	public String visit(FDiv e) {
		e.e1.accept(this);
		e.e2.accept(this);
		return null;
	}

	@Override
	public String visit(Eq e) {
		e.e1.accept(this);
		e.e2.accept(this);
		return null;
	}

	@Override
	public String visit(LE e) {
		e.e1.accept(this);
		e.e2.accept(this);
		return null;
	}

	@Override
	public String visit(If e) {
		e.e1.accept(this);
		e.e2.accept(this);
		e.e3.accept(this);
		return null;
	}

	@Override
	public String visit(Let e) {
		e.e1.accept(this);
		e.e2.accept(this);
		return null;
	}

	@Override
	public String visit(Var e) {
		return e.id.id;
	}

	@Override
	public String visit(LetRec e) {
		e.e.accept(this);
		return null;
	}

	@Override
	public String visit(App e) {
		GenerationS.asml += String.format("%s\nbl\tmin_caml_%s",GenerationS.asml,e.e.accept(this));
		return GenerationS.asml;
	}

	@Override
	public String visit(Tuple e) {
		
		return null;
	}

	@Override
	public String visit(LetTuple e) {
		e.e1.accept(this);
		e.e2.accept(this);
		return null;
	}

	@Override
	public String visit(Array e) {
		e.e1.accept(this);
		e.e2.accept(this);
		return null;
	}

	@Override
	public String visit(Get e) {
		e.e1.accept(this);
		e.e2.accept(this);
		return null;
	}

	@Override
	public String visit(Put e) {
		e.e1.accept(this);
		e.e2.accept(this);
		e.e3.accept(this);
		return null;
	}

	@Override
	public String visit(Unit unit) {
		
		return null;
	}

}
