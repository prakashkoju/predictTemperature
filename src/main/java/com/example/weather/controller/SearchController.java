package com.example.weather.controller;

import com.example.weather.ApiProperties;
import com.example.weather.domain.Location;
import com.example.weather.domain.Weather;
import com.example.weather.domain.UserInput;
import com.example.weather.domain.Zipcode;
import com.example.weather.service.LocationService;
import com.example.weather.service.WeatherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class SearchController {
	private static final Logger LOGGER = LoggerFactory.getLogger(SearchController.class);

	private final WeatherService weatherService;
	private final LocationService locationService;

	private final ApiProperties properties;

	public SearchController(WeatherService weatherService, ApiProperties properties, LocationService locationService) {
		this.weatherService = weatherService;
		this.properties = properties;
		this.locationService=locationService;
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView showWeatherSearchForm() {
		LOGGER.debug("Received request for weather search view");
		return new ModelAndView("weather_search", "searchForm", new UserInput());
	}

	@RequestMapping(value = "/", method = RequestMethod.POST)
	public ModelAndView searchWeather(@Valid @ModelAttribute("searchForm") UserInput form, BindingResult bindingResult) {
		LOGGER.debug("Received request to search weather {}, with result={}", form, bindingResult);
		String view = "weather_search";	//if city=="" stick in main view page
		ModelMap model = new ModelMap();
		if(!"".equals(form.getZip())){
			List<Location> location=this.locationService.getLocation(form.getZip());
			if(location.stream().flatMap(location1 ->
					location1.getZipcodes().stream()).filter(zipcode -> zipcode.getZipcode().equals(form.getZip()))
					.count()>0){
				List<WeatherController> weatherSummaryList = getSummary(location.get(0).zipcodes.get(0).getDefault_city());
				if (weatherSummaryList != null && weatherSummaryList.size() > 0) {
					view = "weather_summary";
					model.addAttribute("weather_summary", weatherSummaryList);
				}
			}
		}

		else if (!"".equals(form.getCity())) {
			List<WeatherController> weatherSummaryList = getSummary(form.getCity());
			if (weatherSummaryList != null && weatherSummaryList.size() > 0) {
				view = "weather_summary";
				model.addAttribute("weather_summary", weatherSummaryList);
			}
		}
		return new ModelAndView(view, model);
	}

	protected List<WeatherController> getSummary(String city){
		List<WeatherController> summary = new ArrayList<>();
		Weather weather = this.weatherService.getWeather(city);
		if(weather!=null) {
			summary.add(new WeatherController(city, weather));
		}
		return summary;
	}
}
