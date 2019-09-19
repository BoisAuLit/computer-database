package com.excilys.cdb.dao;

import java.util.List;
import java.util.Optional;

public interface Dao<T> {
  /**
   * Get a record by id in a table.
   */
  Optional<T> get(long id);

  /**
   * Get all the records in a table.
   */
  List<T> getAll();

  /**
   * Insert a record.
   *
   */
  int save(T t);

  /**
   * Update a record.
   */
  int update(T t);

  /**
   * Delete a record.
   */
  int delete(T t);
}
