package fr.gemao.view.location;

import java.awt.Desktop;
import java.awt.Graphics;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import fr.gemao.ctrl.PersonneCtrl;
import fr.gemao.ctrl.adherent.AdherentCtrl;
import fr.gemao.ctrl.cheque.ChequeCtrl;
import fr.gemao.ctrl.location.LocationCtrl;
import fr.gemao.ctrl.materiel.CategorieCtrl;
import fr.gemao.ctrl.materiel.EtatCtrl;
import fr.gemao.ctrl.materiel.MaterielCtrl;
import fr.gemao.entity.Personne;
import fr.gemao.entity.adherent.Adherent;
import fr.gemao.entity.materiel.Categorie;
import fr.gemao.entity.materiel.ChequeLocation;
import fr.gemao.entity.materiel.Etat;
import fr.gemao.entity.materiel.Location;
import fr.gemao.entity.materiel.Materiel;
import fr.gemao.entity.personnel.Personnel;
import fr.gemao.form.cheque.ChequeForm;
import fr.gemao.form.location.LocationForm;
import fr.gemao.form.util.Form;
import fr.gemao.view.JSPFile;
import fr.gemao.view.Pattern;

/**
 * Servlet implementation class locationInstrumentServlet
 */
@WebServlet(Pattern.LOCATION_LOCATION_INTERNE)
public class LocationInterneServlet extends HttpServlet implements Printable {
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
	private final String ATTR_AUTO_FAMILLES = "auto_familles";
	private static final String CHAMP_DATE_PAIEMENT = "datePaiement";
	private static final String CHAMP_MONTANT_CHEQUE = "montantCheque";
	private static final String CHAMP_NUMERO_CHEQUE = "numeroCheque";
	private static final String CHAMP_DATE_ENCAISSEMENT = "dateEncaissement";

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 1er passage : choix de la catÃ©gorie
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
		
