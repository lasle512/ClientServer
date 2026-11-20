package ru.university.clothes.report;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ru.university.clothes.dto.CsvImportResult;
import ru.university.clothes.dto.CsvImportRowError;
import ru.university.clothes.exception.InvalidFileException;

@Service
@RequiredArgsConstructor
public class EquipmentImportService {
    private final EquipmentRowImporter importer;

    public CsvImportResult importCsv(InputStream inputStream) {
        List<CsvImportRowError> errors = new ArrayList<>();
        int total = 0;
        int imported = 0;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            String line;
            int lineNumber = 0;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                if (lineNumber == 1 && line.toLowerCase().startsWith("title")) {
                    continue;
                }
                if (line.isBlank()) {
                    continue;
                }
                total++;
                try {
                    String[] parts = line.split(",");
                    if (parts.length != 7) {
                        throw new InvalidFileException("Ожидалось 7 колонки");
                    }
                    importer.importRow(parts[0].trim(), parts[1].trim(), parts[2].trim(), parts[3].trim(),
                            new Integer(parts[4].trim()), new Integer(parts[5].trim()), LocalDate.parse(parts[6].trim()));
                    imported++;
                } catch (Exception rowException) {
                    errors.add(new CsvImportRowError(lineNumber, line, rowException.getMessage()));
                }
            }
        } catch (IOException e) {
            throw new InvalidFileException("Не удалось прочитать CSV-файл: " + e.getMessage());
        }
        return new CsvImportResult(total, imported, total - imported, errors);
    }
}
