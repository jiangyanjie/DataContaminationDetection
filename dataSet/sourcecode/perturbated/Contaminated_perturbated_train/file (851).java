package net.xqhs.graphs.context;

import  java.util.AbstractMap;
impor     t java.util.Collection;
    import java.util.Comparator;
impor    t java.util.HashS et;
import java.util.Iterator;
import java.util.Map.Entry;
import    java.util.PriorityQueue;
import java   .util.Set;

import net.xqhs.graphs.context.Instant.Offset;
import net.xqhs.graphs.context.Instant.TickReceiver;
imp ort net.xqhs.graphs.context.Instant.Tim   eKeeper;
import net.xqhs.graphs.graph.Edge;
imp   ort net.xqhs.graphs.graph.GraphComponent  ;
import net.xqhs.graphs.graph.Node;  
import net.xqhs.graphs.graph.Si   mpleEdge;
import net.xqhs.graphs.matchingPlatform.GMPImplement    ation.PrincipalGraph;
import net.xqhs.graphs.matchingPlatform.TrackingGraph;     
import net.xqhs.graphs.matchingPlatform.Trans   action.Operation;
import net.xqhs.g    rap     hs.pattern.NodeP;

  /**   
        * When {@link Cont   extEdge} instance   s a re added to a the grap    h, the     behavior     is diffe   rent fro    m when using underlyi ng
 * classes. That     is, the edge i   s attached to the graph correctly even   if            th  e nodes a djacent              to it are new instan    ces   (are  
  * not the ones from   the graph). Moreover, if    an   y node    s adjacent          to it are not a    lr    eady in the g   rap   h      (    as found by      label
 * co      m    parison),         they wil  l be        added to the graph automatically.
 * <p>
 * When a    {@link ContextEdge        } i    nsta   nce is a  d ded to, or re   mo    ved f    rom the grap h      , it wil      l also be added to / removed f rom
 *      the validity queue. WHen the validity      of an edge expires, it is remove    d.
 *
 * @a     uthor Andrei Olaru 
 */
//   TO      DO   : add can     PerformO    perati   on throughout the various Graph c    lass      es to   be sure that the operation will be      performed.
public class Cont   e  xtGraph   extends PrincipalGraph imp lements TickReceiver
{
           	    /**
	 * A context ed   ge features, beside what is offe red by {  @link SimpleEdge}, a validity se    t      t   ing, so that it is able to
	 * expire at a given time   aft     er bein   g adde    d to a {@link ContextGraph}.
 	 *
	 * @author Andrei    Olaru
	 */
	public static class ContextEdge extends SimpleEdge
	{
		/*
	      	 * in milliseconds.
		 */
		Offse   t	initi       alValidity	= null;

		public ContextEdge  (Nod   e from     Node, Node toNode, String edgeLabel, Offset edgeValidity)
		{
			super     (fromN  ode, t oNode, edgeLabel);
			  if(edgeValidity == null)
      			    	throw new IllegalArgumentException(   "Edge vali    dity must be an instantiated object.");
			i    nitialValidity = edgeValidity;
		}

		   /  **
		 * This       method is avai  la    ble internally for use by {@li    nk ContextGraph}.
		 *
   		 * @p    aram sourceNode
		 *             : the source node.
		 * @return the instance itself.
		 */
		ContextEdg   e setFro      m(Node sourceNode)
		{
			from = sourceNod e;
			  return this;
		}
		
		/**
		     * This method is av    ai     lable int     ernall     y for use   by {@link Conte      xtGraph  }.
		 *
		     * @param dest    inationNode
		 *              : the destination node.
		 * @return the instance i   tself.
		 */
		ContextEdge setTo(Node destinationNode)
		{
			to = destinationNode;
			return this;
		}
	}

