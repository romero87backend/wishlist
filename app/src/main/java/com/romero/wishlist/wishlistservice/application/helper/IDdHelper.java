package com.romero.wishlist.wishlistservice.application.helper;

import com.romero.wishlist.wishlistservice.application.exceptions.IdGeradorException;
import org.apache.commons.lang3.StringUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

public final class IDdHelper {

    private IDdHelper() {
    }

    public static String gerarID(String idCliente, String idProduto) {
        try {
            if(StringUtils.isNoneBlank(idCliente) && StringUtils.isNoneBlank(idProduto)){
                return gerarSHA256Hash(idCliente + "-" + idProduto);
            }
            throw new IllegalAccessException("Id Cliente ou Id Produto  nulos");

        }catch(Exception e){
            throw new IdGeradorException("Erro ao gerar id", e);
        }
    }

    private static String gerarSHA256Hash(String input) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes = digest.digest(input.getBytes());
        StringBuilder hexString = new StringBuilder();

        for (byte hashByte : hashBytes) {
            String hex = Integer.toHexString(0xff & hashByte);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }

        return hexString.toString();
    }
}
