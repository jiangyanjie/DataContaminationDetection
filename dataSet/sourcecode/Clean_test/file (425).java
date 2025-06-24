package com.example.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomCollectionEditor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.WebDataBinder;

import com.example.model.Application;
import com.example.model.Device;
import com.example.service.ApplicationService;
import com.example.service.DeviceService;


/**
 * 
 * Controller class for Application model
 * @author sabina
 *
 */
@Controller
@RequestMapping("/application")
public class ApplicationController {

	private Map<Integer, Device> deviceCache;
	
	@Autowired
	private ApplicationService applicationService;
	
	@Autowired
	private DeviceService deviceService;
	
	@RequestMapping("/")
    public String list(Map<String, Object> map) {
    	map.put("application", new Application());
        map.put("applicationList", applicationService.list());
        return "application";
    }
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addApplication(@Valid @ModelAttribute("application") Application application, BindingResult result, Map<String, Object> map) {
		if (result.hasErrors()) {
			map.put("applicationList", applicationService.list());
			return "application";
		}
		applicationService.add(application);
		return "redirect:/people/application/";
    }

    @RequestMapping("/delete/{appId}")
    public String deleteApplication(@PathVariable("appId") Integer appId) {
        applicationService.delete(appId);
        return "redirect:/people/application/";
    }
    
    // Binder method used for displaying devices on application page
    @InitBinder
    public void initBinder(WebDataBinder binder) {
    	binder.registerCustomEditor(List.class, "devices", new CustomCollectionEditor(List.class) {
    		protected Object convertElement(Object element) {
    			if (element instanceof String) {
    				return deviceCache.get(Integer.valueOf(element.toString()));
    			}
    			if (element instanceof Integer) {
    				return deviceCache.get(element);
    			}
    			if (element instanceof Device) {
    				return element;
    			}
    			return null;
    		}
    	});
    }
    
    @RequestMapping(value = "/addDevice/{appId}", method = RequestMethod.GET)
    public String addDevice(@PathVariable("appId") Integer appId, Map<String, Object> map) {
    	deviceCache = new HashMap<Integer, Device>();
    	List<Device> deviceListing = deviceService.listDevice();
    	// DeviceCache is used to check the type in order to display the device list on application page
		for (Device d : deviceListing) {
   			deviceCache.put(d.getDeviceId(), d);    		
        }
    	map.put("appDetails" , applicationService.getApp(appId));
       	map.put("appId" , appId);
    	map.put("deviceListing", deviceService.listDevice());
    	return "addDeviceToApp";
    }
    
    @RequestMapping(value = "/addDevice/mapping/{appId}", method = RequestMethod.POST)
    public String addDevice(@ModelAttribute("appDetails") Application application, 
    		BindingResult result, @PathVariable("appId") Integer appId) {
    	System.err.println("Method that updates the application and device details");
    	applicationService.save(application);
        return "redirect:/people/application/";
    }
    
    @RequestMapping("/addDevice/backToapplication")
    public String redirectToApplicationPage() {
    	return "redirect:/people/application/";
    }
    
    @RequestMapping("/backToDevice")
    public String redirectToDevicePage() {
    	return "redirect:/people/device/";
    }
}
