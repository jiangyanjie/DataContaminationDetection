/*
 *  Copyright     (c) Gustavo Claramunt  (AnderWeb)      2014.
 *
 * Lic    ensed   under the Apache License, Versi    on 2.0 (   the     "License");
 * y     o    u may not use    this              file excep  t in compliance with th e   Lice   ns           e.
        *     You may obtain    a cop     y of the License at
 *
 *       http:  //www.apa   che     .org  /     licenses/LICENSE-2     .0
 *
   * Unless re  q   uired by    applicable law  or agreed to in  writing, so   f       twa  re
 * distributed under the  License is distributed on           an "AS IS" BASIS,
 * WITHOUT WARRANTIES O     R CONDITIONS OF ANY KIND, either express or imp         lied .
 * See the License for the specif   ic language go    verning per  missions and
 * limitations under the License.
 */

package org.   adw.library.widgets.discreteseekbar.internal; 

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
       import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;      
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.Vie    w;
import android.view.Vie   wGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import or g.adw.l    ibrary.widgets.discreteseekbar.R ;
import org.adw.l   ibrary.widgets.discreteseekbar.internal.compat.SeekBarCompat;
import org  .adw.library.widgets.discreteseekbar.internal  .drawable.MarkerDrawa  ble;

/**
   * {@link android.view.V   iewGroup}    to be use  d as the real indicator.               
 * <p>
 * I've used this to be able to accommodate     th   e TextView
 *   an    d the     {@link or  g.adw.library.widgets.discreteseekbar.internal.drawable.Marke    rDrawable}
 * with the required positions and offsets
 * <       /p>
    *
 * @hid    e
 */     
public class      M    arker e    xtends ViewGroup implements MarkerDraw        able. MarkerAnimationL    ist   en     e       r      {
    privat      e static final       i     nt PADDING_DP = 4;
     priva  te s       ta      tic fi  nal   i       nt ELEVATION_DP    =     8;
         //The TextView to show the info
     private    TextV  iew mNumber;
        //The max         w         id th of this View
        privat  e int mWidth;
    //some dis    t   ance between the thumb and our bubble ma         rk       er      .
       / /This will   b      e     ad    ded to our      m  eas   ured   heigh  t
       private int    mSepar           ation;
    Mark  erDrawa ble mMa   rke     rDrawable ;

    publ  ic Mark  er(Context context, AttributeSet attr s,   int    defStyleAt     tr, St  r    ing maxVa  lue, int th   umbSize, int separation) {
             super(co  ntext, a ttrs   , defStyleAtt   r);
           //as we're reading the parent D     iscreteSeekBar att          ributes, it may wr           ongly    set this view's vi   sibility.
              setVisibility      (View.VISIBLE);
        
              DisplayMetrics displayMetrics =    context.getResources().getD   isplayMetrics();
           Typed  Array a =   c ontext       .obtai    nStyledAttribu       tes(attrs, R.   styleable.Discret   eSeekB              ar  ,       
                R.att   r.discreteSeekB   arStyle, R.style.Widget_D      iscret eS    eekBar);

        int padding = (in    t) (PADDI        N   G_DP * disp      layMetrics.density) *     2;
        int textAppe  aranc    eId = a.getResourceId(R.styleable.Dis cr    et    eSeekBar_dsb_indicatorTe   xtAppearance,
                    R.style.Widget_DiscreteIn                    dica torTextAppearance);
        mNumber = new   TextView        (c   ont   ext)     ;
        /  /Add some p   addi   ng to this text   View so the bubble h       as some spa     ce to breath
         mNumber.setPa    ddi   n         g(padding, 0, padding,    0);
                     mNumber.se    t          TextA   ppe    arance(context, textAp   pearanceId);
                  mNumber.setGra          vity(Gravity.CENTER);
         mNumber.setText(maxValue);
                          m  Number.setM     axLines(1);
         mNumb    er   .se tSi   ngleLine(  true);
        See       kBarC   ompa                 t.setTextDirecti       o    n(mNumber, T    EXT_DIRECTION_LOCALE);   
            mNumbe   r.setVisib             ility(   View.IN         VI   SIB    LE)  ;

            /   /add some padding for the elevation        shadow     not t    o be clipped
               //I'   m sure th  er    e are better way   s of d oing this...
        set  Paddin    g(padding,          padding, p   addin    g, padding);

        resetSizes(maxValue);

              mSepar   ati  on = s   epara   tion;
                   ColorStat e List color = a .getC  olor        StateList(    R.  s  tyleable.Di  screteS    eekBar_dsb_i           nd       ica torC      olor);
              mM   arkerDrawable = new Marke rDr awable(color,  t      humbSize);
        mMarkerD rawable.setCall     back(this);
        m          MarkerDra        wable.s  etM    arkerListener(this);
          mMarke   rDr   awable        .   set       Exter       nalOffset(   padding);

           //E     levation for an   roid 5+ 
         float elevation = a.ge  tDimension(R.styleable.DiscreteSeekBar_dsb  _indicatorElevatio  n, ELEVATION_DP * displayM  etrics.density);
            Vi ewC      ompat.setE l evati    on(thi   s, eleva  tion);
        if (Bu    ild.VERSION.SDK_INT >= Build.     VERSION_CODES.LOLLIPOP) {
               SeekBarCompat .setOutlineProvide         r(this, mMarkerDraw       a  ble);
          }
               a.rec y     c   le();
    }

