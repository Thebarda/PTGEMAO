<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript">

//Fonction qui permet l'autocompletion du champs codePostal et commune.
//selecteurCommune : selecteur jquery du champ commune
//selecteurCodePostal : selecteur jquery du champ codePostal
function autocompletionCommuneCodePostal(selecteurCommune, selecteurCodePostal){
	$(function() {
		var availableTags = ${requestScope.listNomCommune};
		$(selecteurCommune).autocomplete({
			source : availableTags,
			change: function(event, ui){
				if(ui.item != null){
					remplirCodePostal(selecteurCommune, selecteurCodePostal, ui.item.value);
				}
			},
			select: function(event, ui){
				if(ui.item != null){
					remplirCodePostal(selecteurCommune, selecteurCodePostal, ui.item.value);
				}
			}
		});
		
		
		$(selecteurCommune).on("change", {selecteurCommune: selecteurCommune, selecteurCodePostal: selecteurCodePostal}, remplirCodePostalHandler);
		$(selecteurCommune).on("input", {selecteurCommune: selecteurCommune, selecteurCodePostal: selecteurCodePostal}, remplirCodePostalHandler);
		
		$(selecteurCodePostal).on("change", {selecteurCodePostal: selecteurCodePostal, selecteurCommune: selecteurCommune}, remplirCommuneHandler);
		$(selecteurCodePostal).on("input", {selecteurCodePostal: selecteurCodePostal, selecteurCommune: selecteurCommune}, remplirCommuneHandler);
	});


	function remplirCodePostal(selecteurCommune, selecteurCodePostal, value){
		var dicoCommune = ${requestScope.dicoCommune};
		for(c in dicoCommune){
			if(c == value){
				$(selecteurCodePostal).val(dicoCommune[c]);
			}
		}
	}

	function remplirCodePostalHandler(event){
		var dicoCommune = ${requestScope.dicoCommune};
		for(c in dicoCommune){
			if(c == $(event.data.selecteurCommune).val()){
				$(event.data.selecteurCodePostal).val(dicoCommune[c]);
			}
		}
	}

	function remplirCommuneHandler(event){
		var dicoCommune = ${requestScope.dicoCommune};
		console.log($(event.data.selecteurCodePostal).val());
		for(c in dicoCommune){
			if(dicoCommune[c] == $(event.data.selecteurCodePostal).val()){
				$(event.data.selecteurCommune).val(c);
			}
		}
	}
}

</script>