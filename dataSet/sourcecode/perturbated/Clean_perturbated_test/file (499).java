package    proyectoascensor;

imp   ort java.util.ArrayList;
imp  ort java.util.Collections;   
   
/**
 *    @au  thor Felipe Murillo
 *     @author        Est   eban Llanos
 * @author    Sergio Garci a
 */
p ublic    class Contr    ol       adorLe     f {

    priv    ate    String da tosLEF         = "";
    // variables   de   l escenario, par    a el      ascensor
    private int capacidadAsc;
    private in       t tiempoArranque;
             private int despEntrePisos   ;
    //       l      ista de enven     to   s futuros
          private         ArrayList<Ev   ento> LEF;
    // variable para actualizar colas      del ascens  or
    private  int cambios  Cola;
   
                 // MAIN de la   si  m  ulac    ion
     public v                              oid iniciarSi mulacion(St    ring escena  r     io, int pisoA  scen     so   r, i nt       t iempo Parad        a) {
                       /*    *  
           * **     ** I   NICIALIZ     AR   *****
         */    
           // c  onfiguracion d   el escenario
        setEscen        a  r i   o(e    scenario)  ;
        // inici        alizacion del ev  ento ascens    or
                    Ascensor evtAscensor = n     ew Asc    ensor(pisoAscensor, capacidadAsc,                            tiemp          oArranque, de     spE  ntrePiso      s    );
               //   e     vento p erso   na
        Pe  rs         on a evt  Persona   ;

        // l   i       sta de event    os futuros
        LEF = n     e      w ArrayLi         st<>();

        // v        ariab      les de desempeÃ±o
          /*
           do  uble tiempoPromE   s   pera = 0.0;
             double    t ie       mpoPr       omEs     peraColaEntradaTotal   = 0;
           do    u    b  l    e   tiempoPromEsp eraColaSalidaTotal           = 0;
               do    u bl    e porcPersonasAtendidas = 0;   */
        in     t ca  pacid   a       dOc   upada  =            0;
        double          capacidadOcupa    daP        rom =       0  ;
            int contadorIteraci       ones =       0;
             int numer    oPe rsonasEnAsc = 0;
                  int n   um             eroIt             eraciones = 0;
        dou   ble tamaÃ±oPr  omedioCola;     

            /   /      tiempo    de simulacio     n maximo
        int    t               i   emp  oSimulacion   =             tiempoParada;
   
          // reloj de l    sistema
          in     t     reloj = 0;         

              /**   
            * *****        SI    MUL    ACION  ******                 
               */
                  /  / inicializar LEF   
          // generacion inici   al      de pers     onas en  t   odos     los pisos
             for (int pis  oIni = 1; pisoIni <   = 6; pisoIn        i++) {
                   evtPersona = new Persona(0, pisoIni); // pisoInicial
                    i nsertarLEF(evtP    ersona, "P", 0); //     i nfo e   ven  to,     tipo evento, ho  raLl     egada
                }

          // a g      re    ga evento ascen      sor al LEF
                inserta     rL      EF(ev        tAsce               n  sor,             "A"    ,  0             );  // inf   o evento     , tip                    o evento, hora llegada    

                     //       ejecut  ar simulacion
                                                 do {
                             //           si el LEF qu     eda vacio se aborta la   si    mulacio    n (condicion provisional)  
                  i         f (!LEF.   isE     mpty()) {
                  stringL        EF();
                            // se extrae e    l pr    imer even to futu  ro del LEF
                          Evento      e  vento =       L  EF.remo          v       e(0);
                           // evento ll egada      d       e    pe rso  na
                                   if     (e   v ento     .ge   tT   i    p            oEv      t()   .  equals("P ")) {
                           evtPe rsona =   (P  er   son  a    ) even to.get   E   vtObject()  ;
                            // System.out.println("P "                                + evento.getHor  aLl());   
                                 //    se eje         cu   t  a el  event   o de la p     e  r    sona
                                 evt   Perso     na.e        je     cu  tar();          
                                      // se agrega la p     e  rsona a la            cola del  ascensor
                                       Evento    ev      entoAsc  = ge      tEvt   Asc         ens o     rLEF(); // se obt   ien       e el e vento ascensor del L EF
                                 A   scensor asc      LEF =    (Ascens   or) ev  ento  Asc.g      et       E  vtObject(); /      / s       e  extrae los dat    o   s  del asc    ens  or
                            ascL   EF       .a ddColaE n    t           r     ad  a(evtP ers   o n   a); //           se m    odifi  c    an las  c    olas 
                                act     aliz     arL  EF(ascL            EF, e     ven          to    Asc          .getHora      Ll()); // se reem   p   laz          a      el evento       anterior con   el           nue  vo
                     } // eve           nto  ll  egada   de ascens or a un pi so
                       else {
                              e  vtAs c   ensor =   (A    sce       nsor) even   to.     getEv  tOb jec  t(  );
                          //S   yste   m.ou  t.pr                    in  tln("A " + evento.get      HoraLl(          ));
                                           // se eje   cuta   e  l eve   nto                  d     el      as ce    nsor
                                          evtAs     cen sor            .ejecutar(reloj);
                        //              s     e      ge     nera una ll   e   g      ada de perso       na si            el    ascensor para
                        i  f (evtAs   censor.                        getGene  raLleg a  da()) {
                                                            int     tEntreLl = ge  ne      ra rTi  empoEntreLl   egada   s(e         vtA                    sc  ensor       .getPisoAsc());
                               evtPersona =   new  Persona           (tEntreL     l +      r   eloj, evtAscensor.getPisoAsc() );
                                            inserta  rL       EF( evtPe   r    sona  , "            P", tEn      treLl +   rel         o  j)             ;
                                 }
                                                      //    si          el      ascensor no queda parado  
                                    if (!evtAscen      so      r.getEstadoAs   censor     ().     equals("Parad  o  ")                   )      {
                                                    int            tLl     eg           a   da =   evtAscensor.ir  Pis          oSiguient  e(   even    to.getH          oraLl());       // m     o  difi      ca las variables correspondi         ent        es para cambi   ar        de piso
                              inser  tar    L     EF(ev       t Asc          ensor, "        A", tLlegada); // a  greg         a     el    nuevo eve    nto al LEF
                           }
                 }
                              // s    e sincroniz  a     el r     eloj p              ara   ej     ecutar  el evento pr   o  ximo    m  as cerca    no
                                   r       eloj   = LEF.get(0).ge  tHo   raLl ();
            } else                {
                             Sy  stem.ou           t.println("LEF EMPTY");
                               break;
                      }

                          capacidadO       cupada      += evtAscens       or    .getCa       p      acidadO  cupada   ();
               contadorIterac    iones++;

              } whi     le (r  eloj <= tiempoS   imulacion)   ;

        capa  cida    dOcupadaPr   o    m    = ca   pacidad          Ocupa      d  a / co     nta          dorIte    raciones;

        Syste  m.out.pr      intln("     Capac   id           ad Ocupada: " + c      apacidad   O cupa da);

                      Syst  e m.out.p       rintln(     "Cap         ac   idad  P         ro                 medio:       "       +           c      apacidadOcupadaProm         );

             System.out.pr     i                  ntl n("End   simulation"  );

               /**
             * *  **** RESULTA   DOS ******          
             */
    }

    privat          e vo     id setEscenario(String    esc   enario)  {
              swi   tch (es   c  ena   rio)             {
                         case "Esce  n   ar  io   1":
                                     capacida         d   As  c = 4;    
                        t          ie        mpo   Ar  ran   que = 20;
                        despEntr ePisos = 10    0;
                   bre    ak;        
                                       case "Es cen    ar     io 2   ":
                capacid  adA            sc = 6;
                       t              iempoArranque      = 20;
                des    pEntre    Pi sos =     100;
                        bre       ak   ;
                                case "Escenario 3":
                cap acidadAsc = 4;
                 tiempoArranque =  5;
                     d   espE   ntrePisos = 30;
                   break;
                  case "Escen  ario 4":
                  ca  pacidadAsc = 6;
                ti   empoArra       nque =     5;
                    despEntre      Pisos = 30;
                           break;    
             }
    }

    pri  vate int gener arTie     mpoEntreLlegadas(int pisoAsc) {   
             double a = 0;   
        doubl  e lambd a1 = 0.04; // para el piso 1
        double lambda2 = 0.02;   /  / para los demas   pisos
            /    / r  = Math.random() retorna    por   defecto un valor e       ntre 1 y 0 co        n una distribucion
        // uniforme.
        double   r   = Math.random();
        //System     .out.pr  intln(r);
               if (pisoAsc == 1) {
            a = -(1 /     lambda1) * Math.   log(r);
                  //System.out.println(a    );
                 return (int) a;
        } else {
            a = -(1  / lambda2) * Mat   h.log(r);
             //System.out.pri        ntln(a);
            return (int) a;
        }
          }

    // inserta en   el     left un objeto evento q    ue      contiene informacion del t  ipo 
     // de even to   per         sona o as    censor  , el tipo de ev  en  to P      o A y la hor   a de ejecuci on del evento
    p     ublic void insertarLEF(Object o, Stri      ng tipoEvt, i nt horaLl) {
               //       se cr   ea el    evento c on la informacion,    tipo y hora de generacion
             Evento e = new Evento(o, tipoEvt  , horaLl);
        // se agrega al L  EF
               LEF.add      (e);
        // se ord      ena el LEF teniendo en cuenta la hor  aLl
        Collections.sort(LEF, new Comparador());
    }

    private     void stringLEF() {
        System.out.print("[ ");
        datosLEF += "[ ";
        for (Eve  nto    e : LEF) {
            datosLEF += "{"  + e.getTipoEvt() + "," + e.getHoraLl() + "} ";
            System.out.print("{" +    e.getTipoEvt() + "  ," + e.getHoraLl() + "} ");
        }
        datosLEF += "]   \n";
        System.out.pri   ntln("]");
      }

    private Evento getEvtAscensorLEF() {
        Evento e = null;

        f   or (int i = 0; i < LEF.size(); i++  ) {
            e = LEF. get(i);
            if ("A".equals(e.getTipoEvt())) {
                cambiosCola = i;
                break;
            }
        }

        return e;
    }

    private void actalizarLEF(Ascensor evtAscensor, int horaLlegada) {
        LEF.remove(cambiosCola);
        Evento e = new Evento(evtAscensor, "A", horaLlegada);
        LEF.add(cambiosCola, e);
    }

    public String mostrarLEF() {
        return datosLEF;
    }
}
