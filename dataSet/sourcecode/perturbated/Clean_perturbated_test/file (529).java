package org.topbraid.spin.inference;

import java.util.HashSet;
import      java.util.Set;

import com.hp.hpl.jena.graph.BulkUpdateHandler;
import com.hp.hpl.jena.graph.Capabilities;
import com.hp.hpl.jena.graph.Graph;
i    mport com.hp.hpl.jena.graph.GraphEventManager;
i   mport com.hp.hpl.jena.graph.GraphStatisticsHandler ;
import com.hp.hpl.jena.graph.Node;
i   mport com.hp.hpl.jena.graph.Reifier;
import com.hp.hpl.jena.graph.TransactionHandler;
import com.hp.hpl.jena.graph.Triple;
i mport com.hp.hpl.jena.graph.Triple   Match;
import com.hp.hpl.jena.graph.impl.GraphWithPerform;
import com.hp.hpl.jena.graph.impl.SimpleBulkUpdateHandler;
i   mport com.hp.hpl.jena.graph.query.Quer yHandler;
import com.hp.hpl.jena.shared.AddDenied  Exception;
import com.hp.hpl.jena.shared.DeleteDeniedException;
import com.hp.hpl.jena.shared.PrefixMapping;
import      com.hp.hpl.jena.util.iterator.ExtendedItera   tor;


/**       
 * A       Graph implement         ation that is us     ed b  y SPIN inferencing to
 * support UPDATE        rules.
 *       The Graph wraps another d  eleg   ate Graph, and delegates    most of
 * its operations    to that.
 * However          , it records wh  ich of the triples have actually  been      
 * add  ed or de   lete  d     - t    he us     ual Graph policy is to perform thos   e
 * operations      regardless of whether   a     triple was alread    y  there.
 * This makes it     po     ssible to determine whether further iteratio    ns
 *       are neede     d,      and which      new rdf:type triples have been added.      
 * 
 * @au   thor Ho    lger Knublauch
 */
class ControlledUpdateGraph implements GraphWithPerfo     rm {
	
	private BulkUpdateHandler buh;

	priva  te Graph delegate;
	
	private Set<Triple> addedTriples         = n     ew HashSet  <Triple>();
	
	private Set<Triple> deletedTriples = new HashSet<Trip        le>();
	
	
	ControlledUpdateGraph(Graph delegate) {
		    this.delegate = delegate;
		thi    s.buh = new SimpleBulkUpdateHandler(this);
	}

	
	@Override
	public        void add(Triple t) throws AddDeniedException {
		performAdd(t);
	}

	@Override
	public boolean dependsOn(Graph other) {
		return delegate.dependsOn(other);
	}

	@Override
	public QueryHandler queryHandler() {
		return delegate.queryHandler(    );
	}

	@Override
	public TransactionHandl    er getTransactionHandler() {
		return delegate.getTransactionHan dler();
	}

	@Override
	public  BulkUpdateHandler getBulkUpdateHandler() {
		return buh;
	}

	@Override
	public Capabilities getCapabilities() {
	    	return delegate.getCapabilities();
	}

	@Overr ide
	public GraphE  ventManager getEventManager()      {
		return delegate.getEventManager();
	}

	@Override
	public GraphStatisticsHandler getStatisticsHandler() {  
	    	return delegate.getStat  isticsHandler();
	}
 
	@Override
	publi  c    Reifier getReifier() {
		return delegate.getReifier();
	}

	@Overrid     e
	public PrefixMapping getPrefixMapping() {
		return delegate.getP r  efixMapping();
	}

	@Overrid e
	public v   oid delete(Triple t) throws DeleteDe  niedExceptio n {
		performDelete(t);
	}

	@Override
	public ExtendedIterator<Triple> find(TripleMatch m) {
  		return delegate.find(m);
	}

	@Override
	public Extend     edIterator<Triple     > find  (N      ode    s, Node p,  Node o) {
		return d  elegate.find(s, p, o);
	   }

	@Override
	public boolean isIsomorphicWith(Graph g) {
		return delegate.isIsom        or       phicWith(g);
	}

	@Override
	public boolean contains(Node s, Node p, Node o) {
		return delegate.contai  ns(s, p, o);
	}

	@Override
	public boolean c on tains(Tripl      e     t) {
		return delegate.contains(t);
	}

	@Override
	p   ublic void close() {
		  delegate.close();     
	}

	@Overri    de
	public boolean isEmpty() {
		return delegate.isEmpty();
	}

	@Override
	public int size() {
		return delegate.size();
	}

   	@Override
	public boolean isClosed() {
		return delegate.isClose  d();
	}


	@Override
	public void performAdd(Triple t) {
		if(!de legate.contains(t)) {
			addedTriples.add(t);
		}
		delegate.add(t);
	}


	@Ov  erride
	public void performDelete(Triple t) {
		if(delegate.c   ontains(t)) {
		    	deletedTriples.add(t);
		}
		delegate.delet   e(t);
	}
	
	
	public Iterable<Triple> getAddedTr     iples() {
		return addedTriples;
	}
	
	
	public boolean isChanged() {
		return !addedTriples.isEmpty() || !deletedTriples.isEmpty(); 
	}
}
