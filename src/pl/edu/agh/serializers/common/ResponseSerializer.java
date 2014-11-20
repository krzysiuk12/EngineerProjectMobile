package pl.edu.agh.serializers.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import pl.edu.agh.exceptions.ErrorMessages;
/**
 * Created by Krzysztof Kicinger on 2014-11-15.
 */
public class ResponseSerializer<T> {

	private ResponseStatus status;
	private List<ErrorMessages> errorMessage;
	private T result;

	public ResponseSerializer() {
	}

	public ResponseSerializer(T result) {
		this.result = result;
	}

	public ResponseSerializer(ResponseStatus status, ErrorMessages errorMessage) {
		this.status = status;
		this.errorMessage = new ArrayList<>(Arrays.asList(errorMessage));
	}

	public ResponseSerializer(ResponseStatus status, List<ErrorMessages> errorMessage) {
		this.status = status;
		this.errorMessage = errorMessage;
	}

	public ResponseSerializer(ResponseStatus status, List<ErrorMessages> errorMessage, T result) {
		this.status = status;
		this.errorMessage = errorMessage;
		this.result = result;
	}

	public ResponseStatus getStatus() {
		return status;
	}

	public void setStatus(ResponseStatus status) {
		this.status = status;
	}

	public List<ErrorMessages> getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(List<ErrorMessages> errorMessage) {
		this.errorMessage = errorMessage;
	}

	public T getResult() {
		return result;
	}

	public void setResult(T result) {
		this.result = result;
	}
}
