/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Presentacio;
import Domini.CtrDomini;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author miquelmasrieraquevedo
 */
public class CtrPresentacio {
    
    static String unitatDocent;
    static CtrDomini cd;
    static Scanner s;
    
    public static void mayn() {
        
       boolean tancar = false;
        
        while(!tancar){
            s = new Scanner( System.in ); 
        
            System.out.println("Nom de la unitat docent: ");
            unitatDocent = s.next(); 
        
            cd = new CtrDomini( unitatDocent );
      
            int opcio = 0;
            boolean opcio4 = false;
        
            while( !tancar && !opcio4) {
                System.out.println(" MENU ");
                System.out.println(" 1 - generar horari");
                System.out.println(" 2 - cargar horari");
                System.out.println(" 3 - modificar dades");
                System.out.println(" 4 - logOut");
                System.out.println(" 5 - Tancar");
                System.out.println();
                System.out.println(" Entra opcio");
                opcio = s.nextInt();
            
                if(opcio == 1) generar();
                else if(opcio == 2) carregar();
                else if(opcio == 3) modificar(); // la unica implementada
                else if(opcio == 4) opcio4 = true;
                else if(opcio == 5) tancar = true;
            }
           if(opcio4) System.out.println("-----------------------------------------------------------");
            System.out.println();
        }
        System.out.println(" FIN "); 
    }
    
