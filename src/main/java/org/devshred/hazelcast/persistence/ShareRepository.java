package org.devshred.hazelcast.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShareRepository extends JpaRepository<Share, Integer> {
    List<Share> findAll();

    Share findByMic(String mic);
}