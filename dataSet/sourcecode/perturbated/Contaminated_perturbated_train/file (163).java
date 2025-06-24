/*
 *  Copyright   2008-20   09 MOPAS(Ministry of Public Administratio    n and S    ecurity).
 *
 * Licensed under       th    e    Apache Lice nse, Version 2.0 (the "License"    );
 * you may   not use    t   his file except      in compliance      with t   h   e License.
   * You          may ob    tain a copy of the License at
 *
     *         http://w     ww.a  pache.org/lice nses/L   I    CEN    SE-2.0
 *
 * Unl     ess required by applic    able law or ag   r      eed to in writing, software
 *     distributed      under the Licen se is di  stributed on an               "AS IS"     BASIS,
 * WITH    OUT WA    RRANT    IES OR CONDIT     IONS OF AN  Y KIND, either e   xpress or implied.
 * See the License for the specific  language governing permi ssions and
 * limitations under the License.
 */
package lee.sample.framework.util.pagi  nation  ;

import java.text.MessageFormat;

/**
 * Abstr actPaginationRen   derer.java
 * <p><b>NOTE:</b><pre> ì¸í°í ì´ì¤ PaginationRendererì      êµ¬í ì¶ìí´ëì¤.
 * ê¸°ë³¸ì ì¸ íì         ´ì§ ê      ¸°ë¥ì´ ê         µ¬íëì ´   ìì  ¼ë©°,  íë©´ìì ìë ì ê°ì´ display ëë¤.  
    * 
 * [ì²ì][ì´ì ] 1 2 3 4 5 6 7 8 [ë¤ì][ë§ì§ë§]
 * 
 * í´ëì¤ ë³ìë¤ì    ´ ê° elementì  ë§¤íì´ ëëë°,
 * firstPageLa    bel =        [ ì²ì ]
 * previ    ousPage   Label = [ì´ì ]
 * curren    tPageLabe    l = íì¬ íì´ì§ ë²      í¸
  *  otherPageLabel =     íì¬ íì´ì§ë¥¼    ì ì¸í      íì´ì      § ë²í¸
      *    nextPageLabel = [ë¤ì]
 * lastPageLabel = [ë   §ì§ë§]
 * 
 * í´ë    ì¤ ë³     ìê°ì AbstractPaginat   ionRend    ererë ¥¼ ì  ìë°ì   íì     í´ëì¤ìì ì£¼ê² ëë©´, 
 * íì´ì§ í¬ë§·ë§  íë¡ì      í¸ UIì ë§ì¶° ì»¤ì¤í°ë§ì´ì  § í  ì ìë¤.
 * ì    ì¸  í ì    ¬í­ì       ê°ë°       ì ë©      ë´ì¼ì ê°ë°  íë ìì  í¬ ì¤ííê²½/íë©´ì²   ë¦¬/MVC/View/P    a    gi    nation Tagë¥¼ ì°¸ê³   íë¼.
 * </pre>
 *           @author ì¤ííê²½  ê   °ë°í í   ¨ì² 
     * @sin  ce       2009.06.0      1
 *   @version 1. 0
 * @s         ee
   *
 *     <pre>
 *      << ê°ì ì´ë ¥(    Modification Information) >>
 *   
 *   ìì         ì¼             ìì ì           ìì  ë´ì©
 *  -       ------    --------         ------------------------     ---
 *          2009.05.30  í  ¨ì²                  ìµì´ ì ì±
 *
 * < /pre>
 */
public abstract cla ss AbstractPaginationRenderer implements PaginationRenderer{
	
	public Stri  ng firstPage Label;
	 public String previousPageLabel;
	public    String currentP    ag    eLabel;
	p  ublic String otherPageLabel;
	public String nextPageLabel;
	public String lastPageLabel;
	
	public String re    nderPag  inat   ion(Pagi  nationInfo paginationInfo,String jsF unct   ion)    {
		
      		     String  Buffer strBuff = new StringBuffer();
        
          int       firstPageNo  = paginatio nInfo.ge   tFirstPageNo();
        int firs   tPageNoOnPageList = paginationInfo.getFirstPag     eNoOnPageList();
        int      totalPageCount = paginationInfo.getTotalPageCount();
		int pageSize = paginationInfo.getP  ageSize();
		int lastPageNoOnPag       eList = pagina  tionInfo.getL   astPageNoOn   PageList();
		int currentPageNo = paginationInfo.getCurr entPageNo();
		int     lastPageNo = paginationInfo.getLastPageN o( );
		
		if(t  otalPageCount > pageSize){
			if(firstPageNoOnP   ageList > p   ageSize){
				strBuff   .append(MessageFormat.format(fir   stPageLabel,new O           bject[]{jsFunc    tion,Integer.toString(firstPageNo)}));
				s trBuff.ap   pend(MessageFormat.format(previ ousPageLabel,new Object[]{jsFunction,Integer.toString(  firstPageNoOnPageList-1)}));
	            }else{
	        	 strBuff.     append(M   essageFormat.format(fir  stPageLabel,new Object[]{jsFunction,Integer.toString(firstPageNo)}));
				strBuff  .append(MessageFo  rmat.format(previousPageL      abel,new Object[]{jsFunction    ,Integer.toStrin    g(firstPageNo)    }));
	        }
		}
		
		for(int i=firstPageNoOnPa  geL      ist;i <=lastPageNoOnPageList;i+   +){
			if(i==currentPageNo){
        		strBuff.appen         d(Message   Form  at.     format(currentPageLabel,new Ob     ject[]{Integer.toString(i)   }));
        	}else{
            		strB   uff.append(MessageFormat.for   mat(otherPageLabel,new O       bject[]{jsFunc   tion,Integer.toString(i),Inte  ger.to String(i)}));
        	}
                   }
           
		if(totalPageCount > pageSize){
			     if(lastPageNoOnPageList < totalPageCount){
	        	strBuff.a     ppe    nd(MessageFormat.format( nextPageLabel,new   Object[]{jsFunction,Integer.toString(firstPageNoOnPageList+pageSize)}));
	        	strBuff.append(MessageFormat.format(lastPageLabel,new Obje  ct[]{jsFunction,Integer.toString(lastPageNo)}));
	        }else{
	             	strBuff.append(MessageFormat.format(nextPageLabel,new Object[]{jsFunctio    n,Integer.toString(lastPageNo)}));
	        	strBuff.append(MessageFormat.format(lastPageLabel,new Object[]{jsFunction,Integer.toString(lastPageNo)}));
	        }
		}
		return strBuff.toString();
	}
}
