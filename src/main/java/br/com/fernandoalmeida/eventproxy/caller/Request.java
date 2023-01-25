package br.com.fernandoalmeida.eventproxy.caller;

import java.util.Optional;

import javax.validation.constraints.Digits;

import org.hibernate.validator.constraints.URL;

import lombok.Data;

@Data
public class Request {
	private Optional<@Digits(fraction = 0, integer = 3) Integer> delay;

	@URL
	private String url;

}
