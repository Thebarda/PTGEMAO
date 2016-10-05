package fr.gemao.sql.materiel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import fr.gemao.entity.materiel.Reparation;
import fr.gemao.sql.DAOFactory;
import fr.gemao.sql.IDAO;
import fr.gemao.sql.exception.DAOException;
import fr.gemao.sql.util.DAOUtilitaires;

public class ReparationDAO extends IDAO<Reparation> {

	public ReparationDAO(DAOFactory conn) {
		super(conn);
	}

	@Override
	public Reparation create(Reparation obj) {
		if (obj == null) {
			throw new NullPointerException("Le materiel ne doit pas �tre null");
		}

		long id = 0;

		PreparedStatement requete = null;
		ResultSet result = null;
		try {
			String sql = "INSERT INTO REPARATION(idReparation,"
					+ "idReparateur," + "dateCertificat" + "VALUES (?,?,?);";
			requete = factory.getConnection().prepareStatement(sql,
					Statement.RETURN_GENERATED_KEYS);
			requete.setInt(1, obj.getIdReparation());
			requete.setInt(2, obj.getReparateur().getIdReparateur());
			requete.setDate(3, obj.getDateCertificat());

			requete.executeUpdate();

			result = requete.getGeneratedKeys();
			if (result != null && result.first()) {
				id = result.getLong(1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (requete != null) {
				try {
					if (result != null) {
						result.close();
					}
					requete.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		return this.get(id);
	}

	@Override
	public void delete(Reparation obj) {
		if (obj == null) {
			throw new NullPointerException(
					"La Reparation ne doit pas �tre null");
		}

		if (obj.getIdReparation() == 0) {
			throw new NullPointerException(
					"La reparation ne peut pas avoir un id = 0");
		}

		Statement stat = null;
		try {
			stat = factory.getConnection().createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			stat.execute("DELETE FROM REPARATION WHERE idReparation = "
					+ obj.getIdReparation() + ";");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (stat != null) {
				try {
					stat.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public Reparation update(Reparation obj) {
		if(obj == null){
			throw new NullPointerException("Reparation NULL");
		}
		Connection connexion = null;
		PreparedStatement requete = null;
		ResultSet result = null;
		String sql ="UPDATE Reparation SET idReparateur =?, dateCertificat = ?"
				+"WHERE idReparation =?;";
		try{
			connexion = factory.getConnection();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion,
					sql, false,
					obj.getReparateur().getIdReparateur(),
					obj.getDateCertificat(),
					obj.getIdReparation());
					
		requete.executeUpdate();
	} catch (SQLException e) {
		throw new DAOException(e);
	} finally {
		DAOUtilitaires.fermeturesSilencieuses(result, requete, connexion);
	}
	return this.get(obj.getIdReparation());
}
	

	@Override
	public Reparation get(long id) {
		Reparation reparation = null;

		PreparedStatement requete = null;
		ResultSet result = null;
		try {
			String sql = "SELECT * FROM REPARATION WHERE idReparation = ?;";
			requete = factory.getConnection().prepareStatement(sql);
			requete.setLong(1, id);
			result = requete.executeQuery();

			if (result.first()) {
				reparation = new Reparation(result.getInt("idReparation"),
						new ReparateurDAO(factory).get(result
								.getInt("idReparateur")),
						result.getDate("dateCertificat"));
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		} finally {
			if (requete != null) {
				try {
					if (result != null) {
						result.close();
					}
					requete.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return reparation;
	}

	@Override
	public List<Reparation> getAll() {
		List<Reparation> liste = new ArrayList<>();

		Reparation reparation = null;

		PreparedStatement requete = null;
		ResultSet result = null;
		try {
			String sql = "SELECT * FROM Reparation;";
			requete = factory.getConnection().prepareStatement(sql);
			result = requete.executeQuery();

			while (result.next()) {
				reparation = new Reparation(result.getInt("idReparation"),
						new ReparateurDAO(factory).get(result
								.getInt("idReparateur")),
						result.getDate("dateCertificat"));
				liste.add(reparation);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		} finally {
			if (requete != null) {
				try {
					if (result != null) {
						result.close();
					}
					requete.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		return liste;
	}

	@Override
	protected Reparation map(ResultSet result) throws SQLException {
		return new Reparation(result.getInt("idReparation"),
				factory.getReparateurDAO().get(result
						.getInt("idReparateur")),
				result.getDate("dateCertificat"));
	}

}
