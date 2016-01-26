import java.util.LinkedList;

public class TypeChecking2 implements ObjVisitor<LinkedList<Equations>> {

	@Override
	public LinkedList<Equations> visit(Bool e) {
		Equations eq = new Equations(new TBool(), e.typeAttendu,e.toString());
		LinkedList<Equations> retour = new LinkedList<Equations>();
		retour.add(eq);
		return retour;
	}

	@Override
	public LinkedList<Equations> visit(Int e) {
		Equations eq = new Equations(new TInt(), e.typeAttendu,e.toString());
		LinkedList<Equations> retour = new LinkedList<Equations>();
		retour.add(eq);
		return retour;
	}

	@Override
	public LinkedList<Equations> visit(Float e) {
		Equations eq = new Equations(new TFloat(), e.typeAttendu,e.toString());
		LinkedList<Equations> retour = new LinkedList<Equations>();
		retour.add(eq);
		return retour;
	}

	@Override
	public LinkedList<Equations> visit(Not e) {
		Equations eq = new Equations(new TBool(), e.typeAttendu,e.toString());
		LinkedList<Equations> retour = new LinkedList<Equations>();
		retour.add(eq);
		e.e.typeAttendu = new TBool();
		e.e.env = e.env;
		retour.addAll(e.e.accept(this));
		return retour;
	}

	@Override
	public LinkedList<Equations> visit(Neg e) {
		Equations eq = new Equations(new TInt(), e.typeAttendu,e.toString());
		LinkedList<Equations> retour = new LinkedList<Equations>();
		retour.add(eq);
		e.e.typeAttendu = new TInt();
		e.e.env = e.env;
		retour.addAll(e.e.accept(this));
		return retour;
	}

	@Override
	public LinkedList<Equations> visit(Add e) {
		Equations eq = new Equations(new TInt(), e.typeAttendu,e.toString());
		LinkedList<Equations> retour = new LinkedList<Equations>();
		retour.add(eq);
		e.e1.typeAttendu = new TInt();
		e.e1.env = e.env;
		e.e2.typeAttendu = new TInt();
		e.e2.env = e.env;
		retour.addAll(e.e1.accept(this));
		retour.addAll(e.e2.accept(this));
		return retour;
	}

	@Override
	public LinkedList<Equations> visit(Sub e) {
		Equations eq = new Equations(new TInt(), e.typeAttendu,e.toString());
		LinkedList<Equations> retour = new LinkedList<Equations>();
		retour.add(eq);
		e.e1.typeAttendu = new TInt();
		e.e1.env = e.env;
		e.e2.typeAttendu = new TInt();
		e.e2.env = e.env;
		retour.addAll(e.e1.accept(this));
		retour.addAll(e.e2.accept(this));
		return retour;
	}

	@Override
	public LinkedList<Equations> visit(FNeg e) {
		Equations eq = new Equations(new TFloat(), e.typeAttendu,e.toString());
		LinkedList<Equations> retour = new LinkedList<Equations>();
		retour.add(eq);
		e.e.typeAttendu = new TFloat();
		e.e.env = e.env;
		retour.addAll(e.e.accept(this));
		return retour;
	}

	@Override
	public LinkedList<Equations> visit(FAdd e) {
		Equations eq = new Equations(new TFloat(), e.typeAttendu,e.toString());
		LinkedList<Equations> retour = new LinkedList<Equations>();
		retour.add(eq);
		e.e1.typeAttendu = new TFloat();
		e.e1.env = e.env;
		e.e2.typeAttendu = new TFloat();
		e.e2.env = e.env;
		retour.addAll(e.e1.accept(this));
		retour.addAll(e.e2.accept(this));
		return retour;
	}

	@Override
	public LinkedList<Equations> visit(FSub e) {
		Equations eq = new Equations(new TFloat(), e.typeAttendu,e.toString());
		LinkedList<Equations> retour = new LinkedList<Equations>();
		retour.add(eq);
		e.e1.typeAttendu = new TFloat();
		e.e1.env = e.env;
		e.e2.typeAttendu = new TFloat();
		e.e2.env = e.env;
		retour.addAll(e.e1.accept(this));
		retour.addAll(e.e2.accept(this));
		return retour;
	}

