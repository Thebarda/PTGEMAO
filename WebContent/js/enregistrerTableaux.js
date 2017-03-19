$(document).on("click","#enregistrer",function(){
	$("input").removeClass("hasDatepicker");
	$("#tableau>tbody>tr>td>img").remove();
	var tfc= "";
	$("table>tbody>.tfc").each(function(){
		tfc+="<tr class='tfc'>"+$(this).html()+"</tr>";
	});
	$.ajax({
	       url : '#',
	       type : 'POST',
	       data : 'recap=' + $("#recap").html()+'&tfc='+tfc
	       
	    });
	 $('#validation').html("Les tableaux ont été enregistrés");
	 $('tr').trigger("click");
});