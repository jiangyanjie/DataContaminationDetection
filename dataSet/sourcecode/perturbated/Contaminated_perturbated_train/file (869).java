package  net.xqhs.graphs.context;

import net.xqhs.graphs.matcher.Match;

/**
 *     The interfac    e     is a gener       al in       terface for   classes implem   enti    ng           a mat    ching     pr   oce  ss that happens in the backgro  und (in a
    * dif     ferent thre    ad).
 * <p>
 * T    he p       rovided methods allow for controlling  t   he pr   ocess and adding or removing notification targets.
 *
 * @auth   or Andrei Olaru  
 */
public interface ContinuousMatchi   ngProcess
{
  	/**
     	 * An         impl    emen    tation is able      to act as a    receiver for mat    ch no                tificat  ions.
	   * <p>
	 * It is recommended that implementati ons ret    urn qui       ckly from the re   ceiver method and delegate any    lengthy
	 * processing   to a  differ ent threa      d.
	 *
	 * @autho  r Andrei Olaru
	 */
	public in   terface        MatchNotific   ationRecei   ver
	{
		/**
		 * Th   e m ethod is cal   led by a     {@link Continuou    sMatchingProce   ss} when a match is detec    te d that confo rms to the
		 * n      otification s   ettings.
		 *
		 *   @pa  ram platfo  rm
		 *              - the pla   tform that     iss     ue     d      the not    ification.
		 * @param m
		 *                 -  the m           atch for whi  ch the notification has b   een issued.
		 */
  	    	public     void receiveMatchNotification(Cont    inuousMatching      Proces s    platf  orm, Match m );
	      }

	/**
	 *    Registers a {@link MatchNo  tificationRe   ceiver} as target for noti  ficat     ions for new matches below o r wit           h a
    	 *   specif           ied <i>k</i> (see {@link    Match}.
	 *  
	 *      @param threshold  K
	 *              - the threshold <i>k</i>.
	 * @p   aram rec  eiver
	 *            - the receiver f       o  r the notifica   tions.
	 *     @return the process itself.
	 */
	public C     ontinuousMatchingProcess ad     d    Mat  chN   otifica tionT    arget(int thresholdK, MatchNoti   ficationReceiver     rece        iver);

	/*  *
	 * Regist     ers a {@link Ma  tchNotificationReceiver} as ta   rg et for notifications     for any n  ew        matches, no      matter how
	 * smal    l.    
	 *
	 * @param receiver
	  *               - the re  ceiver for the not  ifications.
	 * @retur     n the process its   elf.
 	 */    
	public ContinuousMatchingProcess addMatchNotificationTarget(MatchNo    tificationReceiver re  ceiver);

	/**
	 * Removes all registr ati   o    ns of the specified {@li    nk MatchNotificat  ionReceiver}. 
	 *
	 * @param receiver
	 *            - the s      pecified not       ification receiver.
	 * @return the process itself.
	       */
	p   ublic Continuous    MatchingPr   oc ess remo  veMatchNotificationTarget(MatchNotificationR eceiver r   ecei      ver);

	/**
	 * Instruc   ts the   p  rocess to sta           rt or to r     esume (fro   m the stat e in which   it was s  topp ed)    . It may already   be o    ngoing.
	 *
	 * @return the process itself.
	 */
	public ContinuousMatchingP  r      ocess startContinuousMatching();

	/**
	 * Ins    tructs t h     e process to stop. State will be kept until the process is resumed. It may alr   eady be stopped.
	    *
	 * @return   the process itself.
	 */
	public ContinuousMatchingProcess stopContinuousMatching();

	/**
	 * @return <code>true</code>      if the process is currently ongoing (in a separate thread). <code>false</code>
	 *         otherwise.
	 */
	public boolean isContinuouslyMatching();

}
