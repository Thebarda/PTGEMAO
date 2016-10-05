package fr.gemao.form.personnel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import fr.gemao.ctrl.RecupererResponsabiliteCtrl;
import fr.gemao.ctrl.TypeContratCtrl;
import fr.gemao.ctrl.personnel.PersonnelCtrl;
import fr.gemao.ctrl.planning.DisciplineCtrl;
import fr.gemao.entity.Adresse;
import fr.gemao.entity.Commune;
import fr.gemao.entity.cours.Discipline;
import fr.gemao.entity.personnel.Contrat;
import fr.gemao.entity.personnel.Diplome;
import fr.gemao.entity.personnel.Personnel;
import fr.gemao.entity.personnel.Responsabilite;
import fr.gemao.entity.personnel.TypeContrat;

/**
 * Classe de validation du formulaire Personnel
 * 
 * @author Coco
 *
 */
public class PersonnelForm {

	// Informations relatives à la personne
	private static final String CHAMP_LISTERESPONSABILITE = "fonction";
	private static final String CHAMP_LISTEDIPLOME = "diplome";
	private static final String CHAMP_DISCIPLINES = "discipline";

	private static final String CHAMP_NOM = "nom";
	private static final String CHAMP_PRENOM = "prenom";

	private static final String CHAMP_LOGIN = "login";
	private static final String CHAMP_PASSWORD = "password";
	private static final String CHAMP_POINTSANCIEN = "pointsAncien";
	private static final String CHAMP_NUMRUE = "numRue";
	private static final String CHAMP_NOMRUE = "nomRue";
	private static final String CHAMP_INFOCOMPL = "infoCompl";
	private static final String CHAMP_VILLE = "ville";
	private static final String CHAMP_CODE = "code";
	private static final String CHAMP_TELFIXE = "telFixe";
	private static final String CHAMP_TELPORT = "telPort";
	private static final String CHAMP_EMAIL = "email";
	private static final String CHAMP_NUMERO_SS = "numeroSS";
	private static final String CHAMP_MEMBRECA = "membreCA";

	private static final String CHAMP_TYPE_CONTRAT = "type";
	private static final String CHAMP_DUREE_CONTRAT = "dureeContrat";
	private static final String CHAMP_DATE_DEB_CONTRAT = "datedeb";

	// Informations relatives à la personne
	private String nom;
	private String prenom;
	private Date dateNaissance;
	private Integer codePostNaiss;
	private Commune comNaiss;
	private String telFixe;
	private String telPort;
	private String email;
	private String numeroSS;

	// Adresse
	private Adresse adresse;
	private Commune commune;

	// Commune
	private Integer codePostal;
	private String nomCommune;

	// Droit à l'image
	private String droitImage;

	// Discipline
	private String discipline;
	private String classe;

	// Membre CA
	private String membreCA;

	// Inscription
	private String dateEntree;

	private List<Responsabilite> listeResponsabilite;
	private List<Diplome> listeDiplomes;
	private List<Discipline> listeDisciplines;

	private Integer idContrat;
	private String login;
	private String password;
	private int pointsAncien;

	private String resultat;
	private Map<String, String> erreurs = new HashMap<String, String>();

	public List<Responsabilite> getListeResponsabilite() {
		return listeResponsabilite;
	}

	public List<Diplome> getListeDiplomes() {
		return listeDiplomes;
	}

	public List<Discipline> getListeDisciplines() {
		return listeDisciplines;
	}

	public Integer getIdContrat() {
		return idContrat;
	}

	public String getLogin() {
		return login;
	}

	public String getPassword() {
		return password;
	}

	public int getPointsAncien() {
		return pointsAncien;
	}

