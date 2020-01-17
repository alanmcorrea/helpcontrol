package es.indra.helpcontrol.encryption;

import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

import org.springframework.stereotype.Component;

@Component
public class Criptografia {
	
	public String getMD5(String stringEntrada) {
		
        String md5Retorno = null;
        MessageDigest md;
        
        try {
        	
            md = MessageDigest.getInstance("MD5");
            md.update(stringEntrada.getBytes(Charset.forName("UTF-8")));
            md5Retorno = String.format(Locale.ROOT, "%032x", new BigInteger(1, md.digest()));
            
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e);
        }
        return md5Retorno;
	}
	
	

}
