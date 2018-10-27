<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
    <title>Shipping advice</title>
    <style type="text/css">
        table.advice_detail {
            font-size: 8.5pt;
        }

        table.advice_detail td.title {
            font-weight: bolder;
        }

        table.advice_detail tr.section {
            font-style: italic;
            height: 30px;
            vertical-align: bottom;
        }

        table.si_wn_detail {
            color: #333;
            width: 100%;
            border-collapse: collapse;
            border-spacing: 0;
        }

        table.si_wn_detail td {
            border-bottom: 1px solid black;
            height: 20px;
        }

        table.si_wn_detail tr.head {
            background: #DFDFDF; /* Darken header a bit */
            font-weight: bold;
            border-bottom: 1px solid black;
        }

        table.si_wn_detail tr.foot {
            background: aliceblue; /* Darken header a bit */
            font-weight: bold;
            border-bottom: 0px solid black;
        }

        table.si_wn_detail th.text {
            text-align: left;
        }

        table.si_wn_detail td.text {
            text-align: left;

        }

        table.si_wn_detail th.number {
            text-align: right;
        }

        table.si_wn_detail td.number {
            text-align: right;

        }

        table.si_wn_detail td.number input {
            text-align: right;

        }

        td {
            padding: 0 !important;
        }

        #info_left {
            float: left;
        }

        #info_right {
            float: right; font-size: 9pt;
        }

        #info_name {
            font-size: 17pt;
            margin-bottom: 10px;
        }

        #info_subname {
            font-size: 13pt;
        }
    </style>
</head>
<body>

