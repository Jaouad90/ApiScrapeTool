package com.a1.apiscraper;

import com.a1.apiscraper.controller.APIRestController;
import com.a1.apiscraper.domain.API;
import com.a1.apiscraper.domain.APIConfig;
import com.a1.apiscraper.domain.Endpoint;
import com.a1.apiscraper.domain.Result;
import com.a1.apiscraper.service.RepositoryService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.sql.Date;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApiscraperApplication.class)
@WebAppConfiguration
@ActiveProfiles("test")
public class ApiRestControllerTests {

    private MockMvc mockMvc;

    @Mock
    private RepositoryService repositoryService;

    @InjectMocks
    private APIRestController apiRestController;

    API api1 = new API();
    API api2 = new API();
    List<Result> results = new ArrayList<>();
    java.util.Date now = Date.from(Instant.now());

    @Before
    public void setup(){
        api1.setId(1L);
        api1.setName("Coindesk");
        api1.setBaseUrl("https://coindesk.com/api/v1/");
        APIConfig apiConfig1 = new APIConfig();
        apiConfig1.setId(1L);
        api1.setConfig(apiConfig1);

        api2.setId(2L);
        api2.setName("Marktplaats");
        api2.setBaseUrl("https://www.marktplaats.nl/kijkinuwwijk/");
        APIConfig apiConfig2 = new APIConfig();
        apiConfig2.setId(2L);
        api2.setConfig(apiConfig2);
        Endpoint endpoint = new Endpoint();
        endpoint.setId(1L);
        endpoint.setName("items.json?ids=m1262177404");
        Result result1 = new Result();
        result1.setId(1L);
        result1.setResult("{\"valid\":true,\"callback\":null,\"value\":{\"ads\":[{\"title\":\"Trui van het merk Soliver maat M/L\",\"imageUrl\":\"//i.ebayimg.com/00/s/MTAyNFg3NjQ=/z/FXoAAOSwmXlansGj/$_82.JPG\",\"vipUrl\":\"https://link.marktplaats.nl/m1262177404\",\"price\":\"Bieden\"}]},\"errors\":null,\"messages\":null}");
        result1.setDateTimeStamp(Date.from(Instant.now()));
        Result result2 = new Result();
        result2.setId(2L);
        result2.setResult("{\"valid\":true,\"callback\":null,\"value\":{\"ads\":[{\"title\":\"Trui van het merk Soliver maat M/L\",\"imageUrl\":\"//i.ebayimg.com/00/s/MTAyNFg3NjQ=/z/FXoAAOSwmXlansGj/$_82.JPG\",\"vipUrl\":\"https://link.marktplaats.nl/m1262177404\",\"price\":\"Bieden\"}]},\"errors\":null,\"messages\":null}");
        result2.setDateTimeStamp(Date.from(Instant.now().plusSeconds(172800L)));

        results.add(result1);
        results.add(result2);

        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(apiRestController)
                .build();
    }

    @Test
    public void findOneAPI() throws Exception {
        Mockito.when(repositoryService.getSingleAPI(1L)).thenReturn(api1);

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/api/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.content().string(containsString("Coindesk")));

        verify(repositoryService, times(1)).getSingleAPI(1L);
        verifyNoMoreInteractions(repositoryService);
    }

    @Test
    public void findAllAPIS() throws Exception {
        Mockito.when(repositoryService.getAllAPIs()).thenReturn(Arrays.asList(api1, api2));

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/api/list"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.content().string(containsString("Coindesk")))
                .andExpect(MockMvcResultMatchers.content().string(containsString("Marktplaats")));

        verify(repositoryService, times(1)).getAllAPIs();
        verifyNoMoreInteractions(repositoryService);
    }

//    @Test
//    public void succeedingFindAllResultsForAPI() throws Exception {
//        Mockito.when(repositoryService.getAllResultsForApiBetween(2L, Date.from(Instant.ofEpochSecond(1514764800)), now)).thenReturn(results);
//
//        mockMvc.perform(MockMvcRequestBuilders.get("/v1/api/2/results").param("till", String.valueOf(now.getTime() / 1000)))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
//                .andExpect((MockMvcResultMatchers.content()).string(containsString("Trui van het merk Soliver maat M/L")));
//
//        verify(repositoryService, times(1)).getAllResultsForApiBetween(2L, Date.from(Instant.ofEpochSecond(1514764800)), now);
//        verifyNoMoreInteractions(repositoryService);
//    }

    @Test
    public void succeedingFindAllResultsForAPIThatDoesNotExist() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/api/3/results"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

}