    private static void definirRes(){
        int opcio = 0;
        while( opcio != 7 ) {
            System.out.println(" OPCIONS ");
            System.out.println(" 1 - Restringir un grup a una aula");
            System.out.println(" 2 - Restringir un grup a un dia i hora");
            System.out.println(" 3 - Restringir una hora en la que no es pugui impartir una asignatura+grup"); /*OK*/
            System.out.println(" 4 - Restringir un dia per a que no es pugu impartir una assig+grup");
            System.out.println(" 5 - Una assig+grup no es pot impartir a la vegada que una altre temporalmente parlant");
            System.out.println(" 6 - inhabilitar una aula a un dia/hora"); /*OK*/
            System.out.println(" 7 - Tornar");
            opcio = s.nextInt();
            
            if(opcio == 1){
                boolean repetir = true;
                String assignatura;
                Integer grup;
                String aula;
                String rep;
                ArrayList params;
                while(repetir){
                    params = new ArrayList();
                    System.out.println("Introduir nom assignatura del grup");
                    assignatura = s.next();
                    System.out.println("Introduir numero del grup");
                    grup = s.nextInt();
                    System.out.println("Introduir nom aula");
                    aula = s.next();
                    params.add(assignatura);
                    params.add(grup);
                    params.add(aula);
                    cd.afegirRestriccio(1,params);
                    System.out.println("Vols afegir-ne una altre?<y,n>");
                    rep = s.next();
                    if(rep.equals("n")) repetir = false;
               }
            }
            else if(opcio == 2){
                boolean repetir = true;
                String rep;
                ArrayList params;
                while(repetir){
                    params = new ArrayList();
                    System.out.println("Introduir nom assignatura del grup");
                    String assignatura = s.next();
                    System.out.println("Introduir numero del grup");
                    int grup = s.nextInt();
                    System.out.println("Introduir dia <nom en minuscula>");
                    String dia = s.next();
                    System.out.println("Introduir hora <0-23>");
                    int hora = s.nextInt();
                    if( ! cd.AfegirRestriccioGrupSessio( assignatura, grup, dia, hora) )
                        System.err.println("no es pot definir aquesta restriccio");
                    System.out.println("Vols afegir-ne una altre?<y,n>");
                    rep = s.next();
                    if(rep.equals("n")) repetir = false;
               }
            }
            else if(opcio ==  3 ){
                boolean repetir = true;
                String assignatura;
                Integer grup;
                Integer hora;
                String rep;
                ArrayList params;
                while(repetir){
                    params = new ArrayList();
                    System.out.println("Introduir nom assignatura del grup");
                    assignatura = s.next();
                    System.out.println("Introduir numero del grup");
                    grup = s.nextInt();
                    System.out.println("Introduir hora <0-23>");
                    hora = s.nextInt();
                    params.add(assignatura);
                    params.add(grup);
                    params.add(hora);
                    cd.afegirRestriccio(3,params);
                    System.out.println("Vols afegir-ne una altre?<y,n>");
                    rep = s.next();
                    if(rep.equals("n")) repetir = false;
               }   
            }
            else if(opcio == 4 ){
                boolean repetir = true;
                String assignatura;
                Integer grup;
                String dia;
                String rep;
                ArrayList params;
                while(repetir){
                    params = new ArrayList();
                    System.out.println("Introduir nom assignatura del grup");
                    assignatura = s.next();
                    System.out.println("Introduir numero del grup");
                    grup = s.nextInt();
                    System.out.println("Introduir dia");
                    dia = s.next();
                    params.add(assignatura);
                    params.add(grup);
                    params.add(dia);
                    cd.afegirRestriccio(4,params);
                    System.out.println("Vols afegir-ne una altre?<y,n>");
                    rep = s.next();
                    if(rep.equals("n")) repetir = false;
               }
                
            }
            else if(opcio == 5 ){
                boolean repetir = true;
                String assignaturaP;
                Integer grupP =null;
                String assignaturaS;
                Integer grupS = null;
                String rep;
                String nivell = "n"; //valor aleatori que ha de ser diferent de "a" i de "g"
                ArrayList params;
                while(repetir){
                    params = new ArrayList();
                    System.out.println("Solapament a nivell de assignatura o de grup ?<a,g>");
                    while((!nivell.equals("a")) && (!nivell.equals("g"))) {nivell = s.next();}
                    System.out.println("Introduir nom assignatura");
                    assignaturaP = s.next();
                    if(nivell.equals("g")){
                        System.out.println("Introduir numero de grup de la assignatura anterior");
                        grupP = s.nextInt();
                    }
                    System.out.println("Introduir nom de l'altre assignatura");
                    assignaturaS = s.next();
                    if(nivell.equals("g")){
                        System.out.println("Introduir numero de grup de la assignatura anterior");
                        grupS= s.nextInt();
                    }
                    params.add(assignaturaP);
                    params.add(assignaturaS);
                    if(nivell.equals("g")) {params.add(grupP); params.add(grupS);}
                    cd.afegirRestriccio(5,params);
                    System.out.println("Vols afegir-ne una altre?<y,n>");
                    rep = s.next();
                    if(rep.equals("n")) repetir = false;
               }
                
            }
            else if(opcio == 6 ){
                boolean repetir = true;
                String Aula;
                Integer hora;
                String dia;
                String rep;
                ArrayList params;
                while(repetir){
                    params = new ArrayList();
                    System.out.println("Introduir Aula");
                    Aula = s.next();
                    System.out.println("Introduir hora <0-23>");
                    hora = s.nextInt();
                    System.out.println("Introduir dia");
                    dia = s.next();
                    params.add(Aula);
                    params.add(hora);
                    params.add(dia);
                    cd.afegirRestriccio(6,params);
                    System.out.println("Vols afegir-ne una altre?<y,n>");
                    rep = s.next();
                    if(rep.equals("n")) repetir = false;
               }       
            }
        }  
    }
    private static void generarHorari(){
        if ( cd.generar() ){
            cd.imprimeixHorari();
        }    
        else System.out.println("\n NO S'HA POGUT GENERAR CAP HORARI");
    }
    
    
    
    
    
    private static void generar() {
        
       cd.inicialitzaGenerador(); // inicialitza quadricula, aules i assignatures
       int opcio = 0;
        while( opcio != 3 ) {
            System.out.println(" OPCIONS ");
            System.out.println(" 1 - definir restriccions");
            System.out.println(" 2 - generar horari");
            System.out.println(" 3 - tornar");
            opcio = s.nextInt();     
            if(opcio == 1) definirRes();
            else if(opcio == 2) generarHorari();
        }
}


    private static  void carregar() {} // 3a entrega
    
    private static void modificar() {
        
        int opcio = 0;
        while( opcio != 4 ) {
            System.out.println(" OPCIONS ");
            System.out.println("1-assignatures\n2-aules\n3-restriccions\n4-tornar");
            opcio = s.nextInt();
            
            if(opcio == 1) opcionsAsignatures();
            else if(opcio == 2) opcionsAules();
            else if(opcio == 3) opcionsRestriccions();
        }
    }
    
