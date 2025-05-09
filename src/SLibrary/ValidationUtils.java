package SLibrary;

public class ValidationUtils {
    
    public static boolean isValidEmail(String email) {
        return email != null && email.matches("^[A-Za-z0-9+_.-]+@gmail\\.com$");
    }
    
    public static boolean isValidPhone(String phone) {
        return phone != null && phone.matches("^\\d{10}$");
    }
    
    public static boolean isValidName(String name) {
        return name != null && name.matches("^[A-Za-z\\s]+$");
    }
    
    public static boolean isRequired(String field) {
        return field != null && !field.trim().isEmpty();
    }
    
    public static String validateRequiredField(String fieldName, String value) {
        if (!isRequired(value)) {
            return fieldName + " is required";
        }
        return null;
    }
    
    public static String validateEmail(String email) {
        if (!isRequired(email)) {
            return "Email is required";
        }
        if (!isValidEmail(email)) {
            return "Email must be a valid Gmail address (e.g., example@gmail.com)";
        }
        return null;
    }
    
    public static String validatePhone(String phone) {
        if (!isRequired(phone)) {
            return "Phone number is required";
        }
        if (!isValidPhone(phone)) {
            return "Phone number must be exactly 10 digits";
        }
        return null;
    }
    
    public static String validateName(String name) {
        if (!isRequired(name)) {
            return "Name is required";
        }
        if (!isValidName(name)) {
            return "Name must contain only letters and spaces";
        }
        return null;
    }
} 