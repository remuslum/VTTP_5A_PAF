package sg.nus.edu.iss.vttp_5a_day21_practice.model;

import java.util.Date;

import org.springframework.jdbc.support.rowset.SqlRowSet;

public class Book {
    private String asin;
    private String title;
    private String author; 
    private String soldBy;
    private String imageUrl;
    private String productUrl;
    private float stars;
    private int reviews;
    private float price;
    private Date publishedDate;
    private String categoryName;

    public Book() {
    }
    public String getAsin() {
        return asin;
    }
    public void setAsin(String asin) {
        this.asin = asin;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public String getSoldBy() {
        return soldBy;
    }
    public void setSoldBy(String soldBy) {
        this.soldBy = soldBy;
    }
    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public String getProductUrl() {
        return productUrl;
    }
    public void setProductUrl(String productUrl) {
        this.productUrl = productUrl;
    }
    public float getStars() {
        return stars;
    }
    public void setStars(float stars) {
        this.stars = stars;
    }
    public int getReviews() {
        return reviews;
    }
    public void setReviews(int reviews) {
        this.reviews = reviews;
    }
    public float getPrice() {
        return price;
    }
    public void setPrice(float price) {
        this.price = price;
    }
    public Date getPublishedDate() {
        return publishedDate;
    }
    public void setPublishedDate(Date publishedDate) {
        this.publishedDate = publishedDate;
    }
    public String getCategoryName() {
        return categoryName;
    }
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public static Book createBook(SqlRowSet rowSet){
        Book book = new Book();
        book.setAsin(rowSet.getString("asin"));
        book.setTitle(rowSet.getString("title"));
        book.setAuthor(rowSet.getString("author"));
        book.setSoldBy(rowSet.getString("soldby"));
        book.setImageUrl(rowSet.getString("imgUrl"));
        book.setProductUrl(rowSet.getString("productURL"));
        book.setStars(rowSet.getFloat("stars"));
        book.setReviews(rowSet.getInt("reviews"));
        book.setPublishedDate(rowSet.getDate("publishedDate"));
        book.setCategoryName(rowSet.getString("category_name"));
        return book;
    }

    
}
