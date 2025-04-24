package com.trading.webhook.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.trading.webhook.dto.OperationsDto;
import com.trading.webhook.entity.Operations;

/**
 * OperationsRepository (operaciones CRUD de la entidad) - Spring Boot
 *
 * @author Jesus Garcia
 * @since 1.0
 * @version jdk-11
 */
public interface OperationsRepository extends CrudRepository<Operations, Long> {

	Operations findByEpicAndStateAndAccountAndDirection(String epic, String state, String account, String direction);

	@Query(" SELECT new com.trading.webhook.dto.OperationsDto ( o.id, o.dealid, o.dealreference, o.affecteddeals, o.epic, o.direction, o.state, o.orderSize, o.guaranteedstop, o.stoplevel, o.profitlevel, o.openDate, o.closeDate, o.account) FROM Operations o order by o.openDate desc  ")
	List<OperationsDto> findAllOperations();

}
