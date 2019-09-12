package com.excilys.cdb.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.excilys.cdb.domain.Company;
import com.excilys.cdb.domain.Computer;

public class ComputerDao extends Dao<Computer> {

    // @formatter:off
    private static final String GET_QUERY = "SELECT * FROM computer WHERE id=?";
    
    private static final String GET_ALL_QUERY = "SELECT "
            + "c1.id as computer_id, "
            + "c1.name as computer_name, "
            + "introduced, "
            + "discontinued, "
            + "company_id, "
            + "c2.name as company_name "
            + "FROM computer c1 "
            + "LEFT JOIN company c2 "
            + "ON c1.company_id=c2.id";
    
    private static final String SAVE_QUERY = "INSERT INTO "
            + "computer(id, name, introduced, discontinued, company_id) "
            + "VALUES(?, ?, ?, ?, ?)";
    
    private static final String UPDATE_QUERY = "UPDATE computer SET "
            + "name=?, introduced=?, discontinued=?, company_id=? WHERE id=?";
    
    private static final String DELETE_QUERY = "DELETE FROM computer WHERE id=?";
    // @formatter:on

    public ComputerDao(ConnectionManager cm) {
        super(cm);
    }

    @Override
    public Computer get(long id) {
        Computer com = new Computer();
        try {
            PreparedStatement ps = cm.getPreparedStatement(GET_QUERY);
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.first()) {
                String computerName = rs.getString("name");
                com.setId(id);
                com.setName(computerName);
            }
            return com;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Computer> getAll() {
        try {
            PreparedStatement ps = cm.getPreparedStatement(GET_ALL_QUERY);
            ResultSet rs = ps.executeQuery();

            List<Computer> result = new ArrayList<>();

            while (rs.next()) {
                long id = rs.getLong("computer_id");
                String name = rs.getString("computer_name");
                Timestamp introduced = rs.getTimestamp("introduced");
                Timestamp discontinued = rs.getTimestamp("discontinued");
                long companyId = rs.getLong("company_id");
                String companyName = rs.getString("company_name");

                Company company = new Company(companyId, companyName);

                Computer computer = new Computer(id, name, introduced, discontinued, company);
                result.add(computer);
            }

            return result;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public int save(Computer c) {
        try {
            PreparedStatement ps = cm.getPreparedStatement(SAVE_QUERY);

            // Don't provide id, let mysql server automatically do this for us
            ps.setNull(1, java.sql.Types.BIGINT);

            if (c.getName() == null) {
                throw new RuntimeException("Comupter name cannot be null !");
            } else {
                ps.setString(2, c.getName());
            }

            if (c.getIntroduced() == null) {
                ps.setNull(3, java.sql.Types.TIMESTAMP);
            } else {
                ps.setTimestamp(3, c.getIntroduced());
            }

            if (c.getDiscontinued() == null) {
                ps.setNull(4, java.sql.Types.TIMESTAMP);
            } else {
                ps.setTimestamp(4, c.getDiscontinued());
            }

            if (c.getCompany() == null) {
                ps.setNull(5, java.sql.Types.BIGINT);
            } else {
                ps.setLong(5, c.getCompany().getId());
            }

            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int update(Computer c) {
        try {
            PreparedStatement ps = cm.getPreparedStatement(UPDATE_QUERY);

            ps.setString(1, c.getName());

            if (c.getName() == null) {
                throw new RuntimeException("Comupter name cannot be null !");
            } else {
                ps.setString(1, c.getName());
            }

            if (c.getIntroduced() == null) {
                ps.setNull(2, java.sql.Types.TIMESTAMP);
            } else {
                ps.setTimestamp(2, c.getIntroduced());
            }

            if (c.getDiscontinued() == null) {
                ps.setNull(3, java.sql.Types.TIMESTAMP);
            } else {
                ps.setTimestamp(3, c.getDiscontinued());
            }

            if (c.getCompany() == null) {
                ps.setNull(4, java.sql.Types.BIGINT);
            } else {
                ps.setLong(4, c.getCompany().getId());
            }

            ps.setLong(5, c.getId());

            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int delete(Computer c) {
        try {
            PreparedStatement ps = cm.getPreparedStatement(DELETE_QUERY);

            ps.setLong(1, c.getId());

            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

}
