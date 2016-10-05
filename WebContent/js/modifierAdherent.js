var compteur=0;

function retireDiscipline(){
	if(compteur>1){
		document.getElementById("disciplines").removeChild(document.getElementById("disciplines").lastChild);
		compteur--;
	}else if(compteur == 1){
		document.getElementById("select1").style.display="none";
		document.getElementById("disciplines1").setAttribute("disabled", "disabled");
		compteur --;
	}
}

function ajoutDiscipline(){
	if(compteur == 0){
		document.getElementById("select1").style.display="block";
		document.getElementById("disciplines1").removeAttribute("disabled");
		compteur++;
	}else{
		compteur++;
		var div = document.getElementById("disciplines").lastElementChild.cloneNode(true);
		div.id="select"+compteur;
		document.getElementById("disciplines").appendChild(div);
		document.getElementById("select"+compteur).firstChild.nextSibling.nextSibling.nextSibling.name="disciplines"+compteur;
		/*<!--document.getElementById(compteur).lastElementChild.firstElementChild.name="clas"+compteur;	-->*/
	}
}

function ajoutNewDiscipline(){
	var answer = prompt ("Ajouter une discipline");
	answer = answer.trim();
	if(answer && answer!=""){
		var input=document.getElementById("nomDiscipline");
		input.value=answer;
		document.getElementById("modifAdherent").submit();
	} else {
		alert("Abandon de l'ajout");
	}
}

$(function() {
	bouton1=document.getElementById("ajoutDiscipline");
	bouton2=document.getElementById("retireDiscipline");
//	bouton3=document.getElementById("ajoutNewDiscipline");
	ajouteEvent(bouton1, 'click', ajoutDiscipline, false);
	ajouteEvent(bouton2, 'click', retireDiscipline, false);
//	ajouteEvent(bouton3, 'click', ajoutNewDiscipline, false);
	document.getElementById("select1").style.display="none";
	
	document.getElementById("disciplines1").setAttribute("disabled", "disabled");
	
	$(".supprimerDiscipline").click(function (e) {
		var div = e.target.parentNode;
		var listSuivant = [];
		suivant = div.nextSibling.nextSibling;
		while(suivant && suivant.id!="select1"){
			listSuivant.push(suivant);
			suivant = suivant.nextSibling.nextSibling;
		}
		for (i=listSuivant.length-1;i>=0;i--){
			prev=listSuivant[i].previousSibling.previousSibling;
			listSuivant[i].getElementsByTagName('input')[0].name=prev.getElementsByTagName('input')[0].name;
		}			
		div.parentNode.removeChild(div);
	});
});