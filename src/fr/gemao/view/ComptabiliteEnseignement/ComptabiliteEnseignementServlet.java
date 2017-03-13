package fr.gemao.view.ComptabiliteEnseignement;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.gemao.ctrl.adherent.AdherentCtrl;
import fr.gemao.ctrl.adherent.FamilleCtrl;
import fr.gemao.ctrl.location.LocationCtrl;
import fr.gemao.ctrl.materiel.EtatCtrl;
import fr.gemao.ctrl.materiel.MaterielCtrl;
import fr.gemao.entity.Personne;
import fr.gemao.entity.adherent.Adherent;
import fr.gemao.entity.adherent.Famille;
import fr.gemao.entity.adherent.FamilleTableaux;
import fr.gemao.entity.materiel.Etat;
import fr.gemao.entity.materiel.Location;
import fr.gemao.entity.materiel.Materiel;
import fr.gemao.entity.materiel.Reparation;
import fr.gemao.entity.materiel.TypeLocation;
import fr.gemao.form.util.Form;
import fr.gemao.view.JSPFile;
import fr.gemao.view.Pattern;

@WebServlet(Pattern.COMPTABILITE_ENSEIGNEMENT)
public class ComptabiliteEnseignementServlet extends HttpServlet{
	
	private static final long serialVersionUID = 1L;
	private static final String FAMILLES = "familles";
	private static final String IDFAMILLE = "idFamille";
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		List<Famille> tmpFamilles = FamilleCtrl.recupererAllFamille();
		List<String> familles = new ArrayList<>();
		for(Famille f : tmpFamilles){
			familles.add('"'+f.getNomFamille()+'"');
		}
		request.setAttribute(FAMILLES, familles);
		List<Integer> annee = new ArrayList<>();
		Date date = new Date();
		int year = 1900+date.getYear();
		year--;
		while(year>=1998){
			annee.add(year);
			year--;
		}
		session.setAttribute("date", annee);
		this.getServletContext().getRequestDispatcher(JSPFile.COMPTABILITE_ENSEIGNEMENT).forward(request, response);
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		int anne;
		List<Famille> tmpFamilles = FamilleCtrl.recupererAllFamille();
		List<String> familles = new ArrayList<>();
		for(Famille f : tmpFamilles){
			familles.add('"'+f.getNomFamille()+'"');
		}
		request.setAttribute(FAMILLES, familles);
		if(Form.getValeurChamp(request, "famille")!=null){
			if(Form.getValeurChamp(request, "annee")==null){
				Calendar calendar = Calendar.getInstance();
				Date date = calendar.getTime();
				if((date.getMonth()>=0)&&(date.getMonth()<=7)){
					anne=(1900+date.getYear()-1);
				}else{
					anne=(1900+date.getYear());
				}
			}else{
				anne=Integer.parseInt(""+Form.getValeurChamp(request, "annee"));
			}
			int idFamille = FamilleCtrl.getFamille(Form.getValeurChamp(request, "famille"));
			request.setAttribute("nbEleves", FamilleCtrl.getNbEleve(idFamille));
			List<Adherent> adherents = AdherentCtrl.recupererTousAdherents();
			List<String> elevesTmp = new ArrayList<>();
			for(Adherent adh : adherents){
				if(adh.getFamille().getIdFamille()==idFamille){
					elevesTmp.add(adh.getPrenom());
				}
			}
			List<FamilleTableaux> famtabs= FamilleCtrl.getFamilleTableaux(idFamille, anne);
			if(!famtabs.isEmpty()){
				Calendar calendar = Calendar.getInstance();
				Date date = calendar.getTime();
	
				boolean chgt=false;
				FamilleTableaux famtab=null;
				int annee=0;
				//on récupère l'année la plus récente
				for(FamilleTableaux f : famtabs){
					if(f.getAnnee()>annee){
						annee=f.getAnnee();
						famtab = f;
					}
				}
				//Si l'on se trouve entre Janvier et Aout
				if((date.getMonth()>=0)&&(date.getMonth()<=7)){
					//Si les tableaux ont été créé pour l'année avant l'année n-1 Alors on créé de nouveaux tableaux
					if(famtab.getAnnee()<(1900+date.getYear()-1)){
						String tableauFicheComptable="<table><tr><td class='ignorer'></td><th class='numEleve'>Elève 1</th><th class='numEleve'>Elève 2</th><th class='numEleve'>Elève 3</th><th class='numEleve'>Elève 4</th><th class='numEleve'>Elève 5</th></tr><tr><th class='intitule'>Prénom + Nom</th><td contenteditable='true' class='nom' id='eleve_1'></td><td contenteditable='true' class='nom' id='eleve_2'></td><td contenteditable='true' class='nom' id='eleve_3'></td><td contenteditable='true' class='nom' id='eleve_4'></td><td contenteditable='true' class='nom' id='eleve_5'></td></tr><tr><th class='intitule'>QF (1, 2, 3)</th><td contenteditable='true' id='qf'></td><td contenteditable='true' id='qf'></td><td contenteditable='true' id='qf'></td><td contenteditable='true' id='qf'></td><td contenteditable='true' id='qf'></td></tr><tr><th class='intitule'>FM / inst<br>Eveil / éveil inst</th><td contenteditable='true' id='lig_0_col_0'  class='sommeMensuelle'></td><td contenteditable='true' id='lig_0_col_1' class='sommeMensuelle'></td><td contenteditable='true' id='lig_0_col_2' class='sommeMensuelle'></td><td contenteditable='true' id='lig_0_col_3' class='sommeMensuelle'></td><td contenteditable='true' id='lig_0_col_4' class='sommeMensuelle'></td></tr><tr><th class='intitule'>FM seule éveil</th><td contenteditable='true' id='lig_1_col_0' class='sommeMensuelle'></td><td contenteditable='true' id='lig_1_col_1' class='sommeMensuelle'></td><td contenteditable='true' id='lig_1_col_2' class='sommeMensuelle'></td><td contenteditable='true' id='lig_1_col_3' class='sommeMensuelle'></td><td contenteditable='true' id='lig_1_col_4' class='sommeMensuelle'></td></tr><tr><th class='intitule'>Instrument seul</th><td contenteditable='true' id='lig_2_col_0' class='sommeMensuelle'></td><td contenteditable='true' id='lig_2_col_1' class='sommeMensuelle'></td><td contenteditable='true' id='lig_2_col_2' class='sommeMensuelle'></td><td contenteditable='true' id='lig_2_col_3' class='sommeMensuelle'></td><td contenteditable='true' id='lig_2_col_4' class='sommeMensuelle'></td></tr><tr><th class='intitule'>Atelier</th><td contenteditable='true' id='lig_3_col_0' class='sommeMensuelle'></td><td contenteditable='true' id='lig_3_col_1' class='sommeMensuelle'></td><td contenteditable='true' id='lig_3_col_2' class='sommeMensuelle'></td><td contenteditable='true' id='lig_3_col_3' class='sommeMensuelle'></td><td contenteditable='true' id='lig_3_col_4' class='sommeMensuelle'></td><th class='intituleTotal'>Total mensuel</th></tr><tr><th class='intitule'>Total mensuel par élève</th><td class='sous_total' id='sommeMensuelle_0'></td><td class='sous_total' id='sommeMensuelle_1'></td><td class='sous_total' id='sommeMensuelle_2'></td><td class='sous_total' id='sommeMensuelle_3'></td><td class='sous_total' id='sommeMensuelle_4'></td><td class='total' id='totalMensuel'></td></tr><tr><th class='intitule'>Pratique collective</th><td contenteditable='true' id='lig_0_col_0' class='sommeAnnuelle'></td><td contenteditable='true' id='lig_0_col_1' class='sommeAnnuelle'></td><td contenteditable='true' id='lig_0_col_2' class='sommeAnnuelle'></td><td contenteditable='true' id='lig_0_col_3' class='sommeAnnuelle'></td><td contenteditable='true' id='lig_0_col_4' class='sommeAnnuelle'></td></tr><tr><th class='intitule'>Cotisation</th><td contenteditable='true' id='lig_1_col_0' class='sommeAnnuelle'></td><td contenteditable='true' id='lig_1_col_1' class='sommeAnnuelle'></td><td contenteditable='true' id='lig_1_col_2' class='sommeAnnuelle'></td><td contenteditable='true' id='lig_1_col_3' class='sommeAnnuelle'></td><td contenteditable='true' id='lig_1_col_4' class='sommeAnnuelle'></td></tr><tr><th class='intitule'>Membre admin</th><td contenteditable='true' id='lig_2_col_0' class='sommeAnnuelle'></td><td contenteditable='true' id='lig_2_col_1' class='sommeAnnuelle'></td><td contenteditable='true' id='lig_2_col_2' class='sommeAnnuelle'></td><td contenteditable='true' id='lig_2_col_3' class='sommeAnnuelle'></td><td contenteditable='true' id='lig_2_col_4' class='sommeAnnuelle'></td><th class='intituleTotal'>Total annuel</th></tr><tr><th class='intitule'>Total Annuel par élève</th><td class='sous_total' id='sommeAnnuelle_0'></td><td class='sous_total' id='sommeAnnuelle_1'></td><td class='sous_total' id='sommeAnnuelle_2'></td><td class='sous_total' id='sommeAnnuelle_3'></td><td class='sous_total' id='sommeAnnuelle_4'></td><td class='total' id='totalAnnuel'></td></tr><tr><th class='intitule'>Part C.E.</th><td contenteditable='true' class='ce'></td><td contenteditable='true' class='ce'></td><td contenteditable='true' class='ce'></td><td contenteditable='true' class='ce'></td><td contenteditable='true' class='ce'></td></tr><tr><td class='ignorer'></td></tr><tr><th class='intituleTotal'>Reste à la charge du client</th><td class='total' id='totalFinal'></td></tr></table>";
						String tableauRecapitulatif="<table id='tableau'><thead><tr><td class='ignorer'></td><td class='intitule'>1er Elève</td><td class='intitule'>2ème Elève</td><td class='intitule'>3ème Elève</td><td class='intitule'>4ème Elève</td><td class='intitule'>5ème Elève</td><td class='intitule'>TOTAL</td><td class='intitule'>N° de chèque</td><td class='intitule'>Montant chèque</td><td class='intitule'>Date encaissement</td><td class='intitule'>Observation</td></tr></thead><tbody><tr data='01'><td class='intitule'>SEPTEMBRE</td><td contenteditable='true' id='lig_01_col_0' class='tableauRecap'></td><td contenteditable='true' id='lig_01_col_1' class='tableauRecap'></td><td class='gris'></td><td class='gris'></td><td class='gris'></td><td id='total_01' class='sous_total'></td><td contenteditable='true'></td><td contenteditable='true' class='sommePayee' id='paiement_01'></td><td><span id='date_01'>--/--/----</span><input type='hidden' id='datepicker_01'></td><td contenteditable='true'></td></tr><tr data='02'><td class='intitule'>OCTOBRE</td><td contenteditable='true' id='lig_02_col_0' class='tableauRecap'></td><td contenteditable='true' id='lig_02_col_1' class='tableauRecap'></td><td contenteditable='true' id='lig_02_col_2' class='tableauRecap'></td><td contenteditable='true' id='lig_02_col_3' class='tableauRecap'></td><td contenteditable='true' id='lig_02_col_4' class='tableauRecap'></td><td id='total_02' class='sous_total'></td><td contenteditable='true'></td><td contenteditable='true' class='sommePayee' id='paiement_02'></td><td><span id='date_02'>--/--/----</span><input type='hidden' id='datepicker_02'></td><td contenteditable='true'></td></tr><tr data='03'><td class='intitule'>NOVEMBRE</td><td contenteditable='true' id='lig_03_col_0' class='tableauRecap'></td><td contenteditable='true' id='lig_03_col_1' class='tableauRecap'></td><td contenteditable='true' id='lig_03_col_2' class='tableauRecap'></td><td contenteditable='true' id='lig_03_col_3' class='tableauRecap'></td><td contenteditable='true' id='lig_03_col_4' class='tableauRecap'></td><td id='total_03' class='sous_total'></td><td contenteditable='true'></td><td contenteditable='true' class='sommePayee' id='paiement_03'></td><td><span id='date_03'>--/--/----</span><input type='hidden' id='datepicker_03'></td><td contenteditable='true'></td></tr><tr data='04'><td class='intitule'>DECEMBRE</td><td contenteditable='true' id='lig_04_col_0' class='tableauRecap'></td><td contenteditable='true' id='lig_04_col_1' class='tableauRecap'></td><td contenteditable='true' id='lig_04_col_2' class='tableauRecap'></td><td contenteditable='true' id='lig_04_col_3' class='tableauRecap'></td><td contenteditable='true' id='lig_04_col_4' class='tableauRecap'></td><td id='total_04' class='sous_total'></td><td contenteditable='true'></td><td contenteditable='true' class='sommePayee' id='paiement_04'></td><td><span id='date_04'>--/--/----</span><input type='hidden' id='datepicker_04'></td><td contenteditable='true'></td></tr><tr data='05'><td class='intitule'>JANVIER</td><td contenteditable='true' id='lig_05_col_0' class='tableauRecap'></td><td contenteditable='true' id='lig_05_col_1' class='tableauRecap'></td><td contenteditable='true' id='lig_05_col_2' class='tableauRecap'></td><td contenteditable='true' id='lig_05_col_3' class='tableauRecap'></td><td contenteditable='true' id='lig_05_col_4' class='tableauRecap'></td><td id='total_05' class='sous_total'></td><td contenteditable='true'></td><td contenteditable='true' class='sommePayee' id='paiement_05'></td><td><span id='date_05'>--/--/----</span><input type='hidden' id='datepicker_05'></td><td contenteditable='true'></td></tr><tr data='06'><td class='intitule'>FEVRIER</td><td contenteditable='true' id='lig_06_col_0' class='tableauRecap'></td><td contenteditable='true' id='lig_06_col_1' class='tableauRecap'></td><td contenteditable='true' id='lig_06_col_2' class='tableauRecap'></td><td contenteditable='true' id='lig_06_col_3' class='tableauRecap'></td><td contenteditable='true' id='lig_06_col_4' class='tableauRecap'></td><td id='total_06' class='sous_total'></td><td contenteditable='true'></td><td contenteditable='true' class='sommePayee' id='paiement_06'></td><td><span id='date_06'>--/--/----</span><input type='hidden' id='datepicker_06'></td><td contenteditable='true'></td></tr><tr data='07'><td class='intitule'>MARS</td><td contenteditable='true' id='lig_07_col_0' class='tableauRecap'></td><td contenteditable='true' id='lig_07_col_1' class='tableauRecap'></td><td contenteditable='true' id='lig_07_col_2' class='tableauRecap'></td><td contenteditable='true' id='lig_07_col_3' class='tableauRecap'></td><td contenteditable='true' id='lig_07_col_4' class='tableauRecap'></td><td id='total_07' class='sous_total'></td><td contenteditable='true'></td><td contenteditable='true' class='sommePayee' id='paiement_07'></td><td><span id='date_07'>--/--/----</span><input type='hidden' id='datepicker_07'></td><td contenteditable='true'></td></tr><tr data='08'><td class='intitule'>AVRIL</td><td contenteditable='true' id='lig_08_col_0' class='tableauRecap'></td><td contenteditable='true' id='lig_08_col_1' class='tableauRecap'></td><td contenteditable='true' id='lig_08_col_2' class='tableauRecap'></td><td contenteditable='true' id='lig_08_col_3' class='tableauRecap'></td><td contenteditable='true' id='lig_08_col_4' class='tableauRecap'></td><td id='total_08' class='sous_total'></td><td contenteditable='true'></td><td contenteditable='true' class='sommePayee' id='paiement_08'></td><td><span id='date_08'>--/--/----</span><input type='hidden' id='datepicker_08'></td><td contenteditable='true'></td></tr><tr data='09'><td class='intitule'>MAI</td><td contenteditable='true' id='lig_09_col_0' class='tableauRecap'></td><td contenteditable='true' id='lig_09_col_1' class='tableauRecap'></td><td contenteditable='true' id='lig_09_col_2' class='tableauRecap'></td><td contenteditable='true' id='lig_09_col_3' class='tableauRecap'></td><td contenteditable='true' id='lig_09_col_4' class='tableauRecap'></td><td id='total_09' class='sous_total'></td><td contenteditable='true'></td><td contenteditable='true' class='sommePayee' id='paiement_09'></td><td><span id='date_09'>--/--/----</span><input type='hidden' id='datepicker_09'></td><td contenteditable='true'></td></tr><tr data='10'><td class='intitule'>JUIN</td><td contenteditable='true' id='lig_10_col_0' class='tableauRecap'></td><td contenteditable='true' id='lig_10_col_1' class='tableauRecap'></td><td contenteditable='true' id='lig_10_col_2' class='tableauRecap'></td><td contenteditable='true' id='lig_10_col_3' class='tableauRecap'></td><td contenteditable='true' id='lig_10_col_4' class='tableauRecap'></td><td id='total_10' class='sous_total'></td><td contenteditable='true'></td><td contenteditable='true' class='sommePayee' id='paiement_10'></td><td><span id='date_10'>--/--/----</span><input type='hidden' id='datepicker_10'></td><td contenteditable='true'></td></tr><tr id='ajoutLigne_0'  data='11'><td class='intitule'>PART C.E.</td><td contenteditable='true' id='lig_11_col_0' class='tableauRecap'></td><td contenteditable='true' id='lig_11_col_1' class='tableauRecap'></td><td contenteditable='true' id='lig_11_col_2' class='tableauRecap'></td><td contenteditable='true' id='lig_11_col_3' class='tableauRecap'></td><td contenteditable='true' id='lig_11_col_4' class='tableauRecap'></td><td id='total_11' class='sous_total'></td><td contenteditable='true'></td><td contenteditable='true' class='sommePayee' id='paiement_11'></td><td><span id='date_11'>--/--/----</span><input type='hidden' id='datepicker_11'></td><td contenteditable='true'></td></tr></tbody><tfoot><tr><td class='ignorer' rowspan='1'><input type='button' id='ajoutTab' class='button2' value='Ajouter ligne'></button></td></tr><tr><td class='ignorer'></td></tr><tr><td class='intituleTotal'>Total restant à payer</td><td id='totalRestant'></td></tr></tfoot></table>";
						famtab.setTfc(tableauFicheComptable);
						famtab.setTr(tableauRecapitulatif);
						famtab.setAnnee((1900+date.getYear()-1));
						FamilleCtrl.ajouterFamilleTableaux(famtab);
					}
				}else{//Si l'on se trouve entre Septembre et Décembre
					//Si les tableaux ont été créé pour l'année avant l'année n Alors on créé de nouveaux tableaux
					if(famtab.getAnnee()<(1900+date.getYear())){
						String tableauFicheComptable="<table><tr><td class='ignorer'></td><th class='numEleve'>Elève 1</th><th class='numEleve'>Elève 2</th><th class='numEleve'>Elève 3</th><th class='numEleve'>Elève 4</th><th class='numEleve'>Elève 5</th></tr><tr><th class='intitule'>Prénom + Nom</th><td contenteditable='true' class='nom' id='eleve_1'></td><td contenteditable='true' class='nom' id='eleve_2'></td><td contenteditable='true' class='nom' id='eleve_3'></td><td contenteditable='true' class='nom' id='eleve_4'></td><td contenteditable='true' class='nom' id='eleve_5'></td></tr><tr><th class='intitule'>QF (1, 2, 3)</th><td contenteditable='true' id='qf'></td><td contenteditable='true' id='qf'></td><td contenteditable='true' id='qf'></td><td contenteditable='true' id='qf'></td><td contenteditable='true' id='qf'></td></tr><tr><th class='intitule'>FM / inst<br>Eveil / éveil inst</th><td contenteditable='true' id='lig_0_col_0'  class='sommeMensuelle'></td><td contenteditable='true' id='lig_0_col_1' class='sommeMensuelle'></td><td contenteditable='true' id='lig_0_col_2' class='sommeMensuelle'></td><td contenteditable='true' id='lig_0_col_3' class='sommeMensuelle'></td><td contenteditable='true' id='lig_0_col_4' class='sommeMensuelle'></td></tr><tr><th class='intitule'>FM seule éveil</th><td contenteditable='true' id='lig_1_col_0' class='sommeMensuelle'></td><td contenteditable='true' id='lig_1_col_1' class='sommeMensuelle'></td><td contenteditable='true' id='lig_1_col_2' class='sommeMensuelle'></td><td contenteditable='true' id='lig_1_col_3' class='sommeMensuelle'></td><td contenteditable='true' id='lig_1_col_4' class='sommeMensuelle'></td></tr><tr><th class='intitule'>Instrument seul</th><td contenteditable='true' id='lig_2_col_0' class='sommeMensuelle'></td><td contenteditable='true' id='lig_2_col_1' class='sommeMensuelle'></td><td contenteditable='true' id='lig_2_col_2' class='sommeMensuelle'></td><td contenteditable='true' id='lig_2_col_3' class='sommeMensuelle'></td><td contenteditable='true' id='lig_2_col_4' class='sommeMensuelle'></td></tr><tr><th class='intitule'>Atelier</th><td contenteditable='true' id='lig_3_col_0' class='sommeMensuelle'></td><td contenteditable='true' id='lig_3_col_1' class='sommeMensuelle'></td><td contenteditable='true' id='lig_3_col_2' class='sommeMensuelle'></td><td contenteditable='true' id='lig_3_col_3' class='sommeMensuelle'></td><td contenteditable='true' id='lig_3_col_4' class='sommeMensuelle'></td><th class='intituleTotal'>Total mensuel</th></tr><tr><th class='intitule'>Total mensuel par élève</th><td class='sous_total' id='sommeMensuelle_0'></td><td class='sous_total' id='sommeMensuelle_1'></td><td class='sous_total' id='sommeMensuelle_2'></td><td class='sous_total' id='sommeMensuelle_3'></td><td class='sous_total' id='sommeMensuelle_4'></td><td class='total' id='totalMensuel'></td></tr><tr><th class='intitule'>Pratique collective</th><td contenteditable='true' id='lig_0_col_0' class='sommeAnnuelle'></td><td contenteditable='true' id='lig_0_col_1' class='sommeAnnuelle'></td><td contenteditable='true' id='lig_0_col_2' class='sommeAnnuelle'></td><td contenteditable='true' id='lig_0_col_3' class='sommeAnnuelle'></td><td contenteditable='true' id='lig_0_col_4' class='sommeAnnuelle'></td></tr><tr><th class='intitule'>Cotisation</th><td contenteditable='true' id='lig_1_col_0' class='sommeAnnuelle'></td><td contenteditable='true' id='lig_1_col_1' class='sommeAnnuelle'></td><td contenteditable='true' id='lig_1_col_2' class='sommeAnnuelle'></td><td contenteditable='true' id='lig_1_col_3' class='sommeAnnuelle'></td><td contenteditable='true' id='lig_1_col_4' class='sommeAnnuelle'></td></tr><tr><th class='intitule'>Membre admin</th><td contenteditable='true' id='lig_2_col_0' class='sommeAnnuelle'></td><td contenteditable='true' id='lig_2_col_1' class='sommeAnnuelle'></td><td contenteditable='true' id='lig_2_col_2' class='sommeAnnuelle'></td><td contenteditable='true' id='lig_2_col_3' class='sommeAnnuelle'></td><td contenteditable='true' id='lig_2_col_4' class='sommeAnnuelle'></td><th class='intituleTotal'>Total annuel</th></tr><tr><th class='intitule'>Total Annuel par élève</th><td class='sous_total' id='sommeAnnuelle_0'></td><td class='sous_total' id='sommeAnnuelle_1'></td><td class='sous_total' id='sommeAnnuelle_2'></td><td class='sous_total' id='sommeAnnuelle_3'></td><td class='sous_total' id='sommeAnnuelle_4'></td><td class='total' id='totalAnnuel'></td></tr><tr><th class='intitule'>Part C.E.</th><td contenteditable='true' class='ce'></td><td contenteditable='true' class='ce'></td><td contenteditable='true' class='ce'></td><td contenteditable='true' class='ce'></td><td contenteditable='true' class='ce'></td></tr><tr><td class='ignorer'></td></tr><tr><th class='intituleTotal'>Reste à la charge du client</th><td class='total' id='totalFinal'></td></tr></table>";
						String tableauRecapitulatif="<table id='tableau'><thead><tr><td class='ignorer'></td><td class='intitule'>1er Elève</td><td class='intitule'>2ème Elève</td><td class='intitule'>3ème Elève</td><td class='intitule'>4ème Elève</td><td class='intitule'>5ème Elève</td><td class='intitule'>TOTAL</td><td class='intitule'>N° de chèque</td><td class='intitule'>Montant chèque</td><td class='intitule'>Date encaissement</td><td class='intitule'>Observation</td></tr></thead><tbody><tr data='01'><td class='intitule'>SEPTEMBRE</td><td contenteditable='true' id='lig_01_col_0' class='tableauRecap'></td><td contenteditable='true' id='lig_01_col_1' class='tableauRecap'></td><td class='gris'></td><td class='gris'></td><td class='gris'></td><td id='total_01' class='sous_total'></td><td contenteditable='true'></td><td contenteditable='true' class='sommePayee' id='paiement_01'></td><td><span id='date_01'>--/--/----</span><input type='hidden' id='datepicker_01'></td><td contenteditable='true'></td></tr><tr data='02'><td class='intitule'>OCTOBRE</td><td contenteditable='true' id='lig_02_col_0' class='tableauRecap'></td><td contenteditable='true' id='lig_02_col_1' class='tableauRecap'></td><td contenteditable='true' id='lig_02_col_2' class='tableauRecap'></td><td contenteditable='true' id='lig_02_col_3' class='tableauRecap'></td><td contenteditable='true' id='lig_02_col_4' class='tableauRecap'></td><td id='total_02' class='sous_total'></td><td contenteditable='true'></td><td contenteditable='true' class='sommePayee' id='paiement_02'></td><td><span id='date_02'>--/--/----</span><input type='hidden' id='datepicker_02'></td><td contenteditable='true'></td></tr><tr data='03'><td class='intitule'>NOVEMBRE</td><td contenteditable='true' id='lig_03_col_0' class='tableauRecap'></td><td contenteditable='true' id='lig_03_col_1' class='tableauRecap'></td><td contenteditable='true' id='lig_03_col_2' class='tableauRecap'></td><td contenteditable='true' id='lig_03_col_3' class='tableauRecap'></td><td contenteditable='true' id='lig_03_col_4' class='tableauRecap'></td><td id='total_03' class='sous_total'></td><td contenteditable='true'></td><td contenteditable='true' class='sommePayee' id='paiement_03'></td><td><span id='date_03'>--/--/----</span><input type='hidden' id='datepicker_03'></td><td contenteditable='true'></td></tr><tr data='04'><td class='intitule'>DECEMBRE</td><td contenteditable='true' id='lig_04_col_0' class='tableauRecap'></td><td contenteditable='true' id='lig_04_col_1' class='tableauRecap'></td><td contenteditable='true' id='lig_04_col_2' class='tableauRecap'></td><td contenteditable='true' id='lig_04_col_3' class='tableauRecap'></td><td contenteditable='true' id='lig_04_col_4' class='tableauRecap'></td><td id='total_04' class='sous_total'></td><td contenteditable='true'></td><td contenteditable='true' class='sommePayee' id='paiement_04'></td><td><span id='date_04'>--/--/----</span><input type='hidden' id='datepicker_04'></td><td contenteditable='true'></td></tr><tr data='05'><td class='intitule'>JANVIER</td><td contenteditable='true' id='lig_05_col_0' class='tableauRecap'></td><td contenteditable='true' id='lig_05_col_1' class='tableauRecap'></td><td contenteditable='true' id='lig_05_col_2' class='tableauRecap'></td><td contenteditable='true' id='lig_05_col_3' class='tableauRecap'></td><td contenteditable='true' id='lig_05_col_4' class='tableauRecap'></td><td id='total_05' class='sous_total'></td><td contenteditable='true'></td><td contenteditable='true' class='sommePayee' id='paiement_05'></td><td><span id='date_05'>--/--/----</span><input type='hidden' id='datepicker_05'></td><td contenteditable='true'></td></tr><tr data='06'><td class='intitule'>FEVRIER</td><td contenteditable='true' id='lig_06_col_0' class='tableauRecap'></td><td contenteditable='true' id='lig_06_col_1' class='tableauRecap'></td><td contenteditable='true' id='lig_06_col_2' class='tableauRecap'></td><td contenteditable='true' id='lig_06_col_3' class='tableauRecap'></td><td contenteditable='true' id='lig_06_col_4' class='tableauRecap'></td><td id='total_06' class='sous_total'></td><td contenteditable='true'></td><td contenteditable='true' class='sommePayee' id='paiement_06'></td><td><span id='date_06'>--/--/----</span><input type='hidden' id='datepicker_06'></td><td contenteditable='true'></td></tr><tr data='07'><td class='intitule'>MARS</td><td contenteditable='true' id='lig_07_col_0' class='tableauRecap'></td><td contenteditable='true' id='lig_07_col_1' class='tableauRecap'></td><td contenteditable='true' id='lig_07_col_2' class='tableauRecap'></td><td contenteditable='true' id='lig_07_col_3' class='tableauRecap'></td><td contenteditable='true' id='lig_07_col_4' class='tableauRecap'></td><td id='total_07' class='sous_total'></td><td contenteditable='true'></td><td contenteditable='true' class='sommePayee' id='paiement_07'></td><td><span id='date_07'>--/--/----</span><input type='hidden' id='datepicker_07'></td><td contenteditable='true'></td></tr><tr data='08'><td class='intitule'>AVRIL</td><td contenteditable='true' id='lig_08_col_0' class='tableauRecap'></td><td contenteditable='true' id='lig_08_col_1' class='tableauRecap'></td><td contenteditable='true' id='lig_08_col_2' class='tableauRecap'></td><td contenteditable='true' id='lig_08_col_3' class='tableauRecap'></td><td contenteditable='true' id='lig_08_col_4' class='tableauRecap'></td><td id='total_08' class='sous_total'></td><td contenteditable='true'></td><td contenteditable='true' class='sommePayee' id='paiement_08'></td><td><span id='date_08'>--/--/----</span><input type='hidden' id='datepicker_08'></td><td contenteditable='true'></td></tr><tr data='09'><td class='intitule'>MAI</td><td contenteditable='true' id='lig_09_col_0' class='tableauRecap'></td><td contenteditable='true' id='lig_09_col_1' class='tableauRecap'></td><td contenteditable='true' id='lig_09_col_2' class='tableauRecap'></td><td contenteditable='true' id='lig_09_col_3' class='tableauRecap'></td><td contenteditable='true' id='lig_09_col_4' class='tableauRecap'></td><td id='total_09' class='sous_total'></td><td contenteditable='true'></td><td contenteditable='true' class='sommePayee' id='paiement_09'></td><td><span id='date_09'>--/--/----</span><input type='hidden' id='datepicker_09'></td><td contenteditable='true'></td></tr><tr data='10'><td class='intitule'>JUIN</td><td contenteditable='true' id='lig_10_col_0' class='tableauRecap'></td><td contenteditable='true' id='lig_10_col_1' class='tableauRecap'></td><td contenteditable='true' id='lig_10_col_2' class='tableauRecap'></td><td contenteditable='true' id='lig_10_col_3' class='tableauRecap'></td><td contenteditable='true' id='lig_10_col_4' class='tableauRecap'></td><td id='total_10' class='sous_total'></td><td contenteditable='true'></td><td contenteditable='true' class='sommePayee' id='paiement_10'></td><td><span id='date_10'>--/--/----</span><input type='hidden' id='datepicker_10'></td><td contenteditable='true'></td></tr><tr id='ajoutLigne_0'  data='11'><td class='intitule'>PART C.E.</td><td contenteditable='true' id='lig_11_col_0' class='tableauRecap'></td><td contenteditable='true' id='lig_11_col_1' class='tableauRecap'></td><td contenteditable='true' id='lig_11_col_2' class='tableauRecap'></td><td contenteditable='true' id='lig_11_col_3' class='tableauRecap'></td><td contenteditable='true' id='lig_11_col_4' class='tableauRecap'></td><td id='total_11' class='sous_total'></td><td contenteditable='true'></td><td contenteditable='true' class='sommePayee' id='paiement_11'></td><td><span id='date_11'>--/--/----</span><input type='hidden' id='datepicker_11'></td><td contenteditable='true'></td></tr></tbody><tfoot><tr><td class='ignorer' rowspan='1'><input type='button' id='ajoutTab' class='button2' value='Ajouter ligne'></button></td></tr><tr><td class='ignorer'></td></tr><tr><td class='intituleTotal'>Total restant à payer</td><td id='totalRestant'></td></tr></tfoot></table>";
						famtab.setTfc(tableauFicheComptable);
						famtab.setTr(tableauRecapitulatif);
						famtab.setAnnee((1900+date.getYear()));
						FamilleCtrl.ajouterFamilleTableaux(famtab);
					}
				}
				
				//On récupère le nom de la famille
				//Oui j'aurais pu créer une requête sql mais chut....
				String nomFamille="";
				List<Famille> tmpFamilles2 = FamilleCtrl.recupererAllFamille();
				for(Famille f : tmpFamilles2){
					if(f.getIdFamille()==idFamille){
						nomFamille = f.getNomFamille();
					}
				}
				
				
				
				request.setAttribute(IDFAMILLE, idFamille);
				session.setAttribute("FAMILLE", idFamille);
				request.setAttribute("nomFamille", nomFamille);
				request.setAttribute("annee", famtab.getAnnee());
				session.setAttribute("ANNEE", famtab.getAnnee());
				request.setAttribute("anneeFin", (famtab.getAnnee()+1));
				request.setAttribute("tfc", famtab.getTfc());
				request.setAttribute("tr", famtab.getTr());
				
				List<Famille> allFamilles = FamilleCtrl.recupererAllFamille();
				List<String> famillesAutoCompletion = new ArrayList<>();
				for(Famille f : allFamilles){
					famillesAutoCompletion.add('"'+f.getNomFamille()+'"');
				}
				request.setAttribute(FAMILLES, famillesAutoCompletion);
				
			}else{
				request.setAttribute("vide", "La famille n'existait pas cette année là");
			}
		}else{
			FamilleCtrl.updateTableaux(Form.getValeurChamp(request, "tfc"), Form.getValeurChamp(request, "recap"), Integer.parseInt(""+session.getAttribute("FAMILLE")), Integer.parseInt(""+session.getAttribute("ANNEE")));
			request.setAttribute("tableauxenregistres", "Les tableaux ont été enregistrés");
			List<Famille> allFamilles = FamilleCtrl.recupererAllFamille();
			List<String> famillesAutoCompletion = new ArrayList<>();
			for(Famille f : allFamilles){
				famillesAutoCompletion.add('"'+f.getNomFamille()+'"');
			}
			request.setAttribute(FAMILLES, famillesAutoCompletion);
		}
		this.getServletContext().getRequestDispatcher(JSPFile.COMPTABILITE_ENSEIGNEMENT).forward(request, response);
	}
}
