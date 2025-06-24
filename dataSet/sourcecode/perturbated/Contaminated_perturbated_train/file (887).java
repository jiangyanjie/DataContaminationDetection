package   proyectoascensor;

import java.util.ArrayList;
import java.util.Collections;

/* *
 * @author    Felipe Murillo
 * @a  uthor     Est     eban Llanos
 * @author     Sergio Garci    a
 */
public class Controlador Lef {
     
    p       rivate String    datosLEF          = "     "    ;   
    // variables   del escenario, para el ascensor  
    private int capacidadA   sc;
    priva      te int tiempoArra   nque;
       priv   a   te in       t despEntrePis   os;
        // lista de envento               s fut  uros
    private ArrayList<Evento> LEF;
    // variable para    actu   alizar colas del asc   en sor
    private int ca  mbiosC        ola;

    // MAI   N de l   a      simulacion    
    pu blic void       inicia            rSimulacion(String esc       e                 nario, int piso     Ascenso       r, int t  iem       poParada) {
        /**
              *  **** INICIA         LIZAR   *****
             */
            // c        onfi    g     uracion del esce   nario
               set   Escenario(escenario);
                // inicializacion del evento a      scens  or
           As  cen     so  r         evtA     scens or = new Asc      e     nsor(pisoAs    cens   or, capa  cidadA  sc,        tiempoArranque,  despEn              trePisos);
            //        ev      en     to persona
          Pers   o na evtPers   ona; 

        // lis   ta d e e   v  entos f   uturos    
        L            EF =  new ArrayList<>();   

           // va  riable       s d   e desem    p eÃ±o    
             /*
         double tie      mpoPromE    spera = 0.0   ;
                    dou   ble      tiempoPromE  s  peraColaEntr  adaTot  al   = 0;
                      double tie    mpoPr  o m         Esp         eraC        ol           aSalida       Total   = 0;
         double po  rc  PersonasAtend        idas = 0  ;*/
        int capacidadOcupad     a      = 0;
          double capacidadOcupadaProm = 0;
               int con  tadorIte  raciones = 0;
                int numer     oPer        sonasEnAsc             = 0  ;
        int nume   roItera        ciones =   0;
        do           ub    le t    amaÃ±oPromed ioC     ola    ;       

        // t  iem    po de simulacion maximo
                     i   nt tiempo Simulac    ion       = tiem    po    Parada    ;

               // reloj   del            sistema
        i nt reloj = 0;

          /*      *  
         * ***** S        IMULACION ******  
           */   
        / / i nic   ializar LEF
                 / / generac     ion inicial de  p       er  sonas e   n              todos l            os pisos
          for   (int pisoIni =  1;   pisoIni <= 6; pisoIni      +   +) {
              ev     tPersona = new Pe           rs  o             na(0   ,  pisoIn i); //      pisoInicial
                                  insertarLEF(ev     tPers    on a, "P", 0)        ; // in fo event  o, tipo  eve    nto, hor aLl  egada
           }

            //    a                              grega evento ascensor al LEF
        in      serta      rLEF(evtA    s      censor,           "A", 0   );     / / info evento        , tipo event  o     , hora       llega      da

            //  ejecu       tar     si   mulacio n
                        do         {
             //              si el LEF queda vacio se a  b      o     rta  la      simula    cio     n (condicion p  r            ovisional)
                  if                  (    !LEF.isEmpty()  ) {
                                st  ringLEF();   
                                                   // s  e  extra      e             el   pri           mer evento futuro del      LEF
                     Even  t      o  evento = LEF     .remove(0          ); 
                           // e  v   ento llegada de pers  o   n           a
                                     if (evento.g       etTipoEvt       ()          .equals("P"    )) {
                                    e       v  tPers            on a      = (Persona  ) even           to.getEv    tObject();
                              //System.out     .p  rin t           ln("          P " + eve  n    to   .getHoraLl()   );
                                                            // se     ejecuta   el even      to de la p   erson   a
                                      evtPer    so         na.ej       ec        u      tar();
                                // se agrega la     persona a la cola d   e  l    ascensor
                                            Evento eventoAs   c      = getEvtAscensorLEF   (); // se obtiene    el      even   to asc   ensor    del LEF
                       Ascensor            ascLEF        = (Ascen         sor)      e   ve  n        toAsc.g  etEvtObject(); // se     extrae los datos de   l asc    ensor
                                 asc     LEF     .addCol       aEn       trada(e      vtPersona);               //       se modifi    can las co        las    
                                  act      ali    zarL        EF(ascLEF, eventoAs              c. getHo          ra                   L  l(       ));    // se   reemplaza      el evento    ante    rior con el nu   evo
                    } //      event   o llegada d      e ascen  sor a u    n piso    
                    e   lse       {   
                       evtAscens   or = (Ascensor)         evento.getEvtObject();
                                               //Syste m.out. printl     n("A " + evento.getHo  raLl());
                                                                     // se ejecut  a el evento       del as ce    n                        sor
                       evtAsc ensor.eje     cut              ar(reloj);
                                   // se gene       r      a una llegada de per s    on    a s       i    el ascens      or    p  ar  a    
                                if (evt               Ascensor.getGenera       L        legada()) {       
                        int tEn   tr     eL  l                           = gen               era   rTiempo   En    t   r   eLl      egada                                s(evtAscens   o r.getPis oAsc());
                            e      vtP      e       rsona = n         ew Persona(tEnt  reLl    + reloj, evtAscen   sor.getPisoAs   c(     ));
                                     insertarLE       F(evtP er    s      o        na,    "P", tEntreL       l    + reloj);
                                                  }
                                          //          si     e      l        ascensor      n   o qued a    parado
                         if            (!evtAscensor.getE   stadoAscen  sor().equ        als      ("Parado"  )) {
                                         int tLlegada = evtAscensor.irP      isoSiguiente(ev                   ento.  g              etHo     raL    l()); /     / mod   ifica las v   ariab  les co            rre      s pondie  ntes para c                       ambiar   de    piso
                                      in    s       ert arLEF(evtAscensor, "A   ",      tLl      e gad   a);    //     agre       ga el nuevo evento a  l LEF
                           }
                          }
                    / / se sincroniza el      re            lo   j  para ej          ec utar   el e ven   to pr    o  xi mo mas cercano
                         r                              e         loj = LEF    .g    et           (0).getHoraLl();
                   } el   se {
                         System.      o  ut    .p   rintln(         "LEF     EMPTY");
                brea    k;
                 }

                    ca    paci  dadOcupa   d        a +=    ev            tA      scens or.getCapacidadOcupad          a();
                       co       nta     dorIteracio  nes++;

         } whil e (rel  oj        <= tiempoSimulaci on);

             capacidad  Oc upada  Prom              = cap   ac    id    adOcupada /    c on t     ado        rIte  raciones;

                           Sy        s tem.        out    .pr intln("           Capacidad Ocupada:          "        +   capa     cidadOc upa          da)   ;

                  Syst em.out.printl    n(  "C            apac       idad Pr     omedio       :    "   + capacidadOcupadaProm);

                         System.o   u   t.println("En d         simul     ation"         );

            /** 
         *        *****     RESULTADO S ****          **
                  */
    }

