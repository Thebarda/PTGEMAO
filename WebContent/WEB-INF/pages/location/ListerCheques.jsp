<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="fr.gemao.view.Pattern"%>

<c:set var="titre" value="Liste des chèques des partenaires" scope="request" />

<c:import url="/inc/head.inc.jsp" />

<c:import url="/inc/header.inc.jsp" />
<c:import url="/inc/menu.inc.jsp" />


<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<h1>Lister les chèques des partenaires</h1>

<form method="post" action="#" id="formDate" class="offset">	
			Choisir le mois et l'année : 
			<select name="month" required='required'>
				<option></option>
				<c:forEach items="${lesMois}" var="mois">
					<option value="${mois}"><c:out value="${mois}"></c:out></option>
				</c:forEach>
			</select>
			<select name="year" required='required'>
				<option></option>
				<c:forEach items="${lesAnnees}" var="annee">
					<option value="${annee}"><c:out value="${annee}"></c:out></option>
				</c:forEach>
			</select>
			<input type="submit" class="btn" value="Filtrer" />
</form>
<br>
<a class="offset btn" href="<c:url value="<%= Pattern.COMPTABILITE_AJOUTER_CHEQUES %>"/>">Ajouter un cheque de partenaire</a>
<br>
<br>

<table class='tablesorter-blue  pure-table' id="tableNom">
	<thead>
		<tr>
			<th>Partenaires</th>
			<th>Date paiement</th>
			<th>Montant chèque</th>
			<th>N° du chèque</th>
			<th>Date encaissement prévu</th>
			<th>Date encaissement effective</th>
		</tr>
	</thead>
	<c:choose>
		<c:when test="${empty chequesParMoisAnnee}">
		<form method="post" action="#">
			<c:forEach items="${cheques}" var="cheque">
				<tr>
					<td><c:out value="${cheque.getPartenaire().getRaisonSociale() }"></c:out></td>
					<td><c:out value="${cheque.getDatePaiement() }"></c:out></td>
					<td><c:out value="${cheque.getMontant() }"></c:out> €</td>
					<td><c:out value="${cheque.getNumero() }"></c:out></td>
					<td><c:out value="${cheque.getDateEncaissement() }"></c:out></td>
					<td>
						<c:if test="${empty cheque.getDateEncaissementEffective()}">
								<input type="text" name="numCheques" class="hidden" value="<c:out value="${cheque.getNumero() }"></c:out>">
								<input type="text" name="dateEffective" class="datepicker">
								<input type="submit" class="btn" value="Valider">
						</c:if>
						<c:out value="${cheque.getDateEncaissementEffective() }"></c:out>
					</td>
				</tr>
			</c:forEach>
			</form>
		</c:when>
		<c:otherwise>
		<form method="post" action="#">
			<a class="offset btn" href="<c:url value="<%= Pattern.COMPTABILITE_LISTER_CHEQUES %>"/>"/>Lister tous les chèques des partenaires</a>
			<br>
			<br>
			<c:forEach items="${chequesParMoisAnnee}" var="cheque">
				<tr>
					<td><c:out value="${cheque.getPartenaire().getRaisonSociale() }"></c:out></td>
					<td><c:out value="${cheque.getDatePaiement() }"></c:out></td>
					<td><c:out value="${cheque.getMontant() }"></c:out></td>
					<td><c:out value="${cheque.getNumero() }"></c:out></td>
					<td><c:out value="${cheque.getDateEncaissement() }"></c:out></td>
					<td>
						<c:if test="${empty cheque.getDateEncaissementEffective()}">
								<input type="text" name="numCheques" class="hidden" value="<c:out value="${cheque.getNumero() }"></c:out>">
								<input type="text" name="dateEffective" class="datepicker">
								<input type="submit" class="btn" value="Valider">
						</c:if>
						<c:out value="${cheque.getDateEncaissementEffective() }"></c:out>
					</td>
				</tr>
			</c:forEach>
			</form>
		</c:otherwise>
	</c:choose>
</table>

<c:import url="/inc/footer.inc.jsp" />