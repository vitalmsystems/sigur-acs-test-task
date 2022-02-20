package com.vitalmsystems.siguracstesttask.repositories;

import com.vitalmsystems.siguracstesttask.model.Guest;
import org.springframework.data.repository.CrudRepository;

public interface GuestRepository extends CrudRepository<Guest, Long> {
}
