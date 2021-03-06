package ua.nure.efimov.summarytask4.db.dao.jdbc;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import ua.nure.efimov.summarytask4.db.dao.GenericDao;
import ua.nure.efimov.summarytask4.db.dao.Identified;
import ua.nure.efimov.summarytask4.db.dao.PersistException;

/**
 * Абстрактный класс предоставляющий базовую реализацию CRUD операций с
 * использованием JDBC.
 *
 * @author Alexandr Efimov
 * @param <T>
 *            тип объекта персистенции
 * @param <PK>
 *            тип первичного ключа
 */
public abstract class AbstractJDBCDAO<T extends Identified<PK>, PK extends Serializable> implements GenericDao<T, PK> {

	/**
	 * Jdbc connection.
	 */
	private Connection connection;

	/**
	 * Возвращает sql запрос для получения всех записей.
	 * <p/>
	 * SELECT * FROM [Table]
	 */
	public abstract String getSelectQuery();

	/**
	 * Возвращает sql запрос для вставки новой записи в базу данных.
	 * <p/>
	 * INSERT INTO [Table] ([column, column, ...]) VALUES (?, ?, ...);
	 */
	public abstract String getCreateQuery();

	/**
	 * Возвращает sql запрос для обновления записи.
	 * <p/>
	 * UPDATE [Table] SET [column = ?, column = ?, ...] WHERE id = ?;
	 */
	public abstract String getUpdateQuery();

	/**
	 * Возвращает sql запрос для удаления записи из базы данных.
	 * <p/>
	 * DELETE FROM [Table] WHERE id= ?;
	 */
	public abstract String getDeleteQuery();

	/**
	 * Разбирает ResultSet и возвращает список объектов соответствующих
	 * содержимому ResultSet.
	 * 
	 * @throws PersistException
	 */
	protected abstract List<T> parseResSet(ResultSet rs) throws PersistException;

	/**
	 * Устанавливает аргументы update запроса в соответствии со значением полей
	 * объекта object.
	 * 
	 * @throws PersistException
	 */
	protected abstract void prepareStForUpdateSetArgs(PreparedStatement statement, T object) throws PersistException;

	/**
	 * Устанавливает аргументы insert запроса в соответствии со значением полей
	 * объекта object.
	 */
	protected abstract void prepareStatementForInsert(PreparedStatement statement, T object) throws PersistException;

	@Override
	public T getByPK(final PK key) throws PersistException {
		List<T> list;
		String sql = getSelectQuery();
		sql += " WHERE id = ?";
		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setInt(1, (int) key);
			ResultSet rs = statement.executeQuery();
			list = parseResSet(rs);
		} catch (Exception e) {
			throw new PersistException(e);
		}
		if (list == null || list.isEmpty()) {
			return null;
		}

		return list.iterator().next();
	}

	@Override
	public List<T> getAll() throws PersistException {
		List<T> list = null;

		String sql = getSelectQuery();
		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			ResultSet rs = statement.executeQuery();
			list = parseResSet(rs);
		} catch (Exception e) {
			throw new PersistException(e);
		}
		return list;
	}

	@Override
	public void update(final T object) throws PersistException {
		String sql = getUpdateQuery();

		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			prepareStForUpdateSetArgs(statement, object);
			statement.executeUpdate();

		} catch (Exception e) {
			throw new PersistException(e);
		}
	}

	@Override
	public void delete(final T object) throws PersistException {
		String sql = getDeleteQuery();

		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setObject(1, object.getId());
			statement.executeUpdate();
		} catch (Exception e) {
			throw new PersistException(e);
		}
	}

	@Override
	public T persist(final T object) throws PersistException {
		System.out.println(object);
		if (object == null || object.getId() != null) {
			throw new PersistException("Object is already persist or null.");
		}

		T persistInstance;
		String sql = getCreateQuery();
		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			prepareStatementForInsert(statement, object);
			statement.executeUpdate();
		} catch (Exception e) {
			throw new PersistException(e);
		}

		sql = getSelectQuery() + " WHERE id = last_insert_id();";
		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			ResultSet rs = statement.executeQuery();
			List<T> list = parseResSet(rs);
			if ((list == null) || (list.size() != 1)) {
				throw new PersistException("Exception on findByPK new persist data.");
			}
			persistInstance = list.iterator().next();
		} catch (Exception e) {
			e.printStackTrace();
			throw new PersistException(e);
		}
		return persistInstance;
	}

	/**
	 * Set connection.
	 * 
	 * @param connection
	 *            is connection
	 */
	public AbstractJDBCDAO(final Connection connection) {
		this.connection = connection;
	}

}
