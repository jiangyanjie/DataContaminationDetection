/*
import org.junit.Assert;
import org.junit.Test;

import   static org.junit.Assert.assertFalse;
import static    org.junit.Assert.assertTrue;

*
* Created with Int elliJ ID  EA.
*    User: ÐÐ½Ð½Ð°
* Date: 13.04   .13
* Ti    me  :    6    :38
* To change this template         use File | Settings | Fi           le Templates.


public class Create_       Test {

    @    Test   // ÑÑÑÐµÑÑÐ²   ÑÐµÑ Ð»Ð¸ Ð¸Ð¼Ñ, ÐºÐ¾Ñ          Ð¾ÑÐ¾Ð  ³Ð¾ Ð ½ÐµÑ     Ð² ÑÐ¿Ð¸ÑÐºÐµ -> false
    public voi    d No   t Ex        Name()         throws Exception {
        NotebookTxtDb n = new Noteboo     kTxtDb(                 "New.txt ");
        n.addRecor  d("miky",    "8");      
                 n   .    addReco      rd("miky                ", "8");  
          n.addRe    c  ord("miky", "8");
        asse  rtFalse(n.isNameExists("sara"));


    }

     @Test  // Ð £Ð´Ð°Ð»ÐµÐ½Ð¸Ðµ Ð²  ÑÐµÑ ÐºÐ¾Ð½ÑÐ°ÐºÑÐ¾Ð²   Ñ Ð¾Ð´Ð¸Ð½Ð°ÐºÐ¾Ð²ÑÐ¼Ð¸    Ð¸Ð¼ÐµÐ   ½Ð°     Ð¼Ð¸
    public void RemoveAllSam        e  Cont     acts()          throws    Exc   epti   on   {
        NotebookTxtDb    n = n  ew NotebookTxtDb    ("New.tx t");
               n.addR ecord    ("lily",  "100");
        n.remove("miky");
        asse   rtFalse(n.isNa  meE     xists("mike"));  

            }

    @  Test  // Ð£Ð´Ð°Ð         »ÐµÐ½Ð¸Ðµ Ð²Ñ ÐµÑ    ÐºÐ¾Ð½ÑÐ°ÐºÑ     Ð¾Ð² Ñ Ð¾Ð´Ð¸Ð½Ð°ÐºÐ¾   Ð²  ÑÐ  ¼   Ð¸          Ð¸Ð¼ÐµÐ½Ð°Ð¼Ð¸
    p      ubli   c void RemoveAllSameContacts2() t   hrows        Ex   ception               {
         NotebookTxtMappedDb no = new Not    ebookTxtMapped   Db("New.t   xt"  );
        no.addReco   rd("lily", "   10  0");
           no .remove("miky");
               asser  tF  alse(no.isNameExists("mike"));
    }

         @Test  /   / Ð£Ð´Ð°Ð»ÐµÐ½Ð¸Ðµ Ð½ÐµÑÑÑÐ   µÑÑ     Ð²ÑÑÑÐµÐ       ³Ð¾ ÐºÐ¾Ð½Ñ     Ð°ÐºÑÐ°
    public v   oid R  emoveN    oExContact(   )     throw     s Except   ion {
        NotebookTx tDb n = new NotebookTxtDb("New.txt");
        n   .remove(  "  Lily");
                 assertTrue(n.isNameExists("lily"  ));
    }

    @    Test  // Ð£Ð´Ð°Ð»ÐµÐ½Ð¸Ðµ Ð½ÐµÑ   ÑÑÐ      µÑÑÐ²ÑÑÑÐµÐ³Ð¾ Ð  ºÐ¾Ð½ÑÐ    °Ð  º   ÑÐ°
        public void RemoveNoExContac      t2() throws     Exception {
                    No             tebook TxtMappedDb no = n  ew NotebookTxtMappedDb("New.tx    t");
               no.addR   ecord("lily",     "10  0");
                 no.remove("Lily")               ;
        assertTrue(no.isNameExists("       l    ily"));
      }


    @Test  //    Ð   ¿Ð¾Ð¸Ñ  Ðº Ð¿Ð¾ Ð¸Ð¼ÐµÐ½Ð¸. Ð¢Ð°Ðº     Ð¾Ð³Ð¾ Ðº  Ð¾Ð½ÑÐ°ÐºÑ   Ð° Ð½Ð  µÑ      .
     public  void SearchN() throws E             xceptio        n {
          No  te bookTxtDb n      =  new NotebookT   x       tDb("New.txt");      
           Assert.asser    tEquals(null, n.s       earchByNa     me("ann   a"))         ;
       }


    @T  est  // Ð¿Ð¾Ð¸ÑÐº   Ð¿Ð¾ Ð½Ð¾Ð¼ÐµÑÑ. Ð¢Ð°Ðº Ð¾Ð³   Ð¾ ÐºÐ¾Ð½Ñ Ð°ÐºÑÐ° Ð½ÐµÑ.
    publi     c void Search    Ph() t hrows Exc   epti   on         {
        Not   ebookTxtDb n = new Not e    bookTxtDb("New.t    xt");
          Assert  .assertEquals(n  ull, n.s earchByPhone("199993"));
         }

    @Tes  t  / / Ð¿Ð¾Ð¸ÑÐº Ð¿Ð¾ Ð½Ð¾Ð¼ÐµÑÑ. Ð¢Ð     °ÐºÐ¾Ð³Ð¾ ÐºÐ¾Ð½ÑÐ°ÐºÑÐ° Ð½ÐµÑ.
    publi       c void   SearchPh2() throws Exception {
              Notebook        TxtMappedDb   no = new Notebo      ok    TxtMappedDb(  "       New.txt");
                no.a  ddRecord( "o l   imp",      "     95"   );
               no.addRecord("    j", "11");
        no.addRecord("imp", "0");
          no.add Reco     rd("i", "99")  ;
              no.sear  ch   ByPhone("    99");
        //n   o   .rem  ove("olimp");
          Assert.assert      Equals(null,  no.searchByPho  ne("999"));
    }

         @Test  // Ð¿Ð¾Ð  ¸ÑÐº Ð¿Ð¾ Ð¸  Ð¼ÐµÐ½Ð¸.    Ð¡ÑÑÐµÑ  Ñ    Ð²Ð¾Ð²Ð°Ð½Ð¸Ðµ  >1 ÐºÐ¾Ð½ÑÐ°Ð    ºÑÐ¾Ð² Ñ ÑÐ°ÐºÐ¸Ð¼ Ð¸Ð¼ÐµÐ½ÐµÐ¼ .
           publi   c void Sear  ch  One()   throws Exception {
            NotebookTxtDb n =    new    Notebo   ok TxtDb("New.t   xt");
        n.a ddRecord("ol    imp", "  1 1");
        n.addRecord    (  "    o  li       mp",    "0"   );
        n.addR       ecord("ol   imp", "868   6");
              Assert.assertEquals("11",     n.searchBy       Nam   e("olimp"))  ;

    }

    @Test  //Ð¿ÑÐ¾Ð²Ðµ    ÑÐºÐ° map Ð¿ÑÐ¸ Ñ   Ð¾Ð·Ð´Ð°Ð½Ð¸Ð¸ Ð½Ð¾Ð  ²ÑÑ  Ð¾Ð± ÑÐµÐºÑÐ¾Ð²  ÐºÐ»Ð°ÑÑÐ°
    public voi   d Proverka()   throws   Exc  eption {
        N  otebookDb db = new NotebookT    xtMappedDb("db.txt");
            db.addRecord(   "name1", "ph on      e");
        NotebookDb d  b1 = new NotebookTxtMa ppedDb("db.txt");
              ass    er  tTrue(db   .isN ameE      xis  ts("name1"  ));
        }

    @Te             st      // ÑÑÑÐµÑÑÐ²ÑÐµÑ   Ð»Ð¸ Ð¸Ð¼Ñ, ÐºÐ¾ÑÐ¾ÑÐ¾Ð   ³Ð¾ Ð½ÐµÑ Ð²      ÑÐ¿Ð¸ÑÐºÐµ -     > false        
    public     void NotExName2() throws Exception {
              NotebookTxtMappedDb no = new NotebookTxtMappedD b("New.txt");
        no.addReco     rd("miky", "8");
        no.addRecord("mik y"     , "8");
        no.addR    ecord("miky", "8");
        assertFalse(no.isName  Exists("sara"));

    }


    @Test  // Ð¿Ð¾Ð¸ÑÐº Ð¿Ð¾ Ð¸Ð¼Ðµ  Ð½Ð¸. Ð¢Ð°ÐºÐ¾Ð³Ð¾ ÐºÐ¾Ð½ÑÐ°         ÐºÑ  Ð° Ð½ÐµÑ.
    public      voi d Se     a   rchN2() t   hrows Exception {
           NotebookTxt   MappedDb no = new NotebookTxtMappedDb("New.txt");
         As  sert.assertE quals(null, no.searchByName("anna"));
    }

 @Test  // Ð¿Ð¾Ð¸ÑÐº Ð¿Ð¾ Ð¸Ð¼ÐµÐ½Ð¸. Ð¡ÑÑÐµÑÑÐ²Ð¾Ð²Ð°Ð½Ð¸Ð µ >1 ÐºÐ¾Ð½ÑÐ°ÐºÑÐ¾Ð² Ñ ÑÐ°ÐºÐ¸Ð¼ Ð¸Ð¼ÐµÐ½Ðµ  Ð¼.
     public void SearchOne2() throws E   x   ception {
        NotebookTxtMappedDb no = new NotebookTxtMappedDb("N   ew.txt");
            no.addRecord("olimp", "11");
        no.addRecord("olimp", "0");
        no.addRecord("olimp", "8686");
        Assert.assertEquals("11", no.searchByName("olimp"));

    }


}
*/
