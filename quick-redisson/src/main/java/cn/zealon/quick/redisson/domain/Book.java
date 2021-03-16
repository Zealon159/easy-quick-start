package cn.zealon.quick.redisson.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: zealon
 * @since: 2021/3/16
 */
@Data
public class Book  implements Serializable {
    private String bookId;
    private String name;
}
