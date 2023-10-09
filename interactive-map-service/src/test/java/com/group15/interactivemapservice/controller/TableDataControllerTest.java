package com.group15.interactivemapservice.controller;

import com.group15.interactivemapservice.model.Device;
import com.group15.interactivemapservice.model.TableData;
import com.group15.interactivemapservice.service.TableDataService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username="admin", authorities="ADMIN")
@ActiveProfiles("test")
public class TableDataControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private TableDataService tableDataService;
    private List<TableData> tableDataList;

    @BeforeEach
    public void setUp(){
        tableDataList = Arrays.asList(new TableData("10010020304", "7 Privet Drive", "TA11 6AH",
                new ArrayList<>(Arrays.asList(new Device("11-00-3E-44-ED", "4500", "COMMISSIONED", "400", "5.1.1", "6.0.0", "ACTIVE")))));
    }

    // Testing /search assigns correct default values when not declared.
    @Test
    void whenPageNumberNotProvidedSearchTakesDefaultValue() throws Exception{
        Page<TableData> response = new PageImpl<>(tableDataList);
        when(tableDataService.searchData(anyString(), any())).thenReturn(response);

        // page number not provided in parameters
        mvc.perform(get("/search")
                        .param("size", "10")
                        .param("sortBy", "mpxn")
                        .param("direction", "asc")
                        .param("search", "searching"))
                .andExpect(status().isOk());

        ArgumentCaptor<Pageable> pageable = ArgumentCaptor.forClass(Pageable.class);
        verify(tableDataService).searchData(eq("searching"), pageable.capture());

        Pageable pageableResponse = pageable.getValue();

        // page number is default value (0)
        assertEquals(0, pageableResponse.getPageNumber());
    }

    @Test
    void whenPageSizeNotProvidedSearchTakesDefaultValue() throws Exception{
        Page<TableData> response = new PageImpl<>(tableDataList);
        when(tableDataService.searchData(anyString(), any())).thenReturn(response);

        // page size not provided in parameters
        mvc.perform(get("/search")
                        .param("page", "0")
                        .param("sortBy", "mpxn")
                        .param("direction", "asc")
                        .param("search", "searching"))
                .andExpect(status().isOk());

        ArgumentCaptor<Pageable> pageable = ArgumentCaptor.forClass(Pageable.class);
        verify(tableDataService).searchData(eq("searching"), pageable.capture());

        Pageable pageableResponse = pageable.getValue();

        // page size is default value (10)
        assertEquals(10, pageableResponse.getPageSize());
    }

    @Test
    void whenSortByNotProvidedSearchTakesDefaultValue() throws Exception{
        Page<TableData> response = new PageImpl<>(tableDataList);
        when(tableDataService.searchData(anyString(), any())).thenReturn(response);

        // sortBy not provided in parameters
        mvc.perform(get("/search")
                        .param("page", "0")
                        .param("size", "10")
                        .param("direction", "asc")
                        .param("search", "searching"))
                .andExpect(status().isOk());

        ArgumentCaptor<Pageable> pageable = ArgumentCaptor.forClass(Pageable.class);
        verify(tableDataService).searchData(eq("searching"), pageable.capture());

        Pageable pageableResponse = pageable.getValue();

        // sortBy should be the default value ("mpxn")
        assertEquals("mpxn", pageableResponse.getSort().getOrderFor("mpxn").getProperty());
    }

    @Test
    void whenSortDirectionNotProvidedSearchTakesDefaultValue() throws Exception{
        Page<TableData> response = new PageImpl<>(tableDataList);
        when(tableDataService.searchData(anyString(), any())).thenReturn(response);

        // sortBy not provided in parameters
        mvc.perform(get("/search")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sortBy", "mpxn")
                        .param("search", "searching"))
                .andExpect(status().isOk());

        ArgumentCaptor<Pageable> pageable = ArgumentCaptor.forClass(Pageable.class);
        verify(tableDataService).searchData(eq("searching"), pageable.capture());

        Pageable pageableResponse = pageable.getValue();

        // The direction of the value being sorted (mpxn) is in the default order (ascending)
        assertEquals(Sort.Direction.ASC, pageableResponse.getSort().getOrderFor("mpxn").getDirection());
    }

    // Testing /tabledata assigns correct default values when not declared.
    @Test
    void whenPageNumberNotProvidedItTakesDefaultValue() throws Exception{
        Page<TableData> response = new PageImpl<>(tableDataList);
        when(tableDataService.getAllData(any())).thenReturn(response);

        // page number not provided in parameters
        mvc.perform(get("/tabledata")
                        .param("size", "10")
                        .param("sortBy", "mpxn")
                        .param("direction", "asc"))
                .andExpect(status().isOk());

        ArgumentCaptor<Pageable> pageable = ArgumentCaptor.forClass(Pageable.class);
        verify(tableDataService).getAllData(pageable.capture());

        Pageable pageableResponse = pageable.getValue();

        // page number is default value (0)
        assertEquals(0, pageableResponse.getPageNumber());
    }


    @Test
    void whenPageSizeNotProvidedItTakesDefaultValue() throws Exception{
        Page<TableData> response = new PageImpl<>(tableDataList);
        when(tableDataService.getAllData(any())).thenReturn(response);

        // size not provided in parameters
        mvc.perform(get("/tabledata")
                        .param("page", "0")
                        .param("sortBy", "mpxn")
                        .param("direction", "asc"))
                .andExpect(status().isOk());

        ArgumentCaptor<Pageable> pageable = ArgumentCaptor.forClass(Pageable.class);
        verify(tableDataService).getAllData(pageable.capture());

        Pageable pageableResponse = pageable.getValue();

        // size should be the default value (10)
        assertEquals(10, pageableResponse.getPageSize());
    }


    @Test
    void whenSortByNotProvidedItTakesDefaultValue() throws Exception{
        Page<TableData> response = new PageImpl<>(tableDataList);
        when(tableDataService.getAllData(any())).thenReturn(response);

        // sortBy not provided in parameters
        mvc.perform(get("/tabledata")
                        .param("page", "0")
                        .param("size", "10")
                        .param("direction", "asc"))
                .andExpect(status().isOk());

        ArgumentCaptor<Pageable> pageable = ArgumentCaptor.forClass(Pageable.class);
        verify(tableDataService).getAllData(pageable.capture());

        Pageable pageableResponse = pageable.getValue();

        // sortBy should be the default value ("mpxn")
        assertEquals("mpxn", pageableResponse.getSort().getOrderFor("mpxn").getProperty());
    }


    @Test
    void whenSortDirectionNotProvidedItTakesDefaultValue() throws Exception{
        Page<TableData> response = new PageImpl<>(tableDataList);
        when(tableDataService.getAllData(any())).thenReturn(response);

        // direction not provided in parameters
        mvc.perform(get("/tabledata")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sortBy", "mpxn"))
                .andExpect(status().isOk());

        ArgumentCaptor<Pageable> pageable = ArgumentCaptor.forClass(Pageable.class);
        verify(tableDataService).getAllData(pageable.capture());

        Pageable pageableResponse = pageable.getValue();

        // The direction of the value being sorted (mpxn) is in the default order (ascending)
        assertEquals(Sort.Direction.ASC, pageableResponse.getSort().getOrderFor("mpxn").getDirection());
    }

    // getData()
    // Testing the HTTP Status codes returned by getData() are as expected
    // Success when input is valid
    @Test
    void whenValidInputGetDataReturnsOk() throws Exception {
        Page<TableData> response = new PageImpl<>(tableDataList, PageRequest.of(0, 10, Sort.by("mpxn").ascending()), tableDataList.size());
        when(tableDataService.getAllData(any())).thenReturn(response);

        mvc.perform(get("/tabledata")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sortBy", "mpxn")
                        .param("direction", "asc"))
                .andExpect(status().isOk());
    }

    // Testing getData invalid parameters
    @Test
    void whenInvalidPageParamGetDataReturns4xxError() throws Exception {
        Page<TableData> response = new PageImpl<>(tableDataList, PageRequest.of(0, 10, Sort.by("mpxn").ascending()), tableDataList.size());
        when(tableDataService.getAllData(any())).thenReturn(response);

        mvc.perform(get("/tabledata")
                        .param("page", "-1")
                        .param("size", "10")
                        .param("sortBy", "mpxn")
                        .param("direction", "asc"))
                .andExpect(status().is5xxServerError());
    }

    // Testing getData invalid parameters
    @Test
    void whenInvalidPageParamGetDataReturns5xxError() throws Exception {
        Page<TableData> response = new PageImpl<>(tableDataList, PageRequest.of(0, 10, Sort.by("mpxn").ascending()), tableDataList.size());
        when(tableDataService.getAllData(any())).thenReturn(response);

        mvc.perform(get("/tabledata")
                        .param("page", "ABC")
                        .param("size", "10")
                        .param("sortBy", "mpxn")
                        .param("direction", "asc"))
                .andExpect(status().is4xxClientError());
    }

    // If "page" parameter is not specified, the default parameter should be applied.
    @Test
    void whenMissingPageParamDefaultParamWorks() throws Exception {
        Page<TableData> response = new PageImpl<>(tableDataList, PageRequest.of(0, 10, Sort.by("mpxn").ascending()), tableDataList.size());
        when(tableDataService.getAllData(any())).thenReturn(response);

        mvc.perform(get("/tabledata")
                        .param("size", "10")
                        .param("sortBy", "mpxn")
                        .param("direction", "asc"))
                .andExpect(status().isOk());
    }

    // If "size" parameter is not specified, the default parameter should be applied.
    @Test
    void whenMissingSizeParamDefaultParamWorks() throws Exception {
        Page<TableData> response = new PageImpl<>(tableDataList, PageRequest.of(0, 10, Sort.by("mpxn").ascending()), tableDataList.size());
        when(tableDataService.getAllData(any())).thenReturn(response);

        mvc.perform(get("/tabledata")
                        .param("page", "0")
                        .param("sortBy", "mpxn")
                        .param("direction", "asc"))
                .andExpect(status().isOk());
    }

    // If "sortBy" parameter is not specified, the default parameter should be applied.
    @Test
    void whenMissingSortByParamDefaultParamWorks() throws Exception {
        Page<TableData> response = new PageImpl<>(tableDataList, PageRequest.of(0, 10, Sort.by("mpxn").ascending()), tableDataList.size());
        when(tableDataService.getAllData(any())).thenReturn(response);

        mvc.perform(get("/tabledata")
                        .param("page", "0")
                        .param("size", "10")
                        .param("direction", "asc"))
                .andExpect(status().isOk());
    }

    // If "direction" parameter is not specified, the default parameter should be applied.
    @Test
    void whenMissingDirectionParamDefaultParamWorks() throws Exception {
        Page<TableData> response = new PageImpl<>(tableDataList, PageRequest.of(0, 10, Sort.by("mpxn").ascending()), tableDataList.size());
        when(tableDataService.getAllData(any())).thenReturn(response);

        mvc.perform(get("/tabledata")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sortBy", "mpxn"))
                .andExpect(status().isOk());
    }

    // Testing values for pagination return as expected when calling getData()
    @Test
    void whenValidInputGetDataPaginationValuesAreCorrect() throws Exception{
        Page<TableData> response = new PageImpl<>(tableDataList, PageRequest.of(0, 10, Sort.by("mpxn").ascending()), tableDataList.size());
        when(tableDataService.getAllData(any())).thenReturn(response);

        mvc.perform(MockMvcRequestBuilders.get("/tabledata")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sortBy", "mpxn")
                        .param("direction", "asc"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.devices.length()").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.currentPage").value(0)) //currentPage is 0-indexed
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalItems").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalPages").value(1));
    }


    // searchData()
    // Testing the HTTP Status codes returned by searchData() are as expected
    // Success when input is valid
    @Test
    void whenValidInputSearchReturnsOk() throws Exception {
        Page<TableData> response = new PageImpl<>(tableDataList, PageRequest.of(0, 10, Sort.by("mpxn").ascending()), tableDataList.size());
        when(tableDataService.searchData(anyString(), any())).thenReturn(response);

        mvc.perform(get("/search")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sortBy", "mpxn")
                        .param("direction", "asc")
                        .param("search", "10300200"))
                .andExpect(status().isOk());
    }

    // Testing getData invalid parameters
    @Test
    void whenInvalidPageParamSearchDataReturns4xxError() throws Exception {
        Page<TableData> response = new PageImpl<>(tableDataList, PageRequest.of(0, 10, Sort.by("mpxn").ascending()), tableDataList.size());
        when(tableDataService.searchData(anyString(), any())).thenReturn(response);

        mvc.perform(get("/search")
                        .param("page", "-1")
                        .param("size", "10")
                        .param("sortBy", "mpxn")
                        .param("direction", "asc"))
                .andExpect(status().is4xxClientError());
    }

    // Testing getData invalid parameters
    @Test
    void whenInvalidSizeParamSearchDataReturns4xxError() throws Exception {
        Page<TableData> response = new PageImpl<>(tableDataList, PageRequest.of(0, 10, Sort.by("mpxn").ascending()), tableDataList.size());
        when(tableDataService.searchData(anyString(), any())).thenReturn(response);

        mvc.perform(get("/search")
                        .param("page", "0")
                        .param("size", "-1")
                        .param("sortBy", "mpxn")
                        .param("direction", "asc"))
                .andExpect(status().is4xxClientError());
    }

    // Testing getData invalid parameters
    @Test
    void whenInvalidSortByParamSearchDataReturns4xxError() throws Exception {
        Page<TableData> response = new PageImpl<>(tableDataList, PageRequest.of(0, 10, Sort.by("mpxn").ascending()), tableDataList.size());
        when(tableDataService.searchData(anyString(), any())).thenReturn(response);

        mvc.perform(get("/search")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sortBy", "ABC")
                        .param("direction", "asc"))
                .andExpect(status().is4xxClientError());
    }
    // Testing getData invalid parameters
    @Test
    void whenInvalidDirectionParamSearchDataReturns4xxError1() throws Exception {
        Page<TableData> response = new PageImpl<>(tableDataList, PageRequest.of(0, 10, Sort.by("mpxn").ascending()), tableDataList.size());
        when(tableDataService.searchData(anyString(), any())).thenReturn(response);

        mvc.perform(get("/search")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sortBy", "mpxn")
                        .param("direction", "ABC"))
                .andExpect(status().is4xxClientError());
    }

    // Testing values for pagination return as expected when calling searchData()
    @Test
    void whenValidInputSearchDataPaginationValuesAreCorrect() throws Exception{
        Page<TableData> response = new PageImpl<>(tableDataList, PageRequest.of(0, 10, Sort.by("mpxn").ascending()), tableDataList.size());
        when(tableDataService.searchData(anyString(), any())).thenReturn(response);
        mvc.perform(MockMvcRequestBuilders.get("/search")
                        .param("page", "0")
                        .param("size", "10")
                        .param("search", "1100450022"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.devices.length()").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.currentPage").value(0)) //currentPage is 0-indexed
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalItems").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalPages").value(1));
    }

    // All data columns in /table.html appear
    @Test
    public void displayAllDataTableColumns() throws Exception{
        this.mvc.perform(get("/tableView/table"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Meter Data Table")))
                .andExpect(content().string(containsString("MPXN")))
                .andExpect(content().string(containsString("Device ID")))
                .andExpect(content().string(containsString("Device Type")))
                .andExpect(content().string(containsString("Device Status")))
                .andExpect(content().string(containsString("Device Model")))
                .andExpect(content().string(containsString("Device Manufacturer")))
                .andExpect(content().string(containsString("Device Firmware Version")))
                .andExpect(content().string(containsString("Device Firmware Status")))
                .andExpect(content().string(containsString("Postcode")));
    }
}