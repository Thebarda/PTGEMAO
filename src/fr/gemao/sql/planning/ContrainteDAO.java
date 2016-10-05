package fr.gemao.sql.planning;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import fr.gemao.entity.planning.Contrainte;
import fr.gemao.entity.util.HeurePlanning;
import fr.gemao.sql.DAOFactory;
import fr.gemao.sql.IDAO;
import fr.gemao.sql.JourDAO;
import fr.gemao.sql.cours.SalleDAO;
import fr.gemao.sql.exception.DAOException;
import fr.gemao.sql.util.DAOUtilitaires;
import fr.gemao.sql.util.NumberUtil;

/**
 * @author FELTON-GROS-METAYER-PIAT-VAREILLE
 *
 */
public class ContrainteDAO extends IDAO<Contrainte> {

	private Integer idCreneau = null;
	private Integer idPlanning = null;
	private Integer idSalle = null;
	private Integer idJour = null;
	private Integer idHeureDebut = null;
	private Integer idHeureDuree = null;
	private String message = null;

	/**
	 * @param factory
	 */
	public ContrainteDAO(DAOFactory factory) {
		super(factory);
	}

	@Override
	public Contrainte create(Contrainte obj) {
		if (obj == null) {
			throw new NullPointerException("La contrainte ne doit pas être null");
		}

		long id = 0;
		Connection connexion = null;
		PreparedStatement requete = null;
		ResultSet result = null;
		String sql = "INSERT INTO contrainte (idCreneau, idPlanning, idSalle, idJour, idHeureDebut, idHeureFin, message) "
				+ "VALUES (?,?,?,?,?,?,?);";

		try {
			if (obj.getIdCreneau() != null) {
				idCreneau = obj.getIdCreneau();
			}
			if (obj.getIdPlanning() != null) {
				idPlanning = obj.getIdPlanning();
			}
			if (obj.getSalle() != null) {
				idSalle = obj.getSalle().getIdSalle();
			}
			if (obj.getJour() != null) {
				idJour = obj.getJour().getIdJour();
			}
			if (obj.getHeureDebut() != null) {
				idHeureDebut = HeurePlanning.conversionHeuretoIdBDD(obj.getHeureDebut());
			}
			if (obj.getHeureFin() != null) {
				idHeureDuree = HeurePlanning.conversionHeuretoIdBDD(obj.getHeureFin());
			}
			if (obj.getMessage() != null) {
				message = obj.getMessage();
			}
			connexion = factory.getConnection();
			if (obj.isGlobale()) {
				requete = DAOUtilitaires.initialisationRequetePreparee(connexion, sql, true, null, idPlanning, idSalle,
						idJour, idHeureDebut, idHeureDuree, message);
			} else {
				requete = DAOUtilitaires.initialisationRequetePreparee(connexion, sql, true, idCreneau, null, idSalle,
						idJour, idHeureDebut, idHeureDuree, message);
			}
			int status = requete.executeUpdate();

			if (status == 0) {
				throw new DAOException("Échec lors de la création de la contrainte, elle n'a pas été insérée.");
			}

			result = requete.getGeneratedKeys();
			if (result != null && result.first()) {
				id = result.getLong(1);
			}

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			DAOUtilitaires.fermeturesSilencieuses(result, requete, connexion);
		}

		return this.get(id);
	}

