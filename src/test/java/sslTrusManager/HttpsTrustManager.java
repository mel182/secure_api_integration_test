package sslTrusManager;

import javax.net.ssl.*;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * This is the HTTPS trust manager which by pass the HTTPS handshake certification task
 * @author Melchior Vrolijk
 */
public class HttpsTrustManager implements X509TrustManager
{
    //region Local instances
    private static TrustManager[] trustManagers;
    private static final X509Certificate[] _AcceptedIssuers = new X509Certificate[]{};
    //endregion

    //region Determine if client is trusted
    /**
     * Determine if the client is a trusted source
     * @param chain The X509Certificate list
     * @return 'True' if client is trusted and 'false' if not
     */
    public boolean isClientTrusted(X509Certificate[] chain) {
        return true;
    }
    //endregion

    //region Determine if server is trusted
    /**
     * Determine if the server is a trusted source
     * @param chain The list of X509 certificate
     * @return 'True' if client is trusted and 'false' if not
     */
    public boolean isServerTrusted(X509Certificate[] chain) {
        return true;
    }
    //endregion

    //region X509 trust manager check client trusted
    /**
     * {@inheritDoc}
     */
    public void checkClientTrusted(java.security.cert.X509Certificate[] x509Certificates, String s) throws CertificateException { }
    //endregion

    //region X509 trust manager check client trusted
    /**
     * {@inheritDoc}
     */
    public void checkServerTrusted(java.security.cert.X509Certificate[] x509Certificates, String s) throws CertificateException { }
    //endregion

    //region Get accepted issuers
    /**
     * {@inheritDoc}
     */
    @Override
    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
        return _AcceptedIssuers;
    }

    public static void allowAllSSL() {

        HttpsURLConnection.setDefaultHostnameVerifier((s, sslSession) -> true);

        SSLContext context = null;
        if (trustManagers == null) {
            trustManagers = new TrustManager[]{new HttpsTrustManager()};
        }

        try {
            context = SSLContext.getInstance("TLS");
            context.init(null, trustManagers, new SecureRandom());
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            e.printStackTrace();
        }

        HttpsURLConnection.setDefaultSSLSocketFactory(context != null ? context.getSocketFactory() : null);
    }
    //endregion
}
