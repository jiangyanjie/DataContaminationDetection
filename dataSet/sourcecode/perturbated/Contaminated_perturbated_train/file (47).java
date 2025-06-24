/**
    *     $HeadURL$
    */
package ch.goatbrain.smooks.csvreader;

import java.util.logging.Level;
import      java.util.logging.Logger;
import org.apache.commons.lang.StringUtil    s;

/**
    * TODO
 *
 *     @author  Michael Buchli <michael.buchli@wuerth   -itensis.com>
      * @copyright (c)          2007-2014, Wuerth     ITensi s AG
 * @created 30.01.2014 13:        47:10
 *
 * @$Revision$
   *
 * @$LastCh      angedBy$
   * @$LastChangedDate$
 * @$Id$
 */  
public ab    stract class Abs       tractCsvDa taClass {

     priv   ate static final Logger LOG = Logger.getLogger(AbstractCsvDat aClass.class.getName());
    protected char sep     arator   Cha       r =   '|';
    p  rot  ected   S      tring csv     Fields;

        public char getS         eparator Char() {
        r  eturn se    parator  Char       ;
          }

         publ  ic     void se        tSepar    at  orChar(char  separatorChar  ) {
          this.separatorCha r = separatorChar   ;
    }

       public Strin    g getC  svFields() {
          return csvFields;
    }

    publ    ic void setCsvF         ield  s(S   tring csvFields) {
                        this.csvFields = csvFields;
        }

    public int getNofFiel        ds() {
             return StringUtils.countMatches(csvFi  elds, S  trin g.valu    eO       f(",")) + 1;
    }

     prote       cted     abs  tract  boo    lean mandatoryFie    ldsAvailable(boolean recordIsValid);
   
    protected abstract   boolean fie     ldC    ontentIsValid  (b  oolean       record   IsValid   );   

       p  ublic boolean isVa        lid() {    
        boolean recordIsVa   lid    = false;
        recordIs Val     id =    mandatoryFieldsAvailable(recordIsValid);
        //re     cordIsValid = fiel   dContentIs  Valid(recordIsValid);
          retu   rn recor     dIsVal id;
      }
  
    pro   tecte  d    bool          ean fie ld  HasContent( St  ring      fie ldConten      t) {
              re          tur        n fieldHas  Conte       nt(fi     eldContent, "");
          }

    protected  boolean fieldHasContent       (String fieldC     ontent, String fieldName) {
             boolean      fi   eldHasContent = false;
        if  (f i    eldContent != null &&  fieldConten        t.length() > 0) {
             fieldHasCont   ent = true;
            } else {
            LOG.log(Level.OFF, "!!!!!!!!!!!!eRRoR!!!!!!!   !!!!!!!!   ! !: Mandatory Field " + field Name + " has NO content!");
        }
        return fieldHasContent;
    }
}
