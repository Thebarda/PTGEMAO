package fr.gemao.ctrl.cheque;

import java.text.ParseException;
import java.util.List;

import fr.gemao.entity.materiel.ChequeLocation;
import fr.gemao.entity.partenaire.ChequePartenaire;
import fr.gemao.sql.DAOFactory;
import fr.gemao.sql.cheque.ChequeDAO;
import fr.gemao.sql.cheque.ChequePartenaireDAO;

public class ChequePartenaireCtrl {
	public static void ajouterCheque(ChequePartenaire cheque) {
		new ChequePartenaireDAO(DAOFactory.getInstance()).create(cheque);
	}
	
	public static void supprimerCheque(ChequeLocation cheque) {
		new ChequeDAO(DAOFactory.getInstance()).delete(cheque);
	}
	
	public static List<ChequePartenaire> getAll() {
		ChequePartenaireDAO cheDAO = new ChequePartenaireDAO(DAOFactory.getInstance());
		try {
			return cheDAO.getAll();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static List<ChequePartenaire> getByMonthYear(String month, String year) {
		ChequePartenaireDAO cheDAO = new ChequePartenaireDAO(DAOFactory.getInstance());
		return cheDAO.getByMonthYear(month, year);
	}

	public static ChequeLocation getByNumCheque(long idCheque) {
		ChequeDAO cheDAO = new ChequeDAO(DAOFactory.getInstance());
		return cheDAO.getByNumCheque(idCheque);
	}

	public static void addDEEByNumCheque(String dEE, long numCheque) {
		ChequePartenaireDAO cheDAO = new ChequePartenaireDAO(DAOFactory.getInstance());
		cheDAO.addDEEByNumCheque(dEE, numCheque);
	}
}
