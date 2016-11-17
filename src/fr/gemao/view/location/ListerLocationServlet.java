package fr.gemao.view.location;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.gemao.ctrl.location.LocationCtrl;
import fr.gemao.entity.materiel.Location;
import fr.gemao.view.Pattern;

@WebServlet(Pattern.LOCATION_LISTER)
public class ListerLocationServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;

	private final String PARAM_LISTE_LOCATIONS = "listeLocations";
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<Location> listeLocations = new ArrayList<>();
		try {
			listeLocations = LocationCtrl.getAll();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		request.setAttribute(PARAM_LISTE_LOCATIONS, listeLocations);
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
	}
}
