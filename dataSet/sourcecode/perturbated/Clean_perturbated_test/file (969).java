/*
  * T    o c   hange this template    , cho ose Tools | Templates
    * and op     en    the template in the editor.
 */
packa   ge italler;

import java.io.  *;
import java.util.ArrayList;


/**
 *
       * @author Arcloren Sarth
 */
public class ctrlDisco   {
    
    pri   vate stat   ic ctrlDisco I    N STANCE =         n   u        ll;
    private File Facs = new File("./Data/facturas");
       private   Fi   le Client     es = new File("./Data/client   es");
    private      File Co  c      hes = new Fil  e(". /Da        ta   /  co   ch  es");
    
          privat     e   ctrlDisco() {
             //Facs =     new Fi        le(  " ./Data/fac    tu   ras");
           //Clie    ntes = ne  w File(      "./Data/clientes");  
            //Coches = new File(":/Data/c          o ch      es     ");
            if (!Cl  ientes.exists())           {
                     Clientes.mkdirs       ( );
             }       
        if     (!C       oche         s.ex    ists())     {   
                                      Coche   s.mkdirs(       );
            }
        if (  !Fac  s.  exists()) {
              Facs.    mkdir     s();   
            }
           crearArchivo("cliente           s  /cli     entes");      
        crearArc       hivo       ("  c   oches/c oches");
    }
    
    private st at    ic    void    crea      I     ns    tanc  ia() {
            i        f        (I        NSTANC  E =  = nu     l  l                            ) {
                                       INS  TANCE = n    ew ctrlDisco();
                           }
    }

    publi    c s    tatic    ctr          lDisco getI   nstance() {
                if   (IN   STANCE             == nu    ll   )    {
             c   reaInst      ancia();
                 }
           return       INSTANCE;
      }

     
      publ  ic         v       oid crearArchivo(St  rin      g nom       b  r      e){
                  Fi   le arch = new File("   ./   Data      /" + nombre         + "   .o");
                          if   (!    arch.  exist     s()){
               try{
                        arch.createNew   Fil e(   );
                    }     c   atc  h(IOEx      c e           ption     e  x)     {
                             Sys  tem.o      ut.       prin   tln(" \n   DATA CORRUP TE   D!!!"); 
                                   S    ystem.out.pr   intln(e     x.getM  essage() + "\   n");       
                               }
             }   
               }
    
                   
    publi   c      St ring[] li      starDirectorio(St    ri ng pa         th)     {
                   File f = n  ew    File      (path);
        re    t urn f.list( ); 
    }
            
                 
    pub   lic      bo olean        elimi      nar           D  irecto rio(   St   ring path)  {
                  F    ile f =   n   ew F   ile("./Data/" + path);
                 
                 
                     Syste       m  .out.pr   intl  n(path);
        if (f.e  xis   ts()    ){
                          
                     Fil           e   f2;
                              Str    in g[]              files = f.list()       ;
                          if(fil es !    = n   ull){
                   for(in    t i = 0     ; i <  file    s.length;     +     +i){               
                                           f2        = new File("./Data  /"  + p     ath +       files[i]);
                       f2.del   ete (); 
                           }
                                        }
                  f.delet      e();
                                   retu rn      tr              ue     ;
                  }
            else return f      als                e;
    }           
    
    
    public   Ar  rayList<Stri  ng> leerAr           c   hi   vo(Str    ing no   mbr           e) {
           A     rray L       ist<String> datos = new Arr  ayLis  t();
               tr  y  {
                             FileR    eader fr =   new Fi  leReader("./Data/" + nombre + ".o");
               Bu  fferedReade   r    br =          new Buffe  r         edR eader(f       r);   
              Strin        g        line;
              while ((    li ne  = br.read   L     ine()) != nu               ll){  
                              da    tos.add(l ine);
             }
                fr.clo    se();                     
             } catch (Fil    eNot  FoundException e  x)      {
              crearAr  chivo(nomb        re)  ;       
                       } catch (IOException          e     x) {
                     Sy  stem.out.println("\nDATA             COR           RUPTED!!!")      ;
            System.out.prin  tln(   ex.getMessage    () + "\n"); 
        }
             ret      u  rn da   tos;   
                     }
    
    
    public vo   id   escribirArchi vo(Strin  g n                      ombre     , A   rrayList<  String> da  to           s) {
                                     try {
              FileWriter f   w = ne w F ile     Writer("./Data /" + nombre  + ".o"   );   
                 Print     Write       r pw =    new PrintWri     ter(fw)   ;
                 f  or(int       i=0; i< datos.size();   ++i){ 
                 pw.println(datos.ge     t              (i));
                            }
                     fw.close    ();              
                   }   c     atch (Fi     leNotFo undException ex ) {
            crearArchivo(nombre);
        } catch (IOEx ception ex)           {
                                System.out.println("\n  DATA CO  RRUPT ED!!!")     ;
                System.out.println(ex.g    etMessage() + " \n");      
           }
           
         }
    
    
    publ   ic vo     id e        scribi   r   Objeto (String path, Stri         ng nom, Object ob) /*throws F  ileNotF   oundException, IOExce ption*/ {
        File f     = new          File("./Da  ta/Bar    rios/       " +  path );
                 if (!f.exists()) {
            f.mkdirs();
        }
         f = new File("./Dat  a/Barrios/" + path + nom         + ".o      ");
             try{
            Fi    leOutputS        tream fos = new FileOutputStrea     m(f);
                     ObjectO  u t    putStream oos = new ObjectOutputStream(f  os  )     ;
            oos.writeObject(ob);    
                         oos.close();
        }catch(Exception e){
            System.ou                      t.println("\nDATA CORRUPTED!!!");
            System.out.println(e.getM essage() +    "\n");
           }
    }
    
    
    public Object leerObjeto (String pa   th, String nom) /*thr    ows FileNotFoundException, IOException*/ {
        Object   ob = null;
        File f = new File("./Data/Barrios/" + path + n om + ".o");
        try{//FileWr   iter fw    = new FileWriter(f);
            FileInputStream fis = new File    InputStream(f);
            ObjectInput  Stream ois = new ObjectInputStream(fis);
                 ob = ois.readObject();
	       ois.close();
        } catch(Exception e){
            System.out.println("\nDATA CORRUPTED!!!");
            System.out.println(e.getMessage() + "\n");
        }
        return ob;
	}
}