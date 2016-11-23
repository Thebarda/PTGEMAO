<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="fr.gemao.view.Pattern"%>

<c:set var="titre" value="Location externe d'un instrument" scope="request" />

<c:import url="/inc/head.inc.jsp" />

<c:import url="/inc/header.inc.jsp" />
<c:import url="/inc/menu.inc.jsp" />
<script type="text/javascript">
function autocompletionFamille(selecteurFamille){
	$(function() {
		var availableTags = ${requestScope.auto_familles};
		$(selecteurFamille).autocomplete({
			source : availableTags
		});
	});
}
autocompletionFamille("#pers");
</script>

	<h1>Location externe d'un instrument</h1>

	<c:choose >
		<c:when test="${empty resultat}">
			<form id="location" method="post" action="#">
				<fieldset>
					<legend>Instrument</legend>
					<c:choose>
						<c:when test="${!empty requestScope.listeCategorie}">
							<div>
								<label for="categorie">Catégorie :</label>
								<select name="categorie">
									<c:forEach items="${requestScope.listeCategorie}" var="categorie">
										<option value="${categorie['idCategorie']}"><c:out
												value="${categorie['libelleCat']}" /></option>
									</c:forEach>
								</select>
							</div>
						</c:when>
						<c:otherwise>
							
								<div><span class='text-label'>Catégorie : </span><span><%= session.getAttribute("nomCategorie") %></span></div>
								</fieldset>
								<fieldset>
								<label for="instrument">Instrument : </label>
									<select name="nomDesignation" rows="40">
										<c:forEach items="${listeMateriel}" var="instrument">
											<option value="${instrument.key}"><c:out
													value="${instrument.value}" /></option>
										</c:forEach>
									</select><br><br>
									<label for="adh">Personne : </label><input type="text" id="pers" name="adherent">
									<!--<select name="adherent">
										<c:forEach items="${listePersonne}" var="pers">
											<option value="${pers['idPersonne']}"><c:out
													value="${pers['nom']}" /></option>
										</c:forEach>
									</select>--><br><br>
									<label>Date d'emprunt : </label><input id="dateEmprunt" type="text" class="datepicker" required='required' name="debutLocation"><span id="dateErreur"></span><br><br>
									<label>Date d'échéance : </label><input id="dateEcheance" type="text" class="datepicker" required='required' name="finLocation"><span id="dateErreur2"></span><br><br>
									<br><br><label>Montant (par mois) : </label><input id="montant" type="text" required='required' name="montant"><span id="montantErreur"></span>
									<br><br><label>Date encaissement : </label>
							</fieldset>
						</c:otherwise>	
						</c:choose>
				<fieldset class='align-center no-border'>
					<input id="valider" type="submit" value="Valider" />
				</fieldset>
			</form>
		</c:when>
		<c:otherwise>
			<form method="post" action="#">
				<fieldset>
					<legend>Validation des informations</legend>
					<label>Categorie : </label><span id="nomCategorie"><%= session.getAttribute("nomCategorie") %></span><br>
					<label>Instrument : </label><span id="nomInstrument"><%= session.getAttribute("nomInstrument") %></span><br>
					<label>Personne : </label><span id="nomAdherent"><%= session.getAttribute("nomAdherent") %></span><br>
					<label>Date d'emprunt : </label><span id="debutLocation"><%= session.getAttribute("debutLocation") %></span><br>
					<label>Date d'echéance : </label><span id="finLocation"><%= session.getAttribute("finLocation") %></span><br>
					<label>Montant : </label><span id="leMontant"><%= session.getAttribute("montant") %></span><br>
					<label>Caution : </label><span id="laCaution"><%= session.getAttribute("caution") %></span><br>
					<label>Imprimer : </label><input type="radio" name="imprimer" value="Oui" checked> Oui
  											  <input type="radio" name="imprimer" value="Non"> Non<br>
				</fieldset>
				<fieldset class='align-center no-border'>
						<button href="<c:url value="<%= Pattern.ACCUEIL %>"/>">Annuler</button>
						<input type="submit" value="Valider" />
				</fieldset>
			</form>
		</c:otherwise>
	</c:choose>
	

<c:import url="/inc/footer.inc.jsp" />