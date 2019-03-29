package com.kt.millet.helm.repository;

import org.springframework.data.repository.CrudRepository;

import com.kt.millet.helm.bean.GrainBean;

public interface FieldRepository extends CrudRepository<GrainBean, Long> {

}
