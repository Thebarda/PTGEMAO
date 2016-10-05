/**
 * 
 */
package fr.gemao.view;

import javax.servlet.http.HttpServletRequest;

/**
 * @author FELTON-GROS-METAYER-PIAT-VAREILLE
 *
 */
public class ServletUtil {

	/**
	 * Cette fonction permet de retourner la valeur d'un champ GET.
	 * 
	 * @param parameter
	 *            Champ GET de l'URL à récupérer
	 * @param request
	 *            {@link HttpServletRequest}
	 * @return <code>null</code> si la valeur n'existe pas/n'est pas correcte ||
	 *         {@link Integer} de la valeur
	 */
	public static Integer getParameterIntegerValue(String parameter, HttpServletRequest request) {
		String temporaire = request.getParameter(parameter);
		if (temporaire != null) {
			try {
				Integer retour = Integer.parseInt(temporaire);
				if (retour != null) {
					return retour;
				}
			} catch (NumberFormatException nfe) {
				return null;
			}
		}
		return null;
	}
}