		//Ici on va soit imprimer et ajouter la location dans la bdd, soit simplement ajouter la location dans la bdd
		if(Form.getValeurChamp(request, "imprimer")!=null){
			//Impression
			String imprimer = Form.getValeurChamp(request, "imprimer");
		
			//On charge les éléments nécessaire pour éditer le contrat
			Personnel connectee = (Personnel)session.getAttribute("sessionObjectPersonnel");
			char lettreNom = connectee.getNom().charAt(0);
			char lettrePrenom = connectee.getPrenom().charAt(0);
			int numeroLocation = LocationCtrl.getNbLocation();
			String adherent = ""+session.getAttribute("nomAdherent");
			String[] adh = adherent.split(" ");
			String nom = adh[1];
			String prenom = adh[0];
			String adresse = "";
			List<Personne> personnes = PersonneCtrl.recupererToutesPersonnes();
			for(Personne p : personnes){
				if((p.getNom().equals(nom))&&(p.getPrenom().equals(prenom))){
					adresse = p.getAdresse().getNumRue()+" "+p.getAdresse().getNomRue()+" "+p.getAdresse().getCommune().getCodePostal()+" "+p.getAdresse().getCommune().getNomCommune();
				}
			}
			List instruments = (List) session.getAttribute("nomInstrument");
			String instrument = (String) instruments.get(0);
			String marqueType = "";
			Date date = new Date();
			int annee = 1900+date.getYear();
		      int mois = 1 + date.getMonth();
			List<Materiel> materiels = MaterielCtrl.recupererMaterielByCategorie(Integer.parseInt(""+session.getAttribute(PARAM_ID_CATEGORIE)));
			for(Materiel mat : materiels){
				if(mat.getIdMateriel()==Long.parseLong(""+session.getAttribute(PARAM_ID_DESIGNATION))){
					marqueType = mat.getTypeMat();
				}
			}
			int montant = Integer.parseInt(""+session.getAttribute(PARAM_MONTANT));
			
			//Generation du rapport
			File rep = new File("contratsLocationInterne");
			if(!rep.exists()){
				rep.mkdir();
			}
			Document document = new Document(PageSize.A4);
		    try {
		      PdfWriter pdf = PdfWriter.getInstance(document,
		          new FileOutputStream(new File("contratsLocationInterne\\ContratLocationInterne"+nom+""+prenom+""+numeroLocation+".pdf")));
		      pdf.setViewerPreferences(PdfWriter.PageLayoutSinglePage | PdfWriter.PageModeUseThumbs);
		      document.open();
		      Font font = FontFactory.getFont("Comic Sans MS", 15);
		      Font font2 = FontFactory.getFont("Comic Sans MS", 18);
		      Font font3 = FontFactory.getFont("Comic Sans MS", 12);
		      document.add(new Paragraph("10 rue de la gare\n18570 LA CHAPELLE SAINT URSIN \n\n\n\n"));
		      Paragraph paragraph = new Paragraph(" Contrat de location : ANA "+annee+"-"+lettrePrenom+""+lettreNom+"-"+numeroLocation, font2);
		      
		      paragraph.setAlignment(Element.ALIGN_CENTER);
		      document.add(paragraph);
		      
		      paragraph = new Paragraph("Nom : "+nom, font2);
		      paragraph.setAlignment(Element.ALIGN_CENTER);
		      document.add(paragraph);
		      
		      paragraph = new Paragraph("Prénom : "+prenom, font2);
		      paragraph.setAlignment(Element.ALIGN_CENTER);
		      document.add(paragraph);
		      
		      paragraph = new Paragraph("Adresse : "+adresse, font2);
		      paragraph.setAlignment(Element.ALIGN_CENTER);
		      document.add(paragraph);
		      
		      paragraph = new Paragraph("Instrument : "+instrument, font2);
		      paragraph.setAlignment(Element.ALIGN_CENTER);
		      document.add(paragraph);
		      
		      paragraph = new Paragraph("Marque Type : "+marqueType, font2);
		      paragraph.setAlignment(Element.ALIGN_CENTER);
		      document.add(paragraph);
		      
		      paragraph = new Paragraph("N° : "+numeroLocation+"\n\n", font2);
		      paragraph.setAlignment(Element.ALIGN_CENTER);
		      document.add(paragraph);
		      
		      document.add(new Paragraph("L’ANACROUSE  loue cet instrument  en bon état, il appartient au titulaire de la location d’en assurer l’entretien.\nLes éventuels frais de remise en état découverts après restitution seront à la charge du titulaire du présent contrat et éventuellement pris sur la caution (non encaissée) préalable à la location. La caution est fixée à 120 € payable par chèque.\nCe contrat est conclu pour une durée de trois mois renouvelables contre la somme de 20€ par an.\n\n", font));
		      document.add(new Paragraph("Période location couverte "+session.getAttribute(PARAM_DATE_DEBUT)+" au "+session.getAttribute(PARAM_DATE_FIN)+"\n\n", font));
		      document.add(new Paragraph("Fait à La Chapelle Saint Ursin le "+date.getDate()+"/"+mois+"/"+annee+" en double exemplaire\n\n", font));
		      document.add(new Paragraph("Le régisseur de l'anacrous                    Le titulaire du contrat (ou son représentant légal)\n\n\n\n", font3));
		      document.add(new Paragraph(""+connectee.getPrenom()+" "+connectee.getNom(), font));
		      document.add(new Paragraph("Nota : cette location est renouvelable dans le respect du principe suivant : priorité sera donnée au débutant.", font));
		    } catch (DocumentException de) {
		      de.printStackTrace();
		    } catch (IOException ioe) {
		      ioe.printStackTrace();
		    }
		    document.close();
			if(imprimer.equals("Oui")){ 
			    Desktop desktop = Desktop.getDesktop();
			    desktop.print(new File("contratsLocationInterne\\ContratLocationInterne"+nom+""+prenom+""+numeroLocation+".pdf"));
			}
			
			Map<String, String> tarifs = LocationCtrl.recupereTarifsLocation();
			LocationCtrl.ajouterLocation(""+session.getAttribute(PARAM_ID_ADHERENT), ""+session.getAttribute(PARAM_ID_DESIGNATION), ""+session.getAttribute("etatDebut"), ""+session.getAttribute(PARAM_DATE_DEBUT), ""+session.getAttribute(PARAM_DATE_FIN), Float.parseFloat(""+tarifs.get("caution")), Float.parseFloat(""+tarifs.get("montantLocationInterne")), "contratsLocationInterne\\ContratLocationInterne"+nom+""+prenom+""+numeroLocation+".pdf", "Interne");
			Materiel materiel = MaterielCtrl.getMaterielById(Integer.parseInt(""+session.getAttribute(PARAM_ID_DESIGNATION)));
			MaterielCtrl.updateEstLouable(Integer.parseInt(""+session.getAttribute(PARAM_ID_DESIGNATION)), 0);
			
			int id = LocationCtrl.getIdLastInserted();
			
			List<Location> locations = LocationCtrl.getAll();
			Location location = null;
			for(Location loc : locations){
				if(loc.getId()==id){
					location=loc;
				}
			}
			
			if((session.getAttribute(CHAMP_DATE_PAIEMENT)!=null)&&(session.getAttribute(CHAMP_MONTANT_CHEQUE)!=null)&&(session.getAttribute(CHAMP_NUMERO_CHEQUE)!=null)&&(session.getAttribute(CHAMP_DATE_ENCAISSEMENT)!=null)){
				ChequeLocation cheque = new ChequeLocation(location, ""+session.getAttribute(CHAMP_DATE_PAIEMENT), Float.parseFloat(""+session.getAttribute(CHAMP_MONTANT_CHEQUE)), Long.parseLong(""+session.getAttribute(CHAMP_NUMERO_CHEQUE)), ""+session.getAttribute(CHAMP_DATE_ENCAISSEMENT), null);
				ChequeCtrl.ajouterCheque(cheque);
				session.removeAttribute(CHAMP_DATE_PAIEMENT);
				session.removeAttribute(CHAMP_DATE_ENCAISSEMENT);
				session.removeAttribute(CHAMP_MONTANT_CHEQUE);
				session.removeAttribute(CHAMP_NUMERO_CHEQUE);
			}
			
			request.setAttribute("validation", "Location enregistrée !");
			this.getServletContext().getRequestDispatcher(JSPFile.LOCATION_INTERNE).forward(request, response);
		}
		
