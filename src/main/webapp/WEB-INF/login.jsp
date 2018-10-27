<%-- 
    Document   : login
    Created on : Jun 26, 2013, 4:46:49 PM
    Author     : kiendn
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Mill System</title>
    </head>
    <body>
        <jsp:include page="header.jsp">
            <jsp:param name="url" value="0"/>
        </jsp:include>
        <form id="login_form" method="post">
            <input type="hidden" id="action" name="action" />
            <div id="main_content">
                <h1 style="text-align: center">Welcome to ROOT COFFEE</h1>
                <div style="border: 1px solid black; width: 280px; height: 150px; margin: 175px auto 175px auto">
                    <li class="line">
                    <c:set var="message" value="${message}" />
                    <span class="left_element" style="text-align: center; color: red; width: 100%"><c:out value="${message}" /></span>
                    </li>
                    <ul style="width: 100%" class="text_display">
                        <li class="line">
                            <span class="left_element" style="margin-left: 20px">Username</span>
                            <input type="text" class="right_element" id="username" name="username"/>
                        </li>
                        <li class="line">
                            <span class="left_element" style="margin-left: 20px">Password</span>
                            <input type="password" class="right_element" id="txt_password" name="txt_password"/>
                            <input type="hidden" id="password" name="password" />
                        </li>
                        <li class="line">
                            <input type="button" style="margin-left: 20px" class="left_element" id="login" name="login" value="Login"/>
                            <input type="button" class="right_element" id="cancel" name="cancel"value="Cancel"/>
                        </li>
                    </ul>
                </div>
            </div>
        </form>
        <jsp:include page="footer.jsp"></jsp:include>
        <script>
            
            
        </script>
        <script type="text/javascript">
            $(document).ready(function() {
                $("#login").click(function() {
                    submitLogin();
                });
                
                

                $('body').keypress(function(event) {
                    if (event.which === 13) {
                        event.preventDefault();
                        submitLogin();
                    }
                });

                $("#cancel").click(function() {
                    $("#username").val("");
                    $("#txt_password").val("");
                    $("#password").val("");
                });
            });

            function submitLogin() {
                $('#password').val($.md5($('#txt_password').val()));
                $("#login_form").attr("action","login.htm");
                $("#login_form").submit();
            }
        </script>
    </body>
</html>
