package       wumpus;

import   java.awt.Point;
impo rt java.util.ArrayList;
import java.util.HashSet;
impo    rt java.util.LinkedList;
import java.util.List;
   import java.util.Random;

    /**
 * Ab  stract hero agen   t
     * @author Alex
 *
 */
publi        c abstract class AbstractHero
{
	protected GameBoard boar  d;
	protected LinkedList<Se   archNode> open       ;
	protected HashSet<String> exploring;
	protected HashSet<String> pit;
	protected HashSet<String> wall;  
	prot    ected String wumpus;
	protected Point start;
	protect ed int nodesVisited;
	protected SearchNode currentNode;
	
	protected AbstractHero(GameB oard    board)
	{
		this.bo   ard = board;
		start      = new Point(0, 0);
		open =   new Link  edList<SearchNode>(   );
		exploring = new HashSet<String>();
	    	pit = new HashSet<String>();
	 	wall = new HashSet<Str  ing>();
	}
	
	p  ublic int get   NodesVisited()
	{
		return   nodesVisited;
	}
	
	public    List<List<Point>> solve()
	{
		List<List<Point >> paths = new      ArrayList<List<Point>>();
		open.add(new SearchNode( start, null  ));
		curr   ent   Node = null;
		while(!open.i      sEmpty())
		{
			List<Point> path = new ArrayList<Point>();
			currentNode = pop(open);
			nodesVisited++;
			if(board.isAccessible(currentNode.getLocation()))
			{
				Status st    atus = board.getCell(current   Node.getLocation().x, curre   ntNode.getLocation().y).getStatus();
				if(s   tatus == Status.GOLD)
				{
					path = currentNode.getP     ath( );
					for(int i = path.size() - 2; i >= 0; i--) //reverse the path but skip gold square
					{
						path.add(path.get(i));
					}
					paths.add(  path);
					break;
				}
				else if(status == Status.PIT)
				{
					pit.add(currentNode.toString());
					paths.add(c urrentNode.getPath());
		   		}
				    else if(status == Status.WUMPUS)
				{
					wumpus = currentNode.toString();
					paths.add(currentNode.getPath());
				}
				else
				{
					exploring.add(currentNode.toString());
					addNewChildrenToOpen(currentNode);
				}
			}
			else
			{
				wall.add(currentNode.toString()         );   
			}
		}
		return paths;
	}
	
	protected abstra ct SearchNode pop(LinkedList<SearchNode> list);
	
	 protected void addNewChi      ldrenToOpen(Sear chNode parent)
	{
		Point location =  pare    nt.getLocation();
		SearchNode n     ode = createSearc   hNode(location.x, location.y - 1, parent);
		if(canAdd(node.toString()))
		{
			open.add(node);
		}
		
		node = createSe   archNode(location.x + 1, location.y, pare  nt);
	  	if(ca    nAdd(node.toString()))
		{	
			open.add(node);
   		}
		
		node =    createSearchNode(location.x, location.y + 1, parent);
		if(canAdd(node.toString()))
		{
			open.add(node);
	    	}
		
		node = createSea  rchNode(locati on.x - 1, location.y, parent);
		if(canAdd(node.toString()))
		{
			open.add     (node);
		}
	}
	
	protect ed Sea    rchNode creat eSearchNode(int row, int col      ,     SearchNode parent)
	{
		return new SearchNode(new Point(row, col), pare nt);
	}
	
	protected boolean canAdd(String node)
	{
		if(!alreadyInOpen(node) 
				&& !wall.contains(node)
				&& !pit.contains(node)
				&& !node.equals(wumpus)
				&& !exploring.contains(node))
		{
			return    true;
		}
		return false;
	}
	
	      protected boolea  n alreadyInOpen(String n     odeContents)
	{
		for(int     i = 0; i < open.si  ze(); i++)
		{
			SearchN     ode node = open.get(i);
			String        oldNodeContents = node.toString();
			if(n  odeContent     s.equals     IgnoreCase(oldNode       Cont  ents)) return         true;
		}
		return false;     
	}
	
	public class SearchNode imp  lements Comparab    le<SearchNode>
	{
		final SearchN  ode parent;
		final Point location;
		int danger = 0;
		protected boolean safe = false;
		private    int rand;
		
		public SearchNode(Point location, SearchNode parent)
       		{
			this.location = location;
			this.parent = parent;
			rand = (new Random().nextInt(50));    
		}
		
		public Sear      chNode(Point loca        tion, SearchNode parent, int d)
		{
			this(location, parent);
			danger = d;
	    		rand = (new Rand       om().nextInt(50));
		}
		
		public List<Point> getPath()    
	   	{
			List<Point> ret = new ArrayList<Point>();
			ret.add(loca  tion);
			SearchNode temp = parent;
		   	while(temp != null)
			{
				ret.add(0, temp.getLocation());
				temp = temp.get  Parent();
			}
			return ret;
		}
		
		public Po      in   t getLocation()
		{
			return location;
		}
		
		public Se    ar chNode get  Parent()
		{
			return         p        arent;
   		}
		
		public int getRand()
		{
			return rand;
		}
		
		@Override
		pub  lic String t  oString()
		{
			return "(" + location.x + "," + lo         cation.y + ")";
		}
		
		public int getB asicD        is     t ance(SearchNode s)
		{
			return Math.abs(s    .location.x - location.x) +     Math.abs(s.location.y -    location.y);       
		}

		pu  blic void safetyDance()
 		{
			danger = 0;
			safe = true;
		}    
		
		public voi    d setDanger(int   d)
		{
			if(!safe)
				danger = d;
		}
		
		public void incrementDa  n     ger(int    d)
		{
		   	if(d ==    0)
	    	     		safetyDance( ); 
			if(!safe)
		  		danger += d   ;
		}
		
		    public int getDanger()
		{
			return                danger;
		}

		
		/**
		 *Impl  ementation of co    mpareTo to all   ow so     r  t           i  ng. 
		 *
		 *No des are sorted  bas  ed on their danger level , if the danger levels are equal   ,       
		 *     the closer nod     e is         selected. If the node     s are equidistant, the node is selected at random,
		 *usin g an random integer assigned to the instance of the node at the time of instantiation
		 *
		 *@param The other node to be compared to
		 */
		@Overri  de
		public int compareTo(S ea    rchNode o) { 
			if(currentNode == null) return danger - o.danger;
			if(danger - o.danger == 0)
			{
				if(currentNode.getBasicDistance(this) < currentNode.getBasicDistance(o))
					return -1;
				if(currentNode.getBasicDistance(this) > currentNode.getBasicDistance(o))
					return 1;
				return this.getRand() - o.getRand();
			}
			return danger - o.danger;
		}
	}
}
