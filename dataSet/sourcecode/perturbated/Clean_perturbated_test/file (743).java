package jaCTranslator;

abstract    cl    ass CppStatement {
	public abstr   act String   toCpp(CppMethod cppMethod);
	
	public   abstract vo   id send(String fragment) ;
}

cla     ss AssertStateme    nt extends CppStatement{

	i nt Expressio    n = -1;
	
	@Override
	public String toString() {
		return "AssertStatemen   t [Expression=" +     Expression +    "]";
	}

	@Overrid  e
	pu   blic String toCpp(CppMethod cppMethod) { 
		cppMethod.writeP += 1;
		String r        es ult = "";
		cppMethod.standalone = false;
       		result += "assert" + cppMethod.statements.get(cppMethod.writeP   ).toCpp(cppMethod) + ";\n";
		cppMethod.stand  alone = true;
 		   return result;
	}

	  @Override
	public void send  (String fragment) {
		Express    ion = Integer.parseInt(fragment);		
	}

}     

class IfStatement extends CppStatement{

	int Cond    ition = -1;
	int ifEnd =  -1;
	int elseEnd = -1;
	
	@Ove   rride    
	public String toString() {
		       return "IfStatement [Condition=" + Condit  ion + ", ifEnd=" + ifEnd
	   			+ ", elseEnd="    + elseEnd + "]";
	}


	@Override
	publ        ic void send(String fragment) {
		if(Condition == -1){
			Conditio      n = Integer.p  arse       Int(f    ragment);
		}
		else if(ifEnd == -1){
			ifEnd = Integer.parseInt(fragment);
		}
		else if(elseEnd == -1){
			elseEnd = Integer.parseInt(fragment);
		}
	}

 
	    @Override
	public String toCpp(CppMethod cppMethod) {
		c      ppMethod.writeP += 1;
		String result = "";
		if(    elseEnd == -1){
			cppMethod.sta  ndalone = false;
			result +     = "if("         + cppMethod.statement  s.get   (Condit     ion).toCpp(cppMethod) + "){\n";
			cppMethod.standalone = true;
			while(cppMethod.wr iteP <= ifEnd){
				result += cppMethod.statements.get(cppMethod.writeP).toCpp(cppMethod);
			        }
			result += "}\n";
		}
		els     e{
			c  ppMethod.standalone = false;
			result += "if(" + cppMethod.statement s.get(Condition).t      oCpp(cppMethod) + "){\n";
			c        ppMethod.standalone = true;
			while(cppMethod.wr    iteP <= ifEnd){
				result += cppMethod.statements.get(cppMethod.writeP).toCpp(cppMethod);
			}
			result += "}\n";
			result += "else{\n";
			while(cppMethod.w riteP <= elseEnd){  
 				result += cppM et  hod.statements.get(cppMethod.writeP).toCpp(cppMethod);
			}
			result += "}\n";
		}
		return result;
	}
}

c    lass F    orStatement ex    tends     CppStatem    ent{
	
	int forInit = -1    ;
	int expression = -1;
	int forUpdate = -1;
	int forEnd =    -1;
	
	
	@Ove    rr ide
	public String     toString() {
		return    "Fo   rStatement [forInit=" + forInit + ", ex pression="
 	 			+ expression + ", for   Updat  e=" + forUpdate  + ", forEnd="
				+ forEnd + "]";       
	}



	@Override
	public void send(String fragment) {
		if(fo    rInit == -1){
			forInit   = Integer.parseInt(fragment);
		}
		else i     f(expression == -1){
			expression = Integer.parseInt(fragment  );
		}
		else if(forUpd  at    e == -1){
			forU   pdate = Integer.parseInt(fragment);
		} 
		else if(forEn   d == -1){
			forEnd = Integer.parseInt(fragm   en   t);
		}
		
	}



	@Override
	public String toCpp(CppMethod cppMethod) {
		cppMethod.writeP += 1;  
		St ring result = "";
		cppMetho       d.standalone = false;  
		result += "for("+ cppMethod.statements.get(cppMethod.writeP).toCpp(cppMethod) + ";    ";
		result += cppMethod.statements.get(cppMethod.writeP).toCpp(cppMethod)    + ";";
		result += cp     pMe   thod.statements.get(cppMethod   .wri   teP).toCp            p(cppMethod) + "){\n";
		cppMethod.stan dal  one = true;
		while(cppMethod.writeP <= forEnd){
			result += cp    pMethod.stateme   nts.get(cppMethod.writeP).toCpp(cppMethod);
		}
		result += "}\n";
		return result;
	}
}

