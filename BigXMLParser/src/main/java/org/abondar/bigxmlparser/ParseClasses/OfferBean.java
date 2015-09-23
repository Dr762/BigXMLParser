/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.abondar.bigxmlparser.ParseClasses;

import java.util.Objects;

/**
 *
 * @author alex
 */
public class OfferBean {
    
    private Integer id;
    private Boolean available;
    private Float price;
    private String pictureURL;
    
    
    
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    
   

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    
    public String getPictureURL() {
        return pictureURL;
    }

    public void setPictureURL(String pictureURL) {
        this.pictureURL = pictureURL;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final OfferBean other = (OfferBean) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.available, other.available)) {
            return false;
        }
        if (!Objects.equals(this.price, other.price)) {
            return false;
        }
        if (!Objects.equals(this.pictureURL, other.pictureURL)) {
            return false;
        }
        return true;
    }

    
    
    @Override
    public String toString() {
        return "OfferBean{" + "id=" + id + ", available=" + available + ", price=" + price + ", pictureURL=" + pictureURL + '}';
    }

    
    
    
    
    
    
}
