import java.util.Enumeration;
import java.util.Hashtable;


public class UnDefinition extends ConstantFolding implements ObjVisitor<Exp> {

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
		return e.accept(this);
	}

	@Override
	public Exp visit(Neg e) {
		return e.accept(this);
	}

	@Override
	public Exp visit(Add e) {
		return e.accept(this);
	}

	@Override
	public Exp visit(Sub e) {
		return e.accept(this);
	}

	@Override
	public Exp visit(FNeg e) {
		return e.accept(this);
	}

	@Override
	public Exp visit(FAdd e) {
		return e.accept(this);
	}

	@Override
	public Exp visit(FSub e) {
		return e.accept(this);
	}

	@Override
	public Exp visit(FMul e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Exp visit(Mul e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Exp visit(FDiv e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Exp visit(Eq e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Exp visit(LE e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Exp visit(If e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Exp visit(Let e) {
		if(ConstantFolding.tabVa.containsKey(e.id.toString())) {
			e = (Let) e.e2.accept(this);
		}
		return e;
	}

	@Override
	public Exp visit(Var e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Exp visit(LetRec e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Exp visit(App e) {
		// TODO Auto-generated method stub
		return null;
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

	@Override
	public Exp visit(Unit unit) {
		// TODO Auto-generated method stub
		return null;
	}

}
