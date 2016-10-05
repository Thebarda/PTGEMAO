package fr.gemao.view.materiel;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.gemao.ctrl.materiel.CategorieCtrl;
import fr.gemao.ctrl.materiel.DesignationCtrl;
import fr.gemao.ctrl.materiel.EtatCtrl;
import fr.gemao.ctrl.materiel.FournisseurCtrl;
import fr.gemao.ctrl.materiel.MarqueCtrl;
import fr.gemao.ctrl.materiel.MaterielCtrl;
import fr.gemao.entity.materiel.Categorie;
import fr.gemao.entity.materiel.Designation;
import fr.gemao.entity.materiel.Etat;
import fr.gemao.entity.materiel.Fournisseur;
import fr.gemao.entity.materiel.Marque;
import fr.gemao.entity.materiel.Materiel;
import fr.gemao.form.materiel.MaterielForm;
import fr.gemao.form.util.Form;
import fr.gemao.sql.DAOFactory;
import fr.gemao.sql.exception.DAOException;
import fr.gemao.sql.materiel.CategorieDAO;
import fr.gemao.sql.materiel.DesignationDAO;
import fr.gemao.sql.materiel.EtatDAO;
import fr.gemao.sql.materiel.FournisseurDAO;
import fr.gemao.sql.materiel.MarqueDAO;
import fr.gemao.view.JSPFile;
import fr.gemao.view.Pattern;

