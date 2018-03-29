package com.a1.apiscraper;

import com.a1.apiscraper.domain.API;
import com.a1.apiscraper.domain.APIConfig;
import com.a1.apiscraper.repository.APIRepository;
import com.a1.apiscraper.service.RepositoryService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApiscraperApplication.class)
@WebAppConfiguration
public class ApiscraperApplicationTests {

	private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

	@Autowired
	private RepositoryService repositoryService;

    @Autowired
    private APIRepository apiRepository;

    @Before
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

	@Test
	public void findAll_ApisFound_ShouldReturnFoundApis() throws Exception {
        API api1 = new API();
        api1.setId(1L);
        api1.setName("Coindesk");
        api1.setBaseUrl("https://coindesk.com/api/v1/");
        APIConfig apiConfig1 = new APIConfig();
        apiConfig1.setId(1L);
        api1.setConfig(apiConfig1);

        API api2 = new API();
        api2.setId(2L);
        api2.setName("Coinmarketcap");
        api2.setBaseUrl("https://coinmarketcap.com/api/v1/");
        APIConfig apiConfig2 = new APIConfig();
        apiConfig2.setId(2L);
        api2.setConfig(apiConfig2);

        Mockito.when(repositoryService.getAllAPIs()).thenReturn(Arrays.asList(api1, api2));
        Mockito.when(apiRepository.findAll()).thenReturn(Arrays.asList(api1, api2));

		mockMvc.perform(MockMvcRequestBuilders.get("/v1/api/list"))
		.andExpect(MockMvcResultMatchers.status().isOk())
        .andDo(MockMvcResultHandlers.print());

		Mockito.verify(repositoryService, Mockito.times(1)).getAllAPIs();
		Mockito.verifyNoMoreInteractions(repositoryService);
	}

}
