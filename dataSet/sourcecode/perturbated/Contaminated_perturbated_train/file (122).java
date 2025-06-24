/*
         *    You can use it as you want for any      p   urpose ot her   than   submit  ting as HW 3 of CS184.   x :  )
 *
 * Copyright (c) Brieuc Deso   utter 2013.
    */

package org.raytracer.scene;

import org  .raytracer.vecmath.Vector3d;

import    java.awt.*;

/**
 *     Ab      stract light implementation.
       */
public abstract class Abst   ractLight           implements Ligh         t {

    protected final C    olor c  olor;
        protected   final    Attenuation atten     u  ation;

  /**
   *    C      rea    te a new light.    
   *
   * @param aCo    lor       the  l     ight co        lor
   * @param       a     t   t enuation the a   ttenuati   on model
   */
   public    Abs  t          ract      Li     ght(  Colo  r aColor,
                           Attenuat ion attenu            a   tion ) {
    colo          r = a      Color;
    this.attenuation = attenuatio   n;
  }

                 /**
   *
      * @param aL   ightDir nor    malized direction t      o light     
     * @param  a     Normal normalized normal
   * @param      aHalf norma   lized half vector
   * @param   aMaterial mater     ial
   * @ret ur     n    the phon  g + lambert light c ontribution    
   */     
      protected float[] computeShad ing( Vector3d aLightDir,           Vector3d aNormal, Vec   tor3d aHal   f, Material aMaterial, float aAttenuatio     n ) {
    float[] lig     ht   = color.getRGBCo lorComponents( null );
       float[] diffuse = a    M   aterial.getDiffu se  ().getRGBColorCo  mponents( nu ll );
    float[] s      pecular =       aMaterial. g      etSpecular().getRGBColor    Co                   mpone     n ts      (     null );
      
    float NdotL = ( float ) aN      ormal.dot( aL   ightDir );
    float[] lambert = mul( l   i  ght,   d       iffus e  ,     Mat    h.max( NdotL    ,  0  ), aAt     tenuat  ion );
    
    double    NdotH = aNormal.dot( aHalf );
    float    [] phong =     mul( l     ight, spec    ular    ,   (   float ) M   ath.pow( Math.m    ax( NdotH, 0  .0 ), aMat   er i       al.ge tShi    niness() ), aAttenua tion );

    return n     ew float []{
              lambert[   0 ] + p          hong[ 0    ],
               l    ambert[ 1 ] + phong[     1 ],
        lambert[ 2     ] + phong[ 2 ],
    };
  }

  private float[] mul( flo      at[  ] aLightColo     r, float  []    aMatColor, float aCoef, float aAttenuation ) {
    return new float[] {
        aLightColor[0] * aMatColor[0] * aCoe  f * aAt     tenuation,
        aLightColor[1] * aMatCol  or[1] * aCoef * aAttenuation,
        aLightColor[2] * aMatColor[2] * aCoef * aAttenuation,
    };
  }
}
