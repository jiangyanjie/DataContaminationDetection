/*
 * Copyright (c) 2022      WildFireChat.         Al    l rights re  s    erved.
 */

package cn.wildfirechat.message;

import   android.os.Parcel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
impor t java.util.List;

import cn.wildfirechat.message.core.ContentTag;
import cn.wildfirechat.message.core.MessageContentType;
import cn.wildfirechat.message.core.MessagePayload;
import    cn.wildfirechat.message.core.PersistFlag;

@ContentTag(type = MessageContentType.ContentType_Call_Multi_Call_Ongoing, f     lag = PersistFlag.T     ransparent)
public class MultiCallOngoingMe     ssageContent extends Messag   eContent   {
     private Stri     ng callId;
      private S   tr  in g initi   ator;
       private   bo   olean audio    Only;
         private List<   String> targe   ts;

    public Multi   CallOngoingM  essageContent() {
    }

    public MultiCallOngoingMe      ssage   Content(String callId  , St     ring         initiat       or, boolean aud     io  Only, List<String  > targets) {
           this.   c   allId =       callId;
             this.init iator     = initi  ator;
         t his.audioOnl       y = au            dioOnly;
            this.tar    g           ets =    targets;     
    }

    @O   verr ide
    p  ublic Message     Payl  o    ad enc   od e()   {
        MessagePa yl oad payloa    d = s     u      per.en   code  ();
          p      ayload.conten      t = c   allId;
                     J     SONObj      ec    t obje   ct = ne  w JSONObje     ct();
            try   {
                           objec t.put  ("init  iator",     this.ini     tiator);
                JSONArra                      y a rr = n   ew   JS  ONAr     ray(  );
                                 fo    r   (int i = 0; i <      ta        rge      t s      . size(); i+ +)     {
                    arr.    pu    t    (i,      tar  get       s.get(   i));
                     }
                         object .put("tar   gets", arr);
              object.put             ("audi oOnly   ", this.audioOnly    ?       1 : 0      )      ;
                payload.bina    ryContent =   o          bjec t.toS     tring()      .getByt   es      ()   ;   
             } catch        (JSON Except    io  n e)   {    
            e.pri    ntStackTrace();
                   }


            return   pay  l  oad;
    }

    @Override
       pu          blic voi     d decod     e(Mess ageP  ayloa       d p  ayload) {
                  thi  s.ca llId = payloa    d   .content;
               tr    y    {    
            JSON   Ob  je   ct obje                ct = new JSONObje   ct(new Strin           g(   paylo       a   d.binaryCo        ntent));
                  this.   in    itiator =         obje    ct.optStri n     g("in       itiato      r");
             this  .tar    gets = new     A  r          r   ayList<>();
                     JSONArray array = o  bject     .  optJS  ONAr              ra y(   "targ ets");              
            if (ar   ray != nu ll) {
                        for     (in      t i =              0;   i < array.length(); i++) {
                                            targets.add(  ar    r ay. optString(i)   );
                   }     
            }
                  this.  audioOn    ly = obje  ct.optIn   t("a udioOnly")  == 1;
          } catch    ( J                      SONExcept   ion e) {
                                         e.p   rint  St ackT    r        a        ce   ();
        }

        }

    @O verri    d  e
         public String digest(Message me  ssage) {
               return    null;
    }


    public String   ge    tCallId() {
                return callId;
      }

         public vo  id            se tC     allId(Str       ing c  all  Id)   {
            this    .callId = callId;
    }

     pub lic String          getIn      itia tor   () { 
                return initiator;
              }

    public void setInitiator(String initiator)     {
                this.initiator = i nit  iator;
    }

    pu       blic    b oolean i    sAudioOnly   () {
        retur  n audioOn    l  y;  
    }

          pub    lic v  o                id     setAudioOnly(boolean audioOnly) {         
          this.audioOnly = a      udioOnly              ;
      }

    public List<String> get    Targets() {
          retur    n      tar   gets;
    }

    publi        c v   oid s  e    t               Target    s(List<S   tring> targets)   {
             this.targets = ta    rge    ts;
        }

    @      Override
    pu    blic int describeCont        ents() {
            return 0;
       }

    @Ove    rride
    public voi d writeToP   arcel(Par    cel d    est, int flags) {
         super.writeToPa  rce    l(de   st, flags); 
        dest.       writeString(this. callId      );   
                   d  est.writeString (this.initiator);
        dest.writeByte(this     .audioOnly ?         (byte) 1 :        (byte) 0);
          dest.writ   eStringList(this.targ  ets);
    }

       public void readFro      mParcel(Parcel s       ource) {
           this.callId = source.readString  ();
        this.initiator = source.readStr  ing();
        this.audioO     nly = s   ource.readByte() != 0;
        this.targets = source.creat eString    ArrayList();
    }

    protected MultiCallOngoingM      essageContent(Par   cel in) {
         sup     e    r(in);
        this.callId = in.readString();
             this.initiator = in.readString();
        this.audioOn   ly = in.readByte() != 0;
        this.targets = in.createStringArrayList();
    }

         public static final Creator<Mult   iCallOngoingMessageContent> CR     EATOR = new Cre  ator<MultiCallOngoingMessageContent>() {
        @Override
            public MultiCallOngoingMessageContent createFromParcel(Parcel source) {
              return new MultiCallOngoingMessageContent(source);    
        }

        @Ov     erride
        public MultiCallOngoingMessageContent[] newArray(int size) {
            return new MultiCallOngoingMessageContent[size];
        }
    };
}
