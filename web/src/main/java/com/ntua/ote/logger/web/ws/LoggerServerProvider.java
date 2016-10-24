package com.ntua.ote.logger.web.ws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.apache.log4j.Logger;


@WebService(wsdlLocation="")
@SOAPBinding(parameterStyle=SOAPBinding.ParameterStyle.BARE)
public class LoggerServerProvider {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = Logger.getLogger(LoggerServerProvider.class);
	
	
	@WebMethod
	@WebResult(name="CommonResponse")
	public CommonResponse initialLogging(@WebParam(name="InitialLoggingRequest") InitialLoggingRequest request){
		CommonResponse resp = new CommonResponse();
		resp.setResponseCode(1);
		LOGGER.info("<initialLogging> invoked");
		return resp;
	}
	
	@WebMethod
	@WebResult(name="CommonResponse")
	public CommonResponse locationLogging(@WebParam(name="LocationLoggingRequest") LocationLoggingRequest request){
		CommonResponse resp = new CommonResponse();
		resp.setResponseCode(1);
		LOGGER.info("<locationLogging> invoked");
		return resp;
	}
	
}