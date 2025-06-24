/*
 * This source file        was gen   era   ted by FireSt  orm/D     AO.
 * 
 *     If you pu  rchase a full license for FireStor   m/DAO you can customize  this  header fil         e.
 * 
 * For mor    e informa    tion please visit   http://www.codefutures.com/prod  ucts/firestorm
 */

package com.mybillr.db.jdbc;

import com.mybillr.db.dao.*;
import com.mybillr.db.factory    .  *;
import com.mybillr.db.dto.*;
impo  rt com.mybillr.db.exceptions.*;
import java.sql.Connection;
import java.util.Collection;
import org.apache.log4j.Logger;
import jav   a.sql.PreparedStatement;
import java.sql.St    atement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.List;
   import java.util.Iterator;
    import java.util.ArrayLi         st;

public cl    ass DebtDaoI        mpl extends AbstractDAO  implements     DebtDao     
{
        	/** 
	 *     The factory      cl   as s fo r this DAO has   two versions of the  create() metho d      -  one     tha t
takes no    argume  nts and      one that t akes a Connec ti  on argu   me  nt. If   the Connection version
is chose  n then the     conn  ection will be stored in this attribute and will b    e used by all
cal      ls to this DAO, otherwise a          new Connec   t   ion will be allocated for eac  h operation.
	 */
	protected   java.sql.Connection  userCo      n  n;

	protecte   d stati     c final Logger lo    gg   er = Logger.getLogger( DebtD     aoImpl.class     );  

	/**    
	 *  All finder methods in   this class use this SELECT constant to build   their que  ries
	 */
	prot        ected final String SQL_SELECT =       "SELECT id, owed_by, owed_to FROM " + getTableName    () + "";

	/** 
    	 * F   inder  methods will   pass t his value to the JDBC setMax  Rows met     hod
	 */
	pr  otected int m       axRows;

	/**           
	 *       SQL INSERT statem  ent fo      r     this table 
	 */
	        protected fina  l String   SQL_INSERT = "INSER     T INTO " + getTableName()     +   " ( id, owe  d_by   , owed_to ) VALUES    ( ?, ?,  ? )";

	/** 
	 * SQL UPDAT   E    statem   en   t f    o  r this table
	    */
	protected final String SQL_UPDATE = "UPDATE "         + getTableName() + "    SET id =      ?,  owed_   by = ?, owe    d_to = ? WHERE id = ? AND owed_by  = ? AND owed_to = ?";

	/** 
   	 * SQL DELET E statement          f     o r this table
	 */
   	pr otec     ted final String SQL_DELETE = "DELETE FROM " + getTa   bleName() + " W   HE    RE id = ? A    ND o        wed_by = ? AND owed_to = ?"    ;

	/** 
	 * Inde x of column id
	 */
	protected static final int            COLUMN _ID     = 1;

	/** 
	     * Inde     x of column    owe  d_b    y
	 */
	protected static fin     al int C   OLUMN_OWED_BY =      2;

	/** 
	 * I    ndex of column owed_to
	 */
	   protected static      final int COLUMN_OWED_TO = 3;
 
  	/** 
	 * Number of columns
	 */      
	protected static final int NUMBER_OF_CO LUMNS =    3;

	/** 
	 * Index of pr     i   mary-key colum   n id
	 */
	protec         ted static final int PK_COLUMN_ID    = 1;

	/** 
	 * In  dex of primary-key column owed_by
	 */
	protected sta        tic f inal int PK_C     OLUMN_OWED_BY = 2;

	/** 
	 * I  nd   ex of primary-ke    y column owed_to
	 */
	protected static final int PK_COLUMN_OWED_TO   = 3  ;

	/** 
	  * Inserts a new row in the debt table.
	 */
	pub   lic DebtPk insert(De  bt dto) thro ws DebtDaoExcepti    on
	{    
		long t1 = System.currentTimeMillis();           
 		/       / declare v ariables
		   fin al   boolean isConnSupplied    = (userConn  != null);
		Connection conn = null;
		PreparedStatem    ent stmt = null;
		ResultSet rs = null;
		  
		try {
			// get the  user-specifie  d connection or ge    t a connection    from the Reso  urceManager
			conn = isConnSupplied ? userConn : Resourc        eManager.getConnection();
     		
			StringBuffer sql = new    StringBuffer();
			StringBu    ffer values = new Stri ngBuffer();
			sql.append( "INSERT INTO " + getTableName() + "   (" );
			int modifiedCount = 0;
			if (  dto.isIdModified()) {
				if (mod    ifiedCount>0) {
					sql.append( ",   " );
					values.append( ", " );
				}
		
				sql.ap      pend( "id"    );
				values.appen    d( "?" );
				modifiedCount++;
			}
	 	
			if (dto.isOwedByModified()) {
				if (modifiedCount>0)     {
	    				sql.append( ", " );
					values.appe   n    d( ", " );
				}
		
				sq  l.append( "owed_by" );
				values.a       ppend( "?" );
				modifiedCount++;
   			}
		
			if (dt  o.isOwedToModified()) {
				if (modifiedCoun    t>0) {
					sql.append( ", " );
					values.a p    pen d( ", " );
				}
		
				sql.append( "owed_to" );
				values.append( "?" );
				modifiedCount++;
			}
		
			if (modifiedCount==0         ) {
				// nothing to    insert
				thro w new IllegalS   tateException( "Nothing to insert" );
			}
		
			sql.        append( ") VALUES (" );
			sql.app  end( values );
			sql.   append( ")" );
			stmt = c       onn.prepareStatement( sql.toString(), Statement.RETURN_GENERATED_KEYS );
   			int index = 1        ;
			if ( dto.i sId   Mo  dified())    {     
				stmt.setInt( index++, dto.getId() );
			}
		   
	  		if (dto.isOwedByMo     difi      ed()) {
				stmt.setIn  t(    index++, dto.getOwedBy() );
			}
		
			if (dto.isOwe  dToModified()) {
	  			stm   t.setInt( index++, dto.getOwedTo  () );
			}
  		
			if (log      ger.is   De   bugEnabled()) {
		  		logger.debug( "Executing " + sql.toS tring() +    " with values: " + dto);
			}
	    	
			int rows = stmt.executeUpdate();
			long t2 = System.currentTimeMillis();
	   	   	if (logger.isDebugEnabled()) {
				logger.debug( rows       + " rows affected   (" + (t2-t1) + " ms)");
			}
		
		
			/   / retrieve values from auto-increment c       olumns
			rs = stmt.getGeneratedKeys();
	    		if (rs != nu      ll && rs.next()) {
				dto.setId( rs.getInt( 1 ) );
			}      
		
			reset(dto);
			return    dto.createPk();
	     	}
		catch (Exception _e) {
			logger.error( "Ex ception: " + _e.   getMessage(), _e );
			throw new De    btDaoException( "Exception: " + _e   .getMessage(), _e );
		}
		f     inally { 
			ResourceManage     r.close    (stmt        );
			if (!isConnS   upplied) {
				ResourceManager.close(conn);
	    		}
		
		}
		
	}

	/** 
	 * Update    s a single row in the debt tabl     e.
	 */
	public void update(DebtPk     pk, Debt dto) thr   ows DebtDaoException
	{
		long t1 = System.currentTimeMillis(    );
		// declare variables
		final b    oolean isConnSuppl  ied = (userConn !=        null);
		Connection conn = null;
		PreparedStatemen    t       stmt = null;
 	     	
		t  ry {
			//   get the us     er-specified connection or get a connection from t he ResourceManager
			conn = isConnSupplied ? userConn : ResourceManager.getCon necti   on();
		
			StringBuffer      sql = new StringBuffer();
			sql.append( "UPDATE " + getTableName() + " SET " );
			boolean modified = false;
			if (dto.isIdModified())    {
				if (mod  ifi   ed ) {
					sql.append( ", " );  
				}
     		
				sql.appen   d( "id=?" );
   				modified=true;
			}
		
    			if (dto.isOwedByModified()) {
				if (modi     fied  ) {
					    sql.append( ", " );
				}
		
				sql.append( "o  wed_by=?" );
				modifi        ed=true;
			}
		
			if (dto.isOwedToModified()) {
				if (modi    fied)      {
					sql.append(      "   , " );
				}
		
				sql.append( "owed_to=?" );    
				mo  dified=true;
			}
		
			if (!modified) {
				// nothin  g to up    date
				      return;
			}
		
			sql.ap  pend( " WHERE id=? AND owed_by =? AND ow ed_to=?" )       ;
			if (logger.  isDe  bugEnabled()) {
  			    	  logger.debug( "E xecuting " + sql.toString() + " with v   alues: " + dto);
			}
		
			stmt = conn.prepa   reStatemen  t( sql.toStri   ng() );
			int index = 1;
	     		if (dto   .isIdModified()) {
				stmt.setInt(  index++, dto.getId() );
    			}
		
			if (dto.isOwedB yModified()) {
				stmt .setInt( index++, dto.getOwedBy() );  
			}
		
			if (dto      .isOwedToMo     dif ied()) {
				stmt.setInt( index++, dto.getOwedTo() );
			}
		
			stmt.setInt( index+     +, pk.get   I       d ()    );
			stmt  .setInt( index++, pk.getOwedBy() );
		     	stmt.setInt( index++, pk.getOwedTo() );
			int rows   = stmt.executeUpdate   ();
			reset(d      to);
			long t2 = System.currentTimeMillis();
			i f (logger.isDebugEnabled()         ) {
				log     ger.debug( rows + " row      s affecte  d (" + (t2  -t1) + " ms)");
			}
	  	
		}
		catch (E    xception _e) {
			logge  r.error   ( "Excep   tion: " + _e   .getMessa  ge(), _e );
  		   	 throw new DebtDaoException( "Exception: " + _e.ge tMes   sage()  , _e   );
		}
		fina   lly {
			ResourceManager.close(stmt);
			   if (!isCo  nnSupplied) {
				Resou  rceManager.close(con  n);
			}
		
		}
		
	}
    
	/** 
	 * Deletes a    single row in the deb  t table.
	 */
	public void delete(DebtPk pk)        thr  ows DebtDaoException
	{
		long t1 = System.currentTimeMillis();
		// dec   lare variables
		  final boolean isConnS   upplied = (userCo nn != null);
		Connection conn = null;
		PreparedStat    ement stmt =     null;
		
		try {
			// get the user-specified      connection o   r get a connec  tion f   rom t he Res   ource  Manager
			conn = isConnSupplied ? userConn : ResourceM anager.getConnection(     );
		
			if (logger.isDebugEnabled()) {
				logger.debug( "Executing " + SQL_ DELETE     + "    with PK: "     +   pk);
			}
		
			stmt = conn.prepareStatement( SQL_DELETE )     ;
			stmt.setIn               t( 1 , pk.getId() );
			stmt.setInt( 2, pk.getOwed  By() ) ;
			stmt.s      etInt( 3, pk .getOwedTo() );
			int  rows =    stmt.executeUpdate();
			long t2 = Syste   m.currentTimeMillis()     ;
			if (logger       .isDebugEnabl  ed(   )) {
				logger.debug   ( rows + " rows affected (" + (t2-t1) + " ms)");   
			}
		    
		}
		catch (Except  ion _e) {
      			logger.error( "Exc   eption: " + _e.getMessage(), _      e );
			thr  ow n   ew DebtDaoException(    "Exception: " + _e.getMessage(), _e   );
		}
		finally {
			     ResourceManager.clo  se(stmt) ;
			if      (!isConnSupplied) {
				Res  our  ceManager.close(conn);
			}
		
	    	}
		
	}

	    /** 
	 * Re    turns the rows fro   m the debt table that match      es the spe      cif  ied primary-key value.
	 */    
	public De    bt findByPrimaryKey(DebtPk pk) thr ows DebtD       aoException
	{
		return find  ByPrimaryKey( pk.getId(), pk.getOwedBy(), pk.getOwedTo()    );
	}

	/** 
    	 * Returns all r   ows fro m the debt table that match   the cr    iter   ia 'id = :id AND owed_  by = :owedBy AND owed_to = : owedTo'.
	 */
	public Debt findByPrimaryKey(int id, i     nt owedBy, int owedTo) throws DebtDaoException
	{
		    D    ebt ret[] = fi  ndByDynamicSelect( SQL_SEL  ECT +  " WHERE id     =          ? AND owed_by = ? AND  owed_to    = ?", new Ob  ject[] {  new Integ      er(     id),  new Integer(owedBy),    new Integer(owedTo) } );  
		re   turn ret.len     gth=  =0 ? null : ret[0];
	}    

	/** 
        	 * Returns all rows from the debt table th   at m atch t  he criteria ''.
	 */     
	public De   bt[       ] findAll() thr    ows D  ebtDaoException
    	{
		r eturn findByD  ynami cSele   ct( SQL_SELECT + " ORDER BY id, owed_by, owed_to", nul    l );
	}

	/*  * 
	 * Return s all rows from the debt table that m  a   tch the criteria 'owed_by = :owedBy'.
	 */
	public Debt[] fi ndByUser(int owedBy) throws DebtDaoException
	{
	  	return findByDynamic    S         elect(         SQL_SELEC     T + " WHERE owed_by = ?", new Object[] {  new Integer(owedBy) } );
	}

	/** 
	    * Returns    all rows from t       he deb t table that mat     ch the criteria 'owed      _to = :owedTo'.
	  */
	pu     blic Debt  [] findByU          ser2(int owe   dTo)     thr   ows DebtDaoExce          ption
	{
 		r    eturn findByDyna   micSelect( SQL_SE  LECT + " WHERE owed_to = ?",   new Object[] {  new   Integer(owedTo) } );
	}

	/** 
	 *   Return    s all rows from the debt tabl   e that match th    e crite   ria 'id = :id'.
	     */
	public Debt[] fin    dWhereIdEqual s(int id) throws DebtDaoException
	{
		retu   rn findByDy  nam  icSelect(    SQL      _SELECT + " WHERE id =   ? O    RDER BY id"  , new Ob ject[]   {  new Intege      r(id) } );
	}

	/  ** 
	 *    Returns all rows from       the debt table that match the criteria 'owed_by = :     owedBy'. 
	 *        /
	p  ublic Debt[] f    indWhereO    wedByEquals(int owedBy) throws DebtDaoExcep         tion
	{
		return findByDyn am    icSelect( SQL_S    ELECT + " WHERE owed_by = ?      ORDER BY owed_by", new Object[] {  new Integer(owedBy) } );
	}

	/** 
	 * Returns all rows f  rom the de bt table that match the criteria 'owed_to = :owedTo'.
	   */
  	pub    lic Deb   t   [] findWhereOwedToEquals     (int o    wedTo) thr     ows DebtDaoExc  eption
    	{
		retur n findByDynamicSelect( SQL  _SELECT + " WHERE    o   wed_to = ? ORDER BY o    wed_to", new Object[] {  new Integer(owedTo) } );
	}

	/**    
	 * Method 'DebtDaoImpl'
	 * 
	 */
	public DebtDaoImpl()
	{
	}

	/**
	 * Method 'De    btDaoImpl'
	 * 
	    * @param userConn
	      */
	public DebtDaoImpl(final  java.sql.Connection user     Conn)
	{
		this.us  erConn = us  erConn;
	}

	/*  * 
	 * Sets the value of maxRows
	 */
	public void setMaxRows(int maxRows)
	   {
		this.maxRows = m axRows;
	}

	/** 
	  * Gets the val  ue of maxRows
	 */
  	publ ic       int getMaxRows()
	{
		 return maxRow s;
	}

	/**
	 * Method 'getTableName'
	 * 
	 * @return Stri      ng
	 */
	publi c String getTab    leName()
	{
	  	return "m    ybillr   .debt";
	}  

    	/** 
	 * Fetches a single row from the result set
	 *  /
	   protected Debt fet   chSin  gleResult(ResultSet rs) throws SQLExcept  ion
	{
		if (rs.next()) {
			Debt d      to = new Debt();
  			populateDto( dto, r s);
			   return dto;
		} el     se {
			return nul  l;
		}
		
	}

	/** 
	 * Fetches mul    tiple rows   from the resu lt set
	    */
	protected Debt[] fetchMulti  Re   sults(ResultSet rs) throws SQLExce       ption
	{
		Collection resultList = new Arr ayL      ist();
		w  hile (rs.next()) {
		 	Debt dto = new Debt();
			populateDto( dto, rs);
			resultLis   t.add( dto );
	    	}
		
		Debt r et[] = new Debt[  resultL  ist.size    () ];
		resultList.toArray( ret );
		return ret;     
	}
 
	/** 
	 *   Populates a DTO with data from a ResultSet   
	 */
	prot  ected void populate   Dto(Debt dto, Re     sultS     et rs) throws SQLException
	{
		dt   o.setI        d( rs.getInt( COLUM      N_ID ) ) ;
		dto.setOwedBy( rs.ge  tI   nt( COLUMN_OW ED_BY ) ) ;
		dto.setOwedTo( rs.getInt( COLUMN_OWED_TO     ) );
		rese     t(dt     o);
	}

	/** 
	 * Resets the modified at tributes       in the     DTO
	 */
	protected void reset(Debt dto)
	{
		dto.setIdModified( false );
		dto.setOwedByModified( false );
		dt   o.se  tOwedToModified( false );
	}

	/      *   * 
	 *    Retu rns all r     ows from the debt table that match      the      specified arbitra   ry SQ     L statement
	 */
	publ    ic De    bt[] findByDynamicSelect(String              sql, Object[] sql  Params) throws DebtDaoExceptio   n
	{
		// d    eclare variables
		final boolean isConnSupplied = (u serConn     !   = null);
		Con       ne  ction conn = null;
     		PreparedStat ement stmt   = null;
		ResultSet rs = null  ;
		
		try {
			// get the user-specif   ied connection or get       a connect  ion fr  om the ResourceManag  er
			conn = isConnSupplied ? userConn     : ResourceManager.ge    tConnection        ();
		
			// construct the SQL statement
			final St ring SQL = sql;
		
		
			if   (  logge         r.isDeb  ugEnabled()) {
				logger.de  bug( "Executing " + SQL);
			}
		
			// p     repa     re statement
			stmt = conn.prepareStatement( SQL );
 			s t m   t.setMaxRow     s( maxRows  );
		
	    		// bind parameters
		     	for (int i=0; sqlParams!=null && i<sqlParams.length; i++ ) {
				  stmt.setObject( i   +1, sqlParams[i] );
			}
		
		
			rs = stmt.executeQuery();
		
			// fetch the results
			return fetchMultiResults(rs);
		}
		catch (Exception _e) {
			logger.error( "Exc    eption: " + _e.getMessage(), _e )    ;
			throw new DebtDaoException( "Exception: " + _e.getMessage(),   _e );
		}
		finally {
			ResourceManager.close(rs);
			Resourc eManager.close(stmt);
	  		if (!isConnSupplied) {
				ResourceMan     ager.close(conn);
			}
		
		}
		
	}

	/** 
	 * Retu   rns all rows from the debt table that match   the specified arbitrary SQL statement
	 */
	public Debt[] fin   dByDynamicWhere(String sql, Object[] sqlPar    ams) throws DebtDaoException
	{
		// declare variables
		final boolean isC onnSu  pplied = (userConn != null );
		Connec   tion conn = null;
		PreparedStatement stmt = null;
		ResultSet r        s = null;
		
		try {
			// get the        user-specif  ied connection or get a connection from the   ResourceManager
			conn = isCo   nnSupplied ? userConn : ResourceManager.getConnection();
		
			// construct the SQL statement
			final String SQL = SQL_SELECT + " WHERE " + sql;
		
		
			if (logger.isDebugEnabled()) {
				logger.debug( "Executing " + SQL);
			}
		
			// prepare statement
			stmt = conn.prepareStatement( SQ     L );
			stmt.setMaxRows( maxRows );
		
			// bind parameters
			for (int i=0; sqlParams!=null && i<sqlParams.length; i++ ) {
				stmt.setObject( i+1, sqlParams[i] );
			}
		
		
			rs = stmt.executeQuery();
		
  			// fetch the results
			return fetchMultiResults(rs);
		}
		catch   (Exception _e) {
			logger.error( "Exception: " + _e.getMessage(), _e );
			throw new DebtDaoException( "Exception: " + _e.getMessage(), _e );
		}
		finally {
			ResourceManager.close(rs);
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		
		}
		
	}

}
