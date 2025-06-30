package configs;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigUtil {

    private static Properties props = new Properties();

    static {
        try (FileInputStream input = new FileInputStream("C:\\Users\\danie\\OneDrive\\Documentos\\NetBeansProjects\\main\\resources\\email.properties")) {
            props.load(input);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Arquivo email.properties não encontrado!");
        }
    }

    public static String getEmailUsuario() {
        return props.getProperty("email.usuario");
    }

    public static String getEmailSenha() {
        return props.getProperty("email.senha");
    }
}
