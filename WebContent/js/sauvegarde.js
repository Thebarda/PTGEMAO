$(document).on("click","#sauvegarde",function(){
	$.ajax({
	       url : '#',
	       type : 'GET',
	       data : 'sauvegarde=sauvegarde'
	    });
});