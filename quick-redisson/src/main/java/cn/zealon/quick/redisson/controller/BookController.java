package cn.zealon.quick.redisson.controller;

import cn.zealon.quick.redisson.domain.Book;
import cn.zealon.quick.redisson.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: zealon
 * @since: 2021/3/16
 */
@RestController
public class BookController {

    @Autowired
    private BookService bookService;

    /**
     * 分布式锁解决缓存击穿用例
     * <P>
     *     解决效率问题，避免热点缓存Key失效后，大量client线程请求同时访问数据资源(如DB)
     *     导致数据资源服务器压力巨大，控制只有一个线程请求数据资源。
     * </P>
     * @param bookId
     * @return
     */
    @GetMapping("book/details")
    public Book getBookDetails(String bookId){
        return this.bookService.getBookDetails(bookId);
    }
}
