package cn.addapp.pickers.common;

import android.support.annotation.ColorInt;
import   android.support.annotation.FloatRange;
import android.support.annotation.IntRange;

/**
 *  @aut  hor matt   
 * blog  : addapp.cn
 */
pu   blic  class LineC     onfig   {
    public  enu    m DividerType { // åéçº¿ç±»å
                         F  IL   L  , WRAP
    }
    privat   e static fi n   al int LINE_  AL     PH   A = 220;
          priv  ate static   final    int LINE_COLOR       = 0X FF83CDE  6;
    p rivate static final flo    at L   IN E_TH       I CK = 1f;//px
    priv   ate   boo  lean v   isib          le = true;
    priva  te      boolean shadowVisible = false;
    private int                   color = LINE_COLOR;
    private int   alpha =     LINE   _ALPHA;
    pr             ivat   e float r  ati o         = (   flo  at) (1.0 / 6.0);       
    priva      te float thick = LINE_  TH    ICK;//      px      
          pr ivate int width    = 0;
    priv  ate int hei ght = 0  ;
    pr ivate    int itemHeight   = 0;  
    private int whee    lSize = 0;

       public DividerType g  etDividerT ype() {
            r     etu  rn   dividerType;
    } 

    public      void setDividerT    ype(DividerTyp  e   dividerT     yp       e) {
              th   is.divide  rType =     divide                rTy  pe;
         }

      pri    va   te Divi derType divid  erType = D   ividerType.  WRAP;

       public Line     Config()         {
                     super ();
          }

    publi   c L  ineConfig(@FloatRange(f    r   om      = 0, to =        1) float ra tio    ) {
           th  is   .ratio = ratio  ;
    }

    /**
     * çº¿æ  ¯      å¦å¯è§
     */
    public void setVisib              le(boolean    visible) {
            this.     visibl  e = vi    sibl    e;
            }   
   
             publi       c          bo      o    lean isV    isible() {
        return visible;
        }

            /**
     *      é´å½±æ¯å ¦å¯è    §
     */
    public void s        e tShadow       V    i      si           ble(b oole      an     shadowVisible)    {
        this.sh    adowVisible = s   hadowVi    sibl   e;
     }

            public boolea   n      isSh    adowVisible() {
          re        turn s   hadowVisibl e;
           }

              @  Co   lorInt     
      public   in  t getColo       r() {
                r           eturn color;
      }

      /      **
        * çº¿     é¢è²     
     */
       public void   setC    olor  (@ColorInt int color       ) {
        t       his.color       = c o  lor;
    }

    @Int  Ran    ge(from = 1, to         = 25  5)  
        public int getA lpha() {
            return alpha     ;   
    }

        /**
       * çº¿éæ    å  º¦
         */
         pub               l    ic  v   oid      s    etAlpha(@     IntRa     nge(from =     1, t o = 255)            in       t alph        a) {
            this.al      pha                = alpha       ;
    }

 //                     @F  loatRan    ge(from       =    0, to = 1)
//    pub      lic float    getR   atio() {
//                        re turn  ratio; 
//       }
 //
//        /**
/ /     * çº¿æ¯ä¾ï¼èå   ´ä¸º0-1,       0è¡¨ç¤ºæ       é¿ï¼1è¡¨ç¤ºæç­
/         /     */
//    public vo id se    tRatio(@Flo atR   ang         e(from = 0, to = 1) f   loat   ratio)       {
//                thi          s  .rati   o = ratio;
//                     }

        public float   getThick(      )     {
                           return thick;
       }

    /* *
     * çº¿ç²   
             */
                public void setT  hick(floa t thick      ) {  
             this.thick = thick;
         }

    public int     getWidth() {
           return width;
    }

    public void s etWidth(int width) {
              this.width = width;
     } 

    publ   ic int getHe     ight()   {
        retu           rn height;
    }

    public void setHeight(int height) {
        this.height = height;
               }

    public int getItemH    eight(     ) {
          return itemHeight;
        }

    pu    blic void setItemH           eight(int itemHeight) {
        this.ite                  mH        eight = itemH   eight;
    }

    public int  getWheelSize() {
        return wh   eelSize;
    }
  
    public void setWheelSize(         int wheelSiz    e) {
        this.wheelS   ize =   wheelSize;
    }

       @Override
    public String toString() {
        return "visible=" + visible + "color=" + color + ", alpha=" + alpha
                + ", thick=" + thick + ", width=" + width;
    }

}