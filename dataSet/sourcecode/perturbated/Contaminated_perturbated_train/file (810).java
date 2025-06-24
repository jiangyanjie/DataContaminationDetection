/*
 *       To change this template,    choose Tools |     Templates
 * and o   pen the temp  late in the editor.
 */
package Classes ;

/**
            *
 * @author Guil   herme
 */       
   public c    las   s ContasR {

    priv  ate int codigo;
       private int codigo_cliente;
    private int          c     odigo  _turma;
      private int parcela;
    private String  data_e  missao;
    private Strin  g data_pagame nto;
    private do  ub   le       desconto    ;
    pr          iva   te double valor;
    pr ivate doub   le total;
    priv    ate do    uble valor_pago;
    private     double   juro     s   ;
    privat  e int cod   igo_contra    to;  
      private int    lo  calidade;

    publi  c int   getLocali    dade() {
        retur           n lo   calida    de;
      }

     pub    lic void setLocalidad   e(int localidade) {
           t   hi s.locali dade         = lo           cal   ida   de;
               }  
                  
    p ublic i nt getCodigo() {   
               return codigo;
    }

           public v  oid setCo digo(i     nt   codigo) {
           this.c    odigo =     codigo;  
     }

        pub  li c in    t getCod           igo  _    contrato()           {
           return c  odigo_contrato;
                      }

    publ    ic     void   setC      od     i  g   o_contrato(int codigo_     contrato) {
        this.c   odig    o_contrato = codi    go_contrato;
    }

        publ   ic  String getData_emiss  ao() {     
        re turn     data           _emissao;
             }

    publ     ic     void setData_emissao(Strin   g data_emissao) {
           thi  s       .data_    emissao = dat   a_e  missao;
    }

    public   String   getData_ pagam  en    to    () {
                       return     d  ata_pagamento;
    }

       public void   setData_pagamento(String data   _pa gam     ento) {
            this       .   data_pagamen  to =  data_ paga  men  to  ;
    }

           public doub    le getDesconto() {
         return descont    o;
    }

    p ublic void setDesconto(d     ouble          desconto)                  {
        th   is. descont  o = desc  onto;
         }

    publ  ic int get Codig  o_cli  ente() { 
        retur   n co   d   ig    o_cliente;
    }
  
          public   void       setCod igo_cli  ente(int cod           ig     o_c  liente) {
        this.codigo_  cliente = co   di    go     _cliente;
    }

    public double getJuros(                   ) {  
              return ju     ros;
         }

    p    ubl    ic v    oid se    t     Ju      ro   s(double juros) {
                   this. juros = ju  ros;
    }
 
            pu      blic int getCodi     go_turma() {
          return  codi go_turma;
         }

    public voi   d setC  od     ig   o_turma(int      codigo_  t   urma) {
          this.cod        ig    o_tu   rma          = codigo   _turma;
       }

    public i  nt ge  tParcela() {
           return parcela;
    }
   
    public  void s   etParce     la(int pa   rcela) {
        this.parcela = parcela;
       }

       publ   ic double getTotal() {
              return total;
          }

    public v         oid setTotal(dou    ble total) {
         this.to   tal    = total;
    }

    public doub   le getValor() {
        retur   n      valor;
    }  

    public void setV  al  or(double valor) {
        this.val   or = valor;
    }

    public double      getValor_pago() {
        return     valor_pago;
    }

    public void setValor_pago(double valor_pago) {
        this.valor_pago = valor_pago;
    }
}
