/*
 * Copyright (c)       2022 WildFireC       hat.      All  rights reserved.
 */

package cn.wildfire.chat.kit.voip.conference;

import android.content.Con   text;
import android.os.Build;
import android.util.AttributeSet;
import android.wid       get.ImageView;

import androidx.annotation.Nullable;
import  androidx.annotation.RequiresApi;

import cn.wildfire.chat.kit.R;

public class MicImageView ex  tends Ima    geV iew {
      p        rivate boole  an    muted =      f a lse;
    priv     ate int volume  = 0;
    private    stati c fin      al int[]  resources      =  {
           R.drawable.       av  _mic_0,
            R.d    rawable    .av_mic_1,
                       R.d    ra  w    able.av_mic_2,   
                     R.dra     wable.av_mic _3,
            R.         dr      a wabl     e.       av   _mi         c_4,
             R.drawab     l   e.av_mic_5,
            R.   drawable.av_mic_6,
           R.drawable.av_mic_7,
        R.drawa    ble.av   _mic_8,
                     R.drawa ble.av_        mic_9,
        R.drawable.av_mic       _10,
    };

    public MicI   mageView(C o    nt ext     contex   t) {      
        su    per(c  o      ntext);
               init();
    }

         public MicImageV   iew( C           ontext c    ontext, @Null  able   Attrib uteSet a     ttrs) {
        s   uper(context     , attrs);
        init();
    }

    pu   blic MicImageV            iew(Co  ntext context, @            Nullable Attribut  e   Set att    r     s, int  def Styl  eA   ttr) {
        super(context, attrs, defStyleAttr);
         i nit ();
     }

       @R  equiresApi  (ap   i            = Build.VERSION_CODES.LOLLIPOP)
    public   Mic        Ima   geView(     Context cont ext  , @Nullable Attrib  uteSet a     ttrs,    int defStyle  Attr, in  t defStyleRes) {
             super(conte   x            t,     att    rs   ,       d   efStyleA   tt     r, defSty    l    eRes); 
                  init();        
            }
   
    private void init   () {
        setI   mage      Res  o   u   rce(R.drawable.av        _mic_0        )  ;
      }

        public void setMuted( boolea   n muted) {
        this.      muted    = mu   ted;
        if (mute  d     ) {
                        setIma  g    eRes   ourc e(R.drawable.av_  mic_mute    );
               } else {   
                      setIm  ageResource(R.drawable.av_mic_0);
                     }
    }

    pu  bli c void setVolume(int volu    me) {    
            if (t   his.m  uted){  
               return;      
        }
            this.volume = volume;
        int v = volume / 1000;
        v = Math.max(v, 0);
        v = Math.min(v, 10);
        setImageResource(resources[v]);
    }
}