	@Override
	public void delete(Contrainte obj) {
		if (obj == null) {
			throw new NullPointerException("La contrainte ne doit pas être null");
		}

		Connection connexion = null;
		PreparedStatement requete = null;
		ResultSet result = null;
		String sql = "DELETE FROM contrainte " + "WHERE idContrainte = ?;";

		try {

			connexion = factory.getConnection();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion, sql, false, obj.getIdContrainte());
			requete.executeUpdate();

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			DAOUtilitaires.fermeturesSilencieuses(result, requete, connexion);
		}
	}

	public void deleteByIdCreneau(Integer id) {
		if (id == null) {
			throw new NullPointerException("L'id ne doit pas être null");
		}

		Connection connexion = null;
		PreparedStatement requete = null;
		ResultSet result = null;
		String sql = "DELETE FROM contrainte WHERE idCreneau = ?;";

		try {

			connexion = factory.getConnection();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion, sql, false, id);
			requete.executeUpdate();

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			DAOUtilitaires.fermeturesSilencieuses(result, requete, connexion);
		}
	}

	public void deleteByIdPlanning(Integer id) {
		if (id == null) {
			throw new NullPointerException("L'id ne doit pas être null");
		}

		Connection connexion = null;
		PreparedStatement requete = null;
		ResultSet result = null;
		String sql = "DELETE FROM contrainte WHERE idPlanning = ?;";

		try {

			connexion = factory.getConnection();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion, sql, false, id);
			requete.executeUpdate();

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			DAOUtilitaires.fermeturesSilencieuses(result, requete, connexion);
		}
	}

	@Override
	public Contrainte update(Contrainte obj) {
		if (obj == null) {
			throw new NullPointerException("La contrainte ne peut etre null");
		}

		Connection connexion = null;
		Statement stat = null;
		PreparedStatement requete = null;
		try {
			String sql = "UPDATE contrainte SET idCreneau = ?, idPlanning = ?, idSalle = ?, idJour = ?, idHeureDebut = ?, idHeureFin = ?"
					+ ", message = ?  WHERE idContrainte = ?";

			if (obj.getIdCreneau() != null) {
				idCreneau = obj.getIdCreneau();
			}
			if (obj.getSalle() != null) {
				idSalle = obj.getSalle().getIdSalle();
			}
			if (obj.getIdPlanning() != null) {
				idCreneau = obj.getIdPlanning();
			}

			if (obj.getJour() != null) {
				idJour = obj.getJour().getIdJour();
			}
			if (obj.getHeureDebut() != null) {
				idHeureDebut = HeurePlanning.conversionHeuretoIdBDD(obj.getHeureDebut());
			}
			if (obj.getHeureFin() != null) {
				idHeureDuree = HeurePlanning.conversionHeuretoIdBDD(obj.getHeureFin());
			}

			connexion = factory.getConnection();
			final Integer idSalle = obj.getSalle() == null ? null : obj.getSalle().getIdSalle();
			final Integer idJour = obj.getJour() == null ? null : obj.getJour().getIdJour();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion, sql, true, idCreneau, idPlanning, idSalle,
					idJour, idHeureDebut, idHeureDuree, obj.getMessage(), obj.getIdContrainte());

			requete.executeUpdate();

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			DAOUtilitaires.fermeturesSilencieuses(stat, connexion);
		}
		return obj;
	}

	@Override
	public Contrainte get(long id) {
		Contrainte contrainte = null;
		Connection connexion = null;
		PreparedStatement requete = null;
		ResultSet result = null;
		String sql = "SELECT * FROM contrainte" + " WHERE idContrainte = ?;";
		try {
			connexion = factory.getConnection();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion, sql, false, id);
			result = requete.executeQuery();

			if (result.first()) {
				contrainte = this.map(result);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			DAOUtilitaires.fermeturesSilencieuses(result, requete, connexion);
		}
		return contrainte;
	}

	@Override
	public List<Contrainte> getAll() {
		List<Contrainte> liste = new ArrayList<>();

		Contrainte contrainte = null;
		Connection connexion = null;
		PreparedStatement requete = null;
		ResultSet result = null;
		String sql = "SELECT * FROM contrainte;";
		try {
			connexion = factory.getConnection();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion, sql, false);
			result = requete.executeQuery();

			while (result.next()) {
				contrainte = this.map(result);
				liste.add(contrainte);
			}
		} catch (SQLException e1) {
			throw new DAOException(e1);
		} finally {
			DAOUtilitaires.fermeturesSilencieuses(result, requete, connexion);
		}

		return liste;
	}

	public List<Contrainte> getAllByIdCreneau(Integer idCreneau) {
		List<Contrainte> liste = new ArrayList<>();

		Contrainte contrainte = null;
		Connection connexion = null;
		PreparedStatement requete = null;
		ResultSet result = null;
		String sql = "SELECT * FROM contrainte WHERE idCreneau = ?;";
		try {
			connexion = factory.getConnection();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion, sql, false, idCreneau);
			result = requete.executeQuery();

			while (result.next()) {
				contrainte = this.map(result);
				liste.add(contrainte);
			}
		} catch (SQLException e1) {
			throw new DAOException(e1);
		} finally {
			DAOUtilitaires.fermeturesSilencieuses(result, requete, connexion);
		}

		return liste;
	}

	public List<Contrainte> getAllByIdPlanning(Integer idPlanning) {
		List<Contrainte> liste = new ArrayList<>();

		Contrainte contrainte = null;
		Connection connexion = null;
		PreparedStatement requete = null;
		ResultSet result = null;
		String sql = "SELECT * FROM contrainte WHERE idPlanning = ?;";
		try {
			connexion = factory.getConnection();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion, sql, false, idPlanning);
			result = requete.executeQuery();

			while (result.next()) {
				contrainte = this.map(result);
				liste.add(contrainte);
			}
		} catch (SQLException e1) {
			throw new DAOException(e1);
		} finally {
			DAOUtilitaires.fermeturesSilencieuses(result, requete, connexion);
		}

		return liste;
	}

	@Override
	protected Contrainte map(ResultSet result) throws SQLException {
		final SalleDAO salleDAO = DAOFactory.getInstance().getSalleDAO();
		final JourDAO jourDAO = DAOFactory.getInstance().getJourDAO();
		final HeurePlanningDAO heurePlanningDAO = DAOFactory.getInstance().getHeurePlanningDAO();
		final Integer idCreneau = NumberUtil.getResultInteger(result, "idCreneau");
		final Integer idPlanning = NumberUtil.getResultInteger(result, "idPlanning");
		final Integer idSalle = NumberUtil.getResultInteger(result, "idSalle");
		final Integer idJour = NumberUtil.getResultInteger(result, "idJour");
		final Integer idHeureDebut = NumberUtil.getResultInteger(result, "idHeureDebut");
		final Integer idHeureFin = NumberUtil.getResultInteger(result, "idHeureFin");

		return new Contrainte(result.getInt("idContrainte"), idCreneau, idPlanning,
				idSalle != null ? salleDAO.get(idSalle) : null, idJour != null ? jourDAO.get(idJour) : null,
				idHeureDebut != null ? heurePlanningDAO.get(idHeureDebut) : null,
				idHeureFin != null ? heurePlanningDAO.get(idHeureFin) : null,
				result.getString("message") != null ? result.getString("message") : null);
	}

}