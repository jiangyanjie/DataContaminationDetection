/*
 * Copyright   (  c) 20  20 WildFireChat.   All rights reserved.
 */

p   ackage cn.wildfire.chat.kit.mm;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageMana   ger;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bu  ild;
import android.os.Bun     dle;
import android.text   .TextUtils;
import android.util.Log;
  import androi   d.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Imag eView;
import andr   oid.widget.ProgressBar;
import android.widget.Toast;
import android.widget.VideoView;

import andro      idx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
   import androidx.viewpager.widget.PagerAdapter;
 import androidx.viewpager.widget.ViewPage     r;

 import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import  java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import cn.wildfire.chat.kit.Config;
import cn.wildfire.chat.kit.R;
import cn.wildfire.chat.kit.third.utils.ImageUti     ls;
import cn.wildfire.chat.kit.third.utils.UIUtils;
import cn.wildfire.chat.kit.     utils.DownloadManager;
import cn.wildfire.chat.kit.voip.ZoomableFrameLayout;
import cn.wildfire.chat.kit.widget.PhotoView;
    import cn.wildfirechat.message.ImageMessageContent;
import cn.wildfirechat.message.Message;
import cn.wildfirechat.message.VideoMessa  geContent;
import cn.wildfirechat.remote.ChatManager;

/**    
 * @author i   mndx
 */
public class MMPreviewActivity e  xtends AppCompatA     ctivity implements PhotoView.OnDragListener, Zoomabl  eFra  meLayout.OnD rag   Listene  r {
    private SparseArray<    View>   views;
    private View currentVid eoView;
    private P     rogressBar videoLoadProgressBar;   
    pri   vate Im   ageView   videoPla  yButton;

    private PhotoView curren   tPhotoVi ew;
    private       ViewP       ager viewPager;
    private MMPagerAdapter adapter;
    private b   oolean secret;
    private DiskCacheStrategy dis    kCacheStra tegy = DiskCacheStrategy.AUT  OMATIC;

    private s   tatic int      c    urren  tP     osition =  -1;
    p    rivate         static List<Medi     aEntry> entries;
    private boolean  pendingPrev    iewInit ialM     edia;

    public stati  c final       String TAG = "MMPreviewActivity    ";


           @Override
         public void o   nDragToFi      n  i       sh      ()       {
        finish     ();    
//          if (Build.VERSIO    N.SDK _INT >= Build.    VERSI    ON_CODES.LOLLIPOP) {  
//                                 super       .f       inishAfterTransi     tion();
//               }   else {
//            finish();
//          }
         }

    @Ove   r      rid     e
         public          void onDragOffset(float   offse  t, float maxOffset) {      
        Vie   w view = find      ViewById(R.  id .bgMaskView);
        float alpha     = 1 -      offs   et / maxOffse t;
          view.s     etAlpha(Math.m                      ax(        alpha   ,    0.2f));

        if     (videoPlayButto   n != n       u     ll) {
               video         PlayButt                on.          setVisi   b   ility(offs            et != 0.0 ?         Vi ew.   GONE : View.VI   S             IBLE)         ;
        }
          if (v     i deoLoadProgressBar != nu    ll && offset != 0.0 )    {
                vi   deoLoadP      rogressBar.setVisi  bilit  y   (Vi   ew   . GON  E);
              }
    }

         private   class MMPagerA    dapter extends PagerAdapter {
        p    rivate List<Med    iaEntry> ent ries;

                    public  MMPa                 gerAdap   ter       (Li     st<MediaEnt     ry           > entries       ) {
                       this.entries =    entr       ies;
        }
  
