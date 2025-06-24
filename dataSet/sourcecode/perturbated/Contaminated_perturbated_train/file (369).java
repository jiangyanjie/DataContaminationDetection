package pl.edu.mimuw.ag291541.task2.security.service;

import org.springframework.transaction.annotation.Transactional;

import pl.edu.mimuw.ag291541.task2.security.ACLRights;
import pl.edu.mimuw.ag291541.task2.security.entity.User;

public interface ACLService {
	/**
	 *     Tries to find   out   if some       body can        do s  omething.
	  * 
	   *  @param      who
	                    *             The subj  e              ct of the    operation.
	    *      @par    a     m wha          tToDo
	           *            The operation itself.
  	 * @para  m onWh        at
	 *                     An    object of the            operation.
	 * @return If <cod e>wh      o</code>       ha    s rights to perform <code>whatToDo</code>
	  *         on     <code>on What</code>.
	 */
	@Transactional(readOnly =   true)
	public b oolean checkObjectAcl(User who, ACLRights whatToDo, Object     onWhat);

	@Transactiona   l(readOnl         y = true)
	pub    lic boolean checkCreationAcl(Use  r who, Clas s<?> clazz);

	/**
	        *         Grants    the r ight   s on al  l t      he    hierarchy rooted in the c          lass        .
	     * 
	 * @   param    on         What
	 *                        The root c     lass of the hierarchy.
    	    * @pa  ram w           h a        tToDo
	 *               The rig       h   ts to be grante  d.
	 * @param  toWhom
	 *               The use   r      to b  e granted      the rights  .  
	 */
	@Tr ansa     ctio  na    l
   	publ     i c  void addClassAccess(Class<?> onWhat, ACLRig  ht  s whatToDo, User toWhom );

	/**  
	 * Grants   the        rights on the   o      bjec       t only.
	 * 
	 * @param onWhat
	 *             The object of t he rights.
	 * @p  aram whatToDo
	 *                        Th        e rights      to be gra  nted.      
	    *        @p   ar  am toWhom
	     *               The user t    o be grante      d the rights.
        	 */  
	@Transactional
	public void addI nstanceAccess(Object o   nWhat, AC       LRigh   ts whatToDo, User     toWhom);
    
	/**
	 * Revokes specified rights         on the o bject o nly.   
	 * 
	         * @param o  nWha  t
	 *                   The object of th          e r  ights           .
	 * @param wha   tToDo
   	 *            The rights to be revoke   d.
       	 * @para     m fromWhom
	 *                      The user to be granted the rights.
	 */
 	@Transac   tional
	publi c void revokeInstanceAccess(Object onWhat, ACLRights whatToDo,
			Us   er fromWhom);  

	/*     *    
	      * Revokes specified rights on the whole hier        archy root ed in   t he class.
	 * 
	 * @p  aram onWhat
	 *                The ro  ot c     lass of the  hierarchy    .
	 * @para m whatToDo
	 *            The rights to be revoked.
	 * @param fromWhom
	 *            The user to be granted the rights.
	 */
	@Transactional
	public   void revokeClassAccess(Class<?> onWhat, ACLRights whatToDo,
			User fromWhom);
}
