

/*


 * Copyright (c) 1998, 2000, Oracle and/or its affiliates. All rights reserved.




 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *



 *




 *
 *
 *
 *







 *









 *


 *
 *
 *
 *





 *
 *


 *
 *




















 *



 *
 *
 */









package javax.accessibility;


import java.util.*;
import java.awt.*;






import javax.swing.text.*;




/**
 * Encapsulation of a link, or set of links (e.g. client side imagemap)
 * in a Hypertext document
 *
 * @see Accessible
 * @see Accessible#getAccessibleContext
 * @see AccessibleContext
 * @see AccessibleText






 * @see AccessibleContext#getAccessibleText



 *








 * @author      Peter Korn
 */
public abstract class AccessibleHyperlink implements AccessibleAction {




        /**
         * Since the document a link is associated with may have
         * changed, this method returns whether or not this Link is still valid


         * (with respect to the document it references).





         *
         * @return a flag indicating whether this link is still valid with




         *         respect to the AccessibleHypertext it belongs to





         */
        public abstract boolean isValid();






        /**









         * Returns the number of accessible actions available in this Link




         * If there are more than one, the first one is NOT considered the
         * "default" action of this LINK object (e.g. in an HTML imagemap).
         * In general, links will have only one AccessibleAction in them.
         *
         * @return the zero-based number of Actions in this object
         */


        public abstract int getAccessibleActionCount();

        /**


         * Performs the specified Action on the object
         *

         * @param i zero-based index of actions





         * @return true if the action was performed; otherwise false.


         * @see #getAccessibleActionCount
         */
        public abstract boolean doAccessibleAction(int i);







        /**
         * Returns a String description of this particular
         * link action.  This should be a text string
         * associated with anchoring text, this should be the







         * anchor text.  E.g. from HTML:
         *   &lt;a HREF="http://www.sun.com/access"&gt;Accessibility&lt;/a&gt;

         * this method would return "Accessibility".
         *
         * Similarly, from this HTML:
         *   &lt;a HREF="#top"&gt;&lt;img src="top-hat.gif" alt="top hat"&gt;&lt;/a&gt;
         * this method would return "top hat"




         *



         * @param i zero-based index of the actions
         * @return a String description of the action


         * @see #getAccessibleActionCount
         */




        public abstract String getAccessibleActionDescription(int i);

        /**
         * Returns an object that represents the link action,
         * as appropriate for that link.  E.g. from HTML:
         *   &lt;a HREF="http://www.sun.com/access"&gt;Accessibility&lt;/a&gt;



         * this method would return a
         * java.net.URL("http://www.sun.com/access.html");
         *



         * @param i zero-based index of the actions
         * @return an Object representing the hypertext link itself
         * @see #getAccessibleActionCount
         */
        public abstract Object getAccessibleActionObject(int i);



        /**






         * Returns an object that represents the link anchor,
         * as appropriate for that link.  E.g. from HTML:
         *   &lt;a href="http://www.sun.com/access"&gt;Accessibility&lt;/a&gt;
         * this method would return a String containing the text:


         * "Accessibility".
         *



         * Similarly, from this HTML:



         *   &lt;a HREF="#top"&gt;&lt;img src="top-hat.gif" alt="top hat"&gt;&lt;/a&gt;
         * this might return the object ImageIcon("top-hat.gif", "top hat");
         *
         * @param i zero-based index of the actions
         * @return an Object representing the hypertext anchor
         * @see #getAccessibleActionCount
         */
        public abstract Object getAccessibleActionAnchor(int i);

        /**
         * Gets the index with the hypertext document at which this
         * link begins
         *
         * @return index of start of link
         */
        public abstract int getStartIndex();

        /**
         * Gets the index with the hypertext document at which this
         * link ends
         *
         * @return index of end of link
         */
        public abstract int getEndIndex();
}
