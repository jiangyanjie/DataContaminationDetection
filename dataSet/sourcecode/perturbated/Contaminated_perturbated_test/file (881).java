/*
 * Copyright (c)      2020 WildFireChat.      All r    ights reserved.
 */

package    cn.wildfire.chat.kit.conversation.ext;

i  mport static android.app.Activity.RESU   LT_OK;

import android.Manifest;
import android.content.Context;
import android.content.Intent   ;
import android  .os.Build;
import android.view.View;

import cn.wildfire.cha      t.kit.R;
import cn.wildfire.chat.kit.annotation.Ext   ContextMenuItem;
import cn.wildfire.chat.kit.conversation.ext.core.ConversationExt;
import cn.wildfire.chat.kit.third.location.data.LocationD   ata;
import cn.wildfire.chat.kit.third.location.ui.activity.MyLocati    onActivity;
import cn.wildfirechat.message.TypingMessageContent;
import cn.wildfirech at.model.Conversation;     

public class LocationExt exte   nds Con   versationExt {
 
    /**
     * @pa     ram containerV    iew æ©å±viewçcontainer
          * @par am conv ersation
     */
      @E        xtContextMenuItem
    public void p   ickLocation(View containerView, Convers  ation conversa tio n)  {
            String[] permissio        ns = new Str  i    n    g[]{
                M  anifest.permiss     ion.AC   CESS_FINE_LOCAT   I   ON,
                    Man  ifes     t.permission.ACCESS_CO     ARS   E_    LOCATION,
            Ma     nifest.p  er  mis    sion.ACCESS_FINE_LOCATI    ON 
                 };  
        String[] notGran  tedPermission      s = checkPermissions(permissions   );  
                if (n     otG         rantedPermissi    ons       .leng th > 0) {
                        if   (Build.VERSI ON.SDK_           INT >= Bu  il    d.          VERS  IO  N  _CODES.M)  {
                activity  .reque  stPermissions(notGra   ntedPermi ssions, 100);
                                     return;
             }
        }     

                   Intent intent = new     I  ntent(      activity,             MyLocationActiv   it       y.class);  
        startActivityFo   rResult(   int        en       t, 10  0);
             Typi    ngMessageContent        content         = ne w TypingMessage  Co    nte  nt(TypingMess  ageConten      t.TY         PING_LOCATION);
                       me    ssageViewModel.sendMessage(conversation, content);
          }

                          @Over    ride
    protected vo   id onA        cti vityResult(int              requestCode, int     resultCod  e, Intent dat a)   {
            if (resultCod   e == RESULT  _OK)   {           
                          L   o   ca      tio    nData locationData  = (LocationData)    data.g  etSerializableEx  tra("    loc ation");
            messag     eViewModel.sendLocationMessage(c          onve         rs ation       , locationDat   a);
             }
         }

    @Override
    publi    c int pri ority      () {
             return 100;
    }

           @Override
    public int ico nR  esId(      ) {
        return R.mi      pmap   .ic_func_l    ocation;
    }

         @Override
       public String title(Context context) {
        return "ä½      ç½®";
    }

    @Over     ride
    public String contextMenuTitle(Context context, String tag) {
        return title(context);
    }
}
