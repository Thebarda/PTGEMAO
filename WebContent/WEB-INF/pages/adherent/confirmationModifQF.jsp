<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="titre" value="Confirmation de l'ajout d'un adhérent" scope="request" />
<%@ page import="fr.gemao.view.Pattern"%>
<c:import url="/inc/head.inc.jsp" />

<c:import url="/inc/header.inc.jsp" />
<c:import url="/inc/menu.inc.jsp" />

<script src="<c:url value="/js/modifierAdherent.js"/>"></script>
<h1>Confirmation</h1>

<c:if test="${modifOK == true}">
	<p class="offset text-success">L'adhérent <c:out value="${adherent['prenom']}" /> <c:out value="${adherent['nom']}" />
		a maintenant le
		<c:choose>
			<c:when test="${params.getQf_min() > adherent.getQf()}">
				<td class="listQF">Quotient 3.</td>
			</c:when>
			<c:when test="${params.getQf_max() > adherent.getQf()}">
				<td class="listQF">Quotient 2.</td>
			</c:when>
			<c:otherwise>
				<td class="listQF">Quotient 1.</td>
			</c:otherwise>
		</c:choose>
		</p>
</c:if>
<c:if test="${modifOK == false}">
	<p class="offset text-danger">Erreur lors de la modification.</p>
</c:if>
<a class="offset btn" href="<c:url value="<%=Pattern.ADHERENT_LISTER%>"/>">Retour</a>
<c:import url="/inc/footer.inc.jsp" />
