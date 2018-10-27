<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="f" uri="http://swcommodities.com/formatFunctions" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Cost List</title>
    <style>
        table.table_border_bottom_head td {
            padding: 1px !important;
        }

        table.cost_fixed_table {
            table-layout: fixed;
        }

        th.btn_save {
            width: 10%;
        }

        th.update_by {
            width: 15%;
        }

        th.value_usd {
            width: 15%;
        }

        th.value_vnd {
            width: 15%;
        }

        th.cost_unit {
            width: 10%;
        }

        th.option_name {
            width: 18%;
        }

        th.space {
            width: 2%;
        }

        table.table_border_bottom_head, tr, td, th
        {
            border-collapse:collapse;
        }
        tr.header
        {
            cursor:pointer;
        }
        .header .sign:after{
            content:"+";
            display:inline-block;
            color: #00529B;
            font-size: 20px;
        }
        .header.expand .sign:after{
            content:"-";
            color: #00529B;
            font-size: 20px;
        }
    </style>
</head>
<body>
<jsp:include page="header.jsp">
    <jsp:param name="url" value="37"/>
    <jsp:param name="page" value="86"/>
</jsp:include>
<div id="main_content" style='width: 1200px !important'>
    <div id="currency">
        <fieldset id="" class="border_1_solid_black">
            <legend>Forex:</legend>
            <table class="table_border_bottom_head">
                <thead>
                <tr>
                    <th>Currency</th>
                    <th>Previous FX to USD</th>
                    <th>Prev. Date</th>
                    <th>Current FX to USD</th>
                    <th>Updated By</th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td align="center"><strong>USD to VND</strong></td>
                    <td align="center"><span id="curExchangePrevRatio"></span></td>
                    <td align="center"><span id="curExchangePrevDate"></span></td>
                    <td align="center"><input style="text-align:right;" type="text" id="currency_usd_vnd" name=""
                                              value="0" class="numberOnly"/></td>
                    <td align="center" id="curExchangeUpdated"></td>
                    <td align="center">
                        <c:if test="${permissonsMap.get('perm_147')}">
                        <input type="button" value="Save" class="" id="saveCurExchange"/>
                        </c:if>
                    </td>
                </tr>

                </tbody>
            </table>
        </fieldset>
    </div>
    <div id="cost_items">

    </div>


</div>
<jsp:include page="footer.jsp"/>
</body>
<script>
    var round2Decimal = function(num) {
        return Math.round(num * 100) / 100;
    };
    var tempcostlist;
    var OptionType = {
        'usd': 0,
        'vnd': 1,
        'none': 2
    }
    $(document).ready(function () {
        loadCurExchange();
        loadCostList();
    }).on("keyup", "input.option_cost", function () {
        var currency = parseFloat($("#currency_usd_vnd").val().replace(',',''));
        var value = parseFloat($(this).val().replace(',',''));
        if ($(this).attr("id").indexOf("vnd") != -1) {
            $("#" + $(this).attr("related_id")).val(accounting.formatNumber(value/currency, 2, ",", "."));
        } else {
            $("#" + $(this).attr("related_id")).val(accounting.formatNumber(value*currency, 0, ",", "."));
        }
    }).on("click", "input.saveSingleCost", function() {
        var send_value = $("#" + $(this).attr("value_id")).val();
        var send_id = $(this).attr("id").split("_")[1];
        $.sendRequest({
            action: "updateOptionCost/" + send_id,
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            data : JSON.stringify({
                send_value : send_value,
                send_id : send_id
            }),
            optional: {url_type: "json"}
        }, function (msg) {
                $.success("Update success!");
                loadCostList();
        });
    }).on("click", "#saveCurExchange", function() {
        var send_value = $("#currency_usd_vnd").val().replace(',','');
        var send_id = 1;
        $.sendRequest({
            action: "updateCurExchange/" + send_id,
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            data : JSON.stringify({
                send_value : send_value,
                send_id : send_id
            }),
            optional: {url_type: "json"}
        }, function (msg) {
                $.success("Update success!");
                loadCurExchange();
                loadCostList();
        });
    }).on("click", ".header", function() {
        var curHeaderLv = parseInt($(this).attr("headerlv"));
        var nextHeaderLv = parseInt($(this).nextAll("tr.header").attr("headerlv"));
        console.log(curHeaderLv + "/" + nextHeaderLv);
        if (curHeaderLv > nextHeaderLv) {
            $(this).toggleClass('expand').nextUntil("tr.header").slideToggle(100);
        } else {
            $(this).toggleClass('expand').nextUntil('tr.headerlv' + curHeaderLv).slideToggle(100);
        }
    });
    
    function getVndId(option_name,id) {
            return "option_" + option_name + "_" + id + "_vnd";
    }

    function getUsdId(option_name,id) {
        return "option_" + option_name + "_" + id + "_usd";
    }

    function convertDate(datestr) {
        return $.format.date(datestr, "dd-MMM-yy hh:mm:ss");
    }

    function renderOption(template, option, level) {
        option['level'] = level;
        return template.render(option, {getVndId: getVndId, getUsdId: getUsdId, convertDate: convertDate, round2Decimal: round2Decimal});
    }

    function renderCategoryWithLevel(template, optionTemplate, cat, level) {
        cat['level'] = level;
        var renderCategory = template.render(cat, {getVndId: getVndId, getUsdId: getUsdId});

        for(var i = 0; i < cat.children.length; i++) {
            renderCategory += renderCategoryWithLevel(template, optionTemplate, cat.children[i], level + 1);
        }
        for(var i = 0; i < cat.options.length; i++) {
            renderCategory += renderOption(optionTemplate, cat.options[i], level + 1);
        }
        return renderCategory;
    }

    function renderRootCat(rootCategoryTmpl, catTemplate, optionTemplate, cat) {

        var renderTrCategory = renderCategoryWithLevel(catTemplate, optionTemplate, cat, 0);
        return rootCategoryTmpl.render({name: cat.name, renderTrCategory: renderTrCategory});
    }

    
    function loadCurExchange() {
        $.sendRequest({
            action: "getCurExchange",
            optional: {url_type: "json"}
        }, function (msg) {
            console.log(msg);
            $("#currency_usd_vnd").val(accounting.formatNumber(msg.ratio, 0, ",", "."));
            $("#curExchangeUpdated").html(msg.username + ": " + $.format.date(msg.updated, "dd-MMM-yy hh:mm:ss"));
            if(msg.exchangeHistory) {
                $("#curExchangePrevDate").html($.format.date(msg.exchangeHistory.exchangeCreated, "dd-MMM-yy hh:mm:ss"));
                $("#curExchangePrevRatio").html(msg.exchangeHistory.ratio);
            }
            return true;
        });
    }

    function loadCostList() {
        $.sendRequest({
            action: "renderCostJson",
            optional: {url_type: "json"}
        }, function (msg) {
            console.log(msg);
            var rootCategoryTmpl = $.templates("#root-category-template");
            var catTemplate = $.templates("#category-template");
            var optionTemplate = $.templates("#option-template");
            console.log(msg);
            var res = "";
            for(var i = 0; i < msg.length; i++) {
                res += renderRootCat(rootCategoryTmpl, catTemplate, optionTemplate, msg[i]);
            }
            $("#cost_items").html(res);
            return true;
        });
    }
