package com.gustavopm1.requestssniffer.storage;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gustavopm1.requestssniffer.models.BufferBody;
import lombok.Data;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FilesystemStorageTest {

    @Autowired
    private FilesystemStorage storage;


    @Test
    public void shouldPersistADummyRequestWithoutHeaders() throws IOException {

        TestDummyObject crashTest = new TestDummyObject();
        crashTest.setId(1L);
        crashTest.setName("Pedro Paulo Paresh");


        storage.persistRequest("/api/1.0/ximira/clients/a50314",new HashMap<>(),crashTest);
        assertTrue(Files.exists(Paths.get("./data/api/1.0/ximira/clients/a50314/headers.json")));
        assertTrue(Files.exists(Paths.get("./data/api/1.0/ximira/clients/a50314/body.json")));
        assertTrue(Files.exists(Paths.get("./data/api/1.0/ximira/clients/a50314/params.json")));

        ObjectMapper mapper = new ObjectMapper();

        BufferBody body = storage.loadRequest("/api/1.0/ximira/clients/a50314");
        assertEquals("/api/1.0/ximira/clients/a50314",body.getUrl());
        assertEquals(new HashMap<>(),body.getHeaders());
        assertEquals(mapper.writeValueAsString(crashTest),mapper.writeValueAsString(body.getBody()));

    }

    @Test
    public void shouldPersistADummyRequestWithoutHeadersAndParamsOnURL() throws IOException {

        TestDummyObject crashTest = new TestDummyObject();
        crashTest.setId(1L);
        crashTest.setName("Pedro Paulo Paresh");


        storage.persistRequest("/api/1.0/ximira/clients/a50315?ximira=true&sleep=false",new HashMap<>(),crashTest);
        assertTrue(Files.exists(Paths.get("./data/api/1.0/ximira/clients/a50315/headers.json")));
        assertTrue(Files.exists(Paths.get("./data/api/1.0/ximira/clients/a50315/body.json")));
        assertTrue(Files.exists(Paths.get("./data/api/1.0/ximira/clients/a50315/params.json")));

        ObjectMapper mapper = new ObjectMapper();

        BufferBody body = storage.loadRequest("/api/1.0/ximira/clients/a50315?ximira=true&sleep=false");
        assertEquals("/api/1.0/ximira/clients/a50315?ximira=true&sleep=false",body.getUrl());
        assertEquals(new HashMap<>(),body.getHeaders());
        assertEquals(mapper.writeValueAsString(crashTest),mapper.writeValueAsString(body.getBody()));

    }


    @Data
    public class TestDummyObject{
        Long id;
        String name;
    }

}