package com.app.repos;

import org.springframework.data.repository.CrudRepository;
import com.app.entity.Email;

public interface EmailRepo extends CrudRepository<Email, Long>  {
}
