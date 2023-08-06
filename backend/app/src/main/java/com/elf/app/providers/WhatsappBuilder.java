package com.elf.app.providers;

import java.nio.file.Path;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import com.elf.app.models.Employee;


import it.auties.whatsapp.api.QrHandler;
import it.auties.whatsapp.api.WebHistoryLength;
import it.auties.whatsapp.api.Whatsapp;
import it.auties.whatsapp.controller.DefaultControllerSerializer;
import it.auties.whatsapp.model.contact.ContactJid;

public class WhatsappBuilder {

    private static final int BRAZIL = 55;
    private static Whatsapp api = null;
    private static String PHONE = "553399775898";
    private static UUID id = null;

    /**
     * Verifica se o dispositivo está registrado na WEB
     * 
     * @return true se estiver registrado, false caso contrário
     */
    public static boolean isValidated() {
        return api != null;
    }

    /**
     * Envia uma mensagem para um número de telefone usando o Whatsapp WEB
     * 
     * @param message Mensagem a ser enviada
     * @throws Exception Caso o dispositivo não esteja registrado
     */
    public static void send(String message, Employee employee) throws Exception {
        if (!isValidated()) {
            connectNewQr();
        } else {
            api = Whatsapp.webBuilder()
                    .serializer(new DefaultControllerSerializer(Path.of("C:/whatsapp4j")))
                    .newConnection(id)
                    .name("WhatsAppVPN")
                    .historyLength(WebHistoryLength.ZERO)
                    .build()
                    .connect()
                    .get(120, TimeUnit.SECONDS);
            id = api.store().uuid();
        }
        if (!employee.getCountry().equalsIgnoreCase("BRASIL")) {
            throw new Exception("O funcionário deve morar no brasil para usar receber mensagem no Whatsapp");
        }
        try {
            ContactJid contactJid = ContactJid.builder().server(ContactJid.Server.WHATSAPP)
                    .user(BRAZIL + "" + employee.getPhoneNumber1().replace("-", "").replace("(", "").replace(")", ""))
                    .build();
            api.sendMessage(contactJid, message);
            api.disconnect();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Registra novo dispositivo na WEB com um QRCode
     * 
     * @throws Exception
     */
    public static void connectNewQr() throws Exception {
        try {
            var options = Whatsapp.webBuilder()
                    .serializer(new DefaultControllerSerializer(Path.of("C:/whatsapp4j")))
                    .newConnection(PHONE)
                    .qrHandler(QrHandler.toTerminal())
                    .name("WhatsAppVPN")
                    .historyLength(WebHistoryLength.ZERO);
            api = options.build().connect().get(120, TimeUnit.SECONDS);
            id = api.store().uuid();
        } catch (Exception e) {
            throw e;
        }
    }
}
