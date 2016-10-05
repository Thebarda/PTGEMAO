package fr.gemao.ctrl;

import java.util.ArrayList;
import java.util.List;

import fr.gemao.entity.personnel.Contrat;
import fr.gemao.sql.ContratDAO;
import fr.gemao.sql.DAOFactory;

public class RecupererContratCtrl {

	/**
	 * Constructeur
	 */
	public RecupererContratCtrl(){
	}
	
	/**
	 * Méthode permettant de récupérer la liste des contrats selon l'identifiant de la personne
	 * @param id
	 * @return liste des contrat
	 */
	public List<Contrat> recupererContrats(long idPersonne){
		DAOFactory co = DAOFactory.getInstance();
		ContratDAO contratDAO = co.getContratDAO();
		
		List<Contrat>contrats = contratDAO.getContratsParPersonne(idPersonne);
		
		return contrats;		
	}
	
	/**
	 * Méthode permettant de récupérer l'ensemble des contrats
	 * @return la liste des contrats de la BD
	 */
	public List<Contrat> recupererTousContrats(){
		List<Contrat> listeContrats = new ArrayList<Contrat>();
		DAOFactory co = DAOFactory.getInstance();
		ContratDAO contratDAO = co.getContratDAO();
		
		listeContrats = contratDAO.getAll();
		
		return listeContrats;
	}
}
