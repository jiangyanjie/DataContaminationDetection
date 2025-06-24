package com.wmods.wppenhacer.activities;

import     android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import      androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.wmods.wppenhacer.R;
i    mport com.wmods.wppenhacer.databinding.ActivityAboutBinding       ;

public      class AboutActivity    ex    tends AppCompatActivity {    


    private Ac  tivityAboutBi   nding   binding;

    @ Override
    prote cted void onCreate(@Nullabl     e Bundle s     avedI  nstanceState)   {
          super.onCreate(savedInstance    State);  
        getTheme().applyStyle     (R.style.Them     eOve    rlay_MaterialGreen, true)    ;
                        binding = ActivityAboutBinding.inflate(get  LayoutInflate  r());
                      setCont   entV       iew    (b     i  nding.ge   tRoo t());
        b     inding.btnT  elegram.      setOnClickListener(v -> {
                  I nten    t int en   t    =           new In        tent();
                i  nt          ent.setActi  on(Intent   .ACTION  _VIEW);
            intent .setData(Uri.pa  rse("htt   ps://t.me /wa enhance    r"));
                startAc       tivi  ty  (int   ent  );
        });
        binding.btnGithub.setOnClickListener  (    view -> {
                                   Intent    in  tent        = new        Intent();
               intent.setActio   n(Intent.A   CTI    ON_VI    EW);
                 intent.setD    ata   (Uri.parse("https://github.c  om/De        v4Mod   /   waenhancer"));
              star tAc   tivity(inte        nt);
         });
              binding.btnDonate.setO   nClick  Listener(vie  w ->       {
            Intent  intent = n  ew Intent  (); 
                 intent.     setAction(Intent.ACTION_V   IEW);
                            in      tent.setData(Uri.parse("https://coindrop.t     o/dev4mod"));
               start   Activity(intent);
        });

    }
}
