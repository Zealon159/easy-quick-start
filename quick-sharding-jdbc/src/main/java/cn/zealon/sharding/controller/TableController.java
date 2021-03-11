package cn.zealon.sharding.controller;

import cn.zealon.sharding.service.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: zealon
 * @since: 2021/3/11
 */
@RestController
@RequestMapping("table")
public class TableController {

    @Autowired
    private TableService tableService;

    @GetMapping("/list/{ds}")
    public List<String> getTableList(@PathVariable("ds") String ds){
        if (ds.equals("user")) {
            return tableService.getBookMSAccountTables();
        } else if (ds.equals("book")) {
            return tableService.getBookMSTables();
        }
        return null;
    }
}
