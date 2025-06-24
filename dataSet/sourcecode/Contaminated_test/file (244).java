/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uniriotec.dasbuch;

import br.uniriotec.dasbuch.dao.DasbuchDAO;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.ejb.Stateless;

import br.uniriotec.dasbuch.entity.*;
import java.util.Date;
import java.util.Calendar;

/**
 *
 * @author afonso
 */
@WebService(serviceName = "DasbuchWS")
@Stateless()
public class DasbuchWS {
    
    private static final double CUSTO_ENTREGA = 10.0;
    private static final int TEMPO_ENTREGA = 1; // dias apos a retirada
    private static final int HORARIO_INICIO_ENTREGAS = 8;
    private static final int HORARIO_FIM_ENTREGAS = 18;

    /**
     * Web service operation
     */
    @WebMethod(operationName = "procederTransporte")
    public ReciboTransporte procederTransporte(
        @WebParam(name = "pedido") String pedido,
        @WebParam(name = "notaFiscal") String notaFiscal,
        @WebParam(name = "cliente") Cliente cliente,
        @WebParam(name = "retirada") Endereco retirada, 
        @WebParam(name = "entrega") Endereco entrega,
        @WebParam(name = "livro") Livro livro,
        @WebParam(name = "livraria") int livraria) {
        
        ReciboTransporte response = new ReciboTransporte();
        
        response.setNumeroDoPedidoCliente(pedido);
        response.setCliente(cliente);
        response.setEnderecoRetirada(retirada);
        response.setEnderecoEntrega(entrega);
        response.setCusto(CUSTO_ENTREGA);
        
        // data de retirada: mesma data da requisicao
        Date dataRetirada = new Date(System.currentTimeMillis());
        response.setDataRetirada(dataRetirada);
        
        // calcula data de entrega a partir da data de retirada
        Calendar c = Calendar.getInstance(); 
        c.setTime(dataRetirada); 
        c.add(Calendar.DATE, TEMPO_ENTREGA);
        
        // data de entrega: <TEMPO_ENTREGA> dias apos data de retirada
        Date dataEntrega = c.getTime();
        
        // tranportadora nao entrega antes das <HORARIO_INICIO_ENTREGAS>h!
        if(dataEntrega.getHours() < HORARIO_INICIO_ENTREGAS) {
            dataEntrega.setHours(HORARIO_INICIO_ENTREGAS);
            dataEntrega.setMinutes(0);
            dataEntrega.setSeconds(0);
        }
        // tranportadora nao entrega apos as <HORARIO_FIM_ENTREGAS>h!
        if(dataEntrega.getHours() > HORARIO_FIM_ENTREGAS) {
            dataEntrega.setHours(HORARIO_FIM_ENTREGAS);
            dataEntrega.setMinutes(0);
            dataEntrega.setSeconds(0);
        }
        response.setDataEntrega(dataEntrega);
        
        DasbuchDAO dao = new DasbuchDAO();
        dao.persistir(response, livro, livraria, notaFiscal);
        
        return response;
    }
   
}
