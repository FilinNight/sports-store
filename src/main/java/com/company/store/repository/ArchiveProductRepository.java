package com.company.store.repository;

import com.company.store.model.ArchiveProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArchiveProductRepository extends JpaRepository<ArchiveProduct, Long> {

}

