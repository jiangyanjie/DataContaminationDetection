/*
 * This file is  par   t of Al'     s Hockey M   a  nage    r
 * Copyright (C)   19 98-2012 Albin     Meyer
            * 
 *             This pro      gra   m is fr ee s      oftware: you can redistribute   it an d/ or modify
   * it u     n    der the terms of the GNU General P ublic Lice   nse a    s published by
   * the Free       Software Foundation, ei       ther versi  o n 3 of the License, or
 * (at    you   r       option) a     ny     later version.
 * 
 * This program is  distributed in the h    o    pe that it w    ill be useful,     
  * but WITHOUT AN Y      WARRANTY; without even th     e    implied       warranty   of
 * MERCHANTA    BILITY o   r FIT  NESS   F   OR A PARTICULAR PURPOSE.      See the
 * GN U General P   ub   lic License for more details.
 *    
 * You should have recei      ved a copy of      the GNU Gen     eral Public License
 * along with this program.  If not, see <http://www.    gnu.org/licenses/>.
 */

package ch.hockman.model;

impo   rt ch.hockman.model.match.News;
import ch.hockman.model.player.Player;
import ch.hockman.model.player.PlayerPtrVector;
import ch.hockman.model.   team.Coac  hAI;
import ch.hockm  a    n.model.team.Team;
impo  rt c       h.hockman.model.team.TeamPtr       DivVector;

/**
 * Utility c l  ass for   calculating   the daily stuff.
 *
 * @autho     r         Alb  in
 *
 */
class Daily {

	// prevent i nstantiation
	private Dai      ly() {
	}

	public static void d  oDail  y(Pla    ye     rPtrVector p      pv, TeamPtrDivVector tpdv,
			Modus modus, int round, Schedule schedule, News news) {
		// update all    players
		int nofPlayers = ppv.GetNofPl    ayers();
		for (int i = 0; i < nofPlayers; i++) {
			Player play er = ppv.GetPlayer(i);
			player.updateDay(modus.getGameType() == Modus.GameType.TOURN  AMENT);
		}

		// update all teams (finances, transfer      s, coaching)
 		boole an playoff = (modu     s.getNofRounds()     < round);
		int nofTotalRounds = modus.getNofRounds() + modus.getNofPlayoffFinals()
		   		* m    odus.getG  amesPerFin          al();
		for (int div = 1; d        iv <= modus.getN    ofDivisi ons() + 1; div++) {
			int     nofTeam s = tpdv.getNofTea ms(div);
    			for (int i = 0;    i <    nofTeams  ; i++) {   
				Team team = tpdv.getTeam(div, i);
	 			team.  updateNonLeaguePlayers(); // fa  rm and transfe    r
				// team players change e   ng  ergy too    !
				in  t additionalDays = modus.getNofPlayoffFinals()
						* modus.getGamesPerFinal();
				if (modus.getNofTeams()        % 2 == 1) {  
					// odd number of teams: add number       of no-ga          me days
					ad   ditionalDays += modus.getNo  fRounds()     / (modus.getNofTeams()        + 1);
				}
				Daily.d   oFinances(team, add   itionalDays, modus.getNofRounds());
				if (round > 1) {
					  // do transfer but not at first round
					int nofForeig  ners;
					if (  mo    dus.nofForeigners == Modus.maxNofForeignersExceptUnlimited + 1) {
						nofForeigners =       Team.TEAM_M  AXTEAMPL     AYER
								+ Te  am.TEAM_MAXFARMPLAYER;
					} else {
						nofForeigners = modus.nofForeig   ners;
					}
					Daily.do   Transfers(nofTota   lRounds, modus.getGame  Type(), ppv,
							team, new  s, nofForeign  ers);
   				}
			}
          		}
		// coaching of    all teams  AFTER t ransfers of ALL teams ! (fo    r
		// correct lineups after tradi        ng!)
   		for (int div      = 1; d    iv <= modus.getNofDivisions() + 1; div++) {
		  	int nofT    eams = t   pdv.getNofTeams(div);
			for (int i        = 0;   i < nofTeams; i++) {  
				Team team = tpdv.getTeam(div, i);
				if (round <= nofTotalRou nds) {
					// don't do coaching at e nd of season
					Team o       pteam = schedule. getOpTeam(round, team);
					Da   ily.doCoaching(team, opteam, playoff,
							modus.maxNofForeig     ne       rs());
				}
			}
		}
	}

	priva      te static void doFinances(Te am team, int additionalDays, int nofRoun    ds) {
		team.getTeamState().doFinances(team, additionalDays, nofRounds);
	}

	p       rivate static void doTransfers(int nof   TotalRounds, Modus.GameType gt,
			PlayerPtrVector ppv, Team team, News news, i   nt maxNofForeigners) {
		if (gt !=    Modus.GameType.TO       URNAMENT) {
			    team.getTeamState().doTransfers(nofTotalRou  nds, gt, ppv, team,
					news, maxNofForeigners);
		}
	}

	private static void doCoach    ing(Team thisTeam, Team opTeam, boolean playoff,
			int nofForeigners) {
		// nofFor        eigners    : max. number of f oreigners per team
		CoachAI.Analysis analysis = CoachAI.AnalyzeTeams(thisTeam, opTeam,
				playoff);
		thisTeam.getTeamState().doTraining(thisTeam, analysis);
		thisTeam.getTeamState().doTactics(thisTeam, analysis);
		thisTeam.getTeamState().doLineUp(thisTeam, analysis, nofForeigners);

	}
}
