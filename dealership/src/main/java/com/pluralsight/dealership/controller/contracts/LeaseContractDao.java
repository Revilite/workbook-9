package com.pluralsight.dealership.controller.contracts;

import com.pluralsight.dealership.model.contract.LeaseContract;

import java.util.List;

public interface LeaseContractDao {
    void addLeaseContract(LeaseContract contract);
    List<LeaseContract> findAllLeaseContracts();
}
