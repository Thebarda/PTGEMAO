package fr.gemao.view.administration;

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
import fr.gemao.ctrl.administration.ModificationCtrl;
import fr.gemao.entity.administration.Modification;
import fr.gemao.fileparser.CSVFileParser;
import fr.gemao.fileparser.Parser;
import fr.gemao.view.JSPFile;
import fr.gemao.view.Pattern;
import fr.gemao.view.ResultatServlet;

/**
 * Servlet implementation class ExportAdherentsServlet
 */
@WebServlet(Pattern.ADMINISTRATION_MODIF_EXPORTER)
public class ExportModificationsServlet extends HttpServlet {
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
		map.put("Jour modification", new ArrayList<String>());
		map.put("Heure modification", new ArrayList<String>());
		map.put("Détails", new ArrayList<String>());

		List<Modification> listeModifications = ModificationCtrl.getAllModifications();
		List<String> listeDonnees;

		for (Modification m : listeModifications) {
			listeDonnees = (List<String>) map.get("ID Personne");
			listeDonnees.add(m.getPersonne().getIdPersonne() + "");

			listeDonnees = (List<String>) map.get("Jour modification");
			listeDonnees.add(new SimpleDateFormat("dd/MM/yyyy").format(m.getDateModif()) + "");

			listeDonnees = (List<String>) map.get("Heure modification");
			listeDonnees.add(new SimpleDateFormat("HH:mm:ss").format(m.getDateModif()) + "");

			listeDonnees = (List<String>) map.get("Détails");
			listeDonnees.add(m.getLibelle() + "");
		}

		String path = request.getSession().getServletContext().getRealPath("/") + "/"
				+ Config.params.get(Config.DOSSIER_RACINE) + "/exports/modifications.csv";
		String pathURL = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
				+ request.getContextPath() + "/" + Config.params.get(Config.DOSSIER_RACINE)
				+ "/exports/modifications.csv";
		Parser parser = new CSVFileParser(path);

		request.setAttribute(ResultatServlet.ATTR_LIEN_BOUTON, Pattern.ADMINISTRATION_LISTER_MODIFICATION);
		request.setAttribute(ResultatServlet.ATTR_NOM_BOUTON, "Retour à la liste");
		request.setAttribute(ResultatServlet.ATTR_TITRE_H1, "Export de données");

		request.setAttribute(ResultatServlet.ATTR_NON_BOUTON_2, "Télécharger le fichier");
		request.setAttribute(ResultatServlet.ATTR_LIEN_BONTON_2, pathURL);

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
