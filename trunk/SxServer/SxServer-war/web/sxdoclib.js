	  var sxglobrad;
	 function show(rad, nr, parameterdata) {
		  sxglobrad = rad;
		if ($("a[name='a"+rad+"']").val() == "Dölj") {
			$("a[name='a"+rad+"']").val("Visa").html("Visa");
			$("#f" + rad).slideUp("fast", function() { $(this).html(""); })
			$("#tr"+rad).removeClass("trhighlite");
		} else {
			$("a[name='a"+rad+"']").val("Dölj").html("Dölj");
			$("#tr"+rad).addClass("trhighlite");;
		  $.get("?", parameterdata , function(data) {$("#f" + sxglobrad).append(data).slideDown("fast");});
		}	
	}
		function showfaktura(rad,nr) {
			 show(rad,nr, { get: "faktura", faktnr: nr });
		 }
		function showorder(rad,nr) {
			 show(rad,nr, { get: "order", ordernr: nr });
		 }
		function showstatfaktura12(rad, frdat, tidat, artnr) {
			 show(rad,0, { get: "statfaktura12", frdat: frdat, tidat: tidat, artnr: artnr });
		 }
