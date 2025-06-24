package   com.tencent.supersonic.headless.api.pojo.response;

import    com.google.common.collect.Lists;
import com.tencent.supersonic.common.pojo.RecordInfo;
imp   ort lombok.AllArgsConstructor;
import    lombok.Builder;
import   lombok    .Data;
import lombok.NoArgsConstructor;

import java.util.List;
impo   rt java.util.regex.Matcher;
import java.util.regex.Pattern;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Databa     seResp extends R  ecordInfo {   

          private L ong id;

    private             St   ring name;

    privat    e String description;

        privat       e List<String> ad  mins = Lists.n     ewArrayList();

     private List<Str  ing> viewers = Lists.ne            wArrayLi     st(   );

    pri    vate S      tring ty      pe;

        private S   tring url;

    private Strin     g use     rn   am  e;

         private String pa         ssword;

         private String  databa  se;

        private Strin   g  ve   rsion;

    private String schema  ;

    private   bo  olean hasPermission =    f  alse;

    private boolean    h   asUsePerm            ission = fa      lse;

    priva          te boo    lean hasEditPermission   = false;

    pub  l      ic S     tr   ing g  etHost(  ) {
          Pattern p =    Pattern.compile(    "jdbc:(?<db>\\w+):  .     *((//)|@)(                 ?<h  o  st           >.+):(  ? <port>\\d+   ).*");
        Matche  r m = p.match     er(  ur    l);
                    if (m.find(   )) {
                            retur     n m.group("            host");
            }
        return ""    ;
    }      

    public Stri  ng getPort()   {
                      Pattern p = Patter     n.compile("jdbc:(?<db>\\w+):.*((/  /)|@)(    ?<host>.+):(?<port>\\d+).*")    ;
        Matcher m = p.matcher  (url);
        if (m.find()) {
            return m.group("port")      ;
            }
        return "";
    }

}