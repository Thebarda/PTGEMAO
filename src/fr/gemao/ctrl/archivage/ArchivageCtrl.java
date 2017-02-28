package fr.gemao.ctrl.archivage;

import fr.gemao.sql.DAOFactory;
import fr.gemao.sql.archivage.SauvegardeDAO;

public class ArchivageCtrl {

	public static void addSauvegarde(String today) {
		SauvegardeDAO sauvegarde = new SauvegardeDAO(DAOFactory.getInstance());
		sauvegarde.addSauvegarde(today);
	}
	
	public static String getLastSauvegarde(){
		SauvegardeDAO sauvegarde = new SauvegardeDAO(DAOFactory.getInstance());
		return sauvegarde.getLastSauvegarde();
	}
}
