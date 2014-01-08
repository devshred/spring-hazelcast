package org.devshred.hazelcast.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ShareRepository extends JpaRepository<Share, Integer> {
	@Override
	List<Share> findAll();

	Share findByMic(String mic);
}
