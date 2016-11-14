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

document.getElementById("dateEcheance").addEventListener("focus", function(e){
    var today = new Date();
    var dateEcheance = e.target.value.split("/", 3);
    var dateEmprunt = document.getElementById("dateEmprunt").value;
    var dateEmpr = dateEmprunt.split("/", 3);
    var yearEmprunt = Number(dateEmpr[2]);
    var monthEmprunt = Number(dateEmpr[1]);
    var dayEmprunt = Number(dateEmpr[0]);
    var dayFin = Number(dateEcheance[0]);
    var monthFin = Number(dateEcheance[1]);
    var yearFin = Number(dateEcheance[2]);
   if(yearFin < yearEmprunt){
    	document.getElementById("dateErreur2").textContent = "Date invalide";
        document.getElementById("dateErreur2").style.color = "red";
        document.getElementById("valider").disabled = true;
    }else if(yearFin == yearEmprunt){
    	if(monthFin < monthEmprunt){
    		document.getElementById("dateErreur2").textContent = "Date invalide";
            document.getElementById("dateErreur2").style.color = "red";
            document.getElementById("valider").disabled = true;
    	}else if(monthEmprunt==monthFin){
    		if(dayFin <= dayEmprunt){
    			document.getElementById("dateErreur2").textContent = "Date invalide";
    	        document.getElementById("dateErreur2").style.color = "red";
    	        document.getElementById("valider").disabled = true;
    		}else{
    			document.getElementById("dateErreur2").textContent = "Date valide";
            	document.getElementById("dateErreur2").style.color = "green";
            	document.getElementById("valider").disabled = false;
    		}
    	}else{
    		document.getElementById("dateErreur2").textContent = "Date valide";
        	document.getElementById("dateErreur2").style.color = "green";
        	document.getElementById("valider").disabled = false;
    	}
    }else{
    	document.getElementById("dateErreur2").textContent = "Date valide year";
    	document.getElementById("dateErreur2").style.color = "green";
    	document.getElementById("valider").disabled = false;
    }
});