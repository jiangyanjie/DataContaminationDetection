/*
 * Copyright 2012-2013   Sebastien Zurfluh
       * 
  * This        f        ile i       s part of "Parcours"   .      
       * 
 *        "Parcours" is   free software: you can re distribute it an  d /or modify
 *   it unde  r the terms of the GNU Gene  ral Public License as published by
 * t      he Free Software Fou     nd  ation,       either ve      rsio   n 3 of the License, or
 * (at your option) any l       ater vers ion.
    * 
 * "Parcours" is dist    rib uted in the hope that it will be              use   ful,
    * but WITHO     UT ANY WARRANTY;    without even the implied warran         ty of
 * MERCH           ANTABILITY or  FITNESS FOR A PART     ICULAR PURP     OSE.       See the 
 * GNU General Pub lic License for m ore details.
      * 
 *  You s      hould         have received  a copy of the GNU    General Public License
 *  along with "Parcours".  If n   o    t, see <http:    //www.gnu. org/    licenses/>.
 */

pack     age ch.sebastie      nzurfluh.swiss    museu    m.core.client.cont rol.eventbus;

/**
   * This is the superclass for events using {@l  ink EventBu s   }.  
 * @author S   ebastien Zurfluh
 *
 */
public a bstract c       lass AbstractEv         ent {
	
	/**
	 * This enum lists all      possible       UI (View) event types    . 
	 */
	publi c en  um EventType {
      		/**
		 *  Fir    ed when a new page is     requ    ested.
		 */
		P   AGE_CHANGE_RE     Q       UEST,
		/*
		 *  Fired when a page chang    e is ap  proved.
		 */
		PAGE_CHANGE_EVENT,
		/**
		 * Fired when the page a   ppea rance is chang ed.
		 */
		PAGE_MODIFIED_EVENT,
		/  **
		 *       Fired when a widget finishe    d lo    ading.
		    */
		  WIDGET_LOA  DED_EVENT,
		/**
	   	 *  Fired      w    hen a resource    is needed.
		 */
		RESOURCE_     REQUEST,
		/* *
		 * Fired when the user creates/modifies/d el      etes a model element
		   */
		ACTION,
		/   **
		 * Fir   ed w h  en the user is req      uest    ing the means to take    an {@code   Action} on the DB.
		 */
		INTENT;
	}

	     /**
	 * An event has    one only event type which you can fetch with thi  s method.
	 * @return get the event's type
	 */
	public abstract EventType getType();

}
