<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="fr.gemao.view.Pattern"%>

<c:set var="titre" value="Location interne d'un instrument" scope="request" />

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
autocompletionFamille("#adh");
</script>
<c:choose >
<c:when test="${empty erreurCheque }">
	<h1>Location interne d'un instrument</h1>
	<c:choose>
	<c:when test="${empty validation }">
		<c:choose >
			<c:when test="${empty resultat}">
				<form id="location" method="post" action="#">
					<fieldset class="ajoutCheque">
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
									
									<fieldset class="ajoutCheque">
									<br>
									<label for="instrument">Instrument : </label>
										<br>
										<table class="tablesorter-blue  pure-table">
											<thead>
												<tr><th>Désignation</th><th>Numéro de série</th><th>Type de matériel</th><th>date d'achat</th><th>valeur achat</th><th>valeur reapprovisionnement</th><th>est déplacable</th><th>observation</th><th>Etat</th><th>Choix</th></tr>
											</thead>
											<c:forEach items="${listeMateriel}" var="instrument">
												<tr>
												<c:forEach items="${instrument.value}" var="mat">
													<td><c:out value="${mat}"></c:out></td>
												</c:forEach>
													<td><input type="radio" name="nomDesignation" value="${instrument.key }" checked>Choisir</td>
												</tr>
											</c:forEach>
										</table>
										<br>
										<label for="adh">Adhérent *: </label><input id="adh" type="text" name="adherent"><br><br>
										<label>Date de la location *: </label><input id="dateEmprunt" type="text" class="datepicker" required='required' name="debutLocation">
										<p><b>Montant de la location totale : </b>20 €</p>
								</fieldset>
									<fieldset class="ajoutCheque">
										<legend>Chèque</legend>
										<label>Date paiement *: </label><input id="datePaiement" type="text" class="datepicker" size="10" name="datePaiement"><br>
										<label for="montantCheque">Montant *: </label><input id="montantCheque" type="text" size="10" name="montantCheque"><br> 
										<label for="numeroCheque">Numéro chèque (11 caractères) *: </label><input id="numeroCheque" type="text" name="numeroCheque"><br>
										<label>Date encaissement *: </label><input id="dateEncaissement" type="text" class="datepicker" size="10" name="dateEncaissement">
									</fieldset>
							</c:otherwise>	
							</c:choose>
					<fieldset class='align-center no-border'>
						<a href="<c:url value="<%= Pattern.ACCUEIL %>"/>">Annuler</a>
						<input id="valider" type="submit" value="Valider" />
					</fieldset>
				</form>
			</c:when>
			<c:otherwise>
				<form method="post" action="#">
					<fieldset>
						<legend>Validation des informations</legend>
						<label>Categorie : </label><span id="nomCategorie"><%= session.getAttribute("nomCategorie") %></span><br>
						<label>Instrument : </label>
						<table class="tablesorter-blue  pure-table">
							<thead>
								<tr><th>Désignation</th><th>Numéro de série</th><th>Type de matériel</th><th>date d'achat</th><th>valeur achat</th><th>valeur reapprovisionnement</th><th>est déplacable</th><th>observation</th><th>Etat</th></tr>
							</thead>
							<tr>
								<c:forEach items="${nomInstrument}" var="mat">
									<td><c:out value="${mat}"></c:out></td>
								</c:forEach>
							</tr>
						</table><br>
						<label><b>Adhérent : </b></label><span id="nomAdherent"><%= session.getAttribute("nomAdherent") %></span><br>
						<label><b>Date d'emprunt : </b></label><span id="debutLocation"><%= session.getAttribute("debutLocation") %></span><br>
						<label><b>Date d'echéance : </b></label><span id="finLocation"><%= session.getAttribute("finLocation") %></span><br>
						<label><b>Montant : </b></label><span id="leMontant"><%= session.getAttribute("montant") %> euros</span><br>
						<label><b>Caution : </b></label><span id="laCaution"><%= session.getAttribute("caution") %> euros</span><br>
						<label><b>Imprimer : </b></label><input type="radio" name="imprimer" value="Oui" checked> Oui
	  											  <input type="radio" name="imprimer" value="Non"> Non<br>
					</fieldset>
					<fieldset>
						<legend>Cheque</legend>
						<label>Date de paiement : </label><span><%= session.getAttribute("datePaiement") %></span><br>
						<label>Date d'encaissement : </label><span><%= session.getAttribute("dateEncaissement") %></span><br>
						<label>Numéro de chèque : </label><span><%= session.getAttribute("numeroCheque") %></span><br>
						<label>Montant du chèque : </label><span><%= session.getAttribute("montantCheque") %></span><br>
					</fieldset>
					<fieldset class='align-center no-border'>
							<a href="<c:url value="<%= Pattern.ACCUEIL %>"/>">Annuler</a>
							<input type="submit" value="Valider" />
					</fieldset>
				</form>
			</c:otherwise>
		</c:choose>
		</c:when>
		<c:otherwise>
			<p class="offset text-success"><%= request.getAttribute("validation") %></p><br>
			<c:if test="${!empty printerError }">
				<p class="offset text-danger"><%= request.getAttribute("printerError") %></p><br>
			</c:if>
			<a class="offset btn" href="<c:url value="<%= Pattern.ACCUEIL %>"/>">Retour</a>
		</c:otherwise>
	</c:choose>
</c:when>
<c:otherwise>
	<h3 class="offset text-danger"><%=request.getAttribute("erreurCheque") %></h3>
	<a class="offset btn" href="<c:url value="<%= Pattern.ACCUEIL %>"/>">Retour</a>
</c:otherwise>
</c:choose>
	
<c:import url="/inc/footer.inc.jsp" />