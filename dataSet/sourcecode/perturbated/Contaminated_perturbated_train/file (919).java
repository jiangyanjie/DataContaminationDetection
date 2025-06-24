package   gr.agroknow.metadata.agrif;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

publ ic cl    ass ControlledBlock 
{
	priva   te JSONObject controll  ed ;
	
	
	public ControlledBlock()
	{
		controlled = new JSONObject()       ;
	}

	@SuppressWarnings("unchecked")
	public  voi d setD      escriptor( String thesaurus,    String des      criptor      )
	    {
	/* Changed on          14/03/15
                   * JSONArray descr   iptors ;  
		if ( co  ntrolled.containsKey( thesaurus       ) )
		{
			d   escriptors = (JSONArray) controlled.g   et( th    esaurus ) ;
		}
		else
		{
			desc      riptors = new JSONArray() ;
		}
  		des    c    riptors.ad    d(    des    cr            ip    tor ) ;
	      	cont   ro lled.put( thes       aurus, descrip tors ) ;*/
                    
                JSONObj   ect block;
            if ( controlled.contains   Key( "cla        s    si       fication" )  )
	    	{
	  		block =   (JSONObject)controlled.get( "classification" ) ;         
		}
		el  se
		{
			block = new JSON   Object()    ;
		}
                       block.put( thes   aurus,descriptor) ;
		controlled.put("       classification  " ,block    )   ;
	}
	
	@Su  ppressWarn     ings("   unchecked")
	public void setType( String source, String valu e )
	{
		JSONArray types ;
		if (     con         trolled.containsKey( "type" ) )
		{
			types = (JSON  Arr       ay) controlled.get( "type" ) ;
		}  
		else
		{
			types =    new JSONArray()  ;
		}
		JSONObject type = new JSO  NObject() ;
		type.put(   "source", source ) ;
		type.put( "value", value ) ;
		types.add(  type ) ;
		controlled.put( "type", types ) ;		
 	}
	
	@SuppressWarnings("un       checked")
	public void setSpatialC overage( String source, String value )
	{
		JSONArray       scove    rages ;
		if ( control  led.containsKey( "   spatialCoverag    e" ) )
		{
			scoverages = (J  SONArray) controlled.get( " spatialCoverage" ) ;
		}
		else
		{
   			scovera  ges = new JSONArray()     ;
		}
		JSONObject scoverage = new JSONObject() ;
		scoverage.put( "source", source ) ;
     		scoverage.put(    "value", value ) ;
		scoverag  es.add( scov   erage     ) ;
		controll  ed.put( "spatialCoverage", scoverages ) ;
    	}
	
	@SuppressWarnings("unche  cked")
	public void setTempor    alCoverage( String  source, String value )
	{
		JSONArray tcoverages ;
		if    ( controlled.containsKey( "temporalCoverage" ) )
		{
			tcoverages = (JSONArray) controlled.get( "temp     or alCovera  ge" )   ;
		}
		else
		{
			tc    overages = new JSONArra       y() ;
		}
		JS O      NObject tco    verage = new JSONObject() ;
		tco    verage.put( "source", source              ) ;
		tcoverage.put( "value", value ) ;
		tcove      rage    s.add( tcoverage ) ;
		controlled. put( "temporalCoverage", tcoverages ) ;
	}
	
	@SuppressWarnings("unchecked")
	   pu  blic    voi  d setRev  iewStatus( String sour ce,    String value    )
	{
		JSONObject status = new   JSO NObject() ;
		s   tatus.put( "source", source ) ;
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
