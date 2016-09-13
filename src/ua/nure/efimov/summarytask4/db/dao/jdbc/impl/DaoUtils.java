package ua.nure.efimov.summarytask4.db.dao.jdbc.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import ua.nure.efimov.summarytask4.db.dao.GenericDao;
import ua.nure.efimov.summarytask4.db.dao.Identified;
import ua.nure.efimov.summarytask4.db.dao.PersistException;

/**
 * Contains util methods for work with jdbc dao.
 * 
 * @author Alexandr Efimov
 *
 */
public class DaoUtils {

	/**
	 * Convert {@link Date} to -> {@link java.sql.Date}. If null -> null.
	 * 
	 * @param date
	 *            is param for converting
	 * @return sql date
	 */
	public static java.sql.Date convertDateToSql(java.util.Date date) {
		if (date == null) {
			return null;
		}
		return new java.sql.Date(date.getTime());
	}

	/**
	 * Convert {@link Date} to {@link Timestamp}. If null -> null.
	 * 
	 * @param date
	 * @return
	 */
	public static Timestamp convertDateToTimestamp(java.util.Date date) {
		if (date == null) {
			return null;
		}
		return new Timestamp(date.getTime());
	}

	/**
	 * Get element from list by id. If no element -> null.
	 * 
	 * @param list
	 *            is list with elements
	 * @param id
	 *            is int id for check
	 * @return element by id
	 */
	public static <T extends Identified<Integer>> T getFromListById(List<T> list, int id) {
		for (T element : list) {
			if (element.getId().intValue() == id) {
				return element;
			}
		}
		return null;
	}

	/**
	 * Checks if list containts element with id equals to param id.
	 * 
	 * @param list
	 *            is list with elements
	 * @param id
	 *            is int id for check
	 * @return true if contains
	 */
	public static boolean listContainsId(List<? extends Identified<Integer>> list, int id) {
		if (list.isEmpty()) {
			return false;
		}
		for (Identified<Integer> element : list) {
			if (element.getId().intValue() == id) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Get list with id from list with identifieds objects.
	 * 
	 * @param identifieds
	 *            is list with objecth
	 * @return list with ids of objects
	 */
	public static List<Integer> getIdListFromIdentifiedList(List<? extends Identified<Integer>> identifieds) {
		if (identifieds == null) {
			return null;
		}
		List<Integer> listId = new ArrayList<>();

		for (Identified<Integer> obj : identifieds) {
			listId.add(obj.getId());
		}
		return listId;
	}

	public static boolean isContainsByPK(GenericDao<? extends Identified<Integer>, Integer> dao,
			Identified<Integer> object) throws PersistException {

		if (dao == null || object == null || object.getId() == null) {
			return false;
		}

		return dao.getByPK(object.getId()) != null;
	}

}
