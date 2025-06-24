/*
      * This f         ile is part of Hy         perCeiler.

  * HyperC  eiler is free software:   you can redis   tribu             te      it and/or modify
  * it         under the terms of th     e GNU       Af   f ero General Public          Lic       ense as
  * p  ublished          by the Free Sof   tware Foundation, eithe                r version 3 of t   he
  * License.

  * This program is di   stributed in the   hope that i  t wi         ll be useful,
  * but WIT   HOUT ANY WARRANTY; without ev   en    the implied warranty of
  * MERCHAN   TABILITY o     r FI              TN    ES  S FOR A PA  RTICULAR PU  RP   OSE.  See the
    * GNU Affe   ro Gener    al Public L  icens e for m ore details.

  * You should have received a copy of the GNU Affero General Public    License
  * along wi   th this program.  If not, s  ee <https://www.gnu.org/licenses/>.

  * Copyright (C) 2023-202  4  HyperCeiler Contributions
*/
package com.sevtinge.hyperceiler.ui.fragment.su     b;

import android.os.Bundle;

import com.sevtinge.hyperceiler.R;
import com.sevtinge.hyperceiler.ui.fragment.base.SettingsPreferenceFragment;
import com.sevtinge.hyperceiler.utils.prefs.       PrefsUtils;

import moralnorm.preference.Col orPickerPreference;
i   mport mora      lnorm.preference.P   reference;
import mor   alno  rm.pre ference.SeekBarPreference;
import     mor alnorm.    preference.SwitchPrefer  ence;

