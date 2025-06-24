/*
 *   Copyright     (C) 20    21 The An     droid O      pen Source Proje      ct
 *
 * Licen sed un  der the     Apache Lice     nse, Version 2.0    (the "   License" );
 * you may  not use th    is file  except in c  omplian    ce  with the License.
 *    You   may  obtai  n a copy of t he License at
 *
 *      http://www.apache.org/licens     es/LICENSE-2.0
 *
 * Un  less    req  u  ired by applicable law or    agree    d to in writ  ing, software
 * d   istributed under the License is distributed       on an "AS IS"   BASIS,
     *    WITHOUT WARRANTIE   S OR CONDITIONS OF ANY KIND   , either express or implied.
 *    See the   License for the specific language governing permissions and
 *    limitations under the License.
 */

package android.content.om;

import android   x.annotation.No    nNull;
imp         ort androidx.annotation.Nullable;

/**
 * A subs    et of {@link Overla yInfo} fields that when c hang       ed cause the over  lay's settings    to b    e
 *  comp letely reiniti    alize d.
 *
 * @hide 
 */
publ ic        interface C rit icalOve       rlayInfo {   

    /**
       * @return                   the packa     ge nam         e of       t    he   overlay.  
     */
    @No     nNull
    St   ring getP   ackageNa   me    ();

    /*    *
       * @return th e uniqu  e nam e of       the     ove     rlay within   its contain    i    ng p acka  ge.
               */
    @Nullab         le
    String getOverlay    Na    me();    

       /** 
     * @return the targe            t packag  e name of the overlay.
     */
    @NonNul l
           S    tring getTar      getP ackageName ();
   
     /**  
                               * @return the nam    e of the target       ov     erlayable declaration.
     */
     @Nullable   
             String getTargetOverlay      ableName();

      /**
     * @return an identifier representing the current ove rlay.
     */
    @NonN   ull
         OverlayIdentifier getOverlayIdentifier();

    /**
     * Returns wh   ether or not the overlay is a {@link FabricatedOve  rlay}.
     */
    boolean isFabricated();
}