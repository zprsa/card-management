package hr.task.cardmanagement.controllers.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PersonRequest {

    private String name;
    private String surname;
    private String oib;
    private String cardStatus;

    private PersonRequest() {
        //jackson
    }

    public PersonRequest(String name, String surname, String oib, String cardStatus) {
        this.name = name;
        this.surname = surname;
        this.oib = oib;
        this.cardStatus = cardStatus;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getOib() {
        return oib;
    }

    public void setOib(String oib) {
        this.oib = oib;
    }

    public String getCardStatus() {
        return cardStatus;
    }

    public void setCardStatus(String cardStatus) {
        this.cardStatus = cardStatus;
    }

    @Override
    public String toString() {
        return "PersonRequest{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", oib='" + oib + '\'' +
                ", cardStatus='" + cardStatus + '\'' +
                '}';
    }
}
