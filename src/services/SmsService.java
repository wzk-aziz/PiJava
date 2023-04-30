/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package services;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class SmsService {
    private static final String ACCOUNT_SID = "ACed550faf67f6029d0c964cbd1f87f69c";
    private static final String AUTH_TOKEN = "f82ff32752b5756407b4512e792ace0e";
    private static final String TWILIO_PHONE_NUMBER = "+16813233888";

    public static void sendSms(String toPhoneNumber, String messageBody) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        Message message = Message.creator(
                new PhoneNumber(toPhoneNumber),
                new PhoneNumber(TWILIO_PHONE_NUMBER),
                messageBody)
                .create();

        System.out.println("Message sent: " + message.getSid());
    }
}
