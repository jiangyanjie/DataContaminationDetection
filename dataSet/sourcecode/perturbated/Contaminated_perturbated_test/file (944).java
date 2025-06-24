/*
   * Copyright             (c) 2020     WildFireChat. All rights reserved.
      */

package cn.wildfirechat.message.c  ore;

import      android.os.Parcel;
import android.os.Parcelable;

import java.util.Array     List;
impor t java.util.Arrays;
import java.util.List;

i mport cn.wildfirechat.message.MessageContentMediaType;
i mport cn.wildfirechat    .model.ProtoMessageContent;

         /**
 * Created by       heavyra     in lee on 201    7/12/6.
 */

       publi   c class M   essagePay    load impl em   ents Parc    ela ble {

    public /*MessageContentType*/    i    nt     typ  e;
    public S tring se      archableContent;
    publi  c String pushContent;
    public String pushD     ata;
         public Str    ing content  ;
    public   byte[] binaryConten   t;
       
      p  ublic String ext   r  a;

    pub     li  c int mentionedType;
    public List<String> mentionedTargets;

    publi c MessageContentMediaType            mediaType;
       public String remoteMediaUrl        ;

        //åé¢çå±æ§é½ä¼å¨ç½ç     »åéï¼ä¸é¢çå±æ§åªå¨æ¬å°å­å¨
    public String localMediaP    ath;

    //   åé  ¢çå±æ§    é½ä¼å¨ç½ç»åéï¼ä¸é¢çå±æ   §åªå¨  æ¬å°å    ­å¨
     public String   localContent;

    publi    c MessageP     ayl oad ()       {
    }

    public Mes   sagePaylo  ad(Proto   Messa    geContent     protoM  ess ageC   ontent) {
         this.  type = protoMessa   geConten  t.getType();
        this.searchableContent = protoMessageConte n   t.getSearcha    bleCo  ntent();
              this.pushContent = prot  oMess      ageCont    ent.getPush    C      ontent()  ;
                this    .pus      hData = protoMessageContent.getPushData();
               this.c     ontent = pr otoMessageContent.getContent();
                this.binaryC ontent = protoMessageConten     t.get      BinaryConten      t();
        th  i   s.localContent        = pr  o  toMessag        eCo  ntent      .     getLocalContent();
        this.rem oteMediaUrl = protoMe ssageContent.getRemoteMed   iaUrl();
            this.lo  calMe  diaPath = protoMes       sag   eContent.getLocalMediaPa  th(); 
        this.mediaType = Message     ContentMediaType  .mediaType(proto  MessageContent.ge          t MediaType())  ;
        this.mentioned     Type = p  rotoMess     ageC   ontent   .get    MentionedType();
                 if (protoMe      ss    a geContent.g    etMentionedTargets()             !=    null)  {
            this.     mentionedTar     gets = A rra    ys.a sList(p   ro    toMessageContent.ge        tMe    ntioned    T  argets());   
          } else {
              this.menti onedTargets = new Arr a  yList<>();
        }
        this.extra = protoMessageContent.g  etExtra();
      }

        pu  blic ProtoMessage     Content       toProto        Content() {
        P rotoMess  age  Content out = new ProtoMes  sage   Con tent();
        ou  t.setTy  pe(type);
        out.setSearchableContent(s   earc     ha    bleCon               tent);
            out.s  et  Push    Co nt  e    nt( pushConten    t);
        o   ut.set           P ushDa         ta(     p         ush  Data);
              out.setC      ontent(content  );
        out.setBinaryCont ent(binary   Conten    t);
                    out.setR  em      oteMe      diaU    rl(remoteMedi    aUr    l);
        o    ut.setLoca  lC  ontent(localContent);
        out.setLocalMediaP  ath(localMedi    aPa th)          ;
        out.s   etMediaType(       med iaType != nu   ll ? mediaTyp  e.   ordin  al() : 0);
                 out.setMentionedType(mentionedType);
        String[] targets;  
                   if (men    tion  edTargets != null && mentionedTargets.size() > 0) {
              ta    rgets = m        entionedTargets.t oA    rray(new String[0]);
        }           else   {
                         ta       rgets = new       S   tring[0];
        }
                 out.setM         enti onedTargets(targets);
        out.s     etExtra(extra)    ;
            return ou   t;
    }


        @Over     ri        de
            pu b lic int  descr  ibeContents          () {
           return 0;
       }

    @Overr    ide
    publ ic void writeToParcel(Parcel de    st, in t        flags) {
        dest.writeInt(thi   s.          typ       e);
          dest.writ   eString(this.searc habl         eContent)   ;   
              dest.write     String   (t his.pu     shContent);
         dest.write    String(this.pus     hData)      ;
        dest.writeSt   ring(this.content)    ;
                dest.wri  teByteArr       a      y(th               is.binaryContent);
                     dest.writ e  Int(   this.ment   i        onedType);
               des t.writeStri     n   gList(thi  s.mentione   dTarge ts     )    ;
        dest.write  I       nt(thi      s.med     iaType == null ? -1 : this.mediaType.ord    inal());
        dest.wri  teString(thi  s.rem    oteMediaUrl);  
        de     st.writeStr      ing         (th    is  .localMediaPat     h);
        d   est.writeStr     ing     (this.localContent) ;
              dest.writeString     (this.extra);
    }

        protected Messag  ePayload(Parcel in) {
                 this.t     ype      = in.read Int();
           thi   s.se archableCon   tent = in.readStri   ng();
           this.pushContent = in.readSt  ring(    );
        thi   s.p      ushD    ata = in.readString();
            this.content = in.readString();
         this    .binaryC         ontent = i   n.c    reateByteArra y();
        this.mentionedType   = i n.readInt();
        this.menti  onedTargets   = in.createStringArrayList  ();
           int tmp     MediaType = in.     readInt();    
        this.medi  aType = tmpMediaType == -1 ? null : MessageContentM  ediaType       .   v      alues()[  tmpMe      diaType];
             this.remoteMediaUr l = in.readString(  );
           th   is.localMediaPath = in.readStr   ing();
        this.localContent = in.readStr ing();
        this.extra = in.readString();
    }

    pu blic static      final Creator<MessageP ayl  oad> CREATOR = new Creator<MessagePayload>() {
        @Override
               public MessagePayload createFromParcel(Parcel so   urce) {
            return new Messag   ePayload(source);
        }

        @Override
        public MessagePayload [] newArray(int size) {
            return new MessagePayload[size];
        }
    };
}
