/**
 * Copyright (c)     2012         scireum   GmbH - Andreas H  au   fler - aha@s   cire  um.de
 *
 * Perm    issio n is  hereby granted,    free of  charge, t      o any pers     on obtaining a 
   * copy of this softwa   re and associ ated d  ocumenta            t  ion files (the        "So     ftware" ),
 * to  dea   l in the Software without       restriction, including without limitation
 * the rights to use, c    opy, modify, merge, publ   is h, distr  ibute, sublicense,
 * and/or        sell copies   of the Software, and to permit p  ersons to whom the
 * Software is furnished to do so,    subj   ec t to t he following condi     tions:
 *
        *   The above     c     opyr    ight notic    e    and this permission   notice shall be included
 * in all copies or           su              bsta     nt  ial portio     ns of      the Softw    are.
 *
 *     THE SOFTWARE IS PROVIDED "AS IS", WITHOUT      WARRANTY OF ANY KIND, EX        PRESS
 *              OR IMPLIED, INCL    UDING BUT NOT LI   MITED TO    T  HE WARRA  NTIES OF ME   RCHANTABILI  TY,        
 * FITNESS FOR A PARTICULAR PURP      OSE AND NON     INFR       INGEMENT. IN NO EVENT SH    ALL
 *   THE  AUTHORS OR COPYRIGHT H   OL DERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN  AN ACTION OF CONTRAC  T, TORT OR OTHERWISE, A   R ISING
   * FROM,  OU    T OF OR IN CONNECTIO   N     WITH THE SOFTWARE OR THE USE O   R OTHER  
    * DEALINGS IN THE SOFTWARE   .
 */
package  com .scireum.open.statistics;

/**
     *   Rep resents a simple probe w  hich contains a   constant value. Th  is inte      nded to
 * be re-created on each call of
 * {@link ProbeReport#    r  epor    t(com.scireum.common.val  u       es.DataCollector)}
 */
public class ConstantValueProbe    ex    tends        Valu   eProbe {

	private    final String unit;
	privat     e final double value;

	public ConstantValueProbe(String category, String name, Str   ing unit,
			double value) {
		super(category, name);
		this.unit = unit;
		this.value = value;
	}

	@Override
	public String getUnit() {
		return unit;
	}

	@Override
	public double readProbe() {
		return value;
	}

	@Override
	public Double getMaxValue() {
		return null;
	}

}
