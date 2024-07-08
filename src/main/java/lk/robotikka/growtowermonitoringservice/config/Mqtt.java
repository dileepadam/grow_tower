//package lk.robotikka.growtowermonitoringservice.config;
//
//import org.eclipse.paho.client.mqttv3.MqttClient;
//import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
//import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.beans.factory.config.ConfigurableBeanFactory;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Scope;
//import org.springframework.core.io.ClassPathResource;
//
//import javax.net.ssl.KeyManagerFactory;
//import javax.net.ssl.SSLContext;
//import javax.net.ssl.SSLSocketFactory;
//import java.io.FileInputStream;
//import java.io.InputStream;
//import java.security.KeyStore;
//import java.security.cert.CertificateFactory;
//import java.security.cert.X509Certificate;
//import java.util.UUID;
//
//@Configuration
//public class Mqtt {
//
//    @Value("${mqtt.broker.url}")
//    private String brokerUrl;
//
//    @Value("${mqtt.client.id}")
//    private String clientId;
//
//    @Bean
//    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
//    public MqttClient mqttClient() throws Exception {
//        MqttClient client = new MqttClient(brokerUrl, clientId + "-" + UUID.randomUUID().toString(), new MemoryPersistence());
//        MqttConnectOptions options = new MqttConnectOptions();
//        options.setUserName("EPICServer");
//        options.setAutomaticReconnect(true);
//        options.setCleanSession(true);
//        options.setConnectionTimeout(1000);
//
//        SSLSocketFactory socketFactory = getSocketFactory();
//        options.setSocketFactory(socketFactory);
//
//        client.connect(options);
//        return client;
//    }
//
//    private static SSLSocketFactory getSocketFactory() throws Exception {
//
//        // Load the keystore from the classpath
//        KeyStore keyStore = KeyStore.getInstance("PKCS12");
//        InputStream keyStoreInputStream = new ClassPathResource("keystore.p12").getInputStream();
//        keyStore.load(keyStoreInputStream, "password".toCharArray()); // Keystore password
//
//        // Initialize KeyManagerFactory with the keystore
//        KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
//        kmf.init(keyStore, "password".toCharArray()); // Keystore password
//
//        // Set up the SSL context with the key manager
//        SSLContext context = SSLContext.getInstance("TLS");
//        context.init(kmf.getKeyManagers(), null, null);
//
//        return context.getSocketFactory();
//    }
//
//    private static X509Certificate loadCertificate(String filePath) throws Exception {
//        CertificateFactory cf = CertificateFactory.getInstance("X.509");
//        try (InputStream is = new FileInputStream(filePath)) {
//            return (X509Certificate) cf.generateCertificate(is);
//        }
//    }
//}
//
