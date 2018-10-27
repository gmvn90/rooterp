
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
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Beneficiary list</title>
    <style>
        .styleTotal td {
            border-top: 1px solid #000;
        }

        .bolder {
            font-size: 12px;
        }

        table.dataTableStandard {
            border-collapse: collapse;
            font-size: 8pt;
            width: 100%;
        }

        table.dataTableStandard tfoot {
            text-align: left;
        }

        table.dataTableStandard tbody tr td, table.dataTableStandard thead tr th {
            border-bottom: 1px solid black;
            padding: 5px;
        }

        table.dataTableStandard thead tr th, table.dataTableStandard tbody tr th {
            background-color: #EEEEEE;
            border-top: 1px solid black;
        }

        table.dataTableStandard td, table.dataTableStandard th {
            width: 5%;
        }

        table.dataTableStandard tbody {
            height: 180px; /* Just for the demo          */
            overflow-y: auto; /* Trigger vertical scroll    */
            overflow-x: hidden; /* Hide the horizontal scroll */
        }

        legend {
            font-size: 10pt;
        }

        fieldset {
            margin-bottom: 30px;
        }


    </style>
    <script type="text/javascript">
    </script>
</head>
<body>
<jsp:include page="header.jsp">
    <jsp:param name="url" value="71"/>
    <jsp:param name="page" value="119"/>
</jsp:include>
<div style="width: 1000px ! important; padding: 15px; background: none repeat scroll 0% 0% white; height: auto; overflow: hidden"
     class="container_16 border" id="wrapper">

    <div id="detail_content">
        <div style="width: 1000px !important; min-height: 80px; height: auto; overflow: hidden;margin-right: 9px;" class="">
            <h1>Beneficiary list</h1>
            <table class="dataTableStandard">
                <tr style="font-size: 13px">
                    <th>Company Name</th>
                </tr>
                <c:forEach var="item" items="${beneficiaries}">
                <tr style="font-size: 13px" data-href="${base_web_url}/beneficiaries/show.htm?id=${item.id}" class="go2Detail">
                    <td >${item.companyMaster.name}</td>
                </tr>
                </c:forEach>
                
            </table>

            
        </div>
    </div>

</div>
<script>
    $(document).ready(function () {
        console.log("ready");
        $('tr[data-href]').on("dblclick", function () {
            document.location = $(this).data('href');
        });
    });
</script>
<jsp:include page="footer.jsp"/>

</body>
</html>
