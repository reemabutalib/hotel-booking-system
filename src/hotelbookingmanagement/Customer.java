
package hotelbookingmanagement;

import java.time.LocalDate;

public class Customer {
    
    private String name;
    private String surname;
    private LocalDate dateOfBirth;
    private String contactDetails;

    // Constructor
    public Customer(String name, String surname, LocalDate dateOfBirth, String contactDetails) {
        setName(name);
        setSurname(surname);
        setDateOfBirth(dateOfBirth);
        setContactDetails(contactDetails);
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty.");
        }
        for (char c : name.toCharArray()) {
            if (!Character.isLetter(c) && c != '-' && c != ' ') {
                throw new IllegalArgumentException("Name can only contain letters, hyphens (-), and spaces.");
            }
        }
        this.name = name.trim();
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        if (surname == null || surname.trim().isEmpty()) {
            throw new IllegalArgumentException("Surname cannot be empty.");
        }
        for (char c : surname.toCharArray()) {
            if (!Character.isLetter(c) && c != '-' && c != ' ') {
                throw new IllegalArgumentException("Surname can only contain letters, hyphens (-), and spaces.");
            }
        }
        this.surname = surname.trim();
    }

    public LocalDate getDateOfBirth() {
    return dateOfBirth;
}

    public void setDateOfBirth(LocalDate dateOfBirth) {
    if (dateOfBirth == null || dateOfBirth.isAfter(LocalDate.now()) || dateOfBirth.isBefore(LocalDate.of(1900, 1, 1))) {
        throw new IllegalArgumentException("Date of birth must be between 1900 and today.");
    }
    this.dateOfBirth = dateOfBirth;
}


    public String getContactDetails() {
        return contactDetails;
    }

    public void setContactDetails(String contactDetails) {
        if (contactDetails == null || contactDetails.trim().isEmpty()) {
            throw new IllegalArgumentException("Contact details cannot be empty.");
        }
        if (!isValidContactDetails(contactDetails)) {
            throw new IllegalArgumentException("Contact details must be a valid phone number (digits only) or an email address (with @ and domain).");
        }
        this.contactDetails = contactDetails.trim();
    }

    // Helper method to validate date
    private boolean isValidDate(String date) {
        try {
            String[] parts = date.split("-");
            if (parts.length != 3) return false;

            int year = Integer.parseInt(parts[0]);
            int month = Integer.parseInt(parts[1]);
            int day = Integer.parseInt(parts[2]);

            if (year < 1900 || year > LocalDate.now().getYear()) return false; // Valid year
            if (month < 1 || month > 12) return false; // Valid month
            if (day < 1 || day > 31) return false; // Valid day

            // Check for months with fewer than 31 days
            if ((month == 4 || month == 6 || month == 9 || month == 11) && day > 30) return false;

            // Check for February and leap year
            if (month == 2) {
                if (isLeapYear(year)) {
                    if (day > 29) return false;
                } else {
                    if (day > 28) return false;
                }
            }

            return true;
        } catch (NumberFormatException e) {
            return false; // If parsing fails, the date is invalid
        }
    }

    private boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }

    // Helper method to validate contact details
    private boolean isValidContactDetails(String contactDetails) {
        if (contactDetails.contains("@")) {
            // Email validation: Must contain @ and a domain
            int atIndex = contactDetails.indexOf("@");
            return atIndex > 0 && atIndex < contactDetails.length() - 3; // Simple check for @ and domain
        } else {
            // Phone validation: Only digits allowed
            for (char c : contactDetails.toCharArray()) {
                if (!Character.isDigit(c)) {
                    return false;
                }
            }
            return contactDetails.length() >= 10 && contactDetails.length() <= 15; // Valid phone number length
        }
    }

    @Override
    public String toString() {
        return "Customer Name: " + name + ", Last Name: " + surname +
               ", Contact Details: " + contactDetails;
    }
}

    
