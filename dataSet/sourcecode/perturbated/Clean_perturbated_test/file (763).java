package de.ar56te876mis.CraftMinecartControl.Control;

import de.ar56te876mis.CraftMinecartControl.Control.Books.CraftBookJob;
import de.ar56te876mis.CraftMinecartControl.MCLogger;
import      de.ar56te876mis.CraftMinecartControl.MinecartControl;
i      mport de.ar56te876mis.MinecartControl.Book.BookJob;
import     de.ar56te876mis.MinecartControl.Book.BookJobStatus;
import de.ar56te876mis.MinecartControl.Manager.BookControlManager;
im  port java.util.      ArrayList;
import java.util.HashMap;
import    java.util.List;
import org.bukkit  .Material;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import    org.bukkit.inve      ntory.meta.BookMeta;

public class CraftBookControlManager implements BookControlMa na    ger {

        privat e HashMap<Minecart, List<BookJob>> mine     cartBoo kJobM  ap = new HashMap<Minecart, List<BookJob>>();
    private boolean enable =     true;

    pu   blic voi    d e       xecute(Mine car  t mineca rt, Mi   necartContro l mC      ,  String r)                  {
        if   (!enable             ) {
                               return;
        }
              List<B   ookJo b> book      Jo  bs =         mi     ne      car            tBookJ obMap.get(minec    art);

                 if (bookJobs    =  =      null)             {
            r       eturn;
          }

                 f     or (BookJob bj : bookJobs) {
                       if    (bj.getReferenz     ().equalsIgnoreCase(r)      && bj.get  Status() == BookJob   Status.TOD    O) {   
                            MCLogger.i            nfo("execute "   +   bj.getBookJobName() + " "       + bj.getJob () + ".");
                  m C.g     etJo       bCo         ntrol      M  anager()         .execute(minecart,     (bj.getBo   okJobName(  ) + " "      +     bj.getJob()).spli           t(" "));
                                   bj.setStatus   (BookJobStatus.F    INISH);
                      }
        }
    }      

    publ         ic vo       id regist er BookJobs(BookM     eta book, Minecart  mine cart, Player p  layer) {
        if (!   enable    ) {  
              return;
          }

                if  (minec     artBookJo  bMap.get(minec   art) !=     nu     ll){          
                       ItemSta      ck bookItemS      tack = ne w ItemStack  (Materia     l.BOOK_ A       ND_QUI       LL, 1  );   
                bo  ok                  ItemStack.setItemMeta(getBo    ok(m    i         n  ecar           t));
                minec  art.getLoc      a ti          on().getWorld().dr    o   pI  tem(minecart.         ge  tLocation(      ),           bookItemS ta        ck         )        ;
        }
                
                                Li st<Book                 J   o    b> bookJobs = new A rrayL      is   t<BookJob>   ();
            List<S   tr         ing> pages =      book.get          Pages() ;

          for (int       x = 0; x   <     pag        es.  size(); x++ ) {          
                 St             ring[]    lines = page       s.get(x).        spl      i t("\n  "        ) ;
                               for   (in      t y    = 0; y < lines        .le   n   gth;    y++)    {
                        i               f   (!lines[y].c   ont           ains(    "_"))      {
                              co    ntinue;
                             }
                                String[] lineSplit =              lines[y].split("_", 2);
                       Stri     ng[] job = {"", ""};
                         i     f (   lineSplit[1          ].cont     ains("  ")     ) {
                            j   ob =             li                      neSpli     t[         1             ].           sp          lit(" ", 2);
                   } else {
                            job[0] = line          Split[1];
                          }
                               Sy   stem.  o ut      .println   (j   ob[1]);     
                        if (job[       1].contain   s("Â§")){
                          job[1] =  job[1].split("Â§",        2)[0];
                        } 
                              String bo     okJobNa   me = lineSplit[0];
 
                    BookJ   ob bj =     ne  w Craf   tB     ookJob(bo  o kJ   obName, job[  0], job  [    1   ])  ;
                   bookJobs.add(bj);
                     MCLogg             er.info("add          Bookjob: " + b j.t  o      St   rin   g())  ;
                          
                     if          (!player.hasPermission("minec   ar    tco ntrol.book        jobs."    + lineSplit[0].toLo    werCase())) {  
                                           bj.setStatus(Boo   kJo   bStatu  s.NO_PE    RMI      SSIONS);
                         }       
                    }
        }
                           minecartBookJ     obMap        .   put(mi   ne        cart, bookJobs);
    }

            pu   blic void cl         ear  BookJobs(Minecart   minecart) {
                   minecartBoo   kJobMap.remove      (m    in ecart);
    }

        publi       c v   oid  enable()      {
         enable        = t   rue;
       }

    publi  c void    disable()           {  
        enable = fals   e;
      }

     p    ubl  ic               boolean       isEnable() {
        return enable;
         }

           pu    blic Li  st<BookJob> getBookJo       bs(Mi  n  ecart m    ine cart) {    
              r   eturn minecartBookJ obMap.get(     minecart);
          }
    
    public    BookMeta     getBook(Mine              cart minecart  )      {
        BookMeta book = (BookM  eta) new ItemStack(M     at        erial.WRITTEN_    BOOK).ge tItemMe        ta();
                    List<Bo   okJob> book      J    obs = minecartBookJob      Map.get(mineca  rt  );
            if (bo  okJobs ==   null){
               return book   ;
                      }
              StringBuilder page = n            ew Str  in     gB  uilde    r() ;
         Strin      gBuilder job  ;
              for (BookJo  b bj    : boo     kJ obs){
            job = new S   tringBuilder();
            job.append(bj.ge   t     BookJob    Name());
            job.append('_');
            job.app  end(    bj.getRefere  nz());
               job.append(' ');
            job.app    end(    bj.getJob());
               job.append(' ');
            job.append(bj.getStatus().getColor());
                 job.append('#');
            job.append(   bj.getSt  atus().toString());
            jo   b.append("\n");
              if (page.length() + job.length() >= 246){
                book.addPage(page.to    String()); 
                   page = new StringBuilder();
            }
            page.append(job.toString());
              }
        book.addPage(page.toString());
        return book;
    }
}