	@Override
	public LinkedList<Equations> visit(FMul e) {
		Equations eq = new Equations(new TFloat(), e.typeAttendu,e.toString());
		LinkedList<Equations> retour = new LinkedList<Equations>();
		retour.add(eq);
		e.e1.typeAttendu = new TFloat();
		e.e1.env = e.env;
		e.e2.typeAttendu = new TFloat();
		e.e2.env = e.env;
		retour.addAll(e.e1.accept(this));
		retour.addAll(e.e2.accept(this));
		return retour;
	}

	@Override
	public LinkedList<Equations> visit(Mul e) {
		Equations eq = new Equations(new TInt(), e.typeAttendu,e.toString());
		LinkedList<Equations> retour = new LinkedList<Equations>();
		retour.add(eq);
		e.e1.typeAttendu = new TInt();
		e.e1.env = e.env;
		e.e2.typeAttendu = new TInt();
		e.e2.env = e.env;
		retour.addAll(e.e1.accept(this));
		retour.addAll(e.e2.accept(this));
		return retour;
	}

	@Override
	public LinkedList<Equations> visit(FDiv e) {
		Equations eq = new Equations(new TFloat(), e.typeAttendu,e.toString());
		LinkedList<Equations> retour = new LinkedList<Equations>();
		retour.add(eq);
		e.e1.typeAttendu = new TFloat();
		e.e1.env = e.env;
		e.e2.typeAttendu = new TFloat();
		e.e2.env = e.env;
		retour.addAll(e.e1.accept(this));
		retour.addAll(e.e2.accept(this));
		return retour;
	}

	@Override
	public LinkedList<Equations> visit(Eq e) {
		Equations eq = new Equations(new TBool(), e.typeAttendu,e.toString());
		LinkedList<Equations> retour = new LinkedList<Equations>();
		retour.add(eq);
		Type same = Type.gen();
		e.e1.typeAttendu = same;
		e.e1.env = e.env;
		e.e2.typeAttendu = same;
		e.e2.env = e.env;
		retour.addAll(e.e1.accept(this));
		retour.addAll(e.e2.accept(this));
		return retour;
	}

	@Override
	public LinkedList<Equations> visit(LE e) {
		Equations eq = new Equations(new TBool(), e.typeAttendu,e.toString());
		LinkedList<Equations> retour = new LinkedList<Equations>();
		retour.add(eq);
		Type same = Type.gen();
		e.e1.typeAttendu = same;
		e.e1.env = e.env;
		e.e2.typeAttendu = same;
		e.e2.env = e.env;
		retour.addAll(e.e1.accept(this));
		retour.addAll(e.e2.accept(this));
		return retour;
	}

	@Override
	public LinkedList<Equations> visit(If e) {
		LinkedList<Equations> retour = new LinkedList<Equations>();
		e.e1.typeAttendu = new TBool();
		e.e1.env = e.env;
		e.e2.typeAttendu = e.typeAttendu;
		e.e2.env = e.env;
		e.e3.typeAttendu = e.typeAttendu;
		e.e3.env = e.env;
		retour.addAll(e.e1.accept(this));
		retour.addAll(e.e2.accept(this));
		retour.addAll(e.e3.accept(this));
		return retour;
	}

	
	@Override
	public LinkedList<Equations> visit(Let e) {
		LinkedList<Equations> retour = new LinkedList<Equations>();
		e.t = Type.gen();
		e.e1.typeAttendu = e.t;
		e.e1.env = e.env;
		e.e2.typeAttendu = e.typeAttendu;
		e.e2.env.addAll(e.env);
		e.e2.addEnv(e.id, e.t);
		if(e.e1 instanceof Array){
			//Type ts = Type.gen();
			Type t = Type.gen();
			((TVar)e.t).typeParamArray = t ;
		}
		retour.addAll(e.e1.accept(this));
		retour.addAll(e.e2.accept(this));
		e.e2.env.removeFirst();
		return retour;
	}

