package fr.gemao.sql.util;

import java.sql.ResultSet;
import java.sql.SQLException;

public class NumberUtil {

	private NumberUtil(){}
	
	/**
	 * Retourne l'entier correspondant à la colonne.
	 * @param rs
	 * 		Le ResultSet où récupérer la valeur
	 * @param column
	 * 		Le nom de la colonne.
	 * @return entier correspondant à la colonne ou null.
	 * @throws SQLException si erreur lors de la lecture.
	 */
	public static Integer getResultInteger(ResultSet rs, String column) throws SQLException{
		int i = rs.getInt(column);
		Integer integer = null;
		if(!rs.wasNull()){
			integer = new Integer(i);
		}
		return integer;
	}
	
	/**
	 * Retourne le float correspondant à la colonne.
	 * @param rs
	 * 		Le ResultSet où récupérer la valeur
	 * @param column
	 * 		Le nom de la colonne.
	 * @return float correspondant à la colonne ou null.
	 * @throws SQLException si erreur lors de la lecture.
	 */
	public static Float getResultFloat(ResultSet rs, String column) throws SQLException{
		float f = rs.getFloat(column);
		Float floater = null;
		if(!rs.wasNull()){
			floater = new Float(f);
		}
		return floater;
	}
	
	/**
	 * Retourne le long correspondant à la colonne.
	 * @param rs
	 * 		Le ResultSet où récupérer la valeur
	 * @param column
	 * 		Le nom de la colonne.
	 * @return long correspondant à la colonne ou null.
	 * @throws SQLException si erreur lors de la lecture.
	 */
	public static Long getResultLong(ResultSet rs, String column) throws SQLException{
		long l = rs.getLong(column);
		Long longer = null;
		if(!rs.wasNull()){
			longer = new Long(l);
		}
		return longer;
	}
	
	/**
	 * Retourne le booléen correspondant à la colonne.
	 * @param rs
	 * 		Le ResultSet où récupérer la valeur
	 * @param column
	 * 		Le nom de la colonne.
	 * @return booléen correspondant à la colonne ou null.
	 * @throws SQLException si erreur lors de la lecture.
	 */
	public static Boolean getResultBoolean(ResultSet rs, String column) throws SQLException{
		Boolean i = rs.getBoolean(column);
		Boolean b = null;
		if(!rs.wasNull()){
			b = new Boolean(i);
		}
		return b;
	}
}
