package com.rms.service;

import com.rms.dto.request.TableRequest;
import com.rms.dto.response.TableResponse;
import com.rms.exception.ResourceNotFoundException;
import com.rms.model.Table;
import com.rms.repository.TableRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TableServiceImpl implements TableService {

    private final TableRepository tableRepository;

    @Override
    public TableResponse create(TableRequest request) {
        Table table = Table.builder()
                .name(request.getName())
                .seats(request.getSeats())
                .available(true)
                .build();
        tableRepository.save(table);
        return toResponse(table);
    }

    @Override
    public TableResponse update(String id, TableRequest request) {
        Table table = tableRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Table not found"));

        table.setName(request.getName());
        table.setSeats(request.getSeats());

        tableRepository.save(table);
        return toResponse(table);
    }

    @Override
    public void delete(String id) {
        if (!tableRepository.existsById(id)) {
            throw new ResourceNotFoundException("Table not found");
        }
        tableRepository.deleteById(id);
    }

    @Override
    public TableResponse getById(String id) {
        Table table = tableRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Table not found"));

        return toResponse(table);
    }

    @Override
    public List<TableResponse> getAll() {
        return tableRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private TableResponse toResponse(Table table) {
        TableResponse res = new TableResponse();
        res.setId(table.getId());
        res.setName(table.getName());
        res.setSeats(table.getSeats());
        res.setAvailable(table.isAvailable());
        return res;
    }
}
