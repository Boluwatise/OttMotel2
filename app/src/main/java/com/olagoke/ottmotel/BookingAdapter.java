package com.olagoke.ottmotel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class BookingAdapter extends ArrayAdapter<Booking> {
  /**
   * The Booking Adapter is our custom adapter we use to display the list of
   * bookings on the listview.
   *
   * @author  Alex Olagoke
   * @version 1.0
   * @since   2023-07-14
   */
  public BookingAdapter(Context context, ArrayList<Booking> bookings) {
    super(context, 0, bookings);
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    // Get the data item for this position
    Booking booking = getItem(position);
    // Check if an existing view is being reused, otherwise inflate the view
    if (convertView == null) {
      convertView = LayoutInflater.from(getContext()).inflate(R.layout.booking_list_item, parent, false);
    }
    // Lookup view for data population
    TextView userID = (TextView) convertView.findViewById(R.id.name);
    TextView timeIn = (TextView) convertView.findViewById(R.id.time_in);
    TextView timeOut = (TextView) convertView.findViewById(R.id.time_out);

    // Populate the data into the template view using the data object
    userID.setText(""+booking.getBookingId());
    timeIn.setText(booking.getCheckInTime());
    timeOut.setText(booking.getCheckOutTime());

    // Return the completed view to render on screen
    return convertView;
  }
}
