package com.example.lecture04.news;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class NewsApiTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void testNewsGetSuccess() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/news/123"))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(MockMvcResultMatchers.jsonPath("$.newsId", Matchers.is(123)))
				.andExpect(MockMvcResultMatchers.jsonPath("$.title", Matchers.is("title 123")))
				.andExpect(MockMvcResultMatchers.jsonPath("$.details", Matchers.is("details 123")))
				.andExpect(MockMvcResultMatchers.jsonPath("$.reportedBy", Matchers.is("reporter 123")))
				.andExpect(MockMvcResultMatchers.jsonPath("$.reportedAt", Matchers.notNullValue()));
	}

	@Test
	public void testNewsGetNotFound() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/news/456"))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	@Order(1)
	@Test
	public void testNewsGetListSuccess() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/news/bitcoin"))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)));
	}

	@Order(2)
	@Test
	public void testNewsPostSuccess() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/news")
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content("{\"title\":\"title 789\",\"details\":\"details 789\"}")
						.with(SecurityMockMvcRequestPostProcessors.csrf())
						.with(SecurityMockMvcRequestPostProcessors.user("test")
								.authorities(new SimpleGrantedAuthority("reporter"))))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isCreated())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(MockMvcResultMatchers.jsonPath("$.newsId", Matchers.notNullValue()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.title", Matchers.is("title 789")))
				.andExpect(MockMvcResultMatchers.jsonPath("$.details", Matchers.is("details 789")))
				.andExpect(MockMvcResultMatchers.jsonPath("$.reportedBy", Matchers.is("test")))
				.andExpect(MockMvcResultMatchers.jsonPath("$.reportedAt", Matchers.notNullValue()));
	}
}
