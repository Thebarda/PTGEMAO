package fr.gemao.sql;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;

import fr.gemao.sql.adherent.AdherentDAO;
import fr.gemao.sql.adherent.FamilleDAO;
import fr.gemao.sql.adherent.MotifSortieDAO;
import fr.gemao.sql.adherent.ResponsableDAO;
import fr.gemao.sql.administration.DroitDAO;
import fr.gemao.sql.administration.ModificationDAO;
import fr.gemao.sql.administration.ModuleDAO;
import fr.gemao.sql.administration.ProfilDAO;
import fr.gemao.sql.administration.TypeDroitDAO;
import fr.gemao.sql.cours.CoursDAO;
import fr.gemao.sql.cours.DisciplineDAO;
import fr.gemao.sql.cours.MatiereDAO;
import fr.gemao.sql.cours.NiveauDAO;
import fr.gemao.sql.cours.ProfDAO;
import fr.gemao.sql.cours.SalleDAO;
import fr.gemao.sql.exception.DAOConfigurationException;
import fr.gemao.sql.location.LocationDAO;
import fr.gemao.sql.materiel.CategorieDAO;
import fr.gemao.sql.materiel.DesignationDAO;
import fr.gemao.sql.materiel.EtatDAO;
import fr.gemao.sql.materiel.FournisseurDAO;
import fr.gemao.sql.materiel.MarqueDAO;
import fr.gemao.sql.materiel.MaterielDAO;
import fr.gemao.sql.materiel.ReparateurDAO;
import fr.gemao.sql.materiel.ReparationDAO;
import fr.gemao.sql.planning.ContrainteDAO;
import fr.gemao.sql.planning.CreneauDAO;
import fr.gemao.sql.planning.HeurePlanningDAO;
import fr.gemao.sql.planning.PlanningDAO;

public class DAOFactory {

	private static final String FICHIER_PROPERTIES = "./fr/gemao/sql/dao.properties";
	private static final String PROPERTY_URL = "url";
	private static final String PROPERTY_DRIVER = "driver";
	private static final String PROPERTY_NOM_UTILISATEUR = "nomutilisateur";
	private static final String PROPERTY_MOT_DE_PASSE = "motdepasse";

	private static boolean CHARGE = false;
	private static DAOFactory instance;
	private static BoneCP connectionPool;

	DAOFactory(BoneCP connectionPool) {
		DAOFactory.connectionPool = connectionPool;
	}

	/*
	 * Méthode chargée de récupérer les informations de connexion à la base de
	 * données, charger le driver JDBC et retourner une instance de la Factory
	 */
	public static DAOFactory getInstance() throws DAOConfigurationException {

		if (!DAOFactory.CHARGE) {
			DAOFactory.configurePool();
		}
		return instance;
	}

	private static void configurePool() {
		DAOFactory instance;
		Properties properties = new Properties();
		String url;
		String driver;
		String nomUtilisateur;
		String motDePasse;
		BoneCP pool = null;

		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		InputStream fichierProperties = classLoader.getResourceAsStream(FICHIER_PROPERTIES);

		if (fichierProperties == null) {
			throw new DAOConfigurationException("Le fichier properties " + FICHIER_PROPERTIES + " est introuvable.");
		}

		try {
			properties.load(fichierProperties);
			url = properties.getProperty(PROPERTY_URL);
			driver = properties.getProperty(PROPERTY_DRIVER);
			nomUtilisateur = properties.getProperty(PROPERTY_NOM_UTILISATEUR);
			motDePasse = properties.getProperty(PROPERTY_MOT_DE_PASSE);
		} catch (IOException e) {
			throw new DAOConfigurationException("Impossible de charger le fichier properties " + FICHIER_PROPERTIES, e);
		}

		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			throw new DAOConfigurationException("Le driver est introuvable dans le classpath.", e);
		}

