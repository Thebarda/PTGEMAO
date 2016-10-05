package fr.gemao.sql.planning;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import fr.gemao.entity.planning.Contrainte;
import fr.gemao.entity.planning.Creneau;
import fr.gemao.entity.planning.Planning;
import fr.gemao.sql.DAOFactory;
import fr.gemao.sql.IDAO;
import fr.gemao.sql.exception.DAOException;
import fr.gemao.sql.util.DAOUtilitaires;
import fr.gemao.sql.util.NumberUtil;

public class PlanningDAO extends IDAO<Planning> {

	public PlanningDAO(DAOFactory factory) {
		super(factory);
	}

	@Override
	public Planning create(Planning obj) {
		if (obj == null) {
			throw new NullPointerException("Le planning ne peut etre null");
		}

		Integer id = 0;
		Connection connexion = null;
		PreparedStatement requete = null;
		ResultSet result = null;

		String sql = "INSERT INTO planning(nomPlanning, dateDebut, dateFin, semaineAnnee, valide)"
				+ " VALUES (?, ?, ?, ?, ?); ";

		try {
			connexion = factory.getConnection();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion, sql, true, obj.getNomPlanning(),
					obj.getDateDeb(), obj.getDateFin(), obj.getSemaineAnnee(), obj.getValide());

			int status = requete.executeUpdate();

			if (status == 0) {
				throw new DAOException("Échec de la création d'un planning, aucune ligne ajoutée dans la table");
			}

			result = requete.getGeneratedKeys();

			if (result != null && result.first()) {
				id = result.getInt(1);
				obj.setIdPlanning(id);
			}

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			DAOUtilitaires.fermeturesSilencieuses(result, requete, connexion);
		}

