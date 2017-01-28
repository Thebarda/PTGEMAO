$(document).on("click","#enregistrer",function(){
	$("input").removeClass("hasDatepicker");
	$("#tableau>tbody>tr>td>img").remove();
	$.ajax({
	       url : '#',
	       type : 'POST',
	       data : 'recap=' + $("#recap").html()+'&tfc='+$("#tfc").html()
	    });
	 $('#validation').html("Les tableaux ont été enregistrés");
	 $('tr').trigger("click");
});