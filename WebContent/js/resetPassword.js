function envoyerForm() {
	// Appelé lors du changement dans la liste déroulante
	var elem = document.getElementById("id");
	if (elem)
		elem.value = '';
	document.getElementById("form").submit();
}

function recupMdp() {
	var answer = document.getElementById("password").value;

	if (answer && answer != "") {
		dialog.dialog("close");
		document.getElementById("form").submit();
	}
}

dialog = $("#dialog-form").dialog({
	autoOpen : false,
	height : 300,
	width : 350,
	modal : true,
	buttons : {
		"Valider" : recupMdp,
		Annuler : function() {
			dialog.dialog("close");
		}
	},
	close : function() {
		// allFields.removeClass( "ui-state-error" );
	}
});
dialog.parent().appendTo($("#form"));

function dispatchEvents() {
	// liste déroulante
	var elem = document.getElementById("idPersonne");
	if (elem)
		ajouteEvent(elem, "change", envoyerForm, false);

	// bouton de validation
	elem = document.getElementById("Valider");
	if (elem) {
		ajouteEvent(elem, "click", function() {
			dialog.dialog("open");
		}, false);
	}
}

window.onload = function() {
	dispatchEvents();
};