    private static  void opcionsAsignatures(){
        
        int opcio = 0;
        while( opcio != 4 ) {
            System.out.println(" Unitat Docent:"+unitatDocent+" llista d'assignatures:");
            ArrayList llista = cd.llistaAssignatures();
            for( int i = 0; i < llista.size(); ++i)    
                System.out.println(llista.get(i) );
            
            System.out.println("");
            System.out.println(" OPCIONS ");
            System.out.println("1-crear\n2-esborrar\n3-modificar\n4-tornar");
            opcio = s.nextInt();
            
            System.out.println("");
            if(opcio == 1) {
                System.out.println("nom de la assigatura a crear ");
                String nomAsg;
                nomAsg = s.next(); // nom de la assignatura 
                if( cd.existeixAssignatura( nomAsg) ) System.err.println("ja existeix");
                else creaAssignatura(nomAsg);
            }
            else if(opcio == 2) esborraAssignatura();
            else if(opcio == 3) {
                System.out.println("nom de la assigatura a modificar ");
                String nomAsg;
                nomAsg = s.next(); // nom de la assignatura 
                if( ! cd.existeixAssignatura( nomAsg) ) System.err.println(nomAsg+" no existeix");
                else creaAssignatura(nomAsg);
            }
        }
    }
    
    /**
    * Crea una assigatura (  un arxiu .txt amb tota la info)
    * nom de l arxiu (unitat docent)-(nom assignatura)
    * 
    * ara nomes posa el nom pero tenen que posarse i validar tots
    * els parametres
    */
    private static void creaAssignatura( String nomAsg){  
       
            System.out.println("nivell");
            int nivell = s.nextInt();
            System.out.println("hores de teoria ");
            int horest;
            horest = s.nextInt();
            ArrayList<Integer> intervalsT = definirIntervalsHores( horest);
            System.out.println("hores de practica");
            int horesp;
            horesp = s.nextInt();
            ArrayList<Integer> intervalsP = definirIntervalsHores( horesp);
            System.out.println("Capacitat grups Teoria");
            int capTeo = s.nextInt();
            System.out.println("Capacitat grups Laboratori");
            int capLab = s.nextInt();
            System.out.println("Numero de grups de teoria");
            int ngt = s.nextInt();
            System.out.println("Numero de grups de laboratori");
            int ngl = s.nextInt();
            ArrayList<Integer> grups = new ArrayList();
            for(int i = 0; i < ngt; ++i){
                for(int j = 0; j < ngl+1; ++j) grups.add((i+1)*10+j);
            }
            cd.creaAssignatura( nomAsg, nivell , horest, intervalsT, horesp, intervalsP,
            capTeo ,capLab, grups);
        
    }
    
    /**
     * 
     */
    private static void opcionsAules(){
        int opcio = 0;
        while( opcio != 4 ) {
            System.out.println(" Unitat Docent:"+unitatDocent+" llista aules:");
            ArrayList llista = cd.llistaAules();
            for( int i = 0; i < llista.size(); ++i)
            System.out.println(llista.get(i) );
            System.out.println("");
            System.out.println(" OPCIONS ");
            System.out.println("1-crear\n2-esborrar\n3-modificar\n4-tornar");
            opcio = s.nextInt();

            System.out.println("");
            if(opcio == 1) creaAula();
            else if(opcio == 2) esborraAula();
            else if(opcio == 3) modificaAula();
        }
    }
    /**
     * 
     */
    private static void logout() {
        
    }
    
