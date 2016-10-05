var cpt = 1;

function ajoutMotif()
{
	var rep = prompt("Ajouter un motif de sortie");
	rep = rep.trim();
	if(rep && rep != "")
	{
		var input = document.getElementById("libelleMotif");
		input.value = rep;
		document.getElementById("desinscrire").submit();
	} 
	else 
	{
		alert("Abandon de l'ajout");
	}
}

function confirmDesinscription()
{
	var res = window.confirm("Souhaitez-vous réellement désinscrire cet adhérent ?");
	
	return res;
}

window.onload = function()
{
	var boutonAjouter = document.getElementById("ajoutMotif");
	var form = document.getElementById("desinscrire");
	var champ_date = document.getElementById("dateSortie");
	champ_date.value = getDateActuelle();
	
	//form.setAttribute("onsubmit", "return confirmDesinscription()");
	if(boutonAjouter)
	{
		ajouteEvent(boutonAjouter, 'click', ajoutMotif, false);
	}
}