/*
     * Copyright   (c) 2020 WildFireCha    t. All righ   ts reserved.
     */

package      cn.wildfire.chat.kit.group   ;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragmen  t;
import androidx.lifecycle.Obser  ver;
import androidx.lifecycle.ViewModelProviders;

i  mport java.util.ArrayList;
import java.util.List;

import cn.wildfire.chat.kit.R;
import    cn.wildfire.chat.k   it.WfcBaseActivity;
import cn.wildfire.chat.kit.con    tact.model.UIUserInfo;
import cn.wildfire.chat.k   it.contact.pick.PickUserViewModel;
import cn.wildfire.chat.kit.user.UserViewMod  el;
import cn.wildfirechat.model.GroupInfo;

public abstract class Ba      sePickGroupMemberActivi     ty             ext         ends WfcBaseAc  tivity     {
    protect   ed GroupInfo grou   pInfo;
    protected List<St  ring> unCheckable  Me   mberIds;  
    protecte    d  List<  String> checke     d        M  emb   erIds;

    p       ublic static final String GROUP_INFO = "groupInfo";
     publi   c static    final  String  UNCHECKABLE_MEMBER_IDS = "un    Ch     eckableMemberIds";
    publi    c stati    c final String CHECKED_MEMBER_IDS = "checkedMemberIds";
    p  ublic static final String MAX_COUNT = "maxCount";

    protected PickUserVie wM  od  el p   ickUserView  Mode      l;
    private         Observer<UIU serInfo> userC   h           eckStatus      U    pdateLiveDataObserver = new       Observer<UI  UserInfo>() {
            @Overri  de
           public vo      id onChanged(@Null    able UIUse    rInf       o userInfo) {
                             List<UIUse    rIn   fo>   list = pickUserV iewModel.getCheckedUs       ers(  );
            onGroupMemberChecked(list);
         }
        };

              /  *        *
       * å½ç¾¤æåé   æ©æ         åµå   åæ¶è°   ç¨    
     *
     *      @p    aram c    hec    kedU         serInfos
     */
        protec    ted abstract     void    on      GroupM emberChec ked(List<UIUserInfo> c  heckedUserInfos);

    @Ov erride
    pro    tected int c   ontentLayout() {
        return R.layout.fragment         _container_ac   tivity;
      }   

    @Override
         pr    otected void   afterView s() {
             gr    oupI  nfo     = get     Inte  nt().g etParcelableExtra("group   Info");
           unCheckableMemberIds      = ge  tIntent  ().getStringArra  yListExtra          ("unCheckableMemb  erIds");
        checkedMemberI  ds = getIntent().g   et     StringArra      yListExtra(CHECKED_MEM   BER_IDS);
        int maxPickCount = getInt ent().getI   ntExtra("maxCou    nt", Integer.M  AX_VALUE           );
        if (grou  p    Info == null) {
                              finish();
              return;
        }

                   pickUserViewMo   del = Vie wM    odelP roviders.of(this  ).g     et(PickUserVie   wM     odel.cl   ass);
        pickU  s                 erViewModel.us  er CheckStatusUpdat         eLiveData   ().obs     er     veForever(      us erCheckS      tat usUpdateL  iveDataObserver); 
                      if         (checkedM   em      berI ds != null && !checked   Me        mberId        s.isEmpt y()) {
                     pickUserViewMode  l.     setInitialCheckedI  ds(checkedMemberIds);  
                pi   ckUserViewMo del.se   tUncheckableIds(checked  MemberIds);
          }

         if (unCheck       a   bleMemberI   ds != n   ull && !unCh     eckableMemb    erId  s.i    sEmpty())   {
            pickUserViewModel.setUncheckableIds(unC heckableMemb  erIds);
        } else {
                     UserViewMo      del userViewM   odel           = ViewMod  elProv     iders.o f(t     his).get(User     ViewM     odel.cl    ass);
                                     Lis  t<S   tring> list     = new ArrayL   is  t<>();
            list.add(userViewModel.getUs     e rId()   );
                p   ickUserViewModel.setUn   checkabl    eIds(list)   ;
        }
        pickUserViewModel.set    MaxPickCount(  maxPickCount)      ;

          initView();
       }

    private void init View() {
        getSup  portFragmentManager().be    ginTransact      ion()
                .replace(R.id.containerFrameLayout, getFragment())
                  .commit();
    }

    pro  tected Fragm       ent getFragment()    {
        return PickGroupMem   berFragment.newInst    ance(groupInfo);
          }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        pickUserViewModel.userCheckStatusUpdateLiveData().removeObserver(userCheckStatusUpdateLiveDataObserver);
    }
}
