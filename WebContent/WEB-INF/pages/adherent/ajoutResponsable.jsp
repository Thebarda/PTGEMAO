<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="titre" value="Ajout d'un adhérent" scope="request" />

<c:import url="/inc/head.inc.jsp" />

<c:import url="/inc/header.inc.jsp" />
<c:import url="/inc/menu.inc.jsp" />
<h1>Ajout d'un responsable</h1>
<p class='offset'>L'adhérent est mineur, veuillez indiquer un
	responsable.</p>
<form action="#" method="post">
	<fieldset>
		<div>
			<label for="nom" class='required'>Nom </label> <input type="text"
				name="nom" required="required" />
		</div>
		<div>
			<label for="prenom" class='required'>Prénom </label> <input
				type="text" name="prenom" required="required" />
		</div>
		<div>
			<label for="tel" class='required'>Téléphone </label> <input
				type="text" name="tel" pattern="[0][1-9][0-9]{8}"
				required="required" maxlength="10" autocomplete="on" />
		</div>
		<div>
			<label for="email">E-mail </label> <input type="text" name="email"
				autocomplete="on" />
		</div>
	</fieldset>
	<fieldset class='align-center no-border'>
		<p class="oblig">* Champs obligatoires</p>
		<input type="submit" class="btn" value="Suivant" />
	</fieldset>
</form>
<c:import url="/inc/footer.inc.jsp" />