package fr.gemao.view.adherent;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.gemao.ctrl.adherent.AdherentCtrl;
import fr.gemao.ctrl.adherent.MotifSortieCtrl;
import fr.gemao.ctrl.administration.ModificationCtrl;
import fr.gemao.entity.adherent.Adherent;
import fr.gemao.entity.adherent.MotifSortie;
import fr.gemao.entity.administration.Modification;
import fr.gemao.entity.personnel.Personnel;
import fr.gemao.form.adherent.DesinscrireAdherentForm;
import fr.gemao.form.util.Form;
import fr.gemao.sql.exception.DAOException;
import fr.gemao.view.ConnexionServlet;
import fr.gemao.view.JSPFile;
import fr.gemao.view.Pattern;

/**
 * Servlet implementation class DesinscrireAdherentServlet
 */
@WebServlet(Pattern.ADHERENT_DESINSCRIRE)
public class DesinscrireAdherentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CHAMP_IDADHERENT = "id";
	private static final String CHAMP_DATE_SORTIE = "dateSortie";
	private static final String CHAMP_MOTIF_SORTIE = "motifSortie";

	private static final String ATTR_LISTE_MOTIF = "listMotifSortie";
	private static final String ATTR_CACHE_LIBELLEMOTIF = "libelleMotif";

	private static final String ERREUR_DATE = "erreurDate";
	private static final String ERREUR_AJOUT_MOTIFSORTIE = "erreurMotif";
	private static final String ERREUR_MODIFICATION = "erreurModif";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Récupération de l'identifiant de l'adhérent
		String id = Form.getValeurChamp(req, CHAMP_IDADHERENT);
		if (id == null) {
			// Accès illégal à cette page
			req.getRequestDispatcher(Pattern.ACCUEIL).forward(req, resp);
		} else {
			// On place l'identifiant dans une variable de session
			Integer idAdh = Integer.parseInt(id);
			HttpSession session = req.getSession();
			session.setAttribute(CHAMP_IDADHERENT, idAdh);

			// On envoie la liste des motifs de sortie à la page
			List<MotifSortie> list = MotifSortieCtrl.recupererAllMotifSortie();
			req.setAttribute(ATTR_LISTE_MOTIF, list);

			// On envoie vers la page jsp
			req.getRequestDispatcher(JSPFile.ADHERENT_DESINSCRIRE_ADHERENT).forward(req, resp);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		DesinscrireAdherentForm form = new DesinscrireAdherentForm();

		// Récupération des données
		MotifSortie infosMotif = new MotifSortie();
		String libelle = Form.getValeurChamp(request, ATTR_LISTE_MOTIF);
		if (libelle != null) {
			infosMotif.setLibelle(libelle);
		}

		// Ajout d'un motif, le fait qu'elle ne soit pas vide
		// a déjà été testé
		if (Form.getValeurChamp(request, ATTR_CACHE_LIBELLEMOTIF) != null) {
			try {
				MotifSortie motif = new MotifSortie(null, Form.getValeurChamp(request, ATTR_CACHE_LIBELLEMOTIF));
				MotifSortieCtrl.ajoutMotif(motif);
				request.setAttribute("resultat", "Le motif a bien été ajouté.");
			} catch (DAOException e) {
				form.setErreur(ERREUR_AJOUT_MOTIFSORTIE, e.getMessage());
			}
		}

		// On envoie la liste des motifs de sortie (si erreur ou ajout d'un
		// motif)
		List<MotifSortie> listMotif = MotifSortieCtrl.recupererAllMotifSortie();
		request.setAttribute(ATTR_LISTE_MOTIF, listMotif);

		// Si ce n'est pas un ajout d'un type de motif de sortie
		if (Form.getValeurChamp(request, ATTR_CACHE_LIBELLEMOTIF) == null) {
			// On récupère les données du formulaire
			String dateSortie = Form.getValeurChamp(request, CHAMP_DATE_SORTIE);
			String idMotif = Form.getValeurChamp(request, CHAMP_MOTIF_SORTIE);

			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			Date date;
			try {
				date = formatter.parse(dateSortie);

				// On récupère l'adhérent concerné grâce à son identifiant
				// contenu dans une variable de session et on lui affecte
				// les données du formulaire
				Adherent adherent = new Adherent();

				adherent = AdherentCtrl.recupererAdherent((int) (session.getAttribute(CHAMP_IDADHERENT)));
				adherent.setMotif(MotifSortieCtrl.recupererMotifSortie(Integer.parseInt(idMotif)));
				adherent.setDateSortie(date);

				if (!AdherentCtrl.modifierAdherent(adherent)) {
					// Si un problème survient
					form.setErreur(ERREUR_MODIFICATION,
							"Une erreur est survenue lors de la désinscription d'un adhérent.");
				} else {
					// Succès de la désincription
					// Archivage
					ModificationCtrl.ajouterModification(new Modification(0,
							(Personnel) session.getAttribute(ConnexionServlet.ATT_SESSION_USER), new Date(),
							"Désincription adhérent : " + adherent.getNom() + " " + adherent.getPrenom()));

					// Redirection
					request.setAttribute("resultat", "La désincription a été effectuée.");
				}

			} catch (ParseException e) {
				form.setErreur(ERREUR_DATE, "Le format de la date est invalide.");
			}

		}

		// Envoi d'éventuelles erreurs
		request.setAttribute("form", form);
		this.getServletContext().getRequestDispatcher(JSPFile.ADHERENT_DESINSCRIRE_ADHERENT).forward(request, response);
	}
}
