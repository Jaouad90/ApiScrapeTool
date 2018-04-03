package com.a1.apiscraper;


import com.a1.apiscraper.controller.HomeController;
import com.a1.apiscraper.domain.API;
import com.a1.apiscraper.domain.APIConfig;
import com.a1.apiscraper.service.RepositoryService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApiscraperApplication.class)
@WebAppConfiguration
@ActiveProfiles("test")
public class HomeControllerTests {

    private MockMvc mockMvc;

    @Mock
    private RepositoryService repositoryService;

    @InjectMocks
    private HomeController homeController;

    API api1 = new API();
    API api2 = new API();


    @Before
    public void setup() {
        api1.setId(1L);
        api1.setName("Coindesk");
        api1.setBaseUrl("https://coindesk.com/api/v1/");
        APIConfig apiConfig1 = new APIConfig();
        apiConfig1.setId(1L);
        api1.setConfig(apiConfig1);

        api2.setId(2L);
        api2.setName("Coinmarketcap");
        api2.setBaseUrl("https://coinmarketcap.com/api/v1/");
        APIConfig apiConfig2 = new APIConfig();
        apiConfig2.setId(2L);
        api2.setConfig(apiConfig2);
        mockMvc = MockMvcBuilders.standaloneSetup(homeController)
                .build();
    }



    @Test
    public void testGetHomePage() throws Exception {
        Mockito.when(repositoryService.getAllAPIs()).thenReturn(Arrays.asList(api1, api2));

        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("home/home"));
    }

}