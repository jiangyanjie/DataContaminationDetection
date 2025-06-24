/*
      * To   chan  ge this lic  ens    e header, choose     License Headers in Proje        ct Properties.
 * To change this template f    ile,    ch  oose T         ools | Tem      plates
 * and open the    templ     ate   in the editor.
 */

package Controladores;
import Datos.A      fecta;
import Datos.HibernateUtil;
import Datos.Plaga;
import Datos.conexion;
import java.util.ArrayList;
import java.util.Li  st;
/**           
    *
 * @     author GABRI EL
 */
p ublic class CtrPl aga {
    p   rivate  conexion cx;

         p   ublic         CtrPlaga( )           {
        cx  =new c    on   exion();
      }
    publi   c b      o                 olean Insertar   Plaga(Plaga    p){       
         i  f ( p!=nu      ll) {
                 cx.Inserta    r(p);
                       r         etur           n   true;
                    }else                    {
                      retu     rn f alse;
             }
    } 
                         
    public boole      a    n  ModificarPlaga(Plaga p){
        if (p!=null) {
                        cx.Modificar(p);
            return true;
                        }el        s        e     {
                               retur    n fa       lse;
        }  
              }
               pu   blic boo  lean           Elim  inar       Plaga    (Plaga p){
                if   (  p!=null) {    
                    cx.El   imina         r(p);   
                    r eturn    true  ;
            }  els e       {
                 return fal   se;
               }
     }
     public      List             <Plaga>   O    bten erPlagas(Strin   g consulta){
                 List<Pl      aga> lista=new  ArrayL ist<Plag     a>();
          if (!co  ns              ulta.equ       als(   "")) {  
                           lista        =c    x              .getObjec             t   s             (cons   u   lt           a);
            
            }els   e {
                 lis          ta=null;
                 }    
         re   tu   rn lista ;
         }
        //      ///          ///////////     //A  F     ECTA   /         //////    //   /////                //
      pub  l    ic boolean Ins  ertarAfe   ct     a(Afect    a p){
                        if (p!=null)   {
              cx     .Insertar( p);
                    return  true;
             }e      lse {
                      retur     n          false;
                       }
    }   
       
    public     boolean   Modificar  Afecta(Afecta p)          {     
        if (p!=nul       l  ) {
                      c    x.Modifica       r(p   );
                    return true;
         }else {
               r      et   urn false;
           }
       }
        p ub  lic boolean EliminarAf    ecta      (A      fecta p){   
        i   f (p!=null) {
              cx   .     Eliminar(p);
            return tr  ue;
        }else {
                 re  tu    rn    false;
        }
        }
        publi     c List<Afect  a> ObtenerAfecta(String        consulta){
        List<Afecta> lista=ne    w  Arra   yList<Afecta  >();
        if (!consulta.equals("")) {
            lista=cx.getObjects(consulta);
            
        }else {
           lista=null;
        }
        return lista;
    }
}
