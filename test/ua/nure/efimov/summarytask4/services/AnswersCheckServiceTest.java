package ua.nure.efimov.summarytask4.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import ua.nure.efimov.summarytask4.beans.AnswerCheckEntry;
import ua.nure.efimov.summarytask4.beans.ResultTestPass;
import ua.nure.efimov.summarytask4.entity.Answer;
import ua.nure.efimov.summarytask4.entity.Question;
import ua.nure.efimov.summarytask4.exception.BusinessException;

public class AnswersCheckServiceTest {

	@Test(expected = BusinessException.class)
	public void emptyListTestParseResultsToEntries() throws Exception {
		List<AnswerCheckEntry> parseResultsToEntries = new AnswersCheckService()
				.parseResultsToEntries(new ArrayList<>());
		Assert.assertNull(parseResultsToEntries);
	}

	@Test
	public void correctParamTestParseResultsToEntries() throws BusinessException {
		List<String> paramsList = new ArrayList<>();
		Collections.addAll(paramsList, "Answer_aId_36_qId_11", "Answer_aId_37_qId_11", "Answer_aId_40_qId_12",
				"Answer_aId_42_qId_13", "Answer_aId_44_qId_13");

		AnswersCheckService checkService = new AnswersCheckService();
		List<AnswerCheckEntry> result = checkService.parseResultsToEntries(paramsList);

		AnswerCheckEntry answerEntry0 = result.get(0);
		AnswerCheckEntry expected = new AnswerCheckEntry();
		expected.setIdQuestion(11);
		expected.getIdAnswersList().add(36);
		expected.getIdAnswersList().add(37);

		Assert.assertEquals(expected, answerEntry0);
	}

	@Test(expected = BusinessException.class)
	public void wrongParamTestParseResultsToEntries() throws BusinessException {
		List<String> paramsList = new ArrayList<>();
		Collections.addAll(paramsList, "Answer_aId_asd_qId_ads", "Answer_aId_37_qId_11", "Answer_aId_40_qId_12", "as",
				"asds");

		AnswersCheckService checkService = new AnswersCheckService();
		List<AnswerCheckEntry> result = checkService.parseResultsToEntries(paramsList);
	}

	@Test(expected = BusinessException.class)
	public void nulArgumentTestParseQuestionsToEntries() throws Exception {
		List<AnswerCheckEntry> parseResultsToEntries = new AnswersCheckService().parseQuestionsToEntries(null);
		Assert.assertNull(parseResultsToEntries);
	}

	@Test
	public void correctParamTestParseQuestionsToEntries() throws BusinessException {
		Question question = new Question();
		question.setId(1);
		List<Answer> answers = question.getAnswers();
		Answer answer = new Answer();
		answer.setId(2);
		answer.setCorrect(true);
		Answer answer2 = new Answer();
		answer2.setId(3);
		answer2.setCorrect(false);
		Answer answer3 = new Answer();
		answer3.setId(4);
		answer3.setCorrect(true);

		answers.add(answer);
		answers.add(answer2);
		answers.add(answer3);
		List<Question> questions = new ArrayList<>();
		questions.add(question);

		List<AnswerCheckEntry> entries = new AnswersCheckService().parseQuestionsToEntries(questions);
		System.out.println(entries);
	}

	@Test(expected = BusinessException.class)
	public void testCheckResult() throws BusinessException {
		List<String> paramsList = new ArrayList<>();
		Collections.addAll(paramsList, "Answer_aId_36_qId_11", "Answer_aId_37_qId_11", "Answer_aId_44_qId_13");

		AnswersCheckService checkService = new AnswersCheckService();
		List<AnswerCheckEntry> listEntries = checkService.parseResultsToEntries(paramsList);
		ResultTestPass checkResult = checkService.checkResult(listEntries, -1);
	}
}
