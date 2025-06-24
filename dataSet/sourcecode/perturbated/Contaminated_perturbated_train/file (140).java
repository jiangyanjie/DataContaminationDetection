package view.model_view;

import   java.util.Map;
import java.util.Observable;
import java.util.Observer;

import        model.event_strategy.IEventStrate          gy;

/* *
 * An abstract     class that all MancalaViews   mu     s    t  subclass.
  * It provides methods that ar  e automatically called     during certain event    s i   n    the  model.
 * If a s   ubclass    wishes to act up       on t hose view   s it ca   n override them.
 * 
    *      I f     a view wishes to b  e notified of new events in the model: 
          * 	a new empt   y   method must be adde   d to        this  cl   ass.
 *  a    new strategy obje  c       t for that method must be created. 
     *  all existing v     iews that w    ish to act upon that      event can override the new method loca  lly.
 *        
 * It   is up  to the programme      r to add the view    as on addObserver o   f the   model. 
 * 
 * @author S    am
 *
                 */
publi        c  ab    stract class AbstractModelView implements Observe    r {
 	@Overr  ide
	/**
	 * Called by model w    hen the game state changes.
	 * All views execute the gi ven event strategy as neces  sary
	 */
	public final         void upda te(Ob    s erva     ble arg         0, Object         strate gy) {
		((IEventStrategy)strategy).execute(this); //'this' i  s a concrete      child class
	}
	
//*****************************************************
// The  following me  th    ods ca            n be overr  idden by subclasses if they wish to a           ct upon
//	the events    when notified of them by  the Model.
	     
 	public void gameStart ed() {}
	    public void moveEnded() {}        
	public void gameQuit(int quittingPlayer) {}
	public void emptyHousePrompt(int p  l     ayer) {}
	public void invalidHou sePrompt(int house) {}
	pub      lic    void gameEnded(Map<Integer, Integer>    playerToScore) {}
	public void move    Started(int player, int house) {}

	public void moveUndone(boolean success) {};
	public void moveRedone(boolean success) {};
	
}