	public String getNom() {
		return nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public Date getDateNaissance() {
		return dateNaissance;
	}

	public Integer getCodePostNaiss() {
		return codePostNaiss;
	}

	public Commune getComNaiss() {
		return comNaiss;
	}

	public String getTelFixe() {
		return telFixe;
	}

	public String getTelPort() {
		return telPort;
	}

	public String getEmail() {
		return email;
	}

	public Integer getCodePostal() {
		return codePostal;
	}

	public String getNomCommune() {
		return nomCommune;
	}

	public String getDroitImage() {
		return droitImage;
	}

	public String getDiscipline() {
		return discipline;
	}

	public String getClasse() {
		return classe;
	}

	public String getDateEntree() {
		return dateEntree;
	}

	public Adresse getAdresse() {
		return adresse;
	}

	public String getNumeroSS() {
		return numeroSS;
	}

	public String getMembreCA() {
		return membreCA;
	}

	public void setMembreCA(String membreCA) {
		this.membreCA = membreCA;
	}

	public void setNumeroSS(String numeroSS) {
		this.numeroSS = numeroSS;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public void setDateNaissance(Date dateNaissance) {
		this.dateNaissance = dateNaissance;
	}

	public void setCodePostNaiss(Integer codePostNaiss) {
		this.codePostNaiss = codePostNaiss;
	}

	public void setComNaiss(Commune comNaiss) {
		this.comNaiss = comNaiss;
	}

	public void setTelFixe(String telFixe) {
		this.telFixe = telFixe;
	}

	public void setTelPort(String telPort) {
		this.telPort = telPort;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setCodePostal(Integer codePostal) {
		this.codePostal = codePostal;
	}

	public void setNomCommune(String nomCommune) {
		this.nomCommune = nomCommune;
	}

	public void setDroitImage(String droitImage) {
		this.droitImage = droitImage;
	}

	public void setDiscipline(String discipline) {
		this.discipline = discipline;
	}

	public void setClasse(String classe) {
		this.classe = classe;
	}

	public void setDateEntree(String dateEntree) {
		this.dateEntree = dateEntree;
	}

	public void setListeResponsabilite(List<Responsabilite> listeResponsabilite) {
		this.listeResponsabilite = listeResponsabilite;
	}

	public void setListeDiplomes(List<Diplome> listeDiplomes) {
		this.listeDiplomes = listeDiplomes;
	}

	public void setListeDisciplines(List<Discipline> listeDisciplines) {
		this.listeDisciplines = listeDisciplines;
	}

	public void setIdContrat(Integer idContrat) {
		this.idContrat = idContrat;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setPointsAncien(int pointsAncien) {
		this.pointsAncien = pointsAncien;
	}

	public void setResultat(String resultat) {
		this.resultat = resultat;
	}

	public void setErreurs(Map<String, String> erreurs) {
		this.erreurs = erreurs;
	}

	public void setAdresse(Adresse adresse) {
		this.adresse = adresse;
	}

	public String getResultat() {
		return resultat;
	}

	public Map<String, String> getErreurs() {
		return erreurs;
	}

	/*
	 * Méthode utilitaire qui retourne null si un champ est vide, et son contenu
	 * sinon
	 */
	public static String getValeurChamp(HttpServletRequest request, String nomChamp) {
		String valeur = request.getParameter(nomChamp);

		if (valeur == null || valeur.trim().length() == 0) {
			return null;
		} else {
			return valeur;
		}
	}

	/*
	 * Ajoute un message correspondant au champ spécifié à la map des erreurs.
	 */
	public void setErreur(String champ, String message) {
		erreurs.put(champ, message);
	}

	/**
	 * Méthode permettant de valider l'ID du contrat
	 * 
	 * @param idContrat
	 *            : l'ID du contrat
	 * @throws Exception
	 */
	private void validationIdContrat(Integer idContrat) throws Exception {
		if (idContrat == null || idContrat < 0) {
			throw new Exception("Merci de saisir un ID de contrat valide.");
		}
	}

	/**
	 * Méthode permettant de valider le login du personnel
	 * 
	 * @param login
	 *            : le login
	 * @throws Exception
	 */
	private void validationLogin(String login) throws Exception {
		if (login == null || login.equals("")) {
			throw new Exception("Merci de saisir un login valide.");
		}
	}

	/**
	 * Méthode permettant de valider le password du personnel
	 * 
	 * @param password
	 *            : le password
	 * @throws Exception
	 */
	private void validationPassword(String password) throws Exception {
		if (password == null || password.equals("")) {
			throw new Exception("Merci de saisir un password valide.");
		}
	}

	/**
	 * Méthode permettant de valider le nombre de points d'ancienneté
	 * 
	 * @param pointsAncien
	 *            : le nombre de points d'ancienneté
	 * @throws Exception
	 */
	private void validationPointsAncien(int pointsAncien) throws Exception {
		if (pointsAncien < 0) {
			throw new Exception("Merci de saisir un nombre de points valide.");
		}
	}

	public List<Responsabilite> lireResponsabilites(HttpServletRequest request) {
		List<Responsabilite> list = new ArrayList<>();
		String str = null;
		Responsabilite res;
		int i = 1;

		do {
			str = getValeurChamp(request, CHAMP_LISTERESPONSABILITE + i);

			if (str != null && !str.equals("")) {
				res = RecupererResponsabiliteCtrl.recupererResponsabilite(Integer.parseInt(str));
				list.add(res);
			}
			i++;

		} while (str != null);
		return list;
	}

	/**
	 * Methode qui récupère la liste des displine
	 * 
	 * @param request
	 */
	public List<Discipline> lireDisciplines(HttpServletRequest request) {
		String str = null;
		Discipline disc;
		List<Discipline> listDisciplines = new ArrayList<>();
		int i = 1;

		do {
			str = getValeurChamp(request, CHAMP_DISCIPLINES + i);
			i++;

			if (str != null && !str.equals("")) {
				disc = DisciplineCtrl.get(Integer.parseInt(str));
				listDisciplines.add(disc);
			}

		} while (str != null);

		i = 1;
		do {
			str = getValeurChamp(request, CHAMP_DISCIPLINES + "Anciennes" + i);
			i++;

			if (str != null && !str.equals("")) {
				String[] parts = str.split("-");
				disc = DisciplineCtrl.recupererDiscipline(parts[0], parts[1]);
				listDisciplines.add(disc);
			}
		} while (str != null);
		return listDisciplines;
	}

	public List<Diplome> lireDiplomes(HttpServletRequest request) {
		List<Diplome> list = new ArrayList<>();
		String str = null;
		Diplome dip;
		int i = 1;

		do {
			str = getValeurChamp(request, CHAMP_LISTEDIPLOME + i);

			if (str != null && !str.equals("")) {
				dip = new Diplome(null, str);
				list.add(dip);
			}
			i++;
		} while (str != null);
		return list;
	}

	/**
	 * Methode qui récupère la liste des contrat
	 * 
	 * @param request
	 */
	public List<Contrat> lireContrats(HttpServletRequest request) {
		String type = null;
		String strdateDeb = null;
		String duree = null;
		Contrat contrat;
		List<Contrat> listContrat = new ArrayList<>();
		int i = 1;

		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

		TypeContratCtrl typeContratCtrl = new TypeContratCtrl();
		TypeContrat typeContrat = null;
		Date dateDeb = null;

		do {
			type = getValeurChamp(request, CHAMP_TYPE_CONTRAT + i);
			strdateDeb = getValeurChamp(request, CHAMP_DATE_DEB_CONTRAT + i);
			duree = getValeurChamp(request, CHAMP_DUREE_CONTRAT + i);

			contrat = new Contrat();
			i++;

			if (type != null && !type.equals("")) {
				typeContrat = typeContratCtrl.recupererTypeContrat(Integer.parseInt(type));
				contrat.setTypeContrat(typeContrat);
				try {
					dateDeb = formatter.parse(strdateDeb);
				} catch (ParseException e) {
					this.setErreur("Date", e.getMessage());
				}
				contrat.setDateDebut(dateDeb);
				contrat.setDateFin(
						PersonnelCtrl.CalculerDateFinContrat(contrat.getDateDebut(), Integer.parseInt(duree)));
				listContrat.add(contrat);
			}

		} while (type != null);

		return listContrat;
	}

	/**
	 * Méthode permettant de tester un personnel
	 * 
	 * @param request
	 */
	public Personnel testerPersonnel(HttpServletRequest request) {
		String numRue;
		String nomRue;
		String infoCompl;
		String ville;
		int code;

		listeResponsabilite = lireResponsabilites(request);
		listeDiplomes = lireDiplomes(request);

		nom = getValeurChamp(request, CHAMP_NOM);
		prenom = getValeurChamp(request, CHAMP_PRENOM);
		login = getValeurChamp(request, CHAMP_LOGIN);
		password = getValeurChamp(request, CHAMP_PASSWORD);
		pointsAncien = Integer.parseInt(getValeurChamp(request, CHAMP_POINTSANCIEN));
		numRue = getValeurChamp(request, CHAMP_NUMRUE);
		nomRue = getValeurChamp(request, CHAMP_NOMRUE);
		infoCompl = getValeurChamp(request, CHAMP_INFOCOMPL);
		ville = getValeurChamp(request, CHAMP_VILLE);
		code = Integer.parseInt(getValeurChamp(request, CHAMP_CODE));
		telFixe = getValeurChamp(request, CHAMP_TELFIXE);
		telPort = getValeurChamp(request, CHAMP_TELPORT);
		email = getValeurChamp(request, CHAMP_EMAIL);
		numeroSS = getValeurChamp(request, CHAMP_NUMERO_SS);
		membreCA = getValeurChamp(request, CHAMP_MEMBRECA);

		adresse = new Adresse();
		commune = new Commune();

		adresse.setNumRue(numRue);
		adresse.setNomRue(nomRue);
		adresse.setInfoCompl(infoCompl);

		commune.setCodePostal(code);
		commune.setNomCommune(ville);

		adresse.setCommune(commune);

		/* Validation du login */
		try {
			validationLogin(login);
		} catch (Exception e) {
			setErreur(CHAMP_LOGIN, e.getMessage());
		}

		/* Validation du password */
		try {
			validationPassword(password);
		} catch (Exception e) {
			setErreur(CHAMP_PASSWORD, e.getMessage());
		}

		/* Validation du nombre de points d'ancienneté */
		try {
			validationPointsAncien(pointsAncien);
		} catch (Exception e) {
			setErreur(CHAMP_POINTSANCIEN, e.getMessage());
		}

		if (getErreurs().isEmpty()) {
			Personnel pers = new Personnel();
			return pers;
		}

		return null;
	}
}