/*
    * T  o c     hange this temp  late, choose Tool    s | Templ   ates
 * and open the te    mplat                 e in the editor.
 */
package ClassesDAO;

import       Classes.ContasReceber;
import Excessoes.BancoException;
import br.com.seeds.FazerConex      ao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
impor   t java.sql.S   QLException;     
import java.util.ArrayList;
import java.util.List;
import java.util.l    ogging.Lev     el;
import jav  a.util.logging.Logger;

/**
 *
 * @author      G       uilherm e August   o
 *    /
public cla  ss ContasReceberDAO {

        Co nnec  tion      con        exao;
       private       Str ing    sql;
  
     public C  ontasRec eberDAO   () throws BancoException   {
        conexao =  (Co nn   ection)    new Faze                   rCon   exa     o().fa      zerCo      nexaoBanco();
    }

    public   void desconectar() {
              try          {
                               conexao.close();
          }         cat  ch (SQLException ex)  {
              Logger   .getLogger      (TurmasDAO.cla s    s.g    etName()).log(Level.    SE    VERE, null  , ex);
                     }
            conexao = n            ull     ;
    }   

                      public       voi      d ad    icion       arContaR(ContasRec eber      conta)  throws BancoExcepti           o     n {
        sql      = "INSERT INTO CONTA     S_RECEBER  (           DESCRI         C  AO   , EM ISSAO,"
                         +               " VEN        CIMENTO, V          AL    OR        ,"
                             + " STATUS) V      ALU  ES ( ?,      ?, ?, ?,    ? )     "     ;            
        tr   y {
            Prepared     Sta    tement  g    u i  =    c     onexao.p      r    epar   eStatement(sql);
                       gu     i        .setStr          ing(1, conta.ge     tDescri  cao());
                       gui.setString  (2        , conta.g  et   E         missao());
                gui.se           tString(                              3, conta   .getVenci          mento());
            gui.se  tFloat(4, con   ta.getValor(  ));           
                    gui.s   etIn    t(5        , conta.get        Stat      us());

                                       gui.execute();    // se nÃ£o precisar r    esg     atar dados
            } c     atch     (SQLExcep            tion     e         ) {
             throw new BancoEx     ce     pti  on(    "" + e             )   ;
           }
    }

                   public List< Co        ntasR ec    eber> pesquisac     ontasReceb    er(          String     pes,           S   tr ing pesCpf  ) t   h    rows BancoException {
             char    as        pas = '"';
           S       t r   i        ng sql;
               if    (pes.equ  a ls     ("") ||  p       es == null)    { //    || pes    Cpf.equals("")   ) {
               sql        = "Sel   ect         * from   CONT   AS_RECEBER";
         } else {  
              sq          l   = "S   elect   * from CONTAS_REC   EBER          where cod     i     go like              " + asp as + "%      "   + pe    s + "%" + aspas + "and status"
                                               + "     lik   e" +    asp  as +       "%" + pesCpf +     "%" + as       pas   ;
        }

            try {
                    Prepared  S   tatement gui    =      c   o     nexao   . prepareStat       ement   (sql);  
                 Resu  lt        Set banco = gui  .executeQuery   ();
  
                                  L      ist<Cont   asRe  ceber> list         a = new  ArrayList<Cont    asR     eceber     > () ; 
                whil   e (banco     .next()) // sÃ³           entra se      existi    r 
                   {
                   ContasReceber despesas = new Co     ntasRece ber()   ;
                           despesas.setCo          dig    o(banco.get Int("codigo"));
                   despesas.setDescricao (ban c    o       . getStri  ng("   descri               cao"));
                    d     espesas.se   t  Em            issao(ba  n     c  o.getString("emis   sa  o"));
                   despesas.set      Ven       cimento(  ban   co.getStri      ng("venc     imento"));  
                     despe                    sas   .s     etV   alor(banco.get    Float         ("v  alor") );
                d   esp   esas.setSt  atus(banco.g              et   Int(   "statu   s"));
                  lista.add(despe    sas);
                   }   
                ban    co.close();
                       g     ui  .close();
                         return l   ista;
         } catc      h (SQLExce    ption e) {
               thr ow new  B  ancoExcep    tion("" + e);
                 }
    }  

        public vo id atual  i    zaDad    os(ContasReceber cl  i    ente)    t   hrow   s BancoE   xceptio  n {
                     sql = "UPDATE CONTAS_RECEBER SET DESCRICAO = ?,"
                         + " EM    ISSAO = ?, V   ENCI   ME    NTO   = ?  , VALOR = ?  ,"
                 + " S        TATUS = ?   WHERE   CODIGO = ?  ";
                   tr      y {
                                 PreparedStatement guiAltera   r  = conexao.p repareStat    e   m  en  t(    sq l);
              guiA  lt   erar.setString(1, cli  ente.getDe    scric     ao  ());
                    gu iAlterar. set     Str   ing(2, clie     n      te   .getEmissao      ());
              guiA        ltera   r.set   St          ring(3,    cliente.getVencimento(  ));
                         guiAltera   r.setFloat(4, cli  ente      .getValor()   );
                guiAlterar.  s     etInt(5, cliente.getSta     tus());
            guiAlte     rar.  setInt(6,    c    liente.    getCo dig           o(   ));

                 g u    i A   l   terar.e   xec   ute();
                  g  uiAlterar.cl os  e();
        }    catch (SQLExc   eption e) {       
            throw new BancoExc    eptio   n("" + e)    ;
        }
     }

        publi    c Contas    Receber carregarContasRPeloC o    d   igo(S tring    nome) throws BancoException      {
          Con    tas Rece be   r       conta            s = new    ContasRecebe r();
          cont    as.set  Emis         sao("nulo");
        sq l =       "S  E LEC  T * FROM      CONTAS_RECEBER      WH   ERE CO  DIGO = ?";
         try     {
            Prepa     redStateme   nt gu        i = conexao.pre      par          e Statement(sql);
            gui.se   tStr              i   ng(1      , n   om  e);
                   R         e      sultS    et banco      = gui .executeQuery      (    );
            while (ba    nco.next()) // sÃ³ en  tra se existir 
                             {        
    
                co     nta                 s.      setC   o        digo(    b     anco.g   etInt("c    odig   o"));
                co  ntas.setDescricao(banc     o.get  Stri             ng("desc     ric                     ao"));
                               con   tas.setEmissao(banco.getString(" e            miss     ao"));
                     contas.setVencimento(banco.getString("ve    nci   mento"));
                     contas.s   etValor(banco.getFloa    t(  "valor"));
                     contas.setStatus(ba           nco.   getI    nt("status   "));
              }
             gui.   cl   ose();
                                banco.clo    se();
            return co    ntas;  
               }   catch (SQLExce   pti    on e) {   
                                        throw new BancoExcept    i on   (""    +   e  );         
         }
       }

           public v       oid deletar  ContasR(ContasReceber controle)     throws BancoEx  cepti    on {
        sql = "DELETE FR   OM CONTA S_REC  EBER where       CODIGO = ?      ";
              try {
               PreparedSt  atem ent cont     rol = cone  xao.prepareSt  atement(sql);
    
                  control.setInt(1, contro    le.get   Codigo());

                           control.execute(); // se nÃ£o precisar   resgatar dados
            control.close();
               } catch   (SQLException e) {
                 throw new    BancoExc     eptio n("" + e);
             }
    }

    pub     lic Li     st<ContasReceber> pesq   u isa  R(String pes, String pesCpf)   t              hrows Banc   oException {
        ch  ar aspas = '"';
        S     tring sql;
          if (pes.equals("") || pes == null)         {/   / || pesCpf.equa  ls("")) {    
            sql =     "Select * from  contas_receber";
        } else {
                  sql = "Select *       from co ntas_receb er where descric ao like "      + as   pa s + "%" + pes + "%" + aspas;
        }


                try {
               PreparedStatement gui = conexao.prepareStatement(sql);
                ResultSe  t banco = gui.executeQuery();

                 List<ContasReceber> lista = new ArrayList<ContasRe   ceber>();
                 while (ba  nco.next()) // sÃ³ entra se existir 
            {
                     Co  ntasReceber produto = new Co      ntasReceber();
                produto.set   Codigo(banco.getInt( "codigo"));
                p    rodut    o.setDescricao(banco.getString("descricao"             ));
                produto.setEmissao(banco.getString("emissao"));
                  produto.s   etVencim    ento(banco.getString("vencimento"));
                produt    o.setVa    lor(banco.getFloat("valor"));
                produto.setStatus(banco.getInt("status"));

                li   sta.add(produto);
            }
            banco.close();
            gui.close();
            return lista;
        } catch (SQLException e) {
            throw new BancoException("" + e);
        }
    }
}
