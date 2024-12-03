package mk.finki.ukim.mk.lab.service.impl;

import mk.finki.ukim.mk.lab.model.EventBooking;
import mk.finki.ukim.mk.lab.model.User;
import mk.finki.ukim.mk.lab.repository.inMemory.InMemoryEventBookingRepository;
import mk.finki.ukim.mk.lab.repository.jpa.EventBookingRepository;
import mk.finki.ukim.mk.lab.service.EventBookingService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventBookingServiceImpl implements EventBookingService {
    public final EventBookingRepository eventBookingRepository;

    public EventBookingServiceImpl(EventBookingRepository eventBookingRepository) {
        this.eventBookingRepository = eventBookingRepository;
    }

    @Override
    public EventBooking placeBooking(User user, String eventName, String attendeeName, String attendeeAddress, int numberOfTickets) {
        EventBooking eventBooking = new EventBooking(eventName, attendeeName, attendeeAddress, (long) numberOfTickets,user);
        eventBookingRepository.save(eventBooking);
        return eventBooking;
    }

    @Override
    public List<EventBooking> listAll() {
        return eventBookingRepository.findAll();
    }

    @Override
    public List<EventBooking> searchEvents(String text) {
        return eventBookingRepository.searchEventBookingByEventName(text);
    }

    @Override
    public List<EventBooking> findByUser(String username) {
        return eventBookingRepository.findAllByUser_Username(username);
    }
}
