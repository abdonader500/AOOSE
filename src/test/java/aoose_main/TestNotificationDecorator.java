package aoose_main;

import aoose_main.entities.others.Notification;
import aoose_main.entities.others.NotificationInterface;
import aoose_main.entities.others.AppointmentReminder;
import aoose_main.entities.others.InventoryAlert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestNotificationDecorator {

    private NotificationInterface basicNotification;

    @BeforeEach
    public void setUp() {
        // Initialize the base Notification object with more realistic data
        basicNotification = new Notification(1, "This is a basic notification.", 101, "appointment", "high");
    }

    @Test
    public void testAppointmentReminderDecorator() {
        // Apply the AppointmentReminder decorator (pass the correct number of arguments)
        AppointmentReminder appointmentReminder = new AppointmentReminder(basicNotification); // 101 could be userID or any other necessary parameter

        // Test sending the decorated notification
        appointmentReminder.send();  // This will send the notification and save it to MongoDB
    }

    @Test
    public void testInventoryAlertDecorator() {
        // Apply the InventoryAlert decorator (pass the correct number of arguments)
        InventoryAlert inventoryAlert = new InventoryAlert(basicNotification, 1001, 15); // 1001 is productID and 15 is stock level

        // Test sending the decorated notification
        inventoryAlert.send();  // This will send the notification and save it to MongoDB
    }

    @Test
    public void testCombinedDecorators() {
        // Apply both decorators (AppointmentReminder and InventoryAlert)
        InventoryAlert inventoryAlert = new InventoryAlert(new AppointmentReminder(basicNotification), 1001, 15);

        // Test sending the decorated notification
        inventoryAlert.send();  // This will send the notification and save it to MongoDB
    }

    @Test
    public void testBasicNotificationWithoutDecorators() {
        // Test the base notification without any decorators
        basicNotification.send();  // This will just send the basic notification without any decorator
    }
}

