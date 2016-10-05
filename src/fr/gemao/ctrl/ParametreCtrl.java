package fr.gemao.ctrl;

import java.util.Calendar;

import fr.gemao.entity.Parametre;
import fr.gemao.sql.DAOFactory;
import fr.gemao.sql.ParametreDAO;

public class ParametreCtrl {

	private DAOFactory daoFactory;
	private ParametreDAO parametreDAO;

	public ParametreCtrl() {
		daoFactory = DAOFactory.getInstance();
		parametreDAO = daoFactory.getParametreDAO();
	}

	public void controlerParametre(Parametre parametre) {

		Parametre param = parametreDAO.getLast();

		if (!parametre.equals(param)) {
			parametre.setDateModif(Calendar.getInstance().getTime());
			parametreDAO.create(parametre);
		} else {
			throw new IllegalArgumentException(
					"Les paramétres n'ont pas été modifiés.");
		}

	}
	
	public Parametre getLast(){
		return parametreDAO.getLast();
	}
	
	public String conversionDeSqlVersAffichage(float valeur){
		
		return Float.toString(valeur).replace('.', ',');
	}
}
