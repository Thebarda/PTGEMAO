package fr.gemao.view.materiel;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.gemao.ctrl.materiel.MaterielCtrl;
import fr.gemao.entity.materiel.Materiel;
import fr.gemao.view.JSPFile;
import fr.gemao.view.Pattern;

@WebServlet(Pattern.MATERIEL_LISTER)
public class ListeMaterielServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
		
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		String param = request.getParameter("modifOk");
		if(param != null){
			int code = Integer.parseInt(param);
			if(code == 0){
				request.setAttribute("message", "Le materiel a bien ete modifié");
			}
		}
		String param2 = request.getParameter("ajoutOk");
		if(param2 != null){
			int code = Integer.parseInt(param2);
			if(code == 0){
				request.setAttribute("message", "Le materiel a bien ete ajoute");
			}
		}
		MaterielCtrl materielCtrl = new MaterielCtrl();
		List<Materiel> materiels = materielCtrl.recupererTousMateriels();
		
		request.setAttribute("listeMateriels", materiels);
		this.getServletContext().getRequestDispatcher(JSPFile.MATERIEL_LISTER).forward(request, response);
	}
}
