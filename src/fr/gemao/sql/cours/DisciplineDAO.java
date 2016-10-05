package fr.gemao.sql.cours;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import fr.gemao.entity.cours.Discipline;
import fr.gemao.sql.DAOFactory;
import fr.gemao.sql.IDAO;
import fr.gemao.sql.exception.DAOException;
import fr.gemao.sql.util.DAOUtilitaires;
import fr.gemao.sql.util.NumberUtil;

public class DisciplineDAO extends IDAO<Discipline> {

	public DisciplineDAO(DAOFactory factory) {
		super(factory);
	}

	@Override
	public Discipline create(Discipline obj) {
		if (obj == null) {
			throw new NullPointerException("La discipline ne doit pas être null");
		}
		Connection connexion = null;
		PreparedStatement requete = null;
		ResultSet result = null;
		Integer id = null;
		String sql = "INSERT INTO discipline(idMatiere, idNiveau) " + "VALUES (?, ?);";

		Integer idNiveau = null;
		if (obj.getNiveau() != null) {
			idNiveau = obj.getNiveau().getIdNiveau();
		}

		Integer idMatiere = null;
		if (obj.getMatiere() != null) {
			idMatiere = obj.getMatiere().getIdMatiere();
		}
		try {
			connexion = factory.getConnection();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion, sql, true, idMatiere, idNiveau);

			int status = requete.executeUpdate();

			if (status == 0) {
				throw new DAOException("Échec de la création de la discipline, aucune ligne ajoutée dans la table.");
			}

			result = requete.getGeneratedKeys();
			if (result != null && result.first()) {
				id = result.getInt(1);
				obj.setIdDiscipline(id);
			}

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			DAOUtilitaires.fermeturesSilencieuses(result, requete, connexion);
		}

