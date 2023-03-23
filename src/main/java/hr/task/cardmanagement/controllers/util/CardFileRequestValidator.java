package hr.task.cardmanagement.controllers.util;

import hr.task.cardmanagement.controllers.pojo.CardFileRequest;
import hr.task.cardmanagement.ex.InvalidPersonException;
import org.springframework.util.StringUtils;

public class CardFileRequestValidator {

    private CardFileRequestValidator() {
        //util
    }

    public static void validateCardFileRequest(CardFileRequest cardFileRequest){

        if (StringUtils.isEmpty(cardFileRequest) || StringUtils.isEmpty(cardFileRequest.getOib()) ) {
            throw new InvalidPersonException("Invalid oib sent for card file creation");
        }
    }
}
