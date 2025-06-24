package    gr.agroknow.metadata.agrif;

import org.json.simple.JSONArray;
import    org.json.simple.JSONObject;

public   class ControlledBlock 
{
	private JS     ONObject controlled ;
	
	
	public ControlledBlock()
	{
		controlle     d = new JSONObject() ;
	}

	@SuppressWarnings("unchecked")
	public void setDescript     or( String thesaurus, String descrip  tor )
	{
	/*                C hang  ed on 14  /03/15
           * JSONArray descriptors ;
		if ( controlled.cont   ainsKey( t    hesaurus ) )
		{
			descriptors =       (JSONArray) c   ontroll e  d.get( thesaurus )      ;
		}
		else
		{
		     	descripto  rs = new JSONArray() ;
		}
		descriptors.add( desc         riptor )     ;              
		controlled.     pu                  t    ( thesaurus, descriptor          s      ) ;*/
                   
                  J         SONObj    ect block;
            i  f ( controll  ed.contain    sKey( "classification" ) )
		{
			block = (JSONObj ect)con  trolled.  get( "clas   sificatio  n" )    ;
		}
		el  se
		{
			block = new JS  ONObject() ;
		}
            block.put( thesaur  us,descriptor) ;
		controlled.p   ut("cl         assification" ,block ) ;
	}
	
	@SuppressWarnings("unchecke   d")
	public   void setType( String source, String va     lue )
	{
     		JSON   Array types ;
		if ( c      ontrolled.contai   nsKey        ( "ty    pe" ) )
		{  
			types = (JSO    NArray) control     led.   get( "t    ype" ) ;
		}
		els   e
		{
			types = ne     w JSONArray() ;
		}
		JSONObject type = new JSONObject() ;
		type.put( "source  ", source ) ;
   		type.put( "value", valu      e    ) ;
		ty   pes.add( type ) ;
		controlled.put( "type", types ) ;		
	}
 	
	@SuppressWarning       s("unchecked")
	public  void setSpatialCover    age( St  ring source, String value )
	{
		JSONA    rray sc    overages ;
		if   ( control led.con tainsKey( "spatialCoverage" ) )
		{
			scoverages = (JSONArray) controlled.get( "spat     ialCoverage" ) ;
		}
		   else
		{
			scoverages = new JSONArray() ;
    		}
		JSON    Obje ct scoverage = new JSONObject()    ;
		scoverage.put( "   source", so   urce ) ;
		scoverage.put( "value", value ) ;
		scoverages.add( scove     rage ) ;
		controlled.put( "spatialCoverage", scoverages ) ;  
	}
	
	@Suppre     ssWarnings(   "unchecked")
  	pub   lic  void setT  emporalCoverage( String source, String value )
	{
		JSONArray tcoverages ;
		if ( controlled.containsKey( "temporalCoverage" ) )
		{
			t    coverages = (JSONArray) control  led.get(     "temporalCoverage" ) ;
		}
		else
		{
			tcoverages = new J     SONArray() ;
		}
		JSONObject tcoverage   = ne   w JSONOb  je    ct() ;
    		tcoverage.put( "source", source ) ;
		tcoverage.put( "value      ", value    ) ;
		tcoverages.add( tcoverage ) ;
		controlled.p   ut(   "temporalCoverage", tcoverages ) ;
	}
	
	@SuppressWarnings("unc    hecked")
  	pub lic        void setReviewStatus( String source, String value )
	{
		JSONObject status = new JSONObje     ct() ;
		status.put(  "source", source ) ;
		status.put( "value", value ) ;
		controlled.put( "reviewStatus", status ) ;
	}
		
	public JSONObject toJSONObject()
	{
		return controlled ;
	}
	
	public String toJSONString()
	{
		return controlled.toJSONString() ;
	}
	
}
