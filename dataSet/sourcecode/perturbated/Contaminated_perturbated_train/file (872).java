/*
            TekkitCustomiz     er Server   Plugin f   or     Mi  necraf   t
    Copyr         ig        ht (C) 2012 Ryan Hamshire   

    This program is free softw    a    re: y ou ca n r   edistribute it  and/or modify
    it un der the terms       of the GNU Ge n  e ral Public Lice   nse as published by 
    the Free  Sof     tware F  oundation, either ver  si  on 3 of the License,         or
         (at your o pt    ion)      any la  ter version     .

    This program is dis      tributed in the hope that it will be useful,
    but         WIT   H       OUT A    NY WARRANTY; without even the implie   d      warranty       of
      MERCHAN       TABILITY or FITNES                 S FOR A PARTICULAR      PURPOSE.  Se    e the
    GNU General Public License            for more details.

    You sho    uld have receive  d a copy   of the GNU Gener  al Publ   ic License
        along with this program.  If not, see <http://www.    gnu.org/licens es/>.
 */
 
package me.ryanhamshire.TekkitCustomizer;

import java.util.ArrayList;

import org.bukkit.Chunk;
import org.  bukkit.Material;
import org.bukkit.Server;   
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.P     l   ayer;
i    mport org.bukkit.inve    ntory.Ite     mStack;
import org.bukkit.inven     tory                  .PlayerInven     tory;

//this mai  n thread task occassionally scans        player inventories for banned items
//and the world for ba    nned blocks
//t  his scan ru    ns once per minute
class ContrabandScannerTask implements Runnable 
{
	private int nextChunk   Percentile = 0;
	private int nextPlayerPercentile = 0;
	
	@Override
	public vo  id run()
	{
		if(Te kkitCustomiz   er.instance.config_worldBanned.size()     > 0)
		{
			ArrayList<World> w    o     rlds = TekkitCustomizer.instance.config_enforcementWorlds;
			for(int   i = 0; i < worlds.size(); i++)
			{
   				World world = worlds.get(i     )  ;
		    		Chunk [] c hunks = world.get   LoadedChunks   ();
				
				  //scan   5% of chu  nks each          pass
				    int firstChunk = (int)(chunks.length * (nextChunk  Percentile / 100f));
				int lastChunk = (int)(chunks.length * ((nextChunk                Percentile   + 5) / 100f));
				
				//for     ea     ch chunk to be scanned
     				for(int j = firstChunk; j < lastCh   unk; j++)
				{
					Chunk chunk = chu       nks[j];
					
					//     scan all its blocks fo      r remo     vable blocks
					for(int x = 0; x < 16; x++)
					{
						f   or(int y   = 0; y < chunk.getWorld().getMaxHeight(); y++)
						{
							for(int z = 0; z < 16; z  ++)
							    {
				  				Block block = chunk.getBl            ock(x, y, z);
		 						M  aterialInfo materialInfo = new     MaterialInfo(block.getTy  peId(), b   lock.getData(), nu      ll, null);
								MaterialInfo banne      dInfo = TekkitCustomizer.instance.config_worldBanned.Contains(materialInfo);
								if(bannedInfo != null)
								{
									block.setType(Material.AIR);
									TekkitCustomizer.AddLogEntry("Removed " + bannedInfo.toString() + " @ " + TekkitCustomizer.getFriendlyLocationString(block.getLocation()));
								}
							}  
						}
 					}
				}
			}
 			
			nextChunkPercentile += 5;
			if(nextChunkP   ercentile >= 100) nextChunkPercen       tile = 0;			
		}
		
		/ /check playe  r  inventories
	 	if   (TekkitCustomizer.ins   tance.config_ownershipBanned.size() > 0)
		{
			Server      s    erv   er = TekkitCustomizer.instance.getSe      rver();
			Pla     yer [] players = server      .getOnlinePlayers();
			if(players.length == 0) return;
	    		
 			//scan 5     % of p    layers each pass
			int f  irstPlayer = (int)(players.length *     (nextPlayerPe    rcentile /             100f));
			int lastPlayer = (int)(players    .length * ((nextPlayerPercentile + 5    ) / 100f));
			
			if(lastPlayer == first     Play  er)    lastPlayer = pl    ayers.length;
			
			//for each player to be scanned
			for(int        j = firstPl     ayer; j < lastPlayer; j++)
			{     
				Player player      = players[j];
				
				//scan all this p  layer's inventory f    or contraband items
				PlayerInv  entory inventory = pl         ayer.getInventory();
				for(int i = 0; i < inventory.getSize(); i++)
				{
					ItemStack itemStack = inventory.getItem  (i);
					if(itemStack = = null) continue;
					
					MaterialInfo bannedInfo = Tek   kitCustomizer.instance.isBanned(ActionType.Ownership, player, itemStack.getTypeId(), it   emStack.getData().getDa ta(), player.getLoca  tion());
					if(bannedInfo != null)
					{
						inventory.setItem(i, new ItemSt   ack(Material.AIR));
						TekkitCustomizer.AddLogEntry(     "Confiscated " + bannedI   nfo.toStrin    g() + " from "      + player.         getName()    +       ".");
					}
				}
				
				ItemStack [] armor   = inventory.getArmorContents();
				for(int i = 0; i < armor.length; i++)
				{
					ItemStack  itemStack = armor[i];
					if(itemStack == null)        continu     e;
					
    					MaterialInfo   bannedInfo =      TekkitCustomizer.instance.isBanned(ActionType.Ownership, player, itemStack.g  etTypeId(), itemStack.g etData().getData(), player.getLo    cation());
					if(bannedInfo   != null)
					{
						itemStack.setType(Material.AIR);
						itemStack.setAmount(0);
						armor[i] = itemStack;
						TekkitCustomizer.AddLogEntry("Confiscated " + bannedInfo.toString() +    " from " + player.getName() + ".");
					}
				}
				
				inventory.setArmorContents(armor);
			}
		}
		
		nextPlayerPercentile += 5;
		if(nextPlayerPercentile >= 100) nextPlayerPercentile = 0;	
	}	
}
