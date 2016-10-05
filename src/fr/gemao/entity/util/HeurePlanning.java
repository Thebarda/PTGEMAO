package fr.gemao.entity.util;

/**
 * 
 * @author FELTON-GROS-METAYER-PIAT-VAREILLE
 *
 *         Classe définissant le système d'heure utilisé dans le planning
 *         organisationnel
 */
public class HeurePlanning {
	private static final String SEPARATEUR = " : ";
	private final int heure;
	private final int minute;

	public HeurePlanning(int heure, int minute) {
		if (heure < 0 || heure > 23) {
			throw new IllegalArgumentException("0 <= heure < 24");
		}

		if (minute < 0 || minute > 59) {
			throw new IllegalArgumentException("0 <= minute < 60");
		}

		if (minute % 15 != 0) {
			throw new IllegalArgumentException("minute = {0, 15, 30, 45}");
		}

		this.heure = heure;
		this.minute = minute;
	}

	@Override
	public String toString() {
		
		String heure = this.heure < 10 ? "0" + this.heure : this.heure + "";
		String minute = this.minute < 10 ? "0" + this.minute : this.minute + "";
		
		return heure + SEPARATEUR + minute;
	}

	public int getHeure() {
		return heure;
	}

	public int getMinute() {
		return minute;
	}

	/**
	 * Méthode statique qui permet la conversion d'une {@link HeurePlanning} en
	 * {@link Integer}, représentant son identifiant en base de données.
	 * 
	 * @param heure
	 *            l'heure planning a convertir
	 * @return l'identifiant correspondant à cette heure planning
	 */
	public static int conversionHeuretoIdBDD(HeurePlanning heure) {
		return heure.getHeure() * 4 + heure.getMinute() / 15 + 1;
	}

	/**
	 * Méthode statique permettant de calculer une heure à partir d'une heure de
	 * début et une durée
	 * 
	 * @param hp1
	 *            L'heure de début
	 * @param duree
	 *            La durée
	 * @return La nouvelle HeurePlanning
	 */
	public static HeurePlanning getHeureAjoutDuree(HeurePlanning hp1, HeurePlanning duree) {
		int heure = hp1.getHeure() + duree.getHeure();
		int minutes = hp1.getMinute() + duree.getMinute();

		if (minutes >= 60) {
			heure++;
			minutes -= 60;
		}

		return new HeurePlanning(heure, minutes);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HeurePlanning other = (HeurePlanning) obj;
		if (heure != other.heure)
			return false;
		if (minute != other.minute)
			return false;
		return true;
	}

	public static HeurePlanning depuisNombreQuartDHeure(int nb) {
		if (nb < 0) {
			throw new IllegalArgumentException("nb < 0");
		}

		final int heures = nb / 4;
		final int minutes = (nb % 4) * 15;
		return new HeurePlanning(heures, minutes);
	}

	public static HeurePlanning depuisString(String chaine) {
		String[] tab = chaine.split(HeurePlanning.SEPARATEUR);

		final Integer heures = Integer.parseInt(tab[0]);
		final Integer minutes = Integer.parseInt(tab[1]);
		return new HeurePlanning(heures, minutes);
	}

	/**
	 * Méthode qui implémente le inférieur ou égal d'une heure
	 * 
	 * @param heureDeb
	 * @return
	 */
	public boolean isStrictInferieur(HeurePlanning heure) {
		if (this.getHeure() < heure.getHeure()) {
			return true;
		}

		if (this.getHeure() == heure.getHeure() && this.getMinute() < heure.getMinute()) {
			return true;
		}

		return false;
	}

	/**
	 * Méthode qui implémente le supérieur ou égal d'une heure
	 * 
	 * @param heureDeb
	 * @return
	 */
	public boolean isStrictSuperieur(HeurePlanning heure) {
		if (this.getHeure() > heure.getHeure()) {
			return true;
		}

		if (this.getHeure() == heure.getHeure() && this.getMinute() > heure.getMinute()) {
			return true;
		}

		return false;
	}
	
	public boolean isInferieur(HeurePlanning heure) {
		return !this.isStrictSuperieur(heure);
	}
	
	public boolean isSuperieur(HeurePlanning heure) {
		return !this.isStrictInferieur(heure);
	}
}
