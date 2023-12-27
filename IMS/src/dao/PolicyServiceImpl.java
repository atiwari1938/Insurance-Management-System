package dao;

import entity.Policy;
import java.sql.*;
import java.util.*;


public class PolicyServiceImpl implements IPolicyService{
	
	//JDBC connection
	private static final String DB_URL = "jdbc:mysql://localhost:3306/ims";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "1234";

	@Override
	public boolean createPolicy(Policy policy) {
		// TODO Auto-generated method stub
		try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
	             PreparedStatement preparedStatement = connection.prepareStatement(
	                     "INSERT INTO policy (policyId, policyName, policyType) VALUES (?, ?, ?)"
	             )) {
	            preparedStatement.setInt(1, policy.getPolicyId());
	            preparedStatement.setString(2, policy.getPolicyName());
	            preparedStatement.setString(3, policy.getPolicyType());

	            int rowsAffected = preparedStatement.executeUpdate();
	            return rowsAffected > 0;
	        } catch (SQLException e) {
	            e.printStackTrace();
	            return false;
	        }

	}

	@Override
	public Policy getPolicy(int policyId) {
		
		try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
	             PreparedStatement preparedStatement = connection.prepareStatement(
	                     "SELECT * FROM policy WHERE policyId = ?"
	             )) {
	            preparedStatement.setInt(1, policyId);
	            ResultSet resultSet = preparedStatement.executeQuery();

	            if (resultSet.next()) {
	                return mapResultSetToPolicy(resultSet);
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return null;
	}

	@Override
	public Collection<Policy> getAllPolicies() {
		
		List<Policy> policies = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM policy")) {

            while (resultSet.next()) {
                Policy policy = mapResultSetToPolicy(resultSet);
                policies.add(policy);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return policies;
	}

	@Override
	public boolean updatePolicy(Policy policy) {
		try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
	             PreparedStatement preparedStatement = connection.prepareStatement(
	                     "UPDATE policy SET policyName = ?, policyType = ? WHERE policyId = ?"
	             )) {
	            preparedStatement.setString(1, policy.getPolicyName());
	            preparedStatement.setString(2, policy.getPolicyType());
	            preparedStatement.setInt(3, policy.getPolicyId());

	            int rowsAffected = preparedStatement.executeUpdate();
	            return rowsAffected > 0;
	        } catch (SQLException e) {
	            e.printStackTrace();
	            return false;
	        }
	}

	@Override
	public boolean deletePolicy(int policyId) {
		try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
	             PreparedStatement preparedStatement = connection.prepareStatement(
	                     "DELETE FROM policy WHERE policyId = ?"
	             )) {
	            preparedStatement.setInt(1, policyId);

	            int rowsAffected = preparedStatement.executeUpdate();
	            return rowsAffected > 0;
	        } catch (SQLException e) {
	            e.printStackTrace();
	            return false;
	        }
	}
	private Policy mapResultSetToPolicy(ResultSet resultSet) throws SQLException {
        int policyId = resultSet.getInt("policyId");
        String policyName = resultSet.getString("policyName");
        String policyType = resultSet.getString("policyType");

        return new Policy(policyId, policyName, policyType, policyId);
    }
	
	

}
