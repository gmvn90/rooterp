

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
        <button class="addChild" data-page="">+</button> 
        <c:forEach var="item" items="${mypages}">
            <p>${item.name} --- url: ${item.url}
                <button class="addChild" data-page="${item.id}">+</button> 
                <button class="edit" data-id="${item.id}" data-name="${item.name}" data-url="${item.url}">Edit</button>
            </p> 
            <c:forEach var="child1" items="${item.pages}">
                <p>----- ${child1.name} 
                    <c:if test="${not empty child1.url}">
                    --- url: ${child1.url}
                    <button class="addChild" data-page="${child1.id}">+</button>
                    </c:if>
                    <button class="edit" data-id="${child1.id}" data-name="${child1.name}" data-url="${child1.url}">Edit</button>
                </p> 
                <c:forEach var="child2" items="${child1.pages}">
                    <p>----------- ${child2.name}
                        <c:if test="${not empty child2.url}">
                        --- url: ${child2.url}
                        </c:if>
                        <button class="edit" data-id="${child2.id}" data-name="${child2.name}" data-url="${child2.url}">Edit</button>
                    </p>
                </c:forEach>
            </c:forEach>
        </c:forEach>
            
        <div id="add-page-form-container" style="display: none">
            <form action="${pageContext.request.contextPath}/new-page.htm" method="POST" id="add-page-form">
                <input type="hidden" name="page" id="parent" />
                Name: <input type="text" name="name" id="name" ><br>
                Url: <input name="url" id="url" />
            </form>
        </div>
                
        <div id="update-page-form-container" style="display: none">
            <form action="${pageContext.request.contextPath}/update-page.htm" method="POST" id="update-page-form">
                <input type='hidden' name="id" id='updateid' />
                Name: <input type="text" name="name" id="updatename" ><br>
                Url: <input name="url" id="updateurl" />
            </form>
        </div>
         
        <jsp:include page="../footer.jsp"></jsp:include>
        <script>
            $(document).ready(function() {
                console.log("ready");
                
                $(".addChild").click(function() {
                    var elm = $(this);
                    $("#parent").val(elm.data("page"));
                    console.log($("#page").val());
                    var dialog = $("#add-page-form-container").dialog({
                        autoOpen: false,
                        height: 150,
                        width: 300,
                        modal: true,
                        buttons: {
                            "Add": function() {

                                $("#add-page-form").submit();
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
                    $("#updateurl").val(elm.data("url"));
                    var dialog = $("#update-page-form-container").dialog({
                        autoOpen: false,
                        height: 150,
                        width: 300,
                        modal: true,
                        buttons: {
                            "Update": function() {
                                $("#update-page-form").submit();
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
