package hu.bp.netcafe.backend.rest.controller;

import hu.bp.netcafe.backend.db.entity.Device;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static java.util.Collections.singletonList;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(DeviceController.class)
@Slf4j
public class DeviceControllerLogicTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private DeviceController controller;

	@Test
	public void allShouldListDevices() throws Exception {
		Device device = new Device("Name","Mac");

		List<Device> devices = singletonList(device);

		when(controller.all()).thenReturn(devices);

		mvc.perform(get("/devices")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("$[0].name").value(device.getName()))
				.andExpect(jsonPath("$[0].macAddress").value(device.getMacAddress()));
	}

	@Test
	public void newDeviceShouldSaveAndDisplayDevices() throws Exception {
		Device device = new Device("Name","Mac");

		when(controller.one(any())).thenThrow(IllegalArgumentException.class);
		when(controller.one(device.getId())).thenReturn(device);

		ResultActions results = mvc.perform(get("/devices/"+device.getId())
				.contentType(MediaType.APPLICATION_JSON));

		log.info(results.toString());
		results
				.andExpect(status().isOk())
				.andExpect(jsonPath("name").value(device.getName()))
				.andExpect(jsonPath("id").value(device.getId()))
				.andExpect(jsonPath("macAddress").value(device.getMacAddress()));
	}



}