		//Traitement du formulaire
		if(Form.getValeurChamp(request, PARAM_ID_DESIGNATION)!=null){
			ChequeForm chequeForm = new ChequeForm(request);
			chequeForm.testerCheque(request);
			if(chequeForm.getErreurs().isEmpty()){
				DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				String personne = Form.getValeurChamp(request, PARAM_ID_ADHERENT);
				String[] tempPers = personne.split(" ");
				String nomPers = tempPers[0];
				String prenomPers = tempPers[1];
				String idPersonne = PersonneCtrl.getIdByNomAndPrenom(nomPers, prenomPers);
				String idMateriel = Form.getValeurChamp(request, PARAM_ID_DESIGNATION);
				session.setAttribute("PARAM_ID_DESIGNATION",Integer.parseInt(idMateriel));
				List<Personne> personnes = PersonneCtrl.recupererToutesPersonnes();
					List<Materiel> mats = MaterielCtrl.recupererMaterielByCategorie(Integer.parseInt(""+session.getAttribute(PARAM_ID_CATEGORIE)));
					Materiel mat=null;
					for(Materiel m : mats){
						if(m.getIdMateriel()==Integer.parseInt(idMateriel)){
							mat = m;
						}
					}
						
					List confirmMat = Arrays.asList(mat.getDesignation().getLibelleDesignation(), mat.getNumSerie(), mat.getTypeMat(), mat.getDateAchat(), mat.getValeurAchat(), mat.getValeurReap(), "Oui", mat.getObservation());
						
					String etatDebut = ""+mat.getEtat().getIdEtat();
						
					String dateDebut = Form.getValeurChamp(request, PARAM_DATE_DEBUT);
					
					Date dateDeb=null;
					try {
						dateDeb = dateFormat.parse(dateDebut);
					} catch (ParseException e1) {}
					
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(dateDeb);
					Date tmp = calendar.getTime();
					String moisDeb = "";
					String dayDeb = "";
					if((tmp.getMonth()+1)<10){
						moisDeb = "0"+(tmp.getMonth()+1);
					}else{
						moisDeb=""+(tmp.getMonth()+1);
					}
					if(tmp.getDate()<10){
						dayDeb = "0"+tmp.getDate();
					}else{
						dayDeb=""+tmp.getDate();
					}
					dateDebut = dayDeb+"/"+moisDeb+"/"+(1900+tmp.getYear());
					
					String dateFin = locationForm.setDateFinFormByDuree(12,Form.getValeurChamp(request, PARAM_DATE_DEBUT));
					
					Date dateFin2=null;
					try {
						dateFin2 = dateFormat.parse(dateFin);
					} catch (ParseException e1) {}
					
					calendar.setTime(dateFin2);
					tmp = calendar.getTime();
					String moisFin = "";
					if((tmp.getMonth()+1)<10){
						moisFin = "0"+(tmp.getMonth()+1);
					}else{
						moisFin=""+(tmp.getMonth()+1);
					}
					String dayFin = "";
					if(tmp.getDate()<10){
						dayFin = "0"+tmp.getDate();
					}else{
						dayFin=""+tmp.getDate();
					}
					dateFin = dayFin+"/"+moisFin+"/"+(1900+tmp.getYear());
						
					String nom = null, prenom = null;
						
					List<Adherent> adhs = AdherentCtrl.recupererTousAdherents();
					for(Adherent adh : adhs){
						if(adh.getIdPersonne()==Long.parseLong(idPersonne)){
							nom = adh.getNom();
							prenom = adh.getPrenom();
						}
					}
					
					
					String datePaiement = Form.getValeurChamp(request, CHAMP_DATE_PAIEMENT);
					String dateEncaissement = Form.getValeurChamp(request, CHAMP_DATE_ENCAISSEMENT);
					String numCheque = Form.getValeurChamp(request, CHAMP_NUMERO_CHEQUE);
					String montantCheque = Form.getValeurChamp(request, CHAMP_MONTANT_CHEQUE);
					session.setAttribute(CHAMP_DATE_PAIEMENT, datePaiement);
					session.setAttribute(CHAMP_DATE_ENCAISSEMENT, dateEncaissement);
					session.setAttribute(CHAMP_NUMERO_CHEQUE, numCheque);
					session.setAttribute(CHAMP_MONTANT_CHEQUE, montantCheque);
					
					Map<String, String> tarifs = LocationCtrl.recupereTarifsLocation();
					session.setAttribute("etatDebut", etatDebut);
					session.setAttribute(PARAM_DATE_DEBUT, dateDebut);
					session.setAttribute(PARAM_DATE_FIN, dateFin);
					session.setAttribute(PARAM_CAUTION, tarifs.get("caution"));
					session.setAttribute(PARAM_MONTANT, tarifs.get("montantLocationInterne"));
					session.setAttribute(PARAM_ID_ADHERENT, idPersonne);
					session.setAttribute(PARAM_ID_DESIGNATION, idMateriel);
					
					request.setAttribute("resultat", "yes");
						
					session.setAttribute("nomInstrument", confirmMat);
					session.setAttribute("nomAdherent", prenom+" "+nom);
					
					
					this.getServletContext().getRequestDispatcher(JSPFile.LOCATION_INTERNE).forward(request, response);
			}else{
				request.setAttribute("erreurCheque", "Cheque Invalide !<br> Veuillez à ce que la date de paiement soit inférieure à la date d'encaissement<br> Veuillez vérifier que le montant soit strictement supérieur à zéro<br> Veuillez vérifier que le numéro fasse exactement 11 caractères");
				this.getServletContext().getRequestDispatcher(JSPFile.LOCATION_EXTERNE).forward(request, response);
			}
		}
		
