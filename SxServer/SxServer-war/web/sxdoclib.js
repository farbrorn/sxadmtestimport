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
		function showutlev2(rad, ordernr) {
			 show(rad,0, { get: "utlev2", ordernr: ordernr });
		 }

var cur;
var next;
var prev;
function loadsokres() {
	$("#divdoclist").load("?" + $("#sokform").serialize(), function() { updateNextPrev(); } );	
}
	
function updateNextPrev() {
	cur = $("input[@name=currentpage]").val();
	 next = $("input[@name=nextpage]").val();
	 prev = $("input[@name=previouspage]").val();
	 if (prev < cur) { $("#getpreviouspage").html("Föregående"); } else {$("#getpreviouspage").html("");}
	 if (next > cur) { $("#getnextpage").html("Nästa"); } else {$("#getnextpage").html("");}
	 $("#sida").html(cur);
	 $("input[@name=tidat]").val($("input[@name=datatidat]").val());
	 $("input[@name=frdat]").val($("input[@name=datafrdat]").val());
	 
}

function loadNextPage() {
	$("input[@name=page]").val(next);
	loadsokres();
}
function loadPreviousPage() {
	$("input[@name=page]").val(prev);
	loadsokres();
}
function toggleD1() {
	$("#d1").toggle();
}
