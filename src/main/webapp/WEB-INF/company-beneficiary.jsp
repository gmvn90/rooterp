
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
    <title>Beneficiary</title>
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
    <script type="text/javascript">
    </script>
</head>
<body>
<jsp:include page="header.jsp">
    <jsp:param name="url" value="72"/>
    <jsp:param name="page" value="120"/>
</jsp:include>
<div style="width: 1000px ! important; padding: 15px; background: none repeat scroll 0% 0% white; height: auto; overflow: hidden"
     class="container_16 border" id="wrapper">

    <div id="detail_content">
        <div style="width: 1000px !important; min-height: 80px; height: auto; overflow: hidden;margin-right: 9px;" class="">
            <fieldset>
            <legend>Beneficiary Master</legend>
                <form:form action="${base_web_url}/beneficiaries/update.htm" method="post" commandName="beneficiaryForm">
                    
                    <table class="" style="width: 100%; font-size: 9pt">
                        <tr>
                            <td class="title">Beneficiary:</td>
                            <td class="field_value">
                                <c:if test="${is_form_edit}">${beneficiaryForm.item.companyMaster.name}</c:if>
                                <c:if test="${! is_form_edit}">
                                    <form:select path="item.companyMaster" id='select-company'>
                                         <form:option  value="-1" label="--- Select ---" />
                                         <form:options items="${companies}" itemValue="id" itemLabel="name" />
                                    </form:select>
                                </c:if>
                            </td>
                        </tr>
                        <tr>
                            <td class="title">Short name:</td>
                            <td class="field_value">
                                <c:if test="${canShowData}">
                                    <input value="${beneficiaryForm.item.companyMaster.name}" type="text" style="width: 300px" disabled="" />
                                </c:if>
                            </td>
                        </tr>
                        <tr>
                            <td class="title">Address 1:</td>
                            <td class="field_value">
                                <c:if test="${canShowData}">
                                    <input value="${beneficiaryForm.item.companyMaster.address1}" type="text" style="width: 300px" disabled="" />
                                </c:if>
                            </td>
                        </tr>
                        <tr>
                            <td class="title">Address 2:</td>
                            <td class="field_value">
                                <c:if test="${canShowData}">
                                    <input value="${beneficiaryForm.item.companyMaster.address2}" type="text" style="width: 300px" disabled="" />
                                </c:if>
                            </td>
                        </tr>
                        <tr>
                            <td class="title">Representative:</td>
                            <td class="field_value">
                                <c:if test="${canShowData}">
                                    <form:input path="item.representative" type="text" style="width: 300px" />
                                </c:if>
                            </td>
                        </tr>
                        <tr>
                            <td class="title">Role:</td>
                            <td class="field_value">
                                <c:if test="${canShowData}">
                                    <form:input path="item.representativeRole" type="text" style="width: 300px"/>
                                </c:if>
                                
                            </td>
                        </tr>
                        <tr>
                            <td class="title">Tax Code:</td>
                            <td class="field_value">
                                <c:if test="${canShowData}">
                                    <input value="${beneficiaryForm.item.companyMaster.taxCode}" type="text" style="width: 300px" disabled="" />
                                </c:if>
                                
                            </td>
                        </tr>
                        <tr>
                            <td class="title">Email:</td>
                            <td class="field_value">
                                <c:if test="${canShowData}">
                                    <form:input path="item.email" type="text" style="width: 300px"/>
                                </c:if>
                                
                            </td>
                        </tr>
                        <tr>
                            <td class="title">Fax:</td>
                            <td class="field_value">
                                <c:if test="${canShowData}">
                                    <form:input path="item.fax" type="text" style="width: 300px"/>
                                </c:if>
                                
                            </td>
                        </tr>
                        <tr>
                            <td class="title">Telephone:</td>
                            <td class="field_value">
                                <c:if test="${canShowData}">
                                    <form:input path="item.telephone" type="text" style="width: 300px"/>
                                </c:if>
                                
                            </td>
                        </tr>
                        <tr>
                            <td class="title">Country:</td>
                            <td class="field_value">
                                <c:if test="${canShowData}">
                                    <form:select path="item.companyMaster.country" items="${countries}"
                                             itemValue="id" itemLabel="shortName" disabled="true" />
                                </c:if>
                                
                            </td>
                        </tr>

                        <tr>
                            <td class="title"><input type="submit" value="Save"></td>
                            <td class="field_value">
                                
                                
                            </td>
                        </tr>
                    </table>
                </form:form>
            </fieldset>

            <fieldset>
                <legend>Accounts:</legend>
                <table class="" style="width: 100%; font-size: 9pt">
                    <tr>
                        <td>Bank</td>
                        <td>Currency</td>
                        <td>Account No.</td>
                        <td>Transfer Ins.</td>
                        <td></td>
                    </tr>
                    <c:forEach var="acc" items="${beneficiaryForm.item.bankAccounts}">
                    <tr>
                        <td>${acc.bank.name}</td>
                        <td>${acc.currencyString}</td>
                        <td>${acc.accountNumber}</td>
                        <td>${acc.transferInfo}</td>
                        <td></td>
                    </tr>
                    </c:forEach>
                    <tr>
                        <td colspan="5">
                            <input type="button" value="Add account" id="add-account">
                        </td>
                    </tr>
                    <tr style="display: none;" id="additional-container">
                        <td>
                            <select id="additional-account-bank">
                                <c:forEach var="bank" items="${banks}">
                                    <option value="${bank.id}">${bank.value}</option>
                                </c:forEach>
                            </select>
                        </td>
                        <td>
                            <select id='additional-account-currency'>
                                <c:forEach var="curr" items="${currencies}">
                                    <option value="${curr.key}">${curr.value}</option>
                                </c:forEach>
                            </select>
                        </td>
                        <td><input id="additional-account-number" type='text' /></td>
                        <td><textarea id="additional-account-transferInfo"></textarea></td>
                        <td><input type="button" id="additional-account-save-btn" value="Save"></td>
                    </tr>
                </table>
            </fieldset>
        </div>
    </div>

</div>
<script>
    $(document).ready(function () {
        console.log("ready");
        $(".js_date").datepicker({
            dateFormat: "d-M-y",
            changeMonth: true,
            changeYear: true,
            yearRange: "1945:2050"
        });
        $("#select-company").change(function() {
            console.log("on change");
            location.href = getAbsolutePath()+ "/beneficiaries/show.htm?tryId=" + $(this).val();
        });
        $("#add-account").click(function() {
            $("#additional-container").show();
        });
        $("#additional-account-save-btn").click(function() {
            var obj = {};
            obj.bank = parseInt($("#additional-account-bank").val());
            obj.currency = parseInt($("#additional-account-currency").val());
            obj.accountNumber = $("#additional-account-number").val();
            obj.transferInfo = $("#additional-account-transferInfo").val();
            obj.beneficiary = ${currentId};
            $.ajax({
                type: 'POST',
                data: JSON.stringify(obj),
                contentType: "application/json",
                url: getAbsolutePath() + "/banks/add.json",
                success: function(msg) {
                    location.href = getAbsolutePath()+ "/beneficiaries/show.htm?id=" + ${currentId};
                }
            });
        });
    });
</script>
<jsp:include page="footer.jsp"/>

</body>
</html>
