package br.com.fernandoalmeida.eventproxy;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
class EventRestControllerTest
{
    private static final String GET_URL = "/callEvent?key=uol";
    
    @Autowired
    private MockMvc mockMvc;

    @Test
    void testCallEventGet() throws Exception
    {
        this.mockMvc.perform(get(GET_URL)).andDo(print()).andExpect(status().isOk());
    }

}
