package dao;

import entity.Policy;
import util.PropertyUtil;
import myexceptions.PolicyNotFoundException;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class InsuranceServiceImpl implements IPolicyService {
    
    String connectionString = PropertyUtil.getPropertyString();
	
	private final Connection connection;
	
	public InsuranceServiceImpl(Connection connection) {
		this.connection=connection;
	}
	
    @Override
    public boolean createPolicy(Policy policy){
        try (Connection connection = DriverManager.getConnection(connectionString);
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO policy (policyId, policyName, policyType,coverageAmount) VALUES (?, ?, ?,?)"
             )) {
            preparedStatement.setInt(1, policy.getPolicyId());
            preparedStatement.setString(2, policy.getPolicyName());
            preparedStatement.setString(3, policy.getPolicyType());
            preparedStatement.setDouble(4, policy.getCoverageAmount());

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Policy getPolicy(int policyId) throws PolicyNotFoundException {
        try (Connection connection = DriverManager.getConnection(connectionString);
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT * FROM policy WHERE policyId = ?"
             )) {
            preparedStatement.setInt(1, policyId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return mapResultSetToPolicy(resultSet);
            } else {
            	throw new PolicyNotFoundException("Policy with ID " + policyId + " not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new PolicyNotFoundException("Error while retrieving policy with ID " + policyId);
        }
    }

    @Override
    public Collection<Policy> getAllPolicies() throws PolicyNotFoundException  {
        List<Policy> policies = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(connectionString);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM policy")) {

            while (resultSet.next()) {
                Policy policy = mapResultSetToPolicy(resultSet);
                policies.add(policy);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new PolicyNotFoundException("Error while retrieving all policies");
        }

        return policies;
    }

    @Override	
    public boolean updatePolicy(Policy policy) {
        try (Connection connection = DriverManager.getConnection(connectionString);
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "UPDATE policy SET policyName = ?, policyType = ?, coverageAmount = ? WHERE policyId = ?"
             )) {
            preparedStatement.setString(1, policy.getPolicyName());
            preparedStatement.setString(2, policy.getPolicyType());
            preparedStatement.setDouble(3, policy.getCoverageAmount());
            preparedStatement.setInt(4, policy.getPolicyId());

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deletePolicy(int policyId) throws PolicyNotFoundException  {
        try (Connection connection = DriverManager.getConnection(connectionString);
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "DELETE FROM policy WHERE policyId = ?"
             )) {
            preparedStatement.setInt(1, policyId);
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                return true;
            } else {
                throw new PolicyNotFoundException("Policy with ID " + policyId + " not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new PolicyNotFoundException("Error while deleting policy with ID " + policyId);
        }
    }


    private Policy mapResultSetToPolicy(ResultSet resultSet) throws SQLException {
        int policyId = resultSet.getInt("policyId");
        String policyName = resultSet.getString("policyName");
        String policyType = resultSet.getString("policyType");
        double coverageAmount = resultSet.getDouble("coverageAmount");

        return new Policy(policyId, policyName, policyType, coverageAmount);
    }

  
}
