package com.excilys.cdb.dao;

import java.util.List;

public abstract class Dao<T> {
  protected ConnectionManager cm = null;

  protected Dao(ConnectionManager cm) {
    this.cm = cm;
  }

  /**
   * Get a record from the database by id.
   *
   * @param id the id of the record for which we
   * @return
   */
  public abstract T get(long id);

  /**
   * Get all the records of a database table.
   *
   * @return all domain objects converted from the records of the database table.
   */
  public abstract List<T> getAll();

  /**
   * Insert a record to the database.
   *
   * @param t the domain object for which we will create a record in the database.
   * @return rows affected in the database by this operation.
   */
  public abstract int save(T t);

  /**
   * Update the <code>Dao</code> object.
   *
   * @param t the domain objecct for which we will update information in the database.
   * @return rows affected in the database by this operation.
   */
  public abstract int update(T t);

  /**
   * Delete the <code>Dao</code> object.
   *
   * @param t The domain object for which the <code>Dao</code> object is to be deleted
   * @return rows affectecd in the database by this operation.
   */
  public abstract int delete(T t);
}
