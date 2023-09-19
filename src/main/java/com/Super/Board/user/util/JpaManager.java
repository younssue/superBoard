package com.Super.Board.user.util;


import com.Super.Board.user.exception.DatabaseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class JpaManager {
    /**
     * jpaRepository에 entity를 저장합니다. 그런데 이제 동시 요청 충돌이 발생할 경우 '나름의 대처'를 곁들인...
     * @param jpaRepository save 메서드가 있는 JpaRepository 인터페이스
     * @param entity 저장할 entity
     * @return 저장된 entity
     * @throws DatabaseException 저장에 실패했습니다.
     */
    public static <T, ID> T managedSave(JpaRepository<T, ID> jpaRepository, T entity) throws DatabaseException {
        log.info("이거 되는거야?!?!");
        // 동시에 접근한 클라이언트로 인해 충돌이 발생할 경우 '나름' 대비
        for (int i = 0; i < 3; i++) {
            try {
                return jpaRepository.save(entity);

            } catch (OptimisticLockingFailureException e) {
                log.warn("데이터 저장 중 충돌이 발생했습니다: {}", entity.getClass().getSimpleName());
            }
        }
        throw new DatabaseException("서버 측의 문제로 요청에 실패했습니다. 다시 요청해주세요.");
    }
}
