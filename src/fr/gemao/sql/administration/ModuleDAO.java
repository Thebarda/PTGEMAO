/**
 * 
 */
package fr.gemao.sql.administration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import fr.gemao.entity.administration.Module;
import fr.gemao.sql.DAOFactory;
import fr.gemao.sql.IDAO;
import fr.gemao.sql.exception.DAOException;
import fr.gemao.sql.util.DAOUtilitaires;
import fr.gemao.sql.util.NumberUtil;

/**
 * @author clement
 *
 */
public class ModuleDAO extends IDAO<Module> {

	/**
	 * @param factory
	 */
	public ModuleDAO(DAOFactory factory) {
		super(factory);
	}

	/* (non-Javadoc)
	 * @see fr.gemao.sql.IDAO#create(java.lang.Object)
	 */
	@Override
	public Module create(Module obj) {
		throw new UnsupportedOperationException("Impossible de créer un module.");
	}

	/* (non-Javadoc)
	 * @see fr.gemao.sql.IDAO#delete(java.lang.Object)
	 */
	@Override
	public void delete(Module obj) {
		throw new UnsupportedOperationException("Impossible de supprimer un module.");
	}

	/* (non-Javadoc)
	 * @see fr.gemao.sql.IDAO#update(java.lang.Object)
	 */
	@Override
	public Module update(Module obj) {
		throw new UnsupportedOperationException("Impossible de modifier un module.");
	}

	/* (non-Javadoc)
	 * @see fr.gemao.sql.IDAO#get(long)
	 */
	@Override
	public Module get(long id) {
		throw new UnsupportedOperationException("Méthode non implémentée.");
	}

	/* (non-Javadoc)
	 * @see fr.gemao.sql.IDAO#getAll()
	 */
	@Override
	public List<Module> getAll() {
		throw new UnsupportedOperationException("Méthode non implémentée.");
	}
	
	public void load(){
		Connection connexion = null;
		PreparedStatement requete = null;
		ResultSet result = null;
		String sql = "SELECT * FROM module;";
		try {

			connexion = factory.getConnection();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion,
					sql, false);
			result = requete.executeQuery();

			while (result.next()) {
				Module.put(NumberUtil.getResultInteger(result, "idModule"), result.getString("nomModule"));
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			DAOUtilitaires.fermeturesSilencieuses(result, requete, connexion);
		}
	}

	/* (non-Javadoc)
	 * @see fr.gemao.sql.IDAO#map(java.sql.ResultSet)
	 */
	@Override
	protected Module map(ResultSet result) throws SQLException {
		throw new UnsupportedOperationException("Méthode non implémentée.");
	}

}
