<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript">

//Fonction qui permet l'autocompletion du champs codePostal et commune.
//selecteurFamille : selecteur jquery du champ famille
function autocompletionAdherent(selecteurAdherent){
	$(function() {
		var availableTags = ${requestScope.auto_adherents};
		$(selecteurAdherent).autocomplete({
			source : availableTags
		});
	});
}

</script>>