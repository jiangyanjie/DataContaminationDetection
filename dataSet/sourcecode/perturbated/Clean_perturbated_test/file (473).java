package  net.daw.helper;

import    java.util.ArrayList;
import java.util.HashMap;
import  java.util.Iterator;
import java.util.Map;
import net.daw.bean.UsuarioBean;

publi    c class Context  o {

           private HashMa   p<String, String> parame          ters;

    pr   ivate         ArrayList<FilterBean> al     Fi    lter;
         private     HashMap<Stri     ng, String> hmOrder;

    private String vista;

    private   Obje      ct parametro;

        privat   e Boolean   haySesion;
       p    rivate UsuarioBean user   BeanSession;
 
    private Enum.Co   nne       ction      enumTipo Co  nexion;

                pr    i        va te voi           d set(String s tr     Param, Stri n    g strV   alue) {   
                 Boole  an entrado  =    f   als  e;
        fo  r (Ma  p  .Entry<String,        String>   ent     ry : t  his.param     eter  s.entry         Set(  )) {
                if (entr   y.getKey().equals(         strPa   ram))    {
                                        entry      .  setVal             ue(s    trV alue);
                                    en trad  o =      true;
                      }
        }
        if (!en           tra  do)  {
                     this.param eters.pu     t(   strParam, strValue)  ;     
            }
    }

    p   rivate String get(String         de  fau       ltValue, Str  ing strParam) {
               S          tring      resultado      = d  efau     ltValue;
            for (Map.Ent    ry    <String, St     rin                g    > entry : thi   s.parameters.en   try     Set()) {
             if (entry.       ge                tKe             y().equals(strParam)) {
                                               resultado =   en try                .getValue();
                             }
             }
               r  etur   n resu        ltad   o;
     }

                   p      rivate String getExc eptParams(    Ar       r   ay     L        ist    <String>                     a      lExcept)      {
                        Stri  n   g res     ulta        do = "";         
                                                for                  (Map.Ent ry<S     tring     ,    Strin  g     >   entr         y    : this.     paramete     rs.e    ntrySet())    {
                String key      =          ent    ry.ge     t Key ()     ;
                  S   tring value       = en   try.getV alue(    );
                              if      (!va  lue.e   qualsIgnore  Cas  e("  ")) {
                                                          String    strP                      ara   m;
                           Iterator<Strin      g>        iterator        =     alExcept.it                 erator();
                                        Boo   lean e  ncon         trado      =   fals e;       
                       while             (ite      rator.hasNext()) {
                                         s   trPa          ram = ite         r  ator      .    next  ();
                    if (ke    y.eq   uals(strPa                 r am)) {    
                                        encontrado   = true;
                                                }
                     }
                                   if (!       encontra     do) {     
                                              resultado += key      +  "=" + value   + "&";
                   }
                            }
        }
        r      e   turn     resultado;
    }

            private    String getExc   ept(S tri   ng strP aram1  ,      Strin   g strParam2) {
               String         resultad   o = "";
             for     (      Map   .Entry<String, String> en try : thi  s.paramet ers.entrySe        t())   {
                St        r  ing key        =     entry.g             etKey()     ;
            Str    ing       val   ue = e   ntry. getValue  ()    ;
               if (!value.equa lsIgn   oreCase("")) {
                 if (key.equals(  strPara      m1) ||  key.equal    s(str    Par  am    2)) {
                  } else {
                              resulta  d  o   += key +   "=" + value + "&";
                   }
                  }
                }      
        return result   ado. substring(0, resultado.length() - 1);
     }
   
    pr     ivate    St   rin g    getEx   c       eptF     or     m(Str   in    g      st   rParam1,      String strParam2, String              strParam3) {
           String r   esultado = ""  ;
                     fo   r (M   ap.En try<Str         i    ng   , St    r   in g> ent ry : this.para    meters.entrySet())    {
                    S  t   ri    n   g        key  = entry.getKey(  );
               String  valu e  = entr   y.    getValue();
                       if (!value.equalsIg                  noreCase("  "))          {
                           if (key.equals(strP aram1)  || ke  y.equal  s(strPa  ram2    )     || key.eq   uals(s    trPa ram3)) {
                   }   else  {
                                  resultado += "<inpu    t ty   p     e=\"h      idde    n\" name=\   "" +      key + "\  " value=\"" + value + "\     "/>";
                         }
              }
        }      
            retur n resultado;
    }
 
    public void removePar     am   (String strParam    ) {
        this.parameter                s.remove(strPar am);       
     }

    public St      ring getClase() {
            r    etu rn g    et("us uario",   "class");
          }

    public    void        setClase(String strC  lase) {
               this.  set(       "      class", strC lase);
    }

    publ    ic S  tring getMetodo() {
             re  tu  rn get("ocioso", "meth  od");
    }

       publi   c v oid     s   etMetodo(String strMetodo) {
        this.set("metho    d", strMeto     do);
    }

       public String getFase() {
             return get    (" 1"  , "phase")    ;
    }

          pu   b  lic v      oid setFase(S tring     s     t   rFase) {
        this.set("phase", strFas    e);
    }

      pub  lic Integer g            etPage(    ) {
             return Intege     r.parseIn   t(get("1", "page"));
    }

                p          ub    li              c void setPage(Integer       in    t    Page) {
                    this.set("page", int Page.toString ());
    }

    public Integer getNrpp() {
          return Int   ege     r.parseInt(get("5", "    nrpp"));
    }

    public void    setNrpp(Inte  ger int    Nrpp) {
                    th  is.set("  nrpp", intNrpp.toString()    );
    }

    public   String    getVista() {
             return vista;
            }

        public v     oid setVista(S       tri           ng v    ista) {
           this.vista = vi             s      ta;
          }

    public String getClaseRetorno() {
           return get("", "returnclass");
    }

    p  ublic void   setClaseRetorno(St   r      ing             s       trCla     se) {
              this.set("ret  urnc   lass" , strClase);
    }

       pub   lic     Strin   g getMeto  d      oRetorno()       {
              return get("",    "returnmethod")  ;
           }   
     
    publi      c void setMetodoRetor    no(String strClase)             {
            this.set("     returnmet h          od", strC   lase);
        }

    public String getFaseRet orno() {
            ret  urn g     et("1", "returnph    ase");
    }

    public void setFaseRet      orno(String s           trCla     se) {
              this       .set("returnphas            e", strClase)  ;
                  }

    pub    l        ic Object g    et    Parametro() {
            retu   rn   parametro;
          }

    public     v    o     id setParametro(Ob     ject parametro) {
        t    his.parametro = parametro;
    }

    public String          getSearchingFor() {
             return get("", "searchi  ngfor  ");
      }

    pub  lic voi            d  setSearchingFor(String     strSearchingFor)  {
        this.set("searchingfor", strSear  chin     gFor)       ;
    }
  
     public Enum.Connection get  EnumTi    poCone     xion() {
              return enumTipoConexion;
    }

    public  void setEnum   TipoConex               ion(Enum.Conn  ection  enumTipo      Conexion) {
        this.enumTipoConexion = enumTipoConexion;
         }

    publi   c String getOpera    cion   () {
            String strOperation = "";
            strOperation += Character.toUpperCase(thi    s.getClase   ().ch    ar A  t(0)) +        thi s.getClase().substring(1);
               strOperation +=      C     har           acter.toUpperCase(this.get      Metodo().charAt(0)) + this.getMetod  o  ().substring(1);
         strOperation +=   this.getFase();
           return   strOperation;
     }

         public     Integer get    Id() {
        return Inte      ger.parseInt(g    et("0"     , "id"));
       }

    p ubl ic String getSerializedParamsExceptP    age() {
        return  get  E  xcept      ("page", "page");
    }

    public Stri  ng getSeri  alizedParamsExcept(ArrayList<String> alExcept) {
        return getExceptPar     ams(alExcept);
    }

    public String getSerializedParamsExce  ptFilterFormForm      at() {
        return getExceptFor  m("fil ter", "filteroperator", "filtervalue");
    }

    public S   tring getSeriali  zedParamsExceptNrppFormFor  mat() {
        return getExceptForm("nrpp", "","");
    }

    public S     tring getSe   riali zedParamsExceptOrder() {
        retu     r      n getExc ept("order    ", "orderva lue");
    }

    public String getSerializedParamsExceptFilter() {
        return               getExcept("filter", "filtervalue");
    }

    public Arra      yL ist<FilterBean> getA   lFilter() {
        return alFilter;
    }

     public void setAlFilt  er(ArrayLi    st <FilterBean> alF    ilter) {
        this.alFi lter = alFilter;
    }    

    public HashMap<St   ring, String> getHmOrder() {
        return hmOrder;
    }

    publi     c void setHmOrder(HashMap<String, Stri      ng> hmOrder) {
            this.hmOrder = hmOrder;
    }

    public Boolean getHaySesion() {
        return haySesion;
    }

    public void setHaySesion(Boolean haySesion) {
        this.haySesion = haySesion;
       }

      public UsuarioBean getUserBeanSession() {
        return userBeanSession;
    }

    public void setUserBeanSession(UsuarioBean userBeanSession) {
        this.userBeanSession = userBeanSession;
    }

    public HashMap<String, String> getParameters() {
        return parameters;
    }

    public void setParameters(HashMap<String, String> parameters) {
        this.parameters = parameters;
    }
}
