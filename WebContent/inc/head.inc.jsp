<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="fr" class="no-js">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"> 
		<meta name="viewport" content="width=device-width, initial-scale=1.0">

		<%-- Titre --%>
		<title><c:out value="${requestScope.titre}"/></title>
		
		<%-- Icone --%>
		<link rel="shortcut icon" href="<c:url value="/favicon.ico"/>">
		
		<%-- CSS --%>
		<link rel="stylesheet" type="text/css" href="<c:url value="/css/style.css"/>" />
		<link rel="stylesheet" type="text/css" href="<c:url value="/css/normalize.css"/>" />
		<link rel="stylesheet" type="text/css" href="<c:url value="/css/demo.css"/>" />
		<link rel="stylesheet" type="text/css" href="<c:url value="/css/icons.css"/>" />
		<link rel="stylesheet" type="text/css" href="<c:url value="/css/component.css"/>" />
		<link rel="stylesheet" type="text/css" href="<c:url value="/css/datepicker.css"/>" />
		<link rel="stylesheet" type="text/css" href="<c:url value="/css/jquery-ui.css"/>" />
		<link rel="stylesheet" type="text/css" href="<c:url value="/css/planning.css"/>" />
		<link rel="stylesheet" type="text/css" href="<c:url value="/css/toastr.css"/>" />
		<link rel="stylesheet" type="text/css" href="<c:url value="/css/tables.css"/>" />
		<link rel="stylesheet" type="text/css" href="<c:url value="/css/font-awesome/css/font-awesome.min.css"/>" />
		<link rel="stylesheet" type="text/css" href="<c:url value="/css/stylesheetFicheComptable.css"/>" />

		<!-- <link rel="stylesheet" href="<c:url value="/css/buttons.css"/>"> -->
        <link rel="stylesheet" href="<c:url value="/css/cours.css"/>">
        <link rel="stylesheet" href="<c:url value="/css/planning.css"/>">
        


		
		<%-- Scripts --%>
		<script src="<c:url value="/js/modernizr.custom.js"/>" ></script>
		<script src="<c:url value="/js/jquery.min.js"/>"></script>
		<script src="<c:url value="/js/jquery-ui.min.js"/>"></script>
		<script src="<c:url value="/js/datepicker.js"/>"></script>
		<script src="<c:url value="/js/jquery.tablesorter.min.js"/>"></script>
		<script src="<c:url value="/js/jquery.tablesorter.widgets.min.js"/>"></script>
		<script src="<c:url value="/js/script.js"/>" ></script>
		<script src="<c:url value="/js/toastr.js"/>" ></script>
		

	</head>