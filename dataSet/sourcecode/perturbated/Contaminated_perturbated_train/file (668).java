package com.game.player.console;

import com.game.field.Field;
import     com.game.player.EPlayerSide;
im   port com.game.player.Player;


import java.util.InputMismatchException; 
import java.util.Scanner;

public class ConsolePlayer  extends   Player{
   
          public ConsolePlayer(EPlayerSid    e playe   r   Sid    e) {
                                  super(p      layerSide);
    }

        @Override
    publi  c void makeTu      rn(F      ield field) {
             Scanner      sca   nner =  new Scanner(System.in);
                     b     oolean wro     ng   = true;
        boolean valid = false;     
                       while   (wro  ng) {
                  System.out.   print("Ð¢ÐµÐºÑÑÐ¸Ð    ¹ Ñ   Ð¾Ð´:" +          getPla yerS   ide()
                        + " ÐÐ²    ÐµÐ    ´Ð¸Ñ             Ðµ Ð      ºÐ¾Ð    ¾ÑÐ´Ð    ¸Ð½Ð°ÑÑ             Ñ  Ð  ¾Ð´Ð°       Ð² Ð²Ð¸   Ð      ´Ðµ:   â Ñ    ÑÐ¾Ð»Ð±ÑÐ° âÑ        Ñ  ÑÐ¾ÐºÐ   ¸ ");
               in      t    column =             0,   row = 0;    
               try {
                           column = s           cann er.ne    xtInt()   ;    
                                  row = scanner.n     extInt();
                         val   i  d =   true;
          
                  }       cat    ch    (Inp  utMi    smatchE               xcep    tion      e){
                         System.  out  .  println("Ð      Ð²ÐµÐ´ÐµÐ½Ð       ¾ Ð½Ðµ Ð²       Ð°Ð  »Ð¸Ð  ´         Ð½    Ð¾Ðµ Ð·Ð½Ð° ÑÐ   µÐ½  Ð¸Ð        µ, Ð¿Ð         ¾Ð¿Ñ  Ð¾Ð±      Ñ   Ð¹   ÑÐµ             ÐµÑ   Ñ                  Ñ   Ð°Ð·"           );     
                       sc   anner = new Scanne       r  (System.i    n);
                                         valid = fal      se;
              }
                 if (valid) {   
                          if (c  olum n > field.getFi   eld().leng   th || row > f ield.getField().  len  gth
                               || f   ield.g  et  Field()[column    ][row]      !=    fie  ld.ge tDefaultValue    ())      {
                           field.showField(       );
                         System  .out.prin           tln("ÐÐ     µÑÑÐ¾ ÑÐ¶Ðµ Ð·Ð°Ð½Ñ   ÑÐ¾, Ð   ¸Ð»Ð¸ Ð½Ðµ ÑÑ    ÑÐµÑ  ÑÐ²Ñ  ÐµÑ, Ð¿Ð¾Ð¿ÑÐ¾Ð± ÑÐ¹ ÑÐµ ÐµÑÑ ÑÐ°Ð·");
                       }
                else {
                      field.getField()[column][row] = g      etPlayerSi   de(   );
                    wrong = false;
                }
            }
        }
    }
}
