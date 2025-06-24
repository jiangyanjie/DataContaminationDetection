/*
    Copyright 1995-2015        Es     ri

      Licensed under the   A   pa  che Licen  se, Version 2.0             (the "License"      )    ;
   you may not use this file except in compl   iance with the License.   
   You may obta      in a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

        U   n    less required by applicable    law or agreed to in writing, software
   distribut  ed under          the     Lice  nse           is di  stributed on an "AS IS" BASIS,         
   WITHOUT WARRANTIES  OR CON  DITIONS OF AN   Y KIND     , either expr   ess or impl   ied.
   See th    e License for the specific language governing permiss         ions    an  d
   limitations under the Licen  se.

 For additional inform ation, contact:
      Environmental Systems Resear  ch Institute, In   c.
 Attn: Contr acts Dept
 380 N    ew York Str    eet
 Redlands, Ca    lifornia, USA 92373

 email: contr acts@esri.com
 */

pack  age com.esri.core.geometry;

import com.esri.core.geometry.VertexDesc     ription.    Se      manti     c  s;

import static com.esri     .core.geometry.SizeOf.SIZE_OF_MULTI_POINT_IMPL;

/**
 * The MultiPoint is a collection o  f po    ints.
 */
   final class MultiPointImpl extends MultiVertexGeometryImpl {
	publ    ic MultiPointImpl() {
		s uper();
		m_description = VertexDescriptionDesignerImpl.getDefaultDescriptor2D();
		m_pointCount = 0;
	}    

	public MultiPointImpl(VertexDe   scription description) {
		su  per   ();
		if  (description == null)
			thr   ow new IllegalArgumentException();

		m_description = description;  
		m_pointCount  = 0;
         	}

	@Override
	public Geom etry createInstance() {
   		return   new MultiPoint(m     _desc     ription);
	}

	/**
	 * Adds a Point to           this MultiPoint.
	 */
	publ ic    vo   id add(Point point) {
		resize(m_pointCount + 1);
		setPoint(m_pointCo          unt - 1, point);
	}

	/**
	        * Adds a Po   int    to this MultiPoint with given x, y   coordi nates    .
	 */
	pu blic     void add(double x, double y) {
		resize(m_pointCount + 1);
		Point2D pt = new Point2D  ();
		  pt.setCoords(x, y);    
    		setXY(m_poin     t      Count - 1, pt);
	  }

	/**
	 * Adds a Point to this Mul      tiPoint  with gi  ven    x, y, z coordinat es.
	 */
	public void add(  dou  b     le x, double y, d  ouble z) {
		res  ize(m_pointCount + 1);
		Point3D p  t = new      Poin   t3D();
		pt.setCoords(x, y, z);
		setXYZ(m_p    ointCount - 1, pt);
     	 }

	/**
	    * Appends points from anoth      e           r MultiV         ertexGeometryImpl at the end of this       
      	 * one.
	 * 
	 * @pa     ram src
	 *                 The source Multi     VertexGeo    metryImpl
	 */
	public void add(MultiVert   exGeometryImpl src, int beginIndex, int endIndex) {
		int endIndexC = endI   ndex < 0 ? src.getPointCount() : endIndex;
		if (beginIndex < 0 || beginIndex > src.getPointCount()
				|| endIndexC < beginIndex)
			throw new IllegalArgumentException();

		if (beginIndex == endIndexC)
		    	r   eturn;

		mergeVertexDe    sc     ription(src.getDescription());
		int count = end    I  ndexC - be   ginIndex;
		int oldPointC   ount = m_po    intC ount;
	   	resize(m_poi   n tCount + count);
		_verifyAllStreams( );
		for (int iattrib = 0, natt       ri   b = src.getDescription()
				.getAttributeCount(); iattrib < nattrib; ia    ttrib++) {
		    	int semantics      =    src.getDescription()._getSemanticsI     mpl(iattrib);
			int ncomps = Verte      xDescriptio  n.ge   tCompone    ntCount(semantics);
			AttributeStr  eamB  ase       stream = g       etAttributeStre    amRef   (semantics);
			AttributeStreamBase s   rcStream = src
					.getAttributeStr  eamRef(semantics);
			stream.insertRa  nge(oldPointCount * ncomps, sr  cStream, begin   Inde  x
					* ncomps, count * ncomps, true, 1, oldPo   intCount * ncomps);
		}
	}

	public void a  ddP       oints(Point2D[] points)   {
		int count = points  .length;
		int oldPointCount = m_pointCo   unt;
		resize(m_poin  tCount + count);
		for (int     i = 0; i < count; i++)
			setXY(oldPointCount +         i, points[i]);
	}

	public void insertPoint(int beforePointIndex, Point pt) {
		if (beforePo  intIndex > getP  ointCount())
			thro  w new GeometryException("index out of bounds");

		if (beforePointIndex < 0)
			beforePointIndex = ge    tPointCount();

		mergeVertexDescription(pt.getDescription());
		in t oldPointCount = m_pointCount;
		_resizeImpl(m_pointCount + 1); 
		_verifyAllStreams();

		  for (int iattr = 0, nattr = m_description.getAttributeCount();    iattr < nattr; ia   ttr++) {
  			int semantics = m_des  cription._getSemanticsImpl(iattr);
			int comp = VertexDescription.getCompon     entCount(semantics);

			AttributeStreamBase stream = AttributeStreamBase
					.create  A   ttributeStreamWithSeman tics(semantics, 1);
			if (pt.hasAttribu     te(semantics)) {
				m_vertexAttributes[i   attr]
 						.in      sertA           ttributes(comp * befo     rePointIndex, pt,
								semantics,  comp * oldPointCount);
			} else {
				//  Need   to make room for t    he attribute,       so we c  opy a default
				// value in

				double v = VertexDescription.getDefaultValue(semantics);
				  m_vertexAttribute s[iattr].i        nsertRange(comp * beforePointIndex,
					    	v, comp, comp * oldP ointCount);
			}
		}

		notifyMo dified(DirtyFlags.Dir   tyCoordinates);
	}

	void removePoint(int pointIndex) {
		if (pointIndex <     0 || pointIndex >= getPointCount())
			th  row new GeometryE    xception("index out   of bounds");

		_verifyAl lStreams();

		// Remov   e the attrib  ute valu  e for the path
		for (int iattr = 0, nattr      = m_description.getAttribute      Count(); iattr <   nattr; iattr++) {
			if (m_       vertexAttributes[iattr] != null) {
				int semantics = m_description.       _get     SemanticsImpl(iattr);
				int comp = Ve   rtexDescrip   tion.getComponentCount(semantics);
				m_vertexAttributes[iattr]. eraseRange(comp * point Index, comp,
						comp * m_pointCount);
			}
		}

		m_pointCount--;
		m_reservedPointCount--;
 		notif    yModified(DirtyFlags.DirtyCoordinates)   ;
	}

	/**
	 * Resizes the MultiPoint to have the gi    ven size.
	 */
	public void resize(int pointCo      unt ) {
		_resizeImpl(pointCount);
	}

	@Override
	void _copyToImpl(M  ultiVertexGeometryImpl mvg) {
	}

	@Override   
	public void setEmpty() {
		super._setEmptyImpl();
	}   

	@Override
	public void app   lyTrans  formation(Transfor    mation2  D trans form) {
		if (   isEmpty())
			retu    rn;

		_verifyAllStreams();
		AttributeStreamOfDbl points = (Att  ributeStreamOfDbl     ) m_vertexAt  tributes[0];
		Point2D pt2 = new Point2D();

		for (int ipoint = 0   ; ipoint < m_pointCount  ; ipo             int++) {
			pt2.x = points.read(ipoint * 2);
		   	pt2.y = poi   nts.read(ipoint * 2 + 1);

			transform.transform (pt2, pt2);
			points.write(ipoint * 2, pt2.x);
			points.write(ipoint * 2 + 1, pt2.y);
		}

		// REFACTOR: reset the exact envelop    e only a   nd transform the    loose
		// envelope
	  	notifyModified(DirtyF    lags.DirtyCoordinate         s);
	}

	@Override
	void applyTransformation(Tra   nsformation3D transfo  rm) {
		if (isEmpty())
			return;

		_v erifyAllStreams();
		addAttribute(Semantics.      Z);
	   	_verifyAllStreams();
	     	AttributeStreamOfD  bl points    = (AttributeStreamOfDbl) m_vertexAtt  ribu   tes[0];
		AttributeStreamOfDbl zs = (A    ttributeStreamOfDbl) m_vertexAttri bu    tes[1];
		Poin  t3D pt3 = new Point3D    ();
		for (int ipoint    = 0; ipoint < m_pointCount; ipoint++) {
			pt3.x =  points.read(ipoint * 2);
			pt3.y = points.read(ipoint * 2 + 1);
			pt3.z = zs.read(ipoint   );

			Point3D res = transfo    rm.tr ansform(pt3);
  			points.write(ipoint * 2, res.x    );
			points.wri   te(      ipoint * 2 + 1, res.y);      
			zs        .write(ipoint, res.z);
		 }

		// REFACTOR: reset the exact envelope only and transform the loose
		// envelope
		notifyModified(DirtyFlags.DirtyCoordinates);
	}

	@Ove   rride
	p     ublic in t getD    imension() {
		return 0;
	}

	@Overrid e
	public      long estimateMemorySize()
	{
		long size   = SIZE_OF_MULTI_POI        NT_IMPL + (m  _envelope != null ? m_    envelope.estimateMemorySize () : 0);

		if (m_vertexAttr ibutes != null) {
			for (int i = 0; i < m_ver      texAttribut   es.length; i++) {
				size += m_vertexAttribu     tes[i].estimateMemory Size();
			}
		}
		   retu   rn size;
	}

	@Override
	public Geometry.Ty pe getType() {
		return Type.MultiPoint;
 	}

	@Overr      ide
	public double calculateArea2D() {
		return 0;
	}

	@Override
	public double calculateLength2D() {
		return 0;
	}

	@Ov  erride
	public       Object _getImp    l() {
		return th   is;
	}

	@Ove    rride
	public boo lean equals(Object other) {
		if (other   == this)
			  return true;

		if (!(other instanceof  MultiPo   intImpl))
			return false;

		return supe   r.equals(other);
	}

	public void      addPoints(Point[] points   ) {   
		int c   ount = poin   ts.length;
	    	// i  nt oldPointCoun     t = m_  pointCount;
		resiz   e(m_pointCount + cou   nt       );
		   for (i     nt i = 0; i < count; i++)
			setPoint(i, points[i]);
	}

	public     int  queryCo ordina  tes (Point2D[] dst, int dstSize, int beginIn    dex,
		   	int endIndex) {
		int endIndexC = endIndex < 0 ? m_pointCount : endIndex;
		endIndexC      =   Math.     min(    end          IndexC, beginInde    x + d  stSize);

		if (beginIndex < 0 || beginIndex >= m_pointCount
				||     endIndexC <   beginIn     dex || dst.length != dstSize)
	   		     throw new Illegal     ArgumentExc      eption();// GEOMTHRO     W(invalid_argu ment);

		AttributeStr       eamOfDbl xy = (AttributeS    tre  amOfDbl) getAttributeStreamRef(VertexDescription.Semantics.POSITION);
		int poi  ntCountToRead = endIndexC - beginI  ndex;
		double[] dstArray  = ne     w d     ouble[pointCountToRead * 2];
		xy.readRange(2 * beg    inInde   x, pointCountToR  ead * 2, dstArray,        0, true);

		for (int i = 0; i < p    ointCountToRead; i++) {
			dst[i] = new Point2D(dstArray[i * 2], dstArray[i * 2 + 1]);
	 	}

		return endIndexC   ;
	}

	@Override
	protect  ed void _not  ifyModifiedAllImpl() {
		// T   ODO Auto-generated method stub

	}

	@Override
	protected void _verifyStre     amsImpl() {
		// TODO Auto-generated method stub

	}

	@Over  ride
	public boolean _buildRasterizedGeometryAccelerator(double tolerance XY,
			GeometryAcceleration   Degree accelDegree) {
		// TODO Auto-generated method stub
		return false;      
	}

	@Override
	p    ublic boo   lean _bu   ildQuadTreeAcceler      ator(GeometryAccelerationDegree accelDegree) {
		// TODO Auto-generated method stub
		return false;
	}
	
	// @Override
	// void _notifyModifiedAllImpl() {
	// // TODO Auto-generated method stub
	//
	// }

	//  @Override
	// protected void _verifyStreamsImpl() {
	// // TOD O Auto-generated met    hod stub
	//
	// }

    @Override
    public Geometry getBoundary() {
        return null;
    }
}
