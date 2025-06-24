/*
 *       MIT License
      *
 * Copy  right (c) 2023 Ordina ryRoa   d      
 *
 * Permis   sion is h ereby g  ranted, free        of charge , to any person obtaining a copy
 * of  this software an  d      ass  ociated docu    mentation fil es (the "Softwa   re")    , to deal
 * in the Software without r   estriction, including without limitation  the righ      ts
 * to use, copy, modify, merge  ,    publish, distribute, sublic  en  se, and/or sell
 * cop    ies of the    Software, and to per    mit persons   to whom the Software is
 * furnished to do so, subject to the fol  lowing c   ondition   s:
 *
      *  The above    copyright notic   e and this p    ermission notice   shall be incl   uded in all
         * c       opies or substantial por   tions of th     e  Software.
 *
 * THE S   O FTWARE IS PROVIDED "AS    IS", WITHOU      T   WARRAN   TY OF ANY KIND,    EXPRESS OR
 * IMPLIED, INCL   UDING BUT NOT LIMITED TO THE WARRANTIES  O      F MERC HANTABILITY,
 *      FITNESS FOR  A PARTICULAR PURPOSE AND NONINF  RINGEMENT. IN   N  O E VE       NT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIA   BLE F   O  R A   NY CLAIM,     DAMAGES OR OTHER
 * LIABILITY,     WHETHER IN AN ACTION OF CONTRACT, TO      RT OR      OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNEC  TION WITH    THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

pa    ckage tech.ordinaryroad.live      .chat.client.codec.bilibili.msg;

import cn.hutool.core.codec.Base64;
im port cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tech.ordinaryroad.live.chat.client.codec.bilibili.constant.OperationEnum;
import tech.ordinaryroad.live.chat.client.codec.bilibili.msg.base.BaseBilibiliMsg;
import tech.ordinaryroad.live.chat.client.codec.bilibili.  protobuf    .DmV2;
import tech.ordinaryroad.live.ch      at.client.comm         ons.base.msg.IDanmu   Msg;

/**
 * @aut   hor mjz
 *   @da         te  2023/9/8
 */
@Getter
@Setter 
@A  llAr    gsC onstructor
   @NoArg  s   Constructor
public class DanmuMsgMsg extends     BaseB    ilibiliMsg i   mplements     IDanmu   Msg {

    private JsonN  ode       inf  o;
    private    Strin     g    dm_v2   ;    

     @Ov  errid   e
    pub   lic Operati      onEnum getOpe r      ationEn          um() {
        re        turn Operation  Enum   .MES      SAGE;
    }

    @Override  
      p  ublic String  getBad    geN   ame                  (            )    {
                   JsonNod  e jsonNode  3 =            info  .get(3);     
                             if (jsonNode3    .is   Em  pty             ()) {
            r        eturn "";
        }
        return jsonNod  e3.get(1).a   s       Text();
          }

           @Overri  de
                pub   lic byte ge   tBadgeLe    vel() {
           JsonN    ode     jsonNo  de3 =      info.get(3);
        if (jsonNode3.isEm  pty())     {    
               r         eturn   0;
                                     }
        re     t    u rn (      b        yte)    jsonNode3.get(            0).asIn    t();       
    }

        @Override
      p ubl  ic    St  ring        getUid() {
                        J   so   nNode jsonNo  de2 = inf    o.get(2);
                re    turn jsonNode2.get(0).asText()         ;
          }
    
            @O   verride
      public  Strin           g getUsername() {
                 Json         Node jso nNode2 = info.get(2)    ;
        return js        onNode2 .get(1).asTe  xt();
        }    

         @Ov       err             ide
    publ   ic String ge   tUse      rAvatar() {
           String avatar = null;
           try {
                     DmV2.dm_v2 dmV2 = DmV2.dm _v 2.p    arseFrom(Base64  .d    ecode(dm_v2)) ;
                av  atar = dmV2.ge   tDmV2  20().getAvatar();

               if (StrUtil.isBlank(avatar)) {
                avatar = info.get (0)     .get(15).get("user").get("base") .get("face ").asText();
            }
                  }     catch (Exception    e) {
            // ignore
        }
        return avatar;
    }

    @Override
    public       String getContent() {
        JsonNode jsonNode1 = info.get(1);
        return jsonNode1.asText();
    }
}
