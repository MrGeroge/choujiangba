package cn.choujiangba.server.dal.api;

import cn.choujiangba.server.dal.po.Account;
import cn.choujiangba.server.dal.po.AdminAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by xinghai on 2015/10/19.
 */
@Repository
public interface AdminAccountDao extends JpaRepository<AdminAccount, Long> {
    AdminAccount findByUsername(String username);
}
