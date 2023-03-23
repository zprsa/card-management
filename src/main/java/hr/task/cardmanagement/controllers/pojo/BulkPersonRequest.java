package hr.task.cardmanagement.controllers.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BulkPersonRequest {

    private List<PersonRequest> persons;
    private int numberOfPersons;

    private BulkPersonRequest() {
        //jackson
    }

    public List<PersonRequest> getPersons() {
        return persons;
    }

    public void setPersons(List<PersonRequest> persons) {
        this.persons = persons;
    }

    public int getNumberOfPersons() {
        return numberOfPersons;
    }

    public void setNumberOfPersons(int numberOfPersons) {
        this.numberOfPersons = numberOfPersons;
    }

    @Override
    public String toString() {
        return "BulkPersonRequest{" +
                "persons=" + persons +
                ", numberOfPersons=" + numberOfPersons +
                '}';
    }
}
