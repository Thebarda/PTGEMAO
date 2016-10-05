package fr.gemao.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.gemao.entity.personnel.Contrat;
import fr.gemao.entity.personnel.MotifFinContrat;
import fr.gemao.entity.personnel.Personnel;
import fr.gemao.entity.personnel.TypeContrat;
import fr.gemao.sql.exception.DAOException;
import fr.gemao.sql.util.DAOUtilitaires;
import fr.gemao.sql.util.NumberUtil;
import fr.gemao.util.Password;

public class ContratDAO extends IDAO<Contrat> {

	public ContratDAO(DAOFactory factory) {
		super(factory);
	}
	
	@Override
	public Contrat create(Contrat obj) {
		throw new UnsupportedOperationException("Vous n'avez tous bonnement pas le droit");
	}

	public Contrat create(Contrat obj, Long idPersonne) {
		if (obj == null) {
			throw new NullPointerException("Le contrat ne doit pas etre null");
		}

		int id = 0;
		Connection connexion = null;
		PreparedStatement requete = null;
		ResultSet result = null;

		String sql = "INSERT INTO contrat(idTypeContrat, idPersonne, idMotifFin, dateDebut, dateFin, dateRupture)"
				+ "VALUES (?, ?, ?, ?, ?, ?);";
		try {
			connexion = factory.getConnection();
			
			MotifFinContratDAO motifFinContratDAO = factory.getMotifFinContratDAO();
			MotifFinContrat motif = obj.getMotifFinContrat();
			Integer idMotif = null;
			if(motif != null){
				motif = motifFinContratDAO.create(motif);
				obj.setMotifFinContrat(motif);
				idMotif = motif.getIdMotif();
			}
			
			TypeContrat type = obj.getTypeContrat();
			Integer idType = type==null?null:type.getIdContrat();

			
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion,
					sql, true, idType, idPersonne, idMotif,
					obj.getDateDebut(), obj.getDateFin(), obj.getDateRupture());

			int status = requete.executeUpdate();
			if (status == 0) {
				throw new DAOException(
						"Échec de la création du contrat, aucune ligne ajoutée dans la table.");
			}
			
			result = requete.getGeneratedKeys();
			if (result != null && result.first()) {
				id = result.getInt(1);
				obj.setIdContrat(id);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			DAOUtilitaires.fermeturesSilencieuses(result, requete, connexion);
		}

		return obj;
	}

	@Override
	public void delete(Contrat obj) {
		throw new UnsupportedOperationException("Méthode non implémentée.");
	}

	@Override
	public Contrat update(Contrat obj) {
		throw new UnsupportedOperationException("Méthode non implémentée.");
	}

/*	@Override
	public Contrat update(Contrat obj) {
		if (obj == null) {
			throw new NullPointerException("Le personnel ne doit pas être null");
		}
		
		Connection connexion = null;
		PreparedStatement requete = null;
		ResultSet result = null;
		
		Integer idContrat = null;
		if(obj.getIdContrat() != null){
			idContrat = obj.getIdContrat();
		}
		
		String sql = "UPDATE contrat SET idContrat = ?, idPersonne = ?, idTypeContrat = ?, idMotifFin = ?,"
				+ "dateDebut = ?, dateFin = ?, dateRupture = ?;";

		try {
			connexion = factory.getConnection();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion, sql, false, 
					obj.getIdContrat(),
					//idpersonne,
					obj.getTypeContrat(),
					obj.getMotifFinContrat(),
					obj.getDateDebut(),
					obj.getDateFin(),
					obj.getDateRupture());
			requete.executeUpdate();

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			DAOUtilitaires.fermeturesSilencieuses(result, requete, connexion);
		}

		return this.get(obj.getIdContrat());
	}
	*/
	@Override
	public Contrat get(long id) {
		Contrat contrat = null;
		Connection connexion = null;
		PreparedStatement requete = null;
		ResultSet result = null;
		try {
			String sql = "SELECT * FROM contrat WHERE idContrat = ?;";
			connexion = factory.getConnection();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion,
					sql, false, id);
			result = requete.executeQuery();

			if (result.first()) {
				contrat = this.map(result);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			DAOUtilitaires.fermeturesSilencieuses(result, requete, connexion);
		}

		return contrat;
	}

	@Override
	public List<Contrat> getAll() {
		throw new UnsupportedOperationException("Méthode non implémentée.");
	}
	
	public List<Contrat> getContratsParPersonne(Long idPersonnel){
		List<Contrat> liste = new ArrayList<>();
		Contrat contrat = null;
		Connection connexion = null;
		PreparedStatement requete = null;
		ResultSet result = null;
		
		String sql = "SELECT * FROM contrat Where idPersonne=?;";
		
		try {
			connexion = factory.getConnection();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion, sql, false, idPersonnel);
			result = requete.executeQuery();
			
			while (result.next()) {
				contrat = this.map(result);
				liste.add(contrat);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			DAOUtilitaires.fermeturesSilencieuses(result, requete, connexion);
		}

		return liste;
	}
	
	public List<Contrat> addAllContratParPersonnel(List<Contrat> contrats, Personnel personnel){
		List<Contrat> listRes = new ArrayList<>();
		Contrat res = null;
		for(Contrat contrat : contrats){
			res = this.create(contrat, personnel.getIdPersonne());
			listRes.add(res);
		}
		return listRes;
	}

	@Override
	protected Contrat map(ResultSet result) throws SQLException {
		TypeContratDAO contratDAO = factory.getTypeContratDAO();
		MotifFinContratDAO motifDAO = factory.getMotifFinContratDAO();
		Contrat contrat = new Contrat();
		Integer idMotifFin = result.getInt("idMotifFin");
		contrat.setIdContrat(NumberUtil.getResultInteger(result, "idContrat"));
		contrat.setDateDebut(result.getDate("dateDebut"));
		contrat.setDateFin(result.getDate("dateFin"));
		contrat.setDateRupture(result.getDate("dateRupture"));
		contrat.setMotifFinContrat(idMotifFin==null?null:motifDAO.get(idMotifFin));
		contrat.setTypeContrat(contratDAO.get(result.getInt("idTypeContrat")));
		return contrat;
	}



}
