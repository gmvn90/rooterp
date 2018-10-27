var oneCostTmpl;
var totalTmpl;
var oneCatTmpl;
var oneCost_Select;
var costSkeleton;
var globalOptionDict = {};
var globalCostDict = {};
var globalCategoryDict = {};
var globalPackings = [
        {id: 13, name: "Bulk"},
        {id: 14, name: "Big Bag"},
        {id: 15, name: "Jute Bag"},
        {id: 16, name: "PP Bag"},
];
var defaultOptions = {
    packingCostCategory: 13,
    loadingAndTransportCategory: 17,
    documentCategory: 18,
    certificateCategory: 19,
    fumigationCategory: 20,
    fumigationProviderCategory: 21,
    optionalCategory: 23,
    allMarkingCategory: 24,
    markingCategory: 25,
    fumigationDetailCost: "vfc__photoxin-9g",
    fumigationInstore: "vfc__fumigation-in-store",
    certificateCost: "",
    tonPerContainer: 19.2,
    globalOptionalCosts: [],
};
var haveCost = false;
var round2Decimal = function(num) {
    return Math.round(num * 100) / 100;
};

function merge_options(obj1, obj2){
    var obj3 = {};
    for (var attrname in obj1) { obj3[attrname] = obj1[attrname]; }
    for (var attrname in obj2) {
        if(! obj1.hasOwnProperty(attrname)) {
            obj3[attrname] = obj2[attrname];
        }
    }
    return obj3;
}

function getCategory(id) {
    return globalCategoryDict["cat" + id];
}

function renderOneCost(option_name, level, _type, containerClass) {
    var cost = globalCostDict[option_name];
    var option = globalOptionDict[option_name];
    return oneCostTmpl.render({
        cost: cost,
        option: option,
        level: level,
        _type: _type,
        containerClass: containerClass,
        globalOptionalCosts: options.globalOptionalCosts,
    }, {round2Decimal: round2Decimal});
}

function renderOneCat(categories, currentItem, selectId, level, containerClass) {
    return oneCatTmpl.render({
        categories: categories,
        selectId: selectId,
        currentItem: currentItem,
        level: level,
        containerClass: containerClass,
    }, {round2Decimal: round2Decimal});
}

function renderCatOptions(options, currentItem, selectId, level, containerClass, name) {
    var option = globalOptionDict[currentItem];
    var cost = globalCostDict[currentItem];
    return oneCost_Select.render({
        options: options,
        currentItem: currentItem,
        selectId: selectId,
        level: level,
        containerClass: containerClass,
        cost: cost,
        option: option,
        name: name,
    }, {round2Decimal: round2Decimal});
}

function ajaxUpload(fileObject, callback) {
    var formData = new FormData();
    formData.append('file', fileObject[0].files[0]);
    formData.append("name", "name");
    formData.append("emails", "emails");
    $.ajax({
           url : getAbsolutePath() + '/uploadFileSent.json',
           type : 'POST',
           data : formData,
           processData: false,  // tell jQuery not to process the data
           contentType: false,  // tell jQuery not to set contentType
           success : function(data) {
               console.log(data);
               callback(data);
           }
    });
}

function updateGlobalCostDict(parentCat) {
    globalCategoryDict["cat" + parentCat.id] = parentCat;
    if(parentCat.options) {
        if(parentCat.options.length > 0) {
            console.log("will be used", parentCat.id, parentCat.name);
        }
        for(var i = 0; i < parentCat.options.length; i++) {
            globalOptionDict[parentCat.options[i].option_name] = parentCat.options[i];
        }
    }
    if(parentCat.children) {
        for(var i = 0; i < parentCat.children.length; i++) {
            updateGlobalCostDict(parentCat.children[i]);
        }
    }
}

function renderOneOptionalCost(option_name, optionDict, costDict, template, level) {
    var cost = costDict[option_name];
    var option = optionDict[option_name];
    return template.render({
        cost: cost,
        option: option,
        level: level,
    }, {round2Decimal: round2Decimal});
}

function renderOneCertificateCost(optionsNames, option, cost, level, name, containerClass) {
    console.log("certificateCost", options.certificateCost);
    var template = $.templates("#certificateCost");
    return template.render({
        option: option,
        cost: cost,
        level: level,
        options: optionsNames,
        name: name,
        containerClass: containerClass,
        certificateCost: options.certificateCost,
    }, {round2Decimal: round2Decimal});
}

function renderOneFumigationCost(children, currentCat, level, name, containerClass, renderedFumigationDetail) {
    //console.log(renderedFumigationDetail);
    var template = $.templates("#fumigationCost");
    return template.render({
        children: children,
        currentCat: currentCat,
        level: level,
        name: name,
        containerClass: containerClass,
        fumigationCost: options.fumigationProviderCategory,
        renderedFumigationDetail: renderedFumigationDetail,
    }, {round2Decimal: round2Decimal});
}

