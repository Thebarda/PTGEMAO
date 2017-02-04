package fr.gemao.view.location;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.gemao.ctrl.cheque.ChequeCtrl;
import fr.gemao.ctrl.location.LocationCtrl;
import fr.gemao.ctrl.materiel.EtatCtrl;
import fr.gemao.entity.materiel.ChequeLocation;
import fr.gemao.entity.materiel.Etat;
import fr.gemao.entity.materiel.Location;
import fr.gemao.view.JSPFile;
import fr.gemao.view.Pattern;

@WebServlet(Pattern.LOCATION_DETAILS)
public class AfficherDetailsLocationServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	private static final String ID = "id_loc";
    private static final String LOCATION = "location";
	
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
			List<Etat> etats = EtatCtrl.getListeEtat();
			
			String typeLoc = location.getType();
			String categorie = location.getMateriel().getCategorie().getLibelleCat();
			String designation = location.getMateriel().getDesignation().getLibelleDesignation();
			String etat = location.getEtatDebut().getLibelleEtat();
			String nomAdh = location.getPersonne().getNom();
			String prenomAdh = location.getPersonne().getPrenom();
			String dateEmprunt = location.getDateEmprunt();
			String dateEcheance = location.getDateEcheance();
			int montant=0;
			Map<String, String> tarifs = LocationCtrl.recupereTarifsLocation();
			if(typeLoc=="Externe"){
				montant = 3*Integer.parseInt(tarifs.get("montantLocationExterne"));
			}else{
				montant = Integer.parseInt(tarifs.get("montantLocationInterne"));
			}
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
			request.setAttribute("montant", montant);
			session.setAttribute("dateRetour", dateRetour);
			request.setAttribute("etats", etats);
			
			/**
			 * Chèques
			 */
			List<ChequeLocation> tmp = ChequeCtrl.getAll();
			List<ChequeLocation> cheques = new ArrayList<>();
			for(ChequeLocation cl : tmp){
				if(cl.getLocation().getId()==idLoc){
					cheques.add(cl);
				}
			}
			int montantPaye=0;
			for(ChequeLocation cl : cheques){
				montantPaye+=cl.getMontantCheque();
			}
			int montantRestantAPaye = montant-montantPaye;
			request.setAttribute("cheques", cheques);
			request.setAttribute("montantRestantAPaye", montantRestantAPaye);
			
			this.getServletContext().getRequestDispatcher( JSPFile.LOCATION_DETAILS ).forward( request, response );
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}
}
