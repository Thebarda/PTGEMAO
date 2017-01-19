package fr.gemao.sql.cheque;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import fr.gemao.ctrl.location.LocationCtrl;
import fr.gemao.entity.materiel.ChequeLocation;
import fr.gemao.entity.materiel.Location;
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
		String sql = "INSERT INTO cheque(idLocation, typeLocation, idLocataire, idMateriel, datePaiement, montantCheque, numCheque, dateEncaissement) VALUES (?,?,?,?,?,?,?,?);";
		
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
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion, sql, false,idLocation, obj.getTypeLocation(), idLocataire, idMateriel, obj.getDatePaiement(), obj.getMontantCheque(), obj.getNumCheque(), obj.getDateEncaissement());
			
			int status = requete.executeUpdate();
			
			if(status == 0){
				throw new DAOException(
						"Echec de la création de l'adhérent, aucune ligne ajoutée dans la table.");
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
		if (obj == null){
			throw new NullPointerException("Le cheque ne doit pas etre null");
		}
		
		Connection connexion = null;
		PreparedStatement requete = null;
		String sql = "DELETE FROM cheque WHERE numCheque = ?;";
		try {
			connexion = factory.getConnection();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion, sql, false, obj.getNumCheque());
			requete.executeUpdate();
		} catch ( SQLException e){
			throw new DAOException(e);
		} finally {
			DAOUtilitaires.fermeturesSilencieuses(requete, connexion);
		}
	}

	@Override
	public ChequeLocation update(ChequeLocation obj) {
		if (obj == null){
			throw new NullPointerException("La personne ne doit pas être null");
		}
		
		Connection connexion = null;
		PreparedStatement requete = null;
		ResultSet result = null;
		String sql = "UPDATE cheque SET idLocation = ?, typeLocation = ?, idLocataire = ?, idMateriel = ?, datePaiement = ?, montantCheque = ?, numCheque = ?, dateEncaissement = ? WHERE idLocation = ?;";
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
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion, sql, false,idLocation, obj.getTypeLocation(), idLocataire, idMateriel, obj.getDatePaiement(), obj.getMontantCheque(), obj.getNumCheque(), obj.getDateEncaissement());
			
			requete.executeUpdate();
		} catch (SQLException e){
			throw new DAOException(e);
		} finally {
			DAOUtilitaires.fermeturesSilencieuses(result, requete, connexion);
		}
		
		return obj;
	}

	@Override
	public ChequeLocation get(long numCheque) {
		ChequeLocation chequelocation = null;
		Connection connexion = null;
		PreparedStatement requete = null;
		ResultSet result = null;
		String sql = "SELECT * FROM cheque WHERE numCheque = ?;";
		try{
			connexion = factory.getConnection();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion, sql, false, numCheque);
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
		String sql = "SELECT * FROM cheque c JOIN location l ON l.id_loc = c.idLocation ORDER BY c.idLocation;";
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
		
		//Ajouter une méthode dans LocationCtrl pour récupérer une location par son id
		//OU Creer une classe métier qui comprend uniquement l'id
		ChequeLocation chequelocation = new ChequeLocation(LocationCtrl.getLocationById(result.getInt("idLocation")),
				result.getString("datePaiement"),
				result.getFloat("montantCheque"),
				result.getLong("numCheque"),
				result.getString("dateEncaissement"));
		
		return chequelocation;
	}

	public List<ChequeLocation> getByMonthYear(String month, String year) {
		List<ChequeLocation> liste = new ArrayList<>();
		
		ChequeLocation chequelocation = null;
		Connection connexion = null;
		PreparedStatement requete = null;
		ResultSet result = null;
		String sql = "SELECT * FROM cheque WHERE (SELECT SUBSTRING(datePaiement, 4, 2)) = ? AND (SELECT SUBSTRING(datePaiement, 7, 4)) = ?";
		
		try {
			
			connexion = factory.getConnection();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion, sql, false, month, year);
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
	
}
