package com.pragma.reto_aws;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(
		properties = {
				"spring.autoconfigure.exclude=org.springframework.boot.r2dbc.autoconfigure.R2dbcAutoConfiguration"
		}
)
class RetoAwsApplicationTests {

	@Test
	void contextLoads() {
	}
}
