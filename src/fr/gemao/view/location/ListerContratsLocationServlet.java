package fr.gemao.view.location;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.gemao.ctrl.location.LocationCtrl;
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
		this.getServletContext().getRequestDispatcher(JSPFile.LOCATION_CONTRAT_LISTER).forward(request,  response);
	}
}

