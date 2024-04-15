package com.vp.fittrack.repositories;

import com.vp.fittrack.models.UserData;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDataRepository extends JpaRepository<UserData, Long> {

  UserData findByEmail(String email);

  List<UserData> findAll();

  UserData findByToken(String token);

  boolean existsByName(String name);

//  UserData findByName(String name);

  Optional<UserData> findByName(String name);

  Optional<UserData> findById(Long id);
}
