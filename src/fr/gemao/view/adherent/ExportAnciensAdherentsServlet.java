package fr.gemao.view.adherent;

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
import fr.gemao.ctrl.adherent.AdherentCtrl;
import fr.gemao.entity.adherent.Adherent;
import fr.gemao.fileparser.CSVFileParser;
import fr.gemao.fileparser.Parser;
import fr.gemao.view.JSPFile;
import fr.gemao.view.Pattern;
import fr.gemao.view.ResultatServlet;

/**
 * Servlet implementation class ExportAdherentsServlet
 */
@WebServlet(Pattern.ADHERENT_EXPORTER_ANCIENS)
public class ExportAnciensAdherentsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Map<String, Object> map = new LinkedHashMap<String, Object>();

		// Initialisation de la map
		// Informations liées à la personne
		map.put("ID Personne", new ArrayList<String>());
		map.put("Rue", new ArrayList<String>());
		map.put("Info complémentaire", new ArrayList<String>());
		map.put("Commune", new ArrayList<String>());
		map.put("Code postal", new ArrayList<String>());
		map.put("Sexe", new ArrayList<String>());
		map.put("Nom", new ArrayList<String>());
		map.put("Prénom", new ArrayList<String>());
		map.put("Date de naissance", new ArrayList<String>());
		map.put("Téléphone fixe", new ArrayList<String>());
		map.put("Téléphone portable", new ArrayList<String>());
		map.put("Email", new ArrayList<String>());
		map.put("Membre CA", new ArrayList<String>());

		// Informations liées à l'adhérent
		map.put("Responsable", new ArrayList<String>());
		map.put("Email responsable", new ArrayList<String>());
		// TODO
		// map.put("Famille", new ArrayList<String>());
		map.put("Droit image", new ArrayList<String>());
		map.put("Date entrée", new ArrayList<String>());
		map.put("Date sortie", new ArrayList<String>());
		map.put("Motif sortie", new ArrayList<String>());
		map.put("Cotisation", new ArrayList<String>());
		map.put("Quotient familial", new ArrayList<String>());
		map.put("Paiement effectué", new ArrayList<String>());

		List<Adherent> listeAdherents = AdherentCtrl.recupererAnciensAdherents();
		List<String> listeDonnees;

		for (Adherent a : listeAdherents) {
			listeDonnees = (List<String>) map.get("ID Personne");
			listeDonnees.add(a.getIdPersonne() + "");

			listeDonnees = (List<String>) map.get("Rue");
			listeDonnees.add(a.getAdresse().getNumRue() + " " + a.getAdresse().getNomRue());

			listeDonnees = (List<String>) map.get("Info complémentaire");
			listeDonnees.add(a.getAdresse().getInfoCompl());

			listeDonnees = (List<String>) map.get("Commune");
			listeDonnees.add(a.getAdresse().getCommune().getNomCommune() + "");

			listeDonnees = (List<String>) map.get("Code postal");
			listeDonnees.add(a.getAdresse().getCommune().getCodePostal() + "");

			listeDonnees = (List<String>) map.get("Sexe");
			listeDonnees.add(a.getCivilite().getSexe());

			listeDonnees = (List<String>) map.get("Nom");
			listeDonnees.add(a.getNom());

			listeDonnees = (List<String>) map.get("Prénom");
			listeDonnees.add(a.getPrenom());

			listeDonnees = (List<String>) map.get("Date de naissance");
			listeDonnees.add(new SimpleDateFormat("dd/MM/yyyy").format(a.getDateNaissance()));

			listeDonnees = (List<String>) map.get("Téléphone fixe");
			listeDonnees.add(" " + a.getTelFixe());

			listeDonnees = (List<String>) map.get("Téléphone portable");
			listeDonnees.add(" " + a.getTelPort());

			listeDonnees = (List<String>) map.get("Email");
			listeDonnees.add(a.getEmail());

			listeDonnees = (List<String>) map.get("Membre CA");
			listeDonnees.add(a.isMembreCA() ? "Oui" : "Non");

			listeDonnees = (List<String>) map.get("Responsable");
			listeDonnees.add(a.getResponsable() == null ? " " : a.getResponsable().getPrenom() + " " + a.getNom());

			listeDonnees = (List<String>) map.get("Email responsable");
			listeDonnees.add(a.getResponsable() == null ? " " : a.getResponsable().getEmail());

			// TODO
			// listeDonnees = (List<String>) map.get("Famille");
			// listeDonnees.add(a.getFamille()==null?"-":a.getFamille().getNomFamille());

			listeDonnees = (List<String>) map.get("Droit image");
			listeDonnees.add(a.isDroitImage() ? "Oui" : "Non");

			listeDonnees = (List<String>) map.get("Date entrée");
			listeDonnees.add(new SimpleDateFormat("dd/MM/yyyy").format(a.getDateEntree()));

			listeDonnees = (List<String>) map.get("Date sortie");
			listeDonnees.add(new SimpleDateFormat("dd/MM/yyyy").format(a.getDateSortie()));

			listeDonnees = (List<String>) map.get("Motif sortie");
			listeDonnees.add(a.getMotif().getLibelle());

			listeDonnees = (List<String>) map.get("Cotisation");
			listeDonnees.add(a.getCotisation() + "");

			listeDonnees = (List<String>) map.get("Quotient familial");
			listeDonnees.add(a.getQf() + "");

			listeDonnees = (List<String>) map.get("Paiement effectué");
			listeDonnees.add(a.isAPaye() ? "Oui" : "Non");
		}

		String path = request.getSession().getServletContext().getRealPath("/") + "/"
				+ Config.params.get(Config.DOSSIER_RACINE) + "/exports/anciensAdherents.csv";
		String pathURL = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
				+ request.getContextPath() + "/" + Config.params.get(Config.DOSSIER_RACINE)
				+ "/exports/anciensAdherents.csv";
		Parser parser = new CSVFileParser(path);

		request.setAttribute(ResultatServlet.ATTR_NON_BOUTON_2, "Télécharger le fichier");
		request.setAttribute(ResultatServlet.ATTR_LIEN_BONTON_2, pathURL);

		request.setAttribute(ResultatServlet.ATTR_LIEN_BOUTON, Pattern.ADHERENT_LISTER);
		request.setAttribute(ResultatServlet.ATTR_NOM_BOUTON, "Retour à la liste");
		request.setAttribute(ResultatServlet.ATTR_TITRE_H1, "Export de données");

		if (parser.write(map)) {
			// Succès
			request.setAttribute(ResultatServlet.ATTR_RESULTAT,
					"L'export est terminé, vous pouvez récupérer le fichier csv.");
			request.getRequestDispatcher(JSPFile.RESULTAT).forward(request, response);
		} else {
			// Echec
			request.setAttribute(ResultatServlet.ATTR_RESULTAT,
					"L'export a échoué, le fichier doit être utilisé par une autre application.");
			request.getRequestDispatcher(JSPFile.ERREUR).forward(request, response);
		}

	}

}
