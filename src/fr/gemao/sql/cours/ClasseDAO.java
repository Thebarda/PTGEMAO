package fr.gemao.sql.cours;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import fr.gemao.entity.cours.Classe;
import fr.gemao.sql.DAOFactory;
import fr.gemao.sql.IDAO;

public class ClasseDAO extends IDAO<Classe> {

	public ClasseDAO(DAOFactory factory) {
		super(factory);
	}

	@Override
	public Classe create(Classe obj) {
		throw new UnsupportedOperationException("Méthode non implémentée.");
	}

	@Override
	public void delete(Classe obj) {
		throw new UnsupportedOperationException("Méthode non implémentée.");
	}

	@Override
	public Classe update(Classe obj) {
		throw new UnsupportedOperationException("Méthode non implémentée.");
	}

	@Override
	public Classe get(long id) {
		throw new UnsupportedOperationException("Méthode non implémentée.");
	}

	@Override
	public List<Classe> getAll() {
		throw new UnsupportedOperationException("Méthode non implémentée.");
	}

	@Override
	protected Classe map(ResultSet result) throws SQLException {
		throw new UnsupportedOperationException("Méthode non implémentée.");
	}

}
