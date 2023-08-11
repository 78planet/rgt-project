package com.example.rgtproject.orders.presentation;

import com.example.rgtproject.orders.application.OrdersService;
import com.example.rgtproject.orders.domain.CreateOrderDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrdersApi {

    private final OrdersService ordersService;

    @PostMapping()
    public ResponseEntity<String> createOrders(@Valid @RequestBody CreateOrderDto createOrderDto) {
        CreateOrderDto orders = ordersService.createOrders(createOrderDto);
        String jsonResponse = formatResponse(orders);
        return ResponseEntity.ok(jsonResponse);
    }

    @GetMapping()
    public ResponseEntity<List<CreateOrderDto>> showAllOrders() {
        return ResponseEntity.ok(ordersService.getAllOrders());
    }

    @PostMapping("/remove-duplicates")
    public ResponseEntity<String> removeDuplicates() {
        ordersService.removeDuplicateIds();
        return ResponseEntity.ok("Duplicate orderIds removed successfully.");
    }

    @PatchMapping("/{targetData}/{willChangeData}")
    public ResponseEntity<String> modifyProductName(
        @PathVariable("targetData") String targetData,
        @PathVariable("willChangeData") String willChangeData
    ) {
        ordersService.updateProductName(targetData, willChangeData);
        return ResponseEntity.ok("Product Name modified successfully.");
    }

    private String formatResponse(CreateOrderDto dto) {
        try {
            ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
            objectMapper.registerModule(new JavaTimeModule());
            return "<pre>" + objectMapper.writeValueAsString(dto) + "</pre>" + " 주문번호 : " + dto.getOrder_id() + " 수신";
        } catch (JsonProcessingException e) {
            System.out.println("JsonProcessingException: " + e);
        }
        return "";
    }
}