	@Override
	public LinkedList<Equations> visit(Var e) {
		Type Tvar = e.rechercheEnv();
		if (Tvar == null) {
			System.err.println("error "+e.id.id+" is not defined");
			System.exit(1);
		} else {
			LinkedList<Equations> retour = new LinkedList<Equations>();
			retour.add(new Equations(e.typeAttendu, Tvar,e.toString()));
			return retour;
		}
		return null;
	}

	@Override
	public LinkedList<Equations> visit(LetRec e) {
		if ((e.isDefineAs() != null) && !(e.isDefineAs() instanceof TFun)) {
			System.err.println(e.fd.id + " is already defined as a variable");
			System.exit(1);
		}
		LinkedList<Equations> retour = new LinkedList<Equations>();
		e.fd.type = new TFun();
		Type newT = Type.gen();
		((TFun)e.fd.type).typeRetour = newT;
		Type newT2 = Type.gen();
		e.fd.e.typeAttendu = newT2;
		retour.add(new Equations(newT, newT2,e.toString()));
		e.fd.e.env.addAll(e.env);
		for (Id id : e.fd.args) {
			Type newP = Type.gen();
			e.fd.e.addEnv(id, newP);
			((TFun)e.fd.type).typeArgs.add(newP);
		}
		e.fd.e.addEnv(e.fd.id, e.fd.type);
		retour.addAll(e.fd.e.accept(this));
		for (Id id : e.fd.args) {
			e.fd.e.env.removeFirst();
		}
		e.fd.e.env.removeFirst();
		
		e.e.typeAttendu = e.typeAttendu;
		e.e.env.addAll(e.env);
		e.e.env.addAll(e.fd.e.env);
		e.e.addEnv(e.fd.id, e.fd.type);
		retour.addAll(e.e.accept(this));
		e.e.env.removeFirst();
		
		return retour;
	}

	@Override
	public LinkedList<Equations> visit(App e) {
		LinkedList<Equations> retour = new LinkedList<Equations>();
		Type tFun = new TFun();
		if (e.e instanceof Var) {
			e.e.env = e.env;
			tFun = ((Var)e.e).rechercheEnv();
			if (tFun instanceof TFun) {
				Equations eq = new Equations(((TFun)tFun).typeRetour , e.typeAttendu,e.toString());
				retour.add(eq);
				if (e.es.size() == ((TFun)tFun).typeArgs.size()) {
					int cmp = 0;
					for (Exp param : e.es) {
						Type ts = Type.gen();
						eq = new Equations(ts, ((TFun)tFun).typeArgs.get(cmp),e.toString());
						retour.add(eq);
						param.env = e.env;
						param.typeAttendu = ts;
						retour.addAll(param.accept(this));
						cmp++;
					}
				} else {
					System.err.println("error "+((Var)e.e).id.id+" expected "+((TFun)tFun).typeArgs.size()+" arguments");
					System.exit(1);
				}
			} else if (tFun == null) {
				System.err.println("error "+((Var)e.e).id+" is not defined");
				System.exit(1);
			} else {
				if (tFun instanceof TVar) {
					System.err.println("Warning "+((Var)e.e).id.id+" expected as function and found as var");
				}
				//System.exit(1);
			}
		} else {
			((TFun)tFun).typeRetour = e.typeAttendu;
			LinkedList<Type> tParams = new LinkedList<Type>();
			for (Exp param : e.es) {
				Type ts = Type.gen();
				param.env = e.env;
				param.typeAttendu = ts;
				retour.addAll(param.accept(this));
				tParams.add(ts);
			}
			((TFun)tFun).typeArgs = tParams;
			e.e.env = e.env;
			e.e.typeAttendu = tFun;
			retour.addAll(e.e.accept(this));
		}
		return retour;
	}
	
