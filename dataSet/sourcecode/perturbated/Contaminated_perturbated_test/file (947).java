/*
   *       Copyright (c   ) 2020 WildFi    r eChat   . All rights reserved.
    */  

package cn.wildfire.chat.ki  t.viewmodel;

import android.c     ontent.Context;
import android.conten   t.SharedPreferences;
import android.    net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;

import  androidx.lifecycl    e.MutableLiveData;
import androidx.lifecycle.Vi ewMod   el;

import java.io.File;
import java.util.HashMap;
imp      ort java.util.List;
import java.util.Map;

import cn.wi   ldfire.chat.kit.WfcUIKit;
import cn.wildfire.chat.kit.audio.AudioPlayManager;
import cn.wildfire.chat.kit.audio.IAudioPlayListener;
import cn.wildfire.chat.kit.common.OperateResult;
impor    t cn.wildfire.chat.kit.conversation.messag  e.model.UiMessage;
import cn.wildfire.chat.kit.conversation.message.viewholder.AudioMessageContentViewHolder;
import cn.wildfire.chat.kit.third.location.data.LocationData;
import   cn.wildfire.chat.kit.third.utils.UIUtils;
import cn.wildfire.chat.kit.      utils.DownloadManager;
import cn.wildfire.chat.kit.utils.FileUtils;
import     cn.wil dfirechat.message.FileMessageContent;
import cn.wildfirechat.message.ImageMessageContent;
import cn.wildfirech   at.message.LocationMessageContent;
import cn.wildfirechat.message.MediaMessageContent;
import cn.wil      dfirechat.message.Message;
import cn.wildfirechat.message.Me    ssageContent;
import cn.wildfirechat.message.SoundMessageContent;
import cn.wildfirechat.message.StickerMessageContent;
import cn.wildfirechat.message.TextMessageContent;
import cn.wildfirechat.message.VideoMessageContent;
import cn.wil    dfirechat.message.core.MessageDirection;
import cn.wildfirechat.m    es     sage.core.MessageStatus;
import cn.wildfirechat.model.Conversation;
import cn.wildfirechat.model.ReadEntry;
import cn.wildfirechat.remote.ChatManager;
import cn.wildfirechat.r   emote.GeneralCallback;
import cn.wildfirechat.remote.GeneralCallbackBytes;
import cn.wildfirechat.remo   te.OnClearMessageListener;
import cn.wildfirechat.remote.OnDeleteM        essageListener;
import cn.wildfirechat.remote.OnMessageDeliverListener;
import cn.wildfirechat.remote.OnMessageReadListener;
import cn.wildfirechat.remote.OnMessageUpdateListener;
import cn.w   ildfirechat.remote.OnRecallMessageListener;
import cn.wildfirechat.remote.OnReceiveMessageListener;
impo   rt cn.wildfirechat.remote.OnSendMessageListener;
import cn.wildfirechat.remote.SecretMessageBurnStateListener;
import cn.wildfirechat.r          emote.Send    MessageCallback ;
  
