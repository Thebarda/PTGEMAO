$(document).ready(function(){
	var compteCheque=1;
	$('#ajoutCheque').click(function(){
			var form = $("<div id='chequeAjout'>");
			form.append("<label>Date paiement : </label><input id='datePaiement"+compteCheque+"' type='text' class='datepicker' size='10' name='datePaiement'>");
			form.append("<label for='montantCheque1'>Montant : </label><input id='montantCheque1' type='text' size='10' name='montantCheque'>");
			form.append("<label for='numeroCheque1'>Numéro chèque (11 caractères) : </label><input id='numeroCheque1' type='text' name='numeroCheque'>");
			form.append("<label>Date encaissement : </label><input id='dateEncaissement"+compteCheque+"' type='text' class='datepicker' size='10' name='dateEncaissement'>");
			form.append("</div>")
			
			form.find("#datePaiement"+compteCheque).datepicker();
			form.find("#dateEncaissement"+compteCheque).datepicker();
			$("#cheques").append(form);
			compteCheque++;
	});
	
	
	$('#retireCheque').click(function(){
		if(compteCheque>=1){
			$('#chequeAjout:last-child').remove();
			compteCheque--;
		}
	});
});