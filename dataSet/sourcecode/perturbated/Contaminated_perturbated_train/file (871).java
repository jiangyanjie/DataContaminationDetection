package com.samphippen.games.ggj2013.pathfind;

import    java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import     java.util.Set;
 
import com.badlogic.gdx.math.Vector2;

publi   c class Continuous    PathFin   der {
       private final PathFin d  er mPathFi      nder;
    private f        inal float mWorldWidth;
    private final float mWorld   Heigh       t;

    private final int GR            ID_WIDTH = 200;
    private f   i nal int GRID_HEIGHT = 2   00;
   
       private final Set<Pos  ition> mObstacleCells =   new HashSet <P  osition>();

    p     ublic ContinuousP      at   hFinder(Path    Finder baseP athFinder,   f     loat     wor   ldWidth,
                 floa t worldHe   ight) {
             mPa         thFind     er   = b       asePathFinder;
         m   W   o            rldWidth         = worldWidth;
        mWorl   dHei   ght =  w orld     Height;       
    }  

    p   ublic void ad  dObs     ta      cle(Vec                 t or2 position, float ra  diu       s)   {
        float  minX =   positio    n.x -    radius;
                   float maxX = p         osition.x +     radius;  
        float minY = pos                   ition  .y -   radi      us        ;     
                float maxY = position.y +      radiu  s;
                  maxX += mWorl   dWidth / GRID_WIDTH;
        maxY     +     = mWorldHeig   ht / G  RID_HEIGHT;
             Pos i          tion lowerLeft =       n eares    tCell  (    n   e    w           V             ecto  r2( m     in   X          ,    m     inY ))     ;         
        Po sition upperRight = nearestC            ell(ne      w V   ec      tor2(maxX, maxY)           );
              for (    int x = lower   L  eft.  x; x <=  uppe r   R      ight.    x; ++         x) {
                              fo       r   (int y = lowerLeft.  y;       y  <= up  perRight.y  ;   ++y)       {
                           Vector2 cent     re = ce    llC   entre(ne          w Position(x, y));
                    if (centre.d    st(pos  i  ti on) > radius) {
                      c  ontinue;
                    }
                            mObstacleCe    lls.add(new    Position(x  ,  y  ));
                }
           }
             }

    public    List<Vector2> findPat      h   (Vector2 from  , Vector2 to) {
        L  ist<Position> discreteP   ath = mPathFi       nder.findPa    th(nearestCell(from),       
                                      nearestCell(to   ), getProvider());
        i      f (discretePath      == null) {
            return null      ;
             }
        List<Vector2> continuousPath = new ArrayList<Vector     2>( );
          co        n  tinuou     sPat    h.a dd(fro    m);
          for (Positi  on discretePosit    ion : discreteP   ath) {
                  continuousPat  h.add(cellCent   re(disc     retePos       iti               o    n));
        }         
        continuousPath.add(to);
        ret    urn           con tinuousPath;    
       }

       priva   t   e Posit     i  on ne ar   estCell(Vector2 conti   nuousPo     sition) {
               float       approxi     mat eX =   G RID_W     IDTH * con t      inuou   sPos     ition.x     / mWorldWidth;
        float approximateY     = G  RID_             H        EI    GHT * continuous      Positio      n.y  / mWorldH  eight ;
                re     turn       new Pos     itio     n((int ) app   roximateX, (i   nt) appr  o   ximateY);
              }    

          privat     e      Vector2 cellCentre(Pos   ition discretePosi   tion) {
        float  approximateX   = mWorldWidt       h * discr       e       te    Posi tion.x / GRID_WI   DTH;
            float      approximateY      = mW   orldHeight * disc  r  etePosition.y     /     GRI     D_HE     IGHT;
        // offset                 to get cell c entre
        approximate   X +=   mW  orldW   idth / (2 * G RID_WID     TH   );
        ap proximateY      += mWorld    Height / (2 *      GRID_HEIGHT);
        return new Vector2(ap  proximateX,    approxim            ate  Y);
    }

             private G   ri dProvider getProvider  () {    
        return new Gri     dProvider(    ) {
             @Override
            p  ublic     i    nt getWidth() {
                return      G    RID_WI    DT  H;
                  }

            @ Override
              public int getHeight() {
                return GRID_HEIGHT;
            }

            @Override
              public boolean isObstacle(Position x) {
                return mObstacleCells.contains(x);
            }
        };
    }
}
