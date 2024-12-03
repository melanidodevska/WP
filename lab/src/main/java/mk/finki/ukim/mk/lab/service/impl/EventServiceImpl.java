package mk.finki.ukim.mk.lab.service.impl;

import mk.finki.ukim.mk.lab.model.Category;
import mk.finki.ukim.mk.lab.model.Event;
import mk.finki.ukim.mk.lab.model.Location;
import mk.finki.ukim.mk.lab.model.exceptions.CategoryNotFoundException;
import mk.finki.ukim.mk.lab.model.exceptions.LocationNotFoundException;
import mk.finki.ukim.mk.lab.repository.jpa.CategoryRepository;
import mk.finki.ukim.mk.lab.repository.jpa.EventRepository;
import mk.finki.ukim.mk.lab.repository.jpa.LocationRepository;
import mk.finki.ukim.mk.lab.service.EventService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final LocationRepository locationRepository;
    private final CategoryRepository categoryRepository;

    public EventServiceImpl(EventRepository eventRepository, LocationRepository locationRepository,
                            CategoryRepository categoryRepository) {
        this.eventRepository = eventRepository;
        this.locationRepository = locationRepository;
        this.categoryRepository = categoryRepository;
    }


    @Override
    public List<Event> listAll() {
        return eventRepository.findAll();
    }

    @Override
    public Optional<Event> findById(Long id) {
        return eventRepository.findById(id);
    }

    @Override
    public Optional<Event> findByName(String name) {
        return eventRepository.findByName(name);
    }


    @Override
    public List<Event> searchEvents(String text) {
        return eventRepository.findAllByNameLike(text);
    }

    @Override
    public List<Event> findByLocation(Long locationId) {
        return eventRepository.findAllByLocation_Id(locationId);
    }

    @Override
    public List<Event> findByCategory(Long categoryId) {
        return eventRepository.findAllByCategory_Id(categoryId);
    }


    @Override
    public void save(String name, String description, double popularityScore,
                     Long categoryId, Long locationId, int numTickets) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new CategoryNotFoundException(categoryId));
        Location location = locationRepository.findById(locationId).orElseThrow(() -> new LocationNotFoundException(locationId));
        this.eventRepository.save(new Event(name, description, popularityScore, category, location, numTickets));
    }

    @Override
    public void deleteById(Long id) {
        eventRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void reserveCard(Long eventId, int numTickets) {
        this.eventRepository.decrementNum(eventId, numTickets);
    }

    //    @Override
//    public void like(Long id) {
//        Event event = null;
//        if (this.eventRepository.findById(id).isPresent())
//            event = this.eventRepository.findById(id).get();
//        this.eventRepository.like(event);
//    }
}
