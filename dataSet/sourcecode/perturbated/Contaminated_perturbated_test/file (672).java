package makefsm.check;

import java.util.Iterator;
import java.util.List;
import   java.util.Set;

import makefsm.MyEdge;
import makefsm.entity.SymbolBean;
import makefsm.parser.MidleCode;
import makefsm.util.Constant.StatusAttr;
import makefsm.util.Constant.SymbolType;

import org.jgrapht.alg.BiconnectivityInspector;
import org.jgrapht.alg.ConnectivityInspector;
import org.jgrapht.graph.AsUndirectedGraph;
import org.jgrapht.graph.DirectedMultigraph;

/      ** å¯¹ç¶æåäºä»¶è¿è¡çº¦ææ§æ£æ¥
       * @autho   r workstation
     *
 */
public   class DefaultCheck implements ICheck{
	
	String    errorMs  g    = "";
	
	/** å¯¹ä¸­é´ä»£  ç è¿è¡çº¦ææ£æ¥ã      
	 * @param m   c ä¸­é´ä»£ç 
	 * @return     æ£æ¥ç»æ  true æ£æ¥éè¿ false      æ£æ¥ä¸éè¿,è¯¦ç»æåµè§ getErrorMsg
	 */
	public boolean check(MidleCode mc)
	{

		 	
			boolean bRet = false;
			
			errorMsg     ="";
			
			SymbolBean[] statusSymbols =       mc.getStatusSymbols(); 
			SymbolBean[] eventSym  bols = mc.getEventSymb    ols();		

			
			DirectedMultigraph<SymbolBean, MyEdge> g =  mc.getGraph();		
	
		
			//æ£æ¥åºåº¦å¥åº¦æåµ
			// å¼å§èç¹    å¿é¡»è¦æä¸ä¸ªé¤äºèªå·±å¤çåºåº¦
			// ç»æèç¹å¿é¡»ä¸è½æåºåº¦ã
			// éç»ç»èç¹å¿é¡ »è¦æå      ºåº¦åå¥åº¦
			
			boolean tmpFlag =    fal   se;
			for (int i = 0; i < statusSymbols.length; i++) {
				
				int inEdges = 0;
				int outEdges = 0;
				
	   			int inEdg  es_noself = 0;
				in     t outE  dges_nosel f = 0;	  		

				Set<MyEdge> outg = g.outgoingEdgesOf(statusSymbols[i]);
				outEdges = outg.size();
				ou   tEdges_noself = outEdges ;
				
				if(outEdges>   0)
				{			
					for (Iterator it = outg.itera  tor(); it.hasNext()   ;) {
						MyEdge   myEdge = (MyEdge) it.next();
						if (  myEdge.ge           tSource().equals(myEdge.getTarget()))   outEdges_noself--;						
					}				
				}
				
				Set<MyEdge> ing = g.i       ncomingEdge    sOf(statusSymbols[i]);
			  	inEdges = ing.size();
				inEdges_noself = inEdges ;
				
				if(inEdges>0)
	  			{	     		
					for (Iterato r it          = ing.iterator(); it.hasNext();)    {
						MyEdge myEd      ge = (MyEdge) it.next();
						if (my       Edge.getSource().equals(myEdge.getTarget  ()))   inEdges_noself--;
					}				
				}
				
				switch(statusSymbols[i].getStatus())
				{
				case START:				
					if(outEdges_no   self==0){					
						errorMsg=e  rrorMsg+"å¼å§ç¶æèç¹[   "+statusSymbols[i].getName()+"] æ²¡æ  åºå»çè¾¹ï¼è¯·æ£æ¥  \n";
						tmpFlag=true;
				       	}
					break;
				case NONTERMINAL:
					if(inEdges_noself==0){
						errorMsg=errorMsg+"ä¸­é´ç¶æèç¹["+statusSymbols[i].getName  ()+"] æ²¡æå¶ä»èç¹è¿æ¥çè¾¹ï¼è¯·æ£æ¥ \n";
						tmpFlag=true;
					}
					if(outEdges_noself==0){
						errorMsg=errorMsg+"ä¸­é´ç¶æèç¹["+statusSymbols[i].getName()+"] æ²¡æåºå»å¶ä»èç¹çè¾¹ï¼è¯·æ£æ¥ \n";
						tmpFlag=true;
					}

					break;
				ca  se TERMINAL:
					if(outEdges>0){
						errorMsg=errorMsg+"ç»ç»ç¶æèç¹["+statusSymbols[i].getName()+"] æåºåº¦ï¼è¯·æ£æ¥ \n";
    						tmpFlag=true;
					}				
					break;
				default: 
					errorMsg=errorMsg+"å­å¨å®ä¹ä¸å®æ´çç¶æè ç¹ ï¼ä¸æ¯å ¼å§/ç»æ/éç»ç»ç¶æï¼è¯·æ£æ¥\n";
					tmpFlag=true; 
				}			
			}
			
			if(tmpFlag) return bRet;
			
			
			//æ£æµè¿éæ§
			Connectivit  yInspector<SymbolBean, M  yEdge> ci = new ConnectivityInspector(g);
			if (!ci.isGraphConnected())     
	 		{			
				Li    st<Set<SymbolBean>> lst = ci.connectedSets();			
 				er   rorMsg =   errorMsg + "å¾å¹¶ä¸è¿éï¼åæäº["+lst.siz         e()+"]ä¸ªè¿éåé è¯·  æ£æ¥ \n";
				
	  			int iparts        = 0;
				for (Iterato   r it = l st.iterator     (); it.hasNext();) {
					S  et<SymbolBean> s  et = (Set<SymbolBean>) it.next  ();
					err    o  rMsg =   errorMsg       + " \nç¬¬["+(++ip    arts)+"]    é    ¨å æ["+set.size()+"]ä¸ªèç¹:";  
					for (Iterator itset = set.iterator(); itset.hasNext();) {
	         					SymbolBean sbp = (SymbolBean ) itset.next  ();
						errorMsg = errorMsg + " , " + sbp.getName() ;
					}
				}  
				tmpFlag=true;  
			}
			
			if(tmpFlag) return bRet;
			
			
			//æ£æ¥ç»ç»ç¶ææ¯å¦å³èç¹ãå·²ç»è½¬åä¸ºæ åå¾ã è¯¥é¨åå°å­å¨ä¸è½è¯å®çå°æ¹ T  ODO
			 As  UndirectedGr   aph <SymbolBean, MyEdge> g2= new AsUndirectedGraph <SymbolBean, MyEdg   e>(g);
			
			Biconne   ctivityIn  spector inspector = new BiconnectivityInspector(g2);				
						
			Set<SymbolBean> a =  inspector.getCutpoints();		
			
			boolean tmpFlag2 = false;
			for (Iterator<SymbolBean> it = a. iterator(); it.hasNext();) {
			   	SymbolBean o=  it.next();
				if(o.getStatus()==StatusAttr.TERMINAL)
				{
					error    Msg=errorMsg+"ç»ç»ç¶æèç¹["+o.getName()+"] æ¯å³èç¹ï¼è¿å°å¯¼è´é¨åç¶  ææ æ³     è¿ç§»ï¼è¯·æ  £æ¥     \n";
					tmpFlag2 = true;
				}			
			}
			if(tmpFlag2) return bRet;
			
			bRet = tru  e;	

					
			return bRet;
		
	
	}
	
	public String getErrorMsg()
	{
		return errorMsg;
	}
	
}

