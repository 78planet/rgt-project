package com.example.rgtproject.orders.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UpdateProductNameMap {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String targetData;
    private String willChangeData;

    public UpdateProductNameMap(String targetData, String willChangeData) {
        this.targetData = targetData;
        this.willChangeData = willChangeData;
    }
}
