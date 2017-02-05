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

import fr.gemao.ctrl.cheque.ChequeCtrl;
import fr.gemao.ctrl.cheque.ChequePartenaireCtrl;
import fr.gemao.entity.materiel.ChequeLocation;
import fr.gemao.entity.partenaire.ChequePartenaire;
import fr.gemao.form.util.Form;
import fr.gemao.view.JSPFile;
import fr.gemao.view.Pattern;

@WebServlet(Pattern.COMPTABILITE_LISTER_CHEQUES)
public class ListerChequePartenaireServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		List<ChequePartenaire> cheques = ChequePartenaireCtrl.getAll();
		
		List<Integer> annees = new ArrayList<>();
		List<String> mois = new ArrayList<>();
		Date date = new Date();
		int annee = 1900+date.getYear();
		while(annee>=1998){
			annees.add(annee);
			annee--;
		}
		mois.add("01");
		mois.add("02");
		mois.add("03");
		mois.add("04");
		mois.add("05");
		mois.add("06");
		mois.add("07");
		mois.add("08");
		mois.add("09");
		mois.add("10");
		mois.add("11");
		mois.add("12");
		
		session.setAttribute("lesAnnees", annees);
		session.setAttribute("lesMois", mois);
		
		
		request.setAttribute("cheques", cheques);
		this.getServletContext().getRequestDispatcher(JSPFile.COMPTABILIT_LISTER_CHEQUES).forward(request,  response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (Form.getValeurChamp(request, "year") != null && Form.getValeurChamp(request, "month") != null) {
			HttpSession session = request.getSession();
			String year = Form.getValeurChamp(request, "year");
			String month = Form.getValeurChamp(request, "month");
			
			List<ChequePartenaire> cheques = new ArrayList<>();
			
			cheques = ChequePartenaireCtrl.getByMonthYear(month, year);
			
			request.setAttribute("chequesParMoisAnnee", cheques);
			
			
		}else if(Form.getValeurChamp(request, "dateEffective")!=null){
			String[] numCheques = request.getParameterValues("numCheques");
			String[] datesEffective = request.getParameterValues("dateEffective");
			
			for(int i=0;i<numCheques.length;i++){
				Long numCheque = Long.parseLong(numCheques[i]);
				String dateEffective = datesEffective[i];
				ChequePartenaireCtrl.addDEEByNumCheque(dateEffective, numCheque);
			}
			List<ChequePartenaire> cheques = ChequePartenaireCtrl.getAll();
			request.setAttribute("cheques", cheques);	
		}
		this.getServletContext().getRequestDispatcher(JSPFile.COMPTABILIT_LISTER_CHEQUES).forward(request,  response);
	}
}
