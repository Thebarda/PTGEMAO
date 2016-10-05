/**
 * 
 */
package fr.gemao.ctrl.planning;

import java.util.List;

import fr.gemao.entity.cours.Discipline;
import fr.gemao.entity.cours.Matiere;
import fr.gemao.entity.cours.Niveau;
import fr.gemao.sql.DAOFactory;
import fr.gemao.sql.cours.DisciplineDAO;
import fr.gemao.sql.cours.MatiereDAO;
import fr.gemao.sql.cours.NiveauDAO;

/**
 * @author FELTON-GROS-METAYER-PIAT-VAREILLE
 *
 */
public final class DisciplineCtrl {

	private DisciplineCtrl() {

	}

	/**
	 * Retourne la liste de toutes les disciplines stockées en base de données
	 * sous forme d'une liste
	 * 
	 * @return {@link List}
	 */
	public static List<Discipline> getAll() {
		return DAOFactory.getInstance().getDisciplineDAO().getAll();
	}

	/**
	 * Retourne un objet {@link Discipline} via son identifiant
	 * 
	 * @param idDiscipline
	 *            l'id de la {@link Discipline} a récupérer.
	 * @return un objet {@link Discipline} correspondant à cet id
	 */
	public static Discipline get(Integer idDiscipline) {
		return DAOFactory.getInstance().getDisciplineDAO().get(idDiscipline);
	}

	public static boolean ajouterDiscipline(Discipline discipline) {

		DAOFactory factory = DAOFactory.getInstance();
		DisciplineDAO disciplineDAO = factory.getDisciplineDAO();
		NiveauDAO niveauDAO = factory.getNiveauDAO();
		MatiereDAO matiereDAO = factory.getMatiereDAO();

		Matiere matiere = matiereDAO.exist(discipline.getMatiere());
		if (matiere == null)
			matiere = matiereDAO.create(discipline.getMatiere());
		discipline.setMatiere(matiere);

		Niveau niveau = niveauDAO.exist(discipline.getNiveau());
		if (niveau == null)
			niveau = niveauDAO.create(discipline.getNiveau());
		discipline.setNiveau(niveau);

		Discipline disc = disciplineDAO.exist(discipline);
		if (disc == null) {
			discipline = disciplineDAO.create(discipline);
			return true;
		} else {
			return false;
		}
	}

	public static boolean modifierDiscipline(Discipline discipline) {

		DAOFactory co = DAOFactory.getInstance();
		DisciplineDAO disciplineDAO = co.getDisciplineDAO();
		NiveauDAO niveauDAO = co.getNiveauDAO();
		MatiereDAO matiereDAO = co.getMatiereDAO();

		Matiere matiere = matiereDAO.exist(discipline.getMatiere());
		if (matiere == null)
			matiere = matiereDAO.create(discipline.getMatiere());
		discipline.setMatiere(matiere);

		Niveau niveau = niveauDAO.exist(discipline.getNiveau());
		if (niveau == null)
			niveau = niveauDAO.create(discipline.getNiveau());
		discipline.setNiveau(niveau);

		Discipline disc = disciplineDAO.exist(discipline);
		if (disc == null) {
			discipline = disciplineDAO.update(discipline);
			return true;
		} else {
			return false;
		}
	}

	public static Discipline recupererDiscipline(String matiere, String niveau) {
		DAOFactory factory = DAOFactory.getInstance();
		DisciplineDAO disciplineDAO = factory.getDisciplineDAO();
		NiveauDAO niveauDAO = factory.getNiveauDAO();
		MatiereDAO matiereDAO = factory.getMatiereDAO();
		Matiere mat = matiereDAO.get(matiere);
		Niveau niv = niveauDAO.get(niveau);
		Discipline discipline = new Discipline(null, mat, niv, null);
		return disciplineDAO.exist(discipline);
	}
	
	public static void delete(Discipline d) {
		DAOFactory.getInstance().getDisciplineDAO().delete(d);
	}

}
