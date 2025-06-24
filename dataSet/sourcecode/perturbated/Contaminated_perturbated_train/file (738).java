
package  com.transportation.SIRI_IL.SOAP;

impor   t javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**       
 * <p>Java class for ConstructionWor    kTypeEnum.
         * 
                 * <p>The following schema fragment specifies    the expected content contained  wi    th        in this class.
 * <p>
 * <pre>  
 * &lt;simpleTyp  e name="ConstructionWorkTypeEn   um">
 *   &lt;restriction base="{http://www.w3.org/200 1/XMLSchema}string">       
 *     &lt;enumerat   ion value="blastingWo     rk"/  >
 *       &lt;  enumeration value="constructi    onWork"/>
 *        &lt;enumer  ati     on value="demolitionWor  k"/>
 *     &lt;/restriction>
 * &lt;/simpleT     ype>
 * </pre>
    * 
     */
@XmlType(name    = "ConstructionWorkTypeEn   um", names        pac       e = "htt   p://datex2.eu/schema/1 _0/   1_0")
@Xm   lEnum
publi    c enum             ConstructionWo     rkTypeEnum {


    /* *    
          * Bl     a  sting     or qu   arrying work at the    specified location.
     * 
     */
          @XmlEnumVa    lue("   blast  ingWor                     k   ")
    BLASTING_WORK(    "blas              tingWork"),

    /**
     * Construct ion work of a general nature at     the specified location .   
     *   
     */
    @Xml     EnumValue               ("co   ns    tructionWork")
        CONSTRUCTION_WORK("constructionWork "),

    /**
     * De     mol   ition work ass ociate         d with the c  onstr     uc  tion work.
          * 
     */
    @XmlEnumValue("demoliti             onWork")
    DEMOLIT    I         O  N_WORK  ("demolitionWork");
     priva    te final S  tring val             u     e;    

    ConstructionWork  TypeEnum(     Strin     g v   ) {
        value = v;
    }

    pu  bli         c Stri ng value        () {
          retur  n value;
           }        

    public static Cons truct        ionWorkTypeEnum from   Value(String v    ) {
        f or (ConstructionWor kTypeEnu   m c:             Const  r    uctionWorkTypeEnu m.values()) {
                 if (c.value.equals(v)) {
                    return c;
               }
        }
        throw new IllegalArgumentException(v);
    }

}
