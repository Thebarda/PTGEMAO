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
import fr.gemao.ctrl.location.LocationCtrl;
import fr.gemao.entity.materiel.ChequeLocation;
import fr.gemao.entity.materiel.Location;
import fr.gemao.form.cheque.ChequeForm;
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
		
		session.setAttribute("location", location);
		session.setAttribute("cheque", cheque);
		
		this.getServletContext().getRequestDispatcher(JSPFile.LOCATION_CHEQUE_AJOUTER).forward(request,  response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (Form.getValeurChamp(request, "montant") != null) {
			HttpSession session = request.getSession();
			
			Boolean validation = null;
			
			ChequeForm chequeForm = new ChequeForm(request);
			
			chequeForm.testerCheque(request);
			
			Location location = (Location) session.getAttribute("location");
			String datePaiement = chequeForm.getdatePaiement();
			String montantCheque = chequeForm.getmontantCheque();
			String numeroCheque = chequeForm.getnumeroCheque();
			String dateEncaissement = chequeForm.getdateEncaissement();
			
			ChequeLocation cheque = new ChequeLocation(location, datePaiement, Float.parseFloat(montantCheque), Long.parseLong(numeroCheque), dateEncaissement, null);
			
			String identiteLocataire = cheque.getLocation().getPersonne().getNom() + " " + cheque.getLocation().getPersonne().getPrenom();
			
			session.setAttribute("identiteLocataire", identiteLocataire);
			session.setAttribute("cheque", cheque);
			session.setAttribute("validation", validation);
			
			this.getServletContext().getRequestDispatcher(JSPFile.LOCATION_CHEQUE_AJOUTER).forward(request,  response);
		}
		else {
			HttpSession session = request.getSession();
			ChequeCtrl.ajouterCheque((ChequeLocation) session.getAttribute("cheque"));
			Boolean validation = true;
			session.setAttribute("validation", validation);
			this.getServletContext().getRequestDispatcher(JSPFile.LOCATION_CHEQUE_AJOUTER).forward(request,  response);
		}
	}
	
}