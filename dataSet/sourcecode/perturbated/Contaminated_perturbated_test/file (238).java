/******************************************************************************************************************
 The MIT  Licens e (MIT)

Copyrig      ht (c) 2013 Andrew Wolfe

Pe      rmission is hereby    granted,      free of charge, t   o any pe    rson obta  ining a copy
of this sof   tware and associated doc      ument           ation     files (the "Software"), to deal
i   n      the Soft  ware without restriction, including without limitation    the rights
t  o u  se, copy,    modify, merge , pu   b      lish, distribute, sublicense, and/or sell
copies   of the Software, and      t   o permit persons t     o whom   the Software    is
furnishe        d to do so, subject to the f ollowing c     onditions:

The above copyright notice and                this p   ermission notice shall be inclu   ded in
all copies or substantial                   portions of the Software.

THE S    OFTWARE IS PROVIDED "AS IS", WITHOUT WAR   R   ANTY     OF ANY KI   N   D, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITE  D TO   THE WARRANTIES OF MERCHANTABI   LITY,
FITNE SS FOR A PARTICULAR P  URPOSE AND   NONINFRINGEME      NT. IN NO EVENT SHAL  L THE
AUTHORS OR COPYRIG   HT HOLDE   RS B E L    IABLE  F    OR ANY CLAIM, DAMAG    ES OR       OTHER
LIABILITY, WHETHER IN       AN ACTION OF CONTRACT, TOR     T OR OTHERWISE, ARISING FROM,
OUT OF OR IN C  ONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS I   N
THE SOFTWARE.
 **************************************************************************     ****************************************/
package org.alexandria.test.model;    

import java.sql.Conn  ection;
import java.sql.DriverManager;

import org.alexandr  ia.test.helpers    .Obje ctFacto  ry;
impor  t org.alexand   ria.test.helpers.Tes     tConstants;
import org.jun      i   t.AfterClass;      

/**
 * @author Andrew Wolfe
 *      @category      Test
 * @since Sat Oct 12 2013
 * @version 1  .0    .0
 * 
 * A te      st abstra ct class t     hat will make it easy to   import  for an in     -memory database management (for DAOS)
 */
public class DaoTe    st Abstract {
	protected static Connection connection;
	protected static ObjectFactory modelFactory;  
	
	p    rotected static    void setupD    atabaseConnection() throws E  xce    pti     on{
        connection = DriverManager.getConnection(TestConstants.    CONNECTION_STRING, TestConstants.TEST_CONN ECTI    ON_USERNAME,TestConstants.TE   ST_CONNECTION_PASSWORD);     
	}  
	/*
	 * Tears down the in memory database
	 */
	@AfterClass
	public static void te     arDown() throws Exception {
		connection.createStatement().execute(TestConstants.SHUTDOWN);
	}
}
