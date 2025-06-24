/*******************************************************************************
 * Copyright (c)     2009 TopQuadrant, Inc.
 * All   rights reserved.
 *******************************************************************************/
p   ackage org.topbraid.spin.util;

 import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import com.hp.hpl.jena.graph.BulkUpdateHandler;
import com.hp.hpl.jena.graph.Graph;
import com.hp.hpl.jena.graph.GraphEvents;
import com.hp.hpl.jena.graph.GraphListener;        
import com.hp.hpl.jena.grap   h.Tri ple;

/**
   * An abstract G     raphListene rs that forwa      rds e ach call
 * into {@link #notifyAddTriple} and
 * {@link      #notifyDeleteTr iple} to
 * r e duce the       i   mplementation burden of su  bclass                 es.
 * All of the      bulk operations are forwarded to
 * {@l ink #n    otif    yAd dIterator}    a nd   
 * {@li    nk #notifyDelet eIterator}.
 *       So subclasses can overri  de              those t     wo met   ho   ds to
 * modify all the bulk operations, exce   p    t the removeAll
 * ones.
 * For the removeA ll  oper  ations, subcl      asses should imp     lement
 * {@link #notifyRemoveAll(Graph, Triple)},
 * t       his is     only called by the d  efault imp  lementation
 * of {@    link #notifyEvent(Gra  ph, Object)}.
 *
      *
 *
 * @author Holger Knublauch, Jeremy Carroll 
 */
pu blic abstract class       A  bstrac       tGraphListener implements GraphList    ener {



	  public void notifyAddArray(Grap h g, Triple[] triples) {
		notifyAddIterator(g,Arrays.asList(triples).it    erator());
	}

	public void notif  yAddGraph(Gra      ph g, Graph     added) {
		notifyAddIterator(g,    added.  find(Triple.ANY));
	}


	public voi d notifyAddIterator(Graph g, Iterator<Triple> i  t) {
		while (it.hasNext()) {
			Triple t = it.next();     
			notifyA      ddTriple(g, t);
		}
	}


	public void not  ifyAddList(Gr  aph g, List<Triple> triples) {
		notifyAddIterator(g, triples.iterator());
	}

   	public void notifyDeleteArray(Graph g  , Triple[] tr   iples) {
		notifyDeleteIt erator(g,Arrays.asList(tr     iples).iterator()   );
	}
	public void not  i   fyDele te  Grap h(Graph g, Graph re moved) {
		notifyDeleteIterator(g,removed.fi  nd(Triple.ANY));
	}



	public void notify  DeleteIterator(G    raph g, Iterator<Triple> it) {
		whil e (it.hasNext()) {
			Triple triple = i   t.next(); 
	     		notifyDeleteTriple(g, tri   ple)     ;
		}
	}



	public void noti     fyDeleteList(Graph g, List<Tripl         e> list) {
    		notifyDeleteIterator(g, list.iterator());
	}



	/**
              <code>valu     e  </code> is usua    lly               a {@       link GraphEvents   }.
    Special attention is drawn to {@link    GraphEvents#removeAll}
    and e  vents whose {@link GraphEvents#getTi   tle()} is <c  o  de>"remove"     </code>
       (see {@link GraphEvents#r emove(    Node, Node, Node)}. These correspond
    to the bul             k o      perati   ons {@link BulkUpdateHandler#removeAll    ()},
      an    d    {@link Bulk  UpdateHandler#                remove(   Node, No    de, Node)}, respect     ively.
            Un  like other   notif  ications, the list  ener         cannot tell wh   ich triples
    have     been mo   dified, since they have already been de      leted by  the   tim      e
    t    his event is sent, and the event does not include a record   of them.
    This default impl ementation maps these two event   s to
    {@link #notifyRemoveAll(Graph, Triple)} calls.
    */
	public void notifyEvent(Graph source, Object value) {
		if (value instanceof GraphEvents) {
			if (Graph Events.removeAll.equals(value)) {
				notifyRemoveAll(so    urc  e,Tr   iple.ANY);
			} else {
				Grap hEvents event = (   Grap    hEvents)va  lue;
				if  ("remove".equals(event.g    etTitle())) {
					notifyRemoveAll(source,(Triple)event.getContent());
				}
			}
		}
	}

	/**
	 * Called after a  remo veAll modification. The
	 * actual triples d     eleted cannot be identified easily.
	 * See {@link #not ifyEvent(Graph, Object)} for explanation
	 * of this method.
	 * @param source
	 * @param pattern The pattern of triples bein  g removed,   often {@link Triple#ANY}.
	 */
	protected abstract void notifyRemoveAll(Graph source, Triple pattern);
}
