$(function(){
	$(".icon-pen").on("click",modifDiscipline);
});

function modifDiscipline(e){
	var form = e.target.parentNode;
	$(form).children().attr("readonly",false);
	$(e.target).toggle();
	$(form).children(".btModif").toggle();
}