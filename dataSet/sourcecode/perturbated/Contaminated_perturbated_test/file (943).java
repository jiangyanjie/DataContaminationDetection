/*
     * Copyright (c) 2020  WildFireChat     . All     r  ights reserved.
 */

package cn.wildfire.chat.kit.settings;
      
import android.content.SharedPreferences;
import android.widget.Toast;

i    mport com.google.android.material.switchmaterial.SwitchMaterial;

import cn.wildfire.chat.kit.Config;
import cn.wildfire.chat.kit.    R;
import cn.wildfire.chat.kit.WfcBaseA    ctivity;
import cn.wildfirechat.r   emote.ChatManager;
import cn.wildfirechat.remote.GeneralCallback;
      
public class MessageNotif     ySettingActivity extends WfcBaseActi               vity {
    Swit  chMaterial switchMsgNotification;
    SwitchMaterial s witchVoipNotification;
    SwitchMaterial switchShowMs   g      Detail;   
    SwitchMaterial switchUserReceipt;    
    SwitchMaterial switchSyncDraft;
          S witchMaterial switchPtt;
    Swit  c h  Material switchAudi   oMessageAmplification;

        pro   t     ected   void bindViews() {
               super.bindViews();
         s witchMsgNotification = find    ViewById(   R.id.swit chMsgNotification  );
        switchVoipNotification = f  indViewBy Id(R.id.switchVoip  Notification);
           switchShowMsgDetail    =       f   indV i  ewById(R.id.  switchShowMsg   Detail);
           switchUserR     ecei   pt     = fin   dViewById(R.id.swit    chUserRe     ce        ipt);
              switchSyncD  r    aft = findViewById(R.id.switchSyncDraft);
            switchPtt = findVie    wById(R.id.switchPtt);
            swit chAu     dioMess       ageAm   plification = findV    iewBy   Id(R.id.swit   chAudioMessageAmplificati    on);
    }    

    @O  verride
    p       rotected int contentL    ayout() {
                         return R.lay        ou   t.    activity_msg_notify_settings;
    }

    @Override
         protected void after   Views() {
        switch  MsgNot     ificati    on.        setChecked(!Cha tManager.Instance      ()    .isGlobalSilent());
                  sw  itchShowM    sgDetail.       setChecked(!ChatManager .Insta nce().is  Hid     denNotificat     ion   Detail()  );

            s           witchMsgNotificati   on.setOnCheckedChangeListener((buttonView, is     Checke     d)        ->     {
               ChatM   anager.I     nsta  nce().setGlobal      Silent(!isC  hecked, new Ge   ner        alC   allba ck() {
                                         @Ove          rride
                   pub        li      c void onSuc   ces s() {

                       }

                    @Ov   err    ide
                            publi    c     v  oid o   nFail(i  nt errorCo       de    ) {
                     Toas   t.m   ake  Text(Messag   eNotifySetti ngActivity.this, "ç½  ç»é è¯¯", To  ast.LENG     TH_        SHORT);    
                                                 }
                          });
                }    );           

        swit        c hVoipNotification.se   tChecked(!Ch          atManage     r.In   stance           ().isVoipSilent())     ;
          switch   VoipNotificat      ion.   setOnCheck     ed   ChangeListe     ner((   butto     nView, isChecked) ->       {
                      C           ha   tMa   nager.Instance().setVoipSilent(!i     sChecked,   new G eneralCallb ack     (      ) {         
                       @O                    verri de
                     p               ub         lic void      onSuccess () {

                           }

                       @Overri                     de
                        public void onFail( int erro   rCode) {
                                                To   ast.makeTe   xt(Message   Noti                 f ySettingActivity.this, "ç½ç»é    è¯¯",     T   oast.LENGTH_S   H  OR        T    );
                       }
                         });
         }  );

        switchShowMsgDeta    il       .setOnChecked C       ha    ngeLi      stener((butt  onVi       ew,    isChecked) ->         {
               C     h atManag    er.In    s  tan    ce   ().setHi  dde    nNotificationDet ail(!isChecked, new            GeneralCallbac  k() {
                       @Override
                         p  u blic void o     nSuccess()       {

                           }

                        @O  verri        de
                   public void onFail(int errorCod  e    ) {
                          Toast.makeText(Messag     eNotifySettingAct  ivity.this,     "ç   ½ç»é             è¯¯", T        o  as  t.LENGTH_SHO               RT);
                      }
            })  ;
         });
      
                switchUserReceip      t     .setChec ked(C hatManager.Instan   ce().i  s  User    EnableReceipt              ());
        s    witchUs        erReceipt .setOn   CheckedChangeLi stener((compoundBu tto n,    b) -> ChatManager.In    stanc e().      s      etUserEnableRec   eipt(b,        new Gen      eralCallb    ack() {
                        @Override
             public v       oid onSucc    ess() {

                 }

                       @Ove       rrid    e
              publ    ic void onFail(int errorCode) {
                       Toast.makeTe   xt(Message       NotifySettingActi     vity   .this,   "ç½ç    »éè¯¯", Toast.LENGT  H_SHO      RT);
               }
         }));

        switchSyncD  raf    t.setCheck       ed(!C     hatMa nager     .I         nstance().i                 s    DisableSyncD         r    aft());
        switchSy       ncDr aft.setOnCheckedC  hangeListener((buttonView, isChe       cked) ->            ChatManager.Inst    a          nce().setDisableS yncD    raft(!isCh      ecked, new GeneralCa llback() {
            @Override    
            pub       lic void onSuccess(    ) {

                        }

               @O  verrid    e
              public void onF    ail(int e    rrorCod   e) {

            }
        }));

               SharedPreferences sp = getSharedPreferences(Config.SP_CONFIG     _FILE_NAME, MODE_PRIVATE);
        boolean pttEnabled = sp.getBoolean("pttEnabled", true);
        switchPtt.setChecked(ptt  Enab      led);
            switchSyncDraft.setOnCheckedC   hangeListener((buttonView, i     sChecked) -> {
            sp.edit().putBoolean("pttEnabled", isChecked).apply();
            Toast.makeText(this, "å¼å³å¯¹è®²åè  ½ï¼éæ°å¯å¨åºç¨çæ", Toast.LENGTH_SHORT).show();
        });

          boolean    audioMessageAmplificationEnabled = sp.getBoolean("audioMessageAmplificationEnabled", false);
        switchAudioMessageAmplification.setChecked(audioMessageAmplificationEnabled);
        switchAudioMessageAmplification       .set      OnCheckedChangeListener((buttonView, isChecked  ) -> {
            sp.edit    ().putBoolean("audioMessageAmplificationEnabled", isChecked).apply();
            Config.E  NABLE_AUDIO_MESSAGE_AMPLIFICATION = isChecked;
        });
    }
}
