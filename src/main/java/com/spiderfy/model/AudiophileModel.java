package com.spiderfy.model;
import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

public class AudiophileModel {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonPropertyOrder({
            "id",
            "type",
            "url",
            "imageSource",
            "title",
            "createdDate",
            "likesCount",
            "price",
            "priceCurrency",
            "oldPrice",
            "iLiked",
            "labelDopingType",
            "yellowDoping",
            "securePayment",
            "advertOrder",
            "viewCount",
            "description",
            "brand",
            "productStatus",
            "stockAmount",
            "categoryName",
            "lastUpdateDate",
            "userName",
            "content",
            "model",
            "newProductPrice",
            "decPrice",
            "moneyType"
    })


        @JsonProperty("id")
        private Integer id;
        @JsonProperty("type")
        private Integer type;
        @JsonProperty("url")
        private String url;
        @JsonProperty("imageSource")
        private String imageSource;
        @JsonProperty("title")
        private String title;
        @JsonProperty("createdDate")
        private String createdDate;
        @JsonProperty("likesCount")
        private Integer likesCount;
        @JsonProperty("price")
        private String price;
        @JsonProperty("priceCurrency")
        private String priceCurrency;
        @JsonProperty("oldPrice")
        private String oldPrice;
        @JsonProperty("iLiked")
        private Boolean iLiked;
        @JsonProperty("labelDopingType")
        private Object labelDopingType;
        @JsonProperty("yellowDoping")
        private Boolean yellowDoping;
        @JsonProperty("securePayment")
        private Object securePayment;
        @JsonProperty("advertOrder")
        private Integer advertOrder;
        @JsonProperty("viewCount")
        private Integer viewCount;
        @JsonProperty("description")
        private String description;
        @JsonProperty("brand")
        private String brand;
        @JsonProperty("productStatus")
        private String productStatus;
        @JsonProperty("stockAmount")
        private String stockAmount;
        @JsonProperty("categoryName")
        private String categoryName;
        @JsonProperty("lastUpdateDate")
        private String lastUpdateDate;
        @JsonProperty("userName")
        private String userName;
        @JsonProperty("content")
        private String content;
        @JsonProperty("model")
        private String model;
        @JsonProperty("newProductPrice")
        private String newProductPrice;
        @JsonProperty("decPrice")
        private String decPrice;
        @JsonProperty("moneyType")
        private Integer moneyType;
        @JsonIgnore
        private Map<String, Object> additionalProperties = new HashMap<String, Object>();

        @JsonProperty("id")
        public Integer getId() {
            return id;
        }

        @JsonProperty("id")
        public void setId(Integer id) {
            this.id = id;
        }

        @JsonProperty("type")
        public Integer getType() {
            return type;
        }

        @JsonProperty("type")
        public void setType(Integer type) {
            this.type = type;
        }

        @JsonProperty("url")
        public String getUrl() {
            return url;
        }

        @JsonProperty("url")
        public void setUrl(String url) {
            this.url = url;
        }

        @JsonProperty("imageSource")
        public String getImageSource() {
            return imageSource;
        }

        @JsonProperty("imageSource")
        public void setImageSource(String imageSource) {
            this.imageSource = imageSource;
        }

        @JsonProperty("title")
        public String getTitle() {
            return title;
        }

        @JsonProperty("title")
        public void setTitle(String title) {
            this.title = title;
        }

        @JsonProperty("createdDate")
        public String getCreatedDate() {
            return createdDate;
        }

        @JsonProperty("createdDate")
        public void setCreatedDate(String createdDate) {
            this.createdDate = createdDate;
        }

        @JsonProperty("likesCount")
        public Integer getLikesCount() {
            return likesCount;
        }

        @JsonProperty("likesCount")
        public void setLikesCount(Integer likesCount) {
            this.likesCount = likesCount;
        }

        @JsonProperty("price")
        public String getPrice() {
            return price;
        }

        @JsonProperty("price")
        public void setPrice(String price) {
            this.price = price;
        }

        @JsonProperty("priceCurrency")
        public String getPriceCurrency() {
            return priceCurrency;
        }

        @JsonProperty("priceCurrency")
        public void setPriceCurrency(String priceCurrency) {
            this.priceCurrency = priceCurrency;
        }

        @JsonProperty("oldPrice")
        public String getOldPrice() {
            return oldPrice;
        }

