////////////////////////////////////////////////	
// Tableau récapitulatif paiement par famille //
////////////////////////////////////////////////

	// DatePicker
	$(document).ready(function(){
		$('tr').trigger("click");
	});

	$(document).on("click", "tr",function() {
		$('[id*="datepicker_"]').datepicker({
			showOn: 'button',
			buttonText: 'Show Date',
			buttonImageOnly: true,
			buttonImage: 'http://jqueryui.com/resources/demos/datepicker/images/calendar.gif'
		});
	});
	
	$(document).on("click", '[id*="date_"]',function() {
		num_lig = $(this).closest("tr").attr("data");
		$('[id*="datepicker_'+num_lig+'"]').datepicker('show');
	});
		
	$(document).on("change", '[id*="datepicker_"]',function(){
		num_lig = $(this).closest("tr").attr("data");
		var date = $(this).val();
		$("#date_"+num_lig).text(date);
	});
	
	// Totaux par mois (par ligne)
	$(document).on("keyup", "tr",function() {
		var num_lig = $(this).attr("data");
		var total = 0;
		$('[id*="lig_'+num_lig+'"][class="tableauRecap"]').each(function() {
			var val = $(this).text();
			if ($.isNumeric(val)) {
				total += parseInt(val);
			}
		});
		$("#total_" + num_lig).text(total);
	});

	// Total restant à payer
	$(document).on("keyup click", "tr", function(){
		var sommeAPayer = 0;
		var sommePayee = 0;
		$('[id*="total_"').each(function(){    
			var val = $(this).text();
			if($.isNumeric(val)){
				sommeAPayer += parseInt(val);
			}
		});
		$('[class*="sommePayee"]').each(function(){    
			var val = $(this).text();
			if($.isNumeric(val)){
				sommePayee += parseInt(val);
			}
		});
		sommeAPayer -= parseInt(sommePayee);
		$("#totalRestant").text(sommeAPayer);
	});

	// Contrôle paiement mensuel (Somme à payer = Somme payée)
	$(document).on("keyup", "tr", function() {
		var num_lig = $(this).attr("data");
		$('[id*="paiement_"]').each(function(){
			var valeurAttendue = $('[id="total_'+num_lig+'"]');
			var valeurSaisie = $('[id*="paiement_'+num_lig+'"]');
			if(valeurSaisie.text() == valeurAttendue.text()){
				document.getElementById("paiement_"+num_lig).style.backgroundColor = 'lightgreen';
			}
			else{
				if(valeurAttendue.text() == 0 && !valeurSaisie.text()){
					document.getElementById("paiement_"+num_lig).style.backgroundColor = 'lightgreen';
				}
				else{
					document.getElementById("paiement_"+num_lig).style.backgroundColor = 'lightcoral';
				}
			};
		});
	});
	
	// Ajout de ligne
	$(document).ready(function(){		
		$("#ajoutTab").click( function () {
			var ligne = parseInt($("#tableau tbody tr:last-child").attr("data"))+parseInt(1);
			var nbLigne = parseInt(ligne)-parseInt(11);
			var row = $('<tr id="ajoutLigne_'+nbLigne+'" data="'+ligne+'">');

			row.append($('<td contenteditable="true" class="intitule"></td>'))
			   .append($('<td contenteditable="true" id="lig_'+ligne+'_col_0" class="tableauRecap"></td>'))
			   .append($('<td contenteditable="true" id="lig_'+ligne+'_col_1" class="tableauRecap"></td>'))
			   .append($('<td contenteditable="true" id="lig_'+ligne+'_col_2" class="tableauRecap"></td>'))
			   .append($('<td contenteditable="true" id="lig_'+ligne+'_col_3" class="tableauRecap"></td>'))
			   .append($('<td contenteditable="true" id="lig_'+ligne+'_col_4" class="tableauRecap"></td>'))
			   .append($('<td id="total_'+ligne+'" class="sous_total"></td>'))
			   .append($('<td contenteditable="true"></td>'))
			   .append($('<td contenteditable="true" class="sommePayee" id="paiement_'+ligne+'"></td>'))
			   .append($('<td><span id="date_'+ligne+'">--/--/----</span><input type="hidden" id="datepicker_'+ligne+'"></td>'))
			   .append($('<td contenteditable="true"></td>'))
			   .append($('<td class="supprimer ignorer"><button type="button" class="supprTab">X</button></td>'))
			   .append($('</tr>'));
			   
			row.find('input').datepicker({
				showOn: 'button',
				buttonText: 'Show Date',
				buttonImageOnly: true,
				buttonImage: 'http://jqueryui.com/resources/demos/datepicker/images/calendar.gif'
			});
	 
			$("#tableau tbody").append(row);
		});
	});
	
	// Suppression de ligne
	$(document).on("click", ".supprTab", function() {
		var id = $(this).closest("tr").attr("id");
		document.getElementById(id).remove();
	});