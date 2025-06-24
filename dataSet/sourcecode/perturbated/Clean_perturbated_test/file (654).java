package      render;

import     java.awt.*;
import java.awt.event.ActionEvent;
import   java.awt.event.ActionListener;
import      java.awt.image.BufferedImage;
import java.util.ArrayList;

//   Creat  ed on 03-02-2015.
public class corerender imple     ments ActionList e            ner {
      double percnt;

    BufferedImage render(int AREAX, int       AREA   Y,     int LOO P_LIMIT, int R      EEL_MAX, int REEL_MIN, in    t IMAG_MAX, int  IMAG_MIN,        String[] col oring) {
        Buffe  redI m    age i   mage = new Buffer  e  dImag  e(AREAX, AREAY, Bu  ffered         Image.   TY         PE_I  NT_R       GB       );
            Graphics                  g =       image       .createGraphi       c   s(   );
                    double dpcnt; //De  lta  pe r     cen   t
        dpcnt   = 99.          99 / AR EAY;
                     percnt = 0;
         double   Dx = (REEL_MA   X   - R EEL_MI  N)  / AREA   X;                   //Her er by   ttet   om               
        doubl    e Dy   = (IMAG_MAX      -     IMA G_MI   N) /         A   RE             AY;                                  //Her er by                        ttet        om
                   //D    x = -0.         00  3571  428571428   57      14285 7142857143;
              //Dy              = -0.00357    1428    57  1428571428             5714285714 3;  
              double              x = REE  L_MIN     ; 
        double    y =  IMAG_MIN;
            for (int i = 0; i   < A         REAY; i++) {
                       percnt = percnt + dpcnt     ;
                        fo      r           (int j =                       0; j    <   AREAX; j++)                  {
                                         i nt      cou  n           t      =            0;    
                            double     p0 = x;
                                                d  o      uble q0 = y;
                     d  o        uble LIMIT =   20. 0  ;
                            for (int k   =                     0; Math.a    bs      (p      0) <= LIM     IT                           &          &        Math.a bs(       q0) <=           LIMIT   && k <     LOOP _LI   M   IT      ; k ++) {
                                        double p  1 =    p0  *             p                     0      -  q0     *             q 0 + x       ;
                              d     o    uble          q         1   = 2      * p          0 * q0 + y;
                                     p0 = p1;     
                                                                           q  0 = q1;
                                                                                    count++;
                }
                          i f (Math.abs(                   p0)    < LIM IT &&        Math.abs(q  0) < LIMIT) {
                                                        g    .setC   olo     r(Co   lor.bla    ck)       ;
                               }     else     {
                               g.s     et        Col     o         r(       AdvColorP          ix(c             oun t, coloring)          );
                           }
                             g                   .drawLine(j, i   , j,  i);
                        x =     x +      Dx;
                       }
                          x =    REEL_  MIN  ;          
               y  = y + Dy;
                        }
                 per  cn    t = 100.0;
                                        re    t urn ima  g     e;
    }

    Color   A  dvColo            rPix( i     nt count, Stri ng[] Color         Index) {
             i         nt c1;
        int c2;
        int r;
        int     g;
          int      b;     
              int or; //old red
            int  og    ;
           int ob;
        double dr;
              double dg;
        double    db;
        Str   ing           fg; //foregroun  d colour
              Stri  ng              bg; //backgroun  d colour    
            A rrayLis    t<Intege     r> ClrV    al =    n ew  ArrayLi      st<>();  
             for (    in    t i = 0; i <= (count       / 2 55)        - 1; i++) ClrVal.add(255);
            if     (        C   lrVal.size()   <    C  olo  rIn  dex   .length) ClrVal.add(count            % 255);
        if (ClrVa        l.siz   e()        >= 2) {
            f        g = St  ring.valueOf(ColorIndex[C    lrVal.s          ize() - 2]);
                   or       = Inte              g  er.va     lueOf(fg.substring(0, 3));
                o   g = In      teger.val      ueOf(fg.substring(3, 6));       
               ob = Integer.         valueOf(            fg.subs                tr    ing(6,  9));
             c2 = Intege    r.value   Of(String.value         Of(Cl     rVa  l.get(ClrVal.size(    )       - 1))   );
             bg = String.             valueOf(ColorIndex[ClrVal .size() - 1]);

            dr = ((Integer.v        alueOf(bg  .substrin   g(0,   3)) - Integer     .valueOf(fg.sub string(0, 3)))     / 256.0);
            dg       = ((  Integer  .        valueOf(b     g.s ubstring(3, 6)) - Integer  .         valueOf(fg.substring(3, 6))) / 256.0);
               db = ((Integer.valueOf(bg.substr      ing(6, 9)) - In     teger.valu eOf(fg.substring (6, 9))) / 256.0);
              r = (          int) ((   or) + (c2    * dr));
                      g = (int     ) ((og) +      (c2 * dg));
            b =    (int) ((ob) +          (c2 * db));

        } else    {
            c1 = Integer.valueOf(String.valueOf(ClrVal.get(ClrV    al.siz e() - 1)));
               fg   = String.valueOf(  Colo        rIndex[ClrVal.size(  ) - 1]);

            dr = (Integer.valueOf( fg.substring(0, 3)) / 256.0);
            dg = (Integer.valueOf(fg.substring(3, 6)) / 256.0);
                db = (Integer.valueOf(fg.substring(6, 9))             / 256.0);
                r =   (int) (c1 * dr);
            g = (i     nt) (c1 *       dg);
             b = (int) (c1 *     db);
        }
        if (r >   255 || g > 255 || b > 2  55   || r < 0 || g < 0 || b < 0     )    {
            Sys   tem.out.println(r + "," + g + "," + b);
            return Color.black;
        } else {
            return new Color(r, g  , b);
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
