package fr.gemao.form.cheque;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import fr.gemao.form.util.Form;

public class ChequeForm {
	
	private static final String CHAMP_DATE_PAIEMENT = "datePaiement";
	private static final String CHAMP_MONTANT_CHEQUE = "montantCheque";
	private static final String CHAMP_NUMERO_CHEQUE = "numeroCheque";
	private static final String CHAMP_DATE_ENCAISSEMENT = "dateEncaissement";
	

	private String datePaiement;
	private String montantCheque;
	private String numeroCheque;
	private String dateEncaissement;
	
	private Map<String, String> erreurs = new HashMap<String, String>();

	
	public ChequeForm(HttpServletRequest request) {
		datePaiement = Form.getValeurChamp(request, CHAMP_DATE_PAIEMENT);
		montantCheque = Form.getValeurChamp(request, CHAMP_MONTANT_CHEQUE);
		numeroCheque = Form.getValeurChamp(request, CHAMP_NUMERO_CHEQUE);
		dateEncaissement = Form.getValeurChamp(request, CHAMP_DATE_ENCAISSEMENT);
	}
	
	public ChequeForm(){
		
	}
	
	private void setErreur(String champ, String message) {
		erreurs.put(champ, message);
	}

	public String getdatePaiement() {
		return datePaiement;
	}

	public void setdatePaiement(String datePaiement) {
		this.datePaiement = datePaiement;
	}
	
	public String getmontantCheque() {
		return montantCheque;
	}

	public void setmontantCheque(String montantCheque) {
		this.montantCheque = montantCheque;
	}

	public String getnumeroCheque() {
		return numeroCheque;
	}

	public void setnumeroCheque(String numeroCheque) {
		this.numeroCheque = numeroCheque;
	}

	public String getdateEncaissement() {
		return dateEncaissement;
	}

	public void setdateEncaissement(String dateEncaissement) {
		this.dateEncaissement = dateEncaissement;
	}

	public Map<String, String> getErreurs(){
		return erreurs;
	}
	
	private static String getValeurChamp(HttpServletRequest request, String nomChamp){
		String valeur = Form.getValeurChamp(request, nomChamp);
		if(valeur ==null){
			return null;
		} else {
			return valeur;
		}
	}
	
	private void validationDates(String datePaiement, String dateEncaissement) throws Exception{
		if (datePaiement == null || datePaiement.equals("")){
			throw new Exception("Merci de choisir une date de paiement valide");
		}
		if (dateEncaissement == null || dateEncaissement.equals("")){
			throw new Exception("Merci de choisir une date d'encaissement valide");
		}
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date datePaie = dateFormat.parse(datePaiement);
		Date dateEncai = dateFormat.parse(dateEncaissement);
		if (datePaie.after(dateEncai)){
			throw new Exception("Merci de spécifier une date de paiement inférieure à la date d'encaissement");
		}
	}
	
	private void validationMontant(String montantCheque) throws Exception{
		int nb = Integer.parseInt(montantCheque);
		if (nb <= 0) {
			throw new Exception("Merci de préciser un montant supérieur à 0");
		}
	}
	
	private void validationNumeroCheque(String numeroCheque) throws Exception{
		if (numeroCheque.length() != 11){
			throw new Exception("Merci de spécifier un numéro de chèque valide");
		}
		try {
			Integer.parseInt(numeroCheque);
		} catch (NumberFormatException e){
			System.out.println("Le numéro spécifier n'est pas valide");
		}
		
	}
	
	public void testerCheque(HttpServletRequest request){
		datePaiement = this.getValeurChamp(request, CHAMP_DATE_PAIEMENT);
		montantCheque = this.getValeurChamp(request, CHAMP_MONTANT_CHEQUE);
		numeroCheque = this.getValeurChamp(request, CHAMP_NUMERO_CHEQUE);
		dateEncaissement = this.getValeurChamp(request, CHAMP_DATE_ENCAISSEMENT);
		
		try{
			validationMontant(montantCheque);
			}catch(Exception e){
				setErreur(CHAMP_MONTANT_CHEQUE, e.getMessage());
		}
		
		try{
			validationDates(datePaiement, dateEncaissement);
		}catch (Exception e){
			setErreur(CHAMP_DATE_PAIEMENT, e.getMessage());
		}
		
		try{
			validationNumeroCheque(numeroCheque);
		}catch(Exception e){
			setErreur(CHAMP_NUMERO_CHEQUE, e.getMessage());
		}
		
	}
}
