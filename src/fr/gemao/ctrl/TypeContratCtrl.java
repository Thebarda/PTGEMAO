package fr.gemao.ctrl;

import fr.gemao.entity.personnel.TypeContrat;
import fr.gemao.sql.DAOFactory;
import fr.gemao.sql.TypeContratDAO;

public class TypeContratCtrl {

	public TypeContrat recupererTypeContrat(TypeContrat typeContrat){
		DAOFactory co = DAOFactory.getInstance();
		TypeContratDAO typeContratDAO = co.getTypeContratDAO();
		
		return typeContratDAO.exist(typeContrat);
	}
	
	public TypeContrat recupererTypeContrat(Integer idType){
		DAOFactory co = DAOFactory.getInstance();
		TypeContratDAO typeContratDAO = co.getTypeContratDAO();
		
		return typeContratDAO.get(idType);
	}
}
