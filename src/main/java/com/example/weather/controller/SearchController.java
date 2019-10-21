package com.example.weather.controller;

import com.example.weather.apiProperty.ApiProperty;
import com.example.weather.domain.Location;
import com.example.weather.domain.Weather;
import com.example.weather.service.ILocationService;
import com.example.weather.service.IWeatherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
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

@Controller
@Component
public class SearchController {
	private static final Logger LOGGER = LoggerFactory.getLogger(SearchController.class);

	private final IWeatherService weatherService;
	private final ILocationService locationService;

	private final ApiProperty properties;

	public SearchController(IWeatherService weatherService, ApiProperty properties, ILocationService locationService) {
		this.weatherService = weatherService;
		this.locationService=locationService;
		this.properties = properties;

	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView showWeatherSearchForm() {
		LOGGER.debug("Received request for weather search view");
		return new ModelAndView("weather_search", "searchForm", new LocationViewModel());
	}

	@RequestMapping(value = "/", method = RequestMethod.POST)
	public ModelAndView searchWeather(@Valid @ModelAttribute("searchForm") LocationViewModel locationViewModel, BindingResult bindingResult) {
		LOGGER.debug("Received request to search weather {}, with result={}", locationViewModel, bindingResult);
		String view = "weather_search";	//if city=="" stick in main view page
		ModelMap model = new ModelMap();
		if(!"".equals(locationViewModel.getZip())){
			List<Location> location=this.locationService.getLocation(locationViewModel.getZip());
			if(location!=null && location.stream().flatMap(l ->
					l.getZipcodes().stream()).filter(z -> z.getZipcode().equals(locationViewModel.getZip()))
					.count()>0){
				List<WeatherViewModel> weatherSummaryList = getSummary(location.get(0).zipcodes.get(0).getDefault_city());
				if (weatherSummaryList != null && weatherSummaryList.size() > 0) {
					view = "weather_summary";
					model.addAttribute("weather_summary", weatherSummaryList);
				}
			}
		}

		else if (!"".equals(locationViewModel.getCity())) {
			List<WeatherViewModel> weatherSummaryList = getSummary(locationViewModel.getCity());
			if (weatherSummaryList != null && weatherSummaryList.size() > 0) {
				view = "weather_summary";
				model.addAttribute("weather_summary", weatherSummaryList);
			}
		}
		return new ModelAndView(view, model);
	}

	protected List<WeatherViewModel> getSummary(String city){
		List<WeatherViewModel> summary = new ArrayList<>();
		Weather weather = this.weatherService.getWeather(city);
		if(weather!=null) {
			summary.add(new WeatherViewModel(city, weather));
		}
		return summary;
	}
}
