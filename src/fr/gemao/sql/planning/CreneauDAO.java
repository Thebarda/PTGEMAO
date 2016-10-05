package fr.gemao.sql.planning;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import fr.gemao.entity.planning.Creneau;
import fr.gemao.entity.util.HeurePlanning;
import fr.gemao.sql.DAOFactory;
import fr.gemao.sql.IDAO;
import fr.gemao.sql.JourDAO;
import fr.gemao.sql.cours.CoursDAO;
import fr.gemao.sql.cours.SalleDAO;
import fr.gemao.sql.exception.DAOException;
import fr.gemao.sql.util.DAOUtilitaires;
import fr.gemao.sql.util.NumberUtil;

public class CreneauDAO extends IDAO<Creneau> {

	public CreneauDAO(DAOFactory factory) {
		super(factory);
	}

	// TODO ne contient pas Cours
	public List<Creneau> createMultiple(List<Creneau> creneaux) {
		List<Creneau> liste = new ArrayList<>();

		if (creneaux.contains(null)) {
			throw new NullPointerException("Un des elements est null, merci de recommencer !");
		}

		long id = 0;
		Connection connexion = null;
		PreparedStatement requete = null;
		ResultSet result = null;

		String sql = "INSERT INTO creneau (libelle," + "heureDebut," + "duree," + "idJour," + "idSalle,"
				+ "couleur, idPlanning) " + "VALUES (?,?,?,?,?,?,?);";

		Integer idSalle = null;
		Integer idJour = null;
		Integer idHeureDeb = null;
		Integer idHeureDuree = null;
		try {
			connexion = factory.getConnection();
			for (Creneau obj : liste) {

				if (obj.getSalle() != null) {
					if (obj.getSalle().getIdSalle() != -1) {
						idSalle = obj.getSalle().getIdSalle();
					}
				}
				if (obj.getJour() != null) {
					idJour = obj.getJour().getIdJour();
				}
				if (obj.getHeureDeb() != null) {
					idHeureDeb = HeurePlanning.conversionHeuretoIdBDD(obj.getHeureDeb());
				}
				if (obj.getDuree() != null) {
					idHeureDuree = HeurePlanning.conversionHeuretoIdBDD(obj.getDuree());
				}
				connexion = factory.getConnection();
				requete = DAOUtilitaires.initialisationRequetePreparee(connexion, sql, true, obj.getLibelle(),
						idHeureDeb, idHeureDuree, idJour, idSalle, obj.getCouleur(), obj.getIdPlanning());
				int status = requete.executeUpdate();

				if (status == 0) {
					throw new DAOException("Échec de la création de matériel, aucune ligne ajoutée dans la table.");
				}

				result = requete.getGeneratedKeys();
				if (result != null && result.first()) {
					id = result.getLong(1);
				}

				liste.add(this.get(id));

			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			DAOUtilitaires.fermeturesSilencieuses(result, requete, connexion);
		}

		return liste;

	}

	// TODO ne contient pas Cours
	public List<Creneau> updateMultiple(List<Creneau> creneaux) {
		List<Creneau> liste = new ArrayList<>();

		if (creneaux.contains(null)) {
			throw new NullPointerException("Un des elements est null, merci de recommencer !");
		}

		Connection connexion = null;
		PreparedStatement requete = null;
		ResultSet result = null;

		String sql = "UPDATE creneau SET libelle = ?, heureDebut = ?, duree = ?, idJour = ?, idSalle = ?, couleur = ?, idPlanning = ? WHERE idCreneau = ?";

		Integer idHeureDeb = 0;
		Integer idHeureDuree = 0;
		try {
			connexion = factory.getConnection();
			for (Creneau obj : liste) {
				System.out.println(obj);
				if (obj == null) {
					throw new NullPointerException("Le creneau ne peut etre null");
				}

				if (obj.getHeureDeb() != null) {
					idHeureDeb = HeurePlanning.conversionHeuretoIdBDD(obj.getHeureDeb());
				}
				if (obj.getDuree() != null) {
					idHeureDuree = HeurePlanning.conversionHeuretoIdBDD(obj.getDuree());
				}

				requete = DAOUtilitaires.initialisationRequetePreparee(connexion, sql, true, obj.getLibelle(),
						idHeureDeb, idHeureDuree, obj.getJour().getIdJour(), obj.getSalle().getIdSalle(),
						obj.getCouleur(), obj.getIdPlanning(), obj.getIdCreneau());

				int status = requete.executeUpdate();

				if (status == 0) {
					obj = null;
				}

				liste.add(obj);

			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			DAOUtilitaires.fermeturesSilencieuses(result, requete, connexion);
		}

		return liste;
	}

	@Override
	public Creneau create(Creneau obj) {
		if (obj == null) {
			throw new NullPointerException("Le creneau ne doit pas etre null");
		}

		long id = 0;
		Connection connexion = null;
		PreparedStatement requete = null;
		ResultSet result = null;
		String sql = "INSERT INTO creneau (libelle," + "heureDebut," + "duree," + "idJour," + "idSalle,"
				+ "couleur, idPlanning, idCours) " + "VALUES (?,?,?,?,?,?,?,?);";

		Integer idSalle = null;
		Integer idJour = null;
		Integer idHeureDeb = null;
		Integer idHeureDuree = null;

		try {
			if (obj.getSalle() != null) {
				if (obj.getSalle().getIdSalle() != -1) {
					idSalle = obj.getSalle().getIdSalle();
				}
			}
			if (obj.getJour() != null) {
				idJour = obj.getJour().getIdJour();
			}
			if (obj.getHeureDeb() != null) {
				idHeureDeb = HeurePlanning.conversionHeuretoIdBDD(obj.getHeureDeb());
			}
			if (obj.getDuree() != null) {
				idHeureDuree = HeurePlanning.conversionHeuretoIdBDD(obj.getDuree());
			}
			connexion = factory.getConnection();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion, sql, true, obj.getLibelle(), idHeureDeb,
					idHeureDuree, idJour, idSalle, obj.getCouleur(), obj.getIdPlanning(),
					obj.getCours() != null ? obj.getCours().getIdCours() : null);
			int status = requete.executeUpdate();

			if (status == 0) {
				throw new DAOException("Échec de la création de matériel, aucune ligne ajoutée dans la table.");
			}

			result = requete.getGeneratedKeys();
			if (result != null && result.first()) {
				id = result.getLong(1);
				obj.setIdCreneau(Integer.valueOf((int)id));
			}

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			DAOUtilitaires.fermeturesSilencieuses(result, requete, connexion);
		}

		return obj;
	}

	@Override
	public void delete(Creneau obj) {
		if (obj == null) {
			throw new NullPointerException("Le profil ne doit pas être null");
		}

		Connection connexion = null;
		PreparedStatement requete = null;
		ResultSet result = null;
		String sql = "DELETE FROM creneau " + "WHERE idCreneau = ?;";

		try {

			connexion = factory.getConnection();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion, sql, false, obj.getIdCreneau());
			requete.executeUpdate();

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			DAOUtilitaires.fermeturesSilencieuses(result, requete, connexion);
		}
	}