		//GÃ©nÃ©ration du formulaire de location
		if(Form.getValeurChamp(request, CATEGORIE)!=null){
			idCategorie = Integer.parseInt(Form.getValeurChamp(request, CATEGORIE));
			CategorieCtrl categorieCtrl = new CategorieCtrl();
			Categorie categorie = CategorieCtrl.recupererCategorie(idCategorie);
			session.setAttribute(PARAM_ID_CATEGORIE, Integer.parseInt(Form.getValeurChamp(request, CATEGORIE)));
			session.setAttribute(PARAM_NOM_CATEGORIE, categorie.getLibelleCat());
			List<Materiel> listeMateriel = MaterielCtrl.recupereMaterielLouable((int) session.getAttribute(PARAM_ID_CATEGORIE));
			if(listeMateriel.isEmpty()){
				session.setAttribute("ListeMatVide", categorie.getLibelleCat());
				session.setAttribute("errListeVide", true);
				response.sendRedirect(request.getContextPath()+Pattern.MATERIEL_AJOUT+"?errListeVide=1");
				
			}else{
				Map<Integer, List> listeMats = new HashMap<>();
				for(Materiel mat : listeMateriel){
					List mats;
					Etat etat = EtatCtrl.getAllByMat(Integer.parseInt(""+mat.getIdMateriel()));
					if(mat.isDeplacable()==true){
						mats = Arrays.asList(mat.getDesignation().getLibelleDesignation(), mat.getNumSerie(), mat.getTypeMat(), mat.getDateAchat(), mat.getValeurAchat(), mat.getValeurReap(), "Oui", mat.getObservation(), etat.getLibelleEtat());
					}else{
						mats = Arrays.asList(mat.getDesignation().getLibelleDesignation(), mat.getNumSerie(), mat.getTypeMat(), mat.getDateAchat(), mat.getValeurAchat(), mat.getValeurReap(), "Non", mat.getObservation(), etat.getLibelleEtat());
					}
					
					listeMats.put(Integer.parseInt(""+mat.getIdMateriel()), mats);
				}
				List<Adherent> listeAdherent = AdherentCtrl.recupererTousAdherents();
				if(listeAdherent.isEmpty()){
					session.setAttribute("errAdhVide", true);
					response.sendRedirect(request.getContextPath()+Pattern.ADHERENT_AJOUT+"?errAdherent=1");
				}else{
					List<Personne> listePersonne = new ArrayList<>();
					for(Adherent a : listeAdherent){
						Personne pers = new Personne(a.getIdPersonne(), a.getAdresse(), a.getCommuneNaiss(), a.getNom(), a.getPrenom(), a.getDateNaissance(), a.getTelFixe(), a.getTelPort(), a.getEmail(), a.getCivilite(), a.isDroitImage());
						listePersonne.add(pers);
					}
					List<Adherent> adherents = AdherentCtrl.recupererTousAdherents();
					List<String> listAdherents = new ArrayList<>();

					for (Adherent a : adherents) {
						listAdherents.add('"' + a.getNom()+" "+ a.getPrenom() + '"');
					}
					request.setAttribute(ATTR_AUTO_FAMILLES, listAdherents);
					request.setAttribute(PARAM_LISTE_MATERIEL, listeMats);
					request.setAttribute(PARAM_LISTE_ADHERENT, listePersonne);
					this.getServletContext().getRequestDispatcher(JSPFile.LOCATION_INTERNE).forward(request, response);
				}
				
			}
		}
		
	}

	@Override
	public int print(Graphics arg0, PageFormat arg1, int arg2) throws PrinterException {
		// TODO Auto-generated method stub
		return 0;
	}

}
