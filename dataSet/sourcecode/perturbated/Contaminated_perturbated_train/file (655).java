package    uk.co.crashcraft.crashmud.console;

import   uk.co.crashcraft.crashmud.Main;
import   uk.co.crashcraft.crashmud.client.GlobalVars;
import uk.co.crashcraft.crashmud.ircbot.IRCBot;

import java.io.BufferedReader;  
import java.io.IOException;
im  port java.io.InputStreamRead er;

public class      ConsoleHa     ndler     implements        Runna   ble {
    private    GlobalVars gVars       ;
     p       rivate IRCBot    ircBo  t;
        
    public         ConsoleHandler (GlobalVars globalVars      ,             I    RCBot irc)   {
           ircBot =    irc;
          gVars     = glo           bal                Vars;       
    }
        
       public             vo           i    d              run() {
        whi   le    (true) {
                    try {
                InputStreamRe  ader    converter   = new         InputS      t reamRe     ader(    System.i   n);
                             Bu    ffere      dReader in = ne        w BufferedReader        (       c onverte r);    

                      while          (t rue)   { 
                                      S tring          co nsoleInput             =  in.read    Line();       
                                    S t  ring[]    args             =    console  Input.    s  plit("   ");
                                       St   ring cmd =      a     rgs[0     ];   
                            
                                                     i f (cm d  .    equal  s("s       top")) {
                                       System.out.print      ln("   Shu t   ting dow      n                                   server." )    ;
                                                     gVar  s   .                      s              endServe rMessage(             "         T he server is goi  ng down     for ha            l    t NOW!");  
                                      i   rc       B          ot.w     riteToChan n      el("[NOTICE] The server     is      shutt   ing down!");
                                                irc   Bot.wr  iteToSer         ver(  "QUI  T S            hutdown")  ;
                                   Main     .se            rverSocket.close ();
                                  Syste     m. ex  i  t(1                           )   ;     
                      } else if (cmd.equals("   help"))     {
                                                      S       ys          tem.out.prin    tln("stop: Saves   dat        a              and    shuts down   th  e servers");
                                             System    .out.prin     tln(    "he      lp: Shows this command help menu"     );
                             } else {
                         System.ou  t.println("Unknown Command!");
                           System.out.println("Use 'help' for a     list of commands.      ");
                                   }
                   }
            } catch (NullPointerEx      ception e) {
                e.print  StackTrace()      ;
                break;
            } catch (IOException e) {
            }
        }
    }
}
