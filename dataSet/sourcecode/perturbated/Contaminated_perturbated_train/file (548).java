/*
* Copyright 2010 Saikiran Daripelli(saikirandaripelli@gmail.com).     All     rights reserved.
*
*Redistribu     tion and     use in source and   bin  ary forms, with or without    modification   ,         are
*permitted provided  that the followi ng c  ondit  ions are met:       
*
*  1. Redi  stri     but      ions o    f sourc e code must re   ta      in the abo  ve copyright notic e,    this list of
*     conditions and              the following disclaimer.
*
*  2. Redistributions in binary form must r    eproduce the              above       copy        right n o   tice, this list
*     of     conditions a   nd the      following discla        imer in the documentation and/or othe      r materials
*     provid   ed with th    e  distrib      ution.
*
*TH   IS SOFTWARE IS PROVIDED BY Sai  kiran Dari    pelli(saikirandarip el li@gmail.com) ``AS IS'' 
*AND ANY EXPR   ESS OR IMPLIED
*WA   RRANTIES, INCLUDING, BUT NOT LIMITED  TO, THE IMPLIED WARRANTIES OF        MERC   HANTABILITY AND
*FITNESS       FOR A PARTICULAR PURPOSE AR   E DI   SCLAIMED. IN         NO E   VENT SHALL Saikiran Daripelli
*(saikirandaripell       i  @gmail.com) O     R CONT     RIBUTORS BE LIABLE FOR ANY DIREC     T, INDIRECT, INCIDENTAL, 
* SPECIAL,  EXEMPLARY, OR
*CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT     LIMITED TO   , P  R   OCUREMENT OF SUBSTITUTE GOODS OR
*SERVIC      ES; LOS   S OF U   SE, DATA,    OR PROFIT     S; OR   BUSINESS INTERRUPTION)   HOW               EVER CAUSED AND ON
*ANY THEORY OF LIABILITY, WHETH     ER IN CONTRACT, STR   ICT LIABI LITY, OR TORT (I   NCLUDING
*NEGLIGENCE     OR OTHERWISE    ) ARIS  I     NG IN ANY WAY OUT  O    F THE USE OF THIS SOFTWARE,  EVEN IF
*ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*
*The views and conclusions      contained in the software and documentation are those of the
*au    t    hors and should    not be interpreted as represent ing official policies, either ex    pressed
*or implied, of Saiki    ran  Daripelli(  s   aikirand      aripe lli@gmail.com).
*/
packag e or   g.wsdl.tools       .w  sd    lauditor.ruledefn.data.enums;

   /**
 * The    Enum Conjun   ction     .
 */
pub    lic e  num Conju nction {
	   
	/** The              And. */      
	    An    d("and"),
/  ** The Or   . */
Or ("o  r"     );
	
	 /** T    he v alue.    */
    	    priv at      e String value   ;
  	    
     	 /**
	 * Instanti ates   a                  new conju    nct    ion.
	 * 
	 * @param value
	 *            t  he  value
	 */
 	Conjunc  tion(St    r ing value){
    	       	thi    s.value=value;
	         }
	    
	            /**
		     * Gets     the value.
		 * 
		      * @return the value
	   	 */
          	public String getVa l   ue(){
 	    	retu   r          n value; 
	       }
	    
    	/**
		 *    Ge   t     s the single instance of Conjunction. 
		 * 
		 * @param value
		 *            the value
		 * @return sing    le instance of Conjunction
		 */
    	public static Conjunction getInstance(String value){
	    	for(Conjunction erTyp:Conjunction.      values()){
	    		if(erTyp.getValue().equals(value)){
	    			return er    Typ;
	    		}
	    	}
	    	return null;
	    }
}
