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
public class LocationInterneServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private final String PARAM_ID_CATEGORIE = "idCategorie";
	private final String PARAM_NOM_CATEGORIE = "nomCategorie";
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

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 1er passage : choix de la catégorie
		List<Categorie> listeCategorie = CategorieCtrl.recupererToutesCategories();
		HttpSession session = request.getSession();
		if(session.getAttribute(PARAM_ID_CATEGORIE)!=null){
			List<Materiel> listeMateriel = MaterielCtrl.recupererMaterielByCategorie((int) session.getAttribute(PARAM_ID_CATEGORIE));
			request.setAttribute(PARAM_LISTE_MATERIEL, listeMateriel);
		}
		request.setAttribute(PARAM_LISTE_CATEGORIE, listeCategorie);

		this.getServletContext().getRequestDispatcher(JSPFile.LOCATION_INTERNE).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		LocationForm locationForm = new LocationForm(request);
		HttpSession session = request.getSession();
		int idCategorie;
		if(Form.getValeurChamp(request, CATEGORIE)!=null){
			idCategorie = Integer.parseInt(Form.getValeurChamp(request, CATEGORIE));
			CategorieCtrl categorieCtrl = new CategorieCtrl();
			Categorie categorie = CategorieCtrl.recupererCategorie(idCategorie);
			session.setAttribute(PARAM_ID_CATEGORIE, Integer.parseInt(Form.getValeurChamp(request, CATEGORIE)));
			session.setAttribute(PARAM_NOM_CATEGORIE, categorie.getLibelleCat());
			List<Materiel> listeMateriel = MaterielCtrl.recupererMaterielByCategorie((int) session.getAttribute(PARAM_ID_CATEGORIE));
			List<Adherent> listeAdherent = AdherentCtrl.recupererTousAdherents();
			List<Personne> listePersonne = new ArrayList<>();
			for(Adherent a : listeAdherent){
				Personne pers = new Personne(a.getIdPersonne(), a.getAdresse(), a.getCommuneNaiss(), a.getNom(), a.getPrenom(), a.getDateNaissance(), a.getTelFixe(), a.getTelPort(), a.getEmail(), a.getCivilite(), a.isDroitImage());
				listePersonne.add(pers);
			}
			request.setAttribute(PARAM_LISTE_MATERIEL, listeMateriel);
			request.setAttribute(PARAM_LISTE_ADHERENT, listePersonne);
			this.getServletContext().getRequestDispatcher(JSPFile.LOCATION_INTERNE).forward(request, response);
		}
		if(Form.getValeurChamp(request, PARAM_ID_DESIGNATION)!=null){
			String dateFin = locationForm.setDateFinForm(Form.getValeurChamp(request, PARAM_DATE_DEBUT));
			locationForm.testFormulaire(request);
			if(locationForm.getErreurs().isEmpty()!=true){
				String idPersonne = Form.getValeurChamp(request, PARAM_ID_ADHERENT);
				String idMateriel = Form.getValeurChamp(request, PARAM_ID_DESIGNATION);
				List<Materiel> mats = MaterielCtrl.recupererMaterielByCategorie(Integer.parseInt(""+session.getAttribute(PARAM_ID_CATEGORIE)));
				Materiel mat=null;
				for(Materiel m : mats){
					if(m.getCategorie().getIdCategorie()==Integer.parseInt(""+session.getAttribute(PARAM_ID_CATEGORIE))){
						mat = m;
					}
				}
				String etatDebut = ""+mat.getEtat().getIdEtat();
				
				String dateDebut = Form.getValeurChamp(request, PARAM_DATE_DEBUT);
		        
				float caution = Float.parseFloat(Form.getValeurChamp(request, PARAM_CAUTION));
				float montant = Float.parseFloat(Form.getValeurChamp(request, PARAM_MONTANT));
				LocationCtrl.ajouterLocation(idPersonne, idMateriel, etatDebut, dateDebut, dateFin,caution, montant);
				response.sendRedirect(request.getContextPath() + Pattern.ACCUEIL);
			}else{
				response.sendRedirect(request.getContextPath()+Pattern.LOCATION_LOCATION_INTERNE);
			}
		}
	}

}
