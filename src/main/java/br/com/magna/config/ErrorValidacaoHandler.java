package br.com.magna.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorValidacaoHandler {
	
	@Autowired
	private MessageSource messageSource;

	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	//Exception que da quando está vazio
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public List<ErrorCamposDto> handle(MethodArgumentNotValidException ex) {
		List<ErrorCamposDto> dto = new ArrayList<>();
		List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
		
		fieldErrors.forEach(erro -> {
			String mensagemErro = messageSource.getMessage(erro, LocaleContextHolder.getLocale());
			ErrorCamposDto erroDto = new ErrorCamposDto(erro.getField(), mensagemErro);
			dto.add(erroDto);
		});
		return dto;
	}
	
}
