package cn.choujiangba.server.dal.api;

import cn.choujiangba.server.dal.po.Feedback;
import cn.choujiangba.server.dal.po.Item;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by xinghai on 2015/10/21.
 */
@Repository
@Transactional
public interface ItemDao extends JpaRepository<Item, Long> {
    //@Cacheable(value = "defaultCache",condition = "true")
    Page<Item> findAll(Pageable pageable);
    //@Cacheable(value = "defaultCache",condition = "true")
    //List<Item> findByNameLike(String name);
    //@Cacheable(value = "defaultCache",condition = "true")
    //List<Item> findByPropertyLike(String property);
    @Query("from Item a where a.name like %?1%")
    List<Item> findByNameLike(String name);
    @Query("from Item a where a.property like %?1%")
    List<Item> findByPropertyLike(String property);
}
