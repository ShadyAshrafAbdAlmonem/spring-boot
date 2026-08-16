package com.inventory.inventory_management_system.product.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inventory.inventory_management_system.common.response.PageResponse;
import com.inventory.inventory_management_system.product.dto.request.CreateProductRequest;
import com.inventory.inventory_management_system.product.dto.response.ProductDetailsResponse;
import com.inventory.inventory_management_system.product.dto.response.ProductResponse;
import com.inventory.inventory_management_system.product.service.ProductService;
import com.inventory.inventory_management_system.security.filter.ApiKeyAuthenticationFilter;
import com.inventory.inventory_management_system.security.jwt.JwtAuthenticationEntryPoint;
import com.inventory.inventory_management_system.security.jwt.JwtAuthenticationFilter;
import com.inventory.inventory_management_system.security.service.TokenBlacklistService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(
        controllers = ProductController.class,
        excludeFilters = @ComponentScan.Filter(
                type = FilterType.ASSIGNABLE_TYPE,
                classes = {ApiKeyAuthenticationFilter.class, JwtAuthenticationFilter.class}
        )
)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductService productService;

    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @MockBean
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @MockBean
    private UserDetailsService userDetailsService;

    @MockBean
    private TokenBlacklistService tokenBlacklistService;

    private static final String API_KEY = "your-secret-api-key";

    @Test
    @WithMockUser
    void getProductById_shouldReturnProduct() throws Exception {
        ProductDetailsResponse response = new ProductDetailsResponse();
        response.setId(1L);
        response.setName("iPhone 15");
        response.setSku("IPHONE-15");

        when(productService.getProductById(1L)).thenReturn(response);

        mockMvc.perform(get("/api/v1/products/1")
                        .header("X-API-KEY", API_KEY))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.name").value("iPhone 15"));
    }

    @Test
    @WithMockUser
    void getAllProducts_shouldReturnPaginatedList() throws Exception {
        ProductResponse product = new ProductResponse();
        product.setId(1L);
        product.setName("iPhone 15");

        PageResponse<ProductResponse> pageResponse = PageResponse.<ProductResponse>builder()
                .content(List.of(product))
                .pageNumber(0)
                .pageSize(10)
                .totalElements(1)
                .totalPages(1)
                .build();

        when(productService.getAllProducts(any(), eq(0), eq(10))).thenReturn(pageResponse);

        mockMvc.perform(get("/api/v1/products")
                        .header("X-API-KEY", API_KEY)
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.totalElements").value(1))
                .andExpect(jsonPath("$.data.content[0].name").value("iPhone 15"));
    }

    @Test
    @WithMockUser
    void createProduct_shouldReturnCreated() throws Exception {
        CreateProductRequest request = new CreateProductRequest();
        request.setName("iPhone 15");
        request.setSku("IPHONE-15");
        request.setPrice(new BigDecimal("999.99"));
        request.setCostPrice(new BigDecimal("700.00"));
        request.setQuantity(10);
        request.setMinQuantity(5);

        ProductDetailsResponse response = new ProductDetailsResponse();
        response.setId(1L);
        response.setName("iPhone 15");

        when(productService.createProduct(any(CreateProductRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/v1/products")
                        .header("X-API-KEY", API_KEY)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.id").value(1));
    }

    @Test
    @WithMockUser
    void createProduct_withInvalidData_shouldReturnBadRequest() throws Exception {
        CreateProductRequest request = new CreateProductRequest();
        request.setName("");
        request.setSku("");

        mockMvc.perform(post("/api/v1/products")
                        .header("X-API-KEY", API_KEY)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false));
    }
}