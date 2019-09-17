package com.excilys.cdb.dao.mappers;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import com.excilys.cdb.domain.Computer;

public class ComputerListHandler extends BeanListHandler<Computer> {
  private ComputerHandler computerHandler;

  public ComputerListHandler(Connection connection) {
    super(Computer.class);
  }

  @Override
  public List<Computer> handle(ResultSet rs) throws SQLException {
    List<Computer> computers = new ArrayList<>();
    while (rs.next()) {
      Computer computer = computerHandler.handle(rs);
      computers.add(computer);
    }
    return computers;
  }
}
