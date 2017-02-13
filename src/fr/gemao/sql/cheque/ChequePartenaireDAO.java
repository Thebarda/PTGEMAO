package fr.gemao.sql.cheque;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import fr.gemao.ctrl.location.LocationCtrl;
import fr.gemao.ctrl.partenaire.PartenaireCtrl;
import fr.gemao.entity.materiel.ChequeLocation;
import fr.gemao.entity.partenaire.ChequePartenaire;
import fr.gemao.entity.partenaire.Partenaire;
import fr.gemao.sql.DAOFactory;
import fr.gemao.sql.IDAO;
import fr.gemao.sql.exception.DAOException;
import fr.gemao.sql.location.LocationDAO;
import fr.gemao.sql.util.DAOUtilitaires;

public class ChequePartenaireDAO extends IDAO<ChequePartenaire>{

	public ChequePartenaireDAO(DAOFactory factory) {
		super(factory);
		// TODO Auto-generated constructor stub
	}

	@Override
	public ChequePartenaire create(ChequePartenaire obj) {
		Connection connexion = null;
		PreparedStatement requete = null;
		ResultSet result = null;
		String sql = "INSERT INTO chequepartenaire(idPartenaire, datePaiement, montantCheque, numeroCheque, dateEncaissement) VALUES (?,?,?,?,?);";
		
		try{
			connexion = factory.getConnection();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion, sql, false, obj.getPartenaire().getIdPartenaire(), obj.getDatePaiement(), obj.getMontant(), obj.getNumero(), obj.getDateEncaissement());
			
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
	public void delete(ChequePartenaire obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ChequePartenaire update(ChequePartenaire obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ChequePartenaire get(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ChequePartenaire> getAll() throws ParseException {
		List<ChequePartenaire> cheques = new ArrayList<>();
		ChequePartenaire chequePartenaire = null;
		Connection connexion = null;
		PreparedStatement requete = null;
		ResultSet result = null;
		String sql = "SELECT * FROM chequePartenaire;";
		
		try{
			connexion = factory.getConnection();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion, sql, false);
			result = requete.executeQuery();
			
			while (result.next()){
				chequePartenaire = this.map(result);
				cheques.add(chequePartenaire);
			}
		} catch (SQLException e ){
			throw new DAOException(e);
			
		}finally {
			DAOUtilitaires.fermeturesSilencieuses(result, requete, connexion);
		}
		
		return cheques;
	}

	@Override
	protected ChequePartenaire map(ResultSet result) throws SQLException, ParseException {
		//Ajouter une méthode dans LocationCtrl pour récupérer une location par son id
		//OU Creer une classe métier qui comprend uniquement l'id
		ChequePartenaire chequePartenaire = new ChequePartenaire(result.getInt("idCheque"),
				result.getLong("numeroCheque"),
				result.getInt("montantCheque"),
				result.getString("datePaiement"),
				result.getString("dateEncaissement"),
				result.getString("dateEncaissementEffective"),
				PartenaireCtrl.getPartenaireById(result.getInt("idPartenaire")));
		
		return chequePartenaire;
	}

	public List<ChequePartenaire> getByMonthYear(String month, String year) {
		List<ChequePartenaire> liste = new ArrayList<>();
		
		ChequePartenaire chequePartenaire = null;
		Connection connexion = null;
		PreparedStatement requete = null;
		ResultSet result = null;
		String sql = "SELECT * FROM chequePartenaire WHERE (SELECT SUBSTRING(datePaiement, 4, 2)) = ? AND (SELECT SUBSTRING(datePaiement, 7, 4)) = ?";
		
		try {
			
			connexion = factory.getConnection();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion, sql, false, month, year);
			result = requete.executeQuery();
			
			while (result.next()){
				chequePartenaire = this.map(result);
				liste.add(chequePartenaire);
			}
		} catch (SQLException | ParseException e){
			throw new DAOException(e);
		} finally {
			DAOUtilitaires.fermeturesSilencieuses(result, requete, connexion);
		}
		
		return liste;
	}

	public void addDEEByNumCheque(String dEE, long numCheque) {
		Connection connexion = null;
		PreparedStatement requete = null;
		int result = 0;
		String sql = "UPDATE chequePartenaire SET dateEncaissementEffective = ? WHERE numeroCheque = ? ;";
		
		try {
			
			connexion = factory.getConnection();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion, sql, false, dEE, numCheque);
			result = requete.executeUpdate();
		} catch (SQLException e){
			throw new DAOException(e);
		} finally {
			DAOUtilitaires.fermeturesSilencieuses(requete, connexion);
		}
	}

}
