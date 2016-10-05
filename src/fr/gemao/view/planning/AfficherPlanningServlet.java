package fr.gemao.view.planning;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.gemao.Config;
import fr.gemao.ctrl.planning.PlanningCtrl;
import fr.gemao.ctrl.planning.SalleCtrl;
import fr.gemao.entity.cours.Salle;
import fr.gemao.entity.planning.Planning;
import fr.gemao.view.JSPFile;
import fr.gemao.view.Pattern;
import fr.gemao.view.ResultatServlet;
import fr.gemao.view.ServletUtil;

/**
 * Servlet implementation class AfficherPlanningServlet
 */
@WebServlet(Pattern.PLANNING_AFFICHER)
public class AfficherPlanningServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		final List<Salle> salles = SalleCtrl.getAll();
		Integer numPlanning = ServletUtil.getParameterIntegerValue("idPlanning", request);

		Planning p = numPlanning != null ? PlanningCtrl.getPlanning(numPlanning) : null;

		if (p == null) {
			ResultatServlet.redirect(request, response, "Erreur", "Le numéro du planning doit être indiqué.",
					Pattern.PLANNING_LISTER, "Retour à la liste des plannings", null, null, null);
			return;
		}

		request.setAttribute("enModif", false);
		request.setAttribute("planning", p);
		request.setAttribute("salles", salles);
		if (request.getParameter("valider") != null) {
			PlanningCtrl.validerPlanning(p);
			ResultatServlet.redirect(request, response, "Résultat", "Le planning a été validé.",
					Pattern.PLANNING_LISTER, "Retour à la liste des plannings", null, null, null);
			return;
		}

		this.getServletContext().getRequestDispatcher(JSPFile.PLANNING_AFFICHER).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		exportPDF(request, response);

		// response.sendRedirect("/GEMAO" + Pattern.PLANNING_AFFICHER +
		// "?idPlanning=" + numPlanning);
	}

	/**
	 * Cette méthode permet d'exporter un pdf
	 * 
	 * @param request
	 * @param response
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private void exportPDF(HttpServletRequest request, HttpServletResponse response)
			throws FileNotFoundException, IOException {
		final Integer numPlanning = ServletUtil.getParameterIntegerValue("idPlanning", request);
		if (numPlanning == null) {
			throw new NullPointerException("Le planning ne peut être exporté, aucun idPlanning donné !");
		}

		Planning planning = PlanningCtrl.getPlanning(numPlanning);
		final String pathURL = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
				+ request.getContextPath() + "/" + Config.params.get(Config.DOSSIER_RACINE) + "/exports/planning.pdf";

		String path = request.getSession().getServletContext().getRealPath("/") + "/"
				+ Config.params.get(Config.DOSSIER_RACINE) + "/exports/"
				+ PlanningCtrl.getPlanning(numPlanning).getNomPlanning().replaceAll(" ", "") + ".pdf";

		request.setAttribute("url", pathURL);

		PlanningCtrl.genererPDF(planning, path);

		File file = new File(path); // URL du PDF
		byte[] fileData = new byte[(int) file.length()];
		FileInputStream fis = new FileInputStream(file);
		fis.read(fileData);

		response.reset();
		response.setContentType("application/pdf");
		response.setContentLength(fileData.length);
		String filename = planning.getNomPlanning().replaceAll(" ", "") + ".pdf";
		response.setHeader("Content-Disposition", "inline;filename=" + filename);

		ServletOutputStream outputStream = response.getOutputStream();
		outputStream.write(fileData);
		outputStream.flush();
		outputStream.close();
		fis.close();
	}
}