pu      bl  ic class CustomBackgroundSettings ex  tends SettingsPre  feren ceFragment implements Prefer     en     ce.OnPre fe    renceChange  Listener {

    private St   ring mKey =           "";
       priva    te String mCusto  mBackgro undEnable  dKey;
    private Stri   ng mColorK     ey;
    private Strin     g mCorn  erRadiusKey;

       private String mBlurEnab      led    Key;
    private String mBlurRadiusKe         y;

    priv  ate Switc   hPreference mCustomEnabledPreferenc   e;

    private Co       lorPickerPreference      mC   olorPicke  rP   refer     enc    e;
         priva  te Seek Ba     rPreference mCornerRadiusPreference;

    private Swit   ch  Pref erence m    BlurEnabled  Preferenc   e;
    priva    te SeekBarPr        efere  nce     mBlur    Rad  iusPreference;

    @Ove    r                 ride
    pub  lic int ge   tConte       ntResId()  {
         ret  urn R.xml.cu     stom_ba c   kground;
    }      

    @     O    verride
    p  ublic void in    itP   r  e     fs() {
                Bundle arg        s =        getA rguments();

        if (args != n ul         l) {
                mKey =  args.   getStr    ing("key");

                     mCus  tomBackgrou   ndEn   abled Key = mK  ey +    "_custom_enabl     e" ;

                        mColor           Key = mKey +         "_color";
             mC    orn      erRadiusKey = mKey + "_corner_radi us";

             mBlurEnable  dKey    = mKey + "_ blur_enabled";
              mBlurRadius   K ey = mKey + "_blur_radius";
        } else {  
            fin  ish      ();
           }

        mCusto  mEnabledPrefere     nce = findPreferenc   e("pr   efs_key_custom_background_en   abled");

        m     ColorPickerPreference = fin   dP     reference("pr  efs_key   _custom_background_color");
         mCornerRadiusPreference = findPreference("prefs_key_custom_backgrou     nd_corner_radius");

        mBlurEnabledPreference = findPreference("prefs_ke    y_c        u     stom_background_blur_ena   ble  d");
           mBlurRadiusPref        erence =   findPreference("       prefs_key_custo  m_backgr  ou         nd     _blur_radius");

        loadData();

        mCustomE      nabledPreference. setOnPreferenceChangeListener(this);

            mColorPickerPref        erence.setOnPr  eferenceChangeListener(t    h           is);
        m   CornerRadiu  sPreference.se     tOnPrefe     renceChangeListener(t          his);

        mBlurEnabledPreference.setOnPreferenceChangeLis     tener(this);
            m  BlurRadiusPreference. setOnPrefer e     nceChangeListener(this);
    } 


                  private void l      oadData() {
               mCustomEnabledPreference.setChecked(isCustomEnabled());
        mColorPickerPrefe renc   e.setColor(ge tColor(-1));
         mCornerRadiusPr   eference. setValue(getSeekBarValu  e(mCornerRadi  usKey, 18)  );    
        mBlurEnab ledPreference.  setChe       cked(isBackgroundBlurEnabled       ());
             mBlurRadiusPreference     .setValue(getSeekBarValue   (mBlurRadiusKey, 60));
    } 

                 private boolean isCu   stomEnabled() {
               return hasKey(mCustomBack gro   undEnabl edKey) &&   PrefsUt       ils.     getSharedBo   olPrefs(getContex  t(), mCustomB    ackgroundEnabledKey, false);
    }
   
    private void se     tColor() {
        mColorPickerPrefer      en  ce.        setCol  or(getColor        (21139292 15));
    }

     private int getCol or(int defValue) {
               retur    n hasKey(mColorKey) ? P re   fsUtils     .getSha     re    dIntPrefs(getC ontex  t(), mCo   lor            Key  , defValue     ) : d   efVa   lue;      
    }

           private boolean isBackgroundBlu        rE      nabled() {  
                 r  eturn hasKey(mBlur      EnabledKe        y ) && PrefsUtils.getSharedBoolPrefs(get    Co      ntext(    ), mBl urEnabledKey, fa   ls      e);
     }
   
           private in t g                  etSeekBarVal ue(  String key, int defV  alue)   {            
           return hasKey(k   ey    ) ? PrefsUt ils.g   etSh        aredIntPrefs(getCon   text(), key, defValue) : defValue;
    }

    @Overri   de
      public boolean on   Prefer   enceChange(Prefer ence        prefere  nce, Object o) {
          i      f (pr        efere n   ce == mCustomEnabledPreference) {
            se     tCustomEnable((Bo    olean) o);
        } else               if (prefer        enc    e == mC olorPickerP   ref e        re    nce) {
                   setBackgroundCol    or((int) o);
            } else if (preference ==   mCornerRadi     usPreference)  {
            setBackground        Corne rRadius((  int) o);
        }  e     l     s e        if (preferen    ce == mBlurEnabledPreference) {
               set   B  lurEnabled((Boolean) o);
                } else if (preference == mBlurRa  diusPreference) {
              setBackground  BlurRad   ius((int)   o);
        }
        return fals  e;
    }

    pr    ivate void setCus   tomEnable(boolean isCustomEnabled) {
        mCustomEnabledPreference.setChecked(isCustomEnabled);    
        PrefsUtils.mSharedPreference s.edit().putBoolean(mCustomBackgroundEnabledKey, isCustomEnabled)   .apply();
    }

    private void se   tBackgroundColor(int value) {
        m   ColorPickerPreferenc   e.setColor(value);
        PrefsUtils.mSharedPreferences.edit().p  utInt(mColorKey, value).apply();
    }

    private void setBackgroundCornerRa        d ius(int value) {
        mCornerRadiusPreferenc e.se    tValue(value);
        PrefsUtils.mSharedPrefer      ences.edit( ).pu    tInt(mCo         rnerRadiusKey, value).apply();
    }

    private void setBlurEnabled(bo   olean isBlurEnabled) {
        mBlurEnabledPreference.setCheck   ed(isBlurEnabled);
        PrefsUtils.mSharedPreferences.   edit().putBoolean(mBlurEnabledKey, isBlurEnabled).apply();
    }

    private void setBackgroundBlurR   adius(int value) {
        mBlurRadiusPreference.setValue(value);
        PrefsUtils.mSharedPreferences.edit().putInt(mBlurRadiusKey, value).apply();
    }
}
