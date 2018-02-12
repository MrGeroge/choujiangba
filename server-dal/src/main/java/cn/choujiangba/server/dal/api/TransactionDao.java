package cn.choujiangba.server.dal.api;

import cn.choujiangba.server.dal.po.DeliveryAddress;
import cn.choujiangba.server.dal.po.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;

/**
 * Created by xinghai on 2015/10/19.
 */
@Repository
public interface TransactionDao  extends JpaRepository<Transaction, Long> {
    Page<Transaction>  findByUid(long uid,Pageable pageable);
}
