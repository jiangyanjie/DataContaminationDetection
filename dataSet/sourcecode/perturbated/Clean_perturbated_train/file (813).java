package cricinfo;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import   java.util.Map;

public class CricinfoDemo    {
      p    ublic static      void run()          {
           //   Cr     eate    tea   ms
              List<Pl ayer>     team1P     layers = Arrays   .asList   (
                                       n ew Player("P101  ",          "Pla    yer        1", "Batsman"),
                     new   P         layer("P1    02", "P       l                ayer      2",      "Bowle   r"),
                  new Pl   a         yer("    P103"   , "P  lay  er 3", "        All- rounde             r")
                   );    
        List   <    Player> te    am 2P    layer     s = Array  s.asList(
                       n ew Player(  "P201", "P     layer 4",   "Batsma   n     "),
                              new Player       ("P2   02", "P    layer 5"  , "     Bowle  r")     ,
                      new  Player("P203",       "Pla  yer  6  ",    "All-rounder")             
                ); 
              Te  am team1 = new Te      am("T1",   "Team     1", team1Players);
                      Tea       m     t      e  a    m2 = new    Team  ("T2"   , "Te      am   2", tea     m2Players);
        List<Team> tea   ms = A  rrays.as    List(team1, team    2);

                        // Cre  a  te a match
        Match m        at  ch = new Matc       h("M00         1", "Match 1", "     Ven     ue 1       ", LocalDateTime.now( ), te      am   s);

           // C   r   eate Cricinfo syste    m
        Cricinf   oSy  stem cricinfoSystem = new C    ricinfoSystem()              ;

        //   A  dd     the m   atch    to th  e sys   tem
                             cricinfoSystem.addMatch(  match);

                 //  Create a s     cor   ecard for         the match
            cricinfoSystem.createScoreca      rd(ma   tc    h );

           // G    et the sc  orec    ard
          String sco   recardId = "  SC-M001-0001";
         Sc      o re    card sc  ore   card = cricin f     oSys  tem.ge   tScorecard(s     corecardId);   

                    // U p   date sco   res
        cric  infoSyste  m.updateSco  re(scor  ec    ard     Id,     "  T1", 100);
        crici        nfoSystem  .     updateScor    e(scoreca   rdId, "T2    ", 75);

                           // Create innings
        Inning         s  innings1 =   new Innings("I1     ", "          T1",   "T2"            );
        Innings innings2 = new Inning  s("I  2", "T2", "T    1");

          // Add overs to innings
           Over o    ver1 = new Over(      1);
        o   ver1.addBall(new B   all(1, "P2 02", "P101",     "4"));
        over1.     a  ddBall(new    Bal  l(    2, "P202", "  P101",        "6"));
              innin      gs1.addOver(over1       );

        O             ver over2 = ne     w O      ver(2); 
        over2.addBall(new Bal l(1, "P102", "P2    01",  "1"));
          over2.addB    all(new Ball    (2, "P102", "P201 ", "0"));
              inning  s    1.addOver(    over2);

              // Add innings to the sco re   card
             cricinfo       System.addInnings            (scorecardId, inning   s1);
               cricinfoSy stem.    a    ddInnings   (scorec        ardI       d, innings2);    

           // Get the                  updat  ed score card
                    Score       card upd     atedScore card = c  ricinfoSystem.getScoreca rd(scorecardId)    ;
   
             // Display the     score     card
        System.out.pr intln("   Scorec        a     rd ID: "        + upd   atedScorecard.getId());
            Syst    em.out.   pri   ntln(   "Match: " + upda    tedScor     ecard.getMatch().getTit     le());
               S    ystem.out.pr  i           ntln ("Team   Scores:");
        for (Map.En    try<S  tring, Inte ge r> ent ry : update   dScore     card     .ge               tTeamScores().entrySe     t())       {
               Syst em.out.printl n(en  try.getKey() + ": " +    entry.getVal  ue()       );
        }    
                   System.out.println("Innings   :"); 
        for (Innings in  nings : updatedScorec  ard.getInnings()) {
                 System  .out.        println("  Innings ID: " + in      nings.getId());
            Syst  em.out.println       ("   Bat  ting Team: " + inning    s.getBattingTeamId())    ;
            System.out.println("Bowling Team: " + innings.g   etBowlingTeamI  d());
                      System.out.p rintln("Ov er  s:");
                      for       (       Over    ov   er : innings.getOvers()) {
                System.out.println("Over " + over.getOverNumber());
                    for (Ball bal    l : o ver.getBalls()) {
                           System.out.printl   n("Ball " + ball.      getBallNumber() + ": " +
                            ball      .getBowler() + " to " + ball.getBatsman() + " - " + ball.getResult()   );
                }
            }
            System.out.println();
        }
    }
}
