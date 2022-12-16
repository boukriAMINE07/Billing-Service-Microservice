package io.boukriinfo.billingservice;

import io.boukriinfo.billingservice.Repository.BillRepository;
import io.boukriinfo.billingservice.Repository.ProductItemsRepository;
import io.boukriinfo.billingservice.entities.Bill;
import io.boukriinfo.billingservice.entities.ProductItems;
import io.boukriinfo.billingservice.feign.CustomerRestClient;
import io.boukriinfo.billingservice.feign.ProductItemsRestClient;
import io.boukriinfo.billingservice.models.Customer;
import io.boukriinfo.billingservice.models.Product;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.hateoas.PagedModel;

import java.util.Date;

@SpringBootApplication
@EnableFeignClients
public class BillingServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BillingServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner start(BillRepository billRepository,
                            ProductItemsRepository productItemsRepository,
                            CustomerRestClient customerRestClient,
                            ProductItemsRestClient productItemsRestClient) {
        return args -> {
            Customer customer = customerRestClient.getCustomerById(1L);
            Bill bill = billRepository.save(new Bill(null, new Date(), null, customer.getId(), null));
            PagedModel<Product> productPagedModel = productItemsRestClient.pageProducts(0, 20);
            productPagedModel.forEach(p -> {
                ProductItems productItems=new ProductItems();
                productItems.setPrice(p.getPrice());
                productItems.setQuantity(1 + (int) (Math.random() * 100));
                productItems.setBill(bill);

                productItems.setProductID(p.getId());
                productItems.setId(null);
                productItemsRepository.save(productItems);



            });

        };
    }

}
