package fr.gemao.sql.adherent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.gemao.entity.adherent.Adherent;
import fr.gemao.entity.cours.Classe;
import fr.gemao.entity.cours.Cours;
import fr.gemao.sql.DAOFactory;
import fr.gemao.sql.IDAO;
import fr.gemao.sql.PersonneDAO;
import fr.gemao.sql.cours.DisciplineDAO;
import fr.gemao.sql.exception.DAOException;
import fr.gemao.sql.util.DAOUtilitaires;
import fr.gemao.sql.util.DateUtil;
import fr.gemao.sql.util.NumberUtil;

public class AdherentDAO extends IDAO<Adherent> {

	public AdherentDAO(DAOFactory factory) {
		super(factory);
	}

	/**
	 * Appelle la méthode create de PersonneDAO
	 * {@link PersonneDAO#create(fr.gemao.entity.Personne)}
	 */
	@Override
	public Adherent create(Adherent obj) {
		if (obj == null) {
			throw new NullPointerException("L'adhérent ne doit pas être null");
		}
		Connection connexion = null;
		PreparedStatement requete = null;
		ResultSet result = null;
		String sql = "INSERT INTO adherent(idPersonne, idMotifSortie, idResponsable, idFamille, droitImage,"
				+ "	dateEntree, dateSortie, qf, cotisation, aPaye)"
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

		// PersonneDAO personneDAO = factory.getPersonneDAO();
		Integer idMotif = null;
		Integer idFamille = null;
		Long idResponsable = null;
		try {
			// obj = (Adherent) personneDAO.create(obj);
			if (obj.getResponsable() != null) {
				idResponsable = obj.getResponsable().getIdResponsable();
			}
			if (obj.getMotif() != null) {
				idMotif = obj.getAdresse().getIdAdresse();
			}
			if (obj.getFamille() != null) {
				idFamille = obj.getFamille().getIdFamille();
			}
			connexion = factory.getConnection();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion,
					sql, false, obj.getIdPersonne(), idMotif, idResponsable,
					idFamille, (obj.isDroitImage() ? 1 : 0),
					DateUtil.toSqlDate(obj.getDateEntree()),
					DateUtil.toSqlDate(obj.getDateSortie()), obj.getQf(),
					obj.getCotisation(), obj.isAPaye());

			int status = requete.executeUpdate();

			if (status == 0) {
				throw new DAOException(
						"Échec de la création de l'adhérent, aucune ligne ajoutée dans la table.");
			}

			if (obj.getDisciplines() != null) {
				DisciplineDAO disciplineDAO = factory.getDisciplineDAO();
				disciplineDAO.addAllDisciplineParAdherent(obj.getDisciplines(),
						obj.getIdPersonne());
			}

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			DAOUtilitaires.fermeturesSilencieuses(result, requete, connexion);
		}

