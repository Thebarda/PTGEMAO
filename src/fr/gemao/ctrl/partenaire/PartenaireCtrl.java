package fr.gemao.ctrl.partenaire;

import java.text.ParseException;
import java.util.List;

import fr.gemao.entity.partenaire.Partenaire;
import fr.gemao.sql.DAOFactory;
import fr.gemao.sql.partenaire.PartenaireDAO;

public class PartenaireCtrl {
	public static void ajouterPartenaire(Partenaire obj){
		PartenaireDAO partenaire = new PartenaireDAO(DAOFactory.getInstance());
		partenaire.create(obj);
	}
	
	public static List<Partenaire> getAll(){
		PartenaireDAO partenaire = new PartenaireDAO(DAOFactory.getInstance());
		try {
			return partenaire.getAll();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Partenaire getPartenaireById(int int1) {
		PartenaireDAO partenaire = new PartenaireDAO(DAOFactory.getInstance());
		return partenaire.get(int1);
	}
}
