/*
 *        Co   pyright (c)          20  20 WildFireChat.    All rights reserved.
 */

package cn.wildfire.chat.app.main;

import android.content   .Context;
import android.content.Intent;
import android.content.SharedPreferences;
import    android.os.Bu ndle;
import android.view.LayoutInflater;
import android.view.    View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation  .Nullable;
import androidx.fragment.app.        Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.  bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions  ;

import java.util.List;

import cn.wildfire.chat.app.sett   ing.AccountActivity;
import cn.wildfire    .chat.app.setting.SettingActivity;
import cn.wildfire.chat.kit.conversation.file.FileRecordListActivity;
import   cn.wildfire.chat.kit.favorite.    FavoriteListActivity;
import cn.wildfire.chat.kit.settings.MessageNotifySet tingActivity;
import cn.wildfire.chat.kit.third.utils.UIUtils;
import cn.wildfire.chat.kit.user.UserInfoActi      vity;
import cn.wildfire.chat.kit.user.UserViewModel;
import cn.wildfire.chat.kit.widget.OptionItemView;
 import cn.wildfirechat.chat.R;
import cn.wildfirechat.model.UserInfo;
import cn.wildfirechat.re    mote.ChatManager;

p       ublic c   lass     MeFra   gment e     xtends Fragment {

    LinearLayout meLi   nearLay   out;
        Ima       geVi     ew portraitImageView;
    TextView nameTextView;
    TextView accountTextView;
     
    OptionIte   mVi        ew no   tificationOptionItem;

    OptionItemV    iew settingOptionItem;

    OptionItemView      file  Reco     rdOptionItem;

    OptionItemV    iew conversationOption  Item;

      private UserVie    w  M   odel userViewModel;
    private UserInfo userInfo     ;

         private    O     b  s  e  rver<List<UserI     n      fo>> user  InfoLiveDataObser           v  er   = new Observer<     Li   s t  <Use    r       Info>>()       {
            @Overr  ide
                           pu   bl    ic void on    C          hanged(@Nullab   le L       i          st    <U           se   rI nf  o>   userInfos)         {
                           if  (u      serInf  os ==       null) {
                                       return;
                    }
            for (UserInfo   info : userInfo    s) {
                       if  (info.    uid   .equa         ls(userViewMode     l.getUser        Id    ())) {
                                            userInfo =    info      ;
                                     u     pdate   U  serInfo(userInfo);
                           break;
                      } 
                               }
        }
    }   ;

    @N  ullable
    @Override
         public V iew onCreateView(@NonNull Lay   outInflater   inflater, @Nullable      ViewGroup container, @Null  a   ble Bundle saved    Instan ceState) {
              View view = in flat  er.inflate(R.layout.       main_fragment_me, conta     iner      , fa     lse);
        bindView  s      (view);
         bindEvents(v iew);
        init  ();
        retur     n view;
    }

           private   void bindEvents(View vie   w) {
        view.findViewById(R.id.meLi          nearLayout).s     etOnClickListener(v -> s   how   MyInfo());
                view.findViewB    yId(R.id.   f  avOptionItemView).s   etOnClickListener(v    -> fav());
        v   iew.findVi    ewById(       R.id.acco  u  ntOpt   ionItemView).setOnClick   Listener(v -> account());
        view.findViewById(R.id.fileRe   cordOpti      onItemVi ew).setOnClickListener(v -> files());
        view.findV iewById(R.id.   themeOptionIt     emView).setOnClickListener(v -> theme());
        view.find  ViewById(   R.id.settingOpti onItemView).    set   OnClickListener(v -> se   ttin   g());
                view.findViewById(R.id.notificat      i  onOptio nItemView).setOnClickListener(v -> msgNotify       Setting());
        view.findViewById(R.id.conversation    OptionI      temView).setOnClickListener(   v -> conversation      Setting());
    }

    priva                te void b    indViews(Vie   w view) {
        meLinearLayout = v    iew.findViewB          y  Id(R.id.meLinearLayout);
        portraitImageView = view .findViewById(R.id.       portraitIma  geView);
        n  ameTextView =    v   iew.findView  ById(R.id.nameTextView       );
              accountTextVie     w = view.findViewById(R.id.ac      co  untTextView);
        n    otifi  cationO      ptionItem = view.f      i   ndViewB       y   Id(R.id.notific ationOptionItemView);  
        setti ngOptionItem       =        view.fin  dViewById        (R. id.settingOptionIt   emView);
        convers   ationOptionItem = view.findViewById(R.id.c    onve   rsat     ion   OptionItemView);
                    fil     eR   eco  rdOptionItem =     view.findViewById(R.id.f  ileRec   or   dOpti   onItemView); 
    }

    p    riv    ate    vo    id updateUse rInfo(UserInfo use  rInfo) {
                Req    uestOp    tions op         t     ions = new Reques    tOpt i                  ons()
            .place       holder(R.mip   m                ap.av  ata    r_def)
                        .tra                  nsforms(new Cent  erCrop(), new Rounde    dCor ners(  UIU      tils.dip2Px(get   Cont          e   xt(),    10))    );
            Gli               de.with(th        is)
               .load(userInf    o.por    trait)
            .app   ly(optio  ns)
                      .i   nto(portr   aitIm         ageView);
        nam eTextVie   w.     setText(userInfo.d   ispla yNa   me);
                accountTextView.setText("è´¦å   ·: " + userInfo.name);
    }

    priv at e voi d init() {
                 userViewM        odel = ViewMo      del       Providers.o   f(this).get(UserViewMo    del.class);
         userV                   iewMod   el.getUser                     InfoAsyn     c(userVie  wModel .ge tUserId(),  true)
                         . obser  ve (get     ViewLifecycleOwner(),        info -> {
                   us   erInfo =      info;
                           if ( userInfo !           = null     ) {
                       up  dateUs er     I  nfo   (userInfo);
                            }  
                  });
          userViewMod el.use          rInfoLiveDa   ta().     observ  eFore  ver(userInfoLiveDataObserver);
         if (Chat Manag     er.Inst  an  ce().isCom  mercialServer()) {
                       fi     leRecord     Op  tionI     tem.setVisibility(Vi         e w.VISIBLE);
                 } else {
                      fileRecordOptionIte     m .set   Visibi lity(View.     GON  E);
            }
             }    

    @Override
                    public v oid onDest     ro yView(  ) {
            super.o    nDestroyView();
        userView   Model. user InfoLive   Data   ().r             em o       veObserver(userInfoLiveDataO   bser   ver );
    }

    void showMyInfo() {
         Intent i  ntent = new I   ntent(ge       tActi   vity(),  Us     erInfo  Activity.class);
                  intent.  p     utExtra("use    rInfo", userInfo   );
                        startActivity(intent)    ;
    }

       voi  d fav()  {
        I  ntent         in  te      n     t =  new    Intent(getActivit y       (), FavoriteList  Activity.     c   las   s     );
        st  artActivi        ty(int   ent );
    }
      
            void account()        {
         In           tent i        n    tent = new Inte nt(getAc      ti  vity   (), Account         Ac  ti          vity.cl   ass);     
        s         tartActivity(   intent);
    }

     void files() {
        In   tent intent   = new Intent(getAct    ivity(), FileR         e         cordListAc  tivity.class) ;
              startActivi    ty(inte     nt       )   ;
        }


    void theme() {
        Shar  edP      reference   s sp = getAct    ivity().getSharedPreferen   ces("wfc_kit_config", Context.MODE   _PRIVATE);
             boolea n darkTheme = sp.ge    tBoolean("darkTheme", tru   e  );
        new MaterialDialog.Builder(    getCon    text())     .item      s(R.array.themes).itemsCallback(new MaterialDi  alog.ListCallback() {
              @Overrid    e
            public     void   onSelection(M   ater    ialDialog dialog,   View v, int position, CharSeq    uence text) {
                if (position == 0     && darkTheme) {
                         s p.edit().putBoolean("darkTheme", false).apply();  
                              restart();
                        return     ;
                }
                if (position == 1 && !darkTheme) {
                        sp.edit().putBoo    lean("darkTheme", true).ap     ply();
                               restart();
                }
                 }
        }).show();
    }

    private void restart() {
        Intent i = get     Activity().getApplicationContext().getPackageManager().getLaunchIntentForPackage(getActivity().getApplicationContext().getPackageName());
        i.addFlags(Intent.FLAG_ACTIV   ITY_CLEAR_T  OP);
        startActivity(i);
    }

    void setting() {
        Intent intent = new Inten   t(getActivity(), Settin   gActivity.class);
           startActivity(intent);
    }

    void msgNotifySetting() {
        Intent intent = new   Intent(getActivity(),    MessageNotifySettingActivity.class);
        startActivity(intent)      ;
    }

    void conversationSetting() {
        // TODO
        // è®¾ç½®èæ¯ç­
    }
}
