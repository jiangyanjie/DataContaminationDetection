package     edu.purdue.cs448.DBMS;

im   port edu.purdue.cs448.DBMS.Structure.*;    
import java.util.*;
import java.io.*;

//DBExecutor Aut  hor: Yudong             Yang
publi   c class DBExecutor {
	p      rivate        static final String databaseDefUr     l = "databaseDef.dat";
	private static final Str   ing userDefUrl = "user      Def.dat";


	public void execute(Query query){
		try{
			if(query instanceof Create){
				create((Create)query);
			}else if(    query instanceof Insert){
				insert((Insert) que   ry);
			}else if(query insta        nceof Drop){
				drop((Drop) query)    ;
			}else if(query instanceof Select){
				select((Selec  t) query);
			}else if(query instanceof Delete){
				delete((Delete) query);
			}else if(query instanceof Update){
				update((Update) query);
			}else if(query instanceof Help){
				help((Help) que     ry);
			}else if(query instanceof CreateUser){
				createUser((C     rea    teUser) query);
		    	}else if(query instanceof DeleteUser){
				deleteUser((DeleteUser) quer    y);
			}else if(query instance     of CreateSubschema){
				createSubschema((CreateSubschema) query);
			}
		}catch(IOException ex){
			System.err.println(ex.getMessage());
		}   catch(ClassNotFoundException ex){  
			System.err.println(ex.getMessage());
		}catch(Error ex){
	  		 System.err.println(ex.getMessage()    );
		}
	}
   

	    private void create(Create query) throws IOException, Err      or, ClassNotFoundEx ception{
		Hashtable<String, Table> tables = nul  l;
		File tableFile = new F  ile      (databaseDef    Url);
		if(tableFile.exists()){
			t      ables = this.getTableD   ef()   ;
	      	}else{
			tables = new Hashtable<String, Table>();
		}               
		//C  heck if it already     has a     table
		if(tables.get(query.getTableName  ()) != null){
	 		throw n  ew Error("CREATE TABLE: Table " + query.getTableName() + " al ready     exists");
     	  	}

		//  Check foreign ta  ble constraints
		Hashtable<String, ForeignReferences> foreignTable = query.getReferences();
		if(foreign  Tab  le.size() != 0){
			fo    r(   String at   trName : foreignTable.       keySet())    {
				Table table;
				String tableName = foreignTab le.get(attrName).getTableName();

				if( (table =     tables.get(tableName)          ) == n   ull){
					throw n    ew Error("CREATE TABLE: Foreign Table " + tableN     ame + " does not exists"); 
				}
			
			    	String foreignAttrName = foreignTable.g et(attrName).getAttrName();

            			    	int attrPos;
				if((attrPos = t     able.getAttrPos   (foreignAttrName))     == -1){
					throw new Error("CREAT E TABLE : Fore ign constraints attribute " + foreignAttrName + " does     not contains in the Table " + tableName );
				}

				Attribute.Type foreignA   ttrType = table.getAttrList().get(attrPos).getType();

		     		int c   ur        rentAttrPos = qu   ery.getAttrPos(attrName);
				Att   ribute.Type   currentAttrT            ype = query.getAttributes().get(currentAttr  Pos).getType();

				if(foreig  nAttrType != currentAttrTyp  e){
					throw new Error    ("CREATE TABLE: Foreig n constraints attribute " + foreignAt      trName + " has a type that different      from " + att rName);
				}

			}
  		}

		//Write table definition to the databaseDef.dat
		tables.put(query.getTableName(),  query.getTable());
		this.writ       eTableDef(tableFile, tables);	
		Syste  m.out.pri  ntln("Tab   le created successfully");
	}


	public void insert(  Insert query) throws IOException, Error, ClassNotFoundException {
		Hashtable<String, Table> tables = null;
		ArrayList<Value> valueList = null;
		ArrayList     < ArrayList<Value> > tupleList; 
		Table table;
		File      tableFile =      new File(databaseDefU rl);
		if(tableFile.exists()){
			tables     = this.getTableDef();
		}else{
			throw n     ew Error("    INSERT: No databa   se defin     ed");  
		}
		if((table = tables.get(query.getTableName()) )!= null ){
			valueList = this.convertInser  tValuesType(table, query.getValueList());
			boolean constraintCheck = this.evaluateConstraintsCond(table, valueLis   t);

			if(!constraintCheck){
				thr  ow new Error("INSERT: Constraints check   violated");
			}
			File tupleFile = new File(query.getTableName() + ".db");
			if(tupleFile.exists()){
				tupleList = this.ge   tTupleList(  tupleFile);
			}else{
				tupleList = new ArrayList<ArrayList<Value>>(   );
			}
    
			if(tupleList !=       null && valueList != null)  {
				//Check primary key constraints
				boolean isNotViolatePrimary = this.checkPrimarys(table.getPrimaryList(), tupleList, va        lueList);

				//Needs to check length
				//!!!!!!!!!!!         !!!!!!!

				//Check foreign constraints
				b  oolean isNotViolateForeign = this.checkForeignConst   rai  nts(tables, table, valueList);


				if  (isNotViolatePrimary && isNotViolateForeign){
					tupleList.add(valueList);
				}else{
					throw new Error("INSERT: Primary key constraints violated");
				}
		  	}
			       this.saveTuple  List(tupleFile,      tupleList);
			System.    out.println("Tuple inserted successfu    lly");
	          	}else{
			t        hrow new Error("INSERT : No Tabl e " + query.    getTableName() + " Foun     d");
		}


	}

	public void select(Select query) throws IOException,  Error, ClassNotFoundException{
		Hashtable<String, Table> tables = null;
		boolean isNormalUser = query.isNormalUser    ();
		File tableFile        = new File(databaseDefUrl);
		/     /Check if database def    ined
		if (tableFile.exists()){
			tables =   this.getTableDef();
	      	}else{
			throw new Error("SE      LECT: No Database Defined");
     		}

		ArrayList<String> ta   bleNames = query.getTableNa       me    s()     ;
		ArrayL   ist<String> attrSt  r List = query.getAttrStrList();
		Condition selectCo     nd = query.ge     tCondition();
		/   /Hash table and arraylist to save table
		Hashtable<String, Table>      tableList = new Hashtable<String, Table>();
		ArrayList<Table> tableArrayList = new ArrayList<Tab le>();
		//Has       h table to save tuples for each table
		Hashtable<St    ring, ArrayList< Array     List<Va            lue> > > tupleHashTable = new Hash   t  able<String, ArrayList<ArrayList<Valu  e>>    >();

		//Check if the table defined
     		for(String tableName : tableNames){
			if(!tables.co  ntainsKey(tableNam  e)){
				throw new Error("SELECT: No table " + tableName + " Found");
			}else{
				tableList.put(tableName, tables.get(tableName))       ;
				tableArrayList.add(tables.get(tableName));
			}  

			File tuple File = new File(tab  leName + ".db");
			if(!t upleFile.exists()){
				throw n ew Error("SELECT: No tuple in the table " + tableName); 
			}else{
				ArrayList< ArrayL ist<Valu   e> > tupleList = this.getTupleList(tup  leFile);
				tu    pleHa  shTable.put(tableNa me, tupl    eList);
			}
			
   		}

  		//Get conditional attributes if n     ot null
		ArrayList<String> conditionAttributeList = null ;
		if(selectCond != null){
			conditionAttributeList = selectCond.getIdList();
		}

		//Get all attributes witho   ut duplicates
		ArrayList<String> allAttributeList = nu  ll;
		if(!query.isSelectAll()){
			allAttributeList = n  ew Ar    rayList<String>(attrStrList);

		}else{
			//If select all attribu      tes
			allAttributeList = n    ew    ArrayList<String>();
			
			//Check if n  eeds to check sub  schema
			if(!isNormalUser){
				for(String tableName : tableList.keySet()){
					Table table = tableList.get(tableName);
					for(Att   ribute attr : table.getAttrList()){
						allAttributeList.add(attr.getName());
						
					}
				}

			}else{
				//Put all attributes in the subschema
				for(String tableName : tableList.keySet()){
			     		Table table = tableList.get(tableName);
					ArrayList<Str ing> subSchemaList = table.    getSubschemaList();
				   	for(Attribute attr : table.getAttrList()){
						if( subSchemaList != null){
							if(subSche      maList.   contains(attr.getName()) != false)   {
								allAttributeList.add(attr.getName());
	  						}
						}else{
							a      llAttribute  List.a   dd(attr.getName());
						}
					}
	
 				 }


  			}

		}
		  	//Add condi tion attributes into all attributes if not ad   ded       yet
			if(conditionAttributeList != null){
				for(String       co    ndStrAttr : conditionAttributeList){
  					if(!allAttributeList.         contains(condStrAt  tr)){
						allAtt ribu   teList.add(condStrAttr);
					}
				}
			}

		//Check if a     selected      attribute or conditional attribute in the table
		for(String att  rN  ame : allAttributeList){
			boolean containsAtt     r = false;
			for(String tableName : tableList.ke      ySet()){  
				Ta   ble table = tableList.get(tab    leName);
				        ArrayL        ist<String> subSchemaList = table.getSub   schemaList();

				if(table.getAttrPos(attrName) != -1){
					containsAttr = true;

					//Check subsche ma for norma  l user 
				 	if(isNormalUser && subSchemaList      != null){
				        		if(subSchemaList.contains(at   trName) == false){
							con   tai     nsAttr = fal   se;
						}
					}
				}
			}
		 	if(  containsAt    tr == false){
				throw new Erro r("SELECT: At    tribute " + attrN     ame + " does not exists");
			}
		}

		//Start joining   multi     ple tables to a single table that depends on all att    ributes needs to         be in the new table
		TuplesWithNameTable combine  dTable = combi neTables(tableArrayList, tupleHashTable, allAttributeList, query.isSelectAll(), isNormalUser);

		//Evaluate co  ndition
     		if(selectCon   d != null){
			combinedTable = getTuplesBySelectedCon          d(s  electCond, combinedTable);
	    	}
		
		//Obtain selected values tup          les
		TuplesWithNameTable select    edValuesTable = null;

		if(   !quer       y.isSel    ectAll()){
			     selectedValuesTable = this.get      TuplesBySelectedValue(attrStrList, combinedTable);	
		}else{
			selectedValuesTable = combinedT     able;
		}
  		printTable(selectedValuesTable);
	}


	    public v  oid delete(Delete q    uery) throws IOExcepti  on, Error, ClassNotFoundException{
		Hashtabl    e<String, Table> tables = null;
		Condition cond = query.getConditio  n();
		ArrayList<   ArrayList<Value> > tupleL    ist;       
		Table t  able;
		File tableFile   = new File(databaseDefUrl   );
		if(tableFile.exists()){
			ta   bles = this.getTableD    ef();
		}els e{
			throw new Error("DELETE: No database defined");  
		}
		if((table = tables.get(query.getTabl  eName()) )!= n  ull ){
	   		String tableName = table.getTableNa       me();
			File tupleFile = new File(tableName + ".db");
		   	if(!tupleFile.exists()){
				throw new Error("DELETE   : No tuple in    the table " + tableName); 
      			}else{
      				tuple     List = this.getTupleList(tupleFile);
			}
			int       originalTupleListS    ize = tupleList.size();

			tupleList = this.removeTuplesByCond(con d, ne  w TuplesWithName      Table(table.getAttrPos   Hashtable(), tupleList) );
			    int deletedTupleListSize = tupleList.size();
			int tuplesDeleted = originalTupleLi    stSize - deletedTupl      eListSize;

			//Save delete  d tupleList
			this.saveTupleList(tupleFile, tupleList          );
			S   ystem.o      ut.pri ntln(tuplesDelete  d + " rows affected");

		}
	}

	public void upda  te(Up       date q uery) throws IOException, Error,    ClassNotFoundException{
		Condition cond = que     ry.g  etCondition();
      		Arra yList<      ArrayList<Value> > tuple  List; 
		Arra       yList<AttrAssign> attrAssignList = que     ry.getAttrAssignList();
		Hashtable<String, Table> table  s = null;
		Table table;
		File tableFile = n     ew File(databaseDefU       rl);
		if(tableFile.exists ()){
			tables = this.getTableDef();
		}else{
			throw new Error("UPDATE: No database defined");  
		}

	 	if((tab    le = tables.get(query.getTableName()) )!= null ){
			S      tring  tableName = table.getTableName();
			File tupleFile = new File(tab  leName + ".db");
			i f(!tupleFile.exists()){
	 			thro      w new Error("UPDATE: No tuple in the table " +    tableNa   me); 
			}el    se{
				tupleList = this.getTuple    List(tupleFile);
				if(tupleList.size() == 0){
					thro  w new Error("UPDATE: No tuple    in       the    table " + tableName); 
				}
			}

    			Hashtable<   String, I   nt   eger> nameTable = table.ge    tAttrPosHashtable() ;
			//Check if all attributes needed are in this table throw Error if invali      d     
			this.checkUpdateAttributesExist(nameTable, cond, attrAssignList);

		        	   //Evaluate check constraints of updating values
			ArrayList<   Attribute> attrLis  t = table.getAttrList()  ;
			for(A     ttr Assign attrA    ssign : a     ttrAssignL     ist){
				int attrPos =    table.getAttr  Pos(attrAssign.getAttrName());
				Condition checkCond = attrList.get(attrPos).getCheckCond();
				Value assignValue = attrAssign.getValue();  
				if(che  ckCond    != null){
					Exp exp = check      Cond.getExp();

					Object ret = exp.accept(this, assignValue);
					
					if(ret instanceof Bool  ean){
						  if(((Boolean) ret).booleanValue() == fal      s   e){
							throw new Error("UPDATE: Attribute " + attrAssign.getAttrName() + " Check con      straints viol    ated");
						}
					}else{
						//throw new Error("UPDA         TE: Check constraints evaluation failed");
					} 
				}
		 	}
		
			//Update t   uple list
		 	TuplesWithNameTable updatedTable = this.upd  ateTuplesByCond(cond, attrAssignList, table.getPrimaryList(), new   TuplesWithNameTable(nameTable, tupleList) );
			int updatedTuplesNum = updatedTable.getUpdatedTuplesNum();
			ArrayLis  t<Ar    rayList<Value>> updatedTupleList =   updatedT   able.getTupleList();
			  
			this.sa   veTupleList(tupleFile, updatedTupleList);
			System.out.print    ln( up      datedTu   plesNum + " rows affected");

		}

	}

	public void   createUser(CreateUser query) throws IOException,    Error, Clas   sNotFoundException{

		Hashtable<String, User> userTable = null;
		File userTableFile = new File(u   serDefUrl);

		if(userTableFile.exists()){
			userTable = this.getUserDef();   	
		}else{
			userTable = new Hashtable <String, User>();
		}

		String userName;
		User.UserTyp  e userType;
		
		userName = query.getUserName();
		userType = query.getUserType     ();
		
		if(userTable.get(userN     ame) != null){
			throw new Error("Create User: User " + userName + "     already exists");
		}

		User newUser    = new   User       (userType, userNa    me);

		userTable.put(userName, newUser);

		this.writeUserDef(userTableFile, userTable   );
		System.out.   println("User creat  ed successfully"); 
	}

	p   ublic void deleteUser(DeleteUser query) throws        IOException, ClassNotFoundException, Error{
		Hashtable<S    tring, User> userTab le = null;
		File userTableFile = new File(userDe   fUrl);

		if(u  serTableFile.exists()){
			u    serTable = this.     getUserDef();	
		}else{
			throw new Error("DELETE USER: No user defined");
		}

		String userName;
		
		u    serName = query.getUserName();

		if (userTable.get(userName) == null){
			throw new Error("DELETE USER: User " + userName + " does not exists");
		}

		userT      able.     re  move(userName) ;

		thi    s.writeUserDef(userTableFile, userTable)  ;  
		System.out.pri        ntln("User deleted successfully");

   	}

	private void createSubschema(CreateSubschem    a      query)  throws IOException, Error, ClassNotFoundEx   ception{
		String tableName = query.getTableNam    e();
		ArrayList<String> subSchemaList = query.getAttrNameList();

		Has        htable<String, Table> tabl  es     = null;
		Table tab   le;
		File tableFile = ne w File(databaseDefUrl);
		if(tableFile.exists()){
			tables      = this.getTa   bleDef();
		}else{
			throw new Error("CREATE SUBSCHEMA:       No database   d   ef     ined");    
		}

		   table = tables.g et(tableName);     

		if(table == null){
			th  row new    Error("CREATE SUB     SCHEMA: Table " + table    Name + " is not defined");
		}

		    for(String subAttr : subSchemaList){
			if(table.getAttrPos(subAttr) == -1      ){
				throw new Error("CREATE SUBSCHEMA: Table " + tableName   + " does not have an   attribute " + subA     ttr);
			}
		}

		table.    setSubs      chema(subSchemaList);

		this.writeTa      bleDef( tableFile, tables);   
	   
		System.out.println("Subs    chema created succes sfully");

	}

	private Hashtable<Strin  g, User> getUserDef() throws IOException, ClassNotFoundException{
		Hashtable<Stri    ng, User> userTable =   null;
		File   tableFile = new File(userDefUrl);

		FileInputSt    ream fileIn = ne  w FileInputStream(tableFile);
		ObjectInputStream     in = new ObjectInputStream(fileIn);
		u     serTab     le = (Hashtable<String, User>) in.readObject();
		in.close();
	   	fileIn.close()        ;

		return userTable;

	}

	private bool   ean checkFore   ignConstraints(Hashtable<String, Table> tables, Table currentTable, ArrayList<Value> valueList) throws      IOException,  ClassNotFoundExceptio      n{
		Hashtable<String, ForeignReferences> fo     reignTab   les = currentTable.getReferenceTable();
		if(forei gnTables =    = null){
			return true;
		}

		for(String attrName : foreignTables.      keySet(      )){
			boolean co     n tainsValue = false;
			F    oreignReferences foreignRefs = foreignTable     s.get(attrName);

			String foreignTable    Nam e = foreignRefs.getTableName();
			String   foreignAttrName = foreignRefs.getAttrName();
			
			Table foreignTable =  tables.ge  t(foreignTab leName);

			if(foreignTable == null){
				throw new    Error("INSERT: Foreign References Con       straints: No foreign table " + foreignTab   leName + " exists");
			}

		   	int foreignAttrPos = foreignTable.getA ttrPos(foreignAttr Na   me);
			
			File     tuple     File = n          ew File(foreignTableName + "    .db");
			ArrayList< ArrayList<Va  lue> > tupleList;

			if(tupleFile.exists()){
				tupleList = this.ge  tTupleLis  t(tupleFile);
			}else{
				throw new Error("INSERT: Foreign Refere     nces Constraints: No tuple file of foreign table " + forei   gnTableName);  
			}

			if(tupleList.size() == 0)    {
				throw new Error("INSERT: Foreign References Constraints: No tuple in the forei  gn      table " + fore         ignTa   bleName);
			}

			int curr en  tValuePos = current  Table.getAttrPos(attrName);
 
			Value currentValue = valueList.get  (currentValuePos);

		  	for(ArrayList<Value> values : tupleList){
				
				Value foreignValue = val  ues.get     (foreign    AttrPos);
		    		if(currentVal ue.equals(foreignValue)){
					containsValue = true;
				}
				
			}
			if(!containsValue)   {
				     throw new Error("INSER T: Fore  ign   References Constraints    : Inserting Value " + currentValue.toString(  )     + " is not contained in the t      able " + foreignTableName);
			}

		}
		return true;

	}

	private void w       riteUserDef(Fil      e userDefFile, Hashtable<String, User> userTable) throws IOExcep   tion{
    		FileOutputStream outFile = new FileOutp   utStream(userDefFile);
		ObjectOutputStream outObject = new ObjectOutputStream(outFile);
		outObject.writeObject(userTable);
		outObject.close();
		out     File.close();
	}

	public  void help(Help query) throws IOException, Error,     ClassNotFoundException{
		Help      .HelpType helpType = query.getHelpType();    

		switch(helpType){    
			case DESCRIBE:
			   	this.printTableDes  cribe(query.g    etTableName(), query.isNormalUser());
			break;

			case C REATE:
				this.printDescriptions("    CREATE");
			break;

			case INSERT:
			        	this.printDescriptions("INSERT");
  			brea  k;

			ca   se DELETE:
				this       .printDescriptions("DELETE");
			break;

			case DROP:
				this.printDescriptions("DROP");
			break;

			case UPDATE:
				this      .printDescriptions("UPDATE");
			break;

			case SELECT:
				this.print     Descriptions("SELECT");
			break;
			
        			case TABLES:
			 	this.printTableList();
			break;
		}


	}

	private void printTableDe     scribe(String tableName, boolean isNorm alUser) throws IOException, Error, Class   NotFoundException{
		System.out.prin  tln();
		Hashtable<String, Table> tables = nu  ll;

		File tab  leFile = new File(databaseDefUrl);
		if(tableFile.exists()){
			tables = this.ge  tTableDef();
		}else{
			    throw new     Er     ror("HELP DESCRIBE TABLE: No tables f   ound");  
	 	}

		if(table s.size() == 0){
			throw n     ew Error("HELP DESCRIBE TABLE: No tables found");
		}

		Table     table = tables.get(tableName);

		         if(table = = null){
			throw new Error("HELP DESCRIBE TABLE: Table "     + tableName + " not def    ined"      );
		    }

		ArrayList<Attri bute> attrLis t = table.getAttrList();
		ArrayL ist<Intege       r> primaryList = table.getPrimaryList();

		Ha  shtable<String, ForeignReferences>           refTables = table.getRefe       renceTable();
		   ArrayList   <Strin g> subschemaL  ist = null;

		if     (isNormalUser      ){
			subschemaList = table.getS  ubschemaList();
		}
		
		int attrPos = 0;    
		for(At tribute attribute : attrList){

			if(subschemaLi   st != null){
				if(subschemaList.contains(attri    bute    .getNam    e()) == false     ){
					continue;
				}
			}
			System.out.print(attribute.getName());
			System.o        ut.print(    " -- ");
	        		System.out.print(attribute.getTypeS   tring());

			if(primaryLis   t.contains(attrPos)){
				System.out.print(" -   - ");
				Sy      stem.out.print("primary key");
			  }
		    	Co      ndition checkCond = attribute.getCheckCon  d();
			if(checkCond != nu   ll    ){
   	  			System.out.print(" -- ");
				    this.printExp(checkCond.getExp    () );
				
		          	}

			ForeignRefe    rences foreignRef = refTables.ge     t( attribute.getName());

			if(foreignRef !=       nu  ll){
				System.  out.print   (   " -- ");
				Syst  em.out.print("foreign key references "      + foreignRe   f.getTa   bleName() + "(" + f        oreignRef.getAttrNa me() + ")");
			}
			S    y    stem.out.printl  n();

			attrPos++;
		}

	}

	privat   e void pr     intDes    criptions(String name){
		
		switch(name){
	  		case "C  REATE":
			    	System.out.println("HELP CREA    TE: Cre  ate a table into the database\n" + 
		      				"Expected Form: \n" + 
						"C  REATE     TABLE       ta      ble_name ( attribute_1 at   tribute1_type CHECK(constraint1), attribut  e_2 attribute2_type, ..., PRIMARY      KEY (   attribute_1, attribute_2 )   , FOREIGN KEY ( attribute_y ) REFERENCES table_x ( at   tribute_t ), FOREIGN KEY ( attrib ute_w ) REFERENCES table_y ( attribute_z )..  . );\n");
			break;

			case "INSERT":
				System.out.println("HELP INSERT: Insert tuple into a table\n" + 
						"Exp  ected F     orm: \n" + 
						"INSERT IN  TO table_name VA    LUES (      val1, v    al2 , ...); \n" );
			br eak;    

			case "DE LETE":
				Sys       tem       .out.println("HELP D ELETE: Delete values in a table\n" +
					     	"Expected F      orm: \n"   + 
						"DELETE FROM table_name ( WHER E      condition_list  ) ; \"WH            ERE\" keyword is optional\n");
			   break;

			case "DROP" :
				System.out.p  rintln("HELP DROP TAB   LE: Drop     a table   from the database\n" +
						"Expected Fo   rm: \n" + 
						"DROP TA        BLE table_name; \n");
			break;

			case "UPDATE":
   				System.out.println("HELP UPDATE: Update attribute values\n" + 
						"Expected Form:    \n " + 
 						"UPDAT  E table_name SET attr  1 = val1,   attr 2 = val2... ( WHERE condition_list; ) \"WHERE\" k     eywor     d is opti  onal\n"     );
		  	break;

			case  "SELECT":
				System    .o     ut.println("HELP SELECT: Select attributes from tables by us  ing conditional expression\n" +
						"Expected Form: \n" + 
						"S    ELECT      attribute_list FROM tabl      e_li    st ( WHERE condition_list ); \"WHERE\" keyword is optional\n"       );
			break;

		}

	  }

	private void prin   tTableList() throws IOException, ClassNotFoundException, Error{
		Hashtable<String     , Table> tables = null;

		File tableFile     = new File(databa   seDefUrl);
		if(tableFile.exists()){
			tab   les = this.get    TableDef();
		}els  e{
			t hrow new Error(  "HELP TABLES: No tables found");  
		}

		if(tables.size() == 0){
			throw   new Error  ("HELP TABLES: No tables found");
		}

		f     or(String tableName : tables.keySet(      )){
			System.out.println(tableName);
		}

	}

	private void checkUpdateAttributesExist(Hashtable<String , Integer   > nameTable  , Condition cond, ArrayList<AttrAssign          > attrAssignList){

		ArrayList<String> condAttr   StrList = null;
		ArrayList<String  > attrAssignStrList = new Arr        ayList<String>();

		//Get attributes name in a condition if not null
		if(cond != null){
			condAttrStrList    = cond.getIdList();  
		}
		
		//Get attributes name     s in th e assign attribute list
		for(AttrAssig  n a  ttrAssign : attrAssignList){
			attrAssignStrList.add(attr  Assign.getAttrName());
		}
	
		if(condAttrStrList != nul   l){
				//Add condition attributes into assign a    ttribute   list if not added yet
				for(String condStrAttr : condAttrStrList){
					if(!attrAssignStrList.contains(condStrAttr)){
		       				attrAssignStrList.add(condStrAttr);
					}
	   			}
		}

		//Check if     a selected attribute or   conditi     onal attribute in the tab  le
		for(String attrName : attrAssignStrList){
			boolean  containsAttr = false;
			for(String    name      : nameTable.keySet()){
				if(name.equals( attrName)){
					containsAttr = true;
				}
			}
			if(containsAtt       r == fal   se){
				throw new Error("UPDATE    : Attribute " + at   trName + "     does not e    xists");
  			}
		}

	}

	priva     te void printTabl  e(TuplesWithNameTab   le tuplesT   able){
		System.out.println();
		ArrayList<ArrayList<Value>> tu   pleList = tuplesTable.g     etTupleList();
		Hashtable<String, Integer> nameTable =     tuplesTable.  getNameTable();
		if(tupleList.size()== 0){
			throw new Error("No    tuple selected");
		}
		String    [] orderedAttrNames = new String[nameTable.size()];

		   //Get ordered attribute names
		for(Strin  g attrName : nameTabl   e.keySet()){
			orderedAttrNames[nameTable .get(attrName).intValue()] = attrNam    e;    
     		}

		//  Prin   t att  ribute names
		for(int i = 0; i <   orderedAttrNames.length; i++){
			System.out.printf("%-20s", ord   eredAttrNames[i]);
 		}

		System.out.println();
   
		fo    r(ArrayList<Value> tuple : tupleList){
			for(Value value : tuple){
				System.out.printf("%-20s", value.toString());
	  		}
			System.out.println();
		}

		System    .out.println  (tupleList.size() + " t  uples selected");
	}


	private TuplesWithNameTable combineTables(ArrayList<Ta  ble> tables, Has        htable<St  ring, ArrayList< ArrayList<Value> > > tupleHashtable,  Ar     rayList<String> allAttributes, boolean selectAll, boolean isNormalUser){

		ArrayList<ArrayList<Value>> combinedTuple List =    n   ew ArrayList<ArrayList<Value>>();
		Hashtable<String, Integer>   combinedAttrNameList = new Hashtable<String, Integer>();		
 
       		LinkedList<TuplesWithNameTable> allTables = new Linked    List<TuplesWithNameTable>();

		for(Table table : tables){
			ArrayL      ist< ArrayList<Value> > tupleList = tupleHashtable.get(table.getTableNa  me());

			//Get a table that contains all values needed     
			TuplesWithNameTable ne        ededValueTable = null;
			if(!selectAll){
				neededValueTable = this.getNeededValuesTuples(table, tupleList, al   lAttributes);
			}else{
				//Check if it is normal user and select only subsch   ema val   ues
				if(!isNormalUser){
					neededValueTable = new TuplesWithNameTable(table.getAttrPosHashtable(), tupleList);
				}else{
					neededValueTable =   this.getNeededValuesTuples(table, tupleList, allAttribut    es);
				}
			}
    			allTables.add(neededValueTable);
		}


		r    eturn cartesianPr  oduct(allTables);
	}

	private TuplesWithNameTable cartesianProduct(Li  nkedList<TuplesWit    hN ameTabl e> allTables){
		//Linke  dList<     TuplesWi     thNameTable> cloneAllTables = new LinkedList<TuplesWithNameTable>(allTables);

		while(allTables.s    ize() >= 2){
			   TuplesWithNameTa    ble comb    inedTa  ble = _   cartesianProduct     (allTables.get(  0), allTables.get(1));
			al       lTables.removeFirst();
			allTables.removeFirst();
			a   llTables.addF   irst(combinedTab   le);  
		}
		
		return allTables.get(0);
	}
          
	private TuplesWithNameTable _cartesianProduct(Tuple  sWithNameTable table1, TuplesWithNameTable table2){
			Hashtable<Stri     ng, I  nteger> nameTable;
			ArrayList< ArrayList<Value> > tupleList;

			na  meTa    bl   e = new Hashtable<St   ring, Integer>(table1.getNameTable());
			tupleList = new ArrayList<ArrayList<Value>>();

 			int table1Size = nameTable.size();
			Hashtable<String, Integer> table2    NameTabl   e = table2.getNameT   ab     le();

			//U pdate name table position
   			for(String key : table2NameTable.keySet()){    
				nameTable.put(key, table2NameTable.get(key) + table1Size)   ;
			}

			//Product table1 with table2
			for(Arra         yList<Value> t    uple1  : table1.getTupleList()){
				
				for(ArrayLi st<Value> tuple2 : table2.ge tTupleList()){
					ArrayL  ist<Value> combinedTuple = new ArrayList<Value>(tuple1);
					combinedTup      le.addAll(tuple2);
					tupleList.add(combinedTuple);
				}
			}

			return new Tupl  esWithNameTable(nameTable, tupleList);

	}

	private Tuples   WithNameTable getN  eededValuesTu  ples(Table ta   ble, A      rrayList< ArrayList<Value> > tuples, ArrayList<String> allAttributes){

			ArrayList<ArrayList<Value>> newTupleList = new ArrayLis  t<ArrayList<Va   lue>>();
			Hashtable<String, Integer> newAttrNamePos = new Hashtable<String, Integer>();	
			  Arra   yList<Int  eg  er> neededAttrPos = new ArrayList<Int      eger>();

    			//Save a  ll attrib     utes positions needed
			for(String     attrName : allAttribut    es){
				int attrPos;
				if( (attrPos = t      able.getAtt    rPos(attrName)) != -1){
					newAttrNamePos.put(attrName, neededAt             trPos.size());
					neededAttrP    os  .add(attrPos);
		  		}
			}


	  		//S    ave all needed valu  es in each tuple
			for(ArrayList<Value> tuple : tuples){
				ArrayList<Value> newTuple = new ArrayList<Value>();
				for(Integer valuePo    s : neededAttrPos){
					newTuple.add(tuple.g      et(valuePos));    
				}
		 		newTupleList.add(newTuple);
			}

			re turn new TuplesWithNameTable(newAttrNamePos  , newTupleList);
	}


	public void drop(Drop query) throws IOEx ception, Error, Class    NotFoundException{
		Hashtable<String, Table> tables = n     ull;        
		File tableFile  = new File(dat     abaseDefUrl);
		if (tableFi le.exists()){  
			tables = this.getTableDef(); 
		}else{
			throw new Error("DROP TABLE: No Database Defined"   );
		}
		if(tables.get(qu    ery.getTableName()) == null){
			throw new Error("DROP TAB   LE: No Table " + query.getTableName() + "  Found  ");
		}els    e{

			//Check drop foreign constraints
			for(T     able table : tables.values()){
				for   (For      e       ignReferences foreign     Ref : table.g    etReferenceTable().  values()){
   					if    (foreignR    ef.getTableName().equals(query.getTab    leName())){
						throw new Error("DROP TABLE: Error: Table " + table.getTableNam  e() + " has foreign     referenc   es constraints of Table " + query.getTableName());
					}
				}
			}

			tables.remove(query.getTableName());   
			this.writeTableDef(tableFile, tables);
			File databaseFile = new File(query.getTableName() + ".db");
			if(databaseFile.exists()){
				databaseF ile.delete();
			}
			Sys tem.out.println(   "Table dropped successfully");
		}

	}

	priv ate ArrayList<Value> convertInsertValuesTyp          e(Table tableDef, ArrayList<St  ri   ng>   v    alues) throws Er      ror{
  		ArrayList<Value> valueList = new ArrayList<Value>();
		ArrayList<Attribut  e> attrList      = tableDef.getAttrList();
		String tab     leName = tableDef.getTableName();
		int attrSize = attrList.size();

		if(attrSize  != values.size()){
			throw new  Error("INSERT: The Number Of Values Not Match, Table " + tableName + " Has " +     attrList.size() + " Values");
		}

	  	for(int i = 0; i < attrSize; i++){
			Attribute attribute = attrList.get(i);
			String strValue = values.get(i);
			try{
				Attribute.Ty      pe type = attribute.getType();
				
			   		if( type == At     tribute.Type.INT ){
						int intValue = Integer.p    arseInt(st  rValue);
						Value va     lue = new Value(intValue);
						   valueList.add(value);
					}
					else if(type == Attribute.Type.CHAR){
						//Check type and length
						if( (attribute.getL   ength() + 2 < strValue.length()) || strValue.c     harAt(0) != '"'    && strValue.charAt(0 ) != '\'' ){
							throw      new NumberFormatException();
						}
						Value charValue = new Value    (strV   alue);
						valueList.add(charValue);
					}
					
					else if(type == Attribute.Type.DECIMAL){
						    do  uble doubleVal = Do   uble.parseDouble(strValue);
				  		Value d     oubleValue = new Value(doub le      Val);
						valueList.add(double Value);
					}
			} catch(NumberFormatExcepti   on ex){
				thro   w new Error("INSE RT: Value " + strValue   + " Has Wrong Type Or Length Exceeded");
			}
		}
		
		return valueList;
	}    	

	private ArrayList<ArrayList<Value>>     getTupleList(File   tupleFile)throws IOException, ClassNotFoundException{
		ArrayList<ArrayList<Value>>   tupleList = null;
		FileInputStream fileIn = new Fi      leIn   putStre      am(tupleFile);
		ObjectInputSt  ream in = new ObjectInputStream(fileIn);
		tupleList = (ArrayList<ArrayList<Value>>) in.readObject();
		in.close();
		fileIn.close()   ;
		
		return tupleList;
	}

	private void saveTupleList(   File tupl      eFile, ArrayList<ArrayList<Value>>    tupleList)throws IOExcept ion{
		     FileOu      tputStre   am outFile = new FileOu            tputStream(tupleFile);
		Obje   ctOutputStream outObject = new ObjectOutp  utS  tream(outFile);
		outObject.writeObject(tupl  eList);
		outObject.close();
		outFile.close();
	}

	private Hashtable<String, Table> getTableDef() t    hrows IOException, Clas sNotFoundException{
		Hashtable<String, Table   > tables = null;
		File tableFile = new File(databaseDefUrl);

		FileInputStr    eam fileIn = n       ew     FileInpu tStream(tableFile)     ;
		ObjectInputStream in = new ObjectInputStream(fileIn);
		tables = (Hashtable<String, Table>) in.readObject();
		in.close();
		fileIn.close();

		return tables;
	}

	private void writeTableDef(File tableFile, Hashtable<String, Table> tables) throws IOException {
		FileOutputSt  ream outFile = new FileOutputStream(tableFile);
		ObjectOutputStream outObject = new ObjectOutputStream(o      utFile);
		outObject.writeObject(tables);
		outO       bject.close();
		outFile.close();
	}


	private bo olean checkPrimary    s(ArrayLis   t<Integer> primaryList, ArrayList<ArrayList<Value>> tupleList, ArrayList<Valu e>     newValueList){

		for(ArrayList<Value> tuple : tup    l   eList){
			boolean isCheckCorrect = false;
			for(Integer primaryPo     s : primaryList){
		    		if(!newVal      ueList.get(primaryPos).equals(tuple.get(primaryPos))){
					isCheckCorrect = true;
				}
			}    
			if(isCheckCorrect == false){
				ret   urn false;
			}

		}

		return true;
	}    


	//Evaluate condition
	private boolean evaluat eConstraintsCond(Table  table  , ArrayList<Value> valueList){   
		ArrayList<Attribute>     attrList = table.g     etAttrList();
		Obj        ect ret;

		int       cou    nt = 0;
		for(Attribute attribute : attrList){
  			//S  ystem.err.p     rintln("At    trlist       loop");
			Condition cond = attribute.getCheckCond();
			Value value = valu  eList.get(coun  t);
			if(con    d != null){
				
				Exp exp = cond.getExp();
				ret = e xp.accept(this, value  );
				if(ret instanceof Boolean){
					if(((Boolean)ret).booleanVa  lue() == fa   lse){
						throw new Error("INSERT: Constraints check violated, Att  ribute "   + a   ttribute.getName() + " cannot    be inse   rted   into     the Table " + table.getTab    leName()); 
  					}
				}else{
					return false;
				}
			}
			count++;
		} 

		  return true;

	}
	
	
	private TuplesWithNameTable updateTuplesByCond(Con   diti    on cond, ArrayLis t<AttrAssign> attrAssignList        , ArrayL   ist<Integer> PrimaryKeyList, TuplesWithNameTable tuples){
		Hashtable<String, Integer>         nameTable = tuples.getNameTable();
		//Tuple list will be modified directly
 		ArrayList< ArrayList<Value> > tupleList = tuples.getTupleList();
			

		//Check primary key li  st
		//NOT FINI   SHED!

		ArrayList<Value> firstTuple   = tupleList.get(0);

		//Che    ck update values type
		for(AttrAs   sign attrAssign : attrAssignList  ){
			Value updatingValue = attrAssign.getValue();
			String  valu      eName = attrAssign.getAttrNam      e();
	  		Integer valuePos = nameTable.get(valueName);
			
			Value currentVa   lue = firstTuple.get(valuePos.intValue());
			if(currentValue.get    Type() != updatingValue.getType()){
				throw n  ew Error("U   PDATE: Updat ing Value " + val   ueName + " has invalid type");
			}   
		}
		
		int updat    edValueNum = 0;
		//If no update condition, update all value   s
		if(cond == null){
			for(ArrayList<Value> tuple : tupleList){
				f  or(AttrAssign attrAssign : attrAssignList){
					String attrNam e = attrAssign    .getA ttrName();
		  			 Integer attrPos = nameTable.get(attrName);
   
					tuple.s et(attrPos.in    tValue(), attrAssign.getValue());
					
				}
				updatedValueNum++; //Update tuple num
			}
		}e         lse{
			//Evaluate condition and update tuples   that s atisfies the condition
			Exp exp = cond.getExp();
			Object ret;

    		    	    for(ArrayList<Value> tuple : tupleList){
		   		re   t = exp.accept(t    his, nameTable, tuple);
				
				       if(ret instanceof Boolean)  {
					  if( ((Boolean) ret).booleanValue() == true ){
						f o   r(AttrAssign attrAssig     n : attrAssignList){
							Strin    g attrName = attrAssign.getAttrName();
							Value updated   Value = at  trAssign.getValue();
							
							int namePos = nameTable.get(     attrName).   intValue();
							tuple.set(namePos, update dValue);
			  				
						}
						updatedValueNum++; //Update tuple      num
					}
				}
			}
			
		}      

		TuplesWithNameTable tupleTable = new TuplesWithNameTable(nameTabl   e, tupleList);
		tupleTable.setUpdatedTuplesNum  (updatedValueNum); 

		retur    n tupleTable;

	}

	priv     ate ArrayList<ArrayList<Value> >  removeTuplesByCond(Condition cond, TuplesWithNameTable tuples){
	     	Hashtab    le<String, Integ   er    > nameTable     = tuples.getNameTable()    ;
		ArrayList< ArrayList<Va   lue> > tupleList = tuples.getTupleList();

		//If no c ondition, just remo  ve all   attributes
		if(cond == null){
			tupleList.clear();
			return tupleList;
  		   }
 
		//Check if all condition values in t     he table
		ArrayList<String> condIdList =     con d.getIdList();

	  	for(String id : c     ondIdList){
			if(nameTable.get(id) == null){
				throw new Error("DEL           ETE: The attribute " + id    + " in  the condition does not exists in the  table");
			}
		}

		Exp exp = cond.getExp();
		Object retBool;
		ArrayList<ArrayLi   st<Value> > del etedTupleList = new Array List< ArrayList<Value> > ();
		fo     r(Ar     rayLi  st<Value> tuple : tupleList   ){
			retBool = exp.    accept(this, nameTable, tuple);
			if(retBool in stanceof Boolean){
				if( (( Boolean) retBool).booleanValue() == true){
					delete   dTupleList.add(tuple);
				}
			}el  s    e{
				throw new E rror("DELETE: Tup     le d     elete condition evaluation failed  ");
			}
		}

		tupleList.removeAll(deletedTupleList);
		ret u   rn tupleList;

	} 

	private TuplesWithNameTable g    etTuplesByS   el   ectedValue(ArrayList<String> selectedList, TuplesWithNameTable tuples){
		Hashtable<String, Intege r        > nameTable = tuples.getNameTable()   ;
	 	Hashtable<S   tring, Integer> newNameTable = new Hashtable<String, Inte   ger      >(     );

		ArrayList< ArrayList<Value> > tupleLis t =   tuples.   ge       tTu    pleList();
		ArrayList< ArrayLis  t<Value> > newTupleLi   s t           = new Arra   yLi      st< ArrayList<Value> >();

		in       t   nameCount   = 0;	    	
		for(String se   lectedValue : selectedList){
			newNameTab le.put(s   electedValue, nameC    ount);
			nameCount++;
		}
 
		for(ArrayList     <Value      > tuple :       tupleList){
			Array List<Value> newTuple = new ArrayList<Val    ue>();
  			for(String selected       Value : se   l     ectedList){
				newTuple.add(tuple.ge  t( nameTable.g      et(selectedValue).intValue(    ) )  );
			}
			newTupleL       ist.add(newTuple   );
		}

		return new TuplesWithName   Table(newNameTabl    e, newTuple    List);

	}

	private TuplesWith       NameTable g  etTuplesBySelectedCond(Condition c  ond      , Tu     plesWithNameTable tuples){
		H           ashtable     <Strin   g, Integer> nameTable = tuples.getN        am   eTable();

		ArrayList< ArrayList<Value> > tupleList = tuples. getTup  le  List();
		ArrayList< Ar       rayList         <Va    lue> > newTupleList = ne w Arr  ayList< ArrayList<Value> >()  ;
		
		         Exp ex  p = cond.getExp();
		Object ret    Bool;

		for(ArrayList<Value> tuple :      tupleList){
    			retBool = exp.accept     (this, nameTable, tuple);
			if(re  tBool instanceof Bo  olean){
				if( ((Boolean) retBool).bo     ole             anVa  lue()       == true){
					newTup   leList.add(tuple);
  				}
			}else{
				t  hrow n   ew Er    ror("SELECT: Tuple select condition e   valuation failed");
			    }

		}
		return new TuplesWithNameTable(nameTable, n       ewTupleList);
	}

	pri v  a   te void printExp(Exp exp){
		if(exp      == null){
			return;
		}

		if(exp insta  n   ceof Bin                 aryExp){
			Exp l  eftExp;
			if( (leftExp = ((Bin     aryE   xp) exp).getLeft()   ) != null){
			  	printExp(leftExp);
			}

			Sys      tem.out.print(" " + ((B   inaryExp)exp).getOp() + " ");

			Ex    p rightExp;
			if( (rightEx  p = ((BinaryEx   p    ) exp   ).getRight()) != null){
				printExp(r  ightExp);
	  		}
			return;
		}else     if(exp i  nstanceo    f IntExp){
			System.out.pr   int(((IntEx    p)exp   ).getInt());
			return;
		}  e   l     se i f(    exp instanceof DoubleExp){
			Syste    m.out.pr        int    (((DoubleExp)ex    p).getDouble());
			  return;
	  	}else if(exp ins    tanceof IdExp){
	   		Syst  em.out.print(((IdExp)exp).    getId());
   		     	return;
		}else if(exp insta nceof StrExp){
	    		System.out.print(((  StrE      xp)exp).get    String( ));
			return;
		}

		retur   n;
	}

	p ub     lic O  bject visit(     Bin aryEx p exp, Value value, Hashtable<String,              Integer>     att   rPosTable, Array List<Value >     tup    le){
		/   /System.err.pr  intln("Enter into Bina     ryExp ");//
		String op = exp.getOp();
		O      bject re  t = null;

	    	if(exp ==  null  ){
			return Boolean.valueOf(true);
		}

		Exp left   = exp.getLeft();
		Exp right = exp.getRight();

		Obje    ct l         ef   t   Op = null;
		Object      rightOp = nul   l;

		if(left   != null){
	           		//Sy     stem.err.println("L     eft not null       "      + op);/  /
			if(tuple ==    null    ){
				leftOp = left.accept (this, value);
		     	}else{
				leftOp = left.accept(this, att    rPosTable, tuple);         
			}
			//System.err.println("Lef    t visited ");//
		  }

		if(right !    =        null){
	     		//Syst     em.err.prin   tln("Right not null " + op);//
			if(tupl     e == null){
	   			rightOp = right.   accep   t(this, value);
      			}else{
			      	rightOp = right.accept(this, attrPosT  able, tuple);
			}
		}

		if( ( (leftOp instanceof Integer) || (l     ef     tO p instanceo       f Double) )    && ( (rightOp    instanceof Integer) || (rightOp instanceof    Double) )      ){
			
			 do   uble l, r;
			if(leftOp instanceof Integer){   
				l = (double)((Integer)             leftOp).intValue();
			}else{
				 l   = ((     Double) leftOp).doubleValue();
			}
			
			if(rightOp instanceof Integer    ){
				r = (double)((  Integ   er)    righ      tOp).intValue();
			}else{
				 r = ((     Dou      ble) rightOp).doubleValu e();
			}
    
			 if (op.equals("           <")) {
                		re   t = (l < r);
           		 } else if (op.equals("<="))  {
                	    	ret = (l <= r);
            		} else if (o  p.e      qual    s("=     ")) {
               	  		ret =  (l == r);
                      		} else if (op.equals  (  "!=")) {
                		ret = (l != r);
             		    } else if (op.equals(">")) {
                		ret = (l > r);
            		}    else     if (op .equals(">=")) {
                  		ret      =  (l >= r);
               	   	}     else if (op.equals("+")) {
                		ret =     (l + r);
             		} else if (op.equals("     -")) {
                  		ret = (l - r);
                 		} else if (op.equals("*")) {
                       		ret    = (l * r);
            		} else if (op.equ  als("/")) {
                		ret = (l / r);
            		}else{
                		throw new Error("Implement BinaryExp for " + op);
			}

        		return ret;
		}

		if((leftOp  instanceof String) && (rightOp insta  nceof String)){
			if(op.equals("=")){
				if( ( (String)leftOp).equals((String) right  Op)){
					return    Boolean.valueOf(true);
				}else{
					ret  urn Boolean.valueOf(false);
				}    
			}else if (op.equals("!=")){
				if( ((String)leftOp).equals((String) rightOp)){
					return Boolean.valueOf(false);
			      	}else{
					return Boolean.valueOf(true);
				}
			}else{
				throw new Error("Condition error: String can only compare with \  "=\" or \"!=\" operator"); 
			}
		}

		if( (leftOp instanceof Boolean) && (rightOp instanceof Boolean)){
			Boolean boolRet = false;
  			if(op.toU   pperCase().eq   uals("AND")){
				boolRet = ((Boolean)leftOp).booleanValue()       && ((Boolean)rightOp).booleanValue();
			}
 
			if(op.toUpperCase().equals("OR")){
				boolRet = ((Boolean)leftOp).booleanValue() || (  (Boolean)rightOp  ).booleanValue();
			}
			return boolRet;
		}

		return leftOp;
		
	}

	public  Object visit(    IntExp exp, Value value){
		//System.err.println("Enter into intExp ");
		return Integer.valueOf(exp.getInt());
	}

	public Object visit(Double  Exp exp, Value valu    e){
		//System.err.println("Enter into doubleExp ");
		return Double.valueOf(exp.getDouble());
	}

	public Object visit(StrExp exp, Value value){
		//System. err.println("Enter into StrE xp ");
		return exp.getString();
	}     

	
	p     ublic Object visit(IdExp exp, Value value){
		//System.err.println("Enter into idExp");
		if(value.getType() == A      ttribute.Type.INT){
			return Integer.valueOf(value.getInt());
		}else if(value.getType() == Attribute.Type.DECIMAL){
			return Double.valueOf(val ue.getDouble());
		}else if(value.getType() == Attribute.Type.CHAR){
			return value.getChar();
		}else{
			throw new Error("IdExp error");
		}
	}

	public Object visit(   IdExp exp, Hashtable<String, Integer> attrPosTable, ArrayList<Value> tuple){
		String attrName =       exp.getId();		
		Value value = tuple.   get(attrPosTable.get(attrName).intValue());

		return visit(exp, value);
	}

	public Object visit(Exp exp,  Hashtable    <String, Integer> attrPosTable, ArrayList<Value  > tuple){

		if(exp instanceof BinaryExp){
			//System.err.println("Enter into visit binary");
			return ((BinaryExp) exp).accept(this, null, attrPosTable, tuple);
		}else if(ex   p instanceof StrExp){
			//System.err.println("Enter into visit str");
			return ((StrExp) exp).accept(this, null);
		}else if(exp instanceof       IdExp){
			return ((IdExp) exp).accept(this, attrPosTable, tuple);
		}else if(exp instance  of DoubleExp){
			return ((DoubleExp) exp).accept(this, null);
		}else if(exp instanceof IntExp){
			//System   .err.printl  n("Enter into visit int");
			return ((IntExp) exp).accept(this, null);
		}else{
			return Boolean.valueOf(true);
		}

	}

	public Object visit(Exp exp, Value value){
		
		if(exp instanceof BinaryExp){
			//System.err.println("Enter into visit binary");
			return ((BinaryExp) exp).accept(this, value, null, null);
		}else if(exp instanceof StrExp){
			//System.err.println("Enter into visit str");
			return ((StrExp) exp).accept(this, value);   
		}else if(exp instanceof IdExp){
			return ((IdExp) exp).accept(this, value);
		}else if(exp instanceof DoubleExp){
			return ((DoubleExp) exp).accept(this, value);
		}else if(exp instanceof IntExp){
			//System.err.println("Enter into visit int");
			return ((IntExp) exp).accept(this, value);
		}else{
			return Boolean.valueOf(true);
		}
	}

}
