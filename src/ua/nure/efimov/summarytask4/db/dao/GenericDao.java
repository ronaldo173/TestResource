package ua.nure.efimov.summarytask4.db.dao;

import java.io.Serializable;
import java.util.List;

/**
 * Унифицированный интерфейс управления персистентным состоянием объектов
 * 
 * @param <T>
 *            тип объекта персистенции
 * @param <PK>
 *            тип первичного ключа
 */
public interface GenericDao<T extends Identified<PK>, PK extends Serializable> {

	/**
	 * Создает новую запись, соответствующую объекту object.
	 * 
	 * @param notPersistedDto
	 *            is object for creating in storage
	 * @return obj is created object
	 * @throws PersistException
	 *             if smth happened
	 */
	public T persist(T notPersistedDto) throws PersistException;

	/**
	 * Возвращает объект соответствующий записи с первичным ключом key или null.
	 * 
	 * @param key
	 *            key for finding obj
	 * @return object
	 * @throws PersistException
	 *             if smth happened
	 */
	public T getByPK(PK key) throws PersistException;

	/**
	 * Сохраняет состояние объекта group в базе данных.
	 * 
	 * @param object
	 *            for updating
	 * @throws PersistException
	 *             if smth happened
	 */
	public void update(T object) throws PersistException;

	/**
	 * Удаляет запись об объекте из базы данных.
	 * 
	 * @param object
	 *            for deleting
	 * @throws PersistException
	 *             if smth happened
	 */
	public void delete(T object) throws PersistException;

	/**
	 * Возвращает список объектов соответствующих всем записям в базе данных.
	 * 
	 * @return list with objects
	 * @throws PersistException
	 *             if smth happened
	 */
	public List<T> getAll() throws PersistException;

}