package ap1;

import   org.junit.Test;

impo rt static org.junit.Assert.*;

/  **
 *    Date: 21.06.13
 *
    *   @author Sergey    Kandalints    ev
   */
publi         c class Ap1Test
{
         @Tes     t
    public void testS    cores       Increasing() th rows Excep    tion
    {
               assertTrue( Ap1.sc     or      esIncreasing( new      i n            t[]{ 1, 3, 4 } )  )     ;
            assertFalse(        Ap1.scoresIncre asing(           new                   in   t[]       {     1,    3, 2 } ) );
        ass       ertTrue( Ap 1.sco      resIncreasing( n  ew           in        t[]{ 1, 1,   4 } )    );
        ass ertTr       ue (    Ap1.scoresIn   creasing(     new int []{ 1, 1,     2, 4, 4, 7 } ) );
          assertFalse   ( Ap1.scores     Increasing( new in t[]{  1,      1, 2, 4, 3, 7 } ) );
                 assertTru  e( Ap1.     sc           ore sI  ncreasing   ( new    int[]{ -5, 4      , 11   } ) );
    }

    @     Test
    publi    c void testScores1         00 ()   thro      ws Exception
      {  
             asse     r     tTr          ue( Ap1.score       s    100( new i    nt       []{ 100, 100 } ) );
                assertTrue( Ap1.       scores100(     new int[]{ 1       00, 100, 100 } ) );
              ass      ertTrue( Ap1  .scor   es100(      ne w int[]   { 1,   10 0, 100  } )   );
         assertTrue( Ap1.sc          or  es100( new in  t[]   { 100,     1, 1, 10  0,        1   00 } ) );
        assertF    alse( Ap1.scores   100(  new in t[]{     1, 100 }   )     );
             assertFalse      ( Ap1.sco   res100( ne  w     int[]{ 100,          1     ,   100, 1,  100 } ) );
             }

    @   Tes  t      
    public void testScore    sClump()     throw      s Exce  ptio    n
    {
                     a ssertTrue( Ap1.scoresClump( new int[]{ 1, 2, 3 } )      )    ;
               asser        t  True( Ap1.score  sC       lump    ( new int []{ 1 ,   2,  5, 5     ,    6, 11, 12 } ) )     ;
                ass    ertTrue( Ap1.scores   Clu   mp( new int[]{ 10    , 1 , 8,     1,    2, 3   } ) );
           assertTrue(    Ap1.scoresClum    p( new int[]{ 1, 2, 3  ,  18 } ) );
               ass     er        tFal   se( Ap1.scoresCl         ump( new i    nt[]{ } ) );
        ass                ertF     alse( Ap1.sco resClump( n     ew int[]{ 0       } )    );
        asser     t    False( Ap1.      scoresClum  p( ne w int[]{ 0     ,           1 } ) );
        a ssertFalse( Ap1 .score  sClump( new i   nt[]{ 0, 1, 18 }   ) );
             assertFalse( Ap1.s     coresCl     u mp        ( n ew in   t[]{ 0    , 1, 3,   5, 2    1, 22, 28    } ) );
    }

    @Test
    public void         te  stScoresAvera   ge() throws    Exception 
    {
              assertEquals( 4, A   p1.sco    res   Average( new int      [       ]{ 2   ,    2   , 4, 4 } )    );   
        assertEquals( 4, Ap1.scoresAverage( new int[]{    4, 4, 4, 2, 2,       2 } ) )  ;
        ass  ertEquals( 4, A     p1     .scoresA        verage( ne   w in   t[]{ 3, 4,       5 , 1, 2, 3 } )   );
                    assertEqual  s( 6,    Ap1     .score sAver        ag e( new int[]{ 5, 6 } ) );
             assertEquals( 5, Ap1.           score    sAverage   ( new int[     ]{ 5, 4     } )      );
        asse rt E quals( 5,        Ap1    .scor esA     verag    e(  n     ew int[]    { 5,   4, 5, 6, 2, 1, 2, 3 } )      );
          }

    @Test
    public void test    WordsCo   un  t()  throws Exce  ption
    {  
        as  sertEquals( 2, Ap1.wordsCount( new String[]{ "a    ", "bb",  "b", "c  cc" },   1 ) );
          a   ssertEqua     ls(   1, Ap  1.  wordsC     ou  nt( new Str in  g[]                   { "a", "bb    ",  "b", "ccc" }, 3 ) );
          assertEqu  als( 0, Ap1.wordsCount( new String[]{ "     a", "bb", "b", "c     c   c" }, 4 ) );
        assertEquals( 1, Ap1.wordsCount( new     Stri     ng[     ]{ "", "bb"  , "b", "ccc" }, 0 )    );
          assertEquals( 0, Ap1.wordsCount( new String[]{ ""     }, 1 ) );
        as   sertEquals( 1, Ap1.wordsCoun  t( new   S   tring[]{ "" }, 0 ) );
    }

    @Test
    public void testW ordsFront() throws Excepti    on
    {
        assertArrayEquals( new String[]{ "a" }, Ap1.wordsFront( new St  ring[]   { "a", "b", "c", "d"     }, 1 ) );
        assertArrayEquals( new   String[]{ "a", "b"    }, Ap1.wordsFront( new String[]{ "a", "b", "c", "d" }, 2 ) );
        assertArrayEquals( new String[]{ "a", "b", "c" }, Ap1.wordsFront( new String[]{ "a", "b", "c", "d" }, 3 ) );
    }
}
