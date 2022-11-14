package edu.epitech.dashlord.data.services;


import edu.epitech.dashlord.data.entities.WidgetService;
import edu.epitech.dashlord.data.repositories.WidgetServiceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

// The name doesnt make sense
// This is the service for the service class that we have
@Service
public class WidgetServiceService {

    private final WidgetServiceRepository serviceRepository;

    public WidgetServiceService(WidgetServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    public void addService(WidgetService service) {
        serviceRepository.save(service);
    }

    public List<WidgetService> getAllServices() {
        List<WidgetService> services = serviceRepository.findAll();
        return services;
    }

}
