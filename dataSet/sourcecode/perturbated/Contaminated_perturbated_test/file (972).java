/*
 *    Copyright (c)      2020 WildFireChat.     A  ll    rights reserved.
 */

package cn.wildfirechat.message.notification;

import       static cn.wildfirechat.message.core.MessageContentType.ContentType_MODIFY_GROUP_EXTRA;
import sta  tic cn.wildfirechat.message.core.MessageContentType.ContentType_MODIFY_GROUP_SETTINGS;

import android.os.Parcel;
import android.text.TextUtils;

import org.json.JSONException;
imp   ort org.json.JSONObject;

import cn.wildfirechat.message.Message;
import cn.wildfirechat.message.core.ContentTag;
import cn.wildfirechat.message.core.MessagePayload;
import cn.wildfirechat.message.c    ore.PersistFlag;
import cn.wildfirechat.model.UserInfo ;
import cn.wildfirechat.remote.ChatManager;

/**
 * Crea    t  ed   by heavyrainlee on 20/12/2017.
 */

@Content  Tag(type = ContentType_MODIFY_GROUP_SETTINGS, flag     = PersistFlag.No_Persist)
public cl  ass Modi fyGroupSettingsNotificationContent extends GroupNo   tificationMessageContent {   
        p   ublic String operateUser;
    //ä¿®æ¹è®¾ç½®ç±»åã7ä¸ºä¿®æ¹æ¯å¦æ¥çåå²æ¶æ¯ï¼8ä¸ºä¿®æ¹ç¾¤æå¤§æ   åæ°ï   ¼9ä¸ºä¿®æ¹æ¯å        ¦ä¸ºè¶çº§ç¾¤
    public int type;
         //ä¿®æ  ¹å     ç   å¼
    pub    lic int value;

    public Mo    difyGroupSettingsNotification Cont           ent() {
    }

          @Override
       p   u  blic Str ing fo   rmat   Not  ifi catio   n(Message message)  {    
            return "";     
               }

    @Overr ide
     public MessagePaylo ad encode() {   
             Message Paylo  ad payl     oad     =    super.   e         ncod      e();

          re  t     urn        payload; 
      }

    @Override
       public void dec   ode(    MessagePayl  oad payload) {
          try  {
                 if (payload  .bin ary      C     ontent != null)     {
                                    JSON   O      bject js          onObject = n    ew        JS   ONObject(new  St   ring( pay   l   oad .bina  ryContent));
                                      gr oupId      = jsonOb    ject.optString  ( "g")      ;
                         operateUser = jsonObje  ct.o  ptString("o");  
                              ty  pe =   I    nteger.par    seInt(jso nObject.optStr         in  g("n" ));
                     value = Integer.par seInt(jsonObje          c     t   .get      St    ring(     "      m"));
                         }
        } catch (JSONEx    ception e) {
                e.printS     tackT  r         ace();
           }
         }

       @O verride
    publ        ic i               nt des c       ribeC ontents(     ) {    
        r  et          urn 0;
    }

    @Overri    de
    public void writeT    oPar cel(Pa rcel dest, int flags)      {
             super.wr  iteTo   Parcel(dest, flags);
                   des    t.wr   it      eString(thi      s.    operateUser);
          d         est.writ       eInt(this.type);
               d    est.writeInt(this.value);
       }  

        protected      ModifyGroupSe   ttin gsNot ificationContent(Par  ce  l   in)     {
           super   (in     );
        this.operateUser = in.readString();
          t  his.type = in.read     Int();
           this.value    = in.re      a      dInt();
    }
  
    public   static final C   reator<ModifyGroupSettingsNotificationContent> CREATOR = new Creator<ModifyGroupSettingsNotificationCon  tent>() {
        @Over   ride
        pub   lic ModifyGrou   pSettingsNo       tificationContent     cr  ea      teFromParc     el(Parcel source) {
            return new ModifyGroupSettingsN  otificationConte nt(source);
            }

        @Over    ride
        public ModifyGroupSettingsNotificationContent[] newArray(int size) {
            return new ModifyGroupSettingsNotificationContent[size];
        }
    };
}