	@Override
	public Creneau update(Creneau obj) {
		Integer idHeureDeb = null, idHeureDuree = null;
		if (obj == null) {
			throw new NullPointerException("Le creneau ne peut etre null");
		}

		Connection connexion = null;
		Statement stat = null;
		PreparedStatement requete = null;
		try {
			String sql = "UPDATE creneau SET libelle = ?, heureDebut = ?, duree = ?, idJour = ?, idSalle = ?, couleur = ?, idPlanning = ?, idCours = ? WHERE idCreneau = ?";

			if (obj.getHeureDeb() != null) {
				idHeureDeb = HeurePlanning.conversionHeuretoIdBDD(obj.getHeureDeb());
			}
			if (obj.getDuree() != null) {
				idHeureDuree = HeurePlanning.conversionHeuretoIdBDD(obj.getDuree());
			}

			connexion = factory.getConnection();
			final Integer idSalle = obj.getSalle() == null ? null : obj.getSalle().getIdSalle();
			final Integer idJour = obj.getJour() == null ? null : obj.getJour().getIdJour();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion, sql, true, obj.getLibelle(), idHeureDeb,
					idHeureDuree, idJour, idSalle, obj.getCouleur(), obj.getIdPlanning(),
					obj.getCours() != null ? obj.getCours().getIdCours() : null, obj.getIdCreneau());

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
	public Creneau get(long id) {
		Creneau creneau = null;
		Connection connexion = null;
		PreparedStatement requete = null;
		ResultSet result = null;
		String sql = "SELECT idCreneau, libelle, heureDebut, duree, idPlanning, idJour, idSalle, couleur, idCours FROM creneau WHERE idCreneau = ?;";
		try {
			connexion = factory.getConnection();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion, sql, false, id);
			result = requete.executeQuery();

			if (result.first()) {
				creneau = this.map(result);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			DAOUtilitaires.fermeturesSilencieuses(result, requete, connexion);
		}
		return creneau;
	}

	@Override
	public List<Creneau> getAll() {
		List<Creneau> liste = new ArrayList<>();

		Creneau creneau = null;
		Connection connexion = null;
		PreparedStatement requete = null;
		ResultSet result = null;
		String sql = "SELECT * FROM creneau;";
		try {
			connexion = factory.getConnection();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion, sql, false);
			result = requete.executeQuery();

			while (result.next()) {
				creneau = this.map(result);
				liste.add(creneau);
			}
		} catch (SQLException e1) {
			throw new DAOException(e1);
		} finally {
			DAOUtilitaires.fermeturesSilencieuses(result, requete, connexion);
		}

		return liste;
	}

