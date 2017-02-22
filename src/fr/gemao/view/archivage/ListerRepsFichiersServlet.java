package fr.gemao.view.archivage;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.regex.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
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
import fr.gemao.util.Zip;
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
			//test
			if(pasthTmp.equals("Documents\\")||(pasthTmp.equals("Documents"))||(pasthTmp.equals("Documents\\\\"))||pasthTmp.equals("Documents\\\\\\")){
				request.setAttribute("noReturn", true);
			}else{
				String[] tmp = pathActuel.split("--");
				String retour="";
				for(int i=0;i<(tmp.length-1);i++){
					retour+=tmp[i]+"--";
				}
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
			
			byte data[] = new byte[2048];
			FileOutputStream dest = new FileOutputStream("Documents.zip");
			BufferedOutputStream buff = new BufferedOutputStream(dest);
			ZipOutputStream out = new ZipOutputStream(buff);
			out.setMethod(ZipOutputStream.DEFLATED);
			out.setLevel(9);
			for(int i=0;i<tmp.length;i++){
				File f = new File(pasthTmp+"\\"+tmp[i]);
				if(f.isFile()){
					FileInputStream fi = new FileInputStream(f);
					BufferedInputStream buffi = new BufferedInputStream(fi, 2048);
					ZipEntry entry = new ZipEntry(tmp[i]);
					out.putNextEntry(entry);
					int count;
					while((count = buffi.read(data, 0, 2048))!=-1){
						out.write(data, 0, count);
					}
					out.closeEntry();
					buffi.close();
				}else{
					File f2 = new File(pasthTmp+"\\"+tmp[i]);
					this.addFolder(out, tmp[i], f2.getParentFile().getName());
				}
			}
			out.close();
			
			File zip = new File("Documents.zip");
			String absolutePathZip = zip.getAbsolutePath();
			System.out.println(absolutePathZip);
			request.setAttribute("apz", absolutePathZip);
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
			request.setAttribute("ajout", "Elément supprimé");
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
	
	private static void addFolder(ZipOutputStream out, String folder, String baseFolder) throws IOException{
		File f = new File(baseFolder+"\\"+folder);
		System.out.println(f.getAbsolutePath());
		if(f.exists()){
			System.out.println("existe");
			ZipEntry entry = new ZipEntry(folder);
			out.putNextEntry(entry);
			File f2[] = f.listFiles();
			for(int j=0;j<f2.length;j++){
				System.out.println(f2[j].getAbsolutePath());
				addFolder(out, f2[j].getAbsolutePath(), baseFolder);
			}
		}
	}
}
