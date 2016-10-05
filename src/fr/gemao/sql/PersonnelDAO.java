package fr.gemao.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.gemao.ctrl.planning.ProfCtrl;
import fr.gemao.entity.administration.ProfilEnum;
import fr.gemao.entity.cours.Discipline;
import fr.gemao.entity.cours.Prof;
import fr.gemao.entity.personnel.Contrat;
import fr.gemao.entity.personnel.Diplome;
import fr.gemao.entity.personnel.Personnel;
import fr.gemao.entity.personnel.Responsabilite;
import fr.gemao.sql.administration.ProfilDAO;
import fr.gemao.sql.cours.DisciplineDAO;
import fr.gemao.sql.exception.DAOException;
import fr.gemao.sql.util.DAOUtilitaires;
import fr.gemao.sql.util.NumberUtil;
import fr.gemao.util.Password;

/**
 * Classe PersonnelDAO
 * 
 * @author Coco
 *
 */
public class PersonnelDAO extends IDAO<Personnel> {

	/**
	 * Constructeur de la classe PersonnelDAO
	 * 
	 * @param factory
	 */
	public PersonnelDAO(DAOFactory factory) {
		super(factory);
	}

	/**
	 * Redéfinition de la méthode Appelle create de PersonneDAO
	 * {@link PersonneDAO#create(fr.gemao.entity.Personne)} Et associe la liste
	 * des responsabilités et des diplomes à la personne.
	 */
	@Override
	public Personnel create(Personnel obj) {
		if (obj == null) {
			throw new NullPointerException("Le personnel ne doit pas être null");
		}

		Connection connexion = null;
		PreparedStatement requete = null;
		ResultSet result = null;
		String sql = "INSERT INTO personnel(idPersonne, login, pwd, pointAnciennete, idProfil, numeroSS, dateDebutEnseignement)"
				+ "VALUES (?, ?, ?, ?, ?, ?, ?);";

		// PersonneDAO personneDAO = factory.getPersonneDAO();

		ResponsabiliteDAO responsabiliteDAO = factory.getResponsabiliteDAO();
		List<Responsabilite> listResponsabilite;

		DiplomeDAO diplomeDAO = factory.getDiplomeDAO();
		List<Diplome> listeDiplome;

		DisciplineDAO disciplineDAO = factory.getDisciplineDAO();
		List<Discipline> listeDiscipline;

		ContratDAO contratDAO = factory.getContratDAO();
		List<Contrat> listContrat;

		Integer idProfil = null;
		if (obj.getProfil() != null) {
			idProfil = obj.getProfil().getIdProfil();
		}
		try {
			// obj = (Personnel) personneDAO.create(obj);
			connexion = factory.getConnection();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion, sql, false, obj.getIdPersonne(),
					obj.getLogin(), Password.encrypt(obj.getPassword()), obj.getPointsAncien(), idProfil,
					obj.getNumeroSS(), obj.getDateEntree());

			int status = requete.executeUpdate();

			if (status == 0) {
				throw new DAOException("Échec de la création du personnel, aucune ligne ajoutée dans la table.");
			}

			listResponsabilite = responsabiliteDAO.addAllResponsabiliteParPersonnel(obj.getIdPersonne(),
					obj.getListeResponsabilite());
			obj.setListeResponsabilite(listResponsabilite);
			listeDiplome = diplomeDAO.addAllDiplomesParPersonnel(obj.getIdPersonne(), obj.getListeDiplomes());
			obj.setListeDiplomes(listeDiplome);
			disciplineDAO.addAllDisciplineParPersonnel(obj.getListeDiscipline(), obj.getIdPersonne());
			listContrat = contratDAO.addAllContratParPersonnel(obj.getContrat(), obj);
			obj.setContrat(listContrat);
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			DAOUtilitaires.fermeturesSilencieuses(result, requete, connexion);
		}