        @JsonProperty("oldPrice")
        public void setOldPrice(String oldPrice) {
            this.oldPrice = oldPrice;
        }

        @JsonProperty("iLiked")
        public Boolean getILiked() {
            return iLiked;
        }

        @JsonProperty("iLiked")
        public void setILiked(Boolean iLiked) {
            this.iLiked = iLiked;
        }

        @JsonProperty("labelDopingType")
        public Object getLabelDopingType() {
            return labelDopingType;
        }

        @JsonProperty("labelDopingType")
        public void setLabelDopingType(Object labelDopingType) {
            this.labelDopingType = labelDopingType;
        }

        @JsonProperty("yellowDoping")
        public Boolean getYellowDoping() {
            return yellowDoping;
        }

        @JsonProperty("yellowDoping")
        public void setYellowDoping(Boolean yellowDoping) {
            this.yellowDoping = yellowDoping;
        }

        @JsonProperty("securePayment")
        public Object getSecurePayment() {
            return securePayment;
        }

        @JsonProperty("securePayment")
        public void setSecurePayment(Object securePayment) {
            this.securePayment = securePayment;
        }

        @JsonProperty("advertOrder")
        public Integer getAdvertOrder() {
            return advertOrder;
        }

        @JsonProperty("advertOrder")
        public void setAdvertOrder(Integer advertOrder) {
            this.advertOrder = advertOrder;
        }

        @JsonProperty("viewCount")
        public Integer getViewCount() {
            return viewCount;
        }

        @JsonProperty("viewCount")
        public void setViewCount(Integer viewCount) {
            this.viewCount = viewCount;
        }

        @JsonProperty("description")
        public String getDescription() {
            return description;
        }

        @JsonProperty("description")
        public void setDescription(String description) {
            this.description = description;
        }

        @JsonProperty("brand")
        public String getBrand() {
            return brand;
        }

        @JsonProperty("brand")
        public void setBrand(String brand) {
            this.brand = brand;
        }

        @JsonProperty("productStatus")
        public String getProductStatus() {
            return productStatus;
        }

        @JsonProperty("productStatus")
        public void setProductStatus(String productStatus) {
            this.productStatus = productStatus;
        }

        @JsonProperty("stockAmount")
        public String getStockAmount() {
            return stockAmount;
        }

        @JsonProperty("stockAmount")
        public void setStockAmount(String stockAmount) {
            this.stockAmount = stockAmount;
        }

        @JsonProperty("categoryName")
        public String getCategoryName() {
            return categoryName;
        }

        @JsonProperty("categoryName")
        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }

        @JsonProperty("lastUpdateDate")
        public String getLastUpdateDate() {
            return lastUpdateDate;
        }

        @JsonProperty("lastUpdateDate")
        public void setLastUpdateDate(String lastUpdateDate) {
            this.lastUpdateDate = lastUpdateDate;
        }

        @JsonProperty("userName")
        public String getUserName() {
            return userName;
        }

        @JsonProperty("userName")
        public void setUserName(String userName) {
            this.userName = userName;
        }

        @JsonProperty("content")
        public String getContent() {
            return content;
        }

        @JsonProperty("content")
        public void setContent(String content) {
            this.content = content;
        }

        @JsonProperty("model")
        public String getModel() {
            return model;
        }

        @JsonProperty("model")
        public void setModel(String model) {
            this.model = model;
        }

        @JsonProperty("newProductPrice")
        public String getNewProductPrice() {
            return newProductPrice;
        }

        @JsonProperty("newProductPrice")
        public void setNewProductPrice(String newProductPrice) {
            this.newProductPrice = newProductPrice;
        }

        @JsonProperty("decPrice")
        public String getDecPrice() {
            return decPrice;
        }

        @JsonProperty("decPrice")
        public void setDecPrice(String decPrice) {
            this.decPrice = decPrice;
        }

        @JsonProperty("moneyType")
        public Integer getMoneyType() {
            return moneyType;
        }

        @JsonProperty("moneyType")
        public void setMoneyType(Integer moneyType) {
            this.moneyType = moneyType;
        }

        @JsonAnyGetter
        public Map<String, Object> getAdditionalProperties() {
            return this.additionalProperties;
        }

        @JsonAnySetter
        public void setAdditionalProperty(String name, Object value) {
            this.additionalProperties.put(name, value);
        }

    }

