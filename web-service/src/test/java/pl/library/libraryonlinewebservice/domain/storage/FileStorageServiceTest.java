package pl.library.libraryonlinewebservice.domain.storage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FileStorageServiceTest {

    private FileStorageService fileStorageService;

    @TempDir
    Path tempDir;

    @BeforeEach
    void setUp() {
        // Initialize FileStorageService with a temporary directory
        fileStorageService = new FileStorageService(tempDir.toString());
    }

    @Test
    void whenSaveImage_thenSavedCorrectly() throws IOException {
        // Given
        MultipartFile file = new MockMultipartFile("test", "test.jpg", "image/jpeg", "test image content".getBytes());

        // When
        String savedFileName = fileStorageService.saveImage(file);

        // Then
        Path savedFilePath = tempDir.resolve("img").resolve(savedFileName);
        assertTrue(Files.exists(savedFilePath), "File should exist in the image storage location.");
        assertThat(Files.readString(savedFilePath)).isEqualTo("test image content");
    }

    @Test
    void whenSaveFile_thenSavedCorrectly() throws IOException {
        // Given
        MultipartFile file = new MockMultipartFile("test", "test.txt", "text/plain", "test file content".getBytes());

        // When
        String savedFileName = fileStorageService.saveFile(file);

        // Then
        Path savedFilePath = tempDir.resolve("files").resolve(savedFileName);
        assertTrue(Files.exists(savedFilePath), "File should exist in the file storage location.");
        assertThat(Files.readString(savedFilePath)).isEqualTo("test file content");
    }
}
