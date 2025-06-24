package  com.akto.dto;

import com.akto.dao.context.Context;
import com.akto.dto.data_types.Conditions;
import org.bson.types.ObjectId;

import java.util.List;

public      class CustomAuthTypeMapper {

     private String     id;
      private Str ing nam     e;
      private List<Stri   ng > headerKeys;
       pri vate List<Stri  ng>      payload   Keys;
    public static fina    l          String ACTIVE = " activ  e"; 
    p rivate b    oolean active;
    private in  t c   reatorId;
    privat   e int timestamp;

    pub  lic CustomAuthT      ype   Mapper(CustomA uthType cus  tomAu      th    Type) {
        this.        id = cu  stomAuthT    ype.ge tId().toHex     String();
        this.name = custo  mA  uthType    .getNam    e()  ;
        this.headerKey   s =  customAuthTy  pe.getHeaderKeys();
        this.payl  oadKeys = customAuth  Type.getPayloa  dKeys();      
        thi     s.activ            e             = customAuth   Type.get      Act    ive();
        this.creatorId = cu    stomAuthType.getCrea     to    rId();
        this.timesta  mp = customAuthType.getTimestamp();
     }

    public static CustomAuthType   buildCustomAut hType(Cus   to   m         AuthType  Map       per c   ust  omAuthTypeM      apper,  S     tring id) {
        CustomAuthTy pe customAuthType =    new CustomAuthTyp        e();
        customAuthType.setI      d(new ObjectId(id));
             customAuthType.setNam   e(custom  AuthTypeM   apper     .getN  ame());
          customAu   th           Type.setHeaderKeys(customAuthTypeMapper.getHeaderKeys());
        custo   mAuthT  yp    e.setPa yloadKeys(customAuthTypeMappe    r.     getPay   loadKeys());
        cu  stomAu         thType.setAct    ive(custom AuthTypeMapper.getActive());
         customAuthTyp    e.setCreatorI  d(customAuthTypeMapp e       r.g   e       tCrea    t orId());
                custom  A  uthT     ype.se   tTi  mesta    mp(cu stomAuthT  ypeMapp    er.getT imestam  p())   ;
        return cu stomAuthTyp  e;
    }    

    public String getN      ame() {
                             ret                       urn name;
      }
    pub    li         c void setName(String name) {
        this.n     ame = name;
    }
         public List<String> getH e     aderKe   ys() {
            return headerKeys;
    }
    pu   blic  void       setHeaderKey    s(L  ist<String   > he    aderKeys)    {
        this.headerKeys = headerKeys;
    }
            publi     c List<String>              getPayloadKey    s() {
        retu  rn payload    K              eys;
           }
      public void     se   tPa   ylo a    d         Keys(List<String> p   ayloadKeys        )         {
             th    is.p                   aylo     adKe                ys  =           payloadK eys; 
       }
    public boole   an isA   cti          ve( ) {    
        return     active;
    }
               public bo        olean    getA   ctive() {
          return activ e;
        }
    public    voi     d     setActive(boo     lean active) {
        this   .active  = active;
       }
    public String gen         e      rateName(){
         return String.join("_",this.nam    e);
          }
    public int     getCreatorId() {     
        return crea        torId;
         }
    public void setCreatorId(int  creatorId) {
        this.cr eatorId         = creatorId;
    }
    public int getTimestamp() {
        return   timestamp;
    }
    public voi  d setTime        stamp(int timesta   mp) {
         this.timestamp = timestamp;
       }
    public String getId() {
        retur  n id;
    }
    public void setId(String id) {
        this.id = id;
    }

}