    /**
     * 
     * @param opcio 
     */
    private static void modificarRest(int opcio){
        if(opcio ==1) {
                ArrayList<String> llista = cd.llistaRest(1);
                if(!llista.isEmpty()){
                    int max = llista.size();
                    int rest = -1;
                    int i;
                    String a;
                    int g;
                    String aul;
                    System.out.println("");
                    System.out.println(" Unitat Docent:"+unitatDocent+" Llista restriccions tipus "+1+" :");
                    for(i = 0; i < llista.size(); ++i) { int num = i+1; System.out.println(num+"-  "+llista.get(i) );}
                    System.out.println("");
                    System.out.println("Quina es vol modificar?");
                    while( 1> rest || rest > max ) rest = s.nextInt();
                    System.out.println("Introdueix Assignatura");
                    a = s.next();
                    System.out.println("Introduix Grup");
                    g = s.nextInt();
                    System.out.println("Introduiex Aula");
                    aul = s.next();
                    ArrayList params = new ArrayList();
                    --rest;
                    params.add(rest);
                    params.add(a);
                    params.add(g);
                    params.add(aul);
                    cd.modificarRest(1,params);
                }
                else System.out.println("No hi ha restriccions d'aquest tipus");
                System.out.println("");
            }
            else if(opcio ==2) {
                
                ArrayList<String> llista = cd.llistaRest(2);
                if(!llista.isEmpty()){
                    int max = llista.size();
                    int rest = -1;
                    int i;
                    String a;
                    int g;
                    int h;
                    int d;
                    System.out.println("");
                    System.out.println(" Unitat Docent:"+unitatDocent+" Llista restriccions tipus "+2+" :");
                    for(i = 0; i < llista.size(); ++i) { int num = i+1; System.out.println(num+"-  "+llista.get(i) );}
                    System.out.println("");
                    System.out.println("Quina es vol modificar?");
                    while( 1> rest || rest > max) rest = s.nextInt();
                    System.out.println("Introdueix Aula");
                    a = s.next();
                    System.out.println("Introduix grup");
                    g = s.nextInt();
                    System.out.println("Introduix dia");
                    d = s.nextInt();
                    System.out.println("Introduiex hora");
                    h = s.nextInt();
                    ArrayList params = new ArrayList();
                    --rest;
                    params.add(rest);
                    params.add(a);
                    params.add(g);
                    params.add(d);
                    params.add(h);
                    cd.modificarRest(2,params);
                }
                else System.out.println("No hi ha restriccions d'aquest tipus");
                System.out.println("");
            }
            else if(opcio ==3) {
                
                ArrayList<String> llista = cd.llistaRest(3);
                if(!llista.isEmpty()){
                    ArrayList<Integer> posicio = new ArrayList();
                    int max = llista.size();
                    int rest = -1;
                    int i;
                    String a;
                    int g;
                    int h;
                    int num = 0;
                    System.out.println("");
                    System.out.println(" Unitat Docent:"+unitatDocent+" Llista restriccions tipus "+3+" :");
                    for(i = 0; i < llista.size(); ++i) { 
                        if(i%2 == 0) {++num; System.out.println(num+"-  "+llista.get(i) );}
                        else {
                            posicio.add(Integer.parseInt(llista.get(i)));
                        }
                    }
                    System.out.println("");
                    System.out.println("Quina es vol modificar?");
                    while(1> rest || rest > max)rest = s.nextInt();
                    System.out.println("Introdueix Assignatura");
                    a = s.next();
                    System.out.println("Introduix Grup");
                    g = s.nextInt();
                    System.out.println("Introduiex hora");
                    h = s.nextInt();
                    ArrayList params = new ArrayList();
                    --rest;
                    params.add(posicio.get(rest));
                    params.add(a);
                    params.add(g);
                    params.add(h);
                    cd.modificarRest(3,params);
                }
                else System.out.println("No hi ha restriccions d'aquest tipus");
                System.out.println("");
            }
            else if(opcio ==4) {
                
                ArrayList<String> llista = cd.llistaRest(4);
                if(!llista.isEmpty()){
                    ArrayList<Integer> posicio = new ArrayList();
                    int max = llista.size();
                    int rest = -1;
                    int i;
                    String a;
                    int g;
                    String d;
                    int num = 0;
                    System.out.println("");
                    System.out.println(" Unitat Docent:"+unitatDocent+" Llista restriccions tipus "+4+" :");
                    for(i = 0; i < llista.size(); ++i) { 
                        if(i%2 == 0) {++num; System.out.println(num+"-  "+llista.get(i) );}
                        else {
                            posicio.add(Integer.parseInt(llista.get(i)));
                        }
                    }
                    System.out.println("");
                    System.out.println("Quina es vol modificar?");
                    while( 1> rest || rest > max) rest = s.nextInt();
                    System.out.println("Introdueix Assignatura");
                    a = s.next();
                    System.out.println("Introduix Grup");
                    g = s.nextInt();
                    System.out.println("Introduiex Dia");
                    d = s.next();
                    ArrayList params = new ArrayList();
                    --rest;
                    params.add(posicio.get(rest));
                    params.add(a);
                    params.add(g);
                    params.add(d);
                    cd.modificarRest(4,params);
                }
                else System.out.println("No hi ha restriccions d'aquest tipus");
                System.out.println("");
            }
            
            else if(opcio ==5) {
                
                ArrayList<String> llista = cd.llistaRest(5);
                if(!llista.isEmpty()){
                    int max = llista.size();
                    int rest = -1;
                    int i;
                    String a1;
                    int g1;
                    String a2;
                    int g2;
                    System.out.println("");
                    System.out.println(" Unitat Docent:"+unitatDocent+" Llista restriccions tipus "+5+" :");
                    for(i = 0; i < llista.size(); ++i) { int num = i+1; System.out.println(num+"-  "+llista.get(i) );}
                    System.out.println("");
                    System.out.println("Quina es vol modificar?");
                    while( 1> rest || rest > max) rest = s.nextInt();
                    System.out.println("Introdueix Assignatura 1");
                    a1 = s.next();
                    System.out.println("Introduix Grup 1  <-1 si no es vol modificar>");
                    g1 = s.nextInt();
                    System.out.println("Introduiex Assignatura 2");
                    a2 = s.next();
                    System.out.println("Introduiex Grup 2  <-1 si no es vol modificar>");
                    g2 = s.nextInt();
                    ArrayList params = new ArrayList();
                    --rest;
                    params.add(rest);
                    params.add(a1);
                    params.add(g1);
                    params.add(a2);
                    params.add(g2);
                    cd.modificarRest(5,params);
                }
                else System.out.println("No hi ha restriccions d'aquest tipus");
                System.out.println("");
            }
            else if(opcio ==6) {
                
                ArrayList<String> llista = cd.llistaRest(6);
                if(!llista.isEmpty()){
                    int max = llista.size();
                    int rest = -1;
                    int i;
                    String a;
                    String d;
                    int h;
                    System.out.println("");
                    System.out.println(" Unitat Docent:"+unitatDocent+" Llista restriccions tipus "+6+" :");
                    for(i = 0; i < llista.size(); ++i) { int num = i+1; System.out.println(num+"-  "+llista.get(i) );}
                    System.out.println("");
                    System.out.println("Quina es vol modificar?");
                    while( 1> rest || rest > max) rest = s.nextInt();
                    System.out.println("Introdueix Aula");
                    a = s.next();
                    System.out.println("Introduix dia");
                    d = s.next();
                    System.out.println("Introduiex hora");
                    h = s.nextInt();
                    ArrayList params = new ArrayList();
                    --rest;
                    params.add(rest);
                    params.add(a);
                    params.add(d);
                    params.add(h);
                    cd.modificarRest(6,params);
                    
                }
                else System.out.println("No hi ha restriccions d'aquest tipus");
                System.out.println("");
            }
        
    }
    
    
    private static void esborrarRest(int opcio){
        ArrayList<String> llista = cd.llistaRest(opcio);
        int i;
        int max;
        int rest;
        int num;
        if(opcio == 3 || opcio ==4){
            if(!llista.isEmpty()){
                ArrayList<Integer> posicio = new ArrayList();
                max = llista.size();
                rest = -1;
                num = 0;
                System.out.println("");
                System.out.println(" Unitat Docent:"+unitatDocent+" Llista restriccions tipus "+4+" :");
                for(i = 0; i < llista.size(); ++i) { 
                    if(i%2 == 0) {++num; System.out.println(num+"-  "+llista.get(i) );}
                    else {
                        posicio.add(Integer.parseInt(llista.get(i)));
                    }
                }
                System.out.println("");
                System.out.println("Quina es vol esborrar?");
                while( 1> rest || rest > max) rest = s.nextInt();
                --rest;
                cd.esborraRest(opcio,posicio.get(rest));
            }
            else System.out.println("No hi ha restriccions d'aquest tipus");
            System.out.println("");
        }
        else{
            if(!llista.isEmpty()){
                max = llista.size();
                rest = -1;
                System.out.println("");
                System.out.println(" Unitat Docent:"+unitatDocent+" Llista restriccions tipus "+opcio+" :");
                for(i = 0; i < llista.size(); ++i) {num = i+1; System.out.println(num+"-  "+llista.get(i));}
                System.out.println("");
                System.out.println("Quina es vol esborrar?");
                while( 1> rest || rest > max ) rest = s.nextInt();
                --rest;
                cd.esborraRest(opcio,rest);
            }
            else System.out.println("No hi ha restriccions d'aquest tipus");
            System.out.println("");     
        }
    }
    
