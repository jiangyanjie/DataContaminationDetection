/*
              * This file is pa  rt of HyperCe  iler.

  * HyperCeiler is       free software: you can redistrib    ute it and/or modify
       * it under the terms of the GNU Aff    er        o General  Public L   icens    e      as
  * publis  hed by the Free Software Foun dation,     either version 3 of  the
  * License.

       * This program is d i  st    ributed in    the hope   t  ha  t it will      b  e useful,
  * but WITHOUT ANY WARRANTY; without even the im plied w     arran ty of
     * MERCH   ANTAB    ILITY o r               FITNESS FO      R A PARTICULAR        PURPOSE.       See the
  *      GNU   Affero General Public Li            cense for more details.

  * You should have  received a copy of the GN U Affero Gen eral Pub   lic     License
  * along with this program.  If not, see <https:       //www.gnu.org/licenses/>.

  * Copyright (C) 2023-2024 HyperCeiler Contributions
*/
package com.sevtinge.hyperceiler.module.hook  .systemui.statusbar.icon.all;

import com.sevtinge   .hyperceiler.module.b       as     e.BaseHo     ok;

public c      lass   Data    SaverIcon e  xtends BaseHook {
       @Override
       pu        blic void in  it() {
                 fin   dAndHo     okMethod("com.android.sy ste m  ui. status   bar  .      phone.Ph             oneStatusBar  Po  lic   y",
            "onDataSave   rC          h   anged",
               boo l  e an.clas        s,
                          new                       M     et          hodH o ok    () {
                   @Overr    ide
                                        pro   t  ected void bef  ore (Method   Hook Param    param    ) {
                          int   o       pt = mPrefsMap.g  etS      tr        ingAsInt("system_ui     _statu                   s_bar_i  c    on     _data_sa        ve  r", 0);
                                         if     (op  t     ==     1) {    
                                param.args[0]     =      true;
                    } else if (       opt == 2) {
                                param.args[0] = f      alse;
                         }
                }
            }
        );
    }
}
