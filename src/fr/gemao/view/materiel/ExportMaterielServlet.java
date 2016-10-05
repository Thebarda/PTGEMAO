package fr.gemao.view.materiel;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.gemao.Config;
import fr.gemao.ctrl.materiel.MaterielCtrl;
import fr.gemao.entity.materiel.Materiel;
import fr.gemao.fileparser.CSVFileParser;
import fr.gemao.fileparser.Parser;
import fr.gemao.view.JSPFile;
import fr.gemao.view.Pattern;
import fr.gemao.view.ResultatServlet;

/**
 * Servlet implementation class ExportAdherentsServlet
 */
@WebServlet(Pattern.MATERIEL_EXPORTER)
public class ExportMaterielServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String,Object> map = new LinkedHashMap<String, Object>();
		
		// Initialisation de la map
		map.put("Numéro IMMO", new ArrayList<String>());
		map.put("Etat", new ArrayList<String>());
		map.put("Quantité", new ArrayList<String>());
		map.put("Catégorie", new ArrayList<String>());
		map.put("Marque", new ArrayList<String>());
		map.put("Désignation", new ArrayList<String>());
		map.put("Fournisseur", new ArrayList<String>());
		map.put("Type", new ArrayList<String>());
		map.put("Numéro de série", new ArrayList<String>());
		map.put("Date achat", new ArrayList<String>());
		map.put("Valeur achat", new ArrayList<String>());
		map.put("Valeur réapprovisionnement", new ArrayList<String>());
		map.put("Déplacé lors des concerts", new ArrayList<String>());
		map.put("Louable", new ArrayList<String>());
		map.put("Observations", new ArrayList<String>());

		
		MaterielCtrl ctrl = new MaterielCtrl();
		List<Materiel> listeMateriel = ctrl.recupererTousMateriels();
		List<String> listeDonnees;
		
		for(Materiel m : listeMateriel){
			listeDonnees = (List<String>) map.get("Numéro IMMO");
			listeDonnees.add("ANA-"+m.getIdMateriel()+"");
			
			listeDonnees = (List<String>) map.get("Etat");
			listeDonnees.add(m.getEtat().getLibelleEtat()+"");
			
			listeDonnees = (List<String>) map.get("Quantité");
			listeDonnees.add(m.getQuantite()+"");
			
			listeDonnees = (List<String>) map.get("Catégorie");
			if(m.getCategorie() != null)
				listeDonnees.add(m.getCategorie().getLibelleCat()+"");
			else
				listeDonnees.add(" ");
			
			listeDonnees = (List<String>) map.get("Marque");
			if(m.getMarque() != null)
				listeDonnees.add(m.getMarque().getNomMarque()+"");
			else
				listeDonnees.add(" ");
			
			listeDonnees = (List<String>) map.get("Désignation");
			if(m.getDesignation() != null)
				listeDonnees.add(m.getDesignation().getLibelleDesignation()+"");
			else
				listeDonnees.add(" ");
			
			listeDonnees = (List<String>) map.get("Fournisseur");
			if(m.getFournisseur() != null)
				listeDonnees.add(m.getFournisseur().getNomFournisseur()+"");
			else
				listeDonnees.add(" ");
			
			listeDonnees = (List<String>) map.get("Type");
			if(m.getTypeMat() != null)
				listeDonnees.add(m.getTypeMat());
			else
				listeDonnees.add(" ");
			
			listeDonnees = (List<String>) map.get("Numéro de série");
			if(m.getNumSerie() != null)
				listeDonnees.add(m.getNumSerie());
			else
				listeDonnees.add(" ");

			listeDonnees = (List<String>) map.get("Date achat");
			if(m.getDateAchat() != null)
				listeDonnees.add(new SimpleDateFormat("dd/MM/yyyy").format(m.getDateAchat()));
			else
				listeDonnees.add(" ");
			
			listeDonnees = (List<String>) map.get("Valeur achat");
			listeDonnees.add(m.getValeurAchat()+"");
			
			listeDonnees = (List<String>) map.get("Valeur réapprovisionnement");
			listeDonnees.add(m.getValeurReap()+"");
			
			listeDonnees = (List<String>) map.get("Déplacé lors des concerts");
			listeDonnees.add(m.isDeplacable()?"Oui":"Non");
			
			listeDonnees = (List<String>) map.get("Louable");
			listeDonnees.add(m.isLouable()?"Oui":"Non");
			
			listeDonnees = (List<String>) map.get("Observations");
			if(m.getObservation() != null)
				listeDonnees.add(m.getObservation());
			else
				listeDonnees.add(" ");
			
		}
		
		String path = request.getSession().getServletContext().getRealPath("/")
				+ "/" + Config.params.get(Config.DOSSIER_RACINE)
				+ "/exports/materiels.csv";
		String pathURL = request.getScheme() + "://" + request.getServerName()
				+ ":" + request.getServerPort() + request.getContextPath()
				+ "/" + Config.params.get(Config.DOSSIER_RACINE)
				+ "/exports/materiels.csv";
		Parser parser = new CSVFileParser(path);

		request.setAttribute(ResultatServlet.ATTR_NON_BOUTON_2,
				"Télécharger le fichier");
		request.setAttribute(ResultatServlet.ATTR_LIEN_BONTON_2, pathURL);

		request.setAttribute(ResultatServlet.ATTR_LIEN_BOUTON, Pattern.MATERIEL_LISTER);
		request.setAttribute(ResultatServlet.ATTR_NOM_BOUTON, "Retour à la liste");
		request.setAttribute(ResultatServlet.ATTR_TITRE_H1, "Export de données");
		
		if(parser.write(map)){
			// Succès
			request.setAttribute(ResultatServlet.ATTR_RESULTAT, "L'export est terminé, vous pouvez récupérer le fichier csv.");			
			request.getRequestDispatcher(JSPFile.RESULTAT).forward(request, response);
		} else{
			// Echec
			request.setAttribute(ResultatServlet.ATTR_RESULTAT, "L'export a échoué, le fichier doit être utilisé par une autre application.");			
			request.getRequestDispatcher(JSPFile.ERREUR).forward(request, response);			
		}
		
		
	}



}
