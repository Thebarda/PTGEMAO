$(document).ready( ->

    $(".supprimer").css("cursor", "pointer")
    
    $(".supprimer").click ->
        
        toastr.options =
          "closeButton": true
          "debug": false
          "newestOnTop": false
          "progressBar": true
          "positionClass": "toast-top-center"
          "preventDuplicates": false
          "onclick": null
          "showDuration": "300"
          "hideDuration": "1000"
          "timeOut": 0
          "extendedTimeOut": 0
          "showEasing": "swing"
          "hideEasing": "linear"
          "showMethod": "fadeIn"
          "hideMethod": "fadeOut"
          "tapToDismiss": false
        
        toastr["info"]("Voulez-vous vraiment supprimer ce planning ?<br /><br /><button type=\"button\" class=\"btn clear redirige\">Oui</button><button type=\"button\" class=\"btn clearToast\">Non</button>")
        $(".clear").data("idPlanning",$(this).closest("tr").attr("id"))
        
        
        
        $(".redirige").click ->
            document.location.href="ListerPlannings?idPlanningDelete=" + $(this).data("idPlanning")
            
        $(".clearToast").click ->
            $("#toast-container").remove()
)