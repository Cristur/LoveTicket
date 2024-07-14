package com.cristianosenterprise.event;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByCategoryId(Long categoryId);
    List<Event> findByCategory_Id(Long categoryId);
}
