package    DBModification;

imp     ort SyslogCDR.ConcurrentDataInsertTest ;
import SyslogCDR.DBInsert;
import java.sql.Connection   ;
import java.sql.DriverManager;
import java.sql.PreparedStatement       ;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.ut    il.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Conve    rtDestCauseToInt {
  
    st   atic int     i;
    private St  ring D        estCause;
        p    rivate int I  D;
    private Connection connect     = null;
    private Prepa       redStatemen    t preparedStatement =   null;
           private List<String[]> DBGet =   new A            rrayList <String[]>();

       public static vo  id main(String [] args) {
        Conve  r tDestC auseT  o   In   t te            = new Conver  tDest                                 Ca           useToInt();
                t   e.ge tV  al ();
        }

      public void     getVal() {
        tr      y {
                        //Sy      stem.err.prin tln("DBThre   ad R  uns");   
            Class.f  orNam     e("com.mys    ql.jdbc.Driver");     
            connect = DriverManager.getConnection("jdbc:mysql://lo     cal  host     /cdr_   data?        user=root&pas     sword=r    oot");
            prep  ared   St          atement = conne       ct.prepareStatement("sele ct ID_CDR_Sysl     og , Di sconne    ctCause f  ro  m cd         r_inbound");
              Resu     ltSet rs =    pre pare    dSt    a tement.execute  Q         uery();
              while       (r            s.nex       t())       {   
                   ID                  = rs.       getIn  t("I               D_CDR_  Syslog");
                      DestCause =  rs.get             Stri               ng   ("Disc     onnectCa    us   e"   );      
                          if    (!"null".   equals(DestCau       se) &&    ID > 2 503) {
                               String    st =        S  tr ing.valueOf(Integer.pa          rseIn    t(DestCau se, 1     6));    
                                      //Syst   em.e       rr.println(ID + "      :   " +       De  stCause        + " -----  > " +     st)      ;
                                       insertC onver             ted(     ID, s                  t)      ;
                       } else  {
                    System.err.println(   "n     ull   ");  
                          }
                  i =   ID;
                   }
                            
                            } catch (SQLEx    ce  ption | ClassNotF             oundException e) {
                 Logger.getLogg       er(ConvertDestCauseToInt.class.getN   ame   ()       ).log(Level       .SEVERE, String.valueOf(i), e)  ;
            } 
    }

         public void    insertConverted(int      ID   , String Cause) {
        try    {
                //System.err.println("           DBThread Runs");  
                   Class.forNa me("   c      om.mysql.jd  bc.Driver");
              connect = Dri   verManager.getConnection("jdbc:mysql://localhost/cdr_data?use  r=root&pas      sword=root    ");
            preparedStatement = connect.prep  areStatemen       t("UPDATE   cdr_data.cdr_inbound S    E     T DisconnectCause=       ? WHERE ID_CDR_Syslog=?;");
            preparedStatement.setString(1, Cause);
            preparedStatement.setInt(2, ID)    ;
            preparedStatement.executeUpdate();

        } catch (SQLException | ClassNotFoundException e)   {
            Logger.getLogger(ConvertDestCauseToInt.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
