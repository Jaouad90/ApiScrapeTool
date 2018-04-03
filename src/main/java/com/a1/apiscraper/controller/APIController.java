package com.a1.apiscraper.controller;

import com.a1.apiscraper.domain.*;
import com.a1.apiscraper.logic.AbstractLogger;
import com.a1.apiscraper.logic.ConsoleLogger;
import com.a1.apiscraper.logic.ErrorLogger;
import com.a1.apiscraper.logic.WarningLogger;
import com.a1.apiscraper.service.APIExporter;
import com.a1.apiscraper.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

import static com.a1.apiscraper.ApiscraperApplication.getChainOfLoggers;


@Controller
public class APIController {

    DateTimeFormatter formatter;
    AbstractLogger loggerChain = getChainOfLoggers();

    //Constant strings
    private static final String TIMEINTERVALS = "timeintervals";
    private static final String DECORATORS  = "decorators";
    private static final String SCRAPEBEHAVIORS  = "scrapebehaviors";

    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private APIService apiService;
    @Autowired
    private APIExporter apiExporter;

    public APIController(APIExporter apiExporter) {
        formatter = DateTimeFormatter.ofLocalizedDateTime( FormatStyle.SHORT )
                        .withLocale( Locale.ENGLISH)
                        .withZone( ZoneId.systemDefault() );
        //Benodigd voor gescheiden service/controller
        this.apiExporter = apiExporter;
    }


    @RequestMapping(value = "/api/add", method = RequestMethod.GET)
    public String showForm(Model model) {
        model.addAttribute(DECORATORS, repositoryService.getAllDecorators());
        model.addAttribute(SCRAPEBEHAVIORS, repositoryService.getAllScrapeBehaviors());
        model.addAttribute(TIMEINTERVALS, repositoryService.getAllTimeIntervals());
        model.addAttribute("api", new API());
        return "api/edit";
    }

    @Transactional
    @RequestMapping(value = "/api", method = RequestMethod.POST)
    public ModelAndView submit(@Valid @ModelAttribute("api") API apiModel, BindingResult result) {
            if (result.hasErrors()) {
                loggerChain.logMessage(1, "Niet alle velden correct ingevoerd");
                ModelAndView modelAndView = new ModelAndView();
                modelAndView.setViewName("api/edit");
                modelAndView.addObject("formErrors", result.getAllErrors());
                modelAndView.addObject(SCRAPEBEHAVIORS, repositoryService.getAllScrapeBehaviors());
                modelAndView.addObject(DECORATORS, repositoryService.getAllDecorators());
                modelAndView.addObject(TIMEINTERVALS, repositoryService.getAllTimeIntervals());
                return modelAndView;
            }
            API api = apiService.saveAPIModel(apiModel);
        return new ModelAndView("redirect:/api/" + api.getId());
    }

    @RequestMapping(value = "/api/{id}")
    public ModelAndView view(@PathVariable("id") API api) { ;
        api.getTimeInterval().getTimeList();
        return new ModelAndView("home/detail", "api", api);
    }

    @Transactional
    @RequestMapping(value = "/api/restore/{apiid}/{mementoid}")
    public ModelAndView restoreState(@PathVariable("apiid") API api, @PathVariable("mementoid") APIMemento apiMemento ) {
        //Restore Memento
        api = apiService.restoreAPIFromMemento(api, apiMemento);
        return new ModelAndView("redirect:/api/" + api.getId());
    }

    @Transactional
    @RequestMapping(value = "/api/edit/{id}")
    public ModelAndView edit(@PathVariable("id") API api) {
        //create editView
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("api/edit");
        modelAndView.addObject(TIMEINTERVALS, repositoryService.getAllTimeIntervals());
        modelAndView.addObject("api", api);
        modelAndView.addObject(SCRAPEBEHAVIORS, repositoryService.getAllScrapeBehaviors());
        modelAndView.addObject(DECORATORS, repositoryService.getAllDecorators());
        api.getEndpoints();
        return modelAndView;
    }

    @RequestMapping(value="/result/{resultid}", method=RequestMethod.GET)
    @ResponseBody
    public void downloadFile(@PathVariable(value="resultid") Result result, @RequestParam String format, HttpServletResponse response) throws IOException {
        loggerChain.logMessage(AbstractLogger.WARNING, "downloaden gestart");
        apiExporter.setFormat(format);
        File file = apiExporter.convertedData(result);
        InputStream inputStream = null;
        response.setContentType("application/" + format);
        response.setHeader("Content-Disposition", "attachment; filename=" + file.getName());
        response.setHeader("Content-Length", String.valueOf(file.length()));
        try {
            inputStream = new FileInputStream(file);
            FileCopyUtils.copy(inputStream, response.getOutputStream());
            inputStream.close();

        } catch (IOException e1) {
            loggerChain.logMessage(AbstractLogger.WARNING, "IOexception : " + e1.getMessage());
        } finally {
            inputStream.close();
        }
    }
}