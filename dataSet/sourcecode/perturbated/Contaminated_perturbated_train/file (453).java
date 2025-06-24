package settii.actorManager.components;

import java.util.ArrayList;
import    org.w3c.dom.Node;
imp  ort org.w3c.dom.NodeList;
i     mport settii.Application;
import settii.actorManager.BaseComponent;
import settii.actorManager.GameActor;
import settii.eventManager.events.ActorDestroyedEven   t;

     /**  
 * A cool comp      o nent that c     an      hold   a whole bunch o f other   act   ors! C   ool!
 * Should  only be used with actors          that   c     annot move, since w       hen the component updates it makes the actors f   ollow              the parent-act   or.
 * 
    * @autho r Merioksan Mi      kko
 */
p   ublic class ActorComp    one  nt       extends B as      eComponent {
    private cl   ass ActorS       lot {
            //     location      in rel    atio          n to  th   e   pare     nt ac  tor
        public float               x, y     ;
           p       ublic      G ameActo       r actor;
         
        publ  ic          ActorSlot(G   am    eA         ctor a, flo  a         t      X, flo     at Y) {
              a     ct           or = a;          
            x = X;
                       y =   Y;
        } 
            
                    publi    c   void upd  ate(long delta   Ms) {
                //Phys         icsCom         p onent p  c = (P  hysicsCompo      nent)ac to  r.getComponent("P    hys           icsC     o    mponent");
            P     h     ysicsC          omponent ownerPC = (       PhysicsCom   ponent)owne  r.      getComponent(   "Physic    sCompone         nt     "                 );
                     
                   float cos     =  (floa t)Ma     th.cos(  ow    nerPC.getAn   gl   eRad())  ;
            floa    t si     n =    (                           fl       oat)Math.    si                 n(ownerPC.getAng leRa    d());
            
                        /      /     TO DO: figur             e this ou t proper ly, somth       ing     just   ain   '      t right.    >:( 
                       float    cx =          y  ;
                     fl oa t cy =      -x;
                              
                                   fl        oat newX = cx     *cos - cy*sin;
                  floa   t n       ewY = cx*si  n + cy*c   os;
                   
                    cx  = new           X +          ownerPC.   getX();
                                    cy = newY + ow   ne  rPC.getY();
               
                   actor.m     ove(c  x      , cy    );
               
               actor.update              (deltaMs);
           }
         }
    
    priva     te ArrayLi st<ActorSlo       t> actors;
              
       publi c              ActorComponent(   )    {
        ac          tors               =     new ArrayList<ActorSlot>();
    }
    
      @Overri      de
    public void     update(lo        ng d   e ltaMs) {
               for(Acto  rS  lot as : actors) {              
                         a    s.update   (   deltaMs) ;
        }
           }  
              
      publi c      ArrayLi      st<GameA  c     tor>   g  etAc     tors() {
        Array           Li      st<GameActo     r> list =    new Ar   rayLi   s           t<GameAc       t  or>();
           for(ActorSl       ot as      :   actors) {     
            list.a   dd(          a  s.actor) ;
              }
              return    l    i     st;   
    }
       
                     @Override
    p   ublic void   creat  eFr   o     mX    ML        (NodeList attribut         e  s) {
        No  d  e    nod  e      = attrib   utes.item(0   );
        whil    e (node != null )   {    
                     GameA   c                          tor a  = null;
              float X =    0;
                      float Y = 0;
                                                  if(node.ge  tNod  eT  ype() =  = Node    .ELEM    ENT_NODE && nod e.get NodeN   ame().equa     lsIgn   oreCa     se("    acto  r") ) {
                        N    ode n          = node     .  get       FirstChi         ld()          ;
                 whil e(  n != null) {
                         if(n  .getNodeType()     == N      ode.ELEMENT_NODE) {
                                                  if(n.g   etNodeNa    me(             ).e     qua       lsIgnoreCase     ("Res  ource")) {
                                   a = A pplication.get                ().getLogic(  ).createAc  tor(n.        g  et  FirstChild().g     etNodeVa  lue()             );
                            }
                                                 if(n.getNodeName  ().equalsIgno   r  eC ase("x")) {
                                    X =      Float.par   seFloat(n.getFirs    tChild   (  ).getN  odeV           alue());
                        }
                              if(n.ge   tN     odeNam     e    ().     equalsIgnor   eCase("y")) {
                            Y = Float.parseFloat(n        .getFirs   tC   hild()          .getNodeValu   e()  )   ;
                              }
                                }
                        n = n.getNextSibling();
                     }
                   PhysicsCompone    nt pc = (PhysicsC omponent)a.getC          omponent("PhysicsComponent");
                        PhysicsComponent o  PC = (Physic  sComponent)owner.getComponent("PhysicsComponent");
                pc.setZ(oPC.getZ()+1);
                      actors.add(new ActorSlot(a, X, Y));
            }
             node = node.  getNextSibli ng();
        }
    }
    
    @Override
    public void destroy() {
        for(ActorSlot as : actors) {
            Application.get().getEventManager().queue   Event(new ActorDestroyedEvent(as.actor));
        }
    }
}
