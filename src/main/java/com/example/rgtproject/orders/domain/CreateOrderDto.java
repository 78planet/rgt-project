package com.example.rgtproject.orders.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateOrderDto {

    @NotNull
    private String order_id;

    @NotNull
    private String product_name;

    @NotNull
    private String options;

    @NotNull
    private Integer table_no;

    @NotNull
    private Long quantity;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate order_date;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime order_time;

    @NotNull
    private String robot_status;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime date_time;

    @NotNull
    private String seq;

    @NotNull
    private String dong;

    @NotNull
    private String ho;

    @NotNull
    private String orderer_name;

    @Builder
    public CreateOrderDto(String order_id, String product_name, String options, Integer table_no,
        Long quantity, LocalDate order_date, LocalTime order_time, String robot_status,
        LocalDateTime date_time, String seq, String dong, String ho, String orderer_name) {
        this.order_id = order_id;
        this.product_name = product_name;
        this.options = options;
        this.table_no = table_no;
        this.quantity = quantity;
        this.order_date = order_date;
        this.order_time = order_time;
        this.robot_status = robot_status;
        this.date_time = date_time;
        this.seq = seq;
        this.dong = dong;
        this.ho = ho;
        this.orderer_name = orderer_name;
    }

    public Orders toOrders() {
        return Orders.builder()
            .orderId(this.getOrder_id())
            .productName(this.getProduct_name())
            .options(this.getOptions())
            .tableNo(this.getTable_no())
            .quantity(this.getQuantity())
            .orderDate(this.getOrder_date())
            .orderTime(this.getOrder_time())
            .robotStatus(this.getRobot_status())
            .dateTime(this.getDate_time())
            .seq(this.getSeq())
            .dong(this.getDong())
            .ho(this.getHo())
            .ordererName(this.getOrderer_name())
            .build();
    }

    public static List<CreateOrderDto> from(List<Orders> orders) {
        return orders.stream().map(order ->
                CreateOrderDto.builder()
                    .order_id(order.getOrderId())
                    .product_name(order.getProductName())
                    .options(order.getOptions())
                    .table_no(order.getTableNo())
                    .quantity(order.getQuantity())
                    .order_date(order.getOrderDate())
                    .order_time(order.getOrderTime())
                    .robot_status(order.getRobotStatus())
                    .date_time(order.getDateTime())
                    .seq(order.getSeq())
                    .dong(order.getDong())
                    .ho(order.getHo())
                    .orderer_name(order.getOrdererName())
                    .build()
            ).collect(Collectors.toList());
    }

    public void setProductName(String willChangeData) {
        this.product_name = willChangeData;
    }
}
