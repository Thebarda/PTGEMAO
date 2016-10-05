package fr.gemao.sql.location;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
	
	@Override
	public Location create(Location obj) {
		throw new UnsupportedOperationException("Méthode non implémentée.");
		/*if (obj == null) {
			throw new NullPointerException("L'objet location ne doit pas être null");
		}
		Connection connexion = null;
		PreparedStatement requete = null;
		ResultSet result = null;
		String sql = "INSERT INTO location(idPersonne, idMateriel, idEtatDebut, idEtatFin, "
				+ "idReparation, dateEmprunt,dateEcheance, dateRetour, montant) VALUES "
				+ "(?, ?, ?, ?, ?, ?, ?, ?, ?);";
		try {
			connexion = factory.getConnection();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion,
					sql, false,
					obj.getPersonne().getIdPersonne(),
					obj.getMateriel().getIdMateriel(),
					obj.getEtatDebut().getIdEtat(),
					obj.getEtatFin().getIdEtat(),
					obj.getReparation().getIdReparation(),
					obj.getDateEmprunt(),
					obj.getDateEcheance(),
					obj.getDateRetour(),
					obj.getMontant()
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

		return this.get(obj.getIdDiscipline());*/
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
				result.getFloat("montant"),
				idReparation==null?null:factory.getReparationDAO().get(idReparation));
	}

}
