<html>
<%@ include file="/jspf/directive/page_content_encoding.jspf"%>
<%@ include file="/jspf/directive/taglib.jspf"%>
<c:set var="title" value="Test edit" />
<%@ include file="/jspf/head.jspf"%>
<body>
	<%@ include file="/jspf/header_languages_part.jspf"%>


	<div class="container">
		<c:if test="${not empty resultMessages}">
			<div class="alert alert-danger ">
				<a class="panel-close close" data-dismiss="alert"></a> <i
					class="fa fa-coffee"></i>
				<table class="table">
					<thead>
						<tr>
							<th>What happened</th>
							<th>Message</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="resMessage" items="${resultMessages}">
							<tr>
								<td>${resMessage.key}</td>
								<td>${resMessage.value}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</c:if>
	</div>

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
						href="${pageContext.request.contextPath}/tests/admin/testsEdit"
						role="button">Tests edit page</a></li>

				</ul>
			</div>
		</div>
	</div>

	<div class="row">
		<div class="well">
			<div class="panel panel-default">

				<div class="panel-heading">Tests</div>
				<div class="panel-body">
					<form method="post"
						action="${pageContext.request.contextPath}\Controller">
						<input type="hidden" name="command" value="modifyTest" /> <input
							type="hidden" name="action" value="test_edit" />
						<table class="table table-bordered">
							<thead>
								<tr>
									<th>ID</th>
									<th><fmt:message key="test_name" /></th>
									<th><fmt:message key="subject" /></th>
									<th><fmt:message key="difficulty" /></th>
									<th><fmt:message key="description" /></th>
									<th><fmt:message key="pass_time" /></th>
								</tr>
							</thead>
							<tbody>

								<tr>
									<td><input type="text" name="id" class="form-control"
										value="${testForUpdate.id}" disabled="disabled"></td>
									<td><input type="text" name="name" class="form-control"
										value="${testForUpdate.name}"></td>

									<td><select name="item_subjects" class="form-control">
											<c:forEach items="${subjects}" var="subj">

												<c:choose>
													<c:when test="${subj.id == testForUpdate.subject.id}">
														<option value="${subj.id}" selected="selected">${subj.subject}</option>
													</c:when>
													<c:otherwise>
														<option value="${subj.id}">${subj.subject}</option>
													</c:otherwise>
												</c:choose>
											</c:forEach>
									</select></td>


									<td><input type="text" name="difficulty"
										class="form-control" value="${testForUpdate.difficulty}"></td>
									<td><input type="text" name="description"
										class="form-control" value="${testForUpdate.description}"></td>
									<td><input type="text" name="passTime"
										class="form-control" value="${testForUpdate.passTime}"></td>
								</tr>
							</tbody>
						</table>
						<button name="update_test" type="submit"
							class="btn btn-primary btn-block">Update!</button>
					</form>
				</div>

			</div>
		</div>
	</div>

	<!-- table -->
	<div class="row">
		<div class="well">
			<div class="panel panel-default">
				<div class="panel-heading">Questions</div>


				<table class="table">
					<thead>
						<tr>
							<th>#</th>
							<th>ID</th>
							<th>Name</th>
							<th>Answers</th>
							<th>Action</th>
						</tr>
					</thead>
					<tbody>
						<c:set var="num" value="0" />
						<c:forEach var="question" items="${testForUpdate.questions}">
							<c:set var="num" value="${num+1}" />
							<form method="post"
								action="${pageContext.request.contextPath}\Controller">
								<input type="hidden" name="command" value="modifyTest" /> <input
									type="hidden" name="action" value="questions_edit" />
								<tr>
									<th scope="row"><c:out value="${num}" /></th>
									<th scope="row"><c:out value="${question.id}" /></th>
									<td>${question.body }</td>
									<td><select class="form-control">
											<c:forEach var="answer" items="${question.answers}">
												<option>${answer}</option>
											</c:forEach>
									</select></td>
									<td><button name="delete_question" value="${question.id}"
											type="submit" class="btn btn-primary">Delete</button></td>
								</tr>
							</form>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>

	<div class="row">
		<div class="well">
			<div class="panel panel-default">
				<div class="panel-heading">ADD QUESTION</div>

				<form method="post"
					action="${pageContext.request.contextPath}\Controller">
					<input type="hidden" name="command" value="modifyTest" /> <input
						type="hidden" name="action" value="questions_add" />
					<div class="alert alert-success">

						<div class="form-group">
							<label for="idTest">Test id:</label> <input type="text"
								class="form-control" value="${testForUpdate.id}"
								disabled="disabled" id="idTest">
						</div>
						<div class="form-group">
							<label for="question_body">Quest body:</label> <input type="text"
								class="form-control" name="question_body" id="question_body">
						</div>
						<div class="well">

							<c:forEach var="i" begin="1" end="4">
								<div class="form-group">
									<label for="answer">Answer ${i}:</label> <input type="text"
										class="form-control" name="answer_${i}" id="answer">

									<div class="form-check">
										<label class="form-check-label"> <input
											class="form-check-input" type="radio" name="answ_${i}"
											id="gridRadios${i}" value="correct"> Correct
										</label>
									</div>
									<div class="form-check">
										<label class="form-check-label"> <input
											class="form-check-input" type="radio" name="answ_${i}"
											id="gridRadios${i}_" value="wrong" checked> Wrong
										</label>
									</div>
								</div>

							</c:forEach>

						</div>
					</div>
					<button type="submit" class="btn btn-primary btn-block">ADD
						QUESTION</button>
				</form>

			</div>
		</div>

	</div>


</body>
</html>