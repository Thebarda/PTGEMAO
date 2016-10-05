package fr.gemao.ctrl.planning;

import java.util.List;

import fr.gemao.entity.cours.Salle;
import fr.gemao.entity.planning.Creneau;
import fr.gemao.entity.planning.Planning;
import fr.gemao.sql.DAOFactory;

/**
 * @author FELTON-GROS-METAYER-PIAT-VAREILLE
 *
 */
public final class CreneauCtrl {

	private CreneauCtrl() {

	}

	public static List<Salle> getAllSalles() {
		return DAOFactory.getInstance().getSalleDAO().getAll();
	}

	public static Salle getSalle(Integer idSalle) {
		return DAOFactory.getInstance().getSalleDAO().get(idSalle);
	}

	public static Creneau create(Creneau c) {
		if (getPlanning(c.getIdPlanning()).getValide()) {
			throw new IllegalStateException(
					"Impossible d'ajouter un créneau à un planning validé. Veuillez le dupliquer et le modifier à nouveau.");
		}

		if (isCreneauDejaPris(c)) {
			throw new IllegalStateException("Ce créneau ne peut-être placé ici, la place est déjà prise !");
		}
		return DAOFactory.getInstance().getCreneauDAO().create(c);
	}

	public static Creneau update(Creneau c) {
		if (getPlanning(c.getIdPlanning()).getValide()) {
			throw new IllegalStateException(
					"Impossible de modifier un créneau appartenant à un planning validé. Veuillez le dupliquer et le modifier à nouveau.");
		}

		if (isCreneauDejaPris(c)) {
			throw new IllegalStateException("Ce créneau ne peut-être placé ici, la place est déjà prise !");
		}
		return DAOFactory.getInstance().getCreneauDAO().update(c);
	}

	public static List<Creneau> getCreneaux(Integer idPlanning) {
		return DAOFactory.getInstance().getCreneauDAO().getListCreneauxPlanning(idPlanning);
	}

	/**
	 * @param idCreneau
	 * @return
	 */
	public static Creneau getCreneau(Integer idCreneau) {
		return DAOFactory.getInstance().getCreneauDAO().get(idCreneau);
	}

	/**
	 * Méthode qui retourne la liste des tous les créneaux enregistrés
	 * 
	 * @return
	 */
	public static List<Creneau> getAllCreneaux() {
		return DAOFactory.getInstance().getCreneauDAO().getAll();
	}

	/**
	 * @param idPlanning
	 * @return
	 */
	public static Planning getPlanning(Integer idPlanning) {
		return DAOFactory.getInstance().getPlanningDAO().get(idPlanning);
	}

	public static void deleteCreneau(Creneau c) {
		DAOFactory.getInstance().getContrainteDAO().deleteByIdCreneau(c.getIdCreneau());
		DAOFactory.getInstance().getCreneauDAO().delete(c);
	}
	
	public static void deleteByIdPlanning(Integer idPlanning) {
		for (Creneau c : CreneauCtrl.getCreneaux(idPlanning)) {
			ContrainteCtrl.deleteByIdCreneau(c.getIdCreneau());
		}
		DAOFactory.getInstance().getCreneauDAO().deleteByIdPlanning(idPlanning);
	}

	/**
	 * Cette méthode permet de savoir si un créneau est déjà placé à cet
	 * endroit. (jour, heure [sur la durée et HeureDeb ] et salle)
	 * 
	 * @param c
	 * @return <code>true</code> si le créneau est pris || <code>false</code> si
	 *         le créneau est libre
	 */
	private static boolean isCreneauDejaPris(Creneau c) {

		List<Creneau> creneauxPlanning = PlanningCtrl.getPlanning(c.getIdPlanning()).getListeCreneau();

		// On a la liste de tous les créneaux du planning.

		for (Creneau tmp : creneauxPlanning) {
			// On teste individuellement chaque creneau avec le creneau que l'on
			// souhaite tester.
			if (isConflit(tmp, c)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @param tmp
	 * @param c
	 * @return
	 */
	private static boolean isConflit(Creneau tmp, Creneau c) {

		boolean testJour = testJour(c, tmp);
		boolean testSalle = testSalle(c, tmp);

		//System.out.println("Test salle " + testSalle + " & test jour" + testJour);

		if (testJour && testSalle) {
			if (testHeure(tmp, c)) {
				//System.out.println("Coucou");
				return true;
			}
		}

		return false;
	}

	/**
	 * Si l'heure début n'est pas renseigné sur l'un des deux créneaux, on ne
	 * teste pas. (return false)
	 * 
	 * @param t
	 *            {@link Creneau}
	 * @param tmp
	 *            {@link Creneau}
	 * @return <code>true</code> si les heures se superposent,
	 *         <code>false</code> sinon
	 */
	private static boolean testHeure(Creneau t, Creneau tmp) {
		if (t.getClass() == t.getClass()) // Condition idiote de test pour return false;
			return false;

		//System.out.println("Details creneau T : ");
		//System.out.println("Heure deb : " + t.getHeureDeb() + "\nHeure fin : " + t.getHeureFin());
		//System.out.println("\nDetails creneau tmp : ");
		//System.out.println("Heure deb : " + tmp.getHeureDeb() + "\nHeure fin : " + tmp.getHeureFin());

		if (t.getHeureDeb() == null || tmp.getHeureDeb() == null)
			return false;

		// Test sur l'heure de debut et de fin
		if (t.getHeureDeb().equals(tmp.getHeureDeb())) {
			//System.out.println("Cas 1");
			return true;
		}

		if (t.getHeureFin().equals(tmp.getHeureFin())) {
			//System.out.println("Cas 2");
			return true;
		}

		// Test sur la durée.
		if (t.getHeureDeb().isSuperieur(tmp.getHeureFin())) {
			if (t.getHeureFin().isInferieur(tmp.getHeureDeb())) {
				//System.out.println("Cas 3");
				return true;
			}
		}

		if (t.getHeureFin().isInferieur(tmp.getHeureDeb())) {
			if (t.getHeureFin().isSuperieur(tmp.getHeureDeb())) {
				//System.out.println("Cas 4");
				return true;
			}
		} else {
			if (t.getHeureFin().isInferieur(tmp.getHeureDeb())) {
				//System.out.println("Cas 5");
				return true;
			}
		}

		return false;
	}

	/**
	 * On effectue le test du jour uniquement si le jour est renseigné sur les
	 * deux Creneaux
	 * 
	 * @param t
	 *            {@link Creneau}
	 * @param tmp
	 *            {@link Creneau}
	 * @return <code>true</code> si les jours sont identique, <code>false</code>
	 *         sinon
	 */
	private static boolean testJour(Creneau t, Creneau tmp) {
		//System.out.println("T jour : " + t.getJour());
		//System.out.println("Tmp jour : " + tmp.getJour());
		if (t.getJour() != null && tmp.getJour() != null)
			return t.getJour().equals(tmp.getJour());
		return false;
	}

	/**
	 * On effectue le test de la salle uniquement si la salle est renseignée sur
	 * les deux créneaux.
	 * 
	 * @param t
	 *            {@link Creneau}
	 * @param tmp
	 *            {@link Creneau}
	 * @return <code>true</code> si les salles sont identiques,
	 *         <code>false</code> sinon
	 */
	private static boolean testSalle(Creneau t, Creneau tmp) {
		//System.out.println("T salle : " + t.getSalle());
		//System.out.println("Tmp salle : " + tmp.getSalle());

		if (t.getSalle() != null && tmp.getSalle() != null)
			return t.getSalle().equals(tmp.getSalle());
		return false;
	}
}
