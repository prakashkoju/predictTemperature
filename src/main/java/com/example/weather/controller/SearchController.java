package com.example.weather.controller;

import com.example.weather.apiProperty.ApiProperty;
import com.example.weather.domain.Location;
import com.example.weather.domain.Weather;
import com.example.weather.domain.Zipcode;
import com.example.weather.service.LocationService;
import com.example.weather.service.WeatherService;
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
import java.util.stream.Collectors;

@Controller
@Component
public class SearchController {
	private static final Logger LOGGER = LoggerFactory.getLogger(SearchController.class);

	private final WeatherService weatherService;
	private final LocationService locationService;

	private final ApiProperty properties;

	public SearchController(WeatherService weatherService, ApiProperty properties, LocationService locationService) {
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
		if(!locationViewModel.getZip().equals(""))
		{
			String city=getCityByZipCode(locationViewModel.getZip());
			if(city==""){LOGGER.debug("No City Found for ZipCode={}", locationViewModel.getZip());
				return new ModelAndView(view, model);
			}
			 locationViewModel.setCity(city);
		}
		// weather Api call
		List<WeatherViewModel> weatherSummaryList = getSummary(locationViewModel.getCity());
		if (weatherSummaryList != null && weatherSummaryList.size() > 0) {
			view = "weather_summary";
			model.addAttribute("weather_summary", weatherSummaryList);
		}
		return new ModelAndView(view, model);
	}

	private String getCityByZipCode(String zipCode){
		List<Location> location=this.locationService.getLocation(zipCode);
		if(location!=null ){
			List<Zipcode> possibleMatchedZipCodes=location.stream().flatMap(l ->l.getZipcodes().stream())
					.filter(z -> z.getZipcode().equals(zipCode)).collect(Collectors.toList());
			if(possibleMatchedZipCodes!=null &&possibleMatchedZipCodes.size()>0) return possibleMatchedZipCodes.get(0).getDefault_city();
		}
		return "";
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
