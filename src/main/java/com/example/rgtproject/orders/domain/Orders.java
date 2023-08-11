package com.example.rgtproject.orders.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String orderId;

    private String productName;

    private String options;

    private Integer tableNo;

    private Long quantity;

    private LocalDate orderDate;

    private LocalTime orderTime;

    private String robotStatus;

    private LocalDateTime dateTime;

    private String seq;

    private String dong;

    private String ho;

    private String ordererName;

    @Builder
    public Orders(String orderId, String productName, String options, Integer tableNo, Long quantity,
                LocalDate orderDate, LocalTime orderTime, String robotStatus, LocalDateTime dateTime, String seq,
                String dong, String ho, String ordererName) {
        this.orderId = orderId;
        this.productName = productName;
        this.options = options;
        this.tableNo = tableNo;
        this.quantity = quantity;
        this.orderDate = orderDate;
        this.orderTime = orderTime;
        this.robotStatus = robotStatus;
        this.dateTime = dateTime;
        this.seq = seq;
        this.dong = dong;
        this.ho = ho;
        this.ordererName = ordererName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
