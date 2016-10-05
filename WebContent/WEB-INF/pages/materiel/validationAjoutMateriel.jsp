<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="fr.gemao.view.Pattern"%>

<c:set var="titre" value="Récapitulatif de l'ajout d'un matériel" scope="request" />

<c:import url="/inc/head.inc.jsp" />
<c:import url="/inc/header.inc.jsp" />
<c:import url="/inc/menu.inc.jsp" />

<h1>Récapitulatif de l'ajout d'un matériel</h1>
<table class='table-col-2'>
	<caption>Informations générales</caption>
	<tr>
		<td>Catégorie : </td> 
		<td>${materiel.getCategorie().getLibelleCat()}</td>

	</tr>
	
	<tr>
			<td>Valeur d'achat : </td> 
			<td>
				${materiel.getValeurAchat()}
				<span class="euro"></span>
			</td>
			
	</tr>
	<tr>
			<td>Date d'achat : </td> 
			<td>
			<c:choose>
				<c:when
					test="${materiel.getDateAchat()==null}">
					Non renseignée
				</c:when>
				<c:otherwise>
					${materiel.getDateAchat()}
				</c:otherwise>
			</c:choose>
			</td>
	</tr>
	
	<tr>
		<td>Valeur de réaprovisionnement : </td>
		<td>
			${materiel.getValeurReap()}
			<span class="euro"></span>
		</td>
	</tr>
	
	<tr>
		<td>Fournisseur : </td>
		<td>${materiel.getFournisseur().getNomFournisseur()}</td>
	</tr>
</table>

<table class='table-col-2'>
	<caption>Informations détaillées</caption>
	<tr>
		<td>Désignation : </td> 
		<td>${materiel.getDesignation().getLibelleDesignation()}</td>
	</tr>
	
	<tr>
		<td>Type : </td>
		<td>${materiel.getTypeMat()}</td>
	</tr>
	<tr>
		<td>Etat : </td>
		<td>${materiel.getEtat().getLibelleEtat()}</td>
	</tr>

	<tr>
		<td>Marque : </td>
		<td>${materiel.getMarque().getNomMarque()}</td>
	</tr>
	
	<!-- Ajouter situation -->
	
	<tr>
		<td>Prix unitaire : </td>
		<td>${materiel.getValeurAchat()} <span class="euro"></span></td>
	</tr>
	<tr>
		<td>Numéro de série : </td>
		<td>${materiel.getNumSerie()}</td>
	</tr>

	<tr>
		<td>Déplaçable : </td>
		<td>
			<c:choose>
				<c:when
					test="${materiel.isDeplacable()==true}">
					oui
				</c:when>
				<c:otherwise>
					non
				</c:otherwise>
			</c:choose>
		</td>
	</tr>
	
	<tr>
		<td>Ouvert à la location : </td>
		<td>
			<c:choose>
				<c:when
					test="${materiel.isLouable()==true}">
					oui
				</c:when>
				<c:otherwise>
					non
				</c:otherwise>
			</c:choose>
		</td>
	</tr>
</table>

<c:if test="${materiel.getObservation()} != null">
	<table>
		<caption>Observations</caption>
			<tr>
				<td>${materiel.getObservation()}</td>
			</tr>
	</table>
</c:if>	
<form action="#" method="post">
<p class='align-center'>
	<a href="<c:url value="<%= Pattern.MATERIEL_AJOUT %>" />"><input type="button" value="Retour" />
	</a>
	<input type="submit" value="Valider" />
</p>
</form>
<c:import url="/inc/footer.inc.jsp" />