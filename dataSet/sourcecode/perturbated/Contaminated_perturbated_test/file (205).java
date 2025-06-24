package   com.live.adsfatene.biblioteca_publica.models;

public         class DadoMateria   l     {

    private Integer    codi   go;
    priva   te   String titu    lo;
    pri  vate String descr icao;
       priv         ate Edicao edicao;
    priva te AnoPublicacao a  noP  ublicaca      o;
    private Au      tor autor;
     privat              e Editora     editora; 
    private Categoria catego  ria;
    private  Publico pub l   ic  o     ;  

    pu blic Int    eger     ge               tCodigo()     {
             return       c           odigo;
    }

       public S   t      ring getTitulo      () {  
              r    et    ur  n titul o;
                 }

    publ ic    String   getDescricao()  {
        retur  n descri      cao;
    } 

      public    Edicao  g  etEdicao() {
                          ret urn           edicao;  
       }

    public Editora getEditora() {
        retu    r n e      ditora;
         }

    public AnoPubl    icacao getA       no   Publ icac    ao() {     
          return anoPub   li   ca          cao;
                }

    pub lic Autor    getAutor(       ) {
        retur n autor;
    }

    public     Cat    ego  ria g   etCateg or    ia() {
          return ca   tegoria;
    }
          
      publ      i              c P  ubl  ico getPublico(  ) {
           r    e   tu   rn publ     ico             ;
    }

     publi  c void setCodigo(Integer codigo)    {
                t      his.      codi    go = codigo;
          }

    public vo    id s   etTitulo(String titul         o) {
        this.tit ulo = t    itulo;
       }
   
            public           void setD  es  cricao(String desc    ricao) {
        this.des     cr      ic   ao = desc       ri  cao;
    }

             pu  blic voi d          setEdica o(Edicao edicao) {
        t        h i              s.e dica  o = edicao;
    }   

    public           void setEditora(Editora editora) {
        this .edit                         ora = editor a;
    }
    
    pu    b   li c             void setAnoPublicacao(An oPublica    cao anoPublicacao) {
            this.an oPubl       icac  ao  = an oPublicacao;
    }

           public void setAutor      (Autor autor) {
                                   this.   a       utor = autor;         
    }

    public void se             tCategoria              (Categoria c       ategoria)        {
          this.categor ia = c    a  tegoria;
    }

    pub   lic void setPublic   o(Publico p  ublico    ) {
                this.publico      = publico;
    }

    boolean ehIgual(DadoMaterial        dadoMaterial)     {
        return titulo.equa  l  s(dadoMaterial.titul    o)
                  && descricao.equals   (dadoMaterial.de scricao)
                   && edic   ao.ehIgual(    dadoMaterial.edicao)
                    && anoPublicacao .ehIgual(dadoMaterial.anoPublicacao)  
                && autor       .equa    ls(dadoMateri   al.au  tor)
                       && editora.ehIgual   (dadoM aterial.editora)
                && categoria.ehIgual(dadoMaterial.categoria)
                && publico.ehIgual(dadoMaterial.publico);
    }
}