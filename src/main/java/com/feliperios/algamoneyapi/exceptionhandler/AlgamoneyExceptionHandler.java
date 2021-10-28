package com.feliperios.algamoneyapi.exceptionhandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@ControllerAdvice
public class AlgamoneyExceptionHandler extends ResponseEntityExceptionHandler {

	@Autowired
	private MessageSource messageSource;

	// Handler para erros de requisição inválida.
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException exception,
																  HttpHeaders headers,
																  HttpStatus status,
																  WebRequest request){

		String pretty = messageSource.getMessage("error.invalidRequest", null, LocaleContextHolder.getLocale());
		String detailed = extractExceptionCause(exception);
		List<ErrorMessage> errorMessageList = Arrays.asList(new ErrorMessage(pretty, detailed));
		return handleExceptionInternal(exception, errorMessageList, headers, HttpStatus.BAD_REQUEST, request);
	}

	// Handler para erros de validação de campos.
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception,
																  HttpHeaders headers,
																  HttpStatus status,
																  WebRequest request) {

		List<ErrorMessage> errorMessageList = extractErrorMessageList(exception.getBindingResult());
		return handleExceptionInternal(exception, errorMessageList, headers, HttpStatus.BAD_REQUEST, request);
	}


	// Handler para erros de recurso não encontrado
	@ExceptionHandler({EmptyResultDataAccessException.class, EntityNotFoundException.class})
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ResponseEntity<Object> handleEmptyResultDataAccessException(RuntimeException exception, WebRequest request) {

		String pretty = messageSource.getMessage("error.resourceNotFound", null, LocaleContextHolder.getLocale());
		String detailed = extractExceptionCause(exception);
		List<ErrorMessage> errorMessageList = Arrays.asList(new ErrorMessage(pretty, detailed));

		return handleExceptionInternal(exception, errorMessageList, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
	}

	private String extractExceptionCause(RuntimeException exception) {
		if (exception.getCause() != null)
			return exception.getCause().toString();
		else
			return exception.toString();
	}

	private List<ErrorMessage> extractErrorMessageList(BindingResult bindingResult) {
		List<ErrorMessage> errorList = new ArrayList<ErrorMessage>();

		for (FieldError fieldError : bindingResult.getFieldErrors()) {
			String prettyErrorMessage = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
			String detailedErrorMessage = fieldError.toString();
			errorList.add(new ErrorMessage(prettyErrorMessage, detailedErrorMessage));
		}

		return errorList;
	}

	public static class ErrorMessage {
		private String prettyErrorMessage;
		private String detailedErrorMessage;

		ErrorMessage(String prettyErrorMessage, String detailedErrorMessage) {
			this.prettyErrorMessage = prettyErrorMessage;
			this.detailedErrorMessage = detailedErrorMessage;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;
			ErrorMessage that = (ErrorMessage) o;
			return Objects.equals(prettyErrorMessage, that.prettyErrorMessage) && Objects.equals(detailedErrorMessage, that.detailedErrorMessage);
		}

		@Override
		public int hashCode() {
			return Objects.hash(prettyErrorMessage, detailedErrorMessage);
		}

		public String getPrettyErrorMessage() {
			return prettyErrorMessage;
		}

		public void setPrettyErrorMessage(String prettyErrorMessage) {
			this.prettyErrorMessage = prettyErrorMessage;
		}

		public String getDetailedErrorMessage() {
			return detailedErrorMessage;
		}

		public void setDetailedErrorMessage(String detailedErrorMessage) {
			this.detailedErrorMessage = detailedErrorMessage;
		}
	}
}
