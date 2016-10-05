<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="fr.gemao.view.Pattern"%>

<c:set var="titre" value="Location d'un instrument" scope="request" />

<c:import url="/inc/head.inc.jsp" />

<c:import url="/inc/header.inc.jsp" />
<c:import url="/inc/menu.inc.jsp" />

	<h1>Location d'instrument</h1>

	<c:choose >
		<c:when test="${empty resultat}">
			<form id="location" method="post" action="#">
				<fieldset>
					<legend>Instrument</legend>
					<c:choose>
						<c:when test="${!empty requestScope.listeCategorie}">
							<div>
								<label for="categorie">Catégorie :</label>
								<select name="categorie">
									<c:forEach items="${requestScope.listeCategorie}" var="categorie">
										<option value="<c:out value="${categorie['idCategorie']}" />"><c:out
												value="${categorie['libelleCat']}" /></option>
									</c:forEach>
								</select>
							</div>
						</c:when>
						<c:otherwise>
							<div><span class='text-label'>Catégorie : </span><c:out value="${nomCategorie}" /></div>
						</c:otherwise>
					</c:choose>
					
					<c:choose>
						<c:when test="${!empty requestScope.listeDesignation}">
							<div>
								<label for="designation">Désignation :</label>
								<select name="designation">
								<c:forEach items="${requestScope.listeDesignation}" var="designation">
									<option value="<c:out value="${designation['idDesignation']}" />"><c:out
											value="${designation['libelleDesignation']}" /></option>
								</c:forEach>
								</select>
							</div>
						</c:when>
						<c:when test="${!empty requestScope.nomDesignation}">
							<div><span class='text-label'>Désignation : </span><c:out value="${nomDesignation}" /></div>
						</c:when>
					</c:choose>
					
				</fieldset>
				<c:if test="${!empty requestScope.listeAdherent}">
					<fieldset>
						<legend>Adhérent</legend>
						<div>
							<label for="adherent">Nom :</label>
							<select name="adherent">
								<c:forEach items="${requestScope.listeAdherent}" var="adherent">
									<option value="<c:out value="${adherent['idPersonne']}" />">
										<c:out value="${adherent['nom']} ${adherent['prenom']}" />
									</option>
								</c:forEach>
							</select>
						</div>
					</fieldset>
					
					<fieldset>
						<legend>Dates</legend>
						
						<div>
							<label for="datedeb">Date d'emprunt :</label>
							<input class='datepicker' type="text" name="datedeb" required />
						</div>
						
						<div>
							<label for="datefin">Date de retour :</label>
							<input class='datepicker' type="text" name="datefin" required />
						</div>
					</fieldset>
				</c:if>
				<fieldset class='align-center no-border'>
					<input type="submit" value="Valider" />
				</fieldset>
			</form>
		</c:when>
		<c:otherwise>
			<p><c:out value="${resultat}"/></p>
		</c:otherwise>
	</c:choose>
	

<c:import url="/inc/footer.inc.jsp" />