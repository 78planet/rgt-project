package com.example.rgtproject.orders.presentation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import com.example.rgtproject.orders.domain.CreateOrderDto;
import com.example.rgtproject.orders.domain.OrdersRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class OrdersApiTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    private OrdersRepository ordersRepository;

    @DisplayName("주문 생성 성공")
    @Test
    void createOrders_success() throws Exception {
        // given
        CreateOrderDto createOrderDto = getCreateOrderDto("0001", "caffelatte");

        // when
        MockHttpServletResponse response = createOrdersApi(createOrderDto);

        // then
        assertAll(
            () -> assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value()),
            () -> assertThat(response.getContentAsString()).contains(createOrderDto.getOrder_id()),
            () -> assertThat(response.getContentAsString()).contains(createOrderDto.getProduct_name()),
            () -> assertThat(response.getContentAsString()).contains("주문번호")
        );
    }

    @DisplayName("중복 주문 데이터 제거 성공")
    @Test
    void removeDuplicates_success() throws Exception {
        // given
        String productName = "카페라떼";
        for (int i = 0; i < 5; i++) {
            createOrdersApi(getCreateOrderDto("0002", productName));
        }

        // when
        assertThat(ordersRepository.countOrdersByProductName(productName)).isEqualTo(5);
        removeDuplicatesApi();

        // then
        assertThat(ordersRepository.countOrdersByProductName(productName)).isEqualTo(1);
    }

    @DisplayName("product name 변경 완료")
    @Test
    void modifyProductName_success() throws Exception {
        // given
        String productName = "아아";
        for (int i = 0; i < 5; i++) {
            createOrdersApi(getCreateOrderDto("0003", productName));
        }

        // when
        String willChangeData = "아이스아메리카노";
        modifyProductNameApi(productName, willChangeData);

        // then
        assertAll(
            () -> assertThat(ordersRepository.countOrdersByProductName(productName)).isEqualTo(0),
            () -> assertThat(ordersRepository.countOrdersByProductName(willChangeData)).isEqualTo(5)
        );
    }

    @DisplayName("product name 변경 후 insert 완료")
    @Test
    void modifyProductNameAndInsert_success() throws Exception {
        // productName a를 b로 바꾸는 modifyProductNameApi를 호출했다면,
        // 다음에 a 이름으로 createOrdersApi 호출 시 자동으로 productName은 b 로 insert 됨.
        // given
        String productName = "카페모캉2";
        for (int i = 0; i < 5; i++) {
            createOrdersApi(getCreateOrderDto("0004", productName));
        }
        String willChangeData = "카페모카";
        modifyProductNameApi(productName, willChangeData);

        // when
        for (int i = 0; i < 5; i++) {
            createOrdersApi(getCreateOrderDto("0004", productName));
        }

        // then
        assertAll(
            () -> assertThat(ordersRepository.countOrdersByProductName(productName)).isEqualTo(0),
            () -> assertThat(ordersRepository.countOrdersByProductName(willChangeData)).isEqualTo(10)
        );
    }


    private static CreateOrderDto getCreateOrderDto(String orderId, String productName) {
        return CreateOrderDto.builder()
            .order_id(orderId)
            .product_name(productName)
            .options("")
            .table_no(1)
            .quantity(2L)
            .order_date(LocalDate.now())
            .order_time(LocalTime.now())
            .robot_status("")
            .date_time(LocalDateTime.now())
            .seq("23101000000000")
            .dong("12")
            .ho("1202")
            .orderer_name("홍길동")
            .build();
    }

    protected MockHttpServletResponse createOrdersApi(CreateOrderDto createOrderDto) throws Exception {
        return mockMvc.perform(post("/api/orders")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(createOrderDto)))
            .andDo(print())
            .andReturn().getResponse();
    }


    protected void removeDuplicatesApi() throws Exception {
        mockMvc.perform(post("/api/orders/remove-duplicates")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andDo(print())
            .andReturn().getResponse();
    }

    protected MockHttpServletResponse modifyProductNameApi(String targetData, String willChangeData) throws Exception {
        return mockMvc.perform(patch("/api/orders/{targetData}/{willChangeData}", targetData, willChangeData))
            .andDo(print())
            .andReturn().getResponse();
    }
}
