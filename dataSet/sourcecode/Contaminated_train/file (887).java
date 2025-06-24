package proyectoascensor;

import java.util.ArrayList;
import java.util.Collections;

/**
 * @author Felipe Murillo
 * @author Esteban Llanos
 * @author Sergio Garcia
 */
public class ControladorLef {

    private String datosLEF = "";
    // variables del escenario, para el ascensor
    private int capacidadAsc;
    private int tiempoArranque;
    private int despEntrePisos;
    // lista de enventos futuros
    private ArrayList<Evento> LEF;
    // variable para actualizar colas del ascensor
    private int cambiosCola;

    // MAIN de la simulacion
    public void iniciarSimulacion(String escenario, int pisoAscensor, int tiempoParada) {
        /**
         * **** INICIALIZAR   *****
         */
        // configuracion del escenario
        setEscenario(escenario);
        // inicializacion del evento ascensor
        Ascensor evtAscensor = new Ascensor(pisoAscensor, capacidadAsc, tiempoArranque, despEntrePisos);
        // evento persona
        Persona evtPersona;

        // lista de eventos futuros
        LEF = new ArrayList<>();

        // variables de desempeño
        /*
         double tiempoPromEspera = 0.0;
         double tiempoPromEsperaColaEntradaTotal = 0;
         double tiempoPromEsperaColaSalidaTotal = 0;
         double porcPersonasAtendidas = 0;*/
        int capacidadOcupada = 0;
        double capacidadOcupadaProm = 0;
        int contadorIteraciones = 0;
        int numeroPersonasEnAsc = 0;
        int numeroIteraciones = 0;
        double tamañoPromedioCola;

        // tiempo de simulacion maximo
        int tiempoSimulacion = tiempoParada;

        // reloj del sistema
        int reloj = 0;

        /**
         * ***** SIMULACION ******
         */
        // inicializar LEF
        // generacion inicial de personas en todos los pisos
        for (int pisoIni = 1; pisoIni <= 6; pisoIni++) {
            evtPersona = new Persona(0, pisoIni); // pisoInicial
            insertarLEF(evtPersona, "P", 0); // info evento, tipo evento, horaLlegada
        }

        // agrega evento ascensor al LEF
        insertarLEF(evtAscensor, "A", 0); // info evento, tipo evento, hora llegada

        // ejecutar simulacion
        do {
            // si el LEF queda vacio se aborta la simulacion (condicion provisional)
            if (!LEF.isEmpty()) {
                stringLEF();
                // se extrae el primer evento futuro del LEF
                Evento evento = LEF.remove(0);
                // evento llegada de persona
                if (evento.getTipoEvt().equals("P")) {
                    evtPersona = (Persona) evento.getEvtObject();
                    //System.out.println("P " + evento.getHoraLl());
                    // se ejecuta el evento de la persona
                    evtPersona.ejecutar();
                    // se agrega la persona a la cola del ascensor
                    Evento eventoAsc = getEvtAscensorLEF(); // se obtiene el evento ascensor del LEF
                    Ascensor ascLEF = (Ascensor) eventoAsc.getEvtObject(); // se extrae los datos del ascensor
                    ascLEF.addColaEntrada(evtPersona); // se modifican las colas
                    actalizarLEF(ascLEF, eventoAsc.getHoraLl()); // se reemplaza el evento anterior con el nuevo
                } // evento llegada de ascensor a un piso
                else {
                    evtAscensor = (Ascensor) evento.getEvtObject();
                    //System.out.println("A " + evento.getHoraLl());
                    // se ejecuta el evento del ascensor
                    evtAscensor.ejecutar(reloj);
                    // se genera una llegada de persona si el ascensor para
                    if (evtAscensor.getGeneraLlegada()) {
                        int tEntreLl = generarTiempoEntreLlegadas(evtAscensor.getPisoAsc());
                        evtPersona = new Persona(tEntreLl + reloj, evtAscensor.getPisoAsc());
                        insertarLEF(evtPersona, "P", tEntreLl + reloj);
                    }
                    // si el ascensor no queda parado
                    if (!evtAscensor.getEstadoAscensor().equals("Parado")) {
                        int tLlegada = evtAscensor.irPisoSiguiente(evento.getHoraLl()); // modifica las variables correspondientes para cambiar de piso
                        insertarLEF(evtAscensor, "A", tLlegada); // agrega el nuevo evento al LEF
                    }
                }
                // se sincroniza el reloj para ejecutar el evento proximo mas cercano
                reloj = LEF.get(0).getHoraLl();
            } else {
                System.out.println("LEF EMPTY");
                break;
            }

            capacidadOcupada += evtAscensor.getCapacidadOcupada();
            contadorIteraciones++;

        } while (reloj <= tiempoSimulacion);

        capacidadOcupadaProm = capacidadOcupada / contadorIteraciones;

        System.out.println("Capacidad Ocupada: " + capacidadOcupada);

        System.out.println("Capacidad Promedio: " + capacidadOcupadaProm);

        System.out.println("End simulation");

        /**
         * ***** RESULTADOS ******
         */
    }

    private void setEscenario(String escenario) {
        switch (escenario) {
            case "Escenario 1":
                capacidadAsc = 4;
                tiempoArranque = 20;
                despEntrePisos = 100;
                break;
            case "Escenario 2":
                capacidadAsc = 6;
                tiempoArranque = 20;
                despEntrePisos = 100;
                break;
            case "Escenario 3":
                capacidadAsc = 4;
                tiempoArranque = 5;
                despEntrePisos = 30;
                break;
            case "Escenario 4":
                capacidadAsc = 6;
                tiempoArranque = 5;
                despEntrePisos = 30;
                break;
        }
    }

    private int generarTiempoEntreLlegadas(int pisoAsc) {
        double a = 0;
        double lambda1 = 0.04; // para el piso 1
        double lambda2 = 0.02; // para los demas pisos
        // r = Math.random() retorna por defecto un valor entre 1 y 0 con una distribucion
        // uniforme.
        double r = Math.random();
        //System.out.println(r);
        if (pisoAsc == 1) {
            a = -(1 / lambda1) * Math.log(r);
            //System.out.println(a);
            return (int) a;
        } else {
            a = -(1 / lambda2) * Math.log(r);
            //System.out.println(a);
            return (int) a;
        }
    }

    // inserta en el left un objeto evento que contiene informacion del tipo 
    // de evento persona o ascensor, el tipo de evento P o A y la hora de ejecucion del evento
    public void insertarLEF(Object o, String tipoEvt, int horaLl) {
        // se crea el evento con la informacion, tipo y hora de generacion
        Evento e = new Evento(o, tipoEvt, horaLl);
        // se agrega al LEF
        LEF.add(e);
        // se ordena el LEF teniendo en cuenta la horaLl
        Collections.sort(LEF, new Comparador());
    }

    private void stringLEF() {
        System.out.print("[ ");
        datosLEF += "[ ";
        for (Evento e : LEF) {
            datosLEF += "{" + e.getTipoEvt() + "," + e.getHoraLl() + "} ";
            System.out.print("{" + e.getTipoEvt() + "," + e.getHoraLl() + "} ");
        }
        datosLEF += "]\n";
        System.out.println("]");
    }

    private Evento getEvtAscensorLEF() {
        Evento e = null;

        for (int i = 0; i < LEF.size(); i++) {
            e = LEF.get(i);
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
