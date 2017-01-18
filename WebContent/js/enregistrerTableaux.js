$("#enregistrer").click(function(){
	 $.ajax({
	       url : '#',
	       type : 'POST',
	       data : 'recap=' + $("#recap").html()+'&tfc='+$("#tfc").html()
	    });
	 $('#validation').html("Les tableaux ont été enregistrés");
});