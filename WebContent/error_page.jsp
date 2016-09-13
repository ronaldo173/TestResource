<%@ page isErrorPage="true"%>
<%@ page import="java.io.PrintWriter"%>
<%@ include file="/jspf/directive/page_content_encoding.jspf"%>
<%@ include file="/jspf/directive/taglib.jspf"%>

<html>

<c:set var="title" scope="page" value="ERROR" />
<%@ include file="/jspf/head.jspf"%>

<body>
	<div class="well">
		<div class="panel panel-info">
			<ul class="list-group">
				<li class="list-group-item"><a
					class="btn btn-primary btn-lg btn-block"
					href="${pageContext.request.contextPath}/" role="button">Go to
						the Test resource!</a></li>

			</ul>
		</div>
	</div>

	<div class="well">

		<table id="main-container">


			<tr>
				<td class="content">
					<%-- CONTENT --%>


					<h2 class="error">Error occurred, try to Log in again!</h2> <c:if
						test="${not empty param.message}">
						<h3>Error: ${param.message}</h3>
					</c:if> <c:if test="${not empty code}">
						<h3>Error code: ${code}</h3>
					</c:if> <c:if test="${not empty message}">
						<h3>${message}</h3>
					</c:if> <%-- if we get this page using forward --%> <c:if
						test="${not empty requestScope.errorMessage}">
						<h3>${requestScope.errorMessage}</h3>
					</c:if> <%-- CONTENT --%>
				</td>
			</tr>


		</table>
	</div>
</body>
</html>