package fr.gemao.sql.adherent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.gemao.entity.adherent.Famille;
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
		String tableauFicheComptable="<table><tr><td class='ignorer'></td><th class='numEleve'>Elève 1</th><th class='numEleve'>Elève 2</th><th class='numEleve'>Elève 3</th><th class='numEleve'>Elève 4</th><th class='numEleve'>Elève 5</th></tr><tr><th class='intitule'>Prénom + Nom</th><td contenteditable='true' class='nom'></td><td contenteditable='true' class='nom'></td><td contenteditable='true' class='nom'></td><td contenteditable='true' class='nom'></td><td contenteditable='true' class='nom'></td></tr><tr><th class='intitule'>QF (1, 2, 3)</th><td contenteditable='true' id='qf'></td><td contenteditable='true' id='qf'></td><td contenteditable='true' id='qf'></td><td contenteditable='true' id='qf'></td><td contenteditable='true' id='qf'></td></tr><tr><th class='intitule'>FM / inst<br>Eveil / éveil inst</th><td contenteditable='true' id='lig_0_col_0'  class='sommeMensuelle'></td><td contenteditable='true' id='lig_0_col_1' class='sommeMensuelle'></td><td contenteditable='true' id='lig_0_col_2' class='sommeMensuelle'></td><td contenteditable='true' id='lig_0_col_3' class='sommeMensuelle'></td><td contenteditable='true' id='lig_0_col_4' class='sommeMensuelle'></td></tr><tr><th class='intitule'>FM seule éveil</th><td contenteditable='true' id='lig_1_col_0' class='sommeMensuelle'></td><td contenteditable='true' id='lig_1_col_1' class='sommeMensuelle'></td><td contenteditable='true' id='lig_1_col_2' class='sommeMensuelle'></td><td contenteditable='true' id='lig_1_col_3' class='sommeMensuelle'></td><td contenteditable='true' id='lig_1_col_4' class='sommeMensuelle'></td></tr><tr><th class='intitule'>Instrument seul</th><td contenteditable='true' id='lig_2_col_0' class='sommeMensuelle'></td><td contenteditable='true' id='lig_2_col_1' class='sommeMensuelle'></td><td contenteditable='true' id='lig_2_col_2' class='sommeMensuelle'></td><td contenteditable='true' id='lig_2_col_3' class='sommeMensuelle'></td><td contenteditable='true' id='lig_2_col_4' class='sommeMensuelle'></td></tr><tr><th class='intitule'>Atelier</th><td contenteditable='true' id='lig_3_col_0' class='sommeMensuelle'></td><td contenteditable='true' id='lig_3_col_1' class='sommeMensuelle'></td><td contenteditable='true' id='lig_3_col_2' class='sommeMensuelle'></td><td contenteditable='true' id='lig_3_col_3' class='sommeMensuelle'></td><td contenteditable='true' id='lig_3_col_4' class='sommeMensuelle'></td><th class='intituleTotal'>Total mensuel</th></tr><tr><th class='intitule'>Total mensuel par élève</th><td class='sous_total' id='sommeMensuelle_0'></td><td class='sous_total' id='sommeMensuelle_1'></td><td class='sous_total' id='sommeMensuelle_2'></td><td class='sous_total' id='sommeMensuelle_3'></td><td class='sous_total' id='sommeMensuelle_4'></td><td class='total' id='totalMensuel'></td></tr><tr><th class='intitule'>Pratique collective</th><td contenteditable='true' id='lig_0_col_0' class='sommeAnnuelle'></td><td contenteditable='true' id='lig_0_col_1' class='sommeAnnuelle'></td><td contenteditable='true' id='lig_0_col_2' class='sommeAnnuelle'></td><td contenteditable='true' id='lig_0_col_3' class='sommeAnnuelle'></td><td contenteditable='true' id='lig_0_col_4' class='sommeAnnuelle'></td></tr><tr><th class='intitule'>Cotisation</th><td contenteditable='true' id='lig_1_col_0' class='sommeAnnuelle'></td>			<td contenteditable='true' id='lig_1_col_1' class='sommeAnnuelle'></td>				<td contenteditable='true' id='lig_1_col_2' class='sommeAnnuelle'></td>				<td contenteditable='true' id='lig_1_col_3' class='sommeAnnuelle'></td><td contenteditable='true' id='lig_1_col_4' class='sommeAnnuelle'></td>			</tr>			<tr>				<th class='intitule'>Membre admin</th>				<td contenteditable='true' id='lig_2_col_0' class='sommeAnnuelle'></td>				<td contenteditable='true' id='lig_2_col_1' class='sommeAnnuelle'></td>				<td contenteditable='true' id='lig_2_col_2' class='sommeAnnuelle'></td>				<td contenteditable='true' id='lig_2_col_3' class='sommeAnnuelle'></td><td contenteditable='true' id='lig_2_col_4' class='sommeAnnuelle'></td><th class='intituleTotal'>Total annuel</th></tr><tr><th class='intitule'>Total Annuel par élève</th><td class='sous_total' id='sommeAnnuelle_0'></td><td class='sous_total' id='sommeAnnuelle_1'></td><td class='sous_total' id='sommeAnnuelle_2'></td><td class='sous_total' id='sommeAnnuelle_3'></td><td class='sous_total' id='sommeAnnuelle_4'></td><td class='total' id='totalAnnuel'></td></tr><tr><th class='intitule'>Part C.E.</th><td contenteditable='true' class='ce'></td><td contenteditable='true' class='ce'></td><td contenteditable='true' class='ce'></td><td contenteditable='true' class='ce'></td><td contenteditable='true' class='ce'></td></tr><tr><td class='ignorer'></td></tr><tr><th class='intituleTotal'>Reste à la charge du client</th><td class='total' id='totalFinal'></td></tr></table>";
		String tableauRecapitulatif="";
		
		if (obj == null) {
			throw new NullPointerException("L'adhérent ne doit pas être null");
		}

		int id = 0;
		Connection connexion = null;
		PreparedStatement requete = null;
		ResultSet result = null;

		String sql = "INSERT INTO famille (nomFamille, TableauFicheComptable, TableauRecapitulatif) VALUES (?, ?, ?)";

		try {
			connexion = factory.getConnection();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion,
					sql, true, obj.getNomFamille(), tableauFicheComptable, tableauRecapitulatif);

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
				result.getString("nomFamille"), result.getString("tableauFicheComptable"), result.getString("TableauRecapitulatif"));
	}

	public String getTableauFicheComptable(int idFamille){
		String tableauFicheComptable="";

		Famille famille = null;
		Connection connexion = null;
		PreparedStatement requete = null;
		ResultSet result = null;
		String sql = "SELECT TableauFicheComptable FROM famille WHERE idFamille = ?;";
		try {

			connexion = factory.getConnection();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion,
					sql, false, idFamille);
			result = requete.executeQuery();

			while (result.next()) {
				tableauFicheComptable=result.getString("TableauFicheComptable");
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			DAOUtilitaires.fermeturesSilencieuses(result, requete, connexion);
		}

		return tableauFicheComptable;
	}
}
