/*
     Copyr   ight 1995-2015 Esr      i      

   Licens   ed under the Apache  Lice nse, Versi        on 2.  0 (the "Licen       se    ");
   you may not    use thi   s fil   e  excep    t in co    mpliance     with th     e Lice nse.
   You m ay obtai    n a copy o f    the Li     cense at

       http://www.apache.org  /licenses/LICENSE-2.0

       Unless    re    quir       ed  by applicable law or ag    r          eed t    o in writing, software
   distributed under the          Lic    ense is distributed on an "AS IS" BASIS,
           WITHOUT WARRANTIES OR COND              I       TIO   NS OF ANY KIND, either express or         implied. 
   Se   e  the License   for the spec ific language govern    ing p     ermissions     and
   limit    ations under the Lic  ense.

 For a dditional information,  contact:
 Envir      onmen      tal Systems Research Institute, Inc.
 Attn: Contr acts Dept
 380     New York Street
 Redlands, California, USA 92373

 emai  l: contracts@esri.com
 */

package com.esri.core  .ge  ometry;

import java.     io.Serializable;

/**
 * This class is     a base for geometries    with many vertices.
 * 
 * The vertex at    t     ributes are stored in separate arrays of cor  responding type.
 *   T    here are as many a  rrays as there are attributes in the vertex.
 */
