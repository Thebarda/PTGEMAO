var compteur = 1;
var compteurF = 1
var compteurD = 1;
function retireDiplome() {
	if (compteur > 1) {
		document.getElementById("divDiplome" + (compteur - 1)).appendChild(
				document.getElementById("ajoutDiplome").cloneNode(true));
		document.getElementById("divDiplome" + (compteur - 1)).appendChild(
				document.getElementById("retireDiplome").cloneNode(true));
		document.getElementById("diplomes").removeChild(
				document.getElementById("diplomes").lastChild);
		ajoutEventAjoutDiplome();
		ajoutEventRetireDiplome();
		compteur--;
	}
}

function ajoutDiplome() {
	// $("#divDiplome"+compteur + "input").remove();
	compteur++;
	var tr = document.getElementById("diplomes").lastElementChild
			.cloneNode(true);
	tr.id = "divDiplome" + compteur;
	document.getElementById("diplomes").appendChild(tr);
	document.getElementById("divDiplome" + compteur).firstChild.nextSibling.nextSibling.nextSibling.name = "diplome"
			+ compteur;
	document.getElementById("divDiplome" + compteur).firstChild.nextSibling.nextSibling.nextSibling.id = "diplome"
			+ compteur;

	$("#divDiplome" + (compteur - 1) + " input[type=button]").remove();
	ajoutEventAjoutDiplome();
	ajoutEventRetireDiplome();
}

function retireFonction() {
	if (compteurF > 1) {
		document.getElementById("divFonction" + (compteurF - 1)).appendChild(
				document.getElementById("ajoutFonction").cloneNode(true));
		document.getElementById("divFonction" + (compteurF - 1)).appendChild(
				document.getElementById("retireFonction").cloneNode(true));
		document.getElementById("fonctions").removeChild(
				document.getElementById("fonctions").lastChild);
		ajoutEventAjoutFonction();
		ajoutEventRetireFonction();
		compteurF--;
	}
}

function ajoutFonction() {
	// $("#divFonction"+compteurF + "input").remove();
	compteurF++;
	var tr = document.getElementById("fonctions").lastElementChild
			.cloneNode(true);
	tr.id = "divFonction" + compteurF;
	document.getElementById("fonctions").appendChild(tr);
	document.getElementById("divFonction" + compteurF).firstChild.nextSibling.nextSibling.nextSibling.name = "fonction"
			+ compteurF;
	document.getElementById("divFonction" + compteurF).firstChild.nextSibling.nextSibling.nextSibling.id = "fonction"
			+ compteurF;

	var compteurDec = compteurF - 1;
	var fonction = $('#fonction' + compteurDec + ' option:selected').val();
	$("#fonction" + compteurF + " option[value=" + fonction + "]").hide();

	// Supprime les boutons de l'avant dernier champ
	$("#divFonction" + (compteurF - 1) + " input[type=button]").remove();
	ajoutEventAjoutFonction();
	ajoutEventRetireFonction();
}

function retireDiscipline() {
	if (compteurD > 1) {
		document.getElementById("divDiscipline" + (compteurD - 1)).appendChild(
				document.getElementById("ajoutDiscipline").cloneNode(true));
		document.getElementById("divDiscipline" + (compteurD - 1)).appendChild(
				document.getElementById("retireDiscipline").cloneNode(true));
		document.getElementById("disciplines").removeChild(
				document.getElementById("disciplines").lastChild);
		ajoutEventAjoutDiscipline();
		ajoutEventRetireDiscipline();
		compteurD--;
	}
}

function ajoutDiscipline() {
	// $("#divFonction"+compteurF + "input").remove();
	compteurD++;
	var tr = document.getElementById("disciplines").lastElementChild
			.cloneNode(true);
	tr.id = "divDiscipline" + compteurD;
	document.getElementById("disciplines").appendChild(tr);
	document.getElementById("divDiscipline" + compteurD).firstChild.nextSibling.nextSibling.nextSibling.name = "discipline"
			+ compteurD;
	document.getElementById("divDiscipline" + compteurD).firstChild.nextSibling.nextSibling.nextSibling.id = "discipline"
			+ compteurD;

	// Supprime les boutons de l'avant dernier champ
	$("#divDiscipline" + (compteurD - 1) + " input[type=button]").remove();
	ajoutEventAjoutDiscipline();
	ajoutEventRetireDiscipline();
}

$(function() {
	ajoutEventAjoutDiplome();
	ajoutEventRetireDiplome();
	ajoutEventAjoutFonction();
	ajoutEventRetireFonction();
	ajoutEventAjoutDiscipline();
	ajoutEventRetireDiscipline();
	$('#disciplines').hide();
});

function ajoutEventAjoutDiplome() {
	bouton1 = document.getElementById("ajoutDiplome");
	ajouteEvent(bouton1, 'click', ajoutDiplome, false);
}

function ajoutEventRetireDiplome() {
	bouton2 = document.getElementById("retireDiplome");
	ajouteEvent(bouton2, 'click', retireDiplome, false);
}

function ajoutEventAjoutFonction() {
	bouton1 = document.getElementById("ajoutFonction");
	ajouteEvent(bouton1, 'click', ajoutFonction, false);
}

function ajoutEventRetireFonction() {
	bouton2 = document.getElementById("retireFonction");
	ajouteEvent(bouton2, 'click', retireFonction, false);
}

function ajoutEventAjoutDiscipline() {
	bouton1 = document.getElementById("ajoutDiscipline");
	ajouteEvent(bouton1, 'click', ajoutDiscipline, false);
}

function ajoutEventRetireDiscipline() {
	bouton2 = document.getElementById("retireDiscipline");
	ajouteEvent(bouton2, 'click', retireDiscipline, false);
}

function afficherDiscipline() {
	var estProf = false;
	for (var i = 1; i <= compteurF; i++) {
		if ($('#fonction' + i + " option:selected").text() == "Professeur") {
			estProf = true;
		}
	}
	if (estProf) {
		$('#disciplines').show();
	} else {
		$('#disciplines').hide();
	}
}


