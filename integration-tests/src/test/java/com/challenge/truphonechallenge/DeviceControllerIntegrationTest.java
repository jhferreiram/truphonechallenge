package com.challenge.truphonechallenge;

import com.challenge.truphonechallenge.dataprovider.IDeviceDataProvider;
import com.challenge.truphonechallenge.dto.DeviceDTO;
import com.challenge.truphonechallenge.model.Device;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.ResourceUtils;
import org.springframework.web.context.WebApplicationContext;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDateTime;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = TruPhoneChallengeApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DeviceControllerIntegrationTest {

    @Autowired
    private IDeviceDataProvider dataProvider;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;
    private ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    void init() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }

    @AfterEach
    void tearDown() {
        this.dataProvider.drop();
    }

    @Test
    void listDevices() throws Exception {
        Device device = Device.builder()
                .name("Dummy_Device_1")
                .brand("Dummy_Brand_1")
                .time(LocalDateTime.parse("2021-01-01T01:01:01"))
                .build();
        this.dataProvider.saveDevice(device);
        device = Device.builder()
                .name("Dummy_Device_2")
                .brand("Dummy_Brand_2")
                .time(LocalDateTime.parse("2021-01-01T01:01:01"))
                .build();
        this.dataProvider.saveDevice(device);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/devices")
                .contentType(MediaType.APPLICATION_JSON);

        this.mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void getDevice() throws Exception {
        Device device = Device.builder()
                .name("Dummy_Device_1")
                .brand("Dummy_Brand_1")
                .time(LocalDateTime.parse("2021-01-01T01:01:01"))
                .build();
        Long id = this.dataProvider.saveDevice(device).getId();

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/devices/" + id)
                .contentType(MediaType.APPLICATION_JSON);

        DeviceDTO expected = this.mapper.readValue(this.loadFile("src/test/resources/get_device.json"), DeviceDTO.class);

        this.mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("name", is(expected.getName())))
                .andExpect(jsonPath("brand", is(expected.getBrand())))
                .andExpect(jsonPath("time", is(expected.getTime())));
    }

    @Test
    void createDevice() throws Exception {this.dataProvider.drop();
        String requestBody = this.loadFile("src/test/resources/create_device.json");
        DeviceDTO expected = this.mapper.readValue(requestBody, DeviceDTO.class);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/devices")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody);

        this.mockMvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("name", is(expected.getName())))
                .andExpect(jsonPath("brand", is(expected.getBrand())))
                .andExpect(jsonPath("time", is(expected.getTime())));
    }

    @Test
    void updateDevice() throws Exception {
        Device device = Device.builder()
                .name("Dummy_Device_1")
                .brand("Dummy_Brand_1")
                .time(LocalDateTime.parse("2021-01-01T01:01:01"))
                .build();
        Long id = this.dataProvider.saveDevice(device).getId();

        String requestBody = this.loadFile("src/test/resources/update_device.json");
        DeviceDTO expected = this.mapper.readValue(requestBody, DeviceDTO.class);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.put("/devices/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody);

        this.mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("name", is(expected.getName())))
                .andExpect(jsonPath("brand", is(expected.getBrand())))
                .andExpect(jsonPath("time", is(expected.getTime())));
    }

    @Test
    void patchDevice() throws Exception {
        Device device = Device.builder()
                .name("Dummy_Device_1")
                .brand("Dummy_Brand_1")
                .time(LocalDateTime.parse("2021-01-01T01:01:01"))
                .build();
        Long id = this.dataProvider.saveDevice(device).getId();

        String requestBody = this.loadFile("src/test/resources/patch_device.json");
        DeviceDTO expected = this.mapper.readValue(requestBody, DeviceDTO.class);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.patch("/devices/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody);

        this.mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("name", is(expected.getName())));
    }

    @Test
    void deleteDevice() throws Exception {
        Device device = Device.builder()
                .name("Dummy_Device_1")
                .brand("Dummy_Brand_1")
                .time(LocalDateTime.parse("2021-01-01T01:01:01"))
                .build();
        Long id = this.dataProvider.saveDevice(device).getId();

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.delete("/devices/" + id)
                .contentType(MediaType.APPLICATION_JSON);

        DeviceDTO expected = this.mapper.readValue(this.loadFile("src/test/resources/get_device.json"), DeviceDTO.class);

        this.mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("name", is(expected.getName())))
                .andExpect(jsonPath("brand", is(expected.getBrand())))
                .andExpect(jsonPath("time", is(expected.getTime())));
    }

    private String loadFile(String location) throws IOException {
        return mapper
                .readTree(
                        new FileInputStream(
                                ResourceUtils.getFile(location)
                        )
                ).toString();
    }
}