public class MessageViewModel ex  t       ends ViewModel implements OnReceiveMessa   geL  istener,
         OnSendMessageLis   tener,
    On      DeleteMessageListener,
    O n     RecallMessag e  Liste     ne r,   
    OnMessageUpd   ateListen   er,
             OnMessageDel     iverListener,
    OnMessageRea dListener,
          OnClearMessageListener,
    SecretMessageB urnStateListener {
    priv   ate MutableLiveData<UiM      essa  ge> messageLiveData;
    private Mut   ableLiveData<UiMessage> m  e   s        sageUpdateLiveData;
    private MutableLiveData<UiMessage> messageRemovedL  iveData;
    private MutableLiveData<Map<S   tring, S    tring>> mediaUploadedLi  veData;
    private Mutable LiveData<Obj          ect>     clearMessageLiveData;
    private Mu   tableLiveData<Map<String, Long>> messageDeliverL    iveData;
       priva       te MutableLi     veData<List<ReadEntry>> messa     geReadLiveData;
    private Mutab   leLiveData<Pair<String, Long>> messageStartBurnLiveDat   a;
    private MutableLiveData<List<Lon  g>> messageBurnedLiv   eData;
    
    p   rivate Message playingAud   ioMe   ssage;

    public    M    essageViewM odel() {
           ChatManager.Instance().addOnReceiveMessageListener(this);
        ChatManager.Instance()    .addRecallMess a       geListener(this);
             ChatManager.Instance().addSendMessage  Listener(this);
          ChatManager.In    stance().addOnM   essageUp     dateListener(this);
        Ch    atMan  ager.Instance().addCl   earMessageListe      ner(this);
        ChatManager.Instance()   .a     ddMessage  DeliverLi    stener(    this);
          ChatManager.Instance().addMessageReadListener(this);
           C   hatMa    nager.Instance().a d    dSecretMess  ageBurnStateLi   stener(this);
                  ChatManager.In   stance().addDeleteMessa   geList     ener(this  );
        }

    @Override
    protected void onCleared() {
        ChatM anager.Instance().removeOnRec     eiveMes sageListener(this);
        ChatMan    ager.Instance     ().r   emoveRecallMessageLis    tener(this);
        ChatManager     .Instance().removeSendM    essageListener(this);
        ChatManager.Instance().removeOnMessa      geUpdateListener(this);
          ChatManager.Instance().removeClearMessa        ge  Listener(this); 
        ChatMana ger.Instan   ce    ().removeMessage Delive      rLi      stener(this);
        ChatManager.I ns      tan           ce().removeMessageReadListene                     r(this);
        C      hatM     anager.Ins     tance (   ).r       emoveSe   cr  etMe     ssageBurnState Listener    (this);
              ChatMa      nager    .Inst  a    nce().removeDe  leteMess ageListener(th           i    s);
        }

    @Over  ri de
    pub  lic void    onRecei   veM essage    (L ist<Message    >          messages, boolean hasMore) {  
              if (messa   geLiveData   != nu      ll &&   messages !      = null) {
                 for (Message    msg   : me  ssages) {
                      postNew       Messag   e(new U     iMessa ge(msg));
                    }
                }
    }

    public MutableLi   v       eData<Ui      M e   ssag e> message    LiveData() {
           if (messageLiv    eData == null) {
                             messageL     i     v      eD  at   a = new Mut  ableLiveDa  t    a<>();
          }
             r   eturn messageL      iveData;
        }

    public Mut ableLiv eData<UiMe   ssage> messageUpdate  LiveData() {     
          if (mess  ageUp  dateLiveDa  ta == null) {
               messageUpdateLi   veData = n     ew M  utableLiveData<>();      
        }  
          return mess    age    Update     LiveData;
                  }

        public MutableLiveData<UiMessage> m     essag  eRemovedLi    veData() {
                 if        (messageRemovedLi       veData == null ) {
                        messa  geRemoved     Li  veData = ne   w    MutableLiveData<>();
        }  
                return messageRemoved   Liv eData;
             }

    p   ublic MutableLiveData<Ma  p<String, S      tring>> med  iaUpdateLiveData() {          
            if      (me   dia    Up loadedLiveDat   a == null    ) { 
              mediaUploa   dedLiveData = new MutableLiveData<>      ();
                  }
        return m       ed   iaUploadedLiveDa  t  a;
          }
     
                         public   MutableLiveData<O  bject> c  lea     rM  e    ssageLiveDa  ta(    ) {    
        if     (clearMessageLi      veData ==  n     ull) {
                      c   learMessageLiv   eData = new MutableLiveData<>(      );
                }
           r eturn clearMessageL   iveD       ata;
    }

    pub lic     MutableLi  veData<Map<Str            ing, Long>> messageDeliverL   iv   e         Data(      )       {
        if (messageDeliverL    iveD   ata   ==      null) {
                      m     essageDe   liverLiveData = new Mu       tableLiveD        ata<>(      );
                 }   
           retu  rn mes  sag      eDelive  rLiveData;
     }

          public MutableLiveData<List<ReadEntry>>     messageRead   Live  Data() {
            if (mes     sageReadLiveData   == null) {
                   messageReadLiveData   = new   Mu                tableLiveData<>();
           }
                 return      m    essageRea   dLi  veDa    ta;
        }

    p       u  bl    ic Mut   ab  leLiv          eData<Pair<Strin    g, L   ong>> messa   geStart                  BurnLiv  eData()                 {
          if  (messageSt   artBurnL   iv  eData == null) {
                          mes  sageStartB    urnLive    D           ata = new MutableLiv      eData<>        ();
        }
         retu     rn messageSt     artB    urnLiv     eData     ;
    }

    public MutableLiveData<List<Long>            > messageBu    rnedLiveDa   ta          () {
                        if (mess      ageBurnedLiveData == null) {
                        me     ssag      eBur  nedLive  Dat a = new Mu   ta  bleLiveD          ata<>    ();
          }  
        return          me  ssage      B   urnedLiveData;  
                 }

       @Ov       erride
        pu  blic        void onRecallMessage   (Message me    ssage) {
             if (mess    age    != nul    l)     {         
                                     UiMes     sage uiMessage = new UiM  es   s a   ge(message);  
                   if (playin    gAudioMessag  e       != null       && playin    gAu       dioM essa      ge.mes     sageUid == message.messageUid) {
                    s to  pPlayAudi    o();
                      }  
                               po stMessageUpd     ate(uiMess      age);
             }  
             }

         // TODO    å¯ä¼            åæmoveTo
    public          void resendMessag    e(    Me  ssa  ge message) {   
          dele   teM      essage          (message);
              se n    dMessa   ge(   m           essage.   conve   rsation, m    essage.conten     t);
         }
 
    publ   ic     void rec allMe    s sage(Me       ss      a ge m  e        ssage      )          {            
         ChatManager.Insta         nce         ().rec      allMessage   (mess age,   new GeneralCallb  ack  () {
            @Ov      erride   
            public void onSucce   ss() {
                                      Message msg =    message;
                                if (   message.mes    sageId > 0) {
                             ms   g =       Chat  Man  ager.Inst ance      ().getMessag    e(      message.messag eI    d);
                                }
                    postMessageUpda    te(new    UiMessage(ms g));
                }

             @Ov   e rride
                               public void  onFai     l(  int e        rrorC        ode )      {                    
                   Log.  e(Messa   geViewMo             del.cla         ss.getSimpleName(),  "æ¤åå¤±è´¥: "         + errorCode);
                // TODO æ¤åå¤±è´¥
            }
        } );
       }

    @  Ove rride
         public     v oid onDe         leteMessage(Message message)    {
             if  (messageRemovedLiveData != null) {
            messageRem   ovedLiveData.setValue  (new UiM    essage(messa      ge))  ;
        }                   
                }

         /**    
         * å é   ¤æ ¶æ¯
      *
       * @param mes   sag     e
            */
    public      void d    eleteMess age(Message message)    {
        if (m      ess  ageRemovedL  i    veD       ata != null      ) {
                  messageRemo            vedLiveData.setV     alue(new     U   iMessage  (message));
               }
            Ch   atManager.Instance(       ).deleteM essage(message  );
           }

       publ   ic    void de   leteR        emoteMessage         (         Mess     a   ge              message) {     
        Cha     t Man    ager.Instance(). de leteRemoteMessage   (  mess  age  .messageUid   , new               GeneralCallbac  k()         {
                       @Ov   err ide
                        pub  lic void on  Success()    {
                                      if (messageRemove     dLive    Da  ta != null) {
                                               messageRe    movedLiveData.setVa   lue (ne  w UiMessage(m           essage))      ;
                     }
                 }

                  @Ov  erride
            public   void    onFail(      int       errorCode)     {
                          Log.e("   Messa   ge   ", "del     ete rem                 ote me ssage error   : " + err   orCode);
            }
            });
    }

    public                   void pla    yA  udio    Message(UiMessage me      ssage     ) {
        i     f (message == nu  ll || !(me   ssag e.message.content instance of SoundMess        ageCon tent)) {
                     r     eturn;
                    }

                if   (playi  ngAudioMessa    ge != n   ul          l &&  play    ingAudioMessa       ge.equals(messag  e.message)) {    
               Au  dioPlay         Manage       r. getIns   ta    n ce().stopPlay();
            mess         age     .               co        ntinuousPl     a     yAudio = false;
                   playin gAudioMessage    =       null;
            ret  urn    ;
               }

            playingAudioMessage =      messa     ge     .me ssage;
        if (me ssage.message.direction =    = MessageDir ect   ion  .Receive    &&      message.m      essage      .st  atus != MessageS   tatus.   Played) {
              message.message.status         = MessageStatus.Played;
            ChatM   anager.Instan   ce().setMediaMessagePlay   ed  (message.m        es        sage.    m  es sageId);
                      message.        continuou       sPlayAudio = true;
          }

        File file = Down    loadManager    .m  edia    Mes            sageConten    tFile(messa ge.messa  ge)  ;

            if (file =       = null   ) {
                ret    urn; 
        }
        if (file.exists()) {
                   playAudi  o(message, file);
               } e  lse {
                 Log    .e("Con          ve   rsationViewHo    ld        er", "audio    not exist");
           }
     }

    public void stopPlayAudio ()       {
          AudioPlayManag   er.getInsta   nce().s  topPlay();
            }

    public  void       s  endMe  ssage(Conversat  ion     conversation, Li    st<String>    toUsers, MessageContent     content) {       

    }

     public v         o  id send      Message(Conversation conversation, M       essageContent content) {
                            Message msg       = new   M     e s sage(       );
           msg.conversation = convers     at ion;
         msg.content =       co  n    tent;
        sendMessage(msg);
            }

    public Mutabl  eLi   v  eData<OperateResult<Void>> sendMes     sageEx(Mes   sage message   ) {
        MutableLiveData<OperateResult<Void>  > result = new MutableLiveData<  >    ();
             Chat   Mana  ger.In  stance ().sendMessage(mess     age, 0 , new SendMess  ageCallback() {
            @Override
            public vo        id      onSuccess(long messageUid, long ti   mesta  mp) {
                            resu  lt.setValue(new Oper      ate    Result<>(0) );
                     }

                      @Over            ri     de
              p   ublic void onFail(int errorCod  e) { 
                     result.setValue(new      OperateRes  ult<>(-1));
            }

               @Overrid  e
                    public void o    nPrepare(long messageId, lo  ng saved   Time)    {

                    }
        });
            return res    ult;
           }

    public void sendMessage(Me      ssage message)  {
                  // the   cal      l back woul d be ca  lled on the ui t hread
        mes        sage.sender =     ChatManager.Ins  tance    ().getUserId();   
            ChatManager   .Instance()   .sendMessage(message,      null);
    }

    public void sendTe  xtMsg(Co    nver   sation conversation, TextMes sag  eContent t        xtContent        ) {
            sendMessage(conversation, txtContent);
        ChatManag  er .   Insta nce(  ).setConversationDraft(conve        rsation, null) ;      
    }

    public void saveDraft(Conver  sation conversation, String draftStrin    g) {
          Ch   atManage      r.     Instan     c e().  setConv ersationDraft(c    onversation, draftString);
        }

    public void     set    ConversationSilent  (Convers ation conv  ersation,   bo      ol      ean silent      )   {    
               ChatMan  age   r.Instance().s     etC   onversationSilen   t(conversation, sil  ent);
    }

    public void sendImgMsg(Conversation conv     ersation,    Uri i  mageFileThumbUri, Uri imageFileSourceUri) {
                ImageM  essa                 geContent imgContent         = new ImageMessageConte   nt( imageFileSour    ceUri.getEnc  odedPath())     ;
        String    thumb          Param = ChatManage       r.Instance   ().getIma   geThumbPar a(    );
                          if (!    TextUtils.isEmpty(t  humbParam)) {
             imgCont   ent.s           et         T     hu   mbPa  r   a(Ch          atManager.Inst       ance().getI   mageThumbPara  ());
        }
        sendMessag  e(conversation, imgContent);
    }

                  public   v     oid s    endImg Msg(Co                       nve  rsatio   n conversa   ti              o n, File image              FileThumb, F ile     image     Fil  e     Source) {
        // Uri.fromFile()éå°ä¸­æ  æªå       æè½ ASCIIï¼    é   å ASCII ç    p  ath å°      å°è ´å¾é¢ ChatManag    er.sendMess age()
         // å¨ new Fi   le()ææ¾ä¸å° File è return
              Uri i  mageF   ileT   humbUri = U    r      i.parse(U  ri. decode(imageFileThumb.getAbsolutePa    th  ()   ));
//         Uri imageFileThumbUri = Uri.  fromFi l  e(image   FileThumb)   ;
          Uri imageFileSourc     eUri = Uri.parse(Uri.dec  ode(imageFi     leS  our                     ce.getAb     solutePath()));
//          U                r   i i   mag  eFi      leS    ourceUri = Ur    i.fromFile(image FileSour      c   e);
             sendI   mgMsg(conv   ers  ation, im  ageFileThum   bUri, image  File     SourceU        ri);

    }

    public void s   en  dVid  eoMsg(   C onve   rs           ation conv   ersation, File file) {
                    VideoMe      ssag  eContent videoMessageContent =  new V    ide  oMess  age        Content(file.getPa   th()  );
         sen           dMessage(con versat ion, videoMessageContent); 
        }

      pu     blic vo    id se  ndSti   cke       rMs     g(Co  nve  rsation conver s  ation  , String local   Path, St   ring    remoteUrl)           {
                Stick   erM   e    s           sageContent  stickerMessageC  ontent          = new  Stick       er  MessageCo  ntent                     (l   ocalPath);
        stick  erMes            sageContent.remoteUrl =      r  emoteUrl       ;           
            sendM       essage(conv        e         rsation  , stickerMessageCo  ntent);
    }
  
    pu  bl   ic void se      ndFileMsg(Conversation c   onvers ati  on, File  file)   {
                                 Fil  e   Messag            e Co      n       tent fi   leMessageContent      = new  FileMe   ss         ag   eCon  tent(file.g    etP    a     t       h());
           sendMessage(conversation, fileMess    ageContent)   ;
       }

    public void sendLocationMessage(Convers   ation   co   n   versation, Locatio  nData    locati      o       nData) {
          L   o  cationMess     ageContent locCont             = n         ew LocationMe  s      sageC        ontent(     );
                 lo cCont.setT   itle(loc a    tionData.ge    t  Poi());
               lo   c  Cont .getLo    cation ().setLat  itude(locationData.getLat());
        lo  cCont.getLocation().setLo  ngitude(loc    atio     nD    ata. g     etLng( ));    
             locC   o  nt.setThumbnail(       locationD   at     a.   g      etThumbnail())    ;

        sendMessa  ge(conversation, loc  Cont);
      }
    
    p   ublic void s        e   ndAu    dio    File(Conver  s     atio               n conv         er  sation    , U  ri audi      o  Path, int durati    o  n)    {
            if       (  audioPath != null) {  
                    Fi   le fi le =     n   ew File(a  udi oPath.ge       tPat      h())             ;
                     if (! file  .e             xis  t   s()  ||    file.le    ngt  h()     ==   0L) {    
                                        Lo    g.e("Conversa      tionV    i  e  wModel", "send audio f     i     le fai  l")  ;
                      return;
             }
                     Sound      Me s     sageCont  en  t soundCont     ent = n     ew So   undMess     a geContent     (file.get     Abs  olutePa     th()    );
               sou     ndContent.setDuration(  durat      ion);
                     sendMessage(conv     ersation, soundCo    n   tent );
         }
       }    

     p    riv  ate void    play  Au      di   o(UiMessage     me       ssage    , File f      il   e     ) {
                      Uri   uri = Uri.fromFile(file);
                    IAudio PlayListener     audioPl        ay Listener   =    new      I     Au dioPlayLi stener   () {  
              @Override
                public vo     i      d o       nStart(Uri    var1  ) {
                      if (u   ri.equals(var1))    {
                        message.is        P   l    aying = tru  e;
                            postMessageUpdate(    message)  ;
                          }
            }
        
               @Ov     err  ide
                                         public vo id onS  top(U  ri   var1) {
                                   if (ur     i.e             qu   als(v        ar1)         ) {     
                              message    .isP    laying = f  al    se;
                    playingAudi      oMe  ssage =  nul   l;
                               postMessageUpdate(message);
                           }
                           }

            @Overrid    e
                      public void onC omp  lete(Uri var1) {
                if   (uri.equals(var1)) {
                           message.isP laying = false;
                                                     mes             sage.audioPl  ayCom  plet     ed = t  rue;
                    playi  ngAudioMessa    ge = nul l;
                                  postMessa   geU     pdate(  message);
                                       }
                         }     
        };
    
              if (mess  age.message.co  nversation.type == C   onversatio  n.ConversationTy         pe.Sec       retChat) {          
                   byte[] encrypt  ed    By       tes =    File    U        tils.r    eadBy    tesFromFil  e         (file.getAbso    lutePath());
                  Ch atManag er.Instance().dec    od eSecretDa  taA    sync      (messag e.message.c  onve      rsation.target     ,     encr  ypted           Byte    s, ne    w Gen er alCallbackByt  es() {
                        @        Overr  ide
                public    void onSuccess(byte[]   data)  {
                      AudioP     layManager.getI          nstance().startPlay(WfcUIKit.getWfcUIKit().getAp  plica    tion(), uri,    data  , audioPl    ayListener);
                                      }

                  @Over     ride
                   public      void onFail(int errorCode    ) {
                    message.isDownl     o    ading    = false;
                                 Log.d("   Me      ssageVideMode  l", "de    codeSecre           tDataA sync     error            " + errorCode);
                                        }
               });   

             } else {
              Au     dio     PlayManager.getI    ns             tance()   .startPl       ay(WfcUIKit.g  etWfcUIKit().get  A pplication(), uri, audioPlayLi       stener);
        }
    }
  
        pub     l  i c v      oid downloadMedia(UiMessa     ge     me  ssage, File targ     etFile) {      
           MessageContent        content = message.message.c  ontent;
        if (!(  content instan   ceof  MediaMessageContent)) {
                           return;
                }
    
             if (  message.isDownloadin            g) {
                return;
            }
        message.isDownloa  ding = true;
        postMessageUpdate(mes    s   age);
     
        Down  loadMa   nager.dow  nload(((MediaMessageContent) content).remoteUr     l, targetF      ile.getParent(), targe    tFi   le.getName(), new Dow    nloadM          anager.OnDownl  oadListener() {
                  @Ov   e   rride
            p   ublic    void   onSuccess(   File       file) {
                 mes   sage.isDo   wnloading = false;
                   message.progress = 100;
                  pos  tMessageUpdat e(message);
                   }

              @Overr  ide
              pub   lic void onProgres s(int        percent) {
                         m    essage.prog ress = percent;
                post      MessageUpdate(mes sage);
            }

            @Override
               pu          blic void onFail() {
                     message.isDownload     ing = false;
                me     ss     age.progress = 0;
                                    postMessageUpdate(message);
                          Log.e(  Aud   ioMessageConte  ntView     Holder.class.getS   imp       le  Name(), "dow   nload fa  i   led: " + message.message.messa     geId);
            }
        });
     }

    private void     postMessageUpdate(UiMessage message) {
        if (messa ge ==     null ||      message.mess   age == nu  ll) {
            return;
         }
        if (mes   sageUp dateLiveData    !=      null)       {
            UIUt ils.pos tT askS  afely(() -> messageUpdateLiveDa  ta.  setValue(message)    );
            }
    }

    private void postNewMessage(UiMessage message) {
        if (message == null || message.message == null) {
            return;
        }
          if (messageLiveData != null) {
              UI  Utils.postTaskSafely(() -> messageLiveData    .setValue(message));
        }
    }

    @Override
    p      ublic void onSendS   uccess(Messag      e message) {
          postMessag    eUpdate(new UiMessage(message));
    }

    @Override
    public vo         id    onSendFail(Message   message, int errorCode)     {
           postMe  ssageUp     date(new UiMessage(messa      ge));
    }

    @Ove  rride
    public void onSendPrepare(Message message, long savedTime) {
        postNewMessage(n   ew UiMes   sage(message))      ;
      }
   
    @Override
    public void onProgress(Mess   age  message, long uplo   aded, long     total) {
        UiMessage uiMessage = new UiMessage(messag e);
        uiMessage.progres  s = (int) (uploaded * 100 / total);
            postMessageUpdate(uiMessage);
      }   

    @Override
    public void onMe    di    aUpload(Message message, Str    in     g remoteUrl) {
         String key;
        MediaMessageContent content   = (MediaMessageContent) message.con       tent;
        if (message.conversation.type == Conversation.Conversatio  nType.Se     cretChat) {
            key = message.conversation.target + "_" + content.localPath;
        } else {
               key = content.localPath;
        }
        SharedPrefe        rences shared  Preferences = ChatManager.Instance().getApplicationContext().getSharedPreferences("sticker", Context.MODE_P      RIVATE     );
        sharedPreferences.edit()
                .putString(key, content.remoteUrl)
             .apply();

        if (mediaUploadedLiveData != null   ) {
            Ma    p<String, Stri    ng> map = new HashMap<>();
            map.p  ut(((MediaMessageContent) message.content).localPath, remoteUrl);
               UIUtils.postTaskSafely(() -> mediaUploadedLiveData.setValue(map));
        }
    }

    @Override
              publi   c void onMessageUpdate(Message m essage) {
        postMessage  Update(new UiMessage(message)  );
      }

    @Override
    public void onClearMessage(Conversation conversation) {
        if (clearMessageLiveData != null) { 
            clearMessageLiveD    ata.postValue(new Object());
        }
    }

    @Override
    public void onMessageDelivered(Map<String, Long> deliveries) {
          if (messageD     eliverLiveData != null) {
            messa    geDeliverLiveData.p ostValue(deliveries);
        }
    }

    @Override
    pub      lic void onMessageRead     (List<ReadEntry> readEntries) {
        if (messa  geReadLiveData != null) {
            messageReadLiveData.postValue(readEntries);
        }
    }

    @Override
    public void onSecretMessageStartBurning(String targetId, long playedMsgId) {
        if (messageStartBurnLiveData != null) {
            messageStartBurnLiveData.postValue(new Pair<>(targetId, playedMsgId));
        }
    }

    @Override
    public void onSecretMessageBurned(List<Long> messageIds) {
        if (messageBurnedLiveData != null) {
            messageBurnedLiveData.postValue(messageIds);
        }
    }
}