	@Override
	public LinkedList<Equations> visit(Tuple e) {
		LinkedList<Equations> retour = new LinkedList<Equations>();
		LinkedList<Type> tParams = new LinkedList<Type>();
		TTuple tuple = new TTuple(Type.gen().toString());
		for (Exp param : e.es) {
			Type arg;
			param.env = e.env;
			Type ts = Type.gen();
			if(param.typeAttendu == null){
				param.typeAttendu = ts;
			} 
			tParams.add(param.typeAttendu);
			if(param instanceof Var){
				arg = ((Var)param).rechercheEnv();
				if (arg == null) {
					System.err.println("error "+((Var)param).id+" is not defined");
					System.exit(1);
				}
				Equations eq = new Equations(param.typeAttendu,arg,e.toString());
				retour.add(eq);
			} else {
				retour.addAll(param.accept(this));
			}
			
		}
		tuple.typeArgs = tParams;
		if(e.typeAttendu instanceof TVar){
			((TVar)e.typeAttendu).typeArgs = tParams;
		} else if (e.typeAttendu instanceof TTuple){
			((TTuple)e.typeAttendu).typeArgs = tParams;
		}
		Equations eq = new Equations(tuple, e.typeAttendu,e.toString());
		retour.add(eq);
		return retour;
	}

	@Override
	public LinkedList<Equations> visit(LetTuple e) {
		LinkedList<Equations> retour = new LinkedList<Equations>();
		if(!(e.e1 instanceof Tuple)){
			if(e.e1 instanceof App){
				e.e1.typeAttendu = new TTuple(Type.gen().toString());
				e.e1.env = e.env;
				e.e2.typeAttendu = e.typeAttendu;
				e.e2.env.addAll(e.env);
				int cpt = 0;
				LinkedList<Type> Ttype = new LinkedList<Type>();
				for (Id param : e.ids) {
					Ttype.add(Type.gen());
				}	
				e.ts = Ttype;
				for (Id param : e.ids) {
					e.e2.addEnv(param, e.ts.get(cpt));
					cpt++;
				}
			} else if (e.e1 instanceof Var){
				Type ts = Type.gen();
				e.e1.typeAttendu = ts ;
				e.e1.env = e.env;
				e.e2.typeAttendu = e.typeAttendu;
				e.e2.env.addAll(e.env);
				int cpt = 0;
				LinkedList<Type> Ttype = new LinkedList<Type>();
				for (Id param : e.ids) {
					Ttype.add(Type.gen());
				}	
				e.ts = Ttype;
				for (Id param : e.ids) {
					e.e2.addEnv(param, e.ts.get(cpt));
					cpt++;
				}
			}
			else{
				System.err.println("error "+e.e1.toString() +" is not a tuple");
				System.exit(1);
			}
		} else if( ((Tuple)e.e1).es.size() != e.ids.size()){
			System.err.println("error size of tuple");
			System.exit(1);
		} else {
			int cpt = 0;
			LinkedList<Type> Ttype = new LinkedList<Type>();
			for (Id param : e.ids) {
				Ttype.add(Type.gen());
			}	
			e.ts = Ttype;
			for (Exp param : ((Tuple)e.e1).es) {
				param.typeAttendu = e.ts.get(cpt);
				cpt++;
			}	
			e.e1.typeAttendu = new TTuple(Type.gen().toString());
			e.e1.env = e.env;
			e.e2.typeAttendu = e.typeAttendu;
			e.e2.env.addAll(e.env);
			cpt = 0;
			for (Id param : e.ids) {
				e.e2.addEnv(param, e.ts.get(cpt));
				cpt++;
			}
		}
		e.e2.env.addAll(e.env);
		e.e1.env = e.env;
		retour.addAll(e.e1.accept(this));
		retour.addAll(e.e2.accept(this));
		e.e2.env.removeFirst();
		return retour;
	}

