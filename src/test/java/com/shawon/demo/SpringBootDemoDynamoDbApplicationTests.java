package com.shawon.demo;

import com.shawon.demo.common.CSVOperations;
import com.shawon.demo.marchent.entity.Merchent;
import com.shawon.demo.marchent.service.MarchentService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.Optional;

@SpringBootTest
class SpringBootDemoDynamoDbApplicationTests {

    @Autowired
    private MarchentService marchentService;

    @Autowired
    CSVOperations csvOperations;

    @Test
    void test() throws IOException {
//        Assertions.assertTrue(csvOperations.csvToBean());
    }

    @Test
    @DisplayName("Save Merchant Happy Path")
    void saveMerchantHappyPath() {

        Merchent model = Merchent.builder()
                .id("1")
                .name("Ashik")
                .number("6666")
                .build();

        Mono<Boolean> modResult = marchentService.modMarchentUsingWebFlux(model);

        Optional<Boolean> booleanOptional = modResult.blockOptional();

        if (booleanOptional.isPresent()) {
            Assertions.assertTrue(booleanOptional.get());
        } else {
            Assertions.fail();
        }


    }

    @Test
    void abortOnEmptyNumber() {

        Merchent model = Merchent.builder()
                .id("1")
                .name("Ashik")
                .number("")
                .build();

        Mono<Boolean> modResult = marchentService.modMarchentUsingWebFlux(model);

        Optional<Boolean> booleanOptional = modResult.blockOptional();

        if (booleanOptional.isPresent()) {
            Assertions.assertFalse(booleanOptional.get());
        } else {
            Assertions.fail();
        }
    }
}
