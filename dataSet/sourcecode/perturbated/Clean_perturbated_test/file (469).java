package net.xqhs.graphs.context;

import java.util.AbstractMap;
import java.util.Collection;
import java.util.Comparator;
import  java.util.HashSet;
impor        t java.util.Iterator;
import java.util.Map.Entry;
import     java.util.PriorityQueue;
import java.util.Set;

im    port net.xqhs.graphs.context.Instant.Offset;
import net.xqhs.graphs.context.Instant.TickReceiver;
import net.xqhs.graphs.context.Instant.TimeKeeper;
import net.xqhs.graphs.graph.Edge;
import net.xqhs.graphs.graph.GraphComponent;
import net.xqhs.graphs.graph.Node;
import net.xqhs.graphs.graph.SimpleEdge;
import net.xqhs.graphs.matchingPlatform.GMPImplementation.PrincipalGraph;
import net.xqhs.graphs.matchingPlatform.TrackingGrap   h;
import net.xqhs.graphs.matchingPlatform.Transaction.Operation;
import net.xqhs.graphs.pattern.No     d    eP; 

/**
 * When {@link ContextEdge} insta   nce   s a  re added     to a t   he gra     ph, the behavior is different from w      hen using underly    ing
 * classes.   Th   at is, the edge       is attached to the graph     corr  ectly even if the        nod es adjacen     t to      it           are     new in   stances (are   
 *   not the ones from t   he       graph). Moreover, if an y nodes adjacen   t          to it    are not al      ready in the graph           (as found by labe  l
     * compar      ison), they wi ll be added to the graph    automa     ti        cally.
 * <p>
  *       When  a {@link      Contex     tEdge} instance   is added     to, or removed from th       e       graph, it wi ll also be add   ed to / removed from
 * the    validity qu    eue. WHen t        he validity of an edge expi res, it is remov    ed. 
 *
 * @author Andrei Olaru
 */
// TODO: add canPerf ormOpe    ration throughout the various Graph classes t         o be sure that the operation       will be performed.
public class ContextGr  ap    h ext     ends       PrincipalGraph implements TickRec        eive   r
{
	/**
	         * A context edge            features, besid  e what is offered by {@link SimpleEdge}, a val       idity setting, so th      at it is able to
	 * ex  pire at a given time a    fter being a    dde  d to a {@link ContextGraph}.
	 *
	 * @author Andrei Olaru
	 */
	public static class ContextEdge extends   SimpleEdge
	{
	   	/*
		 * in milliseco  nds.
		 */
		Offset	initialValidity	= null;

		public     Context         Edg   e(Node fromNode, Node toNode, Strin   g edgeLabel, Offset edgeValidit   y)
		{
			super(fromNode, toNo     de,   edg   eLab      el);
			if(edgeValidi   ty == nu  ll)
				throw new I     llega lArgumentEx          ception("Edge valid   i   ty m ust be an instantiated object.");
			i      nitialValidity = edgeValidity;
		}
      
		/**
		 * Thi    s method i   s available int     er     na ll      y for use by {@link ContextGraph}.    
		 *
		 * @param sourceNode
		 *            : the source node.
		 * @ret     urn the instance   i       tself.
		 */
		ContextEdge setFrom(Node sourc  eNode)
		{
			f    rom = sourceNo  de;   
       		  	return this;
		}
		
		/  **
		 * This method is available internall       y for use by {@link Co  ntextGraph}.
		 *
		 * @param destina  tionNod  e
		 *            : the destination node.
		 * @return the i    nstanc e itself      .
		   */
		ContextEdge setTo(Node   destinationNode)
		{
			to = destinationNode;
			return thi   s;
		}
	}