	@Override
	public LinkedList<Equations> visit(Array e) {
		LinkedList<Equations> retour = new LinkedList<Equations>();
		LinkedList<Type> tParams = new LinkedList<Type>();
		if(e.e1 instanceof App){
			System.err.println("expected one argument or operation");
			System.exit(1);
		} else {
			if(e.e1 instanceof Neg){
					System.err.println("negative argument");
					System.exit(1);
			}
			Type ts = Type.gen();
			e.e1.typeAttendu = Type.gen();
			if(e.e2.typeAttendu instanceof TVar){
				
			} else {
				e.e2.typeAttendu = Type.gen();
			}
			Equations eq = new Equations(new TInt(), e.e1.typeAttendu,e.toString());
			retour.add(eq);
			eq = new Equations(new TArray(), e.typeAttendu,e.toString());
			retour.add(eq);
			eq = new Equations(ts, e.e2.typeAttendu,e.toString());
			retour.add(eq);
			if(((TVar)e.typeAttendu).typeParamArray != null){
				eq = new Equations(((TVar)e.typeAttendu).typeParamArray, e.e2.typeAttendu,e.toString());
				retour.add(eq);
			}
			e.e1.env = e.env ;
			e.e2.env.addAll(e.env);
			retour.addAll(e.e1.accept(this));
			retour.addAll(e.e2.accept(this));
			e.e2.env.removeFirst();
			
			if(e.e2 instanceof Tuple){
				int cpt = 0 ;
				while (cpt < ((Tuple)e.e2).es.size() ){
					tParams.add((((Tuple)e.e2).es.get(cpt)).typeAttendu);
					cpt++;
				}
				((TVar)e.typeAttendu).typeArgs = tParams ;
			}
		}
		return retour;
	}

	@Override
	public LinkedList<Equations> visit(Get e) {
		LinkedList<Equations> retour = new LinkedList<Equations>();
		e.e1.typeAttendu = Type.gen();
		e.e2.typeAttendu = Type.gen();
		e.e1.env = e.env ;
		e.e2.env.addAll(e.env);
		if(e.e1 instanceof Array){
			((Array)e.e1).e2.typeAttendu = Type.gen();
			Equations eq = new Equations(new TArray(e.typeAttendu), e.e1.typeAttendu,e.toString());
			retour.add(eq);
			eq = new Equations(new TInt(), e.e2.typeAttendu,e.toString());
			retour.add(eq);
			eq = new Equations(e.typeAttendu, ((Array)e.e1).e2.typeAttendu,e.toString());
			retour.add(eq);
		} else if (e.e1 instanceof Var){
			Type ts = ((Var)e.e1).rechercheEnv();
			if(ts instanceof TVar){
				if(((TVar)ts).typeParamArray == null) {
					((TVar)ts).typeParamArray = Type.gen();
				}
				Equations eq = new Equations(e.typeAttendu, ((TVar)ts).typeParamArray,e.toString());
				retour.add(eq);
				eq = new Equations(new TInt(), e.e2.typeAttendu,e.toString());
				retour.add(eq);
			} else if (ts instanceof TArray) {
				Equations eq = new Equations(e.typeAttendu, ((TArray)ts).typeParamArray,e.toString());
				retour.add(eq);
				eq = new Equations(new TInt(), e.e2.typeAttendu,e.toString());
				retour.add(eq);
			} else {
				System.err.println("expected a type var");
				System.exit(1);
			}
		} else if(e.e1 instanceof App) {
			Equations eq = new Equations(new TInt(), e.e2.typeAttendu,e.toString());
			retour.add(eq);
			eq = new Equations(new TArray(e.typeAttendu), e.e1.typeAttendu,e.toString());
			retour.add(eq);
		} else if(e.e1 instanceof Get) {
			Equations eq = new Equations(new TInt(), e.e2.typeAttendu,e.toString());
			retour.add(eq);
			eq = new Equations(new TArray(e.typeAttendu), e.e1.typeAttendu,e.toString());
			retour.add(eq);
		} else if(e.e1 instanceof Put) {
			Equations eq = new Equations(new TInt(), e.e2.typeAttendu,e.toString());
			retour.add(eq);
			eq = new Equations(new TArray(e.typeAttendu), e.e1.typeAttendu,e.toString());
			retour.add(eq);
		}else {
			System.err.println("expected an array");
			System.exit(1);
		}
		retour.addAll(e.e1.accept(this));
		retour.addAll(e.e2.accept(this));
		e.e2.env.removeFirst();
		return retour;
	}

