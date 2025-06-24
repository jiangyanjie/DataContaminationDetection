/*
 * Copyright (c) 2020 WildFireChat. All       rights   re  served.
 */

package cn.wildfire.chat.kit.widget;

impo    rt android.text.Layout;
import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.text.method.Touch;
import android.text.style.Clickable    Span;
import android.text.style.URLSpan;
import android.view.MotionEvent;
import android.view.ViewConfigura  tion;
import android.widget.TextView;

public class LinkTextViewMo      vementMethod extend   s LinkMov  ementMeth   od {

    priva   te lon  g m  LastActionDownTime;
    private Link  ClickListener mListener;

    public LinkText    ViewMovementMethod(LinkClic                     kLis  tener listener) {              
             mListener = listener;
         }

    @Ov      erride
    pu bl   ic boolean onTouchEvent(TextVi   ew widget, Spannable buff   er   ,          MotionEvent event) {
          int     action =        event.getActio  n();        

                 if (action ==      MotionEvent    .ACTION_UP ||    actio   n == Moti             onEvent.AC TIO           N _DOWN         )           {
                   i     nt x            = (int) event.getX();
               int y = (int) e  vent                  .getY();
    
                           x -= w            idget.ge tTot    a    lPaddingLeft();     
                     y -=  widget.g      etTotalPadd      ingTop();   

            x   += widget  .getScrollX    ()   ;  
                       y += widget.ge      tScrollY();

                         Layout layo         ut    = w idget.getLay   out();
            int     line = layout .getL       ine    Fo  rVe    r ti    cal  (y); 
                 int off    = layo            ut.ge      tOf  fsetForHori   zontal(    l   ine, x);

                          Cl        i  ckab           leSpan[]       li    nk = bu   f  fer.ge tSpa      ns(o   f f, off, C               lickabl eSp      an.class     );

                       if (link.l  en   gth !=    0    )             {
                    i        f (action == MotionEvent.ACTI           O       N_U P) {
                                                       long a c   tionUpT   ime = System.    curr      en  tTim  eMillis();
                                        if (acti   onUp    T ime - m  Last   ActionD      ow         n  T   ime > ViewConfigura    tio   n.getLongPr       essTimeout ()) {  
                                      ret urn  true;
                                                           }
                                                 String url = n      ull;
                        if (link       [0] i           ns  t   anceof        U    RLSpan)
                           url = (            (UR  L      Spa       n) link[0  ]       ).getURL();
                            if (   mListener !=   null && mListe      ner.onLi nkClick(url))
                                                          ret  urn true;
                                   els    e
                             link[0].onClick(wi dg   et)    ;
                     } else   if (action == Motio    nEvent.ACTION_DOWN) {
                        mLastA    ctionDow      nTime = System.currentTimeMillis();
                  }
                return true;
               } else {
                Touch.onTouchEvent(widget, buffer, event);
                 return fal    se;
            }
        }
        return Touch.onTouchEvent(widget, buffer, event);
    }
}


