package com.waveq.imgbook.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
 
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("com.waveq.imgbook.validators.PasswordValidator")
public class PasswordValidator implements Validator {
	
	private static final String PASSWORD_PATTERN = "[0-9]{2}[a-z]{4}";
	 
	private Pattern pattern;
	private Matcher matcher;
 
	public PasswordValidator(){
		  pattern = Pattern.compile(PASSWORD_PATTERN);
	}
	@Override
	public void validate(FacesContext context, UIComponent component,
			Object value) throws ValidatorException {
		matcher = pattern.matcher(value.toString());
		if(!matcher.matches()){
 
                    FacesMessage msg = 
                        new FacesMessage("Hasło powinno być w formacie CCLLLL,"
                            + " gdzie C to cyfra a L to mala liteka", 
                            "Hasło powinno być w formacie CCLLLL, gdzie C to cyfra");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);
		}
 
	}

}