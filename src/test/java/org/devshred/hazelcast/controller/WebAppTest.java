package org.devshred.hazelcast.controller;

import org.devshred.hazelcast.config.WebAppConfig;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;


@ContextConfiguration(classes = WebAppConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class WebAppTest {
	private MockMvc mockMvc;

	@Autowired
	@SuppressWarnings("SpringJavaAutowiringInspection")
	protected WebApplicationContext wac;

	@Before
	public void setup() {
		mockMvc = webAppContextSetup(wac).build();
	}

	@Test
	public void list() throws Exception {
		mockMvc.perform(get("/")).andExpect(status().isOk()).andExpect(view().name("list"));
	}
}
