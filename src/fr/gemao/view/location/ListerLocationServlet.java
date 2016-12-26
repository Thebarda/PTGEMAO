package fr.gemao.view.location;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.gemao.ctrl.adherent.AdherentCtrl;
import fr.gemao.ctrl.location.LocationCtrl;
import fr.gemao.ctrl.materiel.EtatCtrl;
import fr.gemao.ctrl.materiel.MaterielCtrl;
import fr.gemao.entity.Personne;
import fr.gemao.entity.materiel.Etat;
import fr.gemao.entity.materiel.Location;
import fr.gemao.entity.materiel.Materiel;
import fr.gemao.entity.materiel.Reparation;
import fr.gemao.entity.materiel.TypeLocation;
import fr.gemao.form.util.Form;
import fr.gemao.view.JSPFile;
import fr.gemao.view.Pattern;

@WebServlet(Pattern.LOCATION_LISTER)
public class ListerLocationServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;

	private final String PARAM_LISTE_LOCATIONS = "listeLocations";
	private final static String PARAM_SET_LOCATION = "setLocation";
	private final static String PARAM_YEAR = "year";
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		//Liste locations normale
		List<Location> locations = LocationCtrl.getAll();
		if(locations.isEmpty()){
			String vide = "La liste des locations est vide";
			request.setAttribute("vide", vide);
		}else{
			List<TypeLocation> typeLocations = new ArrayList<>();
			for(Location loc : locations){
				typeLocations.add(new TypeLocation(loc));
			}
			
			//Liste locations triÃ©es pas date d'emprunt
			List<Integer> annee = new ArrayList<>();
			Date date = new Date();
			int year = 1900+date.getYear();
			for(int i=0;i<8;i++){
				annee.add(year);
				year--;
			}
			session.setAttribute("date", annee);
			request.setAttribute("typeLocations", typeLocations);
		}
		
		
		
		this.getServletContext().getRequestDispatcher(JSPFile.LOCATION_LISTER).forward(request, response);
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<Location>locations = new ArrayList<>();
		if(Form.getValeurChamp(request, PARAM_SET_LOCATION)!=null){
			String setLocation = Form.getValeurChamp(request, PARAM_SET_LOCATION);
			if(setLocation.equals("allLocs")){
				//Toutes les locations
				locations = LocationCtrl.getAllAll();
			}else{
				//L'année courante
				Date date = new Date();
				int year = 1900+date.getYear();
				locations = LocationCtrl.getLocsByYear(year);
			}
		}
		
		if(Form.getValeurChamp(request, PARAM_YEAR)!=null){
			//L'année choisie
			String tmpYear = Form.getValeurChamp(request, PARAM_YEAR);
			int year = Integer.parseInt(tmpYear);
			locations = LocationCtrl.getLocsByYear(year);
		}
		//La liste de location est vide ou non
		if(locations.isEmpty()){
			String vide = "La liste des locations est vide";
			request.setAttribute("vide", vide);
		}else{
			List<TypeLocation> typeLocations = new ArrayList<>();
			for(Location loc : locations){
				typeLocations.add(new TypeLocation(loc));
			}
			request.setAttribute("typeLocations", typeLocations);
		}
		this.getServletContext().getRequestDispatcher(JSPFile.LOCATION_LISTER).forward(request, response);
	}
}