function renderTotalCost(option_names, costDict, template, name, itemClass) {

    var total = 0;
    for(var i = 0; i < option_names.length; i++) {
        if($("#" + option_names[i] + " .isEnable").is(":checked")) {
            total += costDict[option_names[i]].value_per_metric_ton;
        }
    }
    return template.render({
        total: total,
        name: name,
        options: option_names,
        itemClass: itemClass,
    }, {round2Decimal: round2Decimal});
}

function renderDocumentTotalCost(name) {
    var total = 0;
    return totalTmpl.render({
        total: total,
        name: name,
        options: "",
        itemClass: "document-cost-total",
    }, {round2Decimal: round2Decimal});
}

function renderHeader(name) {
    var headerTmpl = $.templates("#costHeader");
    return headerTmpl.render({name: name});
}

function renderAllCostsInCategory_ListView(id, _type, name, isRenderTotal, containerClass, level, forcedName) {
    var res = "";
    var cat = globalCategoryDict["cat" + id];
    var options = [];
    res += renderHeader(name);
    for(var i = 0; i < cat.options.length; i++) {
        options.push(cat.options[i].option_name);
        res += renderOneCost(cat.options[i].option_name, level, _type, containerClass);
    }
    var forcedName = forcedName || "Total " + name;
    if(isRenderTotal) {
        res += renderTotalCost(options, globalCostDict, totalTmpl, forcedName);
    }
    return res;
}
function renderAllCostsInCategory_CertificateView(id) {
    var res = "";
    var cat = globalCategoryDict["cat" + id];
    var localOptions = [];
    for(var i = 0; i < cat.options.length; i++) {
        localOptions.push(cat.options[i]);
    }
    var option = cat.options[0];
    for(var i = 0; i < localOptions.length; i++) {
        if(localOptions[i].option_name == options.certificateCost) {
            option = localOptions[i];
            break;
        }
    }
    var cost = globalCostDict[option.option_name];
    console.log(option, localOptions, cost);
    res += renderOneCertificateCost(localOptions, option, cost, 4, "Qualtiy & Weight Certificate:", "document-cost");
    return res;
}

function renderAllCostsInCategory_FumigationView(id) {
    var res = "";
    var cat = globalCategoryDict["cat" + id];
    var children = [];
    for(var i = 0; i < cat.children.length; i++) {
        children.push(cat.children[i]);
    }
    var currentCat = cat.children[0];

    for(var i = 0; i < children.length; i++) {
        if(children[i].id === options.fumigationProviderCategory) {
            currentCat = children[i];
            break;
        }
    }
    var renderOptions = globalCategoryDict["cat" + options.fumigationProviderCategory].options;
    var renderedFumigationDetail = renderCatOptions(renderOptions, options.fumigationDetailCost, "fumigationDetailSelect", 5, "document-cost", "Fumigation detail");
    res += renderOneFumigationCost(children, currentCat, 5, "Fumigation:", "document-cost", renderedFumigationDetail);
    res += renderOneCost(options.fumigationInstore, 5, "optional", "document-cost");
    return res;
}

function renderAllCategoryInCategory_SelectView(id, currentItem, name, selectId) {
    var cat = globalCategoryDict["cat" + id];

    return $.templates("#childrenCategory").render({
        selectId: selectId,
        children: cat.children,
        currentItem: currentItem,
        name: name,
    });
};

function renderMarkingCost() {
    var res = "";
    res += renderHeader("Marking:");
    res += renderAllCategoryInCategory_SelectView(options.allMarkingCategory, options.markingCategory, "Marking type", "selectMarkingType");
    res += renderAllCostsInCategory_ListView(options.markingCategory, 'normal', getCategory(options.markingCategory).name, true, 'marking-category', 4, "Total marking:");
    return res;
}

function updateCertificateTotalCost() {
    var options = $(".document-cost");
    var option_names = [];
    options.each(function() {
        if($(this).attr("id")) {
            option_names.push($(this).attr("id"));
        }
    });
    $(".document-cost-total").attr("options", option_names);
}

function updateAllCostTotal() {
    var totalAll = 0;
    updateCertificateTotalCost();
    $(".total").each(function() {
        var elm = $(this);
        totalAll += updateOneCostTotal(elm);
    });
    $("#totalAll").html(round2Decimal(totalAll));
}

function getAllChosenCost() {
    var res = "";
    $(".total").each(function() {
        var elm = $(this);
        if(res === "") {
            res = elm.attr("options");
        } else {
            res += "," + elm.attr("options");
        }
    });
    var costs = [];
    var options = res.split(",");
    for(var i = 0; i < options.length; i++) {
        if($("#" + options[i] + " .isEnable").is(":checked")) {
            costs.push(options[i]);
        }
    }
    return costs;
}

function getCostInfo() {
    var info = {};
    info.costs = getAllChosenCost();
    info.tonPerContainer = $("#tonPerContainer").val();
    info.export_containers = $("#export_containers").val();
    info.selectBag = $("#selectBag").val();
    info.selectCertificateCost = options.certificateCost;
    info.selectFumigationCost = options.fumigationProviderCategory;
    //console.log(getFumigation(), getWeightCertificate());
    return info;
}

