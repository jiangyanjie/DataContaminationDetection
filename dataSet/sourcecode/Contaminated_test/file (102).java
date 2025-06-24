package logger.customFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import logger.filters.Filterer;
import logger.outputs.Output;

/**
 * A factory for creating Custom objects.
 */
public class CustomFactory {

	/**
	 * Check if the Custom Object can be implemented.
	 *
	 * @param oneClass the class generated with the String Implementor
	 * @param customClass the custom class for the method createCustom
	 * @return true, if Custom Object can be implemented
	 */
	private boolean implementsCustom(final Class<?> oneClass, final Class<?> customClass) {
		Class<?>[] interfacesImplemented = oneClass.getInterfaces();
		for (Class<?> oneInterface: interfacesImplemented) {
			if (oneInterface.getName().equals(customClass.getName())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Creates a new Custom object.
	 *
	 * @param implementor Name of the object class to create
	 * @param params the parameters of the constructor of the class to create
	 * @param customClassToCreate the custom class to create
	 * @return the custom object
	 */
	private Object createCustom(final String implementor, final String[] params, final Class<?> customClassToCreate) {
		Class<?> customClass;
		try {
			customClass = Class.forName(implementor);
			if (!implementsCustom(customClass, customClassToCreate)) {
				return null;
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		Constructor<?>[] allConstructors;
		Constructor<?> constructor = null;
		Object custom;
		try {
			allConstructors = customClass.getDeclaredConstructors();
			for (Constructor<?> oneConstructor: allConstructors) {
				if (oneConstructor.getParameterTypes().length == params.length) { // assuming types are correct
					constructor = oneConstructor;
				}
			}
			custom = constructor.newInstance((Object[]) params);
		} catch (InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException
				| SecurityException e) {
			e.printStackTrace();
			return null;
		}
		return custom;
	}

	/**
	 * Creates a new Custom object.
	 *
	 * @param implementor Name of the object class to create
	 * @param params the parameters of the constructor of the class to create
	 * @return the filterer
	 * @throws CustomFilterException the custom filter exception
	 */
	public final Filterer createCustomFilter(final String implementor, final String[] params) throws CustomFilterException {
		Class<Filterer> customClassToCreate = Filterer.class;
		Filterer custom = customClassToCreate.cast(createCustom(implementor, params, customClassToCreate));
		if (custom == null) {
			throw new CustomFilterException();
		}
		return custom;
	}

	/**
	 * Creates a new Custom object.
	 *
	 * @param implementor Name of the object class to create
	 * @param params the parameters of the constructor of the class to create
	 * @return the writable object
	 * @throws CustomOutputException the custom output exception
	 */
	public final Output createCustomOutput(final String implementor, final String[] params) throws CustomOutputException {
		Class<Output> customClassToCreate = Output.class;
		Output custom = customClassToCreate.cast(createCustom(implementor, params, customClassToCreate));
		if (custom == null) {
			throw new CustomOutputException();
		}
		return custom;
	}

}