		return obj;
	}

	@Override
	public void delete(Discipline obj) {
		// throw new UnsupportedOperationException("Méthode non implémentée.");
		if (obj == null) {
			throw new NullPointerException("La discipline ne peut etre null");
		}

		Connection connexion = null;
		Statement stat = null;
		try {
			connexion = factory.getConnection();
			stat = connexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			stat.execute("DELETE FROM discipline WHERE idDiscipline = " + obj.getIdDiscipline() + ";");
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			DAOUtilitaires.fermeturesSilencieuses(stat, connexion);
		}
	}

	@Override
	public Discipline update(Discipline obj) {
		if (obj == null) {
			throw new NullPointerException("La discipline ne doit pas être null");
		}

		Connection connexion = null;
		PreparedStatement requete = null;
		ResultSet result = null;
		String sql = "UPDATE discipline SET idMatiere = ?, idNiveau = ? where idDiscipline = ?";
		try {
			connexion = factory.getConnection();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion, sql, false,
					obj.getMatiere().getIdMatiere(), obj.getNiveau().getIdNiveau(), obj.getIdDiscipline());
			requete.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			DAOUtilitaires.fermeturesSilencieuses(result, requete, connexion);
		}

		return obj;
	}

	@Override
	public Discipline get(long id) {
		Discipline discipline = null;
		Connection connexion = null;
		PreparedStatement requete = null;
		ResultSet result = null;
		String sql = "SELECT * FROM discipline WHERE idDiscipline = ?;";
		try {

			connexion = factory.getConnection();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion, sql, false, id);
			result = requete.executeQuery();

			if (result.first()) {
				discipline = this.map(result);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			DAOUtilitaires.fermeturesSilencieuses(result, requete, connexion);
		}
		return discipline;
	}

	@Override
	public List<Discipline> getAll() {
		List<Discipline> liste = new ArrayList<>();

		Discipline discipline = null;
		Connection connexion = null;
		PreparedStatement requete = null;
		ResultSet result = null;
		String sql = "SELECT * FROM discipline;";
		try {

			connexion = factory.getConnection();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion, sql, false);
			result = requete.executeQuery();

			while (result.next()) {
				discipline = this.map(result);
				liste.add(discipline);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			DAOUtilitaires.fermeturesSilencieuses(result, requete, connexion);
		}

		return liste;
	}

	@Deprecated
	public Discipline get(String nom) {
		Discipline discipline = null;
		Connection connexion = null;
		PreparedStatement requete = null;
		ResultSet result = null;
		String sql = "SELECT * FROM discipline WHERE nom = ?;";
		try {

			connexion = factory.getConnection();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion, sql, false, nom);
			result = requete.executeQuery();

			if (result.first()) {
				discipline = this.map(result);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			DAOUtilitaires.fermeturesSilencieuses(result, requete, connexion);
		}
		return discipline;
	}

	/**
	 * Retourne toutes les discipline association à l'adhérent.
	 * 
	 * @param idAdherent
	 * @return
	 */
	public List<Discipline> getDisciplineParAdherent(long idAdherent) {
		List<Discipline> list = new ArrayList<>();
		Discipline discipline = null;
		Connection connexion = null;
		PreparedStatement requete = null;
		ResultSet result = null;
		String sql = "SELECT * from discipline d inner join suit s on d.idDiscipline=s.idDiscipline WHERE idAdherent = ?;";
		try {
			connexion = factory.getConnection();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion, sql, false, idAdherent);
			result = requete.executeQuery();

			while (result.next()) {
				discipline = this.map(result);
				list.add(discipline);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			DAOUtilitaires.fermeturesSilencieuses(result, requete, connexion);
		}
		return list;
	}

	/**
	 * Associe une discipline et un adhérent.
	 * 
	 * @param idDiscipline
	 * @param idAdherent
	 */
	public void addDiscplineParAdherent(int idDiscipline, long idAdherent) {
		Connection connexion = null;
		PreparedStatement requete = null;
		String sql = "INSERT INTO suit(idAdherent, idDiscipline) values ( ?, ?);";
		try {
			connexion = factory.getConnection();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion, sql, false, idAdherent, idDiscipline);
			int status = requete.executeUpdate();
			if (status == 0) {
				throw new DAOException("Échec de la création de suit, aucune ligne ajoutée dans la table.");
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			DAOUtilitaires.fermeturesSilencieuses(requete, connexion);
		}
	}

	public List<Discipline> getDisciplineParPersonnel(long idPersonnel) {
		List<Discipline> list = new ArrayList<>();
		Discipline discipline = null;
		Connection connexion = null;
		PreparedStatement requete = null;
		ResultSet result = null;
		String sql = "SELECT * from discipline d inner join personnelXdiscipline s on d.idDiscipline=s.idDiscipline WHERE idPersonne = ?;";
		try {
			connexion = factory.getConnection();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion, sql, false, idPersonnel);
			result = requete.executeQuery();

			while (result.next()) {
				discipline = this.map(result);
				list.add(discipline);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			DAOUtilitaires.fermeturesSilencieuses(result, requete, connexion);
		}
		return list;
	}

	public void addDiscplineParPersonnel(int idDiscipline, long idPersonnel) {
		Connection connexion = null;
		PreparedStatement requete = null;
		String sql = "INSERT INTO personnelXdiscipline(idPersonne, idDiscipline) values ( ?, ?);";
		try {
			connexion = factory.getConnection();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion, sql, false, idPersonnel, idDiscipline);
			int status = requete.executeUpdate();
			if (status == 0) {
				throw new DAOException(
						"Échec de la création de personnelXdiscipline, aucune ligne ajoutée dans la table.");
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			DAOUtilitaires.fermeturesSilencieuses(requete, connexion);
		}
	}

	public void addAllDisciplineParPersonnel(List<Discipline> listDiscipline, long idPersonnel) {
		for (Discipline d : listDiscipline) {
			List<Discipline> dejaInscrit = this.getDisciplineParPersonnel(idPersonnel);
			if (!dejaInscrit.contains(d))
				addDiscplineParPersonnel(d.getIdDiscipline(), idPersonnel);
		}
	}

	/**
	 * Supprime l'association Discipline / adhérent
	 * 
	 * @param idDiscipline
	 * @param adherent
	 */
	public void deleteDisciplineParAdherent(int idDiscipline, long adherent) {
		Statement stat = null;
		Connection connect = null;
		try {
			connect = factory.getConnection();
			stat = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			stat.execute(
					"DELETE FROM suit WHERE idAdherent = " + adherent + " and idDiscipline = " + idDiscipline + ";");
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			DAOUtilitaires.fermeturesSilencieuses(stat, connect);
		}
	}

	/**
	 * Supprime toutes les association discipline / Adherent
	 * 
	 * @param listDisciplines
	 * @param idAdherent
	 */
	public void deleteAllDisciplinesParAdherent(List<Discipline> listDisciplines, long idAdherent) {
		for (Discipline d : listDisciplines) {
			deleteDisciplineParAdherent(d.getIdDiscipline(), idAdherent);
		}
	}

	/**
	 * Mets à jour les association Adhérent / discipline
	 * 
	 * @param listDiscipline
	 * @param idAdherent
	 */
	public void updateAllDisciplineParAdherent(List<Discipline> listDiscipline, long idAdherent) {
		List<Discipline> dejaInscrit = this.getDisciplineParAdherent(idAdherent);
		deleteAllDisciplinesParAdherent(dejaInscrit, idAdherent);
		addAllDisciplineParAdherent(listDiscipline, idAdherent);

	}

	/**
	 * Associe une liste de disciplines à une adhérent.
	 * 
	 * @param listDiscipline
	 * @param idAdherent
	 */
	public void addAllDisciplineParAdherent(List<Discipline> listDiscipline, long idAdherent) {
		for (Discipline d : listDiscipline) {
			List<Discipline> dejaInscrit = this.getDisciplineParAdherent(idAdherent);
			if (!dejaInscrit.contains(d))
				addDiscplineParAdherent(d.getIdDiscipline(), idAdherent);
		}
	}

	/**
	 * Return la discipline si elle existe dans la base, sinon null La
	 * comparaison s'effectue sur l'idMatiere et l'idNiveau
	 * 
	 * @param discipline
	 *            la discipline à tester
	 * @return la discipline si elle existe.
	 */
	public Discipline exist(Discipline discipline) {
		Connection connexion = null;
		PreparedStatement requete = null;
		ResultSet result = null;
		String sql = "SELECT * from discipline where idNiveau = ? and idMatiere = ?";
		Discipline verif = null;

		Integer idNiveau = null;
		if (discipline.getNiveau() != null) {
			idNiveau = discipline.getNiveau().getIdNiveau();
		}

		Integer idMatiere = null;
		if (discipline.getMatiere() != null) {
			idMatiere = discipline.getMatiere().getIdMatiere();
		}
		try {
			connexion = factory.getConnection();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion, sql, false, idNiveau, idMatiere);
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
	protected Discipline map(ResultSet result) throws SQLException {
		NiveauDAO niveauDAO = this.factory.getNiveauDAO();
		MatiereDAO matiereDAO = this.factory.getMatiereDAO();
		SalleDAO salleDAO = this.factory.getSalleDAO();
		Integer idDiscipline = NumberUtil.getResultInteger(result, "idDiscipline");
		return new Discipline(idDiscipline, matiereDAO.get(NumberUtil.getResultLong(result, "idMatiere")),
				niveauDAO.get(NumberUtil.getResultLong(result, "idNiveau")),
				salleDAO.getAllSalleParDiscipline(idDiscipline));
	}

}
