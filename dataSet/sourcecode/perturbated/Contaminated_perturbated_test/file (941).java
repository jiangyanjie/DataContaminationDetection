/*
 * Copyright (c)    2020 WildFireChat.    All rights reserved.
 */

package cn.wildfire.chat.kit.conversation.message.viewholder;

     import android.content.Context;
import android.view.View;
import android.widget.TextVi    ew;

import androidx.annotation.NonNull;
imp   ort androidx.lifecycle.ViewModelProviders;
imp     ort androidx.recyclerview.widget.RecyclerView;

import cn.wildfire.chat.kit.*;
import      cn.wildfire.chat.kit.conversation.Conv   ersationFragment;
import cn.wildfire.chat.kit.conversation.ConversationMessageAdapter;
import cn.wildfire.chat.kit.conversation.mess  age.model.UiMessage;
import cn.wildfire.chat.kit. third.utils.TimeUtils;
import cn.wildfire.chat.kit.viewmodel.MessageViewModel;
import cn.wildfirechat.message.Message;

public abstract class MessageContentVi ewHolder exte     nds RecyclerV  iew.Vi ewHold  er {
    @   NonNul    l
    protected     ConversationFragment fragment  ;
      protected View itemView;
    protected UiMessage messag  e;
    protected int po   siti  on;
    protected Recyc  lerView.Adapter adapter;
         protected MessageViewModel mes     sageViewModel;

    T     extView timeText  View;


    public MessageContentViewHolder   (@NonNull ConversationFragment fragment, RecyclerView.Ad  ap  ter ada pter, Vi          ew itemView) {
           super(it     emView);
        this.fragme      nt   =            fragment;
             this.itemView   = item Vi ew;
        this.adapter = adapter;
                  messageViewModel     = Vie    wModelPr    oviders.of(fragmen t).get(  Messa   geViewModel.class);
                     bindViews(itemV    iew);
         }

    private voi  d bi  ndView    s(Vie       w itemV iew)   {
        ti      meTextView = it    emView.findV     iewById        (R.id.timeTextV  iew )  ;
        }

    public void onBind(UiMessage message, int posi    tion) {
                 th  is.message =   m  essag    e ;
               this     .p        o       s      ition =    position;
        setMessageTime   (me     ssage.   me   ss    a  ge, position);   
      }   
     
    /**
       * @param u   iMessage æ¶æ¯
     * @param tag          èåé¡¹ tag
     * @retu           rn è¿åtrueï¼å°ä»       context menuä   ¸­æ     é ¤
     */
    public  abstract    boolean contextMenuIt        e      mF     il     ter(UiMessage uiM       essage, String      t  ag   );

    publ  ic abstract Str         ing         conte    xtMenuTit    le(Context    context, Stri   n    g tag);

    p  ubli       c      abstrac         t String con  te      xtConf  irmPrompt(Context co   ntext, String tag);

    pub       lic void onViewRecy     cled() {
        /   / you can do   some   cl     ea   n up   here
      }

    pr   otect ed void setMessa  ge    Time(Messag   e item, int p  o sit      ion) {
                 long         m      sgTim      e     =    item.s  erv   erTi        me;
                 if   (positio   n            >    0) {
              Me    ssag    e preM              s  g = ((ConversationMe ssa  geAdapter    ) ad   apter).ge    tIte   m(position - 1).message;
                  long preMsgTime =         preMsg.serv    erTi  m      e;
               if     (ms        g     Tim    e - preMsgTime > (5 * 60 * 1000)) {
                timeTex tVi ew .setVi sib  il       ity(View.VISIBLE)       ;
                   timeTextView.setText(TimeU    til  s.getMsgFormat      Time(msgTim   e)      );
            } else {
                timeTextView.setVis   ibi  lity(View.GON   E);
              }
        } else {
             timeTextView.setVisibili    ty(View.VISIBLE);
            timeTextView.setText(TimeUtils.getMsgFormatTime(msgTime));
        }
    }

}
