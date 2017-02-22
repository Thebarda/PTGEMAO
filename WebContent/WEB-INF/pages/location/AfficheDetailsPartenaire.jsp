<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="fr.gemao.view.Pattern"%>

<c:set var="titre" value="Liste des contrats de location" scope="request" />

<c:import url="/inc/head.inc.jsp" />

<c:import url="/inc/header.inc.jsp" />
<c:import url="/inc/menu.inc.jsp" />

<h1>Détails du partenaire <c:out value="${partenaire.getRaisonSociale() }"></c:out></h1>
<form method="post" action="#">
	<fieldset>
		<legend>Partenaire</legend>
		<p><b>Raison social : </b><c:out value="${partenaire.getRaisonSociale() }"></c:out></p>
		<p><b>Adresse postale : </b><c:out value="${partenaire.getAdresse().getNumRue() }"></c:out> <c:out value="${partenaire.getAdresse().getNomRue() }"></c:out> <c:out value="${partenaire.getAdresse().getInfoCompl() }"></c:out></p>
		<p><b>Commune : </b><c:out value="${partenaire.getAdresse().getCommune().getNomCommune() }"></c:out></p>
		<p><b>Code postal : </b><c:out value="${partenaire.getAdresse().getCommune().getCodePostal() }"></c:out></p>
		<p><b>Année de partenariat : </b><c:out value="${partenaire.getAnnee() }"></c:out>
		<p><b>Année du dernier versement : </b><c:out value="${partenaire.getAnneeDernierVersement() }"></c:out></p>
		<b>Taille de la page</b> : <input type="text" name="taillePage" value='<c:out value="${partenaire.getTaillePage() }"></c:out>'>
		<input type="submit" value="Modifier la taille de la page" class="btn">
	</fieldset>
	<fieldset>
		<legend>Chèques</legend>
		<table class='tablesorter-blue  pure-table' id="tableNom">
			<thead>
				<tr>
					<th>Partenaires</th>
					<th>Date paiement</th>
					<th>Montant chèque</th>
					<th>N° du chèque</th>
					<th>Date encaissement prévue</th>
					<th>Date encaissement effective</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${cheques}" var="cheque">
					<tr>
						<td><c:out value="${cheque.getPartenaire().getRaisonSociale() }"></c:out></td>
						<td><c:out value="${cheque.getDatePaiement() }"></c:out></td>
						<td><c:out value="${cheque.getMontant() }"></c:out> €</td>
						<td><c:out value="${cheque.getNumero() }"></c:out></td>
						<td><c:out value="${cheque.getDateEncaissement() }"></c:out></td>
						<td><c:out value="${cheque.getDateEncaissementEffective() }"></c:out></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<p><b>Montant total des chèques : </b><c:out value="${montantTotal }"></c:out> €</p>
	</fieldset>
</form>
<c:import url="/inc/footer.inc.jsp" />