	  var sxglobrad;
	 function show(rad, nr, getstr) {
		  sxglobrad = rad;
		if ($("a[name='a"+rad+"']").val() == "Dölj") {
			$("a[name='a"+rad+"']").val("Visa").html("Visa");
			$("#f" + rad).slideUp("fast", function() { $(this).html(""); })
			$("#tr"+rad).removeClass("trhighlite");
		} else {
			$("a[name='a"+rad+"']").val("Dölj").html("Dölj");
			$("#tr"+rad).addClass("trhighlite");;
		  $.get(getstr, function(data) {$("#f" + sxglobrad).append(data).slideDown("fast");});
		}	
	}
		function showfaktura(rad,nr) {
			 show(rad,nr, "?get=faktura&faktnr="+nr);
		 }
		function showorder(rad,nr) {
			 show(rad,nr, "?get=order&ordernr="+nr);
		 }
