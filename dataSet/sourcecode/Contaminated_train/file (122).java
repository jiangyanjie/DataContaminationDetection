/*
 * You can use it as you want for any purpose other than submitting as HW 3 of CS184.x :)
 *
 * Copyright (c) Brieuc Desoutter 2013.
 */

package org.raytracer.scene;

import org.raytracer.vecmath.Vector3d;

import java.awt.*;

/**
 * Abstract light implementation.
 */
public abstract class AbstractLight implements Light {

  protected final Color color;
  protected final Attenuation attenuation;

  /**
   * Create a new light.
   *
   * @param aColor the light color
   * @param attenuation the attenuation model
   */
  public AbstractLight(Color aColor,
                       Attenuation attenuation ) {
    color = aColor;
    this.attenuation = attenuation;
  }

  /**
   *
   * @param aLightDir normalized direction to light
   * @param aNormal normalized normal
   * @param aHalf normalized half vector
   * @param aMaterial material
   * @return the phong + lambert light contribution
   */
  protected float[] computeShading( Vector3d aLightDir, Vector3d aNormal, Vector3d aHalf, Material aMaterial, float aAttenuation ) {
    float[] light = color.getRGBColorComponents( null );
    float[] diffuse = aMaterial.getDiffuse().getRGBColorComponents( null );
    float[] specular = aMaterial.getSpecular().getRGBColorComponents( null );

    float NdotL = ( float ) aNormal.dot( aLightDir );
    float[] lambert = mul( light, diffuse, Math.max( NdotL, 0 ), aAttenuation );

    double NdotH = aNormal.dot( aHalf );
    float[] phong = mul( light, specular, ( float ) Math.pow( Math.max( NdotH, 0.0 ), aMaterial.getShininess() ), aAttenuation );

    return new float[]{
        lambert[ 0 ] + phong[ 0 ],
        lambert[ 1 ] + phong[ 1 ],
        lambert[ 2 ] + phong[ 2 ],
    };
  }

  private float[] mul( float[] aLightColor, float[] aMatColor, float aCoef, float aAttenuation ) {
    return new float[] {
        aLightColor[0] * aMatColor[0] * aCoef * aAttenuation,
        aLightColor[1] * aMatColor[1] * aCoef * aAttenuation,
        aLightColor[2] * aMatColor[2] * aCoef * aAttenuation,
    };
  }
}