	@Override
	protected Creneau map(ResultSet result) throws SQLException {
		final SalleDAO salleDAO = DAOFactory.getInstance().getSalleDAO();
		final JourDAO jourDAO = DAOFactory.getInstance().getJourDAO();
		final HeurePlanningDAO heurePlanningDAO = DAOFactory.getInstance().getHeurePlanningDAO();
		final CoursDAO coursDAO = DAOFactory.getInstance().getCoursDAO();
		final ContrainteDAO contrainteDAO = DAOFactory.getInstance().getContrainteDAO();
		final Integer idCreneau = NumberUtil.getResultInteger(result, "idCreneau");
		final Integer idSalle = NumberUtil.getResultInteger(result, "idSalle");
		final Integer idJour = NumberUtil.getResultInteger(result, "idJour");
		final Integer idPlanning = NumberUtil.getResultInteger(result, "idPlanning");
		final Integer idHeureDebut = NumberUtil.getResultInteger(result, "heureDebut");
		final Integer idDuree = NumberUtil.getResultInteger(result, "duree");
		final Integer idCours = NumberUtil.getResultInteger(result, "idCours");

		return new Creneau(idCreneau, result.getString("libelle"),
				idHeureDebut != null ? heurePlanningDAO.get(idHeureDebut) : null, heurePlanningDAO.get(idDuree),
				idPlanning, idSalle != null ? salleDAO.get(idSalle) : null, idJour != null ? jourDAO.get(idJour) : null,
				result.getString("couleur"), idCours != null ? coursDAO.get(idCours) : null, contrainteDAO.getAllByIdCreneau(idCreneau));
	}

	public List<Creneau> getListCreneauxPlanning(Integer idPlanning) {
		List<Creneau> liste = new ArrayList<>();

		Creneau creneau = null;
		Connection connexion = null;
		PreparedStatement requete = null;
		ResultSet result = null;
		String sql = "SELECT * FROM creneau WHERE idPlanning = ?;";
		try {
			connexion = factory.getConnection();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion, sql, false, idPlanning);
			result = requete.executeQuery();

			while (result.next()) {
				creneau = this.map(result);
				liste.add(creneau);
			}
		} catch (SQLException e1) {
			throw new DAOException(e1);
		} finally {
			DAOUtilitaires.fermeturesSilencieuses(result, requete, connexion);
		}

		return liste;
	}

	/**
	 * @param idPlanning
	 */
	public void deleteByIdPlanning(Integer idPlanning) {
		
		DAOFactory.getInstance().getContrainteDAO().deleteByIdPlanning(idPlanning);
		
		if (idPlanning == null) {
			throw new NullPointerException("Le planning ne doit pas être null");
		}

		Connection connexion = null;
		PreparedStatement requete = null;
		ResultSet result = null;
		String sql = "DELETE FROM creneau " + "WHERE idPlanning = ?;";

		try {

			connexion = factory.getConnection();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion, sql, false, idPlanning);
			requete.executeUpdate();

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			DAOUtilitaires.fermeturesSilencieuses(result, requete, connexion);
		}
	}

}
