package org.alilopez.service;

import org.alilopez.model.Cat;
import org.alilopez.repository.CatsRepository;

import java.sql.SQLException;

public class CatsService {
    private final CatsRepository catsRepository;
    public CatsService(CatsRepository catsRepository) {
        this.catsRepository = catsRepository;
    }

    public int create(Cat cat) throws SQLException {
        return catsRepository.save(cat);
    }
}
