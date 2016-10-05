package fr.gemao.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.gemao.entity.personnel.Diplome;
import fr.gemao.sql.exception.DAOException;
import fr.gemao.sql.util.DAOUtilitaires;
import fr.gemao.sql.util.NumberUtil;

public class DiplomeDAO extends IDAO<Diplome> {

	public DiplomeDAO(DAOFactory factory) {
		super(factory);
	}

	@Override
	public Diplome create(Diplome obj) {
		if (obj == null) {
			throw new NullPointerException("Le  ne doit pas être null");
		}
		
		Connection connexion = null;
		PreparedStatement requete = null;
		ResultSet result = null;
		String sql = "INSERT INTO diplome(idDiplome, nomDiplome)"
				+ "VALUES (?, ?);";
		Integer id = null;
		try {
			connexion = factory.getConnection();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion, sql, true,
					obj.getIdDiplome(),
					obj.getNomDiplome());
			
			int status = requete.executeUpdate();
			
			if ( status == 0 ) {
	            throw new DAOException( "Échec de la création du diplome, aucune ligne ajoutée dans la table." );
	        }
			
			result = requete.getGeneratedKeys();
			if (result != null && result.first()) {
				id = result.getInt(1);
				obj.setIdDiplome(id);
			}

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			DAOUtilitaires.fermeturesSilencieuses(result, requete, connexion);
		}

		return obj;
	}

	@Override
	public void delete(Diplome obj) {
		throw new UnsupportedOperationException("Méthode non implémentée.");
	}

	@Override
	public Diplome update(Diplome obj) {
		throw new UnsupportedOperationException("Méthode non implémentée.");
	}

	@Override
	public Diplome get(long id) {
		throw new UnsupportedOperationException("Méthode non implémentée.");
	}

	@Override
	public List<Diplome> getAll() {
		throw new UnsupportedOperationException("Méthode non implémentée.");
	}
	
	/**
	 * Ajoute une liste de diplome dans la base. N'ajoute pas les
	 * éléments qui existe déjà.
	 * 
	 * @param liste
	 * @return
	 */
	public List<Diplome> createAll(List<Diplome> liste) {
		List<Diplome> result = new ArrayList<>();
		Diplome exist = null;
		for (Diplome resp : liste) {
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
	 * Retourne null si lz diplome n'existe pas dans le base de données;
	 * Compare le nomDiplome.
	 * @param diplome
	 * @return
	 */
	public Diplome exist(Diplome diplome) {
		Diplome verif = null;
		Connection connexion = null;
		PreparedStatement requete = null;
		ResultSet result = null;

		String sql = "SELECT * FROM diplome WHERE nomDiplome = ?;";

		try {
			connexion = factory.getConnection();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion,
					sql, false, diplome.getNomDiplome());
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
	 * Associe toutes une listes de diplome à une personne.
	 * @param idPersonne
	 * @param listeDiplome
	 * @return
	 */
	public List<Diplome> addAllDiplomesParPersonnel(long idPersonne, List<Diplome> listeDiplome){
		List<Diplome> results = new ArrayList<>();
		results = this.createAll(listeDiplome);
		Diplome d;
		for(Diplome dip : listeDiplome){
			if((d=this.exist(dip)) == null){
				dip = this.create(dip);
			}else{
				dip = d;
			}
			this.addDiplomeParPersonnel(idPersonne, dip);
		}
		return results;
	}
	
	/**
	 * Associe un diplome à une Personne
	 * @param idPersonne
	 * @param diplome
	 */
	public void addDiplomeParPersonnel(long idPersonne, Diplome diplome){
		if (diplome == null) {
			throw new NullPointerException(
					"Le diplome ne doit pas être null");
		}

		Connection connexion = null;
		PreparedStatement requete = null;
		ResultSet result = null;
		String sql = "INSERT INTO personneXdiplome(idDiplome, idPersonne)"
				+ "VALUES (?, ?);";
		
		try {
			connexion = factory.getConnection();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion,
					sql, false, diplome.getIdDiplome(), idPersonne);

			int status = requete.executeUpdate();

			if (status == 0) {
				throw new DAOException(
						"Échec de la création de l'association entre le diplome et la personne, aucune ligne ajoutée dans la table.");
			}

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			DAOUtilitaires.fermeturesSilencieuses(result, requete, connexion);
		}
	}
	
	/**
	 * Retourne une liste de diplome associé à une personne.
	 * @param idPersonne
	 * @return
	 */
	public List<Diplome> getDiplomesParPersonnel(Long idPersonne){
		Connection co = null;
		PreparedStatement state = null;
		ResultSet result = null;
		Diplome diplome = null;
		List<Diplome> liste = new ArrayList<>();
		
		String sql = "SELECT * FROM diplome d inner join personneXdiplome pd on d.idDiplome = pd.idDiplome WHERE idPersonne = ?;";
		
		try {
			co = factory.getConnection();
			state = DAOUtilitaires.initialisationRequetePreparee(co, sql, false, idPersonne);
			result = state.executeQuery();
			
			while (result.next()) {
				diplome = this.map(result);
				liste.add(diplome);
			}
			
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			DAOUtilitaires.fermeturesSilencieuses(result, state, co);
		}
		
		return liste;
	}

	@Override
	protected Diplome map(ResultSet result) throws SQLException {
		return new Diplome(NumberUtil.getResultInteger(result, "idDiplome"), result.getString("nomDiplome"));
	}

}
