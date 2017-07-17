package components;

import config.ConfigurationManager;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;
import org.apache.ignite.cache.CacheAtomicityMode;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.spi.discovery.tcp.TcpDiscoverySpi;
import org.apache.ignite.spi.discovery.tcp.ipfinder.vm.TcpDiscoveryVmIpFinder;

import java.util.Arrays;

public class IgniteManager {
    Ignite ignite;

    private static IgniteManager instance = new IgniteManager();

    public static IgniteManager getInstance() {
        return instance;
    }

    private IgniteManager() {
    }

    private void getConnection() {
        try {
            if (ignite == null) {
                Ignition.setClientMode(true);
                IgniteConfiguration icfg = new IgniteConfiguration();
                TcpDiscoverySpi discoverySpi = new TcpDiscoverySpi();
                TcpDiscoveryVmIpFinder ipFinder = new TcpDiscoveryVmIpFinder();
                ipFinder.setAddresses(Arrays.asList(ConfigurationManager.getInstance().igniteAddress + ":47501..47502"));
                discoverySpi.setIpFinder(ipFinder);
                icfg.setDiscoverySpi(discoverySpi);
                ignite = Ignition.start(icfg);
            }
        } catch (Exception e) {
            e.printStackTrace();
            ignite = null;
        }
    }

    public IgniteCache getOrCreateCache(String cacheName) {
        org.apache.ignite.IgniteCache cache = null;
        try {
            getConnection();
            CacheConfiguration cfg = new CacheConfiguration(cacheName);
            cfg.setAtomicityMode(CacheAtomicityMode.TRANSACTIONAL);
            cache = ignite.getOrCreateCache(cfg);

        } catch (Exception e) {
            e.printStackTrace();
            cache = null;
        }

        return cache;
    }

}
