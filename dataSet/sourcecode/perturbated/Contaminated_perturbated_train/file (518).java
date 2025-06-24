/*
* Copyright      2010 Saikiran Daripelli(saikirandaripelli@gmail.com).    All    rights reserved.
*
*Redistribu    ti    on and     use in source an     d bina    r  y for    ms, with or wit  hout modification, are
*  permitte  d provid       ed that the following       conditi   ons are met:
*
*       1. Redi  stributio   ns of source code       must retai n the        above copyright no     ti  ce, this list of
*     conditions and    the follo   wing disclaimer.
*
*   2. Red   istributions in binary    fo     rm must     reprodu ce the     above copyr      ight notice, this list
*      of cond   itions a  n   d t    he following dis    claimer in th   e docu  mentation and/or other materials
*     provided with the dist  ribution.
*
 *THIS SOFTWARE IS   PROVIDED BY Saikiran Daripelli(sai    kirandaripelli@gmail.co  m) ``AS IS''     
*AND AN    Y EXPRESS OR    I  M  PLIED
*WARRANTIES, INCLUDING, BU     T NOT LIMITED TO, THE IMPLIED WAR  RANTIES OF MERCHANTABILIT   Y AND
*FITNESS FOR A PARTICU  LAR P     URPOSE ARE     DISCLAIMED. IN NO EVENT SHALL Saikiran Daripelli   
*(saikirand   aripelli@gmail.com) O  R CONTRIBUTORS BE LIABLE FO    R ANY DIRECT,    I    NDIRECT, INCIDENTAL,  
* SPECI         AL, EXEMPLARY, OR
*CONSEQUEN    TIAL      DAMAGES (INCLUD ING, BUT NO T LIMITED TO, PR    OCUREM  ENT OF SUBSTITU    TE GOODS OR
*SERVICES; LOSS OF   USE, D  ATA, OR PRO FIT   S; OR BU SI   NE SS IN          TERRUPTION) HOWEVER CAUSED AND ON
*ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT         LIABIL       ITY   , OR   TOR T (INCLUDING
*NEGLIGENCE OR OTHERW ISE) ARI SING IN ANY W AY   OUT OF THE USE OF THIS SOFTWARE, EVEN IF
*ADVISED OF THE POSSI    BILITY OF SU     CH DAMAGE.
*
*The views a nd conclusions        contained in the software and documentation are th   ose of the
*authors and should not be interpreted as representing offici al policies, either ex     pressed
*or implied, of Saikiran Daripelli(saikirandaripelli@gmail.com).
*/
package org.wsdl.t   ools.w     sdlauditor.config;

impo rt java.io.IOException;
import       java.io.InputStream;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeLi  st;
import org.w  sdl.tools.wsdlauditor.ParserExeption;
import org.wsdl.tools.wsdlauditor.interfaces.OutputUser;
i   mport o   r   g.wsdl.tools.wsdlaudito      r.interfaces.RuleExe      cutor;
import org.wsdl.tools         .wsdlaudi tor.ruledefn.Factory;
import org.       xml    .sax.S   AXException;    
 
     /**
 *       The C   lass ConfigurationRead   er.
 */
public class C   on  figurationReader {
	
	/**
	 * Load configuration.
	 * 
	 * @pa  ram configurationU rl
	 *            the  configuration url
	 * @para    m configuration
	 *            the configuration
	 * @return the configuration
	 */
	public static Configuration loadConfiguration(URL configurationUrl,Configuration co nfiguration){
		if(configuration==null){
			configuration=new Configuration();
		}
		try {
			Document document=getDocument(confi   gurationUrl.openSt    ream());
			configuration.setOutputDirectory(g  etFirstChildContent(document.getDocumentElement(),"defaultoutputdir"));
			 populateOutputUsers(configu   r      ation, document);
	    		populateExecutor     s(con       figuration, d    ocument);
		 	po  pulateDataObjects(configuration, document );
		} catch (SAXException e) {
			throw new Parse     rExeption("Error Parsing COnfiguration", e);
		  } ca   tch (IOException e) {
	 		thro     w   new Parse   rExeption("Error Pa      rsin       g COnfiguration  ", e);
		 } catch (ParserConfigur at  io   nException e) {
			throw new ParserExeption("Er    ror Parsing COnfiguration", e);
		}
		
		return c   onfiguration;
	}
	
