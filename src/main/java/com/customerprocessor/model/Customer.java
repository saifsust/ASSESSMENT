package com.customerprocessor.model;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.regex.Pattern;

/**
 * Class is used to store customer information.{@link Indexing} is used to find out specific data of customer.
 */
public final class Customer {
    private static final String WITHOUT_BRACKET_PHONE_NUMBER_CHECKER_PATTERN = "[0-9]*[ ]*[0-9][0-9][0-9][- ]*[0-9][0-9][0-9][- ]*[0-9][0-9][0-9][0-9]";
    private static final String WITH_BRACKET_PHONE_NUMBER_CHECKER_PATTERN = "[(][0-9][0-9][0-9][)][- ]*[0-9][0-9][0-9][- ]*[0-9][0-9][0-9][0-9]";
    private static final String EMAIL_CHECKER_PATTERN = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
    private static final Pattern WITHOUT_BRACKET = Pattern.compile(WITHOUT_BRACKET_PHONE_NUMBER_CHECKER_PATTERN);
    private static final Pattern WITH_BRACKET = Pattern.compile(WITH_BRACKET_PHONE_NUMBER_CHECKER_PATTERN);
    private static final Pattern EMAIL_MATCHER = Pattern.compile(EMAIL_CHECKER_PATTERN);
    private String firstName;
    private String lastName;
    private String city;
    private String state;
    private String postalZipCode;
    private String phoneNumber;
    private String email;
    private String ipAddress;

    public Customer(String firstName, String lastName,
                    String city, String state,
                    String postalZipCode, String phoneNumber,
                    String email, String ipAddress) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.city = city;
        this.state = state;
        this.postalZipCode = postalZipCode;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.ipAddress = ipAddress;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getPostalZipCode() {
        return postalZipCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public static Builder builder(String line) {
        return new Builder(line);
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Customer)) return false;
        Customer customer = (Customer) o;
        return Objects.equals(getPhoneNumber(), customer.getPhoneNumber()) &&
                Objects.equals(getEmail(), customer.getEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPhoneNumber(), getEmail());
    }

    /**
     * Method is check whether customer is valid or not.
     *
     * @return if valid then true otherwise false.
     */
    public boolean isValid(){
        return isValidPhoneNumber() && isValidEmail();
    }

    /**
     * Method is check whether {@code phoneNumber} is valid or not
     *
     * @return if valid then true otherwise false.
     */
    private boolean isValidPhoneNumber(){
        return WITHOUT_BRACKET.matcher(this.phoneNumber).find() ||
                WITH_BRACKET.matcher(this.phoneNumber).find();
    }

    public boolean isEmpty() {
        return this.firstName.isEmpty() &&
                this.lastName.isEmpty() &&
                this.city.isEmpty() &&
                this.state.isEmpty() &&
                this.phoneNumber.isEmpty() &&
                this.email.isEmpty() &&
                this.postalZipCode.isEmpty() &&
                this.ipAddress.isEmpty();
    }

    /**
     * Method is check whether {@code email} is valid or not
     *
     * @return if valid then true otherwise false.
     */
    private boolean isValidEmail(){
        return EMAIL_MATCHER.matcher(this.email).find();
    }

    @Override
    public String toString() {
        return firstName + ',' + lastName + ',' + city + ',' + state + ','
                + postalZipCode + ',' + phoneNumber + ',' + email + ',' + ipAddress+'\n';
    }

    public String toValue() {
        return "('"+firstName + "','" + lastName + "','" + city + "','" + state + "','" + postalZipCode + "','" +
                phoneNumber + "','" + email + "','" + ipAddress+"')";
    }

    public static class Builder {
        private static final String COMA_SEPARATOR = ",";
        private static final String EMPTY = "";
        /**
         * {@code PRODUCER} used to find out data of customer such as name, phone number, email etc.
         */
        private static final BiFunction<String[], Indexing, String> PRODUCER = (data, indexing) ->
                data.length > indexing.getValue() ?  data[indexing.getValue()] : EMPTY;

        private String[] information;
        private String firstName;
        private String lastName;
        private String city;
        private String state;
        private String postalZipCode;
        private String phoneNumber;
        private String email;
        private String ipAddress;

        private Builder(){

        }

        private Builder(String line) {
            this.information = line.split(COMA_SEPARATOR);
        }

        public Builder firstName() {
            this.firstName = PRODUCER.apply(information, Indexing.FIRST_NAME);
            return this;
        }

        public Builder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder lastName() {
            this.lastName = PRODUCER.apply(information, Indexing.LAST_NAME);
            return this;
        }

        public Builder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder city() {
            this.city = PRODUCER.apply(information, Indexing.CITY);
            return this;
        }

        public Builder city(String city) {
            this.city = city;
            return this;
        }

        public Builder state() {
            this.state = PRODUCER.apply(information, Indexing.STATE);
            return this;
        }

        public Builder state(String state) {
            this.state = state;
            return this;
        }


        public Builder postalZipCode() {
            this.postalZipCode = PRODUCER.apply(information, Indexing.POSTAL_ZIP_CODE);
            return this;
        }

        public Builder postalZipCode(String postalZipCode) {
            this.postalZipCode = postalZipCode;
            return this;
        }

        public Builder phoneNumber() {
            this.phoneNumber = PRODUCER.apply(information, Indexing.PHONE_NUMBER);
            return this;
        }

        public Builder phoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public Builder email() {
            this.email = PRODUCER.apply(information, Indexing.EMAIL);
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder ipAddress() {
            this.ipAddress = PRODUCER.apply(information, Indexing.IP_ADDRESS);
            return this;
        }

        public Builder ipAddress(String ipAddress) {
            this.ipAddress = ipAddress;
            return this;
        }

        public Customer build() {
            return new Customer(this.firstName, this.lastName,
                    this.city, this.state,
                    this.postalZipCode, this.phoneNumber,
                    this.email, this.ipAddress);
        }
    }

    /**
     * Used to get required index of customer data.
     */
    private enum Indexing {
        FIRST_NAME(0),
        LAST_NAME(1),
        CITY(2),
        STATE(3),
        POSTAL_ZIP_CODE(4),
        PHONE_NUMBER(5),
        EMAIL(6),
        IP_ADDRESS(7);
        private final int value;

        Indexing(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
}
