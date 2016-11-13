package fr.gemao.sql.location;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import fr.gemao.entity.cours.Discipline;
import fr.gemao.entity.materiel.Location;
import fr.gemao.entity.materiel.Materiel;
import fr.gemao.sql.DAOFactory;
import fr.gemao.sql.IDAO;
import fr.gemao.sql.exception.DAOException;
import fr.gemao.sql.util.DAOUtilitaires;
import fr.gemao.sql.util.NumberUtil;

public class LocationDAO extends IDAO<Location>{

	public LocationDAO(DAOFactory factory) {
		super(factory);
	}
	

	public boolean create(String idPersonne, String idMateriel,
			String etatDebut, String dateEmprunt, String dateFin, float caution, float montant) {
		Connection connexion = null;
		PreparedStatement requete = null;
		ResultSet result = null;
		String sql = "INSERT INTO location(idPersonne, idMateriel, idEtatDebut, idEtatFin, "
				+ "idReparation, dateEmprunt,dateEcheance, dateRetour, caution, montant) VALUES "
				+ "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
		try {
			connexion = factory.getConnection();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion,
					sql, false,
					idPersonne,
					idMateriel,
					etatDebut,
					null,
					null,
					dateEmprunt,
					dateFin,
					null,
					caution,
					montant
			);

			int status = requete.executeUpdate();

			if (status == 0) {
				throw new DAOException(
						"Échec de la création de la location, aucune ligne ajoutée dans la table.");
			}

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			DAOUtilitaires.fermeturesSilencieuses(result, requete, connexion);
		}

		return true;
	}

	@Override
	public void delete(Location obj) {
		throw new UnsupportedOperationException("Méthode non implémentée.");
	}

	@Override
	public Location update(Location obj) {
		throw new UnsupportedOperationException("Méthode non implémentée.");
	}

	@Override
	public Location get(long id) {
		throw new UnsupportedOperationException("Méthode non implémentée.");
	}

	@Override
	public List<Location> getAll() {
		throw new UnsupportedOperationException("Méthode non implémentée.");
	}
	
	public List<Location> getLocationParAdherent(long idAdherent){
		throw new UnsupportedOperationException("Méthode non implémentée.");
	}

	@Override
	protected Location map(ResultSet result) throws SQLException {
		Integer idEtatFin = result.getInt("idEtatFin"),
				idReparation = result.getInt("idReparation");
		return new Location(
				factory.getPersonneDAO().get(result.getInt("idPersonne")),
				factory.getMaterielDAO().get(result.getInt("idMateriel")),
				factory.getEtatDAO().get(result.getInt("idEtatDebut")),
				idEtatFin==null?null:factory.getEtatDAO().get(idEtatFin),
				result.getDate("dateEmprunt"),
				result.getDate("dateRetour"),
				result.getDate("dateEcheance"),
				result.getInt("caution"),
				result.getFloat("montant"),
				idReparation==null?null:factory.getReparationDAO().get(idReparation));
	}

	@Override
	public Location create(Location obj) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public int getNbLocation(){
		Connection connexion = null;
		PreparedStatement requete = null;
		ResultSet result = null;
		String sql = "SELECT COUNT(idPersonne) FROM location";
		int res=0;
		try {
			connexion = factory.getConnection();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion,
					sql, false);
			result = requete.executeQuery();
			res= result.getInt(0);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}

}
