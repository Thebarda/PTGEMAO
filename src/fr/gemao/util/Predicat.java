package fr.gemao.util;

/**
 * 
 *
 * @param <T>
 *            la classe de l'objet sur lesquels les tests implémentés seront
 *            effectués
 * 
 *            Interface représentant un prédicat (condition devant être
 *            réalisée).
 */
public interface Predicat<T> {

	/**
	 * Méthode testant si les conditions du test implémenté sont réalisées sur
	 * l'instance t.
	 * 
	 * @param t
	 *            l'objet à tester
	 * @return vrai si les tests sont réalisés, faux sinon
	 */
	boolean test(T t);

	boolean test(T t, T tmp);
}