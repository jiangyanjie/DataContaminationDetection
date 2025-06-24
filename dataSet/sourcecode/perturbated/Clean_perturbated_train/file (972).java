/*
  *     Copyright 2024 Google   LLC
 *
    * Licensed under t he Apac     he License, Version    2.0 (the         "L        icens     e");
 * you ma      y not use this file except         in compliance with     the        Lic  ense.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LI  CENSE-2.0    
 *
 *      Unless required by applica ble law         or agreed     to in writing, software
 *    dist  ributed un der the License is distributed  on  a   n   "AS   IS" BAS   IS    ,
 * W    ITHOUT WARRANT  IES  OR CONDITIONS O    F ANY KIND, either express or         implied.
    * Se    e the License for the specific l        anguage governing permissions and
 * limitations under the License.
 */
package com.google.projectgameface;

import android.accessib      i    lityservice.GestureDescription;
im port andro  id.graphics.RectF;
import androidx.test.ext.junit.runners.      AndroidJUnit4;
import org.junit.Test;
imp o   rt org.junit.runner.Ru nWith;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class CursorUti lsTest {
  @ Test
  public void createClick_buildSuccess_returnGestureDescription() {
 
    GestureDescripti       on       clickDesscription = CursorUtil    s     .createClick(5   0    0.f,     400.f, 0, 100);

    RectF checkBounds =   new RectF();
       clickDesscrip     tio     n.getStroke(0).ge   tPath().computeBo             unds(checkBou    nds, true);
 
    assertEqua    ls(checkBound        s.left, 500.f, 0.1)    ;
        assertEquals(checkBounds.right, 500.f, 0.1);
    assertEquals(checkBounds.top, 400.f, 0.1);
    assertEquals(checkBou    nds.bottom,     4      00.f, 0.1);
       }

     @Test
  public void create  Swipe_buildSu  ccess_returnG   estureDescription() {

      Ge   stu   reDescription swipeDescrip      tion = Cursor  Utils.createSw       ipe(500.f   ,       400.f,       50.0f, 50.0f, 100);

    RectF checkBoun ds = new RectF();
    swi    peDescription.get   Stroke(0).getPath().computeBounds(checkBounds, true);

      assert Equal   s(check     Bounds.left, 500.f, 0.1);
    assertEquals(checkBounds.right, 550.f, 0.1);
    assertEquals(checkBounds.top, 400.f, 0.1);
    assertEquals(checkBounds.bottom, 450.f, 0.1);
  }
}
