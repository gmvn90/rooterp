<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Common Page</title>
        <style type="text/css">

        </style>
    </head>
    <body>
        <jsp:include page="header.jsp">
            <jsp:param name="url" value="37"/>
            <jsp:param name="page" value="85"/>
        </jsp:include>
        <div id="main_content" style="">  
            
        </div>
        <jsp:include page="footer.jsp"></jsp:include>
        <script type="text/javascript">
            $(document).ready(function() {
                $.sendRequest({
                    action: "test_1"
                }, function(msg) {
                    console.log(msg);
                    return true;
                });
            });
        </script>  
    </body>
</html>
