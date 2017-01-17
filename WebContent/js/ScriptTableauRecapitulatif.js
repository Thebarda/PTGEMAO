////////////////////////////////////////////////	
// Tableau récapitulatif paiement par famille //
////////////////////////////////////////////////

	// Variable
	var ligne = 11;
	var nbLigne = 1;

	// DatePicker
	$(document).ready(function(){
		$( ".datepicker" ).datepicker();
	});
	
	

	// Totaux par mois (par ligne)
	$(document).on("keyup", "tr",function() {
		var num_lig = $(this).attr("data")-1;
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
	$(document).on("keyup", "tr", function(){
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

	// Contrôle paiement mensuel
	$(document).on("keyup", "tr", function() {
		var num_lig = $(this).attr("data")-1;
		$('[id*="paiement_"]').each(function(){
			var valeurAttendue = $('[id="total_'+num_lig+'"]');
			var valeurSaisie = $('[id*="paiement_'+num_lig+'"]');
			console.log("Total = " + valeurAttendue.text() + ", Payé : " + valeurSaisie.text());
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
			var row = $('<tr id="ajoutLigne_'+nbLigne+'" data="'+(ligne+1)+'">');

			row.append($('<td contenteditable="true" class="intitule"></td>'))
			   .append($('<td contenteditable="true" id="lig_'+ligne+'_col_0" class="tableauRecap"></td>'))
			   .append($('<td contenteditable="true" id="lig_'+ligne+'_col_1" class="tableauRecap"></td>'))
			   .append($('<td contenteditable="true" id="lig_'+ligne+'_col_2" class="tableauRecap"></td>'))
			   .append($('<td contenteditable="true" id="lig_'+ligne+'_col_3" class="tableauRecap"></td>'))
			   .append($('<td contenteditable="true" id="lig_'+ligne+'_col_4" class="tableauRecap"></td>'))
			   .append($('<td id="total_'+ligne+'" class="sous_total"></td>'))
			   .append($('<td contenteditable="true"></td>'))
			   .append($('<td contenteditable="true" class="sommePayee" id="paiement_'+ligne+'"></td>'))
			   .append($('<td><input type="text" class="datepicker"></td>'))
			   .append($('<td contenteditable="true"></td>'))
			   .append($('<td class="supprimer ignorer"><button type="button" class="supprTab">X</button></td>'))
			   .append($('</tr>'));
			   
			row.find('input').datepicker();
	 
			$("#tableau tbody").append(row);
			nbLigne += 1;
			ligne += 1;
		});
	});
	
	// Suppression de ligne
	$(document).on("click", ".supprTab", function() {
		var id = $(this).closest("tr").attr("id");
		document.getElementById(id).remove();
	});