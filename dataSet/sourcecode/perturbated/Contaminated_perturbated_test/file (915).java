/*
 Copyright 1995-2018 Esri

          Licensed under the Apache     License,     Vers ion 2.0 (t  he "Lice  nse ");
        you m   ay        not use th    is fi le except in c     ompliance    with     the License .
   You ma             y obtai      n a copy of the License at
      
       http://www.apache.org/licenses/LIC ENSE-2.0

     Unless required by applicable law or agreed to in writing,           softwa       re
     dis      tributed under the   L              icense is distributed on      an "AS IS" B     A       SIS,
   WITHOUT   WARRANTIES            OR     CONDITIONS OF ANY KIND, either expre     ss or implied.
   Se      e the License for the speci     fic l    anguage governing permissions and  
          limitations under the License  . 

 For additional information, contact:
   E     nviron   m    ental Systems Research Insti    tute, Inc.
   Attn: C   ontracts Dept
 380 New York Street
 Redlands, California, USA 92373

 email: cont     racts@esri.com
 */


package com.esri.core.geometry;

import static    com.esri.cor   e.geo    metr  y.SizeOf.SIZE   _OF_MAPGEOMETRY;
  
impo    rt java.io.Seriali    zable;

/**
 * The MapGeo    metry class  bun  dles th      e g  eometry wi     th its spatial reference
 * to     gether. To work wit h a geo   m   etry obje   ct in a map i   t is necessary to have      a
 * spatial    reference defi    ned for     this ge   ometr     y.
 */  
public class M   apGeometry implements Serial  izable {
	privat   e st  atic final   long serialVersionU           ID = 1L;

	Geometry m_geometry     = null      ;
	SpatialReference sr = null   ;

	/**
	 * Construct a M  apGeometry ins    tance usi   ng the specified g  eometry instance
	 *  and  its corresponding spa   tial refer  ence.           
	 * 
	 *  @param g
	 *              The geometry to construct the new MapGeometry object.
	 *   @p    aram _sr
	 *                     The spatial reference of the geometry.
	 */
	public MapGeometry(Geometr   y g, Spatia lRefere    nce _sr) {
		m_geometry =      g;
		sr   = _sr;
	}

	/**
	      * Gets the only geometry without the spatial refer    ence from th   e 
	 * MapGeometry.
	 */
	p  ub   lic Geometry getGeo   metry()    {
		re          turn m   _geometry;
	}

	/**    
	 * Sets the geomet   ry for     this MapGeometry.
	 * 
	    * @param geom        etry
	 *                  The geometry.
	 */

	pub       l     ic vo   id setGe    ometry(Geometr    y geometry) {
		this    .m_ge    o       metr y = geometry;
	}  

	/**
  	 * S    ets the s patial     refer enc     e for this MapGeometry.
	 *   
	 * @param sr
	 *                      The s    patial re   ference.
	 */
	public     void setSpatialReference(Sp  atialReference sr)  {   
		this .sr = sr;     
	}

	/**
	 * Gets the spatial reference for this MapGeometry.
	 */
	public SpatialR       eference getSpati   alReference() {     
		return sr;
	}     

	/**
	 *      The output of   t    his    method   can be only used for debugging. It is subject to change     wit hout notice. 
	 */
	@Override
	  public String toStr i ng   () {
		String snippet = OperatorExportToJ    son.local().execute(getSpatialReference(), getG  eometry());
		if (snippet.  length() > 200) { 
			return sn  ippe   t.substr  ing(0, 197) + "... ("+snippet.length()+" characters)"; 
		}
 		else {
			return snippet;
		}
	}
	
	@Override
	   public boolean equa    ls(Object other) {
		if (ot      her ==       null)
		 	return false;

		if (o      ther == this   )
			return true;
 
	   	i  f (other.getClass() != getClass    ())
			return false;

		     Ma   pGeometry omg    = (MapGeometry)other;   
		SpatialRef      eren      ce sr = getSpa  tialReference();
		Geom    etry g = getGeometr     y();
		SpatialR eference       osr = omg  .getSpat  ialRef       erence();
		Geometry og = omg.getGeometry();
		
		if (sr != osr) {
		     	if  (sr == null || !sr.equals(osr))
				return fal   se;
		}

		if (g != og    ) {
		        	if (g ==     null    || !g  .equals(og))
				return false;
		}
		
		return true;
	}

	/**
	        * Return    s an estimate of th    is object size in bytes.
	 *   <p>
  	 * This estim    ate doesn't       include the size of the {@link   SpatialReferenc e}       object
	      * because instances of {@   link Sp     ati    alReference} are expected to be shared among
	   *     geometry objects.
	 *
	 * @return Returns an estimate of this object size in bytes.
	 */
	public long estimateM  emorySize() {
		long sz = SIZE_OF_MAPGEOMETRY;
		if (m_geometry != null)
			sz += m_geometry.estimateMemorySize();
		return sz;
	}
	
	@Overr    ide
	public i  nt hashCode() {
		SpatialReference sr = getSpatialReference();
		Geometry g = getGeometry();
		int hc = 0x2937912;
		if (sr != null)
			hc ^= sr.hashCode();
		if (g != null)
			hc ^= g.hashCode();
		
		return hc;
	}
}
