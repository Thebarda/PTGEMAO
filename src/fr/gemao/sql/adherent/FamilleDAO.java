package fr.gemao.sql.adherent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.gemao.entity.adherent.Famille;
import fr.gemao.entity.adherent.FamilleTableaux;
import fr.gemao.sql.DAOFactory;
import fr.gemao.sql.IDAO;
import fr.gemao.sql.exception.DAOException;
import fr.gemao.sql.util.DAOUtilitaires;

public class FamilleDAO extends IDAO<Famille> {

	public FamilleDAO(DAOFactory factory) {
		super(factory);
	}

	@Override
	public Famille create(Famille obj) {
		if (obj == null) {
			throw new NullPointerException("L'adhérent ne doit pas être null");
		}

		int id = 0;
		Connection connexion = null;
		PreparedStatement requete = null;
		ResultSet result = null;

		String sql = "INSERT INTO famille (nomFamille) VALUES (?)";

		try {
			connexion = factory.getConnection();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion,
					sql, true, obj.getNomFamille());

			int status = requete.executeUpdate();
			if (status == 0) {
				throw new DAOException(
						"Échec de la création de la famille, aucune ligne ajoutée dans la table.");
			}

			result = requete.getGeneratedKeys();
			if (result != null && result.first()) {
				id = result.getInt(1);
				obj.setIdFamille(id);
			}

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			DAOUtilitaires.fermeturesSilencieuses(result, requete, connexion);
		}

