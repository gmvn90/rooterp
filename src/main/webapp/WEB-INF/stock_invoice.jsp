<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Invoice</title>
<style>
table.sIP {
	margin-left: 30px;
	margin-top: 30px
}

table td {
	padding: 0;
	margin: 0
}

.sStorage {
	margin-top: 10px;
}

div#ip_section,.sStorage {
	width: 500px
}

.sHead td {
	font-weight: bold;
	border-bottom: 2px solid black;
	font-size: 13px;
	text-align: center;
}

.sBody td,.sTotal td {
	font-size: 11px;
	text-align: center;
}

.sTitle:first-child td {
	border-top: none
}

.sFoot td {
	padding: 5px 0 5px 20px;
	background: #EEE !important;
	font-weight: bold;
	font-size: 13px;
}

.sTitle td {
	padding-left: 20px;
	background: grey;
	color: white;
	border: 1px solid black;
	font-size: 13px;
}

.sTotal td:first-child {
	padding-left: 20px
}

.sTotal:last-child td {
	padding-bottom: 15px;
}

.sTotal td {
	padding-bottom: 25px;
	font-weight: bold;
	border-top: 1px solid black;
}

.sIP {
	float: left
}

table.sIP,table.sStorage,.sHead td,.sIP td {
	border-collapse: collapse
}

.sWidth60 {
	width: 60px
}

div#ip_section {
	position: relative;
	height: auto;
	overflow: hidden
}

table.sSummarize {
	float: left;
	margin-top: 30px;
	margin-left: 80px;
}

table.sSummarize td:nth-child(2) {
	text-align: right !important;
	padding-right: 10px
}

table.sStorage tr.sBody:nth-child(even) {
	background-color: #def;
}

table.sStorage tr.sBody:nth-child(even) td {
	background: none
}

.formatNumber,.formatNumber3 {
	text-align: right !important;
	padding-right: 10px !important
}

.xp_grade,.ip_grade {
	text-align: left !important;
	padding-left: 10px
}

.hid {
	display: none !important
}

a.expandCollapse {
	cursor: pointer;
	padding-right: 15px
}

table.sStorage input[type=checkbox] {
	margin: 0
}

tr.sHead.notborder td {
	border-bottom: none !important;
	vertical-align: center
}

table.pi_detail.hid {
	display: none
}

.clear {
	clear: both
}