		return obj;
	}

	/**
	 * Redéfinition de la méthode delete
	 */
	@Override
	public void delete(Personnel obj) {

	}

	/**
	 * Redéfinition de la méthode update
	 */
	@Override
	public Personnel update(Personnel obj) {
		if (obj == null) {
			throw new NullPointerException("Le personnel ne doit pas être null");
		}

		Connection connexion = null;
		PreparedStatement requete = null;
		ResultSet result = null;

		Integer idProfil = null;
		if (obj.getProfil() != null) {
			idProfil = obj.getProfil().getIdProfil();
			if (ProfilEnum.PROFESSEUR.getId() == idProfil) {
				Prof prof = new Prof(obj, obj.getListeResponsabilite(), obj.getListeDiplomes(),
						obj.getListeDiscipline(), obj.getContrat(), obj.getLogin(), obj.getPassword(),
						obj.getPointsAncien(), obj.getProfil(), obj.isPremiereConnexion(),
						new java.sql.Date(obj.getDateEntree().getTime()), obj.getNumeroSS());
				ProfCtrl.create(prof);
			}
		}

		String sql = "UPDATE personnel SET login = ?, pwd = ?, pointAnciennete = ?, premiereConnexion = ?,"
				+ "idProfil = ?, numeroSS = ?, dateDebutEnseignement=? " + "WHERE idPersonne = ?;";

		try {
			connexion = factory.getConnection();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion, sql, false, obj.getLogin(),
					obj.getPassword(), obj.getPointsAncien(), obj.isPremiereConnexion(), idProfil, obj.getNumeroSS(),
					obj.getDateEntree(), obj.getIdPersonne());
			requete.executeUpdate();

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			DAOUtilitaires.fermeturesSilencieuses(result, requete, connexion);
		}

		return this.get(obj.getIdPersonne());
	}

	/**
	 * Redéfinition de la méthode get
	 */
	@Override
	public Personnel get(long id) {
		Personnel personnel = null;
		Connection connexion = null;
		PreparedStatement requete = null;
		ResultSet result = null;

		String sql = "SELECT * FROM personnel pl inner join personne p on pl.idPersonne = p.idPersonne WHERE p.idPersonne = ?;";

		try {

			connexion = factory.getConnection();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion, sql, false, id);
			result = requete.executeQuery();

			if (result.first()) {
				personnel = this.map(result);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			DAOUtilitaires.fermeturesSilencieuses(result, requete, connexion);
		}

		return personnel;
	}

	/**
	 * Redéfinition de la méthode getAll
	 */
	@Override
	public List<Personnel> getAll() {
		List<Personnel> liste = new ArrayList<>();
		Personnel personnel = null;
		Connection connexion = null;
		PreparedStatement requete = null;
		ResultSet result = null;

		String sql = "SELECT * FROM personnel pl inner join personne p on pl.idPersonne = p.idPersonne order by nom, prenom;";

		try {
			connexion = factory.getConnection();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion, sql, false);
			result = requete.executeQuery();

			while (result.next()) {
				personnel = this.map(result);
				liste.add(personnel);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			DAOUtilitaires.fermeturesSilencieuses(result, requete, connexion);
		}

		return liste;
	}

	/**
	 * Redéinition de la méthode map
	 */
	@Override
	protected Personnel map(ResultSet result) throws SQLException {
		PersonneDAO personneDAO = factory.getPersonneDAO();
		ResponsabiliteDAO responsabiliteDAO = factory.getResponsabiliteDAO();
		DiplomeDAO diplomeDAO = factory.getDiplomeDAO();
		DisciplineDAO disciplineDAO = factory.getDisciplineDAO();
		ContratDAO contratDAO = factory.getContratDAO();
		ProfilDAO profilDAO = factory.getProfilDAO();

		Integer idProfil = NumberUtil.getResultInteger(result, "idProfil");
		Long idPersonne = result.getLong("idPersonne");

		Personnel personnel = new Personnel(personneDAO.map(result),
				responsabiliteDAO.getResponsabilitesParPersonne(result.getLong("idPersonne")),
				diplomeDAO.getDiplomesParPersonnel(result.getLong("idPersonne")),
				disciplineDAO.getDisciplineParPersonnel(result.getLong("idPersonne")),
				contratDAO.getContratsParPersonne(idPersonne), result.getString("login"), result.getString("pwd"),
				NumberUtil.getResultInteger(result, "pointAnciennete"),
				idProfil == null ? null : profilDAO.get(idProfil), result.getBoolean("premiereConnexion"),
				result.getString("numeroSS"), result.getDate("dateDebutEnseignement"));

		return personnel;
	}

	/**
	 * Méthode permettant retourner un personnel en fonction de son login
	 */
	public Personnel getLoginParPersonnel(String login) {
		Personnel personnel = null;
		Connection connexion = null;
		PreparedStatement requete = null;
		ResultSet result = null;

		String sql = "SELECT * FROM personnel pl inner join personne p on pl.idPersonne = p.idPersonne WHERE login = ?;";

		try {

			connexion = factory.getConnection();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion, sql, false, login);
			result = requete.executeQuery();

			if (result.first()) {
				personnel = this.map(result);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			DAOUtilitaires.fermeturesSilencieuses(result, requete, connexion);
		}

		return personnel;
	}

	public int getNbNomPersonnel(String nom) {
		Connection connexion = null;
		PreparedStatement requete = null;
		ResultSet result = null;
		int nb = 0;

		String sql = "SELECT pl.idPersonne FROM personnel pl inner join personne p on pl.idPersonne = p.idPersonne WHERE nom = ?;";

		try {

			connexion = factory.getConnection();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion, sql, false, nom);
			result = requete.executeQuery();
			result.last();
			nb = result.getRow();
			result.beforeFirst();
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			DAOUtilitaires.fermeturesSilencieuses(result, requete, connexion);
		}

		return nb;
	}
}