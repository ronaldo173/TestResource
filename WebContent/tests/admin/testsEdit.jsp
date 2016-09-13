<html>
<%@ include file="/jspf/directive/page_content_encoding.jspf"%>
<%@ include file="/jspf/directive/taglib.jspf"%>
<c:set var="title" value="Tests edit" />
<%@ include file="/jspf/head.jspf"%>
<body>
	<%@ include file="/jspf/header_languages_part.jspf"%>
	<jsp:include page="/SubjectsLoad"></jsp:include>


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

					<c:if test="${not empty updateTestId}">
						<div>
							<p>You can go and update test with ID: ${updateTestId }</p>
							<a
								href="${pageContext.request.contextPath}/Controller?command=modifyTest"
								class="btn btn-success btn-block" role="button">Go and
								update the test</a>
						</div>
					</c:if>
				</div>
			</c:if>
		</div>

		<div class="row">
			<div class="well">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h4>Subjects</h4>
					</div>
					<div class="panel-body">
						<div class="alert alert-danger ">
							<a class="panel-close close" data-dismiss="alert"></a> <i
								class="fa fa-coffee"></i> If you delete subject - all tests by
							that subject will also be deleted!!
						</div>


						<table class="table">
							<thead>
								<tr>
									<th>Id</th>
									<th>Name</th>
									<th>Save</th>
									<th>Delete</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="subject" items="${subjects}">
									<form method="post"
										action="${pageContext.request.contextPath}\Controller">
										<input type="hidden" name="command" value="testsModify" /> <input
											type="hidden" name="action_with" value="subjects_edit" />
										<tr>
											<td>${subject.id }</td>
											<td><input type="text" name="subject_name"
												class="form-control" required="required"
												placeholder="${subject.subject }"></td>
											<td><button name="update_subj" value="${subject.id }"
													type="submit" class="btn btn-primary">Update</button></td>
											<td><button name="delete_subj" value="${subject.id}"
													type="submit" class="btn btn-primary">Delete</button></td>
										</tr>
									</form>
								</c:forEach>


							</tbody>
						</table>
					</div>

					<div class="panel-body">

						<div class="alert alert-info ">
							<a class="panel-close close" data-dismiss="alert"></a> <i
								class="fa fa-coffee"></i>
							<div class="row">
								<form method="post" class="form-inline"
									action="${pageContext.request.contextPath}\Controller">
									<input type="hidden" name="command" value="testsModify" /> <input
										type="hidden" name="action_with" value="subjects_add" />

									<div class="form-group">
										<p>Add new subject:</p>
									</div>
									<div class="form-group">
										<input type="text" name="subject_name" class="form-control"
											placeholder="Subject name" required="required">
									</div>
									<div class="form-group">
										<button name="add_subj" type="submit" class="btn btn-primary">Add</button>
									</div>

								</form>
							</div>
						</div>
					</div>

				</div>
			</div>
		</div>

		<div class="row">
			<div class="row">
				<div class="well">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4>Tests</h4>
						</div>
						<div class="panel-body">
							<div class="alert alert-danger ">
								<a class="panel-close close" data-dismiss="alert"></a> <i
									class="fa fa-coffee"></i> If you delete test -> all questions,
								answers will also be deleted!
							</div>


							<table class="table">
								<thead>
									<tr>
										<th>Id</th>
										<th>Name</th>
										<th>Subject</th>
										<th>Description</th>
										<th>Difficulty</th>
										<th>Pass Time</th>
										<th>Questions</th>
										<th>Action</th>
										<th>Action</th>
									</tr>
								</thead>
								<tbody>

									<c:forEach var="test" items="${tests}">
										<form method="post"
											action="${pageContext.request.contextPath}\Controller">
											<input type="hidden" name="command" value="testsModify" /> <input
												type="hidden" name="action_with" value="tests_edit" />
											<tr>
												<td>${test.id}</td>
												<td>${test.name}</td>
												<td>${test.subject.subject}</td>
												<td>${test.description}</td>
												<td>${test.difficulty}</td>
												<td>${test.passTime}</td>
												<td>${fn:length(test.questions)}</td>

												<td><button name="update_test" value="${test.id }"
														type="submit" class="btn btn-primary">Update</button></td>
												<td><button name="delete_test" value="${test.id}"
														type="submit" class="btn btn-primary">Delete</button></td>
											</tr>
										</form>
									</c:forEach>
								</tbody>
							</table>
						</div>

						<div class="panel-body">

							<div class="alert alert-info ">
								<a class="panel-close close" data-dismiss="alert"></a> <i
									class="fa fa-coffee"></i> Add new test!
								<div>
									<form method="post"
										action="${pageContext.request.contextPath}\Controller">
										<input type="hidden" name="command" value="testsModify" /> <input
											type="hidden" name="action_with" value="test_add" />


										<div class="form-group">
											<label for="name">Name:</label> <input type="text"
												required="required" class="form-control" id="name"
												name="test_name">
										</div>

										<div class="form-group">
											<label for="subj">Subject:</label> <select
												class="form-control" name="test_subject_id" id="subj">
												<c:forEach items="${subjects}" var="subj">
													<option value="${subj.id}">${subj.subject}</option>
												</c:forEach>
											</select>
										</div>

										<div class="form-group">
											<label for="descr">Description:</label> <input type="text"
												required="required" class="form-control" id="descr"
												name="test_descr">
										</div>

										<div class="form-group">
											<label for="diff">Difficulty:</label> <select
												class="form-control" name="test_difficulty" id="diff">
												<c:forEach var="i" begin="1" end="5">
													<option>${i}</option>
												</c:forEach>
											</select>
										</div>
										<div class="form-group">
											<label for="passTime">Pass time:</label> <input type="text"
												required="required" class="form-control" id="passTime"
												name="test_passTime">
										</div>
										<button type="submit" class="btn btn-primary btn-block">Create
											test</button>
									</form>

								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>

		<div class="row">
			<%@ include file="/jspf/footer.jspf"%>
		</div>
</body>
</html>