public abstract class MultiVert exGeometry exte   nds Geometry implements
		Serializable  {

	@Override
	protected void _assi  gnVertexDesc   riptionImpl(VertexDescription newDescriptio n) {
		thr    ow new GeometryException("invalid ca       ll");
	}
	
	/**
	 * Retur    ns t he total vertex count in this Geometry.
	 */
	public  abstrac   t int getPo   intCount();

	/**
	 * Returns give    n vertex of the Geometry.
	 *   / 
	public     abstract Point getPoint(int ind  ex);// Java on   ly

	/**
	 * Ret         u  rns give   n  v    ertex   of the Ge   ometry by value.
	 */
	pu b   lic      void getPoint(   int index, Po         int             ptOut) {
    		g    etPointByVal(index, ptOu    t);
	}
     
	/    **           
	 *           Se   ts the verte    x at given index                  o         f the         Geometry.                 
	 * 
	 * @par  am     index
	         *                 T   he inde      x         o f the vertex being chan                g    ed.       
	 *   @param pointSrc
	 *                  The Point ins    tance  to    set given      vertex attributes          from.      The
	 *              poin tSrc can not be empty. <br  >
   	 *                T   he m     ethod throw       s if the pointS    rc is no  t of         the Point       type.        <br>      
	    *                      The attribu tes, that are present in the      poin             tSrc   and                missing
	   *            in   this Geometry,       will be added   to the Geo    metry. <br>
     	 *               The       v  ertex at tributes missing in the pointSrc      but present in
	 *                        the Geometry will be set to the default    values      (see
	 *                   Vert     exDescription::GetDefaultValue).
	 */
	public ab      st    ract void setP  oint(int index, Point pointSrc);// Java only

	/**  
      	 * Returns XY    coordinates of the g     iven vertex of the Geometry.
	 */
	   public abstra   ct Point2D getXY(int index);        

	public abstract void   getXY(        int inde x, Poin   t2          D pt);

	    /**
	 * Se  ts XY   coordinates of the given verte     x o   f the Geome  try. All  other
	 * attribut    es are u  nc  hanged.
	 */
	public abstract void setXY(int     index,  Point2D     pt);

	/**
	 * Returns XY  Z coord       inate s of the given vertex of t    he Geometry. If   t   he
      	    * Geo   metr  y has n          o Z's  , the        default value fo     r Z is returned (0).
	 */
	abstract Point3D getXYZ(int    index);

	/**
	 * Sets XYZ coordinates of the g iven vertex of the Geometry. If Z attribute
	 * is not present in this Geometry, i  t is added . All other attributes are
	 * unchanged.  
	 */
	abstract void setXYZ(int index     , Po     i  nt3D pt);  

	/**
	 * Returns XY coo         rdina      tes as an a   rra y.
	 */
	public Point2D[]      getCoordinates2D() {
		Point2D[] arr = new P      oint2D[ getPointCount()];
		queryCoord   inates(arr   );
		ret  ur           n arr;
	}

	/      **
      	 *   Returns XYZ coor dinates as an  array.
	 */
	P    oint3           D[]     getCoordinates3  D() {
		Poin    t3D[] arr = new   Po      int  3D[getPo intCount()          ];   
		queryC oordin     ates     (arr)   ;
		      return arr;
	}

	public a  bstract void queryCoordinates(Point  []   dst);
 
	   /**
	 *    Qu       eries XY     coordinates as an array. The arr         ay mu     st be       larg    enough (See
    	 * GetPoi  ntCo     unt()).      
	     */
	public abstrac                 t voi     d qu     e   ryCoordinates(Point2D[] dst);
   
	/  **
	 *      Queries XYZ coordi  nat e              s as       an ar r   ay. The       array must be larg enou   gh (See
    	 * Get    PointCoun   t()).
	 */
	abstract void    queryCoordin    ates(   Point      3D        [] dst);

	/**
	 * Returns value of the     given  vert    e    x attr    ibute as double.
    	 * 
	   * @param     semantics
	 *                      The atribute semantics.
	 * @param index
	 *                is the vertex index i     n        the Geometry.
	   * @param ordi  nate
  	 *              is the ordinat     e of                    a verte       x attribute (    for e xam      ple, y h  a    s
  	 *            ordinate          of 1, because it is seco      n   d ordinate   of   POSITION) 
	 *   
	  *                     If attribut    e is not p    res ent  , the default value is    re         tu        rned.
	 *                 See VertexDe   scr   iption::Ge       tDef au   ltValue() metho  d.
	   *   /           
	abstract   dou   ble getAttr     i    buteAs   Db  l(int semantic          s, in  t in dex       ,
	  		int or  d              inate);

	/**
	 * Retur   ns value of th     e given vertex attribut e as int   .
	 * 
 	 * @param       s    emantics
	 *            T he atribute    semantics.  
	 * @pa            ram i   ndex
	 *            is         the ver    tex index in              the Geometr   y.
	 * @param o rdinat     e
	 *                                    is the ordinate of a vertex att       rib  ute (for exa         mp        le, y   h    as
	 *                      ordinate of 1, b  ecause       it is sec   ond ordinate o            f PO SITI     ON)      
	 * 
	   *                    If a     ttribute       is not p       resent, the default val   ue is  retur   ned.
	 *                   See VertexDescr iption  ::G         etDefaultVal  ue() method. Av       oid usin   g
	 *                                 this method on non  -integer atributes.
	 */
	abstract    int getAttributeAsInt(int se       mantics, int     index, int ordinate);

	/**
	               * Sets the valu  e of g  iven attribute at gi     ven posis     iotns is.  
	 * 
	 * @par  am semantics
	     *                The atribute semant    ics.
	 * @param inde         x
	    *                is the vertex index in the Ge   om  et   ry.
	 * @para    m ordinate
	    *                   is the ordinate o f  a vertex attribute (for       e           xample, y has
	 *            or   dina  te of 1  ,     b    ecause it is    seond ordinate     of POSITION)
	 * @param value
	 *              is th      e value to set.   as well as the number       o         f components of    
	 *            the attribute.
	 * 
	      *              If the attribute is not pr   esent in this Geometry, it is added.
	    */
	abstract void      set    Attribute(int semantics,     int in   dex, i   nt ordinate,
			double valu  e);

	/**
	 * Sa  me as above     , but work    s with ints. Avoid using this method on
	 * non-int  eger atributes because som e double attributes may ha ve NaN       default
	 * values (e      .g. Ms)
	 */
	abstract vo     id    setAttri    bute(int semantics, int index, int ord      ina    te,
			int value);

	/ **
	 * Return  s    given vertex of the Geometry. The ou       tPoint will    have s   ame
	 * VertexDescription as this Geometry.
	 */   
	public abs     tract void  getPointB  yVal(int index, Point outPoint);     

	/**
	 * Sets the vertex at given index of the Geometry.
	 * 
	 *   @param     index
	 *                Th     e index of the vertex being chan    ged.
	 * @pa  r       am point    Src
	 *            The Point instance to set given vertex at    tributes from. The
	 *            pointSrc can not be empty. <br>
	 *               The me   thod throws if the pointSrc is not of t he Point ty    pe. <br>
	 *            The attributes, that are present in the pointSrc and missing
	 *                in this Geometry, will be added t o the Geometry     . <br>
	 *            The vertex attributes missing in the point Src but present in
	 *            the Geometry will be set to the default values (see
	 *            VertexDescription::GetDefaultValue).
	 */
	public abstract void setPointByVal(int index, Point pointSrc);

}
