package es.indra.helpcontrol.service.exception;

public class ServiceException extends Exception {

	private static final long serialVersionUID = -2641274876971132146L;

	public ServiceException(String msg) {
		
		super(msg);
	}

	public ServiceException(Throwable e) {

		super(e);
	}
	
public ServiceException(String msg, Throwable e) {
		
		super(msg, e);
	}

}
