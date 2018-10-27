
<%--
    Document   : stock_listview
    Created on : Dec 6, 2013, 2:19:58 PM
    Author     : kiendn
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="f" uri="http://swcommodities.com/formatFunctions" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Daily Basic</title>
    <style>
        .styleTotal td {
            border-top: 1px solid #000;
        }

        .bolder {
            font-size: 12px;
        }

        #btn_add_new, #btn_delete {
            min-width: 50px !important
        }

        table.instructionDetailStandard {
            border-collapse: collapse;
        }

        .title {
            font-weight: normal !important;
            font-size: 13px !important;
            padding: 1px !important;
            text-align: left;
        }

        .subTitle {
            font-weight: bold;
            padding-top: 2px !important;
            font-size: 13px !important;
            text-align: left;
        }

        .field_value {
            font-weight: bold;
            font-size: 13px !important;
            padding: 1px !important;
            text-align: left;
        }

        .underline-bottom {
            border-bottom: 1px solid black;
        }

        table.instructionDetailStandard tr td {
            padding: 5px !important;
        }
        table.instructionDetailStandard tr th {
            padding: 5px !important;
        }


    </style>
</head>
<body>
<jsp:include page="header.jsp">
    <jsp:param name="url" value="74"/>
    <jsp:param name="page" value="123"/>
</jsp:include>
<div style="width: 1200px ! important; padding: 15px; background: none repeat scroll 0% 0% white; height: auto; overflow: hidden"
     class="container_16 border" id="wrapper">

    <div id="detail_content">
    <div style="width: 1200px !important; min-height: 80px; height: auto; overflow: hidden;margin-right: 9px;"
         class="">
        <table class="instructionDetailStandard" style="padding: 20px; width: 100%">

            <%--Exchange section--%>
            <tr>
                <td class="" colspan="6">
                    <fieldset>
                        <legend>Exchange</legend>
                        <table class="" style="width: 100%; font-size: 9pt">
                            <thead>
                            <tr>
                                <th></th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr>
                                <td></td>
                            </tr>
                            </tbody>
                        </table>
                    </fieldset>
                </td>
            </tr>
            <!-- end Exchange Section -->

                <%--Daily basic section Grade 	Last Ct. Diff 	Last Ct. Date 	Trade Dept Diff 	Authorized By 	--%>
                <tr>
                    <td class="" colspan="6">
                        <fieldset>
                            <legend>Grade mapping</legend>
                            <table class="" style="width: 100%; font-size: 9pt">
                                <thead>
                                    <tr>
                                        <th>Mill Grade</th>
                                        <th>Trade grade</th>
                                        <th></th>
                                    </tr>
                                    
                                </thead>
        
                                <tbody>
                                    <c:forEach items="${gradeConverters}" var="item">
                                    <form method="post" action="update-fob-mapping.htm">
                                        <tr>
                                            <td>${item.gradeMaster.name}<input type="hidden" value="${item.gradeMaster.id}" name="millId" /></td>
                                            <td>
                                                <select class="trade-selection">
                                                    <option value="">None</option>
                                                    <c:forEach items="${trades}" var="tradeItem" >
                                                        <option data-mill="${item.tradeId}" value="${tradeItem.id}" <c:if test="${not empty tradeItem.id  && tradeItem.id == item.tradeId}">selected</c:if> >${tradeItem.value}</option>
                                                    </c:forEach>
                                                </select>
                                                <input type="hidden" value="${item.tradeId}" name="tradeId" class="tradeId" />
                                                <input type="submit" value="Save" />
                                            </td>
                                            <td></td>
                                        </tr>
                                    </form>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </fieldset>
                    </td>
                </tr>
                <!-- end Daily basic Section -->

               
        </table>
    </div>
    </div>

</div>
                <style>
                    .before-trade-load {
                        display: none;
                    }
                </style>
<script>
    $(document).ready(function () {
        $(".js_date").datepicker({
            dateFormat: "d-M-y",
            changeMonth: true,
            changeYear: true,
            yearRange: "1945:2050"
        });
        
        $(".trade-selection").change(function() {
            var elm = $(this);
            elm.parent().find(".tradeId").val(elm.val());
        });
        
    });
   
    
</script>
<jsp:include page="footer.jsp"/>

</body>
</html>
