package ua.nure.efimov.summarytask4.db.mysql;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import ua.nure.efimov.summarytask4.db.dao.GenericDao;
import ua.nure.efimov.summarytask4.db.dao.Identified;
import ua.nure.efimov.summarytask4.db.dao.jdbc.impl.DaoUtils;
import ua.nure.efimov.summarytask4.entity.TestQuestions;

@RunWith(Parameterized.class)
public abstract class GenericDaoTest<Context> {

	/**
	 * Logger.
	 */
	private static final Logger LOGGER = Logger.getLogger(GenericDaoTest.class);

	/**
	 * Класс тестируемого дао объекта.
	 */
	protected Class<? extends Identified<Integer>> daoClass;

	/**
	 * Экземпляр доменного объекта, которому не соответствует запись в системе
	 * хранения.
	 */
	protected Identified<Integer> notPersistedDto;

	/**
	 * Экземпляр тестируемого дао объекта.
	 */
	@SuppressWarnings("rawtypes")
	public abstract GenericDao dao();

	/**
	 * public interface GenericDao<T extends Identified<PK>, PK extends
	 * Serializable> {
	 */

	/**
	 * Контекст взаимодействия с системой хранения данных.
	 */
	public abstract Context context();

	@Test
	@SuppressWarnings("unchecked")
	public void testGetByPK() throws Exception {

		Identified<Integer> identifiedFirstObj = (Identified<Integer>) dao().getAll().get(0);
		Integer idForGetByPk = identifiedFirstObj.getId();
		Identified<Integer> identifiedByPK = dao().getByPK(idForGetByPk);

		LOGGER.info("testGetByPK: id:" + idForGetByPk + " get: " + identifiedByPK);
		Assert.assertNotNull(identifiedByPK);
	}

	@Test
	public void testGetAll() throws Exception {
		LOGGER.info("testGetAll");

		@SuppressWarnings("unchecked")
		List<? extends Identified<Integer>> list = dao().getAll();
		for (Object object : list) {
			LOGGER.info(object);
		}
		Assert.assertNotNull(list);
		Assert.assertTrue(!list.isEmpty());
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testPersist() throws Exception {
		LOGGER.info("testPersist");

		List<Identified<Integer>> allBeforeAdd = dao().getAll();
		int sizeBeforeAdd = allBeforeAdd.size();
		// persist
		dao().persist(notPersistedDto);
		List<Identified<Integer>> allAfterAdd = dao().getAll();
		int sizeAfterAdd = allAfterAdd.size();

		// just if test TestQuestions assert size list(one to many)
		// will be refactored later, make other test
		if (notPersistedDto instanceof TestQuestions) {
			Integer id = notPersistedDto.getId();
			TestQuestions fromListBefore = (TestQuestions) DaoUtils.getFromListById(allBeforeAdd, id);
			TestQuestions fromListAfter = (TestQuestions) DaoUtils.getFromListById(allAfterAdd, id);
			sizeBeforeAdd = fromListBefore.getQuestionsList().size();
			sizeAfterAdd = fromListAfter.getQuestionsList().size();
		}
		LOGGER.info("size before add: " + sizeBeforeAdd + " ,after: " + sizeAfterAdd);
		Assert.assertTrue(sizeAfterAdd > sizeBeforeAdd);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testDelete() throws Exception {
		LOGGER.info("testDelete");

		Identified<Integer> persistedObj = dao().persist(notPersistedDto);
		int sizeBeforeDelete = dao().getAll().size();
		dao().delete(persistedObj);
		int sizeAfterDelete = dao().getAll().size();

		// just if test TestQuestions assert size list(one to many)
		if (persistedObj instanceof TestQuestions) {
			int sizeBefore = ((TestQuestions) persistedObj).getQuestionsList().size();
			int sizeAfter = ((TestQuestions) dao().getAll().get(0)).getQuestionsList().size();
			Assert.assertTrue(sizeBefore > sizeAfter);
		} else {
			LOGGER.info("size before delete: " + sizeBeforeDelete + " ,after: " + sizeAfterDelete);
			Assert.assertEquals(1, sizeBeforeDelete - sizeAfterDelete);
		}

	}

	public GenericDaoTest(final Class<? extends Identified<Integer>> clazz, final Identified<Integer> notPersistedDto) {
		this.daoClass = clazz;
		this.notPersistedDto = notPersistedDto;
	}

	/**
	 * Test get photo.
	 *//*
		 * @Test public void testGetPhoto() throws Exception {
		 * LOGGER.info("testGetPhoto");
		 * 
		 * Identified<Integer> persistedObj = dao().persist(notPersistedDto);
		 * 
		 * // just if test USER object else skip if (persistedObj instanceof
		 * User) { UserDaoImpl userDaoImpl = (UserDaoImpl) dao(); byte[]
		 * userPhoto = userDaoImpl.getUserPhoto(3);
		 * System.out.println(userDaoImpl); }
		 * 
		 * }
		 */
}