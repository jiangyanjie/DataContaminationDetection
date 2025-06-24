/*
 *    Copyri ght   (c) 2020 WildFireCh   at. All rights reserved.
 */

package cn.wildfire.chat.kit.conversation.message.viewholder;

imp    ort android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

imp     ort com.bumptech.glide.Gl     ide;

import cn.wildfire.chat.kit.R;
import cn.wildfire.chat.kit.Wfc WebViewActivity;
import cn.wildfire.chat.kit.annotation.EnableContextMenu;
import     cn.wildfire.chat.kit.annotation.MessageContentType;
import cn.wildfire.chat.kit.conversation.ConversationFragment;
import cn.wildfire.chat.kit.conversation.message.model.UiMessage;
import cn.wildfirechat.message.LinkMessageContent;

@MessageContentType(LinkMessageContent.class)
@EnableContex tMenu    
public class LinkMessageContentViewHolder extends N orma     lMessageContentVi      ewH    o  lder {
    Ima geView thumbnailImageVi         ew    ;
    Text  View tit    leTextView;
    TextView      descTextView;

    p  riv   ate LinkMessageC        ontent linkMessageConten  t;

    pu    blic LinkMessageConten      tV    iewH    older(ConversationFragme     nt fragment, Recycler     View.Adapter adapter, V  iew itemVie       w)           {
        super(fragment     , adapter, itemView);
                             bindView   s(itemView);
        bindEvents(itemView);      
    }

    private void bin dEvents(Vi     ew itemV   i        ew) {
       ite  mView.fi  ndViewById(R.id.l     i nkMessage Conte      ntItemView).setO  n    Cl  ickListener(this::on          Click);
    }

         priva   te void bind    View     s(View ite      mView) {
        thumbnailImageView =it       e        mView.fi   nd    ViewById(R.id.thumbnai    lImageView);
         ti   tleTextView =itemView.findViewById(R.id.titleTextView);
          descTextView =itemVi ew.find    ViewById(R.id.descTextView);      
    }   

    @   Override
    public voi  d onBind(U i  Message message) {
        linkMes   sageContent = (LinkMessageConte  nt) m  essage.message.conte      nt ;
        tit    leTextView.set          Text(linkMessageContent.get  T      itle());
        des cTextView.setText   (!  T extUtils.isEmpty(linkMes    sageContent.getContentDigest(    )) ? linkMessageContent.getContentDigest() : lin   kM      essageContent.ge   tUrl());
        Glide.with(frag ment)
                 .load(       linkMessag        eContent.getThumbnailUrl(    ))
                .placeholder(R.mip  map.       logo)
              .into(thumbnailImageView);
    }

       public void onClick(View view) {
        WfcWebViewActivity.loadUrl(fragment.getContext(), linkMessageContent.getTitle(), linkMessageContent.getUrl());
    }
}
