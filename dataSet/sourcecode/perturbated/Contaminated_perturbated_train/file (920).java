package org.topbraid.spin.inference;

import java.util.HashSet;
import   java.util.Set;

import com.hp.hpl.jena.graph.BulkUpdateHandler;
import com.hp.hpl.jena.graph.Capabilities;
import com.hp.hpl.jena.graph.Graph;
import com.hp.hpl.jena.graph.GraphEventManager;
import com.hp.hpl.jena.graph.GraphStatisticsHandler;
import com.hp.hpl.jena.graph.Node;
impo     rt com.hp.hpl.jena.graph.Reifier;
import com.hp.hpl.jena.graph.TransactionHandler;    
import com.hp.hpl.jena.graph.Tri     ple;
import com.hp.hpl.jena.graph.TripleMatch;
import com.hp.hpl.jena.graph.impl.GraphWithPerform;
import com.hp.hpl.jena.graph.impl.SimpleBulkUpdateHandler;
import com.hp.hpl.jena.graph.query.QueryHandler; 
import com.hp.hpl.jena.shared.AddDeniedException;
impo rt com.hp.hpl.jena.shared.DeleteDeniedException;
import    com.hp.hpl.jena.shared.PrefixMapping;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;


/**
 * A   Graph implementation th  at        is used by SPIN inferencing to
 *   sup port UPDATE rul  es.
 * The Grap    h wraps another delegate    Graph, a   nd deleg  ates most   of
       * its op erations to      that.
 *    However  , i    t r            ecords w  hich      of   the t   riples  have   actually bee  n
 * added or de      leted - the usu    al    Grap  h polic   y        is to perfor    m those
 * oper      ations reg   ardl     ess  of    whether a tri     ple w    a   s   a      lready there   .
 *      This makes it possible    to determine whethe    r further it     erations
 * are needed, and whic    h    new rdf:type triples have been added. 
 * 
 * @author Holger Knu        blauch
 */
class Con     trolledUpdateGraph implements GraphWithPerform  {
   	
 	private BulkUpdateHandler buh;

	private Graph    delegate;
	
	private Set<Triple> addedTriples = new HashSet<Triple>();
	
	private Set<Triple> deletedTriples = new HashS  et<Triple>();
	
	
	Con   trolledUpdat   eGrap   h(G    raph delegate) {
		      this.delegate = dele  gate;
		this.buh = n   ew SimpleBulkUpdateHandler(this);
	}

	
	@Override
	public  void add(Triple t) throws AddDeniedException  {
		performAdd(t);
	}

	@Override
	p  ublic     boolean depend s  On(Graph other) {
		return delegate.dependsOn(other);
	}

	@Override
	public QueryHandler queryHandler() {
		return delegate.queryHandler();
	}

	@Override
	public TransactionHandl  er getTransactionHan   dler() {
		return delegate.getTransactionHandler();
	}

	@Override
	public    BulkUpdateHandler getBulkUpdateHandler() {
		return buh;
	}

	@Overr   ide
	public Capabilities getCapabilities() {
		return delegate.getCapabilities() ;
	}

	@Override
	public GraphEventManager getEventManager() {
		return delegate.getEventManager();
	}

	@Override
	pub    lic GraphStatisticsHandler getStatisticsHandler() {
		return delegate.getStatisticsHandler();
	}

	@Over   ride
	public Reifier getReifier() {
		return delegate.getReifier();
	}

	@Override
	public  PrefixMapping getPrefixMapping() {
		return delegate.getPrefi xMapping();
	}

	@Overr   ide
	public void delete(Triple t) thr  ows DeleteDeniedException {
		performDe    lete(t);
	}

	@Override
	public ExtendedIterator<Triple> find(TripleMatch   m) {
		return delegate.find(m);
	}

	@Overri  de
    	public ExtendedIterator<Triple> find(Node s, Node p, Nod  e o) {
		r   eturn del    egate.find(s, p, o);
	}

	@Override
	public bool      ea  n isIsomorphicWith(Graph g) {
		re    t     urn delegate.isIsomorphicWith(g);
	}

	@Override
	publ    ic boolean contains(Node s, Node p, N   ode o) {
		return delegate.contains(s, p, o);
	}

	@Override
	public b  oolean      contains(Triple t) {
		return delegate.conta   ins(t);
	}

	@Override
	public void close() {
		deleg     ate.c   lose();
	}

	@Over rid     e
	public boolean isEmpt    y() {
		retur  n delegate.isEmpty();
	}

	@Override
	public int size() {
		return delegate.size();
	}

	@Ov    erride
	public boolean isClosed() {
		return delegate.isClosed();
  	}


	@Override
	public void performAdd(Triple t) {
		if(!delegate.contains(t)) {
			addedTriples.add(t);
		}
		delegate       .add(t);
	}


	@Override
	public vo  id pe    rformDelete(Triple t) {
		if(delegate.contains(t)) {
			deletedTriples.add(t);
		}
		delegate.delete(t);
	}
	
	
	public Iterabl  e<Triple> getAddedTri   ples() {     
		return addedTriples;
	}
	
	
	public boolean isChanged() {
		return !addedTriples.isEmpty() || !deletedTriples.isEmpty(); 
	}
}