        priv         a   te void     se      tEsce     n    ario(String escenari      o) {
        s wi  tch (esc  enario) {
               case      "E sce      nario   1":
                  capa   cidad    Asc =       4;
                   t   iemp   oA    rranque =         20   ;     
                despEnt  re    Piso       s = 1          00;
                                  br   eak;
                          c         ase "Es     ce   nario 2":
                 capacida    dAs  c = 6;
                 tiempoArranque = 20;
                  despEn  trePisos =    100;
                   b    reak;
                        case "Escenario 3":
                         capacid    adAsc = 4 ;
                     tiempoArra nque =      5;       
                      despEntrePisos =   30;
                   break;            
                      case "Escen     ario 4":
                capa ci  dadA   sc =     6;
                               tiempoArranque = 5  ;
                        desp     Entr  ePis   os = 30;
                 break   ;
          }
    }  

    priva    te int gener a     rT     ie  mpoEnt    reLlegadas( int pisoAsc) {
         double   a = 0;
        d ouble lambda1 = 0.04; /  / para el piso 1
           doubl   e lambda2 = 0.02; /  / para los    demas pisos
          //  r = Math.random    () retorna por def   ecto un val   or entre 1 y 0 con   una distr  ibucion
        // unif       orme.
        doubl   e r = Math.random();
        //System.out.pr       intln(r);    
              if (pisoA   sc == 1) {   
               a = -(     1 / lambda1) * Math.log(r);
            //System.out.println(a);
                return (int) a;
           } else {
                 a = -(1 /    l     ambda2) * Math.log(r);
                  //System.out.pr  in      tln(a);
            ret      urn (i   nt)       a;
        }
    }

    /           / inserta en     el left un ob     jeto   evento que contiene i   n  formacion del tipo 
    // de evento persona o ascensor, el tipo de    evento P o A      y la ho      ra de ejecucion del evento
    public void inser     tarLEF(Object o, String tipoEv t, int horaLl) {
        //  se crea el evento con la infor   macion, tipo y hora de       genera   cion
        Evento e = new Evento(o, tipoEvt, h  oraLl);
        // se agrega al LEF
        LEF.add(e  );
        /  /  se ordena el LEF       teniendo en c      uenta la horaLl
        Collections      .sort(LEF, new Comparador())    ;   
    }

    priva te vo     id stringLEF() {
        System.out.print("[     ");
         datosLEF += "[ ";
        for (Evento e : LEF) {
            datosLEF += "{" + e.getTipoEvt() + "," + e.getHoraLl() + "} ";
                  System.out.print("{" + e. g    etTipoEvt() + "," + e.getHoraLl() + "} ");
        }
        d     atosLEF += "]\n";
          System.out.println("]");
    }

    private Evento getEvtAscensorLEF() {
        Evento e = null;

        for (int i =        0; i < LEF.size(); i++) {
            e = LEF.get(i);
            if ("A".equals(e.getTipoEvt()))    {
                 cambiosCola = i;
                break;
            }
        }

        return e;
    }

    private void actalizarLEF(Ascensor evtAscensor, int horaLlegada) {
        LEF.remove(cambiosCola);
        Evento e = new Evento(evtAscensor, "A", horaLlegada);
        LEF.add(cambiosCol    a, e);
    }

    public String mostrarLEF() {
        return datosLEF;
    }
}
