package com.vlucenco.selenium.model;

public class Product {

    private String name;
    private String code;
    private String category;
    private String quantity;

    private String manufacturer;
    private String imagePath;
    private String shortDescription;

    private String purchasePrice;
    private String priceUSD;

    public static Builder newEntity() {
        return new Product().new Builder();
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public String getCategory() {
        return category;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getPurchasePrice() {
        return purchasePrice;
    }

    public String getPriceUSD() {
        return priceUSD;
    }

    public class Builder {
        private Builder() {
        }

        public Product build() {
            return Product.this;
        }

        public Builder withName(String name) {
            Product.this.name = name;
            return this;
        }

        public Builder withCode(String code) {
            Product.this.code = code;
            return this;
        }

        public Builder withCategory(String category) {
            Product.this.category = category;
            return this;
        }

        public Builder withQuantity(String quantity) {
            Product.this.quantity = quantity;
            return this;
        }

        public Builder withManufacturer(String manufacturer) {
            Product.this.manufacturer = manufacturer;
            return this;
        }

        public Builder withImage(String imagePath) {
            Product.this.imagePath = imagePath;
            return this;
        }

        public Builder withShortDescription(String shortDescription) {
            Product.this.shortDescription = shortDescription;
            return this;
        }

        public Builder withPurchasePrice(String purchasePrice) {
            Product.this.purchasePrice = purchasePrice;
            return this;
        }

        public Builder withPriceUSD(String price) {
            Product.this.priceUSD = price;
            return this;
        }
    }
}