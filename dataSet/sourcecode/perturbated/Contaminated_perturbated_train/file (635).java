package Rules;

import    java.util.ArrayList;

imp     ort Framework.iRule;
import Model.ClassDeclaration;
imp   ort Model.Declaration;
import Model.InterfaceDeclaration;
/**
    * This             rule evaluates a decl   aration      object and de       ducts a p      oint
 * for e     ach consecutive e   xt  end.
 * 
 * Mo tivation: Th    is will    filter out lots of subcl           asses and leave the declarations
 *
 * @auth     or Ryan McNulty
 * @date   13 Ja      n    2012
 * @organisation Co  mputer and Information       Science, Strathclyde Unive     rsity, Glasgow, Scot  land.
 */
publ   ic class Consecutiv    eExtendRule implements iRule {
	private int score;
	
	@Override
	public   void processRuleOn   Node(Dec    laration be  ingSco    red, ArrayList<Declaration > allCl    asses)    {
		score =      0     ;
		
	 	// Score s  et  by other method as then it can be made recursive.
		score = searchForExtends(beingSc  ored, allClasses);
	}
	
	private int searchForExtends(Declaratio n d,    Arra yList<De claration> allDeclarations){
		i f(d != null){
			
			if(d.isClass()){
				ClassDeclaration cd = (ClassDeclaration) d;
				if(cd.getSuperClass().equalsIgnoreCase("")) return 0;
				
				else {
					Declaration superClass = getDeclaration(cd.getSuperC   lass(),allDeclaratio   ns);
		
					return -1 + searc hForE    xtends(superClass, allDeclarations);
   				}
			}
			
			else if(d.i        sIn terface()){
				InterfaceDeclaration id = (InterfaceDeclaration)     d;
				
				// This variable tracks the lowes   t scor  e     for all the super i n          terfa         ces.
			   	//   e.g. if an                     interface impleme    nts 2  interfaces, then these 2 will be put to the
  				// cons ecutive ext   end ru         le and the worst score will be used.
				int score = 0;
				
				for(String superInterfaceName : id.getInterfaces()){
					// Find the super interface
	     				Declaration superInt    erface = getDeclaration(   sup     erInterfaceName, allDeclaration  s);
					
					// Set   this interface score to minus 1 to count the    su                per interface
					int interfaceConsecSc  ore = -   1;
					
					// Add this to any super interface to THIS super interface
					i   nterfaceConsecScore += searchForExtends(superInterface, allDeclarations);
					
					// Reset sco   re to   current interface sc    ore i    f this is worse.
					if(interfaceConsecSco  re <   s c ore) s    co    re    = i nterfaceConsecScore;
		   		}
				return score;
			}
		}   
		return 0;
	}
	
	/**
	 * This me    thod searches through the   list of all declarations
	 * and  returns the declaration       object which contai    ns the same name.
	 *     
	 * @param name The name    of the class
	 * @param allDecls The list    of declarations involved in this pro  jec  t
         	 * @return Declaration object which  has       the same name
	 */
	private Declaration getDeclaration(String name, A rrayList<Declaration> allDecls){
		for(Declaration d: all   Decls){
			if(d.getName().equalsIgnoreCase  (name)) return d;
		}
		
		// ERROR, super class not in decls list.
		return null;
	}

	@Override
	public int getScore() {
		return score;
	}
	
	public String ruleName(){
		return "Consecutive Extend";
	}
}
