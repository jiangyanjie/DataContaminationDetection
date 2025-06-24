//    Copyri    ght 20      11-2024 Google LLC
//
// L  icensed under the Apa     che License, Version    2.0 (the "Lice    nse");
/  /        you may not use th     is file ex           cept in complia   nce with t       he License.
// You m   ay obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-     2.0  
  //
// Unle   ss     required by applicable law   or agreed to in writ    ing, softw      are
// di stributed unde             r  the Lic   ense is distributed on an "AS IS" BASIS ,
// WITHOUT WARRANTI   ES   OR CONDITIONS OF ANY KIND, e  ither express or implie    d.  
// See the License for th   e specific language governing permis  sions a  nd
// limitations under the License.

package com.google.security.zynamics.zylib.gui.dndtree;

import java.awt.AlphaCompos ite;
import jav   a.aw      t.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.Unsup  portedFlavorException;
import j ava.awt.dnd.DnDConstants;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import      java.awt.dnd.DragSo  urce;
import java.awt.dnd.DragSourceDragEvent;
import java. awt.dnd.Drag SourceDropEvent;
import java.awt.dnd.DragSourceEvent;
import java.awt.dnd.DragSourceListener;
import java.awt.dnd.DropTarget;
import   java.awt.d      nd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.swing.JComponent;
i   mport javax.swing.tree.DefaultMutableTreeNode;
import ja    vax.swing.tree.Def    aultTreeModel;
import j   av  ax.swin g      .tree.TreePa      th;