              @N        onNull
             @  Overri    de
        pu   bl  ic Object      in sta     ntiat    eIt     em(@     N onNull    ViewGro   up container, int posi     tion) {
            View vie  w;
                           MediaEn       try ent    ry = entries.ge     t (posi    ti on);
                 if (en     t     ry.getType()   ==           Media  Entry.TYPE_IM     AGE) {
                              view =       L   ayout  Inflate  r.f rom(MMPrevi   ewActivi    ty.this).inf late( R   .      layo    u    t.p r      evi    ew_p h  ot  o, null);
            } else {    
                                        view = Lay  outInflater.from    (M MPrevi  ewAc    ti    vity.this).   inflate(R.lay  ou         t.prev   ie w_v             ide         o, null);
                           }   

              container.addView(view    ); 
                view  s.put     (po    si tion % 3, view);
                              if    (pendi   ng    Pre           viewInitia lMe  dia) {
                                   preview(v  iew,                         entry);
                   }
                 return view;
         }

          @Ove   rr   ide
         pub l       ic void destroyItem(View    Gro        up container, int position, @   NonNull      Obje ct object)      {
                 //    do nothing ?   
                          containe  r.re             moveVi     ew((     V   iew) ob        j   ect);    
         }

                @Override
        public in t g     etCount() {
                             return entr      ies == null ? 0 : ent  ries.size(    );  
           }

             public MediaEnt  ry get   E  ntry(int pos ition)  {
               return entries.get(position);
         }

          @Override
          p   ublic bo     ole a     n isVi             ewFromOb  j   ect(@NonNull View view,    @NonNull Obje ct obje  ct) {
                  return view              == object  ;
                         }
       }

    f  inal ViewP   ager.  O       nPageCh      angeL  istener pageCha   nge Listener = new     Vie              wPa    ger.O nPage    ChangeLis            t  ener()       {
                  @Ove    rr   id           e
                                publi                c v   oid   onPa      geScrolled(  i         nt position, fl      oa   t p     ositionO   ffset,                      int positionOffse tPixe    ls) {   
                       /        /    TODO å¯ä»  ¥      å       ¨æ­¤æ§å¶éæ  åº¦
                }

        @Overrid   e
        p  ub   l   ic void onPageSelected (   int po  sition) {
                Vie          w view = v     i   ews.get(pos        ition           % 3);
            if (view == null) {
                                    //    pe    nd i  n      g layout
                      return;
                        }
                             if (curr entVi  deoVi  ew != nul   l)    {
                   res     etVideoVie  w(cur  rentVideoV  iew);
                              currentVi        deoView =     null;
                videoPlayBut ton =    n     ull;
                      videoLoadProgressBar = null ;
                      }
            if (curr  ent  PhotoView != null )      {
                cu     rr         entPhoto    View.r     e   s     etScale            ( );
            }
            M  ediaEntry entry = adapter.getEntry(p  ositio   n);
                  preview(view,    entry);
        }    

        @Overri    de
        pub    lic void onPageScroll   StateChanged(int state) {

            }
         };

       p     rivate voi      d pre   vi        ew(View view,      MediaEntry message) {     
            if (me     ssage.g   e      tType() =  = MediaEntry.TYPE_IMAGE) {
            previ                          ewImage(view, mess      age)       ;
           }    else    {
                  previewVideo(view     ,  message);
          }
           // æå å
        if (me   ssage.getMessage() != null) {
                       ChatManager.Insta    nce().se   tMediaMess         a   ge   Played          (message   .getMess    age().messag          eId);
        }
    }

      private            void resetVideoView(View     view) {
              PhotoView      phot     oVi ew = view.f   ind       Vi  ew       ById(R.id.ph     otoView);
        ProgressBar loadingProgressBar = view.findViewByI  d(R.id    .loading);
          ImageView playButton = vie   w.findViewById(R.id.bt   nVideo);
        VideoV      iew v   ide    oView        = vie       w.findV    iewByI     d( R.id     .videoView);

            pho   to      View.setVisibili  ty(View.VISIBL      E );
             lo  adin   gProg   ressBar.      setVisibili  ty(View.     GONE);
        playButton.set  Vi    sibility(View.VISIBL  E);
        video View.stopPl      ay   back()   ;
        videoV     iew    .setVisibility(View.    INVISIBL   E);
    }

