package com.a1.apiscraper;

import com.a1.apiscraper.controller.APIRestController;
import com.a1.apiscraper.domain.API;
import com.a1.apiscraper.domain.APIConfig;
import com.a1.apiscraper.domain.CareTaker;
import com.a1.apiscraper.repository.APIRepository;
import com.a1.apiscraper.service.RepositoryService;
import io.swagger.annotations.Api;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.mock;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApiscraperApplication.class)
@WebAppConfiguration
@ActiveProfiles("production")
public class ApiRestControllerTests {

    private MockMvc mockMvc;

    @Mock
    private RepositoryService repositoryService;

    @InjectMocks
    private APIRestController apiRestController;

    API api1 = new API();
    API api2 = new API();


    @Before
    public void setup(){
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
        mockMvc = MockMvcBuilders.standaloneSetup(apiRestController)
                .build();
    }

    @Test
    public void findOneAPI() throws Exception {

        Mockito.when(repositoryService.getSingleAPI(1L)).thenReturn(api1);

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/api/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.content().string(containsString("Coindesk")))
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    public void findAllAPIS() throws Exception {

        Mockito.when(repositoryService.getAllAPIs()).thenReturn(Arrays.asList(api1, api2));

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/api/list"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.content().string(containsString("Coindesk")))
                .andExpect(MockMvcResultMatchers.content().string(containsString("Coinmarketcap")))
                .andDo(MockMvcResultHandlers.print());

    }

}