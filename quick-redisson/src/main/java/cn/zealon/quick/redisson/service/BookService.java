package cn.zealon.quick.redisson.service;

import cn.zealon.quick.redisson.domain.Book;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBucket;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.concurrent.TimeUnit;

/**
 * @author: zealon
 * @since: 2021/3/16
 */
@Slf4j
@Service
public class BookService {

    @Autowired
    private RedissonClient redissonClient;

    public Book getBookDetails(String bookId){
        Book book = null;
        String dataKey = "book_" + bookId;

        RBucket<Book> bookRBucket = redissonClient.getBucket(dataKey);
        book = bookRBucket.get();
        if (book != null) {
            log.info("Thread:{}命中缓存，直接返回.", Thread.currentThread().getName());
            return book;
        }

        String lockKey = "lock:book_" + bookId;
        RLock lock = redissonClient.getLock(lockKey);
        // 尝试加锁，最多等待10秒，上锁以后10秒自动解锁
        boolean res = false;
        try {
            log.info("Thread:{}请求接口.", Thread.currentThread().getName());
            res = lock.tryLock(10, 10, TimeUnit.SECONDS);
            if (res) {
                try {
                    log.info("Thread:{}成功获得锁.", Thread.currentThread().getName());
                    bookRBucket = redissonClient.getBucket(dataKey);
                    Thread.sleep(1500);
                    book = getBookById(bookId);
                    bookRBucket.set(book, 60, TimeUnit.SECONDS);
                } finally {
                    log.info("Thread:{}成功释放锁.", Thread.currentThread().getName());
                    lock.unlock();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return book;
    }

    private Book getBookById(String bookId) {
        Book b = new Book();
        b.setBookId(bookId);
        b.setName("十万个精英");
        return b;
    }
}
