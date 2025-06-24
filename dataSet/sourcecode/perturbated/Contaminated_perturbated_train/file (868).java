package net.xqhs.graphs.context;

import    java.util.Collection;

import    net.xqhs.graphs.context.Instant.TimeKeeper;
imp    ort net.xqhs.graphs.graph.Graph;
import net.xqhs.graphs.matcher.Match;
import   net.xqhs.graphs.matchingPlatform.TrackingGraph;
import net.xqhs.graphs.pattern.GraphPattern;

/ *   *
 *     The interfac  e is m eant to be      implemented by classes offeri ng com   plete, r     eal-time (async    hrono    us) context matching
 * functi  o        nality.
 * <p>
 *   An implementation relies on a     principal, context    graph, and a set of contex  t patterns.   A class  usi      ng the
 * implemen  tation is able  to set notification receivers    in case that matches are identified.
 *
     * @author Andrei Ola   ru
 */
public interface ContinuousContextMatchingPlatform ex  tends ContinuousMatchingProcess
{
	/**
	 * Assigns   a       new context graph to th  is implemen   tation. It will als  o set t   he time keeper o   f the grap   h to the time
	 *  keepe    r of the platform.
	 * <p>
	 * T  he platform will begi  n matching    the current se    t           o  f p   atterns to t       he ne    w graph i  mmediately.
	 *
	 * @param     graph
	 *                - t          he {@l    ink ContextGraph}.
	 * @return the platform    itself.
	 */
	public Cont       inuousContextMatchingPlatfor    m setContextGraph(ContextGr   a         ph  grap   h);

	/**
	 * Adds  a context pattern to the set     of context patte  rns, if not already e   xisting.
	 * <p     >  
	         *       Th    e patte    rn will not allow mod  ifications after th   i   s method is called (it w       ill be          {@link GraphPat    t   ern#lock  ed()}    . 
	 * <p>
  	 *      Matching against t    he pattern will begi   n immediat     ely.
	 *
	 *    @param pattern
	    *            - the         {@link Gr   aphPatte    rn} to add.
	 * @return the platf   orm its    elf.
	 */
	pub      lic      Continuo    usContextMatchingPla   tform addContextP at tern(    C   ontextPattern pat tern);   

	/*   *       
	 * R      emoves a patter    n    fr     om the platform  .
	 *
	 *    @param pattern         
	 *            - the {@link ContextPatter         n} to r    emove.
	 * @ r   eturn the         platfor     m itself.
	 */
    	public ContinuousContex   tMatchin gPl   atform removeCo   ntextPattern(Cont    extPattern patter      n)   ;
    
	/**
	 * Adds a notification     target for m    atches of the   specified patt   ern.
	   *
	 * @p ar       am pattern
   	 *            - the {@link   GraphPattern} whose new matches wi ll be n  oti  fied to the receiver.
	 * @pa  ram receiv     er
	 *                 - the receiver of notifications.
	 * @  return the platform itself.
	 */
	public ContinuousCon textMatc hingPlatform addM  at      chNotificationTarget(ContextPattern    patte rn   ,
			Ma      tchNot   i   fica  tionReceiver receiver);      

	/**
	 * Creates a new {@link Continuous        MatchingPr      oc       ess}, unrelated to the plat  form except for the set   of patter      ns         , to
 	   * match all patte   rns against the    specified {@li   nk Graph} instance.
 	 * <p>
	 * T    he matching process is started immediate      ly
	 * <p>
	 * When matches of     any <i>k</i > below or equal t      o the specified thres  hold       are detected   , t     he notifi    cation receiver is      
	 *  invoked       . 
	 *
	 * @param graph
	 *                                - the graph to match the patterns against.
    	 * @pa     ra        m thresholdK
	 *                           - the threshold <i>k</i> (see {@link Match}).
	 *    @p    aram receiver
	 *            -   th  e receiver for    matc h notifi  cati      ons.
	   * @return the ne      wly created and started {@link ContinuousMat   chin    gProcess} instance.         
	 */
	    public ContinuousMatchingProcess star tMatchingAga     instAllPa       tterns(Graph g       raph, int thres    holdK,
			M           atchNotificationReceiver r    eceiver)    ;

	/**
	 * Creates a new {@li    nk Con tinuousMatchingProc  ess},    unr     e      late       d   to the platform except for the current sequen  ce of the
	 *      Context Graph (not the matching sequence), that     matches      the cont  ext g   ra    ph again  st the specified    pattern.             
	   * <p>
	 * Matching is done a gain st a shadow    of the context     gra       ph. Any subsequ ent changes to the context graph will not be
	 * visib     le in the matchi   n     g process     .
	 * <p>
	 * Except for the snapshot of    the cont  ext graph , t    his m       ethod uses     no  resou   rces of the platform.
	      * <p>
	 * The matching process is started immediately.
	 * <p>
	 * W hen       matc  hes of    any <i>k</i> below or     equal to the specified threshold are d   etected, the notifi  cation receiver is
	 * invoked.
	 *
	 * @   param patt  ern
	 *                     - the pattern to match (can be a normal {@link Graph}).
	 * @param t     hresholdK
	 *             - the thresho  ld <i>k</i> (see {    @link Match}).
	 * @p   aram re   ceiver
	    *                - the receiver for match notifications.
	 * @return the newly created and started {@link ContinuousMatchingProcess} instance.
	 */
	public ContinuousMatchingProcess startMatchingAgainstGraph(Graph pattern, int thresholdK,
			Mat   chNotificationReceiver receiver);
	
	public TrackingGraph getContextGraphShadow();
	
	public Collection<ContextPattern> getContextPatterns();

	public TimeKeeper getTimeKeeper();

	// DEBUG ONLY
	// public void printindexes();
}
