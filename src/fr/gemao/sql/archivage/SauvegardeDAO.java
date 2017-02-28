package fr.gemao.sql.archivage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import fr.gemao.entity.Adresse;
import fr.gemao.sql.DAOFactory;
import fr.gemao.sql.IDAO;
import fr.gemao.sql.exception.DAOException;
import fr.gemao.sql.util.DAOUtilitaires;

public class SauvegardeDAO extends IDAO<Adresse>{

	public SauvegardeDAO(DAOFactory instance) {
		super(instance);
	}

	@Override
	public Adresse create(Adresse obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Adresse obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Adresse update(Adresse obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Adresse get(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Adresse> getAll() throws ParseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Adresse map(ResultSet result) throws SQLException, ParseException {
		// TODO Auto-generated method stub
		return null;
	}

	public void addSauvegarde(String today) {
		Integer id = 0;
		Connection connexion = null;
		PreparedStatement requete = null;
		ResultSet result = null;
		String sql = "INSERT INTO sauvegarde(dateSauvegarde) VALUES (?);";
		try {
			connexion = factory.getConnection();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion,
					sql, true, today);
			int status = requete.executeUpdate();

			if (status == 0) {
				throw new DAOException(
						"Échec de la création d'une sauvegarde, aucune ligne ajoutée dans la table.");
			}


		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			DAOUtilitaires.fermeturesSilencieuses(result, requete, connexion);
		}
	}

	public String getLastSauvegarde() {
		String dateLastSave = "";
		Connection connexion = null;
		PreparedStatement requete = null;
		ResultSet result = null;
		String sql = "SELECT dateSauvegarde, idSauvegarde FROM sauvegarde HAVING idSauvegarde >= ALL (SELECT idSauvegarde from sauvegarde);";
		try {
			connexion = factory.getConnection();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion,
					sql, false);
			result = requete.executeQuery();
			while (result.next()) {
				dateLastSave=result.getString("dateSauvegarde");
			}

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			DAOUtilitaires.fermeturesSilencieuses(result, requete, connexion);
		}
		return dateLastSave;
	}
}
