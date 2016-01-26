import java.util.LinkedList;
import java.util.List;

public class InlineExpansion implements ObjVisitor<Exp> {
	private static LinkedList<LetRec > listeLetRec= new LinkedList<LetRec>();
	private static int threshold = 3;
	public InlineExpansion() {

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
		e.e1=e.e1.accept(this);		
		e.e2=e.e2.accept(this);
		return e;
	}

	@Override
	public Exp visit(Sub e) {
		e.e1=e.e1.accept(this);		
		e.e2=e.e2.accept(this);
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
		e.e1=e.e1.accept(this);		
		e.e2=e.e2.accept(this);
		return e;
	}

	@Override
	public Exp visit(Eq e) {
		e.e1=e.e1.accept(this);		
		e.e2=e.e2.accept(this);
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
		int height = Height.computeHeight(e.fd.e);
		
		if (height<=threshold){
			
			listeLetRec.add(e);
			e.fd.e=e.fd.e.accept(this);
			e.e=e.e.accept(this);
			return e;
		}
		else{
			e.fd.e=e.fd.e.accept(this);
			e.e=e.e.accept(this);
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
						TVar z = new TVar(newId.id) ;
						exp = new Let(i, z, e.es.get(cmp),exp);
						cmp ++;
					}
					exp.accept(this);
					return exp;
				}			
			}
		} else if(e.e instanceof App){
			if(((App)e.e).e instanceof Var) {
				Var x = (Var)((App)e.e).e;
				Id varId = x.id;
				for(LetRec lt : listeLetRec){
					if(lt.fd.id.equals(varId)){
						Exp exp=(Exp)lt.fd.e.clone();
						exp = new App(exp,e.es);
						exp.accept(this);
						return exp;
					}			
				}
			}
		}
		int cpt = 0 ;
		for(Exp t : e.es){
			t = t.accept(this);
			e.es.set(cpt, t);
			cpt++;
		}
			return e;
	}

	@Override
	public Exp visit(Tuple e) {
		List<Exp> newExp = new LinkedList<Exp>();
		for (Exp exp : e.es) {
			newExp.add(exp.accept(this));
			
		}
		e.es=newExp;
		return e;
	}

	@Override
	public Exp visit(LetTuple e) {
		e.e1 = e.e1.accept(this);
		e.e2 = e.e2.accept(this);
		return e;
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
}
