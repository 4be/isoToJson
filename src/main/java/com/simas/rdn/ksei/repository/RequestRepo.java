package com.simas.rdn.ksei.repository;

import com.simas.rdn.ksei.models.entity.RequestQueue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestRepo extends JpaRepository<RequestQueue,Long> {

}