		return obj;
	}

	@Override
	public void delete(Planning obj) {
		if (obj == null) {
			throw new NullPointerException("Le planning ne peut etre null");
		}

		Connection connexion = null;
		Statement stat = null;
		try {
			connexion = factory.getConnection();
			stat = connexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			stat.execute("DELETE FROM planning WHERE idPlanning = " + obj.getIdPlanning() + ";");
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			DAOUtilitaires.fermeturesSilencieuses(stat, connexion);
		}
	}

	@Override
	public Planning update(Planning obj) {
		if (obj == null) {
			throw new NullPointerException("Le planning ne peut etre null");
		}

		Connection connexion = null;
		Statement stat = null;
		PreparedStatement requete = null;
		try {
			String sql = "UPDATE planning SET nomPlanning = ?, dateDebut = ?, dateFin = ?, semaineAnnee = ? WHERE idPlanning = ?";

			connexion = factory.getConnection();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion, sql, true, obj.getNomPlanning(),
					obj.getDateDeb(), obj.getDateFin(), obj.getSemaineAnnee(), obj.getIdPlanning());

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
	public Planning get(long id) {
		Planning planning = null;

		Connection connexion = null;
		PreparedStatement requete = null;
		ResultSet result = null;

		String sql = "SELECT idPlanning, nomPlanning, dateDebut, dateFin, semaineAnnee, valide, estArchive "
				+ "FROM planning WHERE idPlanning = ?;";
		try {
			connexion = factory.getConnection();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion, sql, false, id);
			result = requete.executeQuery();

			if (result.first()) {
				planning = this.map(result);
			}
		} catch (SQLException e1) {
			throw new DAOException(e1);
		} finally {
			DAOUtilitaires.fermeturesSilencieuses(result, requete, connexion);
		}

		return planning;
	}

	@Override
	public List<Planning> getAll() {
		List<Planning> plannings = new ArrayList<>();

		Connection connexion = null;
		PreparedStatement requete = null;
		ResultSet result = null;

		String sql = "SELECT idPlanning, nomPlanning, dateDebut, dateFin, semaineAnnee, valide, estArchive "
				+ "FROM planning ;";
		try {
			connexion = factory.getConnection();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion, sql, false);
			result = requete.executeQuery();

			while (result.next()) {
				plannings.add(this.map(result));
			}
		} catch (SQLException e1) {
			throw new DAOException(e1);
		} finally {
			DAOUtilitaires.fermeturesSilencieuses(result, requete, connexion);
		}

		return plannings;
	}

	public List<Planning> getAllEnCours() {
		List<Planning> plannings = new ArrayList<>();

		Connection connexion = null;
		PreparedStatement requete = null;
		ResultSet result = null;

		String sql = "SELECT idPlanning, nomPlanning, dateDebut, dateFin, semaineAnnee, valide, estArchive "
				+ "FROM planning WHERE estArchive = 0;";
		try {
			connexion = factory.getConnection();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion, sql, false);
			result = requete.executeQuery();

			while (result.next()) {
				plannings.add(this.map(result));
			}
		} catch (SQLException e1) {
			throw new DAOException(e1);
		} finally {
			DAOUtilitaires.fermeturesSilencieuses(result, requete, connexion);
		}

		return plannings;

	}

	public List<Planning> getAllArchive() {
		List<Planning> plannings = new ArrayList<>();

		Connection connexion = null;
		PreparedStatement requete = null;
		ResultSet result = null;

		String sql = "SELECT idPlanning, nomPlanning, dateDebut, dateFin, semaineAnnee, valide, estArchive "
				+ "FROM planning WHERE estArchive = 1;";
		try {
			connexion = factory.getConnection();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion, sql, false);
			result = requete.executeQuery();

			while (result.next()) {
				plannings.add(this.map(result));
			}
		} catch (SQLException e1) {
			throw new DAOException(e1);
		} finally {
			DAOUtilitaires.fermeturesSilencieuses(result, requete, connexion);
		}

		return plannings;

	}

	@Override
	protected Planning map(ResultSet result) throws SQLException {
		final Integer idPlanning = NumberUtil.getResultInteger(result, "idPlanning");
		final CreneauDAO creneauDAO = factory.getCreneauDAO();
		List<Creneau> creneaux = creneauDAO.getListCreneauxPlanning(idPlanning);
		boolean valide = result.getInt("valide") == 1 ? true : false;
		return new Planning(idPlanning, result.getString("nomPlanning"), creneaux, result.getDate("dateDebut"),
				result.getDate("dateFin"), result.getInt("semaineAnnee"), valide, result.getBoolean("estArchive"));
	}

	public void dupliquer(Planning p) {
		if (p.getListeCreneau() == null) {
			p = this.get(p.getIdPlanning());
		}
		boolean estValide = p.getValide();
		String nomPlanning = p.getNomPlanning();
		p.setNomPlanning(nomPlanning + "-copie");
		p.setValide(false);
		final Planning duplicat = this.create(p);
		p.setValide(estValide);
		p.setNomPlanning(nomPlanning);
		final CreneauDAO creneauDAO = new CreneauDAO(this.factory);
		final ContrainteDAO contrainteDAO = DAOFactory.getInstance().getContrainteDAO();
		if (duplicat.getListeCreneau() == null)
			System.out.println("Je suis nul");
		List<Creneau> l = duplicat.getListeCreneau();
		for (Creneau c : l) {
			c.setIdPlanning(duplicat.getIdPlanning());

			Creneau dup = creneauDAO.create(c);
			for (Contrainte cc : dup.getContraintes()) {
				cc.setIdCreneau(dup.getIdCreneau());
				contrainteDAO.create(cc);
			}

		}
	}

	public void valider(Planning p) {
		if (p == null) {
			throw new NullPointerException("Le planning ne peut etre null");
		}

		Connection connexion = null;
		Statement stat = null;

		try {
			connexion = factory.getConnection();
			stat = connexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			stat.execute("UPDATE planning SET valide = 1 WHERE idPlanning = " + p.getIdPlanning() + ";");

		} catch (SQLException e1) {
			throw new DAOException(e1);
		} finally {
			DAOUtilitaires.fermeturesSilencieuses(stat, connexion);
		}
	}

	public void archiver(Planning p) {
		if (p == null) {
			throw new NullPointerException("Le planning ne peut etre null");
		}

		Connection connexion = null;
		Statement stat = null;

		try {
			connexion = factory.getConnection();
			stat = connexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			stat.execute("UPDATE planning SET estArchive = 1 WHERE idPlanning = " + p.getIdPlanning() + ";");

		} catch (SQLException e1) {
			throw new DAOException(e1);
		} finally {
			DAOUtilitaires.fermeturesSilencieuses(stat, connexion);
		}
	}

	public void desarchiver(Planning p) {
		if (p == null) {
			throw new NullPointerException("Le planning ne peut etre null");
		}

		Connection connexion = null;
		Statement stat = null;

		try {
			connexion = factory.getConnection();
			stat = connexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			stat.execute("UPDATE planning SET estArchive = 0 WHERE idPlanning = " + p.getIdPlanning() + ";");

		} catch (SQLException e1) {
			throw new DAOException(e1);
		} finally {
			DAOUtilitaires.fermeturesSilencieuses(stat, connexion);
		}
	}

	/**
	 * Récupère les caractéristiques des plannings, sans les créneaux et
	 * contraintes associées.
	 * 
	 * @return
	 */
	public List<Planning> getAllCaracteristiques() {
		List<Planning> plannings = new ArrayList<>();

		Connection connexion = null;
		PreparedStatement requete = null;
		ResultSet result = null;

		String sql = "SELECT idPlanning, nomPlanning, dateDebut, dateFin, semaineAnnee, valide, estArchive "
				+ "FROM planning WHERE estArchive = 0;";
		try {
			connexion = factory.getConnection();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion, sql, false);
			result = requete.executeQuery();

			while (result.next()) {
				plannings.add(this.mapLight(result));
			}
		} catch (SQLException e1) {
			throw new DAOException(e1);
		} finally {
			DAOUtilitaires.fermeturesSilencieuses(result, requete, connexion);
		}

		return plannings;

	}

	/**
	 * Ne s'occupe pas de récupérer les créneaux correspondant au planning.
	 * 
	 * @param result
	 * @return
	 * @throws SQLException
	 */
	private Planning mapLight(ResultSet result) throws SQLException {
		final Integer idPlanning = NumberUtil.getResultInteger(result, "idPlanning");
		boolean valide = result.getInt("valide") == 1 ? true : false;
		return new Planning(idPlanning, result.getString("nomPlanning"), null, result.getDate("dateDebut"),
				result.getDate("dateFin"), result.getInt("semaineAnnee"), valide, result.getBoolean("estArchive"));
	}

}
