package fr.gemao.sql.partenaire;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import fr.gemao.entity.materiel.ChequeLocation;
import fr.gemao.entity.partenaire.Partenaire;
import fr.gemao.sql.DAOFactory;
import fr.gemao.sql.IDAO;
import fr.gemao.sql.exception.DAOException;
import fr.gemao.sql.util.DAOUtilitaires;

public class PartenaireDAO extends IDAO<Partenaire>{

	public PartenaireDAO(DAOFactory factory) {
		super(factory);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Partenaire create(Partenaire obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Partenaire obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Partenaire update(Partenaire obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Partenaire get(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Partenaire> getAll() throws ParseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Partenaire map(ResultSet result) throws SQLException, ParseException {
		// TODO Auto-generated method stub
		return null;
	}

	

}
