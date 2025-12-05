package com.rms.repository;

import com.rms.model.Table;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TableRepository extends MongoRepository<Table, String> {
}
