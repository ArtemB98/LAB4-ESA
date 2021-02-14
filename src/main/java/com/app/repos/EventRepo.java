package com.app.repos;

import org.springframework.data.repository.CrudRepository;
import com.app.entity.Event;

public interface EventRepo extends CrudRepository<Event, Long>  {
}
