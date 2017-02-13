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

import fr.gemao.ctrl.cheque.ChequePartenaireCtrl;
import fr.gemao.ctrl.partenaire.PartenaireCtrl;
import fr.gemao.entity.partenaire.ChequePartenaire;
import fr.gemao.entity.partenaire.Partenaire;
import fr.gemao.form.util.Form;
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
		if (Form.getValeurChamp(request, "raisonSociale") != null) {
			String raisonSociale = Form.getValeurChamp(request, "raisonSociale");
			String adresse = Form.getValeurChamp(request, "adresse");
			int montant = Integer.parseInt(Form.getValeurChamp(request, "annee"));
			String taillePage = Form.getValeurChamp(request, "taillePage");
			
			Partenaire partenaire = new Partenaire(raisonSociale, adresse, montant, taillePage);
			session.setAttribute("partenaire", partenaire);
			request.setAttribute("validation", false);
		}else{
			PartenaireCtrl.ajouterPartenaire((Partenaire) session.getAttribute("partenaire"));
			Boolean validation = true;
			request.setAttribute("validation", validation);
		}
		this.getServletContext().getRequestDispatcher(JSPFile.COMPTABILITE_AJOUTER_PARTENAIRE).forward(request,  response);
	}
}
