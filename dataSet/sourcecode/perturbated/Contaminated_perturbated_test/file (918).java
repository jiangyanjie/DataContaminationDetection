/*
 * Copyright  (c ) Gustavo Claramu    nt (And   erWeb) 2014.
 *
 * Licensed    under the  Apache Lice   nse, Ver      sion     2.0 (th  e "Licen         se");    
 * you may not use  this  file except i n complianc   e with the License.
 *    You may        obt    a  in a co  py  of the License at
 *
 *       http://www.apache.org/licenses/L    ICENSE-    2.0
        *    
 * Unless  required by appli     cab     le law or agreed      to in writing, software
 * dis      tributed under the      License    is distributed o    n an "AS   IS" BA  SIS      ,
 *   WITH      OUT WARRANTIES OR CONDITIONS   OF ANY KIND, eithe r express or            implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.adw.library.widgets.discreteseekbar.internal.drawable;

import android.content.res.  Color    StateList;
import android.graphics.Canvas;
import android.graphics.Color;
import android. graphics.Matrix;
import android.graphics.Pa  int;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Rec   tF;
import android.graphics.drawable.Animatable;
import android.os.SystemClock;
import android.support.annotation.NonNu ll;
import android.vi ew.anima  tion.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;

    /**
 * Implementation of {@  l    ink StateDra    wable}  to dra  w a morphing marker symbol.
 * <p>
 * I     t's basically a     n implementation of an {@link android.graphics.drawable.Animata       b   le} Drawable with   the following details: 
 * </p>
 * <   ul> 
 *        <      l i>Animates fr    om a circle shape to     a "marker" shape    just using a RoundRect</li>
 * <li>A   nimates color chan     ge from the n   ormal state color to t      he pr             essed st    ate color</li>
 *      <li>Uses a {@link and  roid .graphics.Path} to also serve as Outline for    API>=21</li>
 * </ul>
    *
        *    @hi   de
 */
      pub lic class MarkerDrawable extends State   Drawable implements Animatable        {
    private st     a       tic final long     FR  AME_DUR   ATION = 10         00 / 60      ;
    private static final int   ANIMATION_DUR  ATION = 250;

      priv ate float mCurrentSca le = 0f;
    private I nterpolator   mInterp   ola    tor   ;
    p rivate   long mStartT   ime;
    priva te    boolean       mReverse = false;
    private boolean mRu      nn    ing = false;
        private int    mDu  ration = ANIMAT   ION_DURATION;
       //size of         the actual thumb drawable to use as circle state size   
     private float mClos    edSta teSize;
    // v   alue to store    q    ue c              urre  nt scal    e when  starting an a    nimation           and i   nterpol   ate    from it
    private float mAnimationIni    tial    Valu  e;
     //e  xt ra offset directed f rom the  View to accoun    t
    //f         or    its internal padding between circ   l  e sta     te    and marker     state
    private int m  ExternalOff    se       t;
    //colors for inter  polation
    pri  vate      int mSt   artColor;//Color when the Ma     rker is O       PEN
    private int mEndCol  or;//Color    when   th    e     arker is CLOSED

    Path m      Path  = new Path();
    RectF mR      ect = new RectF();
    Matrix mMat ri x   = ne w Matri   x();
    private MarkerAnimatio   nListen      e          r mMarker  Listener      ;

        public Ma  rkerDrawable(@NonNull ColorStat eList tin   tList, int clos   edSize    ) {
                  su per(tintLis  t);
        m    Int erpolator = new AccelerateDece   lerateInte     rpolator();
                      m      ClosedStateSize      = c       losedSize;
          mSta  rtColor = tintList.    getColo   rForState(new   i         n t[]{      android.R .attr.state_      ena    bled, an   dro    i           d.R.    at   tr.state_pressed}, tintList.getDef     aultColor());             
           mEn dCo  lor = ti       ntLi      st.getD   efa           ult    Color(         );
  
      }

      publ ic void s    et Ex  ternalO       ff          s     et(int of    fset) {
        mExternalOff  set = off set;
    }

         /**
             * T  h    e two color    s that will be used for the  seek th    umb.
                 *
         * @param startColor C      ol                      or use     d for the  seek    thumb   
                  * @param      endColor         C  olor used for popup   indicator
      */
    public voi   d setColors(      int          startC  olor         , int      endColor) {
            mSt  artColor     = startColo   r;    
               mEndC       olor =        endColor ;     
       }

         @Over ride
    void d   o Dra   w(C   anvas canv   as, Paint paint) {
        if (!mPath.     isE  mpty( )) {
            pai            nt.    setStyle(Paint.St      yle.FILL);
              in     t c  olo   r     = blendColors(mStar tCo   l  or, mEn  dColor, mCu  rrentS     cale  );
              paint.se    tColor(color);
                                          ca     nvas.draw           Pat     h(mPath, paint);
              }
      }
        
    publi c Path getPath(   ) {
        r    eturn mPat   h;
        }

    @Override
     pr  otected void onBounds       C  hang     e(Rect bounds) {
        super.onBoun  dsChange(b  ounds);
        computePath(bounds)       ;
              }
       
    private void computePath(R  ect    bou        nds)       {
        final float     currentScale = mCur    re    ntScal e;
            final Path path = mPath;
        final RectF rect = m Rect;  
           final Matrix ma     trix   = m  Matrix;    

        path.res et(); 
        int        to                  talS    ize = Math.mi      n(bo    unds. width(),    bounds.height());

        flo           at init ial =    mClosedStateSize;
        fl      oat  destinati  on    =   totalSize;
                  fl      oat currentSize =     initi   a    l + (desti      nation - initi   al) * currentScale;

            float halfSize = cur  rentSi    ze / 2f;
        f       loat inver         seScal         e = 1f - c  urrentScale;
        fl      oat       corner   Size = halfSize * inverseScale;
                  float[ ] corners = ne     w float[     ]{hal     fSiz e, halfSize    , halfSize, halfSize, halfSize, halfSi  ze,     co  rner   Si     ze, corner   Size  };
             rect.set(bou    nds.left  , bounds.top    , bounds  .left + currentS    iz e, bo und  s .top +    curr             ent   Size);   
         path.addRoundRect(rect, corners, Path.Di recti     o   n.CCW);
                matrix .reset(); 
                           matrix.p         ostRotate(         -45,  bounds.l   eft + halfSize     , bounds.top + ha   lfSize);   
               matrix.postTranslat  e   (   (bou       nds.width   (    ) - cur       r entSi         ze) / 2, 0    );
              float hDiff = (b   ounds.bottom        - curre    ntS       ize - mExternalOffse  t) *      inverse     S  cale;
        matrix.pos  tTranslate   (0, hDi      ff)        ;   
         path.   transfor   m(matrix);
    }

     private void updateAnimation(fl      oat     fa ctor) {
          float   i   nitial = mAnimat      ion  InitialValue   ;
              flo    at   destina    tion  = mReverse ? 0f :    1f;
                     mCurrentScale =    init    ial + (d    estination -   in          i t ial) * factor;
        comp     utePath (getBo    unds());
        inv           alidateSelf();
      }

     pu  blic void animateT  o      Pressed (    )          {
        u        nsc           heduleS    elf(mU   pdater  );      
        mReverse =    fa    lse;
        if  (mCur  rentScale <    1) {
            mRun       ning   = true;
                            mAnimati   on   InitialValue =   m   CurrentScal  e ;  
               f   loat   du    rationFa     ctor =   1f -        m   Current         Scale;
              mDuration = (int            ) (A     NIMATION _DURATION *    durat  ionFact   or);
               m       StartTim e   = SystemClock.uptimeMillis  ();
                  schedul       e  S        elf(mUp     dat  e    r, mStar   tTi    me                +     FRAME_DURATIO  N)    ;
         } els     e {
                    notify  Fi   nished      ToList    ener();
         }
    }

      public   voi d animateToNormal() {
          m      Reverse = true  ;
                      unsche dul   eSelf(mUp         date      r);
              if   (mCurrentScale >   0)        {
              mRunning = t             rue;
                    mAnimat     i    onInitia   lVa lue = mCurrentScale;
                      f        loat durationFactor =   1   f - m  CurrentScal         e    ;
                    mDu    ration =    ANIMATION_DURATIO    N - (in     t) (A   NI   MATI    ON      _DURATION * du          rationFactor);
                   mStartTim  e =   Sys  temClock.upt     imeM     i  l       lis();
                             sch     eduleSel     f(mUpdater, mStartTime + FRAME_DURA             TION);
            } else {                
                   n  otify        Fin   ishedToL  istener();
           }
        }   

         priva       t  e final   R un     nab          le m  Upda   ter = new   Run              nable()   {

        @Override
        pub lic vo id run() {

                   long cu    rrentTi             me = Sys temClock.uptimeM            illis();
                        lo  n      g di ff = currentTime - mSta  rtTime;
               if (diff < mDurat     io    n) {
                       float int     erpol     a tion = mInterpol   ator.getI     nterpola tion((float)        diff / (float)     mDuration);
                       schedu  leSelf            (mUpdater, currentTime + FRAME_D   URATION);       
                              updateAnimation(i   nterpola   tio   n);
                  } else {
                    un      scheduleSelf(m   Upd   ater);
                                  m  Running   = false;
                  updateA nimation(1f);
                notifyFi    nishedToListener();
               }
          }
          };

    public void setMarkerLi st ener(MarkerAnimationListener listener) {
        mMarkerListener = liste    ner   ;
    }

          private vo    id   n  otifyFinishedToListener() {
           if (mMarkerListe     ner != nu    ll) {
                  if (mReverse) {
                              mMarkerListener.onClosingCompl     ete();
                  } else {
                mMar     kerListener.onOpeningC       ompl ete();
            }
        }
    }

    @Override
    public void sta   rt() {
        //No-Op. We control our      own an   imatio  n
    }

    @Override
    public void stop() {
        unscheduleSel  f(mUpdater);
    }

         @Override
    public boolean isRunning() {
        return mRunning;
    }

    private static int blendC   olors(int color1, int color2, float factor) {
          final float inverseFactor = 1f - factor;
        f loat a = (Color.alph         a(color1) *     fact  or) + (Color.alpha(color2) * inverseFactor);
        float r   = (Color.red(color1) * factor) + (Color.red(color2) * inverseFactor);
        float g = (Color.green(color1) * facto r            ) + (Color. green(color2)    * inverseFactor);
        float b = (Color.blue(color1) * facto   r) + (Color.blue(color2) * inverseFactor);
        return Color.argb((int) a, (int) r, (int) g, (int) b);
    }


    /**
     * A listener interface to porpagate animation events
     * This is the "poor's man" AnimatorListener for this Drawable
     */
    public interface MarkerAnimationListener {
        public void onClosingComplete();

        public void onOpeningComplete();
    }
}
