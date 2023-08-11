package com.example.rgtproject.orders.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UpdateProductNameMapRepository extends JpaRepository<UpdateProductNameMap, Integer> {
    UpdateProductNameMap findUpdateProductNameMapByTargetData(String targetData);
}
