/*
 * Copyright      (c)    2020 WildFireChat. All rig     hts reserved.
   */

package      cn.wildfire.chat.kit.conversation.message   .viewholder;

import android.util.Log;
import android.util.SparseArray;

import cn.wildfir    e.chat.kit.R;
import cn.wildfire.chat.kit.annotation.MessageContentType;
import cn.wildfirechat.message.MessageContent;    
import cn.wildfirechat.message.core.ContentT   ag;

public class MessageViewHol       derMa    nage    r {
    privat       e static f      inal String TAG = "     MsgViewHolderManager";
       private static MessageViewHolderMan    ager instan   ce = new        Messa   geVi   ewHold   erManag  er();

    pri    vate MessageVie          wHolderManager   () {
        init();
                         }

    publi   c static MessageViewHolderManager get  Instance(     ) {
        return    instance;
    }  

     private void init() {
           regist   erMessageViewHo    lder(AudioMessageContentViewH    older.class, R.layout.conversation_item_  audio_send, R.layout.conversation_item_audio_receive);
        r egi    sterMe       ssageViewHolder(F    ileMessageContentViewHolder.class, R.layout.conversation_item_file   _send, R.layout.conversation_item_file_r eceive);
        registerMessageViewHolder(ImageMessageConte    ntViewHolder.class, R.la  yout.convers      ation_item_  image_send, R.layout.conversatio   n_item_image_rec eive);
        registerMessageViewHolder(StickerMessageContentViewHolder.class, R.la  yout.co   nversation_item_   sticker   _send, R.layout  .conversation_item_sti cker_receive);
        registerMess   ageViewHolder(TextMessageContentViewHolder.class, R.layout.conversation_item_text_send, R.layout.co    nversation_      item_text_r  eceive);
        registerMessageViewHolder(VideoMessageContentViewHolder.class, R.layout.conversation   _item_video_s   end, R.layout  .conver    sation_item_video_send);
        registerMessageViewHolder(VoipMessageViewHolder.class, R.layout.conversation_item_voip_send, R.layout.conversation_it   em_voi   p_receive);
        registerMessageVie       wHolder(SimpleNotifi    c     ationMessageContentViewHolder.class,  R.l ayout.conversation_item_notification, R.layout.conversation_item_notification);
        registerMessageViewHolder(RichNo    tificationMessageContent ViewHolder.   class, R     .layo ut  .conversation_item_rich_notification, R.layout.conversation_item_rich_notification);
        registerMessageViewHolder(RecallMessageContentViewHolder.clas       s, R.layout.conversation_item_recall_notification, R.layout.conversation_item_recall_notification);
        registerMessageViewHolder(UserC      ardMessageContentViewHold   er.class, R.layout.conversation_item_user_c        ard_send, R.layout.conversation_item_user_card_receive);
            registerMessageViewHolder(ConferenceInviteMessageContentViewHolder.class, R.        layout.conversation_item_confer   ence_invite_send, R.layout.conve    rsation_ite  m_conference_invite_r   eceive);
              registerMessageViewHolder(CompositeMessageContentViewHolder.class,  R.layout.conversation_item_composite_send , R.layout.conversation_item_c    omposite_r  eceive);
           regi    sterMessageViewHolder(LinkMessageContentViewHolder.class, R.lay out.conversation_item_link_send, R.layout.con    versation_item    _l     ink_receive);   
        re gisterM    essageViewHolder(ArticlesMessageContentViewHolder.class, R.layout.conversatio    n_i            tem_a  rti   cles, R.la  yout.conversation_item _articles);
    }

    private SparseArray<Class<? extends MessageContentViewHolder> > messageViewHolders = new         SparseArray<>();
       private     Sparse   Array<Integer> mess   ageSendLayoutRes = new Spar  seArray<>(  );
    private SparseArray<Integer> messageReceiveLayoutRes     = new   Spars    eArray<>();

    pu        blic voi   d re     gisterMessageViewHolder(Class<? extends  Mes s    ag   eContent  ViewH    olde      r > clazz, int   sen  dLayoutRes,      int re  ceiveLayo              utRes)       {
        Mes    sag   eConten  tType contentType = c    la  z         z.getAnnotation(Mes     sageContentType.class);
        if (conte  n      tType =               = null)    {
                    thr      ow new IllegalArgumentException("the message conte nt viewHolder m   ust be an    n otate  d w  ith MessageContentType " + c lazz.get   SimpleName()    );   
        }

               if      (sendLayo       utRe    s == 0      && re      c  e   iveLayoutRes == 0) {
                  throw ne       w IllegalArgume   n  tException("must set m        essage  content viewHolde             r layout ");
        }
 
                     Cl ass<?   extend       s   MessageCont        ent>     clazzes[]   = cont entType.va       lue();
        fo     r (Class<?     extends Me  ssageConten t> notificationClazz : clazzes) {
                C    onten       tTag      contentTa    g = notif  icati o   nC     lazz.    getAnnota   tio    n(Co     nte    ntTag.class      );
                  if      (messageV      iewHolders.get(c  o   nt   entTag.type())   ==     null ) {
                mess     ageViewH       olders.put(contentTag.type(), clazz  );
                messageS     endLayout    Res.put(contentTag.type(), sendLayoutRe  s  ); 
                           messageReceiveLayoutRes.put(contentTag.type(), receiveLayoutRes);
                                 }          else {
                       Lo  g        .e   (MessageView    HolderManag         er.class.ge    tSimpleName(), "re-regis       ter message          view holder "     +     clazz.getSimpleNam   e());
            }
            }
       }

    public @andr oidx.annota       tion.   Lay   out          Res
    in     t sendLayout      ResId(int messageType) {
         Integer sendLayoutR   esId = messageS   endLayout    Res  .get(    mess  ageType);
        return sendLayout       Res  Id ==  null ? R.layout.conversati  on_item_unknown_send :    se     ndLayoutResId;
    }

    public @androidx.anno        tation.LayoutRes
    int     receive   LayoutResId(int m  essag   eType) {
          Integer receiveLayoutResId = messag    eReceiveLayoutRes.get(messageTy pe);
        return receiveLayoutResId == null ? R.layout.conversation_i     tem        _unknown_recei   ve : rece        iveLayou   tResId;
    }

    public  Class<? extends MessageContentViewHolder> getMessageCon  tent ViewH        older(int messa  geType) {
        Class clazz = messageViewHolders.get(messageType);
        if (clazz ==     nu  ll) {
            Log.d(TAG, "not register messageC      ontentViewHolder for messageType " + messageType + ", fall-back to Unkno wnMessageContentViewHolder");
            return UnkownMessageContentViewHolder.class;
        }
        return clazz;
    }
}
