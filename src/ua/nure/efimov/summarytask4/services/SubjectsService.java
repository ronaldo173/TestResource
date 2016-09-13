package ua.nure.efimov.summarytask4.services;

import java.io.Serializable;
import java.sql.Connection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import ua.nure.efimov.summarytask4.db.dao.DaoFactory;
import ua.nure.efimov.summarytask4.db.dao.jdbc.impl.DaoFactoryMySqlImpl;
import ua.nure.efimov.summarytask4.db.dao.jdbc.impl.SubjectDaoImpl;
import ua.nure.efimov.summarytask4.entity.Subject;
import ua.nure.efimov.summarytask4.exception.BusinessException;

/**
 * Service for work with subjects. Load them, sort, etc.
 * 
 * @author Alexandr Efimov
 *
 */
public class SubjectsService {

	/**
	 * Dao factory object.
	 */
	private DaoFactory<Connection> factory;

	/**
	 * Comparator for comparing subjects by their subject name.
	 * 
	 * @author Alexandr Efimov
	 * 
	 */
	private final class ComparatorSubjectByName implements Comparator<Subject>, Serializable {
		private static final long serialVersionUID = -1299029040286100601L;

		@Override
		public int compare(Subject o1, Subject o2) {
			return o1.getSubject().compareTo(o2.getSubject());
		}
	}

	/**
	 * Get all subjects using dao.
	 * 
	 * @return list with all subjects
	 * @throws BusinessException
	 *             if error
	 */
	public List<Subject> getSubjects() throws BusinessException {
		if (factory == null) {
			factory = DaoFactoryMySqlImpl.getInstance();
		}

		try (Connection connection = factory.getContext()) {

			SubjectDaoImpl subjectDao = (SubjectDaoImpl) factory.getDao(connection, Subject.class);
			return subjectDao.getAll();
		} catch (Exception e) {
			throw new BusinessException(e);
		}
	}

	/**
	 * Sort list with subjects by subject name.
	 * 
	 * @param subjectsList
	 *            list with items
	 */
	public void sortSubjects(List<Subject> subjectsList) {
		Collections.sort(subjectsList, new ComparatorSubjectByName());

	}

	/**
	 * Get subject by index using dao.
	 * 
	 * @param index
	 *            is index of
	 * @return subject by index
	 * @throws BusinessException
	 *             if can't do operation
	 */
	public Subject getSubjectByIndex(int index) throws BusinessException {
		if (factory == null) {
			factory = DaoFactoryMySqlImpl.getInstance();
		}

		try (Connection connection = factory.getContext()) {

			SubjectDaoImpl subjectDao = (SubjectDaoImpl) factory.getDao(connection, Subject.class);
			return subjectDao.getByPK(index);
		} catch (Exception e) {
			throw new BusinessException(e);
		}
	}

	/**
	 * Update subj by id in db.
	 * 
	 * @param subjEditId
	 *            is id
	 * @param subjName
	 *            is new name
	 * @throws BusinessException
	 */
	public void updateSubject(int subjEditId, String subjName) throws BusinessException {
		if (factory == null) {
			factory = DaoFactoryMySqlImpl.getInstance();
		}

		try (Connection connection = factory.getContext()) {

			SubjectDaoImpl subjectDao = (SubjectDaoImpl) factory.getDao(connection, Subject.class);
			Subject subject = new Subject();
			subject.setId(subjEditId);
			subject.setSubject(subjName);
			subjectDao.update(subject);

		} catch (Exception e) {
			throw new BusinessException(e);
		}

	}

	/**
	 * Delete subject by id. Also removes all the tests!
	 * 
	 * @param subjEditId
	 * @throws BusinessException
	 */
	public void deleteSubject(int subjEditId) throws BusinessException {
		if (factory == null) {
			factory = DaoFactoryMySqlImpl.getInstance();
		}

		try (Connection connection = factory.getContext()) {

			SubjectDaoImpl subjectDao = (SubjectDaoImpl) factory.getDao(connection, Subject.class);
			Subject subject = new Subject();
			subject.setId(subjEditId);
			subjectDao.delete(subject);

		} catch (Exception e) {
			throw new BusinessException(e);
		}

	}

	/**
	 * Add subject to db.
	 * 
	 * @param subjName
	 * @throws BusinessException
	 */
	public void addSubject(String subjName) throws BusinessException {
		if (factory == null) {
			factory = DaoFactoryMySqlImpl.getInstance();
		}

		try (Connection connection = factory.getContext()) {

			SubjectDaoImpl subjectDao = (SubjectDaoImpl) factory.getDao(connection, Subject.class);
			Subject subject = new Subject();
			subject.setSubject(subjName);
			subjectDao.persist(subject);

		} catch (Exception e) {
			throw new BusinessException(e);
		}
	}
}
