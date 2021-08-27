package br.com.fernandoalmeida.eventproxy;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;

import br.com.fernandoalmeida.eventproxy.caller.EventCaller;
import br.com.fernandoalmeida.eventproxy.caller.Request;
import br.com.fernandoalmeida.eventproxy.config.ApplicationConfig;
import br.com.fernandoalmeida.eventproxy.web.EventRestController;

@WebMvcTest(controllers = EventRestController.class)
@AutoConfigureMockMvc
class EventRestControllerTest
{
    private static final String SERVICE_URL = "/callEvent";
    private static final String TEST_URL = "http://mysite";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EventCaller caller;

    @MockBean
    private ApplicationConfig config;

    @Test
    void testCallEventGetSucessfull() throws Exception
    {
        this.mockMvc.perform(get(SERVICE_URL).param("key", "uol").param("delay", "10")).andDo(print()).andExpect(status().isOk());
    }

    @Test
    void testCallEventGetWithoutParameters() throws Exception
    {
        this.mockMvc.perform(get(SERVICE_URL)).andDo(print()).andExpect(status().is4xxClientError());
    }

    @Test
    void testCallEventGetWithInvalidDelayParameter() throws Exception
    {
        this.mockMvc.perform(get(SERVICE_URL).param("key", "uol").param("delay", "NON_NUMERIC")).andDo(print()).andExpect(status().is4xxClientError());
    }

    @Test
    void testCallEventPostSucessfull() throws Exception
    {
        Request request = new Request();

        request.setUrl(TEST_URL);
        request.setDelay(Optional.of(10));

        ObjectMapper mapper = new ObjectMapper();

        // Allows Optional elements to be mapped correctly
        mapper.registerModule(new Jdk8Module());

        ObjectWriter writer = mapper.writer();
        String requestBody = writer.writeValueAsString(request);

        this.mockMvc.perform(post(SERVICE_URL).contentType(MediaType.APPLICATION_JSON).content(requestBody)).andDo(print()).andExpect(status().isOk());
    }

}
