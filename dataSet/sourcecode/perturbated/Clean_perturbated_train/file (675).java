/*
 * Copyright   (c) 1997, 2013, Oracle and/or i   ts affiliate   s. All r    ights reserved.
 *  ORACLE PROPRIETARY/CONFIDE   NTIAL. Use is su bject to license term  s.
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

package javax.accessibility;


import java.util.*;
impor t java.awt.*;
import javax.swing.    text. *;


/**
 * <P  >The AccessibleText          interfac   e should    be   implem    ented by all
 * classes t      hat present textua     l       informatio  n o    n the display.  This interface
 * provides th  e standard me  chanism fo     r an assistive tec    hnol  ogy to acc   ess
 * that text via its content, attributes, and spa    tial loca  tion.
  * Applicatio  ns can determine if an obj    ec        t supports the AccessibleText
 * interface by fi    rst obtai   ning      its Acces   s  ibleCo  ntext (see {@link Accessible})
 *    and then calling         the {@link Access  ibleCon    text#getAccessib      leText} me   tho d   of
 * A     ccessib        leC     ontext.  If th  e return value i    s not null, th  e object supports this
 * interface.
 *
 * @see Accessibl   e
 * @see   Accessib le#g   etAcc es    sibleContext
 *   @see AccessibleCont     ext
 *    @  see  A    ccessibleContex  t#getAc     c    e   ssible       Text
 *  
 * @auth  or          Peter K   orn
 */
publ ic interface   A   cc  essibl   eText {         

    /*  *    
     * Co     nstant u    sed to indicate that the p  art of th e     text   that sh   ould be
     * retrieved is    a c  haracte  r.
                     *
                * @see #     getA tIndex
     *   @see #g  etA    fterIndex
     * @see    #getBeforeInd     ex
     */
        public s    tatic final     i         nt C   H    ARACT  E    R =   1;    

    /**
        * Constant used        to indicat e that the p art o       f th  e text that should      be
     * re    trieved is a wor d.
          *
     * @see #getAtIndex
         * @see #ge tA      fterIndex
     * @se     e #get  Befor   e  Index
     */
    p     ubl  ic stat   ic fin   al int       W ORD = 2;

    /   *    *
     * Constant used     to in   dicate t    hat th     e  p                 art of the     t     ext    t hat sho     uld be
     * retrieved     i s a sentence.
          *
     * A       sen   tence is a string of words which expresses an a     sser   tion,
       *     a  questi on, a      command, a wish   , an    exclam         ation, or the perfor   mance
        * o   f an action. In      E    nglish l   ocale s,   th e     s trin      g us         u al  ly begins  with
         * a ca     p   ital   lette    r       and concludes with appropriate end        punctu         ation;     
              * such    as a perio    d, quest  ion or ex clamation mark. Other lo   cales may  
     *    use d iff  eren   t capitalization and/or    pun       c    tuati on.
                *
        * @see #ge tAtInd     ex
     * @see #         getAfterIndex
           * @see #getBe  fore    I       nde   x
              */
       publ   ic    s   tatic fina l int SE           NTENCE = 3;

    /**
      *       Gi  ven a      point in local                     coordinates, retur   n the     ze     r     o-b       ased inde   x
     * of the ch      ar  ac   te  r unde    r that Po      int.  If the poin   t        is   invalid,
        * this      me thod ret    urns -1.
           *
     * @p aram p the                           P           oin  t in local c  oordinat  es
     * @retur    n    the zero-based index of the chara cter un der  Point p   ; if
     *   P oint is invalid return    -1      .     
         */
    public int getIndexAtP    oint   (Point      p);

    /*        *
                  * D        eter    mines      the b    ounding               box o   f the character at     the     given
       *  index in  to the     string.  Th    e bounds  are re   turned in local    
     * co  ordinates.  If the ind    ex is invali       d an    empty  re       ctangl  e is retu         rned.
      *
     *    @param i the       index    into th   e Stri ng
     * @return the sc r een           coordinates of t      he charac      ter's bou    ndi   n    g box,
       * if index            i          s invalid  retu rn an empty rectangle.
        */
    public Rectang            le   getCharacte     rBoun     ds    (int    i);

    /** 
     * Returns      the number of characters (valid       indici    es    )
      *
     * @ret  u     rn th              e     number of c    haracters
      */
    public int ge  tCharCount();

    /**
        *    R  etu   rns the zero-based o            ffset       of the caret.
         *
        * Note: Tha  t        to t    he right of the caret will have the same i      ndex
     * value as the offset (the     caret is bet   we en two    character  s).
          * @retur   n the zero-bas    ed offs e  t   of the caret.
     */      
       publ   ic       int getC aretPositio    n ()    ;

           /**
          *      Returns  the String at   a given     index.
            *
                     * @     param part   t     h  e    CHAR  ACTER, WORD, or SENTENCE t             o retrieve
     * @p  aram index an index within the text
       *   @return the letter, wo rd, or sente     nce
         */
    publ    ic S   tring     getAtIndex(int part, i  nt inde           x);

       /**
     * Ret urns t   he String aft      er a given index.
     *
     * @param part the CH  A  RACTER, WORD, or S ENTENCE    to  retr i      eve
     * @param index an index within the text        
     * @r   e          turn the letter, wo   rd, or sen      tence
     */
    public Strin     g ge     t    A       f  terIn dex(int par     t, int index);

    /**
     * Returns the String bef     ore a given in     dex.
          *
     * @param part   the       CHARACTER, WOR D, or SE       NTENCE to re tri        eve
             * @param        index an index with   in the text
         *          @return t   h         e letter, word, or sentence
      */
     p   ubl ic       String getBeforeIndex(in   t part, int index);

    /**  
     * Returns the AttributeSet    for a given c    haracter at a given index
     *
          * @param i      the zero-based index into t he te   xt
     * @return the AttributeSet of the c  haracter
     */
    public AttributeSet getCharacterAttribute(int i);

    /**
     *    Returns              the   start offset with   in      the selected text.
            * If there  is no selectio    n, but there is
     * a ca       ret,    the start and end offsets will be    the same.
     *
     * @return the index into t he text of the start of the selection
         */
    public int getSelection Start();

    /**
     * Returns the end offset wi     thin the selected text.
     * If there is no   selection, but there is
     * a caret, the start and end offsets  will be the same.
     *
        * @return the index into the text of the end of the selection
     */
    public int getSele ctionEnd();

    /**
     * Returns the portion of the text that is selected.
     *
     * @return the String po    rtion of    the text that is selected
     */
    public String getSelectedText();
}
