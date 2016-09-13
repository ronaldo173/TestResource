<html>
<%@ include file="/jspf/directive/page_content_encoding.jspf"%>
<%@ include file="/jspf/directive/taglib.jspf"%>
<%@ include file="/jspf/head.jspf"%>
<body>
<c:set var="title" value="Users edit" />
	<%@ include file="/jspf/header_languages_part.jspf"%>


	<div class="container">
		<div class="row">
			<div class="well">
				<div class="panel panel-info">
					<ul class="list-group">
						<li class="list-group-item"><a
							class="btn btn-primary btn-lg btn-block"
							href="${pageContext.request.contextPath}/" role="button">Main
								page</a></li>
						<li class="list-group-item"><a
							class="btn btn-primary btn-lg btn-block"
							href="${pageContext.request.contextPath}/Controller?command=myPage"
							role="button">My page</a></li>

					</ul>
				</div>
			</div>
		</div>
		<div class="row well">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h4>Users</h4>
				</div>

				<form method="post"
					action="${pageContext.request.contextPath}\Controller">
					<input type="hidden" name="command" value="usersControl" /> <input
						type="hidden" name="action" value="user_ban" />
					<table class="table table-bordered">
						<thead>
							<tr>
								<th>#</th>
								<th>login</th>
								<th>firstName</th>
								<th>lastName</th>
								<th>email</th>
								<th>roles</th>
								<th>Action</th>
							</tr>
						</thead>
						<tbody>

							<c:set var="num" value="0" />
							<c:forEach var="user" items="${allUsers}">
								<c:set var="num" value="${num+1}" />
								<tr>
									<th scope="row">${num}</th>
									<th>${user.login}</th>
									<td>${user.firstName}</td>
									<td>${user.lastName}</td>
									<td>${user.email}</td>
									<td>${user.roles}</td>
									<td><input type="hidden" name="userBan_${user.id}"
										value="0" /> <c:choose>
											<c:when test="${user.isBanned}">
												<div class="checkbox">
													<label><input type="checkbox"
														name="userBan_${user.id}" value="1" checked="checked">Banned</label>
												</div>
											</c:when>
											<c:otherwise>
												<div class="checkbox">
													<label><input type="checkbox"
														name="userBan_${user.id}" value="1">Not banned</label>
												</div>
											</c:otherwise>
										</c:choose></td>
								</tr>
							</c:forEach>

						</tbody>
					</table>
					<div class="form-group">
						<button type="submit" class="btn btn-success btn-block">Save</button>
					</div>
				</form>
			</div>
		</div>
		<div class="row">
			<%@ include file="/jspf/footer.jspf"%>
		</div>
	</div>

</body>
</html>