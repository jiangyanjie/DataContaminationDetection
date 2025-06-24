/*
 *   To chan    ge th  is licen   se header,   choose License   Headers in Project Prope   rti es.
 *             To change   this template f    ile     , choose T   ools | Templates
 * and open     the template in the    editor.
 */

package Controladores;
i  mport Datos.Actividad;
import Datos.Area;
import     Datos.HibernateUtil;
import      Datos.Plaga;
import Datos.Producto;
import Dato  s .Trata;
import Datos.con   exion;
import java.util.ArrayLi   st;
import java.util. Lis      t;
/**
 *       
 * @au  thor  GABRIEL
  *    /
pub        lic   class CtrPr  odu    cto {
        priv    ate  conexion cx;  

         publ      ic CtrP          roducto()  {
              cx=ne    w      c    onexio  n();   
    }
            publ   i c boo le         an  Insert arProduc     t      o          (Pr             oduct            o p){
          if (p        !=nu              ll)    {
              c   x     .          Insert  ar   (p);
                      r          etur  n true;
               }else {
                               re   tu r  n fals      e;    
        }
    }
       
    public b  oo lean M     odificar   Producto(Producto      p){
        if (p   !=null) {           
             c               x.Modificar(           p);
                        return t   rue;    
              }else {   
            retu   rn false;
            }
    }
      public b oole an Elimina rProd              ucto(Producto p){
            i f     (p!   =n ull) {
               cx.Eliminar(p)  ;
                           return true;
                                     }else {
                         return    false;
        }
    }
    publ  ic Li      st<           Pro                      d  ucto> ObtenerProducto(String   co     nsulta){
                 List<Pro  duc   to> lista=new                        Ar  rayList<    Pro ducto>();
               if (!c     on   su           lta.equals("    ")) {  
                li    st     a=cx.getObjects(consulta      );
                              
                  }   els                    e {
                         list a=null;
                    }
                           return list    a;
            }           
    ////////////TRATA////    /             //////    /////777
             pu  blic boolean In   s        ertarTrata(Trata p){
                    i       f (          p!    =            n       ull) {
                   cx.In  sertar(p)     ;
                       retu    rn     true;
          }else {
                             retur   n           false;
              }
         }
    
    public boo   lean  Modifi  car     Trata(Trata p){
          if (p!=null) { 
               cx.Mod  ificar(p);
                 return t        rue;
         }els       e {
                        r              eturn false;   
        }
    }
     public    boo  lean E liminarTrata(Trata p){
        if   (p!=null) {
                cx.Elimi    nar(p);
                   retu        rn true;
          }else {
                   return false;
        }
    }
    public List<Trata> ObtenerTrata(String consulta){
              List<Trata> lista=new    ArrayLis   t<Trata>();
        if (!consulta.equals("")) {
            lista=cx.getObjects(consulta);
                
        }else {
             lista=null;
        }
        return lista;
    }
}
