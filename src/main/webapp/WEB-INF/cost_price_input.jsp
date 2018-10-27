<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="f" uri="http://swcommodities.com/formatFunctions" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Cost Price Input</title>
    <style type="text/css">

        table td {
            padding: 5px;
            font-size: 10pt;
        }

        td.start-table {
            border-left: 1px solid red;
        }

        td.end-table {
            border-right: 1px solid red;
        }
        tr.first-row td {
            border-top: 1px solid red;
        }
        tr.last-row td {
            border-bottom: 1px solid red;
        }
        tr td.seperate {
            border-top: 1px solid #fff;
        }
        .bold-column {
            font-weight: bold;
        }
        .first-row td.seperate {
            border-top: 1px solid #fff;
        }

        tr.last-row td.seperate {
            border-bottom: 1px solid #fff;
        }

    </style>
</head>
<body>
<jsp:include page="header.jsp">
    <jsp:param name="url" value="39"/>
    <jsp:param name="page" value="88"/>
</jsp:include>
<div id="main_content" style='width: 1500px !important'>
    <div >
        <table cellpadding="0" cellspacing="0" class="table_border_bottom_head cost_fixed_table" >
            <tbody>
                <tr>
                    <td colspan="9">Assumptions:</td>
                    <td class="seperate"></td>
                    <td colspan="6">Client Details:</td>
                </tr>
                <tr class="first-row">
                    <td class="start-table" colspan="8">F/X  VND/USD</td>
                    <td class="end-table" id="currency_usd_vnd"></td>
                    <td class="seperate"></td>
                    <td class="start-table">Client</td>
                    <td colspan="4">
                        <form action="getPriceReport.htm" method="get">
                        <select id="companyList" name="" style="display: none;">
                            <option value="">---</option>
                        </select>
                            <label id="companyName"></label>
                            <input type="hidden" value="" name="companyId" id="hiddenCompanyId">
                        <input style="min-width: 10px"
                               class="button ui-button ui-widget ui-state-default ui-corner-all"
                               type="submit" value="Price Report"/>
                        </form>
                    </td>
                    <td colspan="" class="end-table"></td>
                </tr>

                <tr>
                    <td class="start-table" colspan="8">Mt./20' Container</td>
                    <td class="end-table">20000</td>
                    <td class="seperate"></td>
                    <td class="start-table end-table" colspan="6"></td>

                </tr>

                <tr class="last-row" id="fixMarginContainer">
                    <td class="start-table" colspan="8">Estimated Containers / Shipment</td>
                    <td class="end-table">1</td>
                    <td class="seperate"></td>
                    <td class="start-table">Fixed margin</td>
                    <td colspan="2">
                        <input class="input" id="fixedMargin">
                    </td>
                    <td colspan="2" class="updater-and-time"></td>
                    <td class="end-table">
                        <c:if test="${permissonsMap.get('perm_148')}">
                        <button id="save_fixed_margin">Save</button>
                        </c:if>
                    </td>
                </tr>



            </tbody>
            <tbody id="cost_items">
            </tbody>
        </table>
    </div>