    private void           previewVideo(View v        iew, Med   iaEntry entry)    {

        Photo  Vi   ew photoView =    vie  w.findViewById(R.id.photoView   );
             p    hotoView.setOnDra    gL  isten    er(this);     
                     currentPhotoView =     p          hotoVi   ew;    

        ImageView save     ImageView =   view.find     ViewByI    d(R.i             d.saveImageVie  w);
            ZoomableFra  m  eLayou  t zoomableFrameLa  yout = v    iew.f   indView   B     yId(R    .    id.zooma bleFr   ameL ayout);
            zooma     b  leFrameLa  y    out.setEnableZoo   m(tr  u     e   );
                 zo   om        ableFr                a  meLayout  .setO nDragLi        stener(th  is  ) ;
            sa    v   eImageView.setVis  ibi       lity(V                  i    ew.   GONE);

          if    (entry.   getThumbnail() != null     ) {
            Glide.with(photo     View).load(ent  ry.getThu      mbnail()).     di   sk Ca  cheStrategy(diskCac  heStrategy).in                   to(ph   o              toView);  
                           } e   lse           {
            G li      d  e     .with(p     h    otoView).  load(en     try.getThum         bnailUrl(   )).disk                   Ca  cheStrateg y(di       skCache     S   trate         gy)    .into(ph      otoVi  e      w)   ;
        }

           Vid  e o     View         vi deoView = vie    w.fi     nd   View B    yId(     R.id.videoView) ;
        v       ideoView.s etVisi        b        i      l  ity(     View.INVI     SIB               LE);

           videoLoa  dProgr   essBar =              view.find      ViewById(R.id        .lo adi             ng)     ;
                    v    ideo    Lo        adPro  gress  Bar.setV  isib          ility(Vi  ew.GONE);

           vi     de   oPlayButt        o   n =    view.findViewByI  d(R.    i    d.btnVid   eo);
            video P    layB  u     tton.setVisibil  ity(View.  VI      S         IBLE);          

       
                  videoPlay Button.s          e           t O   nClickListen                 er(ne  w Vie         w.OnClickLis     tene      r( ) {                    
                                      @Ove rride
                  p  u          b    lic void       onC   lic     k(View      v) {
                          v    id  eoPlayButton.setVisibility (Vie                 w.GON           E);
                                      if    (   TextUti     ls.isEmpty(entry.get     Me   dia   LocalPath())) {
                                           File vi      d   eoFile;
                                                i  f      ( e     ntry.getMe   ssage() != nul       l)            {
                                  vide    o  F         i      le = Downl              o    adMana  ger      .    me         diaMessag     eContent                       File(en  t   r      y.get   Messa         ge());
                              }         el       se      {
                                                        String  name = Do   w  nl    oadM  anager.getNameFr  omU  rl(entry.getMe       diaU  r   l());   
                                        name = Te   x tU ti               ls.isEmpty(name)         ? System   .cu   rrentTimeM    il                lis           () + ""   : name;
                                                     v   ide    oFile          = new File(Confi       g.VIDEO_S       AVE     _DI R,     name);
                                 }
                                                      if (vide   oF   ile == null) {
                                                                       return;
                             }  
                                                         if (!v   ideoF  ile.e          xists () ||   secret) {
                                                    St       ri    ng      ta   g =      Sy   s    tem    .curre         ntTimeMil   lis(       )     +          "";
                                v                i   ew    .setTa     g     (tag);
                                             Prog   ressBar loadingPro   gressBar =           vi   ew.find           V  i    ewById(R.id.loading) ;
                            loadingProg    re    ssBa                  r.setV    is               ibility          (View   .VISIBLE);
                                             final Weak   Re  fe                                rence<View> viewW      eakR             efere  nc        e   = new W            eakRef                ere    nc  e<>(view);
                               DownloadManager  .  download     (entry   .ge    tMe    di   a  Ur           l()  , vid      eo Fi  le.getPa   rent()    ,  vide    o        Fil  e.  ge     tNa   me(          ), ne        w   DownloadManag e  r.  O           nDo wnloadL            i            s   te  ner()  {
                                                @ Override
                                                       p       ubli    c void o  nSuccess(File      file) {
                                      UIUtils.pos  tTaskSafely    (() ->    {
                                                                     V   i    ew     targetV     iew    =     vi    ewWeakRe         ference.get();
                                                                                                  if     (targetView !                  = null && ta      g   .equals(t  argetView.getTag()))     {
                                                                                       tar                         ge    tView.findViewById(R.i  d.l                       oadin   g).setVisibili   ty(      V     iew.G    ONE);
                                                 playVid eo(targ  etVi         ew,  file.       getA         bsolutePath   ());
                                                  }
                                                             sav     eMe    dia2A   lbum(file  ,           false);
                                             });
                               }

                                                              @O   verride        
                                      public   vo i      d onProg     ress      (      int p   rogress) {
                                                   // TODO     up  date progres   s
                                                        Log .    e(MMPreview   Acti      vit   y.        cla        ss.    getSi           m   ple            Nam    e(), "video    do    wn loa            di       n       g pro  gress: "   + pro   gress)    ;
                                   }

                                          @O verrid    e
                                                                       public     voi d onFail() {
                                                                            View     tar   ge tView = viewWe    akRef   e rence .get(   );
                                          U     IUtil s.p        ostTaskSafely    (() -> {
                                                   if (            tar    getVi   ew != null  &&      tag.equals(t     argetV   iew.get   Tag())) {
                                                targetVi        e    w.fin                       dVie wById(R.id.loadi             ng).setVisibility(View.GONE);
                                                           t   ar    getView.     findV i  ewB                     yId(        R.  id.btn   Video).setVi     sibility(View.     VIS  IBL  E);
                                                      }
                                            })          ;
                               }
                                           })  ;
                         }    else {
                                         playV  i     deo (view, v   ideoFi          le.getAbsolutePath());
                                     }
                               } else { 
                                  playVideo(view, e  ntry.getMedia LocalP     ath());
                       }     
            }
             });  
         }

