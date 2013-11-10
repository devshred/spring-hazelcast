<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="/WEB-INF/pages/header.jsp"/>

<c:choose>
    <c:when test="${empty shares}">
        No shares found. <a href="/hazelcast/init">init db.</a>
    </c:when>
    <c:otherwise>
        <table class="gridtable">
            <tr>
                <th>MIC</th>
                <th>Company</th>
                <th>Quote</th>
                <th></th>
            </tr>
            <c:forEach var="share" items="${shares}">
                <tr>
                    <td><b>${share.mic}</b></td>
                    <td>${share.name}</td>
                    <td>${share.quote}</td>
                    <td><a href="/hazelcast/share/${share.mic}/">show</a></td>
                </tr>
            </c:forEach>
        </table>
    </c:otherwise>
</c:choose>

<jsp:include page="/WEB-INF/pages/footer.jsp"/>