package com.ey.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ey.entity.Customer;
import com.ey.entity.Notification;
import com.ey.repository.NotificationRepository;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository repository;

    public void notify(Customer customer, String message) {

        Notification notification = new Notification();
        notification.setCustomer(customer);
        notification.setMessage(message);

        repository.save(notification);
    }
}