	@Override
	public LinkedList<Equations> visit(Put e) {
		LinkedList<Equations> retour = new LinkedList<Equations>();
		LinkedList<Type> tParams = new LinkedList<Type>();
		e.e1.typeAttendu = Type.gen();
		e.e2.typeAttendu = Type.gen();
		e.e3.typeAttendu = Type.gen();
		e.e1.env = e.env ;
		e.e2.env.addAll(e.env);
		e.e3.env = e.env;
		if(e.e1 instanceof Array){
			((Array)e.e1).e2.typeAttendu = Type.gen();
			Equations eq = new Equations(new TArray(), e.e1.typeAttendu,e.toString());
			retour.add(eq);
			eq = new Equations(new TInt(), e.e2.typeAttendu,e.toString());
			retour.add(eq);
			if(e.e3 instanceof Var) {
				Type ts = ((Var)e.e3).rechercheEnv();
				eq = new Equations(ts, ((Array)e.e1).e2.typeAttendu,e.toString());
				retour.add(eq);
			} else {
				eq = new Equations(e.e3.typeAttendu, ((Array)e.e1).e2.typeAttendu,e.toString());
				retour.add(eq);
			}
		} else if (e.e1 instanceof Var){
			Type ts = ((Var)e.e1).rechercheEnv();
			if(ts instanceof TVar){
				Equations eq = new Equations(new TInt(), e.e2.typeAttendu,e.toString());
				retour.add(eq);
				eq = new Equations(new TArray(), e.e1.typeAttendu,e.toString());
				retour.add(eq);
			} else if (ts instanceof TArray) {
				Equations eq = new Equations(new TInt(), e.e2.typeAttendu,e.toString());
				retour.add(eq);
				eq = new Equations(new TArray(), ts,e.toString());
				retour.add(eq);
			} else {
				System.err.println("expected a type var");
				System.exit(1);
			}
		} else if(e.e1 instanceof Get) {
			Equations eq = new Equations(e.e1.typeAttendu, new TArray(e.e3.typeAttendu),e.toString());
			retour.add(eq);
			eq = new Equations(new TInt(), e.e2.typeAttendu,e.toString());
			retour.add(eq);
		} else if (e.e1 instanceof App) {
			Equations eq = new Equations(e.e1.typeAttendu, new TArray(e.e3.typeAttendu),e.toString());
			retour.add(eq);
			eq = new Equations(new TInt(), e.e2.typeAttendu,e.toString());
			retour.add(eq);
			
		} else if (e.e1 instanceof Put) {
			Equations eq = new Equations(e.e1.typeAttendu, new TArray(e.e3.typeAttendu),e.toString());
			retour.add(eq);
			eq = new Equations(new TInt(), e.e2.typeAttendu,e.toString());
			retour.add(eq);
		}
		else {
			System.err.println("expected an array");
			System.exit(1);
		}
		retour.addAll(e.e1.accept(this));
		retour.addAll(e.e2.accept(this));
		retour.addAll(e.e3.accept(this));
		if(e.e1 instanceof Get ){
			if(((TVar)e.e1.typeAttendu).typeParamArray != null) {
				Equations eq = new Equations(((TVar)e.e1.typeAttendu).typeParamArray, e.e3.typeAttendu,e.toString());
				retour.add(eq);
			} else {
				((TVar)e.e1.typeAttendu).typeParamArray = Type.gen();
				Equations eq = new Equations(((TVar)e.e1.typeAttendu).typeParamArray, e.e3.typeAttendu,e.toString());
				retour.add(eq);
			}
		}
		if(e.e1 instanceof Var) {
			Type ts = ((Var)e.e1).rechercheEnv();
			if (ts instanceof TVar && ((TVar)ts).typeArgs != null){
				if(e.e3 instanceof Tuple){
					if(((Tuple)e.e3).es.size() == ((TVar)ts).typeArgs.size()){
						int cpt =0 ;
						while(cpt < ((TVar)ts).typeArgs.size()){
							Type r = ((TVar)ts).typeArgs.get(cpt);
							Type s = (((Tuple)e.e3).es.get(cpt)).typeAttendu ;
							Equations eq = new Equations(s, r,e.toString());
							retour.add(eq);
							cpt++;
						}
					} else {
						System.err.println("expected similar size of tuple");
						System.exit(1);
					}
					
				} else if(e.e3 instanceof Var){
					Type t = ((Var)e.e3).rechercheEnv();
					if (t instanceof TVar && ((TVar)t) != null){
						Type r = ((TVar)ts).typeParamArray;
						Type s = ((TVar)t);
						Equations eq = new Equations(s, r,e.toString());
						retour.add(eq);
					}
				} else {
					System.err.println("expected similar size of tuple");
					System.exit(1);
				}
			}
		}
		if(e.e3 instanceof Var && e.e1 instanceof Array && ((Array)e.e1).e2 instanceof Tuple){
			Type ra = ((Var)e.e3).rechercheEnv();
			if( ra == null ){
				System.err.println("error "+((Var)e.e3).id+" is not defined");
				System.exit(1);
			} else if ( ra instanceof TVar && ((TVar)ra).typeArgs != null){
				if(((TVar)ra).typeArgs.size() == ((Tuple)((Array)e.e1).e2).es.size()){
					int cpt =0 ;
					while(cpt < ((TVar)ra).typeArgs.size()){
						Type r = ((TVar)ra).typeArgs.get(cpt);
						Type s = (((Tuple)((Array)e.e1).e2).es.get(cpt)).typeAttendu ;
						Equations eq = new Equations(s, r,e.toString());
						retour.add(eq);
						cpt++;
					}
				} else {
					System.err.println("expected similar size of tuple");
					System.exit(1);
				}
			}
		} else if(e.e3 instanceof Tuple && e.e1 instanceof Array && ((Array)e.e1).e2 instanceof Tuple) {
			if(((Tuple)e.e3).es.size() == ((Tuple)((Array)e.e1).e2).es.size() ){
				int cpt =0 ;
				while(cpt < ((Tuple)e.e3).es.size()){
					Type r = (((Tuple)e.e3).es.get(cpt)).typeAttendu ;
					Type s = (((Tuple)((Array)e.e1).e2).es.get(cpt)).typeAttendu ;
					Equations eq = new Equations(s, r,e.toString());
					retour.add(eq);
					cpt++;
				}
			} else {
				System.err.println("expected similar size of tuple");
				System.exit(1);
			}
		}
		Equations eq = new Equations(new TUnit(), e.typeAttendu,e.toString());
		retour.add(eq);
		e.e2.env.removeFirst();
		return retour;
	}

