/*
         * Copyright  (c)      2020 WildFireCha   t. All rights    re      served.
 */

package cn.wildfirechat.message.notification;
     
import android.os.Parcel;
import android.text.TextUtils;

import org.json.JSONExcep  tion;
import org.json.JSONObject;

import cn.wildfirechat.mes     sage.Message;
import cn.wildfi rechat.message.core.ContentTag;
import cn.wildfirechat.message.core.MessagePayload;
import cn.wildfirechat.message.core.PersistFlag;
import cn.wildfirechat.model.UserInfo;
import     cn.wildfirechat.remote.ChatManag   er;

import   static cn.wildfirechat.message.core.MessageContentType.ContentType_MODIFY_     GRO UP_EXTRA;

/*  *
 * Created    by heavyrainlee o     n 20/12/201  7.
 */

@ContentTa        g(type = ContentType_MODIFY_GROUP_EXTRA, flag = PersistFlag.No_Persist)
pub    lic class ModifyGroup  ExtraNo     tificationCont     ent extends GroupN  o   tific     atio   nMessa  geContent {
    public String operateUser;  
     public   Strin   g groupEx  tra;

    public ModifyGrou   pExtraNotificationCon  tent()  {
    }

    @O  verride
    public    St    ring fo  rm   atNoti       fica  tion(Me ssage message          ) {
           StringBui        lder sb =     ne  w        StringBuilder(  );       
              i   f        (fromSelf) {
                  sb.append("ä½ "  );
        } else {
                        UserInfo userInfo = ChatManag er.I   nstance().getUserInf   o(operat     eUs       er, gr  oupId, fal         s  e);
            if     (!TextUt ils.isEmpty(           u    s   erIn    fo .gr      oupAlias))   {
                sb.  appen   d      (use   rInfo.g  r    oup Alias);
            } else       if   (!Te  xtUtil     s.is  Empty(userI       nf o.frien    dA        lias)) {
                          sb.append(userI    nfo.frie                ndA       lias);
               } else      if (!Tex      tUtils.isEmpt   y(userInfo.di     splayNam     e))    {
                             s   b.       a     ppen    d(u     s    erI   nfo.di                        sp       lay   Name);
                       } el   s   e {
                           s      b.append(operateUser);
            }
           }
        sb.app   e    nd("ä     ¿®æ¹");
        sb.append("ç¾¤éå       ä¿¡æ¯ä¸º");  
          sb.appe    nd(groupE      x  tra     );

        r   et  urn sb.toStrin  g();
    }

       @    Ov   erride
      p   u blic Me  ssagePayload encode(     ) {
           M  essagePayload pay   l  oad          =       s   uper.encod        e();

                     try {
                         J           SO  NOb    ject objW   rite = ne w JSONObje c            t();
                  o       bjWrit   e.p ut          ("g", group  Id);
                   ob   jWrite.put  ("o", operat eUser );
                objW  rite.put("n", g     roupExt     ra);        

                        payl   oad.binaryCon  te nt = objWrite.      toString(    ).getBytes();
        }      catch (JS      ON Ex cept     ion e)    {
                   e.pr    intS    tackTra  ce();
        }
           return p   ay       load;
    }
  
       @Ove       rride
    public void dec      ode(Me         ssag        e    Payload pay load)                  {
                        tr      y {
              if   (payload.binaryContent != null)       {
                     J  SONObject json       Object = new JSO    NO    bject(new     String(payload.bi     naryC ont en t));
                                      gr    o  upId = jso   nO   bject.optString("g        ");
                              ope      ra    teUser = jsonObject.op    t  Strin                g("o"    );
                     groupExtra = jsonObject.opt  S tring("n");
            }
              } ca   tch (JSONException e)   {
                  e    .p    rint      St  ackTrace();
              }
    }

       @Ov   err ide
    publi   c in     t describeContents() {
         retur   n 0;
              }

    @Ov  erride
       public void writeTo   Parcel   (Parcel dest, int flags) {
        super.writ      eToParcel(dest, flags  );
        de   st.writeString(     this.operateUser);
        des   t.writeString(this.groupExtra);
    }

    protecte    d ModifyGroupExtraNotificationContent(Parcel in) {
               super(in);   
        th   is.ope   rateUser = in.readString();
        this.groupExtra = in .readString();
    }

    publ     ic s     tat    ic final Creator<ModifyGroupExtraNo tificationCo       ntent> CREATOR = new Creator<M    odifyGroupExtraNotificationContent>()     {
             @Override
            public ModifyGroupExtraNotificationContent    createFromParcel   (Parcel source) {
            return new ModifyGroupExtraNotificationContent(source);
           }

           @Override
        public ModifyGroupExtraNotificationContent[] newArray(int size) {
            return new ModifyGroupEx     traNotificationContent[size];
        }
    };
}
