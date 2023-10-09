package com.group15.databaseuploadservice;

import com.group15.databaseuploadservice.entity.DataEntry;
import com.group15.databaseuploadservice.repo.DatabaseUploadRepository;
import com.group15.databaseuploadservice.service.N3rgyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.mockito.Mockito.*;

public class N3rgyServiceTest {

    @Mock
    private DatabaseUploadRepository repository;

    private N3rgyService n3rgyService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        n3rgyService = new N3rgyService(repository);
    }

    @Test
    public void testSaveToDatabase() throws Exception {
        String jsonString = loadJsonFromFile("test_input.json");
        n3rgyService.saveToDatabase(jsonString);

        verify(repository, times(1)).save(any(DataEntry.class));
    }

    private String loadJsonFromFile(String filename) throws IOException {
        ClassPathResource resource = new ClassPathResource(filename);
        Path filePath = resource.getFile().toPath();
        byte[] contentBytes = Files.readAllBytes(filePath);
        return new String(contentBytes, StandardCharsets.UTF_8);
    }
}