package run.autoium.config;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * httpClient配置类，将它需要的各个部分拆解为bean，通过线程池和工厂将其实例化，
 * 可以轻松的设置连接池的连接数、并发数；请求的连接时间、超时时间、响应的超时
 * 时间以及检测连接是否可用
 */
@Configuration
@ConfigurationProperties(prefix = "http")
public class HttpClientConfig {

    /**
     * 最大连接数
     */
    private Integer maxTotal;

    /**
     * 并发数
     */
    private Integer defaultMaxPerRoute;

    /**
     * 连接超时时间
     */
    private Integer connectTimeout;

    /**
     * 请求超时时间
     */
    private Integer connectionRequestTimeout;

    /**
     * 响应超时时间
     */
    private Integer socketTimeout;

    /**
     * 提交请求前测试连接是否可用
     */
    private Boolean staleConnectionCheckEnabled;

    /**
     * 实例化连接池，设置最大连接数和并发数
     *
     * @return httpClientConnectionManager
     */
    @Bean(name = "httpClientConnectionManager")
    public PoolingHttpClientConnectionManager getHttpClientConnectionManager() {
        PoolingHttpClientConnectionManager httpClientConnectionManager = new PoolingHttpClientConnectionManager();

        // 设置连接池最大连接数和并发数
        httpClientConnectionManager.setMaxTotal(maxTotal);
        httpClientConnectionManager.setDefaultMaxPerRoute(defaultMaxPerRoute);
        return httpClientConnectionManager;
    }

    /**
     * 创建httpClient工厂，设置工厂所用的连接池
     *
     * @param httpClientConnectionManager 连接池管理器
     * @return httpClientBuilder
     */
    @Bean(name = "httpClientBuilder")
    public HttpClientBuilder getHttpClientBuilder(@Qualifier("httpClientConnectionManager") HttpClientConnectionManager httpClientConnectionManager) {

        // HttpClientBuilder中的构造方法被protected修饰，所以这里不能直接
        // 使用new来实例化一个HttpClientBuilder,可以使用HttpClientBuilder
        // 提供的静态方法create()来获取HttpClientBuilder对象
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        httpClientBuilder.setConnectionManager(httpClientConnectionManager);
        return httpClientBuilder;
    }

    /**
     * 通过工厂创建httpClient
     *
     * @param httpClientBuilder 工厂
     * @return closeableHttpClient
     */
    @Bean
    public CloseableHttpClient getCloseableHttpClient(@Qualifier("httpClientBuilder") HttpClientBuilder httpClientBuilder) {
        return httpClientBuilder.build();
    }

    /**
     * 创建生产requestConfig的工厂
     *
     * @return requestConfigBuilder
     */
    @Bean(name = "requestConfigBuilder")
    public RequestConfig.Builder getRequestConfigBuilder() {
        RequestConfig.Builder requestConfigBuilder = RequestConfig.custom();

        // 设置请求的连接超时、请求超时、响应超时、测试连接
        requestConfigBuilder.setConnectTimeout(connectTimeout)
                .setConnectionRequestTimeout(connectionRequestTimeout)
                .setSocketTimeout(socketTimeout)
                .setStaleConnectionCheckEnabled(staleConnectionCheckEnabled);
        return requestConfigBuilder;
    }

    /**
     * 通过工厂生产requestConfig
     *
     * @param builder 工厂
     * @return requestConfig
     */
    @Bean
    public RequestConfig getRequestConfig(@Qualifier("requestConfigBuilder") RequestConfig.Builder builder) {
        return builder.build();
    }

    // 使用ConfigurationProperties必须配合set方法
    public void setMaxTotal(Integer maxTotal) {
        this.maxTotal = maxTotal;
    }

    public void setDefaultMaxPerRoute(Integer defaultMaxPerRoute) {
        this.defaultMaxPerRoute = defaultMaxPerRoute;
    }

    public void setConnectTimeout(Integer connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public void setConnectionRequestTimeout(Integer connectionRequestTimeout) {
        this.connectionRequestTimeout = connectionRequestTimeout;
    }

    public void setSocketTimeout(Integer socketTimeout) {
        this.socketTimeout = socketTimeout;
    }

    public void setStaleConnectionCheckEnabled(Boolean staleConnectionCheckEnabled) {
        this.staleConnectionCheckEnabled = staleConnectionCheckEnabled;
    }
}
