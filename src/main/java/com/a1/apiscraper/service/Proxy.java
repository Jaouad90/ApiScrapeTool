package com.a1.apiscraper.service;

import com.a1.apiscraper.domain.*;

import java.util.HashMap;

public interface Proxy {
    HashMap<Endpoint, Result> proxyScrapeSingleAPI(long apiID);
    API proxySaveAPIModel(API apiModel);
    API proxyRestoreAPIFromMemento(API api, APIMemento apiMemento);
    void proxySaveAPIConfig(APIConfig config);
    void proxyAutoLogin(String username, String password);
}
