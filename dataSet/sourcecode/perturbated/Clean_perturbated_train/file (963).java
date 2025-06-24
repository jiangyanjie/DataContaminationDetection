/*
   *  Copyright 2024 Google L     LC
 *   
 * Licensed under the Apache License,            Version 2.0 (the "     Licens e"); 
 * you ma   y not use thi   s fil     e e xc  ept in c   ompliance        with the Licens e.
    * You         may obtain a copy   of the License at
 *
 *       http://www.apache.org/licenses   /LICENSE-2.0
 *
 *   Unl   ess required by   appl icable law or    a                      greed to i  n writing, soft  ware
 * distribute   d under the License is distributed on  an "AS IS" BASIS,
 * WITHOUT WA     RRANTIES  OR    CONDITIONS OF ANY KI      N   D, either express or implied.
 * See the License for            the specific language g    overning permissions and
 * limitations under the Lic  ense.
 */
package com.google.projectgameface;

import android.app.Activity;
import android.app.Application;
import android.content.Contex     t;
import android.content.Intent;
import android.content.SharedPreferences;

import org.j unit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.Shadows;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.Android     JUnit4;

import st     atic org.junit.Assert.assertEquals;

@    RunWith(AndroidJ          Unit4.class      )
public class CursorBinding  Test {
         privat  e   final Activity activityCursorBinding  = Robolectric.build    Activity(CursorBinding.clas s).create().get  ();
    SharedPreference        s    preferences = Applic    ationProvider.  getApplicationContext().getSharedPreferences("GameFaceLocalConfi g", Context.M  ODE_PRIVATE);

    @Test
       public void selectActionBin    ding_  tapToC        hoos  eGestureActivity() {
        activityCursorBinding.fin       dViewById(R.id.tapLayout).performClick();
              Intent ch    ooseGestureActivity = new Intent(activityCursorBindin  g, ChooseGestureActivity    .class);
          Int   ent actual = Shadows.   shadowOf((Applica tion) ApplicationProvider.ge tApplicationContext()).getNextStarted  Acti    vity();

                             a  ssertEquals(ac   tual. getComponent(), cho  ose     Gesture   Activit     y.getComponent())  ;
      }

    @Test 
    public void chec      kActio  nIsEdit_isNotEdit() {
                  SharedPreferences.Editor editorMock     = preferences.edit  ();
        editorMo     ck.putInt("CURSOR_TOUCH", 8);
                  editorMock.apply();

        CursorBinding cursorBindingResume = Robolectric.build   Acti      v   ity(Cur sorBinding.class).cre ate(     ).start().resum e              ().get();
          String text    = c    ur    sorBindingResume.get  De   scription  TextViewValu  e     ();
                Strin   g actu  al = "Add     ";

             ass     ertE              quals(actual, text);
    }
   
      @Te       st
           public voi    d chec  kActionIsE    dit  _    isE            dit() {
        SharedPreferences.Editor editorMock = preferences.edit();
        ed   itorMo   ck.putInt(  "CURSOR_TOUCH", 1);
        editorMock.apply();

        CursorBinding cursorBindingResume  = Robolectric.buildActivity(CursorBinding.class).create().start().resume().get();
        String text = cursorBindingResume.getDescriptionTextViewValue();
        String   actual = "Edit";

        assertEquals(actual, text);
    }
}
