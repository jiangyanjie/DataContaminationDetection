/*
 * To change this   template, choose Tools   | Templates
 * and open the templa te in    the ed      itor.
 */
package server.database;

import java.util.logging.Level;
import java.util.logging.Logger;
impo     rt server.API;
import org.junit.*;
import shared.commun  ication.*;

/**
 *
 * @author schuyler   
      *  /
public  c   lass            API    Test  {
       
         private API api;
    pr ivate Dat  abase dat  abase;
      
    publ       ic A PITest() throws API.APIException {
        t ry {
              D  at      abase.initialize();
                   da tab   ase = new    D     ataba  se();
             } cat  ch (Database.DatabaseExcept   ion  ex)  {
                Logger.getLogger(A PITest.cl   ass       .getName()) .lo   g(Level.SEVERE                 , n   ull,   e           x     );
              }
      }
        
      @Befor            eClass
    public s    t        ati    c void     setUp  Class() {
         }   
    
      @AfterCla      ss
        public    stat   ic vo     id tearDownClass()       {
    }
     
               @Bef     or      e
    public void se             tUp()   {
          }
          
        @After
      public v   oid tearD     own()  {
    }
      
             @T                    est 
       public void val   i   dateUser() throws ValidateUser_Res  ult.Validate  User_ResultEx  cept   ion    {

        S    tring username = "HH";
        St   r      ing pas  swo  rd     = "hooops" ;
           V alidat    eUse   r_       Param params = new ValidateUs   er_Param(usernam    e, pas      sword);
                Validat  eUser_Result fakeResult = new Validate User_Result   (false);
              Val   idateUser_R        esult real   R   esult = API.validateUser( database, p    arams);
         Assert.ass   ertEquals(fakeResu    lt, realResult);
//        
//            ValidateU       ser_Pa       ram param s   2 = new Validat  eUser      _Param("        test1", "test1");
/ /        Va  lida  teUs er_Result fak eResult     2    = new  ValidateUser_Result    (tr    ue, 1, "Test", "One            ", 8); 
//         Va          lidateUser_Result          r ealResult2 =    api.v         alida teUser(params  2);
  //        Assert.a           ssertEq        uals(f      akeResult2, realRes ul t2);
          }   
    
   //               @Test
//      public void getP      rojects() {
//                                         
  //                                       String user    na  m   e        =         "test1";
//             String passw   ord =  "test1";     
//        GetProjects_Param params =  ne  w Get   P ro   jects_Par am(       user name , password   );
//                        GetProjects_Result       res         ult    = a  pi.getProjects(  par       am     s);
//                
//    }
//    
//    @Test
/   /    public void getSamp  l   eImag   e() {
//          
//                 S tring username =     "test1";
//        String password    = "test1  ";
//              Get                SampleImage_Param params = ne               w Ge   tSamp leImage_Param         (use   r     n    am    e, passwor      d,    1);
/      /               Ge    tSampleImage_Result     res  ult = api.getSampleI  mag  e(par ams);     
//        
/     /    }
//    
   //    @Test
//        publ ic vo   id downloa         dBatch   () {
  //           
//               String  user   nam  e = "test2";
//        Stri ng     pa      ssword = "   test2";
                //           int project    Id = 2;
//           DownloadBatch_Param params =        new Downl                oadBatch          _  Param(usern          a  me    ,           pass  w  ord,      projec       tId) ;    
//            Downlo    adBatch_Res    ult    resu   lt =  api.download   Batch(params);
//    }
//       
//    @Tes      t
        //    p       ubl     ic v   oi    d     submitBatch()  {
/     /        
  //                 Stri         ng use    r     name =  "te st1";
//                String   password = "  test1"    ;
  //           int batch     Id      = 1     ;
//                Str        ing   records =   "Lee,Robert,M,45;"
//                          +   "Rea,Botu        lism,F   ,15;  "
//                    + "Kay,Tay,F,42;  "       
//                                                              +     "Le  e,Ree,F,31;"
//                    + ",,aoe,;"
//                       + "Mar  ,Tar    ,M,98;   "
//                  + "Th      e Pira  te,Yar,M   ,56;"
//                        + "      Lee II,Robert,M,   23;";
     //           SubmitBatch_Param params    = new Su       bmi   tBatch_Param    (username, passw     ord, batchId, records);
//             SubmitBatch_Result r     esult = api.submitBatch(params);
//    }
//    
//    @Test
//    public void      getFields() throws API.APIException {
//        St      r  ing username     =   "test1";  
//        String  passw   ord = "test1";
//        Integer     projectId = new Integer(1);
//        GetF       ields_Param params = ne         w GetFields_Param(user         name, passwor   d,     projec  tId);
//        GetFie           lds_Result    result = ap  i.getFields(params     );
//        String projectString = "";
//        params = new GetF        ields_Param(username, password, projectString);
//        GetFields_Result result2 = api.getFields    (params);
//    }
//    
//    @Test
//    p  ublic void search() {
//        S   tring   username = "test1";
//        String password = "test1";
//        String fields = "10,3";
//        String values = "FR,F";
//        Search_Param params = new Search_Param(username, password, fields, val   ues);
//        Search_Result result = api.search(params);
//    }
    
}