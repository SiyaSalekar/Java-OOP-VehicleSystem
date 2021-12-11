package org.example;

import java.time.LocalDate;

public class Email {
    private String To;
    private String Subject;
    private String Text;
    private LocalDate Date;
    private final BookingManager bookingManager;

    public Email(String to, String subject,String text, int year, int month, int day, BookingManager bookingManager){
        this.To = to;
        this.Subject = subject;
        this.Text = text;
        this.Date=LocalDate.of(year,month,day);
        this.bookingManager=bookingManager;
    }

    public String getTo() {
        return To;
    }

    public void setTo(String to) {
        To = to;
    }

    public String getSubject() {
        return Subject;
    }

    public void setSubject(String subject) {
        Subject = subject;
    }

    public String getText() {
        return Text;
    }

    public void setText(String text) {
        Text = text;
    }

    public LocalDate getDate() {
        return Date;
    }

    public void setDate(int year, int month, int day) {
        Date = LocalDate.of(year,month,day);
    }


}
