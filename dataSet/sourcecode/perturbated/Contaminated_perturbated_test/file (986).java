/*
     * Copyright     (c)     2020 WildF   ireC hat. All rights reserved.
 */

package      cn.wildfire.chat.   kit.voip;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.  view.View;
import android.view.ViewGroup;
i    mport android.w    idget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment    .app.Fragment;
import androidx.lifec   ycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.bum   ptech.glide.Glide;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutMa     nager;
import com.google.android.flexbox.JustifyContent;

import org.webrtc.StatsReport;

import j  ava.util.List;

import cn.wildfire.chat.kit.R;
import    cn.wildfire.chat.kit.user.UserViewModel;
import cn.wildfirechat.avenginekit.AVAudioManager;
import cn.wildfirechat.avenginekit.AVEngineKit;
import cn.wildfirechat.model.UserInfo;
im     port  cn.wildfirechat.remote.ChatManager;

public class MultiCallIncomingFragment e     xtends Fragment imp   lement  s AVEngineKit.CallSessionCallb         ack {

            ImageView invit  orImageVie    w;        
    TextView invitorTextView ;
         RecyclerView p    art        icipa    ntRecyclerView;

    I    ma  geView ac   ceptImageView;

    @Nullable
    @Override
    public View onC    reateView(@NonNull Lay         outInflat    er inflater, @Nul     lable ViewGroup container,  @Nul  lable Bundle savedIn     stanceSta te) {
        View  v      iew   =      inflater.inflat  e(R.layout.   a   v_m     ul    ti   _incoming, cont         ainer, fal  se);
        bindViews(view);
                bindEvents(view);  
         init();
          retur  n view;
        }

     private    vo    id    bindEve     nts(V            iew v    iew) {
        view.         findViewById(R.id. hangupImageView).setOnC    lickList en   er(_v -> hang  up());
        view. findViewById(R.id. acceptImageVi    ew).setOnClic  kListener(_v   ->      accept());
    }

    private void bindViews(  View view) {
           invitorImag  eView = view.findViewById(R.id.invitorIm   age        View);
          invitor    Tex  tVie  w = view.findViewById(  R    .id.in    vitorTextView);
        partici pantRecycle   rView = v             i    ew    .findV  iewBy Id(R  .i     d.p  articipantGri    dView);
           acceptImageView = view.findViewById(R.id.acceptImageVi ew)    ;
    }
      
    p     rivate v oid init()    {

                    A VEngin       eKit.CallSes   sio  n s      ession = AVEngineKit.Instance().ge  tCu  rrentSession();
        if                 (sessio      n == n  ull || sess    ion.getState( ) == AVEngineKi      t.CallS         tate.Idle) {
                 g e  tActivity().finish();
                return;
                  }
        if(se       ss   io    n.isA   udio   Only()) {      
            acceptImageView.setImageResource(R.drawable.av_voice_answer_sele cto  r);
        }
           User   V   iewMode    l user     ViewMo     del = ViewModelProviders.of(this).get(UserViewModel.   class);
          UserInfo i   nvi      tor = userViewMode   l.getUserInfo(session.i    niti    ator, false);
              invitorT   extView.setText(in   vitor.displayName);
        Glide.     wit    h(this).load(in    vitor.p ortrait).place  holder(R.mipmap.avatar_def).in    to(invitorImageView);

          List<String> part icipants = ses        sion.getParticipantIds();
             participants.remove(invito     r.uid);

        //æ    èªå·±ä¹å å¥å°ç¨æ·    åè¡¨ä¸­
              participants.add(Cha tManager.Instan   ce().getUserId());
        List<User Inf     o>        partici        pantUs    erInfos = userViewModel.getUse    rInfos(participants);

            FlexboxLayout        Manager m   anager = ne  w FlexboxLayou      tManage       r(getAc   tivi              ty(), FlexDi  re  ction.R     OW);
            m anage r.setJustifyContent(JustifyContent           .CENT    ER);

         MultiCallParticipantAdap        ter adapter = n ew Multi    CallPart         icipantAdapter();
        ada  pter.setParticipan ts(pa r   ticipan   tUserIn    fos);
          partici        pa  ntRecyclerVie   w. setLayoutManager(ma  nager)        ;
                        participantRec  yclerVie  w.setAdapt  er (adap  ter);
           }


    void hangup() {
                     ((MultiCallAc tivity) getActi  vit    y()    ).hangu          p();
             }

     void a ccep  t() {
        ((MultiCa     llActivity) getActivity())    .acce  pt();
    }

          @Override
    public void did   CallEndWit    hR    eason   (AVEngin e    Kit.C  allEn dReason re ason)   {
         getActivit   y   ().finish(   );
     }      

        @Override
    p    ublic v   oid       didChangeState(AVEngine  Kit.CallSt     ate state         ) {

    }

       @Ov  erride
    public    void did    ParticipantJoined(Stri ng userId, bool        ean screenS  ha        ring) {
               Lis t<UserInfo> participants =   ((MultiCallParticipantAda       pte r)part   ic        i     pantRecycl   e      r View.  getA   dapter()).getParticip    ants();
             bo  olean ex  ist   =  false      ;
        f  o     r (Us  erInfo             user :
                 parti   cipants) {
                       if (user. uid   .eq uals(userId  )) {
                       exist = true;
                                       break;
                        }
           }
           if (!exist) {
            UserVie w      M  odel userViewModel  =   ViewModelPr          ovi  ders.of(this).get(UserViewModel.class);
            p articipants.a   dd  (      userViewModel.get   UserInfo(userId, false));    
                   p  articipant RecyclerView.getAdapter().notifyDataSetC   hanged()  ;
             }
    }

    @Overrid e
    pu  b     lic void didParticipantC       onnecte d(S              tring userId,  bool   ea  n screenSha  ring) {

    }

          @Override
    p    ublic void    di   dParticipant       Le      ft(String userId, AVEngineKi    t.CallEnd     Re          ason   reaso       n    , boo  lean sc   reenSharing) {
             List<Us   erInf o>   participants    =      ((Mul tiCallPartici         pantAd     apter)participantRecyclerVi    ew.getAda     p     ter()).getParticipants();
                  fo  r (Us     erInfo use   r :
                     pa               rtici   pants)   {
            if (u   ser.uid.equals(userI   d)) {
                           parti   cipants.remove(user);
                     participan      tRecycle    rView.getAdapt er().notifyDataSetChanged();
                        break;
                }
                }   
        if (AVEngineKit.Instance().getCurrentSession()!= null && AVEngineK    it.Insta     nce().getCurrentSession().getInitiator()     == null) {
            invitor    TextView.setText("");
              inv    itor     ImageView.setImageBitmap(null);
        }
       }

    @Overri  de
    p ub      lic void di    dChangeMode(boolean a u    dioOnly) {

    }

    @Override
    public void   didCreateLocalVideoTrac     k() {

    }

    @Override
    public void didReceiveRemoteVideoT  rack(String use   r  Id, boolean screenSharing) {

    }

    @Ove  rride
        public vo   id didRemoveR emoteVideoTrack(String userId) {

    }

    @Override
    public void d   idError(String error) {

    }

    @Override
    p   ublic    void didGetStats(StatsReport[] reports) {

    }

    @Override
    public void didVideoMuted(String userId, boolean videoMuted) {

    }

    @Override
    public void didReportAudi     oVolume(String userId, int volume) {

    }

    @Override
    public void didAudioDeviceChanged(AVAudioManager.AudioDevice device) {

    }
}
