package org.yordanoffnikolay.lmrproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.yordanoffnikolay.lmrproject.models.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByAuthority(String user);
}
