package fr.gemao.view.location;

import java.io.IOException;
import java.util.ArrayList;
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
import fr.gemao.ctrl.materiel.MaterielCtrl;
import fr.gemao.entity.Personne;
import fr.gemao.entity.adherent.Adherent;
import fr.gemao.entity.materiel.Categorie;
import fr.gemao.entity.materiel.Materiel;
import fr.gemao.entity.personnel.Personnel;
import fr.gemao.form.location.LocationForm;
import fr.gemao.form.util.Form;
import fr.gemao.view.JSPFile;
import fr.gemao.view.Pattern;

/**
 * Servlet implementation class locationInstrumentServlet
 */
@WebServlet(Pattern.LOCATION_LOCATION_EXTERNE)
public class locationExterneServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private final String PARAM_ID_CATEGORIE = "idCategorie";
	private final String PARAM_NOM_CATEGORIE = "nomCategorie";
	private final String PARAM_LISTE_CATEGORIE = "listeCategorie";
	private final String PARAM_LISTE_MATERIEL = "listeMateriel";
	private final String PARAM_LISTE_PERSONNE = "listePersonne";
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

		this.getServletContext().getRequestDispatcher(JSPFile.LOCATION_EXTERNE).forward(request, response);
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
		
		if(Form.getValeurChamp(request, "imprimer")!=null){
			if(Form.getValeurChamp(request, "imprimer")=="Oui"){ //impression
				Personnel connectee = (Personnel)session.getAttribute("sessionObjectPersonnel");
				char lettreNom = connectee.getNom().charAt(0);
				char lettrePrenom = connectee.getPrenom().charAt(0);
				int numeroLocation = LocationCtrl.getNbLocation();
				String adherent = ""+session.getAttribute("nomAdherent");
				String[] adh = adherent.split(" ");
				String nom = adh[0];
				String prenom = adh[1];
				String instrument = ""+session.getAttribute("nomInstrument");
				String marqueType = "";
				List<Materiel> materiels = MaterielCtrl.recupererMaterielByCategorie(Integer.parseInt(""+session.getAttribute(PARAM_ID_CATEGORIE)));
				for(Materiel mat : materiels){
					if(mat.getDesignation().getIdDesignation()==Integer.parseInt(""+session.getAttribute(PARAM_ID_DESIGNATION))){
						marqueType = mat.getTypeMat();
					}
				}
				int montant = Integer.parseInt(""+session.getAttribute(PARAM_MONTANT));
				
				//Generation du report
				/*String jrxmlFileName = "";
				String jasperFileName="";
				try {
					JasperCompileManager.compileReportToFile(jrxmlFileName, jasperFileName);
					
				} catch (JRException e) {
					e.printStackTrace();
				}*/
			}
			LocationCtrl.ajouterLocation(""+session.getAttribute(PARAM_ID_ADHERENT), ""+session.getAttribute(PARAM_ID_DESIGNATION), ""+session.getAttribute("etatDebut"), ""+session.getAttribute(PARAM_DATE_DEBUT), ""+session.getAttribute(PARAM_DATE_FIN), Float.parseFloat(""+session.getAttribute(PARAM_CAUTION)), Float.parseFloat(""+session.getAttribute(PARAM_MONTANT)));
			response.sendRedirect(request.getContextPath()+Pattern.ACCUEIL);
		}
		
		if(Form.getValeurChamp(request, PARAM_ID_DESIGNATION)!=null){
			locationForm.testFormulaire(request);
			if(locationForm.getErreurs().isEmpty()==true){
				String idPersonne = Form.getValeurChamp(request, PARAM_ID_ADHERENT);
				String idMateriel = Form.getValeurChamp(request, PARAM_ID_DESIGNATION);
				session.setAttribute("PARAM_ID_DESIGNATION",Integer.parseInt(idMateriel));
				List<Materiel> mats = MaterielCtrl.recupererMaterielByCategorie(Integer.parseInt(""+session.getAttribute(PARAM_ID_CATEGORIE)));
				Materiel mat=null;
				for(Materiel m : mats){
					if(m.getDesignation().getIdDesignation()==Integer.parseInt(idMateriel)){
						mat = m;
					}
				}
				
				String materiel = mat.getDesignation().getLibelleDesignation();
				
				String etatDebut = ""+mat.getEtat().getIdEtat();
				
				String dateDebut = Form.getValeurChamp(request, PARAM_DATE_DEBUT);
				int duree = Integer.parseInt(""+Form.getValeurChamp(request, PARAM_DATE_FIN));
				String dateFin = locationForm.setDateFinFormByDuree(duree, dateDebut);
		        
				float caution = Float.parseFloat(Form.getValeurChamp(request, PARAM_CAUTION));
				float montant = Float.parseFloat(Form.getValeurChamp(request, PARAM_MONTANT));
				
				String nom = null, prenom = null;
				
				List<Personne> pers = PersonneCtrl.recupererToutesPersonnes();
				for(Personne per : pers){
					if(per.getIdPersonne()==Long.parseLong(idPersonne)){
						nom = per.getNom();
						prenom = per.getPrenom();
					}
				}
				
				session.setAttribute("etatDebut", etatDebut);
				session.setAttribute(PARAM_DATE_DEBUT, dateDebut);
				session.setAttribute(PARAM_DATE_FIN, dateFin);
				session.setAttribute(PARAM_CAUTION, caution);
				session.setAttribute(PARAM_MONTANT, montant);
				session.setAttribute(PARAM_ID_ADHERENT, idPersonne);
				session.setAttribute(PARAM_ID_DESIGNATION, idMateriel);
				
				request.setAttribute("resultat", "yes");
				
				session.setAttribute("nomInstrument", materiel);
				session.setAttribute("nomAdherent", prenom+" "+nom);
				
				
				this.getServletContext().getRequestDispatcher(JSPFile.LOCATION_EXTERNE).forward(request, response);
			}else{
				response.sendRedirect(request.getContextPath()+Pattern.ACCUEIL);
			}
		}
		if(Form.getValeurChamp(request, CATEGORIE)!=null){
			idCategorie = Integer.parseInt(Form.getValeurChamp(request, CATEGORIE));
			CategorieCtrl categorieCtrl = new CategorieCtrl();
			Categorie categorie = CategorieCtrl.recupererCategorie(idCategorie);
			session.setAttribute(PARAM_ID_CATEGORIE, Integer.parseInt(Form.getValeurChamp(request, CATEGORIE)));
			session.setAttribute(PARAM_NOM_CATEGORIE, categorie.getLibelleCat());
			List<Materiel> listeMateriel = MaterielCtrl.recupererMaterielByCategorie((int) session.getAttribute(PARAM_ID_CATEGORIE));
			List<Personne> listePersonnes = PersonneCtrl.recupererToutesPersonnes();
			List<Personne> listePers = new ArrayList<Personne>();
			for(Personne p : listePersonnes){
				Personne pers = new Personne(p.getIdPersonne(), p.getAdresse(), p.getCommuneNaiss(), p.getNom(), p.getPrenom(), p.getDateNaissance(), p.getTelFixe(), p.getTelPort(), p.getEmail(), p.getCivilite(), false);
				listePers.add(pers);
			}
			request.setAttribute(PARAM_LISTE_MATERIEL, listeMateriel);
			request.setAttribute(PARAM_LISTE_PERSONNE, listePers);
			this.getServletContext().getRequestDispatcher(JSPFile.LOCATION_EXTERNE).forward(request, response);
		}
		
	}

}
