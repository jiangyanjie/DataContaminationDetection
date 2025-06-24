package com.jsf.demo;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@FacesConverter(value = "customDateConverterImpl")
public class CustomDateConverterImpl implements Converter {

	final Logger logger = LoggerFactory
			.getLogger(CustomDateConverterImpl.class);

	/**
	 * The minimum value for age that we will be using
	 */
	final static int MINIMUM_AGE = 16;

	/**
	 * Our preferred date format
	 */
	final public static String LOCAL_DATE_FORMAT = "dd/MM/yyyy";

	public String getAsString(FacesContext context, UIComponent component,
			Object objectValue) {

		MiscUtils.checkForNull(context, "context");
		
		MiscUtils.checkForNull(component, "component");
		logger.debug("FYI: objectValue : " + objectValue);
		final Date date = (Date) objectValue;

		SimpleDateFormat sdf = new SimpleDateFormat(LOCAL_DATE_FORMAT);
		Calendar c1 = Calendar.getInstance(); // today
		c1.setTime(date);

		return sdf.format(c1.getTime());
	}

	public Object getAsObject(FacesContext context, UIComponent component,
			String stringValue) {

		MiscUtils.checkForNull(context, "context");
		
		MiscUtils.checkForNull(component, "component");

		DateFormat df = new SimpleDateFormat(LOCAL_DATE_FORMAT);

		try {

			Date dateValue = df.parse(stringValue);
			
			logger.debug(String.format("FYI: date value [%s]", dateValue));
			
			int currentAgeInYear = MiscUtils.calculateAgeInYear(dateValue);

			if (currentAgeInYear < MINIMUM_AGE) {

				String text = MiscUtils.getMessage(
						MiscUtils.getMessage("valid_age_value"), new Object[] {
								MINIMUM_AGE, showAgeValue(currentAgeInYear) });

				FacesMessage message = new FacesMessage(
						FacesMessage.SEVERITY_ERROR, text, text);

				throw new ConverterException(message);
			}

			return dateValue;
		} catch (ParseException e) {
			String messageText = MiscUtils
					.getMessage("value_must_be_valid_date");
			logger.debug(String.format("FYI: date value entered [%s]", stringValue));
			FacesMessage message = new FacesMessage(
					FacesMessage.SEVERITY_ERROR, messageText, messageText);
			throw new ConverterException(message);
		}
	}

	/**
	 * Provide user friendly message for internal use.
	 */
	private String showAgeValue(int age) {
		if (age <= 0) {
			return "less than one year";
		} else if (age == 1) {
			return "just one year";
		}

		return String.format("%s years", age);
	}

}