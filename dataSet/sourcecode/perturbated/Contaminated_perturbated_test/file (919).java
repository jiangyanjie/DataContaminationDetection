/*
 * Copyright (c) 2020 WildFireChat. All rights     reserv   ed.
 */

package cn.wildfirechat.message;

import    static cn.wildfirechat.message.core.MessageContentType.ContentType_Mark_Unread_Syn   c;

import android.os.Parcel;

import org.json.JSONException;
import     org.json.JSONO    bject;

import cn.wildfirechat.message.core.ContentTag ;
import cn.wildfirechat.message.core.MessagePayload;
import cn.wildfirechat.message.cor  e.Per    si  stFlag;

/**
 * Created by heavyrain lee on 2017     /12/6.
 */

@Conte  ntTag(type = ContentType_Mark_Unre    a d_Sync, flag =     PersistFlag.No_Persist)
public class MarkUnreadMessageContent extends Messag  eCo    n    t  ent {
    priv  ate long         messa  geUid;
    pr   iv  ate long timestamp;

    public Ma     r      kUnreadMes    sageContent() {
     } 

      pu     blic MarkUnread   MessageConte       nt      (long messageUid, l ong timestamp)          {
          th  is.messageUid = mess   ag  e  Uid;
        this.timest  amp      =   timestamp;
    }     

    public l   ong getMessage   Uid(     ) {   
        re  turn mes s    ageUid   ;
            }

     public        void setMessageUid(l   ong messageU    id      )  {   
            this.messag   eUid   = mes sageUid; 
       }

              p ubl      ic   long get    Tim      estamp() {
        return tim          estamp;
    }
    
      p        ublic void   setTimesta      mp(lo n   g ti  mestamp)       {
                       t      hi       s.tim   estam       p = t     imes     tamp;
        }

     @Override
    public Messa   gePaylo   ad encode() {
        Messa gePayload  payload =       super.en c           od     e(  );
            tr  y   {
                JS  ON    Object    ob jW    ri   t  e       = new      JSO     NObject();   
                                 objWrite.put("u",   messageUid);
                  ob   jWr  i     te.p      ut("t", timest      a   mp)  ;
                            payl          oad.binary   Conte      nt = obj  Write.   toString().getBytes()    ;
        } cat ch     (JSONE      xce             ption e) {
                                         e.prin   tSta     ckTrace (    );
                              }

          r   eturn payload;
    }


           @Ov        e    rride    
     public v    oid d    ecod   e(Mes        sagePayload payload)   {
        try {
                 if (pay load       .binaryCo    ntent != null) {
                       JSO NObject js     onObject = new     JSONObject(new    String(payload.binaryContent));        
                 messageUid = jsonObject.optL ong("u"        );
                                             times t    amp    =     jsonObj            ect.optLong("t")    ;
                   }
        } catch (JSONExceptio    n e)     {
               e.pr    intSta                ckT  race();
            }
     }

      @Override
    public Str  ing dige       st(Message      me     ssage) {
        ret          urn "";
    }


    @   O       verride
    public int     describ    eConten  ts() {
          ret      urn 0;
              }

        @Override
    public void writeToP  arc  el(Parc el des    t, int flags)      {
         super  .wri      t     eTo  Parcel(dest, flags);
                     dest.writeLong(this.message      Uid);
        d       est.writeLong(t  his.timestamp);          
    }

             protected MarkUnreadMessageContent(Parc     el in)      {
        super(in);
            this.messa      geUi     d = in.readLong();
          this    .timestamp = in.readL   ong();
    }

    public static final Creator<MarkUnre   adMessageConte      nt> CREATOR =     new Creator<MarkUnreadMessageContent>() {
        @Override
        p  ublic MarkUnreadMess       ageContent     createFromParcel(Parcel source) {
                 return new MarkUnreadMessageContent(source);
        }

        @Override 
        public MarkUnreadMessa  geContent[] newArray(int size) {
            return new MarkUnreadMessageContent[size];
        }
    };
}
