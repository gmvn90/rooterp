<%-- 
    Document   : print_pallet
    Created on : Sep 10, 2013, 5:30:38 PM
    Author     : kiendn
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Print Pallet</title>
</head>
<body>
	<jsp:include page="header.jsp">
		<jsp:param name="url" value="23" />
		<jsp:param name="page" value="25" />
	</jsp:include>
	<style>
#pallet_form ul li {
	display: inline;
}

#pallet_form ul li label span {
	font-size: 13px
}
</style>
	<form id="pallet_form">
		<div
			style="width: 900px ! important; padding: 15px; background: none repeat scroll 0% 0% white; height: auto; overflow: hidden; min-height: 700px;"
			class="container_16 border" id="wrapper">
			<h3>Print Pallet</h3>
			<hr />
			<p id="current_pallet"></p>
			<ul>
				<li><label><span>From Pallet</span> <input type="text"
						id="pallet_from" class="numberOnly" /></label></li>
				<li><label><span>To Pallet</span> <input type="text"
						id="pallet_to" class="numberOnly" /> <input type="button"
						id="btn_generate" value="Generate" /> <input type="button"
						id="btn_print" value="Print" /> </label></li>
			</ul>
			<hr />
			<div id="barcode" style="width: 100%"></div>
		</div>
	</form>
	<jsp:include page="footer.jsp"></jsp:include>
	<script>
            $(document).on("click", "#btn_generate", function() {
                $("#barcode").html("");
                var from = $("#pallet_from").val();
                var to = $("#pallet_to").val();
                var count = 0;
                var row = 0;
                var i = parseInt(from);
                while (i <= parseInt(to)) {
                    if (row == 0 || (row % 10) == 0) {
                        count++;
                        $("#barcode").append("<div id='barcode_" + count + "' class='printing_page' style='width: 810px; height: 1190px; border: 1px solid gray'></div>");
                    }
                    $("#barcode_" + count).append("<ul style='list-style:none; clear:both;'>" +
                            "<li id='" + row + "1' style='float:left;padding: 15px !important;margin-top: 30px'></li><li id='" + row + "2'  style='float:right;padding: 15px !important;margin-top: 30px'></li>"
                            + "</ul>");
                    sendJsonRequest("","check_pallet",{name:i},5,[
                        {name:"setBarcode",params:[row]}
                    ],true,["validate"]);
                    row++;
                    i++;
                }
            }).on("click", "#btn_print", function() {
                var options = {mode: "popup", popClose: true};
                //$("#barcode").printElement({pageTitle: "SWCBD"});
                $("#barcode").printArea(options);
            }).ready(function(){
              //load current_pallet
                sendJsonRequest("current_pallet","get_current_pallet",{},5,[
                    {name:"getCurrentPallet",params:[]}
                ],true,["current_pallet"]);
            });
            function getCurrentPallet(){
                $("#current_pallet").text("Current Pallet: " + $.data(document.body,"current_pallet").ref_number);
            }
            function setBarcode(row){
                if ($.data(document.body,"validate").name != ""){
                    $("#" + row + "1").barcode($.data(document.body,"validate").name,"code128",{output: "css"});
                    $("#" + row + "2").barcode($.data(document.body,"validate").name,"code128",{output: "css"});
                }
            }
        </script>
    </body>
</html>
