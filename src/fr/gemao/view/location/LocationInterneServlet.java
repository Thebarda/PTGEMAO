package fr.gemao.view.location;

import java.awt.Desktop;
import java.awt.Graphics;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.PrintServiceAttribute;
import javax.print.attribute.standard.Sides;
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
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;

import fr.gemao.ctrl.PersonneCtrl;
import fr.gemao.ctrl.adherent.AdherentCtrl;
import fr.gemao.ctrl.location.LocationCtrl;
import fr.gemao.ctrl.materiel.CategorieCtrl;
import fr.gemao.ctrl.materiel.MaterielCtrl;
import fr.gemao.entity.Personne;
import fr.gemao.entity.adherent.Adherent;
import fr.gemao.entity.materiel.Categorie;
import fr.gemao.entity.materiel.Location;
import fr.gemao.entity.materiel.Materiel;
import fr.gemao.entity.personnel.Personnel;
import fr.gemao.form.location.LocationForm;
import fr.gemao.form.util.Form;
import fr.gemao.view.JSPFile;
import fr.gemao.view.Pattern;
import fr.gemao.view.util.AutocompletionAdherent;
import fr.gemao.view.util.AutocompletionCommune;
import fr.gemao.view.util.AutocompletionFamille;

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
		
		//Ici on va soit imprimer et ajouter la location dans la bdd, soit simplement ajouter la location dans la bdd
		if(Form.getValeurChamp(request, "imprimer")!=null){
			//Impression
			String imprimer = Form.getValeurChamp(request, "imprimer");
			if(imprimer.equals("Oui")){
				//On charge les éléments nécessaire pour éditer le contrat
				Personnel connectee = (Personnel)session.getAttribute("sessionObjectPersonnel");
				char lettreNom = connectee.getNom().charAt(0);
				char lettrePrenom = connectee.getPrenom().charAt(0);
				int numeroLocation = LocationCtrl.getNbLocation();
				String adherent = ""+session.getAttribute("nomAdherent");
				String[] adh = adherent.split(" ");
				String nom = adh[0];
				String prenom = adh[1];
				List instruments = (List) session.getAttribute("nomInstrument");
				String instrument = (String) instruments.get(0);
				String marqueType = "";
				Date date = new Date();
				List<Materiel> materiels = MaterielCtrl.recupererMaterielByCategorie(Integer.parseInt(""+session.getAttribute(PARAM_ID_CATEGORIE)));
				for(Materiel mat : materiels){
					if(mat.getIdMateriel()==Long.parseLong(""+session.getAttribute(PARAM_ID_DESIGNATION))){
						marqueType = mat.getTypeMat();
					}
				}
				int montant = Integer.parseInt(""+session.getAttribute(PARAM_MONTANT));
				
				//Generation du rapport
				Document document = new Document(PageSize.A4);
			    try {
			      PdfWriter pdf = PdfWriter.getInstance(document,
			          new FileOutputStream(new File("ContratLocationInterne"+nom+""+prenom+""+numeroLocation+".pdf")));
			      pdf.setViewerPreferences(PdfWriter.PageLayoutSinglePage | PdfWriter.PageModeUseThumbs);
			      document.open();
			      Font font = FontFactory.getFont("Comic Sans MS", 15);
			      Font font2 = FontFactory.getFont("Comic Sans MS", 18);
			      Font font3 = FontFactory.getFont("Comic Sans MS", Font.BOLD, 13);
			      document.add(new Paragraph("10 rue de la gare\n18570 LA CHAPELLE SAINT URSIN \n\n\n\n"));

			      Paragraph paragraph = new Paragraph(" Contrat de location : ANA "+date.getYear()+"-"+lettrePrenom+""+lettreNom+"-"+date.getMonth(), font2);
			      
			      paragraph.setAlignment(Element.ALIGN_CENTER);
			      document.add(paragraph);
			      
			      paragraph = new Paragraph("Nom : "+nom, font2);
			      paragraph.setAlignment(Element.ALIGN_CENTER);
			      document.add(paragraph);
			      
			      paragraph = new Paragraph("Prénom : "+prenom, font2);
			      paragraph.setAlignment(Element.ALIGN_CENTER);
			      document.add(paragraph);
			      
			      paragraph = new Paragraph("Instrument : "+instrument, font2);
			      paragraph.setAlignment(Element.ALIGN_CENTER);
			      document.add(paragraph);
			      
			      paragraph = new Paragraph("Marque Type : "+marqueType, font2);
			      paragraph.setAlignment(Element.ALIGN_CENTER);
			      document.add(paragraph);
			      
			      paragraph = new Paragraph("N° : "+numeroLocation+"\n\n\n\n", font2);
			      paragraph.setAlignment(Element.ALIGN_CENTER);
			      document.add(paragraph);
			      
			      document.add(new Paragraph("L’ANACROUSE  loue cet instrument en bon état, il appartient au titulaire de la location d’en assurer l’entretien et notamment de le faire réviser avant restitution. \nLes éventuels frais de remise en état découverts après restitution seront à la charge du titulaire du présent contrat. \nCe contrat est conclu pour une durée d’une année scolaire contre la somme de 20 €.\n\n", font));
			      document.add(new Paragraph("Période location couverte "+session.getAttribute(PARAM_DATE_DEBUT)+" au "+session.getAttribute(PARAM_DATE_FIN)+"\n\n", font));
			      document.add(new Paragraph("Fait à La Chapelle Saint Ursin le "+date.getDate()+"/"+date.getMonth()+"/"+date.getYear()+" en double exemplaire\n\n", font));
			      document.add(new Paragraph("Le régisseur de l'anacrous                    Le titulaire du contrat (ou son représentant légal)\n\n", font));
			      document.add(new Paragraph(""+connectee.getPrenom()+" "+connectee.getNom(), font));
			      document.add(new Paragraph("Nota : cette location est renouvelable dans le respect du principe suivant : priorité sera donnée au débutant.", font));
			    } catch (DocumentException de) {
			      de.printStackTrace();
			    } catch (IOException ioe) {
			      ioe.printStackTrace();
			    }

			    document.close();
			    Desktop desktop = Desktop.getDesktop();
				desktop.print(new File("ContratLocationInterne"+nom+""+prenom+""+numeroLocation+".pdf"));
			}
			
			
			Map<String, String> tarifs = LocationCtrl.recupereTarifsLocation();
			LocationCtrl.ajouterLocation(""+session.getAttribute(PARAM_ID_ADHERENT), ""+session.getAttribute(PARAM_ID_DESIGNATION), ""+session.getAttribute("etatDebut"), ""+session.getAttribute(PARAM_DATE_DEBUT), ""+session.getAttribute(PARAM_DATE_FIN), Float.parseFloat(""+tarifs.get("caution")), Float.parseFloat(""+tarifs.get("montantLocationInterne")));
			Materiel materiel = MaterielCtrl.getMaterielById(Integer.parseInt(""+session.getAttribute(PARAM_ID_DESIGNATION)));
			MaterielCtrl.updateEstLouable(Integer.parseInt(""+session.getAttribute(PARAM_ID_DESIGNATION)), 0);
			response.sendRedirect(request.getContextPath()+Pattern.ACCUEIL);
		}
		
		//Traitement du formulaire
		if(Form.getValeurChamp(request, PARAM_ID_DESIGNATION)!=null){
			String dateFin = locationForm.setDateFinForm(Form.getValeurChamp(request, PARAM_DATE_DEBUT));
			String personne = Form.getValeurChamp(request, PARAM_ID_ADHERENT);
			String[] tempPers = personne.split(" ");
			String nomPers = tempPers[0];
			String prenomPers = tempPers[1];
			String idPersonne = PersonneCtrl.getIdByNomAndPrenom(nomPers, prenomPers);
			String idMateriel = Form.getValeurChamp(request, PARAM_ID_DESIGNATION);
			session.setAttribute("PARAM_ID_DESIGNATION",Integer.parseInt(idMateriel));
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
				
			String nom = null, prenom = null;
				
			List<Adherent> adhs = AdherentCtrl.recupererTousAdherents();
			for(Adherent adh : adhs){
				if(adh.getIdPersonne()==Long.parseLong(idPersonne)){
					nom = adh.getNom();
					prenom = adh.getPrenom();
				}
			}
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
		}
		
		//Génération du formulaire de location
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
					if(mat.isDeplacable()==true){
						mats = Arrays.asList(mat.getDesignation().getLibelleDesignation(), mat.getNumSerie(), mat.getTypeMat(), mat.getDateAchat(), mat.getValeurAchat(), mat.getValeurReap(), "Oui", mat.getObservation());
					}else{
						mats = Arrays.asList(mat.getDesignation().getLibelleDesignation(), mat.getNumSerie(), mat.getTypeMat(), mat.getDateAchat(), mat.getValeurAchat(), mat.getValeurReap(), "Non", mat.getObservation());
					}
					
					listeMats.put(Integer.parseInt(""+mat.getIdMateriel()), mats);
				}
				List<Adherent> listeAdherent = AdherentCtrl.recupererTousAdherents();
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

	@Override
	public int print(Graphics arg0, PageFormat arg1, int arg2) throws PrinterException {
		// TODO Auto-generated method stub
		return 0;
	}

}
