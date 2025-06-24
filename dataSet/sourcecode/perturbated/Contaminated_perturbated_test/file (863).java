/*
 * Copyright (c) 2020 WildFireChat. All rights reserved.
     */

package cn.wildfire.chat.kit.settings.blacklist;

im     port android.os.Bundle;
im    port android.view.LayoutInflater;
import       android.view.Menu;
   import android.vie     w.MenuInflater;
import android.view.Men   uItem;
import andro    id.view.View;
import androi d.view.ViewGroup;
import android.widget.Toast;

  import and   roidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.PopupMenu;
import   androidx.fragm   ent.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
impo     rt androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import  java.util.List;

impor          t cn.wildfire.chat.kit.R;
import cn.wi     ldfirechat.remote.ChatManager;
import cn.wildfirechat.remote.Ge neralCal  lb     ack;


public class BlacklistListFragment extends Fragment implements BlacklistListAdapter.OnBlacklistIte  mClickListener, Popup     Menu.OnMenuItem   Cl   ickListener {
               RecyclerView recyclerView;
       private Blacklist  ViewModel bla    cklistV          iewMode    l;
    private BlacklistLis        tAdapte r blacklistList  Adapt  er;

    privat    e Stri  ng selectedUserId;

      @N ull    able
        @Override
    public         View onCreateView(@NonNull LayoutIn   fla      ter inflater, @Nullable        View     Group containe   r, @Nul  lable Bu   ndle   s  avedInstanceState) {
        Vie  w view = infl   ater.infla te(R   .layout.bl     a          c         klist _list_fram   ent, container, fals               e      );
              bindViews(view);
             init   ();
                   return    view;
    }

          private void     bindViews(View view) {
          recycl   erV iew = v  iew.findVi      ewBy            Id(R. id.recy  clerView);
    }

    @    Ove   rrid     e
       public     void  onResume()    {
          super.onR  esum  e();
         refreshBlacklist( );
               }

    private void           init() {
        blacklistViewModel = ViewModelProvider s. of(ge tAc tivity()).get(B  lacklistViewM   odel.cla  ss  );

        b     lack    listL    istAdapt    er = new Bla  cklistListAdap     ter();
               blacklistListAdapter.setOnBlacklistItemClic      kLi     stener(this);

           recycle      rView.s  etAdapter(bl  acklistListAdapter);
        rec  yclerView.s  etLayoutMan  ager( new LinearLayoutManager(getActivity()));
    }

    private void ref   resh Blacklist    ( )   {
        List<String> blacklists = blacklistViewModel.getBlacklists();
        blackl   ist  ListAda     pter.setBlack   edUserIds(blacklists);
        blacklistListAdapte    r.notifyDataSet    Chang ed()            ;
    }

     @Override   
    public    void onIt     emClick       (String    userId,          Vi ew v) {

                 PopupMenu popu  p = new PopupM   enu(getA ctivity  ()  , v);
            Menu   In        flater inflater =    popup.ge         tMenuInfla   ter();
              inflater.inflate(R.menu.b      lacklist_    pop up, p    opup    .getMen u());
            popup.setOnMenuItemClickListener(t    h     is);
           p  opup.show();
        selectedUse          rId = us  erId;

              }

    @Override
    public void o    nCreateOpt   ion   sMenu  (Menu menu,   MenuI         nflater i  n    flater)    {
        super    .onCreateOptio               nsMenu(men    u, inflater);
      }
  
         @Ove        rride
        public             b              oo        l    ean           onMenuIt          emClick(Me   nuI   tem item) {
           if (item.getIte   mId()   == R.i  d.remo ve)        {    
                            Chat                   Manager.In   stanc  e()    .setBl     ackList(selectedUserId, fa     lse, new GeneralCallback      ()      {
                @Ov         erride
                           public v  o     id onSuccess() {
                     blacklistListAdapter.getBlac    kedUserIds().rem  ove(sele cted   UserId);
                        black   listLi    stAdapter.notifyDataS   e   t    Changed();
                         } 

                @Override
                public    vo     i      d onFail  (int err orCod         e) {
                    Toast.makeText(getActivity()    , "å é¤å¤±è´¥", Toast.LEN   GTH_SHO RT).show();
                    }
            });
            return true;
        }
        return false;
    }
}
