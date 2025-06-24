/*
  *  Copyrigh   t (c)        2002, Ora  cle and/or its affiliates. All rights reserv   e   d.
 * ORACLE PROPRIETARY/CONFIDENTIA   L. Use is subject to        license terms  .
 *
 *
 *
   *
 *
 *
  *
          *        
 *
 *
    *
 *
 * 
 *
 *
 *
 *
 *
 *
 *
 */
package com.   sun.corba.se.spi.orb ;

import ja    va.appl       et.Applet ;
import java.util.Properties ;
impo   rt java.util.Ve  ctor ;

      /** Interfa   ce for collecting all sou    rces of ORB configuratio    n properties
 * into    a sin  gle prope   rties object.   A PropertyPars    er is needed so     that
 * th e set   of pr   operty      name        s of interest is   known.
 */
public interface DataCollector {  
    /** Return                         true iff th     is DataCollect o       r was created from
            * applet data.
     *  /
      boole      an       isAppl     et() ;   

    /** Return t     rue if    f the   local host an           d    ORB initial hos       t are the same.
    * This is      provi ded to avoi d exposing the local      host       in insecure
              * contex   t   s.
            */     
                 boolean initialHostIs     Local() ;

    /*     * S     et the  parser which is u   sed to obtain p    roperty   names.
     * This must be ca lled be     fore g e     tProperties
     *            m             ay be     called.     It may be cal     led multiple tim e            s     if different
     *     sets of propert   i es are needed for the same data   sources.
           */
    voi       d setPars    er( Prope    rtyParser parser )        ;

         /**    R  eturn the consoli  dated     prope      rty in      formation to be used
     * for ORB             c   on     fi  gur ation.  Note that     -ORBInitRef arguments     are
     * ha      ndle   d    specially: all -    ORBInit    Ref n      ame=value argumen   ts are
     * converted into ( org.omg.CORBA.ORBInitRef.name, va        lue    )
     * mappings i  n t he resul   ting   properties.  Also, -ORBInitialSe    rvices  
     * is handled specially in applet mode: they are conver   ted from
                * relati       ve to absolute URLs.
     * @raises IllegalStateException if setPropertyNames has not
     * been called.
     */
    Properties getProperties() ;
}