	@Override
	public LinkedList<Equations> visit(Unit unit) {
		Equations eq = new Equations(new TUnit(), unit.typeAttendu,unit.toString());
		LinkedList<Equations> retour = new LinkedList<Equations>();
		retour.add(eq);
		return retour;
	}

	public static LinkedList<Equations> resolution(LinkedList<Equations> lEq) {
		int assertNotInfiniteBoucle = 0;
		if (lEq == null || lEq.isEmpty()) {
			return lEq;
		}
		for (int i = 0; i < lEq.size(); i ++) {
			if (assertNotInfiniteBoucle <= lEq.size()*lEq.size()) {
				Equations eq = lEq.get(i);
				if (eq.t1 instanceof TVar) {
					if (eq.t2 instanceof TVar) {
						Equations toInsert = lEq.remove(i);
						lEq.addLast(toInsert);
						i--;
					} else {
						// on parcourt la liste en remplacant var pas son type
						for (int j = i+1; j< lEq.size(); j++) {
							Equations eq2 = lEq.get(j);
							if (eq.t1.equals(eq2.t1)) {
								eq2.t1 = eq.t2;
							} else if (eq.t1.equals(eq2.t2)) {
								eq2.t2 = eq.t2;
							}
						}
					}
				} else {
					if (eq.t2 instanceof TVar) {
						// on parcourt la liste en remplacant var pas son type
						for (int j = i+1; j< lEq.size(); j++) {
							Equations eq2 = lEq.get(j);
							if (eq.t2.equals(eq2.t1)) {
								eq2.t1 = eq.t1;
							} else if (eq.t2.equals(eq2.t2)) {
								eq2.t2 = eq.t1;
							}
						}
					} else {
						if (!eq.t1.equalsType(eq.t2)) {
							System.err.println(eq.v.toString() + " > type error : " + eq.t1.toString() + " != " + eq.t2.toString());
							System.exit(1);
						} 
					}
				}
				assertNotInfiniteBoucle ++;
			}

		}
		return lEq;
	}
}
