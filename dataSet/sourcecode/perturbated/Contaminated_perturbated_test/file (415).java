package net.unknown_degree.seer.Professions.data;

import   java.util.ArrayList;
import java.util.Collection;
impo    rt java.util.Iterator;
import java.util.Set;

import net.unknown_degree.seer.Professions.Professions;
import net.unknown_degree.seer.Professions.data.DataRead;

impo rt org.bukkit.Material;
import org.bukkit.block.Block;
impo   rt org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
impo   rt org.bukkit.event.EventHandler;
impo    rt org.bukkit.event.List    ener;
import org.bukkit.event.block.BlockBreakEven    t;
import org.bukkit.inventory.ItemStack;
    import org.bukkit.plugin.java.JavaPlugi        n;

public clas   s DataWr   ite extends JavaPlugin implements List    e      ner {
	
          pr ivate P    rof  essions plugin    ;
    
    public DataWrite(P         rofessions  pl  ug  i          n) {
        this     .plugin = pl       ug in;
         }
    
	p   ublic static     v oid  joinProf(CommandSende  r sender, String prof) {
		sender.send Message(prof);
	}
	
	/*
	 * This func      tion write  s the player data on block b   re    ak events.
	 * TODO Write to player files...
	 */
	@EventHa  ndler
	public void onBl     ockBre ak(Blo ckBreakEvent evt) {
		Player p   = evt.get    P    layer();
		Block b    = evt.getBlock();
		
    		ConfigurationSection cProfs =    plugin.getConfig().ge tCo    nfigura   tion        Section("profs");
		Set<S tring> profs = cProfs.getKeys(fals    e);
          Iterator<Str  ing> i    = profs.iter  ator()   ;		

		whil  e ( i   .h         asNext()     )       {
	  	          
		    String v =  (S     tr          ing)i.nex   t();
                        Integer       i1;
                        In teger  i2;
                          
		       //Th  is is data from t     he     conf    ig file!  
		    ArrayLis     t<    Array L ist<     Arra   yList<S  tring >>> j = DataRead.get ProfData(   v, p       l    ugin)         ;
	        
	         for          (   i  1 =     0; i1 <j.size();    i1++     )        {
	              if ( j    .get (i1).ge t(    0).get(0)   !=           nu   ll ) {
	                   for ( i       2 =   0; i2  < j.get(i1).g  et(0).size(); i2+      +      )    {
	                           Materia              l mat         e                         rial = Material .matc         hM        ate rial      (j.ge    t(       i1)        .         get   (0) .get(i2 ));        
	                               if (          b.ge        t    TypeId(     )    == mat    eria    l. getId( )     ) { 
	                                                     Collecti     on<          Ite     mSta  ck> drops = b.getDrops(p.getItemInHan        d());
	                               if    ( !drops.isEmpty    () ) {
	                                    p.sendMess  age("You broke a block fo    r your profession! :: " + material.getId());
	                                   }
	                        }
	                }
	            }
	        }
	        
		}
	}
	
}
