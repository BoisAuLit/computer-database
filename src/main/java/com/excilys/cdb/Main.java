package com.excilys.cdb;

import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.dao.ConnectionManager;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.util.StatusPrinter;

public class Main {

//    static Logger logger = LoggerFactory.getLogger(Main.class);

    @SuppressWarnings("unused")
    private static void testDAO() throws SQLException {
        ConnectionManager cm = ConnectionManager.getInstance();

        /////////////////////////////////////////////////////
        // Test CompanyDao.getAll -> tested works
//        new CompanyDao(cm).getAll().stream().forEach(System.out::println);

        /////////////////////////////////////////////////////
        // Test CompanyDao.get -> tested, works
//        Company com = new CompanyDao(cm).get(1);
//        System.out.println(com);

        /////////////////////////////////////////////////////
        // Test ComputerDao.getAll -> tested, works
//        new ComputerDao(cm).getAll().stream().forEach(System.out::println);

        /////////////////////////////////////////////////////
        // Test ComputerDao.get -> tested, works
//        Computer com = new ComputerDao(cm).get(1);
//        System.out.println(com);

        ///////////////////////////////////////////////////////
        // Test ComputerDao.save -> tested, works
//        Computer com = new Computer();
//        com.setName("titane");
//        int rowsAffected = new ComputerDao(cm).save(com);
//        System.out.println(rowsAffected);

        ///////////////////////////////////////////////////////
        // Test ComputerDao.update -> tested, works
//        Computer com = new Computer();
//        com.setId(576L);
//        com.setName("bohao's computer");
//        Company c = new Company();
//        c.setId(10);
//        com.setCompany(c);
//        int rowsAffected = new ComputerDao(cm).update(com);
//        System.out.println(rowsAffected);

        ///////////////////////////////////////////////////////
        // Test ComputerDao.delete -> tested, works
//        Computer c = new Computer();
//        c.setId(576);
//        int rowsAffected = new ComputerDao(cm).delete(c);
//        System.out.println(rowsAffected);

        cm.close();
    }

    /**
     * Entry point, for the sake of testing.
     *
     * @param args command line arguments
     */
    public static void main(final String[] args) {
        Logger logger = LoggerFactory.getLogger("com.excilys.cdb.Main");
        logger.debug("Hello world.");

        // print internal state
        LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
        StatusPrinter.print(lc);

    }
}
