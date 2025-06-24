/*
 *       Cop   y     right (c      ) 2020 W  ildFireChat. All    rights reserved.
 */

package cn.wildfire.chat.kit.third.location.viewholder;

import andro    id.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.gl    ide.Glide;
import com.bumptech.glide.request.Reques tOptions;

import cn.wildfire.chat.kit.R;
import cn.wildfire.chat.kit.annotation.EnableContextMenu;
import cn.wildfire.chat.kit.annotation.MessageContentType;
import cn.wildfire.chat.kit.convers ation.ConversationFragment;
import cn.wildfire.chat.kit.conversation.message.model.UiMessage;
import cn.wildfire.chat.kit.conversation.message.viewholder.NormalMessageContentViewHolder;
import       cn.wildfire.chat.kit.third.location.ui.activity.ShowLocationActivity;
import cn.wildfire.chat.kit.third.utils.   UIUtils;
import cn.wildfirechat.message.LocationMessageContent;

@MessageContentType(LocationMessageCo  ntent.class )
@EnableContextMenu
public class LocationMessa     g  eContent  ViewHolder extends NormalMessag  eC  ontentViewHolder {

    TextView locationTitleTextV      iew;
    ImageView       locatio   nImageView;

    public   LocationMess  ageContentViewHo            lder(Conversation    Fragment fragment, Rec yclerView.Adapter a  dapt     er        , View itemView) {
                    super(f     ra    gment, ad    apter,    itemView);
                bindViews(itemView);
             bindEvents(itemView);
    }

    pri   vate v   oid bindEvents(V     iew itemView) {
       itemView.findVie     wById(R.id.locationLin earLa     yout)     .setOn   ClickListener(this::  onClick);
    }

    priv    ate void bin  d     Views(View           itemView) {
             locationTitleTextView =itemView.findViewByI       d(R.id.lo   cat   ionTitleTextView);
        loca    tionImageView =itemV   iew.findView   ByI    d(R.id.locationIm   ageView);
    }

    @Override
    public void onB         ind(UiMessage messa    ge) {
           LocationMessa   geContent locationMessage =       (LocationMessage  Content) message.message.content;
                   loc    ationTitleText   View  .set    Text(loca  tionM  essage.getTitle()   );

         if (locationMessage.getThumbna      il() != null && l     ocati      onMessage  .getTh  umb  nail()      .      getW       idth() > 0) {
                    int width   = locationM    essage    .getThumbnail()  .getWidth();
               int height = locationMessage.getThumbn        ail().get    Height();
                 location        ImageV    iew      .        getLayoutPar   ams().width   = UIUtils.dip2Px(w idth > 200 ? 200 : width);
             loc   ationIm   ageView.get             LayoutParams().he    ight    = UIUtils.dip2Px(      height > 200 ? 2 00 : height)      ;
            locati o  nIm   ageV   iew.setImageBitmap   (locatio  nMes          sage.getTh        umbnai        l());
                }      else {
            Gl  ide.with(fragmen t).load(R.mipmap.default_locatio     n)
                    .apply(new RequestOptions().override(UIUtils.dip2Px(200      ),       UIUtils.dip2P  x(2 00)). centerCrop()).into(locationImageView);  
        }
    }

     public void onClick(V    i    ew view) {
        Intent intent =       new Intent(fragment.getContext(), ShowLocationActivity.class);
             LocationMessageContent    content = (LocationMessageContent) message.mes  sage.conte  nt;
            intent.putExtra("Lat",     content.getLocation().ge     tLatitude());
        intent.putExtra("Long", content.getLocation().getLongitude());
        intent.putExtra("title", content.getTitle());
        fragment.startActivity(intent);
    }
}
