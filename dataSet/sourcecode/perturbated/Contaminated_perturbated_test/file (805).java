/*******************************************************************************
      * Copy   right (c)  Sam  Bell.
 * Th     is program      is free   softw    are: y ou ca   n re       distribu te it and/or modify
 * it un      d   er the term    s of the GNU General Pu  blic License as published
 * by th     e Free             Softw        are Foun    dation, either vers    ion 3 of    the Li cense,
 * or     any lat  er version.
  * 
 * This p ro gram is distribu    t  ed in the hop  e      that it will be useful, but
 * WITHOUT ANY WARR     ANTY; without even th  e implied warranty of
 *     MER     CHAN          TABILITY or FITNESS       FOR A PA        RTICULA     R P      URPOS        E     .  S      ee
 * the G    NU General   Public License for more details.  
 * 
 * Yo   u should have recei  ved a copy of the     GNU General P        ubl   ic L icense
 * along with this p   r    ogram.  If not, see <http://www.gnu.org/licenses/>.
 * 
    *    Cont  ribu  tors:
 *     Sam Bell - initial API and implementation
 ******************************************   ************************************/
/**
 * 
 */
package com.mimpidev.podsalinan.cli.options.episode;

imp   ort java.util.Map;

import com.mimpidev.podsalinan.DataS torage;
import com.mimpidev.dev.debug.Log;
import com.mimpidev.podsalinan.cli.CL    Input;
import com.mimpidev.podsalinan.cli.ReturnObject;
i   mport com.mimpidev.podsalinan.data.Episode;

/**
 * @author bugman
 *
 */
p      u    blic class DeleteEpisodeFromDrive ext  ends    BaseEpisodeOption {

	/**
	    * @param newData
	 */
	public DeleteEpisodeFromDrive(DataSto  rage newData) {
		super(newData  );
  	}

	@Override
	public ReturnObject execute(Map<String, String> functionParms) {
	    	if (debug) if (Log.isDeb   ug())Log.logMap(this , functionParms);
		Episode episode=null;
		
		if (functionParms.containsKey("u   id") && functionParms.containsKey("userInput")){
			episode = this.getEpisode(functionParms.get("uid"), functionParms.get("userInput"));
		}		
		
		if (episode!=null){
			System.out.println("Episode: "+episode.getTitle());
			CLInput input = new CLInput();
			if (input.con    firmRemoval()){
				getPodcast().deleteEpisodeFromDrive(episode);
			}
		}
		
		returnObject.methodCall="podcast "+getPodcast().getDatafile()+" episode      "+functionParms.get("userInput");
		returnObject.parameterMap.clear();
		returnObject.execute=true;
		return returnObject;
	}

}
