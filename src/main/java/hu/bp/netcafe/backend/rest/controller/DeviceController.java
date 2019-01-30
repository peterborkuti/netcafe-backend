package hu.bp.netcafe.backend.rest.controller;

import hu.bp.netcafe.backend.db.entity.Device;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController("devices")
@RequestMapping("/devices")
public class DeviceController {
	private final DeviceControllerLogic logic;

	public DeviceController(DeviceControllerLogic logic) {
		this.logic = logic;
	}

	@GetMapping()
	List<Device> all() {
		return logic.all();
	}

	@PostMapping()
	Device newDevice(@RequestBody Device newDevice) {
		return logic.newDevice(newDevice);
	}

	@GetMapping("/{id}")
	Device one(@PathVariable UUID id) {
		return logic.one(id);
	}

	@PutMapping("/{id}")
	Device replaceDevice(@RequestBody Device newDevice, @PathVariable UUID id) {
		return logic.replaceDevice(newDevice, id);
	}

	@DeleteMapping("/{id}")
	void deleteDevice(@PathVariable UUID id) {
		logic.deleteDevice(id);
	}
}
