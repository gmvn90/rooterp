
<%-- 
    Document   : reports
    Created on : Jul 17, 2013, 9:38:37 AM
    Author     : minhdn
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title></title>
    </head>
    <body>
        <jsp:include page="header.jsp">
            <jsp:param name="url" value="17"/>
            <jsp:param name="page" value="6"/>
        </jsp:include>
        Shipping advice: ${info.shippingAdviceNumber} <br>
        Sample sents: ${info.sampleSentNumber} <br>
        shipping Instruction: ${info.shippingInstructionNumber}<br>
        <p>Claim: ${info.claimNumber}</p>
        <p>PI: ${info.processingInstructionNumber}</p>
        <p>sample type: ${info.sampleTypeRefNumber}<p>
        <jsp:include page="footer.jsp"/>
        
    </body>
</html>
