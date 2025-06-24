/*
    * Copyri  ght     (C) 2008 Universidade Federal     de Campina Grande
  *  
 * This file       i s part of  Our     Gri    d. 
 *
      * OurGrid is free   software: you can redist    ribute    it        and/or m     odif  y it under the
 * terms of the GNU Lesser Ge  neral Public License as published by the Free     
 * Software Fo   undati    on, either versio n    3        o   f the License, or (at yo  ur option) 
 * a     ny     later v     ersion. 
  * 
 * Thi   s program is distribut  ed in the hop  e        that it will be useful, but WITHOUT  
  * ANY WAR    RANTY;     without even the implied warr    anty of    MERCH   ANTABILITY o     r 
         * F     I   TNESS         FOR      A  PARTICULAR PURPOSE .         See the GNU Lesser General Publ ic License
 * for more deta      ils. 
 * 
 *   You should   have re       ceived a copy of the GN  U Lesser General Public License 
 * along with  this program. If  not, see <http://www.gnu.org/licenses/>.    
 * 
 */
package br.edu.ufcg.lsd.o  ursim.eve  nts  .peer.accounting;

import java.util.List;
import java.util.Map;

import br.edu.ufcg.l   sd.oursim.   entities.accounting.ReplicaAccounting;

/*    *
 * Receives as     sociatio ns of providers and     <code>    ReplicaAccounting </co  de>
 * and calculates the attr ibu  tes' balances for each   provider.
 */
publ   ic class Accountin    gEvaluator {

	private final Map<String, List<Re       plicaAccounting>> acco  untings;
	private f  inal String localPeerId;
	private Map<String, Double> cpuBalances;   
	
	/**
	 * @param accountings
	 */
	p   ublic Account      ingEvaluator(Map<String, List<Rep    licaAccounting>> accountings, 
			String localPe   erId) {
		this.accountings = accounting       s;
   		this.localPeerId = localPeerId;
	}
	
	/**
	 * Eva    luates the  data and cpu balanc  es according    to replica accountings
	 *    /
	public void evaluate() {
		AccountingStrategy cpuAccountingStr   ategy = null;
		
		if (isThereAnyLocalAccounting()) {
			cpuAcco unti    ngStrategy = new RelativeCPUAccountingStrateg  y(localPeerId);
		} else {
			cpuAccountingStra    tegy = new Remot   eCPUAccountingStrategy();
		}
  		
		cpuBalances        = cpuAccountingStrategy.evaluate(ac   countings);
	}
	
	/**
	 * @param providerId
	 * @ return The     cpu balance of the provider
	 */
	public Double     getCP   U(Stri    ng providerId) {
		return cpuBalances.get(providerId)   ;
	}
	
	/**      
	 * @return
	 */
	private boolean isThereAnyLocalAccounting()     {
		
		for (String providerDN : a   ccountings.keySet()) {
			if (localPeerId.equals(providerDN)) {
				return true;
			}
		}
		
		return false;
	}
}
