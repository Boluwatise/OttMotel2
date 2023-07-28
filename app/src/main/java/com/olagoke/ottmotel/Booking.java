package com.olagoke.ottmotel;

public class Booking {
  private Integer bookingId;

  public Integer getBookingId() {
    return bookingId;
  }

  public void setBookingId(Integer bookingId) {
    this.bookingId = bookingId;
  }

  private Integer userId;
  private Integer room_id;
  private String checkInTime;
  private String checkOutTime;

  /**
   * The Booking object is what we use to store the various bookings in the database.
   *
   * @author  Alex Olagoke
   * @version 1.0
   * @since   2023-07-14
   */
  public Booking(Integer bookingId, Integer userId, Integer room_id, String checkInTime, String checkOutTime) {
    this.bookingId = bookingId;

    this.userId = userId;
    this.room_id = room_id;
    this.checkInTime = checkInTime;
    this.checkOutTime = checkOutTime;
  }


  public void setUserId(Integer userId) {
    this.userId = userId;
  }

  public String getCheckInTime() {
    return checkInTime;
  }

  public void setCheckInTime(String checkInTime) {
    this.checkInTime = checkInTime;
  }

  public String getCheckOutTime() {
    return checkOutTime;
  }

  public void setCheckOutTime(String checkOutTime) {
    this.checkOutTime = checkOutTime;
  }


  public Integer getUserId() {
    return userId;
  }


  public Integer getRoom_id() {
    return room_id;
  }

  public void setRoom_id(Integer room_id) {
    this.room_id = room_id;
  }


}
