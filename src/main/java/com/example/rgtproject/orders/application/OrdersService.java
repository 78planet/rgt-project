package com.example.rgtproject.orders.application;

import com.example.rgtproject.orders.domain.CreateOrderDto;
import com.example.rgtproject.orders.domain.Orders;
import com.example.rgtproject.orders.domain.OrdersRepository;
import com.example.rgtproject.orders.domain.UpdateProductNameMap;
import com.example.rgtproject.orders.domain.UpdateProductNameMapRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrdersService {

    private final OrdersRepository ordersRepository;
    private final UpdateProductNameMapRepository productNameMapRepository;

    @Transactional
    public CreateOrderDto createOrders(CreateOrderDto createOrderDto) {
        UpdateProductNameMap upMap = getUpdateProductNameMap(createOrderDto.getProduct_name());

        if (upMap != null) {
            createOrderDto.setProductName(upMap.getWillChangeData());
        }
        Orders orders = createOrderDto.toOrders();
        ordersRepository.save(orders);

        return createOrderDto;
    }

    private UpdateProductNameMap getUpdateProductNameMap(String targetData) {
        return productNameMapRepository.findUpdateProductNameMapByTargetData(targetData);
    }

    public List<CreateOrderDto> getAllOrders() {
        return CreateOrderDto.from(ordersRepository.findAll());
    }

    @Transactional
    public void removeDuplicateIds() {
        List<String> distinctIds = ordersRepository.findAll()
            .stream()
            .map(Orders::getOrderId)
            .distinct()
            .toList();

        for (String orderId : distinctIds) {
            List<Orders> entities = ordersRepository.findAllByOrderIdOrderByOrderDateDesc(orderId);
            for (int i = 1; i < entities.size(); i++) {
                ordersRepository.delete(entities.get(i));
            }
        }
    }

    @Transactional
    public void updateProductName(String targetData, String willChangeData) {

        productNameMapRepository.save(new UpdateProductNameMap(targetData, willChangeData));
        ordersRepository.updateProductName(targetData, willChangeData);
    }
}
