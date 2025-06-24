/*
      * To c   hange this t       emplate        , choose Tools        |   Templates
 * and open the template in      the    edi  to    r.
 */
package DBHelper;

import Utilities  .Consta    nts;
import java.sql.*;
impo   rt java.util.ArrayList;
import java.util.Date;
i    mport java.util.HashMap;
import ja   va.util.logging.Level;
import java.util.logging.Logg  er;     
import model.Customer;
    
/**
 *
    * @author Administ  rator
      */
pub   lic class C    ustomerDBH     el  per {

    C onnection   c    on = null;
       Statement stat   ement;

    p   ubl     ic      CustomerDBHelper() {
        try {
              Cl    as s.forN     ame("org.sqlite.  JDBC");
                          con = D riverManager.   getCon       nection("jdbc:sqlite:infog ym.s3db")    ;

                sta   temen   t = con.     createStat    emen  t();
     


        } catch (SQLExce     ption ex) {
            L         ogger.getLog  ger(CustomerDBHelpe   r.clas  s.getName(          )).lo g(    Level.SEV       ERE, null, ex  );
        } catch (ClassNotFoundE     xcept   ion ex)         {
            Logger.getLo       gger    (Custome   rDB Hel per.clas  s.g  e tName()) . log(Level.SEVE        RE, nu   l   l    , ex);
           }

         }

    public void sa    veCustomer(Custom   er custo     mer) {
        String  query = "";




               qu     ery = "insert into customer(first_name,last_name,address,tel_no,m  obile_    no,date_of_birth,da te    _      of_joining,membershipid, gender,active   ,marketing_channel_id) "
                + "values('&firstname','&lastname','&           address','&tel_no'       ,'&mobile_no','&dob','&doj','&members  hipid','&gender'     ,'&active'   ,'&marketing_channel_id');";

              quer    y =   query.replace("&fi     rstname", cust    omer.g  etF      ir     st_name   ());
              query = query.r   eplace      ("&lastname", customer.getL ast_na  me()        );
        query = query.replace("&  ad   dress", c    ust omer.getAddre    ss());
        query     = query.repla  ce("&tel  _no", customer.getTel());
        query = query.replace("&m      obi le_no", customer.getMo          bile())   ;
        query = query.replace("&dob   ", customer.   getDob() + "");
          query = query.replace("&d oj", c    ustom     er.getDoj() +       "");
        query = query.rep  l    a   ce("&membershipid"  , custome      r.getM   embership().get      Id()       + "");
             query = query.replace("   &ge  nder"     , cust    ome   r.getG    ender());
        quer   y = query.replace("& active", c      ustomer.isActive(      )+""   ) ;  
             que    ry =   query.replace("&    marke      ting_channel_id", c    usto mer.getMarketCha     nnel().getId() + "")  ;
        tr y {
                statement.exe       c        ute(query     )    ;
        } catch (            SQLExcepti    on ex) {
              Logge  r .getLogger(CustomerDBHelper.          cl  ass         .g    etName() ).log(Level.SEVERE, null     , ex) ;
        }

       }
        
    public Ar           r a  yList<Custome   r> get  Cust   ome     rs (String searchType,  Strin  g      sear c    hBy) {
                 ArrayList<Cu   stomer> customerList = new ArrayList    <Cu       sto    m       e                r>();
        S   t    ring query = "select * fr     o  m cu  stomer whe   re &searchtype like   '%&s     earchBy%'";
                 Const     ants c      onstants    = n ew Consta      nts();  
                    HashMap<S   t           ring, String> m   ap = c       onstants.searchTypeToColum   n;
                          if (sea    rchType.equalsIgnoreCase("All")) {
                         que  r          y = "     select           * fr       om cu  stomer      ";
        } el       se {
                  q uer  y = quer   y.repla ce("&searchtype", ma p.get(      searc  hT   ype));
              query     = qu    er         y   .replace("&searchBy", searchBy);

          }
        t   ry {         
              ResultSet    rs   = statement.executeQue    r y        (query);   
                    whil       e (  rs.next())  {
                         C   us    tomer cust = new  Customer(           );
                                 cust.setId(rs.getInt(1));
                                            c ust.setFir       st_name(rs.get       Stri                        n     g(2));
                                 cust. s                  etLast  _na   me(rs.     getString(3))   ;
                cust   .s      et    Address(    rs       .getString   (4));
                   cust.  setTe      l   (rs.get      String(5));
                cust  .setMobile(rs.getS     t  ring   (6));
                       cust.s    e    tD  o  b(rs.getTime  stamp(7));
                  cust.s    etDoj    (r s.getTimestamp(8 ));


                              cu   s tomerList.add(cust);  

                     }
               }         catch    (S      QLE      xcep    ti on e     x)      {
                    Log   ge r.get    Logger(Cu   sto     merDBH elper.class.    getName()).log(Level.SEVER E, n ull , ex         );
                      }

         

              return custo  merList;
    }   

           public Customer getCu   s        t      omer(int       id){
                 Custome        r cust = new       Cu     stomer();
        String    query      ="select   * f     rom customer wh                     ere        id = '&id'";
             query = q   uery    .replace("&id",   id+"   ");
        tr       y    {
                   Re   sultSet        rs =         statement.executeQuery   (query);
            i f(rs.n    e  xt(    )){
                        cust.se       tId(r s.getInt(1))    ;
                            cust.set   Firs   t_name(rs.getString(2));
                       cust.set Last_name(rs.getString(3));
                cust.setA   d   dress(rs.getString(4));
                           cust.setTel(rs.ge tString(5)  );
                   cust.setMobile(rs.getS  tr  ing(6))       ;
                             c ust.setDob(rs.getTimestam p(7));
                       cust. setDoj(rs.getTimestamp(8)); 
                          
              }
          } catch (SQLException ex) {
            Logger.getLogger(CustomerDBHelper.class.getName()).l  og(Leve   l.SEVERE, null, ex);
        }
            
            
                
        r    eturn cust;
    }
  
    public static void main(String[] args) {
        Cu  stomer cust = new Customer();
        cust .setFirst_name("ankit");

        cust.setLast_name("kaushal");
        cust.setAddress("dwarka");
          cust.setDob(new Timestamp(new Date().getTime()));
        cust.setDoj(new Timestamp(new Date().getTime()));
        cust.setTel("25085435");
         cust.setMobile("9818536576");
      //  cust.setMembership   ("yearly");
        CustomerDBHelper dbh = new CustomerDBHelper();
        dbh.saveCustomer(cust);



    }
}
