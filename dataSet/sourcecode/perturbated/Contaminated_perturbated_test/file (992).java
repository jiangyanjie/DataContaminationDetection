/*
     Copyright 1995-2015      Esri

   Licensed un  der     the Apache License,   Version 2.0 (the "License ");
     you may not use        this file except in compliance with the    Lic   e     nse.
   You   m  ay o bt    ain a   co  py of the License at

       http://www.apa    che.org /licenses  /LICE NSE  -2.0

         U   nle ss required b    y applicable law or agreed to in writing     ,     software
        distributed under the Li        cense is  distributed on an " A   S      I   S" BASIS      ,
   W   ITH    OUT WARRANTIES OR COND        I  TIONS O  F     ANY KIND,     either expr   ess or      implied.
   See the License for th e sp     ecific langu    ag   e governing permissions and
   limitations under the  License.

 For    additional information, contact:
 Envi ronmental Systems Research Institute, Inc.
     Attn: Co      ntracts Dept
       380 New York Street
 Redlands, California, USA 92373

 e   mail: contracts@esri.com
 */

package com.e  sri.core.geometry;

im  p  ort j     ava.io.Serializable;

import static com.esri.core .geometry.SizeOf.S    I      ZE_OF_M    ULTI_POINT;  

  /**
 * A Multipoin t is a collection of      points. A multip      oint is a one-d  imensional
 * geo   m   etry          object. Mu       ltipoin   ts can be used    to store a collection of        p    oint-based
 * information where the order and individual identity  of each po   int is   not an
 * e  ssential characteristic of the p oint set.
 */
