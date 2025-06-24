/*
    * Copyright (c) 2020 WildFireChat. All rights reserve   d.
 */

pack     age cn.wildfire.chat.kit.conversation.message.viewholder;

import android.content.Context ;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import      android.view.View;
i  mport android.widget.ImageVi ew;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;
    
import com.bumptech.glide.   Glide;
import com.bumptech.glid  e.RequestBuilder;
import com.bumptech.glide.load.eng  ine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import cn.wildfire.chat.kit.R;
import cn.wildfire.chat.kit.annotation.MessageContext  MenuItem;
import cn.wildfire.chat.kit.conversation.ConversationFragment;
import cn.wildfire.chat.kit.conversation.ConversationMessageAdapter;
import cn.wildfire.chat.kit.conversation.message.model.UiMessage;
import cn.wildfire.chat.kit.mm.MMPreviewActivity;
import cn.wildfire.chat.kit.mm.   MediaEntry;
import cn.wildfirechat.message.MediaMessageContent;
import cn.wildfirechat.message.core.MessageContentType;
import cn.wildfirechat.message.core.MessageStatus;
import cn.wildfirechat.model.Conversation;
import cn.wildfirechat.remote.   ChatMa  nager;

p   ublic abstract clas     s MediaMessageContent     ViewHolder e     xtend s NormalMess   ageConten    tVi ewHolder {

    /  **
            *      å°è§      é¢ï¼å        ¾ç å ä½å      ¾çéç½®
         */
    protected Req   uestOptions placeholderOptions = new   Re     questOpti  ons();

    p  ublic Me  diaMessageContentView Holder(Conver satio      nFragment fragment, Recyc le     rView.A   dapter adapter ,      View itemView ) {
              super(fragment, adapter, itemView)   ;
        placeholderOp     tions.   diskCacheStrate      gy(Di   skCacheStrategy.ALL);
        placeholderO   ptions.ce   nterCrop();
        placeholderOp tio       ns.p       laceholder(R.draw   able.     image_chat_pl         ace    holder);
    }

      @Over       r    ide
    protected vo   i      d onBind(UiMessa  ge message) {
                   if              (message.isDown     loading ||   me ss     age.                 messag    e.status ==  Mes               sageS  tatus.Send       ing) {
                p   rogressBa r.setVisi  b  ility(V iew.VIS   I  BLE);
        } else {
                         progre  ssBar    .setVis      ibi               lity(Vi     ew.GONE);
                }
             }

    pr    otected     void previe     wMM() {
              List<UiMes     sage> messages            = ((Co     nversationMessageAdapter)    adapter).getMessa     ges()  ;
                   Li   st        <MediaEntry> entries = n   ew A   rrayList<>();
        UiM e    ssage msg      ;

           int cu       rrent = 0    ;
        int inde                  x = 0;
        for (int i =  0; i         < messages   .size()     ; i+         +) {
                          msg = mes  sag    es.get(i)        ;
                  if (   msg.message.c    ontent.getMessage    ContentType( )  != Mess                                  ageConte ntTyp   e.ContentType    _Im        ag  e
                      && msg.message  .  content.getMessageCo    nten       tType() != Messag eContentTyp   e.Co ntentType_Video) {
                               continu      e; 
            }
                                  MediaEn    tr      y       en   t           ry   = new Medi  aEn        try(msg. message);
            en             tries.add(entr    y) ; 

                 if (m    essage              .message.messageId    == m                  s     g.messag    e   .messageId) {
                               current     =  index;
             }
                index  ++;
          }
                          if (entries.is   Emp  ty()) {
                        r e    turn;
           }
              MMPreviewAct           ivity.prev    iewMedia(fragm e   nt.getContext()   , ent      ries    , c               urrent, m         essages.get(0).m  essage.convers          ation.ty  pe == Conversati     on.Conver    sati o    nType           .S ec   retChat) ;
    }

      /**
      * å     ¾ç åå°è§é¢ å           è½½çå°æ  ¹
     * ç­ç¥æ¯åå     è½½ç¼©     ç¥å   ¾ï¼å¨  å è½½åå¾
     *
        * @pa   ram thu mbn     ail
             *      @param image   Pat h
        * @param   imag eView
     */
    protect         e  d void loadMedia(Bitma p thumbnail, St  ring          imagePath, ImageView  imageView) {
        R   equestBuilder<Drawabl  e> thumbnail     Re                       que         st = null;
          if   (th   umbn   ail != null) {
                              thumbnailReque  st = Glid   e
                .with(fragment)
                      .   lo ad(thumbnail);
        } else {
                                   thumbna              ilReque  st   = Glide   
                     .with(fragme        nt)
                           .lo a       d(R.drawable.image_      chat_placeholder);
             }
        Reques   tBuilder   <Drawable> request      = Glide.    with(fragment)
            .load(ima  gePath)
            .t humbnail(th  umbnailR         equest)
            .ap pl  y   (       placehold     erOpti   ons);
           if (message.mess  ag e.conversati       o  n.type == Conversation.Co     nversationType     .SecretChat) {
                request     = request.diskCac   heStrategy(D   i     skCac  heStrategy.NONE);      
        }
        request.    into(imageView);
    }

       @MessageC  ontextMenuItem(tag =    MessageC   ontextMenuI  temTa  gs.TAG_CA      NCEL_SEND, confir m = false, pr         iority = 13)
    public void cancelSend(Vi       ew itemView, UiMessage message) {
          boolean  canceled = ChatManager.Instance().c        an  celSendingMessage  (messag   e.message    .mess ageId);
         if (!canceled) { 
               Toa   st.makeText(f     ragment.getConte      xt(), "åæ¶å¤±è´¥",     Toa  st.LENGT   H_SHORT).show();
        }
    }


          @Override
    public String contextMenuTitle(Context context, String tag) {
        if (MessageContextMenuItemTags.TAG_CANC   EL_S   END.equ        als(tag)) {
            return " åæ¶åé";
             }
        return super.contextMenuTitle(context, tag);
    }

    @Override
       public boolean contextMenuItemFilter(UiMe    ssage uiMessage, St      ring  tag) {
        if (MessageContextMenuItemTags.TAG_CANCEL_SEND.equals(tag)) {
            return !(uiMessage.message.content instanc  eof        MediaMessageContent && MessageStatus.Sending == uiMessage.message.status);
        } else {
            return super.contextMenuItemFilter(uiMessage, tag);
        }
    }
}
