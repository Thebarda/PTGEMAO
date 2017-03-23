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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.apache.tomcat.util.http.fileupload.IOUtils;

import fr.gemao.ctrl.archivage.ArchivageCtrl;
import fr.gemao.form.util.Form;
import fr.gemao.util.Zip;
import fr.gemao.view.JSPFile;
import fr.gemao.view.Pattern;

@WebServlet(Pattern.ARCHIVAGE_LISTER)
@MultipartConfig
public class ListerRepsFichiersServlet extends HttpServlet{
	static int UN = 1;
    static int DOUZE = 12;
	private File zip;
	private List<String> reps;
	private Map<String, String> files;
	private String pathActuel;
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		if(request.getParameter("path")!=null){
			pathActuel = request.getParameter("path");
			String pasthTmp = pathActuel.replaceAll("--", "\\\\");
			String[] pathAfficheTmp = pathActuel.split("--");
			String pathAffiche="";
			for(int i=0;i<pathAfficheTmp.length;i++){
				if(!pathAfficheTmp[i].equals("")){
					pathAffiche+=pathAfficheTmp[i]+"\\";
				}
			}
			request.setAttribute("pathAffiche", pathAffiche);
			//test
			if(pasthTmp.equals("Documents\\")||(pasthTmp.equals("Documents"))||(pasthTmp.equals("Documents\\\\"))||pasthTmp.equals("Documents\\\\\\")){
				request.setAttribute("noReturn", true);
			}else{
				String[] tmp = pathActuel.split("--");
				String retour="";
				int moins=tmp.length-1;
				for(int i=0;i<moins;i++){
					if(!tmp[i].equals("")){
						retour+=tmp[i]+"--";
					}
				}
				session.setAttribute("retour", retour);
				request.setAttribute("noReturn", false);
			}

			files = new HashMap<>();
			reps = new ArrayList<>();
			File file = new File(pasthTmp);
			File[] tmp = file.listFiles();
			for(int i=0;i<tmp.length;i++){
				if(tmp[i].isFile()){
					String pathAbsolute = tmp[i].getAbsolutePath();
					files.put(tmp[i].getName(), "file:///"+pathAbsolute.replaceAll("\\\\", "/"));
				}else{
					reps.add(tmp[i].getName());
				}
			}
			Date todaytoday = new Date();
			File directoryToZip = new File("Documents");
			List<File> fileList = new ArrayList<File>();
			Zip zipTmp = new Zip();
			zipTmp.getAllFiles(directoryToZip, fileList);
			zipTmp.writeZipFile(directoryToZip, fileList);
			File documents = new File("Documents.zip");
			documents.renameTo(new File("sauvegarde_"+todaytoday.getDate()+"_"+(todaytoday.getMonth()+1)+"_"+(1900+todaytoday.getYear())+".zip"));
			File zip = new File("sauvegarde_"+todaytoday.getDate()+"_"+(todaytoday.getMonth()+1)+"_"+(1900+todaytoday.getYear())+".zip");
			String pathAbsolute = zip.getAbsolutePath().replaceAll("\\\\", "/");
			String absolutePathZip = "file:///"+pathAbsolute;
			String lastSaveTmp = ArchivageCtrl.getLastSauvegarde();
			if(!lastSaveTmp.equals("")){
				DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				Calendar cal1 = Calendar.getInstance();
				Calendar cal2 = Calendar.getInstance();
				Calendar cal0 = Calendar.getInstance();
				int nbMois=0;
				int nbAnnees=0;
				int nbJours=0;
				Date lastSave = null;
				try {
					lastSave = dateFormat.parse(lastSaveTmp);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				Date today = new Date();
				if(lastSave.equals(today)){
					request.setAttribute("diffDate", "Vous avez sauvegardé aujourd'hui");
				}else{
					cal1.setTime(today);
					cal2.setTime(lastSave);
					while(cal1.before(cal2)){
						cal1.add(GregorianCalendar.MONTH, UN);
						if(cal1.before(cal2)||cal1.equals(cal2)){
							nbMois++;
						}
					}
					nbAnnees = (nbMois/DOUZE);
					nbMois=(nbMois-(nbMois*DOUZE));
					cal0 = Calendar.getInstance();
					cal0.setTime(today);
					cal0.add(GregorianCalendar.YEAR, nbAnnees);
					cal0.add(GregorianCalendar.MONTH, nbMois);
					nbJours = (int) ((cal2.getTimeInMillis()-cal0.getTimeInMillis())/86400000);
					nbJours-=(2*nbJours);
					request.setAttribute("diffDate", "Vous n'avez pas sauvegardé depuis "+nbJours+" jour(s). Pensez à sauvegarder fréquemment.");
				}
			}else{
				request.setAttribute("diffDate", "Vous n'avez effectué aucune sauvegarde");
			}
			request.setAttribute("apz", zip.getAbsolutePath());
			request.setAttribute("reps", reps);
			request.setAttribute("files", files);
			session.setAttribute("lastPath", pathActuel);
			session.setAttribute("path", pathActuel);

		}
		if(request.getParameter("delete")!=null){
			String pathActuel = ""+session.getAttribute("delete");
			File aSuppr = new File(pathActuel);
			System.out.println(aSuppr.getAbsolutePath());
			if(aSuppr.isDirectory()){
				this.supprimerFichiersDansDossier(aSuppr.getAbsolutePath());
			}
			aSuppr.delete();
			request.setAttribute("ajout", "Elément supprimé");
			
		}
		if(request.getParameter("verificationSuppression")!=null){
			String[] split = request.getParameter("verificationSuppression").split("=");
			String pathActuel = split[0];
			String pasthTmp = pathActuel.replaceAll("--", "\\\\");
			File aSuppr = new File(pasthTmp);
			session.setAttribute("delete", aSuppr);
			request.setAttribute("demandeVerifcationSuppresssion", "Voulez vous supprimer l'élément "+aSuppr+" ?");
			String[] tmp = request.getParameter("verificationSuppression").split("--");
			String retour="";
			int moins=tmp.length-1;
			for(int i=0;i<moins;i++){
				if(!tmp[i].equals("")){
					retour+=tmp[i]+"--";
				}
			}
			session.setAttribute("retour", retour);
		}
		if(request.getParameter("sauvegarde")!=null){
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			Date todayTmp = new Date();
			String today="";
			String moisDeb = "";
			String dayDeb = "";
			if((todayTmp.getMonth()+1)<10){
				moisDeb = "0"+(todayTmp.getMonth()+1);
			}else{
				moisDeb=""+(todayTmp.getMonth()+1);
			}
			if(todayTmp.getDate()<10){
				dayDeb = "0"+todayTmp.getDate();
			}else{
				dayDeb=""+todayTmp.getDate();
			}
			today = dayDeb+"/"+moisDeb+"/"+(1900+todayTmp.getYear());
			ArchivageCtrl.addSauvegarde(today);
			request.setAttribute("ajout", "Votre sauvegarde a été téléchargée");
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
			if((dossier.contains("<"))||(dossier.contains("("))||(dossier.contains(")"))||(dossier.contains("#"))||(dossier.contains("/"))||(dossier.contains(">"))){
				request.setAttribute("ajout", "Veuillez éviter les caractères '<', '>', '(', ')', '/', '#' dans le nom de dossier");
			}else{
				File path = new File(pasthTmp+"\\"+dossier);
				if(!path.exists()){
					path.mkdir();
				}
				request.setAttribute("ajout", "Dossier ajouté");
			}
		}
		if(request.getPart("fichier")!=null){
			Part filePart = request.getPart("fichier");
			String verificationNom = request.getPart("fichier").toString();
			if((verificationNom.contains("<"))||(verificationNom.contains("("))||(verificationNom.contains(")"))||(verificationNom.contains("#"))||(verificationNom.contains("/"))||(verificationNom.contains(">"))){
				request.setAttribute("ajout", "Veuillez éviter les caractères '<', '>', '(', ')', '/', '#' dans le nom de fichier");
			}else{
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
		}
		
		this.getServletContext().getRequestDispatcher(JSPFile.ARCHIVAGE_LISTER).forward(request, response);
	}
	
	public void supprimerFichiersDansDossier(String absoluthePathDossier){
		File dossier = new File(absoluthePathDossier);
		String[] contenu = dossier.list();
		for(int i=0;i<contenu.length;i++){
			File fichier = new File(absoluthePathDossier+"\\"+contenu[i]);
			System.out.println(fichier.getAbsolutePath());
			if((fichier.isDirectory())&&(fichier.list().length!=0)){
				this.supprimerFichiersDansDossier(absoluthePathDossier+"\\"+contenu[i]);
			}
			fichier.delete();
		}
	}
}
