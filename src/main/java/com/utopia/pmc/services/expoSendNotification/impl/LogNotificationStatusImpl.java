package com.utopia.pmc.services.expoSendNotification.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.utopia.pmc.services.expoSendNotification.LogNotificationStatus;

import io.github.jav.exposerversdk.ExpoPushMessage;
import io.github.jav.exposerversdk.ExpoPushMessageTicketPair;
import io.github.jav.exposerversdk.ExpoPushTicket;
import io.github.jav.exposerversdk.PushClient;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class LogNotificationStatusImpl implements LogNotificationStatus {

        @Override
        public void logForSendNotification(PushClient client, List<ExpoPushMessage> expoPushMessages,
                        List<CompletableFuture<List<ExpoPushTicket>>> messageRepliesFutures)
                        throws InterruptedException, ExecutionException {

                List<ExpoPushTicket> allTickets = new ArrayList<>();
                List<ExpoPushMessageTicketPair<ExpoPushMessage>> zippedMessagesTickets = new ArrayList<>();

                for (CompletableFuture<List<ExpoPushTicket>> messageReplyFuture : messageRepliesFutures) {
                        for (ExpoPushTicket ticket : messageReplyFuture.get()) {
                                allTickets.add(ticket);
                        }
                        zippedMessagesTickets = client.zipMessagesTickets(expoPushMessages, allTickets);

                }

                List<ExpoPushMessageTicketPair<ExpoPushMessage>> okTicketMessages = client
                                .filterAllSuccessfulMessages(zippedMessagesTickets);
                String okTicketMessagesString = okTicketMessages.stream().map(
                                p -> "Title: " + p.message.getTitle() + ", Id:" + p.ticket.getId())
                                .collect(Collectors.joining(","));

                log.info("Recieved OK ticket for " +
                                okTicketMessages.size() +
                                " messages: " + okTicketMessagesString);

                List<ExpoPushMessageTicketPair<ExpoPushMessage>> errorTicketMessages = client
                                .filterAllMessagesWithError(zippedMessagesTickets);
                String errorTicketMessagesString = errorTicketMessages.stream().map(
                                p -> "Title: " + p.message.getTitle() + ", Error: " + p.ticket.getDetails().getError())
                                .collect(Collectors.joining(","));

                log.error("Recieved ERROR ticket for " +
                                errorTicketMessages.size() +
                                " messages: " +
                                errorTicketMessagesString);
        }

}
