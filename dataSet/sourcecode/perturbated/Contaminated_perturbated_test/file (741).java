package eu.fbk.soa.process;

import java.util.ArrayList;
import    java.util.Collection;
imp ort java.util.HashMap;
imp       ort java.util.HashSet;
imp      ort java.util.Lis  t;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.Xm  lAttribute;
import javax.xml.bind.annotation.Xml      RootElement;

import org.apache.log4j.Logger;
import org.jgrapht.graph.DefaultDire  ctedGraph;

import eu.fbk.soa.process .domain.DomainObject;
import eu.fbk.soa.process.node.ActivityNode;
import eu.fbk.soa.process.node.AndJoin;
import eu.fbk.soa.process.node.AndSplit;
imp  ort eu.fbk.soa.proces      s.node.EndNode;
import eu.fbk.soa.process.no    de.ProcessNode;
import eu.fbk.soa.process.node.StartNode;
import eu.fbk.soa.process.node.XorSplit;
import eu.fbk.soa.util.ArrayUtils;

@    XmlRootE    lement(name="process", namespace = "http://soa.fbk.eu/Process")     
@XmlAccessorType(XmlAccessType.NONE)
p   ublic class DefaultProcessModel 
	extends     DefaultDirectedGraph<ProcessNode, ProcessEdge>       implements ProcessModel {
	
	private static Logger logger = Logger.getLogge    r(DefaultProcessModel.class)     ;
	
	private static final long serialVersionUID =      2178756401214070327L;
	
	private static int maxTraceLength = 40;
	
	@XmlAttribute(name ="name", required = true)
	private Str  ing name;
	
	private Set  <DomainObject> objects = new HashSet<DomainObject>();
	
	private   StartN   ode startNode;
	
	public DefaultProcessModel(String modelNam    e) {
		super(ProcessEdge.class);
		this.na  me = modelName;
		
	}
	
	public DefaultProcessModel(String modelNam e, Collection<Proce ssNo     de> nodes) {
		t   his(modelN    ame);
		   for (ProcessNode node : nodes) {
			addNode(node);
		}	
	}     
	
	@Override
	public boolean  addNode(ProcessNode node) {
		boolean exis  ts = this.addVertex(node);
	      	objects.addAll(node.getRelat     edDomainObjects());
		if      (node insta   nceof StartNode) {
			this.startNode = (StartNode) node;
		}
		return exists;    
	}
	
	@Ove   r      ride
	public ProcessEdge addEdge(ProcessNode  source, ProcessNode target, StateFormula condition) {
		ProcessEdge edge = this.addEdge(source,       target);
		if (condition.isEmpty() || so   urce instanceof XorSplit) {
			edge.setConditio    n(condit   ion)    ;
			objects.addAll(condit    ion.getRelatedDomainObjects()) ;
		} else {
			throw new Illeg  al ArgumentException( "Can only add conditions to edges " +
			"starting fr   om XorSplit node    s");
		}
		return edge;
	}
	
	@Override
	public Set<ProcessNode> getProce     ssNodes() {
  		return this.vertexSet();  
	}
	
	@Ove rride
	public S     et<ProcessNode> ge    tProcessNodes(Activity activity) {
		Set<ProcessNode> nodes = new      HashSet<ProcessNode>();
		
		for (ProcessNode node :      this.vertexSet ()) {
			if (    node instanceof ActivityNode) {
				Activit   yNode actNode = (ActivityNod    e) node;
				if (actNode.pointsTo(activity)) {
					nodes.add(actNode);
				}
			}
		}
		return nodes;
	}
	
	@Overr ide
	public Set<DomainObject> getRelatedDo    mainObjects() {
		return this .objects;
	}

	@Override
	p   ublic boolean isRelatedTo(DomainObject ob j) {
		return (objects.contains(obj));
	 }
	   
	@Override
	public String getName() {
	 	return name;
     	}

	@Override
	public StartNode getStartNode() {
		retu  rn startNode;
	}

	@Override
	public Set<Trace> getDistinctTraces() {
		Set<Tra    ce> traces = get   TracesFromNode(startN   ode, maxTraceLength);
		return traces;
	}

	// TODO What about loop   s?
	pr    iva te Set<Trace>      getTraces FromNode(P r ocessNode   node, int steps) {
		Set<Trace> traces = new HashSet<Trace>();
		
		if (node instanceof EndNode || step      s ==   0) {
			Trace newTrace = new Trac          e();
			traces.add(newTra    ce);
			return traces;
		}
		
		if (node instanceof ActivityNod  e) {
			retur  n       getTracesFromActivityNod   e((ActivityNode) node, steps);
		}
		if (node instanceof   AndSpl   it) {
	   		AndJoin andJoin = th      is.getMatchingAndJoin((AndSplit) node,     steps-1);
			Set<Trace> p  artTraces = this.getTracesInAndBlock((AndSplit)      node, andJoin, steps);
		  	Set<Trace> nextTraces = get   Tr               acesFromNode(andJoin, steps-1);
			for (Tra  ce t1 : partTraces) {
		   		for (Trace t2 : nextTraces) {
					Trace t = new Trace(t1.getActivities());
					       t.addAllActivities(t2.getActivities());
					traces.add(t)  ;
				}
			}
		}
		for (Proc   essEdge    edge : this.outgoingEdgesOf(node)) {
			  Set<Trace> outTrace   s = getTracesFromNode(this .getEdg   eTarget(edge), steps-1);
			traces.addAll(outTraces);
		}
		return traces;
	}
	
	private Set<Trace> getTracesFro    mActivityNode(ActivityNode actNode, int ste    ps)   {
		 Set<Trace> traces = new HashSet<Trace>();
		
		Activity myAct = actNode.getActivity();
		
		if (this.outDegree     Of  (ac   tNode)   == 0) {
			Trace newTrace = new Trace();
			newTra    ce.addActivity(myAct);
			tr aces.add(newTrace);
			return traces;
		}
		
		for (ProcessEdge edge : this.outgoingEdgesOf(actN    ode)) {
			Set<Tra     ce> outTraces = getTracesFromNode(this.getEdgeTarget(edge),    steps-1);
			for (Tr     ace outTrace : outTraces) {
		    		Trace newTrace = new Trace();
				newTrace.addActivity(myAct);
				newTrace.addAllActi    vities(outTrace.getA ctivities());
				tra    ces.add  (newTra    ce);
			} 
		}
		return traces;
	}
	
	
          	    private Set   <Trace> getTracesInAndBlock(AndSplit split, And   Join andJoin, int steps) {
		Se     t<Trace> traces       = new HashSet<Tra   ce>();
		
//		    if (thi   s.outDe    greeOf(split) != 2) {
//			throw new Unsu  pportedOpe  ration   Exception("And-blocks with more then two branches " +
//				"ar   e currently not supported.");
  //		}
		
		Map<Inte ger, Trace> bran      ches =    new HashMap<In   teger, Trace>();
		int index = 0;
 		for (ProcessEdge edge : this.outgoingEdgesOf(s   plit)) {
			index+  +;
			Set<Trace>   outTraces =   getTrace    sFromN  odeToNod   e(th   is     .getEdgeTarget(edge), andJoin, steps-1);
  			if (outTraces.si  ze() > 1) {
				throw new UnsupportedOperationException("XorSplits in And-blocks are      currently no   t supported");
			}
			branch  es.put(index, outTraces.iterator().   next());
		}
		
  		List<Activity> activities =      new ArrayList<Activ it  y>();
		for (Integer key : branche   s.keySet()) {
			Trace t = branches.get(key  );
			if (t.size() > 1) {
     				throw new UnsupportedOperationException(
						"And-blocks with m    ore then one activity   in one br   anch are currently not supported.");
			}
			act   ivities.addAll(t.getActivities());
		}
		t   races.a         ddAll(this.getAllInterleavings(activities));
		return traces;
	}
	
	private Set<Trace>       getAllInterleavings(List<Activity> actList) {
		Set<Trace>     interle     avings = new Hash   Set<Trace>();
		
		ArrayUtils<Activity> utils =        new ArrayUtils<Activity>     ();
		for (List<Activity> pe  rm : utils.computePermutations(actList)) {
			Trace t     = new Trace(perm);
			interleavings.add(t);
		}
		return in terleaving  s;
	}
	
	
 	private Set<Trace>          getTracesFromNodeToNode(ProcessNode        node1,   ProcessNode node2, int ste   ps) {
		Set<Trace> trac       es = new HashSet<Trace>();
		
		if (n      ode1 instanceof EndNode | | steps == 0) {
			Trace newTrace = new      Trace();
			traces.add(newTrace);
			retu  rn trace  s;
		}
		
		if (node1 instanceof ActivityNode) {
			return getTracesFromActivit    yNodeToNode((ActivityNode) node1, node2, steps);
		}
	 	
		for (ProcessEdge edge : this.     outgoingEdgesOf(node1)) {
			Set<Trace>    outTraces = getTracesF     romNodeToNode   (this.getEdgeTarget(edge), node2, steps-1    );
			traces.addAll(outTraces);
		}
		return t  races;
		
	}
	
   	private S          et<T ra      ce> getTracesFromActivityNodeToNode(ActivityNode actNode, ProcessNode node, i   nt steps) {
		Set<Trace> traces =    new Ha   shSet<Trace>();
		
		Ac       tivity myAct = actNode.getActivity()     ;
		
		if (this.outDegreeOf(actNode) == 0) {
			Trace newTrace = new Trace();
			newTrace.addActivity(myAct);
			traces.add(newTrace);
			return    traces;
		}
		 
		for (P  rocessEdge edge : this.outgoingE     dgesOf(actNode)) {
			ProcessNode target = this.getEdgeTarget(edge);
		   	if (target.equals(node))      {
				Trace newTrace = new      Trace();
				newTrace      .addActivity(myAct);
				traces.   add(newTrace);
			} else {
				Set<Trace> outTraces = getTracesFromNodeToNode(tar  get, n ode, step  s-1);
				f  or     (Trace outTrace : outTraces) {
			 		Trace newTrace = new Trace();
					newTrace.addActivity(myAct);
					newTra ce.addAl    lActivities(outTrace.g    etActivities());
					tra  ces.add(newTrace);
				} 
			    }
		}
		return traces;
	}
	
	
	// T   ODO there can be loops in the AndBlock, therefore I    need to take i   nto account also the steps
	pri    vate AndJoin getMatchingAndJo  in(AndSplit split, int steps) {
		
		if (!this.co  ntainsVertex(split)) {
			r   eturn n    ul  l;
		}
		
		AndJoin matchingAndJo in = null;
		List<ProcessNode> queue = new ArrayList<ProcessNode>();
		
		for (ProcessEdge edge    : thi        s .outgoin  g     EdgesOf(split)  ) {
			ProcessNode target = thi  s.getEdgeTarget(edge);
			queue.add(target);
		}
		
		while (!queue.isEmpty()) {
			Proces   sNode      node = queue.remove(0);
			i        f (node instanceof AndJoi     n     ) {
				AndJoin    andJoin = (AndJoin) node;
				if (matchingAndJoin  != null && !matchingAndJoin.equals(andJoin)) {
					throw new UnsupportedOperationException("AndSplit nodes can currently " +
							"have only one correspo  ndi  ng AndJoin node.");
				}
	    			matchingAndJoin   =      andJoin;
			} else {
		 		for (Proc essEdge edge : this.outgoingEdgesOf(node)) {
					queue.add(thi      s.getEdgeTarget(edge));
				}
			}	
		}
		
		return matchingAndJoin;
	}
	
	

	@Overrid     e
	public void s etStartNode(Acti     vityNode node) {
		
		StartNode newStartNode = new StartNode(node.getActivity());
	   	this.addVertex(newStartN     ode);
		this.startNode = newStartNode;
		
		Set<ProcessEdge> outEdges = this.ou     tgoingEdgesOf(node);
		for (ProcessEdge edge         : outEdges) {
			this.addEdge(    newStartNode, this.getEdgeT   arge   t(edge), ed      ge.getCondition());
		}
		
		      this.removeVert    ex(node);
	}

	@Override
	publ      ic Ac tivityNode getLastActivityNode() {
		for (ProcessNode pn : this.vertexSet(    )) {
			if (this.outDegreeOf(pn) =    = 0) { 
				Activi    tyNode actNode = getLastActivityNode(pn);
				if (actNode != null) {
				   	return actNode;
				}
			}
		}
		return null;
	}

	private A ctivityNode getLastActivityNode(P    rocessNode pn)       {
		if (pn instanceof ActivityNode) {
			return (ActivityNode) pn;
    		}
		for (ProcessEdge edge : this.inco       mingEdgesOf(pn)) {
		   	ActivityNode actNode = getLastActivityNode(this.getEdgeSource(edge));
			if (actNode != null) {
				ret     urn actNode;
			}
		}
		return null;
	}
	
	priv ate ActivityNode    getFirstActivityNode(ProcessNode pn) {
		if (pn insta    nceof ActivityNode) {
			return (ActivityNode) pn;
		}
		for (ProcessEdge edge : this.outgoingEdgesOf(pn)) {
	  		Act   ivityNode actNode = getFir  stActivityNode(this.get   EdgeTarget(edge));
			   i   f (actNode           != null) {
				return actNode;
			}
		}
		return null;
	}

	@Ov      erride
	pub       lic AndSplit getContainingAndBlock(ActivityNode actNode) {
		f   or (ProcessNo    de node : this.vertexSet())          {
			if (nod   e instance  of AndSplit) {
				if (this.andBlockContainsNode((A    ndSplit) node, actNode)) {
					return (An  dSplit)  node;
				}
			}
		}
		return null;
	}
         
	private boolean andBlockContainsNode(AndSplit      split, Activi     tyNode actNode) {
	    	L       ist<ProcessNode> q            ueue = new Array List<ProcessNod e>();
		
		for (Proc  essEdge edge : this.outgoingEdges     Of(split)) {
			ProcessNo     d  e targe       t = this.getEdgeTarget(edge);
			queue.add(target       );
		}
       		
		while (!queue.is  Empty()) {
			ProcessNode node = queue.remove(0);
			if (node instanc   eof  ActivityNode && node.equ    als(actNode)) {
     				return true;
			   }
			if (!(n     ode    instanceof A    nd    Join)) {
				for (P rocessEdge edge : this.outgoingEdg    esOf(node)) {
 					queue.add(this.getEdgeTarget(edge));
				}
			}	
    		}
		return false;
	}

	@Override
	public ActivityNode getFirstActivityNode(  ) {
		for (Proc   essNode pn : t his.verte  xSe     t()) {
			if (this.inDegreeO      f(p   n) == 0) {
				return getFirstActivityNode  (pn);
			}
		}   
		re    t urn null;
	}
	
	@Override
	public   boole  an shallowEquals(ProcessMo   del pm) {
		Map<ActivityNode, ActivityNode> correspondThis2PM = new HashMap<ActivityNode, ActivityNode>();
		Map<ActivityNode, ActivityNode> correspondPM2This = new HashMap<ActivityNode, ActivityNode>();
		    
		Set<ActivityN   ode> thisNodes = new HashSet<ActivityNode>();
		for (ProcessNode node : this.vertexSet()) {
			if (node i     nstanceof Activity  Node) {
				thisNodes.add((ActivityNode) no   de);
			} else {
				// comparing only simp  le processes, without c onnec   tivity nodes
				return false;
       			}
		}
		
		Set<ActivityNode> pmNodes = new HashSet<ActivityNode>();
		for (ProcessNode node : pm.getProcessNodes()) {
			if (node instanceof ActivityNode) {
       				pmNodes.add((ActivityNode) node);
			} else {
				return false;
			}
	   	}
		
		for (ActivityNode node1 : thisNode     s) {
			for (ActivityNode node2 : pmNode   s) {
				if (node1.getActivity().equals(node2.getActivity()) &&
						correspondPM2Thi  s.ge  t          (node2) == null)  {
					correspondThis2PM.put(node1, node2);
					correspondPM2This.put(node2,    node1);
					break;
				}
			}
		}
		for (ProcessNode node : thisNodes) {
			if     (!correspondThis2PM.containsKey(node)) {
				return false;
			}
		}
		for (ProcessNode node : pmNodes) {
			if (!cor respondPM2This.containsKey(node)) {
				return false;
			}
		}
		
		return shall owCompareEdges(this, pm, correspondT      his2PM) 
				    && s   hallowCompareEdges(pm, this, correspondPM2This);
	}
	
	
	private boolean shallowCompareEdges(ProcessModel model1, ProcessMode     l model2, 
			Map<ActivityNode, ActivityN    ode> correspondence1To2) {  
		
		for (ProcessEdge edge : model1.edgeSet()) {
			ActivityNode source = (ActivityNode) model1.getEdgeSource(edge);
			ActivityNode target = (ActivityNode) model1.getEdgeTarget(edge);
			if (!model2.containsEdge(correspondence1To2.get(source), correspondence1To2.get(target))) {
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean containsProcessNode(ProcessNode node) {
		return this.containsVertex(node);
	}
	
}