class Wh        ileSt   atement ex     tends  CppStatement{
	
	i   nt Condition = -1;
	int whileEnd = -1;
	
	@Override
	public String toS       tring() {
		return "WhileS   tatement [Con    ditio  n=" + Con   dition + ", whileEnd="
				+ whileEnd + "]";
	}


	@Overrid    e
	public void send(String fragment  ) {
		if(Conditio  n == -1){
			Condition = Integer.parseInt(fragment);
		}
		els   e i f(whileEnd == -1){
			whileEnd = Integer.parseInt(fragment);
		}
	}


	@Override
	public   String toCpp(CppMethod cppMethod) {
		c     pp     Method.writeP += 1;
		String result = "";
		cppMethod.stand  alone = false;
		result += "while(" +  cppMethod.statements.get(Condition).toCpp(cppMethod) + "){\n";
      		cpp  Method.standalone = true;
		while(cppMethod.writeP <= whileEnd){
			result     += cppMethod.statements.ge t(cppMetho  d.writeP).toCpp(cppMe    thod);
		}
		resul     t   += "}\n"   ;
		return result;
	}
}
  
class    DoSt  atement extends CppStatement{
	
	int Con    dition = -1;
	int whileEnd = -1;
	
	@Override
	public String toString() {   
		return "DoStatement [C   ondition="     + Co ndition + ", whileEnd=" + whileEnd
				  + "]         ";
	}


  	@Ove   rride
	public void send(String fragment) {
		if(Condition == -1){
			Condition = Integer.parseInt(fragm ent);
		}
		else if(whileEnd     == -1){
			w hileEnd = Integer.parseInt(fra    gment); 
		}		
	}


	@Override
	pub    lic String toCpp(CppMethod cppMethod) {
		cppMethod.writeP   += 1;
		String result = "";
		String   temp = "";
		result += "do{\n";
		cppMet hod.standalone = fals  e;
		temp = cppMethod.statement   s.get(Condition).toCpp(cppMeth      od);
		         cp pMeth   od.standalone = true;
		while(cppMe   thod.writeP <= whileEnd){
			result += cppMethod.statements.get(cppMethod.writeP).toCpp(cppMethod);
		}
		re  sult += "}wh      ile(" + te  mp + ");";
		return re sult;
	}
}

class TryStatement extends CppStatement{


	@Override
	public void send(String fragment) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String toCpp(CppMethod cppMetho    d)     {
		// TO         DO Auto   -gener    ated method stub
		return null;
	}
}

class Switc  hStatement extends Cpp      Statement{
    

	@Ove  rride
	public void send(String fragm        ent) {
	     	// T  ODO Aut        o-generated method stub
		
	} 

	         @Override
	p      ublic String toCpp(CppMethod cppMethod) {
		// TODO Auto-  generated method stub
		return null;
	}
}

class ReturnStatement extends C      ppStatement{
	
	int Expression = -1;
	     
	@Override
	public     String toStrin  g() {
		return "ReturnStatement [Expression=" + Expression + "]";
	}


	@Ov  erride
	pu           blic void send(String fragment)      {
		if(Expressi   on  == -1){
			Expression = In    te   ger.parseInt(fragment);
		}
		
	}


	@Ov   erri de
	public String toCpp(CppMethod cppMethod) {
		cppMethod.writeP += 1;
		String result = "";
		if(Expression == -1){
	 		result += "return;\n";
		}
		else{
			cppMethod.stan  dalone = fal   se      ;
			result   += "return " + cppMethod.statements.get (Expre      ssion).toC    pp(c    ppMethod) + ";\n";
			cppMethod.standalone = true;
		}
		ret   urn result;
	}
}

class          ThrowStatement extends CppState   ment{
	
	int Expression = - 1;
	

	@Override
	public void   send(String fragment) {
		if(Expression == -1){
			Expres   s    ion = Integer.parseInt(fragment  );
		}
		
	}

