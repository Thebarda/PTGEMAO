package fr.gemao.view.location;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.gemao.ctrl.location.LocationCtrl;
import fr.gemao.ctrl.materiel.EtatCtrl;
import fr.gemao.ctrl.materiel.MaterielCtrl;
import fr.gemao.entity.materiel.Etat;
import fr.gemao.entity.materiel.Location;
import fr.gemao.entity.materiel.Materiel;
import fr.gemao.entity.materiel.TypeLocation;
import fr.gemao.form.util.Form;
import fr.gemao.view.JSPFile;
import fr.gemao.view.Pattern;

/**
 * Servlet implementation class RetourInstrumentServlet
 */
@WebServlet(Pattern.LOCATION_RETOUR)
public class RetourInstrumentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static final String ERREUR = "erreur";
    private static final String ID = "id_loc";
    private static final String PARAM_ETATFIN = "etatFin";
    private static final String DATERETOUR = "dateRetour";
    private static final String LOCATION = "location";
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		if(request.getParameter("id")==null){
			this.getServletContext().getRequestDispatcher(JSPFile.ERREUR_DROIT).forward(request, response);
		}else{
			int idLoc = Integer.parseInt(request.getParameter("id"));
			List<Location> locations = LocationCtrl.getAll();
			Location location = null;
			for(Location l : locations){
				if(l.getId()==idLoc){
					location = l;
				}
			}
			TypeLocation typeLocation = new TypeLocation(location);
			List<Etat> etats = EtatCtrl.getListeEtat();
			
			String typeLoc = typeLocation.getTypeLocation();
			String categorie = location.getMateriel().getCategorie().getLibelleCat();
			String designation = location.getMateriel().getDesignation().getLibelleDesignation();
			String etat = location.getEtatDebut().getLibelleEtat();
			String nomAdh = location.getPersonne().getNom();
			String prenomAdh = location.getPersonne().getPrenom();
			String dateEmprunt = location.getDateEmprunt();
			String dateEcheance = location.getDateEcheance();
			Date date = new Date();
			String dateRetour = ""+date.getDate()+"/"+(date.getMonth()+1)+"/"+(date.getYear()+1900);
			
			session.setAttribute(ID, idLoc);
			session.setAttribute(LOCATION, location);
			request.setAttribute("typeLocation", typeLoc);
			request.setAttribute("categorie", categorie);
			request.setAttribute("designation", designation);
			request.setAttribute("etat", etat);
			request.setAttribute("nomAdh", nomAdh);
			request.setAttribute("prenomAdh", prenomAdh);
			request.setAttribute("dateEmprunt", dateEmprunt);
			request.setAttribute("dateEcheance", dateEcheance);
			session.setAttribute("dateRetour", dateRetour);
			request.setAttribute("etats", etats);
			this.getServletContext().getRequestDispatcher( JSPFile.LOCATION_RETOUR ).forward( request, response );
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		//Traitement du formulaire
		if(Form.getValeurChamp(request, PARAM_ETATFIN)!= null){
			int idEtatFin = Integer.parseInt(Form.getValeurChamp(request, PARAM_ETATFIN));
			int id = Integer.parseInt(""+session.getAttribute(ID));
			String dateRetour = ""+session.getAttribute(DATERETOUR);
			int etatFin = Integer.parseInt(""+Form.getValeurChamp(request, PARAM_ETATFIN));
			Location loc = (Location) session.getAttribute(LOCATION);
			LocationCtrl.updateRetourLocation(id, dateRetour, etatFin);
			Materiel mat = loc.getMateriel();
			MaterielCtrl.updateEstLouable(Integer.parseInt(""+loc.getMateriel().getIdMateriel()), 1);
			MaterielCtrl.updateEtat(Integer.parseInt(""+loc.getMateriel().getIdMateriel()), etatFin);
		}
		request.setAttribute("validation", "Retour de l'instrument enregistré !");
		this.getServletContext().getRequestDispatcher(JSPFile.LOCATION_RETOUR).forward(request, response);
	}

}
