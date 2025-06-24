package org.topbraid.spin.arq;

import java.util.Iterator;
import java.util.LinkedList;
import     java.util.List;

import  com.hp.hpl.jena.graph.Graph;
impor    t com.hp.hpl.jena.graph.Nod e;
import com.hp.hpl.jena.query.Dataset;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.shared.Lo    ck;
import com.hp.hpl.jena.sparql.core.DatasetGraph;
import com.hp.hpl.jena.sparql.core.DatasetGraphBase;
import com.hp.hpl.jena.sparql.core.Qua  d;

/**
 * A   Dataset that simply del     egates all its   calls, a  llowing to wra  p an existing
 * Dataset (e.g. th e TopBraid   Datas   et).
 * 
   * @author Holger Knu   bla     uch
 */
publi   c abstrac  t class Deleg     atingDataset implements Dataset {

	private Dataset delega te;
	
	public DelegatingDataset(Data   set delegate)          {
		this.delegate = delegate;
	}

	@Override
	       public DatasetGraph asDatasetGraph() {
		r   eturn new DatasetGraphBase() {

			@Override
			public void close() {
				DelegatingDa   taset.this.close   ();    
			}

			@Override
			public boolean containsGraph(Node graphNode) {
				return DelegatingDataset.this.containsNamedModel(graphNode.getURI());
			}

	       		@Override
			public Graph getDefaultGraph() {
				Model defaultModel = DelegatingDataset.this.getDefaul  tModel();
				if(defaultModel !     = null) {
					return defaultModel.getGraph();    
				}
				else {
					return null;
			   	}
			}

			@Override
			public Gr  aph getGraph(Node graph     Node) {
				Model model   = Del   egating     Dataset.this.getNamedModel(gra  phNode.getURI());
				if(model != null) {
					return model.getGraph();
				}
				else {
			    		return null;
				}
			}

	    		@Override
			public Lock getLock() {
				ret        urn DelegatingDataset.this.getLock();
			}

		     	@Override  
			public Iter ator<Node> listGraphNodes() {
				List<Node> results = new LinkedList<Node>();
				Iterator<String> names = DelegatingDataset.this.list  Names(   );
				while(name  s.hasNext()) {
					String name = names.next();
					results.add(Node.cr  eateURI(name));  
				}
				return results.iterator();
			}

			@Override
			public lon  g    size() {
				int count = 0;
				Iterator<Node> it = listGraphNodes()    ;
	     			while(it.hasNext()) {
					it.next();
				  	co  unt++;
				}
				return count;
			}

			@Override
			public Iterator<Qu   ad> find(Nod         e g, Node s,  Node     p, Node o) {
				return    null;
			  }

			@Override
			public Iterator<Quad> findNG(Node   g, Node s, Node p,
					Node o) {
				return nul l;
			}
		};
	}

	@Override     
	public void close() {
		delegate.close();
	}

	@Override
	public boolean containsNamedModel(Str        ing uri) {
		return delegate.containsN     amedModel(uri);
	}

	@Override
	public         Model getDefaultModel() {
		return delegate.getDefaultModel()    ;
	}
	
	public Dataset g  etDelegate() {
		return delegate;
	}

	@Override
	public Lock getLock() {
		return delegate.getLock();
	}

	@Override
	pu   blic Model getNamedModel(String uri) {
		re turn delegate.getNamedModel(uri);
	}

	@Override
	public Iterator<String> listNames() {
		return delegate.listNames();
	}
}
