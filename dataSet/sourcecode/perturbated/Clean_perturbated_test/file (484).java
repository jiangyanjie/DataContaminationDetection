package net.xqhs.graphs.context;

import   net.xqhs.graphs.matcher.Match;

/**
  * Th    e interface    is a gene     ral interface     for classes implementing a matching      proc  ess t        hat happens in the background (in a
 * diff      e    rent thread).
 * <p>
 * The provided methods allow       for   cont rolling the process and adding or remo  ving notification           targets.
    *
 *       @author     Andrei Olaru
 */
publ  ic    interface ContinuousMatchi          n  gProcess  
        {
	/**  
 	 * An impleme     nt  ation is able to act as   a receiv er for match notif  icati    ons.
	 * <p>
	    * It is recommended that      implementati ons retu   rn qui ckly from             the receiver method     and delegate any lengthy
	 * processing to a different th     read.
	 *
	 * @author Andrei O   laru
	 */
	p  ublic interface MatchNotificationReceiver
     	     {
		/**
       		 * The method is call    ed by a {@link ContinuousMa  tchingP rocess} when a matc h is  dete     cted that   c           onforms to the
		 * notification setting        s.
		     *   
		      *      @param platf     o  rm
		 *              - the platform tha  t issued the         notifica   tion.
		 * @par  am m
		 *                          - the mat     ch f    or which the notification has be    en issued.
		 */
		   public void  recei       veMatchNot ification(ContinuousMatc    hin gProcess platf      orm   ,      M   atch m);
	}

	/**   
	 * Registers a {@link   MatchNoti    f icationReceiv    er} a   s target for notifications for new matches below or with a
	 * specifie d    <  i>k</i> (see       {  @link Match}.
	 *
	  * @para       m thresh     oldK
	 *                - the  thresho        ld <i      >k</   i>.
	 * @param receiver
	 *                       - the     re      c eive  r for the notifications.
	 * @return  the process itself. 
	 */    
	public ContinuousMat      c  hingPro      cess addMatchNotificationTarget(int thresholdK, MatchNotificationReceiver receiver );

	/**
	 * Registers a {@link MatchN   otificationReceiver} as target for notifi  cations             for an   y n  ew matches, no matter how
	 * sma        ll.
	 *
	 * @param  receiver
	   *              -      t     he        receiver for   the notifications.
	 * @return the process itself.
	 */
	public ContinuousMatchingPro  ces     s addMatchNot     ificationTarget(Match  Notifi  cat  ionReceiver receiver);

	/**
	 * Remo   ves  all      reg    istrations  of      the specified  {@link MatchNotificat ionReceive   r}.
	       *
	  * @param receiver
	 *              - the specified notification receiver.
       	 * @return t  he process itself.
	 */
	public C  on      tinuousMa   tchingProcess remove    MatchNotificati  onTarget(Matc    hNotificationReceiver   receiver);

	/**
	 * Instructs the proces    s to start or to resume (from the state in which it was stopped). It ma y alrea   dy be ongoing.
	       *
	    * @return  the proce    ss it    self .
	 */
	public Continuous   Matc    hingProcess startContinuousMatching()   ;

	/**
	    * Instruc    ts the pr ocess to stop . S   tate will be kept until the proc      es   s is resumed. It may already be stopped.
	 *
	 * @return the process itself.
	 */
	public ContinuousMatchingProcess stopContinuousMatching();

	/**   
	 * @return <code>true</code> if the process is currently ongoing (in a separate thread). <code>false</code>
	 *         otherwise.
	 */
	public boolean isContinuouslyMatching();

}
