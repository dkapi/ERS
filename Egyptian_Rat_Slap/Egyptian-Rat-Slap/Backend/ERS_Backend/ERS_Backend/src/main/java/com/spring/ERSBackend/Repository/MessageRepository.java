package com.spring.ERSBackend.Repository;

import com.spring.ERSBackend.Entities.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

}
