package com.example.secondservice.service;

import com.example.secondservice.entity.CatalogEntity;

public interface CatalogService {
    Iterable<CatalogEntity> getAllCatalog();
}
