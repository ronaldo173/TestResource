
<!-- CSS -->
<%@ include file="/jspf/header_languages_part.jspf"%>
<fmt:message key="registration" var="title" scope="page" />
<%@ include file="/jspf/head.jspf"%>
<link
	href="${pageContext.request.contextPath}/resources/css/form-elements.css"
	rel="stylesheet">
<link href="${pageContext.request.contextPath}/resources/css/style.css"
	rel="stylesheet">

<body
	BACKGROUND="${pageContext.request.contextPath}/resources/img/backgrounds/1.jpg" />
<!-- Top menu -->
<nav class="navbar navbar-inverse navbar-no-bg" role="navigation">
	<div class="container">
		<!-- Collect the nav links, forms, and other content for toggling -->
		<div class="collapse navbar-collapse" id="top-navbar-1">
			<ul class="nav navbar-nav navbar-right">
				<li><span class="li-text"><fmt:message key="write_to_me" />
				</span> <span class="li-social"> <a href="#"><i
							class="fa fa-facebook"></i></a> <a href="#"><i
							class="fa fa-twitter"></i></a> <a href="#"><i
							class="fa fa-envelope"></i></a> <a href="#"><i
							class="fa fa-skype"></i></a>
				</span></li>
			</ul>
		</div>
	</div>
</nav>

<!-- Top content -->
<div class="top-content">
		<div class="container">
			<div class="row">
				<div class="col-sm-8 col-sm-offset-2 text">
					<c:if test="${not empty validationRegRes}">
						<c:choose>
							<c:when test="${validationRegRes.isSuccess}">
								<h1>
									<fmt:message key="reg_success" />
								</h1>
								<a href="${pageContext.request.contextPath}"
									class="btn btn-info" role="button"><h1>
										<fmt:message key="log_now" />
									</h1></a>
							</c:when>
							<c:otherwise>
								<h1>
									<fmt:message key="not_registr" />
								</h1>

								<table class="table">
									<thead>
										<tr>
											<th><p class="text-danger">
													<fmt:message key="fields" />
												</p></th>
											<th><p class="text-danger">
													<fmt:message key="problem" />
												</p></th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${validationRegRes.map}" var="entry">
											<tr>
												<td><p class="text-danger">${entry.key}</p></td>
												<td><p class="text-danger">${entry.value}</p></td>
											</tr>
										</c:forEach>

									</tbody>
								</table>
							</c:otherwise>
						</c:choose>

					</c:if>
					<c:if test="${empty validationRegRes}">
						<h1>
							<fmt:message key="reg_to_resource" />
						</h1>
					</c:if>
				</div>
			</div>

			<div class="row">
				<div class="col-sm-6 phone">
					<img
						src="${pageContext.request.contextPath}/resources/img/iphone.png"
						alt="">
				</div>
				<div class="col-sm-5  form-box">
					<div class="form-top">
						<div class="form-top-left">
							<ul class="list-group">
								<li class="list-group-item"><a
									href="${pageContext.request.contextPath}" role="button"> <fmt:message
											key="or_login" />
								</a></li>
							</ul>
						</div>
						<div class="form-top-right">
							<i class="fa fa-pencil"></i>
						</div>
					</div>
					<div class="form-bottom">
						<form class="form-horizontal" method="post"
							action="${pageContext.request.contextPath}\Controller">
							<input type="hidden" name="command" value="registrationCommand" />

							<div class="form-group">
								<label for="login" class="cols-sm-2 control-label"><fmt:message
										key="login" /></label>
								<div class="cols-sm-10">
									<div class="input-group">
										<span class="input-group-addon"><i
											class="fa fa-users fa" aria-hidden="true"></i></span> <input
											type="text" class="form-control" name="login" id="login"
											placeholder="Enter your Login" required="required" />
									</div>
								</div>
							</div>


							<div class="form-group">
								<label for="first_name" class="cols-sm-2 control-label"><fmt:message
										key="first_name" /></label>
								<div class="cols-sm-10">
									<div class="input-group">
										<span class="input-group-addon"><i
											class="fa fa-user fa" aria-hidden="true"></i></span> <input
											type="text" class="form-control" name="first_name"
											id="first_name" placeholder="Enter your first name"
											required="required" />
									</div>
								</div>
							</div>

							<div class="form-group">
								<label for="last_name" class="cols-sm-2 control-label"><fmt:message
										key="last_name" /></label>
								<div class="cols-sm-10">
									<div class="input-group">
										<span class="input-group-addon"><i
											class="fa fa-user fa" aria-hidden="true"></i></span> <input
											type="text" class="form-control" name="last_name"
											id="last_name" placeholder="Enter your last name"
											required="required" />
									</div>
								</div>
							</div>

							<div class="form-group">
								<label for="email" class="cols-sm-2 control-label"> <fmt:message
										key="your_mail" /></label>
								<div class="cols-sm-10">
									<div class="input-group">
										<span class="input-group-addon"><i
											class="fa fa-envelope fa" aria-hidden="true"></i></span> <input
											type="text" class="form-control" name="email" id="email"
											placeholder="Enter your Email" required="required" />
									</div>
								</div>
							</div>


							<div class="form-group">
								<label for="password" class="cols-sm-2 control-label"><fmt:message
										key="password" /></label>
								<div class="cols-sm-10">
									<div class="input-group">
										<span class="input-group-addon"><i
											class="fa fa-lock fa-lg" aria-hidden="true"></i></span> <input
											type="password" class="form-control" name="password"
											id="password" placeholder="Enter your Password"
											required="required" />
									</div>
								</div>
							</div>

							<div class="form-group">
								<label for="confirm_password" class="cols-sm-2 control-label">
									<fmt:message key="confirm_password" />
								</label>
								<div class="cols-sm-10">
									<div class="input-group">
										<span class="input-group-addon"><i
											class="fa fa-lock fa-lg" aria-hidden="true"></i></span> <input
											type="password" class="form-control" name="confirm_password"
											id="confirm_password" placeholder="Confirm your Password"
											required="required" />
									</div>
								</div>
							</div>

							<div class="form-group ">
								<button type="submit"
									class="btn btn-primary btn-lg btn-block login-button">
									<fmt:message key="register" />
								</button>
							</div>
							<script src='https://www.google.com/recaptcha/api.js'></script>
							<div class="g-recaptcha form-group"
								data-sitekey="6Ld34CgTAAAAAOSGVBbkvSylUglxvYrYPkcVbWhY"></div>
						</form>
					</div>
				</div>
			</div>
		</div>
</div>



<!-- Javascript -->
<script
	src="${pageContext.request.contextPath}/resources/js/jquery-1.11.1.min.js"></script>
<script
	src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
<script
	src="${pageContext.request.contextPath}/resources/js/jquery.backstretch.min.js"></script>
<script
	src="${pageContext.request.contextPath}/resources/js/retina-1.1.0.min.js"></script>

</body>

</html>