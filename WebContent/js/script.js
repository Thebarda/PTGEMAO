function ajouteEvent(objet, typeEvent, nomFunction, typePropagation){
	
	if(objet.addEventListener){
		objet.addEventListener(typeEvent,nomFunction,typePropagation);
	} else if(objet.attachEvent){
		objet.attachEvent('on'+typeEvent,nomFunction);
	}
}

function getDateActuelle()
{
	var date = new Date;
	var jour = date.getDate();
	var mois = date.getMonth()+1;
	var annee = date.getFullYear();
	if (jour<10)
		jour = "0"+jour;
	if (mois<10)
		mois = "0"+mois;
	var dateActuelle = jour+"/"+mois+"/"+annee;
	
	return dateActuelle;
}

$(function(){
	$(".tablesorter-blue").tablesorter();
});

document.getElementById("dateEmprunt").addEventListener("focus", function(e){
    var today = new Date();
    var date = e.target.value.split("/", 3);
    var day = date[0];
    var month = date[1];
    var year = date[2];
    if((today.getDate()>day)||(today.getMonth()>month)||(today.getFullYear()>year)){
        document.getElementById("dateErreur").textContent = "Date invalide";
        document.getElementById("dateErreur").style.color = "red";
        document.getElementById("valider").disabled = true;
    }else{
    	document.getElementById("dateErreur").textContent = "Date valide";
    	document.getElementById("dateErreur").style.color = "green";
    	document.getElementById("valider").disabled = false;
    }
});