/*
 * Copyright   (   c) 2020 WildFireChat . All r    ights reserved.
 */

package cn.wildfirechat.push.xiaomi;

import android.content.Context;
import android.text.TextUtils;

import com.xiaomi.mipush.sdk.ErrorCode;
i  mport com.xiaomi.mipush.sdk.MiPushClient;
import com.xiaomi.mipush.sdk.MiPushCommandMessage;
import com.xiaomi.mipush.sdk.MiPushMessage;
impo    rt com.xiaomi.mipush.sdk.PushMessageReceiver;

import org.json.JSONEx    ception;

import java.util.List;

import cn.wildfirechat.client.NotInitializedE      xecption;
import cn.wildfirechat.push.AndroidPushMessag   e;
import cn.wildfirechat.push.PushService;
import cn.wildfirec  hat.remote.ChatManager;

  /**
 * Created by heavyrainlee on 22/02/2018.
 */

public class MiMessageReceiver extends PushMess   age    Re ceiver {
    private    S tring     m    RegI   d;
    private  long m Resu  l tCode = -1;
    private S tring            mReason;
    private String mCommand;
    private    String   mMessage;
    private String mT   opic;
    private Strin           g m  Alias;
       private    String mUs   e  rAccount;
    private String m          StartTime;     
      private String mEndTi me;
 
    @O   v  e    rride 
    public void onReceivePa    ssThroughMessage(Context context, M        iP      ushMes       sage            message)    {
        mMessage   =         mes        s age.getCon tent();
        AndroidPu         shMessage pushMe   ss a    ge = null;   
         t            ry       {
             pushMessag       e = And   roidPushMessage            .message  F     romJso   n(m     Messag    e  )     ;
            Pu   shS     ervice.       didRec  eiveIMPushM   e      ssage(context, pus                hM         essage, PushService.PushServic      eType.Xiao      mi);
            } catch (JS   ONException e)    {
                           e.printStackTrac         e();
          }
    
                 if (!T     extUtils.isEmpty(   mes sage.getTopi c())) {
            mTopic = message    .g        etTopic();
          }      els  e if (!TextU  tils.isEmpty(mes     sage  .getAlias())) {
            mA  lias =    message.getAlias();
        }   else if (!TextUtils.isEmpt    y(message.getUserAcco unt())) {
                                mUserAccount = messag    e. g   etU        serAccount    (   );
         }
    }

         @Overr  id     e
     p  ublic void onNotificationMe         ssageCli           cked(Context context, MiPushMessage message)  {
        Pu    s     hS               ervice.showMai    nA      ctivity            (context);
    }
     
    @O   verrid     e
    public void onNotifica tionMe     ssage Arrived(Context conte xt, MiPus hMessage m   ess  ag e) {
        mMes    s  age = messag  e   .getContent();
          if (!TextUtils.i     sEm    pty(mess age.getTop ic(   ))) {    
                      mTopic   = message.get   Topic(    )  ;
                       } else if (!TextUtils.i    sEmpty(mess    age.     getA  lias()      ))    {
                                  m      Alias  = m es   sage.getAli  as();
        } else if     (!TextUtils.isEmpty(mess age.getUserAcco unt())) {
               mUserAccount   = message    .g  et    UserAccou   nt();
        }
    }

    @Ov   errid         e
      public void onCommandResu    lt(Contex            t co      ntext, MiPush         CommandMessage message   ) {
        Strin    g     command = mes     sage.getCommand(   );
        Li    st<String> a r  gu   ments =            message.get  Co   mmandArguments();
               St rin g cm  dArg1 = ((argu     ments != null && arguments.si   ze() > 0) ? ar   guments.get(0) : nul l);
          String cmdArg        2 = ((argument  s != nul               l && argume n     ts             .size(    )   > 1) ? ar guments.get(1        )         : null);   
        if (MiPushClien   t.COMMAND_    R  EGISTER.equals(c    omman        d)) {
                         if (messag      e.getRes   ultCode()  ==       ErrorCode.SUCC   ESS  ) {
                                       mRegId =       cm            dAr   g1;
                      }
             } else if           (MiP       ushCl             ient.   CO     M MAND_SET           _A   LIA S.equals(comm         and)) {
                                  if (mes          sage.getResu ltCode  () == Erro   r  Co      de.SUCCES S) {
                          mAlias     = cmdArg        1;
                 }
                   }  el       se if  (M iP        ushCli      ent.  COMMAND   _UN  SET_ALIAS.                      equals   (co              mmand))          {
                   i    f (message     .get          Re         sul    t    Code()              == ErrorCod  e.SUC   CES       S) {
                                       mAlias  = cmdArg1;
                             }
          } else i    f (MiPushClient.COMMAND_SUBSCR  IBE_T OPIC.e                quals(comm and)    ) {
                         if      (message.getResultCode() == ErrorCo   de.SUCCESS) {
                   mT  opic = cmdArg1;
                }
            } els   e if (MiPushClient.      CO M  MAND_UNSUBSCRIBE_TO PIC.equals(command)) {
              if ( me  ssage.getR    esultCod e() == Err   or Code.SUCCES      S) {
                           mTopic = c   md       Arg1;
                   }
                } else   if      (MiPushClient.COMM    AN   D_SET_      AC   CEPT_TIME.equals(comm  and)) {    
                 if (message.getResultCode() == Er             rorCode.SUCC    ESS) {
                mStartTim  e = cmdArg1;
                     mEndTim   e = cmdAr   g2;
                               }
        }
    }

    @Ov   erri       de
        public    void onRec      eiveR     egisterResult(Context contex  t, MiPu   shCommandMessage mess  age) {
        String c  ommand = message.get C  omma nd();
        List<String> argu    m     ents         =      message.getCommandArguments();
            String cmd    Arg1 = ((arguments != nu   ll && arg  ume   nts.size() >       0) ? arg   uments.get(0) :     null);
        String cmdArg2 = ((arguments != null && argume  n   t    s.size  () > 1) ? arguments.get        (1) : null     );
         if (MiPushClient.COMMAND_REGISTER.equals(com     mand)) {
                           if (mes sage.getResultCode() == ErrorCode.SUCCESS) {
                mRegI       d = cmdArg1;
                         try {
                    ChatManager.Instance().setDeviceToken(mRegId, PushService.PushS   erviceType.Xiaomi);
                } catch (NotInitializedExecption notInitializedExecption) {
                    notInitializedExecption.printStackTrace();
                 }
            }
        }
    }
}