    private static void modRest(int decisio){
       int opcio = 0;
        while( opcio != 7 ) {
            opcio = 0;
            if(decisio == 1)System.out.println(" Indica el tipus de restriccio a modificar ");
            if(decisio == 2)System.out.println(" Indica el tipus de restriccio a esborrar ");
            System.out.println(" 1 - Restringir un grup a una aula");
            System.out.println(" 2 - Restringir un grup a un dia i hora");
            System.out.println(" 3 - Restringir una hora en la que no es pugui impartir una asignatura+grup");
            System.out.println(" 4 - Restringir un dia per a que no es pugu impartir una assig+grup");
            System.out.println(" 5 - Una assig+grup no es pot impartir a la vegada que una altre temporalmente parlant");
            System.out.println(" 6 - Forzar una aula a no poderse usar un dia/hora");
            System.out.println(" 7 - Tornar");
            while(1>opcio || opcio>7){opcio = s.nextInt();if(1>opcio || opcio>7) System.out.println("valor no valid. Torna a seleccinar");}
            switch (decisio) {
            case 1: modificarRest(opcio);
                    break;
            case 2: esborrarRest(opcio);
                    break;
       }
        
        
    }
        
    }
    private static void opcionsRestriccions(){ 
        int opcio = 0;
        while( opcio != 3 ) {
            System.out.println(" OPCIONS ");
            System.out.println("1-modificar\n2-esborrar\n3-tornar");
            opcio = s.nextInt();
            
            System.out.println("");
            if(opcio == 1 || opcio == 2) modRest(opcio);
        }
        
    }
    
