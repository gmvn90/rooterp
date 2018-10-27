var globalPackings = [
    {id: 13, name: "Bulk", value: 21.6},
    {id: 14, name: "Big Bag", value: 20},
    {id: 15, name: "Jute Bag", value: 19.2},
    {id: 16, name: "PP Bag", value: 19.2},
];

var defaultTickedOptions = [
    'documents__phyto-sanitary-certificate',
    'documents__customs',
    'documents__ico',
    'documents__bl-fee',
];

var globalCostDict = {};
var shippingApp = angular.module('app', ['ui.router']);
shippingApp.directive('includeReplace', function () {
    return {
        require: 'ngInclude',
        restrict: 'A', /* optional */
        link: function (scope, el, attrs) {
            el.replaceWith(el.children());
        }
    };
});
shippingApp.config(function ($stateProvider) {
    var helloState = {
        name: 'main',
        url: '*path',
        templateUrl: "main.html",
        controller: "MainController",
        resolve: {

        }
    }

    $stateProvider.state(helloState);

});
shippingApp.run(function ($rootScope) {
    $rootScope.$on('$stateChangeError', function (event, toState, toParams, fromState, fromParams, error) {
        console.log("error", event, error);
    });
});

function getOptionsByCategory(id) {
    var _cat = getCategory(id);
    if (_cat === undefined) {
        return [];
    }
    return _cat.options;
}

var single_option = {name: "----", optionName: "---"};
var single_category = {name: "----", id: -1};

shippingApp.controller('ParentController', function ParentController($scope, $http, $rootScope, $window) {
    $scope.options = merge_options({}, $window.fromBackend);
    $scope.options.costNames = [];
    $scope.options.customCosts = $window.customCosts;
    $scope.globalPackings = globalPackings;
    var defaultNotifyParty = -1;
    $scope.currentNotifyParty = defaultNotifyParty;

    $scope.init = function () {
    };

    $scope.$watch("currentNotifyParty", function (newValue, oldValue) {
        console.log($scope.currentNotifyParty);
        if (newValue != oldValue && newValue != defaultNotifyParty) {
            var res = $http.post(getAbsolutePath() + "/shipping-instruction/" + $scope.options.id + "/add-notify-party.json", {
                id: newValue}, {withCredentials: true
            }).success(function (companyNameData) {
                console.log(companyNameData);
                $scope.options.notifyParties.push(companyNameData);
                $scope.currentNotifyParty = defaultNotifyParty;
            });
        }
    });
    
    $scope.isCostDisabled = function () {
        return isCompleted;
    };
    
    var canMainFormSubmit = function() {
        return $scope.options.totalTons > 0 && $scope.options.tonPerContainer > 0 && $scope.options.numberOfContainer > 0;
    };
    
    $scope.actionWhenMainFormSubmit = function(e) {
        if(! canMainFormSubmit()) {
            e.preventDefault();
            alert("Allocation ton and Mt./Cont. and Export containers must be greater than 0");
            return false;
        }
        return true;
    }
    
});

