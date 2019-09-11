package com.excilys.cdb;

import java.sql.SQLException;

import com.excilys.cdb.dao.ComputerDao;
import com.excilys.cdb.dao.ConnectionManager;
import com.excilys.cdb.domain.Computer;

public class Main {

	public static void main(String[] args) throws SQLException {
		ConnectionManager cm = ConnectionManager.getInstance();

		/////////////////////////////////////////////////////
		// Test CompanyDao.getAll -> tested works
//		new CompanyDao(cm).getAll().stream().forEach(System.out::println);

		/////////////////////////////////////////////////////
		// Test CompanyDao.get -> tested, works
//		Company com = new CompanyDao(cm).get(1);
//		System.out.println(com);

		/////////////////////////////////////////////////////
		// Test ComputerDao.getAll -> tested, works
//		new ComputerDao(cm).getAll().stream().forEach(System.out::println);

		/////////////////////////////////////////////////////
		// Test ComputerDao.get -> tested, works
//		Computer com = new ComputerDao(cm).get(1);
//		System.out.println(com);

		///////////////////////////////////////////////////////
		// Test ComputerDao.save -> tested, works
//		Computer com = new Computer();
//		com.setName("titane");
//		int rowsAffected = new ComputerDao(cm).save(com);
//		System.out.println(rowsAffected);

		///////////////////////////////////////////////////////
		// Test ComputerDao.update -> tested, works
//		Computer com = new Computer();
//		com.setId(576L);
//		com.setName("bohao's computer");
//		Company c = new Company();
//		c.setId(10);
//		com.setCompany(c);
//		int rowsAffected = new ComputerDao(cm).update(com);
//		System.out.println(rowsAffected);

		///////////////////////////////////////////////////////
		// Test ComputerDao.delete ->
		Computer c = new Computer();
		c.setId(576);
		int rowsAffected = new ComputerDao(cm).delete(c);

		System.out.println(rowsAffected);

		cm.close();

	}
}
