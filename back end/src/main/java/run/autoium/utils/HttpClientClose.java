package run.autoium.utils;

import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;

/**
 * 通过线程监听，及时关闭超时的连接
 */
@Component
public class HttpClientClose extends Thread {

    /**
     * httpClient连接池
     */
    @Autowired
    private PoolingHttpClientConnectionManager manager;

    /**
     * volatile表示多线程可变数据，一个线程修改，其他线程立即修改
     */
    private volatile boolean shutdown;

    /**
     * spring管理后立即开启监听线程
     */
    public HttpClientClose() {
        this.start();
    }

    /**
     * 每5秒清理一次连接
     */
    @Override
    public void run() {
        try {
            while (!shutdown) {
                synchronized (this) {
                    wait(5000);
                    manager.closeExpiredConnections();
                }
            }
        } catch (Exception e) {
            shutdown(); // 出现异常立即停止该线程
            e.printStackTrace();
        }
    }

    /**
     * 关闭httpClient所有的清理线程，并立刻清理失效连接
     */
    @PreDestroy // 容器关闭时执行该方法
    public void shutdown() {
        shutdown = true;
        synchronized (this) {
            notifyAll(); // 全部从等待中唤醒，执行关闭操作
        }
    }
}