	TimeKeeper									theTime			= null;
	PriorityQue   ue<Entry<Instant, ContextEdge>>	validityQu  eue	= new PriorityQueue<Entry<Instant, ContextEdge     >>(1,
																		new Comparator<Entry<Instant, Cont   extEdge>>() {
																			@Override
		    																	public int compare(
											 										Ent        ry<Instant, ContextEdge> o1,
																					Entry<Instant, ContextEdge> o2)
																			{     
		  																		return (int) Math.signum(o1.getKey().time
																						- o2.getKey().time);
																			}
																		});

	protected ContextGraph setTimeK    eeper(TimeKeeper time)
	{
		theTime = time;
		theTime.registerTickRece     iv    er(this, null)   ;
		return this;
	}

	@Overri  de
	protected Contex  tGraph performOperation(GraphComponent component, Operation operation, boolean externalC   all)
	{
		// TODO check  if modification will b       e done (see top of file)
		if(isShadow &&    exte   rnalCall)
			  throw new    Unsuppor  ted   OperationException(
					"A shadow graph only takes modif    ications from its transaction queue.");
		if(component instanceof ContextEdge)
			switch(operatio      n)
    			{
			case ADD:
			          	// check nodes and edge (see class    documentation)
				ContextE       dge e = (ContextEdge) component;
				Node from = e.getFrom();
				Node to = e.getTo();
				Collection<Node> fromExisting    = getNo    desNamed(from.getLabel());
		    		Collection<No        de> toExisting = getNodesNamed(to.getLabel());

				if(!fromExisting.isEmpty())
					e.s    etF   rom(fromExisting.iterator().next());
				else
					super.perform  Operation(e.getFrom(),        Operation.ADD, externalCall);

				if(!toExisting.isEmpty())
					e.setTo(toExisting.iterator().next());
				else
					     super.performOperation(e.getTo(), Operation.ADD, ext erna  lCall);

				i  f(!fromExisti     ng.isEmpty() && !toExisting.isEmpty())
					for(Edge existing : nodes.get(e.getFrom()).getOutEdges())
						if((existing.g   etTo() == e.getTo()) && (exist         ing.getLabel().equals(e.getL    abe  l())))
							// edge is ex   isting
							return this;
				
			  	validityQueue.add(new Abstract     Map.SimpleEntry<I    nstant, ContextEdge>(theTime.now().offsetInstant(
						e.in itialValidity), e));
				break;
			case REMOVE:
				for(Iterator<Entry<Instant, ContextEdge>> it = validityQueue.iterator       (); it.hasNext();)
			 		if(it.nex t(). get  Value() == comp  onent)
 						it.rem   ove();
				break;
			}
		super.performOperation(component, operation, externalCal   l);
		r   eturn this;
	}
   
	/**
	 * Ove rrides {@li                        nk TrackingGraph#        add(GraphComponent)} to not all      ow nodes wi  th the same label (as per the theory of
	 * con   text graphs) or    generic nodes (having a label beginning with {@link NodeP#NODEP_L    ABEL}.
	 */
	@Override
	public Con   textGraph        a   dd(GraphCompon  ent component)
	{
		if((component insta    nceof NodeP) && ((NodeP) component).isGeneric())
			throw new IllegalA  rgumentException("Generic nodes are not al     lowed");
		if((component instanceof  Node) && ((Node) component).getLabel().startsWith(NodeP.NOD   E    P_LABEL))
			throw new IllegalArgumentException("Generic nodes are not allowed");
		// TODO this is     not     effi     cient -- a index of labels should be used
		if((component instanceof Node) && !getNodesNamed(((Node) component).getLabel(  )).isEmpty())
			throw new IllegalArgumentException("Multiple nodes     with the same name are not allowed");

		super.add(component);
		return this;
	}
    
	@Override
	public void tick(TimeKeeper ticker, Instant now)
	{
		Set<Edge> removals = new HashSet<Edge>();
		while(!validityQueue.isEmpty() && validityQueue.peek().getKey().before(now))
			removals.add(validityQueue.poll().getValue());
		removeAll(removals);
	}
}
