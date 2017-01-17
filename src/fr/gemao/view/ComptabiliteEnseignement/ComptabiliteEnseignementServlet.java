package fr.gemao.view.ComptabiliteEnseignement;

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
import fr.gemao.ctrl.adherent.FamilleCtrl;
import fr.gemao.ctrl.location.LocationCtrl;
import fr.gemao.ctrl.materiel.EtatCtrl;
import fr.gemao.ctrl.materiel.MaterielCtrl;
import fr.gemao.entity.Personne;
import fr.gemao.entity.adherent.Famille;
import fr.gemao.entity.materiel.Etat;
import fr.gemao.entity.materiel.Location;
import fr.gemao.entity.materiel.Materiel;
import fr.gemao.entity.materiel.Reparation;
import fr.gemao.entity.materiel.TypeLocation;
import fr.gemao.form.util.Form;
import fr.gemao.view.JSPFile;
import fr.gemao.view.Pattern;

@WebServlet(Pattern.COMPTABILITE_ENSEIGNEMENT)
public class ComptabiliteEnseignementServlet extends HttpServlet{
	
	private static final long serialVersionUID = 1L;
	private static final String FAMILLES = "familles";
	private static final String IDFAMILLE = "idFamille";
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		List<Famille> familles = FamilleCtrl.recupererAllFamille();
		request.setAttribute(FAMILLES, familles);
		this.getServletContext().getRequestDispatcher(JSPFile.COMPTABILITE_ENSEIGNEMENT).forward(request, response);
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if(Form.getValeurChamp(request, "famille")!=null){
			int idFamille = Integer.parseInt(Form.getValeurChamp(request, "famille"));
			String tableauFicheComptable = FamilleCtrl.getTableauFicheComptable(idFamille);
			String tableauRecapitulatif = FamilleCtrl.getTableauRecapitulatif(idFamille);
			request.setAttribute("tfc", tableauFicheComptable);
			request.setAttribute("tr", tableauRecapitulatif);
			request.setAttribute(IDFAMILLE, idFamille);
		}
		this.getServletContext().getRequestDispatcher(JSPFile.COMPTABILITE_ENSEIGNEMENT).forward(request, response);
	}
}
