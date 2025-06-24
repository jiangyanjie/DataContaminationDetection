/*
  * To change this license header    , choose Li       cense Headers in Project Properties.
     * To change  this template file,      choos    e Too   ls | Templates
 * and        open the template in the edito   r.   
 */

package business.client;

import  business.filter.attempt4.AppleColourPredicate;
import business.filter.attempt4.ApplePr  edicate;
import business.objects.Apple;
import java.util.ArrayList;
import java.util.List;
     
/      *     *
 *
   * @author N  arendran
 */
pu   blic   clas   s          AppleC      lien    t  {
    
    
              publi           c static      void main( Stri ng[] ar     gs) {
        List<Ap    pl     e> inventory  = new Ar  r    ayList();      
        Apple    apple1   =         new Apple   (100L, "green"    )            ;    
                      inventory.ad d(app    le1);
          A pp le apple2 =  new      Apple(120L, "green ");
        invent  ory.add(apple2);
         Apple ap    ple3 = new Apple(140 L, "gr    een");
                  in    ve ntory.add(apple3);
        Appl e    apple4 = n   ew    App l  e(100L,      "red");
        inv  en  tory.add(     apple4)   ;
                                  App   le apple5 = new Apple(120   L  , "red");
        inven   t  ory.      add(ap        p     l  e5);
        Appl    e apple6 =    new App   le   (140L, "   re       d");
             invent      or  y.ad    d(apple6);
                Apple      apple7 = new Appl e(1   60L,        "red");        
           inventory  .add(a     pp  le7)           ;
            Ap    pl e appl      e8          =     new Apple(170L,  "red");
        i       nvento  ry.add(app   le   8);
        Apple     apple 9 =   new Appl  e(180L, "gre  e    n");
              inv ento    ry. add      (ap  ple9);
            A   pple ap  p  le10 = new A         pp  le(180L, "re d");    
        i  nventory.add(apple1 0);
               
                    /     /att    em   p    t One
        Li st<Apple>        fil   ter       edA   pp     les = null;
        filt   e     r  edApples = business.fi         lter.attempt1.AppleFil   t    er.filterGreenApples(inventor   y);
               
            f    or(    Apple a    pple:  filtered    Apple      s)
          {
              Sys      te   m.out.print   ln       ("Attempt 1");
             System.out.println(ap          ple)           ;
        }
            
        //atte mpt  T wo
        fi  lteredApp     les = business.filt   er.attemp   t2.App  le             Fi   lter.filterApp   lesB       y  Colour(     inventory, "green" );
             
                  for(Appl   e    apple:filteredApples)
                       {   
                    Syste  m.out.p    r   intln("Att empt            2");
                       System.out.p ri            n  tl    n(appl     e);
                }
               
                   //attempt Three
         filteredApples = business.filter.     a  ttempt3.AppleFilter   .filter  (inventory, "gree n",150L   );       
        
          for(Apple app le    :f    ilte       redAp       ples)
        {
            Syste               m  .o  ut.println ("Attempt 3");
                                           S   ystem.out.p  rintln(ap   ple       );
         }
        
          //a       tt        empt Four
        Appl      ePredi cate tes      tGree  nColour = new A          ppleC             olourPred  icat   e( ); //B   eha    viour     Parameterized           
             
                                 filteredApples = busines    s.f    i  lt   er.attempt4.Appl    eF    i   lter.         filter(   invento        ry, testGree    nColour); //   func        tion as p      a       rameter 
             
        for       (A        ppl        e apple:filteredA            pp   les   )
          {
            System.   out.println(    "Attemp t 4");   
                       System.o        ut. prin     t  l   n(ap      pl        e);
                      }
             
            
        //attempt Five : Anonymous Function usage
                   
        f   il ter edApples =     b    usi    ness.filte      r.attempt4.AppleFilter.fil   ter(i nventory, new ApplePredicate() {
                
                          @Override
              public boolean test(Apple apple) {
                     retur n "gree  n".   equalsI     gnoreC        ase(      apple.getC     olou r());
            }
        });  // function a  s parameter
           
        for(Apple apple:filteredApple        s)
           {
            System.out.println("Attempt 5");
                System.out.println(appl e);    
           }
        
        //attempt Six   : Anonymous Function usage
       
        filteredApples =     business.filter.attempt5.GenericFilter.filter(i  nventory, 
                (Object obj)->{
                                Apple apple = (Apple) obj;
                    return "green".equalsIgnoreCase (apple.getColour());}); // fun ction as parameter
        
        fo       r(Apple apple:filteredApples)
        {
            System.out.println("Attempt 6");
            System.out.println(apple);
        }
    }
            
}
