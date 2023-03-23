package hr.task.cardmanagement.controllers;

import hr.task.cardmanagement.controllers.pojo.CardFileRequest;
import hr.task.cardmanagement.controllers.util.CardFileRequestValidator;
import hr.task.cardmanagement.services.CardFileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class CardFileController {

    private static final Logger logger = LoggerFactory.getLogger(CardFileController.class);

    private final CardFileService cardFileService;

    public CardFileController(CardFileService cardFileService) {
        this.cardFileService = cardFileService;
    }

    @PostMapping(
            path = "/card-file",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createCardFile(
            @RequestBody CardFileRequest cardFileRequest) {

        CardFileRequestValidator.validateCardFileRequest(cardFileRequest);

        logger.debug("Requesting card file creation for person with oib: {}", cardFileRequest.getOib());

        cardFileService.createCardFile(cardFileRequest);
        return ResponseEntity.accepted().build();
    }
}
