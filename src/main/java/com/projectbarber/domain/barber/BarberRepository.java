package com.projectbarber.domain.barber;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BarberRepository extends JpaRepository<Barber, Long> {
    //find by active
    Page<Barber> findByActive(boolean isActive);
    
}
