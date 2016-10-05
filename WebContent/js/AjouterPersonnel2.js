var compteurC = 1;
$(function() {
	ajoutEventAjoutContrat();
	ajoutEventRetireContrat();
});

function ajoutEventAjoutContrat() {
	bouton1 = document.getElementById("ajoutContrat");
	ajouteEvent(bouton1, 'click', ajoutContrat, false);
}

function ajoutEventRetireContrat() {
	bouton2 = document.getElementById("retireContrat");
	ajouteEvent(bouton2, 'click', retireContrat, false);
}

function afficherDuree() {
	/*for (var i = 1; i <= compteurC; i++) {
		var selectType = document.getElementById('type' + i).value;
		if (selectType != "1") {
			document.getElementById('duree' + i).removeAttribute(
					'hidden');
		} else {
			document.getElementById('duree' + i).setAttribute('hidden',
					'hidden');
		}
	}
	*/
	for (var i = 1; i <= compteurC; i++) {
		if ($('#type' + i + " option:selected").text() == "CDD") {
			$('#duree'+i).show();
		}else{
			$('#duree'+i).hide();
		}
	}
}

function retireContrat() {
	if (compteurC > 1) {
		document.getElementById("divContrat" + (compteurC - 1)).appendChild(
				document.getElementById("ajoutContrat").cloneNode(true));
		document.getElementById("divContrat" + (compteurC - 1)).appendChild(
				document.getElementById("retireContrat").cloneNode(true));
		document.getElementById("contrats").removeChild(
				document.getElementById("contrats").lastChild);
		ajoutEventAjoutContrat();
		ajoutEventRetireContrat();
		compteurC--;
	}
}

function ajoutContrat() {
	compteurC++;
	var tr = document.getElementById("contrats").lastElementChild
			.cloneNode(true);
	tr.id = "divContrat" + compteurC;
	document.getElementById("contrats").appendChild(tr);
	
	document.getElementById("divContrat" + compteurC).getElementsByTagName(
	'p')[0].innerHTML = "Contrat"+compteurC;

	document.getElementById("divContrat" + compteurC).getElementsByTagName(
			'select')[0].name = "type" + compteurC;
	document.getElementById("divContrat" + compteurC).getElementsByTagName(
			'select')[0].id = "type" + compteurC;

	document.getElementById("divContrat" + compteurC).getElementsByTagName(
			'input')[0].name = "datedeb" + compteurC;
	document.getElementById("divContrat" + compteurC).getElementsByTagName(
			'input')[0].id = "datedeb" + compteurC;
	document.getElementById("divContrat" + compteurC).getElementsByTagName(
	'input')[0].setAttribute('class', null);
	
	for(var i=1; i<=compteurC; i++){
		$('#datedeb'+i).datepicker({
	    	changeMonth: true,
	    	changeYear: true,
	    	yearRange: "-100:+0"
	    	});
	    
		$('#datedeb'+i).datepicker("option", "showAnim","blind");
	}

	document.getElementById("divContrat" + compteurC).getElementsByTagName(
			'input')[1].name = "dureeContrat" + compteurC;
	document.getElementById("divContrat" + compteurC).getElementsByTagName(
			'input')[1].id = "dureeContrat" + compteurC;
	
	document.getElementById("divContrat" + compteurC).getElementsByTagName(
	'div')[0].id = "duree" + compteurC;

	// Supprime les boutons de l'avant dernier champ
	$("#divContrat" + (compteurC - 1) + " input[type=button]").remove();
	ajoutEventAjoutContrat();
	ajoutEventRetireContrat();
}
