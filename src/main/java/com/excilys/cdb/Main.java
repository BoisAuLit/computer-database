package com.excilys.cdb;

import java.sql.SQLException;
import com.excilys.cdb.dao.CompanyDao;
import com.excilys.cdb.dao.ComputerDao;
import com.excilys.cdb.domain.Company;
import com.excilys.cdb.domain.Computer;

public final class Main {

  private Main() {

  }

  @SuppressWarnings("unused")
  private static void testDAO() throws SQLException {

    CompanyDao companyDao = CompanyDao.getInstance();
    ComputerDao computerDao = ComputerDao.getInstance();
    Company c1 = new Company();
    Computer c2 = new Computer();

    /////////////////////////////////////////////////////
    // Test CompanyDao.getAll -> tested works
    // companyDao.getAll().stream().forEach(System.out::println);

    /////////////////////////////////////////////////////
    // Test CompanyDao.get -> tested, works
    // Optional<Company> com = companyDao.get(1);
    // com.ifPresent(System.out::println);

    /////////////////////////////////////////////////////
    // Test ComputerDao.getAll -> tested, works
    // ComputerDao.getInstance().getAll().stream().forEach(System.out::println);

    /////////////////////////////////////////////////////
    // Test ComputerDao.get -> tested, works
    ComputerDao.getInstance().get(1).ifPresent(System.out::println);

    ///////////////////////////////////////////////////////
    // Test ComputerDao.save -> tested, works
    // Computer com = new Computer();
    // com.setName("titane");
    // int rowsAffected = new ComputerDao(cm).save(com);
    // System.out.println(rowsAffected);

    ///////////////////////////////////////////////////////
    // Test ComputerDao.update -> tested, works
    // Computer com = new Computer();
    // com.setId(576L);
    // com.setName("bohao's computer");
    // Company c = new Company();
    // c.setId(10);
    // com.setCompany(c);
    // int rowsAffected = new ComputerDao(cm).update(com);
    // System.out.println(rowsAffected);

    ///////////////////////////////////////////////////////
    // Test ComputerDao.delete -> tested, works
    // Computer c = new Computer();
    // c.setId(576);
    // int rowsAffected = new ComputerDao(cm).delete(c);
    // System.out.println(rowsAffected);

    // cm.close();
  }

  /**
   * Test logger.
   */
  private static void testLogger() {
    // Logger logger = LoggerFactory.getLogger("com.excilys.cdb.Main");
    // logger.debug("Hello world.");
    //
    // // print internal state
    // LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
    // StatusPrinter.print(lc);
  }

  /**
   * Entry point.
   */
  public static void main(final String[] args) throws SQLException, ClassNotFoundException {
    testDAO();
  }
}
