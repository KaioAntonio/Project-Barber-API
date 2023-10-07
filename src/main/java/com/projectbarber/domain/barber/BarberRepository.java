package com.projectbarber.domain.barber;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BarberRepository extends JpaRepository<Barber, Long> {

    Page<Barber> findAllByActive(Boolean active, org.springframework.data.domain.Pageable pageable);

    Barber getReferenceById(Long idBarbeiro);
}
