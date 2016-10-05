package fr.gemao.ctrl;

import fr.gemao.entity.personnel.Personnel;
import fr.gemao.sql.DAOFactory;
import fr.gemao.sql.PersonnelDAO;
import fr.gemao.util.Password;

public class ConnexionCtrl {

	private DAOFactory daoFactory;
	private PersonnelDAO personnelDAO;

	public ConnexionCtrl() {
		daoFactory = DAOFactory.getInstance();
		personnelDAO = daoFactory.getPersonnelDAO();
	}

	public Personnel controlerPersonnel(String login, String passwd) {
		if (login == null) {
			throw new IllegalArgumentException("Le login ne peut être null");
		}
		if (passwd == null) {
			throw new IllegalArgumentException("Le mot de passe ne peut être null");
		}

		Personnel personnel = personnelDAO.getLoginParPersonnel(login);

		if (personnel == null) {
			throw new IllegalArgumentException("Le login n'existe pas");
		}

		final String motDePassePersonnel = personnel.getPassword();

		boolean passwdIdentique = Password.compare(passwd, motDePassePersonnel);

		if (!passwdIdentique) {
			throw new IllegalArgumentException(
					"Mot de passe invalide...");
		}

		return personnel;
	}
}
