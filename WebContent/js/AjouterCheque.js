$(document).ready(function(){
	$('#ajoutCheque').click(function(){
		$('#cheques').append("<div id='chequeAjout'><label>Date paiement : </label><input id='datePaiement1' type='text' class='datepicker' size='10' name='datePaiement'><label for='montantCheque1'>Montant : </label><input id='montantCheque1' type='text' size='10' name='montantCheque'><label for='numeroCheque1'>Numéro chèque (11 caractères) : </label><input id='numeroCheque1' type='text' name='numeroCheque'><label>Date encaissement : </label><input id='dateEncaissement1' type='text' class='datepicker' size='10' name='dateEncaissement'></div>");
	});
	
	$('#retireCheque').click(function(){
		$('#chequeAjout:last-child').remove();
	});
});