package hu.bp.netcafe.backend.rest.controller;

import hu.bp.netcafe.backend.db.entity.Device;
import hu.bp.netcafe.backend.db.repository.DeviceRepository;
import hu.bp.netcafe.backend.rest.exception.DeviceNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class DeviceControllerLogic {
	private final DeviceRepository repository;

	public DeviceControllerLogic(DeviceRepository repository) {
		this.repository = repository;
	}

	public List<Device> all() {
		return repository.findAll();
	}

	public Device newDevice(Device newDevice) {
		return repository.save(newDevice);
	}

	// Single item

	public Device one(UUID id) {
		return repository.findById(id)
				.orElseThrow(() -> new DeviceNotFoundException(id));
	}

	public Device replaceDevice(Device newDevice, UUID id) {
		return repository.findById(id)
				.map(device -> {
					device.setName(newDevice.getName());
					device.setMacAddress(newDevice.getMacAddress());
					return repository.save(device);
				})
				.orElseGet(() -> {
					newDevice.setId(id);
					return repository.save(newDevice);
				});
	}

	void deleteDevice(UUID id) {
		repository.deleteById(id);
	}
}
