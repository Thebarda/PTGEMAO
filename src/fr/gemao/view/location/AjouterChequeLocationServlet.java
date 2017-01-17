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
import fr.gemao.entity.materiel.TypeLocation;
import fr.gemao.form.location.LocationForm;
import fr.gemao.form.util.Form;
import fr.gemao.view.JSPFile;
import fr.gemao.view.Pattern;

@WebServlet(Pattern.LOCATION_CHEQUE_AJOUTER)
public class AjouterChequeLocationServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
		
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		int idLoc = Integer.parseInt(request.getParameter("id"));
		List<Location> locations = LocationCtrl.getAll();
		Location location = null;
		ChequeLocation cheque = null;
		for(Location l : locations){
			if(l.getId()==idLoc){
				location = l;
			}
		}
		
		request.setAttribute("location", location);
		request.setAttribute("cheque", cheque);
		
		this.getServletContext().getRequestDispatcher(JSPFile.LOCATION_CHEQUE_AJOUTER).forward(request,  response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//ChequeForm chequeForm = new ChequeForm(request);
		HttpSession session = request.getSession();
		
		if(Form.getValeurChamp(request, "numeroCheque")!=null){
			
		}
	}
	
}