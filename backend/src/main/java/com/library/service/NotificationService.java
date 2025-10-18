package com.library.service;

import com.library.entity.BookRequest;
import com.library.repository.BookRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationService {
    
    @Autowired
    private BookRequestRepository bookRequestRepository;
    
    @Autowired
    private BookService bookService;
    
    /**
     * Check for books that have become available and notify users
     * This method runs every 5 minutes
     */
    @Scheduled(fixedRate = 300000) // 5 minutes
    @Async
    public void checkAndNotifyBookAvailability() {
        try {
            List<BookRequest> pendingRequests = bookRequestRepository.findAllPendingRequests();
            
            for (BookRequest request : pendingRequests) {
                if (bookService.isBookAvailable(request.getBook().getBarcode())) {
                    // Book is now available, notify the user
                    notifyUser(request);
                }
            }
        } catch (Exception e) {
            System.err.println("Error in scheduled notification check: " + e.getMessage());
        }
    }
    
    /**
     * Notify user about book availability
     */
    private void notifyUser(BookRequest request) {
        try {
            // Update request status to notified
            request.setStatus(BookRequest.RequestStatus.NOTIFIED);
            request.setNotificationSent(true);
            request.setNotificationDate(LocalDateTime.now());
            bookRequestRepository.save(request);
            
            // Here you could implement actual notification mechanisms:
            // - Send email
            // - Send SMS
            // - Push notification
            // - In-app notification
            
            System.out.println("Notification sent to user: " + request.getUser().getUsername() + 
                             " for book: " + request.getBook().getTitle() + 
                             " (Barcode: " + request.getBook().getBarcode() + ")");
            
        } catch (Exception e) {
            System.err.println("Error sending notification: " + e.getMessage());
        }
    }
    
    /**
     * Get all pending notifications for a user
     */
    public List<BookRequest> getPendingNotifications(String username) {
        // This would typically be called from a controller
        // to show pending notifications to the user
        return bookRequestRepository.findPendingRequestsByUser(
            bookRequestRepository.findByUserOrderByRequestDateDesc(
                // You would need to inject UserService to get user by username
                null // This is a placeholder
            ).get(0).getUser()
        );
    }
    
    /**
     * Mark notification as read/fulfilled
     */
    public void markNotificationAsFulfilled(Long requestId) {
        try {
            BookRequest request = bookRequestRepository.findById(requestId)
                    .orElseThrow(() -> new RuntimeException("Request not found"));
            
            request.setStatus(BookRequest.RequestStatus.FULFILLED);
            bookRequestRepository.save(request);
            
        } catch (Exception e) {
            System.err.println("Error marking notification as fulfilled: " + e.getMessage());
        }
    }
}










