package   mx.edu.iems.controller;

import java.io.IOException;
import java.sql.SQLException;
impo      rt java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet   ;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSessio    n;


import mx.edu.iems.dao.GrupoDaoImp;
import mx.edu.iems.dao.Semestr  eDaoImp;
import mx.edu.iems.dto.AlumnoDTO;
import mx.edu.iems.dto.GrupoDTO;
import mx.edu.iems.dto.SemestreDTO;

@WebServlet("/Controlle      rServl   et")
public class ControllerServletSemiescolar     extends    HttpServlet {
        	private static fin       al long serialVersionUID      = 1L; 

	protected  void doGet(HttpServletRequest request,
			HttpServletRespon  se response) throws ServletException, IOException {

/*		GrupoDaoImp grup   osJDBC;
		// agregamo    s los semestres a    la sesion
		Semest reDaoImp semestresJDBC = new SemestreDaoImp("semiescolar");
		List<SemestreDTO> seme stres;
		try {
			semestr  es = semestresJDBC.getAllSemestres  ();
		} catch (   SQLExcept     ion e)    {
			e.printStackTrace();
		}
		HttpSession session = request.getSession( );
		session.setAttribute("semestres"   ,   semestres);

		int accion = Integer.parseInt(request.getParameter("a    ccion")    );

		switc  h (accion) {
		case 0:
			// vi enen del select del formulario
			String semestreSel = request.       getPa rameter("semestre");
			// agregamos el semestre seleccio  nado a la session
			session.setAttri    bute  ("sem     estre", semest  reSel);  
			// agregamos los grupos del semestre seleccionado a la  sesion
			gruposJD    BC = new GrupoDaoImp("s    emiescolar");
			List<GrupoD       TO   > grupos = gruposJDBC.getA llGrupos(semestreSel);
			session.setAttribute("grupos", grupos);

			// Pagina princi     pal de consul    ta    s de la modalidad Semiescolar
			request.getRequestDispatcher(
					"pages/sicse/consultas_semiescolar/semiescolar.jsp")
					.forward(request, response);
			b   reak;

		c          ase 1:

			String id_grupo = request.getParameter (" idGrupo");

			int idGrupo = In teger.parse  Int(id_grupo);
			gruposJDBC = new GrupoDaoImp("semiescolar");
			List<AlumnoDTO> alumnos = gruposJDBC.getAlu      mnosPorGrupo(idGrupo);
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