		try {
			/*
			 * Création d'une configuration de pool de connexions via l'objet
			 * BoneCPConfig et les différents setters associés.
			 */
			BoneCPConfig config = new BoneCPConfig();
			/* Mise en place de l'URL, du nom et du mot de passe */
			config.setJdbcUrl(url);
			config.setUsername(nomUtilisateur);
			config.setPassword(motDePasse);
			/* Paramétrage de la taille du pool */
			config.setMinConnectionsPerPartition(5); // Nombre minimale de
														// connection par
														// partition
			config.setMaxConnectionsPerPartition(30); // Nombre de commnection
														// max par partition
			config.setPartitionCount(3); // Nonbre de partition
			config.setDisableConnectionTracking(true);
			/*
			 * Création du pool à partir de la configuration, via l'objet BoneCP
			 */
			pool = new BoneCP(config);
		} catch (SQLException e) {
			throw new DAOConfigurationException("Erreur de configuration du pool de connexions.", e);
		}
		instance = new DAOFactory(pool);
		DAOFactory.CHARGE = true;
		DAOFactory.instance = instance;
	}

	/* Méthode chargée de fournir une connexion à la base de données */
	public Connection getConnection() throws SQLException {
		return connectionPool.getConnection();
	}

	/**
	 * Retourne le pool de connection
	 * 
	 * @return pool de connection
	 */
	public static BoneCP getConnectionPool() {
		return connectionPool;
	}

	/*
	 * Toutes les fonctions qui suivent retournent un Objet DAO associé à une
	 * classe Entity. Exemple getAdherentDAO retourne un AdherentDAO, il permet
	 * de faire le CRUD sur la base pour l'objet Adhérent. Chaque DAO hérite de
	 * IDAO.
	 */

	public AdherentDAO getAdherentDAO() {
		return new AdherentDAO(this);
	}

	public AdresseDAO getAdresseDAO() {
		return new AdresseDAO(this);
	}

	public PersonneDAO getPersonneDAO() {
		return new PersonneDAO(this);
	}

	public ParametreDAO getParametreDAO() {
		return new ParametreDAO(this);
	}

	public EtatDAO getEtatDAO() {
		return new EtatDAO(this);
	}

	public CategorieDAO getCategorieDAO() {
		return new CategorieDAO(this);
	}

	public MarqueDAO getMarqueDAO() {
		return new MarqueDAO(this);
	}

	public ModificationDAO getModificationDAO() {
		return new ModificationDAO(this);
	}

	public DesignationDAO getDesignationDAO() {
		return new DesignationDAO(this);
	}

	public PersonnelDAO getPersonnelDAO() {
		return new PersonnelDAO(this);
	}
	
	public LocationDAO getLocationDAO(){
		return new LocationDAO(this);
	}

	public ResponsabiliteDAO getResponsabiliteDAO() {
		return new ResponsabiliteDAO(this);
	}

	public MaterielDAO getMaterielDAO() {
		return new MaterielDAO(this);
	}

	public ReparationDAO getReparationDAO() {
		return new ReparationDAO(this);
	}

	public ResponsableDAO getResponsableDAO() {
		return new ResponsableDAO(this);
	}

	public DisciplineDAO getDisciplineDAO() {
		return new DisciplineDAO(this);
	}

	public CommuneDAO getCommuneDAO() {
		return new CommuneDAO(this);
	}

	public ContratDAO getContratDAO() {
		return new ContratDAO(this);
	}

	public TypeContratDAO getTypeContratDAO() {
		return new TypeContratDAO(this);
	}

	public MotifFinContratDAO getMotifFinContratDAO() {
		return new MotifFinContratDAO(this);
	}

	public DiplomeDAO getDiplomeDAO() {
		return new DiplomeDAO(this);
	}

	public FournisseurDAO getFournisseurDAO() {
		return new FournisseurDAO(this);
	}

	public MotifSortieDAO getMotifSortieDAO() {
		return new MotifSortieDAO(this);
	}

	public ReparateurDAO getReparateurDAO() {
		return new ReparateurDAO(this);
	}

	public TypeDroitDAO geTypeDroitDAO() {
		return new TypeDroitDAO(this);
	}

	public ModuleDAO getModuleDAO() {
		return new ModuleDAO(this);
	}

	public DroitDAO getDroitDAO() {
		return new DroitDAO(this);
	}

	public ProfilDAO getProfilDAO() {
		return new ProfilDAO(this);
	}

	public NiveauDAO getNiveauDAO() {
		return new NiveauDAO(this);
	}

	public MatiereDAO getMatiereDAO() {
		return new MatiereDAO(this);
	}

	public SalleDAO getSalleDAO() {
		return new SalleDAO(this);
	}

	public FamilleDAO getFamilleDAO() {
		return new FamilleDAO(this);
	}

	public CreneauDAO getCreneauDAO() {
		return new CreneauDAO(this);
	}

	public PlanningDAO getPlanningDAO() {
		return new PlanningDAO(this);
	}

	public JourDAO getJourDAO() {
		return new JourDAO(this);
	}

	/**
	 * @return
	 */
	public HeurePlanningDAO getHeurePlanningDAO() {
		return new HeurePlanningDAO(this);
	}

	/**
	 * @return
	 */
	public CoursDAO getCoursDAO() {
		return new CoursDAO(this);
	}

	/**
	 * @return
	 */
	public ProfDAO getProfDAO() {
		return new ProfDAO(this);
	}

	public ContrainteDAO getContrainteDAO() {
		return new ContrainteDAO(this);
	}

}
