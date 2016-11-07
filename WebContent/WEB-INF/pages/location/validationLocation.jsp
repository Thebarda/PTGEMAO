<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<c:set var="titre" value="Location interne d'un instrument" scope="request" />

<c:import url="/inc/head.inc.jsp" />

<c:import url="/inc/header.inc.jsp" />
<c:import url="/inc/menu.inc.jsp" />

<form method="post" action="#">
<fieldset>
	<legend>Validation des informations</legend>
	
</fieldset>
<fieldset class='align-center no-border'>
		<input type="submit" value="Valider" />
</fieldset>
</form>

<c:import url="/inc/footer.inc.jsp" />