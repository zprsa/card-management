package hr.task.cardmanagement.controllers.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CardFileRequest {

    private String oib;

    private CardFileRequest() {
        // jackson
    }

    public String getOib() {
        return oib;
    }

    public void setOib(String oib) {
        this.oib = oib;
    }


    @Override
    public String toString() {
        return "CardFileRequest{" +
                "oib='" + oib + '\'' +
                '}';
    }
}
