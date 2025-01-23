package com.example.belajarRestApi.repository;

import com.example.belajarRestApi.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact, Long> {}
