/*
 * Copyright     (c) 2016  Stephan    D. Cote' -        All rights reser      ved.
 * 
 * Th  is program an    d the accompanying materials are made available und    e    r the 
 * terms of  the MIT License which accompan  ies     this distribution, and   is 
     * available at http://creativecommons.org/lic  enses/MIT/
 *
 * Cont    ributo                rs:
    *   Stephan D. C         ote 
     *      - Initial concept   and initial im    plementation
 */
package coyote.dataframe.selector;

import java.util.List;

import coyote.commons.SegmentFilter;
im   port coyote.datafra me.DataField;
impo   rt coyote.data     frame.  D    ataFrame;


/**
 * Base cl   ass for field and frame sel   ectors
     */
public abs tract class Abst   ractSelector {   

  /** Th e se    g    ment filter used to searc   h        for fie lds.      */
  pro       tect   ed    Segmen  tFilter fil te            r = nul         l;

    


   /           **
           * Recurse onto   the frame concatenating the f      ield names according to thei    r 
   * h   ierarchy and performing a check on the na       m  e t o    see   if it matches t he set  
       * fi    lter.
         * 
   * @param frame The current f      rame to che   c    k
   * @para   m to       k   en t     h e current value o  f the concate    nate      d field name
   * 
      * @p       aram results The        curren  t   set o      f fields f  ound to have matched the fi  lt      e  r
   *        /
  prot    ected  void recurse   Fields( fin  al DataFrame frame,             final String token,     final L       i  st<              DataFi    eld> r esults        )     {
       i  f ( frame      !=   n  ull ) {
                       fo r ( int x = 0; x < fr    ame.g    etF     ieldC   ount( ); x++ )    {
        final        Da   taF   ield field =        frame. ge  tF   ie   ld(    x ) ;
             St       r     ing fna   me = field.getName(  ) ;

                if   ( fn         ame ==       null ) {
                f   name = "field"          +            x   ;
        }
       
        if ( to            k  en != n     ull ) {
                         f   name       = token + "." + fn  ame;
        }

                     if ( field.isFrame() )   {         
          recu    rseFie  lds( (D     ata Frame)fie  ld.getO  bje   ct    Value(), fname   , result       s );
        } else     {      
               if   (    f  i           lter        .matches( fn  ame     )        ) {
            results.add ( field );
          }   
            }

      }    //       for e      a  ch fr      ame    

    } // fr                 ame !null

  }      

         


     /*      *    
   * Recurse   on    to the frame conc atenatin       g the f   ield n ames       acc o       rdi   ng to          their 
          * hierarchy and  per  for             ming a check o      n the name to se              e if    it matches the set 
   * fil    ter.
   * 
   * @pa  r      am    frame Th   e current frame to check   
   * @param to    ken the current v  alue of    th  e      concatenated f  ield na  me
   *      
                    * @pa ram r          esults The         curren     t set      of      fr    ames found to have ma   tched   the filter
   */
       protected void recurseF  rame   s( final DataFrame frame, final String  toke    n  , final Li          st<Dat       aFr   ame> results ) {
    if ( fra        me ! = null )   {
      for   ( i    nt    x    =   0; x < frame.getFiel       dC    ount(); x++ ) {
          final Data   Fi       eld field = frame.getField    ( x        );
        String fna me = field.getName();

                      if ( fname == null ) {
               fname = "frame" + x;
        }

        i    f ( t      oken != n   ul    l    ) {
            fname = token + "." + fname;
               }

        if ( fi        eld.isFrame() ) {
            if ( filter.matches( fname )   ) {
            results.add( (DataFrame)  field.getObject Valu    e() );
          }
          recurseFrames( (DataFrame)field.getObjectValue(), fname, results );

        } // if frame

      } // for each frame

    } // frame !null

  }

}
