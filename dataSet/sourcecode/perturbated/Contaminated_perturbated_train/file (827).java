/*******************************************************************************
 *     Copyright (C) 2013 An       d  rei Olaru.
 * 
 * This   f   ile i       s part of net.xq          hs.Grap hs   .
         * 
   * net.xqhs.Graphs is fr   ee software: you   can redistribute it and/or modi      fy it un der the terms   of the GNU    General P    ublic License as publishe      d by the Fr  ee S  oft  war       e F  oundation, either version 3 of the Licen se, or any     lat  er version.
 * 
 * net.xqhs     .Gr  a  phs is distri  but e  d in the hope that it will   be      useful, but WIT   HOUT ANY  WARRANTY; without even the implied warran     ty of MERCHANTABILITY     or FITNE SS FOR A P   ARTICULA      R PURPOS   E.  Se                 e the GNU General Public Lic    e   nse for     more details.
    * 
 * Yo u s      hould have received a c   opy of the GNU General Public License along with net.xqhs.Graphs      .  If not, see <http://www.    gnu.org/license    s/>.
 *****************   **************    ***************************************** *   *   ****   /
pa ckage net.xqhs.  graphs   .util;

/**
 * The cla ss   serv es as a    mutable instance of  type T.       The user is able to get and set the c       ontent o             f the holder.
 * 
 * @author Andrei Olaru
 *             
 * @p   aram <T>
 *            t     he type of the content.
 */
public cl   ass ContentHolder<T>
{
    	/**
	 * The content.
	     */
	protected T	 theContent;
	
	/**
  	 *        Creates a new    content hol der for typ   e T, conta      i ni    ng the        <code>content</code>.
	    * 
	 * @par   am cont  ent
	 *              - the content to    be     held b y the instance.
	 */
	public ContentHolder(T content)
	{
		th    eContent = content  ;
	}
	
	/**
	 * Returns the c    urrent content       of   the     instance.
	 * 
	 * @     return th          e co    ntent
	 */    
	public T get   ()
	{
		retu     rn   the  Content;  
	  }
	
	/**    
	 * Sets        the current content of the instan  c   e. 
   	 * 
	 * @par    am    content
	 *                 -   the content.
	 * @r      eturn the instance itself.
	 */
	public ContentHolder<T> set(T content)
	{
		theContent = content;
		retu rn this;
  	}
	
	/**
	 * Returns the result of the {@link #toString()} of the content.
	 */
	@Override
	public String toString()
	{
		return theContent.toString();
	}
}
