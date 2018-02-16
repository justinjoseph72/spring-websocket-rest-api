package com.yoti.app.websocket.controller;

import com.yoti.app.websocket.domain.KeyTable;
import com.yoti.app.websocket.repos.KeyRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import static com.yoti.app.websocket.controller.EnpointsConstants.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
@Rollback
public class FetchKeyEndpointTest extends AbstractEndpointTest {

    private static final String KEY_FETCH = "key-fetch";

    @Autowired
    private KeyRepository repository;

    private KeyTable data;

    @Before
    public void initData() {
        data = new KeyTable();
        data.setSerialId("name1");
        data.setPublicKey("name1".getBytes());
    }

    @Test
    public void loadContext() {
        Assert.assertNotNull(repository);
    }

    @Test
    public void testFetchData() {
        data = repository.save(data);
        Assert.assertNotNull(data);
        Assert.assertNotNull(data.getId());
    }

    @Test
    public void testRestApi() throws Exception {
        data = repository.save(data);
        getMockMvc().perform(MockMvcRequestBuilders.get(
                CONTEXT.concat(FETCH_KEY).concat("/").concat(data.getSerialId())
        ).accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcRestDocumentation.document(KEY_FETCH));
    }

}
