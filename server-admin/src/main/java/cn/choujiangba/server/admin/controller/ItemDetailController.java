package cn.choujiangba.server.admin.controller;

import cn.choujiangba.server.admin.config.C;
import cn.choujiangba.server.bal.api.ItemService;
import cn.choujiangba.server.bal.dto.ItemDetailDTO;
import cn.choujiangba.server.bal.exception.BizException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Author:zhangyu
 * create on 15/10/25.
 */
@Controller
@RequestMapping("/item")
public class ItemDetailController {

    @Autowired
    private ItemService itemService;

    @RequestMapping(value = "/render",method = RequestMethod.GET)
    public String render( @RequestParam(value="item_id",required = true)long itemId
                            ,Model model) throws BizException {
        ItemDetailDTO itemDetail=itemService.getItem(itemId);
        model.addAttribute("content",itemDetail.getDetail());
        return "item-detail-mobile";
    }
}
