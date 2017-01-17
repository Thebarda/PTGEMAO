package fr.gemao.sql.cheque;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import fr.gemao.entity.materiel.ChequeLocation;
import fr.gemao.sql.DAOFactory;
import fr.gemao.sql.IDAO;
import fr.gemao.sql.exception.DAOException;
import fr.gemao.sql.location.LocationDAO;
import fr.gemao.sql.util.DAOUtilitaires;

public class ChequeDAO extends IDAO<ChequeLocation>{

	public ChequeDAO(DAOFactory factory) {
		super(factory);
	}

	@Override
	public ChequeLocation create(ChequeLocation obj) {
		if (obj==null){
			throw new NullPointerException("Le chèque ne peut pas être null");
		}
		Connection connexion = null;
		PreparedStatement requete = null;
		ResultSet result = null;
		String sql = "INSERT INTO contratlocation(idLocation, typeLocation, idLocataire, idMateriel, datePaiement,"
				+ "montantCheque, numCheque, dateEncaissement) "
				+ "VALUES (?,?,?,?,?,?,?,?);";
		
		int idLocation = 0;
		Long idLocataire = null;
		Long idMateriel = null;
		try{
			if (obj.getLocation() != null){
				idLocation = obj.getLocation().getId();
			}
			if (obj.getLocation().getPersonne()!= null){
				idLocataire = obj.getLocation().getPersonne().getIdPersonne();
			}
			if (obj.getLocation().getMateriel() != null){
				idMateriel = obj.getLocation().getMateriel().getIdMateriel();
			}
			connexion = factory.getConnection();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion,
					sql, false,idLocation, obj.getTypeLocation(), idLocataire, idMateriel,
					obj.getDatePaiement(), obj.getMontantCheque(),
					obj.getNumCheque(), obj.getDateEncaissement());
			
			int status = requete.executeUpdate();
			
			if(status == 0){
				throw new DAOException(
						"Echec de la création de l'adhérent, aucune ligne ajoutée"
						+ " dans la table.");
			}
		} catch (SQLException e){
			throw new DAOException(e);
		} finally {
			DAOUtilitaires.fermeturesSilencieuses(result, requete, connexion);
		}
		
		return obj;
	}

	@Override
	public void delete(ChequeLocation obj) {
		
	}

	@Override
	public ChequeLocation update(ChequeLocation obj) {
		if (obj == null){
			throw new NullPointerException("La personne ne doit pas être null");
		}
		
		Connection connexion = null;
		PreparedStatement requete = null;
		ResultSet result = null;
		String sql = "UPDATE contratlocation SET idLocation = ?, typeLocation = ?,"
				+ " idLocataire = ?, idMateriel = ?, datePaiement = ?,"
				+ "montantCheque = ?, numCheque = ?, dateEncaissement = ?"
				+ "WHERE idLocation = ?;";
		
		int idLocation = 0;
		Long idLocataire = null;
		Long idMateriel = null;
		try{
			if (obj.getLocation() != null){
				idLocation = obj.getLocation().getId();
			}
			if (obj.getLocation().getPersonne()!= null){
				idLocataire = obj.getLocation().getPersonne().getIdPersonne();
			}
			if (obj.getLocation().getMateriel() != null){
				idMateriel = obj.getLocation().getMateriel().getIdMateriel();
			}
			connexion = factory.getConnection();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion,
					sql, false,idLocation, obj.getTypeLocation(), idLocataire, idMateriel,
					obj.getDatePaiement(), obj.getMontantCheque(),
					obj.getNumCheque(), obj.getDateEncaissement());
			
			requete.executeUpdate();
		} catch (SQLException e){
			throw new DAOException(e);
		} finally {
			DAOUtilitaires.fermeturesSilencieuses(result, requete, connexion);
		}
		
		return obj;
	}

	@Override
	public ChequeLocation get(long id) {
		ChequeLocation chequelocation = null;
		Connection connexion = null;
		PreparedStatement requete = null;
		ResultSet result = null;
		String sql = "SELECT * FROM contratlocation cl "
				+ "JOIN location l ON cl.idLocation = l.idLocation "
				+ "WHERE idLocation = ?;";
		try{
			connexion = factory.getConnection();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion, sql, false, id);
			result = requete.executeQuery();
			
			if(result.first()){
				chequelocation = this.map(result);
			}
		} catch (SQLException | ParseException e){
			throw new DAOException(e);
		} finally {
			DAOUtilitaires.fermeturesSilencieuses(result, requete, connexion);
		}
		return chequelocation;
	}

	@Override
	public List<ChequeLocation> getAll() {
		List<ChequeLocation> liste = new ArrayList<>();
		
		ChequeLocation chequelocation = null;
		Connection connexion = null;
		PreparedStatement requete = null;
		ResultSet result = null;
		String sql = "SELECT * FROM contratlocation cl "
				+ "JOIN location l ON l.idLocation = cl.idLocation ;";
		try {
			
			connexion = factory.getConnection();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion, sql, false);
			result = requete.executeQuery();
			
			while (result.next()){
				chequelocation = this.map(result);
				liste.add(chequelocation);
			}
		} catch (SQLException | ParseException e){
			throw new DAOException(e);
		} finally {
			DAOUtilitaires.fermeturesSilencieuses(result, requete, connexion);
		}
		
		return liste;
	}

	@Override
	protected ChequeLocation map(ResultSet result) throws SQLException, ParseException {
		LocationDAO locationDAO = factory.getLocationDAO();
		
		
		ChequeLocation chequelocation = new ChequeLocation(locationDAO.map(result),
				result.getString("datePaiement"),
				result.getFloat("montantCheque"),
				result.getLong("numCheque"),
				result.getString("dateEncaissement"));
		
		return chequelocation;
	}

	
}
