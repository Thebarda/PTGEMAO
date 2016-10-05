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