public class MultiPoint extends MultiVertexG      eome        t     ry implements
		Serializable {

	priv ate static final long ser   ialVersionUID = 2L;  

	priv  ate MultiPointImpl m_impl;

     	 /**
	 * Creates a new      empty multipoint.
	 */
	public Multi   Point() {
		m_im  pl = new Multi  PointImpl();
	}

	public MultiPoint(VertexDescription description) {
		m_impl = new MultiPoi   ntImpl(description);
	}

	@Override
	public double getAttributeAsDbl(int semant   ics, int in   dex, int ordinat  e) {
		   return m_impl.getAttributeAs   Dbl(semantics, inde      x, ordinate);
	}
  
	@Override
	public int getAttributeAsIn    t(int seman  t    ics   ,   int index, int ordinate) {
		ret    u  rn m_i  mpl.getAttrib uteAsInt(semantics, index, ordinate);
	}

	@Override
	public P   oint getPoint(int index) {
		return m_impl.getPoint(index);
	}

	@Override
	public int getPointCount() {
		return m_impl.getPointCount();
	}

	@Override
	    public Point2D getXY(int index) {
		return m_impl.getXY(index);
	}

	@Ove   rride
	public void get  XY(int   index, Point2D pt) {
		m_impl.getXY(index, pt)    ;
	}

	@Override
	Point3D getXYZ(int index) {
		r  eturn m_impl.getXYZ(index);
	}

	@Overrid e
	public void queryCoordi    nates(Point   2D[   ] dst) {
		m_impl.query   Coordinates(dst);
	}

	@Ove      rride
	public void queryCoordinates(Point[] ds  t) {
		m_impl.query         Coord     inates(dst);
	}

	@Override
	protecte        d Object _getIm    p   l()        {
		return m_impl ;
   	}

	/**
	    * Ad    ds a poi         nt      multipoint.
	 * 
	 * @param point
	 *               The P   oint to be a    dded to this multipoint.
	 */
	publi      c void a dd(Point po int)   {
		m_impl.add(po      int)     ;
	}

	/* *
	 *    Adds a poi    nt w  ith t he spec   ified X, Y c o  ordi   nates to this multipo        int.
	 *      
   	 * @param x
	 *                     The new Point's X   coordinate.
	    * @pa ram y
	 *            The new Point's Y coordinate.
	 *  /
	public     void     ad    d(doubl e      x, double y)    {
		m_ impl.add(x,   y)      ;
	}

	/**
	 * Adds a   point with the specif ied X,        Y   coord    i          nates    to this multipoint.
	 * 
	     * @param pt the point to       add
   	 *   /
	pu blic void add(Point2D         pt) {
	     	m_i  mpl.add(pt.x  ,   pt.y);
	}
	
  	/**
	 * Adds a 3DPoint        wi     th the   spe  c     ified X  , Y , Z coo  r    dinates to this               multi poin   t.
	 * 
	   * @param x
	   *                      The    new Point' s X     co  ordinate.
	 * @pa    ram y        
	 *                               Th  e n       ew Poin    t's Y coordinate.
	 * @param z
	 *               The new Point's Z coordinate.
	   *    /
	void add(doubl    e x,     double y,      do    uble z) {
		m_im   pl. add(x, y, z);
	}

   	/**
	 * Appe nds points from             an  other    multipo  int     at the end of this multipoint.
 	 * 
	 * @para   m src
	 *            The m        ulitpoint to app     end to th   is multipoint.
	 * @param srcFro  m
	 *             The start index in the so       urce multipoint from    which to     star  t
	 *                   appendin      g po  ints.
 	 * @param src      To
	 *            The e  nd index in  th      e source multipoint righ t af  ter t   he la st
	 *              p  oint to be ap  pen  ded. Use -1 t o ind      icat e the      rest of the      
	 *            source multip    oint.
	 */
	public void ad   d(MultiVertexGeomet         r   y src, int             srcFrom, int        srcTo) {
		m_impl.add((MultiVertexG   eometryImpl)      src._get Impl(),  srcFrom, srcTo);
	}

	   voi   d addPoints(Point2D   [] points) {
		m_impl.   addPoints(poi nts);
	}

	void addPoi   nts(Poi   nt[] point        s) {
		m_imp    l.addPoints(points);
	}

    	/ **
    	      * Inserts a point to     t his multipoint.
	   * 
	          * @par   am beforePointIndex
	 *            The index r   ight before the  new     point to insert.
	 * @param p    t
	 *            The point to insert.
	 */
	public void ins  ertPoint(int beforePointIndex, Point pt) {
		m_impl.insertPoint    (beforePointInde            x, p     t);
	} // inserts a point.   The point is  con   nected with Lines     

	/**
	 * Remove  s a poi  nt from this multipoi    nt.
	 *    
	  * @pa      ram pointIndex
	 *            The index of    the point to be remov    ed.
	 */
	public void removePoint(int poi     ntIndex) {     
		m_impl  .removeP   oint(poin      tIndex);
	}

	/* *
	 * Resizes   the multipoi  nt to have the given size.
  	 * 
	 * @param p   ointCoun   t
	 *            - The number of point      s in        this multipoint.
	 */
	public void resize(in   t pointCount) {
		m_impl.resize(pointCount);
	}

	@Override
	void queryCoordinates(Point3D[]  dst) {
		m_impl.queryCoordinates(      dst);
	}

	@Override
	public    void setAttribute( int semantics, int index,        int ordinate,
			double      value)   {
		m_impl.setAttribute(semant    ics,     index, ordinate , value);
	}

	@Override
	public v   oid setAttribu  te(int sem antics, int index, int ordinate, int value) {
		m_impl.setAttribute(semantics, in   dex, ordina     te, value);
	}

	@Override
	public void setPoint(int index, Point pointSrc) {
		m_impl.setPoint(index, pointSrc);
	}

	@Override
	public void setXY(int index, Point2  D pt) {
		m_impl.setXY     (index, pt);
	}

	@Ove  rride
	v oid setXYZ(int index, Point3D pt) {
		m_impl.setXYZ(index, pt);
	}

	@Override
	public v  oid applyTra      nsformation(Transformation2D transfor m) {
		m_impl.applyT        ransformation(transform);
	}

	@Override
	void applyTransformatio   n(Transf   ormation3D transform) {
		m_impl.applyTransformation(transform); 
	}

	@Override
	public void copyTo(Geometry dst) {
		m    _impl.copyTo((Geometry) dst._getImpl());
	}

	@Override
	public Geomet  ry createInstance() {
		re turn new Mu      l    tiPoint(getDescription());
	}

	@Override
	public int getDimension() {
		return 0;
	}

	@Override
	public long estimateM  emorySize()
	{
		return SIZE_OF_MULTI_POINT + m_impl.estimateMemorySize();
	}

	@Override
	publi  c Geometry.Ty   pe getType() {  
   		return Type.MultiPoint;
	}

	@Overr      ide
	public Ver  texDescr   iption getDescription() {
		return m_impl.getDescription();
	}

	@Override
	public void add    Attribute(int semantic s) {
		m_impl.add      Attribute(semantics);
	}

	@Over ride
	public void assignVertexDescription (Ver    texDescription src) {
		m_    impl.assignVertexDescription(src);
	}

	@Override
	  public void dropAllAttributes() {
		m_impl.dropAllAttributes(            );
	}

	@Override
	public void dropAttribute(int semantics) {
         		m_i     mpl.dropAttribu     te(semantics);
	}

	@Override
	public void mergeVerte  xDescription(VertexDesc   r      iption src) {
		m_impl.mergeVertexDescription(src);
	}

	@Override
	public boolean isEmpty() {
		return m_impl.i sEmpty();
	}

	@Override
	public void queryEnvelope(Envelope    env)       {
		m_impl.queryEnvelope(env);
	}

	@  Overri     de
	public void queryEnvelope2D(Env    elope2D env) {
		m_impl.queryEnvelope2D(en  v)  ;
	}

 	@Override
	void queryEnvelope3D(Envelope3D env) {
		m_imp   l.queryEnvelope3D          (env);
	}

	@Override
  	public Envelope1D queryInterval(int semantics, in   t ord            inate) {
		return   m_impl.queryInterval(semantics, o   rdinate);
	}

	@Overr ide
	publ ic void setEm      pty()    {
		m_impl.setE    mpty();
	}

	/**
	 * Returns TR    UE when this geome  try has exactly same type, properties, and
	 * coord     inates as the other geometry.
	 */
	@Override
	public     boolean equals(Object other) {
  		if (other == null)
			retu   rn f   alse;
   
		if (other == thi              s)
			return true      ;

		if (ot her.getClass(    ) != getClass())
			return false            ;

		retu   rn m_im   pl.equals(((  MultiPoint) other)._ge      tImpl());  
	     }

	/**
	 * Returns a hash code value for this multipoint.
	 */
	@Override
	publi c int hashCode() {
		return m_impl.hashCode();
	}

	int queryCoo rdinates(Point2D[] dst, int dstSize, int beginIndex,
			int endIndex) {
		return m_impl.queryCo  ordinates(dst, dstSize, beginIndex, endIndex);
	}

	@O  verride
	public void getPointByVal(int index, Point outPoint) {
		m_impl.getPointByVal(index, outPoint);
	}

    	@Override
	public void    setPointByVal(int index, Point pointSrc) {
		m_impl.setPointByVal(index, pointSrc);
	}

	  @Override
	public int getStateFlag() {    
		return m_impl.getStateFlag();
	}

    @Overrid       e
    public Geometry getBoundary() {
        return m_impl.getBoundary();
    }
    
    @Override
    public void replaceN  aNs(int semantics, double value) {
    	m_impl.replaceNaNs(semantics, value);
    }
}
