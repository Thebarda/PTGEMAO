package fr.gemao.view.ComptabiliteEnseignement;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.gemao.ctrl.cheque.ChequeCtrl;
import fr.gemao.ctrl.cheque.ChequePartenaireCtrl;
import fr.gemao.entity.materiel.ChequeLocation;
import fr.gemao.entity.partenaire.ChequePartenaire;
import fr.gemao.form.util.Form;
import fr.gemao.view.JSPFile;
import fr.gemao.view.Pattern;

@WebServlet(Pattern.COMPTABILITE_AJOUTER_CHEQUES)
public class ajouterChequePartenaire extends HttpServlet{
private static final long serialVersionUID = 1L;
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		this.getServletContext().getRequestDispatcher(JSPFile.COMPTABILITE_AJOUTER_CHEQUES).forward(request,  response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		if (Form.getValeurChamp(request, "montant") != null) {
			String datePaiement = Form.getValeurChamp(request, "datePaiement");
			long numero = Long.parseLong(Form.getValeurChamp(request, "numero"));
			int montant = Integer.parseInt(Form.getValeurChamp(request, "montant"));
			String dateEncaissement = Form.getValeurChamp(request, "dateEncaissement");
			String partenaire = Form.getValeurChamp(request, "partenaire");
			
			ChequePartenaire cheque = new ChequePartenaire(0, numero, montant, datePaiement, dateEncaissement, null, partenaire);
			session.setAttribute("cheque", cheque);
			request.setAttribute("validation", false);
		}else{
			ChequePartenaireCtrl.ajouterCheque((ChequePartenaire) session.getAttribute("cheque"));
			Boolean validation = true;
			request.setAttribute("validation", validation);
		}
		this.getServletContext().getRequestDispatcher(JSPFile.COMPTABILITE_AJOUTER_CHEQUES).forward(request,  response);
	}
}
