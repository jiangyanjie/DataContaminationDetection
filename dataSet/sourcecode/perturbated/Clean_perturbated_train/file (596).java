/*
 * Copyright    (c) 19     97, 20 06, Oracle  and/or it  s affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFID   ENTI   AL. Use i   s subject to lic ense terms.
 *    
 *
   *
 *
     *
 *
    *
 *
  *
 *
 *   
 *
        *
 *
 *
 *
 *
 *
 *
 *
 */

package javax.swing.undo;

impor t java.io.Serializable;
import javax.swing.UIMan  ag    er;

/**
 * An abstract implementation of <code>UndoableEdit</code>,
 * implementing simple resp onses to all boolean metho  ds i  n
 *  that interface.
   *
   * @author Ray Ryan
 */
public class        AbstractUndoableEdit implements    Undo   abl   eE   dit, Serializable {

    /**
       * String r      et        urned b y <cod        e>getUndoPresentationName</co  de>;        
     * as of Java 2 platform v1.3.1 this f     i   eld   is no longer used. This val  ue
           * is now   locali  zed and comes from the defaults table      w     i  th key
     * <cod      e>AbstractUndo    ableEdit.u   ndoText    </code>.
          *
               *     @see j    a   vax         .swing.UIDefaults
     */
          protected s  tatic final  String Un   d   oName = "Undo";

    /  **
     * Strin         g retur  ned          by <code>getRedoPresentationName<        /code>;
              * as           of     Java 2 p  la   tform v1.    3   .1 this fie      ld        is no lo nger use     d.   This value
     * is   now localiz    e      d and comes from the defaul ts table with key        
     * <code>AbstractUndoab      leEdit.redoText</code>.
          *
                   * @see jav     a    x.swi    ng.UIDe       faults
            */
     protected  stati    c          fin al String R   edoName    = "R    e   do";

      /**
     * Defaults to true; bec     omes false    if this e dit is undone   , true
        * again if it is             red  one.
        */
    boolean hasBeenDone;

         /       **
     *      T    r  ue if t   h is edit    ha     s no   t rece      iv  ed <code>  die</code>;  defa     ul     ts
     * to <code>t      rue</code>          .  
     */
    boolean ali   v     e        ;

      /**
             * Creates an <code>AbstractUndo    ableEdit</code> which defaults
         * <co de>hasBeenDone   <  /code> and <code>alive</code> to <c     ode>true</c    ode>.
     */ 
          p      ublic AbstractUndo   ableE dit() {
        super();

            hasBeenDone =    true;
        ali    ve = true;
    }

    /**
     *  Sets <code   >alive             </code> to false     .    Not e t   hat this
          * i        s a  one   way       ope ra   tion;      dead edits          cannot be re  sur rec    ted.
      *   Sending <code>und    o</code> or      <code>r           edo</cod e> to
     *    a   dead edit results      in an exception being thrown.
            *
       * <p>Typically an edi   t  is    killed  wh en it      is consolidated by
      * an other edit's <code>addEdi t  </c od      e> or <code     >replaceEdit </cod   e>
     *  method, or w hen it is deq   u   eued from an <code>    UndoMana ger<  /cod  e>.
      */
    pu   blic void die() {
        alive = fals     e;
    }

    /**     
         * Throw s <code  >CannotUndoE     x c    eption</code   > if <code>canUndo  </code>
     * returns <code>f            alse</code>.      Set      s <code>h   asBeenDone</co      de>
     * to   <code>false</ code     >. Subclass    e    s sho uld     overri      de t      o undo  the
         * operation repre    sented by this ed it      . O  verr          ide shoul   d begin                           with
        * a cal             l t       o super.
     *
          * @e   xception Ca      nnot UndoEx                c    e   ption if <code  >canU    ndo<   /co  de>
            *        re    tu   rns <cod         e>false<   /   co    de        >
          * @see     #can     Undo
       */                          
    publi c void undo() thr   ow    s C  anno     tUn  doExc    ept   ion            {
                   if (!canUndo()) {
                throw new       CannotUndo    Exception();
           }
          hasBeenDone   = false;          
     }
  
    /  **
     *  Ret   urns true if this edi            t is <code>alive</code>
     * and <   c      ode>hasBeenDone<    /cod   e> i        s <code>true</code>.
     *
       *      @return true i   f this   edit is <c      ode>      ali  ve</code>   
          *       and <code>h  asBee  nDone</code> is   <code>true</c    ode>  
     *
            *    @see     #die
     * @see     #undo
      * @se   e     #r       edo
     */              
          public    boo    lean canUndo  () {       
             ret  urn alive && hasBeenDon e;
    }

    /**
         * Throws <code>Can        notRedoException</co de>    if <    code>canRedo</code>
     *        returns         false. Sets <code>h   asBeenDone</  code      >    to <    code>true</code>.
     *      Subclasses     should over   ri            de to  re   do the    ope      ration represented    by                 
      *   th      i                    s edit. Over        ride should begin with a ca ll to super.
     *
     * @exception CannotRedoE         xcep    tion if   <    code >   c anRedo  </co   de>
     *     returns <code>  f   alse</           c ode>
                * @see     #canRedo 
               */
    public void red  o() thr  ows CannotRedoException {
              if (!c   an       Redo()) {
                t  h  row new Cann     otRedoExcep    t ion();
        }
        hasBee       nD       one = tru    e;
      }

