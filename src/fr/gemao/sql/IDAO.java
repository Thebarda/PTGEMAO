package fr.gemao.sql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

public abstract class IDAO<T> {
	protected DAOFactory factory = null;

	/**
	 * Constructeur
	 * 
	 * @param factory
	 *            : objet renvoyé par la méthode DAOFactory.getInstance()
	 */
	public IDAO(DAOFactory factory) {
		this.factory = factory;
	}

	/**
	 * Méthode d'insertion d'un objet dans une table.
	 * 
	 * @param obj
	 *            Objet à insérer.
	 * @return l'objet inséré ou null s'il n'est pas trouvé après l'insertion.
	 * @throws DAOException
	 *             si l'objet existe déjà.
	 */
	public abstract T create(T obj);

	/**
	 * Méthode de suppression d'objet.
	 * 
	 * @param obj
	 *            Objet à supprimer.
	 * @throws DAOException
	 *             si l'objet n'existe pas.
	 */
	public abstract void delete(T obj);

	/**
	 * Méthode de mise à jour d'un objet.
	 * 
	 * @param obj
	 *            Objet à mettre à jour.
	 * @return l'objet mis à jour ou null s'il n'est pas trouvé après la mise à
	 *         jour.
	 */
	public abstract T update(T obj);

	/**
	 * Méthode de recherche d'un objet. Retourne une instance d'un objet dans la
	 * base à partir de son identifiant.
	 * 
	 * @param id
	 *            identifiant de l'objet à rechercher.
	 * @return l'objet correspondant à l'identifiant ou null si l'objet n'xiste
	 *         pas.
	 */
	public abstract T get(long id);

	/**
	 * Méthode de recherche de toutes les lignes d'une table de la base.
	 * 
	 * @return la liste de tous les objets.
	 * @throws ParseException 
	 */
	public abstract List<T> getAll() throws ParseException;

	/**
	 * Simple méthode utilitaire permettant de faire la correspondance (le
	 * mapping) entre une ligne issue de la table des T (un ResultSet) et un
	 * bean T. Cette méthode place les résultats d'une requête de type SELECT
	 * dans un objet et renvoie cet objet.
	 * 
	 * @param result
	 *            Le résultat d'une requête.
	 * 
	 * @return L'objet mappé
	 * @throws SQLException
	 * @throws ParseException 
	 **/
	protected abstract T map(ResultSet result) throws SQLException, ParseException;
}
