package fr.gemao.ctrl;

import fr.gemao.entity.personnel.Personnel;
import fr.gemao.sql.DAOFactory;
import fr.gemao.sql.PersonnelDAO;
import fr.gemao.util.Password;

public class ChangerMotDePasseCtrl {
	private DAOFactory daoFactory;
	private PersonnelDAO personnelDAO;

	public ChangerMotDePasseCtrl() {
		daoFactory = DAOFactory.getInstance();
		personnelDAO = daoFactory.getPersonnelDAO();
	}

	public boolean controlerMotDePasse(String login, String passwd) {
		if (login == null) {
			return false;
		}
		if (passwd == null) {
			return false;
		}

		Personnel personnel = personnelDAO.getLoginParPersonnel(login);

		// Si le login n'a pas été trouvé dans la base de données
		if (personnel == null) {
			return false;
		}

		return Password.compare(passwd, personnel.getPassword());
	}
	
	public boolean changerMotDePasse(String login, String nouveauMotDePasse){
		Personnel personnel = personnelDAO.getLoginParPersonnel(login);
		personnel.setPassword(Password.encrypt(nouveauMotDePasse));
		
		return personnelDAO.update(personnel)!=null;
	}
	
	/**
	 * Remplace le mot de passe contenu adns la base de données par
	 * celui présent dans l'objet Personnel passé en paramètre
	 * @param personnel
	 * @return
	 */
	public boolean changerMotDePasse(Personnel personnel){
		personnel.setPassword(Password.encrypt(personnel.getPassword()));
		return personnelDAO.update(personnel)!=null;
	}
}
