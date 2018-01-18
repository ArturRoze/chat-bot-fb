package hello.service;

import hello.domain.facebook.income.message.FacebookMessage;
import hello.domain.facebook.income.message.Recipient;
import hello.domain.facebook.outcome.message.MessageAns;
import hello.domain.facebook.outcome.message.RecipientAns;
import hello.enums.CurrencyType;
import hello.enums.WebhookIncomeMessageType;
import hello.service.currency.CurrencyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;
import java.util.Set;

@Service
public class MainService {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    private final MessageProcessor messageProcessor;
    private final CurrencyService currencyService;

    @Autowired
    public MainService(MessageProcessor messageProcessor, CurrencyService currencyService) {
        this.messageProcessor = messageProcessor;
        this.currencyService = currencyService;
    }

    public void processIncomeMessage(String request) {

        FacebookMessage incomeFacebookMessage = messageProcessor.getIncomeFacebookMessage(request);

        if (incomeFacebookMessage != null) {
            WebhookIncomeMessageType incomeMessageType = incomeFacebookMessage.determineMessageType();
            if (incomeMessageType == WebhookIncomeMessageType.FILE) {

            } else if (incomeMessageType == WebhookIncomeMessageType.MESSAGE) {

                Optional<String> textFromIncomeMessage = messageProcessor.getTextFromIncomeMessage(incomeFacebookMessage);

                if (textFromIncomeMessage.isPresent()) {
                    Set<CurrencyType> currencyTypes = messageProcessor.searchCurrencyInText(textFromIncomeMessage.get());
                    if (!currencyTypes.isEmpty()) {

                        String currencies = currencyService.getCurrency(currencyTypes);


                        Optional<String> senderIdFromIncomeMessage = Optional.empty();
                        try {
                            senderIdFromIncomeMessage = messageProcessor.getSenderIdFromIncomeMessage(request);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        RecipientAns recipientAns = null;
                        if (senderIdFromIncomeMessage.isPresent()){

                            recipientAns = new RecipientAns(senderIdFromIncomeMessage.get());
                        }

                        MessageAns messageAns = new MessageAns(currencies);

                        messageProcessor.sendAnswerMessageToSender(recipientAns, messageAns);

                    } else {
//                      TODO send message to messenger
                    }

                } else {
                    LOGGER.info("text is empty");
                }
            } else {
                LOGGER.info(incomeMessageType.name() + " not supported yet");
            }
        }
    }
}
