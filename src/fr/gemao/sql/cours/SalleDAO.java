package fr.gemao.sql.cours;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import fr.gemao.entity.cours.Discipline;
import fr.gemao.entity.cours.Salle;
import fr.gemao.sql.DAOFactory;
import fr.gemao.sql.IDAO;
import fr.gemao.sql.exception.DAOException;
import fr.gemao.sql.util.DAOUtilitaires;
import fr.gemao.sql.util.NumberUtil;

public class SalleDAO extends IDAO<Salle> {

	public SalleDAO(DAOFactory factory) {
		super(factory);
	}

	@Override
	public Salle create(Salle obj) {
		if (obj == null) {
			throw new NullPointerException("La salle ne doit pas être null");
		}
		Connection connexion = null;
		PreparedStatement requete = null;
		ResultSet result = null;
		Integer id = null;
		String sql = "INSERT INTO salle(nomSalle) " + "VALUES (?);";
		try {
			connexion = factory.getConnection();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion,
					sql, true, obj.getNomSalle());

			int status = requete.executeUpdate();

			if (status == 0) {
				throw new DAOException(
						"Échec de la création de la salle, aucune ligne ajoutée dans la table.");
			}

			result = requete.getGeneratedKeys();
			if (result != null && result.first()) {
				id = result.getInt(1);
				obj.setIdSalle(id);
			}

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			DAOUtilitaires.fermeturesSilencieuses(result, requete, connexion);
		}

		return obj;
	}

	@Override
	public void delete(Salle obj) {
		Objects.requireNonNull(obj, "La salle ne peut etre null");

		Connection connexion = null;
		Statement stat = null;
		
		try {
			connexion = factory.getConnection();
			stat = connexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			stat.execute("DELETE FROM salle WHERE idSalle = " + obj.getIdSalle() + ";");
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			DAOUtilitaires.fermeturesSilencieuses(stat, connexion);
		}
	}

	@Override
	public Salle update(Salle obj) {
		if (obj == null) {
			throw new NullPointerException("La salle ne peut etre null");
		}

		Connection connexion = null;
		Statement stat = null;
		PreparedStatement requete = null;
		try {
			String sql = "UPDATE salle SET nomSalle = ? WHERE idSalle = ?";

			connexion = factory.getConnection();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion, sql, true, obj.getNomSalle(), obj.getIdSalle());

			int status = requete.executeUpdate();

			if (status == 0) {
				obj = null;
				// throw new DAOException("Échec de la création d'un planning,
				// aucune ligne ajoutée dans la table");
			}

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			DAOUtilitaires.fermeturesSilencieuses(stat, connexion);
		}
		return obj;
	}

	@Override
	public Salle get(long id) {
		Salle salle = null;
		Connection connexion = null;
		PreparedStatement requete = null;
		ResultSet result = null;
		String sql = "SELECT * FROM salle WHERE idSalle = ?;";
		try {

			connexion = factory.getConnection();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion,
					sql, false, id);
			result = requete.executeQuery();

			if (result.first()) {
				salle = this.map(result);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			DAOUtilitaires.fermeturesSilencieuses(result, requete, connexion);
		}
		return salle;
	}

	@Override
	public List<Salle> getAll() {
		List<Salle> liste = new ArrayList<>();

		Salle salle = null;
		Connection connexion = null;
		PreparedStatement requete = null;
		ResultSet result = null;
		String sql = "SELECT * FROM salle;";
		try {

			connexion = factory.getConnection();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion,
					sql, false);
			result = requete.executeQuery();

			while (result.next()) {
				salle = this.map(result);
				liste.add(salle);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			DAOUtilitaires.fermeturesSilencieuses(result, requete, connexion);
		}
		return liste;
	}

	/**
	 * Associe une salle à une discipline.
	 * 
	 * @param salle
	 * @param discipline
	 */
	public void addSalleParDiscipline(Salle salle, Discipline discipline) {
		Connection connexion = null;
		PreparedStatement requete = null;
		String sql = "INSERT INTO salleXdiscipline(idSalle, idDiscipline) values ( ?, ?);";
		try {
			connexion = factory.getConnection();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion,
					sql, false, salle.getIdSalle(),
					discipline.getIdDiscipline());
			int status = requete.executeUpdate();
			if (status == 0) {
				throw new DAOException(
						"Échec de la création de l'association salle / discipline, aucune ligne ajoutée dans la table.");
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			DAOUtilitaires.fermeturesSilencieuses(requete, connexion);
		}
	}

	/**
	 * Asscocie une liste de salles à une discipline.
	 * 
	 * @param salles
	 * @param discipline
	 */
	public void addAllSallesParDiscipline(List<Salle> salles,
			Discipline discipline) {
		for (Salle salle : salles) {
			this.addSalleParDiscipline(salle, discipline);
		}
	}

	/**
	 * Return la liste des salles associé à une discipline
	 * 
	 * @param discipline
	 * @return
	 */
	public List<Salle> getAllSalleParDiscipline(Integer idDiscipline) {
		List<Salle> list = new ArrayList<>();
		Salle salle = null;
		Connection connexion = null;
		PreparedStatement requete = null;
		ResultSet result = null;
		String sql = "SELECT * from salle s inner join salleXdiscipline sd on s.idSalle=sd.idSalle WHERE idDiscipline = ?;";
		try {
			connexion = factory.getConnection();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion,
					sql, false, idDiscipline);
			result = requete.executeQuery();

			while (result.next()) {
				salle = this.map(result);
				list.add(salle);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			DAOUtilitaires.fermeturesSilencieuses(result, requete, connexion);
		}
		return list;
	}

	/**
	 * @return
	 */
	public List<String> getAllNomSalle() {
		List<String> liste = new ArrayList<>();

		Salle salle = null;
		Connection connexion = null;
		PreparedStatement requete = null;
		ResultSet result = null;
		String sql = "SELECT idSalle, nomSalle FROM salle;";
		try {

			connexion = factory.getConnection();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion,
					sql, false);
			result = requete.executeQuery();

			while (result.next()) {
				salle = this.map(result);
				liste.add(salle.getNomSalle());
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			DAOUtilitaires.fermeturesSilencieuses(result, requete, connexion);
		}
		return liste;
	}

	@Override
	protected Salle map(ResultSet result) throws SQLException {
		return new Salle(NumberUtil.getResultInteger(result, "idSalle"),
				result.getString("nomSalle"));
	}

}
