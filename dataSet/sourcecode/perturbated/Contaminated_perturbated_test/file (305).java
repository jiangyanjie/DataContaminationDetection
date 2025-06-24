/*
          *   To change   this template, choo     se Tools | Templates
 * and       open the tem      plate in the editor.
 */
p    ackage runpatterncomp;
import java.io.Ser   ializable;
import java.io.ObjectOutputStream;
import java.io.FileOut   putStr  eam;
  import j  ava.io.IOException     ;
/**
 *
 * @author sabs231
    */
public class DataCreator   
{
        pr      iv  ate sta          tic final Strin   g DEFAULT_FILE = "d    ata.      ser";
    
              public sta    tic    v   oid ma   i            n (St            r     ing  []  a   rgs  ){ 
                   String f ile    Name;
            if (args.length == 1){
                                          fileNam         e = args[0];
        }
               else{
                                 fileName =       DEFAULT_F       I         LE;     
               }
             serialize(   fileName);
      }
    
         pu        blic stat    ic void seri    alize(Stri                        ng fileName)     {
        try{
                        serializeToFile(cre     a  teData(), fi   leN   ame     );
        }
                   catch (IOExc    ep                tion    exc){
                       exc   .printStackTrace();
             }
    }
       
    private static Serializable cr         eat           eData(){   
        Contact contac      t1 = new ContactImpl("Ivan", "Ambres", "Jefe de     Pro    yec  t  o", "GDI Info      rmÃ¡tica  ");
        C      o   ntact cont  act2 =  new C  ontactImp    l("Carmen", "GarcÃ­a",       "A   nalist     a Progra  mador", "M  icrosoft");
        Contact co      ntac         t  3 = ne w ContactImpl("Guille   rmo", "Ser              ra       no", "In    geniero de Pruebas", "Fun   cio      na BIE      N TOD    O" ) ;
               Cont    ac   t   contact4 = new ContactIm pl   ("Jorge",    "Mateo", "Arquitecto d  e      Softwa    re", "SOFTGAL");
                               
        
        Project pro ject =    new Project ("Portal    GDI  ", "Desarrollo del Portal  Corpora     tivo de GDI");
           
        Deliver     able deli            verable1 = new Deliverable("Plan   Maestro", "Docume   nto del     Proyecto", contact1);
           Task     tas    k1 = ne      w Task("Def         inir y Analisar",    "Ingenier Ã­a  de Requisitos del Sistema", contact2, 11.0);
        Tas     k ta    sk2 = new Task("DiseÃ±o", "DiseÃ±o del Siste   ma   ",            conta   c     t4, 7.   5);
                      Task    task3 = new Task("Im     p   lementar", "I  mplementar la aplicaciÃ³n", cont     act3, 10.2)       ;
        proj   ect.ad  dProjec  tIt    em(deliverable1);
            p  roject.addProjectItem    (ta       s   k1);
        project.addProject   I       tem(ta   sk2);
        p  roj    ect.addProjectI   tem     (task3);         
        
              Deliv erable deliverable11 = new D   eliverable("SRS","Documento de Requisitos"    , contact1);
        Task    task11 =        new Task("Definir A  c  tores", "Analizar los act   ores que int     ervie    n  e            n en el proyecto", con    tact4, 2   .5);
        Ta s     k task12 = new   Task("Ident  ificar CU", "Desarr   ollar el       modelo de  casos   de uso   del dis    te ma", contact1 , 14.0);
        task1.addP   rojectItem(ta    sk11);
        task1.addPr  ojectItem(tas       k12)  ;  
        t   ask1.    addPro     jectItem(delivera  ble1    1);
                  
        
          Task task21 = new Task("Di     seÃ±o EstatÃ¡tico", "Desarrollar   los     m      odelos de clase    s de cada CU", contact1, 1. 8)     ;
                Ta      sk task22 = new Task("Di   seÃ±o   DinÃ¡mic  o", "Des   arrollar los modelos de i   n    teraciÃ³n de cada CU", contact4, 5.0);
        Task task2  3 = new   Task  ("Modelo desplique"    , "Desarrol   lar  el modelo de despliegue", contact3   , 17.5 );
        task2.addProjectItem(task21);
           task2        .addProjectItem(  task22);
        task2.addProjectItem(task23);           
        
        Deliverable deliverabl   e31 = new Deliverable("CÃ³d  igo Fuente", "Los distintos cÃ³digos desarrolladosCo", contact1);
            task3.addProjectItem(deliverable31);
          return project;
    }
    private static void serializeToFile(Serializable content, String fileName) throw s IOException{
        ObjectOutputStream serOut = new ObjectOutputStream(new FileOutputStream(fileName));
        serOut.writeObject(content);
        serOut.close();
    }
}
