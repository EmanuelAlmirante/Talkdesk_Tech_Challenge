package webservice.repository;

import webservice.model.calldto.CallDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CallRepository extends JpaRepository<CallDto, Long> {
    Page<CallDto> findByCallType(Pageable var1, String callType);
}
