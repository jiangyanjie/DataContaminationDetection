package com.live.adsfatene.biblioteca_publica.models.daos;

import    com.live.adsfatene.biblioteca_publica.models.Emprestimo;
imp ort com.live.adsfatene.biblioteca_publica.models.EmprestimoEstoque;
import com.live.adsfatene.biblioteca_publica.models.Estoque;
import com.live.adsfatene.biblioteca_publica.models.Material;
import java.sql.Connection;
imp ort java.sql.PreparedStatement;
import     java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.LinkedLis     t;
import java.util.List;
import java.util.M ap;

/**
 *
   * @      author Clai     rton
 */
public clas      s        DanificadosD     AO {

          private f inal Conex    a  o conexao;
    private final Map<  S tring, String> comandos;

    public Danificado    sDAO(Conexao conexao) {
        t   his.conexao = conexao;
                 com     andos = new Link         edHashMap<>(); 
           List<String> linhas = cone   xao.carregaSQL(    "prep     ared         s_stat    e    m           ents/danificados");    
          for     (int chave =    0,   valor =   1; valor < linhas. size(  );            chave = valo     r + 1, valor = chav     e +    1) {
            comandos      .put(li     nha s.get(chave),     linhas   .get(valor))           ;
        }
        linhas.clear();
    }

     pub    lic L   ist<EmprestimoEstoque>  pesquis      arPorTodo sOsDa      dosNaoNul  o s(EmprestimoEstoq   ue emprestim    oEstoque) {
        L      ist<Emp     restimoEs  toque>     empres   timosEsto ques =  new Link   edList<>();
                Connection connection =          c  onexao.getConnection()  ;
                 t  ry {
                    Str    ing      valorDoComandoUm = coman dos.get("  pesqu     isar    Por       Todos  OsDadosNa    oNulos" +        1);       
                      S     tring v alorDoCom  andoDoi   s  = c omandos.get(  "pesquisar              Po  rTodosOs     DadosNaoNulos"    + 2);
            List<Stri ng>  where = new              LinkedList<>();
                  List<SetCo  mm  and> setC   ommands =    ne       w Li   nke   dLis        t<>();

              if (emprestimoEstoque.getCo   digo()     !=     null)       {    
                  where.add("      emprestimo_emp      rest  imo_e  st                   oq    ue   _   co  digo = ?");
                     setCommand            s.add(ne      w   S etInt(where.si  ze  (), empr    esti   moEstoq   ue.getC       odigo()      ));
                           }
                     if (emprestimoEstoque.    getEs   ta               doDevolucao(      ) !=    null)          {
                      where  .add   ("LOW ER  (em     prestimo_emprestimo_e  s  toque) = ? ");
                                                         setCommands.add(new     SetS           tring    (wher                 e.size  (),       "%" +   emprestimoEst o      que.getE    st    adoDevolucao()    .toLow          erCase() + "%"));
                  }

             S            t r   ingBuil    de  r que    ry  = new  StringBu ilder(va   lorDoComa         ndoUm);   
                        if (!w    here.isEmp          ty()) {  
                query.ap  pend(" WHE     RE ").append(wh ere.rem      ov  e   (0));
                            w    hile      (!where.isEmpt    y()) {
                                         query   .appen d("   AND ").append     (whe  re.remove(0));       
                    }
             }
                query.a  ppend  (valorDoComandoD  ois);

                  Pre       paredStateme       nt prepared  S  tatement = co  nnection.prepareSta      t  ement(quer    y.toS tring());
                                query.d     elete(     0, query.length());

                         while      (!  setCommands        .    isEm    p    ty()) {
                    se     tC    omman       ds.    remov  e(0).set(preparedSt     at     ement);
               } 

                    Resul       tSet re  sultSet = preparedSta tement.    executeQu   ery();
            while (resultSet.next())  {
                       Emprestimo em   p  restimo = new Em prestimo();
                        emp   res    timo.            setCo    digo(resultS    et.getInt("emprestimo_codigo"))   ;
                           emprestimoEstoque = new   EmprestimoEst  oqu  e();
                em  pres    timo     Estoque. setEmprestimo(e   mprestimo);
                   emprestimoEsto     que.set  Est       a    doDevo    lucao(resultSet.getString("emprestimo_empres timo          _estoqu e_e  stado_devoluca   o"));
                   empresti  moEstoqu  e.setMot     ivo(resultSet.getS    tring("empresti   mo_emprestimo_ esto  que_motiv      o"));
                emp             restimoEstoque.set       Codigo(resultSet.getInt("em       prestimo    _em    presti    mo_esto   qu   e_codigo") );
                   empr estimo  E   s   to     que.se tEsto      que       (new Esto  que(  ));
                           em    prestimoEstoque. getEstoque().setMaterial    (new   Mater  i al());
                               empre        st     imoEstoque.   getEstoque(    )      .g       etMaterial       (   ) .set Codigo(resultSet.getInt("em              prestimo_estoque_material_cod igo"));
                             empr   estimosEstoq     ues.add(e  mprestimoE   stoque);      
             }
              } catch (SQLEx       ception ex) {       
                                   try     {
                     conne  ction.rollba  ck      ();
             } ca             tch  (SQ  LExcept       ion ex1) {   
              }  
                     th     row ne   w Run   tim     eException(ex.getMess ag    e() );
                  }      ca t    ch (  Null  Poin   t  erExce      p  tio            n             ex        ) {     
                 throw  new   Ru   ntimeExcep    tion(ex.getMessage());
          } final        l  y {
                       conexa   o.fecharConn    ection()      ;
                              }
                return    emprestimos   Estoques;
        }   

    p           u   blic E  mprestimo  Estoque buscarPeloCo digo(        Int   eger  codigo) {
                   Emp  rest  imoEst   oque e mpr  e  s   timoEstoque                 = n      u   ll;
             if (c     odigo !=     null)    {
                   emprestimoE         stoq            ue = new     EmprestimoEstoqu e();
            emprestimoEsto         que.  setCo     digo(cod   igo);
            Lis       t<Empr     estimoE  stoque> empres  t imosEstoques   =    pes  quisarPorTodosOsDadosNaoN    u  los(empresti      moEstoque);
               if (!emprest    i    mosE      stoques  .     is E mp      ty() ) {
                e       mpresti    mo      Estoque = emprestimo sEstoq   ue  s.re  move(0);
                       } else {
                emp  restimoEstoque = nul   l;
                        }
        }
        ret ur  n emprest   imoE  stoque;
    }

    public     Boolean res        taurar(Integer codigo) {  
         b    oolean sucesso = fa       lse;
           if (codi         go != null)  {
            Co   nnection  connection = conexao.ge   tConnect  ion();  
                   try {
                        String va lorDoC    omandoUm = comandos.get("restaura  r");
                  Prepa  re   dStatement p   reparedStatement = connection.prepareStatement(valorDoCo      ma  ndoUm);
                     preparedState m e      nt.setInt(1, co    digo);
                    connection.setAutoCom   mit(f alse);
                        sucesso = preparedStatement.executeUpda   te() ==  1;
                      conne     ction   .commit(  );
                connection.se    tA   utoCommit(true);
              } catc  h (SQLException ex )   {
                try {
                     connection.     rollback();
                    } catch (SQLExcepti  on ex1) {
                     }
                       throw ne    w RuntimeException(ex.getMessage());
            } catch (NullPointerException ex) {
                    throw    new RuntimeException(ex.getMessage());
            }    catch (Except ion ex) {
                throw new RuntimeException(ex.getMessage());
            } finally {
                conexao.fecharConnection();
            }
        }
        return sucesso;
    }
}
