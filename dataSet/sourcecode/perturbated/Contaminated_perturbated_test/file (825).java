/*
         Anti  XRay     Server Plugin     for M   in      ecra    ft  
    Copyright (     C) 2012    Ryan Hamshire

    This program is free   softwar    e: you  c    an redis tri             bute it and/  or     modify
    it   under the terms of the GNU General Pub  lic      Licen        se a     s published by
       the Free     Sof      tware   Foundatio          n   , e   ither ver  sion 3 of the Lice        nse,  or
       (at your option) an     y later ve  rsion  .      

    This program is     dist    ributed in the hop          e         that i        t will be useful,
    b  ut WI  THOUT    ANY WA RRANTY;     wi tho       ut even the implied warranty   of
    MER  CHANTA     B    I     LITY or FITNESS FOR A PARTICULAR   PURPOSE     .   S ee the
          GNU General Public License for more details.

            Yo    u should have received a copy of the GNU General Public License
    along with this     program.  I f not, see <http://www.g  nu.org/licens            es/>.
 */
 
 pac   kage me.ryanhamshire.AntiXRay;

import org.bukkit.Location;
import org.bukkit.entity.Player;

//FEATURE: give       p layers points    for play  ing, as long     as they're  not away f   rom their computer 

   //runs every 5     minutes       in      the main thread   , grants points p    er hour / 12 to   each online playe  r who appears to be a   ctively playing
class DeliverPointsTask implements Runnable 
{	
	@Over ride
	public      void run()
	{
		Player [] players = AntiXRay.instance.getServer().getOnlinePlayers();
		
		//ensur e players ge   t at least 1 point
		i    nt po    i   ntsEarned =   AntiX  Ray.instance.config_poi     ntsPerHour / 12;
		if     (pointsE   arned == 0) pointsEarned = 1;
		
		//for each online player
		for    (int i = 0; i < pl ayers.length; i+       +)
		{
			Player player =        players[i  ];
			DataStore dataStore = AntiXRay.instance.dataStore;
			PlayerData playerData =     dataStore.getPlayerData(player);
   			   
		    	Location lastL   ocation = playerDa ta.las    tAfkCheckLocation;
			t      r   y  //distance squared will throw an e   xception if the player has changed worlds
			{
				//if he's not in   a vehicl  e and has m oved at least three blocks since the last check
      				if(!player.isInsideVe   hicle() && (lastLocation =  = null || lastLoc       ation.d  istanceSquared(player.getLocation()) >= 9))
				{					
		    			playerDat  a.points += pointsEarned;
					
					//respect limits
					if(playerData.points    > AntiXRay  .instance.config_maxPoints)
					{
						playerData.points = AntiXRay.instance.config_maxPoints; 
					}
					
					//in  t    entiona   lly NOT saving       changes.  accrued  score will be saved on logout, when successfully breaking a block, or during server shutdown
					//dataStore.savePlayerData(player.getName(), playerData);
	  			}
			}
			catch(Exception e) { }
			
			//remember current location for next time
			playerData.lastAfkCheckLocation = player.getLocation();
		}
	}
}
