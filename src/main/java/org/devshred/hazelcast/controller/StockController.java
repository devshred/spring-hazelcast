package org.devshred.hazelcast.controller;

import org.devshred.hazelcast.persistence.Share;
import org.devshred.hazelcast.services.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class StockController {
    @Autowired
    private StockService stockService;

    @RequestMapping("/")
    public ModelAndView listShares(ModelMap model) {
        final ModelAndView modelAndView = new ModelAndView("list");
        model.addAttribute("shares", stockService.findAll());
        return modelAndView;
    }

    @RequestMapping(value = "/share/{mic}/")
    public ModelAndView showShare(@PathVariable final String mic) {
        final ModelAndView modelAndView = new ModelAndView("share/show");

        final Share share = stockService.findByMic(mic);
        modelAndView.addObject("share", share);

        return modelAndView;
    }

    @RequestMapping(value = "/share/{mic}/{quote}/")
    public ModelAndView updateQuote(@PathVariable final String mic, @PathVariable final int quote) {
        final ModelAndView modelAndView = new ModelAndView("share/show");

        final Share share = stockService.updateQuote(mic, quote);
        modelAndView.addObject("share", share);

        return modelAndView;
    }

    @RequestMapping(value = "/init")
    public String init() {
        stockService.initDb();
        return "redirect:/";
    }
}