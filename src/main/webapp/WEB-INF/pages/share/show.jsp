<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="/WEB-INF/pages/header.jsp"/>

<c:choose>
    <c:when test="${!empty share}">
        <h1>${share.mic}</h1>
        ${share.name}: &#36;${share.quote} (
        <a href="/hazelcast/share/${share.mic}/${share.quote + 1}/">+</a>
        /
        <a href="/hazelcast/share/${share.mic}/${share.quote - 1}/">-</a>
        )
    </c:when>
    <c:otherwise>
        Share not found.
    </c:otherwise>
</c:choose>

<jsp:include page="/WEB-INF/pages/footer.jsp"/>