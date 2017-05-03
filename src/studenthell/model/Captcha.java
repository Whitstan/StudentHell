package studenthell.model;

import java.util.Random;

public class Captcha {
    private String captha;

    public Captcha() {
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVW0123456789";
        final int n = alphabet.length();
        Random r = new Random();
        StringBuilder sb = new StringBuilder();
        
        for(int i=0; i<6; ++i) {
            sb.append(alphabet.charAt(r.nextInt(n)));
        }
        captha = sb.toString();
    }

    public String getCaptha() {
        return captha;
    }
}
