package com.utopia.pmc.data.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.utopia.pmc.data.entities.Regiment;

public interface RegimentRepository extends JpaRepository<Regiment, Long> {
}
