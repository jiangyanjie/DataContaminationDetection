








package es.mundo.controladores;








import java.io.IOException;









import java.util.ArrayList;

import javax.servlet.RequestDispatcher;


import javax.servlet.ServletException;






import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;













import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import es.mundo.modelo.Negocio;
import es.mundo.modelo.Pais;









/**
 * Servlet implementation class DarAltaServlet




 */
@WebServlet("/DarAlta")




public class DarAltaServlet extends HttpServlet {










	private static final long serialVersionUID = 1L;



       



    /**
     * @see HttpServlet#HttpServlet()
     */
	
	/* Constructor por defecto */

    public DarAltaServlet() {




        super();



    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */

    
    /* MÃ©todo doGet */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    /* 1 Recuperar los datos de la URL */
   	/* 2 Adaptarlos si es necesario al tipo de datos del modelo*/
    
    // recibe un String, devuelve un String



    String nombre = request.getParameter("nombre"); 



    
    // recibe un String, le damos la vuelta para convertirlo en un entero con "Integer.parseInt("	
    int habitantes = Integer.parseInt(request.getParameter("habitantes")); 
 		
    /* 3 Pasarle los datos recuperados a la capa Negocio */
    Negocio negocio = new Negocio();










    int id=negocio.darAlta(nombre, habitantes);
    
    // ademÃ¡s de darlo de alta en la BBDD lo muestro...
    // consultar el pais y...
    //->Pais p=negocio.consultarUno(id);
    // meter (setear) el pais en el request para que en vistaIndividual.jsp lo pueda recuperar
    //->request.setAttribute("pais", p); // voy a consultar en BBDD por si borran el pais
    










    //... redirigir a la vista individual
    //->RequestDispatcher rd;
    //->rd=request.getRequestDispatcher("vistaIndividual.jsp");
    
    //... o redirigir a la vista o consulta "mostrarTodos"
    ArrayList<Pais> paises=negocio.consultarTodos();
	
	// meter el arrayList en el request
	request.setAttribute("listado", paises);
	// redirigir al cÃ³digo jsp "mostrarTodos"
	RequestDispatcher rd;
	rd=request.getRequestDispatcher("mostrarTodos.jsp");


    // rd=request.getRequestDispatcher("mostrarTodos.jsp");
    
    rd.forward(request, response);



    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub


	}

}
