package fr.gemao.ctrl.administration;

import java.util.ArrayList;
import java.util.List;

import fr.gemao.entity.administration.Droit;
import fr.gemao.entity.administration.Module;
import fr.gemao.entity.administration.Profil;
import fr.gemao.entity.administration.ProfilEnum;
import fr.gemao.entity.administration.TypeDroit;
import fr.gemao.sql.DAOFactory;
import fr.gemao.sql.administration.DroitDAO;
import fr.gemao.sql.administration.ModuleDAO;
import fr.gemao.sql.administration.ProfilDAO;
import fr.gemao.sql.administration.TypeDroitDAO;
import fr.gemao.sql.exception.DAOException;

public class ProfilsCtrl {

	private DAOFactory daoFactory;

	public ProfilsCtrl() {
		this.daoFactory = DAOFactory.getInstance();
	}

	/**
	 * Charge toutes les informations nécessaires pour les profils. Charge les
	 * types de droit, les modules, et les profils. Attention ne doit être
	 * chargé qu'une fois.
	 */
	public void loadProfil() {
		TypeDroitDAO typeDroitDAO = this.daoFactory.geTypeDroitDAO();
		typeDroitDAO.load();
		ModuleDAO moduleDAO = this.daoFactory.getModuleDAO();
		moduleDAO.load();
		ProfilDAO profilDAO = this.daoFactory.getProfilDAO();
		profilDAO.load();
	}

	/**
	 * Creer un profil, et associe la liste de droit au profil
	 * 
	 * @param nom
	 * @param listDroit
	 * @return un profil
	 */
	public Profil creerProfil(String nom, List<Droit> listDroit) {
		try {
			Profil profil = new Profil(null, nom, listDroit);
			ProfilDAO profilDAO = this.daoFactory.getProfilDAO();

			// Création du profil
			profil = profilDAO.create(profil);
			Profil.put(profil);

			// Création des droits associés au profil
			DroitDAO droitDAO = this.daoFactory.getDroitDAO();
			droitDAO.addAllDroitParProfil(profil.getIdProfil(), listDroit);

			return profil;
		} catch (DAOException daoException) {
			return null;
		}
	}

	public boolean deleteProfil(int idProfil) {

		// Prof et Admin ne peuvent etre supprimé ou modifié.
		if (idProfil == ProfilEnum.ADMIN.getId() || idProfil == ProfilEnum.PROFESSEUR.getId()) {
			throw new IllegalArgumentException("Il est impossible de modifier ou supprimer ce profil !");
		}

		try {
			Profil profil = new Profil();
			ProfilDAO profildao = DAOFactory.getInstance().getProfilDAO();
			DroitDAO droitdao = DAOFactory.getInstance().getDroitDAO();

			// Suppression des droits liés au profil
			droitdao.deleteDroitsProfil(idProfil);

			// Suppression du profil
			profil.setIdProfil(idProfil);
			profildao.delete(profil);

			return true;
		} catch (DAOException daoException) {
			return false;
		}
	}

	public boolean updateProfil(Profil profil) {

		// Prof et Admin ne peuvent etre supprimé ou modifié.
		if (profil.getIdProfil() == ProfilEnum.ADMIN.getId() || profil.getIdProfil() == ProfilEnum.PROFESSEUR.getId()) {
			throw new IllegalArgumentException("Il est impossible de modifier ou supprimer ce profil !");
		}

		try {
			ProfilDAO profildao = DAOFactory.getInstance().getProfilDAO();
			DroitDAO droitdao = DAOFactory.getInstance().getDroitDAO();

			// Mise à jour du nom
			profildao.update(profil);
			// Mise à jour des droits
			// Suppression des droits existants
			droitdao.deleteDroitsProfil(profil.getIdProfil());
			// Création des nouveaux droits
			droitdao.addAllDroitParProfil(profil.getIdProfil(), profil.getListDroits());

			return true;
		} catch (DAOException daoException) {
			return false;
		}
	}

	public Profil getProfil(int id) {
		ProfilDAO profilDAO = this.daoFactory.getProfilDAO();
		return profilDAO.get(id);
	}

	/**
	 * Méthode retournant tous les profils existants dans la base
	 * 
	 * @return
	 */
	public List<Profil> getAllProfils() {
		ProfilDAO profilDAO = this.daoFactory.getProfilDAO();
		return profilDAO.getAll();
	}

	/**
	 * Retourne vrai si le profil à le droit
	 * 
	 * @param personnel
	 * @param droit
	 * @return true si droit
	 */
	public boolean aDroit(Profil profil, Droit droit) {
		List<Droit> droits = profil.getListDroits();
		return droits.contains(droit);
	}

	public boolean aDroit(Profil profil, Module module, TypeDroit type) {
		Droit droit = new Droit(type, module);
		return this.aDroit(profil, droit);
	}

	public boolean aDroit(Profil profil, String nomModule, String nomType) {
		Module module = Module.getModule(nomModule);
		TypeDroit type = TypeDroit.getTypeDroit(nomType);
		return this.aDroit(profil, module, type);
	}

	/*
	 * public boolean aDroit(String nomProfil, String nomModule, String
	 * nomType){ Profil profil = Profil.getProfil(nomProfil); return
	 * this.aDroit(profil, nomModule, nomType); }
	 */

	/**
	 * Donne la liste des modules du profil
	 * 
	 * @param profil
	 * @return
	 */
	public List<Module> getListModuleADroit(Profil profil) {
		List<Droit> droits = profil.getListDroits();
		List<Module> modules = new ArrayList<>();
		for (Droit d : droits) {
			if (!modules.contains(d.getModule())) {
				modules.add(d.getModule());
			}
		}
		return modules;
	}

	public List<String> getListeModuleDroit(Profil profil) {
		List<Droit> droits = profil.getListDroits();
		List<String> modules = new ArrayList<>();
		for (Droit d : droits) {
			if (!modules.contains(d.getModule())) {
				modules.add(d.getModule().getNomModule());
			}
		}
		return modules;
	}

	public Profil getProfilByNom(String nom) {
		return DAOFactory.getInstance().getProfilDAO().getByNom(nom);
	}

	/*
	 * public List<String> getListeModuleDroit(String nomProfil){ Profil profil
	 * = Profil.getProfil(nomProfil); return this.getListeModuleDroit(profil); }
	 */

}