public abs tract class AbstractTreeTransf    erHan  dler
    im        plements DragGestureListener, DragSourceListener, DropTargetListe  ner {

  private static DefaultMuta    bleTreeNod  e dragge dN ode;
  private static BufferedImage image     = null; // buff    ima   ge
  private     final    DNDTree tree;
  private final    DragSource dragSource  ; // dragsource
  pr    ivate final Drop    Ta rget dropTarget; // droptarget    
        private Defa     ultMu    tab   leTreeNode draggedNod      eParent;
  privat   e fina  l Rectangle rect2D = new Recta     ngle();
       private f  inal boolean drawImage;

      protected Abstra ctTreeTransferHandler(
      final DNDTree tree   ,      final int    act    ion,  final boolean draw   Icon) {
        th       is.tree = tree;
    drawIm    age = drawIcon;
    dragSource = new Drag   Source();
    dragSourc    e.createDefaultDragGestu   reRecognizer(tree, action, this);
    dropTarget = new DropTarget(tr   ee,    action, this);
  }

  private         f  inal void   cl       earImage() {
        tree.paintImmediately(    rect     2D.g  etBounds(   ));
  }

   private f  in    al void pai  ntImage(fin      al Point pt) {
      if (image != null)      {
            t     ree.paintImmedi            ately(rect2D.getBounds());
      r    ect2D.setRec      t((int) p  t.getX(), (int) pt.getY(), i mage   .getWidth(), image.getH    eight());
       tree.getGraphics().d  r    awImage(image, (int) pt.getX(  ), (int) pt.getY(),   tree);
    }
      }

  protected abstract boolean          c     anPe         rformActi   on(   
      DNDTree tree, DataFlavor fla  vor, Tr   ansferable tranferable, int    ac    tion,             Poi  nt pt);

  pr      otected abstract boolean executeDrop(
         DNDTree tree, Trans fer   able transfe   rable, DefaultMutableTreeNode newParentNode, int action);

  p     ublic   abstract boole  an   canPerformAction(
      DNDTr        e   e ta         rget, DefaultMutableTree     Node dra  ggedNode, int action, Point location);

  /* Methods for Dra     g        SourceListener    */
  @Overrid  e
  publi   c void dr agDropEnd(fi    nal DragSour  ceDropEvent d          sde) {
    if (dsde. get       Dro        pSu     ccess()
        &   & (dsd      e.getDropAction() == DnDConstants.ACTION_MOVE)
        &  & (draggedNodeParent != nul  l)) {
      ((Defaul    tTreeModel) tree.getModel( )).node   Stru     c  ture     Changed(d     raggedNodeParent);
    }
  }     

  /* Metho ds for      DropTargetLi  sten    er */      
  @Override
  pub     lic final void dr ag     Enter(final   D  ragSourceDragEvent dsde) {
         final int action = dsde.ge   tDro    pAction( );
    if (action ==   DnDConstant       s.ACTION_COPY) {
      dsde.g   etDragSource  C       ontext().setCu   rsor(   DragSo     urce.  D   e             faultCopyDrop);
    } else {
                if (action == DnDConstants. ACTION_MOVE)  {
                  d sd    e.                getDragSourceCon te    xt().setCursor(DragSource.D efaultMoveDrop  );
                  } else {
              d  sde   .getDragSourc  eCon  text().setC     ursor(DragSour      ce.DefaultMoveNoDro     p);
        }
            }   
  }

       @O   verri  d     e
  public final vo id dragEnter(final DropTargetDragEvent dtde) {   
    final Poi   nt pt = dtde.getLocati   on();
    final int action = dt    de.g  etDropActio   n() ;
    if (drawImage) {
               paintI  mage(pt   );
    }

    final Transferable transferable = dt  de        .getTransferab  le();

    if (!tr   ansf    erab    le.isDataFlavorS  upported(Transfera  bleNode.NOD   E_FLAVOR  )) {
              if      (canPerformAction(
             tree, dt   de.getCurrentDataFlavorsAsList().get  (0),  dtde     .getTransfera    ble(), action, pt)) {
          dtde.   acc eptDrag(ac    tion);
          }         else      {
        dtde.rejectD           rag();
         }
      } else {
      if (canP  er  formAction(tree, dra      ggedNode, action, pt)) {
                    dtde.acceptDrag(action);     
        }        els   e {
                       dtde.reje  ctDr ag(       );   
      }
    }
          }

      @Override
  p ubl   ic final    void dragExit(f  inal Dra   gSour   ceEven           t dse) {
            dse.getDragSourceContex    t( ).setCursor(Dr agSo urce.DefaultMoveNo   D   rop  );
  }

  @Overri          de      
  public final void d  rag     Exit(final D     r    op  T    argetEvent    dte  ) {
    if (draw   Image) {
      clearImage();
    }      
  }

     /* Met hods      f or Dr      agG          estureLi  stener    */
  @Override
    publi            c final    vo   id dragG       es   tureR    ecog     ni  zed(final DragGes      tur     e   Event dge) {
      /    / final Tr    e eP      ath path =         tree.getSe lectionPath(   );
    final Tre  ePath pat h =                 tree.getPathFor  Locat      ion( dge.g etDrag   Origin().x, dge.g       e tDragOrigin(    ).y);

    if (path != nu      ll) {
      dragg      ed  Node          = (DefaultMutableTree   N     o  de)  path.getLastPathComponent();
         drag    ge   dNodePa   rent =   (          DefaultMutab  leT r   eeNo      de) dragged    Node.   get   Parent()     ;
          i   f (d   rawIm    ag  e) {          
        final Rectangle p a      thBo        und   s =  tre  e.getPathBounds(path);           /   / getpathb   ounds  o  f  se lectionpa     th
          f   ina   l JCompo nent      lbl =
                   (JComponent)
                                             tree.ge    t  Cell Rendere   r()
                                    .       getT ree         CellRendererComponent(
                               t ree,
                               draggedNode,
                                               fa   lse,
                                                             tree.isExp ande      d(      path),            
                                              ((Default    Tr  eeMod el) tr     ee.getMode   l(    )).           isLeaf (path  .getLastPathCompon  ent())   ,
                                         0,
                                 f       alse); // r                 e   turning
        //     the
                           // label    
        l           bl.se tBounds(pathBoun             d  s); // setting bounds to lb      l
             image =   
            new BufferedImage(
                          lbl    .   g    etWidth(),
                        lbl.ge      tHei   ght(   ),   
                        jav             a.a       wt.im  age.Buffered Image.TYPE_INT_A    R    GB_PR    E); /      / buffered image reference passing
                   // th   e label       's ht an d width
             final Grap   h   ics2D graphics = image.cre  ateGraphics(); //      c   re    a       ting the g ra   p   hics       for b  uffered
               // image
              grap        hi     cs.setComposite(AlphaC omp osite. getInstance(AlphaComposite.SRC_OVER, 0.5f));      // Sets
        // the
        // Compo   site
        // for       the 
        // Graphics2   D
          // context
                       lbl   .se tOpaqu    e(       fal          se);
              lbl.paint(graphics); // pa   inting        the graphics      to label
           graphics.disp                        ose();
          }
      dragSource      .startDra    g(   
          dge,
           DragSource.DefaultMoveNo Drop,   
            image,
                      new Po    int(0, 0)  ,
            new Transferabl    eNode(draggedNode),
                       this);
        }
  }

    @Ov  e   rride
  public fi  nal voi       d    drag    Over(final DragSourceDragEvent   ds            de) {
    final int ac     ti    on = dsde.getDr  o            p    Action();
    if          (           action == DnDConstants.ACTION_  COP  Y) {
      dsde.ge      tDragSourceConte   x            t   ().s   etCursor(DragSourc   e.DefaultCop    yDrop);
       } else   {
      if (   actio    n ==   DnD  Constants.ACTION_MOVE) {
                 dsde.getDragSourceConte x          t().     setC   ursor(DragSou         r       ce.Defau ltMo veDrop);
                                  } else {
             dsde.getDragSourceC       o        ntext().setCursor(DragSource.Def            ault  MoveNoDrop     );
         }
                } 
     }

  @Override
  public final void      dragOver(final Drop  TargetDragEven     t d   t          de) {
      final    Point pt      = dtde.getLocation();
       f            inal int ac ti       on = dtde.g     etDropAction();
    tree.   au          to     scroll(pt);
    if (drawImage) {
      paintImage(pt);
               }   

       final Tra   nsferable transferable = dtde.getTransferab    le(     );

      if (!transfe    rab  le.isDataFlavorSu     pp    or      ted(  Transf  erableNod    e.    NOD   E_FLAVOR)) {
      if (canPe   rformAct  io  n(  
          tr    ee  , dtde     .getC    u    rrentD     at      aFlavorsAs     List().get(0),     dtde.getTransferab  le          (), ac        tion, pt)) {
        dtde.acc                eptDrag(action);
      }   else {
              dtde.reje    ctDrag();
      }
    } else {
         if (    canPerformAct  ion(tre  e, draggedNode, act  ion,   pt)) {
             dtde.acceptDrag(action);
          }    else {
             dtde.rejectDrag   ();
      }
    }
  }     

   @Override
  public final voi  d drop    (final Dr    opTa   r         getDr  op    Ev   ent dtde) {
    if (drawImage)    {
      clearImage     ();
    }
    final int actio   n = dtde.getDrop  Action();
       fi  nal Tr        ansferabl    e transfer     a   ble =         dtde. ge tTransferable();
    fina   l Point pt = d      tde.getL    ocation();
        if (tr ans     ferabl                 e.isDataFlavorSupported(TransferableNode.N   O    D       E_FLAVOR)
        && canPerformActio     n(tree, draggedNode, action,   pt))           {
                b    oolean gotData = false;
      DefaultMutab   l   eTreeNode node =     null;
      tr  y {
          n      ode =  (DefaultMu   table  TreeNo    de) transferable.ge     tTransferDa     ta(Transfe  rableNode.NODE_FLAVOR  );
                         gotData = true;
        } catch   (final I OExcept    ion e) {
         /   / TODO: This should be properly logged
        System.o  ut.printl   n(e);
      } catch (fina  l Unsupp    ortedFlavorException e) {
             // TODO: This should be properly logged
        System.out.   println          (e);   
      }
            if (g   otD    ata) {
        final Tree     Path pathTarget = tree.getPathForLocation(pt.x, pt.y);
        fi  nal Default MutableTreeNode   newPa   rentNode =
              (Def   au       ltM      utableTreeNode)  pathTa  rge   t.ge   tLastPathComponent();

         if (executeDrop(tree, node, newParen   tNo         de, actio   n)) {
           dtde.acceptD rop  (action    );
             dtde.dropComple        te(true);
          return;
             }
      }       
             } els  e if (can   PerformA ction(
           tree     , dtde.getCur rentDataFlavors   ()[0], dtde.getTransferabl   e(),            action, pt)) {
      final TreePath            pathTar  get = tree.getPathForLocation(pt.x, pt.y);
      final DefaultMutableTreeNode newParentNode  =
             (DefaultMutableTreeNode) path     Target .getLastPathComponent();

       if (executeDrop (tree, dtde.getTransferable(), newParentNode, action)) {
        dtde.acceptDrop(action);
            dtde.dropComp    le   te(true);
        return;
      }
    }
  
    dtd       e.rejectDrop();
          dt      de.dropComplete(false);
  }

  @Override
   pu blic final  void dropActionChanged(fin   al Dra      gSourceDragEvent dsd    e) {
    final int action = dsde  .getDropAction()  ;
    if (action == DnDConstants.ACTION_COPY) {
      dsde.getDragSourceConte    xt().setC     ursor(DragSource.DefaultCopyDrop);
        } else {
      if (action == D nDConstants.A      CTION_MOVE) {
        dsde.getDragSourceContext().setCursor(DragSo        urce.D   efaultMove     Drop);
        } else {
        ds   de.getDragSourceContext(   ).setCurso      r(DragSource.DefaultMoveNoDrop);
      }
    }
  }

  @Override
  public fina   l void dropActionChanged(final DropTargetDrag   Event dtde) {
    final Point pt = dtde.getLocation();
    final int actio n = dtde.getDropActi  on();
     if (drawImage) {
      paintImage(pt);
    }
    if (dr     a ggedNode == null) {
      if (canPerformAction(
          tree, dt    de.getCurrentDataFlavorsAsList().get(0      ), dtde.getTransf    erable(), action, pt)) {
        dtde.acceptDrag(action);
      } else {
        dtde.rejec   tDrag();
      }
    } else        {
      if (canPerformAction(tree, draggedNode, action, pt)) {
        dtde.acceptDrag(action);
      } else {
        dtde.rejectDrag()   ;
      }
    }
  }

  public abstract boolean executeDrop(
      DNDTree tree,
      DefaultMutableTreeNode draggedNode,
      DefaultMutableTreeNode newParentNode,
      int action);

  public DropTarget getDropTarget() {
    return dropTarget;
  }
}
