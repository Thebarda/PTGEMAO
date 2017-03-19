<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="fr.gemao.view.Pattern"%>

<c:set var="titre" value="Comptabilité enseignement" scope="request" />

<c:import url="/inc/head.inc.jsp" />

<c:import url="/inc/header.inc.jsp" />
<c:import url="/inc/menu.inc.jsp" />
<h1>Comptabilité enseignement</h1>

<c:choose>
<c:when test="${empty idFamille }">
	<form method="post" action="#">
	<fieldset>
		<legend>Familles</legend>
		<label>Veuillez choisir une famille</label>
		<input type="text" name="famille" id="famille">
		</select>
		<label>Choisir l'année : </label>
		<select name="annee">
			<option></option>
			<c:forEach items="${date }" var="year">
				<option value="${year }"><c:out value="${year }"></c:out></option>
			</c:forEach>
		</select>
		<input type="submit" value="Valider" class="btn">
	</fieldset>
	</form>
</c:when>
<c:otherwise>
	<c:choose>
	<c:when test="${empty vide }">
	<form method="post" action="#">
	<fieldset>
		<legend>Familles</legend>
		<label>Veuillez choisir une famille</label>
		<input type="text" name="famille" id="famille">
		</select>
		<label>Choisir l'année : </label>
		<select name="annee">
			<option></option>
			<c:forEach items="${date }" var="year">
				<option value="${year }"><c:out value="${year }"></c:out></option>
			</c:forEach>
		</select>
		<input type="submit" value="Valider" class="btn">
	</fieldset>
	</form>
	<span><h2 class="offset">Famille : <%= request.getAttribute("nomFamille") %></h2></span>
	<h2 class="offset">Année : <%= request.getAttribute("annee") %> - <%= request.getAttribute("anneeFin") %></h2>
	<h3 id="validation" class="offset text-success"></h3>
	<h2 class="offset">Tableau Fiche Comptable</h2>
	<table>
		<tr>
			<td class='ignorer'></td>
			<th class='numEleve'>Elève 1</th>
			<th class='numEleve'>Elève 2</th>
			<th class='numEleve'>Elève 3</th>
			<th class='numEleve'>Elève 4</th>
			<th class='numEleve'>Elève 5</th>
		</tr>
		<tr>
			<th class='intitule'>Prénom</th>
			<td contenteditable='true' class='nom' id='eleve_1'><c:out value="${eleves[0] }"></c:out></td>
			<td contenteditable='true' class='nom' id='eleve_2'><c:out value="${eleves[1] }"></c:out></td>
			<td contenteditable='true' class='nom' id='eleve_3'><c:out value="${eleves[2] }"></c:out></td>
			<td contenteditable='true' class='nom' id='eleve_4'><c:out value="${eleves[3] }"></c:out></td>
			<td contenteditable='true' class='nom' id='eleve_5'><c:out value="${eleves[4] }"></c:out></td>
		</tr>
		<div id="tfc">
		<%= request.getAttribute("tfc") %>
		</div>
	</table>
	<br><br>
	<h2 class="offset">Tableau Récapitulatif</h2>
	<div id="recap">
		<%= request.getAttribute("tr") %>
	</div>
	<button id="enregistrer" class="offset btn" style="margin-top: 10px;">Enregistrer les tableaux</button>
	</c:when>
	<c:otherwise>
		<p class="offset"><c:out value="${vide }"></c:out></p>
	</c:otherwise>
	</c:choose>