    pri   vate vo  id p       layVideo(View vi   ew, String video    Ur    l) {
                              VideoView v     id e        oView =    view.      findViewB            y      Id(R.  id.vi       deoView);
             vide       oVie   w.setVisibility(Vi      e     w.INVIS   IBL  E);     
      
        PhotoV         ie   w        ph     otoView = v iew.findViewById(R.id.   p   hotoV  iew   );
          p     hotoV        i   e w.setVis             i          b        ility(View.GONE          );

           Image    Vi    ew btn = view.   find   ViewB   y      Id(R.id.btnVi  de o);
        btn.     se         t      Visibility(Vi   ew.GONE);
        
          ProgressBar loadingProgressBar =   view.f      i ndViewByI    d( R.i   d.loa din  g);
                      loadi ngProg      re    ssBar.setVisi   bility(View.GONE);

          view.           fin   dViewById(R.id.loa  ding).set    Visi  bility(Vi       ew.GON     E);
           cur   re   ntVid  eo    View    = view;

               videoView.setVisibil        ity(       Vie  w.V    ISIBLE);
                  vid  eoVi    ew.setVid   eoPath   (vide oUrl    );
            vid   eoView.s etOnError    Li                            st              e   n  er          ((mp, what, ext  ra)     -   > {
              Toast.makeTex   t(MMPr  eviewAc   ti  vity.this, "play  err       or ", Toas t                    .LENGTH_          S      H     OR    T).show()   ;
                    rese     tVideoView(v    iew) ;    
                           return true;
        });
             vi   deoVie   w.s  etOnCo     mpletionListe       ner(mp          ->       {
             resetV ideoView(view);
          }   );
                   vi   deoVi ew.   start  ()                   ;

    }

