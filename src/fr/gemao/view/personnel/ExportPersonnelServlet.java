package fr.gemao.view.personnel;

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
import fr.gemao.ctrl.personnel.PersonnelCtrl;
import fr.gemao.entity.personnel.Personnel;
import fr.gemao.fileparser.CSVFileParser;
import fr.gemao.fileparser.Parser;
import fr.gemao.view.JSPFile;
import fr.gemao.view.Pattern;
import fr.gemao.view.ResultatServlet;

/**
 * Servlet implementation class ExportAdherentsServlet
 */
@WebServlet(Pattern.PERSONNEL_EXPORTER)
public class ExportPersonnelServlet extends HttpServlet {
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

		// Informations liées au membre du personnel
		/*
		 * map.put("Contrat", new ArrayList<String>()); map.put(
		 * "Date début contrat", new ArrayList<String>()); map.put(
		 * "Date échéance contrat", new ArrayList<String>()); map.put(
		 * "Date rupture contrat", new ArrayList<String>()); map.put(
		 * "Motif rupture contrat", new ArrayList<String>());
		 */
		map.put("Profil", new ArrayList<String>());
		map.put("Login", new ArrayList<String>());
		map.put("Date début enseignement", new ArrayList<String>());

		List<Personnel> listePersonnel = PersonnelCtrl.recupererTousPersonnels();
		List<String> listeDonnees;

		for (Personnel p : listePersonnel) {
			// Informations sur la personne
			listeDonnees = (List<String>) map.get("ID Personne");
			listeDonnees.add(p.getIdPersonne() + "");

			listeDonnees = (List<String>) map.get("Rue");
			listeDonnees.add(p.getAdresse().getNumRue() + " " + p.getAdresse().getNomRue());

			listeDonnees = (List<String>) map.get("Info complémentaire");
			if (p.getAdresse().getInfoCompl() != null)
				listeDonnees.add(p.getAdresse().getInfoCompl());
			else
				listeDonnees.add(" ");

			listeDonnees = (List<String>) map.get("Commune");
			listeDonnees.add(p.getAdresse().getCommune().getNomCommune() + "");

			listeDonnees = (List<String>) map.get("Code postal");
			listeDonnees.add(p.getAdresse().getCommune().getCodePostal() + "");

			listeDonnees = (List<String>) map.get("Sexe");
			listeDonnees.add(p.getCivilite().getSexe() + "");

			listeDonnees = (List<String>) map.get("Nom");
			listeDonnees.add(p.getNom() + "");

			listeDonnees = (List<String>) map.get("Prénom");
			listeDonnees.add(p.getPrenom() + "");

			listeDonnees = (List<String>) map.get("Date de naissance");
			listeDonnees.add(new SimpleDateFormat("dd/MM/yyyy").format(p.getDateNaissance()));

			listeDonnees = (List<String>) map.get("Téléphone fixe");
			listeDonnees.add(" " + p.getTelFixe());

			listeDonnees = (List<String>) map.get("Téléphone portable");
			listeDonnees.add(" " + p.getTelPort());

			listeDonnees = (List<String>) map.get("Email");
			listeDonnees.add(p.getEmail() + "");

			listeDonnees = (List<String>) map.get("Membre CA");
			listeDonnees.add(p.isMembreCA() ? "Oui" : "Non");

			// Informations sur le membre du personnel
			/*
			 * listeDonnees = (List<String>) map.get("Contrat");
			 * listeDonnees.add(p.getContrat().getTypeContrat().getLibelle()+"")
			 * ;
			 * 
			 * listeDonnees = (List<String>) map.get("Date début contrat");
			 * listeDonnees.add(new
			 * SimpleDateFormat("dd/MM/yyyy").format(p.getContrat().getDateDebut
			 * ()));
			 * 
			 * listeDonnees = (List<String>) map.get("Date échéance contrat");
			 * if(p.getContrat().getDateFin() != null) listeDonnees.add(new
			 * SimpleDateFormat("dd/MM/yyyy").format(p.getContrat().getDateFin()
			 * )); else listeDonnees.add(" ");
			 * 
			 * listeDonnees = (List<String>) map.get("Date rupture contrat");
			 * if(p.getContrat().getDateRupture() != null) listeDonnees.add(new
			 * SimpleDateFormat("dd/MM/yyyy").format(p.getContrat().
			 * getDateRupture())); else listeDonnees.add(" ");
			 * 
			 * listeDonnees = (List<String>) map.get("Motif rupture contrat");
			 * if(p.getContrat().getMotifFinContrat() != null)
			 * listeDonnees.add(p.getContrat().getMotifFinContrat().getLibelle()
			 * +""); else listeDonnees.add(" ");
			 */

			listeDonnees = (List<String>) map.get("Profil");
			if (p.getProfil() != null)
				listeDonnees.add(p.getProfil().getNomProfil() + "");
			else
				listeDonnees.add(" ");

			listeDonnees = (List<String>) map.get("Login");
			listeDonnees.add(p.getLogin() + "");

			/*
			 * listeDonnees = (List<String>) map.get("Contrat");
			 * listeDonnees.add(p.getContrat().getTypeContrat().getLibelle()+"")
			 * ;
			 */

			listeDonnees = (List<String>) map.get("Date début enseignement");
			if (p.getDateEntree() != null)
				listeDonnees.add(new SimpleDateFormat("dd/MM/yyyy").format(p.getDateEntree()));
			else
				listeDonnees.add(" ");
		}

		String path = request.getSession().getServletContext().getRealPath("/") + "/"
				+ Config.params.get(Config.DOSSIER_RACINE) + "/exports/personnels.csv";
		String pathURL = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
				+ request.getContextPath() + "/" + Config.params.get(Config.DOSSIER_RACINE) + "/exports/personnels.csv";
		Parser parser = new CSVFileParser(path);

		request.setAttribute(ResultatServlet.ATTR_NON_BOUTON_2, "Télécharger le fichier");
		request.setAttribute(ResultatServlet.ATTR_LIEN_BONTON_2, pathURL);

		request.setAttribute(ResultatServlet.ATTR_LIEN_BOUTON, Pattern.PERSONNEL_LISTER);
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
