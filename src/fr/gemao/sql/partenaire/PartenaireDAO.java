package fr.gemao.sql.partenaire;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import fr.gemao.ctrl.AdresseCtrl;
import fr.gemao.entity.materiel.ChequeLocation;
import fr.gemao.entity.partenaire.Partenaire;
import fr.gemao.sql.DAOFactory;
import fr.gemao.sql.IDAO;
import fr.gemao.sql.exception.DAOException;
import fr.gemao.sql.util.DAOUtilitaires;

public class PartenaireDAO extends IDAO<Partenaire>{

	public PartenaireDAO(DAOFactory factory) {
		super(factory);
	}

	@Override
	public Partenaire create(Partenaire obj) {
		if (obj==null){
			throw new NullPointerException("Le chèque ne peut pas être null");
		}
		Connection connexion = null;
		PreparedStatement requete = null;
		ResultSet result = null;
		String sql = "INSERT INTO partenaire(raisonSociale, idAdresse, annee, taillePage) VALUES (?,?,?,?);";
		
		int idLocation = 0;
		Long idLocataire = null;
		Long idMateriel = null;
		try{
			connexion = factory.getConnection();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion, sql, false, obj.getRaisonSociale(), obj.getAdresse().getIdAdresse(), obj.getAnnee(), obj.getTaillePage());
			
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
	public void delete(Partenaire obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Partenaire update(Partenaire obj) {
		// TODO Auto-generated method stub
				return null;
	}

	@Override
	public Partenaire get(long id) {
		Partenaire partenaire = null;
		Connection connexion = null;
		PreparedStatement requete = null;
		ResultSet result = null;
		String sql = "SELECT * FROM partenaire WHERE idPartenaire= ?;";
		try {
			connexion = factory.getConnection();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion, sql, false, id);
			result = requete.executeQuery();
			
			while (result.next()){
				partenaire = this.map(result);
			}
		} catch (SQLException | ParseException e){
			throw new DAOException(e);
		} finally {
			DAOUtilitaires.fermeturesSilencieuses(result, requete, connexion);
		}
		return partenaire;
	}

	@Override
	public List<Partenaire> getAll() throws ParseException {
		List<Partenaire> partenaires = new ArrayList<>();
		Partenaire partenaire = null;
		Connection connexion = null;
		PreparedStatement requete = null;
		ResultSet result = null;
		String sql = "SELECT * FROM partenaire;";
		try {
			connexion = factory.getConnection();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion, sql, false);
			result = requete.executeQuery();
			
			while (result.next()){
				partenaire = this.map(result);
				partenaires.add(partenaire);
			}
		} catch (SQLException | ParseException e){
			throw new DAOException(e);
		} finally {
			DAOUtilitaires.fermeturesSilencieuses(result, requete, connexion);
		}
		
		return partenaires;
	}

	@Override
	protected Partenaire map(ResultSet result) throws SQLException, ParseException {
		return new Partenaire(result.getInt("idPartenaire"), result.getString("raisonSociale"), AdresseCtrl.recupererAdresse(result.getInt("idAdresse")), result.getInt("annee"), result.getString("taillePage"));
	}

	public void updateTaillePage(Integer idPartenaire, String taillePage) {
		Partenaire partenaire = null;
		Connection connexion = null;
		PreparedStatement requete = null;
		Integer result = null;
		String sql = "UPDATE partenaire SET taillePage = ? WHERE idPartenaire=?;";
		try {
			connexion = factory.getConnection();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion, sql, false, taillePage, idPartenaire);
			result = requete.executeUpdate();
		} catch (SQLException e){
			throw new DAOException(e);
		} finally {
			DAOUtilitaires.fermeturesSilencieuses(requete, connexion);
		}
	}

	

}
