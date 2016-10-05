$(function () {
	$(".listQte").css('display','none');
	$(".listDateAchat").css('display','none');
	$(".listFour").css('display','none');
	$(".listMarque").css('display','none');
	$(".listEtat").css('display','none');
	$(".listNumSer").css('display','none');
	$(".listDepl").css('display','none');
	$(".listOuvLoc").css('display','none');
	$(".listType").css('display', 'none');
	$(':checkbox:checked').removeAttr('checked');
});

function affQte(){
	$(".listQte").toggle();
}

function affDateAchat(){
	$(".listDateAchat").toggle();
}

function affFournisseur(){
	$(".listFour").toggle();
}

function affMarque(){
	$(".listMarque").toggle();
}

function affEtat(){
	$(".listEtat").toggle();
}

function affNumSer(){
	$(".listNumSer").toggle();
}

function affDeplacable(){
	$(".listDepl").toggle();
}

function affOuvLoc(){
	$(".listOuvLoc").toggle();
}

function affType(){
	$(".listType").toggle();
}