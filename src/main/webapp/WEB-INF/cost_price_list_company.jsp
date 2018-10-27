<%--
  Created by IntelliJ IDEA.
  User: gmvn
  Date: 9/11/16
  Time: 11:59 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Cost price lists</title>
    <style>

    </style>
</head>
<body>
<jsp:include page="header.jsp">
    <jsp:param name="url" value="42"/>
    <jsp:param name="page" value="93"/>
</jsp:include>
<c:set var="initial_id" value="${initial_id}"></c:set>

<div id="main_content"
     style="width: 700px ! important; padding: 15px; background: none repeat scroll 0% 0% white; height: auto; overflow: hidden"
     class="container_16 border" id="wrapper">
    <div style="width: 100%; overflow: hidden; height: auto; border-bottom: 1px solid #B8C2CC; padding-bottom: 10px">
        <form method="post" action="cost_price_list_company.htm">
            <table>
                <tr>
                    <td>
                        <select name="id">
                            <c:forEach items="${companies}" var="item">
                                <option value="${item.id}">${item.name}</option>
                            </c:forEach>
                        </select>
                    </td>
                    <td>
                        <input name="margin" value="0">
                    </td>
                    <td>
<c:if test="${permissonsMap.get('perm_149')}">
                        <input type="submit" value="Submit">
    </c:if>
    </td>
                    <td></td>
                    <td></td>
                </tr>
            </table>
        </form>
        <table id='costPriceCompanyList' class="display nowrap" style='width: 100%'>

            <thead>
            <tr>
                <th></th>
                <th>Client</th>
                <th>Factory Cost</th>
                <th>Storage Costs</th>
                <th>Cost To FOB</th>
            </tr>
            </thead>
            <tbody>

            </tbody>
        </table>
    </div>
</div>
<script type="text/javascript">
    var oTable, tote, axxx;
    $(document).ready(function () {

        $.sendRequest({
            action: "getAllCompanyCostMap",
            optional: {url_type: "json"}
        }, function(msg) {
            console.log(msg);
            var data = [];
            for ( var i=0 ; i<msg.length ; i++ ) {
                data.push([i+1, msg[i].name, msg[i].costs.FactoryCosts, msg[i].costs.StorageCosts, msg[i].costs.CostsToFOB,msg[i].id]);
            }
            $('#costPriceCompanyList').DataTable( {
                "bServerSide": false,
                "aaData": data,
                "bProcessing": true,
                "aoColumnDefs": [
                    {"sClass": "formatNumber", "aTargets": [2,3,4]},
                    {"sWidth": "150px", "aTargets": [1]},
                    {"sWidth": "50px", "aTargets": [2, 3, 4]},
                    {"sWidth": "10px", "aTargets": [0]},
                ],
                "fnCreatedRow": function( nRow, aData, iDataIndex ) {
                    // Bold the grade for all 'A' grade browsers
                    $(nRow).addClass("data_row");
                    $(nRow).attr("data-id",aData[5]);
                },
                "fnDrawCallback": function(oSettings, json) {
                    //alert("drawback");
                    $("input[type=button]").button();
                    $("td.formatNumber").each(function() {
                        var vl = accounting.toFixed(parseFloat($(this).text()), 2);
                        $(this).text(accounting.formatMoney(vl, "", 2));
                    });
                    $("tbody tr[class*=data_row]").each(function() {
                        $(this).attr('name', 'perm_11');
                        $(this).attr("title", "Double Click To View Detail");
                    });
                    $("tr[class*=data_row]").auth({action:"dblclick"});
                    //show tool tip
                    $('.masterTooltip').hover(function() {
                        // Hover over code
                        var title = $(this).attr('title');
                        $(this).data('tipText', title).removeAttr('title');
                        $('<p class="tooltip"></p>')
                                .text(title)
                                .appendTo('body')
                                .fadeIn('slow');
                    }, function() {
                        // Hover out code
                        $(this).attr('title', $(this).data('tipText'));
                        $('.tooltip').remove();
                    }).mousemove(function(e) {
                        var mousex = e.pageX + 20; //Get X coordinates
                        var mousey = e.pageY + 10; //Get Y coordinates
                        $('.tooltip')
                                .css({
                                    top: mousey,
                                    left: mousex
                                })
                    });
                },
                "sPaginationType": "full_numbers",
                "sDom": 'Rlfrtip',
                "oColVis": {
                    "fnLabel": function(index, title, th) {
                        if (title != "") {
                            return (index + 1) + '. ' + title;
                        }
                    }
                }
            } );
            console.log(data);
            return true;
        });

    }).on("dblclick", "tr.data_row", function() {
        var id = $(this).data("id");
        window.location = getAbsolutePath() + "/cost_price_input.htm?company_id=" + id;
    });
</script>
</body>
</html>
