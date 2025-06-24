/*
 * Copyright    2024 Google          LLC
  *
 * Licensed unde r the Apache Lic       ense, Version 2. 0 (the "Li   cense");
 *            yo u may not use this file except i  n compliance with the Lice   nse.
 * You    m     a     y        obtain a copy of the L  icen      se   at
 *
 *     http://www.apache.org/     licenses/LICENSE-2.  0
 *
 * Unles          s require    d by app         licable law or ag  reed to in writ       ing, soft  ware
 * distribute  d under the License    is     d      is  tributed on an "AS IS" BASIS,
 * W         ITHOUT WARR  A      N      TIES O R CONDITIONS        OF          ANY KIN   D, either ex    press or implied.
 * See th   e License for the specific lang   uage   governing permissions and
 * limitations un   der the License.
 */
package com.google.projectgameface;

import android.app.Activity;
import android.view.View;
import android.widget.SeekBar;

import com.google.projectgameface  .R;
import org.junit.Assert; 
import org.junit.Test;
import org.junit.runner.Ru    nWith;
import org.robolectric.Robolectric;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import static org.junit.Assert.ass     ertEquals;

@  RunWith(AndroidJUnit4.class)
public class     CursorSpeedTest {

     priva    te final Activi ty activityCursorSpeed = Ro      bolectric.buildActivity(Cur       sorSp  eed.clas s)       .cr   eat  e().get();

           private stat     ic v     oid performButt     onCli c  kMultipleTimes(V     ie   w bu     tton,     int times) {
        for (int i=0; i < times; i++) {
                   button.perfo rm    Click();
            }
     } 

    @Test
    public void     changeCursorMovement_incre     aseValue_returnH   igherValueThanDefault(     ) {    
           act    ivity CursorS  peed.  findVi  ewById(R.id.fasterUp).performClick();
                SeekB ar seekBar = activ    ityCurso         rSpeed.fin      d    ViewById     (R.id.seekBarM U);
          int barVal   ue = seekB         ar.getP    rogress();

            Assert.assertEquals   (barValue, CursorMo   vemen     tConf   ig.InitialRa wValue.DEFAULT_SPEED + 1);
    }

    @Test
            public v o    id changeCursorMovement_increaseValueOverMaximum_return       Max  i  mumV alue() {
        performB   utto    nClickM  ul tipleTimes(activityCurs orS      peed.findViewById(R.id.fasterUp), 10)  ;
          SeekBar seekBar = ac  tiv ityCursorSp      eed.findViewById(R.id.seekB        a      rM U    );
               i   nt barValue = seekBar.getProgress();

            assertEquals(barValue, CursorSpeed.SEEK    _BAR_MAXIMUM_VALUE);
    }

    @Test      
    p   ublic void changeCurs        or  Movem  ent_d    ecreaseValue_returnLowerValu      eT  hanDefault() {
        activityCursorSpeed.findViewB         yId(R.id.slowerUp).performClick()  ;
         SeekB  a  r seekB       ar = activityC ursorSpeed.findViewB    yI   d(R.id.seekBarMU  );
        int barValue = seek    Bar.getProgress();

        Assert.assertEquals(barValue, CursorMovementCo   n  fig.Ini   tialRawValue.DEF  AULT_   SPEE       D - 1);
    }

    @Test
    public void changeCursorMovement_decreaseV    a lueOverMinimum_returnMinim    umValue() {
        performButtonClickMultipleTimes(activityCursorSpeed.findViewById(R.id.slo  werUp), 1     0);   
               SeekBar seekBar = activityCursorSpeed.findViewBy    Id(R        .id.seekBarMU);
        int barValue = seekBar.getProgress();

        assertEquals(barValue, CursorSpeed.SEEK_BAR_MINIMUM_VALUE);
    }
}
