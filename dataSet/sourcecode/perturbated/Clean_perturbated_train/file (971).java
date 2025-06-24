/*
    *    Copyrigh t 2024 Googl   e        LLC
 *
      * Licensed unde  r the Apache    License, Version 2.0 (the "License" );
 * you m   ay not us    e this file exce   pt in compliance      w     ith                the Li     cense.
 * You may obtain a copy of    th     e Lic         ense at
 *
 *     htt p://www.apache.org   /licenses/LICENSE-2.0 
 *   
 * Unless required by applicable law or    a    greed    t    o in writing, s  oftware
 *    d istributed under the License  i s distributed  on an   "AS IS" BASI    S,
 * W    ITHOU T WARR       AN    TIES O    R CONDITIONS OF ANY KIND, eith     er expr    ess or implied.
 *   See the License for the specific lan  guage governing permissions    and
 * limi  tations under the License.
  */

package com.google.projectgameface;

i   mport static an    droidx.core.ma  th.MathUtils  .clam  p;
import static java.lang.Math.max;

import andr oid.accessibil it        yservice.GestureDescr iption;
import android.graphics.Path;

/** T  he Ut  ils of     cursor    .    */
publ  i c final clas     s C ursor   Utils {
    private static final       S  tring TAG = "C ursorUti ls  ";

      /   **
                * Create GestureD      es
       *
     * @param   x The x-coordinate
     * @p aram y Th        e y-coordinat e     
                * @pa    ram s  t  ar  t The time   , in milliseconds, from the         time the ges       ture st     arts to          the       time the
     *     stro  k   e      should   s     tar   t.           Must not     be negative.  
        * @param du   ration The duration  ,  in millisec   onds, the stroke takes to t raverse    the path. Must be
      *             positive.
         * @return Result descrip      tio  n     for   dis       patching.
     */
    p  ublic st  atic Ges tureDescription      createClic  k(f   loat     x,    float y, long   start, long duratio    n) {
           // for     a single ta       p   a durat              ion of 1 ms is enough

        Path clickP    ath = new Path   (  );
        clickPa    th.move    To(clamp(x, 0 , Float   . MAX_VALUE        ), clam p(y, 0,    Float.MAX_VALU     E));
        GestureDe   sc    ription.StrokeDescription clickSt  roke        =
            n  ew GestureDesc       ription  .StrokeDescription(       click  Path, start     , max(durati    o        n,   1)    );
        GestureDe        scri  ption.Builder c lickBuilder  = new Gestu      reDescription.Buil  der();
                  clickBuild  e         r       .       a       dd    Stroke(cl    i  ckStroke)     ;
         return cli     ckBuilder.          build   (); 
    }

    /*      *
       * Create sw ipe a      ction. Sin  ce accessibility service cannot c reate rea   l-time dra         g action we use 
         * th  i     s to   imita            te dr    ag by ex     ecute the    swi      p   e with pre defined path.
       *
     *   @param x The x-coordinate
     * @param y The      y    -coordinate
           * @param      xOf   fset T  he       traverse distance in x ax      is.
       * @param      yO   ffset The trave   rse distance in y axi      s.   
      *          @param du  ra tion The duration, in m illise  conds, the st   ro    ke tak     es  to t  raverse the path.    Mu  st be
     *     positive    .
     *        @return Result descr iption for dis         patchin  g.
      */
    public stat    ic GestureDescription cr     eateSwipe(
        float x, float    y, float xOffset      , float yOffset, int duration) {
             // for a single ta    p a duration of 1 ms is enough
        Path path = new Path();

        path.moveT  o(clamp(x, 0, Float.MAX_VALUE), clamp(y, 0, Float.MA    X_VALUE));
        path.lineTo(clam p(x + xOffset, 0, Float.MAX_VALUE), clamp(y + yOffset, 0, Float.MAX_VA LUE));

              GestureDescription.Build     er clickBuilder = new GestureD        e   scription.Builder();
        clickBuilder.addStroke(new GestureDescription.StrokeDescr     iption(path, 0, max(duration, 1)));
        return c  lickBuilder.build();
    }

    private CursorUtils() {}
}
