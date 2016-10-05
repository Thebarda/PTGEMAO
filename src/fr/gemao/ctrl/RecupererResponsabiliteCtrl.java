package fr.gemao.ctrl;

import java.util.List;

import fr.gemao.entity.personnel.Responsabilite;
import fr.gemao.sql.DAOFactory;
import fr.gemao.sql.ResponsabiliteDAO;

public class RecupererResponsabiliteCtrl {
	
	public static Responsabilite recupererResponsabilite(int idResponsabilite){
		DAOFactory factory = DAOFactory.getInstance();
		ResponsabiliteDAO responsabiliteDAO = factory.getResponsabiliteDAO();
		return responsabiliteDAO.get(idResponsabilite);
	}
	
	public static Responsabilite recupererResponsabilite(String libelle){
		DAOFactory factory = DAOFactory.getInstance();
		ResponsabiliteDAO responsabiliteDAO = factory.getResponsabiliteDAO();
		Responsabilite responsabilite = new Responsabilite(null, libelle);
		return responsabiliteDAO.exist(responsabilite);
	}

	public static List<Responsabilite> recupererAllResponsabilite(){
		List<Responsabilite> list;
		ResponsabiliteDAO responsabiliteDAO = DAOFactory.getInstance().getResponsabiliteDAO();
		list = responsabiliteDAO.getAll();
		return list;
	}
}
