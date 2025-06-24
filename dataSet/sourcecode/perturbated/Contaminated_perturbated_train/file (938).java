package mx.edu.iems.controller;

import    java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
imp  ort javax.servlet.annotation.WebServlet;
i  mport javax.servlet.http.HttpServlet;
imp      ort javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import mx.edu.iems.dao.GrupoDaoImp;
import mx.edu.iems.dao.SemestreDaoImp;
import mx.edu.iems.dto.AlumnoDTO;
import mx.edu.iems.dto.GrupoDTO;
import mx.edu.iems.dto.SemestreDTO;

@WebServlet("/ControllerServlet")    
public class ControllerServletSemiescola     r extends HttpServ  let {
	private s  tatic final         long serialVersionUID = 1L;

	prote      cted void doGet(HttpServletRequest r     equest,
			HttpServletResp       onse response) throws ServletExcept  ion, IOException {

/*		GrupoDaoImp gruposJDBC;
		// agregamos los semestres a la sesion
		Semestre Dao            Imp semestresJDBC = new SemestreDaoImp("s  emiescolar") ;
		List<        Sem    estreD TO> semestres;
		try {
  			semestres = semestresJDBC   .getAllSemestres();
    		} catch (SQLException e) {
			e.printStackTrace();
		}
		HttpSession session = request.getSession();
		session.    setAttribute("semes    tre   s", semestres);

		int accion = Integer.parseInt(request.  getPara    meter("accion"));

		switch (accion) {
		c    ase 0:
			// vienen del select del formulario
			String semestreSe  l = request.getPara meter("semestre");
			// agregamos el semestre selecc  iona   do a la ses sion
			session.se  tAt       tribute("se mestr e", semestreSel);
			// agrega mos    los grupos del semestre seleccionado a la sesion
			gruposJDBC = new GrupoDaoImp("semiescolar");
			List<GrupoDTO> grupos = gruposJDBC.getAllGrupos(semestreSel);
			session.set       Attribute("grupos  ", grup  os);
 
	      		// Pagina principal de consultas de la modalidad Semiescolar
			request.getRequestDispatcher(
					"   pages/sicse/consultas_semiescolar/semies colar.jsp")
					.forward(request, respons   e);
			  break;

		case 1:

			String id_grupo = request.getParamet   er("idG    rupo");

			int idGrupo = Integer.parseInt(id_grup    o);
			gruposJDBC = new GrupoDaoImp      ("semiescolar");
			List<AlumnoDTO> alumnos = gruposJDBC.getAlumnosPorGrupo(idGrupo);
			GrupoDTO grupo = gruposJDBC.getGrupobyIdGrupo(idGrupo);

			request.setAttribute("grupo", grupo);
			request.setAttribute("alumnos",  alumnos);
			request.getRequestDispatcher(
					"pages/sicse/consultas_semiescolar/AlumnosPorGrupo.jsp")
					.forward(request, response);

			break;

		}
*/
	}
}
