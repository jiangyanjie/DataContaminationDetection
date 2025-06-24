package mx.edu.iems.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import mx.edu.iems.dao.GrupoDaoImp;
import mx.edu.iems.dao.SemestreDaoImp;
import mx.edu.iems.dto.AlumnoDTO;
import mx.edu.iems.dto.GrupoDTO;
import mx.edu.iems.dto.SemestreDTO;

@WebServlet("/ControllerServlet")
public class ControllerServletSemiescolar extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

/*		GrupoDaoImp gruposJDBC;
		// agregamos los semestres a la sesion
		SemestreDaoImp semestresJDBC = new SemestreDaoImp("semiescolar");
		List<SemestreDTO> semestres;
		try {
			semestres = semestresJDBC.getAllSemestres();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		HttpSession session = request.getSession();
		session.setAttribute("semestres", semestres);

		int accion = Integer.parseInt(request.getParameter("accion"));

		switch (accion) {
		case 0:
			// vienen del select del formulario
			String semestreSel = request.getParameter("semestre");
			// agregamos el semestre seleccionado a la session
			session.setAttribute("semestre", semestreSel);
			// agregamos los grupos del semestre seleccionado a la sesion
			gruposJDBC = new GrupoDaoImp("semiescolar");
			List<GrupoDTO> grupos = gruposJDBC.getAllGrupos(semestreSel);
			session.setAttribute("grupos", grupos);

			// Pagina principal de consultas de la modalidad Semiescolar
			request.getRequestDispatcher(
					"pages/sicse/consultas_semiescolar/semiescolar.jsp")
					.forward(request, response);
			break;

		case 1:

			String id_grupo = request.getParameter("idGrupo");

			int idGrupo = Integer.parseInt(id_grupo);
			gruposJDBC = new GrupoDaoImp("semiescolar");
			List<AlumnoDTO> alumnos = gruposJDBC.getAlumnosPorGrupo(idGrupo);
			GrupoDTO grupo = gruposJDBC.getGrupobyIdGrupo(idGrupo);

			request.setAttribute("grupo", grupo);
			request.setAttribute("alumnos", alumnos);
			request.getRequestDispatcher(
					"pages/sicse/consultas_semiescolar/AlumnosPorGrupo.jsp")
					.forward(request, response);

			break;

		}
*/
	}
}
