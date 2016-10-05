package fr.gemao.view.administration;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.gemao.ctrl.administration.ModificationCtrl;
import fr.gemao.ctrl.administration.ProfilsCtrl;
import fr.gemao.ctrl.personnel.PersonnelCtrl;
import fr.gemao.entity.administration.Modification;
import fr.gemao.entity.administration.Profil;
import fr.gemao.entity.administration.ProfilEnum;
import fr.gemao.entity.personnel.Personnel;
import fr.gemao.form.util.Form;
import fr.gemao.view.ConnexionServlet;
import fr.gemao.view.JSPFile;
import fr.gemao.view.Pattern;

/**
 * Servlet représentant l'interface servant à modifier le profil d'un
 * utilisateur
 */
@WebServlet(Pattern.ADMINISTRATION_CHANGER_PROFIL)
public class ChangerProfilServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final String PARAM_IDENTIFIANT = "id";
	public static final String ATTR_PERSONNEL = "personne";
	public static final String ATTR_LISTE_PROFILS = "listeProfils";
	public static final String CHAMP_ID_PROFIL = "idProfil";

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (request.getParameter("id") == null) {
			// Arrivée invalide vers cette page, on renvoie vers la liste
			request.getRequestDispatcher(Pattern.ADMINISTRATION_LISTER_PROFIL).forward(request, response);
			return;
		}

		// Récupération des données du profil dont l'identifiant est passé en
		// paramètre
		int id = Integer.parseInt(Form.getValeurChamp(request, PARAM_IDENTIFIANT));

		Personnel p = PersonnelCtrl.recupererPersonnel((long) id);

		// Passage des données à la page jsp
		request.setAttribute(ATTR_PERSONNEL, p);
		request.setAttribute(ATTR_LISTE_PROFILS, new ProfilsCtrl().getAllProfils());
		request.getRequestDispatcher(JSPFile.ADMINISTRATION_CHANGER_PROFIL).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Récupération des données
		String idProfil = Form.getValeurChamp(request, CHAMP_ID_PROFIL);
		int idPersonne = Integer.parseInt(Form.getValeurChamp(request, PARAM_IDENTIFIANT));
		Personnel p = PersonnelCtrl.recupererPersonnel((long) idPersonne);

		System.out.println(idProfil);
		// Si un profil est sélectionné
		if (idProfil != null) {
			p.setProfil(new ProfilsCtrl().getProfil(Integer.parseInt(idProfil)));
			if (p.getProfil().getIdProfil() == ProfilEnum.MEMBRE_CA.getId()) {
				p.setMembreCA(true);
			}
			// Archivage de la modification
			HttpSession session = request.getSession();
			ProfilsCtrl profilctrl = new ProfilsCtrl();
			Profil profil = profilctrl.getProfil(Integer.parseInt(idProfil));
			ModificationCtrl.ajouterModification(
					new Modification(0, (Personnel) session.getAttribute(ConnexionServlet.ATT_SESSION_USER), new Date(),
							"Changement profil de " + p.getLogin() + " : " + profil.getNomProfil()));
		} else {
			// Sinon si on souhaite n'affecter aucun profil à cette personne
			p.setProfil(null);
		}

		// Modification de la personne
		PersonnelCtrl.modifierPersonnel(p);

		// Redirection
		// response.sendRedirect( request.getContextPath() +
		// Pattern.PERSONNEL_CONSULTER + "?id="+idPersonne );
		response.sendRedirect(request.getContextPath() + Pattern.PERSONNEL_LISTER);
	}

}
