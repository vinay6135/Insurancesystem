package com.ey.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ey.dto.response.ClaimResponseDTO;
import com.ey.entity.Agent;
import com.ey.entity.Claim;
import com.ey.entity.CustomerPolicy;
import com.ey.enums.ClaimStatus;
import com.ey.exception.ResourceNotFoundException;
import com.ey.mapper.ClaimMapper;
import com.ey.repository.AgentRepository;
import com.ey.repository.ClaimRepository;
import com.ey.repository.CustomerPolicyRepository;

@Service
public class ClaimService {
	
	@Autowired
	private ClaimMapper claimmapper;
	
	@Autowired
	private AgentRepository agentRepository;

    @Autowired
    private ClaimRepository repository;

    @Autowired
    private CustomerPolicyRepository customerPolicyRepository;

    @Autowired
    private NotificationService notificationService;

    public ClaimResponseDTO raiseClaim(Long customerPolicyId, String reason) {

        CustomerPolicy cp = customerPolicyRepository.findById(customerPolicyId)
                .orElseThrow(() -> new RuntimeException("Customer policy not found"));

        Claim claim = new Claim();
        claim.setCustomerPolicy(cp);
        claim.setReason(reason);
        claim.setStatus(ClaimStatus.SUBMITTED);

        notificationService.notify(
                cp.getCustomer(),
                "Claim submitted successfully"
        );

        repository.save(claim);
        return claimmapper.toResponse(claim);
    }
    
    public List<ClaimResponseDTO> getAssignedClaims(String agentEmail) {

        Agent agent = agentRepository.findByUserEmail(agentEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Agent not found"));
        
        List<Claim> list=repository.findByCustomerPolicyAgentIdAndStatus(agent.getId(),ClaimStatus.SUBMITTED);
        if(!list.isEmpty())
        {
        	List<ClaimResponseDTO> resdto=new ArrayList<>();
        	for(Claim claim:list)
        	{
        		resdto.add(claimmapper.toResponse(claim));
        	}
        	return resdto;	
        }
        throw new ResourceNotFoundException("No Claims Assigned");
    }

    public Claim updateStatus(Long id, ClaimStatus status) {

        Claim claim = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Claim not found"));

        claim.setStatus(status);

        notificationService.notify(
                claim.getCustomerPolicy().getCustomer(),
                "Your claim status updated to " + status
        );

        return repository.save(claim);
    }

    public Claim get(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Claim not found"));
    }

    public List<Claim> getAll() {
        return repository.findAll();
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}

