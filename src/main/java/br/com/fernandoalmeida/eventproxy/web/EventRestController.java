package br.com.fernandoalmeida.eventproxy.web;

import java.util.Locale;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.fernandoalmeida.eventproxy.caller.EventCaller;
import br.com.fernandoalmeida.eventproxy.caller.Request;
import br.com.fernandoalmeida.eventproxy.caller.Status;
import br.com.fernandoalmeida.eventproxy.caller.Status.Result;
import br.com.fernandoalmeida.eventproxy.config.ApplicationConfig;

@RestController
public class EventRestController {
	private static final String SUCCESS_SCHEDULED_MESSAGE = "success.scheduled";

	@Autowired
	private EventCaller caller;

	@Autowired
	private ApplicationConfig config;

	@Autowired
	private MessageSource messageSource;

	/**
	 * Calls an event on IFTTT with a delay
	 *
	 * @param eventName String eventName
	 * @param key       String key
	 * @param delay     Delay in seconds
	 */
	@GetMapping(value = "/callIfttEvent")
	public Status callIftttEvent(@RequestParam("event") @NotBlank String eventName,
			@RequestParam("key") @NotBlank String key, @RequestParam("delay") Optional<Integer> delay, Locale locale) {
		caller.callWithDelay(getEventUrl(eventName, key), delay.isPresent() ? delay.get() : config.getDefaultDelay());

		return scheduled(delay, locale);
	}

	/**
	 * Calls a generic url with a delay
	 *
	 * @param key   String key
	 * @param delay Delay in seconds
	 */
	@GetMapping(value = "/callEvent")
	public Status callEvent(@RequestParam("key") @NotBlank String key, @RequestParam("delay") Optional<Integer> delay,
			Locale locale) {
		caller.callWithDelay(config.getUrlByKey(key), delay.isPresent() ? delay.get() : config.getDefaultDelay());

		return scheduled(delay, locale);
	}

	@PostMapping(value = "/callEvent")
	public Status callEvent(@RequestBody @Valid Request request, Locale locale) {
		Optional<Integer> delay = request.getDelay();

		caller.callWithDelay(request.getUrl(), delay.isPresent() ? delay.get().intValue() : config.getDefaultDelay());

		return scheduled(delay, locale);
	}

	/**
	 * Builds an ifttt url for an <code> eventName </code> and key
	 * <code> key </code>
	 * 
	 * @param eventName String eventName
	 * @param key       String key
	 * @return
	 */
	private String getEventUrl(String eventName, String key) {
		return config.getIftttUrl() + "/trigger/" + eventName + "/with/key/" + key;
	}

	private Status scheduled(Optional<Integer> optionalDelay, Locale locale) {
		Integer delay = optionalDelay.isPresent() ? optionalDelay.get() : 0;

		String message = messageSource.getMessage(SUCCESS_SCHEDULED_MESSAGE, new Integer[] { delay }, locale);

		return Status.builder().result(Result.SCHEDULED).message(message).build();
	}

}
