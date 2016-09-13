package ua.nure.efimov.summarytask4.db.dao;

import java.io.Serializable;

/**
 * Interface of identificated objects. It's objects stored in relation db tables
 * and that has id field.
 * 
 * @param <PK>
 *            is any type representing primary key in database.
 */
public interface Identified<PK extends Serializable> extends Serializable {

	/**
	 * Get primary key - > id of object.
	 * 
	 * @return id of object
	 */
	PK getId();
}