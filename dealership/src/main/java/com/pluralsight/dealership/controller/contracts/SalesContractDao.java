package com.pluralsight.dealership.controller.contracts;

import com.pluralsight.dealership.model.contract.SalesContract;

import java.util.List;

public interface SalesContractDao {
    void saveSalesContract(SalesContract salesContract);

    List<SalesContract> findAllSalesContracts();
}
