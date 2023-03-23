package hr.task.cardmanagement.controllers.pojo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(builder = PersonResponse.Builder.class)
public class PersonResponse {

    private final String name;
    private final String surname;
    private final String oib;
    private final String cardStatus;

    private PersonResponse(Builder builder) {
        this.name = builder.name;
        this.surname = builder.surname;
        this.oib = builder.oib;
        this.cardStatus = builder.cardStatus;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getOib() {
        return oib;
    }

    public String getCardStatus() {
        return cardStatus;
    }

    @Override
    public String toString() {
        return "PersonResponse{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", oib='" + oib + '\'' +
                ", cardStatus='" + cardStatus + '\'' +
                '}';
    }

    public static final class Builder {
        private String name;
        private String surname;
        private String oib;
        private String cardStatus;

        private Builder() {
        }

        public static Builder aPersonResponse() {
            return new Builder();
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withSurname(String surname) {
            this.surname = surname;
            return this;
        }

        public Builder withOib(String oib) {
            this.oib = oib;
            return this;
        }

        public Builder withCardStatus(String cardStatus) {
            this.cardStatus = cardStatus;
            return this;
        }

        public PersonResponse build() {
            return new PersonResponse(this);
        }
    }
}
