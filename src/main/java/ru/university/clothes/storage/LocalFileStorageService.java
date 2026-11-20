package ru.university.clothes.storage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import ru.university.clothes.config.StorageProperties;
import ru.university.clothes.exception.InvalidFileException;

@Service
@RequiredArgsConstructor
public class LocalFileStorageService implements StorageService {

    private final StorageProperties storageProperties;
    private final FileSignatureValidator signatureValidator;

    @Override
    public String store(String subDirectory, MultipartFile file) {

        try {
            byte[] content = file.getBytes();
            signatureValidator.assertAllowedImage(content);
            Path targetDir = Path.of(storageProperties.getRootDir(), subDirectory);
            Files.createDirectories(targetDir);

            String extension = content[1] == 0x50 ? ".png" : ".jpg";
            String fileName = UUID.randomUUID() + extension;
            Path targetFile = targetDir.resolve(fileName);
            Files.write(targetFile, content);
            return "/" + subDirectory + "/" + fileName;
        } catch (IOException e) {
            throw new InvalidFileException("Не удалось сохранить файл: " + e.getMessage());
        }
    }

}
