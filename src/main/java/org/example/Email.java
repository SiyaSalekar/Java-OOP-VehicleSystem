package org.example;

import java.time.LocalDate;

public class Email {
    private String To;
    private String Subject;
    private String Text;
    private LocalDate Date;
    private final VehicleManager vehicleManager;
    private final PassengerStore passengerStore;

    //Email called in BookingManager
    //If booking is successful email is sent (email.send()) to Passenger

    public Email(String to, String subject,String text, int year, int month, int day, VehicleManager vehicleManager, PassengerStore passengerStore){
        this.To = to;
        this.Subject = subject;
        this.Text = text;
        this.Date=LocalDate.of(year,month,day);
        this.vehicleManager=vehicleManager;
        this.passengerStore = passengerStore;
    }
    public Email(VehicleManager vehicleManager, PassengerStore passengerStore){
        this.vehicleManager=vehicleManager;
        this.passengerStore = passengerStore;
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

    public void sendBookingEmail(int vehicleId, int passengerId){
        Passenger p = passengerStore.findPassengerById(passengerId);
        Vehicle v = vehicleManager.findByVehicleId(vehicleId);
        System.out.println("Email Address: "+p.getEmail());
        System.out.println("To: "+p.getName());
        System.out.println("Date: "+LocalDate.now());
        System.out.println("Subject: Booking");
        System.out.println("Text: Hello "+p.getName()+"! \nYou have booked - "+v);
        System.out.println("Many Thanks,");
        System.out.println("Harry Motors");
    }

}
