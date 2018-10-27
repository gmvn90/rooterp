<%-- 
    Document   : header
    Created on : Jun 26, 2013, 5:25:42 PM
    Author     : kiendn
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>        
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <!--<link href="${base_web_url}/styles/reset.css" rel="stylesheet"/>-->
        <script type="text/javascript">
            var getDateFormatString = function () {
                return "${dateTimeFormat}";
            };

            var getFormatDate = function (str) {
                return $.format.date(new Date(str), getDateFormatString());
            };
            var base_web_url = "${pageContext.request.contextPath}";
            function getAbsolutePath() {
                return base_web_url;
            }

        </script>

        <link href="${base_web_url}/styles/select2.css" rel="stylesheet" />
        <link href="${base_web_url}/styles/datatable.css" rel="stylesheet"/>  
        <link href="${base_web_url}/styles/datatables.css" rel="stylesheet"/> 
        <link href="${base_web_url}/styles/font_icons.css" rel="stylesheet"/>  
        <link href="${base_web_url}/styles/demos.css" rel="stylesheet"/>
        <link href="${base_web_url}/styles/style.css?version=${version}" rel="stylesheet"/>
        <link href="${base_web_url}/styles/style_contract.css" rel="stylesheet"/>        
        <link href="${base_web_url}/styles/jquery-ui-1.10.1.custom.css" rel="stylesheet"/>
        <link href="${base_web_url}/styles/common.css?noCache=123456789" rel="stylesheet"/>
        <link href="${base_web_url}/styles/jquery.tablescroll.css" rel="stylesheet"/>
        <link href="${base_web_url}/styles/960.css" rel="stylesheet"/>
        <link href="${base_web_url}/styles/topics.css" rel="stylesheet"/>
        <link href="${base_web_url}/styles/jquery-ui-timepicker-addon.css" rel="stylesheet"/>

        <script src="${base_web_url}/js/jquery-1.9.1.js"></script>
        <script src="${base_web_url}/js/jquery.scrollTo.js"></script>
        <script src="${base_web_url}/js/barcode.js"></script>
        <script src="${base_web_url}/js/printElement.js"></script>
        <script src="${base_web_url}/js/printThis.js"></script>
        <script src="${base_web_url}/js/waypoints.js"></script>
        <script src="${base_web_url}/js/jquery.validate.js"></script>
        <script src="${base_web_url}/js/jquery.caret.min.js"></script>
        <script src="${base_web_url}/js/jquery.bind-first-0.2.0.js"></script>
        <script src="${base_web_url}/js/accounting.js"></script>
        <script src="${base_web_url}/js/jquery-ui.js"></script>
        <script src="${base_web_url}/js/jquery.dateFormat-1.0.js"></script>
        <script src="${base_web_url}/js/jquery.dataTables.js"></script>
        <script src="${base_web_url}/js/ColVis.js"></script>
        <script src="${base_web_url}/js/ColReorderWithResize.js"></script>
        <script src="${base_web_url}/js/md5.js"></script>
        <script type="text/javascript" src="${base_web_url}/js/jquery.blockUI.js"></script>
        <script src="${base_web_url}/js/jquery.iframe-transport.js"></script>
        <script src="${base_web_url}/js/jquery.fileupload.js"></script>
        <script src="${base_web_url}/js/datatables.plugins.js"></script>
        <script src="${base_web_url}/js/jquery.tablescroll.js"></script>
        <script src="${base_web_url}/js/jsrender.js"></script>
        <script src="${base_web_url}/js/jquery-barcode.js"></script>
        <script src="${base_web_url}/js/jquery.PrintArea.js"></script>
        <script src="${base_web_url}/js/jquery-ui-timepicker-addon.js"></script>
        <script src="${base_web_url}/js/jquery-ui-sliderAccess.js"></script>
        <script src="${base_web_url}/js/purl.js"></script>



        <script src="${base_web_url}/js/header.js?version=${version}"></script>
        <script src="${base_web_url}/js/jquery.jscroll.js"></script>
        <script src="${base_web_url}/js/select2.js"></script>
        <script src="${base_web_url}/js/scrollTable.js"></script>
        <script src="${base_web_url}/js/scrollTableFooter.js"></script>
        <style type="text/css">
            input, select, textarea {
                box-sizing: border-box;
                -moz-box-sizing: border-box;
                -webkit-box-sizing: border-box;
                -ms-box-sizing: border-box;
            }
        </style>

        <script src="${base_web_url}/js/scripts.js?version=${version}"></script>
        <script src="${base_web_url}/js/js.auth.js"></script>
        <script src="${base_web_url}/js/js.request.js?version=${version}"></script>
    </head>
    <body>
        <form id="header_form" action="Header" method="post">
            <input type="hidden" id="url" name="url" value="${param.url}" />
            <input type="hidden" id="page" name="page" value="${param.page}" />
            <input type="hidden" id="l_action" name="l_action" />
            <input type="hidden" id="user_id" name="user_id" value="${user.userName}" />
            <input type="hidden" id="path" value="${base_web_url}/"/>
        </form>
    </body>
    <div id="header">
        <div id="logo_section">
            <div id="logo1"></div>
            <div id="logo2"></div>
        </div>
        <div id="menu_section">
            <div id="menu_background1">
                <ul class="menu" id="menu_lv1">
                    <c:forEach var="item" items="${menus.menusLevel1}">
                        <li <c:if test="${item.selected}">class="chosen"</c:if>>
                            <a <c:if test="${item.isHome}">class="mpcth-icon-home"</c:if> href="${pageContext.request.contextPath}/${item.url}.htm">
                                <c:if test="${!item.isHome}">${item.name}</c:if>
                                </a>
                            </li>
                    </c:forEach>
                </ul>
            </div>

            <div id="menu_background2">
                <ul class="menu" id="menu_lv2">
                    <c:forEach var="item" items="${menus.menusLevel2}">
                        <li>
                            <a <c:if test="${item.selected}">class="chosen"</c:if> href="${pageContext.request.contextPath}/${item.url}.htm">
                                ${item.name}
                            </a>
                            <c:if test="${not empty item.children}">

                                <ul class="menu_lv3">
                                    <c:forEach var="child" items="${item.children}">
                                        <li>
                                            <a <c:if test="${child.selected}">class="chosen"</c:if> href="${pageContext.request.contextPath}/${child.url}.htm">
                                                ${child.name}
                                            </a>
                                        </li>
                                    </c:forEach>

                                </ul>
                            </c:if>
                        </li>
                    </c:forEach>
                </ul>
            </div>
        </div>
    </div>
    <div id="breadcrum" style="height: 36px;">
        <c:if test="${not empty userFullName}">
            <div style="float: right; height: 100%">
                <img class="border_1_solid_black profile_picture" src="/warehouse/images/maleLogo.jpg" style="display: inline-block;" width="30px" height="30px">
                <div style="display: inline-block; vertical-align: top; margin-top: 10px">
                    <a href="${base_web_url}/profile.htm" class="bold" id="span_fullname">${userFullName}</a>
                    <a href="${base_web_url}/logout.htm">(Logout)</a>
                </div>
            </div>
        </c:if>
    </div>
</html>
