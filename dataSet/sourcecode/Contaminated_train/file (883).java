/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package servidoragro;

import Eventos.EventoBD;
import Interfaces.EscuchadorEventoMensajeBD;
import Protocolo.MensajeBD;
import Protocolo.Respuesta;
import Util.ListaEscuchadoresBD;
import Util.ListaEscuchadoresMsjBD;

/**
 *
 * @author GABRIEL
 */
public class ControladorBD implements EscuchadorEventoMensajeBD{

    @Override
    public void alRecibirPaqueteGuardar(EventoBD e) {
    System.out.println(e.mensajeBD.getKeyPort()+" : " +e.mensajeBD.getNombreTabla());
        
    MensajeBD msjRBD=new MensajeBD(e.mensajeBD.getKeyPort(), "123", "Propietario", null, ""+Respuesta.MENSAJE_ERROR);
        EventoBD msjrespuestaBD=new EventoBD(this, msjRBD);                        
        
        ListaEscuchadoresMsjBD.DispararOnRecibirPaqueteBD(msjrespuestaBD);
        //FIN Envio de Respuesta
        
        System.out.println("SE GUARDO CORRECTAMENTE LAS TABLAS..");
    
    }

    @Override
    public void alRecibirPaqueteModificar(EventoBD e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void alRecibirPaqueteEliminar(EventoBD e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void alRecibirPaqueteConsulta(EventoBD e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
