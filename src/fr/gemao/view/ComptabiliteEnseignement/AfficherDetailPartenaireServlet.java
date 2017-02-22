package fr.gemao.view.ComptabiliteEnseignement;

import java.io.IOException;
import java.util.ArrayList;
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

@WebServlet(Pattern.COMPTABILITE_DETAILS_PARTENAIRE)
public class AfficherDetailPartenaireServlet extends HttpServlet{

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		if((request.getParameter("id")!=null)||(!request.getParameter("id").equals(""))){
			int idPartenaire = Integer.parseInt((String) request.getParameter("id"));
			Partenaire partenaire = PartenaireCtrl.getPartenaireById(idPartenaire);
			partenaire.setAnneeDernierVersement(ChequePartenaireCtrl.getMaxYearDatePaiement(partenaire.getIdPartenaire()));
			List<ChequePartenaire> cheques = ChequePartenaireCtrl.getChequesByIdPartenaire(idPartenaire);
			int montantTotal = 0;
			for(ChequePartenaire cheque : cheques){
				montantTotal+=cheque.getMontant();
			}
			
			session.setAttribute("idPartenaire", idPartenaire);
			request.setAttribute("partenaire", partenaire);
			request.setAttribute("cheques", cheques);
			request.setAttribute("montantTotal", montantTotal);
			this.getServletContext().getRequestDispatcher(JSPFile.COMPTABILITE_DETAILS_PARTENAIRE).forward(request,  response);
		}else{
			this.getServletContext().getRequestDispatcher(JSPFile.COMPTABILITE_LISTER_PARTENAIRES).forward(request,  response);
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(Form.getValeurChamp(request, "taillePage")!=null){
			String taillePage = Form.getValeurChamp(request, "taillePage");
			if((taillePage!=null)||(taillePage.equals(""))){
				HttpSession session = request.getSession();
				PartenaireCtrl.updateTaillePage(Integer.parseInt(""+session.getAttribute("idPartenaire")), taillePage);
				
				
				Partenaire partenaire = PartenaireCtrl.getPartenaireById(Integer.parseInt(""+session.getAttribute("idPartenaire")));
				partenaire.setAnneeDernierVersement(ChequePartenaireCtrl.getMaxYearDatePaiement(partenaire.getIdPartenaire()));
				List<ChequePartenaire> cheques = ChequePartenaireCtrl.getChequesByIdPartenaire(Integer.parseInt(""+session.getAttribute("idPartenaire")));
				int montantTotal = 0;
				for(ChequePartenaire cheque : cheques){
					montantTotal+=cheque.getMontant();
				}
				request.setAttribute("partenaire", partenaire);
				request.setAttribute("cheques", cheques);
				request.setAttribute("montantTotal", montantTotal);
			}else{
				request.setAttribute("erreur", "Veuillez saisir une valeur pour la taille de la page");
			}
		}else{
			request.setAttribute("erreur", "Veuillez saisir une valeur pour la taille de la page");
		}
		this.getServletContext().getRequestDispatcher(JSPFile.COMPTABILITE_DETAILS_PARTENAIRE).forward(request,  response);
	}
}