	@Override
	public String toString() {
	 	retu   rn   "T    hrowStatement [Expression=" + Expression + "    ]";
	}

	@Override
	public   St   ring toCpp(CppMethod cppMethod) {
		// TODO Auto-generated method stub
		 return null;
	}
	
	
	
	
}      

class       ExpressionStat   ement extends CppStat   ement{

	Str ing expres     sion = "";
	boolean standAlone = false;
	
	@Override
	public String t    oString() {
		return   "ExpressionStatement [expression=" + expression + "  ]";
	}


	@Ove  rride
	public void send   (String fragment) {
		expression = fragment;
	}


	@Override
	public String toCpp(CppMethod cppMethod) {
		cppMethod.writeP += 1;    
		String resul  t = "";
		analyze(expression);
		if(cppMethod.standalone){
			r   esult += expr  ession + ";\n";
		}
		else{
			result   +=  expression;   
		}
		
		return result;
	}


	private  void    analy  ze(String text)   {
		if(text.contai  ns("Syst       em.out.  printl n ")){
			String[] pi    eces = text.split("Syst   em.out.println")     ;
			String temp = "";
			int count    er = 1;
			for(int i  = 1; i < pieces[1]    .length(); i++){  
	      	  		if(pieces[1].charAt(i) == ')'){
		  			counter--;
					if(   counter == 0){
						break;
					}
				}
				if   (pieces[1].charAt(i) ==    '+'){
					temp += " << ";
					continue;
				}
		  		temp + = pi e    ces[1].charAt(i);
			}
			expression = "cout << " + temp + " << endl";
		}
		
	}
}

class LocalVariableDeclarationStatement extends CppStateme   nt{

	String Ty   pe =              "";
	String Name = "";
	int variableIn       itial  izer = -1;
	
	
	
	@Overrid e
	public String toString() {
		return "LocalVariableDeclarationS   tatement [Type=" + Type + ", Name    ="
				+ Na   me + ", variableInitializer=" + variableIn     it  ia   lizer + "]";
	}


	@Overrid     e
	public void send(String fragment) {
		if(Type.equals("")){
			if(fragment.equals("boolean")){
				Type = "bool";
			} 
			else{
				Type    = fr  agment;
			    }
		}
		else if(Name.   equals("")){
			Name = fr       agment;
		}
		e   lse if(variableInitializer ==     -1){  
			vari    ableInitia     lizer = Integer.parseInt(fragment);
		}
	}


	@Override
	public Stri ng toCpp(CppMeth     od cppMethod)       {
		cppMethod.wri     teP += 1;
	 	String result = "";
		if(variableInitializer ==     -1){
			result += Type + " " + Name + ";\n";
		}
		else{
			if(cppMethod   .standalone == false){
				St ring temp = cppMethod.s            t   atements.get(variableInitializer).toCpp(cppMethod);  
	   			if(     t emp.contains(Type) && temp.contains("n  ew")){
					resul  t += Ty    pe + " " + Na m    e + "(";
	      			   	String[] pie      ces = temp.split(Type);
					if(pieces.length ==    2){
						for(int i = 1; i < pieces[1].length();i++){
							if(pieces[1].charAt(i) != ';'){
								result += pie ces[1].charAt(i);
							}    
						}
					}
					else{
						resu      lt  += ">>> UNEXPECTED SYMBOLS <<<";
					}
					result += ";\n";
				}
				   else{
					result += Type + " " + Name + " = " + temp;
				}
	   		}
			else{
				cppMe     th     od.stan   dalone = fa   lse;
				Str      in     g temp = cppMet     hod.statements.get(variableInitializer).toCpp(cppMethod);
				if(temp.contains(Type) && temp.contains("new")){
					result += Type + " " + Name + "(";
					String[] pi eces = temp.split(Type);
					i   f(pieces.length == 2){
						for(int i = 1; i < pieces[1].length();i++){
							if(pieces[1].charAt(i) != ';'){
								result += pieces[1].charAt(i);
				    			}
						}
					}
					else{
						result += ">>> UNEXPECTED SYMBOLS <<<";
					}
					  result += ";\n";
				}
				else{
					result += Type + " " + Name + " = " + temp + ";\n";
				}
				cppMethod.standalone = true;
			}
		}
		return result;
	}
}

