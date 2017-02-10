<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="fr.gemao.view.Pattern"%>

<c:set var="titre" value="Liste des chèques de location" scope="request" />

<c:import url="/inc/head.inc.jsp" />

<c:import url="/inc/header.inc.jsp" />
<c:import url="/inc/menu.inc.jsp" />


<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<h1>Liste des chèques</h1>

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

<table class='tablesorter-blue  pure-table' id="tableNom">
	<thead>
		<tr>
			<th>N° Contrat</th>
			<th>Type de location</th>
			<th>Nom locataire</th>
			<th>Nom instrument</th>
			<th>Date paiement</th>
			<th>Montant chèque</th>
			<th>N° du chèque</th>
			<th>Date encaissement prévu</th>
			<th>Date encaissement effective</th>
			<th>Options</th>
		</tr>
	</thead>
	<c:choose>
		<c:when test="${empty chequesParMoisAnnee}">
		<form method="post" action="#">
			<c:forEach items="${cheques}" var="cheque">
				<tr>
					<td>
						<a href="#"><c:out value="${cheque.getLocation().getId() }"></c:out><i class="fa fa-search"></i>
							<span>
								<b>Type :</b> <c:out value="${cheque.getLocation().getType() }"></c:out><br>
								<b>Référence :</b> <c:out value="${cheque.getLocation().getMateriel().getNumSerie() }"></c:out><br>
								<b>Catégorie :</b> <c:out value="${cheque.getLocation().getMateriel().getCategorie().getLibelleCat() }"></c:out><br>
								<b>Désignation : </b><c:out value="${cheque.getLocation().getMateriel().getDesignation().getLibelleDesignation() }"></c:out><br>
								<b>Nom : </b><c:out value="${cheque.getLocation().getPersonne().getNom() }"></c:out><br>
								<b>Prenom : </b><c:out value="${cheque.getLocation().getPersonne().getPrenom() }"></c:out><br>
								<b>Date d'emprunt :</b> <c:out value="${cheque.getLocation().getDateEmprunt() }"></c:out><br>
								<b>Date d'échéance : </b><c:out value="${cheque.getLocation().getDateEcheance() }"></c:out><br>
								<b>Date de retour : </b><c:out value="${cheque.getLocation().getDateRetour() }"></c:out><br>
								<b>Etat début : </b><c:out value="${cheque.getLocation().getEtatDebut().getLibelleEtat() }"></c:out><br>
								<b>Etat de fin : </b><c:out value="${cheque.getLocation().getEtatFin().getLibelleEtat() }"></c:out>
							</span>
						</a>
					</td>
					<td><c:out value="${cheque.getLocation().getType() }"></c:out></td>
					<td><c:out value="${cheque.getLocation().getPersonne().getNom()}"></c:out></td>
					<td><c:out value="${cheque.getLocation().getMateriel().getTypeMat() }"></c:out></td>
					<td><c:out value="${cheque.getDatePaiement() }"></c:out></td>
					<td><c:out value="${cheque.getMontantCheque() }"></c:out></td>
					<td><c:out value="${cheque.getNumCheque() }"></c:out></td>
					<td><c:out value="${cheque.getDateEncaissement() }"></c:out></td>
					<td>
						<c:if test="${empty cheque.getDateEncaissementEffective()}">
								<input type="text" name="numCheques" class="hidden" value="<c:out value="${cheque.getNumCheque() }"></c:out>">
								<input type="text" name="dateEffective" class="datepicker">
								<input type="submit" class="btn" value="Valider">
						</c:if>
						<c:out value="${cheque.getDateEncaissementEffective() }"></c:out>
					</td>
					<td><a href="<c:url value="<%= Pattern.LOCATION_CHEQUE_SUPPRIMER %>"/>?numCheque=<c:out value="${cheque.getNumCheque()}" />"
							title='Supprimer cet enregistrement de cheque'><img src="<c:url value="/ressources/images/supprimer.jpg"/>" alt="Supprimer enregistrement cheque"></a>
							<c:if test="${empty cheque.getDateEncaissementEffective()}"><a href="<c:url value="<%= Pattern.LOCATION_CHEQUE_DEE %>"/>?id=<c:out value="${cheque.getNumCheque()}" />"
					title='Enregistrer une date encaissement effective'><img src="<c:url value="/ressources/images/cheque.jpg"/>" alt="Enregistrer Date Encaissement Effective"></a></c:if>
							</td>
				</tr>
			</c:forEach>
			</form>
		</c:when>
		<c:otherwise>
		<form method="post" action="#">
			<a class="offset btn" href="<c:url value="<%= Pattern.LOCATION_CHEQUE_LISTER %>"/>"/>Lister tous les chèques</a>
			<br>
			<br>
			<c:forEach items="${chequesParMoisAnnee}" var="cheque">
				<tr>
					<td>
						<a href="#"><c:out value="${cheque.getLocation().getId() }"></c:out>
							<span>
								<b>Type :</b> <c:out value="${cheque.getLocation().getType() }"></c:out><br>
								<b>Référence :</b> <c:out value="${cheque.getLocation().getMateriel().getNumSerie() }"></c:out><br>
								<b>Catégorie :</b> <c:out value="${cheque.getLocation().getMateriel().getCategorie().getLibelleCat() }"></c:out><br>
								<b>Désignation : </b><c:out value="${cheque.getLocation().getMateriel().getDesignation().getLibelleDesignation() }"></c:out><br>
								<b>Nom : </b><c:out value="${cheque.getLocation().getPersonne().getNom() }"></c:out><br>
								<b>Prenom : </b><c:out value="${cheque.getLocation().getPersonne().getPrenom() }"></c:out><br>
								<b>Date d'emprunt :</b> <c:out value="${cheque.getLocation().getDateEmprunt() }"></c:out><br>
								<b>Date d'échéance : </b><c:out value="${cheque.getLocation().getDateEcheance() }"></c:out><br>
								<b>Date de retour : </b><c:out value="${cheque.getLocation().getDateRetour() }"></c:out><br>
								<b>Etat début : </b><c:out value="${cheque.getLocation().getEtatDebut().getLibelleEtat() }"></c:out><br>
								<b>Etat de fin : </b><c:out value="${cheque.getLocation().getEtatFin().getLibelleEtat() }"></c:out>
							</span>
						</a>
					</td>
					<td><c:out value="${cheque.getLocation().getType() }"></c:out></td>
					<td><c:out value="${cheque.getLocation().getPersonne().getNom()}"></c:out></td>
					<td><c:out value="${cheque.getLocation().getMateriel().getNumSerie() }"></c:out></td>
					<td><c:out value="${cheque.getDatePaiement() }"></c:out></td>
					<td><c:out value="${cheque.getMontantCheque() }"></c:out></td>
					<td><c:out value="${cheque.getNumCheque() }"></c:out></td>
					<td><c:out value="${cheque.getDateEncaissement() }"></c:out></td>
					<td>
						<c:if test="${empty cheque.getDateEncaissementEffective()}">
								<input type="text" name="numCheques" class="hidden" value="<c:out value="${cheque.getNumCheque() }"></c:out>">
								<input type="text" name="dateEffective" class="datepicker">
								<input type="submit" class="btn" value="Valider">
						</c:if>
						<c:out value="${cheque.getDateEncaissementEffective() }"></c:out>
					</td>
					<td><a href="<c:url value="<%= Pattern.LOCATION_CHEQUE_SUPPRIMER %>"/>?numCheque=<c:out value="${cheque.getNumCheque()}" />"
							title='Supprimer cet enregistrement de cheque'><img src="<c:url value="/ressources/images/supprimer.jpg"/>" alt="Supprimer enregistrement cheque"></a>
							<c:if test="${empty cheque.getDateEncaissementEffective()}"><a href="<c:url value="<%= Pattern.LOCATION_CHEQUE_DEE %>"/>?id=<c:out value="${cheque.getNumCheque()}" />"
					title='Enregistrer une date encaissement effective'><img src="<c:url value="/ressources/images/cheque.jpg"/>" alt="Enregistrer Date Encaissement Effective"></a></c:if>
							</td>
				</tr>
			</c:forEach>
			</form>
		</c:otherwise>
	</c:choose>
</table>
<script type="text/javascript">
  $(function() {
    if ($.browser.msie && $.browser.version.substr(0,1)<7)
    {
      $('.tooltip').mouseover(function(){
            $(this).children('span').show();
          }).mouseout(function(){
            $(this).children('span').hide();
          });
    }
  });
</script>
<c:import url="/inc/footer.inc.jsp" />
