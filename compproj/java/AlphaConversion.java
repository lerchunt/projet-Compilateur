import java.util.ArrayList;

public class AlphaConversion implements ObjVisitor<Exp> {
	static ArrayList <BindVar> variables = new ArrayList<BindVar>();
	/**
	 * Ajout de la variable dans oldVar
	 * @param i
	 */
	public void stockerId(Id id){
		variables.add(new BindVar(id.toString(), id.toString()));
		checkName(id.toString());
	}
	public void checkName(String s){
		boolean existing = false;
		int cpt=0;
		for(BindVar i : variables){
			if(i.getIdPrec().equals(s)){
				cpt++;
				if (cpt>1)
					existing=true;
			}
		}
		if(existing){
			Id newId = Id.gen(); //si la variable existe déjà dans la liste, on la renomme
			 variables.get(variables.size()-1).setIdNew(newId.toString());
		}
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Exp visit(Neg e) {
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub
		return null;
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
		return e;
	}

	@Override
	public Exp visit(If e) {
		return e;
	}

	@Override
	public Exp visit(Let e) {
		e.e1.accept(this);
		stockerId(e.id);
		int last_var=0;
		String valeur=e.id.toString();
		for(BindVar bv : variables){
			if(bv.getIdPrec().equals(valeur)){
				last_var=variables.indexOf(bv);
			}
		}
		e.id.id=variables.get(last_var).getIdNew();
		e.e2.accept(this);
		return e;
	}

	@Override
	public Exp visit(Var e) {
		int last_var=0;
		String valeur=e.id.toString();
		for(BindVar bv : variables){
			if(bv.getIdPrec().equals(valeur)){
				last_var=variables.indexOf(bv);
			}
		}
		e.id.id=variables.get(last_var).getIdNew();
		return e;
	}

	@Override
	public Exp visit(LetRec e) {
		return	e;
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

	private class BindVar{
		private String IdPrec;
		private String IdNew;
		
		private BindVar(String idold,String idnew){
			this.IdNew=idnew;
			this.IdPrec=idold;
		}
		public void setIdPrec(String s){
			this.IdPrec=s;
			
		}
		public void setIdNew(String s){
			this.IdNew=s;
		}
		
		public String getIdPrec(){
			return this.IdPrec;
		}
		public String getIdNew(){
			return this.IdNew;
		}
	}

}
