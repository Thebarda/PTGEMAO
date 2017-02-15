package fr.gemao.view.ComptabiliteEnseignement;

import java.io.IOException;
import java.util.ArrayList;
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
import fr.gemao.ctrl.cheque.ChequePartenaireCtrl;
import fr.gemao.ctrl.partenaire.PartenaireCtrl;
import fr.gemao.entity.Adresse;
import fr.gemao.entity.Commune;
import fr.gemao.entity.partenaire.ChequePartenaire;
import fr.gemao.entity.partenaire.Partenaire;
import fr.gemao.form.adherent.AdherentForm;
import fr.gemao.form.util.Form;
import fr.gemao.sql.CommuneDAO;
import fr.gemao.sql.DAOFactory;
import fr.gemao.view.JSPFile;
import fr.gemao.view.Pattern;
@WebServlet(Pattern.COMPTABILITE_AJOUTER_PARTENAIRE)
public class ajouterPartenaireServlet extends HttpServlet{

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		List<Integer> annees = new ArrayList<>();
		Date date = new Date();
		for(int i=(1900+date.getYear());i>=1998;i--){
			annees.add(i);
		}
		request.setAttribute("annees", annees);
		this.getServletContext().getRequestDispatcher(JSPFile.COMPTABILITE_AJOUTER_PARTENAIRE).forward(request,  response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		AdherentForm adherentForm = new AdherentForm();
		
		if (Form.getValeurChamp(request, "raisonSociale") != null) {
			adherentForm.testerAdherent(request);
			String raisonSociale = Form.getValeurChamp(request, "raisonSociale");
			int montant = Integer.parseInt(Form.getValeurChamp(request, "annee"));
			String taillePage = Form.getValeurChamp(request, "taillePage");
			
			/**
			 * Réupération des données de la commune
			 */
			String com = adherentForm.getNomCommune();
			Integer codePostal = adherentForm.getCodePostal();

			/**
			 * Création de la commune
			 */
			Commune commune = new Commune(null, codePostal, com, false);
			CommuneDAO communeDAO = DAOFactory.getInstance().getCommuneDAO();
			Commune c = communeDAO.existNomCodePostal(commune);
			if (c != null) {
				commune = c;
			}

			/**
			 * Réupération des données de l'adresse
			 */
			String numAdresse = adherentForm.getNumRue();
			String rueAdresse = adherentForm.getNomRue();
			String complAdresse = adherentForm.getInfoCompl();

			/**
			 * Création de l'adresse
			 */
			Adresse adresse = new Adresse(null, commune, numAdresse, rueAdresse, complAdresse);
			
			Partenaire partenaire = new Partenaire(raisonSociale, adresse, montant, taillePage);
			
			session.setAttribute("ajout_adh_commune", commune);
			session.setAttribute("ajout_adh_adresse", adresse);
			session.setAttribute("partenaire", partenaire);
			request.setAttribute("validation", false);
		}else{
			Partenaire partenaire = (Partenaire) session.getAttribute("partenaire");
			CommuneCtrl.ajoutCommune(partenaire.getAdresse().getCommune());
			AdresseCtrl.ajoutAdresse(partenaire.getAdresse());
			PartenaireCtrl.ajouterPartenaire(partenaire);
			Boolean validation = true;
			request.setAttribute("validation", validation);
		}
		this.getServletContext().getRequestDispatcher(JSPFile.COMPTABILITE_AJOUTER_PARTENAIRE).forward(request,  response);
	}
}
