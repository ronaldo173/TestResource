<%@ include file="/jspf/directive/page_content_encoding.jspf"%>
<%@ include file="/jspf/directive/taglib.jspf"%>

<html>
<body
	BACKGROUND="${pageContext.request.contextPath}/resources/img/backgrounds/2.jpg" />
<%@ include file="/jspf/header_languages_part.jspf"%>
<!-- set title depends on language -->
<fmt:message key="login_title" var="title" scope="page" />
<%@ include file="/jspf/head.jspf"%>


<div class="container">
<div class="inner-bg">
		<div class="row">

			<div class="col-sm-6 col-md-4 col-md-offset-4">
				<h1 class="text-center login-title">
					<fmt:message key="autorization" />
				</h1>
				<div class="account-wall">
					<form class="form-signin" method="post" action="j_security_check">
						<input type="text" name="j_username" class="form-control"
							placeholder=<fmt:message key="login" /> required="required">
						<input type="password" name="j_password" class="form-control"
							placeholder=<fmt:message key="password" /> required="required">
						<button class="btn btn-lg btn-primary btn-block" type="submit">
							<fmt:message key="enter" />
						</button>

					</form>
				</div>
				<a class="bg-info"
					href="${pageContext.request.contextPath}\registration"
					class="text-center new-account"><fmt:message
						key="createAccount" /> </a>
			</div>


		</div>
	</div>
</div>
</body>
</html>