</script>

<script id="category-template" type="text/x-jsrender">
    <tr>
        {{if level > 0}}
        <td colspan="{{:level}}" align="left"><strong></strong></td>
        {{/if}}
        <td colspan="{{:(10-level)}}" align="left" data-id="{{:id}}">{{:name}}</td>
    </tr>
</script>

<script id="option-template" type="text/x-jsrender">
    <tr>
        <td colspan="{{:level}}"></td>
        <td align="left" colspan={{:(5-level)}}>{{:name}}</td>
        <td>{{:option_unit}}</td>
        {{if option_type == 0}}
        <td><input style="text-align:right;" type="text" related_id="{{:~getUsdId(option_name,id)}}" id="{{:~getVndId(option_name,id)}}" name="" value="{{:value_in_vnd}}" disabled class="cur_vnd option_cost numberOnly"/></td>
        <td><input style="text-align:right; color: blue;" type="text" related_id="{{:~getVndId(option_name,id)}}" id="{{:~getUsdId(option_name,id)}}" name="" value="{{:~round2Decimal(value_in_usd)}}" class="cur_usd option_cost numberOnly"/></td>
        <td>{{:username}}: {{:~convertDate(updated)}}</td>
        <td>
        <c:if test="${permissonsMap.get('perm_147')}">
        <input type="button" value="Save" class="saveSingleCost" id="btnsave_{{:id}}" value_id="{{:~getUsdId(option_name,id)}}" />
        </c:if>
        </td>
        {{else option_type == 1}}
        <td><input style="text-align:right; color: blue;" type="text" related_id="{{:~getUsdId(option_name,id)}}" id="{{:~getVndId(option_name,id)}}" name="" value="{{:~round2Decimal(value_in_vnd)}}" class="cur_vnd option_cost numberOnly"/></td>
        <td><input style="text-align:right;" type="text" related_id="{{:~getVndId(option_name,id)}}" id="{{:~getUsdId(option_name,id)}}" name="" value="{{:~round2Decimal(value_in_usd)}}" disabled class="cur_usd option_cost numberOnly"/></td>
        <td>{{:username}}: {{:~convertDate(updated)}}</td>
        <td>
        <c:if test="${permissonsMap.get('perm_147')}">
        <input type="button" value="Save" class="saveSingleCost" id="btnsave_{{:id}}" value_id="{{:~getVndId(option_name,id)}}" />
        </c:if>
        </td>
        {{else}}
        <td><input style="text-align:right;" type="text" related_id="{{:~getUsdId(option_name,id)}}" id="{{:~getVndId(option_name,id)}}" name="" value="{{:~round2Decimal(value_in_vnd)}}" disabled class="cur_vnd option_cost numberOnly"/></td>
        <td><input style="text-align:right;" type="text" related_id="{{:~getVndId(option_name,id)}}" id="{{:~getUsdId(option_name,id)}}" name="" value="{{:~round2Decimal(value_in_usd)}}" disabled class="cur_usd option_cost numberOnly"/></td>
        <td></td>
        <td></td>
        {{/if}}
    </tr>
</script>

<script id="root-category-template" type="text/xhtml">
    <fieldset class="border_1_solid_black">
        <legend>{{:name}}</legend>
        <table class="table_border_bottom_head cost_fixed_table">
            <thead>
            <tr>
                <th class="space"></th>
                <th class="space"></th>
                <th class="space"></th>
                <th class="space"></th>
                <th class="option_name"></th>
                <th class="cost_unit">Units</th>
                <th class="value_vnd">Unit Cost VND</th>
                <th class="value_usd">Unit Cost USD</th>
                <th class="update_by">Authorized By</th>
                <th class="btn_save"></th>
            </tr>
            </thead>
            <tbody>
                {{:renderTrCategory}}
            </tbody>
        </table>
    </fieldset>
</script>



</html>