<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="fr.gemao.view.Pattern"%>

<c:set var="titre" value="Liste des contrats de location" scope="request" />

<c:import url="/inc/head.inc.jsp" />

<c:import url="/inc/header.inc.jsp" />
<c:import url="/inc/menu.inc.jsp" />


<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<fieldset>
	<legend>Locataire</legend>
	<label for="adherentNom">Nom :</label>
	<span><c:out value="${nomAdh }"></c:out></span>
	<br>
	<label for="adherentPrenom">Prenom :</label>
	<span><c:out value="${prenomAdh }"></c:out></span>
</fieldset>
<fieldset>
	<legend>Instrument</legend>
	<label for="categorie">Catégorie :</label>
	<span><c:out value="${categorie }"></c:out></span>
	<br>
	<label for="designation">Désignation :</label>
	<span><c:out value="${designation }"></c:out></span>
	<br>
	<label for="etat">Etat :</label>
	<span><c:out value="${etat }"></c:out></span>
</fieldset>
<fieldset>
	<legend>Location</legend>
	<label for="typeLocation">Type de location : </label>
	<span><c:out value="${typeLocation }"></c:out></span>
	<br>
	<label for="datedeb">Date d'emprunt :</label>
	<span><c:out value="${dateEmprunt }"></c:out></span>
	<br>
	<label for="datefin">Date d'échéance :</label>
	<span><c:out value="${dateEcheance }"></c:out></span>
	<br>
	<label for="dateRetourEffectif">Date de retour :</label>
	<span><c:out value="${dateRetour }"></c:out></span>
	<br>
	<p>Montant de la location :
	<span><c:out value="${montant }"></c:out> €</span> </p>
</fieldset>
<fieldset>
	<legend>Chèques</legend>
	<table class="tablesorter-blue  pure-table">
	  	<thead>
	  		<tr>
	  			<th>Date paiement</th>
	  			<th>Montant du chèque</th>
	  			<th>Numéro de chèque</th>
	  			<th>Date d'encaissement</th>
	  		</tr>
	  	</thead>
	  	<c:forEach items="${cheques }" var="cheque">
	  		<tr>
		  		<td><c:out value="${cheque.getDatePaiement() }"></c:out></td>
		  		<td><c:out value="${cheque.getMontantCheque() }"></c:out></td>
		  		<td><c:out value="${cheque.getNumCheque() }"></c:out></td>
		  		<td><c:out value="${cheque.getDateEncaissement() }"></c:out></td>
	  		</tr>
	  	</c:forEach>
	</table>
	<p><b> Il reste à payer <c:out value="${montantRestantAPaye }"></c:out> €</b></p>
</fieldset>
<fieldset>
	<a href="<c:url value="<%=Pattern.LOCATION_LISTER %>" />" class="offset btn">Lister locations</a>
</fieldset>
<c:import url="/inc/footer.inc.jsp" />