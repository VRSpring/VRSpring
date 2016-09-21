<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">

    <title>VRSpring</title>

    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">

    <script>
        function detail(path) {
            location = path;
        }
    </script>
</head>

<body>
<ul>
    <c:forEach var="map" items="${list}">
        <li>
            <img src='${map.path}' height="200" width="200" style="cursor:hand"
                 onclick="javascript:detail('${map.dir}?tourxml=${map.tourxml }&title=${map.name }');"/>
            <p>${map.name}</p>
        </li>
    </c:forEach>
</ul>
<img src="http://qrcoder.sinaapp.com?t=${map.dir}?tourxml=${map.tourxml }&title=${map.name }">
</body>
</html>
