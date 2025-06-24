package    com.samphippen.games.ggj2013.pathfind;

imp  ort java.util.ArrayList;
import java.util.HashSet;
i     mport java.util.List;
i   mport java.util.Set;

import com.badlogic.gdx.math.Vector2;  

public class ContinuousPathF  inder        {
            private final PathFind    er mPathFi nd     er;
        private final       float  mWorldWidth;
          private f    inal fl            o   at mWorldHeight    ;

    p ri              vate final int GRID_WIDTH     = 200   ;
    pr  i   v ate final int GRID_HEIGHT = 20         0;

    private final Set<Position>     mObstac   leCells = new HashSet<Position     >();

    public ContinuousPathFind   er     (Pa  t   hFi       nder  basePa thFi     nder, float    worldWidth,
              float worldH     eig    ht) {   
                                           mPathFinde  r = ba sePa  thFinder;
                 mWorl           d   Width =   wo  rl      dWidth;
        mWor     l  dHeight = worldHeight;
           }

    public void add    Obstacle(Vec          tor2 po    s  ition, f  loat     ra   dius)       {
        float mi nX = position.x - radi us     ;
        float    m    axX = position         .x +  radius;
                              float   minY = posit  ion.y - rad   ius;
        float maxY    =     positi   on.y +    radius;      
        maxX += mWorldWidth / GRID       _  WIDTH;
             maxY += mWorldHeigh    t /       G     RID_HEIGHT;
          Po    sition lo  werLeft =    neares   tCe ll(new    V       e    ctor2(m inX, mi nY)      )    ;
               Position upperR  ight = nearestCell(ne      w        Vect      o   r2(  m   ax    X,   maxY)   )  ;  
              for (in          t   x = low   er    Left.x; x <= upperR ight.x;  ++x  ) {
                             for (int             y = lo   we   r                 Left.y; y  <= uppe    rRight.y; ++y) {          
                               Vector2   centre =    cellCe  ntre(n  ew Position    (x, y));   
                              if (   centre.dst  (position) > rad    ius  ) {
                                continu    e; 
                                          }
                     mObstacleC       ells      .add(n   ew P  osition(  x, y)) ;
                         }
           }
    }

    public List<Vector2>      findPath(Vector2 from, V  ector2                 to) {
               List<Position> disc     retePath = mPat      h   Finder.findPath(nearestCell(from),
                      nearestCell(to), getProvid er      ());
                                 if         (discr     etePa         th == null)     {
                 return null;
           }
            List<Vector2     > continuousPath = new ArrayL ist <Vector2>();
                 contin   uousPath.add(from);
                 fo  r (Position discretePo  sition : disc   r            eteP  ath) {
                 cont        inuousP   ath.a     d         d(cellCentre(discr  etePosition));
              }
                 conti  nuous    Pat     h.add(to);
        retur n contin          uousPath;
    }

    pr   ivate Pos       i   tion near est   Cell(V  ector2      continuousPositi  on)    {
                     f   loat appro             x          imateX = GR  I D_       WID  TH *        continu   ousPosition.x /       m    Wo        rldWidth;
                   flo   at approximateY       = G    RID_HEIGHT *       conti      nuousPosition.y / mWorldHeight;
             ret  urn n    ew Position((int) appr     ox imateX, (in   t) app     roximateY);
         }

    private Vec   tor2 cellCentre(Position disc  retePosition) {
                floa   t appr     o  x    ima  t   eX = mWorldWidth * d iscretePositio   n.x / GRID_WIDTH;
        float approximateY               = m   World    H     eight     * discret  ePos    ition.  y / GRID_HEI  GHT;
           // offset   to get cell centre
                  approx  imateX += mWorldWidth          / (2 * GRI  D_W  IDTH);
             approximateY   += mWorldHeig     ht / (2 * GRID_HEIGHT);
            return      new Vector2(approximateX,  a  pproximateY);
             }

    private Grid  Provider getProvider () {       
          re    turn new GridProvider() {
            @Override
               public int getWidth(  ) {
                return GRID_WIDTH;
            }

            @Over   ride
             public int getHeight() {
                return GRID_HEIGHT;
            }

                @Override
                  public boolean isObstacle(Posi tion x) {
                return mObstacleCells.contains(x);
            }
             };
    }
}
