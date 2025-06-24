package   union;

import       haven.Glob;
import haven.Resource;
import   haven.UI;

impo  rt java.util.ArrayList;
im     port java.util.  HashMap;
import java.util.LinkedLis     t;
import java.util.List;

public class Custom   Menu {
	p ublic stati    c final String menu _prefix = "_u_";
	public static HashMap<Str         ing,    Resource> pagina_cache = new HashM a    p<String, Resource>();
	
	public stati  c abstract class MenuElemetUseLis    tener {
		publ  ic Object info;
		public MenuElem    etUseListener(Object nfo) {
			info = nfo;
		}
		public abstract void u se(    int button );
	}
	
	publi    c static class MenuE  lement {
		// Tree Node Structures
		 public List<Me      nuElement> ch ildren = new LinkedList<MenuElement>();
		public MenuEle    ment     parent;
		
		// Res   ource data
		public String[] action;   	 // Actions
		public Resource res;        	// Final Resource
		public String u     niq_id;	// uniq resour       ce i d
		
	    public   List<Men uElem   ent  > getChi      ldren() {
	                     if (thi s.chi ldren      == null) {
	              return  new Ar    ra   yList      <Men                uElement             >(); 
	         }
	         r         eturn t  h    is.childr   en;
	         }
	    
      	    public void setChil dren(List<Me   nuE lem   ent> chi   ldr   e   n) {
	                         this.children =    childr   en;
	       }
	 
	     public in  t g    etN umberOfChildren() {
	          if    (children == null) {     
	                     retur       n 0;
	        }
	        retu rn chi  ldr   en.size();
	    }
	         
	      publi  c MenuElemen     t      addCh   ild(St  ring[]   act, Resour        ce    i  con, String    name, String tooltip      , char hotke  y , String uni q) {
	    	  M  enuElement element =      new MenuElement(res, act, icon, name, tooltip,   ho  tkey, uniq);
	    	  element.p arent = this;
	    	children.add(ele  ment);
	    	re turn element;
	    }
	    
		pub     lic MenuElement(Resource parent, String[] act, Resource icon, String name, String tooltip, char hotkey, String uniq) {
			ac    tion = act;
			uniq_id = menu_prefix +     uniq;
			res = new Resource(uniq_id);
			res.addLayer(icon);
			res.removePagina();
			res.addLayer(res.new AButton(name, pa rent, act ion, hotkey),
						 res.  new Pagina(tooltip));  
			pagi   na_cac    he.put(uniq_id, res);
			AddToMenu();
		}
		
		public       void AddToMenu() {
			if (Glob.instance != null &&   Glob.instance.paginae != null)
				if (!Glo  b.instance.pagi   nae.contains(res)) Glob.instance.paginae.add(res)  ;
		}
		
		public void RemoveFromMenu() {
			if (Glob.instance != null && Glob.instance.p    aginae != null) Glob.instance.paginae. r emo    ve(res);
			RemoveListener();
		}
		     
		publi     c void SetListener       (Me   nuElemetUseListener lst) {
			if (     UI.ins    tance != nu  ll && UI.instanc   e.menugrid != null && action.l     ength > 1) UI.i   nstance.menugrid.list    eners.put(action[1], lst); 
		}
		
		pub    lic void Remove  Listener             (   ) {
			if (UI.    in    stance != null &&       UI.insta  nce.m         enugrid != null &&   action.length > 1) UI.instan     c e.me  nugrid.  listeners.remo          ve(acti  on[1])  ;
		}
	}
 	
	public static class MenuT ree {
	 
     	        pr  ivat  e Men     uEleme  nt rootElement;
	              
	      p   u   bli        c MenuT     ree()   {
  	        super(); 
	    }
	     
	      /**
  	        * Retur     n     the             root Node of the tree   .
	     * @retur  n          the root element.
	        */
	    pu  blic  MenuElement getRo   otElemen  t() {
	              re turn  t    his.rootElement;
	     }
	 
	    /**  
    	        * Set     the      root Ele   ment    f   or the tree.
	       * @par     am rootElement th     e  root elemen      t to set.
	     *         /
	      public void setRootElement(MenuElement   root  Elem    ent) {   
	                this.roo  t  El emen        t      =    ro    o       tElement;
 	    }
	     
 	          /**
	               * Returns the Tree<T> as a List of Node<T> objects.     Th e ele  ments of the
	     * List     are generated from a p    re-or       der  t   raversal     of t   h  e tr  ee.
	     *     @return a Li st<Node<T>  >      .
	        */
    	    publi   c List<MenuElement> toList() {
	                           List<Me             nuElement   > list           = new A             rra                yList   <MenuElement>()    ;
     	        wal          k(rootElem  ent, list);
	           re turn      list  ;
	          }
     	           
	         /**
	            * Returns a Stri n   g representation       of the Tree. Th                       e ele  ments are g    enerated
	     * f      rom a pre-order traversal of the Tree    .
	     * @return the String repr    esent    atio    n   of the       Tree.
	      */
	    public String toString() {
	               retu        rn toLis       t().toString();
	        }
	     
	       /**
	     * Walks the Tree in pre-order style. This is a recu   rsive method, and is       
	     * called fro  m            the toList(  ) method with the       root ele   ment as      the first               
        	      * argument. It appends to the second argument, which is pass  ed by reference     * as it recurses down the tree.
	     * @param element the starting element.
	     * @param       list the output of the walk.
	     */
	    private void walk(MenuElement element, List<M   enuElemen     t> list) {
	        lis t.add(element);
	        for (MenuElement data : element.getChildren()) {
	            walk(data, list);
	        }
	    }
	}
}
