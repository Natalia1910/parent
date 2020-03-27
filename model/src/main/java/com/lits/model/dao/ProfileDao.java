package com.lits.model.dao;

import com.lits.model.entity.Profile;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProfileDao {
    private Connection connection;

    public ProfileDao(Connection connection) {
        this.connection = connection;
    }

    public List<Profile> findAll() {
        Statement s = null;
        List<Profile> result = new ArrayList<Profile>();
        try {
            s = connection.createStatement();
            ResultSet rs = s.executeQuery("SELECT `id`, `name`, `last_name`, `age` FROM `profile`");
            while (rs.next()) {
                Profile profile = new Profile();
                profile.setId(rs.getInt("id"));
                profile.setName(rs.getString("name"));
                profile.setLastName(rs.getString("last_name"));
                profile.setAge(rs.getInt("age"));
                result.add(profile);
            }
        } catch (SQLException e) {
            if (s != null) {
                try {
                    s.close();
                } catch (SQLException e1) {
                    System.out.println("problem with database");
                }
            }
        }
        return result;
    }

    public Profile save(Profile profile) {
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement("INSERT INTO profile (`name`, `last_name`, `age`) " +
                    "VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, profile.getName());
            ps.setString(2, profile.getLastName());
            ps.setInt(3, profile.getAge());

            int r = ps.executeUpdate();
            if (r > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    profile.setId(rs.getInt(1));
                }
            } else {
                throw new SQLException("Can not save new profile");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    System.out.println("problem with database");
                }
            }
        }
        return profile;
    }

    public Profile findById(int id) {
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        try {
            ps = connection.prepareStatement("SELECT id, name, last_name, age FROM profile WHERE id = ?", Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, id);
            resultSet = ps.executeQuery();
            if (resultSet.next()) {
                Profile result = new Profile();
                result.setId(resultSet.getInt("id"));
                result.setName(resultSet.getString("name"));
                result.setLastName(resultSet.getString("last_name"));
                result.setAge(resultSet.getInt("age"));
                return result;
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    System.out.println("problem with database");
                }
            }
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    System.out.println("problem with database");
                }
            }
        }
    }

    public void delete(int id) {
        PreparedStatement ps = null;
        try {
            String query = "DELETE FROM profile WHERE id = ?";
            ps = connection.prepareStatement(query);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    System.out.println("problem with database");
                }
            }
        }
    }
}
