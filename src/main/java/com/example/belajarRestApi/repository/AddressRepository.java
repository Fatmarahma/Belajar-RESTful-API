package com.example.belajarRestApi.repository;

import com.example.belajarRestApi.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {}