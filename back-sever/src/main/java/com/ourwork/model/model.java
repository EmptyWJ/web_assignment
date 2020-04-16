package com.ourwork.model;



import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class model {
   private long id;
   private String including;
   private LocalDateTime uTime;

   public model(){

   }

   public model(Long id, String including){
      this.id = id;
      this.including=including;
   }

   public String getIncluding() {
      return including;
   }

   public void setIncluding(String including) {
      this.including = including;
   }

   public LocalDateTime getuTime() {
      return uTime;
   }

   public void setuTime() {
      this.uTime = LocalDateTime.now();;
   }

   public long getId() {
      return id;
   }

   public void setId(long id) {
      this.id = id;
   }


}

