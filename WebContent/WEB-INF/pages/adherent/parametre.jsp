<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="fr.gemao.view.Pattern"%>

<c:set var="titre" value="Paramètres" scope="request" />

<c:import url="/inc/head.inc.jsp" />
<c:import url="/inc/header.inc.jsp" />
<c:import url="/inc/menu.inc.jsp" />

<h1>Paramètres</h1>
<form action="<c:url value="<%= Pattern.ADHERENT_PARAMETRE %>" />" id="formParametre" method="post">
	<fieldset>
		<legend>Allocations familiales</legend>
		<div>
			<label for="alloc2">2 enfants </label>
			<input type="text"
			id="alloc2" name="alloc2" required="required"
			pattern="[0-9]*[0-9.,][0-9]*" value="<c:out value="${alloc2}"/>" ><span class='euro'></span> <span>${form.erreurs['alloc2']}</span>
		</div>
		<div>
			<label for="alloc3">3 enfants </label>
			<input type="text"
			id="alloc3" name="alloc3" required="required"
			pattern="[0-9]*[0-9.,][0-9]*" value="<c:out value="${alloc3}"/>"><span class='euro'></span><span>${form.erreurs['alloc3']}</span>
		</div>
		<div>
			<label for="alloc4">4 enfants </label>
			<input type="text"
			id="alloc4" name="alloc4" required="required"
			pattern="[0-9]*[0-9.,][0-9]*"   value="<c:out value="${alloc4}"/>"><span class='euro'></span> <span>${form.erreurs['alloc4']}</span>
		</div>
		<div>
			<label for="alloc5">5 enfants et + </label>
			<input
			type="text" id="alloc5" name="alloc5" required="required"
			pattern="[0-9]*[0-9.,][0-9]*"   value="<c:out value="${alloc5}"/>"><span class='euro'></span><span>${form.erreurs['alloc5']}</span>
		</div>
	</fieldset>
	<fieldset>
		<legend>Quotient familial</legend>
		<div>
			<label for="qMin">Quotient minimal </label>
			<input type="text"
			id="qfMin" name="qfMin" required="required"
			pattern="[0-9]*[0-9.,][0-9]*"   value="<c:out value="${qf_min}"/>"><span class='euro'></span><span>${form.erreurs['qfMin']}</span>
		</div>
		<div>
			<label for="qMax">Quotient maximal </label>
			<input
			type="text" id="qfMax" name="qfMax" required="required"
			pattern="[0-9]*[0-9.,][0-9]*"  value="<c:out value="${qf_max}"/>"><span class='euro'></span><span>${form.erreurs['qfMax']}</span>
		</div>
	</fieldset>
	<fieldset class='align-center no-border'>
		<div>
			<input type="submit" value="Modifier">
		</div>
	</fieldset>
</form>
<p class='align-center no-border'>${form.erreurs['Parametre']}</p>
<p class='align-center no-border text-success'>${form.resultat}</p>


<c:import url="/inc/footer.inc.jsp" />