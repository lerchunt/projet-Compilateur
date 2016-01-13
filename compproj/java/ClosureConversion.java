import java.util.List;


public class ClosureConversion implements ObjVisitor<Exp> {
	
	private class Closure {
		List<Exp> params;
		LetRec funcDApel;
	}

	@Override
	public Exp visit(Unit e) {
		return e;
	}

	@Override
	public Exp visit(Bool e) {
		return e;
	}

	@Override
	public Exp visit(Int e) {
		return e;
	}

	@Override
	public Exp visit(Float e) {
		return e;
	}

	@Override
	public Exp visit(Not e) {
		return e;
	}

	@Override
	public Exp visit(Neg e) {
		return e;
	}

	@Override
	public Exp visit(Add e) {
		return e;
	}

	@Override
	public Exp visit(Sub e) {
		return e;
	}

	@Override
	public Exp visit(FNeg e) {
		return e;
	}

	@Override
	public Exp visit(FAdd e) {
		return e;
	}

	@Override
	public Exp visit(FSub e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Exp visit(FMul e) {
		return e;
	}
	
	@Override
	public Exp visit(Mul e) {
		return e;
	}

	@Override
	public Exp visit(FDiv e) {
		return e;
	}

	@Override
	public Exp visit(Eq e) {
		return e;
	}

	@Override
	public Exp visit(LE e) {
		return e;
	}

	@Override
	public Exp visit(If e) {
		return e;
	}

	@Override
	public Exp visit(Let e) {
		return e;
	}

	@Override
	public Exp visit(Var e) {
		return e;
	}

	@Override
	public Exp visit(LetRec e) {
		return e;
	}

	@Override
	public Exp visit(App e) {
		return e;
	}

	@Override
	public Exp visit(Tuple e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Exp visit(LetTuple e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Exp visit(Array e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Exp visit(Get e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Exp visit(Put e) {
		// TODO Auto-generated method stub
		return null;
	}

}
