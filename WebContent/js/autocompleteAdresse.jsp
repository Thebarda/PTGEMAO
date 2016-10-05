<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript">

//Fonction qui permet l'autocompletion du champs codePostal et commune.
//selecteurCommune : selecteur jquery du champ commune
//selecteurCodePostal : selecteur jquery du champ codePostal
function autocompletionAdresse(selecteurRue, selecteurInfoCompl){
	$(function() {
		var availableRue = ${requestScope.auto_list_rue};
		$(selecteurRue).autocomplete({
			source : availableRue
		});
		
		var availableInfoCompl = ${requestScope.auto_list_compl};
		$(selecteurInfoCompl).autocomplete({
			source : availableInfoCompl
		});
	});

	
}

</script>