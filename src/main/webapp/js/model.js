function merge_options(obj1, obj2) {
    var obj3 = {};
    for (var attrname in obj1) {
        obj3[attrname] = obj1[attrname];
    }
    for (var attrname in obj2) {
        obj3[attrname] = obj2[attrname];

    }
    return obj3;
}
;

function clone(obj) {
    if (obj === null || typeof (obj) !== 'object' || 'isActiveClone' in obj)
        return obj;

    if (obj instanceof Date)
        var temp = new obj.constructor(); //or new Date(obj);
    else
        var temp = obj.constructor();

    for (var key in obj) {
        if (Object.prototype.hasOwnProperty.call(obj, key)) {
            obj['isActiveClone'] = null;
            temp[key] = clone(obj[key]);
            delete obj['isActiveClone'];
        }
    }

    return temp;
}

function Cost(option, cost) {
    this.id = option.id;
    this.name = option.name;
    this.unit = option.option_unit;
    this.costValue = cost.value;
    this.costValueInVND = cost.value * exchangeItem.ratio;
    this.optionName = option.option_name;
}
;

Cost.prototype.getValuePerNetricTon = function (tonPerContainer, numberOfContainer) {
    if (tonPerContainer == 0 || numberOfContainer == 0) {
        return 0;
    }
    switch (this.unit) {
        case "1 Pallet":
            // 1 total
            return this.costValue * 10 / tonPerContainer;
        case "Per Bag - 60kg":
            return this.costValue * 320 / tonPerContainer;
        case "Per Bag - Bulk":
            return this.costValue / tonPerContainer;
        case "Per Bag - Big":
            return this.costValue;
        case "Per Bag":
            // 1 total
            if (this.optionName == "bulk__bulk-bag") {
                return this.costValue / tonPerContainer;
            } else if (this.optionName == "big-bag__big-bag") {
                return this.costValue;
            }
            return this.costValue * 320 / tonPerContainer;
        case "26.25 Kwh/20'Cont.":
        case "20' Cont.":
        case "20 Cont.":
        case "16Kg/20' Cont.":
        case "6 sides/20' Cont.":
            // 4 total
            return this.costValue / tonPerContainer;
        case "Per B/L":
        case "Per/BL":
            // 2 total
            return this.costValue / tonPerContainer / numberOfContainer;
        case "$/Mt./%":
        case "$/Mt.":
        case "$/Mt./Mth.":
            return this.costValue;
        default:
            return 0;
    }
};

function Category() {

}

function Model(globalCostDict, fromBackend, categoryList) {
    this.globalCostDict = globalCostDict;
    this.optionalCategory = fromBackend.optionalCategory;
    this.documentCategory = fromBackend.documentCategory;
    this.packingItemCosts = fromBackend.packingItemCosts;
    this.documentCosts = fromBackend.documentCosts;
    this.fumigationProviderCategory = fromBackend.fumigationProviderCategory;
    this.fumigationInStore = fromBackend.fumigationInStore;
    this.categoryList = clone(categoryList);
    this.fromBackend = clone(fromBackend);
    if(globalCostDict) {
        this.isReady = true;
    } else {
        this.isReady = false;
    }
};

Model.prototype.ready = function() {
    return this.isReady;
};

Model.prototype.getSampleSents = function() {
    return this.fromBackend.sampleSents;
}

Model.prototype.getCost = function (optionName) {
    if (this.globalCostDict && this.globalCostDict[optionName]) {
        return this.globalCostDict[optionName];
    }
    return false;
};

Model.prototype.initDataForCategory = function (cat) {
    if (cat.options) {
        for (var i = 0; i < cat.options.length; i++) {
            // very important to keep option_name instead of optionName, because raw data use option_name
            cat.options[i] = new Cost(cat.options[i], this.getCost(cat.options[i].option_name));
        }
    }
    if (cat.children) {
        for (var i = 0; i < cat.children.length; i++) {
            this.initDataForCategory(cat.children[i]);
        }
    }
};

Model.prototype.getCategory = function (id) {
    for (var i = 0; i < this.categoryList.length; i++) {
        var res = this.getCategoryByObject(id, this.categoryList[i]);
        if (res) {
            return res;
        }
    }
    return false;
};

Model.prototype.getCategoryByObject = function (id, cat) {
    if (cat.id == id) {
        return cat;
    } else {
        for (var i = 0; i < cat.children.length; i++) {
            if (cat.children[i]) {
                var res = this.getCategoryByObject(id, cat.children[i]);
                if (res) {
                    return res;
                }
            }

        }
        return false;
    }
};

Model.prototype.getOptionByNameAndCategory = function (optionName, cat) {
    if (!cat.options || optionName == "") {
        return false;
    }
    for (var i = 0; i < cat.options.length; i++) {
        if (optionName == cat.options[i].optionName) {
            return cat.options[i];
        }
    }
    return false;
};

Model.prototype.getOptionByName = function (optionName) {
    for (var i = 0; i < this.categoryList.length; i++) {
        var res = this.getOptionByNameAndCategory(optionName, this.categoryList[i]);
        if (res) {
            return res;
        }
    }
    return false;
}

Model.prototype.getCopyOptionsByCategory = function (id) {
    if (!id || id == -1) {
        return [];
    }
    var _cat = this.getCategory(id);
    if (_cat === undefined) {
        return [];
    }
    var _opts = _cat.options;
    return _opts;
};


Model.prototype.initData = function () {
    this.isReady = false;
    var that = this;
    for (var i = 0; i < this.categoryList.length; i++) {
        this.initDataForCategory(this.categoryList[i]);
    }
    var reses = this.getCopyOptionsByCategory(this.optionalCategory);
    reses.forEach(function (item) {
        item._isSelected = that.packingItemCosts.indexOf(item.optionName) > -1;
    });

    var reses = this.getCopyOptionsByCategory(this.documentCategory);
    reses.forEach(function (item) {
        item._isSelected = that.documentCosts.indexOf(item.optionName) > -1;
    });
    var cost = this.getOptionByName(this.fumigationInStore);
    if (cost) {
        cost._isSelected = true;
    }
    this.isReady = true;
};

function Utility() {}

Utility.prototype.getRandom = function () {
    return Math.random().toString(36).substring(7);
};

Utility.prototype.roundTon = function (number) {
    return Math.round(number * 10) / 10;
};