package fr.gemao.view.location;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.gemao.ctrl.adherent.AdherentCtrl;
import fr.gemao.ctrl.materiel.CategorieCtrl;
import fr.gemao.ctrl.materiel.DesignationCtrl;
import fr.gemao.entity.adherent.Adherent;
import fr.gemao.entity.materiel.Categorie;
import fr.gemao.entity.materiel.Designation;
import fr.gemao.form.location.LocationForm;
import fr.gemao.view.JSPFile;
import fr.gemao.view.Pattern;

/**
 * Servlet implementation class locationInstrumentServlet
 */
@WebServlet(Pattern.LOCATION_LOCATION)
public class LocationInstrumentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private final String PARAM_ID_CATEGORIE = "idCategorie";
	private final String PARAM_NOM_CATEGORIE = "nomCategorie";
	private final String PARAM_LISTE_CATEGORIE = "listeCategorie";
	private final String PARAM_LISTE_DESIGNATION = "listeDesignation";
	private final String PARAM_NOM_DESIGNATION = "nomDesignation";
	private final String PARAM_LISTE_ADHERENT = "listeAdherent";
	private final String PARAM_ID_DESIGNATION = "idDesignation";
	private final String PARAM_RESULTAT = "resultat";

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 1er passage : choix de la catégorie
		List<Categorie> listeCategorie = CategorieCtrl.recupererToutesCategories();

		request.setAttribute(PARAM_LISTE_CATEGORIE, listeCategorie);

		this.getServletContext().getRequestDispatcher(JSPFile.LOCATION_LOCATION).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		LocationForm locationForm = new LocationForm(request);
		HttpSession session = request.getSession();
		int idCategorie, idDesignation;

		if (locationForm.getCategorie() != null) {
			idCategorie = Integer.parseInt(locationForm.getCategorie());
			// 2ème passage : la catégorie vient d'être choisie,
			// choix de la désignation

			// Mise du numéro de la catégorie dans la session
			session.setAttribute(PARAM_ID_CATEGORIE, idCategorie);

			// Passage en paramètre dans la requête du nom de la catégorie
			request.setAttribute(PARAM_NOM_CATEGORIE, CategorieCtrl.recupererCategorie(idCategorie).getLibelleCat());

			// Récupération de la liste
			List<Designation> listeDesignation = DesignationCtrl.recupererToutesDesignations();
			request.setAttribute(PARAM_LISTE_DESIGNATION, listeDesignation);
		} else if (locationForm.getDesignation() != null) {
			// 3ème passage : la désignation vient d'être choisie,
			// choix de l'instrument
			idDesignation = Integer.parseInt(locationForm.getDesignation());
			idCategorie = ((Integer) session.getAttribute(PARAM_ID_CATEGORIE)).intValue();

			// Mise du numéro de la désignation dans la session
			session.setAttribute(PARAM_ID_DESIGNATION, idDesignation);

			// Passage en paramètre dans la requête du nom de la catégorie
			request.setAttribute(PARAM_NOM_CATEGORIE, CategorieCtrl.recupererCategorie(idCategorie).getLibelleCat());
			request.setAttribute(PARAM_NOM_DESIGNATION,
					CategorieCtrl.recupererCategorie(idDesignation).getLibelleCat());

			List<Adherent> listeAdherent = AdherentCtrl.recupererTousAdherents();
			request.setAttribute(PARAM_LISTE_ADHERENT, listeAdherent);

		} else {
			// Dernier passage : Toutes les informations ont été choisies
			idDesignation = ((Integer) session.getAttribute(PARAM_ID_DESIGNATION)).intValue();
			idCategorie = ((Integer) session.getAttribute(PARAM_ID_CATEGORIE)).intValue();
			int idAdherent = Integer.parseInt(locationForm.getAdherent());
			String dateDebut = locationForm.getDateDebut();
			String dateFin = locationForm.getDateFin();

			// TODO
			// Insérer la location dans la base
		}

		this.getServletContext().getRequestDispatcher(JSPFile.LOCATION_LOCATION).forward(request, response);
	}

}
