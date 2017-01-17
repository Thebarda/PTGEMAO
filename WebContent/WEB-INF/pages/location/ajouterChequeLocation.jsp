<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="fr.gemao.view.Pattern"%>

<c:set var="titre" value="Liste des contrats de location" scope="request" />

<c:import url="/inc/head.inc.jsp" />

<c:import url="/inc/header.inc.jsp" />
<c:import url="/inc/menu.inc.jsp" />


<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<h1>Ajouter un chèque</h1>


<form id="ajoutCheque" action="#" method="post">
	<fieldset>
		<legend>Informations du chèque</legend>
		
		<div>
			<label for="datePaiement" class='required'>Date de paiement </label> <input type="text"
				name="datePaiement" class="datepicker" value="${cheque.getDatePaiement()}" required="required" />
		</div>
		
		<div>
			<label for="montantCheque" class='required'>Montant du chèque </label> <input type="text"
				name="montantCheque" value="${cheque.getMontantCheque()}" required="required" />
		</div>
		
		<div>
			<label for="numeroCheque" class='required'>Numéro du chèque </label> <input type="text"
				name="numeroCheque" value="${cheque.getNumCheque()}" required="required" />
		</div>
		
		<div>
			<label for="dateEncaissement" class='required'>Date d'encaissement </label> <input type="text"
				name="dateEncaissement" class="datepicker" value="${cheque.getDateEncaissement()}" required="required" />
		</div>
		
		
		
	
	</fieldset>
	
	<fieldset class='align-center no-border'>
		<p class="oblig">* Champs obligatoires</p>
		<a href="<c:url value="<%= Pattern.ACCUEIL %>"/>">Annuler</a>
		<input type="submit" class="btn" value="Enregistrer" />
	</fieldset>
</form>



<c:import url="/inc/footer.inc.jsp" />