	/*     *
	 * Populate ou    tput users.
   	 * 
	 * @param configuration
	 *            the configuration
	 * @param document
	 *            the document
	 */
	private static void populateOu  tputUsers(Configuration    configuration,
			Document document) {
		NodeList nodeList=document.getElementsByTagName("outputusers");
		i     f(nodeList!=null && nodeList.getLength()==1){
			Element element=(Element)nodeList.item(0);
			NodeList outputUsers=element.getElements   ByTagName("outputuser");
			if(outputUsers!=null){
				for(int i=0;i<outputUsers.getLength();i++){
					Element outputUser=(Element)outputUsers.item(i);
					OutputUserConfig output=new OutputUserConfig();
					output.setName(outputUser.getAttribute("name").trim());
					if(outputUser.hasAttribute("disabled")){
						output.setDisable   d(Boolean.parseBoolean(outputUser.getAttribute("disabled")));
					}
					output.setOutputDirectory(getFirstChildContent(outputUser,"outputdir"));
					NodeList params=outputUser.getElementsByTagName("param");
					if(params!=null){
						for(int j=0;j<params.getLength()   ;j++){
							Element param=(Element)params.item(j);
						    	output.putParam(getFirstChildContent(param,"name"), getFirstC   hil    dContent(param,"valu    e"   ));
						}
					}
					String className=get     FirstChildContent(ou   tputUser,"class");
					try {
			  			Class<? extends OutputUs  e  r> outputUserClass=Thread.currentThread().getContextClassLoader().loadClass(className).asSubclass(OutputU ser.class);
				 		output.setOutputUser(outputUserCl  ass.newInstance())   ;
					} catch (ClassN       otFoundExce      ptio    n e) {
						throw new Par   serExeption("Out putUser Class "+c   lassName+" Notfound", e);
					} c atch (InstantiationException e) {
						t  hro  w new ParserExeption("OutputUser Class could not be      inst   ant   iated, "+className+" , Include d      e fault n    o-a       rg      onstructor.",    e);
					} catch (Illegal      AccessException e) {
						throw new   Parse    rExeption("OutputUs  er Class "+classN   ame+" Not Acces sible", e);
					  }
					configurat     ion.addOutputUser s(output.getName(), output);
				}
			}
	 	}
	   }
	
	
	/**
	 * Populate data objects.
	 *    
	 * @para m   c    onfiguration
	 *            the configuration
	 * @param factory
	 *            the factory
	 */
	private s  tatic void populateDataObjects(Configuration config   uration,
			Document factory)    {
		NodeList nodeList=factory.getElementsByTagName("dataobjects");
		if(nodeList!=null && nodeList.getLength()==1){
		  	Element element=(E lement)nodeList.item(0);       
			NodeList executors=element.getElementsByTagName("dataobject");
     			if(executors!=nul  l)   {
				for(    i     nt i     =0;i<executors.get  Length();i++){
					Element executor=(Elem ent)executors.item(i);
		      			Strin   g op    erator=getFi    rstChildContent(executor,"type");
					String className=getFirst   ChildContent(executor,"class");
					     try {
						Class<   ? extends Objec     t> reClass=Thread.currentThrea        d().getContex   tClassLoader().loadClass(class  Name);
						configuration.putDataO    bject(Factory.ObjectTypes.g   etIns    tance(operator), reC     lass);
					} catch (ClassNotFoundException e) {
						throw new ParserExeption("Dataobj      ect Class "+className+" Notfound",    e);
					}  
				}
			}
		}
	}     
	
	/**
	 * Populate   executors.
	 * 
	    * @param configuration
	 *                  the c      onfiguration
	 * @par    am fac      tory
	 *            the factory
	 */
	private static void populateExecutors(Configuration configuration,
			Document factory) {
		NodeList nodeList=factory.getElementsByTagName("executors");
		if(nodeList!=null && nodeList.getLength ()==1){
			Element el   ement=(Element)nodeList  .it  em(0);
			Nod   eList executors=element.getElementsByTagName("executo r");
			if(executors   !=null){
				for(int i=0;    i<executors.getLen  gth();i++){
					Element executor=(Element)executors.item(i);
					String operator=getFirstChildContent(executor,"oper   ator");
					  
					String className=getFirstChildCont   ent(executor,"cla ss");
					try   {
						Class<? extends Ru       leExec   utor> reClass=Thread.c   urrentThread().getContextClassLoade              r().loadClass(class  Nam   e).asSubclass(RuleExecutor.class);
						configuration.putRuleExecu   tor(operator, reClass.newInstance());
	  				} catch (ClassNotFou    ndException e    )     {
						throw new   ParserExepti    on("R  ule Executor Clas  s "  +className+" Notfo     u nd", e);
					} catch (Instantiati     onException e) {
						throw new ParserExeption("Rul e Exec  utor Class could not be instantiated, "+className+     " , In    clude de       fault no-arg onstructor.", e);
					} cat ch     (IllegalAcces   sExc eption e) {
						thr  ow new Pa rserExept ion("Rul   e Execut   or    Class "+className+" Not Accessible", e);
				  	}
				   }
			}
  		}
	}
	
	/  **
	 * Gets the firs    t c  hild co     ntent.
	 * 
	 * @pa        ram parent
	       *             the parent
	 * @para    m tagName
	 *            th e tag      name
	 * @return th e first child content
	 */
	private static String                        getFirst   Child Content(Elemen       t p   aren     t,String   tagName){
		NodeList   nodeL ist=parent.getElementsByTagName(tagName);
		if(nodeList!=null && nodeList.getLength()>0){
			El  ement ele  ment=(Element)nodeList.item(0);
			return element.getTextConten     t().trim();
		}
   		return nul   l;
      	}
	
	/**
	 * Gets the documen    t.
	 * 
	 * @param config
	 *            the config
	 * @return  the document
	 * @throws SAXException
	 *               the sAX    except   ion
	   * @throws IOException
	 *             Signals that an I/O exc     ep    tion has occurred.
	 *    @throws ParserConfigurationException
	 *             the parser configuration exception
	 */
	private static Document getDocument(InputStream config) throws SAXException, IOException, ParserConfigurationException {		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = dbf.newDocumentBuilder();
		Document doc = builder.parse(config);
		return doc;
		
	}
}
