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

import fr.gemao.ctrl.location.LocationCtrl;
import fr.gemao.entity.materiel.ChequeLocation;
import fr.gemao.entity.materiel.Location;
import fr.gemao.view.JSPFile;
import fr.gemao.view.Pattern;

@WebServlet(Pattern.LOCATION_CONTRAT_LISTER)
public class ListerContratsLocationServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
		
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		List<Location> locations = LocationCtrl.getAll();
		
		ChequeLocation cheque1 = new ChequeLocation(locations.get(0), "01/01/2017", 100.0F, 1, "03/01/2017");
		ChequeLocation cheque2 = new ChequeLocation(locations.get(0), "05/01/2017", 200.0F, 2, "07/01/2017");
		List<ChequeLocation> cheques = new ArrayList<>();
		cheques.add(cheque1);
		cheques.add(cheque2);
		
		request.setAttribute("cheques", cheques);
		this.getServletContext().getRequestDispatcher(JSPFile.LOCATION_CONTRAT_LISTER).forward(request,  response);
	}
}

