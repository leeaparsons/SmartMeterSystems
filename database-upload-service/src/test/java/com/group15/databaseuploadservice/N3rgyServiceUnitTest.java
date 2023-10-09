//package com.group15.databaseuploadservice;
//
//import com.group15.databaseuploadservice.service.N3rgyService;
//import org.json.JSONArray;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//public class N3rgyServiceUnitTest {
//
//    @Autowired
//    N3rgyService service;
//
//    @Test
//    public void apiConnects() {
//        assertNotNull(service.apiRootCall());
//    }
//
//    @Test
//    public void readApiConnects() {
//        String mpxns = "123,132";
//        assertNotNull(service.apiReadInventoryCall(mpxns));
//    }
//
//    @Test
//    public void createEntriesArrayValidTest() {
//        assertEquals("[\"hello\",\"hello1\"]", service.createEntriesArray("{entries:[hello,hello1]}").toString());
//    }
//
//    @Test
//    public void createCommaSeparatedList() {
//        JSONArray ja = new JSONArray();
//        ja.put("123");
//        ja.put("132");
//
//        assertEquals("123,132", service.createEntryString(ja));
//    }
//}
