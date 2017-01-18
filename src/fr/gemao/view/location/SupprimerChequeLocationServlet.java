package fr.gemao.view.location;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.gemao.ctrl.cheque.ChequeCtrl;
import fr.gemao.entity.materiel.ChequeLocation;
import fr.gemao.view.JSPFile;
import fr.gemao.view.Pattern;

/**
 * Servlet implementation class SupprimerChequeLocationServlet
 */
@WebServlet(Pattern.LOCATION_CHEQUE_SUPPRIMER)
public class SupprimerChequeLocationServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Boolean validation = null;
		long numCheque = Long.parseLong(request.getParameter("numCheque"));
		List<ChequeLocation> cheques = ChequeCtrl.getAll();
		ChequeLocation cheque = null;
		for(ChequeLocation cl : cheques){
			if(cl.getNumCheque()==numCheque){
				cheque = cl;
			}
		}
		
		session.setAttribute("cheque", cheque);
		session.setAttribute("validation", validation);
		
		this.getServletContext().getRequestDispatcher(JSPFile.LOCATION_CHEQUE_SUPPRIMER).forward(request,  response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		ChequeCtrl.supprimerCheque((ChequeLocation) session.getAttribute("cheque"));
		Boolean validation = true;
		session.setAttribute("validation", validation);
		this.getServletContext().getRequestDispatcher(JSPFile.LOCATION_CHEQUE_SUPPRIMER).forward(request,  response);
	}

}
