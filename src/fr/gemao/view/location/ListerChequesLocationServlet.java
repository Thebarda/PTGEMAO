package fr.gemao.view.location;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.gemao.ctrl.cheque.ChequeCtrl;
import fr.gemao.entity.materiel.ChequeLocation;
import fr.gemao.entity.materiel.Location;
import fr.gemao.view.JSPFile;
import fr.gemao.view.Pattern;

@WebServlet(Pattern.LOCATION_CHEQUE_LISTER)
public class ListerChequesLocationServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
		
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		List<ChequeLocation> cheques = ChequeCtrl.getAll();
		
		request.setAttribute("cheques", cheques);
		this.getServletContext().getRequestDispatcher(JSPFile.LOCATION_CHEQUE_LISTER).forward(request,  response);
	}
}

