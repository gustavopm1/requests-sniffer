package com.gustavopm1.requestssniffer.repository;

import com.gustavopm1.requestssniffer.model.RequestEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestRepository extends CrudRepository<RequestEntity, String> {

}
