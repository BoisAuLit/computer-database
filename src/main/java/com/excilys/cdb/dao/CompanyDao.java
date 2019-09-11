package com.excilys.cdb.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.excilys.cdb.domain.Company;

public class CompanyDao extends Dao<Company> {

	private static final String GET_QUERY = "SELECT * FROM company WHERE id=?";
	private static final String GET_ALL_QUERY = "SELECT * FROM  company";

	public CompanyDao(ConnectionManager cm) {
		super(cm);
	}

	@Override
	public Company get(long id) {
		PreparedStatement ps;

		Company com = new Company();

		try {
			ps = cm.getPreparedStatement(GET_QUERY);
			ps.setLong(1, id);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				String companyName = rs.getString(2);
				com.setId(id);
				com.setName(companyName);
			}

			return com;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return com;
	}

	@Override
	public List<Company> getAll() {

		try {
			PreparedStatement ps = cm.getPreparedStatement(GET_ALL_QUERY);
			ResultSet rs = ps.executeQuery();

			List<Company> result = new ArrayList<>();
			while (rs.next()) {
				int id = rs.getInt(1);
				String name = rs.getString(2);
				Company c = new Company(id, name);
				result.add(c);
			}

			return result;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public int save(Company c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int update(Company c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int delete(Company c) {
		throw new UnsupportedOperationException();
	}
}
