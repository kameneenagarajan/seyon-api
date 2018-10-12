package io.seyon.client.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import io.seyon.client.entity.ClientEntity;

public interface ClientRepository extends JpaRepository<ClientEntity, Long>{

	List<ClientEntity> findByCompanyId(Long companyId);
	
	List<ClientEntity> findByName(String name);
	
}
