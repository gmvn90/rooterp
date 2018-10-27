<html>
    <head>
        <style type="text/css">
            table {
                width: 100%;
                font-size: 10pt;
                border-collapse: collapse;
                font: Tahoma;
            }
            .underline-bottom {
                border-bottom: 1px solid black;
            }

            .total-row th{
                border-top: 1px solid black;
                height: 40px;
                vertical-align: top;
            }

            .header-row {
                height: 40px;
                vertical-align: bottom;
            }

            .header-sub {
                text-indent: 20px;
            }

            .align-right {
                text-align: right;
            }
        </style>

    </head>
    <body>
        <table>
            <thead>
                <tr>
                    <th style="width: 5%"></th>
                    <th colspan="${totalCol - 4}"></th>
                    <th style="width: 15%"></th>
                    <th style="width: 15%"></th>
                    <th style="width: 10%"></th>
                </tr>
            </thead>
            <tbody>


            <tr> <th class='underline-bottom' colspan='${totalCol}'>SW Commodities - Binh Duong Warehouse - Price List</th> </tr>
            <tr><td colspan='${totalCol - 2}'></td><td colspan="2">Update on: ${updateOn?date}</td></tr>
            <tr>  <th colspan='${totalCol}'>Client:</th>  </tr>
            <tr>  <td></td>  <td colspan='${totalCol - 2}'>${clientName}</td></tr>
            <tr style="height: 40px; vertical-align: top">  <td></td>  <td colspan='${totalCol - 2}'>${clientAddress}</td></tr>


            <tr style="text-align: center">
                <td colspan='${totalCol - 4}'></td> <td></td> <td class='underline-bottom'>Average Processing Wt. Loss</td>
                <td class='underline-bottom'>Average Reject Grade 3</td> <td class='underline-bottom'>Cost USD/Mt</td>
            </tr>
            <tr>
                <th colspan='${totalCol}'>Upgrade Type:</th>
            </tr>
            <#list piTypes as item>
            <tr> <td></td> <td colspan='${totalCol - 4}'>${item.name}</td>
                <td class="align-right">${item.totalProcessingWeighLoss?string(",##0.00")}%</td> <td class="align-right">${item.totalRejectGrade3?string(",##0.00")}%</td> <td class="align-right">${item.totalPrice?string(",##0.00")}</td>
            </tr>
            </#list>
            <tr>
                <td colspan="${totalCol}"></td>
            </tr>
            <tr>
                <td colspan="${totalCol}"></td>
            </tr>

            <tr>  <th colspan='${totalCol}'>Assumptions:</th>  </tr>
            <tr>  <td></td>  <td colspan='${totalCol - 2}'>F/X  VND/USD</td> <td>${ratio}</td>  </tr>
            <tr>  <td></td>  <td colspan='${totalCol - 2}'>Average Mt./20' Container</td> <td>20</td>  </tr>
            <tr style="height: 40px; vertical-align: top">  <td></td>  <td colspan='${totalCol - 2}'>Estimated Containers / Shipment</td> <td>1</td>  </tr>
            


            <tr>
                <td colspan="${totalCol}"></td>
            </tr>
            <tr style="text-align: center"> <td colspan='${totalCol - 4}'></td> <td class='underline-bottom'>Unit</td> <td class='underline-bottom'>Unit price VND</td> <td class='underline-bottom'>Unit price USD</td> <td class='underline-bottom'>Cost USD/Mt.</td></tr>

            <tr>
                <th colspan="${totalCol}">Handling:</th>
            </tr>
            <#list handlingCat.options as item>

                <tr>
                    <td></td> <td colspan='${totalCol - 4 - 1}'>${item.name}</td> <td>${item.optionUnit}</td> <td class="align-right">${(item.cost.costValue * ratio)?string(",###")}</td>
                    <td class="align-right">${item.cost.costValue?string(",##0.00")}</td> <td class="align-right">${item.cost.costPerTon?string(",##0.00")}</td>
                </tr>
            </#list>

            <tr>
                <th colspan="${totalCol}">Storage cost:</th>
            </tr>
            <#list storageCat.childOptions as item>
                <tr>
                    <td></td> <td colspan='${totalCol - 4 - 1}'>${item.name}</td> <td>${item.optionUnit}</td> <td class="align-right">${(item.cost.costValue * ratio)?string(",###")}</td>
                    <td class="align-right">${item.cost.costValue?string(",##0.00")}</td> <td class="align-right">${item.cost.costPerTon?string(",##0.00")}</td>
                </tr>
            </#list>

            <tr>
                <th colspan="${totalCol}">Optional package items:</th>
            </tr>
            <#list optionalPackageCat.options as item>
                <tr>
                    <td></td> <td colspan='${totalCol - 4 - 1}'>${item.name}</td> <td>${item.optionUnit}</td> <td class="align-right">${(item.cost.costValue * ratio)?string(",###")}</td>
                    <td class="align-right">${item.cost.costValue?string(",##0.00")}</td> <td class="align-right">${item.cost.costPerTon?string(",##0.00")}</td>
                </tr>
            </#list>


            <tr class='header-row'><th colspan='3'>FDW SWC-BD to FOB Cat Lai - Bulk:</th><td colspan='${totalCol - 6}'></td><th>21.60</th><th colspan="2">Mt/20' Cont.</th></tr>
            <tr><th class="header-sub" colspan="${totalCol}">Packing cost</th></tr>
            <#list packingBulkCat.options as item>
                <tr>
                    <td></td> <td colspan='${totalCol - 4 - 1}'>${item.name}</td> <td>${item.optionUnit}</td> <td class="align-right">${(item.cost.costValue * ratio)?string(",###")}</td>
                    <td class="align-right">${item.cost.costValue?string(",##0.00")}</td> <td class="align-right">${item.cost.costPerTon?string(",##0.00")}</td>
                </tr>
            </#list>
            <tr><th class="header-sub" colspan="${totalCol}">Loading and Transport cost</th></tr>
            <#list loadingAndTransport216.options as item>
                <tr>
                    <td></td> <td colspan='${totalCol - 4 - 1}'>${item.name}</td> <td>${item.optionUnit}</td> <td class="align-right">${(item.cost.costValue * ratio)?string(",###")}</td>
                    <td class="align-right">${item.cost.costValue?string(",##0.00")}</td> <td class="align-right">${costMap["cat216"][item.optionName]?string(",##0.00")}</td>
                </tr>
            </#list>
            <tr><th class="header-sub" colspan="${totalCol}">Document cost</th></tr>
            <#list documentCat216.options as item>
                <tr>
                    <td></td> <td colspan='${totalCol - 4 - 1}'>${item.name}</td> <td>${item.optionUnit}</td> <td class="align-right">${(item.cost.costValue * ratio)?string(",###")}</td>
                    <td class="align-right">${item.cost.costValue?string(",##0.00")}</td> <td class="align-right">${costMap["cat216"][item.optionName]?string(",##0.00")}</td>
                </tr>
            </#list>
            <tr class="total-row"><th colspan='${totalCol - 1}'>Total FDW SWC-BD to FOB Cat Lai - Bulk:</th><th class="align-right">${total216?string(",##0.00")}</th></tr>


            <tr class='header'><th colspan='3'>FDW SWC-BD to FOB Cat Lai - 1 Ton Export Big Bag:</th><th colspan='${totalCol - 6}'></th><th>20.00</th><th colspan="2">Mt/20' Cont.</th></tr>
            <tr><th class="header-sub" colspan="${totalCol}">Packing cost</th></tr>
            <#list packingBigCat.options as item>
                <tr>
                    <td></td> <td colspan='${totalCol - 4 - 1}'>${item.name}</td> <td>${item.optionUnit}</td> <td class="align-right">${(item.cost.costValue * ratio)?string(",###")}</td>
                    <td class="align-right">${item.cost.costValue?string(",##0.00")}</td> <td class="align-right">${item.cost.costPerTon?string(",##0.00")}</td>
                </tr>
            </#list>
            <tr><th class="header-sub" colspan="${totalCol}">Loading and Transport cost</th></tr>
            <#list loadingAndTransport20.options as item>
                <tr>
                    <td></td> <td colspan='${totalCol - 4 - 1}'>${item.name}</td> <td>${item.optionUnit}</td> <td class="align-right">${(item.cost.costValue * ratio)?string(",###")}</td>
                    <td class="align-right">${item.cost.costValue?string(",##0.00")}</td> <td class="align-right">${costMap["cat20"][item.optionName]?string(",##0.00")}</td>
                </tr>
            </#list>
            <tr><th class="header-sub" colspan="${totalCol}">Document cost</th></tr>
            <#list documentCat20.options as item>
                <tr>
                    <td></td> <td colspan='${totalCol - 4 - 1}'>${item.name}</td> <td>${item.optionUnit}</td> <td class="align-right">${(item.cost.costValue * ratio)?string(",###")}</td>
                    <td class="align-right">${item.cost.costValue?string(",##0.00")}</td> <td class="align-right">${costMap["cat20"][item.optionName]?string(",##0.00")}</td>
                </tr>
            </#list>
            <tr class="total-row"><th colspan='${totalCol - 1}'>Total FDW SWC-BD to FOB Cat Lai - 1 Ton Export Big Bag:</th><th class="align-right">${total20?string(",##0.00")}</th></tr>


            <tr class='header'><th colspan='3'>FDW SWC-BD to FOB Cat Lai - 60Kg. Jute Bags:</th><td colspan='${totalCol - 6}'></td><th>19.20</th><th colspan="2">Mt/20' Cont.</th></tr>
            <tr><th class="header-sub" colspan="${totalCol}">Packing cost</th></tr>
            <#list packingJuteBag.options as item>
                <tr>
                    <td></td> <td colspan='${totalCol - 4 - 1}'>${item.name}</td> <td>${item.optionUnit}</td> <td class="align-right">${(item.cost.costValue * ratio)?string(",###")}</td>
                    <td class="align-right">${item.cost.costValue?string(",##0.00")}</td> <td class="align-right">${item.cost.costPerTon?string(",##0.00")}</td>
                </tr>
            </#list>
            <tr><th class="header-sub" colspan="${totalCol}">Loading and Transport cost</th></tr>
            <#list loadingAndTransport192Jute.options as item>
                <tr>
                    <td></td> <td colspan='${totalCol - 4 - 1}'>${item.name}</td> <td>${item.optionUnit}</td> <td class="align-right">${(item.cost.costValue * ratio)?string(",###")}</td>
                    <td class="align-right">${item.cost.costValue?string(",##0.00")}</td> <td class="align-right">${costMap["cat192Jute"][item.optionName]?string(",##0.00")}</td>
                </tr>
            </#list>
            <tr><th class="header-sub" colspan="${totalCol}">Document cost</th></tr>
            <#list documentCat192Jute.options as item>
                <tr>
                    <td></td> <td colspan='${totalCol - 4 - 1}'>${item.name}</td> <td>${item.optionUnit}</td> <td class="align-right">${(item.cost.costValue * ratio)?string(",###")}</td>
                    <td class="align-right">${item.cost.costValue?string(",##0.00")}</td> <td class="align-right">${costMap["cat192Jute"][item.optionName]?string(",##0.00")}</td>
                </tr>
            </#list>
            <tr class="total-row"><th colspan='${totalCol - 1}'>Total FDW SWC-BD to FOB Cat Lai - 60Kg. Jute Bags:</th><th class="align-right">${total192Jute?string(",##0.00")}</th></tr>


            <tr class='header'><th colspan='3'>FDW SWC-BD to FOB Cat Lai - 60Kg. PP Bags:</th><td colspan='${totalCol - 6}'></td><th>19.20</th><th colspan="2">Mt/20' Cont.</th></tr>
            <tr><th class="header-sub" colspan="${totalCol}">Packing cost</th></tr>
            <#list packingPPBag.options as item>
                <tr>
                    <td></td> <td colspan='${totalCol - 4 - 1}'>${item.name}</td> <td>${item.optionUnit}</td> <td class="align-right">${(item.cost.costValue * ratio)?string(",###")}</td>
                    <td class="align-right">${item.cost.costValue?string(",##0.00")}</td> <td class="align-right">${item.cost.costPerTon?string(",##0.00")}</td>
                </tr>
            </#list>
            <tr><th class="header-sub" colspan="${totalCol}">Loading and Transport cost</th></tr>
            <#list loadingAndTransport192PP.options as item>
                <tr>
                    <td></td> <td colspan='${totalCol - 4 - 1}'>${item.name}</td> <td>${item.optionUnit}</td> <td class="align-right">${(item.cost.costValue * ratio)?string(",###")}</td>
                    <td class="align-right">${item.cost.costValue?string(",##0.00")}</td> <td class="align-right">${costMap["cat192PP"][item.optionName]?string(",##0.00")}</td>
                </tr>
            </#list>
            <tr><th class="header-sub" colspan="${totalCol}">Document cost</th></tr>
            <#list documentCat192PP.options as item>
                <tr>
                    <td></td> <td colspan='${totalCol - 4 - 1}'>${item.name}</td> <td>${item.optionUnit}</td> <td class="align-right">${(item.cost.costValue * ratio)?string(",###")}</td>
                    <td class="align-right">${item.cost.costValue?string(",##0.00")}</td> <td class="align-right">${costMap["cat192PP"][item.optionName]?string(",##0.00")}</td>
                </tr>
            </#list>
            <tr class="total-row"><th colspan='${totalCol - 1}'>Total FDW SWC-BD to FOB Cat Lai - 60Kg. PP Bags:</th><th class="align-right">${total192PP?string(",##0.00")}</th></tr>
            </tbody>
        </table>
    </body>
</html>