<div id="main_content"
     style="width: 700px; font-size: 8pt !important; font: 13px/1.65em 'HelveticaNeue', 'Helvetica Neue',Helvetica,Arial,sans-serif">
    <div style="width: 100%; height: 65px;">

        <div id="info_left">
            <div id="info_name">
                SUNWAH COMMODITIES
            </div>
            <div id="info_subname">
                SUNWAH GROUP
            </div>
        </div>

        <div id="info_right">
            <div>SUNWAH COMMODITIES (VIET NAM)</div>
            <div>Sunwah Tower, 115 Nguyen Hue Blvd,</div>
            <div>Dist, 1 Hochiminh City, Viet Nam</div>
            <div>T: 3 8 27 8638 - F: 3 8 27 8639</div>
        </div>

    </div>

    <hr/>
    <h1 style="text-align: center;">SHIPPING ADVICE</h1>

    <table class="advice_detail" style="">
        <tbody>
        <tr class="section">
            <td colspan="7">Claim Details:</td>
        </tr>
        <tr>
            <td></td>
            <td class="title" align="right">SI Ref.:</td>
            <td class="content" align="left">${si.refNumber}</td>
            <td class="title" align="right">SI Date:</td>
            <td class="content" align="left">${(si.date?datetime?string(dateTimeAMPMFormat))!}</td>
            <td colspan="2"></td>
        </tr>
        <tr>
            <td></td>
            <td class="title" align="right">Shipping Advice Ref.:</td>
            <td class="content" align="left">${si.shippingAdviceRefNumber}</td>
            <td class="title" align="right">SA Date:</td>
            <td class="content" align="left">${(si.shippingAdviceDate?datetime?string(dateTimeAMPMFormat))!}</td>
            <td colspan="2"></td>
        </tr>

        <tr class="section">
            <td colspan="7">Party Details:</td>
        </tr>
        <tr>
            <td></td>
            <td class="title" align="right">Client:</td>
            <td class="content" align="left">${si.client}</td>
            <td class="title" align="right">Client Ref.:</td>
            <td class="content" align="left">${si.clientRef}</td>
            <td colspan="2"></td>
        </tr>
        <tr>
            <td></td>
            <td class="title" align="right">Supplier:</td>
            <td class="content" align="left">${si.supplier}</td>
            <td class="title" align="right">Supplier Ref.:</td>
            <td class="content" align="left">${si.supplierRef}</td>
            <td colspan="2"></td>
        </tr>
        <tr>
            <td></td>
            <td class="title" align="right">Shipper:</td>
            <td class="content" align="left">${si.shipper}</td>
            <td class="title" align="right">Shipper Ref.:</td>
            <td class="content" align="left">${si.shipperRef}</td>
            <td colspan="2"></td>
        </tr>
        <tr>
            <td></td>
            <td class="title" align="right">Buyer:</td>
            <td class="content" align="left">${si.buyer}</td>
            <td class="title" align="right">Buyer Ref.:</td>
            <td class="content" align="left">${si.buyerRef}</td>
            <td colspan="2"></td>
        </tr>
        <tr>
            <td></td>
            <td class="title" align="right">Consignee:</td>
            <td class="content" align="left">${(si.consignee)!""}</td>
            <td colspan="4"></td>
        </tr>
        <tr>
            <td></td>
            <td class="title" align="right">Notify Party:</td>
            <td class="content" align="left"></td>
            <td colspan="4"></td>
        </tr>
        <tr class="section">
            <td colspan="7">Shipping Details:</td>
        </tr>
        <tr>
            <td></td>
            <td class="title" align="right">B/L Number:</td>
            <td class="content" align="left">${si.blNumber}</td>
            <td class="title" align="right">B/L Date:</td>
            <td class="content" align="left">${(si.blDate?date)!}</td>
            <td colspan="2"></td>
        </tr>
        <tr>
            <td></td>
            <td class="title" align="right">Container Status:</td>
            <td class="content" align="left">${si.containerStatus}</td>
            <td colspan="4"></td>
        </tr>
        <tr>
            <td></td>
            <td class="title" align="right">Feeder Vessel:</td>
            <td class="content" align="left">${si.feederVessel}</td>
            <td class="title" align="right">Etd:</td>
            <td class="content" align="left">${(si.feederEts?date)!}</td>
            <td class="title" align="right">Eta:</td>
            <td class="content" align="left">${(si.feederEta?date)!}</td>
        </tr>
        <tr>
            <td></td>
            <td class="title" align="right">Port of Loading:</td>
            <td class="content" align="left">${(si.loadingPort)!}</td>
        </tr>
        <tr>
            <td></td>
            <td class="title" align="right">Transit Port:</td>
            <td class="content" align="left">${(si.transitPort)!}</td>
            <td colspan="4"></td>
        </tr>
        <tr>
            <td></td>
            <td class="title" align="right">Mother Vessel:</td>
            <td class="content" align="left">${si.oceanVessel}</td>
            <td class="title" align="right">Etd:</td>
            <td class="content" align="left">${(si.oceanEts?date)!}</td>
            <td class="title" align="right">Eta:</td>
            <td class="content" align="left">${(si.oceanEta?date)!}</td>
        </tr>
        <tr>
            <td></td>
            <td class="title" align="right">Port of Discharge:</td>
            <td class="content" align="left">${(si.destination)!}</td>
            <td colspan="4"></td>
        </tr>
        <tr>
            <td></td>
            <td class="title" align="right">ICO Number:</td>
            <td class="content" align="left">${(si.icoNumber)!}</td>
            <td colspan="4"></td>
        </tr>

        <tr class="section">
            <td colspan="7">Shipping Goods:</td>
        </tr>
        <tr>
            <td></td>
            <td class="title" align="right">Origin:</td>
            <td class="content" align="left">${si.origin}</td>
            <td colspan="4"></td>
        </tr>
        <tr>
            <td></td>
            <td class="title" align="right">Quality:</td>
            <td class="content" align="left">${si.quality}</td>
            <td colspan="4"></td>
        </tr>

        <tr>
            <td></td>
            <td class="title" align="right">Remarks:</td>
            <td colspan="3" class="content" align="left">
                <textarea class="" style="width: 475px; height: 75px" name="" id="sa_remark" rows="" cols="">${si.remark}</textarea>
            </td>
            <td colspan="2"></td>
        </tr>
        </tbody>
    </table>

    <table class="si_wn_detail" style="margin-top: 20px;">
        <tbody>
        <tr class="head">
            <th class="text" colspan="3">Container Details:</th>
            <th class="text" colspan="3">Shipped Weight Details:</th>
        </tr>
        <tr class="head">
            <th class="text">Container No.:</th>
            <th class="text">Seal No.:</th>
            <th class="text">Bags:</th>
            <th class="number">Gross Weight Mt.</th>
            <th class="number">Tare Weight Mt.</th>
            <th class="number">Net Weight Mt.</th>
        </tr>
        <#list aggegrate.contractWeightNotes as item>
        <tr>
            <td class='text'>${item.containerNo}</td>
            <td class='text'>${item.sealNo}</td>
            <td class='text wnbag'>${item.noOfBags}</td>
            <td class='number wngross'>${item.grossWeight}</td>
            <td class='number wntare'>${item.tareWeight}</td>
            <td class='number wnnet'>${item.netWeight}</td>
        </tr>
        </#list>
        <tr class='foot'>
            <th class='number'></th>
            <th class='text'>Total:</th>
            <th class='text' id='twnbag'>${aggegrate.totalContractWeightNotes.noOfBags}</th>
            <th class='number' id='twngross'>${aggegrate.totalContractWeightNotes.grossWeight}</th>
            <th class='number' id='twntare'>${aggegrate.totalContractWeightNotes.tareWeight}</th>
            <th class='number' id='twnnet'>${aggegrate.totalContractWeightNotes.netWeight}</th>
        </tr>


        </tbody>
    </table>
</div>
</body>

</html>