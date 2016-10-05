package fr.gemao.util;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * Classe représentant un filtre
 */
public final class Filter {

	/**
	 * Méthode filtrant une liste passée en paramètre sur un {@link Predicat}
	 * 
	 * @param list
	 *            la liste sur laquelle effectuer les tests
	 * @param predicat
	 *            le prédicat servant à tester la liste
	 * @return une nouvelle liste contenant tous les éléments de la liste
	 *         répondant aux conditions du prédicat
	 */
	public static <T> List<T> filter(List<T> list, Predicat<T> predicat) {
		List<T> resultat = new ArrayList<>();

		for (final T t : list) {
			if (predicat.test(t)) {
				resultat.add(t);
			}
		}
		return resultat;
	}

	/**
	 * Même méthode qu'au dessus, mais avec une méthode de test double.
	 * 
	 * @see Predicat#test(Object, Object)
	 * @param list
	 * @param predicat
	 * @return
	 */
	public static <T> List<T> filterCompare(List<T> list, Predicat<T> predicat) {
		List<T> resultat = new ArrayList<>();
		for (final T t : list) {
			// TODO à virer et à remplacer par autre chose.
			for (final T tmp : list) {
				if (predicat.test(t, tmp)) {
					resultat.add(t);
				}
			}
		}
		return resultat;
	}

	/**
	 * Constructeur de filtre, privé, pour éviter l'instanciation inutile
	 */
	private Filter() {
	}
}
