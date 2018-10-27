package com.swcommodities.wsmill.hibernate.dto;
// Generated Feb 18, 2014 3:38:53 PM by Hibernate Tools 3.2.1.GA


import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * AuthorizationId generated by hbm2java
 */
@Embeddable
public class AuthorizationId  implements java.io.Serializable {


     private int userId;
     private int pageId;

    public AuthorizationId() {
    }

    public AuthorizationId(int userId, int pageId) {
       this.userId = userId;
       this.pageId = pageId;
    }
   

    @Column(name="user_id", nullable=false)
    public int getUserId() {
        return this.userId;
    }
    
    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Column(name="page_id", nullable=false)
    public int getPageId() {
        return this.pageId;
    }
    
    public void setPageId(int pageId) {
        this.pageId = pageId;
    }


   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof AuthorizationId) ) return false;
		 AuthorizationId castOther = ( AuthorizationId ) other; 
         
		 return (this.getUserId()==castOther.getUserId())
 && (this.getPageId()==castOther.getPageId());
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + this.getUserId();
         result = 37 * result + this.getPageId();
         return result;
   }   


}