    private static ArrayList<Integer> definirIntervalsHores( int numHores ){
        ArrayList<Integer> intervals = new ArrayList<Integer>();
        System.out.println("vos dividir les "+numHores+" en intervals ? (s/n)");
        String resposta;
        resposta = s.next();
        if( resposta.equals("s")) {
            System.out.println("HAS RESPONDIDO "+resposta);
            System.out.println("quants intervals vols ?");
            int numInt;
            numInt = s.nextInt();
            while( numInt > numHores ) {
                System.err.println("impossible\n quants intervals vols ?");
                numInt = s.nextInt();
            }
            int horesUsades = 0;
            for( int i = 1; i <= numInt; ++i ){ // ASSUMIM QUE L USUARI HO FA BÉ
                System.out.println("numero d'hores de l'interval "+i+ " ( usades "+horesUsades+" )");
                int nh = s.nextInt();
                horesUsades = horesUsades+nh;
                intervals.add(nh); // es posa l'interval
            }
            if( horesUsades != numHores) System.err.println("numero d'hores incorrecte");
        }
        else {
            intervals.add(numHores);
        }
        return intervals;
    }
    
    private static void esborraAssignatura(){         
        System.out.println("nom de la assigatura a esborrar ");
        String nomAsg = s.next(); // nom de la assignatura 
        if( ! cd.esborraAssignatura(nomAsg) ) 
            System.err.println(nomAsg+" no existeix "); 
    }
    

    private static void creaAula(){  
        
        System.out.println("nom de la aula a crear ");
        String nomAula = s.next(); // nom de la aula 
        if( cd.existeixAula( nomAula) ) System.err.println("ja existeix");
        else {
            System.out.println("es de teoria? (y/n)");
            String y = s.next();
            System.out.println("capacitat ");
            int c = s.nextInt();
            if (y.equals("y")) System.out.println("te projector? (y/n)");
            else System.out.println("te material?(y/n)");
            String n = s.next();
            int teo, boo;
            if (y.equals("y")) teo = 1;
            else teo = 0;
            if (n.equals("n")) boo = 0;
            else boo = 1;
            cd.creaAula(nomAula, c, teo, boo);
        }
    }
    
    private static void esborraAula(){         
        System.out.println("nom de l'aula a esborrar ");
        String nomAula = s.next(); 
        if( ! cd.esborraAula(nomAula) ) 
            System.err.println(nomAula+" no existeix "); 
    }
    
    private static void modificaAula() {
        
        System.out.println("nom de la l'aula a modificar ");
        String nomAula;
        nomAula = s.next();
        if( ! cd.existeixAula( nomAula) ) 
            System.err.println(nomAula+" no existeix");
        else {
            cd.printAula(nomAula);
            System.out.println("es de teoria? (y/n)");
            String y = s.next();
            System.out.println("capacitat ");
            int c = s.nextInt();
            if (y.equals("y")) System.out.println("te projector? (y/n)");
            else System.out.println("te material?(y/n)");
            String n = s.next();
            int teo, boo;
            if (y.equals("y")) teo = 1;
            else teo = 0;
            if (n.equals("n")) boo = 0;
            else boo = 1;
            cd.creaAula(nomAula, c, teo, boo);
        }
    }
}
