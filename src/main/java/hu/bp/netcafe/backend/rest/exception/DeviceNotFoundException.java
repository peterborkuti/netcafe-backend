package hu.bp.netcafe.backend.rest.exception;

import java.util.UUID;

public class DeviceNotFoundException extends RuntimeException {
	public DeviceNotFoundException(UUID id) {
		super("Could not find device " + id.toString());
	}
}
