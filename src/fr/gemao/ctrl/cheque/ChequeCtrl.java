package fr.gemao.ctrl.cheque;

import java.util.List;

import fr.gemao.ctrl.location.LocationCtrl;
import fr.gemao.entity.materiel.ChequeLocation;
import fr.gemao.entity.materiel.Location;
import fr.gemao.sql.DAOFactory;
import fr.gemao.sql.cheque.ChequeDAO;
import fr.gemao.sql.location.LocationDAO;

public class ChequeCtrl {
	
	public static void ajouterCheque(ChequeLocation cheque) {
		if (cheque.getLocation().getId() < 0) {
			throw new IllegalArgumentException("L'id de la location ne peut pas être null");
		}
		if (cheque.getTypeLocation() == null) {
			throw new NullPointerException("Le type de la location ne peut etre null");
		}
		if (cheque.getLocation().getPersonne().getIdPersonne() == null) {
			throw new NullPointerException("L'id du locataire ne peut etre null");
		}
		if (cheque.getLocation().getPersonne().getIdPersonne() < 0) {
			throw new IllegalArgumentException("L'id du locataire ne peut etre inferieur à 0");
		}
		if (cheque.getLocation().getMateriel().getIdMateriel() == null) {
			throw new NullPointerException("L'id du matériel ne peut etre null");
		}
		if (cheque.getLocation().getMateriel().getIdMateriel() < 0) {
			throw new IllegalArgumentException("L'id du matériel ne peut etre inferieur à 0");
		}
		if (cheque.getDatePaiement() == null) {
			throw new NullPointerException("La date de paiement ne peut pas etre null");
		}
		if (cheque.getMontantCheque() < 0) {
			throw new IllegalArgumentException("Le montant du cheque ne peut etre inférieur à 0");
		}
		if (cheque.getNumCheque() < 0) {
			throw new IllegalArgumentException("Le numero du cheque ne peut etre inférieur à 0");
		}
		if (cheque.getDateEncaissement() == null) {
			throw new NullPointerException("La date d'encaissement ne peut pas être null");
		}

		new ChequeDAO(DAOFactory.getInstance()).create(cheque);
	}
	
	public static void supprimerCheque(ChequeLocation cheque) {
		new ChequeDAO(DAOFactory.getInstance()).delete(cheque);
	}
	
	public static List<ChequeLocation> getAll() {
		ChequeDAO cheDAO = new ChequeDAO(DAOFactory.getInstance());
		return cheDAO.getAll();
	}
	
	public static List<ChequeLocation> getByMonthYear(String month, String year) {
		ChequeDAO cheDAO = new ChequeDAO(DAOFactory.getInstance());
		return cheDAO.getByMonthYear(month, year);
	}

	public static ChequeLocation getByNumCheque(long idCheque) {
		ChequeDAO cheDAO = new ChequeDAO(DAOFactory.getInstance());
		return cheDAO.getByNumCheque(idCheque);
	}

	public static void addDEEByNumCheque(String dEE, long numCheque) {
		ChequeDAO cheDAO = new ChequeDAO(DAOFactory.getInstance());
		cheDAO.addDEEByNumCheque(dEE, numCheque);
	}
	
	
}