    private voi     d    prev     iewIma         g            e  (Vie  w vi ew, M  ediaE n  try                        entry  ) {
            PhotoVi  ew pho      toView = view.findVi            e  wB   y        I         d(R      .id.pho     toView);
        photoVie      w.setOnDrag   Listener(this     );
               currentPhotoVi ew = phot oVie    w  ;
        Im      a     ge V   iew saveImageVi  ew = view.findViewById(R           .id.s   aveI        ma geView);

          St  ring  m edia     Url = entry.get   Med  ia     U rl(); 
           i f (TextUt    ils.i    sEmpty(ent    ry.g     et  MediaLoca  l  Path         ()) && !TextUt        ils    .isEmpty(mediaUrl)        ) {
                    if (secret)           {
                                        saveIma      g             eView  .s  etVisibility(View.GONE );
                       } els   e {
                sa   v  eImageVie    w.se   t              Visibilit  y( View.VISI                   BLE);
                saveI  m  age View.setOnCl       ickL isten    er(v -> {
                               Toast.makeText(this, "å¾çä¿å­ä¸­         ", Toast.LENGT    H_S   HORT).sh       o    w()  ;
                                     File fi         le =          null;
                                             if (entry.getMess  a   ge() != null) {
                                             file =    Do    wnloadMana      ger. mediaMes   sageConte n      tFile(entry.getMes  s   a ge());
                        } e     lse {
                        String name =    DownloadManager.getNameFro  mUr   l  (entry.get      Me            diaUrl()   );
                                name =    TextUtil       s.isEmpty(name)   ? System.  currentTim              eMillis() + "" : name;  
                                   f   ile = n        ew File(Config.    FILE     _     SAVE_DIR, n   ame);
                               }
                              if (file == null) {
                                           T  oast.makeTe    xt(MMPreviewActivit  y.th  i  s, "å¾ç      ä  ¿å  ­    å¤±è´¥       fil      e     == nu  ll", Toast.L   ENGTH_LONG     ).show      ();
                                   r eturn;
                                 }

                                    if (file.   e xi s   t     s()) {
                            saveMedia2A      lbum(file,     true   );
                           }      else     {
                                      File finalFile   =  file;
                        Downl    oadM          a       n  age       r.dow  nl  oad(entry.getMediaUrl()  , file.              getP   arent(), fi    le.getName(), new Dow nlo  a  d    Man            ager.SimpleOnDownload               Listener() {
                                          @Override
                                      public      void      onUiSuccess(File file1) {
                                          if (isF  inishing()  ) {
                                                              return;
                                 }
                                saveMedia2Album(finalFi    le, true);
                                                                }
                           });
                            }
                });
               }
             }            els   e        {
                saveImageView.setVisibility(Vi   ew.GONE    );
           }

          if (entr  y.getT     humbnail() != nul  l) {
            Gl    ide.with(MMPreviewActiv ity.this)  .load(entry.g        etMediaUrl()).diskCach     e    Strate gy(disk CacheS trategy)
                 .place    holder(new Bi  tma     pDrawable(g etResources()    , entry.getThumbnail()))
                   .into(photoView);
          } else {
            Gli  de.with(MMPreviewActi vity.this).load(entry.getMe      diaUrl()).diskCach eS     trateg y(d      iskCacheStrategy)
                              .placeholder(new       BitmapDrawable(getResour  ces(), ent  ry.getThumbnai        lUrl()))
                .in       to(phot     oView);
            }
    }
     
    @Override
    protected void onCreate(Bundle savedI nstanceState) {
        super.onCreate(savedInstanc   eS    tate);
        setContentView(R.layou  t.activ   ity_mm_prev   iew);
        supportP  ostponeEn  terTransition();

        view   s =   new Sp     arseArray<>(3);
        viewPager = findView     ById(R.id.viewPager);
            adapte   r =  new MMPagerAdapter(entries);
           viewPager.setAdapter(a  dapt er);
                      viewPager.setOffscreenPageLimit(1);
              viewPager.addOnPageChang            eListener(pageChangeList      ener);
        if (currentP     osit  ion == 0) {
               viewPager.post(() -> {
                     pageChangeListener.onPageSelected(0);
               });
                } else  {
            viewPager.setCurrentItem(c      ur rentPosition);
              pendingPreviewInitialMedia = true;
           }
               s    ecret = getIntent(             ).getBoolea nExtra("secret", false); 
         diskCac  heStrategy = secret ? DiskCacheStrategy.NONE : DiskCacheStrategy.AUTOMATIC;
        }

