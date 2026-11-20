package ru.university.clothes.storage;

import org.springframework.web.multipart.MultipartFile;

public interface StorageService {
    String store(String subDirectory, MultipartFile file);
}