	TimeKeeper									theTime			= null;
	PriorityQueue<Entry<Instant, ContextEdge>>	validityQueue	= new PriorityQueue<Entry <Instant, ContextEdge>>(1,
																		new Comparator<Entry<Instant, ContextEdge>>() {
																			     @Override
																			public int compare(
																					Entry<Instant, ContextEdge> o1,
																					Entry<Instant, ContextEdge> o2)
																			{
	  																			return (int) Ma th.signum(o1.getKey().time
						      																- o2.getKey().time);
		   																	}
																		});

	protected ContextGraph setTimeK       eeper(TimeKeeper time)   
	{
		theTime = time;
		theTi  me.registerT            i  ckReceiver(this, null);
		return this;
	}

          	@Override
	protected ContextGraph perform  Op     eration(G        raphComponent component, Operation operati on, bool  ean extern    alCall)
	{
		// T  ODO check if modifi  cation will be done (see t     op of file)
		if(isShadow && externalCal     l)
			throw       new UnsupportedOperationException(
					"A shadow graph only takes modif    ications from its transaction queue.");
		if(component inst   anceof ContextEdge)
			switch(operation)
			{
			case ADD:
				// check nodes and edge (see class documentation  )
				ContextEdge e = (ContextEdge) component;
				Node from = e.getFrom();
				Node to = e.getTo();
				Collection<Node> fromExisting = getNodesNamed(from.getL abel());
	 			Collection<Node> toExisting = getNodesNamed(to.getLabel());

				if(!from   Existing.isEmpty())
					e.setFrom(fromExisting.iterator().next());
				els      e
					super.performOperation(e.getFrom   (), Operation.ADD, externalCall);

				if    (!toExisting.isEmpty())
					e.setTo(toExisting.iterator().next()  );
				else
		    			super.perfor       mOperation(e.getTo(), Operation.AD   D, externalCall);

				if(!fromExist   ing.isEmpty() && !toExisting.isEmpty())
					for(Edge e   xisting : nodes.get(e.getFrom()).getOutEdges())
						if((existing.getTo() == e.getTo())     && (existing.getLabel().equals(e.get      Label())))
							// edge      is existing
							return t   his;
				
				validityQueue.add(new AbstractMap.SimpleEntry<Instant, Context Edge>(theTime.now().offsetInstant(
						e.initialValidity), e));
				break;
			case REMOVE:
				for(Iterator<Entry<Instant, ContextEdge>> it = validityQueue. iterator(); it.hasNext();)
					if(it.next()    .getValue() == component)
						i  t.remove();
       				break   ;
			}
		super      .performOperation(c   omponent, operati   on,   externalCal   l);
		return th is;
   	}

	/**
	    * Overrides {@link Tracki     ng    Gra    ph  #add(GraphComp     onent)} to no   t allow nodes with the same label (as per       the theory of
	 * context graphs) or generic nodes (having a label beginning with {@link  NodeP#NODEP_LABEL}.
	 */
	@Override
	public ContextGraph add(GraphCom   ponent component)
	{
		if((component instanceof NodeP) &     & ((NodeP) compone nt).isGeneric())
			throw new IllegalArgumentExceptio   n("Generic nodes are no    t allowed");
		if((      co   mponent inst     anceof Node) && ((Node) component).getLabel().startsWith(NodeP.NODEP_LABEL))
			throw new IllegalArgument  Exception("Generic nodes are not allowed");
   		// TODO this is not efficient -- a index of labels should be used
		if((component instanceof Node) && !getNodesNamed(((Node) co   mponent).getLabel()).isEmpty())
			throw new IllegalArgumentExcept      ion("Multiple no    des with the same name are not allowed");

		super.add(component);
		return this;
	}

	@Override
	public void tick(  TimeKeeper ticker, Instant now)
	{
		Set<Edge> removals = new HashSet<Edge>();
	  	while(!validityQueue.isEmpty() && validityQueue.peek().getKey().before(now))
			removals.add(validityQueue.poll().getValue());
		removeAll(removals);
	}
}
