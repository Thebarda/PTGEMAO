package fr.gemao.listeners;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.jolbox.bonecp.BoneCP;

import fr.gemao.ctrl.administration.ProfilsCtrl;
import fr.gemao.sql.DAOFactory;
import fr.gemao.sql.exception.DAOConfigurationException;
import fr.gemao.sql.exception.DAOException;

@WebListener
public class InitialisationDaoFactory implements ServletContextListener {
	public static final String ATT_DAO_FACTORY = "daofactory";
	public static final String ATT_MSG_ERROR = "errordao";

	private DAOFactory daoFactory;

	@Override
	public void contextInitialized(ServletContextEvent event) {
		/* Récupération du ServletContext lors du chargement de l'application */
		ServletContext servletContext = event.getServletContext();
		try{
			/* Instanciation de notre DAOFactory */
			this.daoFactory = DAOFactory.getInstance();
			/* Enregistrement dans un attribut ayant pour portée toute l'application */
			servletContext.setAttribute(ATT_DAO_FACTORY, this.daoFactory);
		} catch(DAOConfigurationException dCe){
			System.out.println("Erreur de configuration : " + dCe.getMessage());
			dCe.printStackTrace();
			servletContext.setAttribute(InitialisationDaoFactory.ATT_MSG_ERROR, "Erreur de configuration : " + dCe.getMessage());
		}
		
		if(this.daoFactory != null){
			try {
				/* Chargement des profils */
				ProfilsCtrl profilsCtrl = new ProfilsCtrl();
				profilsCtrl.loadProfil();
			} catch (DAOException e) {
				System.out.println("Erreur dao lors du chargement des profils : " + e.getMessage());
			}catch(NullPointerException nPe){
				System.out.println("Erreur, le dao n'est pas chargé, impossible de lire les profils : " + nPe.getMessage());
			}
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		try {
			BoneCP connectionPool = DAOFactory.getConnectionPool();
			System.out.println("contextDestroyed....");
			if (connectionPool != null) {
				connectionPool.shutdown();
				connectionPool.close();
				System.out.println("contextDestroyed.....Connection Pooling shut downed!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}