		return obj;
	}

	@Override
	public void delete(Famille obj) {
		throw new UnsupportedOperationException(
				"Vous n'avez pas le droit de supprimer une Famille.");

	}

	@Override
	public Famille update(Famille obj) {
		throw new UnsupportedOperationException(
				"Vous n'avez pas le droit de modifier une Famille.");
	}

	@Override
	public Famille get(long id) {
		Famille famille = null;
		Connection connexion = null;
		PreparedStatement requete = null;
		ResultSet result = null;
		String sql = "SELECT * FROM famille WHERE idFamille = ?;";
		try {

			connexion = factory.getConnection();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion,
					sql, false, id);
			result = requete.executeQuery();

			if (result.first()) {
				famille = this.map(result);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			DAOUtilitaires.fermeturesSilencieuses(result, requete, connexion);
		}
		return famille;
	}

	@Override
	public List<Famille> getAll() {
		List<Famille> liste = new ArrayList<>();

		Famille famille = null;
		Connection connexion = null;
		PreparedStatement requete = null;
		ResultSet result = null;
		String sql = "SELECT * FROM famille;";
		try {

			connexion = factory.getConnection();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion,
					sql, false);
			result = requete.executeQuery();

			while (result.next()) {
				famille = this.map(result);
				liste.add(famille);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			DAOUtilitaires.fermeturesSilencieuses(result, requete, connexion);
		}

		return liste;
	}

	/**
	 * Test si une famille existe déjà. Test sur nomFamille;
	 * 
	 * @param famille
	 * @return null si n'existe pas.
	 */
	public Famille exits(Famille famille) {
		Connection connexion = null;
		PreparedStatement requete = null;
		ResultSet result = null;
		String sql = "SELECT * from famille where nomFamille = ?;";
		Famille verif = null;
		try {

			connexion = factory.getConnection();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion,
					sql, false, famille.getNomFamille());
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

	@Override
	protected Famille map(ResultSet result) throws SQLException {
		return new Famille(result.getInt("idFamille"),
				result.getString("nomFamille"));
	}
	
	protected FamilleTableaux mapFamTab(ResultSet result) throws SQLException {
		return new FamilleTableaux(result.getInt("idFamille"),
				result.getInt("annee"), result.getString("tableauFicheComptable"), result.getString("tableauRecapitulatif"));
	}

	public void updateTableaux(String tfc, String recap, int idFamille, int annee) {
		Famille famille = null;
		Connection connexion = null;
		PreparedStatement requete = null;
		int result = 0;
		String sql = "UPDATE familletableaux SET tableauFicheComptable=?, tableauRecapitulatif=? WHERE idFamille = ? AND annee = ?;";
		try {
			connexion = factory.getConnection();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion,
					sql, false, tfc, recap, idFamille, annee);
			result = requete.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			DAOUtilitaires.fermetureSilencieuse(connexion);
		}
	}

	public void ajouterFamilleTableaux(FamilleTableaux famtab) {
		Connection connexion = null;
		PreparedStatement requete = null;
		ResultSet result = null;

		String sql = "INSERT INTO familletableaux (idFamille, annee, tableauFicheComptable, tableauRecapitulatif) VALUES (?, ?, ?, ?)";

		try {
			connexion = factory.getConnection();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion,
					sql, true, famtab.getIdFamille(), famtab.getAnnee(), famtab.getTfc(), famtab.getTr());

			int status = requete.executeUpdate();
			if (status == 0) {
				throw new DAOException(
						"Échec de la création de la famille, aucune ligne ajoutée dans la table.");
			}

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			DAOUtilitaires.fermeturesSilencieuses(result, requete, connexion);
		}
	}

	public List<FamilleTableaux> getFamilleTableaux(int idFamille, int annee) {
		List<FamilleTableaux> liste = new ArrayList<>();

		FamilleTableaux famtab = null;
		Connection connexion = null;
		PreparedStatement requete = null;
		ResultSet result = null;
		String sql = "SELECT * FROM familletableaux ft INNER JOIN famille f ON f.idFamille = ft.idFamille where f.idFamille=? and annee=?;";
		try {

			connexion = factory.getConnection();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion,
					sql, false, idFamille, annee);
			result = requete.executeQuery();

			while (result.next()) {
				famtab = this.mapFamTab(result);
				liste.add(famtab);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			DAOUtilitaires.fermeturesSilencieuses(result, requete, connexion);
		}

		return liste;
	}

	public int getFamille(String nomFamille) {
		Famille fam = null;
		Connection connexion = null;
		PreparedStatement requete = null;
		ResultSet result = null;
		String sql = "SELECT * FROM famille where nomFamille=?;";
		try {

			connexion = factory.getConnection();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion,
					sql, false, nomFamille);
			result = requete.executeQuery();

			while (result.next()) {
				fam = this.map(result);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			DAOUtilitaires.fermeturesSilencieuses(result, requete, connexion);
		}

		return fam.getIdFamille();
	}

	public int getNbEleves(int idFamille) {
		int nbEleves=0;
		Connection connexion = null;
		PreparedStatement requete = null;
		ResultSet result = null;
		String sql = "SELECT COUNT(idPersonne) as NbEleves FROM adherent where idFamille=?;";
		try {

			connexion = factory.getConnection();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion,
					sql, false, idFamille);
			result = requete.executeQuery();

			while (result.next()) {
				nbEleves = result.getInt("NbEleves");
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			DAOUtilitaires.fermeturesSilencieuses(result, requete, connexion);
		}

		return nbEleves;
	}

	public int getNbElevesByNomFamille(String famille) {
		int nbEleves=0;
		Connection connexion = null;
		PreparedStatement requete = null;
		ResultSet result = null;
		String sql = "SELECT COUNT(idPersonne) as NbEleves FROM adherent a INNER JOIN famille f ON a.idFamille=f.idFamille where nomFamille=?;";
		try {

			connexion = factory.getConnection();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion,
					sql, false, famille);
			result = requete.executeQuery();

			while (result.next()) {
				nbEleves = result.getInt("NbEleves");
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			DAOUtilitaires.fermeturesSilencieuses(result, requete, connexion);
		}

		return nbEleves;
	}

	public List<String> getEleves(int idFamille) {
		List<String> liste = new ArrayList<>();

		FamilleTableaux famtab = null;
		Connection connexion = null;
		PreparedStatement requete = null;
		ResultSet result = null;
		String sql = "SELECT prenom FROM adherent a INNER JOIN personne p ON p.idPersonne=a.idPersonne WHERE idFamille=?;";
		try {

			connexion = factory.getConnection();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion,
					sql, false, idFamille);
			result = requete.executeQuery();

			while (result.next()) {
				liste.add(result.getString("prenom"));
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			DAOUtilitaires.fermeturesSilencieuses(result, requete, connexion);
		}

		return liste;
	}
}
