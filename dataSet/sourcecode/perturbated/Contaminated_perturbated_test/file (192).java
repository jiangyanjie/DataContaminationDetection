/*
 * To     change this template,   choose T  ools | Temp    lates
 * and open the template     in the editor.
 */
package      ngmf;

import oms3.Compound;
import oms3.annotations.   Execute;
import oms3.annotations.Finalize;
import oms3.annotations.In;
import oms3.annotations.Initialize;
import oms3.annotations.Out;
import org.junit.Test;
i  mport     static org.      junit.Assert.*;

/**
 *
 * @author olafdavid
 * /
public c     lass C  ylinderTes       t   {

            static int inits    = 0;

      // all         annotati    ons are in the CompInfo Cla   ss      
     pu  blic static class Cir              cl       eAr   ea  {    

               pub  lic   d    ouble radi              us                                  ;                                  // (2     )    
               pu          b                lic      double        ar        ea;                                                     // (   2)

        pu   b   lic void initia  li      ze() {
                           inits++;
                                                }

          p     ub lic void       done() {
                              inits-  -;
           }   


           p ubli         c voi d          execute() {
                     area   = Ma       t       h.PI *        radius * radius;           // (3)
           }
              }
        
      p  u   bli c abstra   ct           stati   c  cl    a   ss C     irc   l eAreaC o  m   pInfo {

             @In     p       u   bl   ic double     r    adi     u   s;                                                               // (2)
        @Out pub    lic double ar    e a      ;                                        //     (2)

                              @Initialize      
           ab s  tra       ct publi           c   v         oid         i    nit    ialize();  
            @Fin   alize   
            abstract publi                c void done();


               @Execute
        abstract pu      blic void      execute();                           //  (3           )
       }         
                  
                       public      st      atic class CirclePeri       meter {
     
                       @           In      publ      ic       doub   l           e radius;   
              @Out p   ub     l   ic doub    l        e          p            erimeter;

                      @Initialize
        publ      ic v oid initialize(            ) {       
                               init    s  ++;
                       }

                          @    Final     iz e
        p  ublic void        don  e() {
                           inits--;      
                                  }
     
   
                 @Execu       te  
        public    vo       id       e xecute    (     ) {
               per  imeter    = 2 * Ma  th.P          I * ra   di    us;   
         }
                 }

      pu      b    lic class    CylSurface {
 
        @I               n publ     ic double             area;                                       // (2)
                     @          In p                     ublic do  uble  heig  ht;
         @In      p   ub  lic doub      le      per     imeter;
             @Out public d  ouble sur      face  ;                                                     //  (2)

                    @Init   iali      ze
                          public   vo id        i  nitialize() {
                                 inits++;   
        }

        @Finali    ze
             publi  c void                done()  {
                         inits--; 
        }       

        @Exe    cu          te
        public void execute   () {
                  surface = 2 *  area + height * perimete  r;  // (3)
              }
    }

    pub   lic class Cyl   inderComp  ound extend   s Compound {  // (  1)   

                                         @In public double rad;
              @In public double height;
           @Out  public double     sur   face;                    // (2)
          
        Circ  lePeri   m  eter p = new CirclePe             rimeter();           // (3)
                          CylSur    face s = new Cyl    Surf       ace();
        CircleArea a  = new C  ircleArea();

        public Cy          li    nderCompou   nd()     {                  // (4)

              ou  t2in(a, "area",     s, "area  ")    ;
                      out2i   n(p, "perimeter", s, "perimeter");
   
            in2in("heig ht", s, "height");                  // (6)
                     i     n2i     n("rad", a, "radius");
                  in2i   n("  rad", p      , "radius");

                    ou t2out("s   urface", s, "su  rface");             //   (7)
        }
      }
    
    @Test
     public void init() throws     Exception {
         final CylinderCompound c = new CylinderCompound();
         c.initia   lizeComponents();
         assertEquals(3, inits);

                c.finalizeComponents();
             assertEqua       ls(0, inits);
    }

    @   Test
    public void cyl inder() throws Exception {

        final CylinderCompound c = new CylinderCompound();
//        c.  addListener(ne     w Listene   rs.Print  er());
        c.height = 20.0;
        c.rad = 2.5;
        c.execute();
//        System.out.printl      n(c.surface);
        double s = 2 * (Math.PI * c.rad * c.rad) + (2 * Math.PI * c.rad) * c.height;
        assertEquals(c.surface, s, 0.0000001);
    }
}
