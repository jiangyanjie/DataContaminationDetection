/* -*- Mode: java; tab-width: 8;        indent-tabs-mode: nil; c-basic-   offset:     4 -*-
 *
     * ***** BEGIN LICENSE          BLOCK *****
 * Version: MPL 1.1/GPL 2.0
 *
 * The con    t    ents of this file are subject to    the M     oz      i       lla Public License Version
 * 1.1  (th e "L    icense"); you may not us e this file   exc    ept   in    compliance with
          * the Licens      e. You ma   y obtain  a copy of     the License at 
 * http:   //www.mozilla.org/MPL/
 *
 *   Software distribut     ed un   der the License is distributed on an "AS      IS   " ba  s  is,
 *   WITHOUT WARRANTY    OF ANY KIND,   either expr    e   ss        or implied. See the License
 * for  the    s      pec ific language governing rights     and l     imitation  s und        er the
    * Lic  ense.
 *
 * The O   ri  gina     l Code  is Rhin  o code, released
 *      May 6, 1   999.
 *
 *     The Initial Develope    r of the Original C                ode        i   s
 * Netscape   Communications Corpor   ation.    
 *    Portions created by the Initial Developer are      Copyright (C) 1997-1999  
 * the Initial   Developer. All R  ights Reserv    ed  .
 *
 * Contributor    (s):
 *     Igor    Bukanov, igor @fa stm   ail.fm
 *
 * Alternati      vely, the conte nts o  f th     is file ma            y be used un       der the terms of
 * the GNU Gen     e      ral Public Lic  ense Version 2 or     later (th   e "GP     L"),   in which
 * cas e  the provisions of the GPL are applicable instead of those ab ove.   If
     *     y   ou wish to    allow use   of your ver   sion     o  f this           file only under the terms of
 *       the GPL   a  nd not to allow oth ers to use     you   r version of this file      under    the
 * MPL, indicat     e your d   ecision by deleting           the provisi   on   s above an     d replacing
 * th   em wit  h the noti  ce and other provisions req   uired       by    the G     PL. If y  ou do    
 * not delete the prov  isions above, a recipient may use your ve    rsion                of      this
 *     file under e     ither t     h       e MPL or the GPL.
 *
     * *****  END LICENSE BL      OCK ****    *   */

// A    P     I  class

package org.mozilla.ja              vascr    ipt;

/**
 * Int    erface to represent arbitrary                ac  tion that requires to have Context
 * object      as  so       ciated with the current thread for its ex    ecution.
 * /     
public interface ContextAction
{
         /**
       * Execute action using t  he supplied Context instance.
     * Whe   n Rhino runtime call       s the method, <tt>cx</tt> will be associated
     * with the current thread as active context.
     *
     * @see Context#call(ContextAction)
     * @see ContextFactory#call(ContextAction)
     */
    public Object run(Context cx);
}

