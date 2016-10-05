package fr.gemao.view.personnel;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.gemao.ctrl.AdresseCtrl;
import fr.gemao.ctrl.CommuneCtrl;
import fr.gemao.ctrl.RecupererContratCtrl;
import fr.gemao.ctrl.personnel.PersonnelCtrl;
import fr.gemao.entity.Adresse;
import fr.gemao.entity.Commune;
import fr.gemao.entity.cours.Discipline;
import fr.gemao.entity.personnel.Contrat;
import fr.gemao.entity.personnel.Diplome;
import fr.gemao.entity.personnel.Personnel;
import fr.gemao.entity.personnel.Responsabilite;
import fr.gemao.view.JSPFile;
import fr.gemao.view.Pattern;

/**
 * Servlet implementation class ConsulterPersonnelServlet
 */
@WebServlet(Pattern.PERSONNEL_CONSULTER)
public class ConsulterPersonnelServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		if (request.getParameter("id") == null) {
			this.getServletContext().getRequestDispatcher(JSPFile.ERREUR_404).forward(request, response);
		}

		long id = Long.parseLong(request.getParameter("id"));
		Personnel personnel = PersonnelCtrl.recupererPersonnel(id);

		Adresse adresse = AdresseCtrl.recupererAdresse(personnel.getAdresse().getIdAdresse());

		Commune commune = CommuneCtrl.recupererCommune(adresse.getCommune().getIdCommune());

		RecupererContratCtrl recupererContratCtrl = new RecupererContratCtrl();
		List<Contrat> contrats = personnel.getContrat();
		List<Discipline> listeDisciplines = personnel.getListeDiscipline();

		List<Responsabilite> listeResponsabilite = personnel.getListeResponsabilite();
		if (listeResponsabilite.isEmpty()) {
			listeResponsabilite.add(new Responsabilite(0, "Aucune"));
		}

		List<Diplome> listeDiplome = personnel.getListeDiplomes();
		if (listeDiplome.isEmpty()) {
			listeDiplome.add(new Diplome(0, "Aucun"));
		}

		request.setAttribute("listeDiplome", listeDiplome);
		request.setAttribute("listeResponsabilite", listeResponsabilite);
		request.setAttribute("listeDiscipline", listeDisciplines);
		request.setAttribute("personnel", personnel);
		request.setAttribute("adresse", adresse);
		request.setAttribute("commune", commune);
		request.setAttribute("contrats", contrats);
		this.getServletContext().getRequestDispatcher(JSPFile.PERSONNEL_CONSULTER).forward(request, response);
	}

}
