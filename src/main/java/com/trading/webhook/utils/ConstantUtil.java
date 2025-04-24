package com.trading.webhook.utils;

/**
 * ConstantUtil 
 * 
 * @author Jesus Garcia - Open2000
 * @version 0.1
 * @since jdk-11.0.7
 */
public class ConstantUtil {
	
	private ConstantUtil () {
		
	}

	/**
	 * Logs letter
	 */
	public static final String LOG_START = "[INFO :: %s] - START.";
	public static final String LOG_START_OPERATION = "[ COMIENZA OPERACION DE %s ] - START.";
	public static final String LOG_END_OPERATION = "[ TERMINA OPERACION DE %s ] - END.";
	public static final String LOG_NOT_FOUND = "[ NO SE ENCONTRÓ OPERACION ] ";
	public static final String LOG_END = "[INFO :: %s] - END.";
	public static final String LOG_ERROR = "[ERROR :: %s] - Exception.";
	public static final String EXCEPTION = "Content not present.";
	public static final String EXCEPTION_DUPLICATE = "Content duplicated.";
	public static final String EXCEPTION_UPLOAD = "Error al subir el archivo selecionado.";
	public static final String EXCEPTION_GENERATE = "ERROR TO GENERATE FILE.";
	public static final String FILE_EXCEPTION = "[ERROR ::  %s] - MSG: %s";
	public static final String LOG_START_URI = "[INFO :: %s] - URI - %s";
	
	public static final String LOG_REQUEST = "[REQUEST :: %s -END].";
	public static final String LOG_RESPONSE = "[RESPONSE :: %s -END].";
	public static final String LOG_URL = "[URL :: %s -END].";
	
	
	
}
