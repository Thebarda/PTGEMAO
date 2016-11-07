package fr.gemao.view.location;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.gemao.ctrl.PersonneCtrl;
import fr.gemao.ctrl.adherent.AdherentCtrl;
import fr.gemao.ctrl.location.LocationCtrl;
import fr.gemao.ctrl.materiel.CategorieCtrl;
import fr.gemao.ctrl.materiel.DesignationCtrl;
import fr.gemao.ctrl.materiel.MaterielCtrl;
import fr.gemao.entity.Personne;
import fr.gemao.entity.adherent.Adherent;
import fr.gemao.entity.materiel.Categorie;
import fr.gemao.entity.materiel.Designation;
import fr.gemao.entity.materiel.Materiel;
import fr.gemao.form.location.LocationForm;
import fr.gemao.form.util.Form;
import fr.gemao.view.JSPFile;
import fr.gemao.view.Pattern;

/**
 * Servlet implementation class locationInstrumentServlet
 */
@WebServlet(Pattern.LOCATION_LOCATION_INTERNE)
public class ValidationLocationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private final String PARAM_ID_CATEGORIE = "idCategorie";

	private final String PARAM_LISTE_CATEGORIE = "listeCategorie";
	private final String PARAM_LISTE_MATERIEL = "listeMateriel";
	private final String PARAM_LISTE_ADHERENT = "listeAdherent";
	private final String PARAM_ID_DESIGNATION = "nomDesignation";
	private final String PARAM_ID_ADHERENT = "adherent";
	private final String PARAM_DATE_DEBUT = "debutLocation";
	private final String PARAM_DATE_FIN = "finLocation";
	private final String PARAM_CAUTION = "caution";
	private final String PARAM_MONTANT = "montant";
	private static String CATEGORIE = "categorie";
	
	private final String PARAM_NOM_CATEGORIE = "nomCategorie";
	private final String PARAM_NOM_ADHERENT = "nomAdherent";
	private final String PARAM_NOM_MATERIEL = "nomMateriel";
	private final String PARAM_PRENOM_ADHERENT = "prenomAdherent";

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 1er passage : choix de la catégorie
		
		HttpSession session = request.getSession();
		
		String nom = null, prenom = null;
		
		List<Adherent> adhs = AdherentCtrl.recupererTousAdherents();
		for(Adherent adh : adhs){
			if(adh.getIdPersonne()==session.getAttribute(PARAM_ID_ADHERENT)){
				nom = adh.getNom();
				prenom = adh.getPrenom();
			}
		}
		
		String materiel = null;
		List<Materiel> materiels = MaterielCtrl.recupererMaterielByCategorie(Integer.parseInt(""+session.getAttribute(PARAM_ID_CATEGORIE)));
		for(Materiel mat : materiels){
			if(mat.getCategorie().getIdCategorie()==Integer.parseInt(""+session.getAttribute(PARAM_ID_CATEGORIE))){
				materiel = mat.getDesignation().getLibelleDesignation();
			}
		}
		
		request.setAttribute(PARAM_NOM_CATEGORIE, session.getAttribute(PARAM_NOM_CATEGORIE));
		request.setAttribute(PARAM_NOM_ADHERENT, nom);
		request.setAttribute(PARAM_PRENOM_ADHERENT, prenom);
		request.setAttribute(PARAM_NOM_MATERIEL, materiel);
		request.setAttribute(PARAM_DATE_DEBUT, session.getAttribute(PARAM_DATE_DEBUT));
		request.setAttribute(PARAM_DATE_FIN, session.getAttribute(PARAM_DATE_FIN));
		request.setAttribute(PARAM_CAUTION, session.getAttribute(PARAM_CAUTION));
		request.setAttribute(PARAM_MONTANT, session.getAttribute(PARAM_MONTANT));
		
		this.getServletContext().getRequestDispatcher(JSPFile.LOCATION_INTERNE).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

}