package com.kt.millet.helm.repository;

import org.springframework.data.repository.CrudRepository;

import com.kt.millet.helm.bean.BatchBean;

public interface FieldBatchRepository extends CrudRepository<BatchBean, Long> {

}
