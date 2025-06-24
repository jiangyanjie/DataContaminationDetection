package    projetvelov.accesBD;



import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import  java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

pu   blic class      AccesBDInf   o {

       private    Connection c      onnexio          nBD;

               public AccesBD Info() {
          this.connexionBD = Conn        ex     ionOracleFactory.cree    rConnexion(    );
    }
       private static AccesBDI  nf  o i  n   stance    = null;         

    public  s   tati c AccesBDI   nfo g             etInst         ance()  {
           if (ins    t       ance     =     = null) {
                     i   nsta           nce = new AccesB DI  n     fo();
              }
        return  i   ns     tanc    e;    
          }

    p     ubl    ic void Verif i     erConnexion(String pseudo, String md   p) throw  s   SQLExc     e    ption {
        try {
                         String sql = "select * from connex     ion where      iden    tifia nt = ? and         motdepasse =  ?";
                Pr  epar   ed    State    men  t  p   reparedStatement = connexion  BD   .pr    epareStatement(sql);
                prepa   redS  tatement.set  S   tr  in                       g(1, pse   udo);
                preparedStateme      nt.setStrin   g(2, m   d       p)      ;
                                       ResultSet re  sul    t   = preparedStatemen   t.execu  teQ  uery();    
                       res           ult.next();
                System.out  .pr        intln         (resu       lt);
                                    
                       if (result     ==        nu                 ll) {
                      J    OptionPane.showMessageDialog(null,             "L    'identifiant ou le           mot   de passe    est incorrect.",    "Erreur C     onn exion    ", J        OptionPane.ERROR_MESSAGE);
                   } else {
                     JOptionPane.showMessa          geDialog(null, "Connex  ion rÃ ©ussie.", "Erreur Connexion", JOptio  nPane.ERROR_ME  SS  A  GE);
                    }
            result.close();
            preparedStatement.close();
        } catch (SQLException | HeadlessException e) {
            System.out.println(e.getMessage());    
        }
    }
}