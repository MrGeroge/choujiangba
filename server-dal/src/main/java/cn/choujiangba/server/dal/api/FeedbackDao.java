package cn.choujiangba.server.dal.api;

import cn.choujiangba.server.dal.po.AccountAuthRecord;
import cn.choujiangba.server.dal.po.Feedback;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by xinghai on 2015/10/20.
 */
@Repository
public interface FeedbackDao extends JpaRepository<Feedback, Long>{
    Page<Feedback> findAll( Pageable pageable);
    Page<Feedback> findByStatus(int status, Pageable pageable);
}
