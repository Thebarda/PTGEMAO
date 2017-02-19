package fr.gemao.view.archivage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.apache.tomcat.util.http.fileupload.IOUtils;

import fr.gemao.form.util.Form;
import fr.gemao.view.JSPFile;
import fr.gemao.view.Pattern;

@WebServlet(Pattern.ARCHIVAGE_LISTER)
@MultipartConfig
public class ListerRepsFichiersServlet extends HttpServlet{
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if(request.getParameter("path")!=null){
			HttpSession session = request.getSession();
			String pathActuel = request.getParameter("path");
			String pasthTmp = pathActuel.replaceAll("--", "\\\\");
			if(pasthTmp.equals("Documents")){
				request.setAttribute("noReturn", true);
			}else{
				String[] tmp = pasthTmp.split("\\\\");
				String retour = (String) session.getAttribute("lastPath");
				session.setAttribute("retour", retour);
				request.setAttribute("noReturn", false);
			}
			
			File file = new File(pasthTmp);
			String[] tmp = file.list();
			List<String> files = new ArrayList<>();
			List<String> reps = new ArrayList<>();
			for(int i=0;i<tmp.length;i++){
				File f = new File(pasthTmp+"\\"+tmp[i]);
				if(f.isFile()){
					files.add(tmp[i]);
				}else{
					reps.add(tmp[i]);
				}
			}
			request.setAttribute("reps", reps);
			request.setAttribute("files", files);
			session.setAttribute("lastPath", pathActuel);
			session.setAttribute("path", pathActuel);
		}
		if(request.getParameter("delete")!=null){
			String pathActuel = request.getParameter("delete");
			String pasthTmp = pathActuel.replaceAll("--", "\\\\");
			File aSuppr = new File(pasthTmp);
			aSuppr.delete();
		}
		this.getServletContext().getRequestDispatcher(JSPFile.ARCHIVAGE_LISTER).forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		if(request.getParameter("dossier")!=null){
			String pathActuel = request.getParameter("path");
			String pasthTmp = pathActuel.replaceAll("--", "\\\\");
			String dossier = request.getParameter("dossier");
			File path = new File(pasthTmp+"\\"+dossier);
			if(!path.exists()){
				path.mkdir();
			}
			request.setAttribute("ajout", "Dossier ajouté");
		}
		if(request.getPart("fichier")!=null){
			Part filePart = request.getPart("fichier");
			String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
			InputStream fileContent = filePart.getInputStream();
			String pathActuel = request.getParameter("path");
			String pasthTmp = pathActuel.replaceAll("--", "\\\\");
			File file = new File(pasthTmp+"\\"+fileName);
			OutputStream destination = new FileOutputStream(file);
			IOUtils.copy(fileContent, destination);
			destination.close();
			request.setAttribute("ajout", "Fichier ajouté");
		}
		this.getServletContext().getRequestDispatcher(JSPFile.ARCHIVAGE_LISTER).forward(request, response);
	}
}
