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
		e.e = e.e.accept(this);
		return e;
	}

	@Override
	public Exp visit(Neg e) {
		e.e = e.e.accept(this);
		return e;
	}

	@Override
	public Exp visit(Add e) {
		e.e1 = e.e1.accept(this);
		e.e2 = e.e2.accept(this);
		return e;
	}

	@Override
	public Exp visit(Sub e) {
		e.e1 = e.e1.accept(this);
		e.e2 = e.e2.accept(this);
		return e;
	}

	@Override
	public Exp visit(FNeg e) {
		e.e = e.e.accept(this);
		return e;
	}

	@Override
	public Exp visit(FAdd e) {
		e.e1 = e.e1.accept(this);
		e.e2 = e.e2.accept(this);
		return e;
	}

	@Override
	public Exp visit(FSub e) {
		e.e1 = e.e1.accept(this);
		e.e2 = e.e2.accept(this);
		return e;
	}

	@Override
	public Exp visit(FMul e) {
		e.e1 = e.e1.accept(this);
		e.e2 = e.e2.accept(this);
		return e;
	}

	@Override
	public Exp visit(Mul e) {
		e.e1 = e.e1.accept(this);
		e.e2 = e.e2.accept(this);
		return e;
	}

	@Override
	public Exp visit(FDiv e) {
		e.e1 = e.e1.accept(this);
		e.e2 = e.e2.accept(this);
		return e;
	}

	@Override
	public Exp visit(Eq e) {
		e.e1 = e.e1.accept(this);
		e.e2 = e.e2.accept(this);
		return e;
	}

	@Override
	public Exp visit(LE e) {
		e.e1 = e.e1.accept(this);
		e.e2 = e.e2.accept(this);
		return e;
	}

	@Override
	public Exp visit(If e) {
		e.e1 = e.e1.accept(this);
		e.e2 = e.e2.accept(this);
		e.e3 = e.e3.accept(this);
		return e;
	}

	@Override
	public Exp visit(Let e) {
		if(ConstantFolding.tabVar.containsKey(e.id.toString()) && ConstantFolding.tabVar.get(e.id.id)) {
			e.e1 = e.e1.accept(this);
			return ((Exp)e.e2.accept(this)) ;
		} else {
			e.e2 = e.e2.accept(this);
			e.e1 = e.e1.accept(this);
		}
		return e;
	}

	@Override
	public Exp visit(Var e) {
		return e ;
	}

	@Override
	public Exp visit(LetRec e) {
		if(ConstantFolding.tabVar.containsKey(e.fd.id.toString()) && ConstantFolding.tabVar.get(e.fd.id.id)) {
			return ((Exp)e.e.accept(this)) ;
		}
		e.e = e.e.accept(this);
		e.fd.e = e.fd.e.accept(this);
		return e ;
	}

	@Override
	public Exp visit(App e) {
		e.e = e.e.accept(this);
		return e ;
	}

	@Override
	public Exp visit(Tuple e) {
		for (Exp exp : e.es) {
			exp = exp.accept(this);
		}
		return e;
	}

	@Override
	public Exp visit(LetTuple e) {
		int cpt = 0 ;
		while(cpt < e.ids.size()){
			Id arg = e.ids.get(cpt);
			if(ConstantFolding.tabVar.containsKey(arg.toString()) && ConstantFolding.tabVar.get(arg.id)) {
				cpt++;
			} else {
				e.e2 = e.e2.accept(this);
				e.e1 = e.e1.accept(this);
				return e ;
			}
		}
		return ((Exp)e.e2.accept(this)) ;
	}

	@Override
	public Exp visit(Array e) {
		e.e1 = e.e1.accept(this);
		e.e2 = e.e2.accept(this);
		return e;
	}

	@Override
	public Exp visit(Get e) {
		e.e1 = e.e1.accept(this);
		e.e2 = e.e2.accept(this);
		return e;
	}

	@Override
	public Exp visit(Put e) {
		e.e1 = e.e1.accept(this);
		e.e2 = e.e2.accept(this);
		e.e3 = e.e3.accept(this);
		return e;
	}

	@Override
	public Exp visit(Unit unit) {
		return unit;
	}

}
