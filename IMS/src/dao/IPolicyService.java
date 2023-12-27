package dao;
import entity.Policy;
import myexceptions.PolicyNotFoundException;

import java.util.Collection;

public interface IPolicyService {
	
	 boolean createPolicy(Policy policy);
	    Policy getPolicy(int policyId) throws PolicyNotFoundException;
	    Collection<Policy> getAllPolicies() throws PolicyNotFoundException;
	    boolean updatePolicy(Policy policy) throws PolicyNotFoundException;;
	    boolean deletePolicy(int policyId) throws PolicyNotFoundException;;
}
