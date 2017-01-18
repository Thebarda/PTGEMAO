package fr.gemao.sql.location;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.mysql.jdbc.Statement;

import fr.gemao.entity.adherent.Adherent;
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
	

	public int create(String idPersonne, String idMateriel,
			String etatDebut, String dateEmprunt, String dateFin, float caution, float montant, String nomContrat) {
		Connection connexion = null;
		PreparedStatement requete = null;
		ResultSet result = null;
		int status = 0;
		String sql = "INSERT INTO location(idPersonne, idMateriel, idEtatDebut, idEtatFin, "
				+ "idReparation, dateEmprunt,dateEcheance, dateRetour, caution, montant, nomContrat) VALUES "
				+ "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
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
					montant,
					nomContrat
			);

			status = requete.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			DAOUtilitaires.fermeturesSilencieuses(result, requete, connexion);
		}

		return status;
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
		List<Location> liste = new ArrayList<>();

		Location location = null;
		Connection connexion = null;
		PreparedStatement requete = null;
		ResultSet result = null;
		String sql = "SELECT * FROM location where dateRetour IS NULL;";
		try {

			connexion = factory.getConnection();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion,
					sql, false);
			result = requete.executeQuery();

			while (result.next()) {
				location = this.map(result);
				liste.add(location);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			DAOUtilitaires.fermeturesSilencieuses(result, requete, connexion);
		}

		return liste;
	}
	
	public List<Location> getLocationParAdherent(long idAdherent){
		throw new UnsupportedOperationException("Méthode non implémentée.");
	}

	@Override
	public Location map(ResultSet result) throws SQLException {
		Integer idEtatFin = result.getInt("idEtatFin"),
				idReparation = result.getInt("idReparation");
		return new Location(
				result.getInt("id_loc"),
				factory.getPersonneDAO().get(result.getInt("idPersonne")),
				factory.getMaterielDAO().get(result.getInt("idMateriel")),
				factory.getEtatDAO().get(result.getInt("idEtatDebut")),
				idEtatFin==null?null:factory.getEtatDAO().get(idEtatFin),
				result.getString("dateEmprunt"),
				result.getString("dateRetour"),
				result.getString("dateEcheance"),
				result.getInt("caution"),
				result.getFloat("montant"),
				idReparation==null?null:factory.getReparationDAO().get(idReparation),
				result.getString("nomContrat"));
	}
	
	protected Location map2(ResultSet result) throws SQLException {
		Integer idEtatFin = result.getInt("idEtatFin"),
				idReparation = result.getInt("idReparation");
		String dateRetour = result.getString("dateRetour");
			return new Location(
					result.getInt("id_loc"),
					factory.getPersonneDAO().get(result.getInt("idPersonne")),
					factory.getMaterielDAO().get(result.getInt("idMateriel")),
					factory.getEtatDAO().get(result.getInt("idEtatDebut")),
					idEtatFin==null?null:factory.getEtatDAO().get(idEtatFin),
					result.getString("dateEmprunt"),
					dateRetour==null?null:dateRetour,
					result.getString("dateEcheance"),
					result.getInt("caution"),
					result.getFloat("montant"),
					idReparation==null?null:factory.getReparationDAO().get(idReparation),
					result.getString("nomContrat"));
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
		String sql = "SELECT COUNT(idPersonne) AS nbLoc FROM location";
		int res=0;
		try {
			connexion = factory.getConnection();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion,
					sql, false);
			result = requete.executeQuery();
			while (result.next()) {
				res = result.getInt("nbLoc");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}
	
	public List<Location> getAllAll() {
		List<Location> liste = new ArrayList<>();

		Location location = null;
		Connection connexion = null;
		PreparedStatement requete = null;
		ResultSet result = null;
		String sql = "SELECT * FROM location l INNER JOIN personne p ON l.idPersonne=p.idPersonne ORDER BY p.nom ;";
		try {
			connexion = factory.getConnection();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion,
					sql, false);
			result = requete.executeQuery();

			while (result.next()) {
				location = this.map(result);
				liste.add(location);
			}
		} catch (SQLException e1) {
			throw new DAOException(e1);
		} finally {
			DAOUtilitaires.fermeturesSilencieuses(result, requete, connexion);
		}

		return liste;
	}


	public String getTypeLocation(int idPersonne) {
		Location location = null;
		String type = "";
		Connection connexion = null;
		PreparedStatement requete = null;
		ResultSet result = null;
		String sql = "SELECT idPersonne FROM adherent where idPersonne = ?;";
		try {
			connexion = factory.getConnection();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion,
					sql, false, idPersonne);
			result = requete.executeQuery();
			if(!result.next()){
				type="Externe";
			}else{
				type="Interne";
			}
		} catch (SQLException e1) {
			throw new DAOException(e1);
		} finally {
			DAOUtilitaires.fermeturesSilencieuses(result, requete, connexion);
		}
		return type;
	}


	public List<Location> getLocsByYear(int year) {
		List<Location> liste = new ArrayList<>();

		Location location = null;
		Connection connexion = null;
		PreparedStatement requete = null;
		ResultSet result = null;
		String sql = "SELECT * FROM location l INNER JOIN personne p ON l.idPersonne=p.idPersonne WHERE LOCATE(?,dateEmprunt) ORDER BY p.nom ;";
		try {
			connexion = factory.getConnection();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion,
					sql, false, year);
			result = requete.executeQuery();

			while (result.next()) {
				location = this.map(result);
				liste.add(location);
			}
		} catch (SQLException e1) {
			throw new DAOException(e1);
		} finally {
			DAOUtilitaires.fermeturesSilencieuses(result, requete, connexion);
		}

		return liste;
	}


	public void updateRetourLocation(int id, String dateRetour, int etatFin) {
		Connection connexion = null;
		PreparedStatement requete = null;
		int result = 0;
		String sql = "UPDATE location set dateRetour=?, idEtatFin=? WHERE id_loc=?";
		try {
			connexion = factory.getConnection();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion,
					sql, false, dateRetour, etatFin, id);
			result = requete.executeUpdate();
		} catch (SQLException e1) {
			throw new DAOException(e1);
		} finally {
			DAOUtilitaires.fermeturesSilencieuses(requete, connexion);
		}
	}

	public String getNomContratById(int id){
		Connection connexion = null;
		PreparedStatement requete = null;
		ResultSet result = null;
		String sql = "SELECT nomContrat FROM location WHERE id_loc=?";
		String res= null;
		try {
			connexion = factory.getConnection();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion,
					sql, false, id);
			result = requete.executeQuery();
			while (result.next()) {
				res = result.getString("nomContrat");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}
}