		return obj;
	}

	@Override
	public void delete(Adherent obj) {

	}

	@Override
	public Adherent update(Adherent obj) {
		if (obj == null) {
			throw new NullPointerException("La personne ne doit pas être null");
		}

		Connection connexion = null;
		PreparedStatement requete = null;
		ResultSet result = null;
		String sql = "UPDATE adherent SET idMotifSortie = ?, idResponsable = ?, idFamille = ?, droitImage = ?, "
				+ "dateEntree = ?, dateSortie = ?, qf = ?, cotisation = ? , aPaye = ? "
				+ "WHERE idPersonne = ?;";

		Integer idMotif = null;
		Integer idFamille = null;
		Long idResponsable = null;
		try {

			if (obj.getResponsable() != null) {
				idResponsable = obj.getResponsable().getIdResponsable();
			}
			if (obj.getMotif() != null) {
				idMotif = obj.getMotif().getIdMotif();
			}
			if (obj.getFamille() != null) {
				idFamille = obj.getFamille().getIdFamille();
			}
			connexion = factory.getConnection();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion,
					sql, false, idMotif, idResponsable, idFamille, (obj.isDroitImage() ? 1
							: 0), DateUtil.toSqlDate(obj.getDateEntree()),
					DateUtil.toSqlDate(obj.getDateSortie()), obj.getQf(), obj
							.getCotisation(), obj.isAPaye(), obj
							.getIdPersonne());
			requete.executeUpdate();
			
			if (obj.getDisciplines() != null) {
				DisciplineDAO disciplineDAO = factory.getDisciplineDAO();
				disciplineDAO.updateAllDisciplineParAdherent(obj.getDisciplines(),
						obj.getIdPersonne());
			}

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			DAOUtilitaires.fermeturesSilencieuses(result, requete, connexion);
		}

		return obj;
	}

	@Override
	public Adherent get(long id) {
		Adherent adherent = null;
		Connection connexion = null;
		PreparedStatement requete = null;
		ResultSet result = null;
		String sql = "SELECT * FROM adherent a inner join personne p on a.idPersonne=p.idPersonne WHERE p.idPersonne = ?;";
		try {

			connexion = factory.getConnection();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion,
					sql, false, id);
			result = requete.executeQuery();

			if (result.first()) {
				adherent = this.map(result);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			DAOUtilitaires.fermeturesSilencieuses(result, requete, connexion);
		}
		return adherent;
	}

	@Override
	public List<Adherent> getAll() {
		List<Adherent> liste = new ArrayList<>();

		Adherent adherent = null;
		Connection connexion = null;
		PreparedStatement requete = null;
		ResultSet result = null;
		String sql = "SELECT * FROM adherent a inner join personne p on a.idPersonne=p.idPersonne Where dateSortie is NULL order by nom, prenom;";
		try {

			connexion = factory.getConnection();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion,
					sql, false);
			result = requete.executeQuery();

			while (result.next()) {
				adherent = this.map(result);
				liste.add(adherent);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			DAOUtilitaires.fermeturesSilencieuses(result, requete, connexion);
		}

		return liste;
	}
	
	public List<Adherent> getAllAnciens() {
		List<Adherent> liste = new ArrayList<>();

		Adherent adherent = null;
		Connection connexion = null;
		PreparedStatement requete = null;
		ResultSet result = null;
		String sql = "SELECT * FROM adherent a inner join personne p on a.idPersonne=p.idPersonne Where dateSortie is not NULL order by nom, prenom;";
		try {

			connexion = factory.getConnection();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion,
					sql, false);
			result = requete.executeQuery();

			while (result.next()) {
				adherent = this.map(result);
				liste.add(adherent);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			DAOUtilitaires.fermeturesSilencieuses(result, requete, connexion);
		}

		return liste;
	}

	@Override
	public Adherent map(ResultSet result) throws SQLException {
		PersonneDAO personneDAO = factory.getPersonneDAO();
		DisciplineDAO disciplineDAO = factory.getDisciplineDAO();
		ResponsableDAO responsableDAO = factory.getResponsableDAO();
		MotifSortieDAO motifSortieDAO = factory.getMotifSortieDAO();
		FamilleDAO familleDAO = factory.getFamilleDAO();

		Long idResponsable = NumberUtil.getResultLong(result, "idResponsable");
		Integer idMotifSortie = NumberUtil.getResultInteger(result,
				"idMotifSortie");
		Integer idFamille = NumberUtil.getResultInteger(result, "idFamille");

		Adherent adherent = new Adherent(personneDAO.map(result),
				idMotifSortie == null ? null : motifSortieDAO
						.get(idMotifSortie),
				idResponsable == null ? null : responsableDAO
						.get(idResponsable), result.getBoolean("droitImage"),
				result.getDate("dateEntree"), result.getDate("dateSortie"),
				NumberUtil.getResultFloat(result, "qf"),
				NumberUtil.getResultFloat(result, "cotisation"),
				disciplineDAO.getDisciplineParAdherent(result
						.getLong("idPersonne")), new ArrayList<Classe>(),
				new ArrayList<Cours>(), result.getBoolean("aPaye"),
				idFamille == null ? null : familleDAO.get(idFamille));
		return adherent;
	}

}
