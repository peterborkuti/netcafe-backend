package hu.bp.netcafe.backend.rest.controller;

import hu.bp.netcafe.backend.db.entity.Device;
import hu.bp.netcafe.backend.rest.exception.DeviceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.util.NestedServletException;

import java.util.List;

import static java.util.Collections.singletonList;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(DeviceController.class)
@Slf4j
public class DeviceControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private DeviceControllerLogic logic;

	@Before
	public void setUp() {
		reset(logic);
	}

	@Test
	public void allShouldListDevices() throws Exception {
		Device device = new Device("Name","Mac");

		List<Device> devices = singletonList(device);

		when(logic.all()).thenReturn(devices);

		mvc.perform(get("/devices")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("$[0].name").value(device.getName()))
				.andExpect(jsonPath("$[0].macAddress").value(device.getMacAddress()));

		verify(logic).all();
	}

	@Test
	public void oneShouldDisplayDeviceIfItExists() throws Exception {
		Device device = new Device("Name","Mac");

		when(logic.one(device.getId())).thenReturn(device);

		mvc.perform(get("/devices/"+device.getId())
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("name").value(device.getName()))
				.andExpect(jsonPath("id").value(device.getId().toString()))
				.andExpect(jsonPath("macAddress").value(device.getMacAddress()));

		verify(logic).one(device.getId());
	}

	@Test(expected = NestedServletException.class)
	public void oneShouldThrowExceptionWhenDeviceDoesNotExists() throws Exception {
		Device device = new Device("Name","Mac");

		when(logic.one(any())).thenThrow(DeviceNotFoundException.class);

		mvc.perform(get("/devices/"+device.getId())
				.contentType(MediaType.APPLICATION_JSON));

		verify(logic).one(any());
	}

	@Test
	public void newDeviceShouldReturnWithTheDevice() throws Exception {
		Device device = new Device("Name","Mac");

		when(logic.newDevice(device)).thenReturn(device);

		mvc.perform(post("/devices/")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("name").value(device.getName()))
				.andExpect(jsonPath("id").value(device.getId().toString()))
				.andExpect(jsonPath("macAddress").value(device.getMacAddress()));

		verify(logic).all();
	}
}
