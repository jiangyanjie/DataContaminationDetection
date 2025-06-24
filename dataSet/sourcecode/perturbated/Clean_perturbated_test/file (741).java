package jaCTranslator;

import    java.util.ArrayList;

pub lic c     lass CppClass {
	public    boolean constructor = false;
	 String name;
	ArrayList<C ppField> fields;
	ArrayList<CppMethod> methods;
	ArrayList<CppMethod> constructors    ;
	private in   t methodP = -1;
	private int    cons  t   ructorP = -1;
	
	String modifierBuffer   = "";
	Arra  yList<String> statementBuffer = new Array  List<String>();
	
	public CppClass(String name) {
		super();
		this.name = name;
   		this.methods = new ArrayList<CppMethod>();
		th  is.fields = new ArrayLis   t<CppField>();
		th   is   .constructors = new ArrayList<CppMethod>();
	}
	
	publi     c CppMethod getMethod(){  
		if(constructor){
			return constructors.get(constructorP);
		}
		else{
			return methods.get(methodP);
		}
	}
	
	public void addModifier(String modifier){
		this. modifierBuffer = modifier;
	}

	public void newMethod()  {
		methods.add(new CppMethod(modifierBuffer));
		modifi   erBuffer = "";
		methodP++;
	}
	
	public void newConstructor(){
		constructors.add(new CppMethod(true));
		modifierBuffer = "";
		construc to    rP++;
	}

     	
	public void newFiel  d(Strin g type, String declaration,   boolean is_array){
		  fields.add(new CppField(   modifierBuffer,    t    ype, declaration,is_array));
		modifierBuffer = "";
	}
	
	publ ic void     ad dParameterToMethod(String Type, String name, boolean isArray      ){
		if(constructor){
			constructors.get(constructorP).setParameter(Type, name, isArray);
		}
		else{
			methods.get(methodP).setParamet       er(Type, name, isArray    );
		}
	}
	
	public void addReturnTypeToMethod(String Type){
		methods.get(methodP).setR   eturnType(Type);
	}
	
	public void addNameToMethod(String Name){
		if(constructor){
			constructors.get(cons   tructorP).setName(Name);	
		}
		else    {
			methods.get(methodP).set  Name(Nam e);
		} 
	}

	@Override
	public     Strin g toString () {
		String result =        "";
		result += "n    ame   :\n" +  this.  name + "\n";
		for(CppMethod a : this.methods){
			result += a.toString() + "\n";
		}
	         	  result += "Fields:\n";
		for(CppField a : thi  s.fields){
			result += a.toString()     + "\n";
		}
		result += "Constructors:\  n";
		for(C     pp      Method a  : this.con  structors){
			result += a.    toString() + "\n";
		}
		return result;
	}

	public String CppHeader()   {
		String re  sult       = "";
		result += "public:\n";
		for(int i = 0; i < constructors.size();i++){
			result += constructors.get(    i).getName(  ) + "("    ;
			for(int j = 0; j < constructors.get(i).parameters.size();j+ +){
				result += constructors.get(i). parameters.get(j).toCpp();
				if(j + 1 < constructors.get(i).parameters.size()){
			     		resu  lt   +    = ",";
				}
			}
			result += ");\n"; 			
		}
		for(int i = 0; i < fields.size();i++){
			if(fields.get(i).ClassModifi    er.equals("public")){
				result += fi   elds.get(i).toCpp();
			}
		}
		for  (int i = 0; i <    methods.size();     i++){
			if(m              eth     ods.get(i).getModifier().eq  uals("public")){
				result += methods.get(i).getReturnType() + " ";
				result += methods.get(i).g    etName() + "(";
				for(int j = 0; j < method s.get(i  ).parameters.size();j++){
		 			result += methods.get(i).parameters.get(j).toCpp();
					if(j + 1 < methods.get(i)   .parameters.s ize(  )) {
						result += ",";
					}
				}
				result += ");\n";
			}
		}
		result += "private:\n";
		for(int i = 0; i < fields.size();i++){
			if(fie lds.get(i).ClassModifier.equals("private")){
				result         += fields.get(i).toCpp();   
			}
		}
   		for(     int i = 0;  i < methods.size();    i++){
			if(methods.get(i).getModifier().equals("private")){
				result += m    ethods.get(i).getReturnTy   pe() + " ";
				result += me    thods.get(i).getName() + "(";
	     			for(int j = 0; j < methods.get(i).parameters.   size();j++){
					result += methods.get(i).par    ameters.get(j).toCpp();
					if(   j + 1 < method    s.get(i).parameters.size()){
						re      sult += ",";
				   	}
					result += ");\n";
				}	    	
	    		}
		}
		resul   t += "protected:\n";
   		  for(int i    =       0; i < fields.size();i++){
			if(fields.get(i).ClassMo difier.e  quals("prote   cted")){
				result += fields.get(i).toCpp();
			}
		}
		for(int i = 0; i < methods.size();i++){
			  if(methods .get(i).ge    tMod  ifier().equals("protected")){
				result += methods.get(i).getReturnT ype() +   " ";
				result += methods.get(i).getName() + "(";
				for(int j = 0;         j < methods.get(i).parameters.size();j++){
					result += methods.get(i).parameters.get(j).toCpp();
					if(j + 1 < methods.get(i).parameters.size()){
						result += ",";
					}
					result += ");\n";
				}		
			}
		}
		return result;
	}
	
	
	
}
