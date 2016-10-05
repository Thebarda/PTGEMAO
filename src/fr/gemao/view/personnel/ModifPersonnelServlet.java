package fr.gemao.view.personnel;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.gemao.ctrl.AdresseCtrl;
import fr.gemao.ctrl.CommuneCtrl;
import fr.gemao.ctrl.administration.ModificationCtrl;
import fr.gemao.ctrl.personnel.PersonnelCtrl;
import fr.gemao.entity.Adresse;
import fr.gemao.entity.Commune;
import fr.gemao.entity.administration.Modification;
import fr.gemao.entity.cours.Discipline;
import fr.gemao.entity.personnel.Contrat;
import fr.gemao.entity.personnel.Diplome;
import fr.gemao.entity.personnel.Personnel;
import fr.gemao.entity.personnel.Responsabilite;
import fr.gemao.form.personnel.PersonnelForm;
import fr.gemao.sql.DAOFactory;
import fr.gemao.view.ConnexionServlet;
import fr.gemao.view.JSPFile;
import fr.gemao.view.Pattern;

/**
 * Servlet implémentant la classe ModifPersonnelServlet
 */
@WebServlet(Pattern.PERSONNEL_MODIFIER)
public class ModifPersonnelServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Chargement de la page de modification d'un personnel. Un paramètre doit
	 * être envoyé au doGet pour récupérer la personne concernée par les
	 * modifications.
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();

		/* Si le paramètre id est null */
		if (request.getParameter("id") == null) {
			this.getServletContext().getRequestDispatcher(JSPFile.PERSONNEL_LISTER).forward(request, response);
			/* Sinon */
		} else {
			long id = Long.parseLong(request.getParameter("id"));
			Personnel personnel = PersonnelCtrl.recupererPersonnel(id);

			Adresse adresse = AdresseCtrl.recupererAdresse(personnel.getAdresse().getIdAdresse());

			Commune commune = CommuneCtrl.recupererCommune(adresse.getCommune().getIdCommune());

			List<Contrat> contrats = personnel.getContrat();

			List<Responsabilite> listeResponsabilite = personnel.getListeResponsabilite();

			/* Si la liste des responsabilités est vide */
			if (listeResponsabilite.isEmpty()) {
				listeResponsabilite.add(new Responsabilite(0, "Aucune"));
			}

			List<Diplome> listeDiplome = personnel.getListeDiplomes();
			List<Discipline> listeDiscipline = personnel.getListeDiscipline();

			/* Si la liste des diplômes est vide */
			if (listeDiplome.isEmpty()) {
				listeDiplome.add(new Diplome(0, "Aucun"));
			}

			/* Positionnement des attributs */
			session.setAttribute("listeDiplome", listeDiplome);
			session.setAttribute("listeResponsabilite", listeResponsabilite);
			session.setAttribute("listeDiscipline", listeDiscipline);
			session.setAttribute("personnel", personnel);
			session.setAttribute("adresse", adresse);
			session.setAttribute("commune", commune);
			session.setAttribute("contrats", contrats);
			this.getServletContext().getRequestDispatcher(JSPFile.PERSONNEL_MODIFIER).forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PersonnelForm form = new PersonnelForm();

		form.testerPersonnel(request);

		/* Récupération de la session depuis la requête */
		HttpSession session = request.getSession();

		/* Si le formulaire n'a pas renvoyé d'erreur */
		if (form.getErreurs().isEmpty()) {
			Personnel pers = (Personnel) session.getAttribute("personnel");

			/*
			 * Positionnement des attributs du pers à partir des informations du
			 * form
			 */
			pers.setNom(form.getNom());
			pers.setPrenom(form.getPrenom());
			pers.setTelFixe(form.getTelFixe());
			pers.setTelPort(form.getTelPort());
			pers.setEmail(form.getEmail());

			String nomCommune = request.getParameter("ville");
			Integer codePostal = Integer.parseInt(request.getParameter("code"));
			Commune commune = new Commune(null, codePostal, nomCommune, false);

			CommuneCtrl.ajoutCommune(commune);

			Adresse adresse = new Adresse();
			String numRue = request.getParameter("numRue");
			adresse.setNumRue(numRue);
			String nomRue = request.getParameter("nomRue");
			adresse.setNomRue(nomRue);
			String infoCompl = request.getParameter("infoCompl");
			adresse.setInfoCompl(infoCompl);
			adresse.setCommune(commune);
			AdresseCtrl.ajoutAdresse(adresse);

			DAOFactory factory = DAOFactory.getInstance();
			commune = factory.getCommuneDAO().existNomCodePostal(commune);

			pers.setAdresse(adresse);
			pers.setListeDiplomes(form.getListeDiplomes());
			pers.setListeResponsabilite(form.getListeResponsabilite());
			pers.setNumeroSS(form.getNumeroSS());
			String membreCA = request.getParameter("membreCA");
			pers.setMembreCA(Boolean.parseBoolean(membreCA));

			session.removeAttribute("personnel");

			PersonnelCtrl.modifierPersonnel(pers);

			// Archivage
			ModificationCtrl.ajouterModification(
					new Modification(0, (Personnel) session.getAttribute(ConnexionServlet.ATT_SESSION_USER), new Date(),
							"Modification personnel : " + pers.getNom() + " " + pers.getPrenom()));
			session.setAttribute("modif_personnel", pers);

			/* On redirige vers la liste des personnels */
			response.sendRedirect(request.getContextPath() + Pattern.PERSONNEL_LISTER);
		} else {
			form.setErreur("Modification", "Problème de session");
			this.getServletContext().getRequestDispatcher(JSPFile.PERSONNEL_MODIFIER).forward(request, response);
		}

	}
}