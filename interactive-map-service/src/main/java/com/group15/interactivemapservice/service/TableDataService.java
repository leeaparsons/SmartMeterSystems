package com.group15.interactivemapservice.service;

import com.group15.interactivemapservice.model.TableData;
import com.group15.interactivemapservice.repo.TableDataRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TableDataService {

    private final TableDataRepository repository;

    public TableDataService(TableDataRepository repository) {
        this.repository = repository;
    }

    public Page<TableData> getAllData(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Page<TableData> searchData(String search, Pageable pageable) {
        return repository.findByMpxnOrPostcodeContainingIgnoreCase(search, pageable);
    }
}

