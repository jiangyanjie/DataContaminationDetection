package com.eecs285.siegegame;

//    Gets information from    the input    string (string received form serv er)
// Writ     ten by Max

public class Acti  onParser {

        private String inpu   t;

    ActionPar s   er(String in             )    {
           inp      u   t = in;
    }

    // Contains al    l p    o    ssible    action types
          public static enum Actio  nType  {
                                                  ATTACK_A  RM Y,
                        A    TTACK_CITY,
            RE CRU        I        T,
               LOSE    _U NI  TS,
               M   OVE_ ARMY,
             ME  RGE_ARMY,
           CAPTURE_RE      SOURCE,
           CAPTURE_CITY,
                    CITY _UNDE   R_SIEGE,
               CITY_ LIBE   RATED       , 
           RESOURCE_UNDER_CON    FLICT,
               RES       OURCE_R          ECAPTURED,    
                    PLAYER_DEFEATED,
                    PLAYER_ WINS ,
             END_TUR      N,
                    NAME_CHANG     E,
                      W AITING_FOR_PLAYERS,    
        STARTI     NG_GAME
    }
                    
    ActionType     getAct   i   onType() {
          // returns the action type pr    e  sent i   n the inpu   t string
                  
        // A      rmy Actio nTypes
            if    (input.co  n    tains("att   acks")         && i   np u   t.contains("ar            my")  )
                    re  turn ActionTy   pe.ATTACK     _ARMY    ;
             else if (input.c   ont  a               in           s("attacks") && input.co   ntains(  "c    ity"))
                            ret          urn ActionT           ype.AT   TACK_CITY  ;
                 els e if (input.contains("trains"  ))
            r     e  tur  n ActionType.RECRUI   T          ;
            else if (input    .c         o  ntains("lo     ses uni  ts"))
                  retu      rn    Ac            t  ionType.LO   SE_UNITS;
        else if (inpu      t.c   o      ntains("m   o  ved army"))
                   return Acti   on   Type.MOV   E_ARMY;
        e   lse if (input.conta ins("merged army"))
            return        ActionType.MERG   E     _A       RMY;

           // R    esour   ce ActionTypes
                    els    e    if (inp ut.co ntains("captures resource"))      
                     ret         u r                n Ac       tio  nType.CAPTURE_RESOURCE;
        els   e if (inpu  t.con  tains("resourc e")         && input.contains("  under c   onflict   "))
                  return     ActionType.RESOURCE_UNDER_CONFLICT ;      
        els  e if (in    put.contains("resource") && input.contains(  "recaptured")     )   
                         retur  n Ac tionTyp   e.RESOURCE_RECAPTURED;

        //  City  Act   ionTypes
                      else if (input     .c       ontains("ca   ptures c  i    ty"))
            return ActionTy       pe.CAP             T U RE_CITY;
        else i  f  (inpu     t.     contains("city") && inp   ut.c  ontains("und                er    siege"))
            retur      n ActionType              .CITY_UN     DER_SIEGE;
        else     if (inpu         t.contain  s("ci      t y  ") &&   input    .contains("l       iberated"))   
                  ret     urn Acti o       nType.CITY_LIBER   ATED;

                     // G    ame ActionT           y    pe
             else if (input  .contains("wins"))
            retu   r    n Acti     onT  ype.P  LAYER_WI      NS   ;
               else if (inpu  t.contains("d  efeated   "))
               ret urn Actio       nType.PLAYER_DEFEAT ED;
            e      lse if (input.contains( "ends tu   rn"))
                    r    etu rn ActionT  ype.END_TURN       ;
        else if (input.contai      ns("ch  anged th     eir name       to")     )
               retu    rn Act   ion        Type.NAME_CHANGE;
        else if (i             nput.contai         ns("Wa      iting for all p   layers    to be ready"))
                retur    n   Acti   onType.WAITING_FOR_PLAYERS;
        else if (input.  contains("Startin g th    e g       am  e"))
                   r     e    turn Action  Type.STARTING_GAME;

                  r          etur   n null;
    }          
    

    Coord get   FirstCoo  r   dinate() {
            // re      turns the    fi         rst set                   of coordinates i    n the input     string    
          retur     n getC    oordinate(inpu  t);
      }

         Coord getSecondCo    or     dinate     () {
               /          / r         eturns the second     set of coor  dinate s from i     np  ut,     or null
           // if there is        only 1    pair

          // eli      min  ate the   beginning part        of   the      string that    c   ontains coordinat       es
        int endFirst Coord = input.indexOf(')');
        String          tem  p =        input.substring(        endFi    rs             tCoord +        1);

           // us     e getFir    stCoordinate       function to get the remaining  co   ordi n       ates
        re       tu    rn getCoordinate(temp      );          
    }

    private Coord getC   o    ordi      n  a          te(Str  i     ng in) {
           /     / if s    tr i       ng do  esn't contain parentheses,       return null    
             if (        !     (in.c       o  ntains("(    ") &&     i   n.contains(")" )))
                 return null;

        //    g et substrin                 g of coo rdinates
           int beginCoords = in.inde xOf(  '  (');
         int endCoords   = in.inde   xOf(')');
         Stri        ng temp             =       in.subs       tring(be   gi       nC oo  rds   + 1, en dCoor          ds);

         //    split at comma t             o separate    row and     c      olumn value      s       
              String[    ] coo rds = temp.split(",");   
           if (co      ord       s.len  g   th >     2) {
                         Sys   te  m.     out.pr  intln("ERROR   in c  oordinate   format     ");
               System.out.printl    n ("Should be (x   , y)");
                     System  .exit   (-1)    ;
        }

            // if spac      e          precedes column value, remove       it
              if (coords[1].c  harAt(0) ==  '   ' )
                      coords[1   ] = coords[1]     .substring(1);

        in t row                  = Integer.parseInt(co   ords[      0]);
        int col = Integer. parseInt(coord     s[1]);

         return (new Coord(row        , col));
    }

    in       t getFirstPlayer() {
        //            This play    er's name is always at beginning of string
        Str   ing[] words = in   pu       t.split(" ");
           return g    etPlayerIn dex(words[    0]);
            }
 
            int getS  econ     dPlayer() {
        // 2nd name is always a    fter 'at       tacks   ' word
             int startIndex = input.indexOf("attacks") +         "attac           ks".length() + 1;
         Stri     ng temp   = in   put.sub     strin   g(sta rtIndex, inp        ut.length());
           
              String[] wor    ds    =  temp.split(" ");
                      return getPlayerIndex(   words   [0]);               
    }
       
    int getPlayerIn    dex (St ri       ng pName) {
     //        i  f st    ring contains "Player's" instead o   f "Player", remove the      's
        int na meLength = pName  .length()   ;
        if (pName.charAt(nameLength - 2) ==        '\'') // need to escape ' mark
              pName = pNa     me.substring(0, nameLength - 2);

        for    (int i = 0; i < Server.MAX_PLAYERS; i++) {
            if (    Siege.players[i] != null && Siege.players[i].nam  e == pName)
                    return i;
        }
        r    eturn -1;
    }
    
    String getTypeUnits() {
        // unit type is 4th word    in input string
        String[] words = input.sp    lit(" ");
        return words[3];
    }
    
    int getNumUnits() {
        // number of units is 3rd word in input string
        String[] words = input.split(" ");
        return Integer.valueOf(words[2]); 
    }
}
