package ru.university.clothes.storage;

import java.util.Map;

import org.springframework.stereotype.Component;

import ru.university.clothes.exception.InvalidFileException;

@Component
public class FileSignatureValidator {
    private static final Map<String, byte[]> SIGNATURES = Map.of(
            "image/jpeg", new byte[] { (byte) 0xFF, (byte) 0xD8, (byte) 0xFF },
            "image/png", new byte[] { (byte) 0x89, 0x50, 0x4E, 0x47, 0x0D, 0x0A, 0x1A, 0x0A });


    public void assertAllowedImage(byte[] content){
        if (content == null || content.length == 0 ) {
            throw new InvalidFileException("Файл пуст");
        }
        boolean ok  = SIGNATURES.values().stream().anyMatch(sig -> startsWith(content, sig));
        if (!ok){
            throw new InvalidFileException("Не разрешенный тип файла");
        }
    }

    private boolean startsWith(byte[] content, byte[] signature) {
        if (content.length < signature.length) {
            return false;
        }
        for (int i = 0; i < signature.length; i++) {
            if (content[i] != signature[i]) {
                return false;
            }
        }
        return true;
    }
}