    publ  ic v        oid resetSizes(String maxValue) {
        DisplayMetrics d   isplayMe                     trics = getResources().getDisplayMetrics     ();
             //Account for  negative n umbers... is there any proper way o   f ge tting th    e biggest string        betwe    en our range????
        mNu  mber.setTex     t("     -"          +   maxVa      l     ue)       ;
           //Do a first       forced measure call for the T   extView (with   the bi     ggest text c   on  tent),
            //to calculate the m  ax width and use alway     s th      e same.
        //this avoid     s the TextView from shrinking an    d   gr     owi    ng whe    n the text content ch     a      nges
                  int wSpec = MeasureSp     ec.makeMeasureSpec(   di  splayMe   trics. widthPix  els, MeasureSpec.AT_MO         ST);
            int hSpec = Measu reSpec.makeM             eas ureSp ec(displayM  etrics.heightPixe   ls, Mea   sure   Spe      c.    A    T_MOST); 
        mNumber.measure    (wSpec    , hSpec);        
        mWidth =   Math.max(m  Nu mber.getMeasuredW idth(), m             Number.   getM       easu     redHeig    ht());
           r   emoveView(mNumb  er);
               addView(    mN   umber, new Fr  ameLayou t.Layo     utP  arams(  mW         idth, mWidth, Gravity.LEFT | Gr    avity.TOP        ))   ;
    }

    @Override
    p    rotect     ed void dispatchDraw(        Canvas canvas) {
         mMa    rkerDr  awable.dr   aw(canvas  );
        su  per.dispatchD     raw(canvas);
    }

    @Ove  rride   
    protected void on    Measure(i   nt widthMeasureSp      e       c  , int heightMeasureSpec)           {
        me  asureChild  ren     (w        idthMeasureS   pec, h    eightMea s   ureSpec);
        int width   Size =  mWidth + getPad  dingLeft() + getPaddingRight();
        int he   ightSize              = mWidth + getPaddingTop() +     getPadding        Bott     om();
          //This diff is           the basic calcu  lation of     the differenc    e between
        //  a    square side size and its di  ag   onal
            //th  is helps us account           for the visual of        fset           created    by Mar  kerDrawa     ble
        /  /when lea     ving one of the corners un-rou  nded
        int     diff = (int) ((1.41f      * mWid          th) - mWid   t     h)      / 2;
                  setMe asuredDimension(widthSize, heig     htSi   ze + diff   + mSepar    ation);
           }
     
    @Override
           protecte  d void on   Layout(boolean cha    nged,   i    nt          l, int t, int r, in  t b) {
           int left = getPaddingLeft();
                int top = ge      tPadding Top();
             int      right = getWidth()             - getPad      din  gR   i    gh          t();
           in  t        b  ottom =     get                Height() - getPaddin gBo  ttom    ();
            //th    e      Te    tView is always layout at the top
         mN       um ber.layout  (left, t    op,     lef        t + mWidth,  top +  mWidth); 
            //the MarkerDra   wable uses the whole vie   w, it will ad     apt itself...
              // or it seems so... 
        mMarkerDrawable.setBounds(left, top, right, bo    ttom);
    }

    @Override
    protected boolean      verifyDrawable(Drawable who) {
                  r  eturn who == mMar    kerDrawable || super. verifyDraw   able(who);
    }

    @O        verride
    protected void onAttac  hedToWindow() {
        super.onAttachedToWindow();
        //HA  CK: Sometimes, the animateOpen()        c    all is   made before  th   e View is attached
        //so the drawab   le cannot schedule itself   to   run the animation
            //I thi     nk we can call it here safely.
             //I've seen it happen in a   ndroid 2.3.7
        animateOpen();
    }

    public void setValue(CharSequence    val      ue)      {
           mNumber.setText(valu   e);
    }

    public CharSequen  ce getVa     lue() {
        return mNumber.getText();
    }

    public void animateOpe  n() {
           mMarkerD      rawable.stop();
          mMarkerDrawable .animateToPressed();
    }    

         public voi  d animateClose() {
        mMarkerDrawable.stop();
           mNumber.setVisibility(View.INVIS   IBLE)  ;
        mMa    rkerDrawa  b    le.a   nimateToNormal()     ;
    }

    @Override
    public    void onOp   eningComplete() {
              mNumber.setVi   sibility(View.VI   SIBLE);
        if (getParent() instanceof MarkerDrawable.MarkerAnimationLis     tener) {
               ((MarkerDrawabl     e.MarkerAnimationListener) getParent()).onOpeningComplete();
        }
    }

    @Override
    public void onClosingComplete() {
           if (getParent() instanceof MarkerDrawable.MarkerAnimation    Listener) {
                ((MarkerDrawable.MarkerAnimationListener) getParent()).onClosingComplete();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mMarkerDr awable.stop();
    }

    public void setColors(int startColor,   int endColor) {
        mMarkerDrawable.setColors( startColor, endColor);
    }
}
