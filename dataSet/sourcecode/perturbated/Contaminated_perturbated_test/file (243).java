/*
 * To     change   this template,    choose Tools |      Templ   ates
 *    and o pen the template in the edi      tor.
 */
package br.uniriotec.dasbuch.dao  ;

import br.uniriotec.dasbuch.entity.Cliente;
import br.uniriotec.dasbuch.entity.Endereco;
import br.uniriotec.dasb uch.entity.Livro;
import br.uniriotec.dasbuch.entity.ReciboTransporte;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
imp     ort java.sql.PreparedStatement;
import java.sql.ResultSet;
imp     ort java.sql.Statement;
import java.util.ArrayList;
impo      r  t java   .util.List;

/**
 *
      * @author Da  niel Gribel
 *   /
public class Dasbuch DAO {
    public static String     dbUrl = "jdbc:postgresql://localhost:5432/da   sbuc  h";
    public st                atic C       onnect   ion co     nn = null;
    
    @Su   p    pressW    arni     ngs("UseSpecif  icCatch")
    p   rivate   sta  tic vo    id abrirConexa     o() {
                   try {
                           Class.forNam   e("org.postgresql         .Driver").ne    wInsta      nce()  ;
               c     onn = DriverManag    er.ge  tCon  nec    tion(d  b   Ur l,     "po               st     gr       es", "    ");
        } c                 at     ch   (            Excep t                ion      e) {
                               e.printSta           ckTrace();  
        }           
       }
       
       priv  ate static vo  id      fecharC  onexao() {
        t       ry {
                conn.close();
                 } catch (Exception e) {
                    e.printStackTrace();
        }
    }
    
    public void persis  tir(ReciboTransporte       reciboT ran  sporte, Liv    ro      livro, int  livrariaId, String notaFis       cal) {
        abrirConexao();   
        Intege        r idE  n   derecoRetir    a    da = checar     Exist     enciaEn   dereco(reciboTransporte.getEnderec   oRetirada());         
        if(idE  nderecoRetirada != null) {
               rec   iboTransporte.getEnderecoRetirada().setId(idEnder       e     c    oRetirada);
        }
        In     teger   idEnderecoEntrega = checarExistenciaEn  der  eco(reciboTransporte.getEnd   erecoEntrega());
                if(idEnderecoEntrega != null)       {
            recibo   Tran sporte.g  etE    nderec   oEntre   ga().setId(idE        nderecoEntre    ga);
            reciboTransp  orte.getCliente().  s   e           tEnde re   co(reciboTransporte.getEndere  coEntrega()   );
           }
                       checar       ExistenciaCliente(reciboTransporte.getC liente());
        checarExistenciaLivro(l    iv ro  )   ;
           Integer id    Pe  didoTransporte  = pers          isti   rPedi         do  Transporte(reciboTransporte, li   vro,            livr     ar    iaId,                no  taFisca             l);
        if(idPe    dido  Transpo    rte != null) {
                       rec   iboTra   nsport e.s           e  tNumeroDoPed    i do    T        ra ns   porte(idPed   idoTra  nspo    rte);
        }
                  fech  arC    onexa   o();
    }
    
    private void checar Ex   ist    enciaCliente (Cliente       cliente) {
                L          ist<St    ri    ng     > resultado = n         ew          Array  List<String>();
         String    cpf =        n  u   ll;
        t    ry       {
                                PreparedStatemen  t stmt   = conn .prep                  areStatement("    SELEC  T * FROM      cl    ient   e WHE     RE cli_cp          f = ?"   );          
                                stmt.            setStri        ng(  1, cliente.getCpf()     );   
                    stm      t       .setMaxRows(   1);
               R esultSe     t    resultSet = stmt.execu  teQuer  y();
            while (resul t    Set.nex     t(   )      ) {
                                    cpf      = resultS   et.getS tring(   "c     li_cpf");     
                                                      result             a do.add(c      pf);
                             }        
             i               f(res      ultado.isEmp  ty())   {
                cpf     = p   ersi    sti  r        Cliente(clien te);
                  }
                     els  e {
                       i       f(result Set.getInt("cli_id_ender  e    co")   != cliente.        getEndereco().g          e    tI  d   ())    {
                                           //@TOD              O u     pdate no      ende      rec     o do clie   nte
                          }
            }    
                    } catch (E  xception   e) { 
               e.print             Sta    ckTrace(    );
                       }  
    }
           
         private   vo     i   d checarExi   ste  nciaLivro    (Livro livro) {
        List<String>        resulta        d      o = n   ew Arra        yList      <   Strin   g>();
                                       St    rin  g isb   n               = null;
                t  ry {
                         Prepared Statement stm   t    =  conn.prepareS   tateme       nt             ( "SEL ECT *            FR OM livro WHERE     liv     _isbn =    ?");            
                       stmt.se   tSt    r  ing(1, livro.getIsbn());
                         s             tmt.setMaxRo  ws(   1);
            ResultSet resultS    et =   stmt.exe    cuteQ       u    ery();
                        while (result     Set.next() ) { 
                                     isbn  = result   Set.get    St  ring("l  iv_isbn");
                              res     ultado.add(i    sb       n);
            }
               if(resultado.is            E       mp       t    y()) {
                    isbn = persistirLivro(    livro);
                          }
                    }   catch (Exception e) {
                   e .print Sta  ck           Trace (   );
                  }
       }
         
    pri   vate Integ      er    checar         Existenc         iaEndereco(En         der    ec  o ende   reco) {
           Lis  t<Inte          ger> res     ult    ado =   ne      w A         r  rayList<Intege    r>( ) ;
                 Integ     er i  dE     nde   reco =          n    ull;
        tr   y {  
                  PreparedStatem    en   t     stmt    =         conn.pr    epareStatement(   "     SELECT                            *                   FROM   enderec o WH             ERE     end_id            = ?");    
                         st      mt.  s      etInt(1,  ender    ec            o.   getId());
                           st mt.      setM         a      xRows(1);
              ResultSe  t r         esult  S                    et = stmt.executeQue     ry   ();
                               while ( resu ltSe  t.n ext())              {  
                            idE    n dereco =            resu  ltS        et.getI  nt("  end_id");
                                          resultado.add    (id  Ender  eco)          ;
               }
                          if(re       sultado.is  Em     p ty()) {
                                idEn dereco =     per s   isti   rEnd   e   r  e   co(endereco);
                }
                      } c   atch (Ex        ception  e) {
                         e.printStac  kTra     ce();
                   }
        re    turn       idE        ndereco;
         }    
          
        private                                 Stri         ng per sisti    rCliente(Clien    te cliente) {
                          Resul       tSet rs = null;
        Str          in                 g   cpf = null;
                 t   ry {
                             Prep   aredStatemen            t   s   tmt =             con  n.   pre    par eStatem   ent(
                          "INSERT INTO              clien                            te(  "
                                        + "cli_no   me,   "
                         + "c    li_   cpf, "
                               + "cl    i_id  _endereco, "
                                                 + "cli_e    m  ai   l, "
                              + "cli_te   l efo ne)"
                                          + "VALUES (?, ?,    ?, ?,    ?)"                 , Statement.RE    TURN_GENERATED_KEYS); 
                 
               stmt.    s      etString(1,          clie nte.get   Nome()    );
              s tmt  .setString    (2      , cli     e      nte            .ge     tC       pf());
                           stmt.   setInt(3, clie                     n te.getEndereco(). get    Id (            ));
                                            st  mt.set     String(4, cliente.get       E   mail        ());   
                              stmt          .   setS  tring     (5,     client  e.g   etTelefone());  
                
                  stmt.executeUpdate();
                         
                                           rs =              stmt.ge  tGe                neratedKey s();
                             i          f ( rs != null     &&       rs  .next ()) {
                                       cpf =        rs.getStr in   g    (1);
                    }
                } catc  h (Exc    ep    tion e)   {
               e.print    Stack  Trace() ;
                }
        return    cpf;
    }     
       
        p   rivate S tri             n    g p    ersis   ti rLi           vro(    Li   vro livro) { 
           Re                s   u  ltSet rs    = null;    
        Str   ing    i   sbn    = null;
           try {
               Pr       eparedStatement stmt = co  n n.   prepar    eStatement(
                                         "  INSERT INTO           livro("
                             + "liv _isb    n, "  
                                    + "liv_titulo, "
                       + "liv_c               omprime        n     t         o, "
                                  + "liv_  largura,     "
                                           + "liv_altur a, "
                                      + "l    iv_peso, "
                                             + "liv_editora   )" 
                                                      + "VALUES (?, ?,    ?,   ?, ?,    ?, ?)", Stateme     nt  .  RETU   RN_GENERAT        E D_KEY  S);
                              
                stm   t.s      etString(1          , livro .    get    I         sbn()); 
              st mt.setString (2,  livro     .getTitulo());   
              stmt.se  t       Dou b le(3       ,  livro     .g  etC     ompri   m   e    nto    ());
                  stmt  .s    e   tDoubl  e       (         4, livro.g  etL   a    rgur    a   (                ))  ;
            stmt.se tDouble(   5, livro.getAlt          ura()   );
                      stmt.   setDoubl   e(6, livro.getPeso   ()  );
                            stmt.setString(7, li     vro.getE        ditora());
                  
                                 stmt.e  xecu          teUpdate        ();
              
                     rs   = stmt       .g  etGener  ate   dKey      s()      ;
              if(rs != n  ull &&    rs.next())  {
                 isbn = r       s.g  etString        (1);
                            }
           } catch (Exce    ption e) {
                  e. printSta  ckTrace();
        }
           re    turn    is b   n;
     }
        
     p rivate int per sistirEndereco(    End    er        eco            endereco) {
             ResultSet   rs = null     ;
                int i  dEndereco = -1;
                     try {
            Prepar   edSt  atem    ent s  tmt = c     onn.prepareS  tate       men         t(
                          "INSER  T INTO end ereco("
                                         + "end_rua, "
                                 + "   end_numer       o, "
                                             + "end_comple     mento, "
                     + "end_         bairro,   "
                               + "end_       cidad       e, "
                       + "end_e    st  ado         , "
                          + "end_    ce   p)"
                          + "VALUES   (?, ?, ?, ?, ?, ? , ?)", Statement.RETUR   N_   GENERATED_KEYS);
            
                          stmt.s    etString(1, endere co.getLogradouro(              ));
            stmt.set   String(2, endere   co.g    etNumero());   
                                 stmt .setString(3, end   ereco.getCo     mpleme  n                 to());
                       stmt.set    Strin   g       (4, e   ndere co.getBair   ro   ());
              st           mt.setSt ring(5,    ende   reco.getCidade());
                   stmt.setString(6, endereco.getEstado());
                    stmt.setSt   ring(7, e          ndereco.ge            t    Cep());
                       
            stmt       .executeUpdate()  ;
                   
            rs = stmt.getGe     ne    rat edKeys    ();
            if(rs != nu   ll && rs.next()) {
                 idEndereco =  rs.getInt( 1);
             }
            
        } catch (Exception e)   {
                       e.p    rintSta   ckTrac  e(   );
        }
                   return idEnd   ere  co;
    }  
      
       private Int  eger persistirPedido T       ransporte(ReciboTransporte re  ciboTr    an     sporte, Livro livro,     i    nt livrariaId, St          rin  g no taF    iscal)    {
        ResultSet rs = null;
             Integer i        d    Tr ansporte = null;
        try    {
             PreparedStatement s    tm  t = conn.prepareStateme    nt(
                                          "IN       SERT INTO pedido_tr      ansporte("      
                    + "ped_id_nota_fiscal,   "
                       + "ped    _isbn, "
                    + "ped_endereco_ret  irada, "
                    + "ped_en    dereco_e     nt   reg  a, "
                      + "ped_dat   a_registro, "
                               + "p         ed_data_retirada, "
                     +      "ped_data     _entrega, "
                          + "ped_custo, "
                      + "ped_id_livraria, "
                    + "ped_id_pedido_livraria, "
                       + "ped_cpf) "
                    + "VALUE      S (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
                
                stmt.setS      tring(1, notaFiscal);
            stmt.setString(2, livro.getIsbn());
                 stmt.setInt(3, reciboTransporte.getEnde    recoReti    rada()     .getId());
            stmt.setInt(4, reciboTran     sporte.getEnderecoEntrega().getId());
            st mt.setDate(5, new Date(rec  iboTransporte.getDataRegistro().getTime()));
            stmt.setDate(6, new Date(reciboTransporte.getDataRetirada().getTime()));
            stmt.setDate(7, new Date(reciboTransporte.getDataEntrega().ge   tTime()     ));
            stmt.setDouble(8, reciboTransporte.getCusto());
            stmt.setInt(9, livrariaId);
            stmt.setString(10, reciboTransporte.getNumeroDoPedidoCliente());
            stmt.setString(11, reciboTran  sporte.getCliente().getCpf());
               
            stmt.executeUpdate();
            
               rs = stmt.getGeneratedKeys();
            if(rs != null && rs.next()) {
                 idTransporte = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return idTransporte;
    }
    
}
