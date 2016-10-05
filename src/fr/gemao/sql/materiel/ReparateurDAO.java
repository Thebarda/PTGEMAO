package fr.gemao.sql.materiel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import fr.gemao.entity.materiel.Reparateur;
import fr.gemao.sql.DAOFactory;
import fr.gemao.sql.IDAO;
import fr.gemao.sql.exception.DAOException;
import fr.gemao.sql.util.DAOUtilitaires;

public class ReparateurDAO extends IDAO<Reparateur> {

	public ReparateurDAO(DAOFactory conn) {
		super(conn);
	}

	@Override
	public Reparateur create(Reparateur obj) {
		if (obj == null) {
			throw new NullPointerException(
					"Le reparateur ne doit pas etre null");
		}

		long id = 0;

		PreparedStatement requete = null;
		ResultSet result = null;
		try {
			String sql = "INSERT INTO REPARATEUR (idReparateur," + "nom)"
					+ "VALUES (?,?);";
			requete = factory.getConnection().prepareStatement(sql,
					Statement.RETURN_GENERATED_KEYS);
			requete.setInt(1, obj.getIdReparateur());
			requete.setString(2, obj.getNom());

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
	public void delete(Reparateur obj) {
		if (obj == null) {
			throw new NullPointerException(
					"La Reparation ne doit pas �tre null");
		}

		if (obj.getIdReparateur() == 0) {
			throw new NullPointerException(
					"Le Reparateur ne peut pas avoir un id = 0");
		}

		Statement stat = null;
		try {
			stat = factory.getConnection().createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			stat.execute("DELETE FROM REPARATION WHERE idReparation = "
					+ obj.getIdReparateur() + ";");
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
	public Reparateur update(Reparateur obj) {
		if(obj == null){
			throw new NullPointerException("Le reparateur ne doit pas etre null");
		}
		Connection connexion = null;
		PreparedStatement requete = null;
		ResultSet result = null;
		String sql = "UPDATE Reparateur SET nom = ?"
				+ "WHERE idReparateur = ?;";
		try {
			connexion = factory.getConnection();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion,
					sql, false,
					obj.getNom(),
					obj.getIdReparateur());
			requete.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			DAOUtilitaires.fermeturesSilencieuses(result, requete, connexion);
		}
		return this.get(obj.getIdReparateur());
	}

	@Override
	public Reparateur get(long id) {
		Reparateur reparateur = null;

		PreparedStatement requete = null;
		ResultSet result = null;
		try {
			String sql = "SELECT * FROM REPARATEUR WHERE idReparateur = ?;";
			requete = factory.getConnection().prepareStatement(sql);
			requete.setLong(1, id);
			result = requete.executeQuery();

			if (result.first()) {
				reparateur = this.map(result);

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
		return reparateur;
	}

	@Override
	public List<Reparateur> getAll() {
		List<Reparateur> liste = new ArrayList<>();

		Reparateur reparateur = null;

		PreparedStatement requete = null;
		ResultSet result = null;
		try {
			String sql = "SELECT * FROM Reparateur;";
			requete = factory.getConnection().prepareStatement(sql);
			result = requete.executeQuery();

			while (result.next()) {
				reparateur = this.map(result);
				liste.add(reparateur);
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
	protected Reparateur map(ResultSet result) throws SQLException {
		return new Reparateur(result.getInt("idReparateur"),
				result.getString("nom"));
	}

}
