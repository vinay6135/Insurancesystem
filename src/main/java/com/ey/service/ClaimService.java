package com.ey.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ey.entity.Claim;
import com.ey.entity.CustomerPolicy;
import com.ey.enums.ClaimStatus;
import com.ey.repository.ClaimRepository;
import com.ey.repository.CustomerPolicyRepository;

@Service
public class ClaimService {

    @Autowired
    private ClaimRepository repository;

    @Autowired
    private CustomerPolicyRepository customerPolicyRepository;

    @Autowired
    private NotificationService notificationService;

    public Claim raiseClaim(Long customerPolicyId, String reason) {

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

        return repository.save(claim);
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

