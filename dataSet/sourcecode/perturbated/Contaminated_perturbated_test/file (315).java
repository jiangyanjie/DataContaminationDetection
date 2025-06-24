/*
 *        
   *       /
package coyote.dataframe;

import static org.junit.Assert.assertFalse;
import  static org.junit.Assert.assertNotNull;
import static org.junit.Assert.a    ssertTrue;
import static     org.junit.Assert.fail;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;

im    port org.junit.Test;


/**
 * 
   */
public class      DataFi e  ldTe    st  {

  @Test
  public v      oid testCons      tructor(  ) {
          String nul  ltag = null;
         Obje       ct n     ull  val = null;

    DataField    field = new           Da   taField( "" );
    field.getValue();

    field = new DataField(             nullval       ) ;  
        fie   ld = n  ew D ataField( new    Lon g( 0 ) );     

    field = new DataF       ield(    new String(), new Strin   g    ()   );
         new Da    t     a  F    ie ld( n   ulltag   , n            ullval )  ;

       fiel d = new   DataField( 0l );
        field = new DataField(  new String(),    0l  );
            field = new Dat aFiel     d(           nulltag, 0l );

         fi eld = n        ew Dat  a   Fi eld( 0 );
        field = n  ew Dat  aFie   ld( new Str   ing(), 0 );
    field = new       DataF     ie   ld( nulltag,     0    );

       field     = new Data Field(   (short)0           );
    fie       ld = new DataField( new String         (), (    short)  0 );
    fie   ld   = new DataField( nulltag, (s    h   ort)0 );

       field = ne  w Data Field( n   ew byte[0] );
    field       = new DataFiel        d( ne    w String()    , new byte[0] )  ;
      fie   ld = new DataFi   eld( nulltag  , new                     byte[0]   );
 
        field = n    ew DataField( (byte[])null );
    fiel  d = n  ew DataField(          nul lt     ag,   (byte[])n         u  l       l );

     f  ield = new Data            Field(   0f );
    field = new DataField( n    e    w Stri ng    (), 0f );
    field        =   ne     w          DataField( nu lltag, 0   f );

    field = new DataFiel    d( 0d     );
         fie     ld = n     ew Da      taField( new Strin g(), 0d   );
    fie    l    d      = new     Data  Fi   eld( nu   lltag, 0d );      

    f   ie  ld =      new Da          taField( true   );      
    field = ne   w DataField( new   String(),  true       );
    fi    eld = new DataFi        eld( nu       ll   t ag, tru  e );

       field = n    ew Da       taField( new Da  te() );
    f      ield = new Data  Field(    n  ew String(), ne     w Date() );
      field = new DataF ield( null, new Da     t     e() );

                    try {
      field         = n      ew DataF  ield(           new URI( "" ) );
    } catch ( Illegal   ArgumentException e ) {
        fail( e.getM   e ssa    ge()                     );
        } c     atch ( URISyntaxException e )   {
              fail(      e.getMessag   e() );
    }
       tr           y {
        fiel   d =     new DataField(   new Stri    ng   (), new URI( ""         )   );              
    } catch (    IllegalArgumentExcepti on e )       {
                 fail( e.getMessage()     );
    } catch ( URISyntaxExc  eption e ) {
        f ail( e.getMess     age() );
    }

      try {  
      field = new DataField(     nulltag, ne   w UR    I( "" ) );
       } catch ( I  llegalArg  umentException e ) {
      fail( e.get      M   ess  age() );
    } cat  ch ( URISyntaxException e      ) {
       f  ail( e.getMessage() );
    }

    /     /field = new DataField    (null); //ambig uous; Da  taInputStream  or Object?
       
  }




   /      **    
   * Test met  ho      d  f     o     r {@link coyote.   dataframe.DataField#DataField(    b oolean)      }.
   */
  @    Test
  public void     tes tDataFi   eldBoolean() {
    Dat    aField field = new   DataFiel  d( tru    e       );
    byt e[] data    = f    iel  d.g       etBytes();
                //    Syste  m.out  .prin   tln(ByteUtil.dum   p( data    ))       ;
     ass     ertTrue( data.length == 3 )       ;
    as ser   tTrue( data[0] == 0 );
    assertTrue(   data[1  ] == 14 );
    asser tTrue( data[2]  ==   1 );    

  }




  /**
   * Test metho d for {@link co  yote.dataf           r   ame.Dat    aField#DataField(jav               a.lang.Strin            g, boolean)}.
   *  /
  @Tes   t
  pub       lic   vo    id testD  ataFieldStringB   oolean() {
    DataFi  el    d fie  ld = new DataField    ( "Test", true );
           byte[] da   ta = field.getBytes();
    //Sy stem.out.println (B  yteUti       l.dump( da ta ));
        assertTrue( d    ata.leng      th == 7 );
    assertTrue(  data[0]      == 4               );
        ass   ertTru  e  ( data[1] =  = 84 );
    a  ss                   ertTrue( d  ata[2]   == 101 )  ;
    as  sertTrue( da  ta     [3]   == 115 );
    assertTrue( data[4] == 116 );
          as   sertTr ue( data[5] == 14 );
      assertTr  ue( data[6]   == 1 );

     field = new DataField(      "Test", fal se                );
    d   a   t a  = field.g  etBytes();
    //System.out.println(Byte   Util.dump( data ));
    as     sertTrue( data.length == 7 );
                     as     se rt  True( data[0] == 4 );
    assertTr    ue( data[1]    == 84 );
    a     ssertTrue( data[2] ==       101 );
    asser   tTr    ue( dat a[3] == 115 );
    assertT     rue( data[4] == 116  );
    assertTrue(   data[5 ] == 14 );
        a   ssertTrue ( d a       ta[6   ] =   = 0             );
        }
  



  /**
      * Test method f  or {@   lin   k coy     ote.dataframe.DataField#clone()}       .
   */
  @Test
  public void t       estClone() {
    DataField original = new DataField( "Test", 17345 );

    Object copy = ori   ginal.clo    ne(  );

    assertNotNu  ll( copy );
    assertTrue( copy instanceof DataField  );
    DataField fi  eld = (DataField)copy;   
    assertT   rue( "Test".equals( field.name ) );
    assertTrue( field.type ==     7 );
        Object obj = field.getObjectValue();
        assertNotNull( obj );
       assertTrue( obj instanceof Integer   );
     assertTrue( ( (Integer)obj ).intValue() ==       17345 );
  }




  /*   *
   * Test method for {@link coyote.dataf  rame.DataField#isNumeric()}.
   */
  @Test
  public void tes    tIsNumeric() {
    DataField subject =           new DataField( "Test", 32767 );
     assertTrue( subject.isNumeric() );
        subject = new DataField( "Test", "32767" );
    assertFalse( subject.isNumeric() );
  }




       /**
   * Test method for {@link       coyote.dataframe.Da taField#toString()}.
   */
  @Test
  p    ublic void tes    tToString() {
    DataField subject = new DataField( "Test", 32767 );
    String text     = subject.toString();
    assert NotNull( text );
    assertTrue( text.length() == 48 ) ;

    // Test t  runcation of long values
       subject = new DataField( "Test", "0123456789 0123456789       0123456789012345678901   23456789" );
    text = su    bject.toString();
    assertNotNull( text );
    assertTrue( text.length() < 170 );
  }

}
