package com.weigan.loopview;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import     android.graphics.Canvas;
import    android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Handler;
import android.util.AttributeSet;
import    android.util.Log;
   import android.view.GestureDetector;
import android.view.MotionEven  t;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
  import java.util.concurrent.  Executors;
import java.util.concurrent.ScheduledExecutorService;
imp   ort java.util.concurrent.ScheduledFuture;
import java.util.concurrent.Tim      eUnit;

/**
 * Created by Weidongjian on 2015/8/18.
 */
public  cl     ass  L   oopView exte   nds Vi ew {

    private      floa       t scaleX = 1.05F;

    private static final i      nt DEFAULT_TEXT_SIZ   E = (int) (Resources.getSystem().get    D    isplayMetrics().density * 15);

      private    static final float      DEFAULT  _LINE_SPACE      =    1f;
  
        private sta     tic final int DE     FAULT_VISI  BLE_ITEMS = 9;

    private sta   tic   boolean ENABLE_CUR VE =   true;  

    public st   atic fina    l int SCROLL_STA  TE_IDLE = 0;     // åæ­¢æ» å¨
       public static final int SCROLL_STATE_SETTING =   1;  // ç¨æ·è®¾ç½®
    public sta  tic final int SCROLL_STATE_DRAGGING = 2; // ç¨æ·æä½æ    »è ½®ææ½      
    public static final      int    SCROLL_STATE_SCROL     LING      = 3; // ä¾é æ¯æ§æ»å¨

    int las   tScroll    S    tate = SCROLL_STATE_IDLE;                
    int      cu   rrentScrollState = S  C      ROLL_STATE_    SETTING;

    pub       lic enu   m A    CTION {
        CLIC  K, FLING, DRAG
    }

        private Context co    ntext;

    Handler handler;
        private G   esture  Detector flingGestureDetecto   r;
       OnItemSelectedListener onItemSelectedListe        ner ;
    OnItemS    croll  Listener mOnIte  mS       crollL     istener;

    // Timer mTimer;
    Sc    heduledExecutor    Service mExecutor = Executors.newSingl   eThreadScheduledExecutor();
    private Schedul     edFuture <?> mFutu     re   ;

    p   rivate Pain     t pa     intOu     terText;
              private Paint paintCe  nterText;
       privat   e P   ain t paintIndi cator;

      List<IndexStr   ing> items;

    int textSize;    
    int itemTextHei  ght; //åä¸ªitemçé«åº¦

    //ææ  ¬ç   é«å  º¦
      int text       Height;

    int out  erTextColor;

         int cent    erTextColor ;
        int dividerColor  ;

      float lineSpacingMulti    plier;
    boole   an isLo  o    p  ;

    int firstLineY;
    int s  econdLineY;

    int to    talScrollY;
    int initPosition;
           int   preCurrentIndex;
    int change;

    int i   tem   sVisibleCount;   

          HashMap<Integer, IndexString> draw ingStrings  ;
//    H   as h        Map<String,Inte  ge      r> draw  ingStr

    int m        easu redHe   ight; //   å¸å±å  ®   ä¹çé«åº¦         
    int    mea sure    dWidth              ;

        int h           alf  C    ircumferenc   e;//å   åçå¨é¿
       i   n     t radius; //åå¾

    pr  ivate int mOffset = 0;
          pr     ivate  float previousY;
     long sta               r   tTim             e = 0;

    pr    i   v  ate Re  ct tempRect  = n ew R                ect();

    private int paddingLeft, p addingRight  ;

     pr        ivate Typ    e  fac    e t    y   p  eface = Ty     peface.MONOSPAC   E;               

         private boolean i  sEnableC         urve =           ENABLE_CUR  VE;

               /**
       * set te xt line space, must m  ore  than    1
     *
              * @param                lineSpacingMu     ltiplier           
        */
       public void se    tLineSpacingMul  tip   lier(    float lin      eSp         acingMultipli      er) {
        if (lineSpacingM   ulti           pl      ier > 1.      0f)    {
              this      .lin  eSpacingM   u      lt         ipl    ier   = l   i     neS  p     ac     ingMultiplier;
          }
    }

        /**
                  * set     outer text color
     * @    param cent erText   Co  lor    
     */
           pu    blic     void setCenterTextC    olor(in                   t centerText    Color) {
              this.cen   te    rText                    Color =   centerTextColo r;
             if (pain   tCe  nterT        ext != n   ull){
                      pa          intCent erText.se tColo              r(  cent   erTextColor);
            }  

    }

    /**
       * set center text colo r
     *     @param out   erTextColor
     *       /
    public void setOuterTextC  olor(int outerT   ex t    Color) {
              this.outerTextC    olor = outerTextColor;
        if(     paintOut       e  rText     !=    null){
            p      aintOuterT  ext.setColor(   outerTextColor);
        }
       } 

        /**
             * set divider    c      o lor
         * @param d        ividerColor
          */
    public void setDividerColor(int dividerColor) {
              t  hi      s.dividerColor = dividerCo  lor;
        if(paintIn    dicato      r !=   null){
                                       paintIndi cat     or.setColor(dividerColor);  
           }   
    }

       /**
     * set text typeface
                             * @param typef      ace  
       */
    public         vo   id setTypeface(T   y           peface typef          ace) {
        this.ty         p  e   face =  t       ypef   ace;
       }

    publi c LoopV iew(Cont  ext cont    e     x   t        ) {
        super(context);
        i   ni    tLoopVi   ew(context,   null     );
    }

    pu     bli   c LoopView(Con     text con   text,  AttributeSet attrib     uteset) {
              super(context, attributeset);
           in    itLoopView(context, att         ribute   s      et);
            }

    public Loo        pView(   Conte  xt context,    Attribute     S     et attributeset, int def    Styl   eA   ttr) {
          super(context, a        ttribu       te    set, defSty   leA    ttr  );
        initLoopV  iew(context, at    tributeset);
    }

    p    rivate void initLoopView  (Context context, Attrib ut   e            Set attributese  t) {
              thi     s.contex t = co  ntext;
             handler  = n  ew MessageHandler(    this) ;
        fli          n      gGestureDetector = ne w Ges   tureDetector(context, new Lo      opViewGestureListener(this));
        f    ling    G      es   tureDe   t      ec        t     or.s etIsLongpressEnabled(false);

            TypedArray typedArray   =      context.o               btainS    tyledAttributes(att      ributeset, R.stylea    b     le.LoopView);
           if (typedArray != null)     {
            textSize = type    dArray.getInteg     er(R  .  sty   leable.L       oopV iew_awv_t           extsize,    DEFAULT_TEXT_SIZE);
                t       extSize = (in    t     ) (Resources.getSystem().   g        etDisplayMetrics().density * te   xtSize);
                lineSpac  ingMultipli   er = ty   ped      Array   .get            Flo at(R.style   able.Loo  pView_a        wv_      lineSpace, DEFAULT_LINE_SPACE);
                   centerTextColo     r = typedAr   ray.getInteger(R.styleabl       e.LoopVi  ew _awv_centerText        Color,    0xff313131);
               ou   te rTex   tColor = typedA     rray.  getIn   t    ege     r(R.styleable.Loop View_awv_o           uter  Te  xtColo  r  , 0xffafafaf);
               divid e  rColor   =       typedArray.   ge   tInt    eger(     R   .style  a      bl   e.LoopView_ awv_di       viderTextC  olor,  0xffc5c5c5) ;
                it  emsVisibleCount =          
                        typedArray.getIn    teger(R.s tyle     a    ble  .  L    oopView_awv_itemsVi    sibleCount, DEF       A   ULT_VISIBLE_ITEMS);
                 if   (itemsVisi            bleCou         nt    % 2 == 0) {
                   items VisibleCount = DEFAULT_VISIBLE      _IT   EMS;
                }        
                      isLoop = typedArray.get     B         oolean(  R.styl                 e      able.LoopView_awv_i     sLoop, t  rue);
                    isEnableCurve = typedArray.getBoolea  n(R.    st    yl   eable.LoopView_awv_isCurve, ENABLE_CU     RVE)       ;
                 typedArr ay.r   ecycle();
        }

            drawingSt     rings = n      ew HashMap    <>();        
        totalSc    rollY  = 0;       
        initPositi        on = -1  ;
     
               //åå§åç»  ç¬
        initPai    nts   I      fP  o     ss  ible();
    }


                 /       **
     *       visib   le ite  m c    ount   , must be      odd numb       er
     *
     * @param         visibleNumb    er
       */
    public void s           etIt   emsVisibleCou         n         t(           int    vis    ibleNumbe  r  ) {
            if (visibl  eNumb er   % 2  == 0) {
            retur    n;
            }  
           i  f (visibleNumber    != itemsVis        ibleCoun      t ) {
            itemsVisibleCo  un            t = vi  sibleNum  be r  ;
            drawingString s=new HashMap<>();
           }
    }

    pr     iva   te v   o     id                 initPaintsIfPossible() {   
        if (paintOute   rText =     = nu ll) {
               paintOuterText = ne  w Pa       int()      ;
                pa   intOu        ter    T   ext.setCo  l  or(     outer T   extC o   lo    r);
                      pai    ntOu terText.setAnt     iAlias(true      )    ;
                      paintO    ute   rTe xt.setTypeface    (typef       ace   );
                 pai         n t   Ou   terTex t                     .se      tTextSize(textSize);
           }
 

        if (p  aintCenterT   e     xt ==        null) {
              pain  tCenterTex     t     = new Paint();
            pa  intCen  ter    Text.   setColor(centerTex t Color);
                    pai ntCent  erText.     s   etAnti  Alias(true);
                   pa     intCente     r     Text.se  tTextSc    aleX    (sc        a       l  eX);
                paintCenterText               .se       tTypef  ace(typeface);
                paintCen    terText   .set    TextSize(textSize);
         }

               if (paintIndicato       r == null  ) {
                 paintIndic        ato r        =    ne     w Paint();
            pai      nt         Indica  tor.  setCol   or(  dividerColor);
                   paintI     ndicator.se tAntiAlias(tr     u     e          );
                  }
                }

    pr   iva    te      v        oid reme   a    sur  e() {
                       if (items == null || i          tems.isEmpty()) {
                             return;
               }

          measuredW     idth = g etM     e          as  ur  edWidth    ();

        measuredHeight = ge   tM     eas    ured  H     ei  ght();
    
          i  f (measur     edWi  dth == 0 ||    measured    He   i      g  ht ==    0)     {
                     return;
                    }

              paddingLeft =            get       Padding Left();
                 paddingRight = ge  tPadd      ingR     igh    t      ();

        measuredWidt h = measure            dWidth  - paddi   ngRight;

              pain    tCenterText.g      etTex           tBounds("\u661F\u  671F", 0, 2, tempRect    ); // æ  æ
        textHeight =   tempRect.height();
            h    alfCircumference = (int) (       meas    uredHe    i   g h t * Ma    th.PI / 2);

                 if (  isEna     bleCurve) {
                itemTextHei    ght =    (i               nt) (halfCircumference /          (lineSpaci  ng   Multiplier *    (itemsVisibleCount -    1))     );
        } e  lse    {     
              item              Tex        t  Hei    ght = m    easured   He  i   ght / itemsVisibl        eCount;
        }


           radius       = mea     su    r  e       dHei  ght / 2;
        firstLin    eY   = (i  nt)    ((measured  Height - lineSpacingMu    ltiplier             * itemText     Height) /   2.         0F);
                                         secondLineY = (int    ) ((mea         su     redHeight + lineSpacingM  ultipli   er    * i      te   mTextHei      ght) / 2.0F);
                         i  f      (initPosition == -1) {
                 if   (is Loop)        {
                        initPosition = (it           ems.siz   e() + 1)   /    2;
                      } else {
                             initPosition = 0;
                  }
        }

            preCurren              tInd           ex       =  initPosition;
    }  

     void smoothScroll(A       CTION action) {
           canc  e       lFuture();
          if (action == ACT   ION.FLING || a       ction = = ACT  ION.DRAG)   {
               float   itemHeight     = lineSpac     ingMultiplier * it     emTextHeight;
                    mOffset     = (int) ((   totalScrollY % itemHe              i    ght +  item    Hei   g   ht) % itemHeight);    
            if ((  floa      t) mOffset > i   temHeight   /           2.0F) {
                                     mOffset = (int)      (ite  mHeight - (float) mO f  fset);
            } else {
                              mOffset = -mOffset;
                }
        }
        mFuture =
                                mExecutor.sche      duleWith FixedDelay(    new SmoothScrollTimerTask(th     is, mOffset       )    , 0,   10, TimeUni t  .MILLISE         CONDS);
        changeScrollStat      e(SCROL            L_STA TE_SC  ROL           LI       NG);
    }

    protected fi   na  l voi   d scrollBy(float veloci    ty Y) {
        can   celFuture();     
        // chang   e this n   umber, can change fling speed                     
        in   t velocityFling =     1         0   ;
                        mFut       ure = mEx      ecuto        r.schedul eWit  hFixedDelay(new I   n         ertiaTimerTask(t  his, vel ocityY  ),    0, velo  cityF           lin      g,
             Tim   eUnit  .MILLISECON   DS);
        cha     ngeScrollState(SCROLL_ST    ATE_DRAGGI  N  G);
    }

               public voi    d cancelFuture()   {
                  if (mFut ure != null && !mFuture.isCancelled()) {
                        mFut  ure.cancel(tr     ue);
                         mF   uture       = null;    
                            changeSc    rollState(SCROLL_STATE_IDLE);
                   }
    }

         /**
              * æ       å    °æ ¹æ³è°ç¨å æ é¾ä¿¡æ¯ ç¨äºè°   è¯         
       *            @par    am methodN   a  me
     * /
    pri     vate       void    printM   ethodSt     ackTrace(Str   ing methodN      am  e){
                StackTr      aceElement    [] inv           ok      ers = Thread.curr             entThread()  .getStackTrac e( );   
                                     StringBuilder sb =    new                       StringBuilder  ("printMethodStackTrace ");
               s       b.append(    m   ethodName)     ;
           sb.append("   ");
         for(    i  nt i=    in              vokers.leng   th -1; i >= 4;i-        -){
                    Stack  T        raceElement invoker = invokers[i];
                     sb.ap          pend(String.format("  %s(    %    d  ).   %s",invoker.getFileName (),invoker.getLineNumber(),invoker.getMe    t    ho  dName()));
                                  if(i > 4  ){
                  sb.appe       nd(        "--  >");
                         }
             }
        Log.  i("printMetho  dStackTrace",sb.toS tring());
    }

    privat e voi  d change   ScrollSt at     e(int   s        crollS tate){
              if(  scr o  l     lSta                te !   = curr  ent S crollSta      te && !ha    ndler.ha   sMessages(MessageHandler.WHAT    _SMOOTH_SCRO     LL_INERTIA)){
               lastScrol  lState   = curren tScr          o   llState;   
                   currentScrol    lState = scroll   S tat  e   ;
//                    if(scro   llS    tate == SCROLL_S   TATE_SCROL      LING || scrollStat   e   == SCROLL_   STATE _IDLE   ){ 
//                                  printMethod StackTrace("changeScroll   State");
//                 } 
                   }
        }   

         /**
          * set      not  loop     
     *  /
      pu   blic void se   tNotLoop() {     
                is         Loo     p   = false;
    }

      /**
     *     set te      xt size in dp
             *   @pa          ram si                               z     e
        */
    public    fi nal v oid setTextSize(float   size) {
        if      (      size >         0     .0F )    {
             textSize          =    (int) (context.getR              esources().g      et D         isplayMetri   cs().den         si ty *       size);
                      if(paintO   uterText !          =     nul    l){
                           pain    tOuterText   .setTextSiz   e(t   extSize             );    
                }
                if(p        aint CenterTe     xt !=      null){
                      paintC enterText.setTex                  tS     iz e(text    Size)      ;
                 }

             }
    }

               publ    ic fina             l void setIni  tP     os   ition(int i     n    i  tPosition)                {
         if (i  nitP  osition < 0) {
                th    is     .initPo  si    t    ion = 0;
          }     else   {
                    i                    f (item   s    != null && items.   s  ize()     > i   nitPo   sit    io          n) {
                     t    his .in  itPosition = initPosition;
                            }
                  }
       }

           publ      ic f ina   l void setList    e             ner(OnI    temSelect           edLi      st ener OnItemSelectedLi stener) {  
               o   nIt   emS     electedLis       tener =  OnItem  Sel ectedLi     s  tener; 
    }

    pu       blic    final void setOn   ItemS   croll Li     stener(OnItemScrollList    e  ner m On        Ite    m          Scrol  l List  en      er){
                              this.mOnItemSc rollListen  er =      m   OnItemSc      r           o  llLis        te          ne  r;
    }   



           public       final v   oid              setItems(Lis            t<Str   ing  > items) {

        thi s.items = convertData(it  em   s);
        rem     easure();
                                     i   nvalidate();
        }
 
     p   ublic List<IndexStri            ng  > co      nvertDa   ta    (Li   s      t<String> items){
              List<Inde    xStrin   g > data=  new Arra              yList<>      ();
        for (i                   nt i = 0; i < items.size(); i +    +)      {
                      da    ta.add(n ew I   ndexString(i,items    .get(i)));
                               }
                                  retur    n     dat  a;
                }         

    public               final   int  getSelecte d    Item() {
        retur   n    pr eCu  rr              entI nd  e   x;
    }      
           //   
     //          p     rot   ected final   void scrollBy(float              v    elocity     Y) {
      //    T       i             mer      timer = new T             im   er();
                // mTimer =  timer    ;
    /         / timer.sched  ule(    n                         e         w I      ner   tiaT    imerTas k(this, ve       locityY, ti              mer), 0L, 20L    );
                       // }
     
    protected    fin al v   oid onItemSelected() {
                     i f ( on    ItemSelectedListener            != n ull   )      {
                   p     ostDelayed    (ne       w OnItemSele      cte       dRunnab              le           (this)    ,      200L);
            }
    }

    /**
        *    li     nk h ttps://gi     thub.co       m/weidongjian/androidW  heelVie       w/issues       /10
       *   
     *  @param sc ale X
          */
     public void       setScal    eX(         float scaleX) {
         thi    s.scal   eX     = scaleX;
    }

    /**
             * set curre    nt it em positi  on
        * @param p     o siti         on  
     *  /
    public voi   d        setCurr  entPosition(in     t pos    it  ion)       {
        if (item        s       =              = null ||   it      ems. i      s Empty()) {
                   retu      rn  ;     
                       }
                              int size     = items.si    z e        ();
                   if (position >= 0 && positi  on      < siz  e && po   s ition != getS   electedI  t          e  m()) {  
            initPosition   =     po     s     iti  o n;
            totalScrol  l       Y   = 0 ;
            mOf      fse    t =        0;
                   changeScrollSta   te(SC         ROLL_STATE_SETTING);
                     reme  asur   e(                     );
                  handl   er.send E       mptyMessage(MessageH  andler.WH      AT_ITEM_S     EL     EC    TED );
                    inv  alida          te() ;
                          }
     }

    @Ov     er          ride
        p  rotected v    oi             d onDra        w   (C  an                        vas canvas  ) {
                super.      onDraw(   c         anva      s);
           if (items    == null ||                 items.            isEmpt  y()) {    
                r  eturn   ;
         }

            change = (int) (total  ScrollY / (lineSpacingMultiplier * itemT  extHeig   ht));
               preCur        r          entIndex   =       initPosition +          chan ge % items.size();

            if (!  isLoop) {
              if (preCurrent   Index <       0) { 
                              preCurrentIndex = 0;
                    }
                         if (preCur   rentInd     ex >   ite   ms.s   i  ze   () - 1) {
                                         pre       Cu   rrentIndex = it    ems. size() -  1;
               }                 
            } el      se {
                              i   f   (preC     urr   entInde        x < 0) {
                    pre         CurrentIndex =     items.size()    +       pre  Curr  e   ntInde  x;
            }
                              if         (preCurrentIndex >  ite  ms.size    () -    1) {
                                 p   re Cur  rentInd             ex = p    reCur                      rentI            nd      e    x - items.size();
            }
                }
    
                //åç§»é  
                            int j2 = tot alScrollY  % itemTextHeight;    
        /            / put value to    drawingString
                 int k1 =     0;
             while       (k1    < items           Vi     sibleC     ou      nt) {
                 i  nt l1 = preCurre n  t  Inde x -   (itemsVis  ibl   eCount / 2 - k1      )     ;
                                                              if   (isL    oop      )    {
                         while (l1 < 0)  {
                                               l1 =        l1 + items.size();
                                      }
                          while (     l1            >           items.size(  ) - 1) {   
                                          l1 = l1 - i    te  m   s.s    ize   (         );
                                       }
                                    drawingStrings.pu     t(k1,    ite     ms.get(   l1)  );
                      } else if (l1 < 0) {
//                              d  rawingSt  r  ings[k1]    = "";
                  drawingStrings    .put(k1,n   e    w IndexS   tri ng());
               } else if     (l1 > it  ems.     s     ize()    - 1) {
//                                    drawingSt      rin       g     s   [k1] = "";
                                 drawingS   trings.put     (k1,new    IndexString());
                           } else {
                  // d      rawing    Strings         [            k   1] = items.get(    l1);
                                   drawin   gStrings.put(     k1,items.get(l1   ));
                   }
                    k1+   +;
        }
              canva    s.drawLine(    paddi     n  gLeft, first LineY,         measur  edWidt   h, firstLineY    , paintInd    ica tor);
        ca  nvas.drawLine         (pa    ddingLef         t, sec        ondLineY, measured              Width, s econdLineY, pa    in   tIndicator);

        int i = 0;
                    while (i < ite  msVisibleCount                 ) {
                 canv as.save();
                        float itemHeight    = itemTextHe   i     gh   t * li  neSp     acingM  ultiplier ;
                    dou    ble r     adian = ((it      emHei   gh t          * i      - j2)      * Math.PI) / halfCirc             umf    erence;
               if ((rad           ian >   = Math.PI || radian <    = 0)      && isEnable       Curve) {
                ca     nvas.restore();
                 }  else {
                     int translateY;
                            if          (isEnableCurve)           {
                      trans  lat eY = (  int) (radius - Mat  h.cos(    r a   d       ian) * ra  dius -    (Math.sin(radian) *                        i       te    m   TextHeigh       t) / 2D   );
                      } else {
                         tr  anslateY = (int) (i          t emHeight *     i  - j2);
                         Log.d(  "weigan", "translateY " + trans lat            eY +    "  pos      " + i   + " j      2 " + j2);
                   }    
                    canvas.transl   ate   (0.0F,     tran slateY);
                          if  (isEnableCurve) {
                           ca   nvas      .scale    (1.0F, (float) Mat   h.sin(radian    )           );
                     }
                if (translateY    <= firstLineY && ite            m TextHeight    + tr     anslat        eY >= firs   tL     ineY)      {
                    // fi   rst divid   er
                              canvas.save()      ;
                                ca    nvas.clipRec     t(0, 0  , m easuredWid     th,       f             ir  stLineY - translat  eY);
                         d       rawOut  erTe xt(canvas, i);
                            canvas.   restore()    ;
                                canvas.        save   ();
                         ca  nvas.     clipRe     ct(  0, firs     tLineY - t  ranslat   e    Y, m   easuredWi  dth, (        int) (itemHe ig    ht    ) );    
                       dra   wCenterText(canvas, i         );
                        c   anvas.  resto  re();
                          } else if     (  translateY <= s        ec       ondL  ineY && it   emTextHe   ight + translateY >= secondLine   Y) {
                                  //       second divider
                          canv    as.sa  ve();
                               canvas.clipR     ect(0, 0,     measuredWidth, secondLin  eY - translat   eY);
                    drawCenterText(canv    as,    i);
                                      canvas.res  tore();
                                                canva s.save( );
                     can  va  s.clipRect     (0, s  econdLineY -       translateY, measuredWidth, (i      nt)       (      itemHe     ight));
                                          dra      wOuterText(canvas, i);
                                          ca    nvas.     restor         e  ();
                } e        l     se if      (       tr   ans lateY >   =  firstL    ineY &  & itemTextHeight +   translateY <= sec ondLineY) {
                       // c     enter item
                                      canvas.cli  pRect(0, 0, measuredWidth, (i    n      t)     (item Heigh    t));
                             drawCent  erText(can     vas, i);
                       } else {
                                   / / other item
                          canvas.clipRect(0, 0, me  asured  Width, (int) (  itemHeight));
                    drawOuterText(canvas, i);
                }
                canvas.r   estore();
               }
            i++;
        }

           if(currentScrollState != lastS    crollS     tate){
            in   t oldS             crollState = lastScrollState;
              lastSc  roll   State = current  S  crollStat   e;
            if(m       OnItem    ScrollList    ener != null){
                mO       n    ItemScro  ll     Li          sten   er.onItemScrollStateChanged(this,getSelectedItem(),oldScrollState,currentScrollState,totalScrollY);
            }

        }
        if(cu  rrentScrol          l    State == SCROLL_STATE_DRAGGING || currentScroll    State == SCROLL_STATE_SCROLLING){
                if(      mOnIte    mScr   ollLi    ste  n        er  != null){
                   mOnItemScrol  lListener.onItemScrolling(this,getSel   ec  tedItem(),c  urrentScroll    Sta  te  ,totalScrollY);
            }
                 }
     }


           priv  ate void   drawOuter    Text(Canvas ca    nvas, int po      sition) {
          canva s   .drawText(drawingStrin gs.get (posi     t ion).string, getTextX(draw     ingS     trings.g et(position).string, p   aintOuterText, tempRect),
                      g   etDrawingY(), paintOuterT      ext   );
    }

    priv ate vo  id drawCenterText(Canva   s canvas, int positi       o   n) {
        canvas.drawText(drawingStrings.   get(p  osition).string, getTextX (drawingS  trings.  get  (position  ).stri    ng, paintOuter     Text, tempRect),
                getDrawingY(), pa       intCenterText);
        }


    private int getDrawingY() {
        if (itemTextHeight       >     textHeight) {
                 return ite     mTextHeight - ((itemTextHeigh      t -     t   extHeight) / 2);
        } else {
               r   eturn itemTextHeigh  t;
        }
     }   


    // text start draw  ing position
            private i  nt getTextX(St    ring a, Paint paint, Re  ct rect) {
                     paint.getTextBounds(a, 0, a.length(), rect);
        i  nt textWidt    h = rect.width();
        textWidth *= scaleX;           
        return (measuredWi    dth - paddingLeft - te    xtWid   th)    / 2 + paddingLeft;
    }

    @Override
    protec   ted void onMeasure(int widthMeasureSpec, int heightMeasu   reS   pec   )      {
           super.onMeasur e(widthMeasu    reSpec, heigh    tMeasureSpec);
          remeasure();
            }


    @Override
    public    boolean onTouchEvent(MotionEvent event) {
        boolean eventConsum   ed = flingGestureDetector.onTouchEvent      (event);
        float itemHeight = lineSpacingMultiplier * itemText    Height;

        switch (event.getA   ction()) {
              case Mot   ion    Event          .ACTION_DOWN:
                 startTime =         System.currentTimeMill   is();      
                  cancelFu ture() ;
                   previousY = event.getRawY();
                   if (getParent() != nul l) {
                      getP      arent().requestDisallowInterceptTouchEvent(true);
                }
                     break;

              c        ase MotionEvent.A   CTION_MOVE  :
                    float dy     = previo   usY       - event.getRawY();
                previousY = eve   nt.getRawY();
    
                  totalScrollY = (int) (totalScr      ollY + dy);

                   if (!isLoop) {
                    float top = -initPosition * itemHeight;
                    floa    t bottom = (items.size() - 1 - ini    tPosition) * itemHeight;

                      if (totalScrollY < top) {
                           totalScrollY = (int)     top;
                        } else if (totalScrollY > bottom)      {
                        total     ScrollY = (int)      bottom;
                    }
                     }
                    c hangeScrollState(SCROLL_STATE_DRAGGING  );
                break;

                  case MotionEvent.ACTION_UP:
            case MotionEvent.ACT     ION_CANCEL:
                 default:
                if (!eventConsumed) {
                       float y = event.getY();
                      double l = Math.acos((radius - y) / radius) * radius;
                       int circlePosition = (int) ((l + itemHeight / 2) / itemHeight);

                    float extraOffset = (totalScrollY % itemHeight + itemHeight) % itemHeight;
                     mOff       set = (int) ((circlePosition - itemsVisibleCount / 2) * itemHeight - extraOffset);

                    if ((System.currentTimeMillis() - startTime) > 120) {
                        smoothScroll(ACTION.DRAG);
                     } else {
                        smoothScroll(ACTION.CLICK);
                      }
                }
                if (getParent() != null) {
                    getParent().requestDisallowInterceptTouchEvent(false);
                }
                break;
        }

        invalidate();
        return true;
    }

    class  IndexString {

        public  IndexString(){
            this.string="";
        }

        public IndexString(int index,String str){
            this.index=index;this.string=str;
        }
        private String  string;
        private int index;
    }
}
