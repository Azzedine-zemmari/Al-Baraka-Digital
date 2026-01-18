package com.banc.securise.repository;

import com.banc.securise.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface DocumentRepository extends JpaRepository<Document,Integer> {
    Document findDocumentByOperation_Id(int id);
    @Query("SELECT d FROM Document d JOIN d.operation o WHERE o.status = 'PENDING'")
    List<Document> findAllTheDocumentWithOperationPending();
}
