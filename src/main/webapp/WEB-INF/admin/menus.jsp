
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
        
        <c:forEach var="item" items="${mymenus}">
            <p>${item.name} 
                <button class="addChild" data-parent="${item.id}">+</button> 
                <button class="edit" data-id="${item.id}" data-name="${item.name}" data-order="${item.order}" data-showinmainmenu="${item.showInMainMenu}" 
                        data-default="${item.default_}">Edit</button>
            </p> 
            <c:forEach var="child1" items="${item.menus}">
                <p>----- ${child1.name} --- url: ${child1.page.url}
                    <button class="addChild" data-parent="${child1.id}">+</button>
                    <button class="edit" data-id="${child1.id}" data-name="${child1.name}" data-order="${child1.order}" data-showinmainmenu="${child1.showInMainMenu}"
                            data-default="${child1.default_}">Edit</button>
                    <button class="editpage" data-pageid="${child1.page.id}" data-id="${child1.id}">Edit page</button>
                </p> 
                <c:forEach var="child2" items="${child1.menus}">
                    <p>----------- ${child2.name} --- url: ${child2.page.url}
                        <button class="edit" data-id="${child2.id}" data-name="${child2.name}" data-order="${child2.order}" data-showinmainmenu="${child2.showInMainMenu}"
                                data-default="${child2.default_}">Edit</button>
                        <button class="editpage" data-pageid="${child2.page.id}" data-id="${child2.id}">Edit page</button>
                    </p>
                </c:forEach>
            </c:forEach>
        </c:forEach>
            
        <div id="add-menu-form-container" style="display: none">
            <form action="${pageContext.request.contextPath}/new-menu.htm" method="POST" id="add-menu-form">
                <input type="hidden" name="parent" id="parent" />
                Name: <input type="text" name="name" id="name" ><br>
                Order: <input type="number" name="order" id="order" />
                Show in main menu: <input type="checkbox" name="showInMainMenu" id="showInMainMenu" />
            </form>
        </div>
                
        <div id="update-menu-form-container" style="display: none">
            <form action="${pageContext.request.contextPath}/update-menu.htm" method="POST" id="update-menu-form">
                <input type="hidden" name="id" id="updateid" />
                Name: <input type="text" name="name" id="updatename" ><br>
                Order: <input type="number" name="order" id="updateorder" /><br>
                Is default: <input type="checkbox" name="isDefault" id="updatedefault" /><br>
                Show in main menu: <input type="checkbox" name="showInMainMenu" id="updateShowInMainMenu" />
            </form>
        </div>
               
        <div id="assign-page-form-container" style="display: none">
            <form action="${pageContext.request.contextPath}/update-menu-assign-page.htm" method="POST" id="assign-page-form">
                <input type="hidden" name="id" id="assignid" />
                <select name="page" id="selectpage">
                    <c:forEach var="page" items="${mypages}">
                        <option value="${page.id}">
                            ${page.name} --- ${page.url}
                        </option>
                    </c:forEach>
                </select>
            </form>
        </div>
         
        <jsp:include page="../footer.jsp"></jsp:include>
        <script>
            $(document).ready(function() {
                console.log("ready");
                
                $(".addChild").click(function() {
                    var elm = $(this);
                    $("#parent").val(elm.data("parent"));
                    var dialog = $("#add-menu-form-container").dialog({
                        autoOpen: false,
                        height: 300,
                        width: 400,
                        modal: true,
                        buttons: {
                            "Add": function() {

                                $("#add-menu-form").submit();
                                dialog.dialog("close");
                            },
                        },
                        close: function() {
                            dialog.dialog("close");
                        }
                    });
                    dialog.dialog("open");
                });
                
                $(".edit").click(function() {
                    var elm = $(this);
                    $("#updateid").val(elm.data("id"));
                    $("#updatename").val(elm.data("name"));
                    $("#updateorder").val(elm.data("order"));
                    if(elm.data("showinmainmenu") === true) {
                        $("#updateShowInMainMenu").prop("checked", true);
                    } else {
                        $("#updateShowInMainMenu").prop("checked", false);
                    }
                    if(elm.data("default") === true) {
                        $("#updatedefault").prop("checked", true);
                    } else {
                        $("#updatedefault").prop("checked", false);
                    }
                    
                    
                    var dialog = $("#update-menu-form-container").dialog({
                        autoOpen: false,
                        height: 300,
                        width: 400,
                        modal: true,
                        buttons: {
                            "Update": function() {
                                $("#update-menu-form").submit();
                                dialog.dialog("close");
                            },
                        },
                        close: function() {
                            dialog.dialog("close");
                        }
                    });
                    dialog.dialog("open");
                });
                
                $(".editpage").click(function() {
                    var elm = $(this);
                    $("#selectpage").val($(elm).data("pageid"));
                    $("#assignid").val($(elm).data("id"));
                    
                    var dialog = $("#assign-page-form-container").dialog({
                        autoOpen: false,
                        height: 300,
                        width: 400,
                        modal: true,
                        buttons: {
                            "Assign": function() {
                                $("#assign-page-form").submit();
                                dialog.dialog("close");
                            },
                        },
                        close: function() {
                            dialog.dialog("close");
                        }
                    });
                    dialog.dialog("open");
                });
            });
        </script>
    </body>
</html>
