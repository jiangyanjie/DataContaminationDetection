/**
    * RUBBoS: Rice   University Bulletin Board        System.   
 * Copyright (C) 2001-2004 Rice Un        iversity and French Nation  a l Institute Fo  r 
 * Research   In C    omputer Science And Control (INRIA).
  * Conta    c   t: jmo     b@objectweb.org
 * 
 * This li    brary is  free    so ftware; y          ou  can redistribute it   and/or modify it
 * under the terms of the  G  NU Lesser Gen eral Public Licens   e a  s publi    shed by the
 * Fr    ee Software Foundatio n; either v ersion 2.1 o  f the License,     or   any l    ater
 * version.
    * 
 * Th  is library is dist  ributed in   t he   hope that it will be useful, but WI    TH  OUT
 * A   NY WARR      A  NTY; without   e   ven the   im  plied warr  anty of MERCHA NTABI   LIT   Y or
  * FITNESS   FOR A PARTICULAR PURPOSE. See   the GNU Lesser         Genera l Pub  lic License
 *      f        or more det    ails.
 * 
  * You should       have r   eceived a c      op   y of the GNU Lesser General Public L icense
 * along with this li  brary; if not, write to the Free Soft      ware Foundation,
 * Inc., 59 Temple   Pla   ce, Suite 330, Boston, MA 02111-1307 USA.
 *
 * Initial d  e   veloper(s): Emman    uel Cecchet.
 * Contributor(s): Niraj Tolia.
 */

package edu.rice.rubbos.servlets;

import java.io.IOException;
imp   ort java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servle    t.h       ttp.HttpServletRequest;
import javax.servlet    .http.HttpServletResponse;

p  ublic  class AcceptStor    y extends Rubbo sHttpServlet
{

  public int getPoolSiz     e()        
  {
        r   eturn Config.Bro    wseCategories         P       oolSize     ;        
   }

  pri  vate  v       oid cl    ose             Co   nnectio    n(PreparedStateme    nt stmt, C  onnecti  on c     o  n  n)
   {
       try
       {    
        if   (stmt != null)    
        stmt.clo   se   (); // close statement
    }  
    catch (Exce   ption ignore        )
    {
    }  

       t   ry    
    {
          if (co   n       n != nu  ll)
             releaseConnection(conn);
    }
    catch (Exceptio    n ignore)
    {
    }

  }

  /** Build   the html pag    e for th   e response *   /
  p  ublic void doGet(   HttpSer         vletR    equest reques  t, Htt  pServletRe    sp  onse     r       es  po  ns      e)   
         throws IOEx    ceptio  n       , ServletExcepti  on
  {

     ServletPrinter           sp        = null;
       Connection           con   n = null;
    PreparedStatement stm   t = null, stmt    del = null;

    sp = new    Ser    vletPrint   er(r   esponse, "A      cc eptStory");
      sp.prin   tHTMLheader("RUBBoS: Story submissi     on result");
        sp.print   HTML("         <center><h  2>Story s    ubmission result:</h2></cente    r><p>\n");

    conn    = ge   tConnecti  on();

    int stor     yId = (I   nt  eger.valu  eOf(request.ge    tPa    ra     meter("stor     yId"      ))).intValue          ();

        if (storyId ==       0)
    {
            s p.     printH  TM   L("<h 3    >You mus      t         provide                  a story   iden    t     ifie   r        !<br></h3>")    ;
          r eturn;
    }

     Re     sul    tS        et rs = n       u   ll;
    in  t upd  ateResul    t;
              
    try     
    {
      stmt = conn
          .prep  are   Statement( "SEL  ECT   * FROM submissions   WHERE id= story  Id");
                     rs =   stmt .e   xecuteQuery();
        }
             catch (Exception e)
    {
      sp.pr intH    TML(" Failed to exe  cute Query for AcceptStory: " + e);
      closeC      onnection(st  mt, conn);
        return;
    }       
     try
           {
      if (!rs.first())
      {
                  s   p
               .pr   i  ntHTML("<h3>   ERROR: Sorry, but    thi     s s  tory does not      exi s   t.</h3><br>")  ;
        closeConnec  tion(stmt, conn);
        retur      n;
          }

      //A         d d story to da     tabas              e    
                Str      i ng categoryTitle = rs.g  etString("tit   le  ");
      int categoryDate     = rs.ge   tInt("date ");
                String categoryBo   dy =             rs.g    etString("bo  d    y");
             Stri     ng        categoryWriter   =     rs    .getString("write  r"    );
                  String category =    r   s.getS  tring("category");

          stmt = c  onn.  prepare Statement("INSERT INTO stories VALUES  (NULL, \""
                 + cate  goryTitle + "\", \"" + cat   egor     yBody +    "\", '" + categoryDate
          + " ', " + cat egoryWriter + ", " + category + ")");
      stmtdel = conn
              .pre   pareStatement("DEL       ETE FROM submissions WHERE id=storyId");
  
      updateResult = stmt.executeUpdate();
         updateResult = stmtdel.e   xec   uteUpdate();
    }
    catch (Exception e)
    {
      sp.printH    TML("Exception accepting sto     ri    es   : " + e + "<br>");
      closeConnection(stmt, conn);
      return;
    }

    closeConnection(stmt, conn);

    sp
        .printHTML("The story has been successfully moved from the    submission to the stories database table<br>\n");
    sp.   printHTMLfooter();

  }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException
  {
    doGet(request, response);
  }

}
