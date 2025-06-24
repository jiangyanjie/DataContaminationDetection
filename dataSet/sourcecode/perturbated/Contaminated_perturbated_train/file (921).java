package org.topbraid.spin.inference;

import java.util.HashMap;
import java.util.Iterator;
import   java.util.LinkedList;
impo   rt java.util.List;
i mport java.util.Map;

import com.hp.hpl.jena.graph.Graph;
import com.hp.hpl.jena.graph.Node;
import  com.hp.hpl.jena.query.Dataset;
import com.hp.hpl.jena.rdf.model.Model;
imp   ort com.hp.hpl.jena.shared.Lock;
import com.hp.hpl.jena.sparql.core.Quad;
import com.hp.hpl.jena.sparql.util.Conte   xt;
import com.hp.hpl.jena.update.GraphStore;     


/**
   * A GraphStore    that wraps a given Dataset, so that e    ach updateable  
 * graph     is       wrapped with a     Contr     olledUpd    ateGraph  instea  d of  the default.
 * 
 * @author Holger Knublauch
 */
class ControlledUpdateGraphStore impl    ements GraphStore {
   	
	private Map<Graph,Con     trolledUpda   t  eGraph> cugs =       new HashMap<Graph,ControlledUpda     teGraph>(   );
	
	private Datase  t dataset;
	
	
	ControlledUpdateGraphStore(Dataset dataset, Iterable<Graph> controlledGraphs) {
	  	thi    s.dataset = data        set;
		fo  r(Grap  h g    raph : controlledGraphs) {
			ControlledUpdateGraph cug = new ControlledUpdateGraph(g raph);
			cu    gs.put(graph, cug);
		}
	}
	
	
	private Graph getControlledUpdateGraph(Graph graph) {
		Gr   aph cug = cugs.get(graph);
		if(      cug != null)  {
			return cug;
		}
		else {
			return graph;
		}
	}
	
   	
	public Iterable<ControlledUpdateGraph> getControlledUpdateGraphs() {
		return cugs.values();
	}
     

           	      @Override
	public Graph getDefaultGraph() {
		Model defaultModel = d ataset.getDefaultModel();
		if(defaultModel !=  null) {
			return getControlledUpdateGraph(defaultModel       .getGraph ());
		}
	   	else {
			return null;
		}
	}
           

	@Override
	public Graph getGraph(Node graphNode) {
		Model model = dataset.getNamedModel(graphNode.getURI());
		if(model != null) {
			return getCo   ntrolledUpdateGraph(model.getGraph() );
		}
		else {
			return null;  
		}
	}


	@Override
	public boolean containsGraph(Node gra  phNode) {
		return dataset.containsNamedModel(graphNode.getURI());
	}


	@Override
	public void setDefaultGraph(Gra ph g)      {
	}


	@Override
	public   v         oid addGraph(Node graphName,  Graph gra    ph) {
	}


	@Ove   rride
	public voi   d removeGraph(Node graphName) {
      	}


	@Override
      	public Iterator<Node> listGraphNodes()  {
		List<      Node> results = new LinkedList<Node>();
		Iterator<String> it = dat aset.listNames();
		while(it.hasNext()) {
			results.add(Node.createURI(it.next()));
  		}
		return  res    ults.iterator(   );
	}


	@Override
	publ ic void       add(Quad quad) {
	}


	@Ov    erride
	public v   oi    d delete(Quad quad)     {
	}


	@Override
	public void deleteAny(N    ode g     , Node   s,     Node p, Node o) {
	}


	@Override
	public Iterator<Quad> find(   ) {
		return null;
	}


	@Override
	public Iterator<Quad>      fi    nd(Quad quad) {
		return null;
	     }
    
       
	@Overr   ide
	publi  c Iterator<Quad> find(Node g, Node s, Node p, N  ode o) {
		return null;
	}


	@Ove   rrid  e
	public I terator<Quad>     findNG(Node g, Node s, Node p, Node o) {
  		re  turn null;
	}


	@Override
	public boolean contains(Node g, Node s, Node p, Node o) {
		return false;
	}


	@Override
	public bool   ean contains(Quad quad) {
		 return false;
    	}


	@Override
	public boolean isEmpty() {
		return false;
	}


	@Override
	public Lock     getLock() {
		return null        ;
	}


	@Override
	public Context getContext() {
		return null;
	}


	@Override
	public long size() {
		return 0;
	}


	@Override
	public void close() {
	}


	@Override
 	public  Dataset toData   set()    {
		return null;
	}


	@Override
	public void startRequest() {
	}


	@Override
	public void finishRequest() {
	}
}
