package com.excilys.cdb.dao;

import java.util.List;

public abstract class Dao<T> {
  protected ConnectionManager cm = null;

  protected Dao(ConnectionManager cm) {
    this.cm = cm;
  }

  public abstract T get(long id);

  public abstract List<T> getAll();

  public abstract int save(T t);

  public abstract int update(T t);

  public abstract int delete(T t);
}
