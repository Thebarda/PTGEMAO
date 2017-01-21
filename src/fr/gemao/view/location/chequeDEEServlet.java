package fr.gemao.view.location;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.gemao.ctrl.cheque.ChequeCtrl;
import fr.gemao.entity.materiel.ChequeLocation;
import fr.gemao.form.util.Form;
import fr.gemao.view.JSPFile;
import fr.gemao.view.Pattern;

@WebServlet(Pattern.LOCATION_CHEQUE_DEE)
public class chequeDEEServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		long idCheque = Integer.parseInt(request.getParameter("id"));
		HttpSession session = request.getSession();
		session.setAttribute("idCheque", idCheque);
		ChequeLocation cheque = ChequeCtrl.getByNumCheque(idCheque);
		request.setAttribute("cheque", cheque);
		this.getServletContext().getRequestDispatcher(JSPFile.LOCATION_CHEQUE_DEE).forward(request,  response);
	}
		
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();
		String DEE = Form.getValeurChamp(request, "DEE");
		long numCheque = Long.parseLong(""+session.getAttribute("idCheque"));
		ChequeCtrl.addDEEByNumCheque(DEE, numCheque);
		
		session.removeAttribute("idCheque");
		request.setAttribute("resultat", 1);

		this.getServletContext().getRequestDispatcher(JSPFile.LOCATION_CHEQUE_DEE).forward(request,  response);
	}
}
