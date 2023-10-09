package com.group15.interactivemapservice.service;

import com.group15.interactivemapservice.model.TableData;
import com.group15.interactivemapservice.repo.TableDataRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TableDataServiceTest {

    @Mock
    private TableDataRepository repo;

    @InjectMocks
    private TableDataService service;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllDataTest() {
        Pageable pageable = mock(Pageable.class);
        Page<TableData> page = mock(Page.class);

        when(repo.findAll(pageable)).thenReturn(page);

        Page<TableData> result = service.getAllData(pageable);

        // object returned from service as expected
        assertSame(page, result);
        verify(repo).findAll(pageable);
    }

    @Test
    void searchDataTest() {
        String search = "testSearch";
        Pageable pageable = mock(Pageable.class);
        Page<TableData> page = mock(Page.class);

        when(repo.findByMpxnOrPostcodeContainingIgnoreCase(search, pageable)).thenReturn(page);

        Page<TableData> result = service.searchData(search, pageable);

        // object returned from service as expected
        assertSame(page, result);
        verify(repo).findByMpxnOrPostcodeContainingIgnoreCase(search, pageable);
    }
}