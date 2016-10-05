package fr.gemao.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.gemao.entity.personnel.TypeContrat;
import fr.gemao.sql.exception.DAOException;
import fr.gemao.sql.util.DAOUtilitaires;
import fr.gemao.sql.util.NumberUtil;

public class TypeContratDAO extends IDAO<TypeContrat> {

	public TypeContratDAO(DAOFactory factory) {
		super(factory);
	}

	@Override
	public TypeContrat create(TypeContrat obj) {
		if (obj == null) {
			throw new NullPointerException(
					"Le type de contrat ne doit pas etre null");
		}

		Integer id = 0;
		Connection connexion = null;
		PreparedStatement requete = null;
		ResultSet result = null;
		String sql = "INSERT INTO typeContrat(libelle)"
				+ "VALUES (?);";
		try {
			connexion = factory.getConnection();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion,
					sql, true, obj.getLibelle());
			int status = requete.executeUpdate();

			if (status == 0) {
				throw new DAOException(
						"Échec de la création d'un type de contrat, aucune ligne ajoutée dans la table.");
			}

			result = requete.getGeneratedKeys();
			if (result != null && result.first()) {
				id = result.getInt(1);
				obj.setIdContrat(id);
			}

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			DAOUtilitaires.fermeturesSilencieuses(result, requete, connexion);
		}

		return obj;
	}

	@Override
	public void delete(TypeContrat obj) {
		throw new UnsupportedOperationException("Impossible de supprimer un type de contrat.");
	}

	@Override
	public TypeContrat update(TypeContrat obj) {
		throw new UnsupportedOperationException("Impossible de modifier un type de contrat.");
	}

	@Override
	public TypeContrat get(long id) {
		TypeContrat type = null;
		Connection connexion = null;
		PreparedStatement requete = null;
		ResultSet result = null;
		try {
			String sql = "SELECT * FROM typecontrat WHERE idTypeContrat = ?;";
			connexion = factory.getConnection();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion,
					sql, false, id);
			result = requete.executeQuery();

			if (result.first()) {
				type = this.map(result);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			DAOUtilitaires.fermeturesSilencieuses(result, requete, connexion);
		}

		return type;
	}

	@Override
	public List<TypeContrat> getAll() {
		List<TypeContrat> liste = new ArrayList<>();

		TypeContrat typeContrat = null;
		Connection connexion = null;
		PreparedStatement requete = null;
		ResultSet result = null;
		String sql = "SELECT * FROM typecontrat;";
		try {
			connexion = factory.getConnection();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion,
					sql, false);
			result = requete.executeQuery();

			while (result.next()) {
				typeContrat = this.map(result);
				liste.add(typeContrat);
			}
		} catch (SQLException e1) {
			throw new DAOException(e1);
		} finally {
			DAOUtilitaires.fermeturesSilencieuses(result, requete, connexion);
		}

		return liste;
	}
	
	public TypeContrat exist(TypeContrat typeContrat){
		Connection connexion = null;
		PreparedStatement requete = null;
		ResultSet result = null;
		String sql = "SELECT * from typecontrat where libelle = ?;";
		TypeContrat verif = null;
		try {
			connexion = factory.getConnection();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion,
					sql, false, typeContrat.getLibelle());
			result = requete.executeQuery();

			if (result.first()) {
				verif = this.map(result);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			DAOUtilitaires.fermeturesSilencieuses(result, requete, connexion);
		}

		return verif;
	}

	@Override
	protected TypeContrat map(ResultSet result) throws SQLException {
		return new TypeContrat(NumberUtil.getResultInteger(result,
				"idTypeContrat"), result.getString("libelle"));
	}

}
