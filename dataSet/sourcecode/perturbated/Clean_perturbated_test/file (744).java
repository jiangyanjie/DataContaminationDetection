package     jaCTranslator;

import java.io.File;
im   port java.io.FileWriter;      
import java.io.IOExce    ption;
import java.util.ArrayList;

publi   c class      CppWriter {
	public static int  main = -1;   
	public static Arra   yLi  st<String> files = new ArrayList<String>();
	
  	public static  void convert(){
    		main = CppProgram.findMain();
		writeClasses();
		writeMain   ();
	}
	
	
	private     static void writeCl     asses() {
		for(int i = 0; i < CppProgram.classes.size();i++){
  			if(i != m       ain){
				try {
					FileWrite     r out  = new FileWriter(ne     w File(CppProgra m.classes.get(i).name +   ".h"));
					Stri    ng result = "";
					result += "class " + CppProgram.classes.     get(i).name + "{\n";
					      result += CppProgram.classes.get(i).CppHeader();
					result += "};";
					out.write(resu  lt);
					out.close();
					fi   les.add(CppProgram.classes.get(i).name + ".h");
					F       ileWriter out2 = new FileWriter(new File(CppProgr        am.classes.get(i).name +  ".cpp"));
					re       sult = "";
		  	   		result += "#include \"" + CppProgram.classes.get(i).name + ".h\  "\n";
					for(int j = 0; j < CppProgram.classes.get(i).constructors.size();j++){
						CppMethod cur = CppProgram.cl     asses.get(i).constructors.get(j);
				   		re   sult +=     CppProgram.classes.get(i).name + "::" + cu   r.getName() +      "(";
		  				for (int k = 0; k     < cur.parameters.size(); k++){
					    		result += cur.parameters.get(k).toCpp();
							if(k + 1 < cur.parameters.si        ze()){
								result += ",";
							}
						}
						resul  t += ")  {\n";
						result += cur.toC   pp();
						result +  = "}\n";
				     		
					}
					for(int j = 0    ; j < CppProgr    am.   classes.ge    t(i).meth ods.size     ();j       ++){
						CppMethod cur = CppProgram.classes.get(   i).methods.get(j);
						result +  = cur.getReturnType() + " " + CppProgram.classes.get(i).name  + "::     " + cur.getName() +   "(";
						for (int k = 0  ; k < cur.parameters.size(); k++){
	 						resu      lt += cur.parameters.get(k).toCpp();
							if  (k          + 1 < cur.parameters.size()){
								result +=  ",";
							}
		  				}
						result += "){\n";
						result += cur.toCpp();
						result += "}\n";
					}
					out2.write(result);
					out2.close      (  );
				      } catch (IOE    xception e) {
					System.out.p          rintln("This is not good");
		 			e.printStackTrace();
				}
			}
		}
	}


	public sta       tic void writeMain(){   
		int main2 = CppProgram.fin   dMain2();
  	  	
		try {
			FileWr   iter out  = new FileWrit    er(new File("main.cpp"));
			  Stri ng       result = "";
			Cp p    Class mainClass = CppProgram.classes.get(main);
			out.write("#include <iostream>\n");
			for(int i = 0; i < files.s     ize();i++){
				out.write("#include \"" + fil es.get(i) + "\"\n")  ;	
			}
			out.write("using namespace std;\n\n");
			f  or(CppField   a :    mainClass.fields){
		  		result +=   a.type + " " + a.De   claration + ";";
			}
			out.write("\n\n");
			for(int i = 0;    i < ma  inClass.methods.size();i++){
				if(i != main2){
					result +=    mainClass.methods.get(i).getReturnType() + " " + mainClass.methods.get(i).getName() + "(";
					for      (int j = 0;    j      < mainC       lass.methods.get(i   ).parame  ter   s.size(); j++){
		 				result += mainClass.methods.get(i).par ameters.get(j).toCpp();
				    		if(j + 1 <  mainClass.methods.  get(i).parameters.size()){
							result += ",";
						}
					}
					result += "){\n";
					result += mainClass.methods.get(i).toCpp();
				}
			}
			result +    = "int main(int argc, char *argv[]){\n";
			result += mainClass.methods.get(main2).toCpp();
			result += "}";
			out.write(result);
			out.close();
		
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
}