</c:otherwise>
</c:choose>
<script type="text/javascript">
$(document).ready(function(){	
	//////////////////////////////////
	var nbColonneAGriser = 5-${requestScope.nbEleves}
	var depart=${requestScope.nbEleves};
	//Grisage
	for(var i=depart;i<=5;i++){
		$('[id*="_col_'+i+'"]').each(function(){
			$(this).attr("contenteditable", false);
			$(this).css("background-color", "#A6A1A0");
			$(this).text("");
		});
	}
	for(var i=(depart+1);i<=5;i++){
		$('[id="eleve_'+i+'"]').each(function(){
			console.log(i);
			$(this).attr("contenteditable", false);
			$(this).css("background-color", "#A6A1A0");
			$(this).text("");
		});
	}
	for(var i=(depart+1);i<=5;i++){
		$('[id*="qf_'+i+'"]').each(function(){
			$(this).attr("contenteditable", false);
			$(this).css("background-color", "#A6A1A0");
			$(this).text("");
		});
	}
	for(var i=(depart+1);i<=5;i++){
		$('[class*="ce_'+i+'"]').each(function(){
			$(this).attr("contenteditable", false);
			$(this).css("background-color", "#A6A1A0");
			$(this).text("");
		});
	}
	//Blanchage
	for(var i=0;i<depart;i++){
		$('[id*="_col_'+i+'"]').each(function(){
			$(this).attr("contenteditable", true);
			$(this).css("background-color", "#FFFFFF");
		});
	}
	for(var i=1;i<=depart;i++){
		$('[id="eleve_'+i+'"]').each(function(){
			$(this).attr("contenteditable", true);
			$(this).css("background-color", "#FFFFFF");
		});
	}
	for(var i=1;i<=depart;i++){
		$('[id*="qf_'+i+'"]').each(function(){
			$(this).attr("contenteditable", true);
			$(this).css("background-color", "#FFFFFF");
		});
	}
	for(var i=1;i<=depart;i++){
		$('[class*="ce_'+i+'"]').each(function(){
			$(this).attr("contenteditable", true);
			$(this).css("background-color", "#FFFFFF");
		});
	}
	//////////////////////////////////
	$("#ajoutTab").click( function () {
		var ligne = parseInt($("#tableau tbody tr:last-child").attr("data"))+parseInt(1);
		var nbLigne = parseInt(ligne)-parseInt(11);
		var row = $('<tr id="ajoutLigne_'+nbLigne+'" data="'+ligne+'">');

		row.append($('<td contenteditable="true" class="intitule"></td>'))
		   .append($('<td contenteditable="true" id="lig_'+ligne+'_col_0" class="tableauRecap"></td>'))
		   .append($('<td contenteditable="true" id="lig_'+ligne+'_col_1" class="tableauRecap"></td>'))
		   .append($('<td contenteditable="true" id="lig_'+ligne+'_col_2" class="tableauRecap"></td>'))
		   .append($('<td contenteditable="true" id="lig_'+ligne+'_col_3" class="tableauRecap"></td>'))
		   .append($('<td contenteditable="true" id="lig_'+ligne+'_col_4" class="tableauRecap"></td>'))
		   .append($('<td id="total_'+ligne+'" class="sous_total"></td>'))
		   .append($('<td contenteditable="true"></td>'))
		   .append($('<td contenteditable="true" class="sommePayee" id="paiement_'+ligne+'"></td>'))
		   .append($('<td><span id="date_'+ligne+'">--/--/----</span><input type="hidden" id="datepicker_'+ligne+'"></td>'))
		   .append($('<td contenteditable="true"></td>'))
		   .append($('<td class="supprimer ignorer"><button type="button" class="supprTab">X</button></td>'))
		   .append($('</tr>'));
		   
		row.find('input').datepicker({
			showOn: 'button',
			buttonText: 'Show Date',
			buttonImageOnly: true,
			buttonImage: 'http://jqueryui.com/resources/demos/datepicker/images/calendar.gif'
		});
		$("#tableau tbody").append(row);
		for(var i=depart;i<=(nbColonneAGriser+4);i++){
			$('[id*="_col_'+i+'"]').each(function(){
				$(this).attr("contenteditable", false);
				$(this).css("background-color", "#A6A1A0");
				$(this).text("");
			});
		}
		for(var i=0;i<depart;i++){
			$('[id*="_col_'+i+'"]').each(function(){
				$(this).attr("contenteditable", true);
				$(this).css("background-color", "#FFFFFF");
			});
		}
	});
});
</script>
<script src="<c:url value="/js/ScriptFicheComptable.js"/>" ></script>
<script src="<c:url value="/js/ScriptTableauRecapitulatif.js"/>" ></script>
<script src="<c:url value="/js/enregistrerTableaux.js"/>" ></script>
<script type="text/javascript">
function autocompletionFamille(selecteurFamille){
	$(function() {
		var availableTags = ${requestScope.familles};
		console.log(availableTags);
		$(selecteurFamille).autocomplete({
			source : availableTags
		});
	});
}
autocompletionFamille("#famille");
</script>
<c:import url="/inc/footer.inc.jsp" />