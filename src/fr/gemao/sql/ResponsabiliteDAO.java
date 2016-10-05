package fr.gemao.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.gemao.entity.personnel.Responsabilite;
import fr.gemao.sql.exception.DAOException;
import fr.gemao.sql.util.DAOUtilitaires;

/**
 * Classe ResponsabiliteDAO
 * 
 * @author Coco
 *
 */
public class ResponsabiliteDAO extends IDAO<Responsabilite> {

	/**
	 * Constructeur de la classe
	 * 
	 * @param factory
	 */
	public ResponsabiliteDAO(DAOFactory factory) {
		super(factory);
	}

	/**
	 * Redéfinition de la méthode create
	 */
	@Override
	public Responsabilite create(Responsabilite obj) {
		if (obj == null) {
			throw new NullPointerException(
					"La responsabilité ne doit pas être null");
		}

		Connection connexion = null;
		PreparedStatement requete = null;
		ResultSet result = null;
		String sql = "INSERT INTO responsabilite(idResponsabilite, libelle)"
				+ "VALUES (?, ?);";
		int id;
		try {
			connexion = factory.getConnection();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion,
					sql, true, obj.getIdResponsabilite(), obj.getLibelle());

			int status = requete.executeUpdate();

			if (status == 0) {
				throw new DAOException(
						"Échec de la création de la responsabilité, aucune ligne ajoutée dans la table.");
			}

			result = requete.getGeneratedKeys();
			if (result != null && result.first()) {
				id = result.getInt(1);
				obj.setIdResponsabilite(id);
			}

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			DAOUtilitaires.fermeturesSilencieuses(result, requete, connexion);
		}

