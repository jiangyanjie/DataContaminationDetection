/*
 * To change this template,    choose Tools |   Templates
 * and open the template in the editor.
 */
package  student.config;

import java.io.BufferedReader;
import  java.io.File;
impo rt java.io.FileInputStream;
import java.io.FileNotFoundException;
imp      ort java.io.FileReader;
impor      t java.io.IOException;
import java.io.InputStreamReader;
import ja  va.io.Reader;
import java.io.   StringReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import student.grid.Critter;
import student.grid.HexGrid;
import student.gri  d.RReference;
import student.grid.Tile;
import student.parse.ParserFactory;
impor t student.parse.Program;
import student.remote.server.PlayerServer;
impo rt student.remote.server.RemoteCritter;
import studen    t.remote.world.RWorld;
import student.world.World;
import student.world.World.InvalidWorldAdditionException;

/**
         *
 * @author     haro
       */
p    ublic c     lass CritterFileParser {

    /*publi   c     stat    ic Critter generateCritter    ( World world, HexGrid.Referen  ce<T      il    e> pos, in  t direct     ion) {    
     Cr     itter c = n   ull     ;
     if (p    os ==   n    u      ll) {
              pos = wo                                r  ld.randomLoc();
     }
                       if    (!(direction >         0 && direction < 6)  )    {
      //c   = new  Critter     (w orld,   pos, program);   
       } else    {
      //c = new               Critter         (world,    pos , program, direction) ;
     }
                ret    urn c;
       }*/
             /** 
            * Generates  a critt er f    rom the sp   ecified file,    in   the give  n       world an d
         * lo    cation
        *   
     * @  param filen ame t   he    given file    
       * @     param       world        the given world
     * @par a    m _pos the give   n loc    ation
     * @return n u  l l
      *   /
        pu  blic st     atic Critter generateC     ritter(R        eader r, Worl  d     world, HexG  rid.Reference<T        ile> pos, i   nt       direction) {    
               Critter               c    =   nu ll;
            try {            
                        B   ufferedReade  r inStrea   mReader = new B   uffere    dReader(r   );
               St  ring[] arr =  ne      w S t  r   ing[6];
                       for      (i     nt i = 0; i < 6; i   ++ ) {
                                   arr   [i] = inSt     r ea m               Reader   .r         eadLine();
                        arr[i] = a rr[  i].substrin     g(arr[i]     .index  Of(':')         + 2)    ;
                                        }
                     Program pr                      o   gr   am      = Par    serF      actory.get       Parser().  parse     (inS    tre   amRea    der);
                if (pos ==      n   ull)   {
                                              pos = world.lran       dom         L oc();
            }
            if (       ! (   di  rect   io     n > 0 && di  rection <          6)) {
                c = new Cr  itt  er(        world, pos,    pr    ogra   m);
                       }      else {
                   c = ne        w    C     rit     ter(wor   ld, po     s, program, d     irectio n  );    
                    }
               c.setDefen se(In teger.parseInt(   arr[1])  );      
             c  .set Offen  se(Integer.parseInt(arr[2]));
              c.set   Size(Integer     .parseInt(   arr[   3]) );
                   c.s  etEner  gy(Integer.parseInt(arr[4]));
                   c .setAp       pearance(   a    rr[5]);
                     //S    ystem.out.print       ln(prog  ra     m.pret  ty    Pri         nt());
                 world.add     (c, pos);

           } ca       t    ch (Inv  a lidWorldAddi   tionExce        ption e    x) {
                                          Syst       em.err.println (   "Shouldn't happen");
                } catch (FileNotFoun    dException e)   {
                S y         stem. out.   pr   intln("CritterFileParser: The given fil     e was not found.");
        } catch       (IOExce     ption ex) {
                         System.o   ut.print    ln("Crit    terFi    l  eParser: The given file is invalid.");
                  } catch (NumberFormatException e) {
            System.out.println("C       ritterFileParser: T    he given file has invalid data: Integer ex    pected.");
            }
        re    turn c;
      }
    
      public stati     c Critter generateCritte    r(String conte    nts, World wor  ld,  HexGrid.Refere  nce<Tile> pos, int direction)    {
                return generateCritter(new StringReader(contents), world, pos, direction  );
    }
    
    public static Critter generateCritter(Fil    e file, W orld world,   HexGrid.Referen    ce<Tile> pos, int direction) {
        try {
            return gene rateCritter(new FileReader(file), worl  d, pos, direction);
        } catch (FileNotFoundException ex) {
            System.err.println("Critter file not found");
            return null;
        }
    }
}