shippingApp.controller('MainController', function MainController($scope, $http, $rootScope, $window) {
    $scope.hasCost = false;
    $scope.exchangeItem = exchangeItem;
    $scope.singleDocumentCost = singleDocumentCost;
    $scope.utility = new Utility();
    $scope.data = {};
    $scope.model = new Model(false, fromBackend, categoryList);
    $scope.units = units;
    $scope.temp = {};
    $scope.temp.selectedUnit = "";
    $scope.customCost = {
        optionName: "",
        value: 0,
        optionUnit: "",
    };

    $scope.getCostPerMetricTon = function (cost) {
        var tonPerContainer = $scope.options.tonPerContainer;
        if (cost.optionUnit == "1 Pallet") {
            return cost.costValue * 10 / tonPerContainer;
        } else if (cost.optionUnit == "Per Bag - 60kg") {
            return cost.costValue * 320 / tonPerContainer
        } else if (cost.optionUnit == "Per Bag - Bulk") {
            return cost.costValue / tonPerContainer;
        } else if (cost.optionUnit == "Per Bag - Big") {
            return cost.costValue;
        } else if (cost.optionUnit == "20 Cont.") {
            return cost.costValue / tonPerContainer;
        } else if (cost.optionUnit == "Per/BL") {
            return cost.costValue / tonPerContainer / getNumberOfContainer();
        } else if (cost.optionUnit == "$/Mt.") {
            return cost.costValue;
        }
        return 0;
    };

    $scope.getTotalCustomCosts = function () {
        var total = 0;
        $scope.options.customCosts.forEach(function (item) {
            total += $scope.getCostPerMetricTon(item);
        });
        return total;
    };

    function getNumberOfContainer() {
        return $scope.options.numberOfContainer || 1;
    }

    function getClientId() {
        return globalClientId;
    }

    $scope.$watch(function () {
        return $scope.model.ready();
    }, function () {
        $scope.$watch("options.fumigationProviderCategory", function (newValue, oldValue) {
            if (newValue == -1) {
                $scope.options.fumigationDetailCost = "";
                $scope.options.fumigationInStore = "";
            } else {
                console.log($scope.model.ready(), $scope.options.fumigationProviderCategory);
                var cat = $scope.getCategory($scope.options.fumigationProviderCategory);
                console.log(cat);
                if (cat) {
                    if (newValue != oldValue) {
                        $scope.options.fumigationDetailCost = cat.options[0].optionName;
                    }
                    $scope.options.fumigationInStore = cat.options[cat.options.length - 1].optionName;
                    console.log($scope.options.fumigationInStore, cat.options[cat.options.length - 1]);
                }
            }
        });
    });


    $scope.$watch("options.client", function (newValue, oldValue) {
        var id = globalClientId;
        if (newValue) {
            id = newValue;
        }
        if (globalClientId || newValue) {
            getNewCost(id, getNumberOfContainer());
        }

    });

    $scope.$watch("options.totalTons", function (newValue, oldValue) {
        if (newValue != oldValue) {
            $scope.options.tonPerContainer = $scope.utility.roundTon($scope.options.totalTons / getNumberOfContainer());
        } else {
            console.log("nochange", newValue, oldValue);
        }
    });

    $scope.$watch("options.tonPerContainer", function (newValue, oldValue) {
        if (newValue != oldValue) {
            getNewCost(getClientId(), getNumberOfContainer());
        }
    });

    $scope.$watch("options.numberOfContainer", function (newValue, oldValue) {
        if (newValue != oldValue) {
            $scope.options.tonPerContainer = $scope.utility.roundTon($scope.getTons() / getNumberOfContainer());
            if (getClientId()) {
                getNewCost(getClientId(), getNumberOfContainer());
            }
        }
    });

    $scope.getTons = function () {
        var _tons = $scope.options.totalTons;
        return _tons;
    };

    $scope.getOptionalDocumentCost = function () {
        if ($scope.getTons() == 0 || getNumberOfContainer() == 0) {
            return 0;
        }
        return $scope.singleDocumentCost * $scope.options.optionalDocumentNumber / $scope.getTons();
    };

    $scope.getSampleSentsCost = function () {
        var sss = $scope.model.getSampleSents();
        var cost = 0.0;
        for (var i = 0; i < sss.length; i++) {
            if (sss[i].cost) {
                cost += sss[i].cost;
            }
        }
        if ($scope.getTons() > 0) {
            return cost / $scope.getTons();
        }
        return 0;
    };

    $scope.getCategory = function (id) {
        return $scope.model.getCategory(id);
    };

    $scope.getOptionsByCategory = function (id) {
        var _cat = $scope.getCategory(id);
        if (_cat === undefined) {
            return [];
        }
        return _cat.options;
    };

    $scope.getCopyOptionsByCategory = function (id) {
        return $scope.model.getCopyOptionsByCategory(id);
    };

    $scope.getChildrenByCategory = function (id) {
        var _cat = $scope.getCategory(id);
        if (_cat === undefined) {
            return [];
        }
        return _cat.children;
    };

    $scope.getChildrenByCategoryIncludingOptional = function (_id) {
        var children = $scope.getChildrenByCategory(_id);
        var _opts = [single_category];
        for (var i = 0; i < children.length; i++) {
            _opts.push(children[i]);
        }
        return _opts;
    };

    var getNewCost = function (id, numberOfContainer) {
        if (id != null && id != "" && id != undefined) {
            if ($scope.options.tonPerContainer != "" && $scope.options.tonPerContainer != null) {
                var res = $http.get(getAbsolutePath() + "/" + "getCostsIncludingCostPerTon/" + id + ".json", {
                    params: {
                        tonPerContainer: $scope.options.tonPerContainer,
                        options: "",
                        numberOfContainer: numberOfContainer,
                    }, withCredentials: true
                }).then(function (costs) {
                    $scope.data.globalCostDict = costs.data.costMap;
                    //initData();
                    $scope.model = new Model($scope.data.globalCostDict, fromBackend, categoryList);
                    $scope.model.initData();
                    if (Object.keys($scope.data.globalCostDict).length === 0) {
                        $scope.hasCost = false;
                    } else {
                        $scope.hasCost = true;
                    }
                });
            }
        } else {
            $scope.hasCost = false;
        }

    };

    $scope.getCostValueInUsd = function (optionName, isOptional) {
        if (optionName == "") {
            return 0;
        }
        var _cost = $scope.getOption(optionName);
        if (!isOptional || _cost._isSelected) {
            return _cost.getValuePerNetricTon($scope.options.tonPerContainer, $scope.options.numberOfContainer);
        }
        return 0;
    };

    $scope.getOption = function (optionName) {
        return $scope.model.getOptionByName(optionName);
    };

    $scope.getTotalCostInCategory = function (categoryId) {
        var result = 0;
        var _options = $scope.getOptionsByCategory(categoryId);
        if (!_options) {
            return 0;
        }
        _options.forEach(function (opt) {
            result += opt.getValuePerNetricTon($scope.options.tonPerContainer, $scope.options.numberOfContainer);
        });
        return result;
    };

    $scope.getTotalCostInOptionalCategory = function (categoryId) {
        var result = 0;
        var _options = $scope.getOptionsByCategory(categoryId);
        if (!_options) {
            return 0;
        }
        _options.forEach(function (opt) {
            if (opt._isSelected) {
                result += opt.getValuePerNetricTon($scope.options.tonPerContainer, $scope.options.numberOfContainer);
            }
        });
        return result;
    };

});