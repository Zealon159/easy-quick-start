package cn.zealon.comjob.domain;

/**
 * 标签库图书
 * @author: tangyl
 * @since: 2020/11/2
 */
public class TagLibBookBo {

    /** 图书基本属性 */
    private String bookId;
    private String bookName;
    private String author;
    private String imgUrl;
    private String intro;
    private String keyword;
    private String cat2Name;
    private String cat3Name;
    private Integer cat2;
    private Integer cat3;

    /** 筛选条件属性 */
    private Integer pindaoId;
    private Integer status;
    private Integer blacklist;
    private String serial;
    private Integer wordCount;
    private Integer hot;
    private Integer newest;
    private Integer[] tags;

    /** 排序属性 */
    private Float bookScore;
    private Integer hotScore;
    private Integer newestScore;
    private Integer chapterCount;

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getCat2Name() {
        return cat2Name;
    }

    public void setCat2Name(String cat2Name) {
        this.cat2Name = cat2Name;
    }

    public String getCat3Name() {
        return cat3Name;
    }

    public void setCat3Name(String cat3Name) {
        this.cat3Name = cat3Name;
    }

    public Integer getCat2() {
        return cat2;
    }

    public void setCat2(Integer cat2) {
        this.cat2 = cat2;
    }

    public Integer getCat3() {
        return cat3;
    }

    public void setCat3(Integer cat3) {
        this.cat3 = cat3;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public Integer getWordCount() {
        return wordCount;
    }

    public void setWordCount(Integer wordCount) {
        this.wordCount = wordCount;
    }

    public Integer getHot() {
        return hot;
    }

    public void setHot(Integer hot) {
        this.hot = hot;
    }

    public Integer getNewest() {
        return newest;
    }

    public void setNewest(Integer newest) {
        this.newest = newest;
    }

    public Integer[] getTags() {
        return tags;
    }

    public void setTags(Integer[] tags) {
        this.tags = tags;
    }

    public Float getBookScore() {
        return bookScore;
    }

    public void setBookScore(Float bookScore) {
        this.bookScore = bookScore;
    }

    public Integer getHotScore() {
        return hotScore;
    }

    public void setHotScore(Integer hotScore) {
        this.hotScore = hotScore;
    }

    public Integer getNewestScore() {
        return newestScore;
    }

    public void setNewestScore(Integer newestScore) {
        this.newestScore = newestScore;
    }

    public Integer getChapterCount() {
        return chapterCount;
    }

    public void setChapterCount(Integer chapterCount) {
        this.chapterCount = chapterCount;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getBlacklist() {
        return blacklist;
    }

    public void setBlacklist(Integer blacklist) {
        this.blacklist = blacklist;
    }

    public Integer getPindaoId() {
        return pindaoId;
    }

    public void setPindaoId(Integer pindaoId) {
        this.pindaoId = pindaoId;
    }
}
