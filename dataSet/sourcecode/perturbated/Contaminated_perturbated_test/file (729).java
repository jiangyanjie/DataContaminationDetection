/*
        * Co         p  yright 2008-2009 MOPAS(Mi     nistry of Publi      c Admi  nistration    and Security).
     *
 *     Licens ed under the Apache License, Version 2.0 (the "Lice   nse");
 * you   may not  use this file ex  cept    in compliance with  the License.
 * You may obtain a      copy of the License at   
 *
     *      http://www.apache   .org/li   cense    s/LICE   NSE-2.0
 *
 * U     nless requ  ired by a    pplicable law o r agreed        to in wr       iting,   software
   * distributed     under      the License is distributed    on an "AS    IS" BASIS   ,
 * WI   TH  OUT WARRANTIES      OR CONDI    TIONS OF ANY KIND, either exp  ress     or im   plied.
   * See the License for t       he spec  ific language governing permi   ssions and
 *  limita   tions under the License.
 */
package   lee.sample.fra   mework.util.paginatio     n;
     
/*      *
 * Default   Pagina            tionRenderer.jav   a
 * <p><b>NOTE:</b><pre> íì´ì§ ë      ¦¬ì¤í¸ì ê¸°ë³¸ì   ì¸ í¬    ë§·ì    ì ê  ³µíë¤.  
 * ì´ë¥¼ ìí´ Abstract Pagin   at   ionRen    dererì í   ´ëì¤ ë³ì   ë¤ì ê°ì ì¸ííë¤.       
 * íë©´ìì               ìëì ê°ì´ display ëë¤.            
 * 
 * [ì²ì][ì´ì ]    1 2 3 4 5 6 7 8       [ë¤ì][ë         §ì§ë§]   
 * 
 * í´ëì¤ ë³ìë¤ì´ ê°    elementì   ë   ¤ìê³   ¼ ê° ì´ ë§¤íì    ´ ëë¤.
 * fir    stPag eLabel = [ì²ì]
 * previousPa             geLabel      = [ì´ì ]
 * currentPageLabel = íì¬ íì´ì§ ë²í¸
 * o therPag   eLabel = íì¬ í ì´ì§ë¥¼ ì ì¸í     í      ì   ´ì§        ë²í   ¸
 * nextPage  Label = [ë¤ì]
 * las   tP ageL      abel = [ë§    ì§ë§]
 *    </pre>
 * @author   ì ¤íí   ê²½ ê    °          ë°í   í¨    ì² 
 * @since 2009 .06.01
 * @version    1.0
 * @s  ee
 *
 * <pre>
 * << ê°ì   ì´ë ¥(Modification Information) >>
 *           
 *   ìì ì¼      ìì ì           ì  ì ë    ´ì©
 *  -------    --------    ------    --   -------------------
       *   2009.05   .30  í ¨ì²                 ìµì´ ìì±
 *
 * </pre>
 */
public clas  s   DefaultPaginationRenderer extends AbstractPa   ginationRen derer         {
	
	public Defau   ltPaginationRenderer() {
		firstPageLabel   = "<     a href=\"#\" onclick=\"{0}({ 1}); return false;\">  [ì²ì]</a>&   #160;";   
        previousPageLabel = "<a hre f=\"#\" oncli    ck=     \"{0}({1}); return false;\">[ì     ´ì ]</a>&      #160;";
        currentPageLabel = "<strong>{0}</s   trong>&#16     0;";  
        otherPageLabel = "<a hre    f=\"#\" onclick=\"{0}({1}); return false; \">{2}</a>&#160;";
        nextPageLabel =    "<a href=\"#\" onclick    =\"{0}({1}); return false;\">[ë¤ì]</a>&#160;";
        lastPageLabel = "<a href=\"#\" onclick=\"{0}({1}); return fa      lse;\">[ë§ì§ë§]</a>&#160;";
	}
	
	@Override
	public String renderPagination(PaginationInfo paginat ionInfo,
			String jsFunction) {
		
		return super.renderPagination(paginationInfo, jsFunction);
	}

}