</div>
<jsp:include page="footer.jsp"/>
</body>
<script>
    var round2Decimal = function(num) {
        return Math.round(num * 100) / 100;
    };
    var round1Decial = function (num) {
        return Math.round(num * 10) / 10;
    };
    var tempcostlist;
    var empty_text = "---";
    var OptionType = {
        'usd': 0,
        'vnd': 1,
        'none': 2
    };
    function loadCompany(id) {
        $.sendRequest({
                action: "getFixedMargin/" + id,
                data : {
                    id : id,
                },
                optional: {url_type: "json"}
            }, function (msg) {
                if(msg.value) {
                    refresh_fixed_margin(msg);
                    $.sendRequest({
                        action: "getAllOptionCosts/" + id,
                        data : {
                            id : id,
                        },
                        optional: {url_type: "json"}
                    }, function (costs) {
                        console.log(costs);
                        $("#companyList").val(id);
                        refreshCostUI(costs);
                        return true;
                    });
                } else {
                    $(".option-container td.will-change-after-loading").html(empty_text);
                    refresh_fixed_margin_to_initial();
                }
                return true;
            });
    }
    var getUrlParameter = function getUrlParameter(sParam) {
        var sPageURL = decodeURIComponent(window.location.search.substring(1)),
                sURLVariables = sPageURL.split('&'),
                sParameterName,
                i;

        for (i = 0; i < sURLVariables.length; i++) {
            sParameterName = sURLVariables[i].split('=');

            if (sParameterName[0] === sParam) {
                return sParameterName[1] === undefined ? true : sParameterName[1];
            }
        }
    };
    function getParam() {
        return getUrlParameter("company_id");
    }
    $(document).ready(function () {
        loadCurExchange();
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
            action: "updateOptionCost",
            data : {
                send_value : send_value,
                send_id : send_id
            },
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
            data : {
                send_value : send_value,
                send_id : send_id
            },
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
    }).on("change", "#companyList", function() {
        var id = $("#companyList").val();
        console.log(id);

        if(id) {
            loadCompany(id);
        };
    }).on("click", "#save_fixed_margin", function() {
        $.sendRequest({
            action: "updateFixedMargin/" + $("#companyList").val(),
            data : JSON.stringify({
                id : $("#companyList").val(),
                margin: $("#fixedMargin").val()
            }),
            headers: { 
                'Accept': 'application/json',
                'Content-Type': 'application/json' 
            },
            optional: {url_type: "json"}
        }, function (marginObj) {
            console.log(marginObj);
            refresh_fixed_margin(marginObj);
            $.success("Update success!");
            $.sendRequest({
                action: "getAllOptionCosts/" + $("#companyList").val(),
                data : {
                    id : $("#companyList").val(),
                },
                
                optional: {url_type: "json"}
            }, function (costs) {
                console.log(costs);
                refreshCostUI(costs);
                return true;
            });
            return true;
        });

    }).on("click", ".updateOneCost", function() {
        var elm = $(this);
        var id = elm.data("id");
        var value = elm.parent().parent().find(".cost-input input").val();
        $.sendRequest({
            action: "updateOneCost/" + id,
            data : JSON.stringify({
                value : value,
                id : id
            }),
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            optional: {url_type: "json"}
        }, function (msg) {
            $.success("Update success!");
            console.log(msg.option);
            refreshOneCost($("#option-container-" + msg.option), msg);
        });

    });

    function refresh_fixed_margin(marginObj) {
        console.log(marginObj);
        $("#fixMarginContainer .input").val(marginObj.value);
        $("#fixMarginContainer .updater-and-time").html(marginObj.username + ": " + $.format.date(marginObj.updated, "dd-MMM-yy hh:mm:ss"));
    }

    function refresh_fixed_margin_to_initial() {
        $("#fixMarginContainer .input").val("");
        $("#fixMarginContainer .updater-and-time").html(empty_text);
    }


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
            console.log("loadCurExchange", msg);
            $("#currency_usd_vnd").html(msg.ratio);
            $("#curExchangeUpdated").html(msg.username + ": " + $.format.date(msg.updated, "dd-MMM-yy hh:mm:ss"));
            if(msg.exchangeHistory) {
                $("#curExchangePrevDate").html($.format.date(msg.exchangeHistory.exchangeCreated, "dd-MMM-yy hh:mm:ss"));
                $("#curExchangePrevRatio").html(msg.exchangeHistory.ratio);
            } else {
                var trs = $(".option-container");

            }
            loadCostList();
            return false;
        });
    }

    function refreshCostUI(costs) {
        for(var i = 0; i < costs.length; i++) {
            var _cost = costs[i];
            var tr = $("#option-container-" + _cost.option);
            refreshOneCost(tr, _cost);
        }
    }

    function refreshOneCost(tr, _cost) {

        if(_cost.value) {
            var oldValue = parseFloat(tr.find("td.value_in_usd").html());
            tr.find("td.cost-input").html("<input value='"+round2Decimal(_cost.value)+"' class=''>");
            tr.find("td.save-cost").html("<button data-id='"+_cost.id+"' class='updateOneCost' >Save</button>");
            tr.find("td.margin-usd").html(round1Decial(_cost.value - oldValue));
            tr.find("td.margin-usd-per-ton").html(round2Decimal((_cost.value - oldValue)/ 20));

            if(oldValue != 0) {
                tr.find("td.margin-percentage").html((Math.round(((_cost.value - oldValue) * 100 / oldValue) * 10) / 10) + " %");
            } else {
                tr.find("td.margin-percentage").html(empty_text);
            }
        } else {
            tr.find("td.cost-input").html(empty_text);
            tr.find("td.save-text").html(empty_text);
            tr.find("td.margin-usd").html(empty_text);
            tr.find("td.margin-usd-per-ton").html(empty_text);
            tr.find("td.margin-percentage").html(empty_text);
        }

        if(_cost.username || _cost.updated) {
            tr.find("td.updater-and-date").html(_cost.username + ": " + convertDate(_cost.updated));
        } else {
            tr.find("td.updater-and-date").html(empty_text);
        }

    }

    function loadAllCompanies() {
        var thisCompany;
        $.sendRequest({
            action: "getAllCompanies",
            optional: {url_type: "json"}
        }, function (msg) {
            $.each(msg, function(key, value) {
                if(value.id == getParam()) {
                    thisCompany = value;
                    $("#hiddenCompanyId").val(value.id);
                    $("#companyName").html(value.name);
                }
                $('#companyList')
                        .append($("<option></option>")
                                .attr("value", value.id)
                                .text(value.name));
            });
            if(getParam()) {
               loadCompany(getParam());
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
            loadAllCompanies();
            return true;
        });
    }
</script>

<script id="category-template" type="text/xhtml">
    <tr>
        {{if level > 0}}
        <td colspan="{{:level}}" align="left" class="start-table"></td>
        <td colspan="{{:(9-level)}}" class="end-table">{{:name}}</td>
        {{else}}
        <td colspan="9" align="left" class="start-table end-table">{{:name}}</td>
        {{/if}}

        <td class="seperate"></td>

        <td class="start-table end-table" colspan="6"></td>

    </tr>
</script>

<script id="option-template" type="text/x-jsrender">
    <tr id='option-container-{{:id}}' class='option-container' option_name="{{:option_name}}">
        <td colspan="{{:level}}" class="start-table"></td>
        <td colspan="{{:(5-level)}}">{{:name}}</td>
        <td>{{:option_unit}}</td>
        <td>{{:~round2Decimal(value_in_vnd)}}</td>
        <td class='value_in_usd'>{{:~round2Decimal(value_in_usd)}}</td>
        <td class="end-table">{{:~round2Decimal(value_in_usd)}}</td>

        <td class="seperate"></td>

        <td class="start-table cost-input will-change-after-loading">---</td>
        <td class='updater-and-date will-change-after-loading'>--</td>
        <td class='save-cost will-change-after-loading'>--</td>
        <td class='margin-usd will-change-after-loading'>--</td>
        <td class='margin-usd-per-ton will-change-after-loading'>--</td>
        <td class="end-table margin-percentage will-change-after-loading">--</td>

    </tr>
</script>

<script id="root-category-template" type="text/xhtml">

        <tr>
            <td colspan="9">{{:name}}</td>
            <td></td>
            <td colspan="6">{{:name}}</td>
        </tr>
            <tr class="first-row bold-column">
                <td class="start-table"></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td>Units</td>
                <td>Unit Cost VND</td>
                <td>Unit Cost USD</td>
                <td class="end-table">Cost USD/Mt.</td>

                <td class="seperate"></td>

                <td class="start-table">Unit price USD</td>
                <td></td>
                <td></td>
                <td>Unit Margin USD</td>
                <td>Unit Margin USD/Mt.</td>
                <td class="end-table">Unit Margin Percent</td>
            </tr>

            {{:renderTrCategory}}

            <tr class="last-row">
                <td class="start-table"></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td class="end-table"></td>

                <td class="seperate"></td>

                <td class="start-table"></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td class="end-table"></td>
            </tr>


</script>

</html>