package com.kbytech.init.domain;

import org.springframework.data.jpa.repository.JpaRepository;

// DAO
public interface UserRepository extends JpaRepository<User,Long> {
//<bean,id type>
    User findByUsername(String username); // username 기반.


}