		return this.get(obj.getIdResponsabilite());
	}

	/**
	 * Redéfinition de la méthode delete
	 */
	@Override
	public void delete(Responsabilite obj) {

	}

	/**
	 * Redéfinition de la méthode update
	 */
	@Override
	public Responsabilite update(Responsabilite obj) {
		if (obj == null) {
			throw new NullPointerException(
					"La responsabilité ne doit pas être null");
		}

		Connection connexion = null;
		PreparedStatement requete = null;
		ResultSet result = null;

		String sql = "UPDATE responsabilite SET idResponsabilite = ?, libelle = ? "
				+ "WHERE idResponsabilite = ?;";

		try {
			connexion = factory.getConnection();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion,
					sql, false, obj.getIdResponsabilite(), obj.getLibelle());

			requete.executeUpdate();

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			DAOUtilitaires.fermeturesSilencieuses(result, requete, connexion);
		}

		return this.get(obj.getIdResponsabilite());
	}

	/**
	 * Redéfinition de la méthode get
	 */
	@Override
	public Responsabilite get(long id) {
		Responsabilite responsabilite = null;
		Connection connexion = null;
		PreparedStatement requete = null;
		ResultSet result = null;

		String sql = "SELECT * FROM responsabilite WHERE idResponsabilite = ?;";

		try {

			connexion = factory.getConnection();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion,
					sql, false, id);
			result = requete.executeQuery();

			if (result.first()) {
				responsabilite = this.map(result);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			DAOUtilitaires.fermeturesSilencieuses(result, requete, connexion);
		}

		return responsabilite;
	}

	/**
	 * Redéfinition de la méthode getAll
	 */
	@Override
	public List<Responsabilite> getAll() {
		List<Responsabilite> liste = new ArrayList<>();
		Responsabilite responsabilite = null;
		Connection connexion = null;
		PreparedStatement requete = null;
		ResultSet result = null;

		String sql = "SELECT * FROM responsabilite;";

		try {
			connexion = factory.getConnection();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion,
					sql, false);
			result = requete.executeQuery();

			while (result.next()) {
				responsabilite = this.map(result);
				liste.add(responsabilite);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			DAOUtilitaires.fermeturesSilencieuses(result, requete, connexion);
		}

		return liste;
	}

	/**
	 * Ajoute une liste de responsabilité dans la base. N'ajoute pas les
	 * éléments qui existe déjà.
	 * 
	 * @param liste
	 * @return
	 */
	public List<Responsabilite> createAll(List<Responsabilite> liste) {
		List<Responsabilite> result = new ArrayList<>();
		Responsabilite exist = null;
		for (Responsabilite resp : liste) {
			exist = this.exist(resp);
			if (exist == null) {
				result.add(this.create(resp));
			}else{
				result.add(exist);
			}
		}
		return liste;
	}

	/**
	 * Retourne null si la responsabilite n'existe pas dans le base de données;
	 * Compare le libellé.
	 * @param responsabilite
	 * @return
	 */
	public Responsabilite exist(Responsabilite responsabilite) {
		Responsabilite verif = null;
		Connection connexion = null;
		PreparedStatement requete = null;
		ResultSet result = null;

		String sql = "SELECT * FROM responsabilite WHERE libelle = ?;";

		try {

			connexion = factory.getConnection();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion,
					sql, false, responsabilite.getLibelle());
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
	
	/**
	 * Associe toutes une listes de responsabilite à une personne.
	 * @param idPersonne
	 * @param responsabilites
	 * @return
	 */
	public List<Responsabilite> addAllResponsabiliteParPersonnel(long idPersonne, List<Responsabilite> responsabilites){
		List<Responsabilite> results = new ArrayList<>();
		results = this.createAll(responsabilites);
		Responsabilite r;
		for(Responsabilite resp : responsabilites){
			if((r=this.exist(resp)) == null){
				resp = this.create(resp);
			}else{
				resp = r;
			}
			this.addResponsabiliteParPersonnel(idPersonne, resp);
		}
		return results;
	}
	
	/**
	 * Associe une responsabilité à une Personne
	 * @param idPersonne
	 * @param responsabilite
	 */
	public void addResponsabiliteParPersonnel(long idPersonne, Responsabilite responsabilite){
		if (responsabilite == null) {
			throw new NullPointerException(
					"La responsabilité ne doit pas être null");
		}

		Connection connexion = null;
		PreparedStatement requete = null;
		ResultSet result = null;
		String sql = "INSERT INTO personnelXresponsabilite(idResponsabilite, idPersonne)"
				+ "VALUES (?, ?);";
		
		try {
			connexion = factory.getConnection();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion,
					sql, false, responsabilite.getIdResponsabilite(), idPersonne);

			int status = requete.executeUpdate();

			if (status == 0) {
				throw new DAOException(
						"Échec de la création de l'association entre la responsabilité et la personne, aucune ligne ajoutée dans la table.");
			}

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			DAOUtilitaires.fermeturesSilencieuses(result, requete, connexion);
		}
	}

	/**
	 * Méthode permettant de retourner la liste des responsabilités liées à une
	 * personne
	 * 
	 * @param idPersonne
	 *            : l'ID de la personne
	 * @return liste : la liste des responsabilités
	 */
	public List<Responsabilite> getResponsabilitesParPersonne(Long idPersonne) {
		Connection co = null;
		PreparedStatement state = null;
		ResultSet result = null;
		Responsabilite responsabilite = null;
		List<Responsabilite> liste = new ArrayList<Responsabilite>();

		String sql = "SELECT * FROM responsabilite r INNER JOIN personnelXresponsabilite pxr on r.idResponsabilite = pxr.idResponsabilite WHERE idPersonne = ?;";

		try {
			co = factory.getConnection();
			state = DAOUtilitaires.initialisationRequetePreparee(co, sql,
					false, idPersonne);
			result = state.executeQuery();

			while (result.next()) {
				responsabilite = this.map(result);
				liste.add(responsabilite);
			}

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			DAOUtilitaires.fermeturesSilencieuses(result, state, co);
		}

		return liste;
	}

	/**
	 * Redéfinition de la méthode map
	 */
	@Override
	protected Responsabilite map(ResultSet result) throws SQLException {

		return new Responsabilite(result.getInt("idResponsabilite"),
				result.getString("libelle"));
	}

}