@WebServlet(Pattern.MATERIEL_MODIFIER)
public class ModifierMaterielServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static String VUE_LISTE = Pattern.MATERIEL_LISTER;

	private static String ERREUR_AJOUT_CATEGORIE = "erreurCat";
	private static String ERREUR_AJOUT_DESIGNATION = "erreurDes";
	private static String ERREUR_AJOUT_FOURNISSEUR = "erreurFour";
	private static String ERREUR_AJOUT_MARQUE = "erreurMarque";
	private static String ERREUR_AJOUT_ETAT = "erreurEtat";

	/**
	 * Chargement de la page de modification. Le parametre idMateriel doit etre
	 * envoyé pour le doGet (l'id correspond a celui du materiel a modifier.
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Categorie> listCate = new ArrayList<Categorie>();
		listCate = new CategorieDAO(DAOFactory.getInstance()).getAll();
		request.setAttribute("LISTE_CATEGORIE", listCate);

		List<Etat> listEtat = new ArrayList<Etat>();
		listEtat = new EtatDAO(DAOFactory.getInstance()).getAll();
		request.setAttribute("LISTE_ETAT", listEtat);

		List<Designation> listDesi = new ArrayList<Designation>();
		listDesi = new DesignationDAO(DAOFactory.getInstance()).getAll();
		request.setAttribute("LISTE_DESIGNATION", listDesi);

		List<Marque> listMarques = new ArrayList<Marque>();
		listMarques = new MarqueDAO(DAOFactory.getInstance()).getAll();
		request.setAttribute("LISTE_MARQUE", listMarques);

		List<Fournisseur> listFourni = new ArrayList<Fournisseur>();
		listFourni = new FournisseurDAO(DAOFactory.getInstance()).getAll();
		request.setAttribute("LISTE_FOURNISSEUR", listFourni);

		try {
			HttpSession session = request.getSession();

			String param = request.getParameter("idMateriel");

			if (param != null) {
				int idParametre = Integer.parseInt(param);
				MaterielCtrl matctrl = new MaterielCtrl();
				Materiel mat = matctrl.recupererMateriel(idParametre);
				EtatCtrl etatctrl = new EtatCtrl();
				CategorieCtrl catctrl = new CategorieCtrl();
				FournisseurCtrl fournctrl = new FournisseurCtrl();
				DesignationCtrl desctrl = new DesignationCtrl();
				MarqueCtrl marquectrl = new MarqueCtrl();

				DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
				String dateAchat;
				if (mat.getDateAchat() == null) {
					dateAchat = new String("Non renseignée");
				} else {
					dateAchat = df
							.format(new Date(mat.getDateAchat().getTime()));
				}
				request.setAttribute("dateAchat", dateAchat);

				session.setAttribute("sessionObjectMateriel", mat);

				List<Etat> listEtats = etatctrl.getListeEtat();
				listEtats.remove(mat.getEtat());
				session.setAttribute("listeEtats", listEtats);

				List<Categorie> listCat = catctrl.recupererToutesCategories();
				listCat.remove(mat.getCategorie());
				session.setAttribute("listeCat", listCat);

				List<Fournisseur> listFourn = fournctrl.getListeFournisseur();
				listFourn.remove(mat.getFournisseur());
				session.setAttribute("listeFourn", listFourn);

				List<Designation> listDes = desctrl
						.recupererToutesDesignations();
				listDes.remove(mat.getDesignation());
				session.setAttribute("listeDes", listDes);

				List<Marque> listMarque = marquectrl.recupererToutesMarques();
				listMarque.remove(mat.getMarque());
				session.setAttribute("listeMarque", listMarque);

				this.getServletContext()
						.getRequestDispatcher(JSPFile.MATERIEL_MODIFIER)
						.forward(request, response);
			} else {
				this.getServletContext()
						.getRequestDispatcher(JSPFile.MATERIEL_LISTER)
						.forward(request, response);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		MaterielForm form = new MaterielForm();

		if (request.getParameter("nomMarque") != null
				|| request.getParameter("nomFour") != null
				|| request.getParameter("nomCat") != null
				|| request.getParameter("nomDes") != null
				|| request.getParameter("nomEtat") != null) {
			HttpSession session = request.getSession();
			MaterielCtrl matctrl = new MaterielCtrl();
			EtatCtrl etatctrl = new EtatCtrl();
			CategorieCtrl catctrl = new CategorieCtrl();
			FournisseurCtrl fournctrl = new FournisseurCtrl();
			DesignationCtrl desctrl = new DesignationCtrl();
			MarqueCtrl marquectrl = new MarqueCtrl();
			if (Form.getValeurChamp(request, "nomCat") != null) {
				try {
					CategorieCtrl.ajoutCategorie(Form.getValeurChamp(request,
							"nomCat"));
					List<Categorie> listCat = catctrl.recupererToutesCategories();
					listCat.remove(((Materiel) session.getAttribute("sessionObjectMateriel")).getCategorie());
					session.setAttribute("listeCat", listCat);
				} catch (DAOException e) {
					form.setErreur(ERREUR_AJOUT_CATEGORIE,
							"La catégorie existe déjà");
				}
			}

			if (Form.getValeurChamp(request, "nomDes") != null) {
				try {
					DesignationCtrl.ajoutDesignation(Form.getValeurChamp(
							request, "nomDes"));
					List<Designation> listDes = desctrl
							.recupererToutesDesignations();
					listDes.remove(((Materiel) session.getAttribute("sessionObjectMateriel")).getDesignation());
					session.setAttribute("listeDes", listDes);
				} catch (DAOException e) {
					form.setErreur(ERREUR_AJOUT_DESIGNATION,
							"La désignation existe déjà");
				}
			}

			if (Form.getValeurChamp(request, "nomFour") != null) {
				try {
					FournisseurCtrl.ajoutFournisseur(Form.getValeurChamp(
							request, "nomFour"));
					List<Fournisseur> listFourn = fournctrl.getListeFournisseur();
					listFourn.remove(((Materiel) session.getAttribute("sessionObjectMateriel")).getFournisseur());
					session.setAttribute("listeFourn", listFourn);
				} catch (DAOException e) {
					form.setErreur(ERREUR_AJOUT_FOURNISSEUR,
							"Le fournisseur existe déjà");
				}
			}

			if (Form.getValeurChamp(request, "nomEtat") != null) {
				try {
					EtatCtrl.ajoutEtat(Form.getValeurChamp(request, "nomEtat"));
					List<Etat> listEtats = etatctrl.getListeEtat();
					listEtats.remove(((Materiel) session.getAttribute("sessionObjectMateriel")).getEtat());
					session.setAttribute("listeEtats", listEtats);
				} catch (DAOException e) {
					form.setErreur(ERREUR_AJOUT_ETAT, "L'etat existe déjà");
				}
			}
			
			if (Form.getValeurChamp(request, "nomMarque") != null) {
				try {
					MarqueCtrl.ajouterMarque(Form.getValeurChamp(request,
							"nomMarque"));
					List<Marque> listMarque = marquectrl.recupererToutesMarques();
					listMarque.remove(((Materiel) session.getAttribute("sessionObjectMateriel")).getMarque());
					session.setAttribute("listeMarque", listMarque);
				} catch (DAOException e) {
					form.setErreur(ERREUR_AJOUT_MARQUE,
							"La catégorie existe déjà");
				}
			}
			
			request.setAttribute("form", form);
			
			this.getServletContext()
					.getRequestDispatcher(JSPFile.MATERIEL_MODIFIER)
					.forward(request, response);
		} else {

			form.testerMateriel(request);

			/* Récupération de la session depuis la requête */
			HttpSession session = request.getSession();
			System.out.print(form.getErreurs());

			List<Categorie> listCat = new ArrayList<Categorie>();
			listCat = new CategorieDAO(DAOFactory.getInstance()).getAll();
			request.setAttribute("LISTE_CATEGORIE", listCat);

			List<Etat> listEtat = new ArrayList<Etat>();
			listEtat = new EtatDAO(DAOFactory.getInstance()).getAll();
			request.setAttribute("LISTE_ETAT", listEtat);

			List<Designation> listDes = new ArrayList<Designation>();
			listDes = new DesignationDAO(DAOFactory.getInstance()).getAll();
			request.setAttribute("LISTE_DESIGNATION", listDes);

			List<Marque> listMarque = new ArrayList<Marque>();
			listMarque = new MarqueDAO(DAOFactory.getInstance()).getAll();
			request.setAttribute("LISTE_MARQUE", listMarque);

			List<Fournisseur> listFourn = new ArrayList<Fournisseur>();
			listFourn = new FournisseurDAO(DAOFactory.getInstance()).getAll();
			request.setAttribute("LISTE_FOURNISSEUR", listFourn);

			if (form.getErreurs().isEmpty()) {
				MaterielCtrl matctrl = new MaterielCtrl();
				Materiel mat = null;
				if (session.getAttribute("sessionObjectMateriel").getClass() == Materiel.class) {
					mat = (Materiel) session
							.getAttribute("sessionObjectMateriel");

					CategorieCtrl catctrl = new CategorieCtrl();
					FournisseurCtrl fournctrl = new FournisseurCtrl();
					DesignationCtrl desctrl = new DesignationCtrl();
					EtatCtrl etatctrl = new EtatCtrl();
					MarqueCtrl marquectrl = new MarqueCtrl();

					mat.setCategorie(catctrl.recupererCategorie(form
							.getCategorie()));
					mat.setValeurAchat(form.getValAch());

					if (form.getDateAch() != null) {
						SimpleDateFormat formatter = new SimpleDateFormat(
								"dd/MM/yyyy");
						try {
							mat.setDateAchat(new java.sql.Date(formatter.parse(
									form.getDateAch()).getTime()));
						} catch (ParseException e) {
							e.printStackTrace();
						}
					}
					mat.setValeurReap(form.getValRea());

					Fournisseur four = fournctrl.recupererFournisseur(form
							.getFournisseur());
					mat.setFournisseur(four);
					mat.setDesignation(desctrl.recupererDesignationCtrl(form
							.getDesignation()));
					mat.setTypeMat(form.getType());
					mat.setEtat(etatctrl.recupererEtat(form.getEtat()));
					mat.setMarque(marquectrl.recupererMarque(form.getMarque()));
					mat.setNumSerie(form.getNumserie());
					mat.setDeplacable(form.getDeplacable());
					mat.setLouable(form.getLouable());
					mat.setObservation(form.getObservation());
					mat.setEtat(etatctrl.recupererEtat(form.getEtat()));

					// matctrl.modifierMateriel(mat);
					session.removeAttribute("sessionObjectMateriel");
					session.removeAttribute("materiel");
					session.removeAttribute("listeEtats");
					session.removeAttribute("listeCat");
					session.removeAttribute("listeFourn");
					session.removeAttribute("listeDes");

					session.setAttribute("materiel", mat);
				} else {
					form.setErreur("Modification", "Problème de session");
				}
			}
			if (form.getErreurs().isEmpty()) {

				response.sendRedirect(request.getContextPath()
						+ "/ValidationModifierMaterielServlet");
			} else {
				form.getErreurs().put("Modification",
						"Erreur lors de la modification");

				request.setAttribute("form", form);
				this.getServletContext()
						.getRequestDispatcher(JSPFile.MATERIEL_MODIFIER)
						.forward(request, response);
			}
		}
	}
}
