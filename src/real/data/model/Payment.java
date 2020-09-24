/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package real.data.model;

import java.time.LocalDate;

/**
 *
 * @author kRich
 */
public class Payment {
    String amount;
    String id;
   LocalDate dat;

    public Payment(String amount, String id,LocalDate date) {
        this.amount = amount;
        this.id = id;
        this.dat=date;
    }

    public String getAmount() {
        return amount;
    }

    public String getId() {
        return id;
    }

    public LocalDate getDate() {
        return dat;
    }
    
    
    
}
