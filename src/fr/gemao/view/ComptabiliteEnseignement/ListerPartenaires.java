package fr.gemao.view.ComptabiliteEnseignement;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.gemao.ctrl.cheque.ChequePartenaireCtrl;
import fr.gemao.ctrl.partenaire.PartenaireCtrl;
import fr.gemao.entity.partenaire.ChequePartenaire;
import fr.gemao.entity.partenaire.Partenaire;
import fr.gemao.view.JSPFile;
import fr.gemao.view.Pattern;

@WebServlet(Pattern.COMPTABILITE_LISTER_PARTENAIRES)
public class ListerPartenaires extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		List<Partenaire> tmppartenaires = PartenaireCtrl.getAll();
		List<Partenaire> partenaires = new ArrayList<>();
		for(Partenaire partenaire : tmppartenaires){
			partenaire.setAnneeDernierVersement(ChequePartenaireCtrl.getMaxYearDatePaiement(partenaire.getIdPartenaire()));
			partenaires.add(partenaire);
		}
		//Collections.sort(partenaires);
		request.setAttribute("partenaires", partenaires);
		this.getServletContext().getRequestDispatcher(JSPFile.COMPTABILITE_LISTER_PARTENAIRES).forward(request,  response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.getServletContext().getRequestDispatcher(JSPFile.COMPTABILITE_LISTER_PARTENAIRES).forward(request,  response);
	}
}
