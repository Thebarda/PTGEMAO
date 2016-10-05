package fr.gemao.sql.administration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import fr.gemao.entity.administration.Droit;
import fr.gemao.entity.administration.Module;
import fr.gemao.entity.administration.TypeDroit;
import fr.gemao.sql.DAOFactory;
import fr.gemao.sql.IDAO;
import fr.gemao.sql.exception.DAOException;
import fr.gemao.sql.util.DAOUtilitaires;
import fr.gemao.sql.util.NumberUtil;

public class DroitDAO extends IDAO<Droit> {

	public DroitDAO(DAOFactory factory) {
		super(factory);
	}

	@Override
	public Droit create(Droit obj) {
		throw new UnsupportedOperationException("Impossible de créer un objet Droit");
	}

	@Override
	public void delete(Droit obj) {
		throw new UnsupportedOperationException("Méthode non implémentée.");
	}

	@Override
	public Droit update(Droit obj) {
		throw new UnsupportedOperationException("Méthode non implémentée.");
	}

	@Override
	public Droit get(long id) {
		throw new UnsupportedOperationException("Méthode non implémentée.");
	}

	@Override
	public List<Droit> getAll() {
		throw new UnsupportedOperationException("Méthode non implémentée.");
	}
	
	/**
	 * Méthode effaçant de la base de données tous les droits associés
	 * au profil dont on passe l'identifiant en paramètre
	 */
	public void deleteDroitsProfil(Integer idProfil){
		if (idProfil == null) {
			throw new NullPointerException(
					"L'idProfil ne doit pas etre null");
		}
		
		Connection connexion = null;
		PreparedStatement requete = null;
		ResultSet result = null;
		String sql = "DELETE FROM droit "
				+ "WHERE idProfil = ?;";
		
		
		try {
			connexion = factory.getConnection();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion,
					sql, false, idProfil);
			int status = requete.executeUpdate();

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			DAOUtilitaires.fermeturesSilencieuses(result, requete, connexion);
		}
		
	}
	
	public Droit addDroitParProfil(Integer idProfil, Droit droit){
		if (droit == null) {
			throw new NullPointerException(
					"Le droit ne doit pas etre null");
		}
		
		if (idProfil == null) {
			throw new NullPointerException(
					"L'idProfil ne doit pas etre null");
		}

		Connection connexion = null;
		PreparedStatement requete = null;
		ResultSet result = null;
		String sql = "INSERT INTO droit(idProfil, idModule, idTypeDroit)"
				+ "VALUES (?, ?, ?);";
		
		Integer idModule = null;
		if(droit.getModule() != null){
			idModule = droit.getModule().getIdModule();
		}
		Integer idType = null;
		if(droit.getType() != null){
			idType = droit.getType().getIdType();
		}
		try {
			connexion = factory.getConnection();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion,
					sql, false, idProfil,
					idModule, idType);
			int status = requete.executeUpdate();

			if (status == 0) {
				throw new DAOException(
						"Échec de la création d'un droit, aucune ligne ajoutée dans la table.");
			}

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			DAOUtilitaires.fermeturesSilencieuses(result, requete, connexion);
		}
		return droit;
	}
	
	public List<Droit> addAllDroitParProfil(Integer idProfil, List<Droit> droits){
		List<Droit> resultats = new ArrayList<>();
		Droit res;
		for(Droit d : droits){
			res = this.addDroitParProfil(idProfil, d);
			resultats.add(res);
		}
		return resultats; 
	}
	
	public List<Droit> getAllParProfil(Integer idProfil){
		List<Droit> list = new ArrayList<>();
		Droit droit;
		Connection connexion = null;
		PreparedStatement requete = null;
		ResultSet result = null;
		String sql = "SELECT * FROM droit where idProfil=?;";
		try {

			connexion = factory.getConnection();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion,
					sql, false, idProfil);
			result = requete.executeQuery();

			while (result.next()) {
				droit = this.map(result);
				list.add(droit);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			DAOUtilitaires.fermeturesSilencieuses(result, requete, connexion);
		}
		return list;
	}

	@Override
	protected Droit map(ResultSet result) throws SQLException {
		return new Droit(TypeDroit.getTypeDroit(NumberUtil.getResultInteger(result, "idTypeDroit")),
					Module.getModule(NumberUtil.getResultInteger(result, "idModule")));
	}

}
