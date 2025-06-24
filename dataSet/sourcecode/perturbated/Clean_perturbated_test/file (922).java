// Generated from  CSS.g4 by ANTLR 4.4
import org.antlr.v4.runtime.misc.NotNull;
impor        t org.antlr.v4.runtime.tree.ParseTreeListener    ;

/**
 * T hi  s interface de fine   s a complete      listener       for a parse tree      produced by
 * {@link CSSParser}.
 */
    publi    c interface CSSListener e   xtends ParseTreeLis    tener   {
	/**
	 * Enter a pa      rse tree produced by {@link CSSP    arser#r    o  w_items}.
	 * @para m   ctx the parse tree
 	 */
	void enterRow_ite  ms(@NotNull CSSParser.Row_itemsContext ctx);
	/**
	 * E  xit a parse tre      e p   roduced by {@link CSSParser      #row_items}  .
	 * @param ctx the parse tree
	 */
	void exitRow_items(@   NotNull CSSParser    .Row_i   temsContext ctx); 
	   /**
	 * Enter a pa   rse tree produced by {@link CSSParser#special}.   
	 * @param ctx the parse        tree
	 */
	void enterSpecial(@NotNull CSSParser.SpecialContext        ctx);
	      /**
	 *      Exit a parse tree prod uced by {@link CSSP     a    rser#special}   .
	 * @param ctx the p    arse tree
	     */
	void exitSpecial(@NotNul  l CSSParser.Spe cialContext   ctx);
	/**
	 * Enter a parse tree p  roduce   d by  {@link CSSPa    rser#col}.
	 * @param ctx the        parse tree
	 */
	void enterCol(@NotNull CSSParser.ColCon      text ctx);
	/**
	 *    Exit a parse tree   produced by {@l   ink CSSParser#col}.
    	 *    @p   aram ctx the parse t ree
	 */
	void exitCol(@NotNu   ll    CSSParser.ColContext ctx);
	/**
	 * En  ter a parse tree produced by {@link     CSSParser# title_string}.
	 * @param ctx the parse tre   e
	     */
	void e   nte   rTit       le   _string(@NotNull CSSParser.Title_string     Context c         tx);
	  /**    
	 * Exit    a parse tree produced by {@link CSSParser#tit     le_string}.
	 * @param c     tx the parse tree
	 */       
	void exitTitle_string(@NotNull CSSParse       r.Title_stringContext ctx);
	/   **
	 * Enter a    parse tree pro    duced by {@li  nk CS    SParser#root}.
	 * @param ctx the parse    tree
	 */
      	v oid enterRoot(@Not Null CSSPars er.Root  Context ctx);
	/**  
	 * Exit a parse tree    produce  d by {@link CSSParser#r oot}.
	 * @param     ctx the    parse tree
	 */
	void exitR   oot(@NotNull CSSParser.RootContex      t     ctx);
	/**
	 * En  ter a parse tree produc ed by {@link CS   SParser#lang_string}.
	 * @param ctx the parse tree
	 */
	void enterLang_string(@NotNull CSS    Parser.Lang_string  C   ontext    ctx);
	/**
	 * Exit a parse     tree produced by {@link CSSParser#l ang  _string}.
	       * @param ctx the p         arse tree
	 */
      	void exitLang_string(@N  otNu  l l       CSSParser.           Lang_stringContext         ctx);
	/**
	 * Enter a parse tree     produced by {@link CSSParser    #row}.
	 * @ param ctx t  he parse tree
	 */
	void enterRow(@NotNull CSSPa         rser.R  owContext ctx);
	/**
	 * Exit a    parse tree    produced by {@link CSSParser#row}.
	 *          @param ct   x the parse tree
	         */
	void exitRow(@NotNull CSSParser.  RowContext ctx);
	/**     
	 * Enter a pa    rse tree produced by {@link CSSParser#integ   er}   .
	 * @param ctx the parse tree
	 */
	void enterInte   ger(@NotNull  CSSParser.  IntegerContext c   tx);
	/**
	 * Ex  it     a parse tree produced by {@li   nk C    SSParser      #integer}.
	 * @param ctx the parse tree
	 */ 
	void exitInteger(@NotNull CSSParser.IntegerContext ctx);
	/**
	 * Enter a parse t     ree produced by {@l     ink CSSParser#      title}.     
	 * @ param c    tx the      p  arse tree
	 */
	void enterTitle(@Not   Nu      ll CSSPar  s   er.TitleContext c tx);
	/*    *
	 * Exit a parse tree prod uced by {@l  i    nk CSSParser#title}.
	 * @param   ctx the parse t   r  ee
	 */
	void exitTitle(@NotNull CSSParser.Titl   eContext  ctx);
	/**
	 *      Enter a parse tree produced by {@link CSSParser#lang}.
   	 *  @pa  ram ctx the    parse           tree
    	   */
	void enterLang(@NotNull CSSP    arser.LangContext ctx);
	/**
	 * Exit a parse tree prod    uced by {@l   ink    CSSParser#lang}.
	 * @par       am ctx the p     arse   t    ree
	 */
	void exitLang(@NotNu      ll CSSParser.LangContext ctx)   ;
	/**
	 * Enter a par    se tree      produced by {@link C   SSParser#prog}.
	 * @para      m ctx t  he parse tree
	 */
	void enterProg(@NotNull CSSParser.ProgConte   xt ctx);       
	/**
	 * Exit a  parse tre   e produced    by {@  link CSSParser   #prog}.
	 * @param ctx th       e parse tree
    	 */
	void exitProg(@NotNull CSSParser.ProgContext ctx);
	/**
	 * Enter a parse tree produced       by {@link CSSParser#row_  item}.
	    * @param ctx the parse tree
	 */
	void enterRow_item(@NotNull CSSParser.Row_itemContext ctx);
	/**
	 * Exit a parse tree produce   d  by {@link CSSParse  r#row_it  em}.
	 * @param ctx the parse tree
	 */
	void exitRow_item(@NotNull CSSParser.Row_itemContext ctx);
}