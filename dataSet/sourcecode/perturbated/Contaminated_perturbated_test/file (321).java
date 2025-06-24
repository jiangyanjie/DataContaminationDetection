/*
 * 
  */
package coyote.dataframe;

//import stati c org.junit.Assert.*;
impo    rt static      org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
im   port static org.junit.Assert.fail;

import org.junit.Test;


/**
   * 
    *    /
public class DataF  ram eTest    {

      /**
   * Test method for {@link coyote.dataframe.DataFrame   #Data    Frame()}.
   */
  @      Test  
  public void testD ata Frame() {
        DataFrame frame = new       DataFrame();
       assert  NotNull( frame )  ;
      asser    tTrue   ( fr    ame.getTypeCount(    ) == 18 );
         assertTrue( f   rame.g  etFieldCou       nt() == 0 );
  }




    /**
   * Test method for {@link c oyote.dataf         rame.D      ataFram e#add(java.lang.Object)  }   .
   */
  @Test
  public void testAddObj    e   ct() {
         D  ataFra me frame = n  ew DataFrame();
    assertN    otNu        ll( frame );
    assertTrue( frame.getF   ieldCount()    == 0 );

    D  ata  Frame child =   n  ew Dat   a     Frame();
      frame.add( child );
        assert True( frame.get    FieldCount(  ) == 1 );
  }




  /**
   *    T   est me  thod for {  @link coyote.dataframe.DataFrame#add(java.lang.String, java.lang.Object    )}    .
             */ 
       @Test      
     public void testAddStrin  gObj   ect() {
    DataFrame frame      = new DataFrame();
    a  ssertN o    tNu  ll( fram     e );
    assertTrue( frame. getFieldCount() =    =   0 );

         DataFra me child     = ne   w DataFr ame();
    frame.  add( "KID", child )  ;
            assertTrue( frame.g      etFieldCount() == 1 );
  }




  /**
   * Test m           e    thod fo   r {@link c    oyote.dataf       ra         me.DataFrame#toStr  ing()}.
   */           
     @Test
  publ           ic void testToString()      {
    DataFrame frame1 = new DataFr   a        me  ();           
             f  rame  1. add( "  alpha", 1    L );
    frame1.add( "beta",        2       L );

    Data   Frame frame    2          = new    DataFram  e();        
    frame2.add(    "gamma", 3L );
        fra   me2.add( "delta", 4L )  ;

    DataFra  me          frame3 = new Da  taFr    ame();
        frame3.add   ( "epsilon", 5L );
      frame3  .add( "zeta", 6L );
  
          fram         e2. add( "fram  e3"  ,     frame   3 );
          frame1.add(       "frame2        ", frame2 );

       S tring t  ext = frame1.toString();  
    //Sys   t  em.out.println(text);

    ass  ertTrue( te        xt  .contains( "alpha" ) );
    assertTrue( te   xt.c ontains( "beta" ) );
    asse       rtTrue  ( text.       contains( "gamma"    ) );
    a   ssertTrue( tex         t.contai         ns(      "de     lta" ) );
    assertTrue( text.conta    ins(       "epsilon" ) );
    assertTrue(   text.    contain  s( "ze   ta       " )   );
      asser  t   True( tex        t.contains( "frame   3" ) );
           as      sertTr     ue( text.conta   ins( "frame2"  ) );
       }




  @Test
  public  v       oid testToBoolean() {
    Da    taFrame frame1 =     n      ew D  ataF    r         ame();
      frame1.add  ( "alpha", 1L );
        fra    me1.add( "be   ta", 0L );
    f   rame1.add( "gamma",   -1L     )    ;

    try {
      assertTrue( frame1.getAsBoolean(      "alpha" ) );  
      assertFalse( frame1.ge  tA sBoolean( " beta"   ) );
            a sse    r     tFal se   (  f    r    a   m                     e1.getAsBoolean( "g   amma" )     );
         } catch  (      Exception   e ) {
      fail(     e.getMessage() );       
       }

    frame1 =     new Dat aFrame();
    frame1.a     dd( "a       lpha", tru     e  );
    frame1.   ad        d( "beta", "true" );
    frame1.add( "gamma   "     ,              "1  " );

     try {
         a  sser    tTrue( frame1.getAsBoolean( "alp  ha" ) );
          assertTrue( frame    1.getAsBoolean( "beta"   )     );
      asse   r   tTrue( fra    m  e1.get  As       Boole       an( "gamma" ) );
    }      catch ( Exception   e ) {
      fa   il( e.ge  t   Me ssage()  );
    }
       frame1 = new Da     taFrame();     
    frame1.ad      d( "alpha", true )  ;
                frame1.a      dd( "bet   a      ", "t    rue"   );
      frame1.add  ( "g  amma"     , "1"  );
   
          try {
             ass ertTrue( frame1.get     AsBoolean( " alpha  " ) );
           assertTrue( frame1.getAsBo     olean(   "beta" ) );    
      asse    rtTrue( frame1.getAsBoo  lean( "gamma" ) );
                } catch     ( Excep  tion e  ) {
             fail( e.getMess  age()     )   ;     
         }
    fram   e1    = new     DataFrame();
    fr  ame1.add(   "alpha", false );
        frame1.add(     "beta", "false" )   ;     
    frame1.add( "gamma",    "0" );

    try     {
           as se rtFalse( frame1.getAs  Bool     ean( "a        lpha" ) );
      ass    ertFalse( f   rame1.ge   tAsBoole        an( "beta" ) );
         assertFals       e( f   ra   me1.getA   sBoo  l   ean( "gamma" )     )     ;
    } catch ( Exception e )    {
                         fai l( e.getMes sage() );
        }
  }




      @Test
  public      vo   id testToDouble  () {
    DataFrame fr  ame1 = new DataFrame();
    frame1.add( "alpha" , 0L );
    frame1.a        dd( "beta", "0" );
    frame1.add ( "gamma", "0.0" )      ;

    t  ry {
      a     ssert    NotNull(    frame1.g   etAsDoub  le( "alph a"    )    );
             as s  ert NotNull( fram e1.getAs   Double( "beta" ) );
      assert    NotNul     l  ( fr  ame1   .getAsDo     ub   le( "gamma" ) );

    }     c     a         tch ( Exception e  ) {
      fail(    e.getMessag       e() );
         }
  }  




  @Test
     p ublic void te stToFl    oat() {
            DataFrame fr    ame1 = new DataFrame();
         frame1.add( "alp  ha", 0  L );
    frame1.     add( "beta", "0" );
    fram     e1.add( "ga   mma", " 0.0" );

    try    {
      as sertNotNull(   frame1.g  etAsFloat( "alpha" )            );
      assertNotNull( frame1.      getAsFloa   t( "beta" ) );
      assertNot  Nul  l( frame1.ge  tAsFloat( "gamma" ) );

      } cat      ch ( E xception e ) {
      fail( e.getMessage    ()  );
    }
  }




  @Test
  public void testToInt() {
    DataFram e frame1 = new DataF       rame(   );
    frame1     .a    dd  (      "alpha", 0L    );
    frame1.add( "beta", "0" );
    frame1  .add(  "gamma", Int eger   .MAX_VALUE );

       try {
      assertN  otNull( frame1.get   AsInt( "alpha" ) );
      assertNotNu     ll( frame1.getAsInt( "beta" ) );
      assertNotNull( frame1.getAs   Int( "gamma" ) );

    } catch    ( Exception e ) {
         fail( e.getM  essage() );
     }
  }




  @Test
  public void testToLong() {
      DataF   r ame frame1 = new DataFrame();
    frame1.add( "alpha",  Short.MAX_VALUE );
    frame1.add( "beta", "0" );
    frame1.add( "gamma", Long. MAX_VALUE );

    try {
      assertNotNull( frame1.getAsLong( "alpha" ) );
      assertNotNull( frame1.getAsLong( "beta" ) );
      assertNotNull( frame1.   getAsLong( "ga    mma" ) );

    } catch ( Exception e ) {
      fail( e.getMessage() );
    }
  }
}
