var compteur=1;

function retireDiscipline(){
	if(compteur>1){
		document.getElementById("disciplines").removeChild(document.getElementById("disciplines").lastChild);
		compteur--;
	}
}

function ajoutDiscipline(){
	compteur++;
	var div = document.getElementById("disciplines").lastElementChild.cloneNode(true);
	div.id="select"+compteur;
	document.getElementById("disciplines").appendChild(div);
	document.getElementById("select"+compteur).firstChild.nextSibling.nextSibling.nextSibling.name="disciplines"+compteur;
	/*<!--document.getElementById(compteur).lastElementChild.firstElementChild.name="clas"+compteur;	-->*/
}

function ajoutNewDiscipline(){
	var answer = prompt ("Ajouter une discipline");
	answer = answer.trim();
	if(answer && answer!=""){
		var input=document.getElementById("nomDiscipline");
		input.value=answer;
		document.getElementById("ajoutAdherent").submit();
	} else {
		alert("Abandon de l'ajout");
	}
}

$(function() {
	champ_date_inscription = document.getElementById("dateInscri");
	champ_date_inscription.value = getDateActuelle();
	bouton1=document.getElementById("ajoutDiscipline");
	bouton2=document.getElementById("retireDiscipline");
	ajouteEvent(bouton1, 'click', ajoutDiscipline, false);
	ajouteEvent(bouton2, 'click', retireDiscipline, false);
});