div#result {
	margin-left: 25px
}
</style>
</head>
<body>
	<jsp:include page="header.jsp">
		<jsp:param name="url" value="35" />
		<jsp:param name="page" value="83" />
	</jsp:include>
	<div id="main_content" style="width: 1400px !important">
		<fieldset class="left border"
			style="width: 350px !important; margin-left: 0px; margin-right: 5px; position: relative">
			<legend>Invoice Ref.</legend>
			<div class="left">
				<ul class="ref_list filter" id="type_list"
					style="margin-top: 33px; margin-left: 9px; padding-left: 10px; width: 100px !important; height: 100px !important">
					<li class="chosen" id="type_-1">All</li>
					<li id="type_0">Stock</li>
					<li id="type_1">Shipping</li>
					<li id="type_2">Processing</li>
				</ul>
			</div>
			<div class="right">
				<input type="text" id="ref_filtertext" class="text_filter_child"
					value="" style="width: 340px; position: absolute; left: 18px" />
				<ul class="ref_list filter" id="inv_list"
					style="margin-top: 33px; margin-left: 9px; padding-left: 10px; width: 157px !important">
				</ul>
			</div>
			<div class="clear"></div>
			<div class="right" style="margin-top: 15px">
				<input type="button" id="btn_delete" value="Delete" />
			</div>
		</fieldset>
		<div id="main_container" class="left"
			style='width: 960px !important; margin-left: 20px; margin-top: 20px'>
			<div
				style="width: 100%; height: 50px; border-bottom: 1px solid #d5d5d5; padding: 5px">
				<span> Client <select id="client">
						<!-- <option value="-1"></option> -->
				</select>
				</span> <span> From Date <input type="text" class="date_picker_text"
					id="from_date" readonly />
				</span> <span> To Date <input type="text" class="date_picker_text"
					id="to_date" readonly />
				</span> <span> Type <select id="type">
						<option value="0" selected>Stock</option>
						<option value="1">Shipping</option>
						<option value="2">Processing</option>
				</select>
				</span> <input type="button" class="right" id="btnSaveInvoice"
					value="Create Invoice" style="display: none" /> <input
					type="button" class="right" id="btnGetInvoice" value="Get Invoice"
					style="display: none" />
			</div>
			<div id="result"></div>
		</div>
	</div>
	<jsp:include page="footer.jsp" />
	<script>
	var invoiceType = "";
	var total = 0;
	var count = 0;
	$(document).ready(function() {
		//load ref list
		loadRefList();
	    generateSelection("#client", "Client", "-1", {
		all : true,
		action : "getClientListInSystem",
		type : "select",
		prefix : "client_",
		allText : "",
		async : true
	    }, function() {
		$("#btnGetInvoice").css("display", "");
		$("#btnSaveInvoice").css("display", "");
	    });
	}).on(
		"click",
		"#btnGetInvoice",
		function() {
		    var client = $("#client").val();
		    if (client != -1) {
			$.sendRequest(
				{
				    action : "getStockInvoice",
				    data : {
					client : client,
					fromDate : $("#from_date").val(),
					toDate : $("#to_date").val(),
					type : $("#type").val()
				    }
				},
				function(msg) {
				    console.log(msg);
				    try {
					var data = $.parseJSON(msg);
					var type = $("#type").val();
					var file;
					if (type == "0" || type == "1") {
					    file = getAbsolutePath()
						    + "/js/templates/accounting/stock.html";
					}
					if (type == "2") {
					    file = getAbsolutePath()
						    + "/js/templates/accounting/processInvoice.html";
					}
					//apply templates
					$.when($.get(file)).done(
						function(tmplData) {
						    var tmpl = $.templates(tmplData);
						    $("#result").html(
							    tmpl.render(data));
						    if (type == "2") {
							    var invoices = [];
								$(".piRow").each(function() {
								    var id = $(this).attr("id").split("_")[1];
								    var ref = $.trim($("#piRow_" + id + " td:first-child").text());
								    var date = $.trim($("#piRow_" + id + " td:nth-child(2)").text());
								    var invoice = {};
								    invoice["id"] = id;
								    invoice["refNumber"] = ref;
								    invoice["date"] = date;
								    //load detail
								    $.sendRequest(
									    {
											action : "getPiDetail",
											data : {
											    id : id,
											    client : $("#client").val()
											},
									    },
									    function(msg) {
											console.log(msg);
											try {
											    var _data = JSON.parse(msg);
											    var _file = getAbsolutePath()
												    + "/js/templates/accounting/processDetail.html";
											    invoice["detail"] = _data;
											    $.when($.get(_file)).done(function(tmplData) {
													var _tmpl = $.templates(tmplData);
													$("#pidetail_" + id).html(_tmpl.render(_data));
													calculateProcess(id);
												 });
											} catch (e) {
											    $.error(e);
											}
										return true;
									    });
								    invoices.push(invoice);
								    
								});
								$.data(document.body,"invoice", invoices);
						    }else{
								$.data(document.body,"invoice", data);
							}
							invoiceType = type;
						    $.formatNumber();
						});
					return true;
				    } catch (e) {
					$.error(e);
				    }
				});
		    } else {
			$.warning("Please Choose Client");
		    }
		}).on("click", ".expandCollapse", function() {
	    var id = $(this).attr("id").split("_")[1];
	    console.log(id);
	    if ($(this).hasClass("mpcth-icon-minus")) {
		$(this).toggleClass("mpcth-icon-minus");
		$(this).addClass("mpcth-icon-plus");
		$("#pidetail_" + id).addClass("hid");
	    } else if ($(this).hasClass("mpcth-icon-plus")) {
		$(this).toggleClass("mpcth-icon-plus");
		$(this).addClass("mpcth-icon-minus");
		$("#pidetail_" + id).removeClass("hid");
	    }
	}).on("click", "#btnSaveInvoice", function() {
	    //save invoice
	    $.sendRequest({
		action : "saveInvoice",
		data : {data: JSON.stringify($.data(document.body,"invoice")), type: invoiceType}
	    },function(msg){
			if (msg == 0){
			    loadRefList(false);
				$.success("New Invoice Has Been Added");
			}else{
				$.error("Cannot Create New Invoice");
			}
		});
	}).on("keyup","#ref_filtertext", function(){
	    loadRefList();
	}).on("click","#type_list li", function(){
	    loadRefList();
	}).on("click","#inv_list li", function(){
		var id = $(this).attr("id").split("_")[1];
		$.sendRequest({
			action: "getInvoice",
			data: {id: id},
			optional: {url_type: "json"}
		}, function(msg){
			console.log(msg);
			try{
				var type = msg.type;
				var file = "";
				var content = JSON.parse(msg.content);
				console.log(content);
				switch(type){
				case 2: {
					file = getAbsolutePath() + "/js/templates/accounting/processing.html";
					size = content.length;
					var data = {
						size: size,
						pis: content
					};
					parseProcess(file,data);
				}break;
				}
				return true;
			}catch(e){
				$.error(e);
			}
		});
	});

	function calculateProcess(id){
	    var cost = 0;
		var tons = 0;
		$(".ip_cost_" + id).each(function() {
			    cost = cost - getFloatValueFromText($(this).text());
			});
		$(".xp_cost_" + id).each(function() {
			    cost = parseFloat(cost) + getFloatValueFromText($(this).text());
			});
		$(".xp_tons_" + id).each(function() {
			    tons = parseFloat(tons) + getFloatValueFromText($(this).text());
			});
		var grand_price = cost * tons;
		$("#total_pi_tons_" + id).text(tons.toFixed(3));
		$("#total_pi_costs_" + id).text(cost.toFixed(2));
		$("#grand_pi_costs_" + id).text(grand_price.toFixed(2));
		total += grand_price;
		count++;
		if (count == $("#size").val()) {
		    $("#total_invoice").text(total.toFixed(2));
		    total = 0;
		    count = 0;
		}
		$.formatNumber();
	}
	
	function parseProcess(file, data){
		$.when($.get(file)).done(function(tmplData){
			var tmpl = $.templates(tmplData);
			$("#result").html(tmpl.render(data));
			$(".piRow").each(function() {
			    var id = $(this).attr("id").split("_")[1];
			    calculateProcess(id);
			});
		});
	}
	
	function loadRefList(f){
		var searchTerm = $("#ref_filtertext").val();
		var type = $("#type_list li.chosen").attr("id").split("_")[1];
		$.sendRequest({
			action: "getInvoiceRefList",
			data: {searchTerm: searchTerm, type: type}
		},function(msg){
			try{
				console.log(msg);
				var data = JSON.parse(msg);
				var file = getAbsolutePath()
				    + "/js/templates/accounting/li.html";
				$.when($.get(file).done(function(tmplData){
					var tmpl = $.templates(tmplData);
					$("#inv_list").html(tmpl.render(data.data));
				}));  
				if (f == undefined) f = true;
				return f;  
			}catch(e){
				$.error(e);
			}
		});
	}

	function getFloatValueFromText(txt){
	    if (txt.charAt(0) == '(' && txt.charAt(txt.length - 1) == ')') {
			txt = txt.replace('(', '');
			txt = txt.replace(')', '');
	        txt = '-' + txt;
	    }else if (txt == '-'){
			return 0;
		}
	    return parseFloat(txt);
	}
    </script>
</body>
</html>