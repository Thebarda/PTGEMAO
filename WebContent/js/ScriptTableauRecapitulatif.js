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
	$(document).on("keyup", "#recap tr", function() {
		var num_lig = $(this).attr("data");
		$('[id*="paiement_"]').each(function(){
			var valeurAttendue = $('[id="total_'+num_lig+'"]');
			var valeurSaisie = $('[id*="paiement_'+num_lig+'"]');
			if(valeurSaisie.text() == valeurAttendue.text()){
				console.log("paiement_"+num_lig);
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
	
	
	// Suppression de ligne
	$(document).on("click", ".supprTab", function() {
		var id = $(this).closest("tr").attr("id");
		document.getElementById(id).remove();
	});