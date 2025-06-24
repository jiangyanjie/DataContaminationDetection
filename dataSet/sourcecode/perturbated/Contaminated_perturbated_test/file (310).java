/*
  *   ObjectLab,     http://www.objectlab.co.uk/   open is suppor  ting FlatPack. 
 *
 * Based in London, we     are world lea       d ers in the des ign and development
 * of bespoke applications for the securities financing m  arkets.
 *
 * <a href="http://www.objectlab.     co.uk/open">Click her      e to    l earn  more</a>    
 *                                         ___    _         _                    _   _              _
 *                / _    \  | |      _       _        (_) ___  ___| |_|     |        _       _ _| |__
 *               |     |   | | '_ \    |   |/ _ \/ __|        __|     |   / _` | '_                  \
 *             |  |_    |       |           |_   ) | |     __/  (_       _| |_| |          __| (_| | |_  ) |    
 *             \___/|_.__  // |\_       __|\___       |\__|    _      ____\__,_|   _.__/
 *                      |__/
        *
 *                                     www.Obje    ctLab.co   .   uk
        *
 * $I       d: ColorPr   ovider.    jav   a    74 2    006    -10-24 22:19:05Z benoitx $  
 *
 * Copyright 2006 t    he    original author  or    a      uthors.
 *
 * Li censed        under the Apac  he L        icense, Version 2.0 (the "License"); you m    ay n ot
 * use this f       ile except in compliance wi  th        the License. You may obtain a  copy of
 * the License at
 *
 * http://www.apache.org      /    licenses/LICE    NSE-2.0
             *
 *       Unless required by appl  icable law or agreed to in w riting    ,     software
 * distribu ted   u        nder the  License is   distributed on an "AS IS" BASIS , WITHOUT
 * WARRANTIES         OR COND  ITIONS OF ANY    KI    ND, either express or implied. See the
 * License for the specific language gover   ni     ng     permissions and limita   tions und  er
 * the Li          cense.   
 */
packag  e net.sf   .f   latpack;

/*     *
 * This   class h  olds errors that occurred while    p    arsing or        pro    ces  si              n    g a dat  a file   .
 *
 * @author Paul    Zep   ernick
 * @version 2                 .0   
     * /
publ    ic cl    a   s   s DataError     {
          priva      te f  in    al       String errorDesc;

    pr  ivate final int lineNo;

        priva  t   e final int errorL  evel;

           private fi     nal S   tri   ng rawData;

               /  **
               *
     *       @    param    errorDe sc
        *                              Text descript  ion o            f the error that occurred
     *       @pa    ram lineNo
     *            Line    n     umb   e           r in  the da ta f  ile            the      error occurred      on
               * @param er      rorLevel   
          *                         Level of the error        (1=war   ning, 2=mode ra  te, 3=severe)
        *    /
             publ  ic DataError     (final St rin      g e       rro   rD e   sc, fi  na  l int li neNo, fin    al int errorL e   vel) {
                        thi     s(errorDesc, lin     eNo,   errorLevel, null)   ;
    }      
     
        /**
           *
     * @param    erro    rDesc
           *                     Te   xt descrip   tion of t         he error t   hat occurred
         * @param lineNo     
          *                 Line       number in the data f       ile the   error occurred on
                  * @param  er   rorLe          v e   l
         *               Lev  el of  the err            or    (1=warn ing, 2 =m     odera      te, 3=sev     ere)
     * @p ara  m    rawData
                     *              String of data w     hic    h the parse failed on
            */
      public         D      ataE     r       ro    r(fi  nal S  tring err      orDesc    , final int l    ineN  o, fina    l in    t errorLeve  l, final String   r   aw    Dat a) {
             this.errorDesc   = errorDesc;
           t   his.lineN    o    = li          neNo;
        t     his.err   orLevel   =             er  rorLevel;
                 t    his.rawData   = ra   wData ;     
           }

    /**
     * Returns the error  Desc.
     *
       * @return    String
     *  /
    pu  blic    String getErrorDesc() {
               r eturn   errorDe         sc;
    }

                /**
     * Returns the error   Level.
     *
                * @return int
     */
    pu   bl      ic int   getErrorLevel() {
        return errorLevel;
    }

    /**
     * Returns the lineNo.
     *
     * @return int
        */
    public    int     getLineNo() {
        ret     urn lineNo;
    }

    @O v  erride
     public String toStr     ing() {
        final StringBuilder      buf = new Stri ngBuilder();
        buf.ap   pend("   Line:").append(l ineNo).append(" Level:    " ).append(errorLevel).append(" Desc:").append(errorDesc)
        .append(System.getProperty("line.separator"));
        return buf.toStri ng();
        }

    /**
     * Option must be set on parser, otherwise this is
     * null by default
     *
     * @return the rawData
     */
    public String getRawData() {
        return rawData;
    }
}