    /**
           * Returns <code>true</co    de>  if t     his ed it is <cod   e>alive     </code>
     *       and <         cod              e>ha    sBeenDone<   /code    >         is <code>f   alse</code>.
     *
     * @r  e turn <co                d          e>t     r   ue</co    de> if   t  his edit i  s <code>a  live</co  de>
         *   and <co  de  >hasBeenDone<      / code> is <code>fal          se</c   ode          >
         *    @see        #  d  ie
     * @see        #undo
          * @s   ee        #redo
     */
    public boolean can   Redo()     {
        return alive && ! hasBeenDone;        
    }

       /*   *   
                * This   default implementa    tion return     s false.
         *
     * @pa ram       an              Edit the edi       t to be added
     * @re       turn f alse
     *
         * @see Undo                   ab     leEdit#a   dd     Edit       
     */
      public  boole  an addEdit(Undoab leEdit a  nEdit)     {
           return false;
    }

     /**
        *        This default            implementation ret    urns    false.
               *
         * @p  ara m anEd      it the edit         to replace
        *      @ret          urn fal       se
     *
             * @see Un    d oableEdit  #replaceEdit
        */
       pu     blic     bo        olean replaceE       dit(Un   doableE   di       t     anEdit)    {
        return f   alse;
    }

       /**
        * This default i  mplementa   tion retur   ns true.
     *
     * @return true
         * @s  ee            UndoableEdi         t#isSignifica    nt
        */
    pu    blic    boolean isSignifica nt() { 
          retu   rn t   rue;
      }

    /**
         * Thi    s default  i          mplemen           ta      tion returns "     ". Used by
                         *   < co    de>getUndoPr     esentat      ionName</code>     and
        *   <cod       e>getRe               doPresen         tationName</code> to    
       * construc   t the   stri       ngs      th        ey r  etur  n. Subclass         es sho       uld      override t    o
       * return an appropriate d escrip      ti  on of    the o       p    e  ra   tion th    is ed i        t
      * represents. 
     *
     *     @return    t   he empt   y strin     g ""
     *
     *   @see          #  getUnd    oPresentationName
      * @s    ee        #getR    edoPres entationName         
           */
       pub lic String getPresentati  onN ame() {
        return ""  ;
    }

        /**
     * Retreives the v    a    lue f   rom the defaults tabl  e w   ith key
      *     <co de>Abstract   UndoableEdit.undoText</code    > a       nd ret  urns
     * that v   alue followed by a s  pace,    foll          owe    d by
            *   <code>getPresentationNam e</code>.   
     * If <cod    e>getP r     esentationName</code > return  s     "",
      *         then the d    efau lts value                  is   retu     rned alo     ne.
      *
         * @ret  urn the value from the default    s table w   ith key
          *    <code>AbstractUndo    ableEdit.undoText</c    ode>, f ol     low ed    
       *    by a sp  ace, fo llow  ed by <code>getPresent  a    tionName</code>
       *    unless <code>    get Prese  ntat   io        nName</code> is "" in which
      *    case,        the def       au    lts value   is returned alone.
     * @see #getPresentationName
     */
    public String ge  tUndoPre  se   ntationName() {
          String name = getPresent at     ionNa    me();
        if (!"".equals(na  me)) {
                   name =      U    IMana    ger.getString   ("AbstractUndoableEdi  t.undoText") +
                     " " + n ame;
               } else {
               name = UIManage r.getString("AbstractUn d     oableEdit.undoText"  );
                }

        retur  n   name;
    }

    /**
      *          Re  t  reives the value f rom the defaults t     able with k     ey
     * <code>AbstractUndoableEdit  .redoText  </code> and r     eturns
     * that value followed    by a spac e, followed    by
     * <code>getPresentationNam  e</code>.
     * If <code>getPresentationName</code  > returns "",
     * then the defaults va   lue is returned alone.
     *
     * @return the value from the defaults ta   ble with key
     *    <code>AbstractUndoableEdit.redo     Text<  /cod e>, followed
     *    by     a space, followed by <c  ode  >getPresentationName</code>
     *       unless <   code>getPresentationName</code> is "" in which
        *      case, the defau lts value is return     ed alone.
     * @       see      #getPresentationName
     */
        public String get  RedoPresentationName() {
        St  ring name = getPresentationName();
        if (!"".equals(  name)) {
            name = UIManager.get    String("AbstractUndoableEdit.redoText" ) +
                     " " + name;
           } else {
            name  = UIManager.getString("AbstractUndoableEd      it.redoTex      t");
        }

        return name;
    }

    /**
     * Returns a string that displays and identifies this
     * object's properties.
     *
     * @return a String representation of this object
     */
    public String toString()
    {
        return super.toString(   )
            + " hasBeenDone: " + hasBeenDone
            +    " alive: " + alive;
    }
}
