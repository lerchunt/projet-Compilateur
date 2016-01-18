import java.util.LinkedList;

public class InlineExpansion implements ObjVisitor<Exp> {
	private static LinkedList<LetRec > listeLetRec= new LinkedList<LetRec>();
	private static int threshold = 5;
	private Exp base;
	public InlineExpansion(Exp base) {
		this.base=base;
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
	public Exp visit(Int e){
		return e;
	}

	@Override
	public Exp visit(Float e){
		return e;
	}

	@Override
	public Exp visit(Not e) {
		e.e=e.e.accept(this);
		return e;
	}

	@Override
	public Exp visit(Neg e) {
		e.e=e.e.accept(this);
		return e;
	}

	@Override
	public Exp visit(Add e) {
		e.e1.accept(this);		
		e.e2.accept(this);
		return e;
	}

	@Override
	public Exp visit(Sub e) {
		e.e1.accept(this);		
		e.e2.accept(this);
		return e;
	}

	@Override
	public Exp visit(FNeg e) {
		e.e=e.e.accept(this);
		return e;
	}

	@Override
	public Exp visit(FAdd e) {
		e.e1.accept(this);		
		e.e2.accept(this);
		return e;
	}

	@Override
	public Exp visit(FSub e) {
		e.e1.accept(this);		
		e.e2.accept(this);
		return e;
	}

	@Override
	public Exp visit(FMul e) {
		e.e1.accept(this);		
		e.e2.accept(this);
		return e;
	}
	
	@Override
	public Exp visit(Mul e) {
		e.e1.accept(this);		
		e.e2.accept(this);
		return e;
	}

	@Override
	public Exp visit(FDiv e) {
		e.e1.accept(this);		
		e.e2.accept(this);
		return e;
	}

	@Override
	public Exp visit(Eq e) {
		e.e1.accept(this);		
		e.e2.accept(this);
		return e;
	}

	@Override
	public Exp visit(LE e) {
		e.e1= e.e1.accept(this);
		e.e2=e.e2.accept(this);
		return e;
	}

	@Override
	public Exp visit(If e) {
		e.e1=e.e1.accept(this);
		e.e2=e.e2.accept(this);
		e.e3=e.e3.accept(this);
		return e;
	}

	@Override
	public Exp visit(Let e) {
		e.e1=e.e1.accept(this);
		e.e2=e.e2.accept(this);
		return e;
	}

	@Override
	public Exp visit(Var e) {
		return e;
	}

	@Override
	public Exp visit(LetRec e) {
		int height = Height.computeHeight(e);
		if (height<=threshold){
			if (listeLetRec.contains(e)==false){
				listeLetRec.add(e);
			}
			
			return e;
		}
		else{
			return e;	
		}
	}

	@Override
	public Exp visit(App e) {
		if(e.e instanceof Var) {
			Id varId = ((Var)e.e).id;
			for(LetRec lt : listeLetRec){
				if(lt.fd.id.equals(varId)){
					int cmp =0;
					Exp exp=(Exp)lt.fd.e.clone();
					for(Id i :lt.fd.args){
						Id newId = Id.gen();
						exp = new Let(i, new TVar(newId.id), e.es.get(cmp),exp);
						cmp ++;
					}
				}
						
			}
		}
		this.base.accept(new AlphaConversion());
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
