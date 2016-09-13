package ua.nure.efimov.summarytask4.db.dao;

/**
 * Fabric of objects for working with storage.
 * 
 * @author Alexandr Efimov
 *
 */
public interface DaoFactory<Context> {
	interface DaoCreator<Context> {
		GenericDao<? extends Identified<Integer>, Integer> create(Context context);
	}

	/**
	 * Get storage context, if database -> sql connection etc...
	 * 
	 * @return context
	 * @throws PersistException
	 *             if smth happened
	 */
	Context getContext() throws PersistException;

	/**
	 * Get dao object for control persist object.
	 * 
	 * @param context
	 *            is connect to storage
	 * @param dtoClass
	 *            is class obj for getting dao
	 * @return dao ffr arg class
	 * @throws PersistException
	 */
	GenericDao<? extends Identified<Integer>, Integer> getDao(Context context, Class<?> dtoClass)
			throws PersistException;
}
