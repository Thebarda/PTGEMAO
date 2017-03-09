/////////////////////////////
// Tableau Fiche comptable //
/////////////////////////////
	
	// Total mensuel par élève
	$('td').on("keyup", function(){
		var num_col = $(this).index()-1;
		var sommeMensuelle = 0;
		$('[id*="_col_'+num_col+'"][class="sommeMensuelle"]').each(function(){    
			var val = $(this).text();
			if($.isNumeric(val)){
				sommeMensuelle += parseInt(val);
			}
		});
		$("#sommeMensuelle_"+num_col).text(sommeMensuelle);
	});

	// Total annuel par élève
	$('td').on("keyup", function(){
		var num_col = $(this).index()-1;
		var sommeAnnuelle = 0;
		$('[id*="_col_'+num_col+'"][class="sommeAnnuelle"]').each(function(){    
			var val = $(this).text();
			if($.isNumeric(val)){
				sommeAnnuelle += parseInt(val);
			}
		});
		sommeAnnuelle += 9*parseInt($('[id*="sommeMensuelle_'+num_col+'"]').text());
		$("#sommeAnnuelle_"+num_col).text(sommeAnnuelle);
	});

	// Contrôle paiement si personne non saisie
	$(document).on("keyup", "td", function() {
		var num_col = $(this).index()-1;
		if($('[id*="sommeMensuelle_'+num_col+'"]').text() != 0 && (!$('[id*="eleve_'+(num_col+1)+'"]').text())){
			document.getElementById("eleve_"+(num_col+1)).style.backgroundColor = 'lightcoral';
		}
		else{
			document.getElementById("eleve_"+(num_col+1)).style.backgroundColor = 'transparent';
		}
	});
	
	// Total mensuel par famille
	$("tr").on("keyup", function() {
		var sommeTotalMensuel = 0;
		$('[id*="sommeMensuelle_"]').each(function() {
			var val = $(this).text();
			if ($.isNumeric(val)) {
				sommeTotalMensuel += parseInt(val);
			}
		});
		$("#totalMensuel").text(sommeTotalMensuel);
	});

	// Total annuel par famille
	$("tr").on("keyup", function() {
		var sommeTotalAnnuel = 0;
		$('[id*="sommeAnnuelle_"]').each(function() {
			var val = $(this).text();
			if ($.isNumeric(val)) {
				sommeTotalAnnuel += parseInt(val);
			}
		});
		$("#totalAnnuel").text(sommeTotalAnnuel);
	});

	// Total restant à charge de la famille (Total annuel - Part CE)
	$("tr").on("keyup", function() {
		var sommeTotalFinal = 0;
		var sommeCE = 0;
		sommeTotalFinal += parseInt($('[id*="totalAnnuel"]').text());
		$('[class="ce"]').each(function() {
			var val = $(this).text();
			if ($.isNumeric(val)) {
				sommeCE += parseInt(val);
			}
		});
		sommeTotalFinal -= parseInt(sommeCE);
		$("#totalFinal").text(sommeTotalFinal);
	});