package fr.gemao.view.planning.creneau;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;

import fr.gemao.ctrl.JourCtrl;
import fr.gemao.ctrl.planning.ContrainteCtrl;
import fr.gemao.ctrl.planning.CoursCtrl;
import fr.gemao.ctrl.planning.CreneauCtrl;
import fr.gemao.entity.Jour;
import fr.gemao.entity.cours.Cours;
import fr.gemao.entity.cours.Salle;
import fr.gemao.entity.planning.Contrainte;
import fr.gemao.entity.planning.Creneau;
import fr.gemao.entity.planning.Planning;
import fr.gemao.entity.util.HeurePlanning;
import fr.gemao.form.util.Form;
import fr.gemao.view.JSPFile;
import fr.gemao.view.Pattern;
import fr.gemao.view.ResultatServlet;

/**
 * Servlet implementation class AfficherPlanningServlet
 */
@WebServlet(Pattern.CRENEAU_CREER)
public class CreerCreneauServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private List<Contrainte> contraintes = new ArrayList<>();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		Planning p = (Planning) session.getAttribute("planning");
		if (p == null) {
			ResultatServlet.redirectErreur(request, response, "Erreur", "Aucun planning selectionné !",
					Pattern.PLANNING_LISTER, "Retour à la liste des plannings", null, null, null);
			return;
		}

		List<Salle> listeSalle = new ArrayList<>();
		List<Jour> listeJour = new JourCtrl().getAllJours();
		List<Cours> listeCours = CoursCtrl.getAll();
		// La liste de cours est la liste de cours déjà existant.

		listeSalle = CreneauCtrl.getAllSalles();
		request.setAttribute("LISTE_JOUR", listeJour);
		request.setAttribute("LISTE_SALLE", listeSalle);
		request.setAttribute("LISTE_COURS", listeCours);

		this.getServletContext().getRequestDispatcher(JSPFile.CRENEAU_CREER).forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		JourCtrl ctrlJour = new JourCtrl();

		HttpSession session = request.getSession();
		Planning p = (Planning) session.getAttribute("planning");

		String libelle = Form.getValeurChamp(request, "libelle");
		Salle salle = CreneauCtrl.getSalle((Integer.parseInt(Form.getValeurChamp(request, "salle"))));
		Jour jour = ctrlJour.getJour(Integer.parseInt(Form.getValeurChamp(request, "jour")));
		String couleur = Form.getValeurChamp(request, "couleur");
		Boolean isGlobal = Boolean.parseBoolean(Form.getValeurChamp(request, "isGlobale"));

		couleur = couleur.substring(1, 7);

		HeurePlanning heureDeb = null;
		if (Form.getValeurChamp(request, "heureDeb") != null && Form.getValeurChamp(request, "minuteDeb") != null) {
			heureDeb = new HeurePlanning(Integer.parseInt(Form.getValeurChamp(request, "heureDeb")),
					Integer.parseInt(Form.getValeurChamp(request, "minuteDeb")));
		} else if (Form.getValeurChamp(request, "heureDeb") != null
				&& Form.getValeurChamp(request, "minuteDeb") == null) {
			heureDeb = new HeurePlanning(Integer.parseInt(Form.getValeurChamp(request, "heureDeb")), 0);
		} else if (Form.getValeurChamp(request, "heureDeb") == null
				&& Form.getValeurChamp(request, "minuteDeb") != null) {
			heureDeb = new HeurePlanning(0, Integer.parseInt(Form.getValeurChamp(request, "minuteDeb")));
		}

		HeurePlanning duree = new HeurePlanning(Integer.parseInt(Form.getValeurChamp(request, "heureDuree")),
				Integer.parseInt(Form.getValeurChamp(request, "minuteDuree")));

		Integer idCreneau = null;
		Integer idPlanning = p.getIdPlanning();
		Integer idCreneau_Planning = null;

		Integer idCours = Integer.valueOf(Form.getValeurChamp(request, "idCours"));

		Cours c = null;

		try {
			c = CoursCtrl.get(idCours);

			// A ce stade, soit c == null, c'est à dire que l'on a selectionné
			// aucun cours, soit c est égal au cours que l'on à choisi.

			Creneau creneau = new Creneau(idCreneau, libelle, heureDeb, duree, idPlanning, salle, jour, couleur, c,
					contraintes);
			
			if (creneau.intersection(CreneauCtrl.getCreneaux(idPlanning))){
				System.out.println("pas de chance");

				ResultatServlet.redirectErreur(request, response, "Résultat de la création d'un créneau",
						"Le créneau n'a pas pû être créé car déborde sur un autre.", Pattern.PLANNING_LISTER, "Retour à la liste des plannings", null,
						null, null);
			} else {
			
				Creneau retour = CreneauCtrl.create(creneau);
				if (retour == null) {
					ResultatServlet.redirect(request, response, "Erreur", "Le creneau n'a pas été ajouté.",
							Pattern.CRENEAU_CREER, "Réessayer ?", null, null, null);
					return;
				}
	
				String jsonContrainte = Form.getValeurChamp(request, "jsonContraintes");
				JSONArray jsonContraintes = new JSONArray(jsonContrainte);
	
				for (int i = 0; i < jsonContraintes.length(); i++) {
					contraintes.add(ajouterContrainte(jsonContraintes.getJSONObject(i), retour));
				}
				for (Contrainte contrainte : contraintes) {
					Contrainte ret = ContrainteCtrl.create(contrainte);
				}

				ResultatServlet.redirect(request, response, "Résultat de la création d'un créneau",
						"Le créneau a bien été créé.", Pattern.PLANNING_LISTER, "Retour à la liste des plannings", null,
						null, null);
			}
		} catch (IllegalStateException ie) {
			ResultatServlet.redirectErreur(request, response, "Une erreur est survenue", ie.getMessage(),
					Pattern.ACCUEIL, "Retour à l'accueil ?", null, null, null);
			return;
		}
	}

	private Contrainte ajouterContrainte(JSONObject contrainte, Creneau creneau) {
		Salle salle = null;
		Jour jour = null;
		HeurePlanning heureDebut = null;
		HeurePlanning heureFin = null;
		String message = null;
		Boolean isGlobale = false;

		if (!contrainte.isNull("idSalle")) {
			if (contrainte.getInt("idSalle") != -1) {
				salle = new Salle(contrainte.getInt("idSalle"), null);
			}
		}

		if (!contrainte.isNull("idJour")) {
			if (contrainte.getInt("idJour") != 9) {
				jour = new Jour(contrainte.getInt("idJour"), null);
			}
		}

		if (!contrainte.isNull("heureDebContrainte") && !contrainte.isNull("minuteDebContrainte")) {
			if (!contrainte.getString("heureDebContrainte").isEmpty()
					&& !contrainte.getString("minuteDebContrainte").isEmpty()) {
				heureDebut = new HeurePlanning(contrainte.getInt("heureDebContrainte"),
						contrainte.getInt("minuteDebContrainte"));
			}
		}
		if (!contrainte.isNull("heureFinContrainte") && !contrainte.isNull("minuteFinContrainte")) {
			if (!contrainte.getString("heureFinContrainte").isEmpty()
					&& !contrainte.getString("minuteFinContrainte").isEmpty()) {
				heureFin = new HeurePlanning(contrainte.getInt("heureFinContrainte"),
						contrainte.getInt("minuteFinContrainte"));
			}
		}

		if (!contrainte.isNull("messageContrainte")) {
			if (!contrainte.getString("messageContrainte").isEmpty()) {
				message = contrainte.getString("messageContrainte");
			}
		}

		if (!contrainte.isNull("isGlobale")) {
			isGlobale = contrainte.getBoolean("isGlobale");
		}

		if (isGlobale) {
			return new Contrainte(null, null, creneau.getIdPlanning(), salle, jour, heureDebut, heureFin, message);
		} else {
			return new Contrainte(null, creneau.getIdCreneau(), null, salle, jour, heureDebut, heureFin, message);
		}

	}
}
