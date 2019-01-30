package hu.bp.netcafe.backend.rest.advice;

import hu.bp.netcafe.backend.rest.exception.DeviceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

public class DeviceNotFoundAdvice {

	@ResponseBody
	@ExceptionHandler(DeviceNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	String deviceNotFoundHandler(DeviceNotFoundException ex) {
		return ex.getMessage();
	}
}