function getFumigation() {
    return $("#selectFumigationCost option:selected").text();
    //$("#yourdropdownid option:selected").text();
}

function getWeightCertificate() {
    return $("#selectCertificateCost option:selected").text();
}

function updateOneCostTotal(elm) {
    var options = [];
    if(elm.attr("options")) {
        options = elm.attr("options").split(",");
    }
    if(! options) {
        options = [];
    }
    var total = 0;
    for(var i = 0; i < options.length; i++) {
        if($("#" + options[i] + " .isEnable").is(":checked")) {
            total += globalCostDict[options[i]].value_per_metric_ton;
        }
    }
    elm.html(round2Decimal(total));
    return total;
}

function renderSkeleton() {
    return costSkeleton.render({
        options: options,
        globalPackings: globalPackings,
    });
}

function renderAllInformation() {
    $("#costContainer").html(renderSkeleton());
    console.log("renderAllInformation");
    $(".no-cost-info").hide();
    $(".has-cost-info").show();
    var res = renderAllCostsInCategory_ListView(options.packingCostCategory, "normal", "Packing cost:", true, 'packing-cost', 4);
    res += renderAllCostsInCategory_ListView(options.loadingAndTransportCategory, "normal", "Loading and Transport:", true, 'loading-and-transport-cost', 4);
    res += renderAllCostsInCategory_ListView(options.documentCategory, "optional", "Documents:", false, 'document-cost', 4);
    res += renderAllCostsInCategory_CertificateView(options.certificateCategory);
    res += renderAllCostsInCategory_FumigationView(options.fumigationCategory);
    res += renderDocumentTotalCost("Total Documents:");
    res += renderAllCostsInCategory_ListView(options.optionalCategory, "normal", "Optional Packing Items:", true, 'optional-cost', 4);
    res += renderMarkingCost();
    $("#cost_items").html(res);
    // must come after html render
    updateAllCostTotal();
    loadCurExchange();
}

function loadCostList() {
    console.log("loadCostList");
    $.sendRequest({
        action: "getCostsIncludingCostPerTon/" + $("#client").val(),
        data : {
            id : $("#client").val(),
            tonPerContainer: options.tonPerContainer,
            options: "",
        },
        optional: {url_type: "json"}
    }, function (costs) {
        globalCostDict = costs.costMap;
        if(Object.keys(globalCostDict).length === 0) {
            notifyNoCost();
            haveCost = false;
        } else {
            haveCost = true;
            renderAllInformation();
        }
        return true;
    });
};

function notifyNoCost() {
    $(".no-cost-info").show();
    $(".has-cost-info").hide();
}

function removeOneOptionalCost(cost) {
    for(var i = 0; i < options.globalOptionalCosts.length; i++) {
        if(cost == options.globalOptionalCosts[i]) {
            options.globalOptionalCosts.splice(i, 1);
        }
    }
}

function getAllOptionAndCategory() {
    $.sendRequest({
        action: "renderCostJson",
        optional: {url_type: "json"}
    }, function (cats) {
        for(var i = 0; i < cats.length; i++) {
            updateGlobalCostDict(cats[i]);
        }
        return true;
    });
}

$(document).ready(function () {
    oneCostTmpl = $.templates("#oneCost");
    totalTmpl = $.templates("#totalCost");
    oneCatTmpl = $.templates("#oneCat");
    oneCost_Select = $.templates("#oneCost_Select");
    costSkeleton = $.templates("#costSkeleton");
    getAllOptionAndCategory();
}).on("change", "#client", function() {
    console.log("on change fired");
    loadCostList();
}).on("change", ".isEnable", function() {
    var elm = $(this);
    if(elm.is(":checked")) {
        options.globalOptionalCosts.push(elm.attr("option"));
    } else {
        removeOneOptionalCost(elm.attr("option"));
    }
    updateAllCostTotal();
}).on("change", "#selectBag", function() {
    options.packingCostCategory = $(this).val();
    renderAllInformation();
}).on("change", "#selectCertificateCost", function() {
    removeOneOptionalCost(options.certificateCost);
    $(".certificateCost").attr("id", $(this).val());
    options.certificateCost = $(this).val();
    console.log(options.certificateCost );
    renderAllInformation();
}).on("change", "#selectFumigationCost", function() {
    options.fumigationProviderCategory = $(this).val();
    var fumCat = getCategory(options.fumigationProviderCategory);
    options.fumigationDetailCost = fumCat.options[0].option_name;
    options.fumigationInstore = fumCat.options[fumCat.options.length - 1].option_name;
    renderAllInformation();
}).on("change", "#fumigationDetailSelect", function() {
    options.fumigationDetailCost = $(this).val();
    renderAllInformation();
}).on("change", "#tonPerContainer", function() {
    loadCostList();
});