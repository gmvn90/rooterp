<%-- 
    Document   : update_pallet
    Created on : Sep 10, 2013, 3:22:08 PM
    Author     : kiendn
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Admin Functions</title>
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
                font-style: italic;
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

        </style>
    </head>
    <body>
        <jsp:include page="../header.jsp">
            <jsp:param name="url" value="63"/>
            <jsp:param name="page" value="110"/>
        </jsp:include>
        <form:form action="delivery_saveCachePending.htm" method="post">
            <tr>
                <td></td>
                <td class="title">DI save cache pending</td>
                <td class="field_value" colspan="3">
                    <input style="min-width: 10px"
                           class="button ui-button ui-widget ui-state-default ui-corner-all"
                           type="submit" value="Run"/>
                </td>
            </tr>
        </form:form>
        <form:form action="delivery_saveCacheAll.htm" method="post">
            <tr>
                <td></td>
                <td class="title">DI save cache ALL</td>
                <td class="field_value" colspan="3">
                    &nbsp;&nbsp;Offset value: <input type="number" value="0" name="number" /> <br>
                    <input style="min-width: 10px"
                           class="button ui-button ui-widget ui-state-default ui-corner-all"
                           type="submit" value="Run"/>
                </td>
            </tr>
        </form:form>

        <form:form action="processing_saveCachePending.htm" method="post">
            <tr>
                <td></td>
                <td class="title">PI save cache pending</td>
                <td class="field_value" colspan="3">
                    <input style="min-width: 10px"
                           class="button ui-button ui-widget ui-state-default ui-corner-all"
                           type="submit" value="Run"/>
                </td>
            </tr>
        </form:form>
        <form:form action="processing_saveCacheAll.htm" method="post">
            <tr>
                <td></td>
                <td class="title">PI save cache ALL</td>
                <td class="field_value" colspan="3">
                    &nbsp;&nbsp;Offset value: <input type="number" value="0" name="number" /> <br>
                    <input style="min-width: 10px"
                           class="button ui-button ui-widget ui-state-default ui-corner-all"
                           type="submit" value="Run"/>
                </td>
            </tr>
        </form:form>

        <form:form action="shipping_saveCachePending.htm" method="post">
            <tr>
                <td></td>
                <td class="title">SI save cache pending</td>
                <td class="field_value" colspan="3">
                    <input style="min-width: 10px"
                           class="button ui-button ui-widget ui-state-default ui-corner-all"
                           type="submit" value="Run"/>
                </td>
            </tr>
        </form:form>
        <form:form action="shipping_saveCacheAll.htm" method="post">
            <tr>
                <td></td>
                <td class="title">SI save cache ALL</td>
                <td class="field_value" colspan="3">
                    &nbsp;&nbsp;Offset value: <input type="number" value="0" name="number" /> <br>
                    <input style="min-width: 10px"
                           class="button ui-button ui-widget ui-state-default ui-corner-all"
                           type="submit" value="Run"/>
                </td>
            </tr>
        </form:form>

        <form:form action="sampleSent_saveCachePending.htm" method="post">
            <tr>
                <td></td>
                <td class="title">Sample Sent save cache pending</td>
                <td class="field_value" colspan="3">
                    <input style="min-width: 10px"
                           class="button ui-button ui-widget ui-state-default ui-corner-all"
                           type="submit" value="Run"/>
                </td>
            </tr>
        </form:form>
        <form:form action="sampleSent_saveCacheAll.htm" method="post">
            <tr>
                <td></td>
                <td class="title">Sample Sent save cache ALL</td>
                <td class="field_value" colspan="3">
                    <input style="min-width: 10px"
                           class="button ui-button ui-widget ui-state-default ui-corner-all"
                           type="submit" value="Run"/>
                </td>
            </tr>
        </form:form>
        
        <form:form action="invoice_saveCost.htm" method="post">
            <tr>
                <td></td>
                <td class="title">Invoice save all costs</td>
                <td class="field_value" colspan="3">
                    <input style="min-width: 10px"
                           class="button ui-button ui-widget ui-state-default ui-corner-all"
                           type="submit" value="Run"/>
                </td>
            </tr>
        </form:form>
            
        <form:form action="init_fob_diff.htm" method="post">
            <tr>
                <td></td>
                <td class="title">Init Fob Diff</td>
                <td class="field_value" colspan="3">
                    <input style="min-width: 10px"
                           class="button ui-button ui-widget ui-state-default ui-corner-all"
                           type="submit" value="Run"/>
                </td>
            </tr>
        </form:form>
            
        <form:form action="init_terminal_month.htm" method="post">
            <tr>
                <td></td>
                <td class="title">Init Terminal Month</td>
                <td class="field_value" colspan="3">
                    <input style="min-width: 10px"
                           class="button ui-button ui-widget ui-state-default ui-corner-all"
                           type="submit" value="Run"/>
                </td>
            </tr>
        </form:form>
            
        <form:form action="shippingInstruction_saveNewCost.htm" method="post">
            <tr>
                <td></td>
                <td class="title">Update shipping instruction</td>
                <td class="field_value" colspan="3">
                    <input style="min-width: 10px"
                           class="button ui-button ui-widget ui-state-default ui-corner-all"
                           type="submit" value="Run"/>
                </td>
            </tr>
        </form:form>
            
        <form:form action="initUserAndMenu.htm" method="post">
            <tr>
                <td></td>
                <td class="title">Update users and menus and permissions</td>
                <td class="field_value" colspan="3">
                    <input style="min-width: 10px"
                           class="button ui-button ui-widget ui-state-default ui-corner-all"
                           type="submit" value="Run"/>
                </td>
            </tr>
        </form:form>
            
            
        <jsp:include page="../footer.jsp"></jsp:include>
        <script>

        </script>
    </body>
</html>