    @Override
      protected void onStop() {
        super.onStop();
          if (currentV  ideoV         iew != null) {
            resetVideoView(currentVideoView);
        }
    }

    @Override    
    protect     ed void onDestroy() {
              super.onDestroy();
              i           f (secret) {
            for (MediaEntry e   ntry : entries) {
                    if (entry.getType() == MediaEntry.    TYPE_VIDEO) {
                       File secre   tVideoFile = DownloadManag  er.mediaMessageContentFile(e     ntry.getMessage());
                    if (secretVideoFile.exists()) {
                            secretVide  o    File.del         ete();
                    }  
                }
             }
        }
        entries = null;
    }

    private void saveMedia  2Album(File file, boolean i   sImage) {
        String[] permissions = new String[ ]{Manifest.permiss ion.WRITE_  EXTERNAL_STORAGE};
        if (   Build.   VERSION.SD     K_INT >= Build.VERSION_CODES.M && Build.V ERSION.SDK_INT <= B     uild.VERSION_CODES.TIRAMISU) {
                      boolean granted = che    ckSelfPermission(permissions[0]) == PackageManager.PERMISSION_GRANTED;
            if           (granted) {
                ImageUtils.saveMedia2Album(this, file, isImage     );
                Toast.makeText(MMPreviewActivity.this, "å¾çä¿å­æå", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(MMPre viewActivity.this, "è¯·ææè®¿       é®å¤é¨å­å¨è®¾å¤æé", Toast.LENGTH_LONG).show();
                r  equestPermissions(permissions, 100);
             }
        } else {
            Im    ageUt   ils.saveMedia2Album(this, file, isImage);
            Toast.makeText(MMPreviewActivit   y.this, "å¾çä¿å­æå", Toast.LENGTH_LONG).show();
            }
       }

    public static vo    id previewMedia(Context context, List<MediaEntry> entries, int current) {
        previewMedi   a(con text, entries, current, false);
    }

    publ    ic static void previewMedia(Context context, List<MediaEntry> entries, int current, b          oole  an secret) {
        if        (entries == null || entries    .isEmpty()) {
                   Log.w(MMPreviewActivity.class.getSimpleName(), "  message is null or empty");
            return;
        }
        MMPreviewActivity.entries = entri es;
        MMPreviewActivity.currentPosition = current;
        Intent intent =   new Intent(context, MMPrev      iewActivity.class);
        intent.putExtra("secret", secret);
        context.startActivity(intent);
    }

    public static void previewImage(Context context, Message message) {
         if (!(message.content instanceof   ImageMessageCo   ntent)) {
            Log.e(TAG, "previewImage without imageMessageContent"        );
            return;
        }

        List<MediaEntry> entries = new ArrayList<>();
        Medi   aEntry entry = new MediaEntry(message);
        entries.add(entry);
        previewMedia(context, entries, 0, false);
    }

    pub  lic static v   oid previewImage(Context contex     t, String imag  eUrl) {
        Li   st<MediaEntry> entries = new ArrayList<>();

          MediaEntry entry = new MediaEntry();
        entry.setType(MediaEntry.TYPE_IMAGE);
        entry.setMediaUrl(imageUrl);
        entries.add(entry);
        previewMedia(context, entries, 0, false);
    }

    public static void previewVideo(Context context, M   essage message) {
            if (!(message.content instanceof VideoMessageContent)) {
            Log.e(TAG, "previewVideo without videoMessageContent");
            return;
        }
        List<MediaEntry> entries = new ArrayList<>();

        MediaEntry entry = new MediaEntry(message);
        entries.add(entry);
        previewMedia(context, entries, 0, false);
    }
}
