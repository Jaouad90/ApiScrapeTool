package com.a1.apiscraper;

import com.a1.apiscraper.controller.APIController;
import com.a1.apiscraper.domain.API;
import com.a1.apiscraper.domain.APIConfig;
import com.a1.apiscraper.domain.TimeInterval;
import com.a1.apiscraper.service.RepositoryService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApiscraperApplication.class)
@WebAppConfiguration
@ActiveProfiles("test")
public class APIControllerTests {

    private MockMvc mockMvc;

    @Mock
    private RepositoryService repositoryService;

    @InjectMocks
    private APIController apiController;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(apiController)
                .build();
    }

    @Test
    public void testGetAPIAddPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/add"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("api/edit"));
    }

    @Test
    public void testShowAPIDetailPage() throws Exception {
        API api = new API();
        api.setId(1L);
        api.setName("Coindesk");
        api.setBaseUrl("https://coindesk.com/api/v1/");
        APIConfig apiConfig = new APIConfig();
        apiConfig.setId(1L);
        api.setConfig(apiConfig);
        TimeInterval interval1 = new TimeInterval();
        interval1.setIntervalName("Halfuur");
        api.setTimeInterval(interval1);

        Mockito.when(repositoryService.getSingleAPI(1L)).thenReturn(api);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("api/detail"));
    }
}
